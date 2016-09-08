package com.adtec.ccbbj.servletunion;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet implements Servlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DownLoadServlet() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String file = request.getParameter("file");
		String path = request.getParameter("path");

		// file = request.getRealPath("/")+file;

		file = path + file;

		// 新建一个SmartUpload对象
		SmartUpload su = new SmartUpload();
		// 初始化
		su.initialize(this.getServletConfig(), request, response);

		su.setContentDisposition(null);
		// 下载文件
		try {
			su.downloadFile(file);
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		super.init();
	}
}