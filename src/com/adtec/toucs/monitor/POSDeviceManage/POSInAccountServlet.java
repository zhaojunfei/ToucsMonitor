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

public class POSInAccountServlet extends CommonServlet {


	// �����룺�ڲ��˻�����
	private static final String APPEND = "14301" ;
	// �����룺�ڲ��˻�ɾ��
	private static final String DELETE = "14304";
	// �����룺�ڲ��˻��޸�
	private static final String UPDATE = "14302";
	// �����룺�ڲ��˻���ѯ
	private static final String QUERY = "14303";

	private static final String PARMGETPAGE = "page";
	private POSInAccountBean inAccountBean = new POSInAccountBean();

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
			tarJsp = PAGE_HOME + "POSInAccountManage.jsp?uid=" + login.getUserID();
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
	 * ����µ����pos�ڲ��˻�
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException,IOException
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		String str = request.getParameter("acct_type");
		POSInAccountInfo info = null;
			if (isGetPage) { 
				tarJsp = PAGE_HOME + "POSInAccountReg.jsp?post=" + APPEND;
		    } else {
		    	try {
		    		//����ڲ��˻�ǰ�Ĳ�ѯ
					info = inAccountBean.QueryInfo();
				} catch (MonitorException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				PromptBean prompt = new PromptBean("���pos�ڲ��˻�����");
		    	if(info == null){
		    		info = new POSInAccountInfo();
				    info.fetchData(request);
				    try {
				    	int nReturn = inAccountBean.AppendInfo(info);
				        if (nReturn != -1) {
				          writeLog(login, "���pos�ڲ��˻�[" + info.getParaVal() + "]��ӳɹ�");
				          prompt.setPrompt("���pos�ڲ��˻���ӳɹ�!");
				          request.setAttribute("prompt", prompt);
				          tarJsp = "/ToucsMonitor/Success.jsp";
				        }else {
				          prompt.setPrompt("���pos�ڲ��˻����ʧ�ܣ�");
				          request.setAttribute("prompt", prompt);
				          toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				        }
				     }catch (MonitorException exp) {
				        writeLog(login, "���pos�ڲ��˻�[" + info.getParaVal() + "]���ʧ��");
				        errProc(request, response, exp);
				      }
		    	}else{
		    		  prompt.setPrompt("���pos�ڲ��˻��Ѵ���,�����ظ���ӣ�");
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
	 * ���POS�ڲ��˻�ɾ��
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		try {
			inAccountBean.DeleteInfo();
			PromptBean prompt = new PromptBean("���pos�ڲ��˻�����");
			prompt.setPrompt("���pos�˺�ɾ���ɹ���");	
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
	 * �޸����POS�ڲ��˻���Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if(isGetPage){
			POSInAccountInfo info;
			try {
				info = inAccountBean.QueryInfo();
				String tarJsp = null;
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "POSInAccountUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
		        request.setAttribute("InAccountInfo", info);
		        toPage(request, response, tarJsp);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else{
			POSInAccountInfo info = new POSInAccountInfo();
		     // ������������ȡ��Ҫ�޸ĵ��ڲ��˻���Ϣ
			info.fetchData(request);
			try {
				inAccountBean.UpdateInfo(info);
			} catch (MonitorException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
	        // ת�ɹ���ʾҳ��
	        PromptBean prompt = new PromptBean("���pos�ڲ��˻�����");
	        prompt.setPrompt("���pos�˻��޸ĳɹ���");
	        request.setAttribute("prompt", prompt);
	        toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}
	}
//==============================================END=============================================================================================================
	
//===========================================SELECT START======================================================================================================	
	/**
	 * ���POS�ڲ��˻���ѯ������ 
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		try {
			Vector v = inAccountBean.QueryInfoByList();
			request.setAttribute("POSInAccountList", v);
			toPage(request, response, PAGE_HOME + "POSInAccountList.jsp?uid=" + login.getUserID());
	    } catch (MonitorException exp) {
	    	errProc(request, response, exp);
	    }
	}	
//===============================================================================================================================================================
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���û���ҳ�治����
		response.setHeader("Cache-Control", "no-store");
		response.setContentType ("text/html;charset=GBK");
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
			tarJsp = PAGE_HOME + "POSInAccountManage.jsp?uid=" + login.getUserID();
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
}
