package com.adtec.toucs.monitor.devicemanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.usermanager.*;
import com.adtec.toucs.monitor.systemmanage.*;
import java.sql.*;
//import com.adtec.toucs.monitor.security.*;
/** ATM�豸����Servlet�����ܲ�������Ӧ������Ӧ�ͻ�������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */

public class ATMDeviceManageServlet extends CommonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String ATMDEVICMANAGE="ATMDEVICMANAGE";  //�豸�����ύ������
	private static String GETATMDEVIC="GETATMDEVIC";       //ȡ���豸������
	private static String VALIDATE="VALIDATE";    //��֤�û�����������
	private static String CONFIRMDEAL="CONFIRMDEAL";  //ȷ�Ͻ��״�������

	private static String DEVICEMANAGEDOGET="12000";  //�豸��������
	private static String CANCEL="CANCEL";

	private static String ERRORPAGEURL="";
	//ҵ��Bean��ʵ��
	private static ATMDeviceManageBean aTMDeviceManageBean=new ATMDeviceManageBean();
	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.removeAttribute("Message");
		request.removeAttribute("pwd");
		request.removeAttribute("targetURL");

		//�����������Կ����ӡ��������תdoPost��������
		String DealCode=request.getParameter("DealCode");
		if(DealCode==null) DealCode="";
		DealCode=DealCode.trim();
		Debug.println("doGet:");
		Debug.println("DealCode:"+DealCode);
		Debug.println("reqCode:"+request.getParameter("reqCode"));

		try{
			Debug.println("begin to acceot doget method");
			String lTarget=execDoGet(request,response);
			request.setAttribute("uid",request.getParameter("uid"));
			Debug.println("lTarget==========="+lTarget);
			LoginInfo longinfo=checkLogin(request,response);
			setRes(longinfo.getPermission(),request);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher(lTarget);
			requestDispatcher.forward(request,response);
		}catch(MonitorException exp){
			exp.printStackTrace();
			this.errProc(request,response,exp);
		}
	}
	public String execDoGet(HttpServletRequest request, HttpServletResponse response) throws MonitorException{
		String lTarget=ERRORPAGEURL;
		String requestCode=request.getParameter("reqCode");
		Debug.println("ATMDeviceManageServlet:requestCode doget========================:"+requestCode);
		if(requestCode==null)requestCode="";
		//��½У��
		Debug.println("befor checkLogin");
		LoginInfo logInfo=checkLogin(request,response);
    	Debug.println("after checkLogin");
    	requestCode=requestCode.trim();
    	Debug.println("requestCode=="+requestCode);
    	if(requestCode.equals(DEVICEMANAGEDOGET)){
    		Debug.println("beagin process");
    		List orgList=null;
    		if(logInfo!=null){
    			Debug.println("begin query");
    			orgList=CodeManageBean.queryOrgList(logInfo.getOrgID()) ;
    			request.getSession().setAttribute("OrgList",orgList);
    			Debug.println("end query");
    		}else{
    			throw new MonitorException(ErrorDefine.GETUSERINFOERROR,ErrorDefine.GETUSERINFOERRORDESC);
    		}
    		lTarget="/DeviceManage/ATMManager.jsp";
    	}else if(requestCode.equals("MSGPARSE")){
    		TestParse();
    		lTarget="/DeviceManage/ATMManagerRst.jsp";
    	}else if(requestCode.equals("KEYTEST")){
          //testKey();
    		userManagerBean um=new userManagerBean();
    		try{
    			um.queryUserInfo("lichj","cb001");
    		}catch(Exception exp){   
    			exp.printStackTrace();
    		}
    		lTarget="/DeviceManage/ATMManagerRst.jsp";
    	}
    	return lTarget;
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lTarget="";
		request.removeAttribute("Message");
		request.removeAttribute("pwd");
		request.removeAttribute("targetURL");
		try{
			//��½У��
			checkLogin(request,response);
			lTarget=exec(request,response);
		}catch(MonitorException  exp){
			this.errProc(request,response,exp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Debug.println("lTarget==========="+lTarget);
		if(lTarget.trim().length()!=0){
			RequestDispatcher requestDispatcher=request.getRequestDispatcher(lTarget);
			requestDispatcher.forward(request,response);
		}
	}

	private String exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,MonitorException, ParseException{
		String lTarget="ERRORPAGEURL";
		String requestCode=request.getParameter("reqCode");
		String DealCode=request.getParameter("DealCode");

		Debug.println("requestCode===========dopost:"+requestCode);
		if(requestCode==null || requestCode.trim().length()==0) return lTarget;
		if(DealCode==null) DealCode="";
		DealCode=DealCode.trim();
		requestCode=requestCode.trim();
		LoginInfo longinfo=checkLogin(request,response);
		String uid=longinfo.getUserID();
		Debug.println("���������룺"+requestCode);
		Debug.println("�����룺"+DealCode);

		if(requestCode.equals(VALIDATE)){  //�û�У������
			//��������ػ���������ҪУ���û�����
			//����У��
			//loginManageBean.LogIn()
			//У�鲻ͨ�������׳��쳣
			String pwd=request.getParameter("pwd");
			LoginManageBean.getUserInfo(uid,pwd);
			//У��ͨ��
			//�����������Կ����ӡ������ת��ȷ��ҳ��
			String Message="";
			String Title="";

			String btnTitle0="";
			String btnTitle1="";
			String url0="";
			String url1="";
			boolean isNeedConfirm=false;
			if(DealCode.equals("MG7501")){
				isNeedConfirm=aTMDeviceManageBean.DeviceIsUse(request.getParameter("ATMDevice"));
				if(isNeedConfirm){  //��Ҫȷ�ϣ�ת��ȷ��ҳ��
					Debug.println("====================================exec::isNeedConfirm"+isNeedConfirm);
					Message="�豸��Ϊ��"+request.getParameter("ATMDevice")+"���豸��ʹ���У�����������Կ��ʹ������ֹ��";
					Message=Message+"ȷ��Ҫ�Ը��豸����������Կ����ӡ������";
				}else{
					Message="�豸��Ϊ��"+request.getParameter("ATMDevice")+"���豸��ûͶ��ʹ�á�";
					Message=Message+"ȷ��Ҫ�Ը��豸����������Կ����ӡ������";
				}
				request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
				request.setAttribute("DealCode",request.getParameter("DealCode"));
				request.setAttribute("OrgCodeSel",request.getParameter("OrgCodeSel"));
				Title="ATM�豸����:������Կ����ӡ����ȷ��";

				btnTitle0="ȷ��";	
				btnTitle1="ȡ��";
				url0="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+CONFIRMDEAL;
				url1="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+CANCEL;
				PromptBean prompt=new PromptBean(Title);
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,btnTitle0,url0);
				prompt.setButtonUrl(1,btnTitle1,url1);
				request.setAttribute("prompt",prompt);
				lTarget="/DeviceManage/7501Confirm.jsp";
			}else{ //�ύ����
				Title="ATM�豸����";
				btnTitle1="ȷ��";
				Message=submitRequestToATMP(request,response,longinfo);
				url1="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+DEVICEMANAGEDOGET;
				PromptBean prompt=new PromptBean(Title);
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,btnTitle1,url1);
				request.setAttribute("prompt",prompt);
				lTarget="/Success.jsp";
			}
		}else if(requestCode.equals(ATMDEVICMANAGE)){   //���ܵ��豸�����ύ����
			if(DealCode==null)DealCode="";
			DealCode=DealCode.trim();
			//Ҫ���û�����ȷ��
			request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
			request.setAttribute("DealCode",request.getParameter("DealCode"));
			request.setAttribute("OrgCodeSel",request.getParameter("OrgCodeSel"));
			lTarget="/DeviceManage/OperConfirm.jsp";
		}else if(requestCode.equals(CONFIRMDEAL)){  //�����ȷ���ύ�������ύ����
			Debug.println("�����ȷ���ύ�������ύ����\n");
			String Message=submitRequestToATMP(request,response,longinfo);
			PromptBean prompt=new PromptBean("ATM�豸����");
			prompt.setPrompt(Message);
			prompt.setButtonUrl(0,"ȷ��","/ToucsMonitor/ATMDeviceManageServlet?reqCode="+DEVICEMANAGEDOGET);
			request.setAttribute("prompt",prompt);
			lTarget="/Success.jsp";
		} else if(requestCode.equals(GETATMDEVIC) || requestCode.equals(CANCEL) ){  //���ܵ�ȡ�豸��Ϣ����
			//��ѯ�����Ļ�����Ӧ���豸��
			String orgID=request.getParameter("OrgCodeSel");;
			Debug.println("��ѯ�����Ļ�����Ӧ���豸��");
			Debug.println(uid);
			LoginInfo logInfo=this.checkLogin(request,response);
			List ATMList=null;
			if(logInfo!=null){
				ATMList=aTMDeviceManageBean.queryATMList(orgID) ;
				request.setAttribute("ATMList",ATMList);
			}else{
				throw new MonitorException(ErrorDefine.GETUSERINFOERROR,ErrorDefine.GETUSERINFOERRORDESC);
			}
			//ȡ��ѡ�����֯CODE
			String selOrgCode=request.getParameter("OrgCodeSel");
			//�ж�session�л������Ƿ񻹴��ڣ���������������²�ѯ
			if(request.getSession().getAttribute("OrgList")==null){
				Debug.println("request.getSession().getAttribute(OrgList)==null");
				List orgList=CodeManageBean.queryOrgList(logInfo.getOrgID()) ;
				request.getSession().removeAttribute("OrgList");
				request.getSession().setAttribute("OrgList",orgList);
			}
			Debug.println("selOrgCode==========================="+selOrgCode);
			request.setAttribute("OrgCodeSel",selOrgCode);
			setRes(longinfo.getPermission(),request);

			//�����û�ѡ��Ľ�����������
			request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
			request.setAttribute("DealCode",request.getParameter("DealCode"));
			//����ǿ���ˢ������ת��cardRefresh.jspҳ��
			if(DealCode.equals("MG7550"))
				lTarget="/ParameterSet//cardRefresh.jsp";
			else
				lTarget="/DeviceManage/ATMManager.jsp";
			Debug.println("��ѯ�����Ļ�����Ӧ���豸�� end");
		} else if(requestCode.equals("TEST")){
			lTarget="";
			request.setAttribute("Message","��ѡ������������ͣ�");
		}
		return lTarget;
	}

	//Clean up resources
	public void destroy() {
	}

  /**
   * �����û�Ȩ����Դ
   * @param uid
   * @param request
   */
	private void setRes(String maskCode,HttpServletRequest request){
		int opV=LoginManageBean.checkPermMask(maskCode,"12001");
		if(opV==0) request.setAttribute("MG7520","ok");
		else request.setAttribute("MG7520","style='display:none'");
		opV=LoginManageBean.checkPermMask(maskCode,"12002");
		if(opV==0) request.setAttribute("MG7530","ok");
		else request.setAttribute("MG7530","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12004");
		if(opV==0) request.setAttribute("MG7540","ok");
		else request.setAttribute("MG7540","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12005");
		if(opV==0) request.setAttribute("MG7550","ok");
		else request.setAttribute("MG7550","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12006");
		if(opV==0) request.setAttribute("MG7560","ok");
		else request.setAttribute("MG7560","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12007");
		if(opV==0) request.setAttribute("MG7570","ok");
		else request.setAttribute("MG7570","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12008");
		if(opV==0) request.setAttribute("MG7580","ok");
		else request.setAttribute("MG7580","style='display:none'");

		opV=LoginManageBean.checkPermMask(maskCode,"12010");
		if(opV==0) request.setAttribute("MG7501","ok");
    	else request.setAttribute("MG7501","style='display:none'");
	}

  /**
   * �ύ���󵽵�ATMP
   * @param request
   * @param response
   * @return
   * @throws MonitorException
   */
	private String submitReqToATMP(HttpServletRequest request,HttpServletResponse response,LoginInfo logInfo) throws MonitorException{
		//Ȩ��У��
		commProcess(request,response);
		String ATMDeviceNO;
		String DealCode;
		String orgCode;
		ATMDeviceNO=request.getParameter("ATMDevice");
		DealCode=request.getParameter("DealCode");
		orgCode=request.getParameter("OrgCodeSel");

		if(ATMDeviceNO==null || ATMDeviceNO.length()==0) ATMDeviceNO="000000000";
		else ATMDeviceNO=ATMDeviceNO.trim();

		if(orgCode==null || orgCode.trim().length()==0) orgCode="000000000";
		else orgCode=orgCode.trim();

		//���ATM�豸�Ų�Ϊ����������֯CodeΪ�գ�0000000000000��
		//���豸���������ֻ��һ����Ч
		//��ҪУ��������豸���Ƿ�ΪȨ�޷�Χ�ڵ��豸��
		if(!ATMDeviceNO.equals("000000000")){
			orgCode="000000000";
			checkDevicIsValidate(request,response,ATMDeviceNO);
		}
		DealCode=DealCode.trim();
		//����ύ��������������Ӧ������������豸��Ϊ000000��������Ϊ�û���������
		if(DealCode.equals("MG7560")){
			ATMDeviceNO="000000000";
			orgCode=logInfo.getOrgID();
		}
		String retMessage="";
		retMessage=this.notifyATMP(DealCode,ATMDeviceNO,orgCode);
		//д��־
		String dealDesc="";
		if(DealCode.equals("MG7520")){
			dealDesc="ATM����";
		}else if(DealCode.equals("MG7530")){
			dealDesc="ATM�ػ�";
		}else if(DealCode.equals("MG7540")){
			dealDesc="��Կ����";	
		}else if(DealCode.equals("MG7550")){
			dealDesc="ˢ�¿���";
		}else if(DealCode.equals("MG7560")){
			dealDesc="��Ӧ�����";
		}else if(DealCode.equals("MG7570")){
			dealDesc="��Աǩ��";
		}else if(DealCode.equals("MG7580")){
			dealDesc="��Աǩ��";
		}
		dealDesc="�û���ATMP�ύ��"+dealDesc+"����,�������ݰ����������룺"+DealCode+"��������"+orgCode+"���豸��ţ�"+ATMDeviceNO;
		dealDesc=dealDesc+",ATMP���صĴ�������Ϣ�ǣ�"+retMessage;
		String uid,ip;
		uid=this.getLogin(request,response).getUserID();
		ip=request.getRemoteAddr();
		this.writeLog(uid,ip,dealDesc);

		return retMessage;
	}
  /**
   *����ύ����ʱ���׳��쳣����ת������Ҷ�棬���ô���ҳ���ȷ����ťURL
   * @param request
   * @param response
   * @throws MonitorException
   */
	private String submitRequestToATMP(HttpServletRequest request,HttpServletResponse response,LoginInfo longinfo) throws MonitorException{
		try{
			return submitReqToATMP(request,response,longinfo);
		}catch(MonitorException exp){
			String url="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+DEVICEMANAGEDOGET;
			request.setAttribute("targetURL",url);
			throw exp;
		}
	}

	private void TestParse(){
		String message="";
		message="<Deal><DealCode>"+"Test";
		message=message+"</DealCode><DeviceNO>"+"Test";
		message=message+ "</DeviceNO><OrgId>"+"Test";
		message=message+ "</OrgId></Deal>\n";
		CommToATMP commToATMP=new CommToATMP("","","");
		try{
			commToATMP.commitToATMP();
		}catch(Exception exp){}
	}

  /**
   * Ȩ��У��
   * @param request
   * @param response
   * @throws MonitorException
   */
	private LoginInfo commProcess(HttpServletRequest request, HttpServletResponse response) throws MonitorException{
		//��½У��
		LoginInfo loginfo=checkLogin(request,response);
		//ȡ���û���
		loginfo.getUserID();

		//ȡ���û�Ȩ����
		loginfo.getPermission();
		
		//Ȩ��У��,������������Ϊ��DEVICEMANAGEDOGET����У��
		String reqCode=request.getParameter("reqCode");
		if(reqCode==null)reqCode="";
		reqCode=reqCode.trim();
		if(reqCode.equals(CANCEL))  reqCode=DEVICEMANAGEDOGET;
		if(!reqCode.equals(DEVICEMANAGEDOGET)){  //�������DEVICEMANAGEDOGET������У�齻����
			String dealCode=request.getParameter("DealCode");
			if(dealCode==null)dealCode="";
			dealCode=dealCode.trim();
			Debug.println("commProcess----reqCode:"+reqCode);
			Debug.println("commProcess----dealCode:"+dealCode);
			if(dealCode.equals("MG7520")){
				reqCode="12001";
			}else if(dealCode.equals("MG7530")){
				reqCode="12002";
			}else if(dealCode.equals("MG7540")){
				reqCode="12004";
			}else if(dealCode.equals("MG7550")){
				reqCode="12005";
			}else if(dealCode.equals("MG7560")){
				reqCode="12006";
			}else if(dealCode.equals("MG7570")){
				reqCode="12007";
			}else if(dealCode.equals("MG7580")){
				reqCode="12008";
			}else if(dealCode.equals("MG7501")){
				reqCode="12010";
			}
			LoginManageBean.operValidate(loginfo,reqCode);
		}
		return loginfo;
	}
  /**
   * У���û��Ƿ��и������豸��ŵĲ���Ȩ��
   * @param request
   * @param ATMDeviceNO  �豸���
   * @throws MonitorException
   */
	private void checkDevicIsValidate(HttpServletRequest request,HttpServletResponse response,String ATMDeviceNO)throws MonitorException{
		Object obj=request.getSession().getAttribute("OrgList");
		String org_id=null;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			if(obj==null){
				//����uidȡ����֯����ID
				LoginInfo loginfo=this.getLogin(request,response);
				String uid=loginfo.getUserID();
				LoginInfo logInfo=LoginManageBean.getUserInfo(uid);
				org_id=logInfo.getOrgID();
				aTMDeviceManageBean.deviceInOrg(ATMDeviceNO,org_id,sq);
			} else {
				List orgList=(List)obj;
				aTMDeviceManageBean.deviceInOrgList(orgList,ATMDeviceNO,conn);
			}
		}catch(MonitorException exp){
			throw exp;
		}finally{
			sq.close();
		}
	}
}