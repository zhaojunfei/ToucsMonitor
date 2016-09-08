package com.adtec.toucs.monitor.common;

import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: 监控异常类</p>
 * <p>Description: 封装监控系统定义的异常</p>
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
	//错误类型：系统错误
    public static final int SYSERR=0;
    //错误类型：数据库错误
    public static final int DBERR=1;
    //错误码
    private String errCode;
    //错误类型
    private int errType;

   /**
    * 构造方法
    * @param code 错误代码
    * @param msg 错误描述
    */
    public MonitorException(String code,int type,String msg) {
        super(msg);
        errCode=code;
        errType=type;
    }

    /**
     * 构造方法（封装数据库错误）
     * @param sqle SQL异常
     */
    public MonitorException(SQLException sqle){
        this(sqle.getErrorCode(),DBERR,sqle.getSQLState());
        if(Debug.DEBUG)
            sqle.printStackTrace();
    }

    /**
     * 构造方法
     * @param code 错误码
     * @param type 错误类型
     * @param msg 错误描述
     */
    public MonitorException(int code,int type,String msg){
        this(String.valueOf(code),type,msg);
    }

    /**
     * 构造方法（默认为系统错误）
     * @param code 错误码
     * @param msg 错误描述
     */
    public MonitorException(int code,String msg){
        this(String.valueOf(code),msg);
    }

    /**
     * 构造方法（默认为系统错误）
     * @param code 错误码
     * @param msg 错误描述
     */
    public MonitorException(String code,String msg){
        this(code,SYSERR,msg);
    }

    /**
     * 构造方法
     * @param code 错误码
     * @param type 错误类型
     */
    public MonitorException(String code,int type){
        this(code,type,"");
    }

    /**
     * 构造方法（默认为系统错误）
     * @param code 错误码
     */
    public MonitorException(String code){
        this(code,SYSERR,"");
    }

    /**
     * 取错误代码
     * @return 错误代码
     */
    public String getErrCode(){
        return errCode;
    }

    /**
     * 取错误类型
     * @return 错误类型
     */
    public int getErrType(){
        return errType;
    }

    /**
     * 取错误描述
     * @return 错误描述
     */
    public String getMessage(){
        String ret=null;
        //从错误码表中取错误描述
        if(errType==DBERR)
            ret=CodeManageBean.getCodeDesc(CodeDefine.DB_ERROR,errCode);
        else
            ret=CodeManageBean.getCodeDesc(CodeDefine.SYS_ERROR,errCode);
        //未取得描述，返回父类的描述信息
        if(ret.equals(errCode))
            ret=super.getMessage();
        return getTypeDesc()+ret;
    }

    /**
     * 取错误类型描述
     * @return 错误类型描述
     */
    private String getTypeDesc(){
        if(errType==DBERR)
            return "[数据库错误]";
        else
            return "[系统错误]";
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