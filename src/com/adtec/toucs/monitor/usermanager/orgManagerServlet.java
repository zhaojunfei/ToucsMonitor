package com.adtec.toucs.monitor.usermanager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.usermanager.userManagerBean;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author sunyl
 * @version 2.0
 */

public class orgManagerServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//添加机构
	private static final String ADDORG = "10101";
	//修改机构
	private static final String MODIFYORG = "10102";
	//查询机构
	private static final String QUERYORG = "10104";
	//删除机构
	private static final String DELORG = "10103";

	private userManagerBean UM = null;

	private String lTarget = "";
	private String PromptConstrParam = "";
	private String Prompt = "";
	private String FstButtonUrl = "";
	private String FstButtonLable = "";
	private String SndButtonUrl = "";
	private String SndButtonLable = "";
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
		String orgId = request.getParameter("orgid");
		String orgName = request.getParameter("orgname");
		String p_orgId = request.getParameter("p_orgid");
		String ipAddress = request.getParameter("ipaddress");
		String orgAddress = request.getParameter("orgaddress");
		String connecter = request.getParameter("connecter");
		String phoneNo = request.getParameter("phoneno");
		String operType = request.getParameter("opertype");
		String TxCode = request.getParameter("txcode");
		if (TxCode == null || TxCode.equals("")){
			out.println("<script>alert(\"取系统参数失败！\");</script>");
			return;
		}
		try {
			LoginInfo LInfo = checkLogin(request,response);
			String maskCode = LInfo.getPermission();
			Hashtable operVH = new Hashtable();
			int errCode = LoginManageBean.checkPermMask(maskCode,ADDORG);
			operVH.put(ADDORG,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,MODIFYORG);
			operVH.put(MODIFYORG,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,DELORG);
			operVH.put(DELORG,String.valueOf(errCode));
			errCode = LoginManageBean.checkPermMask(maskCode,QUERYORG);
			operVH.put(QUERYORG,String.valueOf(errCode));
			request.setAttribute("operVH",operVH);
			LoginManageBean.operValidate(LInfo,TxCode);
			//新增机构信息
			if (TxCode.trim().equals(ADDORG)) {
				if (orgId == null || orgId.equals("") ||
					orgName == null || orgName.equals("") ||
					p_orgId == null || p_orgId.equals("") ||
					orgAddress == null || orgAddress.equals("") ||
					operType == null || operType.equals("") ) {
						out.println("<script>alert(\"请输入所有必输项！\");history.go(-1);</script>");
						return;
				}

				int affect = UM.addOrg(orgId,orgName,p_orgId,ipAddress,orgAddress,connecter,phoneNo,operType);
				if (affect > 0) {
					//wuye add 20020226 添加新机构后，应更新CodeTable
					CodeManageBean.initCodeTable();
					//add end
					writeLog(LInfo,"新增机构"+orgId+"成功！");
					PromptConstrParam = "机构信息管理";
					Prompt = "新增机构成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else {
					writeLog(LInfo,"新增机构"+orgId+"失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//修改机构信息
			if (TxCode.trim().equals(MODIFYORG)) {
				if (orgId == null || orgId.equals("") ||
				orgName == null || orgName.equals("") ||
                orgAddress == null || orgAddress.equals("") ||
                operType == null || operType.equals("") ) {
					out.println("<script>alert(\"请输入所有必输项！\");history.go(-1);</script>");
					return;
				}
				int affect = UM.modifyOrgInfo(orgId,orgName,ipAddress,orgAddress,connecter,phoneNo,operType);
				if (affect > 0) {
					//wuye add 20020226 修改机构后，应更新CodeTable
					CodeManageBean.initCodeTable();
					//add end
					writeLog(LInfo,"修改机构"+orgId+"成功！");
					PromptConstrParam = "机构信息管理";
					Prompt = "修改机构成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					writeLog(LInfo,"修改机构"+orgId+"失败！");
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					writeLog(LInfo,"修改机构"+orgId+"失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//查询机构信息
			if (TxCode.trim().equals(QUERYORG)) {
				Vector oV = new Vector();
				if (orgId == null || orgId.length() <= 0) {
					orgId = "*";
				}
				if (orgName == null || orgName.length() <= 0) {
					orgName = "*";
				}

				oV = UM.queryOrgInfo(orgId,orgName,LInfo.getOrgID());
				writeLog(LInfo,"查询机构信息");
				request.setAttribute("orgV",oV);
				lTarget = "/ToucsMonitor/usermanager/orgList.jsp";
			}
			//删除机构信息
			if (TxCode.trim().equals(DELORG)) {
				if (UM.isAtmExist(orgId)) {
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在ATM信息！");
					throw new MonitorException(ErrorDefine.ATMEXISTERR,ErrorDefine.ATMEXISTDESC);
				}
				if (UM.isUserExistInOrg(orgId)) {
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在用户信息！");
					throw new MonitorException(ErrorDefine.USEREXISTERR,ErrorDefine.USEREXISTDESC);
				}
				if (UM.isCdmExist(orgId)) {
					Debug.println("删除机构,因为存在CDM信息！");
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在CDM信息！");
					throw new MonitorException(ErrorDefine.DELORG_FAILED3,"机构或下级机构定义有CDM设备信息，请先删除CDM设备信息");
				}
				if (UM.isMctExist(orgId)) {
					Debug.println("删除机构,因为存在商户信息！");
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在商户信息！");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"机构或下级机构定义有商户信息，请先删除商户信息");
				}
				if (UM.isPemExist(orgId)) {
					Debug.println("删除机构,因为存在PEM信息！");
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在PEM信息！");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"机构或下级机构定义有PEM设备信息，请先删除PEM设备信息");
				}
				if (UM.isMitExist(orgId)) {
					Debug.println("删除机构,因为存在MIT信息！");
					writeLog(LInfo,"删除机构"+orgId+"失败，因为存在MIT信息！");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"机构或下级机构定义有MIT设备信息，请先删除MIT设备信息");
				}
				int affect = UM.delOrgInfo(orgId);
				if (affect > 0) {

					writeLog(LInfo,"删除机构"+orgId+"成功！");
					PromptConstrParam = "机构信息管理";
					Prompt = "删除机构成功！";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "添加";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "查询";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					writeLog(LInfo,"删除机构"+orgId+"失败！");
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else{
					writeLog(LInfo,"删除机构"+orgId+"失败！");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
		} catch (MonitorException ex) {
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
