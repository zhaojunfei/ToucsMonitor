package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;

public class POSPublicAppServlet extends CommonServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSPublicAppServlet() {
	}
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//交易码：新增
	private static final String POSPAPPADD = "13901";	
	
	//交易码：查询
	private static final String POSPAPPQUERY = "13902";
	
	//交易码：修改
	private static final String POSPAPPUPDATE = "13903";
	
	//交易码：删除
	private static final String POSPAPPDELETE = "13904";
	
	//目标页面参数代码
	private static final String PARMGETPAGE = "page";
	
	//EMV卡参数管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/pospublicappconfig";
	
	//EMV卡参数管理页面根路径
	private static final String PAGE_HOME = "/POSDeviceManage/";
	
	//EMV卡参数管理类实例
	private POSPublicAppBean Manage = new POSPublicAppBean();
	
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
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//目标JSP页面
		String tarJsp = null;
		
		//EMV卡参数管理请求
		if (reqCode == null) {
			//要根据当前用户所拥有EMV卡参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosPublicAppReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//添加EMV卡参数 
			if (reqCode.equals(POSPAPPADD)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}
			//修改EMV卡参数
			if (reqCode.equals(POSPAPPUPDATE)) {
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}
			//查询EMV卡参数
			if (reqCode.equals(POSPAPPQUERY)) {
				QueryInfo(request, response, login);
			}
			//删除EMV卡参数
			if (reqCode.equals(POSPAPPDELETE)) {
				DeleteInfo(request, response, login);
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
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPADD) == 0) {
			req.setAttribute("POSPAPPADD", "1");
		}
		//修改
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPUPDATE) == 0) {
			req.setAttribute("POSPAPPUPDATE", "1");
		}
		//删除
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPDELETE) == 0) {
			req.setAttribute("POSPAPPDELETE", "1");
		}
		//查询
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPQUERY) == 0) {
			req.setAttribute("POSPAPPQUERY", "1");
		}
	}
  
  /**
   * EMV卡参数添加请求处理
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void AddInfo(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//EMV卡参数添加Get请求
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosPublicAppReg.jsp?post=" + POSPAPPADD;
		} else {
			POSPublicApp Info = new POSPublicApp();
			//从Http请求中取EMV卡参数基本信息
			Info.fetchData(request);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("EMV卡参数管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "EMV卡参数[" + Info.getAid() + "]添加成功");
					prompt.setPrompt("EMV卡参数[" + Info.getAid() + "]添加成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);       
					tarJsp ="/Success.jsp?uid=" + login.getUserID();
				}else {
					prompt.setPrompt("EMV卡参数[" + Info.getAid() + "]添加失败！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}	
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "EMV卡参数[" + Info.getAid() + "]添加失败！");
				errProc(request, response, exp);
			}
		}
		//转目标页面   
		toPage(request, response, tarJsp);
	}

  /**
   * EMV卡参数查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException,IOException {
		try {
			List v = Manage.VqueryInfo();
			request.setAttribute("publicapplist", v);
			toPage(request, response,PAGE_HOME + "PosPublicAppList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}

  /**
   * EMV卡参数修改查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException,IOException {
		//取EMV卡参数编号
		String aid = request.getParameter("aid");
		System.out.println("pos_code:" + aid);
		//取查询内容（基本信息）
		if (aid != null) {
			String tarJsp = null;
			try {
				POSPublicApp Info = Manage.queryInfo(aid,"");
				if (Info == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "EMV卡参数不存在！");
				}
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "PosPublicAppUpdate.jsp?post=" + POSPAPPUPDATE + "&uid=" + login.getUserID();
				request.setAttribute("pospublicapp", Info);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

  /**
   * 修改EMV卡参数基本信息
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSPublicApp info = new POSPublicApp();
		//从请求数据中取得要修改的EMV卡参数基本信息
		info.fetchData(request);
		//获取主键
		String aid = request.getParameter("aid"); 	 
		String memo1=request.getParameter("memo1");
		
		try {
			//修改EMV卡参数基本信息
			Manage.updateInfo(info, aid, memo1);
			//记录日志
			writeLog(login, "EMV卡参数[" + aid + "]修改成功");
			
			//转成功提示页面
			PromptBean prompt = new PromptBean("EMV卡参数管理");
			prompt.setPrompt("EMV卡参数[" + aid + "]修改成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "EMV卡参数[" + aid + "]修改失败");
			errProc(request, response, exp);
		}
	}

  /**
   * EMV卡参数删除
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//从请求数据中取得要修改的EMV卡参数基本信息
		String aid = request.getParameter("aid");    
		
		try {
			//修改EMV卡参数基本信息
			int nRst = Manage.deleteInfo(aid);
			PromptBean prompt = new PromptBean("EMV卡参数管理");
			if (nRst>0){
				//记录日志
				writeLog(login, "EMV卡参数[" + aid + "]删除成功");
				//转成功提示页面
				prompt.setPrompt("EMV卡参数[" + aid + "]删除成功！");
			}
			//需要校验用户是否有加载权限
			request.setAttribute("prompt", prompt); 	
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "EMV卡参数[" + aid + "]删除失败");
			errProc(request, response, exp);
		}
	}
  
	public int updateInfo(POSPublicApp info, String aid,String memo1) throws MonitorException {
	    //取数据库连接
	    SqlAccess sq = SqlAccess.createConn();
	    try {
	    	//设置数据库连接提交方式为非自动提交
	    	sq.setAutoCommit(false);
	    	//添加EMV卡参数基本信息
	    	PreparedStatement stm = info.makeUpdateStm(sq.conn,aid,memo1);
	    	int flag = stm.executeUpdate();
	    	if (flag == 1) {
	    		sq.commit();
	    	} else {
	    		sq.rollback();
	    		throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改EMV卡参数信息失败！");
	    	}
	    	return flag;
	    } catch (SQLException e1) {
	    	sq.rollback();
	    	throw new MonitorException(e1);
	    } finally {
	    	sq.close();
	    }
	}
   
	//Process the HTTP Post request
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
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//添加EMV卡参数
		if (reqCode.equals(POSPAPPADD)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		}

		//修改EMV卡参数
		if (reqCode.equals(POSPAPPUPDATE)) {
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}

		//查询EMV卡参数
		if (reqCode.equals(POSPAPPQUERY)) {
			QueryInfo(request, response, login);
		}
		
		//删除EMV卡参数
		if (reqCode.equals(POSPAPPDELETE)) {
			DeleteInfo(request, response, login);
		}
	}
}

