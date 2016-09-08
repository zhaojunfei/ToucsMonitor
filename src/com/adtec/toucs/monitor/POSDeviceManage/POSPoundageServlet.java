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

public class POSPoundageServlet extends CommonServlet {

	
	// 交易码：手续费新增
	private static final String APPEND = "14601" ;
	// 交易码：手续费修改
	private static final String UPDATE = "14602";
	// 交易码：手续费查询
	private static final String QUERY = "14603";
	// 交易码：手续费删除
	private static final String DELETE = "14604";
	
	private static final String PARMGETPAGE = "page";
	private POSPoundageBean poundageBean = new POSPoundageBean();
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
			tarJsp = PAGE_HOME + "POSPoundagetManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(APPEND)) {// 增加
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
	 * 
	 * @param req
	 *            Http请求
	 * @param maskCode
	 *            用户权限码
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
//=========================================== INSERT START =====================================================================
	
	/**
	 * 添加新的理财pos手续费金额
	 * @param request Http请求
	 * @param response Http响应
	 *  20121205  syl          
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
			
		String tarJsp = null;//目标地址
		POSPoundageInfo info= new POSPoundageInfo();
		PromptBean prompt = new PromptBean("理财pos手续费管理");
		if (isGetPage) { 
			tarJsp = PAGE_HOME + "POSPoundageReg.jsp?post=" + APPEND;
		}else{
			//添加理财pos手续费前的查询
			try {
				info = poundageBean.QueryInfo();
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}	    	
			if(info == null){
				info = new POSPoundageInfo();
				info.fetchData(request);    
				try {
					//添加新的理财pos手续费信息
					int nReturn = poundageBean.AppendInfo(info);
					if (nReturn != -1) {						     
						writeLog(login, "理财pos手续费[" + info.getParaVal() + "]添加成功");
						prompt.setPrompt("手续费添加成功!");
						request.setAttribute("prompt", prompt);
						tarJsp = "/ToucsMonitor/Success.jsp";
					} else {
						prompt.setPrompt("理财pos手续费添加失败！");					         
						request.setAttribute("prompt", prompt);
						toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
					}					
				} catch (MonitorException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}else{
				prompt.setPrompt("理财pos手续费信息已存在,不能重复添加！");		         
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());	
			}
		}		
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}	
//=============================================== END ======================================================================	

//============================================ DELETE START ============================================================================	
	/**
	 * 删除理财pos手续费信息
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		try {
			//删除理财pos手续费信息
			int nReturn = poundageBean.DeleteInfo();		
			if(nReturn == 1){
				PromptBean prompt = new PromptBean("理财pos手续费管理");
				prompt.setPrompt("理财pos手续费删除成功！");			
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}else{
				PromptBean prompt = new PromptBean("理财pos手续费管理");
				prompt.setPrompt("理财pos手续费删除失败！");			
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}	
//=============================================== END =====================================================================
	
//=========================================== UPDATE START ================================================================	
	/**
	 * 修改理财pos手续费信息
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if(isGetPage){
			POSPoundageInfo info;
			try {
				//查询手续费信息至跳转页面
				info = poundageBean.QueryInfos();			
				String tarJsp = null;
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "POSPoundageUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
				request.setAttribute("poundageInfo", info);
				toPage(request, response, tarJsp);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else{
			POSPoundageInfo info = new POSPoundageInfo();
			// 从请求数据中取得要修改的内部账户信息
			info.fetchData(request);		
			try {
				//修改理财pos手续费信息
				int nReturn = poundageBean.UpdateInfo(info);
				if(nReturn == 1){
					// 转成功提示页面
					PromptBean prompt = new PromptBean("理财pos手续费管理");
					prompt.setPrompt("理财pos手续费修改成功！");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}else{
					// 转成功提示页面
					PromptBean prompt = new PromptBean("理财pos手续费管理");
					prompt.setPrompt("理财pos手续费修改失败！");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException e) {			
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
//============================================ END =========================================================================	
		
//============================================== SELECT START ==============================================================	
	/**
	 * 查询理财pos手续费信息
	 * @param request Http请求
	 * @param response Http响应
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		try {
			//查询理财pos手续费列表信息
			Vector v = poundageBean.QueryInfoByList();
			request.setAttribute("POSPoundageList", v);
			toPage(request, response, PAGE_HOME + "POSPoundageList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}
//============================================== END ======================================================================	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			tarJsp = PAGE_HOME + "POSPoundagetManage.jsp?uid=" + login.getUserID();
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
			}else if (reqCode.equals(UPDATE)) {
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {				
				QueryInfo(request, response, false, login);		
			}
		}
	}	
}
