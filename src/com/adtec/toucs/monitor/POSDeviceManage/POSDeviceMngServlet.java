package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.usermanager.*;

/**
 * <p>Title: POS�豸����</p>
 * <p>Description: ���POS�豸�Ĺ�����,����ͣ�á����á����ء�����ǩ����POS��Կ���ء��̳���Կ���ء���Ӧ����ص�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author Fancy
 * @version 1.0
 */

public class POSDeviceMngServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
    //�豸����
    private static final String POS_START = "12101" ;
    //�豸ͣ��
    private static final String POS_STOP = "12102";
    //����ǩ��
    private static final String POS_REGIST = "12104";
    //�豸��Կ����
    private static final String POS_KEYDOWN = "12103";
    //�̳���Կ����
    private static final String SHOP_KEYDOWN = "12106";
    //��Ӧ�����
    private static final String RESONSE_CODE_LOAD = "2006";
    //ɾ��POS
    private static final String POS_DEL = "12105";
    //POS��Կ������־��ѯ
    private static final String QRY_KEYDOWN_LOG = "14003";

    //add by liyp 20031114
    //ҳ�����
    private static final String PARMKEYDOWN = "keyDown";

    //POS������ҳ��
    private String MANAGE_HOME = "ToucsMonitor/POSDeviceManage/POSDeviceMng.jsp";

    private POSDeviceManageBean pmb ;

    //Initialize global variables
    public void init() throws ServletException {
        pmb = new POSDeviceManageBean();
    }
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        String TxCode = request.getParameter("reqCode");
        //�˴�ΪDCC�ţ�����ת����P�˱��
        String PosCode = request.getParameter("pos_code");

        //add by liyp 20031114
        //POSDCC���
        String PosDccCode = request.getParameter("pos_code");
        //POSP���
        String PosPCode = request.getParameter("pos_p_code");
        //ҳ�����
        String target = request.getParameter("target");

        String tarJsp = "";
        PromptBean prompt=new PromptBean("POS�豸��������");

        try {
            LoginInfo login = checkLogin(request,response);
            request.setAttribute("Login",login );
            if (LoginManageBean.operValidate(login,TxCode) != 0) {
                throw new MonitorException(ErrorDefine.NOT_AUTHORIZED,"Ȩ�޲��㣡");
            }
            if (TxCode == null) { //ȡ������ʧ�ܣ�
                request.setAttribute("Login",login);
                tarJsp = MANAGE_HOME;
                toPage(request,response,tarJsp);
                return;
            }

            Debug.println("*************"+TxCode+" authorize pass************");

			//ת��DCC��ΪP��
			if( PosCode != null ){
				PosCode = POSDeviceManageBean.transDCCCodeToPCode(PosCode);
				PosCode = PosCode.trim();
			}
            if (TxCode.equals(POS_START)) {   //POS�豸����
                pmb.startPOS(PosCode);
                writeLog(login,PosCode+"�豸�����ɹ���");
                prompt.setPrompt("�豸�����ɹ���");
                prompt.setButtonUrl(0,"ȷ��","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_STOP)) {  //POS�豸ͣ��
                pmb.stopPOS(PosCode);
                writeLog(login,PosCode+"�豸ͣ���ɹ���");
                prompt.setPrompt("�豸ͣ���ɹ���");
                prompt.setButtonUrl(0,"ȷ��","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_REGIST)) {  //��Աǩ��
                pmb.registPOS();
                writeLog(login,"POS����ǩ���ɹ���");
                prompt.setPrompt("POSǩ���ɹ���");
                prompt.setButtonUrl(0,"ȷ��","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_KEYDOWN)){//POS��Կ����
            	//modify by liyp 20031114   ����������Կǰȷ��ҳ��
				if (target != null && target.equals("keyDown")){
				    String poskey = pmb.pos_key_down(PosPCode);
				    Debug.println("************begin write log*************");
				    writeLog(login, PosPCode, TxCode, "POS��Կ���سɹ���");
				    request.setAttribute("poskey", poskey);
				    tarJsp = "/ToucsMonitor/POSDeviceManage/PosKeyDown.jsp";
				}else{
				    if ( PosCode == null || PosCode.equals(""))
				        throw new MonitorException(-3009, ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
				    	String mctName = pmb.getPOSMerchantName(PosDccCode);
                        String PosOrgName = pmb.getPOSOrgName(PosDccCode);
                        request.setAttribute("posPCode", PosCode);
                        request.setAttribute("posDccCode", PosDccCode);
                        request.setAttribute("posMerchantName", mctName);
                        request.setAttribute("posOrgName",PosOrgName);
                        tarJsp = "/ToucsMonitor/POSDeviceManage/POSKeyDownConfirm.jsp";
				}
           }else if (TxCode.equals(QRY_KEYDOWN_LOG)){
                LogManagerBean lm = new LogManagerBean();
                List list = lm.queryPosKeyDownLog(PosCode);
                request.setAttribute("poskeydownlog",list);
                tarJsp = "/POSDeviceManage/PosKeyDownLog.jsp";
            } else {
                throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
            }
            	request.setAttribute("prompt",prompt);
            	toPage(request,response,tarJsp);
        } catch (MonitorException mex) {
            errProc(request,response,mex);
        }
    }
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
    //Clean up resources
    public void destroy() {
    }
}
