package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:卡交易权限维护Servlet类</p>
 * <p>Description:接收请求，执行系卡交易权限维护相关的业务处理逻辑，控制页面流向</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Rox
 * @version 1.0
 */

public class TxnAuthMngServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TXN_AUTH_ADD = "17401";
	private static final String TXN_AUTH_MOD = "17402";
	private static final String TXN_AUTH_DEL = "17403";
	private static final String TXN_AUTH_QRY = "17404";
	private static TxnAuthMngBean txnAuthMan = new TxnAuthMngBean();

	public TxnAuthMngServlet() {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		String sys_id = request.getParameter("SysId");

		//权限控制
		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			LoginManageBean.operValidate(login, TXN_AUTH_ADD);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//查询代码
		if (reqCode == null || reqCode.equals(TXN_AUTH_QRY)) {
			Debug.println("getQry");
			try {
				//查询代码
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode != null && reqCode.equals(TXN_AUTH_ADD)) {
			//添加代码
			Debug.println("getAdd");
			TxnAuthBean auth = new TxnAuthBean();
			SqlAccess conn = null;
			try {
				conn = SqlAccess.createConn();
				Vector v1 = CodeManageBean.queryCodes("0280", conn);
				request.setAttribute("CardClassList", v1);
				Vector v2 = CodeManageBean.queryCodes("0290", conn);
				request.setAttribute("CardTypeList", v2);
			} catch (Exception e) {
				Debug.println(e.toString());
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
			auth.setSysId("atm");
			try {
				auth.qryAllTxn();
			} catch (MonitorException ex) {
				Debug.println(ex.toString());
			}

			request.setAttribute("auth", auth);
			toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthReg.jsp?post=" + TXN_AUTH_ADD + "&uid=" + login.getUserID());
		} else if (reqCode != null && reqCode.equals(TXN_AUTH_MOD)) {
			//修改代码
			String card_class = request.getParameter("CardClass");
			String card_type = request.getParameter("CardType");
			Debug.println("getMod" + sys_id + "|" + card_class + "|" + card_type);
			//如果代码类型或代码为空，转代码查询页面
			if (sys_id == null) {
				sys_id = "atm";
			}
			TxnAuthBean auth = new TxnAuthBean();
			auth.setSysId("atm");
			try {
				auth.qryAllTxn();
			} catch (MonitorException ex) {
				Debug.println(ex.toString());
			}
			request.setAttribute("auth", auth);

			if (card_class == null || card_type == null) {
				Debug.println("Mod1");
				//查询代码
				try {
					queryAuth(request, response, sys_id, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				//转代码修改页面
				Debug.println("getMod2");
				try {
					queryModAuth(request, response, sys_id, card_class, card_type, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else if (reqCode.equals(TXN_AUTH_DEL)) {
			//删除代码
			Debug.println("getDel");
			String card_class = request.getParameter("CardClass");
			String card_type = request.getParameter("CardType");
			//如果代码类型或代码为空，转代码查询页面
			if (sys_id == null) {
				sys_id = "atm";
			}
			if (card_class == null || card_type == null) {
				Debug.println("Del1");
				//查询代码
				try {
					queryAuth(request, response, sys_id, login);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Debug.println("getDel2" + card_class + card_type);
					//删除代码
					txnAuthMan.deleteAuth(sys_id, card_class, card_type);
					//查询代码
					queryAuth(request, response, sys_id, login);
				}catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		String sys_id = request.getParameter("SysId");
		Debug.println("post1:" + sys_id);
		if (sys_id == null || sys_id.length() == 0) {
			sys_id = "atm";
		}
		Debug.println("post2:" + sys_id);
		//权限控制
		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			LoginManageBean.operValidate(login, TXN_AUTH_ADD);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//查询代码
		if (reqCode == null || reqCode.equals(TXN_AUTH_QRY)) {
			Debug.println("postQry");
			try {
				//查询代码
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_ADD)) {
			//添加代码
			Debug.println("postAdd");
			try {
				TxnAuthBean auth = new TxnAuthBean();
				auth.fetchData(request);
				//增加代码
				txnAuthMan.addAuth(auth);
				//查询代码
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_MOD)) {
			//修改代码
			Debug.println("postMod");
			try {
				TxnAuthBean auth = new TxnAuthBean();
				auth.fetchData(request);
				txnAuthMan.modifyAuth(auth);
				//查询代码
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(TXN_AUTH_DEL)) {
			//删除代码
			Debug.println("postDel");
			try {
				//删除代码
				if (sys_id == null) {
					sys_id = "atm";
				}
				String card_class = request.getParameter("CardClass");
				String card_type = request.getParameter("CardType");
				txnAuthMan.deleteAuth(sys_id, card_class, card_type);
				//查询代码
				queryAuth(request, response, sys_id, login);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
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
	private void queryAuth(HttpServletRequest request,HttpServletResponse response,String sys_id, LoginInfo login) throws
      	ServletException, IOException, MonitorException {

		request.setAttribute("QUERYPERM", "1");
		request.setAttribute("ADDPERM", "1");
		request.setAttribute("MODPERM", "1");
		request.setAttribute("DELPERM", "1");

		//根据设备类型查询交易码
		if (sys_id == null) {
			sys_id = "atm";
		}
		Vector v = txnAuthMan.queryAuths(sys_id);
		request.setAttribute("SysId", sys_id);
		request.setAttribute("list", v);

		Debug.println("Rox3" + sys_id);
		//转代码查询页面
		toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthQuery.jsp?&uid=" + login.getUserID());
	}

	private void queryModAuth(HttpServletRequest request,
                            HttpServletResponse response,
                            String sys_id, String card_class, String card_type,
                            LoginInfo login) throws ServletException,
      IOException, MonitorException {
		TxnAuthBean auth = txnAuthMan.queryAuth(sys_id, card_class, card_type);
		Debug.println("InMode");
		auth.qryAllTxn();
		request.setAttribute("auth", auth);
		request.setAttribute("modiflag", "1");
		SqlAccess conn = null;
		try {
			conn = SqlAccess.createConn();
			Vector v1 = CodeManageBean.queryCodes("0280", conn);
			request.setAttribute("CardClassList", v1);
			Vector v2 = CodeManageBean.queryCodes("0290", conn);
			request.setAttribute("CardTypeList", v2);
		} catch (Exception e) {
			Debug.println(e.toString());
		}finally{
			if (conn != null)
				conn.close();
		}
		toPage(request, response,"/ToucsMonitor/SystemManage/TxnAuthReg.jsp?post=" + TXN_AUTH_MOD + "&uid=" + login.getUserID());
	}
}
