package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:银行中心代码管理Servlet类</p>
 * <p>Description:接收请求，执行银行中心代码管理相关的业务处理逻辑，控制页面流向</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BankCodeManageServlet extends CommonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//添加代码
	private static final String BANKCODE_ADD="17201";
	//修改代码
	private static final String BANKCODE_MOD="17202";
	//删除代码
	private static final String BANKCODE_DEL="17203";
	//查询代码
	private static final String BANKCODE_QUERY="17204";
	//代码管理类实例
	private static BankCodeManageBean bankCodeMan=new BankCodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode=request.getParameter("reqCode");
		//权限控制
		LoginInfo login=null;
		try{
			//校验用户是否登录
			login=checkLogin(request,response);
			//校验用户操作权限
			if(reqCode!=null&&reqCode.trim().length()>0)
				LoginManageBean.operValidate(login,reqCode);
		}catch(MonitorException exp){
			errProc(request,response,exp);
			return;
		}

		//查询代码
		if(reqCode==null||reqCode.equals(BANKCODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}else if(reqCode!=null&&reqCode.equals(BANKCODE_ADD)){//添加代码
			//转增加代码页面
			BankCodeBean code=new BankCodeBean();
			request.setAttribute("code",code);
			toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeReg.jsp?post="+BANKCODE_ADD+"&isMod=false&uid="+login.getUserID());
		} else if (reqCode!=null&&reqCode.equals(BANKCODE_MOD)){
			//修改代码
			String bankCode=request.getParameter("bankCode");
			//如果代码类型或代码为空，转代码查询页面
			if(bankCode==null){
				//查询代码
				try{
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else {
				//转代码修改页面
				try{
					queryModCode(request,response,bankCode,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		} else if (reqCode.equals(BANKCODE_DEL)){
			//删除代码
			String bankCode=request.getParameter("bankCode");
			//如果代码类型或代码为空，转代码查询页面
			if(bankCode==null){
				//查询代码
				try{
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//删除代码
					bankCodeMan.deleteCode(bankCode);
					//查询代码
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode=request.getParameter("reqCode");
		//权限控制
		LoginInfo login=null;
		try{
			//校验用户是否登录
			login=checkLogin(request,response);
			//校验用户操作权限
			if(reqCode!=null&&reqCode.trim().length()>0)
				LoginManageBean.operValidate(login,reqCode);
		}catch(MonitorException exp){
			errProc(request,response,exp);
			return;
		}

		//查询代码
		if(reqCode==null||reqCode.equals(BANKCODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}else if(reqCode.equals(BANKCODE_ADD)){
			//添加代码
			BankCodeBean code=new BankCodeBean();
			code.fetchData(request);
			try{
				//增加代码
				bankCodeMan.addCode(code);
				//查询代码
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(BANKCODE_MOD)){
			//修改代码
			BankCodeBean code=new BankCodeBean();
			code.fetchData(request);
			try{
				bankCodeMan.modifyCode(code);
				//查询代码
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(BANKCODE_DEL)){
			try{
				//删除代码
				bankCodeMan.deleteCode(request.getParameter("bankCode"));
				//查询代码
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}
	}

  /**
   * 查询代码
   * @param request Http请求
   * @param response Http响应
   * @param codeType 代码种类
   * @param login 登录用户信息
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	private void queryCode(HttpServletRequest request,HttpServletResponse response,
                             LoginInfo login)throws ServletException,IOException,MonitorException{
		//查询用户权限
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//查询银行中心代码
		Vector v=bankCodeMan.queryCodes();
		request.setAttribute("list",v);
		//转代码查询页面
		toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeQuery.jsp?&uid="+login.getUserID());
	}

  /**
   * 查询某交易的详细信息
   * @param request Http请求
   * @param response Http响应
   * @param deviceType 设备种类
   * @param txnCode 交易码
   * @param login 登录用户信息
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	private void queryModCode(HttpServletRequest request,HttpServletResponse response,
                             String bankCode,LoginInfo login)
                             throws ServletException,IOException,MonitorException{
		BankCodeBean code = bankCodeMan.queryCode(bankCode);
		request.setAttribute("code",code);
		toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeReg.jsp?post="+BANKCODE_MOD+"&isMod=true&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}