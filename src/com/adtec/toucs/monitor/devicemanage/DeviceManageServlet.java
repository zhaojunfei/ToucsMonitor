package com.adtec.toucs.monitor.devicemanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.jdom.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title: 设备参数管理Servlet类</p>
 * <p>Description: 处理设备参数管理相关的Http请求</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DeviceManageServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	//交易码：加载设备
	private static final String ATMLOAD = "12003";

	//交易码:更新设备位置信息
	private static final String ATMPOSUPDATE = "15006";

	//交易码:新增加密机信息
	private static final String INSERTENCRYPTINFO = "21001";
	
	//交易码:启用加密机
	private static final String STARTENCRYPT = "21002";

	//交易码:停用加密机
	private static final String STOPENCRYPT = "21003";

	//交易码:删除加密机
	private static final String DELETEENCRYPTINFO = "21004";

	//交易码:查询加密机信息
	private static final String QUERYENCRYPTINFO = "21006";

	//交易码:查询加密机信息
	private static final String MODIFYENCRYPTINFO = "21007";

	//ATM设备地图信息管理
	private static final String ATMDEVICEMAP = "21008";

	//ATM设备地图信息添加
	private static final String ATMDeviceAdd = "21009";

	//Atm时段权限设置
	private static final String ATMtimerPower = "21010";

	//Atm时段时间设置
	private static final String ATMtimerconfig = "21011";

	public int actureXPosi;
	public int actureYPosi;
	public String Rectwidth;
	public String RectHeight;
	public String InputAtmID;

	//设备管理类实例
	private static DeviceManageBean deviceManage = new DeviceManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取请求参数
		String reqCode = request.getParameter("reqCode");
		String tempAtmId = request.getParameter("Atmid");
		String returnAtmCode = request.getParameter("Atmcode");
		String isAtmChecked = request.getParameter("isAtm");
		//用户身份校验
		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				//加密机的不进行校验
				if (!reqCode.trim().substring(0, 3).equals("210")) {
					LoginManageBean.operValidate(login, reqCode);
				}
				if (reqCode.equals(ATMtimerPower) || reqCode.equals(ATMtimerconfig)) {
					LoginManageBean.operValidate(login, reqCode);
				}
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//取设备编号
		String atmCode = request.getParameter("atmCode");
		//目标JSP页面
		String tarJsp = null;
		Debug.print("QUERYENCRYPTINFO" + reqCode);
		//设备参数管理请求
		//加载设备
		if (reqCode.equals(ATMLOAD)) {
			atmLoad(request, response, true, login);
		} else if (reqCode.equals(QUERYENCRYPTINFO)) {
			//查询加密机信息
			try {
				tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				//根据查询条件进行查询，得到设备列表
				request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
				//转设备参数管理页面
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}   else if (reqCode.equals(DELETEENCRYPTINFO)) {
			//删除加密机信息
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				if (deviceManage.deleteEncryptInfo(atmCode)) {
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					writeLog(login, "设备[" + atmCode + "]删除成功");
				} else {
					writeLog(login, "设备[" + atmCode + "]删除失败");
				}
			} catch (MonitorException e) {
				e.printStackTrace();
			}
			//转目标页面
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(STOPENCRYPT)) {
			//停用加密机
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				if (deviceManage.stopEncrypt(atmCode)) {
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					writeLog(login, "设备[" + atmCode + "]停用成功");
				} else {
					writeLog(login, "设备[" + atmCode + "]停用失败");
				}
			} catch (MonitorException e) {
				e.printStackTrace();
			}
			//转目标页面
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(STARTENCRYPT)) {
			//起用加密机
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				deviceManage.loadEncrypt(atmCode);
				request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
				writeLog(login, "设备[" + atmCode + "]停用成功");
			} catch (MonitorException e) {
				writeLog(login, "设备[" + atmCode + "]停用失败");
				e.printStackTrace();
				errProc(request, response, e);
			}
			//转目标页面
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(ATMDEVICEMAP)) {
			tarJsp = "/DeviceManage/ATmGraphMain.jsp";
			Vector oV = new Vector();
			Vector oV1 = new Vector();
			String ImgId = new String();
			try {
				InputAtmID = tempAtmId;
				oV = deviceManage.queryATMDevice("1");
				Debug.println(oV.size());
				request.setAttribute("AtmIsMaped", oV);
				oV1 = deviceManage.queryATMDevice("0");
				Debug.println(oV1.size());
				request.setAttribute("AtmUnMaped", oV1);
				ImgId = deviceManage.getTheAgentId(tempAtmId);
				Debug.println("today is a good day");
				Debug.println(tempAtmId);
				Debug.println(ImgId);
				if (ImgId != "") {
					Debug.println(ImgId);
					request.setAttribute("AtmImg", ImgId);
				} else {
					request.setAttribute("AtmImg", "");
				}
			} catch (MonitorException e) {
				Debug.println("ATM Map occur errors");
			}
			Debug.println("ATM Map ok!");
			writeLog(login, "ATM地图设置");
			//转目标页面
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(ATMtimerPower)) {
			request.setAttribute("AtmReturnID", "");
			if (isAtmChecked != null && isAtmChecked.equals("222")) {
				tarJsp = "/DeviceManage/ATMtimercontrol.jsp?RAtmId=" + returnAtmCode;
				Debug.println("VVV");
				Debug.println(returnAtmCode);
				try {
					deviceManage.PowAtm1 = null;
					deviceManage.PowAtm2 = null;
					deviceManage.PowAtm3 = null;
					deviceManage.PowAtm4 = null;
					deviceManage.PowAtm5 = null;
					deviceManage.PowAtm6 = null;
					deviceManage.PowAtm7 = null;
					deviceManage.PowAtm8 = null;

					deviceManage.UnSelecPow1 = null;
					deviceManage.UnSelecPow2 = null;
					deviceManage.UnSelecPow3 = null;
					deviceManage.UnSelecPow4 = null;
					deviceManage.UnSelecPow5 = null;
					deviceManage.UnSelecPow6 = null;
					deviceManage.UnSelecPow7 = null;
					deviceManage.UnSelecPow8 = null;
					deviceManage.getPowDetail(returnAtmCode);
					
					Debug.println("en I must fire it");
					request.setAttribute("AtmPower1", deviceManage.PowAtm1);
					request.setAttribute("AtmPower2", deviceManage.PowAtm2);
					request.setAttribute("AtmPower3", deviceManage.PowAtm3);
					request.setAttribute("AtmPower4", deviceManage.PowAtm4);
					request.setAttribute("AtmPower5", deviceManage.PowAtm5);
					request.setAttribute("AtmPower6", deviceManage.PowAtm6);
					request.setAttribute("AtmPower7", deviceManage.PowAtm7);
					request.setAttribute("AtmPower8", deviceManage.PowAtm8);
					request.setAttribute("AtmUnPower1", deviceManage.UnSelecPow1);
					request.setAttribute("AtmUnPower2", deviceManage.UnSelecPow2);
					request.setAttribute("AtmUnPower3", deviceManage.UnSelecPow3);
					request.setAttribute("AtmUnPower4", deviceManage.UnSelecPow4);
					request.setAttribute("AtmUnPower5", deviceManage.UnSelecPow5);
					request.setAttribute("AtmUnPower6", deviceManage.UnSelecPow6);
					request.setAttribute("AtmUnPower7", deviceManage.UnSelecPow7);
					request.setAttribute("AtmUnPower8", deviceManage.UnSelecPow8);
				} catch (MonitorException e) {
					Debug.println("ATM Map occur errors");
				}
			} else {
				tarJsp = "/DeviceManage/ATMtimercontrol.jsp?RAtmId=00";
			}
			toPage(request, response, tarJsp);
		}
	}

	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String reqCode = request.getParameter("reqCode");
		String tempAtmId = request.getParameter("Atmid");
		String tempMapId = request.getParameter("Mapid");
		String positionX = request.getParameter("Xposition");
		String positionY = request.getParameter("Yposition");
		Rectwidth = request.getParameter("Imgwidth");
		RectHeight = request.getParameter("ImgHeight");

		LoginInfo login = null;
		try {
			//校验用户是否登录
			login = checkLogin(request, response);
			//校验用户操作权限
			if (reqCode != null && reqCode.trim().length() > 0) {
				//加密机的不进行校验
				if (!reqCode.trim().substring(0, 3).equals("210")) {
					LoginManageBean.operValidate(login, reqCode);
				}
				if (reqCode.equals(ATMtimerPower) || reqCode.equals(ATMtimerconfig)) {
					LoginManageBean.operValidate(login, reqCode);
				}
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//取session信息
		HttpSession session = request.getSession(true);
		Vector atmList = (Vector) session.getAttribute("atmList");
		if (atmList == null) {
			atmList = new Vector();
			session.setAttribute("atmList", atmList);
		}
		QueryOption opt = (QueryOption) session.getAttribute("option");
		if (opt == null) {
			opt = new QueryOption();
			session.setAttribute("option", opt);
		} else if (reqCode.equals(ATMPOSUPDATE)) {
			//更新ATM位置信息
			atmPosUpdate(request, response, false, login);
		} else if (reqCode.equals(INSERTENCRYPTINFO)) {
			//新增加密机信息
			try {
				String tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				if (deviceManage.insertEncryptInfo(request, login.getUserName())) {
					//根据查询条件进行查询，得到设备列表
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					//记日志
					writeLog(login, "设备[" + request.getParameter("encryCode") + "]新增成功");
					//转设备参数管理页面
					toPage(request, response, tarJsp);
				} else {
					writeLog(login, "设备[" + request.getParameter("encryCode") + "]新增失败");
					out.print("<Script>alert(\"存储加密机信息失败！\");history.go(-1);</Script>");
				}
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(MODIFYENCRYPTINFO)) {
			//修改加密机信息
			try {
				String tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				if (deviceManage.modifyEncryptInfo(request, login.getUserName())) {
					//根据查询条件进行查询，得到设备列表
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					//记日志
					writeLog(login, "设备[" + request.getParameter("encryCode") + "]修改成功");
					//转设备参数管理页面
					toPage(request, response, tarJsp);
				} else {
					writeLog(login, "设备[" + request.getParameter("encryCode") + "]修改失败");
					out.print("<Script>alert(\"修改加密机信息失败！\");history.go(-1);</Script>");
				}
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(ATMDEVICEMAP)) {
			Debug.println("Make love");
			String tarJsp = "/DeviceManage/ATmGraphMain.jsp";
			Vector oV = new Vector();
			Vector oV1 = new Vector();
			String ImgId = new String();
			try {
				deviceManage.SetAtmCode(tempAtmId);
				oV = deviceManage.queryATMDevice("1"); ;
				Debug.println(oV.size());
				request.setAttribute("AtmIsMaped", oV);
				oV1 = deviceManage.queryATMDevice("0");
				Debug.println(oV1.size());
				ImgId = deviceManage.getTheAgentId(tempAtmId);
				Debug.println(ImgId);
				request.setAttribute("AtmUnMaped", oV1);
			} catch (MonitorException e) {
				Debug.println("ATM Map occur errors");
			}
			Debug.println("ATM Map ok!");
			writeLog(login, "ATM地图设置");
			//转目标页面
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(ATMDeviceAdd)) {
			String tarJsp = "/DeviceManage/MapAddDeviceSuccess.jsp";
			Debug.println("E...n good");
			Debug.println(positionX);
			Debug.println(positionY);
			Debug.print(InputAtmID);
			try {
				Debug.println("Tommorry is Good");
				Debug.println("tempAtmId");
				Debug.println(tempMapId);
				request.setAttribute("AtmImg", tempMapId);
				request.setAttribute("imageXposi", positionX);
				request.setAttribute("imageYposi", positionY);

				deviceManage.getTheMapDetail(tempMapId);
				screentoActureP(Integer.valueOf(positionX).intValue(),
								Integer.valueOf(positionY).intValue(),
								deviceManage.posi_x, deviceManage.posi_y,
								deviceManage.mapcx, deviceManage.mapcy);
								deviceManage.InsertATMMap(InputAtmID, actureXPosi, actureYPosi);
			} catch (MonitorException e) {
				Debug.println("ATM Map Add ATM occur errors");
			}
			toPage(request, response, tarJsp);
		}
	}

	public void screentoActureP(int mapx, int mapy, int x, int y, int mapcx,int mapcy) {
		if (mapcx == 0) {
			mapcx = 500;
		}
		actureXPosi = mapx * mapcx / 834 + x;
		if (mapcy == 0) {
			mapcy = 400;
		}
		actureYPosi = mapcy - mapy * mapcy / 656 + y + 50;
		Debug.println("Gooooooooooooood");
		Debug.print(actureXPosi);
		Debug.print(actureYPosi);
		Debug.println(RectHeight);
		Debug.println(Rectwidth);
	}

	//Clean up resources
	public void destroy() {
	}

	  /**
	   * 加载设备
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void atmLoad(HttpServletRequest request, HttpServletResponse response, boolean isGet, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//取设备编号
		String atmCode = request.getParameter("atmCode");
		//目前只接受Get请求
		if (atmCode == null) {
			initQryPageList(request, login.getOrgID());
			tarJsp = "/DeviceManage/AtmQuery.jsp?post=" + ATMLOAD + "&uid=" + login.getUserID();
		} else {
			try {
				//加载设备
				deviceManage.loadDevice(atmCode);
				//记录日志
				writeLog(login, "设备[" + atmCode + "]加载成功");
				//转成功提示页面
				PromptBean prompt = new PromptBean("设备参数管理");
				prompt.setPrompt("设备加载成功！");
				prompt.setButtonUrl(0, "确定", MANAGE_HOME + "?uid=" + login.getUserID());
				request.setAttribute("prompt", prompt);
				tarJsp = "/Success.jsp?uid=" + login.getUserID();
			} catch (MonitorException exp) {
				//记录日志
				writeLog(login, "设备[" + atmCode + "]加载失败");
				errProc(request, response, exp);
			}
		}
		toPage(request, response, tarJsp);
	}

	  /**
	   * ATM安装位置维护
	   * @param request Http请求
	   * @param response Http响应
	   * @param isGet Get请求标志
	   * @param login 用户信息
	   * @throws ServletException
	   * @throws IOException
	   */
	private void atmPosUpdate(HttpServletRequest request,
							  HttpServletResponse response, boolean isGet,
                              LoginInfo login) throws ServletException, IOException {
		SqlAccess sq = null;
		PreparedStatement stm = null;
		try {
			Vector posList = fechPosInfo(request);
			sq = SqlAccess.createConn();
			sq.setAutoCommit(false);
			stm = sq.conn.prepareStatement("UPDATE monit_atmposition SET " + "atm_point_x=?,atm_point_y=?,fix_flg=? WHERE atm_id=?");
			//依次更新ATM位置
			String faultInfo = "";
			for (int i = 0; i < posList.size(); i++) {
				AtmPosInfo posInfo = (AtmPosInfo) posList.get(i);
				try {
					stm.setInt(1, posInfo.getAtmPointX());
					stm.setInt(2, posInfo.getAtmPointY());
					stm.setString(3, posInfo.getInstallFlag());
					stm.setString(4, posInfo.getAtmId());
					stm.executeUpdate();
					sq.commit();
					//通知客户端ATM位置更新
					Debug.println("********通知客户端设备位置更新:" + posInfo.getAtmId() + "***********"); 	
					notifyClient("N20004", posInfo.getAtmId(), "3");
				} catch (SQLException e1) {
					faultInfo += "[" + posInfo.getAtmId() + "]" + e1.getMessage() + "\n";
				}
			}
			//记录日志
			writeLog(login, "安装地点维护");
			//全部更新成功
			if (faultInfo.equals("")) {
				faultInfo = "安装地点维护成功";
				//将处理结果回送客户端
			}
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			out.println("<Deal>");
			out.println("<DealCode>" + ATMPOSUPDATE + "</DealCode>");
			out.println("<ErrorCode>0000</ErrorCode>");
			out.println("<ErrorDesc>" + faultInfo + "</ErrorDesc>");
			out.println("</Deal>");
		} catch (SQLException e2) {
			//记录日志
			writeLog(login, "安装地点维护失败");
			MonitorException exp = new MonitorException(e2);
			errProc(request, response, exp);
		} catch (MonitorException e3) {
			//记录日志
			writeLog(login, "安装地点维护失败");
			errProc(request, response, e3);
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}

	  /**
	   * 初始化设备查询页面所需的列表信息
	   * @param req Http请求对象
	   * @param orgId 机构编码
	   */
	private void initQryPageList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//设备类型列表
			v = CodeManageBean.queryCodes(CodeDefine.ATM_TYPE, sq);
			req.setAttribute("atmTypeList", v);
			//安装地点类别列表
			v = CodeManageBean.queryCodes(CodeDefine.ADDR_TYPE, sq);
			req.setAttribute("addrTypeList", v);
			//机构列表
			List list = CodeManageBean.queryOrgList(orgId, sq.conn);
			sq.close();
			req.setAttribute("orgList", list);
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			if (sq != null) {
				sq.close();
			}
		}	
	}

	  /**
	   * 从Http请求中取ATM安装位置信息
	   * @param req Http请求
	   * @return ATM位置信息列表
	   * @throws MonitorException
	   */
	private Vector fechPosInfo(HttpServletRequest req) throws MonitorException {
		Vector retV;
		StringBuffer buf = new StringBuffer();
		//取ATM安装位置信息（XML格式）
		String sData = req.getParameter("data");
		if (sData == null) {
			sData = "";
		}
		buf.append(sData);
		//解析报文
		MessageParse parser = new MessageParse(buf);
		try {
			parser.xmlParse();
			List atmPosList = parser.getRootData();

			retV = new Vector(atmPosList.size());
			for (int i = 0; i < atmPosList.size(); i++) {
				Element elAtmPos = (Element) atmPosList.get(i);
				Element elPos = elAtmPos.getChild("Pos");
				Element elInstallFlag = elAtmPos.getChild("FixFlag");

				AtmPosInfo posInfo = new AtmPosInfo();
				posInfo.setAtmId(elAtmPos.getAttribute("id").getValue());
				posInfo.setAtmPointX(elPos.getAttribute("x").getIntValue());
				posInfo.setAtmPointY(elPos.getAttribute("y").getIntValue());
				//上送的安装操作类型为"0"(删除),对应与位置信息表的安装标志"0"(不安装)
				if (elInstallFlag.getText().equals("0")) {
					posInfo.setInstallFlag("0");
					//上送的安装操作类型为"1"(修改),"2"(增加),对应与位置信息表的安装标志"1"(安装)
				} else {
					posInfo.setInstallFlag("1");
				}
				retV.add(posInfo);
			}
		} catch (DataConversionException e2) {
			throw new MonitorException(ErrorDefine.DATA_ERROR, "解析上送数据失败");
		}
		return retV;
	}

	//设备参数管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/deviceconfig";

}
