package com.adtec.toucs.monitor.BankFutures;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.BankFutures.BfTellerBean;
import com.adtec.toucs.monitor.BankFutures.BfTellerInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


public class BfTellerServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	// 交易码：新增
	private static final String APPEND = "17921";
	// 交易码：删除
	private static final String DELETE = "17922";
	// 交易码：修改
	private static final String UPDATE = "17923";
	// 交易码：查询
	private static final String QUERY = "17924";
	// 目标页面参数代码
	private static final String PARMGETPAGE = "page";
	
	// 商户信息管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/bfteller";
	// 商户信息管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/BankFutures/";
	// 商户信息管理类实例
	private BfTellerBean ClassBean = new BfTellerBean();

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
			tarJsp = PAGE_HOME + "BfTellerManage.jsp?uid=" + login.getUserID();
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
		// 增加
		if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
			req.setAttribute("APPEND", "1");
		}
		// 删除
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}
		// 修改
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
		}
		// 查询
		if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
			req.setAttribute("QUERY", "1");
		}
	}

	/**
	 * 初始化添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId机构编码
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			v = CodeManageBean.queryCodes("0558", sq);
			req.setAttribute("SignFlag", v);
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
	 * 烟草柜员登记请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGetPage Get请求标志
	 * @param login用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String tarJsp = null;
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "BfTellerReg.jsp?post=" + APPEND;
		} else {
			BfTellerInfo info = new BfTellerInfo();
			info.fetchData(request);
			try {
				int nReturn = ClassBean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("期货柜员管理");
				if (nReturn != -1) {
					writeLog(login, "期货柜员[" + info.getOp_no() + "]添加成功");
					prompt.setPrompt("期货柜员添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("期货柜员添加不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "期货柜员[" + info.getFc_id() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	/**
	 * 烟草柜员信息删除
	 * @param request Http请求
	 * @param response Http响应
	 * @param login用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		String strKey1 = request.getParameter("op_no");
		String strKey2 = request.getParameter("fc_id");
		try {
			 ClassBean.DeleteInfo(strKey1, strKey2);
			PromptBean prompt = new PromptBean("期货柜员管理");
			writeLog(login, "期货柜员[" + strKey1 + "]删除成功");
			prompt.setPrompt("期货柜员删除成功！");
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "期货柜员[" + strKey1 + "]删除失败");
			errProc(request, response, exp);
		}
	}

	/**
	 * 修改烟草柜员基本信息
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGetPage Get请求标志
	 * @param login用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		String strKey1 = request.getParameter("op_no");
		if (isGetPage) {
			if (strKey1 != null) {
				String tarJsp = null;
				try {
					BfTellerInfo info = ClassBean.QueryInfo(strKey1, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,"期货柜员不存在！");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "BfTellerUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("BfTeller", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			BfTellerInfo info = new BfTellerInfo();
			info.fetchData(request);
			try {
				ClassBean.UpdateInfo(info, strKey1);
				writeLog(login, "期货柜员[" + strKey1 + "]配置信息修改成功");
				PromptBean prompt = new PromptBean("期货柜员管理");
				prompt.setPrompt("期货柜员修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "期货柜员[" + strKey1 + "]配置信息修改失败");
				errProc(request, response, exp);
			}
		}
	}

	/**
	 * 烟草柜员信息查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "BfTellerManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("fc_id");
			String strType = request.getParameter("investor_id");
			try {
				Vector v = ClassBean.QueryInfoByList(strKey, strType);
				request.setAttribute("BfTellerList", v);
				toPage(request, response, PAGE_HOME + "BfTellerList.jsp?uid="
						+ login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
}
