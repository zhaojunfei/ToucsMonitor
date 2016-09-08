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

public class POSPublicKeyServlet extends CommonServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSPublicKeyServlet() {
	}
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//交易码：新增
	private static final String POSPKEYADD = "13801";

	//交易码：查询
	private static final String POSPKEYQUERY = "13802";
	
	//交易码：修改
	private static final String POSPKEYUPDATE = "13803";
	
	//交易码：删除
	private static final String POSPKEYDELETE = "13804";
	
	//目标页面参数代码
	private static final String PARMGETPAGE = "page";
	
	//公钥管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/poskeyconfig";
	
	//公钥管理页面根路径
	private static final String PAGE_HOME = "/POSDeviceManage/";
	
	//公钥管理类实例
	private POSPublicKeyBean Manage = new POSPublicKeyBean();
	
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
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//目标JSP页面
		String tarJsp = null;
		//公钥参数管理请求
		if (reqCode == null) {
			//要根据当前用户所拥有公钥参数管理权限，以确定其所能执行的操作
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosPublicKeyReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(POSPKEYADD)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}
			//修改公钥
			if (reqCode.equals(POSPKEYUPDATE)) {
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}
			//查询公钥
			if (reqCode.equals(POSPKEYQUERY)) {
				QueryInfo(request, response, login);
			}
			//删除公钥
			if (reqCode.equals(POSPKEYDELETE)) {
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
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYADD) == 0) {
			req.setAttribute("POSPKEYADD", "1");
		}
		//修改
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYUPDATE) == 0) {
			req.setAttribute("POSPKEYUPDATE", "1");
		}
		//删除
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYDELETE) == 0) {
			req.setAttribute("POSPKEYDELETE", "1");
		}
		//查询
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYQUERY) == 0) {
			req.setAttribute("POSPKEYQUERY", "1");
		}
	}
  
  /**
   * 公钥添加请求处理
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
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosPublicKeyReg.jsp?post=" + POSPKEYADD;
		} else {
			POSPublicKey Info = new POSPublicKey();
			//从Http请求中取公钥基本信息
			Info.fetchData(request);
			String temp;
			temp=Info.getRid().replaceAll(" ", "");
			Info.setRid(temp);
			temp=Info.DeleteChar(Info.getSha1h());
			Info.setSha1h(temp);
			temp=Info.DeleteChar(Info.getModulus());
			Info.setModulus(temp);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("公钥管理");
				if (nReturn != -1) {
					//记录日志
					writeLog(login, "公钥[" + Info.getRid() + "]添加成功");
					prompt.setPrompt("公钥[" + Info.getRid() + "]添加成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);       
					tarJsp ="/Success.jsp?uid=" + login.getUserID();
					//通知客户端增加新公钥
				} else {
					prompt.setPrompt("公钥[" + Info.getRid() + "]添加失败！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "公钥[" + Info.getRid() + "]添加失败！");
				errProc(request, response, exp);
			}
		}
		//转目标页面
		toPage(request, response, tarJsp);
	}

  /**
   * 公钥查询请求处理
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
			request.setAttribute("publickeylist", v);
			toPage(request, response,PAGE_HOME + "PosPublicKeyList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}

  /**
   * 公钥修改查询请求处理
   * @param request Http请求
   * @param response Http响应
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
		//取公钥id和index
		String rid = request.getParameter("rid");
		String index = request.getParameter("index");
		System.out.println("pos_code:" + rid);
		//取查询内容（基本信息）
		if (rid != null && index !=null) {
			String tarJsp = null;
			try {
				//查询公钥基本信息
				POSPublicKey Info = Manage.queryInfo(rid,index, "");
				if (Info == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "公钥不存在！");
				}
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "PosPublicKeyUpdate.jsp?post=" + POSPKEYUPDATE + "&uid=" + login.getUserID();
				request.setAttribute("pospublickey", Info);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

  /**
   * 修改公钥基本信息
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSPublicKey info = new POSPublicKey();
		//从请求数据中取得要修改的公钥基本信息
		info.fetchData(request);
		//获取主键
		String rid = request.getParameter("rid");
		String index = request.getParameter("index");
		String memo1=request.getParameter("memo1");
    
		try {
			//修改公钥基本信息
			Manage.updateInfo(info, rid,index,memo1);
			//记录日志
			writeLog(login, "公钥[" + rid + "]修改成功");
			//通知客户端修改公钥配置信息
			//转成功提示页面
			PromptBean prompt = new PromptBean("公钥参数管理");
			prompt.setPrompt("公钥[" + rid + "]修改成功！");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "公钥[" + rid + "]修改失败");
			errProc(request, response, exp);
		}
	}

  /**
   * POS公钥删除
   * @param request Http请求
   * @param response Http响应
   * @param isGet Get请求标志
   * @param login 用户信息
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//从请求数据中取得要修改的公钥基本信息
		String rid = request.getParameter("rid");
		String index=request.getParameter("index");
		try {
			int nRst = Manage.deleteInfo(rid,index);
			PromptBean prompt = new PromptBean("公钥管理");
			if (nRst>0){
				writeLog(login, "公钥[" + rid + "]删除成功");
				//转成功提示页面
				prompt.setPrompt("公钥[" + rid + "]删除成功！");
			}
			//需要校验用户是否有加载权限
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//记录日志
			writeLog(login, "公钥[" + rid + "]删除失败");
			errProc(request, response, exp);
		}
	}
  
	public int updateInfo(POSPublicKey info, String rid,String index,String memo1) throws MonitorException {
	    //取数据库连接
	    SqlAccess sq = SqlAccess.createConn();
	    try {
	    	//设置数据库连接提交方式为非自动提交
	    	sq.setAutoCommit(false);
	    	//添加公钥基本信息
	    	PreparedStatement stm = info.makeUpdateStm(sq.conn,rid,index,memo1);
	    	int flag = stm.executeUpdate();
	    	if (flag == 1) {
	    		sq.commit();
	    	} else {
	    		sq.rollback();
	    		throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改账户信息失败！");
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
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		//添加公钥
		if (reqCode.equals(POSPKEYADD)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		}

		//修改公钥
		if (reqCode.equals(POSPKEYUPDATE)) {
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}

		//查询公钥
		if (reqCode.equals(POSPKEYQUERY)) {
			QueryInfo(request, response, login);
		}

		//删除公钥
		if (reqCode.equals(POSPKEYDELETE)) {
			DeleteInfo(request, response, login);
		}
	}
}

