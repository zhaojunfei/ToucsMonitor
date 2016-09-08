package com.adtec.toucs.monitor.POSDeviceManage;


import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.ThirdPartyManage.ThirdPartyMerchantInfo;
import com.adtec.toucs.monitor.ThirdPartyManage.ThirdPartyTellerInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 * @version 1.0
 */

public class POSBCCompanyServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// �����룺����
	private static final String APPEND = "16901";
	// �����룺ɾ��
	private static final String DELETE = "16902";
	// �����룺�޸�
	private static final String UPDATE = "16903";
	// �����룺��ѯ
	private static final String QUERY = "16904";
	// �����룺����ͣ��
	private static final String MANAGE = "16905";
	
	


	// Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	// Ԥ������Ϣ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/posBCCompany";
	// Ԥ������Ϣ����ҳ���·��
    private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

    private POSBCCompanyBean posManage = new POSBCCompanyBean();

    public void init() throws ServletException {
    }

    // Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Cache-Control", "no-store");
	    String reqCode = request.getParameter("reqCode");
	    String target = request.getParameter("target");

	    LoginInfo login = null;
	    try {
		    //У���û��Ƿ��¼
		    login = checkLogin(request, response);
		    Debug.println("********CHECK LOGIN********");
		    //У���û�����Ȩ��
		    if (reqCode != null && reqCode.trim().length() > 0) {
		 	   LoginManageBean.operValidate(login, reqCode);
		    }
	    }catch (MonitorException exp) {
 	 	    errProc(request, response, exp);
 		    return;
	    }
	    String tarJsp = null;

	   if (reqCode == null) {
		   tarJsp = PAGE_HOME + "POSBCCompanyManage.jsp?post=" + QUERY +"&uid=" + login.getUserID();
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
		   }
	   }
  }

    //Process the HTTP Post request
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
		   DeleteInfo(request, response, login);
	   }else if (reqCode.equals(MANAGE)) {  
		   if ( (target == null) || target.trim().equals("")) {
			   InfoManage(request, response, login, "1" );
		   } else {
			   if (target.equals(PARMGETPAGE)) {
				   InfoManage(request, response, login, "0" );
			   }
		   }
	   }
   }

   //Clean up resources
   public void destroy() {
   }

  /**
   * �����û���ҳ��Ĳ���Ȩ��
   * @param req Http����
   * @param maskCode �û�Ȩ����
   */
 
   private void setPagePerm(HttpServletRequest req, String maskCode) {
	   //����
	   if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
		   req.setAttribute("APPEND", "1");
	   }
	   //�޸�
	   if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
		   req.setAttribute("UPDATE", "1");
	   }
	   //ɾ��
	   if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
		   req.setAttribute("DELETE", "1");
	   }
	   //��ѯ
	   if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
		   req.setAttribute("QUERY", "1");
	   }
   }

  /**
   * ��ʼ�����ҳ��������б���Ϣ
   * @param req Http����
   * @param orgId ��������
   */
   private void InitInfoList(HttpServletRequest req, String orgId) {
	   Vector v;
	   SqlAccess sq = null;
	   try {
		   sq = SqlAccess.createConn();  
			// ��ȫ����
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("secu_kind", v);
		   // �����б�
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
    * Ԥ������˾��Ϣ�Ǽ�������
    * @param request Http����
    * @param response Http��Ӧ
    * @param isGetPage Get�����־
    * @param login �û���Ϣ
    * @throws ServletException
    * @throws IOException
    */
   	private void AppendInfo(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
   		String tarJsp = null;
   		if (isGetPage) {
   			InitInfoList(request, login.getOrgID());
   			tarJsp = PAGE_HOME + "POSBCCompanyReg.jsp?post=" + APPEND;
   		} else {
   			POSBCCompanyInfo info = new POSBCCompanyInfo();
   			info.fetchData(request);
   			try {
   				//������Ӧ����
   				int ret = posManage.AppendInfo(info);
   				
   				PromptBean prompt = new PromptBean("Ԥ������˾����");
   				if (ret != -1) {
				   writeLog(login, "Ԥ������˾[" + info.getCompany_id() + "]��ӳɹ�");
				   prompt.setPrompt("Ԥ������˾��ӳɹ�!");
				   prompt.setButtonUrl(0, "��������", MANAGE_HOME);
				   request.setAttribute("prompt", prompt);
				   tarJsp = "/ToucsMonitor/Success.jsp";
			   } else {
				   prompt.setPrompt("Ԥ������˾��Ӳ��ɹ���");
				   request.setAttribute("prompt", prompt);
				   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			   }
		   } catch (Exception exp) {
			   writeLog(login, "Ԥ������˾[" + info.getCompany_id() + "]���ʧ��");
			   exp.printStackTrace();
			   System.out.println(exp.getMessage());
		   }
	   }
	   Debug.println(tarJsp);
	   toPage(request, response, tarJsp);
   }

  /**
   * Ԥ������˾��Ϣɾ��
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void DeleteInfo(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("company_id");
	   String[] strArray = request.getParameterValues("box1");
		//ȡ��Ҫɾ���Ŀ�����Ϣ�ṹ�壬��ɾ��֮��
		//���´����ݿ��в�ѯ��ȡ���û�����һ��ҳ�����õĲ�ѯ������
		String strIndex="";
		int index=0;
	   try {
		   //������Ӧ����
		   if(strArray != null){
				//ҳ�洫�ݸ�ѡ���е�ֵ��Ϊ�����������ɾ��
				posManage.deleteBCCards(strArray);
			//	String retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
				//jsp��ת
				PromptBean prompt = new PromptBean("Ԥ����POS������Ϣ����");
				prompt.setPrompt("Ԥ����POS������Ϣɾ���ɹ���");
				prompt.setButtonUrl(0, "��������", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}else{
		  
		   posManage.DeleteInfo(strKey);
		   PromptBean prompt = new PromptBean("Ԥ������˾����");
		   writeLog(login, "Ԥ������˾[" + strKey + "]ɾ���ɹ�");
		   prompt.setPrompt("Ԥ������˾ɾ���ɹ���");
		   prompt.setButtonUrl(0, "��������", MANAGE_HOME);
		   request.setAttribute("prompt", prompt);
		   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
	   } catch (Exception exp) {
		   writeLog(login, "Ԥ������˾[" + strKey + "]ɾ��ʧ��");
		   exp.printStackTrace();
		   System.out.println(exp.getMessage());
	   }
   }

  /**
   * �޸�Ԥ������˾������Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void UPdateInfo(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("company_id");
	   if (isGetPage) {
		   if (strKey != null) {
			   String tarJsp = null;
			   try {
				   //������Ӧ�Ĺ���,ҳ����ת
				   POSBCCompanyInfo info = posManage.QueryInfo(strKey, "");
				   if (info == null) {
					   throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "��˾�����ڣ�");
				   }
				   InitInfoList(request, login.getOrgID());
				   setPagePerm(request, login.getPermission());
				   tarJsp = PAGE_HOME + "POSBCCompanyUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
				   request.setAttribute("POSBCCompany", info);
				   toPage(request, response, tarJsp);
			   } catch (Exception exp) {
				   exp.printStackTrace();
				   System.out.println(exp.getMessage());
			   }
		   }
	   } else {
		   POSBCCompanyInfo info = new POSBCCompanyInfo();
		   info.fetchData(request);
		   try {
			   posManage.UpdateInfo(info, strKey);
			   writeLog(login, "Ԥ������˾[" + strKey + "]������Ϣ�޸ĳɹ�");
			   PromptBean prompt = new PromptBean("Ԥ������˾����");
			   prompt.setPrompt("Ԥ������˾�޸ĳɹ���");
			   prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			   request.setAttribute("prompt", prompt);
			   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		   } catch (MonitorException exp) {
			   writeLog(login, "Ԥ������˾[" + strKey + "]������Ϣ�޸�ʧ��");
			   errProc(request, response, exp);
		   }
	   }
   	}

   /**
    * Ԥ������˾��ѯ������
    * @param request Http����
    * @param response Http��Ӧ
    * @param isGetPage Get����
    * @param login �û���Ϣ
    * @throws ServletException
    * @throws IOException
    */
   	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            boolean isGetPage, LoginInfo login) throws ServletException,IOException {
   		if (isGetPage) {
   			InitInfoList(request, login.getOrgID());
   			toPage(request, response, PAGE_HOME + "POSBCCompanyManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
   		} else {
   			String strKey = request.getParameter("company_id");
   			System.out.println("company_id:" + strKey);
   			String strType = request.getParameter("org_id");
   			System.out.println("org_id:" + strType);
   			if (strKey != null && !strKey.equals("")) {
   				try {
   					//����
   					//request.setAttribute("POSBCCompanyList", v);
   					Vector v = posManage.QueryInfoByList(strKey, strType);
   					request.setAttribute("POSBCCompanyList", v);
   					toPage(request, response,PAGE_HOME + "POSBCCompanyList.jsp?uid=" + login.getUserID());
   				} catch (Exception exp) {
   					exp.printStackTrace();
   				}
   			} else {
   				try {
   					String org_id   = request.getParameter("org_id");
   					if(org_id == ""){
   						org_id = null;
   					}

   			     	String company_name = request.getParameter("company_name");
			    	if(company_name == ""){
			 		company_name = null;
			    	}
				
				List list = posManage.getBCByOrgAndName(org_id, company_name); 
				request.setAttribute("POSBCCompanyList", list);
   					toPage(request, response,PAGE_HOME + "POSBCCompanyList.jsp?uid=" + login.getUserID());
   				} catch (MonitorException exp) {
   					errProc(request, response, exp);
   				}
   			}
   		}
   	}

   /**
    * Ԥ������˾��ͣ
    * @param request Http����
    * @param response Http��Ӧ
    * @param use_flag
    * @throws ServletException
    * @throws IOException
    */
   	private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
   		String strKey = request.getParameter("company_id");
   		try {
		   //������Ӧ�ķ���
   			posManage.ManageInfo(strKey, use_flag);
   			PromptBean prompt = new PromptBean("Ԥ������˾����");	 
   			if ( use_flag.equals("0") ) {
   				writeLog(login, "Ԥ������˾[" + strKey + "]ͣ�óɹ�");
   				prompt.setPrompt("Ԥ������˾[" + strKey + "]ͣ�óɹ���");
   			} else {
   				writeLog(login, "Ԥ������˾[" + strKey + "]���óɹ�");
   				prompt.setPrompt("Ԥ������˾[" + strKey + "]���óɹ���");
   			}
   			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
   			//��ҪУ���û��Ƿ��м���Ȩ��
   			request.setAttribute("prompt", prompt);
   			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
   		} catch (Exception exp) {
   			writeLog(login, "Ԥ������˾[" + strKey + "]����ʧ��");
   			exp.printStackTrace();
   			System.out.println(exp.getMessage());
   		}
   	}


}