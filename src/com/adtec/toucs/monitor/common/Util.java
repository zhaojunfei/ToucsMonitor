package com.adtec.toucs.monitor.common;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>Title: 实用类</p>
 * <p>Description: 提供一些实用的方法</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Util {
    //XML报文头
    public static final String PACK_HEADER="<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>";
    //缺省日期格式
    private static String defDtPtn="yyyy-MM-dd hh:mm:ss";
    //日期格式化对象
    private static SimpleDateFormat DATEFORMAT=new SimpleDateFormat(defDtPtn);

  /**
   * 格式化日期对象
   * @param date 日期对象
   * @param pattern 日期格式
   * @return 格式化后的日期字符串
   */
    public static String formatDate(java.util.Date date,String pattern){
        String retv;
        DATEFORMAT.applyPattern(pattern);
        retv=DATEFORMAT.format(date);
        DATEFORMAT.applyPattern(defDtPtn);
        return retv;
    }

  /**
   * 格式化日期对象，使用缺省的格式
   * @param date 日期对象
   * @return 格式化后的日期字符串
   */
    public static String formatDate(java.util.Date date){
        return DATEFORMAT.format(date);
    }

  /**
   * URL编码
   * @param field 被编码的内容
   * @return 编码后的内容
   */
    public static int getIntPara(HttpServletRequest req,String paraName,int def){
        int ret=def;
        try{
            ret=Integer.parseInt(req.getParameter(paraName));
        }catch(Exception exp){
        	exp.printStackTrace();
        }
        return ret;
    }

  /**
   * 从Http请求中取字符参数
   * @param req Http请求
   * @param paraName 参数名
   * @param def 缺省值
   * @return 所取得的字符串参数值
   */
    public static String getStrPara(HttpServletRequest req,String paraName,String def){
        String ret=req.getParameter(paraName);
        if(ret==null)
            ret=def;
        else
        	ret=ret.trim();
        return ret;
    }

  /**
   * 从Http请求中取浮点型参数
   * @param req Http请求
   * @param paraName 参数名
   * @param def 缺省值
   * @return 所取得的浮点型参数值
   */
    public static float getFloatPara(HttpServletRequest req,String paraName,float def){
        float ret=def;
        try{
            ret=Float.parseFloat(req.getParameter(paraName));
        }catch(Exception exp){
        	exp.printStackTrace();
        }
        return ret;
    }

  /**
   * 从结果集中取字符串字段值
   * @param rst 结果集
   * @param colName 字段名
   * @param def 却省值
   * @return 字段值
   * @throws SQLException
   */
    public static String getString(ResultSet rst,String colName,String def) throws SQLException{
        String ret=rst.getString(colName);
        if(ret==null)
            ret=def;
        if(ret!=null)
            ret=ret.trim();
        return ret;
    }

    public static void main(String[] args) {
    }


  /**
   * 获取当前系统日期
   * @return recdate
   */
    public static String getCurrentDate(){
        Calendar mC = Calendar.getInstance();
        
        String sYear = String.valueOf(mC.get(Calendar.YEAR));
        String sMonth = String.valueOf(mC.get(Calendar.MONTH)+1);
        if (sMonth.length()==1){
            sMonth = "0"+sMonth;
        }
        String sDay = String.valueOf(mC.get(Calendar.DATE));
        if (sDay.length()==1){
            sDay = "0"+sDay;
        }
        String recdate = sYear + sMonth + sDay;
        return recdate;
    }

  /**
   * 获取系统当前时间
   * @return hour+minute+second
   */
    public static String getCurrentTime(){
        String hour=String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        if (hour.length()==1)
            hour="0"+hour;
        String minute=String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        if (minute.length()==1)
            minute="0"+minute;
        String second=String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
        if (second.length()==1)
            second="0"+second;
        return hour+minute+second;
    }
}
