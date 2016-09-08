package com.adtec.toucs.monitor.trandetail;

import com.adtec.toucs.monitor.common.*;


import java.util.*;
import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TranDetail {

    private SqlAccess Conn  = null;
    private ResultSet rs = null;
    public TranDetail() {
    }
	//ATM流水详情
    public Vector getTranDetail(String orgId,String atm_code) throws MonitorException{
        Vector retV = new Vector();
        String SqlStr = "SELECT A.atm_code,A.atm_serial,A.trans_code,A.trans_card_no,A.trans_amount,";
        SqlStr += "A.host_response,A.trans_response,A.trans_date,A.trans_time,A.snd_card_no,A.trans_card_type";
        SqlStr += " FROM atm_txnlog A,atm_info B,monit_orgref C";
        SqlStr += " WHERE A.atm_code = B.atm_code AND B.org_id = C.org_id AND C.p_org_id = '";
        SqlStr += orgId;
        SqlStr += "'";
        if (atm_code != null && !atm_code.trim().equals("")) {
            SqlStr += " AND A.atm_code = '";
            SqlStr += atm_code;
            SqlStr += "'";
        }
        SqlStr += " ORDER BY trans_time DESC";
        TranInfo TI = null;
        try {
            Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                TI = new TranInfo();
                TI.setAtmCode(toucsString.unConvert(rs.getString(1)));
                TI.setAtmSerial(toucsString.unConvert(rs.getString(2)));
                TI.setTransCode(toucsString.unConvert(rs.getString(3)));
                TI.setTransCardNo(toucsString.unConvert(rs.getString(4)));
                TI.setTransAmount(toucsString.unConvert(rs.getString(5)));
                TI.setHostResponse(toucsString.unConvert(rs.getString(6)));
                TI.setTransRespons(toucsString.unConvert(rs.getString(7)));
                TI.setTransDate(toucsString.unConvert(rs.getString(8)));
                TI.setTransTime(toucsString.unConvert(rs.getString(9)));
                TI.setSndCardNo(toucsString.unConvert(rs.getString(10)));
                TI.setTransCardType(toucsString.unConvert(rs.getString(11)));
                retV.add(TI);
            }
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            Conn.close();
        }
        return retV;
    }
	//ATM的系统时间
    public String getSysDate(){
        String SqlStr = "SELECT sys_date FROM atm_sys_stat";
        String SysDate = "";
        try {
            Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                SysDate = rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
            Conn.close();
        }
        return SysDate;
    }
   //lihl add begin
    public String getSysDate(String type){
        String SqlStr = "SELECT sys_date FROM kernel_sys_para where sys_id = '" + type + "'";
        String SysDate = "";
        try {
            Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                SysDate = rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
            Conn.close();
        }
        return SysDate;
    }
    
    public Vector getPosTranDetail(String poscode ,String code_type) throws MonitorException{
        code_type = code_type.trim();
        Vector retV = new Vector();
		String SqlStr = "SELECT today_log FROM kernel_sys_para WHERE sys_id = 'pos'";
        try {
        	Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            String logTblName="";
            try {
                if (rs != null && rs.next()) {
                    logTblName = rs.getString(1).trim();
                }
            } catch (Exception ex) {
                throw new MonitorException("1000","取POS当日流水表表名出错！");
            }
	        SqlStr = "SELECT A.pos_code,A.merchant_id,A.pos_serial,A.trans_date,A.trans_time,";
	        SqlStr += "A.host_response,A.trans_response,A.trans_card_no,A.trans_amount,";
	        SqlStr += "A.trans_code,A.trans_card_type,A.tran_flag,A.dc_flag,A.operate_num,A.sys_serial";
	        SqlStr += " FROM ";
                SqlStr += logTblName;
                SqlStr += " A, pos_info B";
                SqlStr += " WHERE A.pos_code = B.pos_code";
	        if (poscode != null && !poscode.trim().equals("")) {
                    if( code_type.equalsIgnoreCase("1") ){
                    	SqlStr += " AND A.pos_code = '";
                    	SqlStr += poscode;
                    	SqlStr += "'";
                    }else if( code_type.equalsIgnoreCase("2") ){
                        SqlStr += " AND A.pos_c_code = '";
                        SqlStr += poscode;
                        SqlStr += "'";
                    }else{
                        SqlStr += " AND A.pos_dcc_code = '";
                        SqlStr += poscode;
                        SqlStr += "'";
                    }
	        }
	        SqlStr += " ORDER BY trans_time DESC";
	        System.out.println("SQL STR:"+SqlStr);
	        PosTranInfo PTI = null;

            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                PTI = new PosTranInfo();
                PTI.setPosCode(toucsString.unConvert(rs.getString(1)));
                PTI.setMerchantid(toucsString.unConvert(rs.getString(2)));
                PTI.setPosSerial(toucsString.unConvert(rs.getString(3)));
                PTI.setTransDate(toucsString.unConvert(rs.getString(4)));
                PTI.setTransTime(toucsString.unConvert(rs.getString(5)));
                PTI.setHostResponse(toucsString.unConvert(rs.getString(6)));
                PTI.setTransRespons(toucsString.unConvert(rs.getString(7)));
                PTI.setTransCardNo(toucsString.unConvert(rs.getString(8)));
                PTI.setTransAmount(toucsString.unConvert(rs.getString(9)));
                PTI.setTransCode(toucsString.unConvert(rs.getString(10)));
                PTI.setTransCardType(toucsString.unConvert(rs.getString(11)));
                PTI.setTransFlag(toucsString.unConvert(rs.getString(12)));
                PTI.setDcFlag(toucsString.unConvert(rs.getString(13)));
                PTI.setOperateNum(toucsString.unConvert(rs.getString(14)));
                //add by liyp 20030809
                PTI.setSysSerial(toucsString.unConvert(rs.getString(15)));
                retV.add(PTI);
            }
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            Conn.close();
        }
        return retV;
    }

    public Vector getCdmTranDetail(String cdmcode) throws MonitorException{
        Vector retV = new Vector();
        String SqlStr = "SELECT today_log FROM kernel_sys_para WHERE sys_id = 'cdm'";
        try {
            Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            String logTblName="";
            try {
                if (rs != null && rs.next()) {
                    logTblName = rs.getString(1).trim();
                }
            } catch (Exception ex) {
                throw new MonitorException("1000","取CDM当日流水表表名出错！");
            }
            SqlStr = "SELECT A.cdm_code,A.cdm_cycle,A.cdm_serial,A.trans_date,A.trans_time,";
            SqlStr += "A.host_response,A.trans_response,A.trans_card_no,A.trans_amount,";
            SqlStr += "A.trans_code,A.trans_card_type,A.snd_card_no";
            SqlStr += " FROM "+logTblName+" A,cdm_info B";
            SqlStr += " WHERE A.cdm_code = B.cdm_code";
            if (cdmcode != null && !cdmcode.trim().equals("")) {
                SqlStr += " AND A.cdm_code = '";
                SqlStr += cdmcode;
                SqlStr += "'";
            }
            SqlStr += " ORDER BY trans_time DESC";
            Debug.println("SQL STR:"+SqlStr);
            CDMTranInfo CTI = null;

            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                CTI = new CDMTranInfo();
                CTI.setCdmCode(toucsString.unConvert(rs.getString(1)));
                CTI.setCdmCycle(toucsString.unConvert(rs.getString(2)));
                CTI.setCdmSerial(toucsString.unConvert(rs.getString(3)));
                CTI.setTransDate(toucsString.unConvert(rs.getString(4)));
                CTI.setTransTime(toucsString.unConvert(rs.getString(5)));
                CTI.setHostResponse(toucsString.unConvert(rs.getString(6)));
                CTI.setTransRespons(toucsString.unConvert(rs.getString(7)));
                CTI.setTransCardNo(toucsString.unConvert(rs.getString(8)));
                CTI.setTransAmount(toucsString.unConvert(rs.getString(9)));
                CTI.setTransCode(toucsString.unConvert(rs.getString(10)));
                CTI.setTransCardType(toucsString.unConvert(rs.getString(11)));
                CTI.setSndCardNo(toucsString.unConvert(rs.getString(12)));
                retV.add(CTI);
            }
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            Conn.close();
        }
        return retV;
    }

    public Vector getMitTranDetail(String mitcode) throws MonitorException{
        Vector retV = new Vector();
        String SqlStr = "SELECT today_log FROM kernel_sys_para WHERE sys_id = 'mit'";
        try {
        	Conn = new SqlAccess();
            rs = Conn.queryselect(SqlStr);
            String logTblName="";
            try {
                if (rs != null && rs.next()) {
                    logTblName = rs.getString(1).trim();
                }
            } catch (Exception ex) {
                throw new MonitorException("1000","取MIT当日流水表表名出错！");
            }
	        SqlStr = "SELECT A.mit_code,A.mit_cycle,A.mit_serial,A.trans_date,A.trans_time,";
	        SqlStr += "A.host_response,A.trans_response,A.trans_card_no,A.trans_amount,";
	        SqlStr += "A.trans_code,A.trans_card_type,A.snd_card_no";
	        SqlStr += " FROM "+ logTblName +" A,mit_info B";
	        SqlStr += " WHERE A.mit_code = B.mit_code ";
	        if (mitcode != null && !mitcode.trim().equals("")) {
	            SqlStr += " AND A.mit_code = '";
	            SqlStr += mitcode;
	            SqlStr += "'";
	        }
	        SqlStr += " ORDER BY trans_time DESC";
	        System.out.println("SQL STR:"+SqlStr);
	        MITTranInfo MITI = null;
            rs = Conn.queryselect(SqlStr);
            while (rs != null && rs.next()) {
                MITI = new MITTranInfo();
                MITI.setMitCode(toucsString.unConvert(rs.getString(1)));
                MITI.setMitCycle(toucsString.unConvert(rs.getString(2)));
                MITI.setMitSerial(toucsString.unConvert(rs.getString(3)));
                MITI.setTransDate(toucsString.unConvert(rs.getString(4)));
                MITI.setTransTime(toucsString.unConvert(rs.getString(5)));
                MITI.setHostResponse(toucsString.unConvert(rs.getString(6)));
                MITI.setTransRespons(toucsString.unConvert(rs.getString(7)));
                MITI.setTransCardNo(toucsString.unConvert(rs.getString(8)));
                MITI.setTransAmount(toucsString.unConvert(rs.getString(9)));
                MITI.setTransCode(toucsString.unConvert(rs.getString(10)));
                MITI.setTransCardType(toucsString.unConvert(rs.getString(11)));
                MITI.setSndCardNo(toucsString.unConvert(rs.getString(12)));
                retV.add(MITI);
            }
        } catch (SQLException sqle){
            throw new MonitorException(sqle);
        } catch (Exception ex) {
            throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
        } finally {
            Conn.close();
        }
        return retV;
    }
}