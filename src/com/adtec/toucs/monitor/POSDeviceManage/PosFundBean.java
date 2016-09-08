package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class PosFundBean {

	/**
	 * <p>Title: 基金签约统计</p>
	 * <p>Description: 基金签约统计</p>
	 * <p>Copyright: Copyright (c) 2009</p>
	 * <p>Company: ADTEC</p>
	 * @author liuxy
	 * @version 1.0
	 */
		
	public PosFundBean() {
		
	}
	
	/**
	 * 查询是否需要获取 昨日表 数据
	 * @param strStartDate 起始日期
	 * @param strEndDate 终止日期
	 * @return yestoday 昨日表表名
	 * @throws MonitorException
	 */
	public String YestodayName ( String strStartDate, String strEndDate ) throws MonitorException {	
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		String yestoday = "";
		int yday,sday,eday;
		try {
			sql = "SELECT yesterday_log FROM kernel_sys_para WHERE sys_id='pos'";
			rst = sq.queryselect( sql );
			if ( rst != null && rst.next() ) {
				yestoday = rst.getString(1).trim();
			}
			yday = Integer.parseInt( yestoday.substring(3,11) );
			sday = Integer.parseInt( strStartDate );
			eday = Integer.parseInt( strEndDate );
			
			if ( yday>=sday && yday<=eday ) {
				return yestoday;
			}else{ 
				return null;
			}
		} catch ( SQLException e1 ) {
			throw new MonitorException( e1 );
		} finally {
			sq.close();
		}
	}
	
	/**
	 * 基金签约统计
	 *     根据 日期 从历史表(昨日表)中取得统计信息
	 * @param strStartDate 起始日期
	 * @param strEndDate 终止日期
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QueryInfo( String strStartDate, String strEndDate ) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		Vector v = new Vector();
		PosFundContract info = null;
		int i = 0;
		int j = 0;
		try {
			//从POS签约表中取出所有机构的机构编号(branch_id)
			sql = "SELECT branch_id FROM pos_enrol GROUP BY branch_id";
			rst = sq.queryselect( sql );
			while (rst != null && rst.next()) {
				info = new PosFundContract();
				i = QueryInfo1( "411000", rst.getString(1).trim(), strStartDate, strEndDate );
				j = QueryInfo1( "421000", rst.getString(1).trim(), strStartDate, strEndDate );
				info.setBranch_id( rst.getString(1) );
				info.setContranct_num( i + "" );
				info.setNocontranct_num( j + "" );
				info.setStart_date(strStartDate);
				info.setEnd_date(strEndDate);		
				if ( i != 0 || j != 0 ){
					v.add( info );
				}
			}
			return v;
		} catch ( SQLException e1 ) {
			throw new MonitorException( e1 );
		} finally {
			sq.close();
		}
	}
	
	public int QueryInfo1( String auth_code, String strKey,String strStartDate, String strEndDate ) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();		
		String sql;
		ResultSet rst; 
		int k = 0;
		try {
			sql = "SELECT pos_code FROM pos_enrol WHERE branch_id = '" + strKey + "' ";
			rst = sq.queryselect( sql );
			while (rst != null && rst.next()) {
				k = k + QueryInfo2( "pos_txn_log", auth_code, rst.getString(1).trim(), strStartDate, strEndDate );
			}
			return k;
		} catch ( SQLException e1 ) {
			throw new MonitorException( e1 );
		} finally {
			sq.close();
		}
	}
	
	public int QueryInfo2( String tableName, String auth_code, String strKey,String strStartDate, String strEndDate ) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();		
		String sql;
		ResultSet rst; 
		int m = 0;
		int n = 0;
		int p = 0;
		try {
			sql = "SELECT count(*) FROM " + tableName + " ";
			sql = sql + "WHERE pos_code = '" + strKey + "' ";
			sql = sql + "AND auth_code = '" + auth_code + "' ";
			sql = sql + "AND tran_flag = '0' ";
			sql = sql + "AND trans_date >= '" + strStartDate + "' ";
			sql = sql + "AND trans_date <= '" + strEndDate + "' ";
			rst = sq.queryselect( sql );
			if ( rst != null && rst.next() ) {
				m = rst.getInt(1);
			}
			tableName = YestodayName( strStartDate, strEndDate );
			if ( tableName != null ) {	
				sql = "SELECT count(*) FROM " + tableName + " ";
				sql = sql + "WHERE pos_code = '" + strKey + "' ";
				sql = sql + "AND auth_code = '" + auth_code + "' ";
				sql = sql + "AND tran_flag = '0' ";
				sql = sql + "AND trans_date >= '" + strStartDate + "' ";
				sql = sql + "AND trans_date <= '" + strEndDate + "' ";
				rst = sq.queryselect( sql );
				if ( rst != null && rst.next() ) {
					n = rst.getInt(1);
				}
			}
			p = m+n;
			return p;
		} catch ( SQLException e1 ) {
			throw new MonitorException( e1 );
		} finally {
			sq.close();
		}
	}
	
	/**
	 * 取当前系统时间并格式化
	 * 返回 date (yyyymmdd)
	 */
	public String sysDate(){
		Date date = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
		String times = from.format(date);
		return times;
	}
}