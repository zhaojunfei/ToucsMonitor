package com.adtec.toucs.monitor.POSDeviceManage;


import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.ThirdPartyManage.ThirdPartyMerchantInfo;
import com.adtec.toucs.monitor.ThirdPartyManage.ThirdPartyTellerInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 * @version 1.0
 */

public class POSBCCompanyServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 交易码：新增
	private static final String APPEND = "16901";
	// 交易码：删除
	private static final String DELETE = "16902";
	// 交易码：修改
	private static final String UPDATE = "16903";
	// 交易码：查询
	private static final String QUERY = "16904";
	// 交易码：启用停用
	private static final String MANAGE = "16905";
	
	


	// 目标页面参数代码
	private static final String PARMGETPAGE = "page";
	// 预付卡信息管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/posBCCompany";
	// 预付卡信息管理页面根路径
    private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

    private POSBCCompanyBean posManage = new POSBCCompanyBean();

    public void init() throws ServletException {
    }

    // Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Cache-Control", "no-store");
	    String reqCode = request.getParameter("reqCode");
	    String target = request.getParameter("target");

	    LoginInfo login = null;
	    try {
		    //校验用户是否登录
		    login = checkLogin(request, response);
		    Debug.println("********CHECK LOGIN********");
		    //校验用户操作权限
		    if (reqCode != null && reqCode.trim().length() > 0) {
		 	   LoginManageBean.operValidate(login, reqCode);
		    }
	    }catch (MonitorException exp) {
 	 	    errProc(request, response, exp);
 		    return;
	    }
	    String tarJsp = null;

	   if (reqCode == null) {
		   tarJsp = PAGE_HOME + "POSBCCompanyManage.jsp?post=" + QUERY +"&uid=" + login.getUserID();
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
		   }else if (reqCode.equals(DELETE)) {
			   DeleteInfo(request, response, login);
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
	   }else if (reqCode.equals(DELETE)) {
		   DeleteInfo(request, response, login);
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
			// 安全种类
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("secu_kind", v);
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
    * 预付卡公司信息登记请求处理
    * @param request Http请求
    * @param response Http响应
    * @param isGetPage Get请求标志
    * @param login 用户信息
    * @throws ServletException
    * @throws IOException
    */
   	private void AppendInfo(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
   		String tarJsp = null;
   		if (isGetPage) {
   			InitInfoList(request, login.getOrgID());
   			tarJsp = PAGE_HOME + "POSBCCompanyReg.jsp?post=" + APPEND;
   		} else {
   			POSBCCompanyInfo info = new POSBCCompanyInfo();
   			info.fetchData(request);
   			try {
   				//调用相应功能
   				int ret = posManage.AppendInfo(info);
   				
   				PromptBean prompt = new PromptBean("预付卡公司管理");
   				if (ret != -1) {
				   writeLog(login, "预付卡公司[" + info.getCompany_id() + "]添加成功");
				   prompt.setPrompt("预付卡公司添加成功!");
				   prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
				   request.setAttribute("prompt", prompt);
				   tarJsp = "/ToucsMonitor/Success.jsp";
			   } else {
				   prompt.setPrompt("预付卡公司添加不成功！");
				   request.setAttribute("prompt", prompt);
				   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			   }
		   } catch (Exception exp) {
			   writeLog(login, "预付卡公司[" + info.getCompany_id() + "]添加失败");
			   exp.printStackTrace();
			   System.out.println(exp.getMessage());
		   }
	   }
	   Debug.println(tarJsp);
	   toPage(request, response, tarJsp);
   }

  /**
   * 预付卡公司信息删除
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
   private void DeleteInfo(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("company_id");
	   String[] strArray = request.getParameterValues("box1");
		//取得要删除的卡表信息结构体，并删除之。
		//重新从数据库中查询（取出用户在上一个页面设置的查询条件）
		String strIndex="";
		int index=0;
	   try {
		   //调用相应功能
		   if(strArray != null){
				//页面传递复选框中的值不为空则进行批量删除
				posManage.deleteBCCards(strArray);
			//	String retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
				//jsp跳转
				PromptBean prompt = new PromptBean("预付卡POS卡表信息管理");
				prompt.setPrompt("预付卡POS卡表信息删除成功！");
				prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}else{
		  
		   posManage.DeleteInfo(strKey);
		   PromptBean prompt = new PromptBean("预付卡公司管理");
		   writeLog(login, "预付卡公司[" + strKey + "]删除成功");
		   prompt.setPrompt("预付卡公司删除成功！");
		   prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
		   request.setAttribute("prompt", prompt);
		   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
	   } catch (Exception exp) {
		   writeLog(login, "预付卡公司[" + strKey + "]删除失败");
		   exp.printStackTrace();
		   System.out.println(exp.getMessage());
	   }
   }

  /**
   * 修改预付卡公司基本信息
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
   private void UPdateInfo(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("company_id");
	   if (isGetPage) {
		   if (strKey != null) {
			   String tarJsp = null;
			   try {
				   //调用相应的功能,页面跳转
				   POSBCCompanyInfo info = posManage.QueryInfo(strKey, "");
				   if (info == null) {
					   throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "公司不存在！");
				   }
				   InitInfoList(request, login.getOrgID());
				   setPagePerm(request, login.getPermission());
				   tarJsp = PAGE_HOME + "POSBCCompanyUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
				   request.setAttribute("POSBCCompany", info);
				   toPage(request, response, tarJsp);
			   } catch (Exception exp) {
				   exp.printStackTrace();
				   System.out.println(exp.getMessage());
			   }
		   }
	   } else {
		   POSBCCompanyInfo info = new POSBCCompanyInfo();
		   info.fetchData(request);
		   try {
			   posManage.UpdateInfo(info, strKey);
			   writeLog(login, "预付卡公司[" + strKey + "]配置信息修改成功");
			   PromptBean prompt = new PromptBean("预付卡公司管理");
			   prompt.setPrompt("预付卡公司修改成功！");
			   prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			   request.setAttribute("prompt", prompt);
			   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		   } catch (MonitorException exp) {
			   writeLog(login, "预付卡公司[" + strKey + "]配置信息修改失败");
			   errProc(request, response, exp);
		   }
	   }
   	}

   /**
    * 预付卡公司查询请求处理
    * @param request Http请求
    * @param response Http响应
    * @param isGetPage Get请求
    * @param login 用户信息
    * @throws ServletException
    * @throws IOException
    */
   	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isGetPage, LoginInfo login) throws ServletException,IOException {
   		if (isGetPage) {
   			InitInfoList(request, login.getOrgID());
   			toPage(request, response, PAGE_HOME + "POSBCCompanyManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
   		} else {
   			String strKey = request.getParameter("company_id");
   			System.out.println("company_id:" + strKey);
   			String strType = request.getParameter("org_id");
   			System.out.println("org_id:" + strType);
   			if (strKey != null && !strKey.equals("")) {
   				try {
   					//调用
   					//request.setAttribute("POSBCCompanyList", v);
   					Vector v = posManage.QueryInfoByList(strKey, strType);
   					request.setAttribute("POSBCCompanyList", v);
   					toPage(request, response,PAGE_HOME + "POSBCCompanyList.jsp?uid=" + login.getUserID());
   				} catch (Exception exp) {
   					exp.printStackTrace();
   				}
   			} else {
   				try {
   					String org_id   = request.getParameter("org_id");
   					if(org_id == ""){
   						org_id = null;
   					}

   			     	String company_name = request.getParameter("company_name");
			    	if(company_name == ""){
			 		company_name = null;
			    	}
				
				List list = posManage.getBCByOrgAndName(org_id, company_name); 
				request.setAttribute("POSBCCompanyList", list);
   					toPage(request, response,PAGE_HOME + "POSBCCompanyList.jsp?uid=" + login.getUserID());
   				} catch (MonitorException exp) {
   					errProc(request, response, exp);
   				}
   			}
   		}
   	}

   /**
    * 预付卡公司启停
    * @param request Http请求
    * @param response Http响应
    * @param use_flag
    * @throws ServletException
    * @throws IOException
    */
   	private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
   		String strKey = request.getParameter("company_id");
   		try {
		   //调用相应的方法
   			posManage.ManageInfo(strKey, use_flag);
   			PromptBean prompt = new PromptBean("预付卡公司管理");	 
   			if ( use_flag.equals("0") ) {
   				writeLog(login, "预付卡公司[" + strKey + "]停用成功");
   				prompt.setPrompt("预付卡公司[" + strKey + "]停用成功！");
   			} else {
   				writeLog(login, "预付卡公司[" + strKey + "]启用成功");
   				prompt.setPrompt("预付卡公司[" + strKey + "]启用成功！");
   			}
   			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
   			//需要校验用户是否有加载权限
   			request.setAttribute("prompt", prompt);
   			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
   		} catch (Exception exp) {
   			writeLog(login, "预付卡公司[" + strKey + "]管理失败");
   			exp.printStackTrace();
   			System.out.println(exp.getMessage());
   		}
   	}


}