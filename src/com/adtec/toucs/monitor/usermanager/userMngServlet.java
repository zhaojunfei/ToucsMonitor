package com.adtec.toucs.monitor.usermanager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class userMngServlet extends CommonServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	//添加用户
	private static final String ADDUSER = "10201";
	//修改用户
	private static final String MODIFYUSER = "10202";
	//删除用户
	private static final String DELUSER = "10203";
	//查询用户
	private static final String QUERYUSER = "10204";
	//添加机构
	private static final String ADDORG = "10101";
	//修改机构
	private static final String MODIFYORG = "10102";
	//查询机构
	private static final String QUERYORG = "10104";
	//删除机构
	private static final String DELORG = "10103";
	//添加角色
	private static final String ADDROLE = "10301";
	//修改角色
	private static final String MODIFYROLE = "10302";
	//查询角色
	private static final String QUERYROLE = "10304";
	//查询日志
	private static final String QUERYLOG = "14001";
	//删除日志
	private static final String DELLOG = "14002";
	//修改密码
	private static final String PASSWD = "19001";
	//查询当日流水
	private static final String DAYQUERY = "18001";
	//lihl add begin
	//查询POS当日流水
  	private static final String POSDAYQUERY = "18101";
  	//查询CDM当日流水
  	private static final String CDMDAYQUERY = "18201";
  	//查询MIT当日流水
  	private static final String MITDAYQUERY = "18301";
  	//lihl add end
  	//Initialize global variables
  	public void init() throws ServletException {
  	}
  	//Process the HTTP Get request
  	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		response.setContentType(CONTENT_TYPE);

  		String TxCode = request.getParameter("reqCode");
  		try{
  			if (TxCode == null) {
  				throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
  			}
  			//校验是否登录
  			LoginInfo LInfo = checkLogin(request,response);

  			//校验操作权限
  			LoginManageBean.operValidate(LInfo,TxCode);
  			request.setAttribute("LoginInfo",LInfo);

  			request.setAttribute("LInfo",LInfo);
  			if (TxCode.equals(ADDUSER)){
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/userInfoInput.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(MODIFYUSER)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryUser.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(QUERYUSER)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryUser.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(DELUSER)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryUser.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(ADDORG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/orgInfoInput.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(MODIFYORG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryOrg.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(QUERYORG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryOrg.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(DELORG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryOrg.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(ADDROLE)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/roleInfoInput.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(MODIFYROLE)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryRole.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(QUERYROLE)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryRole.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(QUERYLOG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryLog.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(DELLOG)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/queryLog.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(PASSWD)) {
  				RequestDispatcher rd = request.getRequestDispatcher("ToucsMonitor/usermanager/modifyPasswd.jsp");
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(DAYQUERY)) {
  				RequestDispatcher rd = null;
  				String AtmCode = request.getParameter("atmCode");
  				if (AtmCode == null) {
  					rd= request.getRequestDispatcher("ToucsMonitor/TranDetail/Condition.jsp");
  				} else {
  					rd = request.getRequestDispatcher("/TranDetailQuery?reqCode=18001&atmcode="+AtmCode);
  				}
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(POSDAYQUERY)) {
  				RequestDispatcher rd = null;
  				String posCode = request.getParameter("poscode");
  				if (posCode == null) {
  					rd= request.getRequestDispatcher("ToucsMonitor/TranDetail/PosCondition.jsp");
  				} else {
  					rd = request.getRequestDispatcher("/TranDetailQuery?reqCode=18101&poscode="+posCode);
  				}
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(CDMDAYQUERY)) {
  				RequestDispatcher rd = null;
  				String cdmcode = request.getParameter("cdmcode");
  				if (cdmcode == null) {
  					rd= request.getRequestDispatcher("ToucsMonitor/TranDetail/CdmCondition.jsp");
  				} else {
  					rd = request.getRequestDispatcher("/TranDetailQuery?reqCode=18201&cdmcode="+cdmcode);
  				}
  				rd.forward(request,response);
  			}
  			if (TxCode.equals(MITDAYQUERY)) {
  				RequestDispatcher rd = null;
  				String mitcode = request.getParameter("mitcode");
  				if (mitcode == null) {
  					rd= request.getRequestDispatcher("ToucsMonitor/TranDetail/MitCondition.jsp");
  				} else {
  					rd = request.getRequestDispatcher("/TranDetailQuery?reqCode=18301&mitcode="+mitcode);
  				}
  				rd.forward(request,response);
  			}
  		} catch (MonitorException ex){
  			errProc(request,response,ex);
  		}
  	}
  	//Clean up resources
  	public void destroy() {
  	}
}