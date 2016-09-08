package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:�������Ĵ������Servlet��</p>
 * <p>Description:��������ִ���������Ĵ��������ص�ҵ�����߼�������ҳ������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BankCodeManageServlet extends CommonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//��Ӵ���
	private static final String BANKCODE_ADD="17201";
	//�޸Ĵ���
	private static final String BANKCODE_MOD="17202";
	//ɾ������
	private static final String BANKCODE_DEL="17203";
	//��ѯ����
	private static final String BANKCODE_QUERY="17204";
	//���������ʵ��
	private static BankCodeManageBean bankCodeMan=new BankCodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode=request.getParameter("reqCode");
		//Ȩ�޿���
		LoginInfo login=null;
		try{
			//У���û��Ƿ��¼
			login=checkLogin(request,response);
			//У���û�����Ȩ��
			if(reqCode!=null&&reqCode.trim().length()>0)
				LoginManageBean.operValidate(login,reqCode);
		}catch(MonitorException exp){
			errProc(request,response,exp);
			return;
		}

		//��ѯ����
		if(reqCode==null||reqCode.equals(BANKCODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}else if(reqCode!=null&&reqCode.equals(BANKCODE_ADD)){//��Ӵ���
			//ת���Ӵ���ҳ��
			BankCodeBean code=new BankCodeBean();
			request.setAttribute("code",code);
			toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeReg.jsp?post="+BANKCODE_ADD+"&isMod=false&uid="+login.getUserID());
		} else if (reqCode!=null&&reqCode.equals(BANKCODE_MOD)){
			//�޸Ĵ���
			String bankCode=request.getParameter("bankCode");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(bankCode==null){
				//��ѯ����
				try{
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else {
				//ת�����޸�ҳ��
				try{
					queryModCode(request,response,bankCode,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		} else if (reqCode.equals(BANKCODE_DEL)){
			//ɾ������
			String bankCode=request.getParameter("bankCode");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(bankCode==null){
				//��ѯ����
				try{
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//ɾ������
					bankCodeMan.deleteCode(bankCode);
					//��ѯ����
					queryCode(request,response,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode=request.getParameter("reqCode");
		//Ȩ�޿���
		LoginInfo login=null;
		try{
			//У���û��Ƿ��¼
			login=checkLogin(request,response);
			//У���û�����Ȩ��
			if(reqCode!=null&&reqCode.trim().length()>0)
				LoginManageBean.operValidate(login,reqCode);
		}catch(MonitorException exp){
			errProc(request,response,exp);
			return;
		}

		//��ѯ����
		if(reqCode==null||reqCode.equals(BANKCODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}else if(reqCode.equals(BANKCODE_ADD)){
			//��Ӵ���
			BankCodeBean code=new BankCodeBean();
			code.fetchData(request);
			try{
				//���Ӵ���
				bankCodeMan.addCode(code);
				//��ѯ����
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(BANKCODE_MOD)){
			//�޸Ĵ���
			BankCodeBean code=new BankCodeBean();
			code.fetchData(request);
			try{
				bankCodeMan.modifyCode(code);
				//��ѯ����
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(BANKCODE_DEL)){
			try{
				//ɾ������
				bankCodeMan.deleteCode(request.getParameter("bankCode"));
				//��ѯ����
				queryCode(request,response,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		}
	}

  /**
   * ��ѯ����
   * @param request Http����
   * @param response Http��Ӧ
   * @param codeType ��������
   * @param login ��¼�û���Ϣ
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	private void queryCode(HttpServletRequest request,HttpServletResponse response,
                             LoginInfo login)throws ServletException,IOException,MonitorException{
		//��ѯ�û�Ȩ��
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),BANKCODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//��ѯ�������Ĵ���
		Vector v=bankCodeMan.queryCodes();
		request.setAttribute("list",v);
		//ת�����ѯҳ��
		toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeQuery.jsp?&uid="+login.getUserID());
	}

  /**
   * ��ѯĳ���׵���ϸ��Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param deviceType �豸����
   * @param txnCode ������
   * @param login ��¼�û���Ϣ
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	private void queryModCode(HttpServletRequest request,HttpServletResponse response,
                             String bankCode,LoginInfo login)
                             throws ServletException,IOException,MonitorException{
		BankCodeBean code = bankCodeMan.queryCode(bankCode);
		request.setAttribute("code",code);
		toPage(request,response,"/ToucsMonitor/SystemManage/BankCodeReg.jsp?post="+BANKCODE_MOD+"&isMod=true&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}