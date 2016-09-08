package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:ƽ̨���������Servlet��</p>
 * <p>Description:��������ִ��ϵͳ���������ص�ҵ�����߼�������ҳ������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TxnCodeManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	//�����룺��ѯ����
	private static final String TXNCODE_QUERY="17104";
	//�����룺��Ӵ���
	private static final String TXNCODE_ADD="17101";
	//�����룺�޸Ĵ���
	private static final String TXNCODE_MOD="17102";
	//�����룺ɾ������
	private static final String TXNCODE_DEL="17103";
	//���������ʵ��
	private static TxnCodeManageBean txnCodeMan=new TxnCodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode=request.getParameter("reqCode");
		String deviceType=request.getParameter("deviceType");
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
		if(reqCode==null||reqCode.equals(TXNCODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode!=null&&reqCode.equals(TXNCODE_ADD)){
			//��Ӵ���
			//�����������Ϊ�գ�ת�����ѯҳ��
			if(deviceType==null){
				//��ѯ����
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else{
				//ת���Ӵ���ҳ��
				TxnCodeBean code=new TxnCodeBean();
				code.setDeviceType(deviceType);
				code.setTxnCode("");
				code.setTxnName("");
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeReg.jsp?post="+TXNCODE_ADD+"&uid="+login.getUserID());
			}
		} else if(reqCode!=null&&reqCode.equals(TXNCODE_MOD)){
			//�޸Ĵ���
			String txnCode=request.getParameter("txnCode");
			String txnName=request.getParameter("txnName");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(deviceType==null||txnCode==null){
				//��ѯ����
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else{
				//ת�����޸�ҳ��
				try{
					queryModCode(request,response,deviceType,txnCode,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}
		} else if(reqCode.equals(TXNCODE_DEL)){
			//ɾ������
			String txnCode=request.getParameter("txnCode");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(deviceType==null||txnCode==null){
				//��ѯ����
				try{
					queryCode(request,response,deviceType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//ɾ������
					txnCodeMan.deleteCode(deviceType,txnCode);
					//��ѯ����
					queryCode(request,response,deviceType,login);
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
		String deviceType=request.getParameter("deviceType");
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
		if(reqCode==null||reqCode.equals(TXNCODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_ADD)){
			//��Ӵ���
			TxnCodeBean code=new TxnCodeBean();
			code.fetchData(request);
			try{
				//���Ӵ���
				txnCodeMan.addCode(code);
				//��ѯ����
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_MOD)){
			//�޸Ĵ���
			TxnCodeBean code=new TxnCodeBean();
			code.fetchData(request);
			try{
				txnCodeMan.modifyCode(code);
				//��ѯ����
				queryCode(request,response,deviceType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if(reqCode.equals(TXNCODE_DEL)){
			//ɾ������
			try{
				txnCodeMan.deleteCode(deviceType,request.getParameter("txnCode"));
				//��ѯ����
				queryCode(request,response,deviceType,login);
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
                             String deviceType,LoginInfo login)throws ServletException,IOException,MonitorException{
		//��ѯ�û�Ȩ��
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),TXNCODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//�����豸���Ͳ�ѯ������
		if(deviceType!=null){
			Vector v=txnCodeMan.queryCodes(deviceType);
			request.setAttribute("deviceType",deviceType);
			request.setAttribute("list",v);
		}
		//ת�����ѯҳ��
		toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeQuery.jsp?&uid="+login.getUserID());
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
                             String deviceType,String txnCode,LoginInfo login)
                             throws ServletException,IOException,MonitorException{
		TxnCodeBean code = txnCodeMan.queryCode(deviceType,txnCode);
		request.setAttribute("code",code);
		toPage(request,response,"/ToucsMonitor/SystemManage/TxnCodeReg.jsp?post="+TXNCODE_MOD+"&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}