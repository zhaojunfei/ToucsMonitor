package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.toucs.monitor.common.CommonServlet;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.PromptBean;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;

public class POSPublicAppServlet extends CommonServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSPublicAppServlet() {
	}
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//�����룺����
	private static final String POSPAPPADD = "13901";	
	
	//�����룺��ѯ
	private static final String POSPAPPQUERY = "13902";
	
	//�����룺�޸�
	private static final String POSPAPPUPDATE = "13903";
	
	//�����룺ɾ��
	private static final String POSPAPPDELETE = "13904";
	
	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	
	//EMV�����������·��
	private static final String MANAGE_HOME = "/ToucsMonitor/pospublicappconfig";
	
	//EMV����������ҳ���·��
	private static final String PAGE_HOME = "/POSDeviceManage/";
	
	//EMV������������ʵ��
	private POSPublicAppBean Manage = new POSPublicAppBean();
	
	public void init() throws ServletException {
	}
  
  
	//Process the HTTP Get request
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
			//У���û�����Ȩ��
			if (reqCode != null && reqCode.trim().length() > 0) {
				LoginManageBean.operValidate(login, reqCode);
			}
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//Ŀ��JSPҳ��
		String tarJsp = null;
		
		//EMV��������������
		if (reqCode == null) {
			//Ҫ���ݵ�ǰ�û���ӵ��EMV����������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosPublicAppReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			//���EMV������ 
			if (reqCode.equals(POSPAPPADD)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}
			//�޸�EMV������
			if (reqCode.equals(POSPAPPUPDATE)) {
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}
			//��ѯEMV������
			if (reqCode.equals(POSPAPPQUERY)) {
				QueryInfo(request, response, login);
			}
			//ɾ��EMV������
			if (reqCode.equals(POSPAPPDELETE)) {
				DeleteInfo(request, response, login);
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
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPADD) == 0) {
			req.setAttribute("POSPAPPADD", "1");
		}
		//�޸�
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPUPDATE) == 0) {
			req.setAttribute("POSPAPPUPDATE", "1");
		}
		//ɾ��
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPDELETE) == 0) {
			req.setAttribute("POSPAPPDELETE", "1");
		}
		//��ѯ
		if (LoginManageBean.checkPermMask(maskCode, POSPAPPQUERY) == 0) {
			req.setAttribute("POSPAPPQUERY", "1");
		}
	}
  
  /**
   * EMV���������������
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void AddInfo(HttpServletRequest request,
                          HttpServletResponse response,
                          boolean isGetPage, LoginInfo login) throws ServletException, IOException {
		String tarJsp = null;
		//EMV���������Get����
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosPublicAppReg.jsp?post=" + POSPAPPADD;
		} else {
			POSPublicApp Info = new POSPublicApp();
			//��Http������ȡEMV������������Ϣ
			Info.fetchData(request);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("EMV����������");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "EMV������[" + Info.getAid() + "]��ӳɹ�");
					prompt.setPrompt("EMV������[" + Info.getAid() + "]��ӳɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);       
					tarJsp ="/Success.jsp?uid=" + login.getUserID();
				}else {
					prompt.setPrompt("EMV������[" + Info.getAid() + "]���ʧ�ܣ�");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}	
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "EMV������[" + Info.getAid() + "]���ʧ�ܣ�");
				errProc(request, response, exp);
			}
		}
		//תĿ��ҳ��   
		toPage(request, response, tarJsp);
	}

  /**
   * EMV��������ѯ������
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void QueryInfo(HttpServletRequest request,
                            HttpServletResponse response,
                            LoginInfo login) throws ServletException,IOException {
		try {
			List v = Manage.VqueryInfo();
			request.setAttribute("publicapplist", v);
			toPage(request, response,PAGE_HOME + "PosPublicAppList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}

  /**
   * EMV�������޸Ĳ�ѯ������
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException,IOException {
		//ȡEMV���������
		String aid = request.getParameter("aid");
		System.out.println("pos_code:" + aid);
		//ȡ��ѯ���ݣ�������Ϣ��
		if (aid != null) {
			String tarJsp = null;
			try {
				POSPublicApp Info = Manage.queryInfo(aid,"");
				if (Info == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "EMV�����������ڣ�");
				}
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "PosPublicAppUpdate.jsp?post=" + POSPAPPUPDATE + "&uid=" + login.getUserID();
				request.setAttribute("pospublicapp", Info);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

  /**
   * �޸�EMV������������Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSPublicApp info = new POSPublicApp();
		//������������ȡ��Ҫ�޸ĵ�EMV������������Ϣ
		info.fetchData(request);
		//��ȡ����
		String aid = request.getParameter("aid"); 	 
		String memo1=request.getParameter("memo1");
		
		try {
			//�޸�EMV������������Ϣ
			Manage.updateInfo(info, aid, memo1);
			//��¼��־
			writeLog(login, "EMV������[" + aid + "]�޸ĳɹ�");
			
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("EMV����������");
			prompt.setPrompt("EMV������[" + aid + "]�޸ĳɹ���");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "EMV������[" + aid + "]�޸�ʧ��");
			errProc(request, response, exp);
		}
	}

  /**
   * EMV������ɾ��
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵ�EMV������������Ϣ
		String aid = request.getParameter("aid");    
		
		try {
			//�޸�EMV������������Ϣ
			int nRst = Manage.deleteInfo(aid);
			PromptBean prompt = new PromptBean("EMV����������");
			if (nRst>0){
				//��¼��־
				writeLog(login, "EMV������[" + aid + "]ɾ���ɹ�");
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("EMV������[" + aid + "]ɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			request.setAttribute("prompt", prompt); 	
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "EMV������[" + aid + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}
  
	public int updateInfo(POSPublicApp info, String aid,String memo1) throws MonitorException {
	    //ȡ���ݿ�����
	    SqlAccess sq = SqlAccess.createConn();
	    try {
	    	//�������ݿ������ύ��ʽΪ���Զ��ύ
	    	sq.setAutoCommit(false);
	    	//���EMV������������Ϣ
	    	PreparedStatement stm = info.makeUpdateStm(sq.conn,aid,memo1);
	    	int flag = stm.executeUpdate();
	    	if (flag == 1) {
	    		sq.commit();
	    	} else {
	    		sq.rollback();
	    		throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�EMV��������Ϣʧ�ܣ�");
	    	}
	    	return flag;
	    } catch (SQLException e1) {
	    	sq.rollback();
	    	throw new MonitorException(e1);
	    } finally {
	    	sq.close();
	    }
	}
   
	//Process the HTTP Post request
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
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		
		//���EMV������
		if (reqCode.equals(POSPAPPADD)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		}

		//�޸�EMV������
		if (reqCode.equals(POSPAPPUPDATE)) {
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}

		//��ѯEMV������
		if (reqCode.equals(POSPAPPQUERY)) {
			QueryInfo(request, response, login);
		}
		
		//ɾ��EMV������
		if (reqCode.equals(POSPAPPDELETE)) {
			DeleteInfo(request, response, login);
		}
	}
}

