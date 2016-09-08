package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;
import com.adtec.toucs.monitor.systemmanage.CodeBean;
import com.adtec.toucs.monitor.systemmanage.CodeManageBean;

public class POSMoneyManageServlet extends CommonServlet {
	
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	  //交易码：理财POS设备新增
	  private static final String POSINFOREG = "14201";
	  //交易码：理财POS设备修改
	  private static final String POSINFOUPDATE = "14202";
	  //交易码：理财POS设备查询
	  private static final String POSINFOQUERY = "14203";
	  //交易码：理财POS设备删除
	  private static final String POSINFODELETE = "14204";
	  //交易码：理财POS启用停用
	  private static final String POSINFOMNG = "14205";
	  //交易码: 文件上传
	  private static final String UPLOAD = "14206";
	  
	  //目标页面参数代码
	  private static final String PARMGETPAGE = "page";

	  //编号种类参数
	  private static final String DCCTYPE = "3";

	  //理财POS管理页面根路径
	  private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	  //理财POS设备管理类实例
	  private POSMoneyManageBean posManage = new POSMoneyManageBean();
	  //POS商户管理类实例
	  private POSMerchantBean posMerchant = new POSMerchantBean();
	  
	  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //设置缓冲页面不可用
		  response.setHeader("Cache-Control", "no-store");
		  //取请求参数
		  String reqCode = request.getParameter("reqCode");
		  String target = "";
		  target = request.getParameter("target");
		  
		  //用户身份校验
		  LoginInfo login = null;
		  //权限控制
		  try {
			  //校验用户是否登录
			  login = checkLogin(request, response);
			  Debug.println("********CHECK LOGIN********");
			  //校验用户操作权限
			  if (reqCode != null && reqCode.trim().length() > 0) {
				  LoginManageBean.operValidate(login, reqCode);
			  }
		  } catch (MonitorException exp) {
			  errProc(request, response, exp);
			  return;
		  }
		  
		  //目标JSP页面
		  String tarJsp = null;
		  //设备参数管理请求
		  if (reqCode == null) {
			  //要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			  setPagePerm(request, login.getPermission());
			  tarJsp = PAGE_HOME + "PosMoneyUpload.jsp?uid=" + login.getUserID();
			  toPage(request, response, tarJsp);
		  } else {
			  //注册理财POS设备信息
			  if (reqCode.equals(POSINFOREG)) {
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoReg(request, response, false, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoReg(request, response, true, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFOUPDATE)) {//修改理财POS设备信息
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoUpdate(request, response, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoUpdateQuery(request, response, true, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFOQUERY)) {//查询理财POS设备信息
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoQuery(request, response, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoQueryPage(request, response, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFODELETE)) {//删除理财POS设备信息
				  Debug.println("删除理财pos信息...POST请求");
				  if(target == null || target.trim().equals("")){
					  posInfoDelete(request, response, login);
				  }else if(target.equals(PARMGETPAGE)){
					  posInfosDelete(request,response,login);
				  }
			  }else if (reqCode.equals(POSINFOMNG)) {   //理财POS设备管理（启用、停用）
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoManage(request, response, login, "1" );
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoManage(request, response, login, "0" );
					  }
				  }
			  }else if(reqCode.equals(UPLOAD)){
				  upload_pos(request, response, login);
			  }
		  }
	  }


	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		  response.setContentType(CONTENT_TYPE);	    
		  //取请求参数
		  String reqCode = request.getParameter("reqCode");
		  //取请求参数
		  String target = request.getParameter("target"); 
		  //权限控制
		  LoginInfo login = null;
		  try {
			  //校验用户是否登录
			  login = checkLogin(request, response);
			  //校验用户操作权限
			  if (reqCode != null && reqCode.trim().length() > 0) {
				  LoginManageBean.operValidate(login, reqCode);
			  }
		  }catch (MonitorException exp) {
			  errProc(request, response, exp);
			  return;
		  }
		  //注册理财POS设备信息
		  if (reqCode.equals(POSINFOREG)) {
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoReg(request, response, false, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoReg(request, response, true, login);
				  }
			  }
		  }
		  //修改理财POS设备信息
		  if (reqCode.equals(POSINFOUPDATE)) {
			  Debug.println("1");
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoUpdate(request, response, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoUpdateQuery(request, response, true, login);
				  }
			  }
		  }
		  //查询理财POS设备信息
		  if (reqCode.equals(POSINFOQUERY)) {
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoQuery(request, response, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoQueryPage(request, response, login);
				  }
			  }
		  }
		  //删除理财POS设备信息
		  if (reqCode.equals(POSINFODELETE)) {
			  Debug.println("删除理财pos信息...POST请求");
			  if(target == null || target.trim().equals("")){
				  posInfoDelete(request, response, login);
			  }else if(target.equals(PARMGETPAGE)){
				  posInfosDelete(request,response,login);
			  }
		  }
		  //理财POS设备管理（启用、停用）
		  if (reqCode.equals(POSINFOMNG)) {   
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoManage(request, response, login, "1" );
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoManage(request, response, login, "0" );
				  }
			  }
		  }	    
		  //excel文件上传下载
		  if(reqCode.equals(UPLOAD)){
			  upload_pos(request, response, login);
		  }	
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
	   * 初始化商户添加页面所需的列表信息
	   * @param req Http请求
	   * @param orgId 机构编码
	   */
	private void initPosList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean = null;
			//预授权设置
			v = new Vector();
			codeBean = new CodeBean();
			codeBean.setCodeId("0");
			codeBean.setCodeDesc("无预授权类交易");
			v.add(codeBean);
			codeBean = new CodeBean();
			codeBean.setCodeId("1");
			codeBean.setCodeDesc("有预授权类交易");
			v.add(codeBean);
			req.setAttribute("preAuthorizedList", v);      
			//机构列表
			//int[] level={3};
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
		}catch (Exception exp) {
			exp.printStackTrace();
		}finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	    
	  /**
	   * 设置用户对页面的操作权限
	   * @param req Http请求
	   * @param maskCode 用户权限码
	   */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		//增加
		if (LoginManageBean.checkPermMask(maskCode, POSINFOREG) == 0) {
			req.setAttribute("POSINFOREG", "1");
		}
		//修改
		if (LoginManageBean.checkPermMask(maskCode, POSINFOUPDATE) == 0) {
			req.setAttribute("POSINFOUPDATE", "1");
		}
		//删除
		if (LoginManageBean.checkPermMask(maskCode, POSINFODELETE) == 0) {
			req.setAttribute("POSINFODELETE", "1");
		}
		//查询
		if (LoginManageBean.checkPermMask(maskCode, POSINFOQUERY) == 0) {
			req.setAttribute("POSINFOQUERY", "1");
		}
	}

	  
//**********************************************************************************************************************	  
  /**
   * 理财POS设备查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void posInfoQuery(HttpServletRequest request, HttpServletResponse response,LoginInfo login) throws ServletException,IOException {
		//取设备编号
		String pos_code = request.getParameter("pos_code");
		//取查询目的（修改、查询）
		if (pos_code != null && !pos_code.equals("")) {
			try {
				Vector v = posManage.qryPosInfoByPoscode( pos_code, request.getParameter("pos_kind"));
				request.setAttribute("posMoneyList", v);
				toPage(request, response,
						PAGE_HOME + "PosMoneyList.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}else {
			try {
				String merchant_id = request.getParameter("merchant_id");
				if(merchant_id == ""){
					merchant_id = null;
				}
				String branch_id   = request.getParameter("orgId");
				if(branch_id == ""){
					branch_id = null;
				}
				List list = posManage.qryMctPosInfo(merchant_id , branch_id);  
				request.setAttribute("posMoneyList", list);          
				toPage(request, response,PAGE_HOME + "PosMoneyList.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
	  
//**************************************END********************************************************************************	  
	    
//************************* START  跳转查询页面前的操作******************************************************************//
	 
	  /**
	   * 设备信息查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQueryPage(HttpServletRequest request,HttpServletResponse response,LoginInfo login) throws ServletException,IOException {
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosMoneyManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
	}  
//**************************************END**************************************************************************//
	   
//************************ START 添加理财POS设备信息  *****************************************************************//	  
	  /**
	   * 设备信息登记请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoReg(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//设备登记Get请求
		if (isGetPage) {
			POSMoneyManageBean pb = new POSMoneyManageBean();
			POSMoneyInfo posMoney;
			String pos_code = request.getParameter("pos_dcc_code");
			try {
				if (pos_code != null && pos_code.length() > 0) {
					posMoney = pb.qryPosInfo(pos_code, "3");
					if ( posMoney != null ) {
						posMoney.setMct_name();
						request.setAttribute( "posMoney", posMoney);
					}
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosMoneyReg.jsp?post=" + POSINFOREG;
		} else {
			//设备登记Post请求
			POSMoneyInfo posMoney = new POSMoneyInfo();
			//从Http请求中取设备基本信息
			posMoney.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posMoney.getMerchant_id());
				int nReturn = posManage.regPosDevice(posMoney, nPosCount);
				PromptBean prompt = new PromptBean("设备参数管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "理财POS设备[" + posMoney.getPos_code() + "]添加成功");
					//通知客户端增加新设备
					Debug.println("通知客户端增加新理财POS设备");
					notifyClient("N20007", posMoney.getPos_code(), "2");
					//转成功提示页面
					prompt.setPrompt("理财POS设备添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("已登记的POS设备数量等于该商户最大POS设备数量" + "，登记POS设备不成功！");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "理财POS设备[" + posMoney.getPos_code() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}  
//***************************************END*************************************************************************//	  
	  
//******************************START  跳转修改页面前的查询操作********************************************************// 
	  /**
	   * 理财POS设备修改查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdateQuery(HttpServletRequest request,HttpServletResponse response,boolean isGetPage,LoginInfo login) throws ServletException,IOException {
		//取设备编号DCC
		String pos_code = request.getParameter("pos_code");
		System.out.println("pos_code:" + pos_code);
		//取查询内容（基本信息）
		if (isGetPage) {
			if (pos_code != null) {
				String tarJsp = null;
				//查询设备基本信息
				try {
					//查询设备基本信息
					POSMoneyInfo posMoney = posManage.qryPosMoney(pos_code, DCCTYPE);
					if (posMoney == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
					}
					initPosList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosMoneyUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posMoney", posMoney);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//请求到来的最初页面
			initPosList(request, login.getOrgID());
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "PosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
		}
	}  
	  /**
	   * 修改理财POS设备信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdate(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSMoneyInfo info = new POSMoneyInfo();
		//从请求数据中取得要修改的设备基本信息
		info.fetchData(request);
		//获取主键
		String key = request.getParameter("key");
		try {
			//修改设备基本信息
			posManage.updatePosInfo(info, key);
			//记录日志
			writeLog(login, "理财POS[" + key + "]配置信息修改成功");
			//通知客户端修改设备配置信息
			notifyClient("N20007", key, "1");
			//转成功提示页面
			PromptBean prompt = new PromptBean("设备参数管理");
			prompt.setPrompt("理财POS信息修改成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//exp.printStackTrace();
			//记录日志
			writeLog(login, "理财POS[" + key + "]配置信息修改失败");
			errProc(request, response, exp);
		}
	}   
//************************************* END ******************************************************************//	  
	
//************************** START 删除操作 ********************************************************************//
	
	  /**
	   * 理财POS设备删除
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoDelete(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//从请求数据中取得要修改的设备基本信息
		String key = request.getParameter("pos_code");
		try {
			//修改设备基本信息
			posManage.deletePosInfo(key);
			PromptBean prompt = new PromptBean("理财POS设备管理");
			// if (nRst>0){
			{
				//记录日志
				writeLog(login, "理财POS设备[" + key + "]删除成功");
				//通知客户端删除设备 add by liyp 20030918
				notifyClient("N20007", key, "0");
				//转成功提示页面
				prompt.setPrompt("理财POS信息删除成功！");
			}
			//需要校验用户是否有加载权限
			//prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "理财POS设备[" + key + "]删除失败");
			errProc(request, response, exp);
		}
	}
	    
	  /**
	   * 理财POS设备批量删除
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfosDelete(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//从请求数据中取得要修改的设备基本信息
		String[] strArray = request.getParameterValues("box1");
		for(int i=0;i<strArray.length;i++){
			System.out.println("复选框中被选中的数据:"+strArray[i]);
		}
		try {
			//修改设备基本信息  	    
			posManage.deletePosInfos(strArray);
			PromptBean prompt = new PromptBean("理财POS设备管理");
			// if (nRst>0){
			{
				//记录日志
				writeLog(login, "理财POS设备删除成功");
				//通知客户端删除设备 add by liyp 20030918
				//notifyClient("N20007", key, "0");
				//转成功提示页面
				prompt.setPrompt("理财POS信息删除成功！");
			}
			//需要校验用户是否有加载权限
			//prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "理财POS设备删除失败");
			errProc(request, response, exp);
		}	
	}  
//*******************************************END*******************************************************************//
	  
//****************************** START 修改理财POS设备的状态****************************************************************//
	  
	  /**
	   * 理财POS设备状态修改
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoManage(HttpServletRequest request,HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		//从请求数据中取得要修改的设备基本信息
		String key = request.getParameter("pos_code");
		try {
			posManage.managePosInfo(key, use_flag);
			PromptBean prompt = new PromptBean("理财POS设备管理");
			{
				if ( use_flag.equals("0") ) {
					//记录日志
					writeLog(login, "理财POS设备[" + key + "]停用成功");
					//转成功提示页面
					prompt.setPrompt("理财POS设备[" + key + "]停用成功！");
				} else {
					//记录日志
					writeLog(login, "理财POS设备[" + key + "]启用成功");
					//转成功提示页面
					prompt.setPrompt("理财POS设备[" + key + "]启用成功！");
				}
			}
			//需要校验用户是否有加载权限
			//prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "理财POS设备[" + key + "]管理失败");
			errProc(request, response, exp);
		}
	}

//************************************END*********************************************************************************	  
	  
	  
//************************************UPLOAD  START*************************************************************************	  
	  
	  private void upload_pos(HttpServletRequest request,HttpServletResponse response, LoginInfo login){
		  String path =request.getParameter("excel");//excel上传文件所在的路径
		  System.out.println("文件所在的路径为"+path);  
		  List list2 = new ArrayList();
		  PromptBean prompt = new PromptBean("理财POS管理");
		  List list =  posManage.recJXL(path);// 将上传文件中的数据放到集合中
		  int num = 0;
		  System.out.println("集合的大小是:"+list.size());
		  try {	 
			  for(int i=0;i<list.size();i++){//遍历集合
				  POSMoneyInfo info = (POSMoneyInfo)list.get(i);
				  System.out.println("pos_dcc_code = "+info.getPos_dcc_code());
				  POSMoneyInfo info1 =  posManage.qryPosInfo(info.getPos_dcc_code(),"3");//对每条数据进行有效性验证
				  info.setMct_name();//查询商户id所对应的商户名称
				  if(info1 != null){
					  info.setPos_code(info1.getPos_code());
					  info.setPos_c_code(info1.getPos_c_code());
					  info.setMerchant_id(info1.getMerchant_id());
					  posManage.regPosDevice(info, 1);//将完整的数据添加到数据库中				 
				  }else{
					  list2.add(info);//将错误的数据放到集合中
					  num++;//统计错误量
				  }		 
			  } 
			  if(list2.equals(null)){
				  prompt.setPrompt("excel文件操作成功！");
				  request.setAttribute("prompt", prompt);
				  toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			  }else{
				  posManage.createJXL(list2,path);
				  prompt.setPrompt("上传数据中存在"+num+"条错误信息，请在D盘根目录下查看错误信息文件errorInfo.xls");
				  request.setAttribute("prompt", prompt);
				  toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			  }
		  } catch (MonitorException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } catch (ServletException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } catch (IOException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } 	  
	  }
	  
//**************************************************END***********************************************************//	  
	  
	public void init() throws ServletException {
		// Put your code here
	}

}
