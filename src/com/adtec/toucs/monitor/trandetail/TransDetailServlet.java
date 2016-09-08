package com.adtec.toucs.monitor.trandetail;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

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

public class TransDetailServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	//查询ATM当日流水
	static final private String QUERYTRANDETAIL = "18001";
	//lihl add begin
	private static final String POSQUERYTRANDETAIL = "18101";
	private static final String CDMQUERYTRANDETAIL = "18201";
	private static final String MITQUERYTRANDETAIL = "18301";

	private static final String POSTYPE = "pos";
	private static final String CDMTYPE = "cdm";
	private static final String MITTYPE = "mit";

	//lihl add end

	//Initialize global variables
	public void init() throws ServletException {
	}
	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		doPost(request,response);
	}
	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		try {
			LoginInfo LInfo = new LoginInfo();
			LInfo = checkLogin(request,response);
			String TxCode = request.getParameter("reqCode");
			if (TxCode == null || TxCode.trim().equals("")) {
				throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
			}
			if (TxCode.equals(QUERYTRANDETAIL)){
				String AtmCode = request.getParameter("atmcode");
				TranDetail TD = new TranDetail();
				String orgID = LInfo.getOrgID();
				Vector TranV = TD.getTranDetail(orgID,AtmCode);
				String SysDate = TD.getSysDate();
				if (TranV == null || TranV.size() <= 0) {
					throw new MonitorException(ErrorDefine.NOTRANDETAILFOUND,ErrorDefine.NOTRANDETAILFOUNDDESC);
				}
				RequestDispatcher rd = request.getRequestDispatcher("/TranDetail/TranDetailList.jsp");
				request.setAttribute("tranV",TranV);
				request.setAttribute("SysDate",SysDate);
				rd.forward(request,response);
			}
			//lihl add begin
			if (TxCode.equals(POSQUERYTRANDETAIL)){
				Debug.println("POSQUERYTRANDETAIL");
				TranDetail TD = new TranDetail();
				String PosCode = request.getParameter("poscode");
				Vector TranV = TD.getPosTranDetail(PosCode,request.getParameter("pos_kind"));
				String SysDate = TD.getSysDate(POSTYPE);
				if (TranV == null || TranV.size() <= 0) {
					throw new MonitorException(ErrorDefine.NOTRANDETAILFOUND,ErrorDefine.NOTRANDETAILFOUNDDESC);
				}
				RequestDispatcher rd = request.getRequestDispatcher("/TranDetail/PosTranDetailList.jsp");
				request.setAttribute("tranV",TranV);
				request.setAttribute("SysDate",SysDate);
				rd.forward(request,response);
			}

			if (TxCode.equals(CDMQUERYTRANDETAIL)){
				Debug.println("CDMQUERYTRANDETAIL");
				TranDetail TD = new TranDetail();
				String cdmCode = request.getParameter("cdmcode");
				Vector TranV = TD.getCdmTranDetail(cdmCode);
				String SysDate = TD.getSysDate(CDMTYPE);
				if (TranV == null || TranV.size() <= 0) {
					throw new MonitorException(ErrorDefine.NOTRANDETAILFOUND,ErrorDefine.NOTRANDETAILFOUNDDESC);
				}
				RequestDispatcher rd = request.getRequestDispatcher("/TranDetail/CdmTranDetailList.jsp");
				request.setAttribute("tranV",TranV);
				request.setAttribute("SysDate",SysDate);
				rd.forward(request,response);
			}
			if (TxCode.equals(MITQUERYTRANDETAIL)){
				Debug.println("MITQUERYTRANDETAIL");
				TranDetail TD = new TranDetail();
				String mitCode = request.getParameter("mitcode");
				String SysDate = TD.getSysDate(MITTYPE);
				Vector TranV = TD.getMitTranDetail(mitCode);
				if (TranV == null || TranV.size() <= 0) {
					throw new MonitorException(ErrorDefine.NOTRANDETAILFOUND,ErrorDefine.NOTRANDETAILFOUNDDESC);
				}
				RequestDispatcher rd = request.getRequestDispatcher("/TranDetail/MitTranDetailList.jsp");
				request.setAttribute("tranV",TranV);
				request.setAttribute("SysDate",SysDate);
				rd.forward(request,response);
			}

			//lihl add end
		} catch (MonitorException ex) {
			errProc(request,response,ex);
		}
  }
	//Clean up resources
	public void destroy() {
	}
}