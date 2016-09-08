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

	// 交易码：新增
	private static final String APPEND = "17901";
	// 交易码：删除
	private static final String DELETE = "17902";
	// 交易码：修改
	private static final String UPDATE = "17903";
	// 交易码：查询
	private static final String QUERY = "17904";
	// 交易码：生成主密钥及打印
	private static final String KEYDOWN = "17906";
	// 交易码：统计
	private static final String STATISTIC = "17907";

	// 目标页面参数代码
	private static final String PARMGETPAGE = "page";
	// 商户信息管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/bffc";
	// 商户信息管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/BankFutures/";
	// 商户信息管理类实例
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
	 * 设置用户对页面的操作权限
	 * @param reqHttp请求
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
	 * @param reqHttp请求
	 * @param orgId机构编码
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// 安全种类
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("SecuKind", v);
			// 使用标志
			v = CodeManageBean.queryCodes("0558", sq);
			req.setAttribute("SignFlag", v);
			// 使用标志
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
	 * 烟草公司信息登记请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGetPage Get请求标志
	 * @param login 用户信息
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
				PromptBean prompt = new PromptBean("烟草公司管理");
				if (nReturn != -1) {
					writeLog(login, "烟草公司[" + info.getFc_id() + "]添加成功");
					prompt.setPrompt("烟草公司添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("烟草公司添加失败！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "烟草公司[" + info.getFc_id() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	/**
	 * 烟草公司信息删除
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		String strKey = request.getParameter("fc_id");
		try {
			ClassBean.DeleteInfo(strKey);
			PromptBean prompt = new PromptBean("烟草公司管理");
			writeLog(login, "烟草公司[" + strKey + "]删除成功");
			prompt.setPrompt("烟草公司删除成功！");
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "烟草公司[" + strKey + "]删除失败");
			errProc(request, response, exp);
		}
	}

	/**
	 * 修改烟草公司基本信息
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGetPage Get请求标志
	 * @param login 用户信息
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
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,"烟草公司不存在！");
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
				writeLog(login, "烟草公司[" + strKey + "]配置信息修改成功");
				PromptBean prompt = new PromptBean("烟草公司管理");
				prompt.setPrompt("烟草公司修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "烟草公司[" + strKey + "]配置信息修改失败");
				errProc(request, response, exp);
			}
		}
	}

	/**
	 * 烟草公司信息查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @param isGetPage Get请求
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
	 * 烟草公司信息删除
	 * @param request Http请求
	 * @param response Http响应
	 * @param use_flag 使用标识
	 * @param login 用户信息
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
				PromptBean prompt = new PromptBean("烟草公司管理");
				writeLog(login, strKey, "MG7001", "烟草公司生成密钥成功");
				prompt.setPrompt(auth_code);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
			} else {
				ClassBean.down_auth(strKey, "");
				PromptBean prompt = new PromptBean("烟草公司管理");
				writeLog(login, strKey, "MG7001", "烟草公司生成主密钥及打印成功！");
				prompt.setPrompt("烟草公司[" + strKey + "]生成主密钥及打印成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException exp) {
			writeLog(login, "烟草公司[" + strKey + "]生成主密钥及打印失败");
			errProc(request, response, exp);
		}
	}

	/**
	 * 烟草公司交易统计
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @param isGetPage Get请求
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
