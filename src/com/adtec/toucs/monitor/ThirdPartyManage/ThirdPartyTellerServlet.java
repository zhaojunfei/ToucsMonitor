	package com.adtec.toucs.monitor.ThirdPartyManage;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 * 
 */

public class ThirdPartyTellerServlet extends CommonServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 交易码：新增
	private static final String APPEND = "13201";
	// 交易码：删除
	private static final String DELETE = "13202";
	// 交易码：修改
	private static final String UPDATE = "13203";
	// 交易码：查询
	private static final String QUERY = "13204";
	// 交易码：管理
	private static final String MANAGE = "13205";
	// 交易码：密钥
	private static final String KEYDOWN = "17506";

	// 目标页面参数代码
	private static final String PARMGETPAGE = "page";
	// 商户信息管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/ThirdPartyManage/";
	// 商户信息管理类实例
	private ThirdPartyTellerBean Bean = new ThirdPartyTellerBean();

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
			System.out.println("--------login.getPermission()----------:"+login.getPermission());//
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		String tarJsp = null;
		if (reqCode == null) {
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "ThirdPartyTellerManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(APPEND)) {
				if ( (target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(UPDATE)) {
				if ( (target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {
				if ( (target == null) || target.trim().equals("")) {
					QueryInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						QueryInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(MANAGE)) {
				if ( (target == null) || target.trim().equals("")) {
					InfoManage(request, response, login, "1" );
				} else {
					if (target.equals(PARMGETPAGE)) {
						InfoManage(request, response, login, "0" );
					}
				}
			}else if (reqCode.equals(KEYDOWN)) {
				if ( (target == null) || target.trim().equals("")) {

				} else {
					if (target.equals(PARMGETPAGE)) {

					}
				}
			}
		}
	}

	//Process the HTTP Post request
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
			if ( (target == null) || target.trim().equals("")) {
				AppendInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AppendInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(DELETE)) {
			DeleteInfo(request, response, login);
		}else if (reqCode.equals(UPDATE)) {
			if ( (target == null) || target.trim().equals("")) {
				UPdateInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UPdateInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(QUERY)) {
			if ( (target == null) || target.trim().equals("")) {
				QueryInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					QueryInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(MANAGE)) {
			if ( (target == null) || target.trim().equals("")) {
				InfoManage(request, response, login, "1" );
			} else {
				if (target.equals(PARMGETPAGE)) {
					InfoManage(request, response, login, "0" );
				}
			}
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
		if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
			req.setAttribute("APPEND", "1");
		}
		//修改
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
		}
		//删除
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}	
		//查询
		if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
			req.setAttribute("QUERY", "1");
		}
	}

  /**
   * 初始化添加页面所需的列表信息
   * @param req Http请求
   * @param orgId 机构编码
   */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		SqlAccess sq = null;
		try {
			
		} catch (Exception exp) {
			
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}

  /**
   * 设备登记请求处理
   * @param request Http请求
   * @param response Http响应
   * @param isGetPage Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void AppendInfo(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "ThirdPartyTellerReg.jsp?post=" + APPEND;
		} else {
			ThirdPartyTellerInfo info = new ThirdPartyTellerInfo();
			info.fetchData(request);
			try {
				int nReturn = Bean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("第三方柜员管理");
				if (nReturn != -1) {
					writeLog(login, "第三方柜员[" + info.getTellerid() + "]添加成功");
					prompt.setPrompt("第三方柜员[" + info.getTellerid() + "]添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("第三方柜员[" + info.getTellerid() + "]添加失败!");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "第三方柜员[" + info.getTellerid() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
		}

  /**
   * 第三方柜员信息删除
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                          HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		String strKey1 = request.getParameter("branch_id");
		String strKey2 = request.getParameter("teller_id");
			try {
				Bean.DeleteInfo(strKey1,strKey2);
				PromptBean prompt = new PromptBean("第三方柜员管理");
				writeLog(login, "第三方柜员[" + strKey2 + "]删除成功");
				prompt.setPrompt("第三方柜员[" + strKey2 + "]删除成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "第三方柜员[" + strKey2+ "]删除失败");
				errProc(request, response, exp);
			}
	}

  /**
   * 修改第三方柜员信息
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UPdateInfo(HttpServletRequest request,
                             HttpServletResponse response,
                             boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String strKey = request.getParameter("teller_id");
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				try {
					ThirdPartyTellerInfo info = Bean.QueryInfo(strKey,"");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "第三方柜员不存在！");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "ThirdPartyTellerUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("ThirdPartyTeller", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}else {
			ThirdPartyTellerInfo info = new ThirdPartyTellerInfo();
			info.fetchData(request);
			try {
				Bean.UpdateInfo(info, strKey);
				writeLog(login, "第三方柜员[" + strKey + "]修改成功");
				PromptBean prompt = new PromptBean("第三方柜员管理");
				prompt.setPrompt("第三方柜员[" + strKey + "]修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "第三方柜员[" + strKey + "]修改失败");
				errProc(request, response, exp);
			}
		}
	}

  /**
   * 第三方柜员查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param isGetPage Get请求
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "ThirdPartyTellerManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("branch_id");
			String strType = request.getParameter("teller_id");
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = Bean.QueryInfoByList(strKey, strType);
					request.setAttribute("ThirdPartyTellerList", v);
					toPage(request, response,PAGE_HOME + "ThirdPartyTellerList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = Bean.QueryInfoByList("", strType);
					request.setAttribute("ThirdPartyTellerList", v);
					toPage(request, response,PAGE_HOME + "ThirdPartyTellerList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

  /**
   * 第三方柜员信息清理
   * @param request Http请求
   * @param response Http响应
   * @param use_flag 使用标识
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		String strKey = request.getParameter("teller_id");
		try {
			Bean.ManageInfo(strKey, use_flag);
			PromptBean prompt = new PromptBean("第三方柜员管理");		
			if ( use_flag.equals("0") ) {
				writeLog(login, "第三方柜员[" + strKey + "]清理成功");
				prompt.setPrompt("第三方柜员[" + strKey + "]清理成功！");
			} else {
				writeLog(login, "第三方柜员[" + strKey + "]清理成功");
				prompt.setPrompt("第三方柜员[" + strKey + "]清理成功！");
			}
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "第三方柜员[" + strKey + "]管理失败");
			errProc(request, response, exp);
		}
	}	
}