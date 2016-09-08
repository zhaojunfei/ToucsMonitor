package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:������Ȩ��ά��Servlet��</p>
 * <p>Description:��������ִ��ϵ������Ȩ��ά����ص�ҵ�����߼�������ҳ������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Rox
 * @version 1.0
 */

public class TxnAuthMngServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TXN_AUTH_ADD = "17401";
	private static final String TXN_AUTH_MOD = "17402";
	private static final String TXN_AUTH_DEL = "17403";
	private static final String TXN_AUTH_QRY = "17404";
	private static TxnAuthMngBean txnAuthMan = new TxnAuthMngBean();

	public TxnAuthMngServlet() {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		String sys_id = request.getParameter("SysId");

		//Ȩ�޿���
		LoginInfo login = null;
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			LoginManageBean.operValidate(login, TXN_AUTH_ADD);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//��ѯ����
		if (reqCode == null || reqCode.equals(TXN_AUTH_QRY)) {
			Debug.println("getQry");
			try {
				//��ѯ����
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode != null && reqCode.equals(TXN_AUTH_ADD)) {
			//��Ӵ���
			Debug.println("getAdd");
			TxnAuthBean auth = new TxnAuthBean();
			SqlAccess conn = null;
			try {
				conn = SqlAccess.createConn();
				Vector v1 = CodeManageBean.queryCodes("0280", conn);
				request.setAttribute("CardClassList", v1);
				Vector v2 = CodeManageBean.queryCodes("0290", conn);
				request.setAttribute("CardTypeList", v2);
			} catch (Exception e) {
				Debug.println(e.toString());
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
			auth.setSysId("atm");
			try {
				auth.qryAllTxn();
			} catch (MonitorException ex) {
				Debug.println(ex.toString());
			}

			request.setAttribute("auth", auth);
			toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthReg.jsp?post=" + TXN_AUTH_ADD + "&uid=" + login.getUserID());
		} else if (reqCode != null && reqCode.equals(TXN_AUTH_MOD)) {
			//�޸Ĵ���
			String card_class = request.getParameter("CardClass");
			String card_type = request.getParameter("CardType");
			Debug.println("getMod" + sys_id + "|" + card_class + "|" + card_type);
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if (sys_id == null) {
				sys_id = "atm";
			}
			TxnAuthBean auth = new TxnAuthBean();
			auth.setSysId("atm");
			try {
				auth.qryAllTxn();
			} catch (MonitorException ex) {
				Debug.println(ex.toString());
			}
			request.setAttribute("auth", auth);

			if (card_class == null || card_type == null) {
				Debug.println("Mod1");
				//��ѯ����
				try {
					queryAuth(request, response, sys_id, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				//ת�����޸�ҳ��
				Debug.println("getMod2");
				try {
					queryModAuth(request, response, sys_id, card_class, card_type, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else if (reqCode.equals(TXN_AUTH_DEL)) {
			//ɾ������
			Debug.println("getDel");
			String card_class = request.getParameter("CardClass");
			String card_type = request.getParameter("CardType");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if (sys_id == null) {
				sys_id = "atm";
			}
			if (card_class == null || card_type == null) {
				Debug.println("Del1");
				//��ѯ����
				try {
					queryAuth(request, response, sys_id, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Debug.println("getDel2" + card_class + card_type);
					//ɾ������
					txnAuthMan.deleteAuth(sys_id, card_class, card_type);
					//��ѯ����
					queryAuth(request, response, sys_id, login);
				}catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		String sys_id = request.getParameter("SysId");
		Debug.println("post1:" + sys_id);
		if (sys_id == null || sys_id.length() == 0) {
			sys_id = "atm";
		}
		Debug.println("post2:" + sys_id);
		//Ȩ�޿���
		LoginInfo login = null;
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			LoginManageBean.operValidate(login, TXN_AUTH_ADD);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//��ѯ����
		if (reqCode == null || reqCode.equals(TXN_AUTH_QRY)) {
			Debug.println("postQry");
			try {
				//��ѯ����
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_ADD)) {
			//��Ӵ���
			Debug.println("postAdd");
			try {
				TxnAuthBean auth = new TxnAuthBean();
				auth.fetchData(request);
				//���Ӵ���
				txnAuthMan.addAuth(auth);
				//��ѯ����
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_MOD)) {
			//�޸Ĵ���
			Debug.println("postMod");
			try {
				TxnAuthBean auth = new TxnAuthBean();
				auth.fetchData(request);
				txnAuthMan.modifyAuth(auth);
				//��ѯ����
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_DEL)) {
			//ɾ������
			Debug.println("postDel");
			try {
				//ɾ������
				if (sys_id == null) {
					sys_id = "atm";
				}
				String card_class = request.getParameter("CardClass");
				String card_type = request.getParameter("CardType");
				txnAuthMan.deleteAuth(sys_id, card_class, card_type);
				//��ѯ����
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

	  /**
	   * ��ѯ����
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param codeType ��������
	   * @param login ��¼�û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   * @throws MonitorException
	   */
	private void queryAuth(HttpServletRequest request,HttpServletResponse response,String sys_id, LoginInfo login) throws
      	ServletException, IOException, MonitorException {

		request.setAttribute("QUERYPERM", "1");
		request.setAttribute("ADDPERM", "1");
		request.setAttribute("MODPERM", "1");
		request.setAttribute("DELPERM", "1");

		//�����豸���Ͳ�ѯ������
		if (sys_id == null) {
			sys_id = "atm";
		}
		Vector v = txnAuthMan.queryAuths(sys_id);
		request.setAttribute("SysId", sys_id);
		request.setAttribute("list", v);

		Debug.println("Rox3" + sys_id);
		//ת�����ѯҳ��
		toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthQuery.jsp?&uid=" + login.getUserID());
	}

	private void queryModAuth(HttpServletRequest request,
                            HttpServletResponse response,
                            String sys_id, String card_class, String card_type,
                            LoginInfo login) throws ServletException,
      IOException, MonitorException {
		TxnAuthBean auth = txnAuthMan.queryAuth(sys_id, card_class, card_type);
		Debug.println("InMode");
		auth.qryAllTxn();
		request.setAttribute("auth", auth);
		request.setAttribute("modiflag", "1");
		SqlAccess conn = null;
		try {
			conn = SqlAccess.createConn();
			Vector v1 = CodeManageBean.queryCodes("0280", conn);
			request.setAttribute("CardClassList", v1);
			Vector v2 = CodeManageBean.queryCodes("0290", conn);
			request.setAttribute("CardTypeList", v2);
		} catch (Exception e) {
			Debug.println(e.toString());
		}finally{
			if (conn != null)
				conn.close();
		}
		toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthReg.jsp?post=" + TXN_AUTH_MOD + "&uid=" + login.getUserID());
	}
}
