package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;
import java.util.Vector;

/**
 * <p>Title:POS�豸����Servlet�� </p>
 * <p>Description: ����POS�豸����������ص�Http����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */
public class POSFCardDevManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSFCardDevManageServlet() {
	}

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	//�����룺POS����
	private static final String POSINFOREG = "13501";
	//�����룺POS�޸�
	private static final String POSINFOUPDATE = "13502";
	//�����룺POS��ѯ
	private static final String POSINFOQUERY = "13503";
	//�����룺POSɾ��
	private static final String POSINFODELETE = "13504";
	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";

	//POS�豸���������·��
	private static final String MANAGE_HOME = "/ToucsMonitor/fcardposconfig";
	//POS����ҳ���·��
	private static final String PAGE_HOME = "/POSDeviceManage/";
	//POS�豸������ʵ��
	private POSFCardDevManageBean posFCardManage = new POSFCardDevManageBean();
	//POS�̻�������ʵ��
	private POSFCardMerchantBean posFCardMerchant = new POSFCardMerchantBean();

	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���û���ҳ�治����
		response.setHeader("Cache-Control", "no-store");
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		String target = "";
		target = request.getParameter("target");
		//�û����У��
		LoginInfo login = null;
		//Ȩ�޿���
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			//У���û�����Ȩ��
			LoginManageBean.operValidate(login,POSINFOREG);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//Ŀ��JSPҳ��
		String tarJsp = null;
		//�豸������������
		if (reqCode == null) {
			try {
				List l = posFCardManage.getPOSInfoByObjId("", "");
				request.setAttribute("posInfoList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
			//Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			//������Ȩ��
			request.setAttribute("POSINFOREG", "1");
			request.setAttribute("POSINFOUPDATE", "1");
			request.setAttribute("POSINFODELETE", "1");
			request.setAttribute("POSINFOQUERY", "1");
			tarJsp = PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//ע��POS
			if (reqCode.equals(POSINFOREG)) {
				if ( (target == null) || target.trim().equals("")) {
					posInfoReg(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoReg(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOUPDATE)) {//�޸�POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoUpdate(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoUpdateQuery(request, response, true, login);
					}
					if (target.equals(PARMGETQUERYPAGE)) {
						posInfoUpdateQuery(request, response, false, login);
					}
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFODELETE)) {//ɾ��POS
				Debug.println("ɾ���̻���Ϣ...GET����");
				posInfoDelete(request, response, login);
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		Debug.println("********reqCode=" + reqCode + "**********");
		//ȡ�������
		String target = "";
		target = request.getParameter("target");
		
		//Ȩ�޿���
		LoginInfo login = null;	
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			LoginManageBean.operValidate(login, POSINFOREG);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		//ȡsession��Ϣ
		if (reqCode == null) {
			String merId = "";
			merId = request.getParameter("mer_id");
			try {
				List l = posFCardManage.getPOSInfoByObjId(merId, "");
				request.setAttribute("posInfoList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
			//Ҫ���ݵ�ǰ�û���ӵ���̻�����Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			String tarJsp = PAGE_HOME + "FCardPosDevManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		}
		//ע��POS
		if (reqCode.equals(POSINFOREG)) {
			if ( (target == null) || target.trim().equals("")) {
				posInfoReg(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoReg(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOUPDATE)) {//�޸�POS
			Debug.println("1+++++" + target);
			if ( (target == null) || target.trim().equals("")) {
				posInfoUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoUpdateQuery(request, response, true, login);
				}else if (target.equals(PARMGETQUERYPAGE)) {
					Debug.println("2");
					posInfoUpdateQuery(request, response, false, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
			if ( (target == null) || target.trim().equals("")) {
				posInfoQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoQueryPage(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFODELETE)) {//ɾ��POS
			Debug.println("ɾ���̻���Ϣ...POST����");
			posInfoDelete(request, response, login);
		}
	}

	  /**
	   * �����û���ҳ��Ĳ���Ȩ��
	   * @param req Http����
	   * @param maskCode �û�Ȩ����
	   */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		//����
		if (LoginManageBean.checkPermMask(maskCode, POSINFOREG) == 0) {
			req.setAttribute("POSINFOREG", "1");
		}
		//�޸�	
		if (LoginManageBean.checkPermMask(maskCode, POSINFOUPDATE) == 0) {
			req.setAttribute("POSINFOUPDATE", "1");
		}
		//ɾ��
		if (LoginManageBean.checkPermMask(maskCode, POSINFODELETE) == 0) {
			req.setAttribute("POSINFODELETE", "1");
		}
		//��ѯ	
		if (LoginManageBean.checkPermMask(maskCode, POSINFOQUERY) == 0) {
			req.setAttribute("POSINFOQUERY", "1");
		}
	}

	  /**
	   * ��ʼ���̻����ҳ��������б���Ϣ
	   * @param req Http����
	   * @param orgId ��������
	   */
	private void initPosList(HttpServletRequest req) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean = null;
			//�������
			v = CodeManageBean.queryCodes("0551", sq);
			req.setAttribute("equitTypeList", v);
			//���������ת����
			v = CodeManageBean.queryCodesNo4("0551", sq);
			req.setAttribute("equitTypeNo4List", v);
			//POS��ʹ�ñ�־
			v = CodeManageBean.queryCodes("0552", sq);
			req.setAttribute("PosstatList", v);
			//ͨѶ�����б�
			v = CodeManageBean.queryCodes(CodeDefine.NET_TYPE, sq);
			req.setAttribute("netTypeList", v);
			//POS����
			v = CodeManageBean.queryCodes("0570", sq);
			req.setAttribute("memo1aList", v);
			//���ӷ�ʽ
			v = CodeManageBean.queryCodes("0580", sq);
			req.setAttribute("memo1bList", v);
			//�����˻���־
			v = CodeManageBean.queryCodes("0590", sq);
			req.setAttribute("memo1cList", v);

			//Ԥ��Ȩ����
			v = new Vector();
			codeBean = new CodeBean();
			codeBean.setCodeId("0");
			codeBean.setCodeDesc("��Ԥ��Ȩ�ཻ��");
			v.add(codeBean);
			codeBean = new CodeBean();
			codeBean.setCodeId("1");
			codeBean.setCodeDesc("��Ԥ��Ȩ�ཻ��");
			v.add(codeBean);
			req.setAttribute("preAuthorizedList", v);
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}

	  /**
	   * ��ʼ���豸��ѯҳ��������б���Ϣ
	   * @param req Http����
	   * @param orgId ��������
	   */
	private void initPosQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		try {
			System.out.println("1");
			CodeBean codeBean;
			v = new Vector();
			Vector vTmp = posFCardMerchant.qryMerchantVector();
			for (int i = 0; i < vTmp.size(); i++) {
				codeBean = new CodeBean();
				codeBean.setCodeType("merchant_info");
				codeBean.setCodeId( (String) vTmp.get(i));
				codeBean.setCodeDesc( (String) vTmp.get(i + 1));
				v.add(codeBean);
				i++;
			}
			req.setAttribute("PosMerchantList", v);
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		}
	}

	  /**
	   * �豸�Ǽ�������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoReg(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//�豸�Ǽ�Get����
		if (isGetPage) {
			initPosList(request);
			tarJsp = PAGE_HOME + "FCardPosTerminalInfoAdd.jsp?post=" + POSINFOREG;
		} else {
			POSFCardInfo posInfo = new POSFCardInfo();
			//��Http������ȡ�豸������Ϣ
			posInfo.fetchData(request);
			try {
				int nReturn = posFCardManage.regPosDevice(posInfo);
				PromptBean prompt = new PromptBean("�豸��������");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "�⿨POS�豸[" + posInfo.getPos_no() + "]��ӳɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("POS�豸��ӳɹ�!�豸���Ϊ��" + posInfo.getPos_no());
					prompt.setButtonUrl(0, "�������", MANAGE_HOME + "?reqCode=13501&target=page");
					prompt.setButtonUrl(1, "�鿴�豸�б�", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					tarJsp = "/Success.jsp";
				} else {
					prompt.setPrompt("�ѵǼǵ�POS�豸�������ڸ��̻����POS�豸����" + "���Ǽ�POS�豸���ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "POS�豸[" + posInfo.getPos_no() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		//תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	  /**
	   * POS�豸��ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQuery(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException, IOException {
		//ȡ�豸���
		String mer_id = request.getParameter("mer_id");
		String pos_no = request.getParameter("pos_no");
		System.out.println("posInfoQuery:[" + mer_id + "][" + pos_no + "]");
		
		if (mer_id == null) {
			mer_id = "";
		}
		if (pos_no == null) {
			pos_no = "";
		}

		if (pos_no.equals("")) {
			try {
				List l = posFCardManage.getPOSInfoByObjId(mer_id, pos_no);
				request.setAttribute("posInfoList", l);
				//���õ�ǰ�û��Ĳ���Ȩ�ޣ����ӡ��޸ġ�ɾ������ѯ���������׼��������ã�
				setPagePerm(request, login.getPermission());
				toPage(request, response,PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			String tarJsp = null;
			//��ѯ�豸������Ϣ
			try {
				//��ѯ�豸������Ϣ
				initPosList(request);
				POSFCardInfo posInfo = posFCardManage.qryPosInfo(mer_id, pos_no);
				if (posInfo == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "�豸�����ڣ�");
				}
				tarJsp = PAGE_HOME + "FCardPosTerminalInfo.jsp";
				request.setAttribute("posInfo", posInfo);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

	  /**
	   * �̻���ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQueryPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
		initPosList(request);
		toPage(request, response, PAGE_HOME + "FCardPosInfoQuery.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
	}

	  /**
	   * POS�豸�޸Ĳ�ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdateQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
		//ȡ�豸���DCC
		String mer_id = request.getParameter("mer_id");
		String pos_code = request.getParameter("pos_no");
		String tarJsp = null;
		System.out.println("pos_code:" + pos_code);
		
		//ȡ��ѯ���ݣ�������Ϣ��
		if (!isGetPage) {
			if (pos_code != null && !pos_code.equals("")) {
				//��ѯ�豸������Ϣ
				try {
					//��ѯ�豸������Ϣ
					POSFCardInfo posInfo = posFCardManage.qryPosInfo(mer_id, pos_code);
					if (posInfo == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
					}
					initPosList(request);
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "FCardPosTerminalUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posInfo", posInfo);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					List l = posFCardManage.getPOSInfoByObjId(mer_id, "");
					request.setAttribute("posInfoList", l);
				} catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
				//Ҫ���ݵ�ǰ�û���ӵ���̻�����Ȩ�ޣ���ȷ��������ִ�еĲ���
				request.setAttribute("POSINFOREG", "1");
				request.setAttribute("POSINFOUPDATE", "1");
				request.setAttribute("POSINFODELETE", "1");
				request.setAttribute("POSINFOQUERY", "1");
				tarJsp = PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID();
				toPage(request, response, tarJsp);
			}
		} else {
			//�����������ҳ��
			initPosList(request);
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "FCardPosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
		}
	}

	  /**
	   * �޸�POS�豸������Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdate(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSFCardInfo info = new POSFCardInfo();
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		info.fetchData(request);
		//��ȡ����
		String merId = request.getParameter("mer_id");
		String posNo = request.getParameter("pos_no");
		Debug.println("RRRR:[" + merId + "][" + posNo + "]");
		try {
			//�޸��豸������Ϣ
			posFCardManage.updatePosInfo(info, merId, posNo);
			//��¼��־
			writeLog(login, "�⿨POS�豸[" + merId + "][" + posNo + "]������Ϣ�޸ĳɹ�");
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("�豸��������");
			prompt.setPrompt("POS�豸��Ϣ�޸ĳɹ���");
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "POS�豸[" + merId + "][" + posNo + "]������Ϣ�޸�ʧ��");
			errProc(request, response, exp);
		}
	}

	  /**
	   * POS�豸ɾ��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoDelete(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String merId = request.getParameter("mer_id");
		String posNo = request.getParameter("pos_no");
		try {
			//�޸��豸������Ϣ
			Debug.println("&&&&&DELETE BEGIN&&&&&");
			int nRst = posFCardManage.deletePosInfo(merId, posNo);
			PromptBean prompt = new PromptBean("POS�豸����");
			//��¼��־
			writeLog(login, "�⿨POS�豸[" + merId + "][" + posNo + "]ɾ���ɹ�");
			//ת�ɹ���ʾҳ��
			prompt.setPrompt("POS��Ϣɾ���ɹ���");
			//��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "�⿨POS�豸[" + merId + "][" + posNo + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}
}
