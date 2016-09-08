package com.adtec.toucs.monitor.usermanager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.usermanager.userManagerBean;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author sunyl
 * @version 2.0
 */

public class roleManagerServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//添加角色
	private static final String ADDROLE = "10301";
	//修改角色
	private static final String MODIFYROLE = "10302";
	//删除角色
	private static final String DELROLE = "10303";
	//查询角色
	private static final String QUERYROLE = "10304";
	//角色授权
	private static final String POWER = "10305";

	private userManagerBean UM = null;
	int affect = -1;

	private String lTarget = "";
	private String PromptConstrParam = "";
	private String Prompt = "";
	private String FstButtonUrl = "";
	private String FstButtonLable = "";
	private String SndButtonUrl = "";
	private String SndButtonLable = "";
	//Initialize global variables
	public void init() throws ServletException {
		UM = new userManagerBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}
	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String roleName = request.getParameter("rolename");
		String roleDesc = request.getParameter("roledesc");
		String TxCode = request.getParameter("txcode");

		if (TxCode == null || TxCode.equals("")) {
			out.println("<script>alert(\"取系统参数失败!\");</script>");
			return ;
		}

		//取用户ID，以便权限校验
		try {
			LoginInfo LInfo = checkLogin(request,response);
			String maskCode = LInfo.getPermission();

			Hashtable operVH = new Hashtable();
			int errCode = LoginManageBean.checkPermMask(maskCode,ADDROLE);
			operVH.put(ADDROLE,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,MODIFYROLE);
			operVH.put(MODIFYROLE,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,DELROLE);
			operVH.put(DELROLE,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,QUERYROLE);
			operVH.put(QUERYROLE,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,POWER);
			operVH.put(POWER,String.valueOf(errCode));
			request.setAttribute("operVH",operVH);

			LoginManageBean.operValidate(LInfo,TxCode);
			Vector roleV = new Vector();

			//新增角色信息
			if(TxCode.equals(ADDROLE)){
				if (roleName == null || roleName.trim().equals("")) {
					out.println("<script>alert(\"请输入所有必输项\");history.go(-1);</script>");
					return ;
				}
				//add by liyp 20030906

				affect = UM.addRole(roleName,roleDesc);
				if (affect > 0) {
					writeLog(LInfo,"新增角色["+roleName+"]成功！");
					PromptConstrParam = "角色信息管理";
					Prompt = "新增角色成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else {
					writeLog(LInfo,"新增角色["+roleName+"]失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//修改角色信息
			if (TxCode.equals(MODIFYROLE)) {
				if (roleName == null || roleName.trim().equals("")) {
					out.println("<script>alert(\"请输入所有必输项\");history.go(-1);</script>");
					return ;
				}

				String roleId = request.getParameter("roleid");

				affect = UM.modifyRoleInfo(roleId,roleName,roleDesc);
				if (affect > 0) {
					writeLog(LInfo,"修改角色["+roleName+"]成功！");
					PromptConstrParam = "角色信息管理";
					Prompt = "修改角色["+toucsString.unConvert(roleName)+"]成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//查询角色信息
			if (TxCode.equals(QUERYROLE)) {
				roleV = new Vector();
				String roleId = request.getParameter("roleid");
				if (roleId == null || roleId.length() <= 0) {
					roleId = "*";
				}
				if (roleName == null || roleName.length() <= 0) {
					roleName = "*";
				}
				roleV = UM.queryRoleInfo(roleId,roleName);
				writeLog(LInfo,"查询角色信息！");
				request.setAttribute("roleV",roleV);
				lTarget = "ToucsMonitor/usermanager/roleList.jsp";
			}
			//删除角色信息
			if (TxCode.trim().equals(DELROLE)) {
				String roleId = request.getParameter("roleid");
				//add by liyp 20030906
				int affect = UM.delRoleInfo(roleId);
				if (affect > 0) {
					writeLog(LInfo,"删除角色["+roleName+"]成功！");
					PromptConstrParam = "角色信息管理";
					Prompt = "删除角色["+toucsString.unConvert(roleName)+"]成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					writeLog(LInfo,"删除角色["+roleName+"]失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//角色授权
			if (TxCode.trim().equals(POWER)) {
				String roleId = request.getParameter("roleid");
				if (roleId == null || roleId.trim().equals("") || roleId.trim().equals("null")) {
					throw new MonitorException(ErrorDefine.GETROLEIDERR,ErrorDefine.GETROLEIDERRDESC);
				}

				String[] resId = request.getParameterValues("Power");
				Vector resV = new Vector();
				if (resId != null){
					for (int i = 0; i < resId.length; i++) {
						resV.add(resId[i]);
					}
				}
				if (!UM.isRoleExist(roleId)) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				}

				int affect = UM.modifyRoleRes(roleId,resV);

				//add by liyp 20030906
				roleName = toucsString.unConvert(roleName);

				if (affect < 0 ) {
					writeLog(LInfo,"角色["+roleName+"]授权失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				} else {
					writeLog(LInfo,"角色["+roleName+"]授权成功！");
					PromptConstrParam = "角色信息管理";
					Prompt = "角色["+roleName+"]授权成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				}
			}
		} catch(MonitorException ex){
			errProc(request,response,ex);
		}
		PromptBean prompt=new PromptBean(PromptConstrParam);
		prompt.setPrompt(Prompt);
		prompt.setButtonUrl(0,FstButtonLable,FstButtonUrl);
		prompt.setButtonUrl(1,SndButtonLable,SndButtonUrl);
		request.setAttribute("prompt",prompt);
		RequestDispatcher rd = request.getRequestDispatcher(lTarget);
		rd.forward(request,response);
	}
	//Clean up resources
	public void destroy() {
	}
}
