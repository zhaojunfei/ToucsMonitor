package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;
import com.adtec.toucs.monitor.devicemanage.*;

/**
 * <p>Title:POS设备管理Servlet类 </p>
 * <p>Description: 处理POS设备参数管理相关的Http请求</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */

public class POSDeviceManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//交易码：POS新增
	private static final String POSINFOREG = "13101";

	//交易码：POS修改
	private static final String POSINFOUPDATE = "13102";

	//交易码：POS查询
	private static final String POSINFOQUERY = "13103";
	
	//交易码：POS删除
	private static final String POSINFODELETE = "13104";

	//交易码：POS启用
	private static final String POSINFOSTART = "12101";

	//交易码：POS停用
	private static final String POSINFOSTOP = "12102";

	//交易码：POS交易设置
	private static final String POSTXNSET = "13105";

	//目标页面参数代码
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";
	private static final String PARMSTARTFLAG = "setStart";
	private static final String PARMSTOPFLAG = "setStop";
	private static final String PARMKEYPAGE = "keyPage";
	private static final String PARMKEYSET = "keySet";
	private static final String PARMTXNPAGE = "txnPage";
	private static final String PARMTXNSET = "txnSet";

	//编号种类参数
	//private static final String POSPTYPE = "1";
	//private static final String POSCTYPE = "2";
	private static final String DCCTYPE = "3";

	//POS设备参数管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/posdeviceconfig";

	//POS管理页面根路径
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	//POS设备管理类实例
	private POSDeviceManageBean posManage = new POSDeviceManageBean();

	//POS商户管理类实例
	private POSMerchantBean posMerchant = new POSMerchantBean();

	public void init() throws ServletException {
	}

	//Process the HTTP Get request
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

		//取session信息
		HttpSession session = request.getSession(true);

		//目标JSP页面
		String tarJsp = null;
		//设备参数管理请求
		if (reqCode == null) {
			POSExInfo exInfo = (POSExInfo) session.getAttribute("exInfo");
			if (exInfo != null) {
				try {
					Vector v = posManage.qryMctPosInfo(exInfo.getMerchantid());
					request.setAttribute("posInfoList", v);
				} catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			} else {
				Debug.println("**********posInfoList get Null***************");
			}
			//要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosInfoManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//注册POS
			if (reqCode.equals(POSINFOREG)) {
				if ( (target == null) || target.trim().equals("")) {
					posInfoReg(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoReg(request, response, true, login);
					}
				}
			}else if (reqCode.equals(POSINFOUPDATE)) {//修改POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoUpdate(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoUpdateQuery(request, response, true, login);
					}
					if (target.equals(PARMGETQUERYPAGE)) {
						posInfoUpdateQuery(request, response, false, login);
					}
					if (target.equals(PARMSTARTFLAG)) {
						posInfoStatSet(request, response, true, login);
					}
					if (target.equals(PARMSTOPFLAG)) {
						posInfoStatSet(request, response, false, login);
					}
					if (target.equals(PARMKEYPAGE)) {
						keyUpdateQuery(request, response, login);
					}
					if (target.equals(PARMKEYSET)) {
						keyUpdate(request, response, login);
					}
					//add by liyp 20030709
					if (target.equals(PARMTXNSET)) {
						Debug.println("********在doGet处理 PARMTXNSET************");
						this.posTxnInfoUpdateQuery(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
					if (target.equals(PARMKEYPAGE)) {
						keyQuery(request, response, login);
					}
					if (target.equals(PARMTXNPAGE)) {
						posTxnQuery(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFODELETE)) {//删除POS
				Debug.println("删除商户信息...GET请求");
				posInfoDelete(request, response, login);
			}else if (reqCode.equals(POSTXNSET)) {//设置POS交易
				if (target != null && target.equals(PARMTXNSET)) {
					Debug.println("**********doPost posTxnQuery*********");
					posTxnInfoUpdateQuery(request, response, login);
				} else {
					Debug.println("**********doPost posTxnInfoUpdate*********");
					this.posTxnInfoUpdate(request, response, login);
				}
			}
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		Debug.println("********reqCode=" + reqCode + "**********");
		//取请求参数
		String target = "";
		target = request.getParameter("target");
		
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
		//取session信息
		HttpSession session = request.getSession(true);
		POSExInfo exInfo = (POSExInfo) session.getAttribute("exInfo");
		if (exInfo != null) {
			try {
				Vector v = posManage.qryMctPosInfo(exInfo.getMerchantid());
				request.setAttribute("posInfoList", v);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		} else {
			Debug.println("**********posInfoList get Null***************");
			Vector v = new Vector();
			session.setAttribute("posInfoList", v);
		}
		//注册POS
		if (reqCode.equals(POSINFOREG)) {
			if ( (target == null) || target.trim().equals("")) {
				posInfoReg(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoReg(request, response, true, login);
				}
			}
		}else if (reqCode.equals(POSINFOUPDATE)) {//修改POS
			Debug.println("1");
			if ( (target == null) || target.trim().equals("")) {
				posInfoUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoUpdateQuery(request, response, true, login);
				}
				if (target.equals(PARMGETQUERYPAGE)) {
					Debug.println("2");
					posInfoUpdateQuery(request, response, false, login);
				}
				if (target.equals(PARMSTARTFLAG)) {
					posInfoStatSet(request, response, true, login);
				}
				if (target.equals(PARMSTOPFLAG)) {
					posInfoStatSet(request, response, false, login);
				}
				if (target.equals(PARMKEYPAGE)) {
					keyUpdateQuery(request, response, login);
				}
				if (target.equals(PARMKEYSET)) {
					keyUpdate(request, response, login);
				}
				//add by liyp 20030709
				if (target.equals(PARMTXNSET)) {
					Debug.println("********在doPost处理 PARMTXNSET************");
					this.posTxnInfoUpdateQuery(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
			if ( (target == null) || target.trim().equals("")) {
				posInfoQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoQueryPage(request, response, login);
				}
				if (target.equals(PARMKEYPAGE)) {
					keyQuery(request, response, login);
				}
				if (target.equals(PARMTXNPAGE)) {
					posTxnQuery(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFODELETE)) { //删除POS
			Debug.println("删除商户信息...POST请求");
			posInfoDelete(request, response, login);
		}else if (reqCode.equals(POSTXNSET)) {//设置POS交易
			if (target != null && target.equals(PARMTXNSET)) {
				Debug.println("**********doPost posTxnQuery*********");
				posTxnInfoUpdateQuery(request, response, login);
			} else {
				Debug.println("**********doPost posTxnInfoUpdate*********");
				this.posTxnInfoUpdate(request, response, login);
			}
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
		//交易及手输卡设置
		if (LoginManageBean.checkPermMask(maskCode, POSTXNSET) == 0) {
			req.setAttribute("POSTXNSET", "1");
			Debug.println("*****************can set POS TXN*************");
		}
		//启用
		if (LoginManageBean.checkPermMask(maskCode, POSINFOSTART) == 0) {
			req.setAttribute("POSINFOSTART", "1");	
		}
		//停用
		if (LoginManageBean.checkPermMask(maskCode, POSINFOSTOP) == 0) {
			req.setAttribute("POSINFOSTOP", "1");
		}
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
			//受理币种
			v = CodeManageBean.queryCodes("0551", sq);
			req.setAttribute("equitTypeList", v);
			//受理币种无转帐码
			v = CodeManageBean.queryCodesNo4("0551", sq);
			req.setAttribute("equitTypeNo4List", v);
			//POS机使用标志
			v = CodeManageBean.queryCodes("0552", sq);
			req.setAttribute("PosstatList", v);
			//通讯类型列表
			v = CodeManageBean.queryCodes(CodeDefine.NET_TYPE, sq);
			req.setAttribute("netTypeList", v);
			//POS类型
			v = CodeManageBean.queryCodes("0570", sq);
			req.setAttribute("memo1aList", v);
			//连接方式
			v = CodeManageBean.queryCodes("0580", sq);
			req.setAttribute("memo1bList", v);
			//跨行退货标志
			v = CodeManageBean.queryCodes("0590", sq);
			req.setAttribute("memo1cList", v);

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
	   * 初始化设备查询页面所需的列表信息
	   * @param req Http请求
	   * @param orgId 机构编码
	   **/
	private void initPosQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			System.out.println("1");
			CodeBean codeBean;
			v = new Vector();
			Vector vTmp = posMerchant.qryMerchantVector();
			for (int i = 0; i < vTmp.size(); i++) {
				codeBean = new CodeBean();
				codeBean.setCodeType("merchant_info");
				codeBean.setCodeId( (String) vTmp.get(i));
				codeBean.setCodeDesc( (String) vTmp.get(i + 1));
				v.add(codeBean);
				i++;
			}
			req.setAttribute("PosMerchantList", v);
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
	   * 初始化密钥配置页面所需的列表信息
	   * @param req Http请求
	   **/
	private void initKeyInfoList(HttpServletRequest req) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//DES加密方式
			v = CodeManageBean.queryCodes(CodeDefine.DES_TYPE, sq);
			req.setAttribute("desTypeList", v);
			//PINBLOCK格式
			v = CodeManageBean.queryCodes(CodeDefine.PINBLOCK, sq);
			req.setAttribute("pinblkList", v);
			//MAC算法
			v = CodeManageBean.queryCodes(CodeDefine.MAC_METHOD, sq);
			req.setAttribute("macList", v);
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
	   * 设备登记请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoReg(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//设备登记Get请求
		if (isGetPage) {
			POSDeviceManageBean pb = new POSDeviceManageBean();
			POSInfo pos;
			String pos_code = request.getParameter("pos_code");
			String mct_id = request.getParameter("merchant_id");
			try {
				if (mct_id != null && mct_id.length() > 0){
					Debug.println("Roxbbb:" + pos_code);
					pos = new POSInfo();
					pos.fetchData(request);
					pos.setOpenbankno(toucsString.unConvert(pos.getOpenbankno()));
					pos.setModel(toucsString.unConvert(pos.getModel()));
					pos.setCounter(toucsString.unConvert(pos.getCounter()));
					pos.setFixperson(toucsString.unConvert(pos.getFixperson()));
					pos.setFixaddress(toucsString.unConvert(pos.getFixaddress()));
					pos.setMct_name();
					request.setAttribute("posInfo", pos);
				} else if (pos_code != null && pos_code.length() > 0) {
					Debug.println("Roxaaa:" + pos_code);
					pos = pb.qryPosInfo(pos_code, "");
					pos.setMct_name();
					request.setAttribute("posInfo", pos);
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosInfoReg.jsp?post=" + POSINFOREG;
		} else {
			POSInfo posInfo = new POSInfo();
			//从Http请求中取设备基本信息
			posInfo.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posInfo.getMerchantid());
				int nReturn = posManage.regPosDevice(posInfo, nPosCount);
				PromptBean prompt = new PromptBean("设备参数管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "POS设备[" + posInfo.getPoscode() + "]添加成功");
					//add by liyp 20030917
					//通知客户端增加新设备
					Debug.println("通知客户端增加新POS设备");
					notifyClient("N20007", posInfo.getPoscode(), "2");

					//转成功提示页面
					prompt.setPrompt("POS设备添加成功!设备顺序号为：" + posInfo.getPoscode());
					prompt.setButtonUrl(0, "继续录入", MANAGE_HOME
                              + "?reqCode=13101&target=page&pos_code="
                              + posInfo.getPoscode());
					prompt.setButtonUrl(1, "查看设备信息", MANAGE_HOME
                              + "?reqCode=" + POSINFOQUERY + "&pos_code="
                              + posInfo.getPoscode()
                              + "&uid=" + login.getUserID());
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("已登记的POS设备数量等于该商户最大POS设备数量" + "，登记POS设备不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "POS设备[" + posInfo.getPoscode() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		//转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}

	  /**
	   * POS设备查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQuery(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException, IOException {
		//取设备编号
		String pos_code = request.getParameter("pos_code");
		System.out.println("pos_code:" + pos_code);
		{
			if (pos_code != null && !pos_code.equals("")) {
				String tarJsp = null;
				//查询设备基本信息
				try {
					//查询设备基本信息
					Vector v;
					SqlAccess sq = null;
					try {
						sq = SqlAccess.createConn();
						CodeBean codeBean = null;
						//受理币种
						v = CodeManageBean.queryCodes("0551", sq);
						request.setAttribute("equitTypeList", v);
						//受理币种无转帐码
						v = CodeManageBean.queryCodesNo4("0551", sq);
						request.setAttribute("equitTypeNo4List", v);
						//POS机使用标志
						v = CodeManageBean.queryCodes("0552", sq);
						request.setAttribute("PosstatList", v);
						//通讯类型列表
						v = CodeManageBean.queryCodes(CodeDefine.NET_TYPE, sq);
						request.setAttribute("netTypeList", v);
						//POS类型
						v = CodeManageBean.queryCodes("0570", sq);
						request.setAttribute("memo1aList", v);
						//连接方式
						v = CodeManageBean.queryCodes("0580", sq);
						request.setAttribute("memo1bList", v);
						//跨行退货标志
						v = CodeManageBean.queryCodes("0590", sq);
						request.setAttribute("memo1cList", v);
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
			            codeBean = new CodeBean();
			            codeBean.setCodeId("2");
			            codeBean.setCodeDesc("自定义交易权限");
			            v.add(codeBean);
			            request.setAttribute("preAuthorizedList", v);
					} catch (Exception exp) {
						exp.printStackTrace();
						System.out.println(exp.getMessage());
					} finally {
						if (sq != null) {
							sq.close();
						}
					}
					POSInfo posInfo = posManage.qryPosInfo(pos_code,request.getParameter("pos_kind"));
					if (posInfo == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "商户不存在！");
					}
					tarJsp = PAGE_HOME + "PosInfo.jsp";
					request.setAttribute("posInfo", posInfo);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					String merchant_id = request.getParameter("merchant_id");
					Vector v = posManage.qryMctPosInfo(merchant_id);
					request.setAttribute("posInfoList", v);
					HttpSession session = request.getSession(true);
					POSExInfo exInfo = new POSExInfo();
					exInfo.setMerchantid(merchant_id);
					session.setAttribute("exInfo", exInfo);
					//设置当前用户的操作权限（增加、修改、删除、查询、清理、交易及手输配置）
					setPagePerm(request, login.getPermission());
					toPage(request, response,PAGE_HOME + "PosInfoManage.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
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
	private void posInfoQueryPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosInfoQuery.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
	}

	  /**
	   * POS设备修改查询请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posInfoUpdateQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException,IOException {
 		//取设备编号DCC
 		String pos_code = request.getParameter("pos_code");
 		System.out.println("pos_code:" + pos_code);
 		//取查询内容（基本信息）
 		if (!isGetPage) {
 			if (pos_code != null) {
 				String tarJsp = null;
 				//查询设备基本信息
 				try {
 					//查询设备基本信息
 					POSInfo posInfo = posManage.qryPosInfo(pos_code, DCCTYPE);
 					if (posInfo == null) {
 						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
 					}
 					posInfo.setMct_name();
 					initPosList(request, login.getOrgID());
 					setPagePerm(request, login.getPermission());
 					tarJsp = PAGE_HOME + "PosInfoUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
 					request.setAttribute("posInfo", posInfo);
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
	   * 修改POS设备基本信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posInfoUpdate(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		POSInfo info = new POSInfo();
 		//从请求数据中取得要修改的设备基本信息
 		info.fetchData(request);
 		//获取主键
 		String key = request.getParameter("key");
 		try {
 			//修改设备基本信息
 			posManage.updatePosInfo(info, key);
 			//记录日志
 			writeLog(login, "POS设备[" + key + "]配置信息修改成功");
 			//通知客户端修改设备配置信息
 			notifyClient("N20007", key, "1");
 			
 			//转成功提示页面
 			PromptBean prompt = new PromptBean("设备参数管理");
 			prompt.setPrompt("POS设备信息修改成功！");
 			prompt.setButtonUrl(0, "继续修改", MANAGE_HOME + "?reqCode=13102&target=page");
 			prompt.setButtonUrl(1, "结束配置", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS设备[" + key + "]配置信息修改失败");
 			errProc(request, response, exp);
 		}
 	}
	
	  /**
	   * POS设备删除
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posInfoDelete(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//从请求数据中取得要修改的设备基本信息
 		String key = request.getParameter("pos_code");
 		try {
 			//修改设备基本信息
 			Debug.println("&&&&&DELETE BEGIN&&&&&");
 			int nRst = posManage.deletePosInfo(key);
 			PromptBean prompt = new PromptBean("POS设备管理");
 			{
 				//记录日志
 				writeLog(login, "POS设备[" + key + "]删除成功");
 				//通知客户端删除设备 add by liyp 20030918
 				notifyClient("N20007", key, "0");
 				//转成功提示页面
 				prompt.setPrompt("POS信息删除成功！");
 			}
 			//需要校验用户是否有加载权限
 			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS设备[" + key + "]删除失败");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * 修改POS设备启用停用
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   **/
 	private void posInfoKeySet(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//从请求数据中取得要修改的设备基本信息
 		String key = request.getParameter("pos_code");
 		String targetPage = "/ToucsMonitor/posdeviceconfig?reqCode=15001&uid=" +
        login.getUserID();
 		RequestDispatcher dp;
 		dp = request.getRequestDispatcher(targetPage);
 		//定向到结果页面
 		dp.forward(request, response);
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
 	private void posInfoStatSet(HttpServletRequest request,
                              HttpServletResponse response,
                              boolean bStat,
                              LoginInfo login) throws ServletException, IOException {
 		String key = request.getParameter("pos_code");
 		try {
 			//修改设备基本信息
 			Debug.println("&&&&&START BEGIN&&&&&");
 			int nRst = posManage.setStatPos(key, bStat);
 			PromptBean prompt = new PromptBean("POS设备管理");
 			if (nRst > 0) {
 				//转成功提示页面
 				if (bStat) {
 					writeLog(login, "POS设备[" + key + "]启动成功！");
 					prompt.setPrompt("POS设备启动成功！");
 				} else {
 					writeLog(login, "POS设备[" + key + "]停用成功！");
 					prompt.setPrompt("POS设备停用成功！");
 				}
 			} else {
 				//通知客户端修改设备配置信息
 				//notifyClient("N20004",key,"1");
 				//转失败提示页面
 				if (bStat) {
 					writeLog(login, "POS设备[" + key + "]启动失败！");
 					prompt.setPrompt("POS设备启动失败！");
 				} else {
 					writeLog(login, "POS设备[" + key + "]停用失败！");
 					prompt.setPrompt("POS设备停用失败！");
 				}
 			}
 			//需要校验用户是否有加载权限
 			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS设备[" + key + "]状态设置失败");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * 查询密钥配置信息
	   * @param atmCode 设备编号
	   * @param post 查询目的（修改、查询）
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyQuery(HttpServletRequest request,
                        HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		String tarJsp = null;
 		String posCode = request.getParameter("pos_code");
 		if (posCode == null) {
 			posCode = "";
 		}
 		posCode = posCode.trim();
 		try {
 			//根据POS编号种类进行转换－》POSP编号
 			String posKind = request.getParameter("pos_kind");
 			if (posKind.trim().equalsIgnoreCase("2")) { //C编号
 				posCode = "P" + posCode;
 			} else if (posKind.trim().equalsIgnoreCase("3")) { //DCC 编号
 				posCode = POSDeviceManageBean.transDCCCodeToPCode(posCode);
 			}
 			AtmKeyInfo keyInfo = posManage.qryKeyInfo(posCode, login.getOrgID());
 			if (keyInfo == null) {
 				throw new MonitorException(ErrorDefine.NOT_FOUND, "未找到指定的信息");
 			}
 			tarJsp = PAGE_HOME + "PosKeyInfo.jsp?" + "uid=" + login.getUserID();
 			request.setAttribute("keyInfo", keyInfo);
 		} catch (MonitorException exp) {
 			errProc(request, response, exp);
 		}
 		if (tarJsp != null) {
 			toPage(request, response, tarJsp);
 		}
 	}

	  /**
	   * 查询要修改的密钥配置信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyUpdateQuery(HttpServletRequest request,
                              HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		System.out.println("*************begin keyUpdateQuery***********");
 		String tarJsp = null;
 		//取登记（初次配置）还是修改标志 修改
 		String posCode = request.getParameter("pos_code");
 		
 		//缺省为修改
 		try {
 			posCode = POSDeviceManageBean.transDCCCodeToPCode(posCode);
 			AtmKeyInfo keyInfo = posManage.qryKeyInfo(posCode, login.getOrgID());
 			if (keyInfo == null) {
 				throw new MonitorException(ErrorDefine.NOT_FOUND, "未找到指定的信息");
 			}
 			initKeyInfoList(request);
 			tarJsp = PAGE_HOME + "PosKeyConfig.jsp?" + "uid=" + login.getUserID();
 			request.setAttribute("keyInfo", keyInfo);
 		} catch (MonitorException exp) {
 			errProc(request, response, exp);
 		}
 		if (tarJsp != null) {
 			toPage(request, response, tarJsp);
 		}
 	}

	  /**
	   * 修改密钥配置信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void keyUpdate(HttpServletRequest request,
                         HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		AtmKeyInfo info = new AtmKeyInfo();
 		//从Http请求中取要修改的密钥配置信息
 		info.fetchData(request);
 		try {
 			//修改密钥配置信息
 			posManage.updateKeyInfo(info);
 			//记录日志
 			writeLog(login, "POS设备[" + info.getAtmCode() + "]密钥配置修改成功");
 			//取登记（初次配置）还是修改标志
 			//转成功提示页面
 			PromptBean prompt = new PromptBean("POS设备信息管理");
 			prompt.setPrompt("POS设备密钥修改成功！");
 			
 			prompt.setButtonUrl(1, "结束配置", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			//记录日志
 			writeLog(login, "POS设备[" + info.getAtmCode() + "]密钥配置修改成功");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * POS设备交易及手输卡设置请求处理
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posTxnInfoUpdateQuery(HttpServletRequest request,
                                     HttpServletResponse response,
                                     LoginInfo login) throws ServletException, IOException {
 		//取设备编号
 		String pos_dcc_code = request.getParameter("pos_code");
 		Debug.println("pos_dcc_code:" + pos_dcc_code);
 		if (pos_dcc_code != null) {
 			String tarJsp = null;
 			try {
 				//查询设备交易权限配置信息
 				POSInfo pi = posManage.qryPosTxnInfo(pos_dcc_code, DCCTYPE);
 				if (pi == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
 				}
 				initPosList(request, login.getOrgID());
 				//交易设置信息
 				Vector v = posManage.qryPosTxnInfoSet(pi);
 				request.setAttribute("txnSetList", v);
 				tarJsp = PAGE_HOME + "PosTxnConfig.jsp?reqCode=" + POSTXNSET + "&uid=" + login.getUserID();
 				request.setAttribute("posInfo", pi);
 				toPage(request, response, tarJsp);
 			} catch (MonitorException exp) {
 				errProc(request, response, exp);
 			}
 		}
 	}

	  /**
	   * 修改POS设备基本信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posTxnInfoUpdate(HttpServletRequest request,
                                HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		Debug.println("********begin posTxnInfoUpdate********");
 		POSInfo info;
 		String key = "";
 		//从请求数据中取得要修改的设备交易和允许手输卡号配置
 		try {
 			POSDeviceManageBean pmb = new POSDeviceManageBean();
 			key = request.getParameter("pos_code");
 			info = pmb.qryPosInfo(key, "1");
 			info.fetchTxnData(request);
 			posManage.updatePosTxnInfo(info);
 			//记录日志
 			writeLog(login, "POS设备[" + key + "]交易权限设置成功");
 			//转成功提示页面
 			PromptBean prompt = new PromptBean("POS设备信息管理");
 			prompt.setPrompt("POS交易权限设置成功！");
 			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
 			request.setAttribute("prompt", prompt);
 			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
 		} catch (MonitorException exp) {
 			writeLog(login, "POS设备[" + key + "]交易权限设置失败");
 			errProc(request, response, exp);
 		}
 	}

	  /**
	   * 查询交易权限配置信息
	   * @param request Http请求
	   * @param response Http响应
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
 	private void posTxnQuery(HttpServletRequest request,
                           HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
 		//取设备编号
 		String posCode = request.getParameter("pos_code");
 		//取编号种类
 		String codeType = request.getParameter("pos_kind");
 		if (posCode != null && codeType != null) {
 			String tarJsp = null;
 			try {
 				//查询交易权限信息
 				POSInfo pi = posManage.qryPosTxnInfo(posCode, codeType);
 				if (pi == null) {
 					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
 				}
 				initPosList(request, login.getOrgID());
 				//交易设置信息
 				Vector v = posManage.qryPosTxnInfoSet(pi);
 				request.setAttribute("txnSetList", v);
 				tarJsp = PAGE_HOME + "PosTxnConfigInfo.jsp?reqCode=" + POSTXNSET + "&uid=" + login.getUserID();
 				request.setAttribute("posInfo", pi);
 				toPage(request, response, tarJsp);
 			} catch (MonitorException exp) {
 				errProc(request, response, exp);
 			}
 		}
 	}
}
