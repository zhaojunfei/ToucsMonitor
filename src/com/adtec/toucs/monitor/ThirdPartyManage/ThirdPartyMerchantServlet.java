package com.adtec.toucs.monitor.ThirdPartyManage;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


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

public class ThirdPartyMerchantServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	// �����룺����
	private static final String APPEND = "13401";
	// �����룺ɾ��
	private static final String DELETE = "13402";
	// �����룺�޸�
	private static final String UPDATE = "13403";
	// �����룺��ѯ
	private static final String QUERY = "13404";
	// �����룺����ͣ��
	private static final String MANAGE = "13405";
	// �����룺��������Կ����ӡ
	private static final String KEYDOWN = "13406";

	// Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";


    // �̻���Ϣ����ҳ���·��
    private static final String PAGE_HOME = "/ToucsMonitor/ThirdPartyManage/";

    // �̻���Ϣ������ʵ��
    private ThirdPartyMerchantBean posManage = new ThirdPartyMerchantBean();

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

	   // �豸������������
	   if (reqCode == null) {
		   // Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
		   tarJsp = PAGE_HOME + "ThirdPartyMerchantManage.jsp?post=" + QUERY +"&uid=" + login.getUserID();
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
		   }else if (reqCode.equals(KEYDOWN)) {  
			   if ( (target == null) || target.trim().equals("")) {
				   KeyDown(request, response, login, "1" );
			   } else {
				   if (target.equals(PARMGETPAGE)) {
					   KeyDown(request, response, login, "0" );
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
		   Debug.println("ɾ���̻���Ϣ...POST����");
		   DeleteInfo(request, response, login);
	   }else if (reqCode.equals(MANAGE)) {  
		   if ( (target == null) || target.trim().equals("")) {
			   InfoManage(request, response, login, "1" );
		   } else {
			   if (target.equals(PARMGETPAGE)) {
				   InfoManage(request, response, login, "0" );
			   }
		   }
	   }else if (reqCode.equals(KEYDOWN)) { 
		   if ( (target == null) || target.trim().equals("")) {
			   KeyDown(request, response, login, "1" );
		   } else {
			   if (target.equals(PARMGETPAGE)) {
				   KeyDown(request, response, login, "0" );
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
		   // �̻�����
		   v = CodeManageBean.queryNewCodes("merchant_type", sq);
		   req.setAttribute("MchantType", v);   
		   // ��ȫ����
		   v = CodeManageBean.queryNewCodes("secu_kind", sq);
		   req.setAttribute("SecuKind", v);
		   // �շ�����
		   v = CodeManageBean.queryCodes("0569", sq);
		   req.setAttribute("FeeKind", v);  
		   // ҵ�����
		   v = CodeManageBean.queryCodes("0601", sq);
		   req.setAttribute("AgentCode", v);

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
   * �������豸��Ϣ�Ǽ�������
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
   			tarJsp = PAGE_HOME + "ThirdPartyMerchantReg.jsp?post=" + APPEND;
   		} else {
   			ThirdPartyMerchantInfo info = new ThirdPartyMerchantInfo();
   			info.fetchData(request);
   			try {
   				int nReturn = posManage.AppendInfo(info, 1);
   				posManage.addInfo(info);//�����̻���Կ 2012.06.19
   				PromptBean prompt = new PromptBean("֧���̻�����");
   				if (nReturn != -1) {
				   writeLog(login, "�̻�[" + info.getMerchantid() + "]��ӳɹ�");
				   prompt.setPrompt("�̻���ӳɹ�!");
				   request.setAttribute("prompt", prompt);
				   tarJsp = "/ToucsMonitor/Success.jsp";
			   } else {
				   prompt.setPrompt("�̻���Ӳ��ɹ���");
				   request.setAttribute("prompt", prompt);
				   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			   }
		   } catch (MonitorException exp) {
			   writeLog(login, "�̻�[" + info.getMerchantid() + "]���ʧ��");
			   errProc(request, response, exp);
		   }
	   }
	   Debug.println(tarJsp);
	   toPage(request, response, tarJsp);
   }

  /**
   * �������̻���Ϣɾ��
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void DeleteInfo(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("merchant_id");
	   try {
		   posManage.DeleteInfo(strKey);
		   posManage.Deletekey(strKey);
		   PromptBean prompt = new PromptBean("֧���̻�����");
		   writeLog(login, "�̻�[" + strKey + "]ɾ���ɹ�");
		   prompt.setPrompt("�̻�ɾ���ɹ���");
		   request.setAttribute("prompt", prompt);
		   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
	   } catch (MonitorException exp) {
		   writeLog(login, "�̻�[" + strKey + "]ɾ��ʧ��");
		   errProc(request, response, exp);
	   }
   }

  /**
   * �޸�POS�豸������Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void UPdateInfo(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
	   String strKey = request.getParameter("merchant_id");
	   if (isGetPage) {
		   if (strKey != null) {
			   String tarJsp = null;
			   try {
				   ThirdPartyMerchantInfo info = posManage.QueryInfo(strKey, "");
				   if (info == null) {
					   throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "�̻������ڣ�");
				   }
				   InitInfoList(request, login.getOrgID());
				   setPagePerm(request, login.getPermission());
				   tarJsp = PAGE_HOME + "ThirdPartyMerchantUpdate.jsp?post=" + UPDATE + "&uid=" + login.getUserID();
				   request.setAttribute("ThirdPartyMerchant", info);
				   toPage(request, response, tarJsp);
			   } catch (MonitorException exp) {
				   errProc(request, response, exp);
			   }
		   }
	   } else {
		   ThirdPartyMerchantInfo info = new ThirdPartyMerchantInfo();
		   info.fetchData(request);
		   try {
			   posManage.UpdateInfo(info, strKey);
			   writeLog(login, "�̻�[" + strKey + "]������Ϣ�޸ĳɹ�");
			   PromptBean prompt = new PromptBean("֧���̻�����");
			   prompt.setPrompt("�̻��޸ĳɹ���");
			   request.setAttribute("prompt", prompt);
			   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		   } catch (MonitorException exp) {
			   writeLog(login, "�̻�[" + strKey + "]������Ϣ�޸�ʧ��");
			   errProc(request, response, exp);
		   }
	   }
   	}

  /**
   * POS�豸��ѯ������
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
		   toPage(request, response, PAGE_HOME + "ThirdPartyMerchantManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
	   } else {
		   String strKey = request.getParameter("merchant_id");
		   String strType = request.getParameter("merchant_type");
		   if (strKey != null && !strKey.equals("")) {
			   try {
				   Vector v = posManage.QueryInfoByList(strKey, strType);
				   request.setAttribute("ThirdPartyMerchantList", v);
				   toPage(request, response,PAGE_HOME + "ThirdPartyMerchantList.jsp?uid=" + login.getUserID());
			   } catch (MonitorException exp) {
				   errProc(request, response, exp);
			   }
		   } else {
			   try {
				   Vector v = posManage.QueryInfoByList("", strType);
				   request.setAttribute("ThirdPartyMerchantList", v);
				   toPage(request, response,PAGE_HOME + "ThirdPartyMerchantList.jsp?uid=" + login.getUserID());
			   } catch (MonitorException exp) {
				   errProc(request, response, exp);
			   }
		   }
	   }
   	}

  /**
   * �������̻���Ϣ��ͣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param use_flag
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void InfoManage(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
	   String strKey = request.getParameter("merchant_id");
	   try {
		   posManage.ManageInfo(strKey, use_flag);
		   PromptBean prompt = new PromptBean("�̻�����");	 
		   if ( use_flag.equals("0") ) {
			   writeLog(login, "�̻�[" + strKey + "]ͣ�óɹ�");
			   prompt.setPrompt("�̻�[" + strKey + "]ͣ�óɹ���");
		   } else {
			   writeLog(login, "�̻�[" + strKey + "]���óɹ�");
			   prompt.setPrompt("�̻�[" + strKey + "]���óɹ���");
		   }
		   
		   //��ҪУ���û��Ƿ��м���Ȩ��
		   request.setAttribute("prompt", prompt);
		   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
	   } catch (MonitorException exp) {
		   writeLog(login, "�̻�[" + strKey + "]����ʧ��");
		   errProc(request, response, exp);
	   }
   	}

  /**
   * �������̻���Կ����
   * @param request Http����
   * @param response Http��Ӧ
   * @param use_flag ���ñ�ʶ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
   private void KeyDown(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
	   String strKey = request.getParameter("merchant_id");
	   try {
		   System.out.println("-----ThirdPartyMerchantServlet------KeyDown------");
		   
		   String auth_code = posManage.down_auth(strKey,use_flag);
		 //�̻�[105110060121264] ����Կ[56728eaac5e1fd19] ��Կ���[01] PINKey[e51e703848d8eba5] MACKey[82c0b0ac3e174938]
		   System.out.println("------auth_code-------"+auth_code);
		   
		   PromptBean prompt = new PromptBean("�̻�����");
		   writeLog(login, strKey, "MG7002", "�̻�������Կ�ɹ�");
		   prompt.setPrompt(auth_code);
		   request.setAttribute("prompt", prompt);
		   toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID()+"&str2="+"1");
	   } catch (MonitorException exp) {
		   writeLog(login, "�̻�[" + strKey + "]��������Կ����ӡʧ��");
		   errProc(request, response, exp);
	   }
   	}
}