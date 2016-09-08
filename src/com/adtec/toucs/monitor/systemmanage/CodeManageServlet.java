package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title:�������Servlet��</p>
 * <p>Description:��������ִ��ϵͳ���������ص�ҵ�����߼�������ҳ������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�����룺��ѯ����
	private static final String CODE_QUERY="17004";
	//�����룺��Ӵ���
	private static final String CODE_ADD="17001";
	//�����룺�޸Ĵ���
	private static final String CODE_MOD="17002";
	//�����룺ɾ������
	private static final String CODE_DEL="17003";
	//���������ʵ��
	private static CodeManageBean codeMan=new CodeManageBean();

	//Initialize global variables
	public void init() throws ServletException {

	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode=request.getParameter("reqCode");
		String codeType=request.getParameter("codeType");

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
		if(reqCode==null||reqCode.equals(CODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode!=null&&reqCode.equals(CODE_ADD)){
			//��Ӵ���
			//�����������Ϊ�գ�ת�����ѯҳ��
			if(codeType==null){
				//��ѯ����
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else	{
				//ת���Ӵ���ҳ��
				CodeBean code=new CodeBean();
				code.setCodeType(codeType);
				code.setCodeId("");
				code.setCodeDesc("");
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/CodeReg.jsp?post="+CODE_ADD+"&uid=");
			}
		} else if (reqCode!=null&&reqCode.equals(CODE_MOD)){
			//�޸Ĵ���
			String id=request.getParameter("id");
			String desc=request.getParameter("desc");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(codeType==null||id==null){
				//��ѯ����
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			} else {
				//ת�����޸�ҳ��
				CodeBean code=new CodeBean();
				code.setCodeType(codeType);
				code.setCodeId(request.getParameter("id"));
				code.setCodeDesc(toucsString.unConvert(desc));
				request.setAttribute("code",code);
				toPage(request,response,"/ToucsMonitor/SystemManage/CodeReg.jsp?post="+CODE_MOD+"&uid=");
			}
		} else if (reqCode.equals(CODE_DEL)){
			//ɾ������
			String id=request.getParameter("id");
			//����������ͻ����Ϊ�գ�ת�����ѯҳ��
			if(codeType==null||id==null){
				//��ѯ����
				try{
					queryCode(request,response,codeType,login);
				}catch(MonitorException exp){
					errProc(request,response,exp);
				}
			}else{
				try{
					//ɾ������
					codeMan.deleteCode(codeType,id);
					//��ѯ����
					queryCode(request,response,codeType,login);
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
		String codeType=request.getParameter("codeType");
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
		if(reqCode==null||reqCode.equals(CODE_QUERY)){
			try{
				//��ѯ����
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_ADD)){
			//��Ӵ���
			CodeBean code=new CodeBean();
			code.setCodeType(codeType);
			code.setCodeId(request.getParameter("id"));
			code.setCodeDesc(request.getParameter("desc"));
			try{
				//���Ӵ���
				codeMan.addCode(code);
				//��ѯ����
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_MOD)){
			//�޸Ĵ���
			CodeBean code=new CodeBean();
			code.setCodeType(codeType);
			code.setCodeId(request.getParameter("id").trim());
			code.setCodeDesc(request.getParameter("desc"));
			try{
				codeMan.modeCode(code,request.getParameter("key"));
				//��ѯ����
				queryCode(request,response,codeType,login);
			}catch(MonitorException exp){
				errProc(request,response,exp);
			}
		} else if (reqCode.equals(CODE_DEL)){
    	 //ɾ������
			try{
				codeMan.deleteCode(codeType,request.getParameter("key"));
				//��ѯ����
				queryCode(request,response,codeType,login);
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
                             String codeType,LoginInfo login)throws ServletException,IOException,MonitorException{
		//��ѯ�û�Ȩ��
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_QUERY)==0)
			request.setAttribute("QUERYPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_ADD)==0)
			request.setAttribute("ADDPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_MOD)==0)
			request.setAttribute("MODPERM","1");
		if(LoginManageBean.checkPermMask(login.getPermission(),CODE_DEL)==0)
			request.setAttribute("DELPERM","1");
		//���ݴ������ͣ���ѯ��Ӧ�Ĵ���
		if(codeType!=null){
			Vector v=codeMan.queryCodes(codeType);
			request.setAttribute("codeType",codeType);
			request.setAttribute("list",v);
		}
		//ת�����ѯҳ��
		toPage(request,response,"/ToucsMonitor/SystemManage/CodeQuery.jsp?&uid="+login.getUserID());
	}

	//Clean up resources
	public void destroy() {
	}
}