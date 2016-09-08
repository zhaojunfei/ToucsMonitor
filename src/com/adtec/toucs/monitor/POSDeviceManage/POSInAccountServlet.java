package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;

public class POSInAccountServlet extends CommonServlet {


	// 交易码：内部账户新增
	private static final String APPEND = "14301" ;
	// 交易码：内部账户删除
	private static final String DELETE = "14304";
	// 交易码：内部账户修改
	private static final String UPDATE = "14302";
	// 交易码：内部账户查询
	private static final String QUERY = "14303";

	private static final String PARMGETPAGE = "page";
	private POSInAccountBean inAccountBean = new POSInAccountBean();

	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置缓冲页面不可用
		response.setHeader("Cache-Control", "no-store");
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");
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

		String tarJsp = null;
		if (reqCode == null) {
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "POSInAccountManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// 增加
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// 删除
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(UPDATE)) {// 修改
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {// 查询
				QueryInfo(request, response, false, login);
			}
		}
	}
	
	/**
	 * 设置用户对页面的操作权限
	 * @param req Http请求
	 * @param maskCode 用户权限码
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

//======================================INSERT START===============================================================	
	/**
	 * 添加新的理财pos内部账户
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException,IOException
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		String str = request.getParameter("acct_type");
		POSInAccountInfo info = null;
			if (isGetPage) { 
				tarJsp = PAGE_HOME + "POSInAccountReg.jsp?post=" + APPEND;
		    } else {
		    	try {
		    		//添加内部账户前的查询
					info = inAccountBean.QueryInfo();
				} catch (MonitorException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				PromptBean prompt = new PromptBean("理财pos内部账户管理");
		    	if(info == null){
		    		info = new POSInAccountInfo();
				    info.fetchData(request);
				    try {
				    	int nReturn = inAccountBean.AppendInfo(info);
				        if (nReturn != -1) {
				          writeLog(login, "理财pos内部账户[" + info.getParaVal() + "]添加成功");
				          prompt.setPrompt("理财pos内部账户添加成功!");
				          request.setAttribute("prompt", prompt);
				          tarJsp = "/ToucsMonitor/Success.jsp";
				        }else {
				          prompt.setPrompt("理财pos内部账户添加失败！");
				          request.setAttribute("prompt", prompt);
				          toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				        }
				     }catch (MonitorException exp) {
				        writeLog(login, "理财pos内部账户[" + info.getParaVal() + "]添加失败");
				        errProc(request, response, exp);
				      }
		    	}else{
		    		  prompt.setPrompt("理财pos内部账户已存在,不能重复添加！");
			          request.setAttribute("prompt", prompt);
			          toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		    	} 
		    }
			Debug.println(tarJsp);
			toPage(request, response, tarJsp);
	}
		
//================================================END==========================================================================================================	
		
	
//================================================DELETE START=================================================================================================	
	/**
	 * 理财POS内部账户删除
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		try {
			inAccountBean.DeleteInfo();
			PromptBean prompt = new PromptBean("理财pos内部账户管理");
			prompt.setPrompt("理财pos账号删除成功！");	
			request.setAttribute("prompt", prompt);
		    toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}	
	}
//================================================END======================================================================================================

	
//============================================UPDATE START==================================================================================================
	/**
	 * 修改理财POS内部账户信息
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if(isGetPage){
			POSInAccountInfo info;
			try {
				info = inAccountBean.QueryInfo();
				String tarJsp = null;
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "POSInAccountUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
		        request.setAttribute("InAccountInfo", info);
		        toPage(request, response, tarJsp);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else{
			POSInAccountInfo info = new POSInAccountInfo();
		     // 从请求数据中取得要修改的内部账户信息
			info.fetchData(request);
			try {
				inAccountBean.UpdateInfo(info);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	        // 转成功提示页面
	        PromptBean prompt = new PromptBean("理财pos内部账户管理");
	        prompt.setPrompt("理财pos账户修改成功！");
	        request.setAttribute("prompt", prompt);
	        toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}
	}
//==============================================END=============================================================================================================
	
//===========================================SELECT START======================================================================================================	
	/**
	 * 理财POS内部账户查询请求处理 
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		try {
			Vector v = inAccountBean.QueryInfoByList();
			request.setAttribute("POSInAccountList", v);
			toPage(request, response, PAGE_HOME + "POSInAccountList.jsp?uid=" + login.getUserID());
	    } catch (MonitorException exp) {
	    	errProc(request, response, exp);
	    }
	}	
//===============================================================================================================================================================
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置缓冲页面不可用
		response.setHeader("Cache-Control", "no-store");
		response.setContentType ("text/html;charset=GBK");
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");
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
  
		String tarJsp = null;
		if (reqCode == null) {
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "POSInAccountManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// 增加
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// 删除
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(UPDATE)) {// 修改
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {// 查询
				QueryInfo(request, response, false, login);
			}
		}
	}
}
