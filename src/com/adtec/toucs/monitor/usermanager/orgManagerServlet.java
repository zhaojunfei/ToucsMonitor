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

	//��ӻ���
	private static final String ADDORG = "10101";
	//�޸Ļ���
	private static final String MODIFYORG = "10102";
	//��ѯ����
	private static final String QUERYORG = "10104";
	//ɾ������
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
			out.println("<script>alert(\"ȡϵͳ����ʧ�ܣ�\");</script>");
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
			//����������Ϣ
			if (TxCode.trim().equals(ADDORG)) {
				if (orgId == null || orgId.equals("") ||
					orgName == null || orgName.equals("") ||
					p_orgId == null || p_orgId.equals("") ||
					orgAddress == null || orgAddress.equals("") ||
					operType == null || operType.equals("") ) {
						out.println("<script>alert(\"���������б����\");history.go(-1);</script>");
						return;
				}

				int affect = UM.addOrg(orgId,orgName,p_orgId,ipAddress,orgAddress,connecter,phoneNo,operType);
				if (affect > 0) {
					//wuye add 20020226 ����»�����Ӧ����CodeTable
					CodeManageBean.initCodeTable();
					//add end
					writeLog(LInfo,"��������"+orgId+"�ɹ���");
					PromptConstrParam = "������Ϣ����";
					Prompt = "���������ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else {
					writeLog(LInfo,"��������"+orgId+"ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//�޸Ļ�����Ϣ
			if (TxCode.trim().equals(MODIFYORG)) {
				if (orgId == null || orgId.equals("") ||
				orgName == null || orgName.equals("") ||
                orgAddress == null || orgAddress.equals("") ||
                operType == null || operType.equals("") ) {
					out.println("<script>alert(\"���������б����\");history.go(-1);</script>");
					return;
				}
				int affect = UM.modifyOrgInfo(orgId,orgName,ipAddress,orgAddress,connecter,phoneNo,operType);
				if (affect > 0) {
					//wuye add 20020226 �޸Ļ�����Ӧ����CodeTable
					CodeManageBean.initCodeTable();
					//add end
					writeLog(LInfo,"�޸Ļ���"+orgId+"�ɹ���");
					PromptConstrParam = "������Ϣ����";
					Prompt = "�޸Ļ����ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					writeLog(LInfo,"�޸Ļ���"+orgId+"ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else {
					writeLog(LInfo,"�޸Ļ���"+orgId+"ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
			//��ѯ������Ϣ
			if (TxCode.trim().equals(QUERYORG)) {
				Vector oV = new Vector();
				if (orgId == null || orgId.length() <= 0) {
					orgId = "*";
				}
				if (orgName == null || orgName.length() <= 0) {
					orgName = "*";
				}

				oV = UM.queryOrgInfo(orgId,orgName,LInfo.getOrgID());
				writeLog(LInfo,"��ѯ������Ϣ");
				request.setAttribute("orgV",oV);
				lTarget = "/ToucsMonitor/usermanager/orgList.jsp";
			}
			//ɾ��������Ϣ
			if (TxCode.trim().equals(DELORG)) {
				if (UM.isAtmExist(orgId)) {
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ����ATM��Ϣ��");
					throw new MonitorException(ErrorDefine.ATMEXISTERR,ErrorDefine.ATMEXISTDESC);
				}
				if (UM.isUserExistInOrg(orgId)) {
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ�����û���Ϣ��");
					throw new MonitorException(ErrorDefine.USEREXISTERR,ErrorDefine.USEREXISTDESC);
				}
				if (UM.isCdmExist(orgId)) {
					Debug.println("ɾ������,��Ϊ����CDM��Ϣ��");
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ����CDM��Ϣ��");
					throw new MonitorException(ErrorDefine.DELORG_FAILED3,"�������¼�����������CDM�豸��Ϣ������ɾ��CDM�豸��Ϣ");
				}
				if (UM.isMctExist(orgId)) {
					Debug.println("ɾ������,��Ϊ�����̻���Ϣ��");
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ�����̻���Ϣ��");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"�������¼������������̻���Ϣ������ɾ���̻���Ϣ");
				}
				if (UM.isPemExist(orgId)) {
					Debug.println("ɾ������,��Ϊ����PEM��Ϣ��");
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ����PEM��Ϣ��");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"�������¼�����������PEM�豸��Ϣ������ɾ��PEM�豸��Ϣ");
				}
				if (UM.isMitExist(orgId)) {
					Debug.println("ɾ������,��Ϊ����MIT��Ϣ��");
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ���Ϊ����MIT��Ϣ��");
					throw new MonitorException(ErrorDefine.DELORG_FAILED2,"�������¼�����������MIT�豸��Ϣ������ɾ��MIT�豸��Ϣ");
				}
				int affect = UM.delOrgInfo(orgId);
				if (affect > 0) {

					writeLog(LInfo,"ɾ������"+orgId+"�ɹ���");
					PromptConstrParam = "������Ϣ����";
					Prompt = "ɾ�������ɹ���";
					FstButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+ADDORG;
					FstButtonLable = "���";
					SndButtonUrl = "/ToucsMonitor/userMngServlet?reqCode="+QUERYORG;
					SndButtonLable = "��ѯ";
					lTarget="/ToucsMonitor/Success.jsp";
				} else if (affect == 0) {
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ�");
					throw new MonitorException(ErrorDefine.RECNOTFOUND,ErrorDefine.RECNOTFOUNDDESC);
				} else{
					writeLog(LInfo,"ɾ������"+orgId+"ʧ�ܣ�");
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
