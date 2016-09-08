package com.adtec.toucs.monitor.BankFutures;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.BankFutures.BfInvestorBean;
import com.adtec.toucs.monitor.BankFutures.BfInvestorInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


public class BfInvestorServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	// �����룺����
	private static final String APPEND = "17911";
	// �����룺ɾ��
	private static final String DELETE = "17912";
	// �����룺�޸�
	private static final String UPDATE = "17913";
	// �����룺��ѯ
	private static final String QUERY = "17914";
	// Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	
	// �̻���Ϣ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/bfinvestor";
	// �̻���Ϣ����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/BankFutures/";
	// �̻���Ϣ������ʵ��
	private BfInvestorBean ClassBean = new BfInvestorBean();

	public void init() throws ServletException {
	}

	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
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

		String tarJsp = null;
		if (reqCode == null) {
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "BfInvestorManage.jsp?uid=" + login.getUserID();
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
			}else if (reqCode.equals(DELETE)) {
				DeleteInfo(request, response, login);
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
			}
		}
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

		// ע��POS
		if (reqCode.equals(APPEND)) {
			if ((target == null) || target.trim().equals("")) {
				AppendInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AppendInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(DELETE)) {
			DeleteInfo(request, response, login);
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
		// ɾ��
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}
		// �޸�
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
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
			v = CodeManageBean.queryCodes("0552", sq);
			req.setAttribute("UseFlag", v);
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}

	/**
	 * �豸�̲ݹ�˾�ͻ���Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGetPage Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String tarJsp = null;
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "BfInvestorReg.jsp?post=" + APPEND;
		} else {
			BfInvestorInfo info = new BfInvestorInfo();
			info.fetchData(request);
			try {
				int nReturn = ClassBean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("�̲ݹ�˾�ͻ�����");
				if (nReturn != -1) {
					writeLog(login, "�̲ݹ�˾�ͻ�[" + info.getFc_id() + "]��ӳɹ�");
					prompt.setPrompt("�̲ݹ�˾�ͻ���ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("�̲ݹ�˾�ͻ���Ӳ��ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "�̲ݹ�˾�ͻ�[" + info.getFc_id() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	/**
	 * �̲ݹ�˾�ͻ���Ϣɾ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		String strKey1 = request.getParameter("fc_id");
		String strKey2 = request.getParameter("investor_id");
		try {
			ClassBean.DeleteInfo(strKey1, strKey2);
			PromptBean prompt = new PromptBean("�̲ݹ�˾����");
			writeLog(login, "�̲ݹ�˾�ͻ�[" + strKey2 + "]ɾ���ɹ�");	
			prompt.setPrompt("�̲ݹ�˾�ͻ�ɾ���ɹ���");
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "�̲ݹ�˾�ͻ�[" + strKey2 + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}

	/**
	 * �޸��̲ݹ�˾�ͻ���Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGetPage Get�����־
	 * @param login�û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String strKey1 = request.getParameter("fc_id");
		String strKey2 = request.getParameter("investor_id");
		if (isGetPage) {
			if (strKey1 != null) {
				String tarJsp = null;
				try {
					BfInvestorInfo info = ClassBean.QueryInfo(strKey1, strKey2);
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,"�̲ݹ�˾�ͻ������ڣ�");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "BfInvestorUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("BfInvestor", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			BfInvestorInfo info = new BfInvestorInfo();
			info.fetchData(request);
			try {
				ClassBean.UpdateInfo(info, strKey2);
				writeLog(login, "�̲ݹ�˾�ͻ�[" + strKey2 + "]������Ϣ�޸ĳɹ�");
				PromptBean prompt = new PromptBean("�̲ݹ�˾�ͻ�����");
				prompt.setPrompt("�̲ݹ�˾�ͻ��޸ĳɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "�̲ݹ�˾�ͻ�[" + strKey2 + "]������Ϣ�޸�ʧ��");
				errProc(request, response, exp);
			}
		}
	}

	/**
	 * �̲ݹ�˾�ͻ���Ϣ��ѯ������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login�û���Ϣ
	 * @param isGetPage Get����
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "BfInvestorManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("fc_id");
			String strType = request.getParameter("investor_id");
			try {
				Vector v = ClassBean.QueryInfoByList(strKey, strType);
				request.setAttribute("BfInvestorList", v);
				toPage(request, response, PAGE_HOME + "BfInvestorList.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
}
