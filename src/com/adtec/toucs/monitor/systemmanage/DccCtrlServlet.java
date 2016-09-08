package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;


public class DccCtrlServlet extends CommonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// �����룺����
	private static final String APPEND = "17801";
	// �����룺ɾ��
	private static final String DELETE = "17802";
	// �����룺�޸�
	private static final String UPDATE = "17803";
	// �����룺��ѯ
	private static final String QUERY = "17804";

	// Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	// �̻���Ϣ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/dccctrl";
	// �̻���Ϣ����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/SystemManage/";
	// �̻���Ϣ������ʵ��
	private DccCtrlBean posManage = new DccCtrlBean();

	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");

		LoginInfo login = null;
		try {
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		String tarJsp = null;
		if (reqCode == null) {
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "DccCtrlManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(UPDATE)) {
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {
				if ((target == null) || target.trim().equals("")) {
					QueryInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						QueryInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {
				DeleteInfo(request, response, login);
			}
		}
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");

		LoginInfo login = null;
		try {
			login = checkLogin(request, response);
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		if (reqCode.equals(APPEND)) {
			if ((target == null) || target.trim().equals("")) {
				AppendInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AppendInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(UPDATE)) {
			if ((target == null) || target.trim().equals("")) {
				UPdateInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UPdateInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(QUERY)) {
			if ((target == null) || target.trim().equals("")) {
				QueryInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					QueryInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(DELETE)) {
			Debug.println("ɾ���̻���Ϣ...POST����");
			DeleteInfo(request, response, login);
		}
	}

	// Clean up resources
	public void destroy() {
	}

  /**
   * �����û���ҳ��Ĳ���Ȩ��
   * @param req Http����
   * @param maskCode�û�Ȩ����
   */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		// ����
		if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
			req.setAttribute("APPEND", "1");
		}
		// �޸�
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
		}
		// ɾ��
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}
		// ��ѯ
		if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
			req.setAttribute("QUERY", "1");
		}
	}

  /**
   * ��ʼ�����ҳ��������б���Ϣ
   * @param req Http����
   * @param orgId��������
   */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// ��������
			v = CodeManageBean.queryCodes("0567", sq);
			req.setAttribute("ChannelId", v);
			// ҵ������
			v = CodeManageBean.queryCodes("0571", sq);
			req.setAttribute("ServiceKind", v);
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
   * @param isGetPage Get�����־
   * @param login�û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "DccCtrlReg.jsp?post=" + APPEND;
		} else {
			DccCtrlInfo info = new DccCtrlInfo();
			info.fetchData(request);
			try {
				int nReturn = posManage.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("������Ϣ����");
				if (nReturn != -1) {
					writeLog(login, "����[" + info.getChannel_id() + "]��ӳɹ�");
					prompt.setPrompt("������Ϣ��ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("������Ϣ��Ӳ��ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "����[" + info.getChannel_id() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

  /**
   * ������Ϣɾ��
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGetPage Get�����־
   * @param login�û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		String strKey1 = request.getParameter("channel_id");
		String strKey2 = request.getParameter("service");
		try {
			posManage.DeleteInfo(strKey1, strKey2);
			PromptBean prompt = new PromptBean("������Ϣ����");
			writeLog(login, "����[" + strKey1 + "]ɾ���ɹ�");
			prompt.setPrompt("������Ϣɾ���ɹ���");
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "����[" + strKey1 + "]ɾ��ʧ��");
			errProc(request, response, exp);
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
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String strKey1 = request.getParameter("channel_id");
		String strKey2 = request.getParameter("service");
		if (isGetPage) {
			if (strKey1 != null && strKey2 != null ) {
				String tarJsp = null;
				try {
					DccCtrlInfo info = posManage.QueryInfo(strKey1, strKey2);
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,"������Ϣ�����ڣ�");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "DccCtrlUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("DccCtrl", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			DccCtrlInfo info = new DccCtrlInfo();
			info.fetchData(request);
			try {
				posManage.UpdateInfo(info, strKey1);
				writeLog(login, "����[" + strKey1 + "]������Ϣ�޸ĳɹ�");
				PromptBean prompt = new PromptBean("������Ϣ����");
				prompt.setPrompt("������Ϣ�޸ĳɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "����[" + strKey1 + "]������Ϣ�޸�ʧ��");
				errProc(request, response, exp);
			}
		}
	}

  /**
   * POS�豸��ѯ������
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGetPage Get����
   * @param login�û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		InitInfoList(request, login.getOrgID());
		try {
			Vector v = posManage.QueryInfoByList();
			request.setAttribute("DccCtrlList", v);
			toPage(request, response, PAGE_HOME + "DccCtrlList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}
}
