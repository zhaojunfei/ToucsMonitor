package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:平台交易码管理Servlet类</p>
 * <p>Description:接收请求，执行系统代码管理相关的业务处理逻辑，控制页面流向</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TxnCodeManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	//交易码：查询代码
	private static final String TXNCODE_QUERY="17104";
	//交易码：添加代码
	private static final String TXNCODE_ADD="17101";
	//交易码：修改代码
	private static final String TXNCODE_MOD="17102";
	//交易码：删除代码
	private static final String TXNCODE_DEL="17103";
	//代码管理类实例
	private static TxnCodeManageBean txnCodeMan=new TxnCodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode=request.getParameter("reqCode");
		String deviceType=request.getParameter("deviceType");
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
		if(reqCode==null||reqCode.equals(TXNCODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode!=null&&reqCode.equals(TXNCODE_ADD)){
			//添加代码
			//如果代码类型为空，转代码查询页面
			if(deviceType==null){
				//查询代码
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else{
				//转增加代码页面
				TxnCodeBean code=new TxnCodeBean();
				code.setDeviceType(deviceType);
				code.setTxnCode("");
				code.setTxnName("");
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeReg.jsp?post="+TXNCODE_ADD+"&uid="+login.getUserID());
			}
		} else if(reqCode!=null&&reqCode.equals(TXNCODE_MOD)){
			//修改代码
			String txnCode=request.getParameter("txnCode");
			String txnName=request.getParameter("txnName");
			//如果代码类型或代码为空，转代码查询页面
			if(deviceType==null||txnCode==null){
				//查询代码
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else{
				//转代码修改页面
				try{
					queryModCode(request,response,deviceType,txnCode,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		} else if(reqCode.equals(TXNCODE_DEL)){
			//删除代码
			String txnCode=request.getParameter("txnCode");
			//如果代码类型或代码为空，转代码查询页面
			if(deviceType==null||txnCode==null){
				//查询代码
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//删除代码
					txnCodeMan.deleteCode(deviceType,txnCode);
					//查询代码
					queryCode(request,response,deviceType,login);
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
		String deviceType=request.getParameter("deviceType");
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
		if(reqCode==null||reqCode.equals(TXNCODE_QUERY)){
			try{
				//查询代码
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_ADD)){
			//添加代码
			TxnCodeBean code=new TxnCodeBean();
			code.fetchData(request);
			try{
				//增加代码
				txnCodeMan.addCode(code);
				//查询代码
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_MOD)){
			//修改代码
			TxnCodeBean code=new TxnCodeBean();
			code.fetchData(request);
			try{
				txnCodeMan.modifyCode(code);
				//查询代码
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_DEL)){
			//删除代码
			try{
				txnCodeMan.deleteCode(deviceType,request.getParameter("txnCode"));
				//查询代码
				queryCode(request,response,deviceType,login);
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
                             String deviceType,LoginInfo login)throws ServletException,IOException,MonitorException{
		//查询用户权限
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//根据设备类型查询交易码
		if(deviceType!=null){
			Vector v=txnCodeMan.queryCodes(deviceType);
			request.setAttribute("deviceType",deviceType);
			request.setAttribute("list",v);
		}
		//转代码查询页面
		toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeQuery.jsp?&uid="+login.getUserID());
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
                             String deviceType,String txnCode,LoginInfo login)
                             throws ServletException,IOException,MonitorException{
		TxnCodeBean code = txnCodeMan.queryCode(deviceType,txnCode);
		request.setAttribute("code",code);
		toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeReg.jsp?post="+TXNCODE_MOD+"&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}