package com.adtec.toucs.monitor.common;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.usermanager.*;
import com.adtec.toucs.monitor.comm.*;
/**
 * <p>Title:Servlet基类</p>
 * <p>Description: 提供登录与权限校验、日志记录等功能</p>
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
	//离线校验标志
	protected boolean timeValidate=false;
	//权限校验标志
	protected boolean operValiedate=false;

	public void init() throws ServletException {
	}

  /**
   * 登录校验并取得登录用户信息
   * @param request Http请求
   * @param response Http响应
   * @return 登录用户信息
   * @throws MonitorException
   */
	public LoginInfo getLogin(HttpServletRequest request,HttpServletResponse response) throws MonitorException{
		String sid=null;	
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"用户未登录或会话过期");
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("sid"))
				sid=cookies[i].getValue();
		}
		if(sid==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"用户未登录或会话过期");
		LoginInfo info=LoginManageBean.getUserInfo(sid);
		if(info==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"用户未登录或会话过期");
		return info;
	}

  /**
   * 登录校验
   * @param request Http请求
   * @param response Http响应
   * @return 登录用户信息
   * @throws ServletException
   * @throws IOException
   * @throws MonitorException
   */
	protected LoginInfo checkLogin(HttpServletRequest request,HttpServletResponse response) throws MonitorException{
		String sid=null;
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"用户未登录或会话过期");
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("sid"))
				sid=cookies[i].getValue();
		}
		if(sid==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"用户未登录或会话过期");
		//校验用户是否登录
		int err=LoginManageBean.logInValidate(sid,request.getRemoteAddr());
		if(err!=0)
			throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		//校验用户是否离线
		if(LoginInfo.timeOut>0){
			err=LoginManageBean.offLineValidate(sid);
			if(err!=0)
				throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		}
		LoginManageBean.getUserInfo(sid);    

		//取用户信息
		return LoginManageBean.getUserInfo(sid);
	}

  /**
   * 错误处理
   * @param req Http请求对象
   * @param rsp Http响应对象
   * @param exp 监控系统异常
   * @throws ServletException
   * @throws IOException
   */
	protected void errProc(HttpServletRequest req,HttpServletResponse rsp,MonitorException exp) throws ServletException,IOException{
		String clientType=req.getParameter("clientType");
		if(clientType!=null&&clientType.equals("1")){
			rsp.setContentType(CONTENT_TYPE);
			//应用程序客户端处理
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
   * 页面转向
   * @param request Http请求
   * @param response Http响应
   * @param tarJsp 目标页面URL
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
   * 记录日志
   * @param login 登录用户信息
   * @param desc 日志信息
   */
	protected void writeLog(LoginInfo login,String desc){
		writeLog(login.getUserID(),login.getIP(),desc);
	}

  /**
   * 记录日志
   * @param uid 登录用户ID
   * @param ip 登录用户IP
   * @param desc 日志信息
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
   * 取登录管理类实例（未使用）
   * @return 登录管理类实例
   */
	protected LoginManageBean getLoginManage(){
		LoginManageBean loginManage=(LoginManageBean)getServletContext().getAttribute("loginManage");
		return loginManage;
	}

	//Clean up resources
	public void destroy() {
	}

  /**
   * 通过Socket通知客户端（ATM信息下载、地图下载）
   * @param dealCode 交易代码
   * @param ObjId 对象编号
   * @param operType 操作类型
   */
	protected void notifyClient(String dealCode,String ObjId,String operType){
		if(ObjId==null) return ;
		if(operType==null) return ;
		if(dealCode==null) return ;
		//根据参数构造XML格式的通知信息
		String notifyXmlMsg="<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
		notifyXmlMsg=notifyXmlMsg+"<Deal>\n";
		notifyXmlMsg=notifyXmlMsg+"<DealCode>"+dealCode.trim()+"</DealCode>\n";
		notifyXmlMsg=notifyXmlMsg+"<ErrorCode>0000</ErrorCode>\n";
		notifyXmlMsg=notifyXmlMsg+"<ErrorDescribe>0000</ErrorDescribe>\n";
		notifyXmlMsg=notifyXmlMsg+"<ObjId>"+ObjId.trim()+"</ObjId >\n";
		notifyXmlMsg=notifyXmlMsg+"<OperType>"+operType.trim()+"</OperType>\n";
		notifyXmlMsg=notifyXmlMsg+"</Deal>\n";

		Debug.println("发送通知消息:");
		Debug.println(notifyXmlMsg);

		StringBuffer strb=new StringBuffer(notifyXmlMsg);
		Object obj=getServletContext().getAttribute("WatchServer");
		if(obj==null) return ;

		//通过监听服务将通知信息发送到所有已连接的客户端
		try{
			WatchServer watchServer=(WatchServer)obj;
			watchServer.sendDataToAllClient(strb);
		}catch(Exception exp){ 
			exp.printStackTrace();
		}
	}

  /**
   * 通知ATMP修改了卡表
   * @return  返回报文交易处理信息
   */
	public String notifyATMP(String DealCode,String DeviceNo,String orgID) throws MonitorException{
		if(DealCode==null) DealCode="";
		DealCode=DealCode.trim();
		//如果是卡表刷新请求或响应码加载请求则设备号为000000
		if(DealCode.equals("MG7550") || DealCode.equals("MG7560")|| DealCode.equals("MG7830")){
			DeviceNo="000000000";
		}

		//如果ATM设备号不为空则设置组织Code为空（0000000000000）
		//既设备号与机构号只能一个有效
		if(!DeviceNo.equals("000000000")){
			orgID="000000000";
		}

		CommToATMP commToATMP=new CommToATMP(DealCode,DeviceNo,orgID);
		String retMessage="";
		//通知ATMP刷新卡表
		commToATMP.commitToATMP();

		String dealDesc="";
		if(DealCode.equals("MG7520")){	
			dealDesc="ATM开机";
		} else if(DealCode.equals("MG7530")) {
			dealDesc="ATM关机";
		} else if(DealCode.equals("MG7540")) {
			dealDesc="密钥传输";
		} else if(DealCode.equals("MG7550")) {
			dealDesc="刷新卡表";
		} else if(DealCode.equals("MG7560")) {
			dealDesc="响应码加载";
		}else if(DealCode.equals("MG7570")) {
			dealDesc="柜员签到";
		}else if(DealCode.equals("MG7580")) {
			dealDesc="柜员签退";
		}else if(DealCode.equals("MG7830")) {
			dealDesc="银行信息刷新";
		}
		retMessage="通知ATMP"+dealDesc+",处理结果:";
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