package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title:POS�̻�����Servlet�� </p>
 * <p>Description: ����POS�̻�������ص�Http����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */

public class POSMerchantServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	
	/**
	 *���������
	 */
	//�����룺��ȡ�̻�����
	private static final String POSMERCHANTADD = "10401";
	
	//�����룺�̻��޸�
	private static final String POSMERCHANTUPDATE = "10402";
	
	//�����룺�̻���ѯ
	private static final String POSMERCHANTQUERY = "10404";
	
	//�����룺�̻�ɾ��
	private static final String POSMERCHANTDELETE = "10403";
	
	//�����룺�齱
	private static final String CheckPrize = "10422";
	
	//������������
	private static final String PARMGETPAGE = "page";
	private static final String PARMGETQUERYPAGE = "queryPage";
	private static final String PARMCLEARFLAG = "clear";
	private static final String PARMNORMFLAG = "normal";
	private static final String CheckPZ = "prize";
	
	//�齱�����ѯ
	private static final String PRIZERULEQUERY = "RuleQry";
	
	/**
	 *��������
	 */
	//POS�̻������·��
	private static final String MANAGE_HOME = "/ToucsMonitor/posmerchantconfig";
	
	//POS����ҳ���·��
	private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
	
	//POS�̻�������ʵ��
	private POSMerchantBean posMerchant = new POSMerchantBean();
	
	public void init() throws ServletException {
	}
	
	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		response.setHeader("Cache-Control", "no-store");
		String target = "";
		target = request.getParameter("target");
		//�û����У��
		LoginInfo login = null;
		//Ȩ�޿���
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			Debug.println("********CHECK LOGIN********");
			//У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//ȡsession��Ϣ
		HttpSession session = request.getSession(true);
		//Ŀ��JSPҳ��
		String tarJsp = null;
		//�̻���������
		if (reqCode == null) {
			POSMerchantInfo Info = (POSMerchantInfo) session.getAttribute(
			"posMerchant");
			if (Info != null) {
				try {
					List l = posMerchant.getMerchantByOrg(Info.getOrgid());
					request.setAttribute("posMerchantList", l);
				}catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			} else {
				Debug.println("**********posMerchantInfoList get Null***************");
			}
			//Ҫ���ݵ�ǰ�û���ӵ���̻�����Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosMerchantManage.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//��ʾ�齱ҳ��
			if (reqCode.equals(CheckPrize)) {
				if ( (target == null) || target.trim().equals("")) {
					if (LoginManageBean.checkPermMask(login.getPermission(), CheckPrize) == 0) {
						request.setAttribute("CheckPrize", "1");
					}
					tarJsp = PAGE_HOME + "CheckPrizeMain.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
				if (target != null && target.equals("checkPZAdded")) {
					Debug.println("Ok man");
					CheckPrizeAdd(request, response, false, login);
				}
				if (target != null && target.equals(CheckPZ)) {
					Debug.println("good Man");
					CheckPrizeAdd(request, response, true, login);
				}
				if (target != null && target.equals("prizeQuery")) {
					Debug.println("good Man 1028");
					setCheckPZPerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "QueryCheckPrizeInput.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
				//�齱�����ѯ
				if (target != null && target.equals(PRIZERULEQUERY)) {
					String channelId = "010310";
					PrizeRule pz = null;
					Vector pi = null;
					try {
						pi = posMerchant.queryPrizeInfo(channelId);
						pz = posMerchant.queryPrizeRule(channelId);
					}catch (MonitorException ex) {
						ex.printStackTrace();
						System.out.println(ex.getMessage());
					}
					request.setAttribute("PrizeInfo", pi);
					request.setAttribute("PrizeRule", pz);
					
					tarJsp = PAGE_HOME + "PrizeRuleInfo.jsp?uid=" + login.getUserID();
					toPage(request, response, tarJsp);
				}
			}else if (reqCode.equals(POSMERCHANTADD)) {//��ȡ����̻�ҳ��
				if ( (target == null) || target.trim().equals("")) {
					posMerchantAdd(request, response, false, login);
				}
				if (target != null && target.equals(PARMGETPAGE)) {
					posMerchantAdd(request, response, true, login);
				}
			}else if (reqCode.equals(POSMERCHANTUPDATE)) {	//��ȡ�޸��̻�ҳ��
				if ( (target == null) || target.trim().equals("")) {
					posMerchantUpdate(request, response, login);
				}else {
					if (target.equals(PARMGETPAGE)) {
						posMerchantUpdateQuery(request, response, true, login);
					}
					if (target.equals(PARMGETQUERYPAGE)) {
						posMerchantUpdateQuery(request, response, false, login);
					}
					if (target.equals(PARMCLEARFLAG)) {
						posMerchantClear(request, response, login, "clear");
					}
					if (target.equals(PARMNORMFLAG)) {
						Debug.println("******wuye debug in normal request *********");		
						posMerchantClear(request, response, login, "normal");
					}
				}
			}else if (reqCode.equals(POSMERCHANTQUERY)) {//��ѯ�̻�
				if ( (target == null) || target.trim().equals("")) {
					posMerchantQuery(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						posMerchantQueryPage(request, response, login);
					}
				}
			}else if (reqCode.equals(POSMERCHANTDELETE)) {//ɾ���̻�
				Debug.println("ɾ���̻���Ϣ...POST����");
				posMerchantDelete(request, response, login);
			}
		}
	}
	
	//Process the HTTP Post request
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		//ȡ�������
		String reqCode = request.getParameter("reqCode");
		//ȡ�������
		String target = "";
		target = request.getParameter("target");
		String queryDate = "";
		queryDate = request.getParameter("dateInput");
		Debug.println("1027 it is a good");
		Debug.println(queryDate);

		//Ȩ�޿���
		LoginInfo login = null;
		try {
			//У���û��Ƿ��¼
			login = checkLogin(request, response);
			//У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//ȡsession��Ϣ
		HttpSession session = request.getSession(true);
		POSMerchantInfo Info = (POSMerchantInfo) session.getAttribute("posMerchant");
		if (Info != null) {
			try {
				List l = posMerchant.qryOrgPosMerchant(Info.getOrgid());
				request.setAttribute("posMerchantList", l);
			} catch (MonitorException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		} else {
			Vector posMerchantList = new Vector();
			session.setAttribute("posMerchantList", posMerchantList);
			Debug.println("**********posMerchantList post Null***************");
		}
		//����̻�
		if (reqCode.equals(POSMERCHANTADD)) {
			if ( (target == null) || target.trim().equals("")) {
				posMerchantAdd(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantAdd(request, response, true, login);
				}
			}
		}else if (reqCode.equals(CheckPrize)) {//��ӳ齱
			if (target != null && target.equals("checkPZAdded")) {
				Debug.println("Ok man");
				CheckPrizeAdd(request, response, false, login);
			}
			if (target != null && target.equals(CheckPZ)) {
				Debug.println("good Man");
				CheckPrizeAdd(request, response, true, login);
			}
			if (target != null && target.equals("Modifypzinfo")) {
				Debug.println("yes do it");
				ModifyMerchantPZinfo(request, response, false, login);
			}
			if (target != null && target.equals("checkPZQuery")) {
				try {
					Debug.println("1027");
					Vector V1 = posMerchant.getCheckPrizeQuery(queryDate);
					String tarJsp = "/ToucsMonitor/POSDeviceManage/QueryCheckPrize.jsp?uid=" + login.getUserID();
					request.setAttribute("CheckPQueryVect", V1);
					toPage(request, response, tarJsp);
				}catch (MonitorException exp) {
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}
			}
		}else if (reqCode.equals(POSMERCHANTUPDATE)) {//�޸��̻�
			if ( (target == null) || target.trim().equals("")) {
				posMerchantUpdate(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantUpdateQuery(request, response, true, login);
				}
				if (target.equals(PARMGETQUERYPAGE)) {
					posMerchantUpdateQuery(request, response, false, login);
				}
				if (target.equals(PARMCLEARFLAG)) {
					posMerchantClear(request, response, login, "clear");
				}
			}
		}else if (reqCode.equals(POSMERCHANTQUERY)) {//��ѯ�̻�
			if ( (target == null) || target.trim().equals("")) {
				posMerchantQuery(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					posMerchantQueryPage(request, response, login);
				}
			}
		}else if (reqCode.equals(POSMERCHANTDELETE)) {//ɾ���̻�
			posMerchantDelete(request, response, login);
		}
	}
	
	//Clean up resources
	public void destroy() {
	}
	
	/**
	 * �����û���ҳ��Ĳ���Ȩ��
	 * @param req Http����
	 * @param maskCode �û�Ȩ����
	 */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTADD) == 0) {
			req.setAttribute("POSMERCHANTADD", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTUPDATE) == 0) {
			req.setAttribute("POSMERCHANTUPDATE", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTDELETE) == 0) {
			req.setAttribute("POSMERCHANTDELETE", "1");
		}
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTQUERY) == 0) {
			req.setAttribute("POSMERCHANTQUERY", "1");
		}
	}
	
	/**
	 * �����û���ҳ��Ĳ���Ȩ��
	 * @param req Http����
	 * @param maskCode �û�Ȩ����
	 */
	private void setCheckPZPerm(HttpServletRequest req, String maskCode) {
		if (LoginManageBean.checkPermMask(maskCode, POSMERCHANTADD) == 0) {
			req.setAttribute("CheckPrize", "1");
		}
	}
	
	/**
	 * ��ʼ���齱���ҳ��������б���Ϣ
	 * @param req Http����
	 * @param orgId ��������
	 */
	private void initCheckPZAddList(HttpServletRequest req) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			v = CodeManageBean.queryCodes("0567", sq);
			req.setAttribute("channelIdList", v);
			//�̻�����
			v = CodeManageBean.queryCodes("0553", sq);
			req.setAttribute("McttypeList", v);
			//ʹ�ñ�־
			v = CodeManageBean.queryCodes("0554", sq);
			req.setAttribute("FlagList", v);
			//�Ƿ�����Ԥ���ѿ�
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			//�Ƿ�ָ���̻���־
			v = CodeManageBean.queryCodes("0222", sq);
			req.setAttribute("IsMerchantList", v);
			v = CodeManageBean.queryCodes("0223", sq);
			req.setAttribute("IsCardTypeList", v);
			v = CodeManageBean.queryMerchantCodes(sq);
			req.setAttribute("AllmerchantList", v);
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	/**
	 * ��ʼ���̻����ҳ��������б���Ϣ
	 * @param req Http����
	 * @param orgId ��������
	 */
	private void initMerchantAddList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//�̻�����
			v = CodeManageBean.queryCodes("0553", sq);
			req.setAttribute("McttypeList", v);
			//ʹ�ñ�־
			v = CodeManageBean.queryCodes("0554", sq);
			req.setAttribute("FlagList", v);
			//�����־
			v = CodeManageBean.queryCodes("0555", sq);
			req.setAttribute("ClearFlagList", v);
			//�̻�����������
			v = CodeManageBean.queryCodes("0569", sq);
			req.setAttribute("FeeType", v);
			//�Ƿ�����Ԥ���ѿ�
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			
			//�����б�
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
			
			//�������б����������
			List cardOrgList = null;
			if (list != null) {
				cardOrgList = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					CodeBean code = (CodeBean) list.get(i);
					if (code.getCodeId().substring(5, 9).equals("3600") ||
							code.getCodeId().substring(3, 9).equals("363700") ||
							code.getCodeId().substring(5, 9).equals("1900" ) ) {
						cardOrgList.add(code);
					}
				}
			}			
			req.setAttribute("cardOrgList", cardOrgList);
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	/**
	 * ��ʼ���̻����ҳ��������б���Ϣ
	 * @param req Http����
	 * @param orgId ��������
	 */
	private void initMerchantQueryList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			//�Ƿ�����Ԥ���ѿ�
			v = CodeManageBean.queryCodes("0599", sq);
			req.setAttribute("Operator", v);
			
			//�����б�
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
			//֧�У������������б�
			if (list != null) {
				String[] branchs = new String[list.size()];
				//�Ӳ�ѯ�õ�������������ȡ�����ϼ�����������֧�У����
				for (int i = 0; i < branchs.length; i++) {
					CodeBean code = (CodeBean) list.get(i);
					branchs[i] = code.getCodeType();
				}
				req.setAttribute("branchs", branchs);
			}
		}catch (Exception exp) {
			exp.printStackTrace();
			System.out.println(exp.getMessage());
		} finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	
	private void ModifyMerchantPZinfo(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		
		String tarJsp = null;
		//�̻����ҳ������
		if (isGetPage) {
			initCheckPZAddList(request);
			tarJsp = PAGE_HOME + "CheckPZAdd.jsp?post=" + CheckPrize + "&uid=" + login.getUserID(); ;
		}else {
			CheckPrizeRuleInfo CheckPZInfo = new CheckPrizeRuleInfo();
			//��Http������ȡ�̻�������Ϣ
			CheckPZInfo.fetchModifyPZinfoData(request);
			try {
				Debug.println("Shooter");
				posMerchant.ModifyCheckPZRule(CheckPZInfo);		
				PrizeRule pz = null;
				//��¼��־
				Debug.println("Shooter");
				Vector oV = new Vector();
				String channelId = request.getParameter("channel_id");
				oV = posMerchant.queryPrizeInfo(channelId);
				pz = posMerchant.queryPrizeRule(channelId);
				writeLog(login, "�޸���ϸ�̻��齱����");
				//ת�ɹ���ʾҳ��
				PromptBean prompt = new PromptBean("���Լ����޸��̻��齱����");
				prompt.setPrompt("��ѡ��Ҫ�޸ĵ��̻��ĳ齱����");
				prompt.setButtonUrl(0, "������ӳ齱����", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				request.setAttribute("PrizeRule", pz);
				request.setAttribute("PrizeInfoV", oV);
				tarJsp = "/ToucsMonitor/SuccessAddMerchantRule.jsp?" + "&uid=" + login.getUserID();
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "�̻���ϸ�齱�������ʧ��");
				errProc(request, response, exp);
			}
			
			Debug.println("In the New World Guys fetchData");
			//��¼��־
			writeLog(login, "��ӳ齱����ɹ�");
		}
		//תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * �齱������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void CheckPrizeAdd(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		String tarJsp = null;
		//�̻����ҳ������
		if (isGetPage) {
			initCheckPZAddList(request);
			tarJsp = PAGE_HOME + "CheckPZAdd.jsp?post=" + CheckPrize + "&uid=" + login.getUserID(); ;
		} else {
			CheckPrizeRuleInfo CheckPZInfo = new CheckPrizeRuleInfo();
			//��Http������ȡ�̻�������Ϣ
			CheckPZInfo.fetchData(request);
			if (CheckPZInfo.getisPrizetype().equalsIgnoreCase("1")) {
				try {
					posMerchant.addCheckPZRule(CheckPZInfo);
					PrizeRule pz = null;
					//��¼��־
					Vector oV = new Vector();
					String channelId = request.getParameter("channel_id");
					oV = posMerchant.queryPrizeInfo(channelId);
					pz = posMerchant.queryPrizeRule(channelId);
					writeLog(login, "�����ϸ�̻��齱����");
					//ת�ɹ���ʾҳ��
					PromptBean prompt = new PromptBean("�����ϸ�̻��齱������ϸ");
					prompt.setPrompt("����д�����̻��ĳ齱����");
					request.setAttribute("prompt", prompt);
					request.setAttribute("PrizeRule", pz);
					request.setAttribute("PrizeInfoV", oV);
					tarJsp = "/ToucsMonitor/SuccessAddMerchantRule.jsp";
				}catch (MonitorException exp) {
					//��¼��־
					writeLog(login, "�̻���ϸ�齱�������ʧ��");
					errProc(request, response, exp);
				}
			}else {
				Debug.println("In the New World Guys fetchData");
				try {
					posMerchant.addCheckPZRule(CheckPZInfo);
					//��¼��־
					writeLog(login, "��ӳ齱����ɹ�");
					tarJsp = "/pzsucessed.html";
				}catch (MonitorException exp) {
					//��¼��־
					writeLog(login, "�齱�������ʧ��");
					errProc(request, response, exp);
				}
			}
		}
		//תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * �̻����������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantAdd(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage, LoginInfo login) throws
			ServletException, IOException {
		String tarJsp = null;
		//�̻����ҳ������
		if (isGetPage) {
			initMerchantAddList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosMerChantAdd.jsp?post=" + POSMERCHANTADD + "&uid=" + login.getUserID();
		} else {
			POSMerchantInfo posMerchantInfo = new POSMerchantInfo();
			//��Http������ȡ�̻�������Ϣ
			posMerchantInfo.fetchData(request);
			Debug.println("zipCode=[" + posMerchantInfo.getZipcode() + "]");
			try {
				posMerchant.addPosMerchant(posMerchantInfo);
				//��¼��־
				writeLog(login, "�̻�[" + posMerchantInfo.getMerchantid() + "]��ӳɹ�");	
				//add by liyp 20030917
				//֪ͨ�ͻ����������豸
				notifyClient("N20006", posMerchantInfo.getMerchantid(), "2");			
				//ת�ɹ���ʾҳ��
				PromptBean prompt = new PromptBean("�̻�����");
				prompt.setPrompt("�̻���ӳɹ���!");
				prompt.setButtonUrl(0, "��������", MANAGE_HOME);
				request.setAttribute("prompt", prompt);
				tarJsp = "/ToucsMonitor/Success.jsp";
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "�̻����ʧ��");
				errProc(request, response, exp);
			}
		}
		//תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}
	
	/**
	 * �̻���ѯ������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantQuery(HttpServletRequest request,
			HttpServletResponse response,
			LoginInfo login) throws ServletException,
			IOException {
		//ȡ�̻����
		String merchantId = request.getParameter("merchant_id");
		System.out.println("merchant_id:" + merchantId);
		//ȡ��ѯĿ�ģ��޸ġ���ѯ��
		if (merchantId != null && !merchantId.equals("")) {
			String tarJsp = null;
			try {
				//��ѯ�̻���ϸ��Ϣ
				POSMerchantInfo posMerchantInfo = posMerchant.
				qryPosMerchant(merchantId);
				if (posMerchantInfo == null) {
					throw new MonitorException(ErrorDefine.RECNOTFOUND, "�̻������ڣ�");
				}
				initMerchantAddList(request, login.getOrgID());
				tarJsp = PAGE_HOME + "PosMerchantInfo.jsp";
				request.setAttribute("posMerchantInfo", posMerchantInfo);
				toPage(request, response, tarJsp);
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		} else {
			try {
				//��ѯ���������������̻���Ϣ
				String organNo = request.getParameter("orgId");
				String merchName = request.getParameter("merchant_name");//�̻����ƿ���Ϊ�գ�Ӧ����ж�20121106 syl
				String operator = request.getParameter("operator");//�Ƿ�����Ԥ����Ҳ�ɲ�ѡ��ӦΪȫ�������ж� 20121106 syl
				List l = posMerchant.getMerchantByOrgAndName(organNo, merchName, operator);
				request.setAttribute("posMerchantList", l);
				HttpSession session = request.getSession(true);
				POSMerchantInfo info = new POSMerchantInfo();
				info.setOrgid(organNo);
				session.setAttribute("posMerchant", info);
				//���õ�ǰ�û��Ĳ���Ȩ�ޣ����ӡ��޸ġ�ɾ������ѯ������
				toPage(request, response, PAGE_HOME + "PosMerchantManage.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
	
	/**
	 * �̻���ѯ������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantQueryPage(HttpServletRequest request,
			HttpServletResponse response,
			LoginInfo login) throws ServletException,
			IOException {
		initMerchantQueryList(request, "110000000");
		toPage(request, response, PAGE_HOME + "PosMerchantQuery.jsp?post=" + POSMERCHANTQUERY + "&uid=" + login.getUserID());
	}
	
	/**
	 * �̻��޸Ĳ�ѯ������
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantUpdateQuery(HttpServletRequest request,
			HttpServletResponse response,
			boolean isGetPage,
			LoginInfo login) throws ServletException,
			IOException {
		//ȡ�̻����
		String merchantId = request.getParameter("merchant_id");
		System.out.println("merchant_id:" + merchantId);
		//ȡ��ѯ���ݣ�������Ϣ��
		if (!isGetPage) {
			if (merchantId != null) {
				String tarJsp = null;
				//��ѯ�̻�������Ϣ
				try {
					//��ѯ�̻�������Ϣ
					POSMerchantInfo posMerchantInfo = posMerchant.
					qryPosMerchant(merchantId);
					if (posMerchantInfo == null) {
						throw new MonitorException(ErrorDefine.RECNOTFOUND, "�̻������ڣ�");
					}
					initMerchantAddList(request, login.getOrgID());
					tarJsp = PAGE_HOME + "PosMerChantUpdate.jsp?post=" +
					POSMERCHANTUPDATE;
					System.out.println("��ѯ���޸ĵ����ݣ�ָ��" + tarJsp);
					request.setAttribute("posMerchantInfo", posMerchantInfo);
					toPage(request, response, tarJsp);
				}catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//marked by liyp 20030904
			initMerchantQueryList(request, "110000000");
			toPage(request, response, PAGE_HOME + "PosMerchantUpdateQuery.jsp?" + "&uid=" + login.getUserID());
			
		}
	}
	
	/**
	 * �޸��̻�������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantUpdate(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws
			ServletException, IOException {
		POSMerchantInfo info = new POSMerchantInfo();
		//������������ȡ��Ҫ�޸ĵ��̻�������Ϣ
		info.fetchData(request);
		//��ȡ����
		String key = request.getParameter("key");
		try {
			//�޸��̻�������Ϣ
			posMerchant.updatePosMerchant(info, key);
			//��¼��־
			writeLog(login, "�̻�[" + key + "]��Ϣ�޸ĳɹ�");
			
			//֪ͨ�ͻ����޸��̻���Ϣ modify by liyp 20030918
			//notifyClient("N20004",key,"1");
			notifyClient("N20006", key, "1");
			
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("�̻�����");
			prompt.setPrompt("�̻���Ϣ�޸ĳɹ���");
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "�̻�[" + key + "]��Ϣ�޸ĳɹ�");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * �޸��̻�������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantDelete(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login) throws
			ServletException, IOException {
		POSMerchantInfo info = new POSMerchantInfo();
		//������������ȡ��Ҫ�޸ĵ��̻�������Ϣ
		info.fetchData(request);
		String key = request.getParameter("merchant_id");
		try {
			//�޸��̻�������Ϣ
			int nRst = posMerchant.deletePosMerchant(key);
			PromptBean prompt = new PromptBean("�̻�����");
			if (nRst == -1) {
				prompt.setPrompt("�̻���Ϣ��POS�豸��Ϣ��ɾ�����ɹ���");
			}
			if (nRst == 0) {
				prompt.setPrompt("�̻������־Ϊ��������״̬��ɾ�����ɹ���");
				System.out.println("�̻������־Ϊ��������״̬��ɾ�����ɹ���");
			}
			if (nRst > 0) {
				//��¼��־
				writeLog(login, "�̻�[" + key + "]ɾ���ɹ�");
				//֪ͨ�ͻ��� add by liyp 20030918
				notifyClient("N20006", key, "0");
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("�̻���Ϣɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "�̻�[" + key + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}
	
	/**
	 * �޸��̻�������Ϣ
	 * @param request Http����
	 * @param response Http��Ӧ
	 * @param isGet Get�����־
	 * @param login �û���Ϣ
	 * @throws ServletException
	 * @throws IOException
	 */
	private void posMerchantClear(HttpServletRequest request,
			HttpServletResponse response, LoginInfo login,
			String clearFlag) throws ServletException,
			IOException {
		String key = request.getParameter("merchant_id");
		String flag = clearFlag;
		Debug.println("********wuye debug clearFlag = " + flag + "*********");
		try {
			//�޸��̻������־��Ϣ
			System.out.println("&&&&&DELETE BEGIN&&&&&");
			PromptBean prompt = new PromptBean("�̻�����");
			if (flag == "clear" || flag == "") {
				int nRst = posMerchant.clearPosMerchant(key);
				if (nRst > 0) {
					//��¼��־
					writeLog(login, "�̻�[" + key + "]��Ϣ�޸ĳɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("�̻������־���óɹ���");
				}
			} else if (flag == "normal") {
				int nRst = posMerchant.normPosMerchant(key);
				if (nRst > 0) {
					//��¼��־
					writeLog(login, "�̻�[" + key + "]��Ϣ�޸ĳɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("�̻�������־���óɹ���");
				}
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		}catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "�̻�[" + key + "]��Ϣ�޸�ʧ��");
			errProc(request, response, exp);
		}
	}
	
}
