package com.adtec.toucs.monitor.common;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.usermanager.*;
import com.adtec.toucs.monitor.comm.*;
/**
 * <p>Title:Servlet����</p>
 * <p>Description: �ṩ��¼��Ȩ��У�顢��־��¼�ȹ���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CommonServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	//����У���־
	protected boolean timeValidate=false;
	//Ȩ��У���־
	protected boolean operValiedate=false;

	public void init() throws ServletException {
	}

  /**
   * ��¼У�鲢ȡ�õ�¼�û���Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @return ��¼�û���Ϣ
   * @throws MonitorException
   */
	public LoginInfo getLogin(HttpServletRequest request,HttpServletResponse response) throws MonitorException{
		String sid=null;	
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("sid"))
				sid=cookies[i].getValue();
		}
		if(sid==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		LoginInfo info=LoginManageBean.getUserInfo(sid);
		if(info==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		return info;
	}

  /**
   * ��¼У��
   * @param request Http����
   * @param response Http��Ӧ
   * @return ��¼�û���Ϣ
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	protected LoginInfo checkLogin(HttpServletRequest request,HttpServletResponse response) throws MonitorException{
		String sid=null;
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("sid"))
				sid=cookies[i].getValue();
		}
		if(sid==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		//У���û��Ƿ��¼
		int err=LoginManageBean.logInValidate(sid,request.getRemoteAddr());
		if(err!=0)
			throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		//У���û��Ƿ�����
		if(LoginInfo.timeOut>0){
			err=LoginManageBean.offLineValidate(sid);
			if(err!=0)
				throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		}
		LoginManageBean.getUserInfo(sid);    

		//ȡ�û���Ϣ
		return LoginManageBean.getUserInfo(sid);
	}

  /**
   * ������
   * @param req Http�������
   * @param rsp Http��Ӧ����
   * @param exp ���ϵͳ�쳣
   * @throws ServletException
   * @throws IOException
   */
	protected void errProc(HttpServletRequest req,HttpServletResponse rsp,MonitorException exp) throws ServletException,IOException{
		String clientType=req.getParameter("clientType");
		if(clientType!=null&&clientType.equals("1")){
			rsp.setContentType(CONTENT_TYPE);
			//Ӧ�ó���ͻ��˴���
			PrintWriter p=rsp.getWriter();
			p.println("<Deal>");
			p.println("<DealCode>"+req.getParameter("reqCode")+"</DealCode>");
			p.println("<ErrCode>"+exp.getErrCode()+"</ErrCode>");
			p.println("<ErrDesc>"+exp.getMessage()+"</ErrDesc>");
			p.println("</Deal>");
			p.flush();
		}else{
			req.setAttribute("javax.servlet.jsp.jspException",exp);
			RequestDispatcher dp=req.getRequestDispatcher("/ToucsMonitor/Error.jsp");
			dp.forward(req,rsp);
		}
	}
  /**
   * ҳ��ת��
   * @param request Http����
   * @param response Http��Ӧ
   * @param tarJsp Ŀ��ҳ��URL
   * @throws ServletException
   * @throws IOException
   */
	protected void toPage(HttpServletRequest request,HttpServletResponse response,String tarJsp) throws ServletException, IOException{
		if(tarJsp!=null){
			RequestDispatcher dp=request.getRequestDispatcher(tarJsp);
			dp.forward(request,response);
		}
	}

  /**
   * ��¼��־
   * @param login ��¼�û���Ϣ
   * @param desc ��־��Ϣ
   */
	protected void writeLog(LoginInfo login,String desc){
		writeLog(login.getUserID(),login.getIP(),desc);
	}

  /**
   * ��¼��־
   * @param uid ��¼�û�ID
   * @param ip ��¼�û�IP
   * @param desc ��־��Ϣ
   */
	protected void writeLog(String uid,String ip,String desc){
		LogManagerBean logMan=new LogManagerBean();
		try{
			logMan.log(uid,ip,desc);
		}catch(MonitorException exp){
			exp.printStackTrace();
		}
	}

  /**
   * ȡ��¼������ʵ����δʹ�ã�
   * @return ��¼������ʵ��
   */
	protected LoginManageBean getLoginManage(){
		LoginManageBean loginManage=(LoginManageBean)getServletContext().getAttribute("loginManage");
		return loginManage;
	}

	//Clean up resources
	public void destroy() {
	}

  /**
   * ͨ��Socket֪ͨ�ͻ��ˣ�ATM��Ϣ���ء���ͼ���أ�
   * @param dealCode ���״���
   * @param ObjId ������
   * @param operType ��������
   */
	protected void notifyClient(String dealCode,String ObjId,String operType){
		if(ObjId==null) return ;
		if(operType==null) return ;
		if(dealCode==null) return ;
		//���ݲ�������XML��ʽ��֪ͨ��Ϣ
		String notifyXmlMsg="<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
		notifyXmlMsg=notifyXmlMsg+"<Deal>\n";
		notifyXmlMsg=notifyXmlMsg+"<DealCode>"+dealCode.trim()+"</DealCode>\n";
		notifyXmlMsg=notifyXmlMsg+"<ErrorCode>0000</ErrorCode>\n";
		notifyXmlMsg=notifyXmlMsg+"<ErrorDescribe>0000</ErrorDescribe>\n";
		notifyXmlMsg=notifyXmlMsg+"<ObjId>"+ObjId.trim()+"</ObjId >\n";
		notifyXmlMsg=notifyXmlMsg+"<OperType>"+operType.trim()+"</OperType>\n";
		notifyXmlMsg=notifyXmlMsg+"</Deal>\n";

		Debug.println("����֪ͨ��Ϣ:");
		Debug.println(notifyXmlMsg);

		StringBuffer strb=new StringBuffer(notifyXmlMsg);
		Object obj=getServletContext().getAttribute("WatchServer");
		if(obj==null) return ;

		//ͨ����������֪ͨ��Ϣ���͵����������ӵĿͻ���
		try{
			WatchServer watchServer=(WatchServer)obj;
			watchServer.sendDataToAllClient(strb);
		}catch(Exception exp){ 
			exp.printStackTrace();
		}
	}

  /**
   * ֪ͨATMP�޸��˿���
   * @return  ���ر��Ľ��״�����Ϣ
   */
	public String notifyATMP(String DealCode,String DeviceNo,String orgID) throws MonitorException{
		if(DealCode==null) DealCode="";
		DealCode=DealCode.trim();
		//����ǿ���ˢ���������Ӧ������������豸��Ϊ000000
		if(DealCode.equals("MG7550") || DealCode.equals("MG7560")|| DealCode.equals("MG7830")){
			DeviceNo="000000000";
		}

		//���ATM�豸�Ų�Ϊ����������֯CodeΪ�գ�0000000000000��
		//���豸���������ֻ��һ����Ч
		if(!DeviceNo.equals("000000000")){
			orgID="000000000";
		}

		CommToATMP commToATMP=new CommToATMP(DealCode,DeviceNo,orgID);
		String retMessage="";
		//֪ͨATMPˢ�¿���
		commToATMP.commitToATMP();

		String dealDesc="";
		if(DealCode.equals("MG7520")){	
			dealDesc="ATM����";
		} else if(DealCode.equals("MG7530")) {
			dealDesc="ATM�ػ�";
		} else if(DealCode.equals("MG7540")) {
			dealDesc="��Կ����";
		} else if(DealCode.equals("MG7550")) {
			dealDesc="ˢ�¿���";
		} else if(DealCode.equals("MG7560")) {
			dealDesc="��Ӧ�����";
		}else if(DealCode.equals("MG7570")) {
			dealDesc="��Աǩ��";
		}else if(DealCode.equals("MG7580")) {
			dealDesc="��Աǩ��";
		}else if(DealCode.equals("MG7830")) {
			dealDesc="������Ϣˢ��";
		}
		retMessage="֪ͨATMP"+dealDesc+",������:";
		retMessage=retMessage+commToATMP.getErrorDesc();
		return retMessage;
	}

	protected void writeLog(LoginInfo login,String DeviceCode,String ResourceId,String Desc)throws MonitorException{
		LogManagerBean lb = new LogManagerBean();
		try {
			lb.log(login,ResourceId,DeviceCode,Desc);
		} catch (MonitorException ex) {
			throw ex;
		}
	}
}