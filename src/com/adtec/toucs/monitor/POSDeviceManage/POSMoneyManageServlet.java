package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;
import com.adtec.toucs.monitor.systemmanage.CodeBean;
import com.adtec.toucs.monitor.systemmanage.CodeManageBean;

public class POSMoneyManageServlet extends CommonServlet {
	
	private static final long serialVersionUID = 1L;

	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	  //�����룺���POS�豸����
	  private static final String POSINFOREG = "14201";
	  //�����룺���POS�豸�޸�
	  private static final String POSINFOUPDATE = "14202";
	  //�����룺���POS�豸��ѯ
	  private static final String POSINFOQUERY = "14203";
	  //�����룺���POS�豸ɾ��
	  private static final String POSINFODELETE = "14204";
	  //�����룺���POS����ͣ��
	  private static final String POSINFOMNG = "14205";
	  //������: �ļ��ϴ�
	  private static final String UPLOAD = "14206";
	  
	  //Ŀ��ҳ���������
	  private static final String PARMGETPAGE = "page";

	  //����������
	  private static final String DCCTYPE = "3";

	  //���POS����ҳ���·��
	  private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";

	  //���POS�豸������ʵ��
	  private POSMoneyManageBean posManage = new POSMoneyManageBean();
	  //POS�̻�������ʵ��
	  private POSMerchantBean posMerchant = new POSMerchantBean();
	  
	  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //���û���ҳ�治����
		  response.setHeader("Cache-Control", "no-store");
		  //ȡ�������
		  String reqCode = request.getParameter("reqCode");
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
		  } catch (MonitorException exp) {
			  errProc(request, response, exp);
			  return;
		  }
		  
		  //Ŀ��JSPҳ��
		  String tarJsp = null;
		  //�豸������������
		  if (reqCode == null) {
			  //Ҫ���ݵ�ǰ�û���ӵ���豸��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			  setPagePerm(request, login.getPermission());
			  tarJsp = PAGE_HOME + "PosMoneyUpload.jsp?uid=" + login.getUserID();
			  toPage(request, response, tarJsp);
		  } else {
			  //ע�����POS�豸��Ϣ
			  if (reqCode.equals(POSINFOREG)) {
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoReg(request, response, false, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoReg(request, response, true, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFOUPDATE)) {//�޸����POS�豸��Ϣ
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoUpdate(request, response, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoUpdateQuery(request, response, true, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFOQUERY)) {//��ѯ���POS�豸��Ϣ
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoQuery(request, response, login);
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoQueryPage(request, response, login);
					  }
				  }
			  }else if (reqCode.equals(POSINFODELETE)) {//ɾ�����POS�豸��Ϣ
				  Debug.println("ɾ�����pos��Ϣ...POST����");
				  if(target == null || target.trim().equals("")){
					  posInfoDelete(request, response, login);
				  }else if(target.equals(PARMGETPAGE)){
					  posInfosDelete(request,response,login);
				  }
			  }else if (reqCode.equals(POSINFOMNG)) {   //���POS�豸�������á�ͣ�ã�
				  if ( (target == null) || target.trim().equals("")) {
					  posInfoManage(request, response, login, "1" );
				  } else {
					  if (target.equals(PARMGETPAGE)) {
						  posInfoManage(request, response, login, "0" );
					  }
				  }
			  }else if(reqCode.equals(UPLOAD)){
				  upload_pos(request, response, login);
			  }
		  }
	  }


	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		  response.setContentType(CONTENT_TYPE);	    
		  //ȡ�������
		  String reqCode = request.getParameter("reqCode");
		  //ȡ�������
		  String target = request.getParameter("target"); 
		  //Ȩ�޿���
		  LoginInfo login = null;
		  try {
			  //У���û��Ƿ��¼
			  login = checkLogin(request, response);
			  //У���û�����Ȩ��
			  if (reqCode != null && reqCode.trim().length() > 0) {
				  LoginManageBean.operValidate(login, reqCode);
			  }
		  }catch (MonitorException exp) {
			  errProc(request, response, exp);
			  return;
		  }
		  //ע�����POS�豸��Ϣ
		  if (reqCode.equals(POSINFOREG)) {
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoReg(request, response, false, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoReg(request, response, true, login);
				  }
			  }
		  }
		  //�޸����POS�豸��Ϣ
		  if (reqCode.equals(POSINFOUPDATE)) {
			  Debug.println("1");
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoUpdate(request, response, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoUpdateQuery(request, response, true, login);
				  }
			  }
		  }
		  //��ѯ���POS�豸��Ϣ
		  if (reqCode.equals(POSINFOQUERY)) {
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoQuery(request, response, login);
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoQueryPage(request, response, login);
				  }
			  }
		  }
		  //ɾ�����POS�豸��Ϣ
		  if (reqCode.equals(POSINFODELETE)) {
			  Debug.println("ɾ�����pos��Ϣ...POST����");
			  if(target == null || target.trim().equals("")){
				  posInfoDelete(request, response, login);
			  }else if(target.equals(PARMGETPAGE)){
				  posInfosDelete(request,response,login);
			  }
		  }
		  //���POS�豸�������á�ͣ�ã�
		  if (reqCode.equals(POSINFOMNG)) {   
			  if ( (target == null) || target.trim().equals("")) {
				  posInfoManage(request, response, login, "1" );
			  }else {
				  if (target.equals(PARMGETPAGE)) {
					  posInfoManage(request, response, login, "0" );
				  }
			  }
		  }	    
		  //excel�ļ��ϴ�����
		  if(reqCode.equals(UPLOAD)){
			  upload_pos(request, response, login);
		  }	
	  }
	  /**
	   * ��¼У��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @return ��¼�û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   * @throws MonitorException
	   */
	protected LoginInfo checkLogin(HttpServletRequest request,HttpServletResponse response) throws MonitorException{
		String sid=null;
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		for(int i=0;i<cookies.length;i++){	   
			if(cookies[i].getName().equals("sid"))
				sid=cookies[i].getValue();
		}					    
		if(sid==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,"�û�δ��¼��Ự����");
		//У���û��Ƿ��¼
		int err=LoginManageBean.logInValidate(sid,request.getRemoteAddr());
		if(err!=0)
			throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		//У���û��Ƿ�����
		if(LoginInfo.timeOut>0){
			err=LoginManageBean.offLineValidate(sid);
			if(err!=0)
				throw new MonitorException(err,LoginManageBean.getErrorDesc(err));
		}
		LoginManageBean.getUserInfo(sid);    
		//ȡ�û���Ϣ
		return LoginManageBean.getUserInfo(sid);
	}
	  
	  /**
	   * ��ʼ���̻����ҳ��������б���Ϣ
	   * @param req Http����
	   * @param orgId ��������
	   */
	private void initPosList(HttpServletRequest req, String orgId) {
		Vector v;
		SqlAccess sq = null;
		try {
			sq = SqlAccess.createConn();
			CodeBean codeBean = null;
			//Ԥ��Ȩ����
			v = new Vector();
			codeBean = new CodeBean();
			codeBean.setCodeId("0");
			codeBean.setCodeDesc("��Ԥ��Ȩ�ཻ��");
			v.add(codeBean);
			codeBean = new CodeBean();
			codeBean.setCodeId("1");
			codeBean.setCodeDesc("��Ԥ��Ȩ�ཻ��");
			v.add(codeBean);
			req.setAttribute("preAuthorizedList", v);      
			//�����б�
			//int[] level={3};
			List list = CodeManageBean.queryOrgListPos(orgId);
			req.setAttribute("orgList", list);
		}catch (Exception exp) {
			exp.printStackTrace();
		}finally {
			if (sq != null) {
				sq.close();
			}
		}
	}
	    
	  /**
	   * �����û���ҳ��Ĳ���Ȩ��
	   * @param req Http����
	   * @param maskCode �û�Ȩ����
	   */
	private void setPagePerm(HttpServletRequest req, String maskCode) {
		//����
		if (LoginManageBean.checkPermMask(maskCode, POSINFOREG) == 0) {
			req.setAttribute("POSINFOREG", "1");
		}
		//�޸�
		if (LoginManageBean.checkPermMask(maskCode, POSINFOUPDATE) == 0) {
			req.setAttribute("POSINFOUPDATE", "1");
		}
		//ɾ��
		if (LoginManageBean.checkPermMask(maskCode, POSINFODELETE) == 0) {
			req.setAttribute("POSINFODELETE", "1");
		}
		//��ѯ
		if (LoginManageBean.checkPermMask(maskCode, POSINFOQUERY) == 0) {
			req.setAttribute("POSINFOQUERY", "1");
		}
	}

	  
//**********************************************************************************************************************	  
  /**
   * ���POS�豸��ѯ������
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void posInfoQuery(HttpServletRequest request, HttpServletResponse response,LoginInfo login) throws ServletException,IOException {
		//ȡ�豸���
		String pos_code = request.getParameter("pos_code");
		//ȡ��ѯĿ�ģ��޸ġ���ѯ��
		if (pos_code != null && !pos_code.equals("")) {
			try {
				Vector v = posManage.qryPosInfoByPoscode( pos_code, request.getParameter("pos_kind"));
				request.setAttribute("posMoneyList", v);
				toPage(request, response,
						PAGE_HOME + "PosMoneyList.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}else {
			try {
				String merchant_id = request.getParameter("merchant_id");
				if(merchant_id == ""){
					merchant_id = null;
				}
				String branch_id   = request.getParameter("orgId");
				if(branch_id == ""){
					branch_id = null;
				}
				List list = posManage.qryMctPosInfo(merchant_id , branch_id);  
				request.setAttribute("posMoneyList", list);          
				toPage(request, response,PAGE_HOME + "PosMoneyList.jsp?uid=" + login.getUserID());
			}catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}
	  
//**************************************END********************************************************************************	  
	    
//************************* START  ��ת��ѯҳ��ǰ�Ĳ���******************************************************************//
	 
	  /**
	   * �豸��Ϣ��ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoQueryPage(HttpServletRequest request,HttpServletResponse response,LoginInfo login) throws ServletException,IOException {
		initPosList(request, login.getOrgID());
		toPage(request, response, PAGE_HOME + "PosMoneyManage.jsp?post=" + POSINFOQUERY + "&uid=" + login.getUserID());
	}  
//**************************************END**************************************************************************//
	   
//************************ START ������POS�豸��Ϣ  *****************************************************************//	  
	  /**
	   * �豸��Ϣ�Ǽ�������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoReg(HttpServletRequest request,HttpServletResponse response,boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//�豸�Ǽ�Get����
		if (isGetPage) {
			POSMoneyManageBean pb = new POSMoneyManageBean();
			POSMoneyInfo posMoney;
			String pos_code = request.getParameter("pos_dcc_code");
			try {
				if (pos_code != null && pos_code.length() > 0) {
					posMoney = pb.qryPosInfo(pos_code, "3");
					if ( posMoney != null ) {
						posMoney.setMct_name();
						request.setAttribute( "posMoney", posMoney);
					}
				}
			} catch (MonitorException ex) {
				Debug.println("Input continuous Failed.");
			}
			initPosList(request, login.getOrgID());
			tarJsp = PAGE_HOME + "PosMoneyReg.jsp?post=" + POSINFOREG;
		} else {
			//�豸�Ǽ�Post����
			POSMoneyInfo posMoney = new POSMoneyInfo();
			//��Http������ȡ�豸������Ϣ
			posMoney.fetchData(request);
			try {
				int nPosCount = posMerchant.qryPosCount(posMoney.getMerchant_id());
				int nReturn = posManage.regPosDevice(posMoney, nPosCount);
				PromptBean prompt = new PromptBean("�豸��������");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "���POS�豸[" + posMoney.getPos_code() + "]��ӳɹ�");
					//֪ͨ�ͻ����������豸
					Debug.println("֪ͨ�ͻ������������POS�豸");
					notifyClient("N20007", posMoney.getPos_code(), "2");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("���POS�豸��ӳɹ�!");
					request.setAttribute("prompt", prompt);
					tarJsp = "/ToucsMonitor/Success.jsp";
				} else {
					prompt.setPrompt("�ѵǼǵ�POS�豸�������ڸ��̻����POS�豸����" + "���Ǽ�POS�豸���ɹ���");
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "���POS�豸[" + posMoney.getPos_code() + "]���ʧ��");
				errProc(request, response, exp);
			}
		}
		//תĿ��ҳ��
		Debug.println(tarJsp);
		toPage(request, response, tarJsp);
	}  
//***************************************END*************************************************************************//	  
	  
//******************************START  ��ת�޸�ҳ��ǰ�Ĳ�ѯ����********************************************************// 
	  /**
	   * ���POS�豸�޸Ĳ�ѯ������
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdateQuery(HttpServletRequest request,HttpServletResponse response,boolean isGetPage,LoginInfo login) throws ServletException,IOException {
		//ȡ�豸���DCC
		String pos_code = request.getParameter("pos_code");
		System.out.println("pos_code:" + pos_code);
		//ȡ��ѯ���ݣ�������Ϣ��
		if (isGetPage) {
			if (pos_code != null) {
				String tarJsp = null;
				//��ѯ�豸������Ϣ
				try {
					//��ѯ�豸������Ϣ
					POSMoneyInfo posMoney = posManage.qryPosMoney(pos_code, DCCTYPE);
					if (posMoney == null) {
						throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "POS�豸�����ڣ�");
					}
					initPosList(request, login.getOrgID());
					setPagePerm(request, login.getPermission());
					tarJsp = PAGE_HOME + "PosMoneyUpdate.jsp?post=" + POSINFOUPDATE + "&uid=" + login.getUserID();
					request.setAttribute("posMoney", posMoney);
					toPage(request, response, tarJsp);
				} catch (MonitorException exp) {
					errProc(request, response, exp);
				}
			}
		} else {
			//�����������ҳ��
			initPosList(request, login.getOrgID());
			setPagePerm(request, login.getPermission());
			toPage(request, response, PAGE_HOME + "PosInfoUpdateQuery.jsp?" + POSINFOUPDATE + "&uid=" + login.getUserID());
		}
	}  
	  /**
	   * �޸����POS�豸��Ϣ
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoUpdate(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSMoneyInfo info = new POSMoneyInfo();
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		info.fetchData(request);
		//��ȡ����
		String key = request.getParameter("key");
		try {
			//�޸��豸������Ϣ
			posManage.updatePosInfo(info, key);
			//��¼��־
			writeLog(login, "���POS[" + key + "]������Ϣ�޸ĳɹ�");
			//֪ͨ�ͻ����޸��豸������Ϣ
			notifyClient("N20007", key, "1");
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("�豸��������");
			prompt.setPrompt("���POS��Ϣ�޸ĳɹ���");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//exp.printStackTrace();
			//��¼��־
			writeLog(login, "���POS[" + key + "]������Ϣ�޸�ʧ��");
			errProc(request, response, exp);
		}
	}   
//************************************* END ******************************************************************//	  
	
//************************** START ɾ������ ********************************************************************//
	
	  /**
	   * ���POS�豸ɾ��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoDelete(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String key = request.getParameter("pos_code");
		try {
			//�޸��豸������Ϣ
			posManage.deletePosInfo(key);
			PromptBean prompt = new PromptBean("���POS�豸����");
			// if (nRst>0){
			{
				//��¼��־
				writeLog(login, "���POS�豸[" + key + "]ɾ���ɹ�");
				//֪ͨ�ͻ���ɾ���豸 add by liyp 20030918
				notifyClient("N20007", key, "0");
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("���POS��Ϣɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			//prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "���POS�豸[" + key + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}
	    
	  /**
	   * ���POS�豸����ɾ��
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfosDelete(HttpServletRequest request,HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String[] strArray = request.getParameterValues("box1");
		for(int i=0;i<strArray.length;i++){
			System.out.println("��ѡ���б�ѡ�е�����:"+strArray[i]);
		}
		try {
			//�޸��豸������Ϣ  	    
			posManage.deletePosInfos(strArray);
			PromptBean prompt = new PromptBean("���POS�豸����");
			// if (nRst>0){
			{
				//��¼��־
				writeLog(login, "���POS�豸ɾ���ɹ�");
				//֪ͨ�ͻ���ɾ���豸 add by liyp 20030918
				//notifyClient("N20007", key, "0");
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("���POS��Ϣɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			//prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "���POS�豸ɾ��ʧ��");
			errProc(request, response, exp);
		}	
	}  
//*******************************************END*******************************************************************//
	  
//****************************** START �޸����POS�豸��״̬****************************************************************//
	  
	  /**
	   * ���POS�豸״̬�޸�
	   * @param request Http����
	   * @param response Http��Ӧ
	   * @param isGet Get�����־
	   * @param login �û���Ϣ
	   * @throws ServletException
	   * @throws IOException
	   */
	private void posInfoManage(HttpServletRequest request,HttpServletResponse response, LoginInfo login, String use_flag ) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ��豸������Ϣ
		String key = request.getParameter("pos_code");
		try {
			posManage.managePosInfo(key, use_flag);
			PromptBean prompt = new PromptBean("���POS�豸����");
			{
				if ( use_flag.equals("0") ) {
					//��¼��־
					writeLog(login, "���POS�豸[" + key + "]ͣ�óɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("���POS�豸[" + key + "]ͣ�óɹ���");
				} else {
					//��¼��־
					writeLog(login, "���POS�豸[" + key + "]���óɹ�");
					//ת�ɹ���ʾҳ��
					prompt.setPrompt("���POS�豸[" + key + "]���óɹ���");
				}
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			//prompt.setButtonUrl(0, "��������", MANAGE_HOME);
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "���POS�豸[" + key + "]����ʧ��");
			errProc(request, response, exp);
		}
	}

//************************************END*********************************************************************************	  
	  
	  
//************************************UPLOAD  START*************************************************************************	  
	  
	  private void upload_pos(HttpServletRequest request,HttpServletResponse response, LoginInfo login){
		  String path =request.getParameter("excel");//excel�ϴ��ļ����ڵ�·��
		  System.out.println("�ļ����ڵ�·��Ϊ"+path);  
		  List list2 = new ArrayList();
		  PromptBean prompt = new PromptBean("���POS����");
		  List list =  posManage.recJXL(path);// ���ϴ��ļ��е����ݷŵ�������
		  int num = 0;
		  System.out.println("���ϵĴ�С��:"+list.size());
		  try {	 
			  for(int i=0;i<list.size();i++){//��������
				  POSMoneyInfo info = (POSMoneyInfo)list.get(i);
				  System.out.println("pos_dcc_code = "+info.getPos_dcc_code());
				  POSMoneyInfo info1 =  posManage.qryPosInfo(info.getPos_dcc_code(),"3");//��ÿ�����ݽ�����Ч����֤
				  info.setMct_name();//��ѯ�̻�id����Ӧ���̻�����
				  if(info1 != null){
					  info.setPos_code(info1.getPos_code());
					  info.setPos_c_code(info1.getPos_c_code());
					  info.setMerchant_id(info1.getMerchant_id());
					  posManage.regPosDevice(info, 1);//��������������ӵ����ݿ���				 
				  }else{
					  list2.add(info);//����������ݷŵ�������
					  num++;//ͳ�ƴ�����
				  }		 
			  } 
			  if(list2.equals(null)){
				  prompt.setPrompt("excel�ļ������ɹ���");
				  request.setAttribute("prompt", prompt);
				  toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			  }else{
				  posManage.createJXL(list2,path);
				  prompt.setPrompt("�ϴ������д���"+num+"��������Ϣ������D�̸�Ŀ¼�²鿴������Ϣ�ļ�errorInfo.xls");
				  request.setAttribute("prompt", prompt);
				  toPage(request, response, "/ToucsMonitor/Success.jsp?uid=" + login.getUserID());
			  }
		  } catch (MonitorException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } catch (ServletException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } catch (IOException e) {
			  e.printStackTrace();
			  System.out.println(e.getMessage());
		  } 	  
	  }
	  
//**************************************************END***********************************************************//	  
	  
	public void init() throws ServletException {
		// Put your code here
	}

}
