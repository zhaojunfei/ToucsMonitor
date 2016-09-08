package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;
import com.adtec.toucs.monitor.devicemanage.*;

/**
 * <p>Title:POS�豸����Servlet�� </p>
 * <p>Description: ����POS�豸����������ص�Http����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */

public class POSDeviceManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//�����룺POS����
	private static final String POSINFOREG = "13101";

	//�����룺POS�޸�
	private static final String POSINFOUPDATE = "13102";

	//�����룺POS��ѯ
	private static final String POSINFOQUERY = "13103";
	
	//�����룺POSɾ��
	private static final String POSINFODELETE = "13104";

	//�����룺POS����
	private static final String POSINFOSTART = "12101";

	//�����룺POSͣ��
	private static final String POSINFOSTOP = "12102";

	//�����룺POS��������
	private static final String POSTXNSET = "13105";

	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";
	private static final String PARMSTARTFLAG = "setStart";
	private static final String PARMSTOPFLAG = "setStop";
	private static final String PARMKEYPAGE = "keyPage";
	private static final String PARMKEYSET = "keySet";
	private static final String PARMTXNPAGE = "txnPage";
	private static final String PARMTXNSET = "txnSet";

	//����������
	//private static final String POSPTYPE = "1";
	//private static final String POSCTYPE = "2";
	private static final String DCCTYPE = "3";

	//POS�豸���������·��
	private static final String MANAGE_HOME = "/ToucsMonitor/posdeviceconfig";

	//POS����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	//POS�豸������ʵ��
	private POSDeviceManageBean posManage = new POSDeviceManageBean();

	//POS�̻�������ʵ��
	private POSMerchantBean posMerchant = new POSMerchantBean();

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
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}	
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//ȡsession��Ϣ
		HttpSession session = request.getSession(true);

		//Ŀ��JSPҳ��
		String tarJsp = null;
		//�豸������������
		if (reqCode == null) {
			POSExInfo exInfo = (POSExInfo) session.getAttribute("exInfo");
			if (exInfo != null) {
				try {
					Vector v = posManage.qryMctPosInfo(exInfo.getMerchantid());
					request.setAttribute("posInfoList", v);
				} catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			} else {
				Debug.println("**********posInfoList get Null***************");
			}
			//Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosInfoManage.jsp?uid=" + login.getUserID();
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
					if (target.equals(PARMSTARTFLAG)) {
						posInfoStatSet(request, response, true, login);
					}
					if (target.equals(PARMSTOPFLAG)) {
						posInfoStatSet(request, response, false, login);
					}
					if (target.equals(PARMKEYPAGE)) {
						keyUpdateQuery(request, response, login);
					}
					if (target.equals(PARMKEYSET)) {
						keyUpdate(request, response, login);
					}
					//add by liyp 20030709
					if (target.equals(PARMTXNSET)) {
						Debug.println("********��doGet���� PARMTXNSET************");
						this.posTxnInfoUpdateQuery(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
					if (target.equals(PARMKEYPAGE)) {
						keyQuery(request, response, login);
					}
					if (target.equals(PARMTXNPAGE)) {
						posTxnQuery(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFODELETE)) {//ɾ��POS
				Debug.println("ɾ���̻���Ϣ...GET����");
				posInfoDelete(request, response, login);
			}else if (reqCode.equals(POSTXNSET)) {//����POS����
				if (target != null && target.equals(PARMTXNSET)) {
					Debug.println("**********doPost posTxnQuery*********");
					posTxnInfoUpdateQuery(request, response, login);
				} else {
					Debug.println("**********doPost posTxnInfoUpdate*********");
					this.posTxnInfoUpdate(request, response, login);
				}
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
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);		
			return;
		}
		//ȡsession��Ϣ
		HttpSession session = request.getSession(true);
		POSExInfo exInfo = (POSExInfo) session.getAttribute("exInfo");
		if (exInfo != null) {
			try {
				Vector v = posManage.qryMctPosInfo(exInfo.getMerchantid());
				request.setAttribute("posInfoList", v);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		} else {
			Debug.println("**********posInfoList get Null***************");
			Vector v = new Vector();
			session.setAttribute("posInfoList", v);
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
				if (target.equals(PARMGETQUERYPAGE)) {
					Debug.println("2");
					posInfoUpdateQuery(request, response, false, login);
				}
				if (target.equals(PARMSTARTFLAG)) {
					posInfoStatSet(request, response, true, login);
				}
				if (target.equals(PARMSTOPFLAG)) {
					posInfoStatSet(request, response, false, login);
				}
				if (target.equals(PARMKEYPAGE)) {
					keyUpdateQuery(request, response, login);
				}
				if (target.equals(PARMKEYSET)) {
					keyUpdate(request, response, login);
				}
				//add by liyp 20030709
				if (target.equals(PARMTXNSET)) {
					Debug.println("********��doPost���� PARMTXNSET************");
					this.posTxnInfoUpdateQuery(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
			if ( (target == null) || target.trim().equals("")) {
				posInfoQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoQueryPage(request, response, login);
				}
				if (target.equals(PARMKEYPAGE)) {
					keyQuery(request, response, login);
				}
				if (target.equals(PARMTXNPAGE)) {
					posTxnQuery(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFODELETE)) { //ɾ��POS
			Debug.println("ɾ���̻���Ϣ...POST����");
			posInfoDelete(request, response, login);
		}else if (reqCode.equals(POSTXNSET)) {//����POS����
			if (target != null && target.equals(PARMTXNSET)) {
				Debug.println("**********doPost posTxnQuery*********");
				posTxnInfoUpdateQuery(request, response, login);
			} else {
				Debug.println("**********doPost posTxnInfoUpdate*********");
				this.posTxnInfoUpdate(request, response, login);
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
		//���׼����俨����
		if (LoginManageBean.checkPermMask(maskCode, POSTXNSET) == 0) {
			req.setAttribute("POSTXNSET", "1");
			Debug.println("*****************can set POS TXN*************");
		}
		//����
		if (LoginManageBean.checkPermMask(maskCode, POSINFOSTART) == 0) {
			req.setAttribute("POSINFOSTART", "1");	
		}
		//ͣ��
		if (LoginManageBean.checkPermMask(maskCode, POSINFOSTOP) == 0) {
			req.setAttribute("POSINFOSTOP", "1");
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
	   **/
	private void initPosQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			System.out.println("1");
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
	   * ��ʼ����Կ����ҳ��������б���Ϣ
	   * @param req Http����
	   **/
	private void initKeyInfoList(HttpServletRequest req) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//DES���ܷ�ʽ
			v = CodeManageBean.queryCodes(CodeDefine.DES_TYPE, sq);
			req.setAttribute("desTypeList", v);
			//PINBLOCK��ʽ
			v = CodeManageBean.queryCodes(CodeDefine.PINBLOCK, sq);
			req.setAttribute("pinblkList", v);
			//MAC�㷨
			v = CodeManageBean.queryCodes(CodeDefine.MAC_METHOD, sq);
			req.setAttribute("macList", v);
		}catch (Exception exp) {
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
			POSDeviceManageBean pb = new POSDeviceManageBean();
			POSInfo pos;
			String pos_code = request.getParameter("pos_code");
			String mct_id = request.getParameter("merchant_id");
			try {
				if (mct_id != null && mct_id.length() > 0){
					Debug.println("Roxbbb:" + pos_code);
					pos = new POSInfo();
					pos.fetchData(request);
					pos.setOpenbankno(toucsString.unConvert(pos.getOpenbankno()));
					pos.setModel(toucsString.unConvert(pos.getModel()));
					pos.setCounter(toucsString.unConvert(pos.getCounter()));
					pos.setFixperson(toucsString.unConvert(pos.getFixperson()));
					pos.setFixaddress(toucsString.unConvert(pos.getFixaddress()));
					pos.setMct_name();
					request.setAttribute("posInfo", pos);
				} else if (pos_code != null && pos_code.length() > 0) {
					Debug.println("Roxaaa:" + pos_code);
					pos = pb.qryPosInfo(pos_code, "");
					pos.setMct_name();
					request.setAttribute("posInfo", pos);
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosInfoReg.jsp?post=" + POSINFOREG;
		} else {
			POSInfo posInfo = new POSInfo();
			//��Http������ȡ�豸������Ϣ
			posInfo.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posInfo.getMerchantid());
				int nReturn = posManage.regPosDevice(posInfo, nPosCount);
				PromptBean prompt = new PromptBean("�豸��������");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "POS�豸[" + posInfo.getPoscode() + "]��ӳɹ�");
					//add by liyp 20030917
					//֪ͨ�ͻ����������豸
					Debug.println("֪ͨ�ͻ���������POS�豸");
					notifyClient("N20007", posInfo.getPoscode(), "2");

					//ת�ɹ���ʾҳ��
					prompt.setPrompt("POS�豸��ӳɹ�!�豸˳���Ϊ��" + posInfo.getPoscode());
					prompt.setButtonUrl(0, "����¼��", MANAGE_HOME
                              + "?reqCode=13101&target=page&pos_code="
                              + posInfo.getPoscode());
					prompt.setButtonUrl(1, "�鿴�豸��Ϣ", MANAGE_HOME
                              + "?reqCode=" + POSINFOQUERY + "&pos_code="
                              + posInfo.getPoscode()
                              + "&uid=" + login.getUserID());
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("�ѵǼǵ�POS�豸�������ڸ��̻����POS�豸����" + "���Ǽ�POS�豸���ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "POS�豸[" + posInfo.getPoscode() + "]���ʧ��");
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
		System.out.println("pos_code:" + pos_code);
		{
			if (pos_code != null && !pos_code.equals("")) {
				String tarJsp = null;
				//��ѯ�豸������Ϣ
				try {
					//��ѯ�豸������Ϣ
					Vector v;
					SqlAccess sq = null;
					try {
						sq = SqlAccess.createConn();
						CodeBean codeBean = null;
						//�������
						v = CodeManageBean.queryCodes("0551", sq);
						request.setAttribute("equitTypeList", v);
						//���������ת����
						v = CodeManageBean.queryCodesNo4("0551", sq);
						request.setAttribute("equitTypeNo4List", v);
						//POS��ʹ�ñ�־
						v = CodeManageBean.queryCodes("0552", sq);
						request.setAttribute("PosstatList", v);
						//ͨѶ�����б�
						v = CodeManageBean.queryCodes(CodeDefine.NET_TYPE, sq);
						request.setAttribute("netTypeList", v);
						//POS����
						v = CodeManageBean.queryCodes("0570", sq);
						request.setAttribute("memo1aList", v);
						//���ӷ�ʽ
						v = CodeManageBean.queryCodes("0580", sq);
						request.setAttribute("memo1bList", v);
						//�����˻���־
						v = CodeManageBean.queryCodes("0590", sq);
						request.setAttribute("memo1cList", v);
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
			            codeBean = new CodeBean();
			            codeBean.setCodeId("2");
			            codeBean.setCodeDesc("�Զ��彻��Ȩ��");
			            v.add(codeBean);
			            request.setAttribute("preAuthorizedList", v);
					} catch (Exception exp) {
						exp.printStackTrace();
						System.out.println(exp.getMessage());
					} finally {
						if (sq != null) {
							sq.close();
						}
					}
					POSInfo posInfo = posManage.qryPosInfo(pos_code,request.getParameter("pos_kind"));
					if (posInfo == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "�̻������ڣ�");
					}
					tarJsp = PAGE_HOME + "PosInfo.jsp";
					request.setAttribute("posInfo", posInfo);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					String merchant_id = request.getParameter("merchant_id");
					Vector v = posManage.qryMctPosInfo(merchant_id);
					request.setAttribute("posInfoList", v);
					HttpSession session = request.getSession(true);
					POSExInfo exInfo = new POSExInfo();
					exInfo.setMerchantid(merchant_id);
					session.setAttribute("exInfo", exInfo);
					//���õ�ǰ�û��Ĳ���Ȩ�ޣ����ӡ��޸ġ�ɾ������ѯ���������׼��������ã�
					setPagePerm(request, login.getPermission());
					toPage(request, response,PAGE_HOME + "PosInfoManage.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
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
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosInfoQuery.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
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
                                  LoginInfo login) throws ServletException,IOException {
 		//ȡ�豸���DCC
 		String pos_code = request.getParameter("pos_code");
 		System.out.println("pos_code:" + pos_code);
 		//ȡ��ѯ���ݣ�������Ϣ��
 		if (!isGetPage) {
 			if (pos_code != null) {
 				String tarJsp = null;
 				//��ѯ�豸������Ϣ
 				try {
 					//��ѯ�豸������Ϣ
 					POSInfo posInfo = posManage.qryPosInfo(pos_code, DCCTYPE);
 					if (posInfo == null) {
 						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
 					}
 					posInfo.setMct_name();
 					initPosList(request, login.getOrgID());
 					setPagePerm(request, login.getPermission());
 					tarJsp = PAGE_HOME + "PosInfoUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
 					request.setAttribute("posInfo", posInfo);
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
 		POSInfo info = new POSInfo();
 		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
 		info.fetchData(request);
 		//��ȡ����
 		String key = request.getParameter("key");
 		try {
 			//�޸��豸������Ϣ
 			posManage.updatePosInfo(info, key);
 			//��¼��־
 			writeLog(login, "POS�豸[" + key + "]������Ϣ�޸ĳɹ�");
 			//֪ͨ�ͻ����޸��豸������Ϣ
 			notifyClient("N20007", key, "1");
 			
 			//ת�ɹ���ʾҳ��
 			PromptBean prompt = new PromptBean("�豸��������");
 			prompt.setPrompt("POS�豸��Ϣ�޸ĳɹ���");
 			prompt.setButtonUrl(0, "�����޸�", MANAGE_HOME + "?reqCode=13102&target=page");
 			prompt.setButtonUrl(1, "��������", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS�豸[" + key + "]������Ϣ�޸�ʧ��");
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
 			writeLog(login, "POS�豸[" + key + "]ɾ��ʧ��");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * �޸�POS�豸����ͣ��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   **/
 	private void posInfoKeySet(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
 		String key = request.getParameter("pos_code");
 		String targetPage = "/ToucsMonitor/posdeviceconfig?reqCode=15001&uid=" +
        login.getUserID();
 		RequestDispatcher dp;
 		dp = request.getRequestDispatcher(targetPage);
 		//���򵽽��ҳ��
 		dp.forward(request, response);
 	}

	  /**
	   * �޸��̻�������Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posInfoStatSet(HttpServletRequest request,
                              HttpServletResponse response,
                              boolean bStat,
                              LoginInfo login) throws ServletException, IOException {
 		String key = request.getParameter("pos_code");
 		try {
 			//�޸��豸������Ϣ
 			Debug.println("&&&&&START BEGIN&&&&&");
 			int nRst = posManage.setStatPos(key, bStat);
 			PromptBean prompt = new PromptBean("POS�豸����");
 			if (nRst > 0) {
 				//ת�ɹ���ʾҳ��
 				if (bStat) {
 					writeLog(login, "POS�豸[" + key + "]�����ɹ���");
 					prompt.setPrompt("POS�豸�����ɹ���");
 				} else {
 					writeLog(login, "POS�豸[" + key + "]ͣ�óɹ���");
 					prompt.setPrompt("POS�豸ͣ�óɹ���");
 				}
 			} else {
 				//֪ͨ�ͻ����޸��豸������Ϣ
 				//notifyClient("N20004",key,"1");
 				//תʧ����ʾҳ��
 				if (bStat) {
 					writeLog(login, "POS�豸[" + key + "]����ʧ�ܣ�");
 					prompt.setPrompt("POS�豸����ʧ�ܣ�");
 				} else {
 					writeLog(login, "POS�豸[" + key + "]ͣ��ʧ�ܣ�");
 					prompt.setPrompt("POS�豸ͣ��ʧ�ܣ�");
 				}
 			}
 			//��ҪУ���û��Ƿ��м���Ȩ��
 			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS�豸[" + key + "]״̬����ʧ��");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * ��ѯ��Կ������Ϣ
	   * @param atmCode �豸���
	   * @param post ��ѯĿ�ģ��޸ġ���ѯ��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyQuery(HttpServletRequest request,
                        HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		String tarJsp = null;
 		String posCode = request.getParameter("pos_code");
 		if (posCode == null) {
 			posCode = "";
 		}
 		posCode = posCode.trim();
 		try {
 			//����POS����������ת������POSP���
 			String posKind = request.getParameter("pos_kind");
 			if (posKind.trim().equalsIgnoreCase("2")) { //C���
 				posCode = "P" + posCode;
 			} else if (posKind.trim().equalsIgnoreCase("3")) { //DCC ���
 				posCode = POSDeviceManageBean.transDCCCodeToPCode(posCode);
 			}
 			AtmKeyInfo keyInfo = posManage.qryKeyInfo(posCode, login.getOrgID());
 			if (keyInfo == null) {
 				throw new MonitorException(ErrorDefine.NOT_FOUND, "δ�ҵ�ָ������Ϣ");
 			}
 			tarJsp = PAGE_HOME + "PosKeyInfo.jsp?" + "uid=" + login.getUserID();
 			request.setAttribute("keyInfo", keyInfo);
 		} catch (MonitorException exp) {
 			errProc(request, response, exp);
 		}
 		if (tarJsp != null) {
 			toPage(request, response, tarJsp);
 		}
 	}

	  /**
	   * ��ѯҪ�޸ĵ���Կ������Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyUpdateQuery(HttpServletRequest request,
                              HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		System.out.println("*************begin keyUpdateQuery***********");
 		String tarJsp = null;
 		//ȡ�Ǽǣ��������ã������޸ı�־ �޸�
 		String posCode = request.getParameter("pos_code");
 		
 		//ȱʡΪ�޸�
 		try {
 			posCode = POSDeviceManageBean.transDCCCodeToPCode(posCode);
 			AtmKeyInfo keyInfo = posManage.qryKeyInfo(posCode, login.getOrgID());
 			if (keyInfo == null) {
 				throw new MonitorException(ErrorDefine.NOT_FOUND, "δ�ҵ�ָ������Ϣ");
 			}
 			initKeyInfoList(request);
 			tarJsp = PAGE_HOME + "PosKeyConfig.jsp?" + "uid=" + login.getUserID();
 			request.setAttribute("keyInfo", keyInfo);
 		} catch (MonitorException exp) {
 			errProc(request, response, exp);
 		}
 		if (tarJsp != null) {
 			toPage(request, response, tarJsp);
 		}
 	}

	  /**
	   * �޸���Կ������Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyUpdate(HttpServletRequest request,
                         HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		AtmKeyInfo info = new AtmKeyInfo();
 		//��Http������ȡҪ�޸ĵ���Կ������Ϣ
 		info.fetchData(request);
 		try {
 			//�޸���Կ������Ϣ
 			posManage.updateKeyInfo(info);
 			//��¼��־
 			writeLog(login, "POS�豸[" + info.getAtmCode() + "]��Կ�����޸ĳɹ�");
 			//ȡ�Ǽǣ��������ã������޸ı�־
 			//ת�ɹ���ʾҳ��
 			PromptBean prompt = new PromptBean("POS�豸��Ϣ����");
 			prompt.setPrompt("POS�豸��Կ�޸ĳɹ���");
 			
 			prompt.setButtonUrl(1, "��������", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			//��¼��־
 			writeLog(login, "POS�豸[" + info.getAtmCode() + "]��Կ�����޸ĳɹ�");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * POS�豸���׼����俨����������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posTxnInfoUpdateQuery(HttpServletRequest request,
                                     HttpServletResponse response,
                                     LoginInfo login) throws ServletException, IOException {
 		//ȡ�豸���
 		String pos_dcc_code = request.getParameter("pos_code");
 		Debug.println("pos_dcc_code:" + pos_dcc_code);
 		if (pos_dcc_code != null) {
 			String tarJsp = null;
 			try {
 				//��ѯ�豸����Ȩ��������Ϣ
 				POSInfo pi = posManage.qryPosTxnInfo(pos_dcc_code, DCCTYPE);
 				if (pi == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
 				}
 				initPosList(request, login.getOrgID());
 				//����������Ϣ
 				Vector v = posManage.qryPosTxnInfoSet(pi);
 				request.setAttribute("txnSetList", v);
 				tarJsp = PAGE_HOME + "PosTxnConfig.jsp?reqCode=" + POSTXNSET + "&uid=" + login.getUserID();
 				request.setAttribute("posInfo", pi);
 				toPage(request, response, tarJsp);
 			} catch (MonitorException exp) {
 				errProc(request, response, exp);
 			}
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
 	private void posTxnInfoUpdate(HttpServletRequest request,
                                HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		Debug.println("********begin posTxnInfoUpdate********");
 		POSInfo info;
 		String key = "";
 		//������������ȡ��Ҫ�޸ĵ��豸���׺��������俨������
 		try {
 			POSDeviceManageBean pmb = new POSDeviceManageBean();
 			key = request.getParameter("pos_code");
 			info = pmb.qryPosInfo(key, "1");
 			info.fetchTxnData(request);
 			posManage.updatePosTxnInfo(info);
 			//��¼��־
 			writeLog(login, "POS�豸[" + key + "]����Ȩ�����óɹ�");
 			//ת�ɹ���ʾҳ��
 			PromptBean prompt = new PromptBean("POS�豸��Ϣ����");
 			prompt.setPrompt("POS����Ȩ�����óɹ���");
 			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS�豸[" + key + "]����Ȩ������ʧ��");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * ��ѯ����Ȩ��������Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posTxnQuery(HttpServletRequest request,
                           HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//ȡ�豸���
 		String posCode = request.getParameter("pos_code");
 		//ȡ�������
 		String codeType = request.getParameter("pos_kind");
 		if (posCode != null && codeType != null) {
 			String tarJsp = null;
 			try {
 				//��ѯ����Ȩ����Ϣ
 				POSInfo pi = posManage.qryPosTxnInfo(posCode, codeType);
 				if (pi == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
 				}
 				initPosList(request, login.getOrgID());
 				//����������Ϣ
 				Vector v = posManage.qryPosTxnInfoSet(pi);
 				request.setAttribute("txnSetList", v);
 				tarJsp = PAGE_HOME + "PosTxnConfigInfo.jsp?reqCode=" + POSTXNSET + "&uid=" + login.getUserID();
 				request.setAttribute("posInfo", pi);
 				toPage(request, response, tarJsp);
 			} catch (MonitorException exp) {
 				errProc(request, response, exp);
 			}
 		}
 	}
}
