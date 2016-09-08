package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;
import com.adtec.toucs.monitor.systemmanage.CodeManageBean;

/**
 * <p>Title: ����ǩԼ����</p>
 * <p>Description: ����ǩԼ����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p> 
 * @author liuxy
 * @version 1.0
 */

public class PosFundServlet extends CommonServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	private static final String FUND = "13301"; // ����ǩԼͳ��	
	private static final String PARMGETPAGE = "page"; // Ŀ��ҳ���������
	
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/"; // �������ҳ���·��
	
	private PosFundBean fundManage = new PosFundBean(); // ���������ʵ��
	
	public void init() throws ServletException {
		
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {		
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
			tarJsp = PAGE_HOME + "PosFundQuery.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// ע��POS
			if (reqCode.equals(FUND)) {
				if ((target == null) || target.trim().equals("")) {
					FundContract(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						FundContract(request, response, true, login);
					}
				}
			}
		}
	}
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	
		response.setContentType(CONTENT_TYPE);
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
		
		// ����ͳ��
		if (reqCode.equals(FUND)) {
			if ((target == null) || target.trim().equals("")) {
				FundContract(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					FundContract(request, response, true, login);
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
		// ͳ��
		if (LoginManageBean.checkPermMask(maskCode, FUND) == 0) {
			req.setAttribute("FUND", "1");
		}
	}
	
	/**
	 * ��ʼ�����ҳ��������б���Ϣ
	 * @param req Http����
	 * @param orgId��������
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
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
	 * ����ǩԼͳ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login�û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void FundContract(HttpServletRequest request, HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws ServletException, IOException {	
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "PosFundQuery.jsp?post=" + FUND + "&uid=" + login.getUserID());
		} else {
			String strStart_date = request.getParameter("start_date");
			String strEnd_date = request.getParameter("end_date");
			try {
				Vector v = fundManage.QueryInfo( strStart_date, strEnd_date );
				toPage(request, response, PAGE_HOME + "PosFundContract.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
}