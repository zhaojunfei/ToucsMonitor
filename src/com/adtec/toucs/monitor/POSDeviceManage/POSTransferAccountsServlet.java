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

public class POSTransferAccountsServlet extends CommonServlet {

	
	// �����룺ת�˽������
	private static final String APPEND = "14501" ;

	// �����룺ת�˽���޸�
	private static final String UPDATE = "14502";

	// �����룺ת�˽���ѯ
	private static final String QUERY = "14503";
	
	// �����룺ת�˽��ɾ��
	private static final String DELETE = "14504";


	private static final String PARMGETPAGE = "page";
	private POSTransferAccountsBean posTransferAccount = new POSTransferAccountsBean();

	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			tarJsp = PAGE_HOME + "POSTransferAccountsManage.jsp?uid=" + login.getUserID();
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
			}

			// ɾ��
			if (reqCode.equals(DELETE)) {
				DeleteInfo(request, response, login);
			}

			// �޸�
			if (reqCode.equals(UPDATE)) {
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}

			// ��ѯ
			if (reqCode.equals(QUERY)) {				
				QueryInfo(request, response, false, login);			
			}
		}
	}

	
	/**
	 * �����û���ҳ��Ĳ���Ȩ��
	 * @param req Http����
	 * @param maskCode �û�Ȩ����
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

//======================================INSERT START===============================================================	
	/**
	 * ����µ����posת�˽��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException	
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		
		String tarJsp = null;
		POSTransferAccountsInfo info = null;
		if (isGetPage) { 
			tarJsp = PAGE_HOME + "POSTransferAccountsReg.jsp?post=" + APPEND;
		}else {
	    	try {
	    		//���ת�˽��ǰ�Ĳ�ѯ 
				info = posTransferAccount.QueryInfo();
				
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			PromptBean prompt = new PromptBean("���posת�˽�����");
		    if(info == null){
		    	info = new POSTransferAccountsInfo();
				info.fetchData(request);

				try {
					int nReturn = posTransferAccount.AppendInfo(info); 
				    if (nReturn != -1) { 
				    	writeLog(login, "���posת�˽��[" + info.getParaVal() + "]��ӳɹ�");
				    	prompt.setPrompt("���posת�˽����ӳɹ�!");
				    	request.setAttribute("prompt", prompt);
				    	tarJsp = "/ToucsMonitor/Success.jsp";  
				     }else {   	
				        prompt.setPrompt("���posת�˽�����ʧ�ܣ�");
				        request.setAttribute("prompt", prompt);
				        toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());  
				     }
				} catch (MonitorException exp) {
				     writeLog(login, "���posת�˽��[" + info.getParaVal() + "]���ʧ��");
				     errProc(request, response, exp);
				}
		    }else{	    		
		    	prompt.setPrompt("���posת�˽���Ѵ���,�����ظ���ӣ�");   
		    	request.setAttribute("prompt", prompt);
		    	toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		    } 
		}
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
		
//================================================END==========================================================================================================	
	
//================================================DELETE START=================================================================================================	
	/**
	 * ���posת�˽��ɾ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
			throws ServletException, IOException {
		try {
			posTransferAccount.DeleteInfo();
			PromptBean prompt = new PromptBean("���posת�˽�����");
			prompt.setPrompt("���posת�˽����Ϣɾ���ɹ���");
			request.setAttribute("prompt", prompt);
		    toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
//================================================END======================================================================================================
	
//============================================UPDATE START==================================================================================================
	/**
	 * �޸����posת�˽����Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		if(isGetPage){
			POSTransferAccountsInfo info;
			try {
				//��ת�޸�ҳ��ǰ��ѯ��Ӧת�˽����Ϣ
				info = posTransferAccount.QueryInfo();
				String tarJsp = null;
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "POSTransferAccountsUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
		        request.setAttribute("posTransferAccountsInfo", info);
		        toPage(request, response, tarJsp);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else{
			POSTransferAccountsInfo info = new POSTransferAccountsInfo();
		     // ������������ȡ��Ҫ�޸ĵ�ת�˽����Ϣ
			info.fetchData(request);
			try {
				posTransferAccount.UpdateInfo(info);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	        // ת�ɹ���ʾҳ��
	        PromptBean prompt = new PromptBean("���posת�˽�����");
	        prompt.setPrompt("���posת�˽���޸ĳɹ���");
	        request.setAttribute("prompt", prompt);
	        toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}
	}
//==============================================END=============================================================================================

//===========================================SELECT START=======================================================================================
	/**
	 * ��ѯ���posת�˽�� 
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login)
			throws ServletException, IOException {
		try {
			//��ѯת�˽����Ϣ
			Vector v = posTransferAccount.QueryInfoByList();
			request.setAttribute("POSTransferAccountsList", v);
			toPage(request, response, PAGE_HOME + "POSTransferAccountsList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}
//========================================== END ========================================================================	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
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
			tarJsp = PAGE_HOME + "POSTransferAccountsManage.jsp?uid=" + login.getUserID();
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
			}else if (reqCode.equals(DELETE)) {
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
