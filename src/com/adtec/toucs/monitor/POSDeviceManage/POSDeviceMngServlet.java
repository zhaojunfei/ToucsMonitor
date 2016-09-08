package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.usermanager.*;

/**
 * <p>Title: POS设备管理</p>
 * <p>Description: 完成POS设备的管理功能,包括停用、启用、加载、批量签到、POS密钥下载、商场密钥下载、响应码加载等</p>
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
    //设备启用
    private static final String POS_START = "12101" ;
    //设备停用
    private static final String POS_STOP = "12102";
    //批量签到
    private static final String POS_REGIST = "12104";
    //设备密钥下载
    private static final String POS_KEYDOWN = "12103";
    //商场密钥下载
    private static final String SHOP_KEYDOWN = "12106";
    //响应码加载
    private static final String RESONSE_CODE_LOAD = "2006";
    //删除POS
    private static final String POS_DEL = "12105";
    //POS密钥下载日志查询
    private static final String QRY_KEYDOWN_LOG = "14003";

    //add by liyp 20031114
    //页面参数
    private static final String PARMKEYDOWN = "keyDown";

    //POS管理主页面
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
        //此处为DCC号，用于转换成P端编号
        String PosCode = request.getParameter("pos_code");

        //add by liyp 20031114
        //POSDCC编号
        String PosDccCode = request.getParameter("pos_code");
        //POSP编号
        String PosPCode = request.getParameter("pos_p_code");
        //页面参数
        String target = request.getParameter("target");

        String tarJsp = "";
        PromptBean prompt=new PromptBean("POS设备参数管理");

        try {
            LoginInfo login = checkLogin(request,response);
            request.setAttribute("Login",login );
            if (LoginManageBean.operValidate(login,TxCode) != 0) {
                throw new MonitorException(ErrorDefine.NOT_AUTHORIZED,"权限不足！");
            }
            if (TxCode == null) { //取交易码失败！
                request.setAttribute("Login",login);
                tarJsp = MANAGE_HOME;
                toPage(request,response,tarJsp);
                return;
            }

            Debug.println("*************"+TxCode+" authorize pass************");

			//转换DCC号为P号
			if( PosCode != null ){
				PosCode = POSDeviceManageBean.transDCCCodeToPCode(PosCode);
				PosCode = PosCode.trim();
			}
            if (TxCode.equals(POS_START)) {   //POS设备启用
                pmb.startPOS(PosCode);
                writeLog(login,PosCode+"设备开启成功！");
                prompt.setPrompt("设备开启成功！");
                prompt.setButtonUrl(0,"确定","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_STOP)) {  //POS设备停用
                pmb.stopPOS(PosCode);
                writeLog(login,PosCode+"设备停机成功！");
                prompt.setPrompt("设备停机成功！");
                prompt.setButtonUrl(0,"确定","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_REGIST)) {  //柜员签到
                pmb.registPOS();
                writeLog(login,"POS批量签到成功！");
                prompt.setPrompt("POS签到成功！");
                prompt.setButtonUrl(0,"确定","/ToucsMonitor/POSDeviceManage");
                tarJsp="/Success.jsp?uid="+login.getUserID();
            } else if (TxCode.equals(POS_KEYDOWN)){//POS密钥下载
            	//modify by liyp 20031114   增加下载密钥前确认页面
				if (target != null && target.equals("keyDown")){
				    String poskey = pmb.pos_key_down(PosPCode);
				    Debug.println("************begin write log*************");
				    writeLog(login, PosPCode, TxCode, "POS密钥下载成功！");
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
