package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:代码管理Servlet类</p>
 * <p>Description:接收请求，执行系统代码管理相关的业务处理逻辑，控制页面流向</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//交易码：查询代码
	private static final String CODE_QUERY="17004";
	//交易码：添加代码
	private static final String CODE_ADD="17001";
	//交易码：修改代码
	private static final String CODE_MOD="17002";
	//交易码：删除代码
	private static final String CODE_DEL="17003";
	//代码管理类实例
	private static CodeManageBean codeMan=new CodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {

	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode=request.getParameter("reqCode");
		String codeType=request.getParameter("codeType");

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
		if(reqCode==null||reqCode.equals(CODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode!=null&&reqCode.equals(CODE_ADD)){
			//添加代码
			//如果代码类型为空，转代码查询页面
			if(codeType==null){
				//查询代码
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else	{
				//转增加代码页面
				CodeBean code=new CodeBean();
				code.setCodeType(codeType);
				code.setCodeId("");
				code.setCodeDesc("");
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/CodeReg.jsp?post="+CODE_ADD+"&uid=");
			}
		} else if (reqCode!=null&&reqCode.equals(CODE_MOD)){
			//修改代码
			String id=request.getParameter("id");
			String desc=request.getParameter("desc");
			//如果代码类型或代码为空，转代码查询页面
			if(codeType==null||id==null){
				//查询代码
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else {
				//转代码修改页面
				CodeBean code=new CodeBean();
				code.setCodeType(codeType);
				code.setCodeId(request.getParameter("id"));
				code.setCodeDesc(toucsString.unConvert(desc));
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/CodeReg.jsp?post="+CODE_MOD+"&uid=");
			}
		} else if (reqCode.equals(CODE_DEL)){
			//删除代码
			String id=request.getParameter("id");
			//如果代码类型或代码为空，转代码查询页面
			if(codeType==null||id==null){
				//查询代码
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//删除代码
					codeMan.deleteCode(codeType,id);
					//查询代码
					queryCode(request,response,codeType,login);
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
		String codeType=request.getParameter("codeType");
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
		if(reqCode==null||reqCode.equals(CODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_ADD)){
			//添加代码
			CodeBean code=new CodeBean();
			code.setCodeType(codeType);
			code.setCodeId(request.getParameter("id"));
			code.setCodeDesc(request.getParameter("desc"));
			try{
				//增加代码
				codeMan.addCode(code);
				//查询代码
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_MOD)){
			//修改代码
			CodeBean code=new CodeBean();
			code.setCodeType(codeType);
			code.setCodeId(request.getParameter("id").trim());
			code.setCodeDesc(request.getParameter("desc"));
			try{
				codeMan.modeCode(code,request.getParameter("key"));
				//查询代码
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_DEL)){
    	 //删除代码
			try{
				codeMan.deleteCode(codeType,request.getParameter("key"));
				//查询代码
				queryCode(request,response,codeType,login);
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
                             String codeType,LoginInfo login)throws ServletException,IOException,MonitorException{
		//查询用户权限
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//根据代码类型，查询相应的代码
		if(codeType!=null){
			Vector v=codeMan.queryCodes(codeType);
			request.setAttribute("codeType",codeType);
			request.setAttribute("list",v);
		}
		//转代码查询页面
		toPage(request,response,"/ToucsMonitor/SystemManage/CodeQuery.jsp?&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}