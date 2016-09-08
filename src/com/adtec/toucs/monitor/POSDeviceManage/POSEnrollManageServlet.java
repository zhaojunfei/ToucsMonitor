package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class POSEnrollManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//�����룺POS����
	private static final String POSINFOREG = "13601";

	//�����룺POS�޸�
	private static final String POSINFOUPDATE = "13602";

	//�����룺POS��ѯ
	private static final String POSINFOQUERY = "13603";

	//�����룺POSɾ��
	private static final String POSINFODELETE = "13604";

	//�����룺POS����ͣ��
	private static final String POSINFOMNG = "13605";

	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";

	//����������
	private static final String DCCTYPE = "3";

	//ǩԼPOS�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/posenrollconfig";

	//ǩԼPOS����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	//POS�豸������ʵ��
	private POSEnrollManageBean posManage = new POSEnrollManageBean();

	//POS�̻�������ʵ��
	private POSMerchantBean posMerchant = new POSMerchantBean();
  
	private static PageProcessor pageProcessor=null;
	private static final String PAGEPROCESSOR="PAGEPROCESSOR";

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
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//Ŀ��JSPҳ��
		String tarJsp = null;
		//�豸������������
		if (reqCode == null) {
			//Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosEnrollManage.jsp?uid=" + login.getUserID();
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
			} else if (reqCode.equals(POSINFOUPDATE)) {//�޸�POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoUpdate(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoUpdateQuery(request, response, true, login);
					}
				}
			} else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
				}
			} else if (reqCode.equals(POSINFODELETE)) {//ɾ��POS
				Debug.println("ɾ���̻���Ϣ...GET����");
				posInfoDelete(request, response, login);
			}else if (reqCode.equals(POSINFOMNG)) {   //ǩԼPOS�豸����
				if ( (target == null) || target.trim().equals("")) {
					posInfoManage(request, response, login, "1" );
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoManage(request, response, login, "0" );
					}
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		response.setContentType(CONTENT_TYPE);
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		//ȡ�������
		String target = request.getParameter("target");

		//Ȩ�޿���
		LoginInfo login = null;
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
//      	if (reqCode != null && reqCode.trim().length() > 0) {
//      	  LoginManageBean.operValidate(login, reqCode);
//     	 	}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
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
			Debug.println("1");
			if ( (target == null) || target.trim().equals("")) {
				posInfoUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoUpdateQuery(request, response, true, login);
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
		}else if(reqCode.equals(PAGEPROCESSOR)){
			List queryList=(List)session.getAttribute("CurrPageEnrollList");
			String strCurrPageNum=request.getParameter("CurrPageNum");
			request.setAttribute("CurrPageNum",strCurrPageNum);
			Integer intCurrPageNum=new Integer(strCurrPageNum);
			try {
				paginate(queryList,intCurrPageNum.intValue(),request);
			} catch (MonitorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toPage(request, response,PAGE_HOME + "PosEnrollList.jsp");
		}else if (reqCode.equals(POSINFODELETE)) {//ɾ��POS
			Debug.println("ɾ���̻���Ϣ...POST����");
			posInfoDelete(request, response, login);
		} else if (reqCode.equals(POSINFOMNG)) {   //ǩԼPOS�豸����
			if ( (target == null) || target.trim().equals("")) {
				posInfoManage(request, response, login, "1" );
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoManage(request, response, login, "0" );
				}
			}
		}
	}

	//Clean up resources
	public void destroy() {
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
	private void initPosList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean = null;

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
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean;
			v = new Vector();
			Vector vTmp = posMerchant.qryMerchantVector();
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
		} finally {
			if (sq != null) {
				sq.close();
			}
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
			POSEnrollManageBean pb = new POSEnrollManageBean();
			POSEnrollInfo posenroll;
			String pos_code = request.getParameter("pos_dcc_code");
			try {
				if (pos_code != null && pos_code.length() > 0) {
					posenroll = pb.qryPosInfo(pos_code, "3");
					if ( posenroll != null ) {		
						posenroll.setMct_name();
						request.setAttribute( "posEnroll", posenroll);
					}
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosEnrollReg.jsp?post=" + POSINFOREG;
		} else {
			POSEnrollInfo posEnroll = new POSEnrollInfo();
			//��Http������ȡ�豸������Ϣ
			posEnroll.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posEnroll.getMerchantid());
				int nReturn = posManage.regPosDevice(posEnroll, nPosCount);
				PromptBean prompt = new PromptBean("�豸��������");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "POS�豸[" + posEnroll.getPoscode() + "]��ӳɹ�");
					//֪ͨ�ͻ����������豸
					Debug.println("֪ͨ�ͻ���������POS�豸");
					notifyClient("N20007", posEnroll.getPoscode(), "2");
					
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("ǩԼPOS�豸��ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/Success.jsp";
				} else {
					prompt.setPrompt("�ѵǼǵ�POS�豸�������ڸ��̻����POS�豸����" + "���Ǽ�POS�豸���ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "POS�豸[" + posEnroll.getPoscode() + "]���ʧ��");
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
		String pos_code = request.getParameter("pos_code");
		//ȡ��ѯĿ�ģ��޸ġ���ѯ��
		if (pos_code != null && !pos_code.equals("")) {
			try {
				Vector v = posManage.qryPosInfoByPoscode( pos_code, request.getParameter("pos_kind"));
				request.setAttribute("posEnrollList", v);
				toPage(request, response,PAGE_HOME + "PosEnrollList.jsp?uid=" + login.getUserID());
			}
			catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			try {
				String merchant_id = request.getParameter("merchant_id");
				List list = posManage.qryMctPosInfo(merchant_id);
				request.setAttribute("posEnrollList", list);
				paginate(list,1,request);
				toPage(request, response,PAGE_HOME + "PosEnrollList.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
  
	private void paginate(List inList,int pageSize,HttpServletRequest request)throws MonitorException{
		if(pageProcessor==null){
			Debug.println("pageProcessor==null");
			pageProcessor=new PageProcessor();
		}
		pageProcessor.setItems(inList);
		Debug.println("inList.size()==========================="+inList.size());
		List list=pageProcessor.getPage(pageSize);
		request.setAttribute("CurrPageEnrollList",list);
		request.setAttribute("PageCount",String.valueOf(pageProcessor.getPageCount()));
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
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosEnrollManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
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
		String pos_code = request.getParameter("pos_code");
		System.out.println("pos_code:" + pos_code);
		//ȡ��ѯ���ݣ�������Ϣ��
		if (isGetPage) {
			if (pos_code != null) {
				String tarJsp = null;
				//��ѯ�豸������Ϣ
				try {
					//��ѯ�豸������Ϣ
					POSEnrollInfo posEnroll = posManage.qryPosEnroll(pos_code, DCCTYPE);
					if (posEnroll == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
					}
					posEnroll.setMct_name();
					initPosList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosEnrollUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posEnroll", posEnroll);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//�����������ҳ��
			initPosList(request, login.getOrgID());
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "PosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
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
		POSEnrollInfo info = new POSEnrollInfo();
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		info.fetchData(request);
		//��ȡ����
		String key = request.getParameter("key");
		try {
			//�޸��豸������Ϣ
			posManage.updatePosInfo(info, key);
			//��¼��־
			writeLog(login, "ǩԼPOS[" + key + "]������Ϣ�޸ĳɹ�");
			//֪ͨ�ͻ����޸��豸������Ϣ
			notifyClient("N20007", key, "1");
			
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("�豸��������");
			prompt.setPrompt("ǩԼPOS��Ϣ�޸ĳɹ���");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "ǩԼPOS[" + key + "]������Ϣ�޸�ʧ��");
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
		String key = request.getParameter("pos_code");
		try {
			//�޸��豸������Ϣ
			Debug.println("&&&&&DELETE BEGIN&&&&&");
			int nRst = posManage.deletePosInfo(key);
			PromptBean prompt = new PromptBean("POS�豸����");
			{
				//��¼��־
				writeLog(login, "POS�豸[" + key + "]ɾ���ɹ�");
				//֪ͨ�ͻ���ɾ���豸 add by liyp 20030918
				notifyClient("N20007", key, "0");
				
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("POS��Ϣɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "POS�豸[" + key + "]ɾ��ʧ��");
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
	private void posInfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String key = request.getParameter("pos_code");
		try {
			int nRst = posManage.managePosInfo(key, use_flag);
			PromptBean prompt = new PromptBean("POS�豸����");
			{
				if ( use_flag.equals("0") ) {
					//��¼��־
					writeLog(login, "ǩԼPOS�豸[" + key + "]ͣ�óɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("ǩԼPOS�豸[" + key + "]ͣ�óɹ���");
				} else {
					//��¼��־
					writeLog(login, "ǩԼPOS�豸[" + key + "]���óɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("ǩԼPOS�豸[" + key + "]���óɹ���");
				}
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "POS�豸[" + key + "]����ʧ��");
			errProc(request, response, exp);
		}
	}
}