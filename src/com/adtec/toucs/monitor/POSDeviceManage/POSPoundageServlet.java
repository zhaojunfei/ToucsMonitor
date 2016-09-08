package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.Vector;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;

import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;

public class POSPoundageServlet extends CommonServlet {

	
	// �����룺����������
	private static final String APPEND = "14601" ;
	// �����룺�������޸�
	private static final String UPDATE = "14602";
	// �����룺�����Ѳ�ѯ
	private static final String QUERY = "14603";
	// �����룺������ɾ��
	private static final String DELETE = "14604";
	
	private static final String PARMGETPAGE = "page";
	private POSPoundageBean poundageBean = new POSPoundageBean();
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// ���û���ҳ�治����
		response.setHeader("Cache-Control", "no-store");
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");	
		LoginInfo login = null;
		try {
			// У���û��Ƿ��¼
			login = checkLogin(request, response);
			// У���û�����Ȩ��
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
			tarJsp = PAGE_HOME + "POSPoundagetManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(APPEND)) {// ����
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// ɾ��
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(UPDATE)) {// �޸�
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {// ��ѯ	
				QueryInfo(request, response, false, login);
			}
		}
	}
	
	/**
	 * �����û���ҳ��Ĳ���Ȩ��
	 * 
	 * @param req
	 *            Http����
	 * @param maskCode
	 *            �û�Ȩ����
	 */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		// ����
		if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
			req.setAttribute("APPEND", "1");
		}
		// ɾ��
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}
		// �޸�
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
		}
		// ��ѯ
		if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
			req.setAttribute("QUERY", "1");
		}
	}
//=========================================== INSERT START =====================================================================
	
	/**
	 * ����µ����pos�����ѽ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 *  20121205  syl          
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
			
		String tarJsp = null;//Ŀ���ַ
		POSPoundageInfo info= new POSPoundageInfo();
		PromptBean prompt = new PromptBean("���pos�����ѹ���");
		if (isGetPage) { 
			tarJsp = PAGE_HOME + "POSPoundageReg.jsp?post=" + APPEND;
		}else{
			//������pos������ǰ�Ĳ�ѯ
			try {
				info = poundageBean.QueryInfo();
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}	    	
			if(info == null){
				info = new POSPoundageInfo();
				info.fetchData(request);    
				try {
					//����µ����pos��������Ϣ
					int nReturn = poundageBean.AppendInfo(info);
					if (nReturn != -1) {						     
						writeLog(login, "���pos������[" + info.getParaVal() + "]��ӳɹ�");
						prompt.setPrompt("��������ӳɹ�!");
						request.setAttribute("prompt", prompt);
						tarJsp = "/ToucsMonitor/Success.jsp";
					} else {
						prompt.setPrompt("���pos���������ʧ�ܣ�");					         
						request.setAttribute("prompt", prompt);
						toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
					}					
				} catch (MonitorException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}else{
				prompt.setPrompt("���pos��������Ϣ�Ѵ���,�����ظ���ӣ�");		         
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());	
			}
		}		
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}	
//=============================================== END ======================================================================	

//============================================ DELETE START ============================================================================	
	/**
	 * ɾ�����pos��������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		try {
			//ɾ�����pos��������Ϣ
			int nReturn = poundageBean.DeleteInfo();		
			if(nReturn == 1){
				PromptBean prompt = new PromptBean("���pos�����ѹ���");
				prompt.setPrompt("���pos������ɾ���ɹ���");			
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}else{
				PromptBean prompt = new PromptBean("���pos�����ѹ���");
				prompt.setPrompt("���pos������ɾ��ʧ�ܣ�");			
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}	
//=============================================== END =====================================================================
	
//=========================================== UPDATE START ================================================================	
	/**
	 * �޸����pos��������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if(isGetPage){
			POSPoundageInfo info;
			try {
				//��ѯ��������Ϣ����תҳ��
				info = poundageBean.QueryInfos();			
				String tarJsp = null;
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "POSPoundageUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
				request.setAttribute("poundageInfo", info);
				toPage(request, response, tarJsp);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else{
			POSPoundageInfo info = new POSPoundageInfo();
			// ������������ȡ��Ҫ�޸ĵ��ڲ��˻���Ϣ
			info.fetchData(request);		
			try {
				//�޸����pos��������Ϣ
				int nReturn = poundageBean.UpdateInfo(info);
				if(nReturn == 1){
					// ת�ɹ���ʾҳ��
					PromptBean prompt = new PromptBean("���pos�����ѹ���");
					prompt.setPrompt("���pos�������޸ĳɹ���");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}else{
					// ת�ɹ���ʾҳ��
					PromptBean prompt = new PromptBean("���pos�����ѹ���");
					prompt.setPrompt("���pos�������޸�ʧ�ܣ�");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException e) {			
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
//============================================ END =========================================================================	
		
//============================================== SELECT START ==============================================================	
	/**
	 * ��ѯ���pos��������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		try {
			//��ѯ���pos�������б���Ϣ
			Vector v = poundageBean.QueryInfoByList();
			request.setAttribute("POSPoundageList", v);
			toPage(request, response, PAGE_HOME + "POSPoundageList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}
//============================================== END ======================================================================	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ���û���ҳ�治����
		response.setHeader("Cache-Control", "no-store");
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");
		LoginInfo login = null;
		try {
			// У���û��Ƿ��¼
			login = checkLogin(request, response);
			// У���û�����Ȩ��
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
			tarJsp = PAGE_HOME + "POSPoundagetManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {		
			// ����
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// ɾ��
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(UPDATE)) {
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {				
				QueryInfo(request, response, false, login);		
			}
		}
	}	
}
