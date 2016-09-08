package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 2.0
 */

public class POSAccountServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSAccountServlet() {
	}
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//�Թ��˻�
	//�����룺����
	private static final String POSINFOREG = "13701";
	//�����룺�޸�
	private static final String POSINFOUPDATE = "13702";
	//�����룺��ѯ
	private static final String POSINFOQUERY = "13703";
	//�����룺ɾ��
	private static final String POSINFODELETE = "13704";
	
	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	
	//ǩԼPOS�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/accountconfig";
	
	//ǩԼPOS����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
	
	//POS�豸������ʵ��
	private POSAccountBean Manage = new POSAccountBean();
	
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
		
		//Ŀ��JSPҳ��
		String tarJsp = null;		
		//�豸������������
		if (reqCode == null) {
			//Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosAccountReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//ע��POS
			if (reqCode.equals(POSINFOREG)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOUPDATE)) { //�޸�POS
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//��ѯPOS
				QueryInfo(request, response, login);
			}else if (reqCode.equals(POSINFODELETE)) { //ɾ��POS
				Debug.println("ɾ���̻���Ϣ...GET����");
				DeleteInfo(request, response, login);
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//ע��POS
		if (reqCode.equals(POSINFOREG)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		} else if (reqCode.equals(POSINFOUPDATE)) {//�޸�POS
			Debug.println("1");
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) { //��ѯPOS
			QueryInfo(request, response, login);
		}else if (reqCode.equals(POSINFODELETE)) { //ɾ��POS
			DeleteInfo(request, response, login);
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
	   * �豸�Ǽ�������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void AddInfo(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//�豸�Ǽ�Get����
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosAccountReg.jsp?post=" + POSINFOREG;
		} else {
			POSAccount Info = new POSAccount();
			//��Http������ȡ�豸������Ϣ
			Info.fetchData(request);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("�Թ��ʻ�����");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "�Թ��ʻ�[" + Info.getAccount() + "]��ӳɹ�");
					//֪ͨ�ͻ����������豸
					notifyClient("N20007", Info.getAccount(), "2");
				} else {
					prompt.setPrompt("�Թ��ʻ�[" + Info.getAccount() + "]���ʧ�ܣ�");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
				String account = request.getParameter("account");
				String auth_code = Manage.down_auth(account);
				writeLog(login, account, "MG7830", "�Թ��ʻ���Ȩ�����سɹ���");
				request.setAttribute("auth_code", auth_code);
				request.setAttribute("prompt", prompt);
				tarJsp = "/ToucsMonitor/POSDeviceManage/PosAccountAuthDown.jsp";
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "�Թ��ʻ�[" + Info.getAccount() + "]���ʧ�ܣ�");
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
 	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException, IOException {
 		String tarJsp = null;
 		try {
 			Vector v = Manage.VqueryInfo( "", "" );
 			request.setAttribute("accountList", v);
 			toPage(request, response,PAGE_HOME + "PosAccountList.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			errProc(request, response, exp);
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
 	private void QueryInfoPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
 		toPage(request, response, PAGE_HOME + "PosAccountManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
 	}

	  /**
	   * POS�豸�޸Ĳ�ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
 		//ȡ�豸���DCC
 		String key = request.getParameter("account");
 		System.out.println("pos_code:" + key);
 		//ȡ��ѯ���ݣ�������Ϣ��
 		if (key != null) {
 			String tarJsp = null;
 			//��ѯ�豸������Ϣ
 			try {
 				//��ѯ�豸������Ϣ
 				POSAccount Info = Manage.queryInfo(key, "");
 				if (Info == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "�Թ��ʻ������ڣ�");
 				}
 				setPagePerm(request, login.getPermission());
 				tarJsp = PAGE_HOME + "PosAccountUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
 				request.setAttribute("posAccount", Info);
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
 	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		POSAccount info = new POSAccount();
 		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
 		info.fetchData(request);
 		//��ȡ����
 		String key = request.getParameter("account");
 		try {
 			//�޸��豸������Ϣ
 			Manage.updateInfo(info, key);
 			//��¼��־
 			writeLog(login, "�Թ��ʻ�[" + key + "]�޸ĳɹ�");
 			//֪ͨ�ͻ����޸��豸������Ϣ
 			notifyClient("N20007", key, "1");
 			
 			//ת�ɹ���ʾҳ��
 			PromptBean prompt = new PromptBean("�豸��������");
 			prompt.setPrompt("�Թ��ʻ�[" + key + "]�޸ĳɹ���");
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "�Թ��ʻ�[" + key + "]�޸�ʧ��");
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
 	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
 		String key = request.getParameter("account");
 		try {
 			//�޸��豸������Ϣ
 			Debug.println("&&&&&DELETE BEGIN&&&&&");
 			int nRst = Manage.deleteInfo(key);
 			PromptBean prompt = new PromptBean("�Թ��ʻ�����");
 			{
 				//��¼��־
 				writeLog(login, "�Թ��ʻ�[" + key + "]ɾ���ɹ�");

 				//֪ͨ�ͻ���ɾ���豸 add by liyp 20030918
 				notifyClient("N20007", key, "0");
 				
 				//ת�ɹ���ʾҳ��
 				prompt.setPrompt("�Թ��ʻ�[" + key + "]ɾ���ɹ���");
 			}
 			//��ҪУ���û��Ƿ��м���Ȩ��
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			//��¼��־
 			writeLog(login, "�Թ��ʻ�[" + key + "]ɾ��ʧ��");
 			errProc(request, response, exp);
 		}
 	}
}