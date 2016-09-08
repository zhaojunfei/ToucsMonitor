package com.adtec.toucs.monitor.common;

import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: ����쳣��</p>
 * <p>Description: ��װ���ϵͳ������쳣</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MonitorException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�������ͣ�ϵͳ����
    public static final int SYSERR=0;
    //�������ͣ����ݿ����
    public static final int DBERR=1;
    //������
    private String errCode;
    //��������
    private int errType;

   /**
    * ���췽��
    * @param code �������
    * @param msg ��������
    */
    public MonitorException(String code,int type,String msg) {
        super(msg);
        errCode=code;
        errType=type;
    }

    /**
     * ���췽������װ���ݿ����
     * @param sqle SQL�쳣
     */
    public MonitorException(SQLException sqle){
        this(sqle.getErrorCode(),DBERR,sqle.getSQLState());
        if(Debug.DEBUG)
            sqle.printStackTrace();
    }

    /**
     * ���췽��
     * @param code ������
     * @param type ��������
     * @param msg ��������
     */
    public MonitorException(int code,int type,String msg){
        this(String.valueOf(code),type,msg);
    }

    /**
     * ���췽����Ĭ��Ϊϵͳ����
     * @param code ������
     * @param msg ��������
     */
    public MonitorException(int code,String msg){
        this(String.valueOf(code),msg);
    }

    /**
     * ���췽����Ĭ��Ϊϵͳ����
     * @param code ������
     * @param msg ��������
     */
    public MonitorException(String code,String msg){
        this(code,SYSERR,msg);
    }

    /**
     * ���췽��
     * @param code ������
     * @param type ��������
     */
    public MonitorException(String code,int type){
        this(code,type,"");
    }

    /**
     * ���췽����Ĭ��Ϊϵͳ����
     * @param code ������
     */
    public MonitorException(String code){
        this(code,SYSERR,"");
    }

    /**
     * ȡ�������
     * @return �������
     */
    public String getErrCode(){
        return errCode;
    }

    /**
     * ȡ��������
     * @return ��������
     */
    public int getErrType(){
        return errType;
    }

    /**
     * ȡ��������
     * @return ��������
     */
    public String getMessage(){
        String ret=null;
        //�Ӵ��������ȡ��������
        if(errType==DBERR)
            ret=CodeManageBean.getCodeDesc(CodeDefine.DB_ERROR,errCode);
        else
            ret=CodeManageBean.getCodeDesc(CodeDefine.SYS_ERROR,errCode);
        //δȡ�����������ظ����������Ϣ
        if(ret.equals(errCode))
            ret=super.getMessage();
        return getTypeDesc()+ret;
    }

    /**
     * ȡ������������
     * @return ������������
     */
    private String getTypeDesc(){
        if(errType==DBERR)
            return "[���ݿ����]";
        else
            return "[ϵͳ����]";
    }

    public void errProc(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setAttribute("javax.servlet.jsp.jspException",this);
            RequestDispatcher dp=request.getRequestDispatcher("/ToucsMonitor/ToucsMonitor/Error.jsp");
            dp.forward(request,response);
        } catch (Exception ex) {
            Debug.println(ex.getMessage());
        }
    }
}