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

	//��ӽ�ɫ
	private static final String ADDROLE = "10301";
	//�޸Ľ�ɫ
	private static final String MODIFYROLE = "10302";
	//ɾ����ɫ
	private static final String DELROLE = "10303";
	//��ѯ��ɫ
	private static final String QUERYROLE = "10304";
	//��ɫ��Ȩ
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
			out.println("<script>alert(\"ȡϵͳ����ʧ��!\");</script>");
			return ;
		}

		//ȡ�û�ID���Ա�Ȩ��У��
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

			//������ɫ��Ϣ
			if(TxCode.equals(ADDROLE)){
				if (roleName == null || roleName.trim().equals("")) {
					out.println("<script>alert(\"���������б�����\");history.go(-1);</script>");
					return ;
				}
				//add by liyp 20030906

				affect = UM.addRole(roleName,roleDesc);
				if (affect > 0) {
					writeLog(LInfo,"������ɫ["+roleName+"]�ɹ���");
					PromptConstrParam = "��ɫ��Ϣ����";
					Prompt = "������ɫ�ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else {
					writeLog(LInfo,"������ɫ["+roleName+"]ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//�޸Ľ�ɫ��Ϣ
			if (TxCode.equals(MODIFYROLE)) {
				if (roleName == null || roleName.trim().equals("")) {
					out.println("<script>alert(\"���������б�����\");history.go(-1);</script>");
					return ;
				}

				String roleId = request.getParameter("roleid");

				affect = UM.modifyRoleInfo(roleId,roleName,roleDesc);
				if (affect > 0) {
					writeLog(LInfo,"�޸Ľ�ɫ["+roleName+"]�ɹ���");
					PromptConstrParam = "��ɫ��Ϣ����";
					Prompt = "�޸Ľ�ɫ["+toucsString.unConvert(roleName)+"]�ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//��ѯ��ɫ��Ϣ
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
				writeLog(LInfo,"��ѯ��ɫ��Ϣ��");
				request.setAttribute("roleV",roleV);
				lTarget = "ToucsMonitor/usermanager/roleList.jsp";
			}
			//ɾ����ɫ��Ϣ
			if (TxCode.trim().equals(DELROLE)) {
				String roleId = request.getParameter("roleid");
				//add by liyp 20030906
				int affect = UM.delRoleInfo(roleId);
				if (affect > 0) {
					writeLog(LInfo,"ɾ����ɫ["+roleName+"]�ɹ���");
					PromptConstrParam = "��ɫ��Ϣ����";
					Prompt = "ɾ����ɫ["+toucsString.unConvert(roleName)+"]�ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					writeLog(LInfo,"ɾ����ɫ["+roleName+"]ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//��ɫ��Ȩ
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
					writeLog(LInfo,"��ɫ["+roleName+"]��Ȩʧ�ܣ�");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				} else {
					writeLog(LInfo,"��ɫ["+roleName+"]��Ȩ�ɹ���");
					PromptConstrParam = "��ɫ��Ϣ����";
					Prompt = "��ɫ["+roleName+"]��Ȩ�ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDROLE;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYROLE;
					SndButtonLable = "��ѯ";
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
