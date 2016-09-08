package com.adtec.toucs.monitor.devicemanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
 

import com.adtec.toucs.monitor.common.*;
 

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PosUpdateServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
	//交易码:更新ATM位置信息
 
	//Initialize global variables
	public void init() throws ServletException {
	}
	//Process the HTTP Get request
 	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		response.setContentType(CONTENT_TYPE);
 		PrintWriter out = response.getWriter();
 		out.println("<html>");
 		out.println("<head><title>PosUpdateServlet</title></head>");
 		out.println("<body>");
 		out.println("<p>The servlet has received a GET. This is the reply.</p>");
 		out.println("</body></html>");
 	}

 	//Process the HTTP Post request
 	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		//通知客户端修改设备配置信息
 		String tranCode=request.getParameter("tranCode");
 		String objId=request.getParameter("objId");
 		String oper=request.getParameter("oper");
 		int count=Util.getIntPara(request,"count",1);
 		for(int i=0;i<count;i++)
 			notifyClient(tranCode,objId,oper);
 		response.setContentType(CONTENT_TYPE);
 		PrintWriter out = response.getWriter();
 		out.println("<html>");
 		out.println("<head><title>NotifyClient</title></head>");
 		out.println("<body>");
 		out.println("<p>[tranCode]"+tranCode+"</p>");
 		out.println("<p>[objId]"+objId+"</p>");
 		out.println("<p>[oper]"+oper+"</p>");
 		out.println("<p>[count]"+count+"</p>");
 		out.println("</body></html>");
 	}
 	//Clean up resources
 	public void destroy() {
 	}
}