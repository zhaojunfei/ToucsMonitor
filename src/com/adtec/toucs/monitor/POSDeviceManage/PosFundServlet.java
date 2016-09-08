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
 * <p>Title: 基金签约管理</p>
 * <p>Description: 基金签约管理</p>
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
	private static final String FUND = "13301"; // 基金签约统计	
	private static final String PARMGETPAGE = "page"; // 目标页面参数代码
	
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/"; // 基金管理页面根路径
	
	private PosFundBean fundManage = new PosFundBean(); // 基金管理类实例
	
	public void init() throws ServletException {
		
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {		
		// 设置缓冲页面不可用
		response.setHeader("Cache-Control", "no-store");
		// 取请求参数
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");		
		// 用户身份校验
		LoginInfo login = null;
		try {
			// 校验用户是否登录
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			// 校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		// 目标JSP页面
		String tarJsp = null;
		
		// 设备参数管理请求
		if (reqCode == null) {
			// 要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosFundQuery.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// 注册POS
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
		// 取请求参数
		String reqCode = request.getParameter("reqCode");
		// 取请求参数
		String target = request.getParameter("target");
		// 权限控制
		LoginInfo login = null;
		try {
			// 校验用户是否登录
			login = checkLogin(request, response);
			// 校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		// 基金统计
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
	 * 设置用户对页面的操作权限
	 * @param req Http请求
	 * @param maskCode用户权限码
	 */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		// 统计
		if (LoginManageBean.checkPermMask(maskCode, FUND) == 0) {
			req.setAttribute("FUND", "1");
		}
	}
	
	/**
	 * 初始化添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId机构编码
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// 机构列表
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
	 * 基金签约统计
	 * @param request Http请求
	 * @param response Http响应
	 * @param login用户信息
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