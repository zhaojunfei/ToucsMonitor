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

	//对公账户
	//交易码：新增
	private static final String POSINFOREG = "13701";
	//交易码：修改
	private static final String POSINFOUPDATE = "13702";
	//交易码：查询
	private static final String POSINFOQUERY = "13703";
	//交易码：删除
	private static final String POSINFODELETE = "13704";
	
	//目标页面参数代码
	private static final String PARMGETPAGE = "page";
	
	//签约POS管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/accountconfig";
	
	//签约POS管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
	
	//POS设备管理类实例
	private POSAccountBean Manage = new POSAccountBean();
	
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置缓冲页面不可用
		response.setHeader("Cache-Control", "no-store");
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		String target = "";
		target = request.getParameter("target");
		
		//用户身份校验
		LoginInfo login = null;
		//权限控制
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//目标JSP页面
		String tarJsp = null;		
		//设备参数管理请求
		if (reqCode == null) {
			//要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosAccountReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//注册POS
			if (reqCode.equals(POSINFOREG)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOUPDATE)) { //修改POS
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
				QueryInfo(request, response, login);
			}else if (reqCode.equals(POSINFODELETE)) { //删除POS
				Debug.println("删除商户信息...GET请求");
				DeleteInfo(request, response, login);
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		//取请求参数
		String target = request.getParameter("target");

		//权限控制
		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//注册POS
		if (reqCode.equals(POSINFOREG)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		} else if (reqCode.equals(POSINFOUPDATE)) {//修改POS
			Debug.println("1");
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) { //查询POS
			QueryInfo(request, response, login);
		}else if (reqCode.equals(POSINFODELETE)) { //删除POS
			DeleteInfo(request, response, login);
		}
	}

	//Clean up resources
	public void destroy() {
	}

	  /**
	   * 设置用户对页面的操作权限
	   * @param req Http请求
	   * @param maskCode 用户权限码
	   */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		//增加
		if (LoginManageBean.checkPermMask(maskCode, POSINFOREG) == 0) {
			req.setAttribute("POSINFOREG", "1");
		}
		//修改
		if (LoginManageBean.checkPermMask(maskCode, POSINFOUPDATE) == 0) {
			req.setAttribute("POSINFOUPDATE", "1");
		}
		//删除
		if (LoginManageBean.checkPermMask(maskCode, POSINFODELETE) == 0) {
			req.setAttribute("POSINFODELETE", "1");
		}
		//查询
		if (LoginManageBean.checkPermMask(maskCode, POSINFOQUERY) == 0) {
			req.setAttribute("POSINFOQUERY", "1");
		}
	}

	  /**
	   * 设备登记请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void AddInfo(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//设备登记Get请求
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosAccountReg.jsp?post=" + POSINFOREG;
		} else {
			POSAccount Info = new POSAccount();
			//从Http请求中取设备基本信息
			Info.fetchData(request);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("对公帐户管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "对公帐户[" + Info.getAccount() + "]添加成功");
					//通知客户端增加新设备
					notifyClient("N20007", Info.getAccount(), "2");
				} else {
					prompt.setPrompt("对公帐户[" + Info.getAccount() + "]添加失败！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
				String account = request.getParameter("account");
				String auth_code = Manage.down_auth(account);
				writeLog(login, account, "MG7830", "对公帐户授权码下载成功！");
				request.setAttribute("auth_code", auth_code);
				request.setAttribute("prompt", prompt);
				tarJsp = "/ToucsMonitor/POSDeviceManage/PosAccountAuthDown.jsp";
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "对公帐户[" + Info.getAccount() + "]添加失败！");
				errProc(request, response, exp);
			}
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	  /**
	   * POS设备查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
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
	   * 商户查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void QueryInfoPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
 		toPage(request, response, PAGE_HOME + "PosAccountManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
 	}

	  /**
	   * POS设备修改查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
 		//取设备编号DCC
 		String key = request.getParameter("account");
 		System.out.println("pos_code:" + key);
 		//取查询内容（基本信息）
 		if (key != null) {
 			String tarJsp = null;
 			//查询设备基本信息
 			try {
 				//查询设备基本信息
 				POSAccount Info = Manage.queryInfo(key, "");
 				if (Info == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "对公帐户不存在！");
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
	   * 修改POS设备基本信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		POSAccount info = new POSAccount();
 		//从请求数据中取得要修改的设备基本信息
 		info.fetchData(request);
 		//获取主键
 		String key = request.getParameter("account");
 		try {
 			//修改设备基本信息
 			Manage.updateInfo(info, key);
 			//记录日志
 			writeLog(login, "对公帐户[" + key + "]修改成功");
 			//通知客户端修改设备配置信息
 			notifyClient("N20007", key, "1");
 			
 			//转成功提示页面
 			PromptBean prompt = new PromptBean("设备参数管理");
 			prompt.setPrompt("对公帐户[" + key + "]修改成功！");
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "对公帐户[" + key + "]修改失败");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * POS设备删除
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//从请求数据中取得要修改的设备基本信息
 		String key = request.getParameter("account");
 		try {
 			//修改设备基本信息
 			Debug.println("&&&&&DELETE BEGIN&&&&&");
 			int nRst = Manage.deleteInfo(key);
 			PromptBean prompt = new PromptBean("对公帐户管理");
 			{
 				//记录日志
 				writeLog(login, "对公帐户[" + key + "]删除成功");

 				//通知客户端删除设备 add by liyp 20030918
 				notifyClient("N20007", key, "0");
 				
 				//转成功提示页面
 				prompt.setPrompt("对公帐户[" + key + "]删除成功！");
 			}
 			//需要校验用户是否有加载权限
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			//记录日志
 			writeLog(login, "对公帐户[" + key + "]删除失败");
 			errProc(request, response, exp);
 		}
 	}
}