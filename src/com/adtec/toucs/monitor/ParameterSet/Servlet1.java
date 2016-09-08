package com.adtec.toucs.monitor.ParameterSet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Servlet1 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Servlet1</title></head>");
		out.println("<body>");
		out.println("<p>The servlet has received a GET. This is the reply.</p>");
		out.println("</body></html>");
	}
	//Clean up resources
	public void destroy() {
	}
}