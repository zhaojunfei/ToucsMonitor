package com.adtec.toucs.monitor.systemmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.devicemanage.*;

import com.adtec.toucs.monitor.usermanager.*;
import com.adtec.toucs.monitor.POSDeviceManage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InitServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
    //�����룺ATM��Ϣ��λ����Ϣ��״̬��Ϣ�����أ����ڿͻ��˳�ʼ����
    private static final String ATMINFO_DOWNLOAD="20004";

    private static final String ORGINFO_DOWNLOAD="20003";
    private static final String SYSCODEINFO_DOWNLOAD="20002";
    private static final String POSINFO_DOWNLOAD = "20007";
    private static final String POSMCHTINFO_DOWNLOAD = "20006";

    //lihl and end
    private static final String TXNINFO_DOWNLOAD = "20011";
    private static final String INITERROR="INITERROR";

    public void init() throws ServletException {
        //�������Ի���
        String debugFlag=getServletContext().getInitParameter("DEBUG");
        if(debugFlag!=null&&debugFlag.equals("1")){
            Debug.DEBUG=true;
            Debug.setDebugFilePath("toucsdebugunix.log");
            Debug.init();
        }
        //����ͨѶ����
        CommToATMP.ATMPHost=this.getServletContext().getInitParameter("ATMPHost");
        int port=0;
        int timeout=10000;
        try{
            Integer tmp=new Integer(this.getServletContext().getInitParameter("ATMPPort"));
            port=tmp.intValue();
            tmp=new Integer(this.getServletContext().getInitParameter("ConnATMPTimeOut"));
            timeout=tmp.intValue();
            }catch(Exception exp){ }
            CommToATMP.ATMPPort=port;
            CommToATMP.timeOut=timeout;
            try{
                CodeManageBean.initCodeTable();
                CodeManageBean.initCodeTypes();
            }catch(MonitorException exp){
            	exp.printStackTrace();
            }
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            execDoGet(request,response);
        }catch(MonitorException exp){
            // do something �����쳣
            Debug.print("[��ʼ������ʧ��]IP:"+request.getRemoteAddr()+"["+exp.getMessage()+"]");
            exp.printStackTrace();
            errProc(request,response,exp);
        }
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>InitServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
    }

    /**
     * �ͻ���ϵͳ��������
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws MonitorException
     */
    private void clientInitSysCode(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, MonitorException{
        SqlAccess sq=SqlAccess.createConn();
        StringBuffer buf=new StringBuffer();
        try{
            ResultSet rst=sq.queryselect("SELECT * FROM kernel_code ORDER BY code_type,sys_code");
            CodeBean code=new CodeBean();
            while(rst.next()){
                code.fetchData(rst);
                code.toXML(buf);
            }
            rst.close();
        }catch(SQLException exp){
            throw new MonitorException(exp);
        }finally{
            sq.close();
        }

        //���Ϳͻ���
        response.setContentType(CONTENT_TYPE);
        PrintWriter out=response.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        out.println("<Deal>");
        out.println("<DealCode>"+SYSCODEINFO_DOWNLOAD+"</DealCode>");
        out.println("<ErrorCode>0000</ErrorCode>");
        out.println("<ErrorDesc>��ѯ�ɹ�</ErrorDesc>");
        out.println("<Data>");
        out.println(buf.toString());
        out.println("</Data>");
        out.println("</Deal>");
    }

    private void clientInitAtmInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MonitorException{
    	SqlAccess sq= new SqlAccess();
        String contentType=request.getParameter("contentType");
        String atmCode=request.getParameter("objId");
        if(atmCode==null||atmCode.equals("000000"))
            atmCode="";
        if(contentType==null)
            contentType="";
        String sXML="";
        try{
            //����������Ϣ
            if(contentType.equals("0")){
                sXML=getAtmInfo(atmCode,sq);
            } else if (contentType.equals("1")){
                sXML=getAtmBriefInfo(atmCode,sq);
            } else if (contentType.equals("2")){
                sXML=getAtmPosInfo(atmCode,sq);
            }else
                throw new MonitorException(ErrorDefine.REQ_PARA_ERROR,"�����������");
        }catch(SQLException exp){
            throw new MonitorException(exp);
        }finally{
            sq.close();
        }

        //���Ϳͻ���
        response.setContentType(CONTENT_TYPE);
        PrintWriter out=response.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        out.println("<Deal>");
        out.println("<DealCode>"+ATMINFO_DOWNLOAD+"</DealCode>");
        out.println("<ErrorCode>0000</ErrorCode>");
        out.println("<ErrorDesc>��ѯ�ɹ�</ErrorDesc>");
        out.println("<Data>");
        out.println(sXML);
        out.println("</Data>");
        out.println("</Deal>");
    }

    /**
     * ��ѯATM��Ϣ(������Ϣ��λ����Ϣ��״̬��Ϣ)��ת��ΪXML��ʽ����
     * @param atmCode
     * @param sq
     * @return buf
     * @throws SQLException
     */
    private String getAtmInfo(String atmCode,SqlAccess sq) throws SQLException{
        String sql="SELECT atm_info.atm_code,atm_info.atm_name,atm_info.atm_type,atm_info.atm_mode,"
                  +"atm_info.org_id,atm_info.mng_phone,atm_info.atm_addr_type,"
                  +"atm_info.last_mmdd_amount,atm_info.atm_level,atm_info.atm_stat,"
                  +"monit_atmposition.*,atm_stat.* FROM atm_info,monit_atmposition,atm_stat WHERE ";
        if(!atmCode.equals(""))
            sql+="atm_info.atm_code='"+atmCode+"' AND ";
        else
            sql+="atm_info.atm_code LIKE 'A%' AND ";//��Ҫ���˵���ATM�豸�������������ܻ��ȣ�
        sql+="atm_info.atm_code=monit_atmposition.atm_id AND atm_info.atm_code=atm_stat.atm_code";

        ResultSet rst=sq.queryselect(sql);
        AtmBriefInfo atmInfo=new AtmBriefInfo();
        AtmPosInfo posInfo=new AtmPosInfo();
        ATMStatInfo statInfo=new ATMStatInfo();
        StringBuffer buf=new StringBuffer();
        while(rst.next()){
            atmInfo.fetchData(rst);
            posInfo.fetchData(rst);
            statInfo.fetchData(rst);
            buf.append("<AtmInfo id=\""+atmInfo.getAtmCode()+"\">\n");
            atmInfo.toXML(buf);
            posInfo.toXML(buf);
            statInfo.toXML(buf);
            buf.append("</AtmInfo>\n");
        }
        rst.close();
        return buf.toString();
    }

    /**
     * ��ѯATM������Ϣ��ת��ΪXML��ʽ����
     * @param atmCode
     * @param sq
     * @return
     * @throws SQLException
     */
    private String getAtmBriefInfo(String atmCode,SqlAccess sq) throws SQLException{
        String sql="SELECT atm_info.atm_code,atm_info.atm_name,atm_info.atm_type,"
                  +"atm_info.atm_mode,atm_info.org_id,atm_info.mng_phone,atm_info.atm_addr_type,"
                  +"atm_info.last_mmdd_amount,atm_info.atm_level,atm_info.atm_stat"
                  +" FROM atm_info";
        if(!atmCode.equals(""))
            sql+=" WHERE atm_code='"+atmCode+"'";
        else
            sql+=" WHERE atm_code LIKE 'A%'";//��Ҫ���˵���ATM�豸

        ResultSet rst=sq.queryselect(sql);
        AtmBriefInfo atmInfo=new AtmBriefInfo();
        StringBuffer buf=new StringBuffer();
        while(rst.next()){
            atmInfo.fetchData(rst);
            buf.append("<AtmInfo id=\""+atmInfo.getAtmCode()+"\">\n");
            atmInfo.toXML(buf);
            buf.append("</AtmInfo>");
        }
        rst.close();
        return buf.toString();
    }

    /**
     * ��ѯATMλ����Ϣ��ת��ΪXML��ʽ����(����ATMλ�ø��º�,�ͻ�����������ATM��װλ����Ϣ)
     * @param atmCode
     * @param sq
     * @return
     * @throws SQLException
     */
    private String getAtmPosInfo(String atmCode,SqlAccess sq) throws SQLException{
        String sql="SELECT * FROM monit_atmposition";
        if(!atmCode.equals(""))
            sql+=" WHERE atm_id='"+atmCode+"'";
        else
            sql+=" WHERE atm_id LIKE 'A%'";//��Ҫ���˵���ATM�豸s
        ResultSet rst=sq.queryselect(sql);
        AtmPosInfo posInfo=new AtmPosInfo();
        StringBuffer buf=new StringBuffer();
        while(rst.next()){
            posInfo.fetchData(rst);
            buf.append("<AtmInfo id=\""+posInfo.getAtmId()+"\">\n");
            posInfo.toXML(buf);
            buf.append("</AtmInfo>");
        }
        rst.close();
        return buf.toString();
    }



    //lichj add the code
    /**
     * ����ͻ��˳�ʼ������
     * @param request
     * @param response
     * @throws MonitorException
     */
    public void execDoGet(HttpServletRequest request, HttpServletResponse response) throws MonitorException{
        String requestCode=request.getParameter("reqCode");
        if(requestCode==null)requestCode="";
        requestCode=requestCode.trim();
        try{
            if(requestCode.equals(ATMINFO_DOWNLOAD)){
                clientInitAtmInfo(request,response);
            } else if(requestCode.equals(ORGINFO_DOWNLOAD)) {
                clientInitOrgInfo(request,response);
            } else if(requestCode.equals(SYSCODEINFO_DOWNLOAD)) {
                clientInitSysCode(request,response);
            } else if (requestCode.equals(POSINFO_DOWNLOAD)) {
                clientInitPOSInfo(request,response);
            } else if (requestCode.equals(POSMCHTINFO_DOWNLOAD)) {
                clientInitPOSMerchantInfo(request,response);
            } else if (requestCode.equals(TXNINFO_DOWNLOAD)) {
              clientInitTxnInfo(request,response);
            }
        } catch(ServletException srvexp){
            throw new MonitorException(INITERROR,srvexp.getMessage());
        } catch(IOException ioexp){
            throw new MonitorException(INITERROR,ioexp.getMessage());
        }
    }

    private void clientInitOrgInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MonitorException{
        userManagerBean UM = new userManagerBean();
        String objId = request.getParameter("objId");
        if (objId == null || objId.trim().equals("000000")) {
             //objId = "010000000"; marked by liyp 20030903
            //���������д���ǲ�����ģ�׼����Ϊ�����ݿ��в���
            objId = "110000000";
        }
        String XmlStr = UM.getOrgInfoXml(objId,"20003","0000","�ɹ�");
        if (XmlStr == null) {
            XmlStr = new String("");
        }
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println(XmlStr);
    }
    //lichj add the code end
    /**
     * ��ʼ���ͻ���POS��Ϣ
     * @param request ����
     * @param response ��Ӧ
     * @throws ServletException
     * @throws IOException
     * @throws MonitorException
     */
    private void clientInitPOSInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, MonitorException{
        String objId = request.getParameter("objId");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        POSDeviceManageBean pb = new POSDeviceManageBean();
        out.print(pb.getPOSInfo_Xml(objId,POSINFO_DOWNLOAD,"0000","�ɹ�"));
    }

    /**
     * ��ʼ���ͻ����̻���Ϣ
     * @param request ����
     * @param response ��Ӧ
     * @throws ServletException
     * @throws IOException
     * @throws MonitorException
     */
    private void clientInitPOSMerchantInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException,MonitorException{
        String objId = request.getParameter("objId");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        POSMerchantBean pmb = new POSMerchantBean();
        out.print(pmb.getPOSMerchantInfo_Xml(objId,POSMCHTINFO_DOWNLOAD,"0000","�ɹ�"));
    }


    public void destroy(){
        //�������ԣ�������Ի���
        if(Debug.DEBUG)
            Debug.close();
    }
     /**
     * ��ʼ���ͻ���ƽ̨������Ϣ
     * @param request ����
     * @param response ��Ӧ
     * @throws ServletException
     * @throws IOException
     * @throws MonitorException
     */
    private void clientInitTxnInfo(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, MonitorException{
        SqlAccess sq=SqlAccess.createConn();
        StringBuffer buf=new StringBuffer();
        try{
            ResultSet rst=sq.queryselect("SELECT * FROM kernel_txn_code ORDER BY sys_id,txn_code");
            TxnCodeBean code=new TxnCodeBean();
            while(rst.next()){
                code.fetchData(rst);
                code.toXML(buf);
            }
            rst.close();
        }catch(SQLException exp){
            throw new MonitorException(exp);
        }finally{
            sq.close();
        }

        //���Ϳͻ���
        response.setContentType(CONTENT_TYPE);
        PrintWriter out=response.getWriter();
        out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        out.println("<Deal>");
        out.println("<DealCode>"+TXNINFO_DOWNLOAD+"</DealCode>");
        out.println("<ErrorCode>0000</ErrorCode>");
        out.println("<ErrorDesc>��ѯ�ɹ�</ErrorDesc>");
        out.println("<Data>");
        out.println(buf.toString());
        out.println("</Data>");
        out.println("</Deal>");
    }
}
