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
/** ATM设备管理Servlet，接受并调用相应方法相应客户端请求
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

	private static String ATMDEVICMANAGE="ATMDEVICMANAGE";  //设备管理提交请求码
	private static String GETATMDEVIC="GETATMDEVIC";       //取得设备请求码
	private static String VALIDATE="VALIDATE";    //验证用户密码请求码
	private static String CONFIRMDEAL="CONFIRMDEAL";  //确认交易处理请求

	private static String DEVICEMANAGEDOGET="12000";  //设备管理请求
	private static String CANCEL="CANCEL";

	private static String ERRORPAGEURL="";
	//业务Bean的实例
	private static ATMDeviceManageBean aTMDeviceManageBean=new ATMDeviceManageBean();
	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.removeAttribute("Message");
		request.removeAttribute("pwd");
		request.removeAttribute("targetURL");

		//如果是生成密钥并打印交易请求，转doPost方法处理
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
		//登陆校验
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
			//登陆校验
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
		Debug.println("交易请求码："+requestCode);
		Debug.println("交易码："+DealCode);

		if(requestCode.equals(VALIDATE)){  //用户校验请求
			//如果开机关机请求则需要校验用户密码
			//密码校验
			//loginManageBean.LogIn()
			//校验不通过，则抛出异常
			String pwd=request.getParameter("pwd");
			LoginManageBean.getUserInfo(uid,pwd);
			//校验通过
			//如果是生成密钥并打印请求则，转到确认页面
			String Message="";
			String Title="";

			String btnTitle0="";
			String btnTitle1="";
			String url0="";
			String url1="";
			boolean isNeedConfirm=false;
			if(DealCode.equals("MG7501")){
				isNeedConfirm=aTMDeviceManageBean.DeviceIsUse(request.getParameter("ATMDevice"));
				if(isNeedConfirm){  //需要确认？转到确认页面
					Debug.println("====================================exec::isNeedConfirm"+isNeedConfirm);
					Message="设备号为："+request.getParameter("ATMDevice")+"的设备在使用中，重新生成密钥将使服务中止。";
					Message=Message+"确认要对该设备发起生成密钥并打印请求吗？";
				}else{
					Message="设备号为："+request.getParameter("ATMDevice")+"的设备还没投入使用。";
					Message=Message+"确认要对该设备发起生成密钥并打印请求吗？";
				}
				request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
				request.setAttribute("DealCode",request.getParameter("DealCode"));
				request.setAttribute("OrgCodeSel",request.getParameter("OrgCodeSel"));
				Title="ATM设备管理:生成密钥并打印请求确认";

				btnTitle0="确定";	
				btnTitle1="取消";
				url0="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+CONFIRMDEAL;
				url1="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+CANCEL;
				PromptBean prompt=new PromptBean(Title);
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,btnTitle0,url0);
				prompt.setButtonUrl(1,btnTitle1,url1);
				request.setAttribute("prompt",prompt);
				lTarget="/DeviceManage/7501Confirm.jsp";
			}else{ //提交请求
				Title="ATM设备管理";
				btnTitle1="确定";
				Message=submitRequestToATMP(request,response,longinfo);
				url1="/ToucsMonitor/ATMDeviceManageServlet?reqCode="+DEVICEMANAGEDOGET;
				PromptBean prompt=new PromptBean(Title);
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,btnTitle1,url1);
				request.setAttribute("prompt",prompt);
				lTarget="/Success.jsp";
			}
		}else if(requestCode.equals(ATMDEVICMANAGE)){   //接受到设备管理提交请求
			if(DealCode==null)DealCode="";
			DealCode=DealCode.trim();
			//要求用户密码确认
			request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
			request.setAttribute("DealCode",request.getParameter("DealCode"));
			request.setAttribute("OrgCodeSel",request.getParameter("OrgCodeSel"));
			lTarget="/DeviceManage/OperConfirm.jsp";
		}else if(requestCode.equals(CONFIRMDEAL)){  //如果是确认提交请求，则提交请求
			Debug.println("如果是确认提交请求，则提交请求\n");
			String Message=submitRequestToATMP(request,response,longinfo);
			PromptBean prompt=new PromptBean("ATM设备管理");
			prompt.setPrompt(Message);
			prompt.setButtonUrl(0,"确定","/ToucsMonitor/ATMDeviceManageServlet?reqCode="+DEVICEMANAGEDOGET);
			request.setAttribute("prompt",prompt);
			lTarget="/Success.jsp";
		} else if(requestCode.equals(GETATMDEVIC) || requestCode.equals(CANCEL) ){  //接受到取设备信息请求
			//查询给定的机构对应的设备集
			String orgID=request.getParameter("OrgCodeSel");;
			Debug.println("查询给定的机构对应的设备集");
			Debug.println(uid);
			LoginInfo logInfo=this.checkLogin(request,response);
			List ATMList=null;
			if(logInfo!=null){
				ATMList=aTMDeviceManageBean.queryATMList(orgID) ;
				request.setAttribute("ATMList",ATMList);
			}else{
				throw new MonitorException(ErrorDefine.GETUSERINFOERROR,ErrorDefine.GETUSERINFOERRORDESC);
			}
			//取得选择的组织CODE
			String selOrgCode=request.getParameter("OrgCodeSel");
			//判断session中机构集是否还存在，如果不存在则重新查询
			if(request.getSession().getAttribute("OrgList")==null){
				Debug.println("request.getSession().getAttribute(OrgList)==null");
				List orgList=CodeManageBean.queryOrgList(logInfo.getOrgID()) ;
				request.getSession().removeAttribute("OrgList");
				request.getSession().setAttribute("OrgList",orgList);
			}
			Debug.println("selOrgCode==========================="+selOrgCode);
			request.setAttribute("OrgCodeSel",selOrgCode);
			setRes(longinfo.getPermission(),request);

			//设置用户选择的交易请求交易码
			request.setAttribute("ATMDevice",request.getParameter("ATMDevice"));
			request.setAttribute("DealCode",request.getParameter("DealCode"));
			//如果是卡表刷新请求，转到cardRefresh.jsp页面
			if(DealCode.equals("MG7550"))
				lTarget="/ParameterSet//cardRefresh.jsp";
			else
				lTarget="/DeviceManage/ATMManager.jsp";
			Debug.println("查询给定的机构对应的设备集 end");
		} else if(requestCode.equals("TEST")){
			lTarget="";
			request.setAttribute("Message","请选择请求操作类型！");
		}
		return lTarget;
	}

	//Clean up resources
	public void destroy() {
	}

  /**
   * 设置用户权限资源
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
   * 提交请求到到ATMP
   * @param request
   * @param response
   * @return
   * @throws MonitorException
   */
	private String submitReqToATMP(HttpServletRequest request,HttpServletResponse response,LoginInfo logInfo) throws MonitorException{
		//权限校验
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

		//如果ATM设备号不为空则设置组织Code为空（0000000000000）
		//既设备号与机构号只能一个有效
		//需要校验输入的设备号是否为权限范围内的设备号
		if(!ATMDeviceNO.equals("000000000")){
			orgCode="000000000";
			checkDevicIsValidate(request,response,ATMDeviceNO);
		}
		DealCode=DealCode.trim();
		//如果提交的请求交易码是响应码加载请求，则设备号为000000，机构号为用户所属机构
		if(DealCode.equals("MG7560")){
			ATMDeviceNO="000000000";
			orgCode=logInfo.getOrgID();
		}
		String retMessage="";
		retMessage=this.notifyATMP(DealCode,ATMDeviceNO,orgCode);
		//写日志
		String dealDesc="";
		if(DealCode.equals("MG7520")){
			dealDesc="ATM开机";
		}else if(DealCode.equals("MG7530")){
			dealDesc="ATM关机";
		}else if(DealCode.equals("MG7540")){
			dealDesc="密钥传输";	
		}else if(DealCode.equals("MG7550")){
			dealDesc="刷新卡表";
		}else if(DealCode.equals("MG7560")){
			dealDesc="响应码加载";
		}else if(DealCode.equals("MG7570")){
			dealDesc="柜员签到";
		}else if(DealCode.equals("MG7580")){
			dealDesc="柜员签退";
		}
		dealDesc="用户向ATMP提交了"+dealDesc+"请求,请求内容包括；交易码："+DealCode+"，机构："+orgCode+"，设备编号："+ATMDeviceNO;
		dealDesc=dealDesc+",ATMP返回的处理结果信息是："+retMessage;
		String uid,ip;
		uid=this.getLogin(request,response).getUserID();
		ip=request.getRemoteAddr();
		this.writeLog(uid,ip,dealDesc);

		return retMessage;
	}
  /**
   *如果提交请求时候抛出异常，则转到错误叶面，设置错误页面的确定按钮URL
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
   * 权限校验
   * @param request
   * @param response
   * @throws MonitorException
   */
	private LoginInfo commProcess(HttpServletRequest request, HttpServletResponse response) throws MonitorException{
		//登陆校验
		LoginInfo loginfo=checkLogin(request,response);
		//取到用户名
		loginfo.getUserID();

		//取得用户权限码
		loginfo.getPermission();
		
		//权限校验,如果是请求代码为：DEVICEMANAGEDOGET，则不校验
		String reqCode=request.getParameter("reqCode");
		if(reqCode==null)reqCode="";
		reqCode=reqCode.trim();
		if(reqCode.equals(CANCEL))  reqCode=DEVICEMANAGEDOGET;
		if(!reqCode.equals(DEVICEMANAGEDOGET)){  //如果不是DEVICEMANAGEDOGET请求，则校验交易码
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
   * 校验用户是否有给定的设备编号的操作权限
   * @param request
   * @param ATMDeviceNO  设备编号
   * @throws MonitorException
   */
	private void checkDevicIsValidate(HttpServletRequest request,HttpServletResponse response,String ATMDeviceNO)throws MonitorException{
		Object obj=request.getSession().getAttribute("OrgList");
		String org_id=null;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			if(obj==null){
				//根据uid取得组织机构ID
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