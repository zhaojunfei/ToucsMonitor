package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;
import com.adtec.toucs.monitor.systemmanage.CodeManageBean;

/**
 * <p>Title: ��ҵIC����˾�����</p>
 * <p>Description: ��ҵIC����˾�����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p> 
 * @author liuxy
 * @version 1.0
 */

public class PosCompanyServlet extends CommonServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	
	private static final String APPEND = "16501"; // ��ҵIC����˾������
	
	private static final String DELETE = "16502"; // ��ҵIC����˾��ɾ��
	
	private static final String UPDATE = "16503"; // ��ҵIC����˾���޸�
	
	private static final String QUERY = "16504"; // ��ҵIC����˾���ѯ
	
	private static final String MANAGE = "16505"; // ��ҵIC����˾������ͣ��
	
	private static final String KEYDOWN = "16506"; // ��������Կ����ӡ
	
	private static final String STATISTICS = "16507"; // ��ҵIC����˾����ͳ��
	
	private static final String SUBSIDIARY = "16509"; // ��ҵIC����˾������ϸ
	
	private static final String PARMGETPAGE = "page"; // Ŀ��ҳ���������
	
	private static final String MANAGE_HOME = "/ToucsMonitor/poscompany"; // ��˾����Ϣ�����·��
	
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/"; // ��˾����Ϣ����ҳ���·��
	
	private PosCompanyBean posManage = new PosCompanyBean(); // ��˾����Ϣ������ʵ��
	
	public void init() throws ServletException {
		
	}
	
	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// ���û���ҳ�治����
		response.setHeader("Cache-Control", "no-store");
		// ȡ�������
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");
		
		// �û����У��
		LoginInfo login = null;
		try {
			// У���û��Ƿ��¼
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			// У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		// Ŀ��JSPҳ��
		String tarJsp = null;	
		// �豸������������
		if (reqCode == null) {
			// Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosCompanyManage.jsp?uid="
			+ login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// ע��POS
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(UPDATE)) {// �޸�POS
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {// ��ѯPOS
				if ((target == null) || target.trim().equals("")) {
					QueryInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						QueryInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// ɾ��POS
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(MANAGE)) { // ǩԼPOS�豸����
				if ((target == null) || target.trim().equals("")) {
					InfoManage(request, response, login, "1");
				} else {
					if (target.equals(PARMGETPAGE)) {
						InfoManage(request, response, login, "0");
					}
				}
			}else if (reqCode.equals(KEYDOWN)) { // ��Կ���ؼ���ӡ
				if ((target == null) || target.trim().equals("")) {
					KeyDown(request, response, login, "1");
				} else {
					if (target.equals(PARMGETPAGE)) {
						KeyDown(request, response, login, "0");
					}
				}
			}else if (reqCode.equals(STATISTICS)) {	// ����ͳ��
				if ((target == null) || target.trim().equals("")) {
					Statistic(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						Statistic(request, response, true, login);
					}
				}
			}else if (reqCode.equals(SUBSIDIARY)) {// ������ϸ
				if ((target == null) || target.trim().equals("")) {
					Subsidiary(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						Subsidiary(request, response, true, login);
					}
				}
			}
		}
	}
	
	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//PrintWriter out = response.getWriter();
		// ȡ�������
		String reqCode = request.getParameter("reqCode");
		// ȡ�������
		String target = request.getParameter("target");
		
		// Ȩ�޿���
		LoginInfo login = null;
		try {
			// У���û��Ƿ��¼
			login = checkLogin(request, response);
			// У���û�����Ȩ��
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
		}else if (reqCode.equals(UPDATE)) {// �޸�POS
			if ((target == null) || target.trim().equals("")) {
				UPdateInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UPdateInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(QUERY)) {// ��ѯPOS
			if ((target == null) || target.trim().equals("")) {
				QueryInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					QueryInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(DELETE)) {// ɾ��POS
			Debug.println("ɾ���̻���Ϣ...POST����");
			DeleteInfo(request, response, login);
		}else if (reqCode.equals(MANAGE)) { // ǩԼPOS�豸����
			if ((target == null) || target.trim().equals("")) {
				InfoManage(request, response, login, "1");
			} else {
				if (target.equals(PARMGETPAGE)) {
					InfoManage(request, response, login, "0");
				}
			}
		}else if (reqCode.equals(KEYDOWN)) { // ��Կ���ؼ���ӡ
			if ((target == null) || target.trim().equals("")) {
				KeyDown(request, response, login, "1");
			} else {
				if (target.equals(PARMGETPAGE)) {
					KeyDown(request, response, login, "0");
				}
			}
		}else if (reqCode.equals(STATISTICS)) {// ����ͳ��
			if ((target == null) || target.trim().equals("")) {
				Statistic(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					Statistic(request, response, true, login);
				}
			}
		}else if (reqCode.equals(SUBSIDIARY)) {// ������ϸ
			if ((target == null) || target.trim().equals("")) {
				Subsidiary(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					Subsidiary(request, response, true, login);
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
			// �շ�����
			v = CodeManageBean.queryCodes("0610", sq);
			req.setAttribute("CompanyKind", v);
			// �̻�����
			v = CodeManageBean.queryCodes("0600", sq);
			req.setAttribute("MchantType", v);
			// ҵ�����
			v = CodeManageBean.queryCodes("0601", sq);
			req.setAttribute("AgentCode", v);
			// ��ȫ����
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("SecuKind", v);
			// ����״̬
			v = CodeManageBean.queryCodes("0630", sq);
			req.setAttribute("HealthStat", v);
			// �����б�
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("BranchId", list);
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
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		// �豸�Ǽ�Get����
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosCompanyReg.jsp?post=" + APPEND;
		} else {
			PosCompanyInfo info = new PosCompanyInfo();
			// ��Http������ȡ�豸������Ϣ
			info.fetchData(request);
			try {
				int nReturn = posManage.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("��ҵIC����˾�����");
				if (nReturn != -1) {
					// ��¼��־
					writeLog(login, "��ҵIC����˾��[" + info.getCompany_id() + "]��ӳɹ�");
					// ת�ɹ���ʾҳ��
					prompt.setPrompt("��ҵIC����˾����ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("��ҵIC����˾����Ӳ��ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				// ��¼��־
				writeLog(login, "��ҵIC����˾��[" + info.getCompany_id() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		// תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * POS�豸ɾ�� 
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login�û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
	throws ServletException, IOException {
		// ������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String strKey = request.getParameter("company_id");
		try {
			// �޸��̻���Ϣ
			posManage.DeleteInfo(strKey);
			PromptBean prompt = new PromptBean("��ҵIC����˾�����");
			{
				// ��¼��־
				writeLog(login, "��ҵIC����˾��[" + strKey + "]ɾ���ɹ�");
				
				// ת�ɹ���ʾҳ��
				prompt.setPrompt("��ҵIC����˾��ɾ���ɹ���");
			}
			// ��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "��ҵIC����˾��[" + strKey + "]ɾ��ʧ��");
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
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		// ȡ�豸���DCC
		String strKey = request.getParameter("company_id");
		// ȡ��ѯ���ݣ�������Ϣ��
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				// ��ѯ�豸������Ϣ
				try {
					// ��ѯ�豸������Ϣ
					PosCompanyInfo info = posManage.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,
						"��ҵIC����˾�����ڣ�");
					}	
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosCompanyUpdate.jsp?post=" + UPDATE
					+ "&uid=" + login.getUserID();
					request.setAttribute("PosCompany", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			PosCompanyInfo info = new PosCompanyInfo();
			// ������������ȡ��Ҫ�޸ĵ��豸������Ϣ
			info.fetchData(request);
			// ��ȡ����
			try {
				// �޸��豸������Ϣ
				posManage.UpdateInfo(info, strKey);
				// ��¼��־
				writeLog(login, "��ҵIC����˾��[" + strKey + "]������Ϣ�޸ĳɹ�");
				
				// ת�ɹ���ʾҳ��
				PromptBean prompt = new PromptBean("��ҵIC����˾�����");
				prompt.setPrompt("��ҵIC����˾���޸ĳɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "��ҵIC����˾��[" + strKey + "]������Ϣ�޸�ʧ��");
				errProc(request, response, exp);
			}
		}
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
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "PosCompanyManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strType = request.getParameter("company_type");
			// ȡ��ѯĿ�ģ�ɾ�����޸ġ���ͣ��
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QueryInfoByList(strKey, strType);
					request.setAttribute("PosCompanyList", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QueryInfoByList("", strType);
					request.setAttribute("PosCompanyList", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
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
	private void InfoManage(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login, String use_flag) throws ServletException, IOException {
		// ������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String strKey = request.getParameter("company_id");
		try {
			posManage.ManageInfo(strKey, use_flag);
			PromptBean prompt = new PromptBean("��ҵIC����˾�����");	
				if (use_flag.equals("0")) {
					writeLog(login, "��ҵIC����˾��[" + strKey + "]ͣ�óɹ�");	
					prompt.setPrompt("��ҵIC����˾��[" + strKey + "]ͣ�óɹ���");
				} else {
					writeLog(login, "��ҵIC����˾��[" + strKey + "]���óɹ�");
					prompt.setPrompt("��ҵIC����˾��[" + strKey + "]���óɹ���");
				}
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "��ҵIC����˾��[" + strKey + "]����ʧ��");
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
	private void KeyDown(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login, String use_flag) throws ServletException, IOException {
		// ������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String strKey = request.getParameter("company_id");
		PosCompanyInfo info;
		try {
			info = posManage.QueryInfo(strKey, "");
			if (info.getSecu_kind().equals("0")) {
				String auth_code = posManage.down_auth(strKey, "CPY" + use_flag);
				PromptBean prompt = new PromptBean("��ҵIC����˾�����");
				writeLog(login, strKey, "MG7001", "��ҵIC����˾��������Կ�ɹ�");
				// ת�ɹ���ʾҳ��
				prompt.setPrompt(auth_code);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} else {
				posManage.down_auth(strKey, "CPY" + "");
				PromptBean prompt = new PromptBean("��ҵIC����˾�����");
				writeLog(login, strKey, "MG7001", "��ҵIC����˾����������Կ����ӡ�ɹ���");
				// ת�ɹ���ʾҳ��
				prompt.setPrompt("��ҵIC����˾��[" + strKey + "]��������Կ����ӡ�ɹ���");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException exp) {
			writeLog(login, "��ҵIC����˾��[" + strKey + "]��������Կ����ӡʧ��");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * ��ҵIC����˾����ͳ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void Statistic(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME
					+ "PosCompanyStatQuery.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strStart_date = "";
			String strEnd_date = "";
			String strMonth = request.getParameter("month");
			String strMerchant_id = request.getParameter("merchant_id");
			
			int intMonth = 0;
			int sysMonth = Integer.parseInt(posManage.sysDate().substring(4, 6));
			int sysYear = Integer.parseInt(posManage.sysDate().substring(0, 4));
			
			if (strMonth != null && !strMonth.equals("")) {
				intMonth = Integer.parseInt(strMonth);
				
				if (intMonth >= sysMonth) {
					strStart_date = (sysYear - 1) + strMonth + "01";
					strEnd_date = (sysYear - 1) + strMonth + "31";
				} else {
					strStart_date = sysYear + strMonth + "01";
					strEnd_date = sysYear + strMonth + "31";
				}
			} else {
				strStart_date = request.getParameter("start_date");
				strEnd_date = request.getParameter("end_date");
			}
			
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QueryStatisticInfo(strKey,strStart_date, strEnd_date, strMerchant_id);
					try {
						posManage.StatisticFile(v, strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCStatistic", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyStatistic.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QueryStatisticInfo("", strStart_date,strEnd_date, strMerchant_id);
					try {
						posManage.StatisticFile(v, strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCStatistic", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyStatistic.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}
	
	/**
	 * ��ҵIC����˾������ϸ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void Subsidiary(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME
					+ "PosCompanySubsQuery.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strStart_date = "";
			String strEnd_date = "";
			String strMonth = request.getParameter("month");
			String strMerchant_id = request.getParameter("merchant_id");
			
			int intMonth = 0;
			int sysMonth = Integer.parseInt(posManage.sysDate().substring(4, 6));
			int sysYear = Integer.parseInt(posManage.sysDate().substring(0, 4));
			
			if (strMonth != null && !strMonth.equals("")) {
				intMonth = Integer.parseInt(strMonth);
				
				if (intMonth >= sysMonth) {
					strStart_date = (sysYear - 1) + strMonth + "01";
					strEnd_date = (sysYear - 1) + strMonth + "31";
				} else {
					strStart_date = sysYear + strMonth + "01";
					strEnd_date = sysYear + strMonth + "31";
				}
			} else {
				strStart_date = request.getParameter("start_date");
				strEnd_date = request.getParameter("end_date");
			}
			
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QuerySubsidiaryInfo(strKey,strStart_date, strEnd_date, strMerchant_id);
					try {
						posManage.SubsidiaryFile(v,strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCSubsidiary", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanySubsidiary.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QuerySubsidiaryInfo("", strStart_date,strEnd_date, strMerchant_id);
					try {
						posManage.SubsidiaryFile(v,strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCSubsidiary", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanySubsidiary.jsp?uid="+ login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}
}