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

public class ActDeviceServlet extends CommonServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 交易码：新增
	private static final String APPEND = "17701";
	// 交易码：删除
	private static final String DELETE = "17702";
	// 交易码：修改
	private static final String UPDATE = "17703";
	// 交易码：查询
	private static final String QUERY = "17704";
	// 交易码：管理
	private static final String MANAGE = "17705";

	private static final String PARMGETPAGE = "page";
	private static final String PAGE_HOME = "/ToucsMonitor/AgentManage/";
	private ActDeviceBean Bean = new ActDeviceBean();

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
	    	tarJsp = PAGE_HOME + "ActDeviceManage.jsp?uid=" + login.getUserID();
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
			tarJsp = PAGE_HOME + "ActDeviceReg.jsp?post=" + APPEND;
		} else {
			ActDeviceInfo info = new ActDeviceInfo();
			info.fetchData(request);
			try {
				int nReturn = Bean.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("代理设备管理");
				if (nReturn != -1) {
					writeLog(login, "代理设备[" + info.getEquipid() + "]添加成功");
					prompt.setPrompt("代理设备[" + info.getEquipid() + "]添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("代理设备[" + info.getEquipid() + "]添加失败!");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "代理设备[" + info.getMerchantid() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

  /**
   * 代理商户设备信息删除
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                          HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		String strKey1 = request.getParameter("merchant_id");
		String strKey2 = request.getParameter("equip_id");
		try {
			Bean.DeleteInfo(strKey1, strKey2);
			PromptBean prompt = new PromptBean("代理设备管理");
			writeLog(login, "代理设备[" + strKey2 + "]删除成功");
			prompt.setPrompt("代理设备[" + strKey2 + "]删除成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "代理设备[" + strKey2 + "]删除失败");
			errProc(request, response, exp);
		}
	}

  /**
   * 修改POS设备基本信息
   * @param request Http请求
   * @param response Http响应
   * @param isGetPage 请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UPdateInfo(HttpServletRequest request,
                             HttpServletResponse response,
                             boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String strKey = request.getParameter("equip_id");
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				try {
					ActDeviceInfo info = Bean.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "代理设备不存在！");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "ActDeviceUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("ActInfo", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			ActDeviceInfo info = new ActDeviceInfo();
			info.fetchData(request);
			try {
				Bean.UpdateInfo(info, strKey);
				writeLog(login, "代理设备[" + strKey + "]修改成功");
				PromptBean prompt = new PromptBean("代理设备管理");
				prompt.setPrompt("代理设备["+strKey+"]修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "代理设备[" + strKey + "]修改失败");
				errProc(request, response, exp);
			}
		}
	}

  /**
   * POS设备查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param isGetPage 请求标识
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "ActDeviceManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("merchant_id");
			String strType = "";
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = Bean.QueryInfoByList(strKey, strType);
					request.setAttribute("ActInfoList", v);
					toPage(request, response,PAGE_HOME + "ActDeviceList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = Bean.QueryInfoByList("", strType);
					request.setAttribute("ActInfoList", v);
					toPage(request, response,PAGE_HOME + "ActDeviceList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

  /**
   * 代理商户设备信息删除
   * @param request Http请求
   * @param response Http响应
   * @param user_flag 使用标识
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		String strKey = request.getParameter("equip_id");
		try {
			Bean.ManageInfo(strKey, use_flag);
			PromptBean prompt = new PromptBean("代理设备管理");
				if ( use_flag.equals("0") ) {
					writeLog(login, "代理设备[" + strKey + "]停用成功");
					prompt.setPrompt("代理设备[" + strKey + "]停用成功！");
				} else {
					//记录日志
					writeLog(login, "代理设备[" + strKey + "]启用成功");
					prompt.setPrompt("代理设备[" + strKey + "]启用成功！");
				}
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "代理设备[" + strKey + "]管理失败");
			errProc(request, response, exp);
		}
	}
}