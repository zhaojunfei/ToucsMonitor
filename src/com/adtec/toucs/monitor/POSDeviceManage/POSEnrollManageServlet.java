package com.adtec.toucs.monitor.POSDeviceManage;

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

public class POSEnrollManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//交易码：POS新增
	private static final String POSINFOREG = "13601";

	//交易码：POS修改
	private static final String POSINFOUPDATE = "13602";

	//交易码：POS查询
	private static final String POSINFOQUERY = "13603";

	//交易码：POS删除
	private static final String POSINFODELETE = "13604";

	//交易码：POS启用停用
	private static final String POSINFOMNG = "13605";

	//目标页面参数代码
	private static final String PARMGETPAGE = "page";

	//编号种类参数
	private static final String DCCTYPE = "3";

	//签约POS管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/posenrollconfig";

	//签约POS管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	//POS设备管理类实例
	private POSEnrollManageBean posManage = new POSEnrollManageBean();

	//POS商户管理类实例
	private POSMerchantBean posMerchant = new POSMerchantBean();
  
	private static PageProcessor pageProcessor=null;
	private static final String PAGEPROCESSOR="PAGEPROCESSOR";

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
			tarJsp = PAGE_HOME + "PosEnrollManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//注册POS
			if (reqCode.equals(POSINFOREG)) {
				if ( (target == null) || target.trim().equals("")) {
					posInfoReg(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoReg(request, response, true, login);
					}
				}
			} else if (reqCode.equals(POSINFOUPDATE)) {//修改POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoUpdate(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoUpdateQuery(request, response, true, login);
					}
				}
			} else if (reqCode.equals(POSINFOQUERY)) {//查询POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
				}
			} else if (reqCode.equals(POSINFODELETE)) {//删除POS
				Debug.println("删除商户信息...GET请求");
				posInfoDelete(request, response, login);
			}else if (reqCode.equals(POSINFOMNG)) {   //签约POS设备管理
				if ( (target == null) || target.trim().equals("")) {
					posInfoManage(request, response, login, "1" );
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoManage(request, response, login, "0" );
					}
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
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
//      	if (reqCode != null && reqCode.trim().length() > 0) {
//      	  LoginManageBean.operValidate(login, reqCode);
//     	 	}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//注册POS
		if (reqCode.equals(POSINFOREG)) {
			if ( (target == null) || target.trim().equals("")) {
				posInfoReg(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoReg(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOUPDATE)) {//修改POS
			Debug.println("1");
			if ( (target == null) || target.trim().equals("")) {
				posInfoUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoUpdateQuery(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
			if ( (target == null) || target.trim().equals("")) {
				posInfoQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoQueryPage(request, response, login);
				}
			}
		}else if(reqCode.equals(PAGEPROCESSOR)){
			List queryList=(List)session.getAttribute("CurrPageEnrollList");
			String strCurrPageNum=request.getParameter("CurrPageNum");
			request.setAttribute("CurrPageNum",strCurrPageNum);
			Integer intCurrPageNum=new Integer(strCurrPageNum);
			try {
				paginate(queryList,intCurrPageNum.intValue(),request);
			} catch (MonitorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toPage(request, response,PAGE_HOME + "PosEnrollList.jsp");
		}else if (reqCode.equals(POSINFODELETE)) {//删除POS
			Debug.println("删除商户信息...POST请求");
			posInfoDelete(request, response, login);
		} else if (reqCode.equals(POSINFOMNG)) {   //签约POS设备启用
			if ( (target == null) || target.trim().equals("")) {
				posInfoManage(request, response, login, "1" );
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoManage(request, response, login, "0" );
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
	   * 初始化商户添加页面所需的列表信息
	   * @param req Http请求
	   * @param orgId 机构编码
	   */
	private void initPosList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean = null;

			//预授权设置
			v = new Vector();
			codeBean = new CodeBean();
			codeBean.setCodeId("0");
			codeBean.setCodeDesc("无预授权类交易");
			v.add(codeBean);
			codeBean = new CodeBean();
			codeBean.setCodeId("1");
			codeBean.setCodeDesc("有预授权类交易");
			v.add(codeBean);
			req.setAttribute("preAuthorizedList", v);
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
	   * 初始化设备查询页面所需的列表信息
	   * @param req Http请求
	   * @param orgId 机构编码
	   */
	private void initPosQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean;
			v = new Vector();
			Vector vTmp = posMerchant.qryMerchantVector();
			for (int i = 0; i < vTmp.size(); i++) {
				codeBean = new CodeBean();
				codeBean.setCodeType("merchant_info");
				codeBean.setCodeId( (String) vTmp.get(i));
				codeBean.setCodeDesc( (String) vTmp.get(i + 1));
				v.add(codeBean);
				i++;
			}
			req.setAttribute("PosMerchantList", v);
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
	   * 设备登记请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoReg(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//设备登记Get请求
		if (isGetPage) {
			POSEnrollManageBean pb = new POSEnrollManageBean();
			POSEnrollInfo posenroll;
			String pos_code = request.getParameter("pos_dcc_code");
			try {
				if (pos_code != null && pos_code.length() > 0) {
					posenroll = pb.qryPosInfo(pos_code, "3");
					if ( posenroll != null ) {		
						posenroll.setMct_name();
						request.setAttribute( "posEnroll", posenroll);
					}
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosEnrollReg.jsp?post=" + POSINFOREG;
		} else {
			POSEnrollInfo posEnroll = new POSEnrollInfo();
			//从Http请求中取设备基本信息
			posEnroll.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posEnroll.getMerchantid());
				int nReturn = posManage.regPosDevice(posEnroll, nPosCount);
				PromptBean prompt = new PromptBean("设备参数管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "POS设备[" + posEnroll.getPoscode() + "]添加成功");
					//通知客户端增加新设备
					Debug.println("通知客户端增加新POS设备");
					notifyClient("N20007", posEnroll.getPoscode(), "2");
					
					//转成功提示页面
					prompt.setPrompt("签约POS设备添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/Success.jsp";
				} else {
					prompt.setPrompt("已登记的POS设备数量等于该商户最大POS设备数量" + "，登记POS设备不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "POS设备[" + posEnroll.getPoscode() + "]添加失败");
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
	private void posInfoQuery(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException, IOException {
		//取设备编号
		String pos_code = request.getParameter("pos_code");
		//取查询目的（修改、查询）
		if (pos_code != null && !pos_code.equals("")) {
			try {
				Vector v = posManage.qryPosInfoByPoscode( pos_code, request.getParameter("pos_kind"));
				request.setAttribute("posEnrollList", v);
				toPage(request, response,PAGE_HOME + "PosEnrollList.jsp?uid=" + login.getUserID());
			}
			catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			try {
				String merchant_id = request.getParameter("merchant_id");
				List list = posManage.qryMctPosInfo(merchant_id);
				request.setAttribute("posEnrollList", list);
				paginate(list,1,request);
				toPage(request, response,PAGE_HOME + "PosEnrollList.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
  
	private void paginate(List inList,int pageSize,HttpServletRequest request)throws MonitorException{
		if(pageProcessor==null){
			Debug.println("pageProcessor==null");
			pageProcessor=new PageProcessor();
		}
		pageProcessor.setItems(inList);
		Debug.println("inList.size()==========================="+inList.size());
		List list=pageProcessor.getPage(pageSize);
		request.setAttribute("CurrPageEnrollList",list);
		request.setAttribute("PageCount",String.valueOf(pageProcessor.getPageCount()));
	}

	  /**
	   * 商户查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQueryPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosEnrollManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
	}

	  /**
	   * POS设备修改查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdateQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
		//取设备编号DCC
		String pos_code = request.getParameter("pos_code");
		System.out.println("pos_code:" + pos_code);
		//取查询内容（基本信息）
		if (isGetPage) {
			if (pos_code != null) {
				String tarJsp = null;
				//查询设备基本信息
				try {
					//查询设备基本信息
					POSEnrollInfo posEnroll = posManage.qryPosEnroll(pos_code, DCCTYPE);
					if (posEnroll == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
					}
					posEnroll.setMct_name();
					initPosList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosEnrollUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posEnroll", posEnroll);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//请求到来的最初页面
			initPosList(request, login.getOrgID());
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "PosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
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
	private void posInfoUpdate(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSEnrollInfo info = new POSEnrollInfo();
		//从请求数据中取得要修改的设备基本信息
		info.fetchData(request);
		//获取主键
		String key = request.getParameter("key");
		try {
			//修改设备基本信息
			posManage.updatePosInfo(info, key);
			//记录日志
			writeLog(login, "签约POS[" + key + "]配置信息修改成功");
			//通知客户端修改设备配置信息
			notifyClient("N20007", key, "1");
			
			//转成功提示页面
			PromptBean prompt = new PromptBean("设备参数管理");
			prompt.setPrompt("签约POS信息修改成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "签约POS[" + key + "]配置信息修改失败");
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
	private void posInfoDelete(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//从请求数据中取得要修改的设备基本信息
		String key = request.getParameter("pos_code");
		try {
			//修改设备基本信息
			Debug.println("&&&&&DELETE BEGIN&&&&&");
			int nRst = posManage.deletePosInfo(key);
			PromptBean prompt = new PromptBean("POS设备管理");
			{
				//记录日志
				writeLog(login, "POS设备[" + key + "]删除成功");
				//通知客户端删除设备 add by liyp 20030918
				notifyClient("N20007", key, "0");
				
				//转成功提示页面
				prompt.setPrompt("POS信息删除成功！");
			}
			//需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "POS设备[" + key + "]删除失败");
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
	private void posInfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		//从请求数据中取得要修改的设备基本信息
		String key = request.getParameter("pos_code");
		try {
			int nRst = posManage.managePosInfo(key, use_flag);
			PromptBean prompt = new PromptBean("POS设备管理");
			{
				if ( use_flag.equals("0") ) {
					//记录日志
					writeLog(login, "签约POS设备[" + key + "]停用成功");
					//转成功提示页面
					prompt.setPrompt("签约POS设备[" + key + "]停用成功！");
				} else {
					//记录日志
					writeLog(login, "签约POS设备[" + key + "]启用成功");
					//转成功提示页面
					prompt.setPrompt("签约POS设备[" + key + "]启用成功！");
				}
			}
			//需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "POS设备[" + key + "]管理失败");
			errProc(request, response, exp);
		}
	}
}