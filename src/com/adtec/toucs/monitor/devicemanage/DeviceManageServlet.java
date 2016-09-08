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
 * <p>Title: �豸��������Servlet��</p>
 * <p>Description: �����豸����������ص�Http����</p>
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

	//�����룺�����豸
	private static final String ATMLOAD = "12003";

	//������:�����豸λ����Ϣ
	private static final String ATMPOSUPDATE = "15006";

	//������:�������ܻ���Ϣ
	private static final String INSERTENCRYPTINFO = "21001";
	
	//������:���ü��ܻ�
	private static final String STARTENCRYPT = "21002";

	//������:ͣ�ü��ܻ�
	private static final String STOPENCRYPT = "21003";

	//������:ɾ�����ܻ�
	private static final String DELETEENCRYPTINFO = "21004";

	//������:��ѯ���ܻ���Ϣ
	private static final String QUERYENCRYPTINFO = "21006";

	//������:��ѯ���ܻ���Ϣ
	private static final String MODIFYENCRYPTINFO = "21007";

	//ATM�豸��ͼ��Ϣ����
	private static final String ATMDEVICEMAP = "21008";

	//ATM�豸��ͼ��Ϣ���
	private static final String ATMDeviceAdd = "21009";

	//Atmʱ��Ȩ������
	private static final String ATMtimerPower = "21010";

	//Atmʱ��ʱ������
	private static final String ATMtimerconfig = "21011";

	public int actureXPosi;
	public int actureYPosi;
	public String Rectwidth;
	public String RectHeight;
	public String InputAtmID;

	//�豸������ʵ��
	private static DeviceManageBean deviceManage = new DeviceManageBean();

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		String tempAtmId = request.getParameter("Atmid");
		String returnAtmCode = request.getParameter("Atmcode");
		String isAtmChecked = request.getParameter("isAtm");
		//�û����У��
		LoginInfo login = null;
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				//���ܻ��Ĳ�����У��
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

		//ȡ�豸���
		String atmCode = request.getParameter("atmCode");
		//Ŀ��JSPҳ��
		String tarJsp = null;
		Debug.print("QUERYENCRYPTINFO" + reqCode);
		//�豸������������
		//�����豸
		if (reqCode.equals(ATMLOAD)) {
			atmLoad(request, response, true, login);
		} else if (reqCode.equals(QUERYENCRYPTINFO)) {
			//��ѯ���ܻ���Ϣ
			try {
				tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				//���ݲ�ѯ�������в�ѯ���õ��豸�б�
				request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
				//ת�豸��������ҳ��
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}   else if (reqCode.equals(DELETEENCRYPTINFO)) {
			//ɾ�����ܻ���Ϣ
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				if (deviceManage.deleteEncryptInfo(atmCode)) {
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					writeLog(login, "�豸[" + atmCode + "]ɾ���ɹ�");
				} else {
					writeLog(login, "�豸[" + atmCode + "]ɾ��ʧ��");
				}
			} catch (MonitorException e) {
				e.printStackTrace();
			}
			//תĿ��ҳ��
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(STOPENCRYPT)) {
			//ͣ�ü��ܻ�
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				if (deviceManage.stopEncrypt(atmCode)) {
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					writeLog(login, "�豸[" + atmCode + "]ͣ�óɹ�");
				} else {
					writeLog(login, "�豸[" + atmCode + "]ͣ��ʧ��");
				}
			} catch (MonitorException e) {
				e.printStackTrace();
			}
			//תĿ��ҳ��
			toPage(request, response, tarJsp);
		} else if (reqCode.equals(STARTENCRYPT)) {
			//���ü��ܻ�
			tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
			try {
				deviceManage.loadEncrypt(atmCode);
				request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
				writeLog(login, "�豸[" + atmCode + "]ͣ�óɹ�");
			} catch (MonitorException e) {
				writeLog(login, "�豸[" + atmCode + "]ͣ��ʧ��");
				e.printStackTrace();
				errProc(request, response, e);
			}
			//תĿ��ҳ��
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
			writeLog(login, "ATM��ͼ����");
			//תĿ��ҳ��
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
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				//���ܻ��Ĳ�����У��
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

		//ȡsession��Ϣ
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
			//����ATMλ����Ϣ
			atmPosUpdate(request, response, false, login);
		} else if (reqCode.equals(INSERTENCRYPTINFO)) {
			//�������ܻ���Ϣ
			try {
				String tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				if (deviceManage.insertEncryptInfo(request, login.getUserName())) {
					//���ݲ�ѯ�������в�ѯ���õ��豸�б�
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					//����־
					writeLog(login, "�豸[" + request.getParameter("encryCode") + "]�����ɹ�");
					//ת�豸��������ҳ��
					toPage(request, response, tarJsp);
				} else {
					writeLog(login, "�豸[" + request.getParameter("encryCode") + "]����ʧ��");
					out.print("<Script>alert(\"�洢���ܻ���Ϣʧ�ܣ�\");history.go(-1);</Script>");
				}
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else if (reqCode.equals(MODIFYENCRYPTINFO)) {
			//�޸ļ��ܻ���Ϣ
			try {
				String tarJsp = "/DeviceManage/EncryptInfoManage.jsp";
				if (deviceManage.modifyEncryptInfo(request, login.getUserName())) {
					//���ݲ�ѯ�������в�ѯ���õ��豸�б�
					request.setAttribute("encryptInfo", deviceManage.queryEncryptInfo());
					//����־
					writeLog(login, "�豸[" + request.getParameter("encryCode") + "]�޸ĳɹ�");
					//ת�豸��������ҳ��
					toPage(request, response, tarJsp);
				} else {
					writeLog(login, "�豸[" + request.getParameter("encryCode") + "]�޸�ʧ��");
					out.print("<Script>alert(\"�޸ļ��ܻ���Ϣʧ�ܣ�\");history.go(-1);</Script>");
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
			writeLog(login, "ATM��ͼ����");
			//תĿ��ҳ��
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
	   * �����豸
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void atmLoad(HttpServletRequest request, HttpServletResponse response, boolean isGet, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//ȡ�豸���
		String atmCode = request.getParameter("atmCode");
		//Ŀǰֻ����Get����
		if (atmCode == null) {
			initQryPageList(request, login.getOrgID());
			tarJsp = "/DeviceManage/AtmQuery.jsp?post=" + ATMLOAD + "&uid=" + login.getUserID();
		} else {
			try {
				//�����豸
				deviceManage.loadDevice(atmCode);
				//��¼��־
				writeLog(login, "�豸[" + atmCode + "]���سɹ�");
				//ת�ɹ���ʾҳ��
				PromptBean prompt = new PromptBean("�豸��������");
				prompt.setPrompt("�豸���سɹ���");
				prompt.setButtonUrl(0, "ȷ��", MANAGE_HOME + "?uid=" + login.getUserID());
				request.setAttribute("prompt", prompt);
				tarJsp = "/Success.jsp?uid=" + login.getUserID();
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "�豸[" + atmCode + "]����ʧ��");
				errProc(request, response, exp);
			}
		}
		toPage(request, response, tarJsp);
	}

	  /**
	   * ATM��װλ��ά��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
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
			//���θ���ATMλ��
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
					//֪ͨ�ͻ���ATMλ�ø���
					Debug.println("********֪ͨ�ͻ����豸λ�ø���:" + posInfo.getAtmId() + "***********"); 	
					notifyClient("N20004", posInfo.getAtmId(), "3");
				} catch (SQLException e1) {
					faultInfo += "[" + posInfo.getAtmId() + "]" + e1.getMessage() + "\n";
				}
			}
			//��¼��־
			writeLog(login, "��װ�ص�ά��");
			//ȫ�����³ɹ�
			if (faultInfo.equals("")) {
				faultInfo = "��װ�ص�ά���ɹ�";
				//�����������Ϳͻ���
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
			//��¼��־
			writeLog(login, "��װ�ص�ά��ʧ��");
			MonitorException exp = new MonitorException(e2);
			errProc(request, response, exp);
		} catch (MonitorException e3) {
			//��¼��־
			writeLog(login, "��װ�ص�ά��ʧ��");
			errProc(request, response, e3);
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}

	  /**
	   * ��ʼ���豸��ѯҳ��������б���Ϣ
	   * @param req Http�������
	   * @param orgId ��������
	   */
	private void initQryPageList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//�豸�����б�
			v = CodeManageBean.queryCodes(CodeDefine.ATM_TYPE, sq);
			req.setAttribute("atmTypeList", v);
			//��װ�ص�����б�
			v = CodeManageBean.queryCodes(CodeDefine.ADDR_TYPE, sq);
			req.setAttribute("addrTypeList", v);
			//�����б�
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
	   * ��Http������ȡATM��װλ����Ϣ
	   * @param req Http����
	   * @return ATMλ����Ϣ�б�
	   * @throws MonitorException
	   */
	private Vector fechPosInfo(HttpServletRequest req) throws MonitorException {
		Vector retV;
		StringBuffer buf = new StringBuffer();
		//ȡATM��װλ����Ϣ��XML��ʽ��
		String sData = req.getParameter("data");
		if (sData == null) {
			sData = "";
		}
		buf.append(sData);
		//��������
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
				//���͵İ�װ��������Ϊ"0"(ɾ��),��Ӧ��λ����Ϣ��İ�װ��־"0"(����װ)
				if (elInstallFlag.getText().equals("0")) {
					posInfo.setInstallFlag("0");
					//���͵İ�װ��������Ϊ"1"(�޸�),"2"(����),��Ӧ��λ����Ϣ��İ�װ��־"1"(��װ)
				} else {
					posInfo.setInstallFlag("1");
				}
				retV.add(posInfo);
			}
		} catch (DataConversionException e2) {
			throw new MonitorException(ErrorDefine.DATA_ERROR, "������������ʧ��");
		}
		return retV;
	}

	//�豸���������·��
	private static final String MANAGE_HOME = "/ToucsMonitor/deviceconfig";

}
