package com.adtec.toucs.monitor.usermanager;

/**
 * <p>Title: LogManager</p>
 * <p>Description: LogManager</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author Fancy
 * @version 1.0
 */


import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

import java.sql.*;
import java.util.*;
import com.adtec.toucs.monitor.common.toucsString;

public class LogManagerBean {

    private SqlAccess conn = null;
    private ResultSet rs = null;
    private static final String POS_KEY_DOWN="12103";
    public LogManagerBean() {
    }

  /**
   * 产生日志ID
   * @return 日志编号
   * @throws MonitorException 异常
   */
    private String BuildLogId() throws MonitorException {
        StringBuffer SqlStr = new StringBuffer();
        SqlStr.append("SELECT MAX(ROUND(log_id/1.0)) FROM monit_log");
        conn = SqlAccess.createConn();
        int maxid = 0;
        String LogId = null;
        try {
            rs = conn.queryselect(SqlStr.toString());
            while (rs != null && rs.next()) {
                maxid = rs.getInt(1);
            }
            LogId = String.valueOf(maxid+1);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonitorException(123,"");
        } finally {
            conn.close();
        }
        return LogId;
    }

  /**
   * 记录操作日志
   * @param user_name 操作员名称
   * @param TermAddr IP地址
   * @param OperDesc 操作描述
   * @return 成功：true，失败：false   *
   * @throws MonitorException
   */
    public boolean log(String user_name,String TermAddr,String OperDesc)throws MonitorException{
        String sql="";
        if ((OperDesc == null)||(OperDesc.length() == 0))
            return false;
        try{
        	sql = "INSERT INTO monit_log(log_id,user_name,oper_time,oper_date,term_addr,oper_memo) VALUES ('"
                +this.BuildLogId()+"','"+user_name+"','"+Util.getCurrentTime()+"','"+Util.getCurrentDate()+"','"+TermAddr+"','"+OperDesc+"')";
            conn = SqlAccess.createConn();
            int res = conn.queryupdate(toucsString.Convert(sql));
            if (res != 1)
                return false;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new MonitorException(sqle);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }finally{
            conn.close();
        }
        return true;
    }

  /**
   * 根据日期段取得日志记录
   * @param beginDate 查询开始日期
   * @param endDate 查询结束日期
   * @return 记录结果向量
   * @throws MonitorException 异常
   */
    public Vector queryLog(String beginDate,String endDate)throws MonitorException{
        StringBuffer SqlStr = new StringBuffer();
        SqlStr.append("SELECT log_id,user_name,term_addr,oper_date,oper_time,oper_memo FROM monit_log ");
        if (beginDate.length() > 0 && endDate.length() > 0) {
            SqlStr.append(" WHERE oper_date >= '");
            SqlStr.append(beginDate+"' AND oper_date <= '");
            SqlStr.append(endDate+"'");
        }
        else if (beginDate.length() == 0 && endDate.length() > 0){
            SqlStr.append(" WHERE oper_date <= '");
            SqlStr.append(endDate+"'");
        }
        else if (endDate.length() == 0 && beginDate.length() > 0) {
            SqlStr.append(" WHERE oper_date >= '");
            SqlStr.append(beginDate+"'");
        }
        SqlStr.append(" ORDER BY log_id DESC");
        conn = SqlAccess.createConn();
        Vector RsVect = new Vector();
        Hashtable logHT = null;
        try {
            rs = conn.queryselect(SqlStr.toString());
            while (rs != null && rs.next()) {
                logHT = new Hashtable();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    logHT.put(rs.getMetaData().getColumnName(i+1),toucsString.unConvert(rs.getString(i+1)));
                }
                RsVect.add(logHT);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonitorException(3000,"查询日志失败！");
        } finally {
            conn.close();
        }
        return RsVect;
    }

  /**
   * 删除日志记录
   * @param beginDate 时间段开始日期
   * @param endDate 时间段结束日期
   * @return 成功：删除记录的条数，失败：－1
   * @throws MonitorException 异常
   */
    public int delLog(String beginDate,String endDate) throws MonitorException{
        StringBuffer SqlStr = new StringBuffer();
        SqlStr.append("DELETE FROM monit_log ");
        if (beginDate.length() > 0 && endDate.length() > 0) {
            SqlStr.append(" WHERE oper_date >= '");
            SqlStr.append(beginDate+"' AND oper_date <= '");
            SqlStr.append(endDate+"'");
        } else if (beginDate.length() == 0 && endDate.length() > 0){
            SqlStr.append(" WHERE oper_date <= '");
            SqlStr.append(endDate+"'");
        } else if (endDate.length() == 0 && beginDate.length() > 0) {
            SqlStr.append(" WHERE oper_date >= '");
            SqlStr.append(beginDate+"'");
        }
        int affect = -1;
        conn = SqlAccess.createConn();
        try {
            affect = conn.queryupdate(SqlStr.toString());
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            conn.close();
        }
        return affect;
    }

  /**
   * 根据日志记录的ID 删除日志记录
   * @param LogId 记录ID
   * @return 成功：删除记录的条数，失败：－1
   * @throws MonitorException 异常
   */
    public int delLog(String LogId) throws MonitorException{
        StringBuffer SqlStr = new StringBuffer();
        SqlStr.append("DELETE FROM monit_log WHERE log_id = '");
        SqlStr.append(LogId+"'");
        int affect = -1;
        conn = SqlAccess.createConn();
        try {
            affect = conn.queryupdate(SqlStr.toString());
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            conn.close();
        }
        return affect;
    }

  /**
   * 此方法为POS密钥下载专用，记录设备ID，资源编号，为密钥下载日志查询交易提供数据。
   * @param login 用户登陆信息
   * @param ResourceId 资源编号，如POS密钥下载为：12104
   * @param DeviceCode 设备编号
   * @param Desc 详细描述信息
   * @return 成功：1，失败：-1；
   * @throws MonitorException
   */
    public int log(LoginInfo login,String ResourceId,String DeviceCode,String Desc) throws MonitorException{
        int affect = -1;
        try {
            String SqlStr = "INSERT INTO monit_log(log_id,user_name,oper_time,oper_date,term_addr,resource_id,oper_memo,memo) VALUES ('";
            SqlStr += BuildLogId()+"','"+login.getUserID()+"','"+Util.getCurrentTime()+"','"+Util.getCurrentDate()+"','"+login.getIP()+"','"+ResourceId+"','"+" "+"','"+DeviceCode+"')";
            conn = SqlAccess.createConn();
            affect = conn.queryupdate(SqlStr);
        } catch (NullPointerException nex) {
            throw new MonitorException(ErrorDefine.NULLPOINTEX,ErrorDefine.NULLPOINTEXDESC);
        } catch (SQLException sqle) {
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            conn.close();
        }
        return affect;
    }

  /**
   * POS密钥下载日志查询
   * @param PosCode POS设备编号
   * @return List
   * @throws MonitorException
   */
    public List queryPosKeyDownLog(String PosCode)throws MonitorException{
        List list = new ArrayList();
        try {
            conn = SqlAccess.createConn();
            String sqlStr = "SELECT log_id,user_name,term_addr,oper_date,oper_time,oper_memo, memo ";
            sqlStr += " FROM monit_log WHERE resource_id = '"+POS_KEY_DOWN+"'";
            if (PosCode != null || !PosCode.equals("")) {
                sqlStr += " AND memo='"+PosCode+"'";
            }
            rs = conn.queryselect(sqlStr);
            if (rs != null) {
                while (rs.next()) {
                    logInfo li = new logInfo();
                    li.setLog_Id(rs.getString(1));
                    li.setUser_Name(rs.getString(2));
                    li.setTerm_Addr(rs.getString(3));
                    li.setOper_Date(rs.getString(4));
                    li.setOper_Time(rs.getString(5));
                    li.setOper_Memo(rs.getString(6));
                    li.setMemo( rs.getString(7));
                    list.add(li);
                }
            }
        } catch (SQLException ex) {
            throw new MonitorException(ex.getErrorCode(),ex.getMessage());
        } finally {
            conn.close();
        }
        return list;
    }
}