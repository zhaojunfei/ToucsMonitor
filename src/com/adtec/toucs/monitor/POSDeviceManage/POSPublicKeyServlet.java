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

public class POSPublicKeyServlet extends CommonServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public POSPublicKeyServlet() {
	}
	static final private String CONTENT_TYPE = "text/html; charset=GBK";

	//�����룺����
	private static final String POSPKEYADD = "13801";

	//�����룺��ѯ
	private static final String POSPKEYQUERY = "13802";
	
	//�����룺�޸�
	private static final String POSPKEYUPDATE = "13803";
	
	//�����룺ɾ��
	private static final String POSPKEYDELETE = "13804";
	
	//Ŀ��ҳ���������
	private static final String PARMGETPAGE = "page";
	
	//��Կ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/poskeyconfig";
	
	//��Կ����ҳ���·��
	private static final String PAGE_HOME = "/POSDeviceManage/";
	
	//��Կ������ʵ��
	private POSPublicKeyBean Manage = new POSPublicKeyBean();
	
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
		} catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}

		//Ŀ��JSPҳ��
		String tarJsp = null;
		//��Կ������������
		if (reqCode == null) {
			//Ҫ���ݵ�ǰ�û���ӵ�й�Կ��������Ȩ�ޣ���ȷ��������ִ�еĲ���
			setPagePerm(request, login.getPermission());
			tarJsp = PAGE_HOME + "PosPublicKeyReg.jsp?uid=" + login.getUserID();
			toPage(request, response, tarJsp);
		} else {
			if (reqCode.equals(POSPKEYADD)) {
				if ( (target == null) || target.trim().equals("")) {
					AddInfo(request, response, false, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						AddInfo(request, response, true, login);
					}
				}
			}
			//�޸Ĺ�Կ
			if (reqCode.equals(POSPKEYUPDATE)) {
				if ( (target == null) || target.trim().equals("")) {
					UpdateInfo(request, response, login);
				} else {
					if (target.equals(PARMGETPAGE)) {
						UpdateInfoQuery(request, response, true, login);
					}
				}
			}
			//��ѯ��Կ
			if (reqCode.equals(POSPKEYQUERY)) {
				QueryInfo(request, response, login);
			}
			//ɾ����Կ
			if (reqCode.equals(POSPKEYDELETE)) {
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
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYADD) == 0) {
			req.setAttribute("POSPKEYADD", "1");
		}
		//�޸�
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYUPDATE) == 0) {
			req.setAttribute("POSPKEYUPDATE", "1");
		}
		//ɾ��
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYDELETE) == 0) {
			req.setAttribute("POSPKEYDELETE", "1");
		}
		//��ѯ
		if (LoginManageBean.checkPermMask(maskCode, POSPKEYQUERY) == 0) {
			req.setAttribute("POSPKEYQUERY", "1");
		}
	}
  
  /**
   * ��Կ���������
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
		if (isGetPage) {
			tarJsp = PAGE_HOME + "PosPublicKeyReg.jsp?post=" + POSPKEYADD;
		} else {
			POSPublicKey Info = new POSPublicKey();
			//��Http������ȡ��Կ������Ϣ
			Info.fetchData(request);
			String temp;
			temp=Info.getRid().replaceAll(" ", "");
			Info.setRid(temp);
			temp=Info.DeleteChar(Info.getSha1h());
			Info.setSha1h(temp);
			temp=Info.DeleteChar(Info.getModulus());
			Info.setModulus(temp);
			try {
				int nPosCount = 0;
				int nReturn = Manage.addInfo(Info, nPosCount);
				PromptBean prompt = new PromptBean("��Կ����");
				if (nReturn != -1) {
					//��¼��־
					writeLog(login, "��Կ[" + Info.getRid() + "]��ӳɹ�");
					prompt.setPrompt("��Կ[" + Info.getRid() + "]��ӳɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);       
					tarJsp ="/Success.jsp?uid=" + login.getUserID();
					//֪ͨ�ͻ��������¹�Կ
				} else {
					prompt.setPrompt("��Կ[" + Info.getRid() + "]���ʧ�ܣ�");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
					toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
				}
			} catch (MonitorException exp) {
				//��¼��־
				writeLog(login, "��Կ[" + Info.getRid() + "]���ʧ�ܣ�");
				errProc(request, response, exp);
			}
		}
		//תĿ��ҳ��
		toPage(request, response, tarJsp);
	}

  /**
   * ��Կ��ѯ������
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
			request.setAttribute("publickeylist", v);
			toPage(request, response,PAGE_HOME + "PosPublicKeyList.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			errProc(request, response, exp);
		}
	}

  /**
   * ��Կ�޸Ĳ�ѯ������
   * @param request Http����
   * @param response Http��Ӧ
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfoQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  boolean isGetPage,
                                  LoginInfo login) throws ServletException, IOException {
		//ȡ��Կid��index
		String rid = request.getParameter("rid");
		String index = request.getParameter("index");
		System.out.println("pos_code:" + rid);
		//ȡ��ѯ���ݣ�������Ϣ��
		if (rid != null && index !=null) {
			String tarJsp = null;
			try {
				//��ѯ��Կ������Ϣ
				POSPublicKey Info = Manage.queryInfo(rid,index, "");
				if (Info == null) {
					throw new MonitorException(ErrorDefine.ATM_NOT_EXIST, "��Կ�����ڣ�");
				}
				setPagePerm(request, login.getPermission());
				tarJsp = PAGE_HOME + "PosPublicKeyUpdate.jsp?post=" + POSPKEYUPDATE + "&uid=" + login.getUserID();
				request.setAttribute("pospublickey", Info);
				toPage(request, response, tarJsp);
			} catch (MonitorException exp) {
				errProc(request, response, exp);
			}
		}
	}

  /**
   * �޸Ĺ�Կ������Ϣ
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void UpdateInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		POSPublicKey info = new POSPublicKey();
		//������������ȡ��Ҫ�޸ĵĹ�Կ������Ϣ
		info.fetchData(request);
		//��ȡ����
		String rid = request.getParameter("rid");
		String index = request.getParameter("index");
		String memo1=request.getParameter("memo1");
    
		try {
			//�޸Ĺ�Կ������Ϣ
			Manage.updateInfo(info, rid,index,memo1);
			//��¼��־
			writeLog(login, "��Կ[" + rid + "]�޸ĳɹ�");
			//֪ͨ�ͻ����޸Ĺ�Կ������Ϣ
			//ת�ɹ���ʾҳ��
			PromptBean prompt = new PromptBean("��Կ��������");
			prompt.setPrompt("��Կ[" + rid + "]�޸ĳɹ���");
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "��Կ[" + rid + "]�޸�ʧ��");
			errProc(request, response, exp);
		}
	}

  /**
   * POS��Կɾ��
   * @param request Http����
   * @param response Http��Ӧ
   * @param isGet Get�����־
   * @param login �û���Ϣ
   * @throws ServletException
   * @throws IOException
   */
	private void DeleteInfo(HttpServletRequest request,
                             HttpServletResponse response, LoginInfo login) throws ServletException, IOException {
		//������������ȡ��Ҫ�޸ĵĹ�Կ������Ϣ
		String rid = request.getParameter("rid");
		String index=request.getParameter("index");
		try {
			int nRst = Manage.deleteInfo(rid,index);
			PromptBean prompt = new PromptBean("��Կ����");
			if (nRst>0){
				writeLog(login, "��Կ[" + rid + "]ɾ���ɹ�");
				//ת�ɹ���ʾҳ��
				prompt.setPrompt("��Կ[" + rid + "]ɾ���ɹ���");
			}
			//��ҪУ���û��Ƿ��м���Ȩ��
			request.setAttribute("prompt", prompt);
			toPage(request, response, "/Success.jsp?uid=" + login.getUserID());
		} catch (MonitorException exp) {
			//��¼��־
			writeLog(login, "��Կ[" + rid + "]ɾ��ʧ��");
			errProc(request, response, exp);
		}
	}
  
	public int updateInfo(POSPublicKey info, String rid,String index,String memo1) throws MonitorException {
	    //ȡ���ݿ�����
	    SqlAccess sq = SqlAccess.createConn();
	    try {
	    	//�������ݿ������ύ��ʽΪ���Զ��ύ
	    	sq.setAutoCommit(false);
	    	//��ӹ�Կ������Ϣ
	    	PreparedStatement stm = info.makeUpdateStm(sq.conn,rid,index,memo1);
	    	int flag = stm.executeUpdate();
	    	if (flag == 1) {
	    		sq.commit();
	    	} else {
	    		sq.rollback();
	    		throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸��˻���Ϣʧ�ܣ�");
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
		}catch (MonitorException exp) {
			errProc(request, response, exp);
			return;
		}
		//��ӹ�Կ
		if (reqCode.equals(POSPKEYADD)) {
			if ( (target == null) || target.trim().equals("")) {
				AddInfo(request, response, false, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					AddInfo(request, response, true, login);
				}
			}
		}

		//�޸Ĺ�Կ
		if (reqCode.equals(POSPKEYUPDATE)) {
			if ( (target == null) || target.trim().equals("")) {
				UpdateInfo(request, response, login);
			} else {
				if (target.equals(PARMGETPAGE)) {
					UpdateInfoQuery(request, response, true, login);
				}
			}
		}

		//��ѯ��Կ
		if (reqCode.equals(POSPKEYQUERY)) {
			QueryInfo(request, response, login);
		}

		//ɾ����Կ
		if (reqCode.equals(POSPKEYDELETE)) {
			DeleteInfo(request, response, login);
		}
	}
}

