package com.adtec.toucs.monitor.common;

import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>Title: ʵ����</p>
 * <p>Description: �ṩһЩʵ�õķ���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Util {
    //XML����ͷ
    public static final String PACK_HEADER="<?xml version=\"1.0\" encoding=\"ISO8859-1\"?>";
    //ȱʡ���ڸ�ʽ
    private static String defDtPtn="yyyy-MM-dd hh:mm:ss";
    //���ڸ�ʽ������
    private static SimpleDateFormat DATEFORMAT=new SimpleDateFormat(defDtPtn);

  /**
   * ��ʽ�����ڶ���
   * @param date ���ڶ���
   * @param pattern ���ڸ�ʽ
   * @return ��ʽ����������ַ���
   */
    public static String formatDate(java.util.Date date,String pattern){
        String retv;
        DATEFORMAT.applyPattern(pattern);
        retv=DATEFORMAT.format(date);
        DATEFORMAT.applyPattern(defDtPtn);
        return retv;
    }

  /**
   * ��ʽ�����ڶ���ʹ��ȱʡ�ĸ�ʽ
   * @param date ���ڶ���
   * @return ��ʽ����������ַ���
   */
    public static String formatDate(java.util.Date date){
        return DATEFORMAT.format(date);
    }

  /**
   * URL����
   * @param field �����������
   * @return ����������
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
   * ��Http������ȡ�ַ�����
   * @param req Http����
   * @param paraName ������
   * @param def ȱʡֵ
   * @return ��ȡ�õ��ַ�������ֵ
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
   * ��Http������ȡ�����Ͳ���
   * @param req Http����
   * @param paraName ������
   * @param def ȱʡֵ
   * @return ��ȡ�õĸ����Ͳ���ֵ
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
   * �ӽ������ȡ�ַ����ֶ�ֵ
   * @param rst �����
   * @param colName �ֶ���
   * @param def ȴʡֵ
   * @return �ֶ�ֵ
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
   * ��ȡ��ǰϵͳ����
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
   * ��ȡϵͳ��ǰʱ��
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
