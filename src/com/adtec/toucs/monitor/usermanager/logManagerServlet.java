package com.adtec.toucs.monitor.usermanager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.usermanager.LogManagerBean;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class logManagerServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	private static final String QUERYLOG = "14001";
	private static final String DELLOG = "14002";
	LogManagerBean LM = null;

	//Initialize global variables
	public void init() throws ServletException {
		LM = new LogManagerBean();
	}
	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String TxCode = request.getParameter("txcode");
		if (TxCode == null || TxCode.equals("")) {
			out.println("<script>alert(\"取系统参数失败！\");</script>");
			return;
		}
		try {
			LoginInfo LInfo = checkLogin(request,response);
			LoginManageBean.operValidate(LInfo,TxCode);
			String beginDate = request.getParameter("begindate");
			String endDate = request.getParameter("enddate");
			if (beginDate == null || endDate == null) {
				out.println("<script>alert(\"取参数失败！\");</script>");
			}
			//查询日志
			if (TxCode.trim().equals(QUERYLOG)) {
				Vector LogVect = new Vector();
				LogVect = LM.queryLog(beginDate,endDate);
				request.setAttribute("logV",LogVect);
				RequestDispatcher rd = request.getRequestDispatcher("/ToucsMonitor/usermanager/logList.jsp");
				rd.forward(request,response);
			}
			//删除日志
			if (TxCode.trim().equals(DELLOG)) {
				int affect = -1;
				affect = LM.delLog( ChangeDateType(beginDate),ChangeDateType(endDate));
				if (affect >= 0) {
					writeLog(LInfo,"删除了日期从"+beginDate+"到"+endDate+"的日志信息！");
					Vector logV = LM.queryLog("0","Z");
					request.setAttribute("logV",logV);
					RequestDispatcher rd = request.getRequestDispatcher("/ToucsMonitor/usermanager/logList.jsp");
					rd.forward(request,response);
				} else {
					throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
				}
			}
		} catch(MonitorException ex){
			errProc(request,response,ex);
		}
	}

	private String ChangeDateType(String DateStr){
		if (DateStr == null || DateStr.trim().equals("")) {
			return "";
		}
		if (DateStr.length() < 8) {
			for (int i = 0; i < DateStr.length(); i++) {
				DateStr += "0";
			}
			return DateStr;
		}
		String temp = DateStr.substring(0,4)+DateStr.substring(4,6)+DateStr.substring(6,8);
		return temp;
	}
	
	//Clean up resources
	public void destroy() {
	}
}
