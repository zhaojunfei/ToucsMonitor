package com.adtec.toucs.monitor.usermanager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;


/**
 * <p>
 * Title: userMangerServlet
 * </p>
 * <p>
 * Description: 用户管理逻辑控制
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Adtec
 * </p>
 * 
 * @author Fancy
 * @version 1.0
 */

public class userManagerServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// 添加用户
	private static final String ADDUSER = "10201";

	// 修改用户
	private static final String MODIFYUSER = "10202";

	// 删除用户
	private static final String DELUSER = "10203";

	// 查询用户
	private static final String QUERYUSER = "10204";

	// 用户授权
	private static final String POWERUSER = "10205";

	// 修改密码
	private static final String PASSWD = "19001";

	private userManagerBean UM = null;

	private String lTarget = "";

	private String PromptConstrParam = "";

	private String Prompt = "";

	private String FstButtonUrl = "";

	private String FstButtonLable = "";

	private String SndButtonUrl = "";

	private String SndButtonLable = "";

	// Initialize global variables
	public void init() throws ServletException {
		UM = new userManagerBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String TxCode = request.getParameter("oper_type");
		String OpCode = request.getParameter("op_code");
		String userId = request.getParameter("userid");
		String userName = request.getParameter("username");
		String userPwd = request.getParameter("userpwd");
		String confirm = request.getParameter("confirm");
		String employDate = request.getParameter("employdate");
		employDate = ChangeDateType(employDate);
		String fireDate = request.getParameter("firedate");
		fireDate = ChangeDateType(fireDate);
		String orgId = request.getParameter("orgid");
		String userDesc = request.getParameter("userdesc");
		String selorgId = request.getParameter("OrgCodeSel");
		String useDate = request.getParameter("usedate");
		Vector userV = null;
		if (TxCode == null || TxCode.equals("")) {
			out.println("<script>alert(\"取系统参数错误！\");history.go(-1)</script>");
			return;
		}
		try {
			LoginInfo LInfo = checkLogin(request, response);
			String maskCode = LInfo.getPermission();
			String uid = LInfo.getUserID();	
			request.setAttribute("LoginInfo", LInfo);
			Hashtable operVH = new Hashtable();
			int errCode = LoginManageBean.checkPermMask(maskCode, ADDUSER);
			operVH.put(ADDUSER, String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode, MODIFYUSER);
			operVH.put(MODIFYUSER, String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode, DELUSER);
			operVH.put(DELUSER, String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode, QUERYUSER);
			operVH.put(QUERYUSER, String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode, POWERUSER);
			operVH.put(POWERUSER, String.valueOf(errCode));
			request.setAttribute("operVH", operVH);

			LoginManageBean.operValidate(LInfo, TxCode);

			// 新增操作员
			if (TxCode.trim().equals(ADDUSER)) {
				if (userId == null || userId.equals("") || userName == null
						|| userName.equals("") || userPwd == null
						|| userPwd.equals("") || orgId == null
						|| orgId.equals("")) {
					out.println("<script>alert(\"请完整填写必输项！\");history.go(-1);</script>");
					return;
				}
				if (!userPwd.equals(confirm)) {
					out.println("<script>alert(\"密码确认错误！\");history.go(-1);</script>");
					return;
				}
				int affect1 = UM.insertUserInfo(userId, userName, orgId,userPwd, employDate, fireDate, userDesc);
				if (affect1 > 0) {
					UM.insertPwdUpdateHistory(userId, userPwd, useDate);
					writeLog(LInfo, "新增用户[" + userId + "]成功！");
					PromptConstrParam = "用户信息管理";
					Prompt = "新增用户成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
					SndButtonLable = "查询";
					lTarget = "/ToucsMonitor/Success.jsp";
				} else {
					writeLog(LInfo, "新增用户[" + userId + "]失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}		
			else if (TxCode.trim().equals(MODIFYUSER)) {
				// 修改用户信息
				if (userId == null || userId.equals("") || userName == null
						|| userName.equals("") || orgId == null
						|| orgId.equals("")) {
					out.println("<script>alert(\"请完整填写必输项！\");history.go(-1);</script>");
					return;
				}
				int affect = UM.modifyUserInfo(userId, orgId, userName,employDate, fireDate, userDesc);
				if (affect == 0) {
					writeLog(LInfo, "修改用户[" + userId + "]失败！");
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				}

				if (affect < 0) {
					writeLog(LInfo, "修改用户[" + userId + "]失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				} else {
					writeLog(LInfo, "修改用户[" + userId + "]成功！");
					PromptConstrParam = "用户信息管理";
					Prompt = "修改用户成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
					SndButtonLable = "查询";
					lTarget = "/ToucsMonitor/Success.jsp";
					request.setAttribute("message", "修改用户成功！");
				}
			} else if (TxCode.trim().equals(QUERYUSER)) {
				// 查询用户信息
				if (userId == null || userId.equals("")) {
					userId = "*";
				}
				userV = UM.queryUserInfo(userId, selorgId);
				request.setAttribute("userV", userV);
				lTarget = "ToucsMonitor/usermanager/userList.jsp";
			} else if (TxCode.trim().equals(DELUSER)) {
				// 删除用户信息
				if (userId == null) {
					out.println("<script>alert(\"取系统参数失败！\");history.go(-1);</script>");
					return;
				}
				if (OpCode != null && OpCode.equals("unlock")) {
					int affect = UM.UnlockUserInfo(userId);
					UM.updateDate(userId);//修改用户的密码有效期
					if (affect == 0) {
						writeLog(LInfo, "解锁用户[" + userId + "]失败！");
						throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
					}
					if (affect < 0) {
						writeLog(LInfo, "解锁用户[" + userId + "]失败！");
						throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
					} else {
						writeLog(LInfo, "解锁用户[" + userId + "]成功！");
						PromptConstrParam = "用户信息管理";
						Prompt = "解锁用户成功！";
						FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
						FstButtonLable = "添加";
						SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
						SndButtonLable = "查询";
						lTarget = "/ToucsMonitor/Success.jsp";
					}
				} else {
					int affect = UM.delUserInfo(userId);
					if (affect == 0) {
						writeLog(LInfo, "删除用户[" + userId + "]失败！");
						throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
					}
					if (affect < 0) {
						writeLog(LInfo, "删除用户[" + userId + "]失败！");
						throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
					} else {
						writeLog(LInfo, "删除用户[" + userId + "]成功！");
						PromptConstrParam = "用户信息管理";
						Prompt = "删除用户成功！";
						FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
						FstButtonLable = "添加";
						SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
						SndButtonLable = "查询";
						lTarget = "/ToucsMonitor/Success.jsp";
					}
				}
			} else if (TxCode.trim().equals(PASSWD)) {
				// 修改密码
				System.out.println("所修改的用户是"+uid);
				int ret = -1;
				String oldpasswd = request.getParameter("oldpasswd");
				if (uid == null) {
					throw new MonitorException(ErrorDefine.GETUSERIDERR,ErrorDefine.GETUSERIDERRDESC);
				} else if (oldpasswd == null) {
					throw new MonitorException(ErrorDefine.CHECKPWDERR,ErrorDefine.CHECKPWDERRDESC);
				} else if (!userPwd.equals(confirm)) {
					throw new MonitorException(ErrorDefine.PWDCONFIRMERR,ErrorDefine.PWDCONFIRMERRDESC);
				} else if (!UM.checkUserPwd(uid, oldpasswd)) {
					throw new MonitorException(ErrorDefine.CHECKPWDERR,ErrorDefine.CHECKPWDERRDESC);
				}
				ret = UM.checkPwdHistory(uid, userPwd);//查询密码的历史修改记录
				if(ret == 1){
					throw new MonitorException(ErrorDefine.RECORDSAME,ErrorDefine.RECORDSAMEDESC);
				}
				ret = UM.passwd(userPwd, uid);
				if (ret < 0) {
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				} else if (ret == 0) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
				int num = UM.insertPwdUpdateHistory(uid, userPwd, useDate);	
				if(num<0){
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
					writeLog(LInfo, "用户[" + uid + "]修改密码成功！");
					PromptConstrParam = "用户信息管理";
					Prompt = "修改密码成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
					SndButtonLable = "查询";
					lTarget = "/ToucsMonitor/Success.jsp";
				}
			} else if (TxCode.trim().equals(POWERUSER)) {
				// 授权
				if (userId == null || userId.trim().equals("")|| userId.trim().equals("null")) {
					throw new MonitorException(ErrorDefine.GETUSERIDERR,ErrorDefine.GETUSERIDERRDESC);
				}
				String[] Roleid = request.getParameterValues("Power");
				Vector roleV = new Vector();
				if (Roleid != null) {
					for (int i = 0; i < Roleid.length; i++) {
						roleV.add(Roleid[i]);
					}
				}
				if (!UM.isUserExist(userId)) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				}
				int affect = UM.modifyUserPower(userId, roleV);
				if (affect < 0) {
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				} else {
					writeLog(LInfo, "用户[" + userId + "]授权成功！");
					PromptConstrParam = "用户信息管理";
					Prompt = "用户授权成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + ADDUSER;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode=" + QUERYUSER;
					SndButtonLable = "查询";
					lTarget = "/ToucsMonitor/Success.jsp";
				}
			}
			PromptBean prompt = new PromptBean(PromptConstrParam);
			prompt.setPrompt(Prompt);
			prompt.setButtonUrl(0, FstButtonLable, FstButtonUrl);
			prompt.setButtonUrl(1, SndButtonLable, SndButtonUrl);
			request.setAttribute("prompt", prompt);
			RequestDispatcher rd = request.getRequestDispatcher(lTarget);
			rd.forward(request, response);
		} catch (MonitorException ex) {
			errProc(request, response, ex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String ChangeDateType(String DateStr) {
		if (DateStr == null || DateStr.trim().equals("")) {
			return "";
		}
		if (DateStr.length() < 8) {
			for (int i = 0; i < 8 - DateStr.length(); i++) {
				DateStr += "0";
			}
			return DateStr;
		}
		String temp = DateStr.substring(0, 4) + DateStr.substring(5, 7) + DateStr.substring(8, 10);
		return temp;
	}

	// Clean up resources
	public void destroy() {
	}
}
