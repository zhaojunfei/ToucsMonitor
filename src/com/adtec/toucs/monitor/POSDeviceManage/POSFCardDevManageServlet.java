package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;
import java.util.Vector;

/**
 * <p>Title:POS设备管理Servlet类 </p>
 * <p>Description: 处理POS设备参数管理相关的Http请求</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */
public class POSFCardDevManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSFCardDevManageServlet() {
	}

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	//交易码：POS新增
	private static final String POSINFOREG = "13501";
	//交易码：POS修改
	private static final String POSINFOUPDATE = "13502";
	//交易码：POS查询
	private static final String POSINFOQUERY = "13503";
	//交易码：POS删除
	private static final String POSINFODELETE = "13504";
	//目标页面参数代码
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";

	//POS设备参数管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/fcardposconfig";
	//POS管理页面根路径
	private static final String PAGE_HOME = "/POSDeviceManage/";
	//POS设备管理类实例
	private POSFCardDevManageBean posFCardManage = new POSFCardDevManageBean();
	//POS商户管理类实例
	private POSFCardMerchantBean posFCardMerchant = new POSFCardMerchantBean();

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
			LoginManageBean.operValidate(login,POSINFOREG);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//目标JSP页面
		String tarJsp = null;
		//设备参数管理请求
		if (reqCode == null) {
			try {
				List l = posFCardManage.getPOSInfoByObjId("", "");
				request.setAttribute("posInfoList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
			//要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			//测试用权限
			request.setAttribute("POSINFOREG", "1");
			request.setAttribute("POSINFOUPDATE", "1");
			request.setAttribute("POSINFODELETE", "1");
			request.setAttribute("POSINFOQUERY", "1");
			tarJsp = PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID();
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
				}
			}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
				if ( (target == null) || target.trim().equals("")) {
					posInfoQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posInfoQueryPage(request, response, login);
					}
				}
			}else if (reqCode.equals(POSINFODELETE)) {//删除POS
				Debug.println("删除商户信息...GET请求");
				posInfoDelete(request, response, login);
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
			LoginManageBean.operValidate(login, POSINFOREG);
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		//取session信息
		if (reqCode == null) {
			String merId = "";
			merId = request.getParameter("mer_id");
			try {
				List l = posFCardManage.getPOSInfoByObjId(merId, "");
				request.setAttribute("posInfoList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
			//要根据当前用户所拥有商户管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			String tarJsp = PAGE_HOME + "FCardPosDevManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
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
			Debug.println("1+++++" + target);
			if ( (target == null) || target.trim().equals("")) {
				posInfoUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoUpdateQuery(request, response, true, login);
				}else if (target.equals(PARMGETQUERYPAGE)) {
					Debug.println("2");
					posInfoUpdateQuery(request, response, false, login);
				}
			}
		}else if (reqCode.equals(POSINFOQUERY)) {//查询POS
			if ( (target == null) || target.trim().equals("")) {
				posInfoQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posInfoQueryPage(request, response, login);
				}
			}
		}else if (reqCode.equals(POSINFODELETE)) {//删除POS
			Debug.println("删除商户信息...POST请求");
			posInfoDelete(request, response, login);
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

	  /**
	   * 初始化商户添加页面所需的列表信息
	   * @param req Http请求
	   * @param orgId 机构编码
	   */
	private void initPosList(HttpServletRequest req) {
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
	   */
	private void initPosQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		try {
			System.out.println("1");
			CodeBean codeBean;
			v = new Vector();
			Vector vTmp = posFCardMerchant.qryMerchantVector();
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
			initPosList(request);
			tarJsp = PAGE_HOME + "FCardPosTerminalInfoAdd.jsp?post=" + POSINFOREG;
		} else {
			POSFCardInfo posInfo = new POSFCardInfo();
			//从Http请求中取设备基本信息
			posInfo.fetchData(request);
			try {
				int nReturn = posFCardManage.regPosDevice(posInfo);
				PromptBean prompt = new PromptBean("设备参数管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "外卡POS设备[" + posInfo.getPos_no() + "]添加成功");
					//转成功提示页面
					prompt.setPrompt("POS设备添加成功!设备编号为：" + posInfo.getPos_no());
					prompt.setButtonUrl(0, "继续添加", MANAGE_HOME + "?reqCode=13501&target=page");
					prompt.setButtonUrl(1, "查看设备列表", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					tarJsp = "/Success.jsp";
				} else {
					prompt.setPrompt("已登记的POS设备数量等于该商户最大POS设备数量" + "，登记POS设备不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "POS设备[" + posInfo.getPos_no() + "]添加失败");
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
		String mer_id = request.getParameter("mer_id");
		String pos_no = request.getParameter("pos_no");
		System.out.println("posInfoQuery:[" + mer_id + "][" + pos_no + "]");
		
		if (mer_id == null) {
			mer_id = "";
		}
		if (pos_no == null) {
			pos_no = "";
		}

		if (pos_no.equals("")) {
			try {
				List l = posFCardManage.getPOSInfoByObjId(mer_id, pos_no);
				request.setAttribute("posInfoList", l);
				//设置当前用户的操作权限（增加、修改、删除、查询、清理、交易及手输配置）
				setPagePerm(request, login.getPermission());
				toPage(request, response,PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			String tarJsp = null;
			//查询设备基本信息
			try {
				//查询设备基本信息
				initPosList(request);
				POSFCardInfo posInfo = posFCardManage.qryPosInfo(mer_id, pos_no);
				if (posInfo == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "设备不存在！");
				}
				tarJsp = PAGE_HOME + "FCardPosTerminalInfo.jsp";
				request.setAttribute("posInfo", posInfo);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
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
	private void posInfoQueryPage(HttpServletRequest request,
                                HttpServletResponse response,
                                LoginInfo login) throws ServletException, IOException {
		initPosList(request);
		toPage(request, response, PAGE_HOME + "FCardPosInfoQuery.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
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
                                  LoginInfo login) throws ServletException, IOException {
		//取设备编号DCC
		String mer_id = request.getParameter("mer_id");
		String pos_code = request.getParameter("pos_no");
		String tarJsp = null;
		System.out.println("pos_code:" + pos_code);
		
		//取查询内容（基本信息）
		if (!isGetPage) {
			if (pos_code != null && !pos_code.equals("")) {
				//查询设备基本信息
				try {
					//查询设备基本信息
					POSFCardInfo posInfo = posFCardManage.qryPosInfo(mer_id, pos_code);
					if (posInfo == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS设备不存在！");
					}
					initPosList(request);
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "FCardPosTerminalUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posInfo", posInfo);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					List l = posFCardManage.getPOSInfoByObjId(mer_id, "");
					request.setAttribute("posInfoList", l);
				} catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
				//要根据当前用户所拥有商户管理权限，以确定其所能执行的操作
				request.setAttribute("POSINFOREG", "1");
				request.setAttribute("POSINFOUPDATE", "1");
				request.setAttribute("POSINFODELETE", "1");
				request.setAttribute("POSINFOQUERY", "1");
				tarJsp = PAGE_HOME + "FCardPosInfoManage.jsp?uid=" + login.getUserID();
				toPage(request, response, tarJsp);
			}
		} else {
			//请求到来的最初页面
			initPosList(request);
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "FCardPosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
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
		POSFCardInfo info = new POSFCardInfo();
		//从请求数据中取得要修改的设备基本信息
		info.fetchData(request);
		//获取主键
		String merId = request.getParameter("mer_id");
		String posNo = request.getParameter("pos_no");
		Debug.println("RRRR:[" + merId + "][" + posNo + "]");
		try {
			//修改设备基本信息
			posFCardManage.updatePosInfo(info, merId, posNo);
			//记录日志
			writeLog(login, "外卡POS设备[" + merId + "][" + posNo + "]配置信息修改成功");
			//转成功提示页面
			PromptBean prompt = new PromptBean("设备参数管理");
			prompt.setPrompt("POS设备信息修改成功！");
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "POS设备[" + merId + "][" + posNo + "]配置信息修改失败");
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
		String merId = request.getParameter("mer_id");
		String posNo = request.getParameter("pos_no");
		try {
			//修改设备基本信息
			Debug.println("&&&&&DELETE BEGIN&&&&&");
			int nRst = posFCardManage.deletePosInfo(merId, posNo);
			PromptBean prompt = new PromptBean("POS设备管理");
			//记录日志
			writeLog(login, "外卡POS设备[" + merId + "][" + posNo + "]删除成功");
			//转成功提示页面
			prompt.setPrompt("POS信息删除成功！");
			//需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "外卡POS设备[" + merId + "][" + posNo + "]删除失败");
			errProc(request, response, exp);
		}
	}
}
