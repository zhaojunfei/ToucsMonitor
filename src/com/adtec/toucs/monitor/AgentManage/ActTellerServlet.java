package com.adtec.toucs.monitor.AgentManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class ActTellerServlet extends CommonServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 交易码：新增
	private static final String APPEND = "17601";
	// 交易码：删除
	private static final String DELETE = "17602";
	// 交易码：修改
	private static final String UPDATE = "17603";
	// 交易码：查询
	private static final String QUERY = "17604";
	// 交易码：管理
	private static final String MANAGE = "17605";


	private static final String PARMGETPAGE = "page";
	private static final String PAGE_HOME = "/ToucsMonitor/AgentManage/";
	private ActTellerBean Bean = new ActTellerBean();

	public void init() throws ServletException {
	}

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
			tarJsp = PAGE_HOME + "ActTellerManage.jsp?uid=" + login.getUserID();
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
			} if (reqCode.equals(MANAGE)) {
				if ( (target == null) || target.trim().equals("")) {
					InfoManage(request, response, login, "1" );
				} else {
					if (target.equals(PARMGETPAGE)) {
						InfoManage(request, response, login, "0" );
					}
				}
			}
		}
	}

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
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// 收费种类
			v = CodeManageBean.queryCodes("0569", sq);
			req.setAttribute("FeeKind", v);
			// 商户类型
			v = CodeManageBean.queryCodes("0600", sq);
			req.setAttribute("MchantType", v);
			// 业务代码
			v = CodeManageBean.queryCodes("0601", sq);
			req.setAttribute("AgentCode", v);
			// 安全种类
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("SecuKind", v);
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
   * 柜员登记请求处理
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
			tarJsp = PAGE_HOME + "ActTellerReg.jsp?post=" + APPEND;
		} else {
			ActTellerInfo info = new ActTellerInfo();
			info.fetchData(request);
			try {
				int nReturn = Bean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("代理柜员管理");
				if (nReturn != -1) {
					writeLog(login, "代理柜员[" + info.getTellerid() + "]添加成功");
					prompt.setPrompt("代理柜员[" + info.getTellerid() + "]添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("代理柜员[" + info.getTellerid() + "]添加失败!");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "代理柜员[" + info.getTellerid() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

  /**
   * 柜员信息删除
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
			PromptBean prompt = new PromptBean("代理柜员管理");
			writeLog(login, "代理柜员[" + strKey2 + "]删除成功");
			prompt.setPrompt("代理柜员[" + strKey2 + "]删除成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "代理柜员[" + strKey2+ "]删除失败");
			errProc(request, response, exp);
		}
	}

  /**
   * 修改柜员信息
   * @param request Http请求
   * @param response Http响应
   * @param isGetPage Get请求标志
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
					ActTellerInfo info = Bean.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "代理柜员不存在！");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "ActTellerUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("ActTeller", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			ActTellerInfo info = new ActTellerInfo();
			info.fetchData(request);
			try {
				Bean.UpdateInfo(info, strKey);
				writeLog(login, "代理柜员[" + strKey + "]修改成功");
				PromptBean prompt = new PromptBean("代理柜员管理");
				prompt.setPrompt("代理柜员[" + strKey + "]修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "代理柜员[" + strKey + "]修改失败");
				errProc(request, response, exp);
			}
		}
	}

  /**
   * 柜员信息查询请求处理
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
			toPage(request, response, PAGE_HOME + "ActTellerManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("branch_id");
			String strType = request.getParameter("teller_id");
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = Bean.QueryInfoByList(strKey, strType);
					request.setAttribute("ActTellerList", v);
					toPage(request, response,PAGE_HOME + "ActTellerList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = Bean.QueryInfoByList("", strType);
					request.setAttribute("ActTellerList", v);
					toPage(request, response,PAGE_HOME + "ActTellerList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

  /**
   * 柜员信息删除
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
			PromptBean prompt = new PromptBean("代理柜员管理");
			if ( use_flag.equals("0") ) {
				writeLog(login, "代理柜员[" + strKey + "]清理成功");
				prompt.setPrompt("代理柜员[" + strKey + "]清理成功！");
			} else {
				writeLog(login, "代理柜员[" + strKey + "]清理成功");
				prompt.setPrompt("代理柜员[" + strKey + "]清理成功！");
			}
			
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "代理柜员[" + strKey + "]管理失败");
			errProc(request, response, exp);
		}
	}
}