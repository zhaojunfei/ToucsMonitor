package com.adtec.toucs.monitor.BankFutures;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.BankFutures.BfFcBean;
import com.adtec.toucs.monitor.BankFutures.BfFcInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


public class BfFcServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// �����룺����
	private static final String APPEND = "17901";
	// �����룺ɾ��
	private static final String DELETE = "17902";
	// �����룺�޸�
	private static final String UPDATE = "17903";
	// �����룺��ѯ
	private static final String QUERY = "17904";
	// �����룺��������Կ����ӡ
	private static final String KEYDOWN = "17906";
	// �����룺ͳ��
	private static final String STATISTIC = "17907";

	// Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	// �̻���Ϣ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/bffc";
	// �̻���Ϣ����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/BankFutures/";
	// �̻���Ϣ������ʵ��
	private BfFcBean ClassBean = new BfFcBean();

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
			tarJsp = PAGE_HOME + "BfFcManage.jsp?uid=" + login.getUserID();
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
						QueryInfo(request, response, false, login);
					}
				}
			}else if (reqCode.equals(KEYDOWN)) { 
				if ((target == null) || target.trim().equals("")) {
					KeyDown(request, response, login, "1");
				} else {
					if (target.equals(PARMGETPAGE)) {
						KeyDown(request, response, login, "0");
					}
				}
			}else if (reqCode.equals(STATISTIC)) {
				if ((target == null) || target.trim().equals("")) {
					Statistic(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						Statistic(request, response, true, login);
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
					QueryInfo(request, response, false, login);
				}
			}
		}else if (reqCode.equals(KEYDOWN)) { 
			if ((target == null) || target.trim().equals("")) {
				KeyDown(request, response, login, "1");
			} else {
				if (target.equals(PARMGETPAGE)) {
					KeyDown(request, response, login, "0");
				}
			}
		}else if (reqCode.equals(STATISTIC)) {
			if ((target == null) || target.trim().equals("")) {
				Statistic(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					Statistic(request, response, true, login);
				}
			}
		}
	}

	// Clean up resources
	public void destroy() {
	}

	/**
	 * �����û���ҳ��Ĳ���Ȩ��
	 * @param reqHttp����
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
	 * @param reqHttp����
	 * @param orgId��������
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// ��ȫ����
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("SecuKind", v);
			// ʹ�ñ�־
			v = CodeManageBean.queryCodes("0558", sq);
			req.setAttribute("SignFlag", v);
			// ʹ�ñ�־
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
	 * �̲ݹ�˾��Ϣ�Ǽ�������
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
			tarJsp = PAGE_HOME + "BfFcReg.jsp?post=" + APPEND;
		} else {
			BfFcInfo info = new BfFcInfo();
			info.fetchData(request);
			try {
				int nReturn = ClassBean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("�̲ݹ�˾����");
				if (nReturn != -1) {
					writeLog(login, "�̲ݹ�˾[" + info.getFc_id() + "]��ӳɹ�");
					prompt.setPrompt("�̲ݹ�˾��ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("�̲ݹ�˾���ʧ�ܣ�");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "�̲ݹ�˾[" + info.getFc_id() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	/**
	 * �̲ݹ�˾��Ϣɾ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		String strKey = request.getParameter("fc_id");
		try {
			ClassBean.DeleteInfo(strKey);
			PromptBean prompt = new PromptBean("�̲ݹ�˾����");
			writeLog(login, "�̲ݹ�˾[" + strKey + "]ɾ���ɹ�");
			prompt.setPrompt("�̲ݹ�˾ɾ���ɹ���");
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "�̲ݹ�˾[" + strKey + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}

	/**
	 * �޸��̲ݹ�˾������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGetPage Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String strKey = request.getParameter("fc_id");
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				try {
					BfFcInfo info = ClassBean.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,"�̲ݹ�˾�����ڣ�");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "BfFcUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("BfFc", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			BfFcInfo info = new BfFcInfo();
			info.fetchData(request);
			try {
				ClassBean.UpdateInfo(info, strKey);
				writeLog(login, "�̲ݹ�˾[" + strKey + "]������Ϣ�޸ĳɹ�");
				PromptBean prompt = new PromptBean("�̲ݹ�˾����");
				prompt.setPrompt("�̲ݹ�˾�޸ĳɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "�̲ݹ�˾[" + strKey + "]������Ϣ�޸�ʧ��");
				errProc(request, response, exp);
			}
		}
	}

	/**
	 * �̲ݹ�˾��Ϣ��ѯ������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @param isGetPage Get����
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "BfFcManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("fc_id");
			String strType = request.getParameter("fc_id");
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = ClassBean.QueryInfoByList(strKey, strType);
					request.setAttribute("BfFcList", v);
					toPage(request, response, PAGE_HOME + "BfFcList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = ClassBean.QueryInfoByList("", strType);
					request.setAttribute("BfFcList", v);
					toPage(request, response, PAGE_HOME + "BfFcList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

	/**
	 * �̲ݹ�˾��Ϣɾ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param use_flag ʹ�ñ�ʶ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void KeyDown(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login, String use_flag)
			throws ServletException, IOException {
		String strKey = request.getParameter("fc_id");
		BfFcInfo info;
		try {
			info = ClassBean.QueryInfo(strKey, "");
			if (info.getSecu_kind().equals("0")) {
				String auth_code = ClassBean.down_auth(strKey, "");
				PromptBean prompt = new PromptBean("�̲ݹ�˾����");
				writeLog(login, strKey, "MG7001", "�̲ݹ�˾������Կ�ɹ�");
				prompt.setPrompt(auth_code);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
			} else {
				ClassBean.down_auth(strKey, "");
				PromptBean prompt = new PromptBean("�̲ݹ�˾����");
				writeLog(login, strKey, "MG7001", "�̲ݹ�˾��������Կ����ӡ�ɹ���");
				prompt.setPrompt("�̲ݹ�˾[" + strKey + "]��������Կ����ӡ�ɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException exp) {
			writeLog(login, "�̲ݹ�˾[" + strKey + "]��������Կ����ӡʧ��");
			errProc(request, response, exp);
		}
	}

	/**
	 * �̲ݹ�˾����ͳ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @param isGetPage Get����
	 * @throws ServletException
	 * @throws IOException
	 */
	private void Statistic(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "BfFcStatQuery.jsp?post="
					+ QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("fc_id");
			String strStart_date = request.getParameter("start_date");
			String strEnd_date = request.getParameter("end_date");
			try {
				BfFcStatistic Info = ClassBean.QueryStatisticInfo(strKey,strStart_date, strEnd_date);
				request.setAttribute("bffcstatistic", Info);
				toPage(request, response, PAGE_HOME + "BfFcStatistic.jsp?uid="
						+ login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
}
