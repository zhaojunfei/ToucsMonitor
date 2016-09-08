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

		// �½�һ��SmartUpload����
		SmartUpload su = new SmartUpload();
		// ��ʼ��
		su.initialize(this.getServletConfig(), request, response);

		su.setContentDisposition(null);
		// �����ļ�
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