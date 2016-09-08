package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;
import com.adtec.toucs.monitor.systemmanage.CodeManageBean;

/**
 * <p>Title: 商业IC卡公司表管理</p>
 * <p>Description: 商业IC卡公司表管理</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p> 
 * @author liuxy
 * @version 1.0
 */

public class PosCompanyServlet extends CommonServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	
	private static final String APPEND = "16501"; // 商业IC卡公司表新增
	
	private static final String DELETE = "16502"; // 商业IC卡公司表删除
	
	private static final String UPDATE = "16503"; // 商业IC卡公司表修改
	
	private static final String QUERY = "16504"; // 商业IC卡公司表查询
	
	private static final String MANAGE = "16505"; // 商业IC卡公司表启用停用
	
	private static final String KEYDOWN = "16506"; // 生成主密钥及打印
	
	private static final String STATISTICS = "16507"; // 商业IC卡公司表交易统计
	
	private static final String SUBSIDIARY = "16509"; // 商业IC卡公司表交易明细
	
	private static final String PARMGETPAGE = "page"; // 目标页面参数代码
	
	private static final String MANAGE_HOME = "/ToucsMonitor/poscompany"; // 公司表信息管理根路径
	
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/"; // 公司表信息管理页面根路径
	
	private PosCompanyBean posManage = new PosCompanyBean(); // 公司表信息管理类实例
	
	public void init() throws ServletException {
		
	}
	
	// Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// 设置缓冲页面不可用
		response.setHeader("Cache-Control", "no-store");
		// 取请求参数
		String reqCode = request.getParameter("reqCode");
		String target = request.getParameter("target");
		
		// 用户身份校验
		LoginInfo login = null;
		try {
			// 校验用户是否登录
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			// 校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		// 目标JSP页面
		String tarJsp = null;	
		// 设备参数管理请求
		if (reqCode == null) {
			// 要根据当前用户所拥有设备参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosCompanyManage.jsp?uid="
			+ login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			// 注册POS
			if (reqCode.equals(APPEND)) {
				if ((target == null) || target.trim().equals("")) {
					AppendInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AppendInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(UPDATE)) {// 修改POS
				if ((target == null) || target.trim().equals("")) {
					UPdateInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UPdateInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(QUERY)) {// 查询POS
				if ((target == null) || target.trim().equals("")) {
					QueryInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						QueryInfo(request, response, true, login);
					}
				}
			}else if (reqCode.equals(DELETE)) {// 删除POS
				DeleteInfo(request, response, login);
			}else if (reqCode.equals(MANAGE)) { // 签约POS设备管理
				if ((target == null) || target.trim().equals("")) {
					InfoManage(request, response, login, "1");
				} else {
					if (target.equals(PARMGETPAGE)) {
						InfoManage(request, response, login, "0");
					}
				}
			}else if (reqCode.equals(KEYDOWN)) { // 密钥下载及打印
				if ((target == null) || target.trim().equals("")) {
					KeyDown(request, response, login, "1");
				} else {
					if (target.equals(PARMGETPAGE)) {
						KeyDown(request, response, login, "0");
					}
				}
			}else if (reqCode.equals(STATISTICS)) {	// 交易统计
				if ((target == null) || target.trim().equals("")) {
					Statistic(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						Statistic(request, response, true, login);
					}
				}
			}else if (reqCode.equals(SUBSIDIARY)) {// 交易明细
				if ((target == null) || target.trim().equals("")) {
					Subsidiary(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						Subsidiary(request, response, true, login);
					}
				}
			}
		}
	}
	
	// Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//PrintWriter out = response.getWriter();
		// 取请求参数
		String reqCode = request.getParameter("reqCode");
		// 取请求参数
		String target = request.getParameter("target");
		
		// 权限控制
		LoginInfo login = null;
		try {
			// 校验用户是否登录
			login = checkLogin(request, response);
			// 校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		// 注册POS
		if (reqCode.equals(APPEND)) {
			if ((target == null) || target.trim().equals("")) {
				AppendInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AppendInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(UPDATE)) {// 修改POS
			if ((target == null) || target.trim().equals("")) {
				UPdateInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UPdateInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(QUERY)) {// 查询POS
			if ((target == null) || target.trim().equals("")) {
				QueryInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					QueryInfo(request, response, true, login);
				}
			}
		}else if (reqCode.equals(DELETE)) {// 删除POS
			Debug.println("删除商户信息...POST请求");
			DeleteInfo(request, response, login);
		}else if (reqCode.equals(MANAGE)) { // 签约POS设备启用
			if ((target == null) || target.trim().equals("")) {
				InfoManage(request, response, login, "1");
			} else {
				if (target.equals(PARMGETPAGE)) {
					InfoManage(request, response, login, "0");
				}
			}
		}else if (reqCode.equals(KEYDOWN)) { // 密钥下载及打印
			if ((target == null) || target.trim().equals("")) {
				KeyDown(request, response, login, "1");
			} else {
				if (target.equals(PARMGETPAGE)) {
					KeyDown(request, response, login, "0");
				}
			}
		}else if (reqCode.equals(STATISTICS)) {// 交易统计
			if ((target == null) || target.trim().equals("")) {
				Statistic(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					Statistic(request, response, true, login);
				}
			}
		}else if (reqCode.equals(SUBSIDIARY)) {// 交易明细
			if ((target == null) || target.trim().equals("")) {
				Subsidiary(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					Subsidiary(request, response, true, login);
				}
			}
		}
	}
	
	// Clean up resources
	public void destroy() {
	}
	
	/**
	 * 设置用户对页面的操作权限
	 * @param req Http请求
	 * @param maskCode用户权限码
	 */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		// 增加
		if (LoginManageBean.checkPermMask(maskCode, APPEND) == 0) {
			req.setAttribute("APPEND", "1");
		}
		// 修改
		if (LoginManageBean.checkPermMask(maskCode, UPDATE) == 0) {
			req.setAttribute("UPDATE", "1");
		}
		// 删除
		if (LoginManageBean.checkPermMask(maskCode, DELETE) == 0) {
			req.setAttribute("DELETE", "1");
		}
		// 查询
		if (LoginManageBean.checkPermMask(maskCode, QUERY) == 0) {
			req.setAttribute("QUERY", "1");
		}
	}
	
	/**
	 * 初始化添加页面所需的列表信息
	 * @param req Http请求
	 * @param orgId机构编码
	 */
	private void InitInfoList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			// 收费种类
			v = CodeManageBean.queryCodes("0610", sq);
			req.setAttribute("CompanyKind", v);
			// 商户类型
			v = CodeManageBean.queryCodes("0600", sq);
			req.setAttribute("MchantType", v);
			// 业务代码
			v = CodeManageBean.queryCodes("0601", sq);
			req.setAttribute("AgentCode", v);
			// 安全种类
			v = CodeManageBean.queryCodes("0602", sq);
			req.setAttribute("SecuKind", v);
			// 健康状态
			v = CodeManageBean.queryCodes("0630", sq);
			req.setAttribute("HealthStat", v);
			// 机构列表
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("BranchId", list);
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
	 * 设备登记请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AppendInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		// 设备登记Get请求
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosCompanyReg.jsp?post=" + APPEND;
		} else {
			PosCompanyInfo info = new PosCompanyInfo();
			// 从Http请求中取设备基本信息
			info.fetchData(request);
			try {
				int nReturn = posManage.AppendInfo(info, 1);
				PromptBean prompt = new PromptBean("商业IC卡公司表管理");
				if (nReturn != -1) {
					// 记录日志
					writeLog(login, "商业IC卡公司表[" + info.getCompany_id() + "]添加成功");
					// 转成功提示页面
					prompt.setPrompt("商业IC卡公司表添加成功!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("商业IC卡公司表添加不成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				// 记录日志
				writeLog(login, "商业IC卡公司表[" + info.getCompany_id() + "]添加失败");
				errProc(request, response, exp);
			}
		}
		// 转目标页面
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * POS设备删除 
	 * @param request Http请求
	 * @param response Http响应
	 * @param isGet Get请求标志
	 * @param login用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteInfo(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login)
	throws ServletException, IOException {
		// 从请求数据中取得要修改的设备基本信息
		String strKey = request.getParameter("company_id");
		try {
			// 修改商户信息
			posManage.DeleteInfo(strKey);
			PromptBean prompt = new PromptBean("商业IC卡公司表管理");
			{
				// 记录日志
				writeLog(login, "商业IC卡公司表[" + strKey + "]删除成功");
				
				// 转成功提示页面
				prompt.setPrompt("商业IC卡公司表删除成功！");
			}
			// 需要校验用户是否有加载权限
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "商业IC卡公司表[" + strKey + "]删除失败");
			errProc(request, response, exp);
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
	private void UPdateInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		// 取设备编号DCC
		String strKey = request.getParameter("company_id");
		// 取查询内容（基本信息）
		if (isGetPage) {
			if (strKey != null) {
				String tarJsp = null;
				// 查询设备基本信息
				try {
					// 查询设备基本信息
					PosCompanyInfo info = posManage.QueryInfo(strKey, "");
					if (info == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST,
						"商业IC卡公司表不存在！");
					}	
					InitInfoList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosCompanyUpdate.jsp?post=" + UPDATE
					+ "&uid=" + login.getUserID();
					request.setAttribute("PosCompany", info);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			PosCompanyInfo info = new PosCompanyInfo();
			// 从请求数据中取得要修改的设备基本信息
			info.fetchData(request);
			// 获取主键
			try {
				// 修改设备基本信息
				posManage.UpdateInfo(info, strKey);
				// 记录日志
				writeLog(login, "商业IC卡公司表[" + strKey + "]配置信息修改成功");
				
				// 转成功提示页面
				PromptBean prompt = new PromptBean("商业IC卡公司表管理");
				prompt.setPrompt("商业IC卡公司表修改成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} catch (MonitorException exp) {
				writeLog(login, "商业IC卡公司表[" + strKey + "]配置信息修改失败");
				errProc(request, response, exp);
			}
		}
	}
	
	/**
	 * POS设备查询请求处理
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryInfo(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME + "PosCompanyManage.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strType = request.getParameter("company_type");
			// 取查询目的（删除、修改、启停）
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QueryInfoByList(strKey, strType);
					request.setAttribute("PosCompanyList", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QueryInfoByList("", strType);
					request.setAttribute("PosCompanyList", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyList.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
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
	private void InfoManage(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login, String use_flag) throws ServletException, IOException {
		// 从请求数据中取得要修改的设备基本信息
		String strKey = request.getParameter("company_id");
		try {
			posManage.ManageInfo(strKey, use_flag);
			PromptBean prompt = new PromptBean("商业IC卡公司表管理");	
				if (use_flag.equals("0")) {
					writeLog(login, "商业IC卡公司表[" + strKey + "]停用成功");	
					prompt.setPrompt("商业IC卡公司表[" + strKey + "]停用成功！");
				} else {
					writeLog(login, "商业IC卡公司表[" + strKey + "]启用成功");
					prompt.setPrompt("商业IC卡公司表[" + strKey + "]启用成功！");
				}
			prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			writeLog(login, "商业IC卡公司表[" + strKey + "]管理失败");
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
	private void KeyDown(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login, String use_flag) throws ServletException, IOException {
		// 从请求数据中取得要修改的设备基本信息
		String strKey = request.getParameter("company_id");
		PosCompanyInfo info;
		try {
			info = posManage.QueryInfo(strKey, "");
			if (info.getSecu_kind().equals("0")) {
				String auth_code = posManage.down_auth(strKey, "CPY" + use_flag);
				PromptBean prompt = new PromptBean("商业IC卡公司表管理");
				writeLog(login, strKey, "MG7001", "商业IC卡公司表生成密钥成功");
				// 转成功提示页面
				prompt.setPrompt(auth_code);
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			} else {
				posManage.down_auth(strKey, "CPY" + "");
				PromptBean prompt = new PromptBean("商业IC卡公司表管理");
				writeLog(login, strKey, "MG7001", "商业IC卡公司表生成主密钥及打印成功！");
				// 转成功提示页面
				prompt.setPrompt("商业IC卡公司表[" + strKey + "]生成主密钥及打印成功！");
				request.setAttribute("prompt", prompt);
				toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			}
		} catch (MonitorException exp) {
			writeLog(login, "商业IC卡公司表[" + strKey + "]生成主密钥及打印失败");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * 商业IC卡公司表交易统计
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 */
	private void Statistic(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME
					+ "PosCompanyStatQuery.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strStart_date = "";
			String strEnd_date = "";
			String strMonth = request.getParameter("month");
			String strMerchant_id = request.getParameter("merchant_id");
			
			int intMonth = 0;
			int sysMonth = Integer.parseInt(posManage.sysDate().substring(4, 6));
			int sysYear = Integer.parseInt(posManage.sysDate().substring(0, 4));
			
			if (strMonth != null && !strMonth.equals("")) {
				intMonth = Integer.parseInt(strMonth);
				
				if (intMonth >= sysMonth) {
					strStart_date = (sysYear - 1) + strMonth + "01";
					strEnd_date = (sysYear - 1) + strMonth + "31";
				} else {
					strStart_date = sysYear + strMonth + "01";
					strEnd_date = sysYear + strMonth + "31";
				}
			} else {
				strStart_date = request.getParameter("start_date");
				strEnd_date = request.getParameter("end_date");
			}
			
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QueryStatisticInfo(strKey,strStart_date, strEnd_date, strMerchant_id);
					try {
						posManage.StatisticFile(v, strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCStatistic", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyStatistic.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QueryStatisticInfo("", strStart_date,strEnd_date, strMerchant_id);
					try {
						posManage.StatisticFile(v, strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCStatistic", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanyStatistic.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}
	
	/**
	 * 商业IC卡公司表交易明细
	 * @param request Http请求
	 * @param response Http响应
	 * @param login 用户信息
	 * @throws ServletException
	 * @throws IOException
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void Subsidiary(HttpServletRequest request,
			HttpServletResponse response, boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		if (isGetPage) {
			InitInfoList(request, login.getOrgID());
			toPage(request, response, PAGE_HOME
					+ "PosCompanySubsQuery.jsp?post=" + QUERY + "&uid=" + login.getUserID());
		} else {
			String strKey = request.getParameter("company_id");
			String strStart_date = "";
			String strEnd_date = "";
			String strMonth = request.getParameter("month");
			String strMerchant_id = request.getParameter("merchant_id");
			
			int intMonth = 0;
			int sysMonth = Integer.parseInt(posManage.sysDate().substring(4, 6));
			int sysYear = Integer.parseInt(posManage.sysDate().substring(0, 4));
			
			if (strMonth != null && !strMonth.equals("")) {
				intMonth = Integer.parseInt(strMonth);
				
				if (intMonth >= sysMonth) {
					strStart_date = (sysYear - 1) + strMonth + "01";
					strEnd_date = (sysYear - 1) + strMonth + "31";
				} else {
					strStart_date = sysYear + strMonth + "01";
					strEnd_date = sysYear + strMonth + "31";
				}
			} else {
				strStart_date = request.getParameter("start_date");
				strEnd_date = request.getParameter("end_date");
			}
			
			if (strKey != null && !strKey.equals("")) {
				try {
					Vector v = posManage.QuerySubsidiaryInfo(strKey,strStart_date, strEnd_date, strMerchant_id);
					try {
						posManage.SubsidiaryFile(v,strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCSubsidiary", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanySubsidiary.jsp?uid=" + login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			} else {
				try {
					Vector v = posManage.QuerySubsidiaryInfo("", strStart_date,strEnd_date, strMerchant_id);
					try {
						posManage.SubsidiaryFile(v,strKey,strStart_date, strEnd_date);
					} catch (WriteException e) {
						e.printStackTrace();
					}
					request.setAttribute("PCSubsidiary", v);
					toPage(request, response, PAGE_HOME
							+ "PosCompanySubsidiary.jsp?uid="+ login.getUserID());
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		}
	}
}