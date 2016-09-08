package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title:POS商户管理Servlet类 </p>
 * <p>Description: 处理POS商户管理相关的Http请求</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */

public class POSMerchantServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	
	/**
	 *交易码参数
	 */
	//交易码：获取商户新增
	private static final String POSMERCHANTADD = "10401";
	
	//交易码：商户修改
	private static final String POSMERCHANTUPDATE = "10402";
	
	//交易码：商户查询
	private static final String POSMERCHANTQUERY = "10404";
	
	//交易码：商户删除
	private static final String POSMERCHANTDELETE = "10403";
	
	//交易码：抽奖
	private static final String CheckPrize = "10422";
	
	//其他参数代码
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";
	private static final String PARMCLEARFLAG = "clear";
	private static final String PARMNORMFLAG = "normal";
	private static final String CheckPZ = "prize";
	
	//抽奖规则查询
	private static final String PRIZERULEQUERY = "RuleQry";
	
	/**
	 *其他参数
	 */
	//POS商户管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/posmerchantconfig";
	
	//POS管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
	
	//POS商户管理类实例
	private POSMerchantBean posMerchant = new POSMerchantBean();
	
	public void init() throws ServletException {
	}
	
	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		response.setHeader("Cache-Control", "no-store");
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
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//取session信息
		HttpSession session = request.getSession(true);
		//目标JSP页面
		String tarJsp = null;
		//商户管理请求
		if (reqCode == null) {
			POSMerchantInfo Info = (POSMerchantInfo) session.getAttribute(
			"posMerchant");
			if (Info != null) {
				try {
					List l = posMerchant.getMerchantByOrg(Info.getOrgid());
					request.setAttribute("posMerchantList", l);
				}catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			} else {
				Debug.println("**********posMerchantInfoList get Null***************");
			}
			//要根据当前用户所拥有商户管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosMerchantManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//显示抽奖页面
			if (reqCode.equals(CheckPrize)) {
				if ( (target == null) || target.trim().equals("")) {
					if (LoginManageBean.checkPermMask(login.getPermission(), CheckPrize) == 0) {
						request.setAttribute("CheckPrize", "1");
					}
					tarJsp = PAGE_HOME + "CheckPrizeMain.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
				if (target != null && target.equals("checkPZAdded")) {
					Debug.println("Ok man");
					CheckPrizeAdd(request, response, false, login);
				}
				if (target != null && target.equals(CheckPZ)) {
					Debug.println("good Man");
					CheckPrizeAdd(request, response, true, login);
				}
				if (target != null && target.equals("prizeQuery")) {
					Debug.println("good Man 1028");
					setCheckPZPerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "QueryCheckPrizeInput.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
				//抽奖规则查询
				if (target != null && target.equals(PRIZERULEQUERY)) {
					String channelId = "010310";
					PrizeRule pz = null;
					Vector pi = null;
					try {
						pi = posMerchant.queryPrizeInfo(channelId);
						pz = posMerchant.queryPrizeRule(channelId);
					}catch (MonitorException ex) {
						ex.printStackTrace();
						System.out.println(ex.getMessage());
					}
					request.setAttribute("PrizeInfo", pi);
					request.setAttribute("PrizeRule", pz);
					
					tarJsp = PAGE_HOME + "PrizeRuleInfo.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
			}else if (reqCode.equals(POSMERCHANTADD)) {//获取添加商户页面
				if ( (target == null) || target.trim().equals("")) {
					posMerchantAdd(request, response, false, login);
				}
				if (target != null && target.equals(PARMGETPAGE)) {
					posMerchantAdd(request, response, true, login);
				}
			}else if (reqCode.equals(POSMERCHANTUPDATE)) {	//获取修改商户页面
				if ( (target == null) || target.trim().equals("")) {
					posMerchantUpdate(request, response, login);
				}else {
					if (target.equals(PARMGETPAGE)) {
						posMerchantUpdateQuery(request, response, true, login);
					}
					if (target.equals(PARMGETQUERYPAGE)) {
						posMerchantUpdateQuery(request, response, false, login);
					}
					if (target.equals(PARMCLEARFLAG)) {
						posMerchantClear(request, response, login, "clear");
					}
					if (target.equals(PARMNORMFLAG)) {
						Debug.println("******wuye debug in normal request *********");		
						posMerchantClear(request, response, login, "normal");
					}
				}
			}else if (reqCode.equals(POSMERCHANTQUERY)) {//查询商户
				if ( (target == null) || target.trim().equals("")) {
					posMerchantQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posMerchantQueryPage(request, response, login);
					}
				}
			}else if (reqCode.equals(POSMERCHANTDELETE)) {//删除商户
				Debug.println("删除商户信息...POST请求");
				posMerchantDelete(request, response, login);
			}
		}
	}
	
	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		//取请求参数
		String target = "";
		target = request.getParameter("target");
		String queryDate = "";
		queryDate = request.getParameter("dateInput");
		Debug.println("1027 it is a good");
		Debug.println(queryDate);

		//权限控制
		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//取session信息
		HttpSession session = request.getSession(true);
		POSMerchantInfo Info = (POSMerchantInfo) session.getAttribute("posMerchant");
		if (Info != null) {
			try {
				List l = posMerchant.qryOrgPosMerchant(Info.getOrgid());
				request.setAttribute("posMerchantList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		} else {
			Vector posMerchantList = new Vector();
			session.setAttribute("posMerchantList", posMerchantList);
			Debug.println("**********posMerchantList post Null***************");
		}
		//添加商户
		if (reqCode.equals(POSMERCHANTADD)) {
			if ( (target == null) || target.trim().equals("")) {
				posMerchantAdd(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantAdd(request, response, true, login);
				}
			}
		}else if (reqCode.equals(CheckPrize)) {//添加抽奖
			if (target != null && target.equals("checkPZAdded")) {
				Debug.println("Ok man");
				CheckPrizeAdd(request, response, false, login);
			}
			if (target != null && target.equals(CheckPZ)) {
				Debug.println("good Man");
				CheckPrizeAdd(request, response, true, login);
			}
			if (target != null && target.equals("Modifypzinfo")) {
				Debug.println("yes do it");
				ModifyMerchantPZinfo(request, response, false, login);
			}
			if (target != null && target.equals("checkPZQuery")) {
				try {
					Debug.println("1027");
					Vector V1 = posMerchant.getCheckPrizeQuery(queryDate);
					String tarJsp = "/ToucsMonitor/POSDeviceManage/QueryCheckPrize.jsp?uid=" + login.getUserID();
					request.setAttribute("CheckPQueryVect", V1);
					toPage(request, response, tarJsp);
				}catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			}
		}else if (reqCode.equals(POSMERCHANTUPDATE)) {//修改商户
			if ( (target == null) || target.trim().equals("")) {
				posMerchantUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantUpdateQuery(request, response, true, login);
				}
				if (target.equals(PARMGETQUERYPAGE)) {
					posMerchantUpdateQuery(request, response, false, login);
				}
				if (target.equals(PARMCLEARFLAG)) {
					posMerchantClear(request, response, login, "clear");
				}
			}
		}else if (reqCode.equals(POSMERCHANTQUERY)) {//查询商户
			if ( (target == null) || target.trim().equals("")) {
				posMerchantQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantQueryPage(request, response, login);
				}
			}
		}else if (reqCode.equals(POSMERCHANTDELETE)) {//删除商户
			posMerchantDelete(request, response, login);
		}
	}
	
	//Clean up resources
	public void destroy() {
	}
	
	/**
	 * 设置用户对页面的操作权限
	 * @param req Http请求
	 * @param maskCode 用户权限码
	 */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTADD) == 0) {
			req.setAttribute("POSMERCHANTADD", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTUPDATE) == 0) {
			req.setAttribute("POSMERCHANTUPDATE", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTDELETE) == 0) {
			req.setAttribute("POSMERCHANTDELETE", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTQUERY) == 0) {
			req.setAttribute("POSMERCHANTQUERY", "1");
		}
	}
	
	/**
	 * 设置用户对页面的操作权限
	 * @param req Http请求
	 * @param maskCode 用户权限码
	 */
	private void setCheckPZPerm(HttpServletRequest req, String maskCode) {
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTADD) == 0) {
			req.setAttribute("CheckPrize", "1");
		}
	}
	
	/**
	 * 初始化抽奖添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId 机构编码
	 */
	private void initCheckPZAddList(HttpServletRequest req) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			v = CodeManageBean.queryCodes("0567", sq);
			req.setAttribute("channelIdList", v);
			//商户类型
			v = CodeManageBean.queryCodes("0553", sq);
			req.setAttribute("McttypeList", v);
			//使用标志
			v = CodeManageBean.queryCodes("0554", sq);
			req.setAttribute("FlagList", v);
			//是否允许预付费卡
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			//是否指定商户标志
			v = CodeManageBean.queryCodes("0222", sq);
			req.setAttribute("IsMerchantList", v);
			v = CodeManageBean.queryCodes("0223", sq);
			req.setAttribute("IsCardTypeList", v);
			v = CodeManageBean.queryMerchantCodes(sq);
			req.setAttribute("AllmerchantList", v);
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	/**
	 * 初始化商户添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId 机构编码
	 */
	private void initMerchantAddList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//商户类型
			v = CodeManageBean.queryCodes("0553", sq);
			req.setAttribute("McttypeList", v);
			//使用标志
			v = CodeManageBean.queryCodes("0554", sq);
			req.setAttribute("FlagList", v);
			//清理标志
			v = CodeManageBean.queryCodes("0555", sq);
			req.setAttribute("ClearFlagList", v);
			//商户手续费类型
			v = CodeManageBean.queryCodes("0569", sq);
			req.setAttribute("FeeType", v);
			//是否允许预付费卡
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			
			//机构列表
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
			
			//卡机构列表（清算机构）
			List cardOrgList = null;
			if (list != null) {
				cardOrgList = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					CodeBean code = (CodeBean) list.get(i);
					if (code.getCodeId().substring(5, 9).equals("3600") ||
							code.getCodeId().substring(3, 9).equals("363700") ||
							code.getCodeId().substring(5, 9).equals("1900" ) ) {
						cardOrgList.add(code);
					}
				}
			}			
			req.setAttribute("cardOrgList", cardOrgList);
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	/**
	 * 初始化商户添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId 机构编码
	 */
	private void initMerchantQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//是否允许预付费卡
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			
			//机构列表
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
			//支行（二级机构）列表
			if (list != null) {
				String[] branchs = new String[list.size()];
				//从查询得到的三级机构中取得其上级机构（二级支行）编号
				for (int i = 0; i < branchs.length; i++) {
					CodeBean code = (CodeBean) list.get(i);
					branchs[i] = code.getCodeType();
				}
				req.setAttribute("branchs", branchs);
			}
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	private void ModifyMerchantPZinfo(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		
		String tarJsp = null;
		//商户添加页面请求
		if (isGetPage) {
			initCheckPZAddList(request);
			tarJsp = PAGE_HOME + "CheckPZAdd.jsp?post=" + CheckPrize + "&uid=" + login.getUserID(); ;
		}else {
			CheckPrizeRuleInfo CheckPZInfo = new CheckPrizeRuleInfo();
			//从Http请求中取商户基本信息
			CheckPZInfo.fetchModifyPZinfoData(request);
			try {
				Debug.println("Shooter");
				posMerchant.ModifyCheckPZRule(CheckPZInfo);		
				PrizeRule pz = null;
				//记录日志
				Debug.println("Shooter");
				Vector oV = new Vector();
				String channelId = request.getParameter("channel_id");
				oV = posMerchant.queryPrizeInfo(channelId);
				pz = posMerchant.queryPrizeRule(channelId);
				writeLog(login, "修改详细商户抽奖规则");
				//转成功提示页面
				PromptBean prompt = new PromptBean("可以继续修改商户抽奖规则");
				prompt.setPrompt("请选择要修改的商户的抽奖规则");
				prompt.setButtonUrl(0, "结束添加抽奖配置", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				request.setAttribute("PrizeRule", pz);
				request.setAttribute("PrizeInfoV", oV);
				tarJsp = "/ToucsMonitor/SuccessAddMerchantRule.jsp?" + "&uid=" + login.getUserID();
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "商户详细抽奖规则添加失败");
				errProc(request, response, exp);
			}
			
			Debug.println("In the New World Guys fetchData");
			//记录日志
			writeLog(login, "添加抽奖规则成功");
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * 抽奖请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void CheckPrizeAdd(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		String tarJsp = null;
		//商户添加页面请求
		if (isGetPage) {
			initCheckPZAddList(request);
			tarJsp = PAGE_HOME + "CheckPZAdd.jsp?post=" + CheckPrize + "&uid=" + login.getUserID(); ;
		} else {
			CheckPrizeRuleInfo CheckPZInfo = new CheckPrizeRuleInfo();
			//从Http请求中取商户基本信息
			CheckPZInfo.fetchData(request);
			if (CheckPZInfo.getisPrizetype().equalsIgnoreCase("1")) {
				try {
					posMerchant.addCheckPZRule(CheckPZInfo);
					PrizeRule pz = null;
					//记录日志
					Vector oV = new Vector();
					String channelId = request.getParameter("channel_id");
					oV = posMerchant.queryPrizeInfo(channelId);
					pz = posMerchant.queryPrizeRule(channelId);
					writeLog(login, "添加详细商户抽奖规则");
					//转成功提示页面
					PromptBean prompt = new PromptBean("添加详细商户抽奖规则明细");
					prompt.setPrompt("请填写各个商户的抽奖规则");
					request.setAttribute("prompt", prompt);
					request.setAttribute("PrizeRule", pz);
					request.setAttribute("PrizeInfoV", oV);
					tarJsp = "/ToucsMonitor/SuccessAddMerchantRule.jsp";
				}catch (MonitorException exp) {
					//记录日志
					writeLog(login, "商户详细抽奖规则添加失败");
					errProc(request, response, exp);
				}
			}else {
				Debug.println("In the New World Guys fetchData");
				try {
					posMerchant.addCheckPZRule(CheckPZInfo);
					//记录日志
					writeLog(login, "添加抽奖规则成功");
					tarJsp = "/pzsucessed.html";
				}catch (MonitorException exp) {
					//记录日志
					writeLog(login, "抽奖规则添加失败");
					errProc(request, response, exp);
				}
			}
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * 商户添加请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantAdd(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		String tarJsp = null;
		//商户添加页面请求
		if (isGetPage) {
			initMerchantAddList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosMerChantAdd.jsp?post=" + POSMERCHANTADD + "&uid=" + login.getUserID();
		} else {
			POSMerchantInfo posMerchantInfo = new POSMerchantInfo();
			//从Http请求中取商户基本信息
			posMerchantInfo.fetchData(request);
			Debug.println("zipCode=[" + posMerchantInfo.getZipcode() + "]");
			try {
				posMerchant.addPosMerchant(posMerchantInfo);
				//记录日志
				writeLog(login, "商户[" + posMerchantInfo.getMerchantid() + "]添加成功");	
				//add by liyp 20030917
				//通知客户端增加新设备
				notifyClient("N20006", posMerchantInfo.getMerchantid(), "2");			
				//转成功提示页面
				PromptBean prompt = new PromptBean("商户管理");
				prompt.setPrompt("商户添加成功了!");
				prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				tarJsp = "/ToucsMonitor/Success.jsp";
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "商户添加失败");
				errProc(request, response, exp);
			}
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * 商户查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantQuery(HttpServletRequest request,
			HttpServletResponse response,
			LoginInfo login) throws ServletException,
			IOException {
		//取商户编号
		String merchantId = request.getParameter("merchant_id");
		System.out.println("merchant_id:" + merchantId);
		//取查询目的（修改、查询）
		if (merchantId != null && !merchantId.equals("")) {
			String tarJsp = null;
			try {
				//查询商户详细信息
				POSMerchantInfo posMerchantInfo = posMerchant.
				qryPosMerchant(merchantId);
				if (posMerchantInfo == null) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND, "商户不存在！");
				}
				initMerchantAddList(request, login.getOrgID());
				tarJsp = PAGE_HOME + "PosMerchantInfo.jsp";
				request.setAttribute("posMerchantInfo", posMerchantInfo);
				toPage(request, response, tarJsp);
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			try {
				//查询符合条件的所有商户信息
				String organNo = request.getParameter("orgId");
				String merchName = request.getParameter("merchant_name");//商户名称可能为空，应添加判断20121106 syl
				String operator = request.getParameter("operator");//是否允许预付卡也可不选择，应为全部加以判断 20121106 syl
				List l = posMerchant.getMerchantByOrgAndName(organNo, merchName, operator);
				request.setAttribute("posMerchantList", l);
				HttpSession session = request.getSession(true);
				POSMerchantInfo info = new POSMerchantInfo();
				info.setOrgid(organNo);
				session.setAttribute("posMerchant", info);
				//设置当前用户的操作权限（增加、修改、删除、查询、清理）
				toPage(request, response, PAGE_HOME + "PosMerchantManage.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
	
	/**
	 * 商户查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantQueryPage(HttpServletRequest request,
			HttpServletResponse response,
			LoginInfo login) throws ServletException,
			IOException {
		initMerchantQueryList(request, "110000000");
		toPage(request, response, PAGE_HOME + "PosMerchantQuery.jsp?post=" + POSMERCHANTQUERY + "&uid=" + login.getUserID());
	}
	
	/**
	 * 商户修改查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantUpdateQuery(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage,
			LoginInfo login) throws ServletException,
			IOException {
		//取商户编号
		String merchantId = request.getParameter("merchant_id");
		System.out.println("merchant_id:" + merchantId);
		//取查询内容（基本信息）
		if (!isGetPage) {
			if (merchantId != null) {
				String tarJsp = null;
				//查询商户基本信息
				try {
					//查询商户基本信息
					POSMerchantInfo posMerchantInfo = posMerchant.
					qryPosMerchant(merchantId);
					if (posMerchantInfo == null) {
						throw new MonitorException(ErrorDefine.RECNOTFOUND, "商户不存在！");
					}
					initMerchantAddList(request, login.getOrgID());
					tarJsp = PAGE_HOME + "PosMerChantUpdate.jsp?post=" +
					POSMERCHANTUPDATE;
					System.out.println("查询待修改的数据，指向：" + tarJsp);
					request.setAttribute("posMerchantInfo", posMerchantInfo);
					toPage(request, response, tarJsp);
				}catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//marked by liyp 20030904
			initMerchantQueryList(request, "110000000");
			toPage(request, response, PAGE_HOME + "PosMerchantUpdateQuery.jsp?" + "&uid=" + login.getUserID());
			
		}
	}
	
	/**
	 * 修改商户基本信息
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantUpdate(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws
			ServletException, IOException {
		POSMerchantInfo info = new POSMerchantInfo();
		//从请求数据中取得要修改的商户基本信息
		info.fetchData(request);
		//获取主键
		String key = request.getParameter("key");
		try {
			//修改商户基本信息
			posMerchant.updatePosMerchant(info, key);
			//记录日志
			writeLog(login, "商户[" + key + "]信息修改成功");
			
			//通知客户端修改商户信息 modify by liyp 20030918
			//notifyClient("N20004",key,"1");
			notifyClient("N20006", key, "1");
			
			//转成功提示页面
			PromptBean prompt = new PromptBean("商户管理");
			prompt.setPrompt("商户信息修改成功！");
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "商户[" + key + "]信息修改成功");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * 修改商户基本信息
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantDelete(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws
			ServletException, IOException {
		POSMerchantInfo info = new POSMerchantInfo();
		//从请求数据中取得要修改的商户基本信息
		info.fetchData(request);
		String key = request.getParameter("merchant_id");
		try {
			//修改商户基本信息
			int nRst = posMerchant.deletePosMerchant(key);
			PromptBean prompt = new PromptBean("商户管理");
			if (nRst == -1) {
				prompt.setPrompt("商户信息下POS设备信息，删除不成功！");
			}
			if (nRst == 0) {
				prompt.setPrompt("商户清理标志为‘正常’状态，删除不成功！");
				System.out.println("商户清理标志为‘正常’状态，删除不成功！");
			}
			if (nRst > 0) {
				//记录日志
				writeLog(login, "商户[" + key + "]删除成功");
				//通知客户端 add by liyp 20030918
				notifyClient("N20006", key, "0");
				//转成功提示页面
				prompt.setPrompt("商户信息删除成功！");
			}
			//需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}catch (MonitorException exp) {
			//记录日志
			writeLog(login, "商户[" + key + "]删除失败");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * 修改商户基本信息
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantClear(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login,
			String clearFlag) throws ServletException,
			IOException {
		String key = request.getParameter("merchant_id");
		String flag = clearFlag;
		Debug.println("********wuye debug clearFlag = " + flag + "*********");
		try {
			//修改商户清理标志信息
			System.out.println("&&&&&DELETE BEGIN&&&&&");
			PromptBean prompt = new PromptBean("商户管理");
			if (flag == "clear" || flag == "") {
				int nRst = posMerchant.clearPosMerchant(key);
				if (nRst > 0) {
					//记录日志
					writeLog(login, "商户[" + key + "]信息修改成功");
					//转成功提示页面
					prompt.setPrompt("商户清理标志设置成功！");
				}
			} else if (flag == "normal") {
				int nRst = posMerchant.normPosMerchant(key);
				if (nRst > 0) {
					//记录日志
					writeLog(login, "商户[" + key + "]信息修改成功");
					//转成功提示页面
					prompt.setPrompt("商户正常标志设置成功！");
				}
			}
			//需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}catch (MonitorException exp) {
			//记录日志
			writeLog(login, "商户[" + key + "]信息修改失败");
			errProc(request, response, exp);
		}
	}
	
}
