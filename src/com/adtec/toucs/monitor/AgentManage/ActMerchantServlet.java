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

public class ActMerchantServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 交易码：新增
	private static final String APPEND = "17501";
	// 交易码：删除
	private static final String DELETE = "17502";
	// 交易码：修改
	private static final String UPDATE = "17503";
	// 交易码：查询
	private static final String QUERY = "17504";
	// 交易码：启用停用
	private static final String MANAGE = "17505";
	// 交易码：生成主密钥及打印
	private static final String KEYDOWN = "17506";

	private static final String PARMGETPAGE = "page";
	private static final String MANAGE_HOME = "/ToucsMonitor/actmerchant";
	private static final String PAGE_HOME = "/ToucsMonitor/AgentManage/";
	private ActMerchantBean posManage = new ActMerchantBean();

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
			tarJsp = PAGE_HOME + "ActMerchantManage.jsp?uid=" + login.getUserID();
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
			}else if (reqCode.equals(KEYDOWN)) {  
				if ( (target == null) || target.trim().equals("")) {
					KeyDown(request, response, login, "1" );
				} else {
					if (target.equals(PARMGETPAGE)) {
						KeyDown(request, response, login, "0" );
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
			Debug.println("删除商户信息...POST请求");
			DeleteInfo(request, response, login);
		}else if (reqCode.equals(MANAGE)) {   
			if ( (target == null) || target.trim().equals("")) {
				InfoManage(request, response, login, "1" );
			} else {
				if (target.equals(PARMGETPAGE)) {
					InfoManage(request, response, login, "0" );
				}
			}
		}else if (reqCode.equals(KEYDOWN)) {   
			if ( (target == null) || target.trim().equals("")) {
				KeyDown(request, response, login, "1" );
			} else {
				if (target.equals(PARMGETPAGE)) {
					KeyDown(request, response, login, "0" );
				}
			}
		}
	}

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
			tarJsp = PAGE_HOME + "ActMerchantReg.jsp?post=" + APPEND;
		} else {
			ActMerchantInfo info = new ActMerchantInfo();
			info.fetchData(request);
			try {
				int nReturn = posManage.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("支付商户管理");
				if (nReturn != -1) {
					writeLog(login, "商户[" + info.getMerchantid() + "]添加成功");
					prompt.setPrompt("商户添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("商户添加不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				writeLog(login, "商户[" + info.getMerchantid() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

  /**
   * 代理商户信息删除
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                          HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		String strKey = request.getParameter("merchant_id");
		try {
			posManage.DeleteInfo(strKey);
			PromptBean prompt = new PromptBean("支付商户管理");
			writeLog(login, "商户[" + strKey + "]删除成功");
			prompt.setPrompt("商户删除成功！");
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "商户[" + strKey + "]删除失败");
			errProc(request, response, exp);
		}
	}

  /**
   * 修改代理商户信息
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UPdateInfo(HttpServletRequest request,
                             HttpServletResponse response,
                             boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String strKey = request.getParameter("merchant_id");
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				try {
					ActMerchantInfo info = posManage.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "商户不存在！");
					}
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "ActMerchantUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
					request.setAttribute("ActMerchant", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			ActMerchantInfo info = new ActMerchantInfo();
			info.fetchData(request);
			try {
				posManage.UpdateInfo(info, strKey);
				writeLog(login, "商户[" + strKey + "]配置信息修改成功");
				PromptBean prompt = new PromptBean("支付商户管理");
				prompt.setPrompt("商户修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "商户[" + strKey + "]配置信息修改失败");
				errProc(request, response, exp);
			}
		}
	}

  /**
   * 代理商户信息查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @param isGetPage Get请求标识
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "ActMerchantManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("merchant_id");
			String strType = request.getParameter("merchant_type");
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QueryInfoByList(strKey, strType);
					request.setAttribute("ActMerchantList", v);
					toPage(request, response, PAGE_HOME + "ActMerchantList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QueryInfoByList("", strType);
					request.setAttribute("ActMerchantList", v);
					toPage(request, response,PAGE_HOME + "ActMerchantList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

  /**
   * 代理商户信息删除
   * @param request Http请求
   * @param response Http响应
   * @param use_flag 使用标识
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		String strKey = request.getParameter("merchant_id");
		try {
			posManage.ManageInfo(strKey, use_flag);
			PromptBean prompt = new PromptBean("商户管理");
			if ( use_flag.equals("0") ) {
				writeLog(login, "商户[" + strKey + "]停用成功");
				prompt.setPrompt("商户[" + strKey + "]停用成功！");
			} else {
				writeLog(login, "商户[" + strKey + "]启用成功");
				prompt.setPrompt("商户[" + strKey + "]启用成功！");
			}
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "商户[" + strKey + "]管理失败");
			errProc(request, response, exp);
		}
	}

  /**
   * 商户生成主密钥
   * @param request Http请求
   * @param response Http响应
   * @param use_flag 使用标识
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void KeyDown(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		String strKey = request.getParameter("merchant_id");
		try {
			posManage.QueryInfo(strKey, "");
			
			String auth_code = posManage.down_auth(strKey,use_flag);
			
			PromptBean prompt = new PromptBean("商户管理");
			writeLog(login, strKey, "MG7001", "商户生成密钥成功");
			prompt.setPrompt(auth_code);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID()+"&str2="+"1");
		} catch (MonitorException exp) {
			writeLog(login, "商户[" + strKey + "]生成主密钥及打印失败");
			errProc(request, response, exp);
		}
	}
}