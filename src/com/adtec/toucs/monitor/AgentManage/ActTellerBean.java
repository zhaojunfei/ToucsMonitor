package com.adtec.toucs.monitor.AgentManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class ActTellerBean {
	
	public ActTellerBean() {
	}

  /**
   * 登记柜员信息
   * @param info 登记的柜员信息
   * @param iCount 未使用参数
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int AppendInfo(ActTellerInfo info, int iCount) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeInsertStm(sq.conn);
  			int flag = stm.executeUpdate();		
  			if (flag == 1) {
  				sq.commit();
  			}     else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.REG_FAILED, "登记柜员信息失败！");
  			}
  			return flag;
  		} catch (SQLException e1) {
  			sq.rollback();
  			throw new MonitorException(e1);
  		} finally {
  			sq.close();
  		}
  	}

  /**
   * 删除柜员信息
   * @param strKey1  未使用参数
   * @param strKey2  未使用参数
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		ActTellerInfo info = new ActTellerInfo();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
  			int flag = stm.executeUpdate(); 	 
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除柜员信息失败！");
  			}
  			return flag;
  		} catch (SQLException e1) {
  			sq.rollback();
  			throw new MonitorException(e1);
  		} finally {
  			sq.close();
  		}
  	}

  /**
   * 修改柜员信息
   * @param info 登记的柜员信息
   * @param strKey 未使用参数
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int UpdateInfo(ActTellerInfo info, String strKey) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
  			int flag = stm.executeUpdate(); 	  
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改柜员信息失败！");
  			}
  			return flag;
  		} catch (SQLException e1) {
  			sq.rollback();
  			throw new MonitorException(e1);
  		} finally {
  			sq.close();
  		}
  	}

  /**
   * 查询柜员信息
   * @param strKey 渠道号
   * @param strType 未使用参数
   * @return v 柜员信息，如果柜员不存在，返回null
   * @throws MonitorException 监控系统异常
   */
  	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			if ( strKey != null && !strKey.trim().equals("")) {
  				sql = "SELECT * FROM act_teller WHERE branch_id='" + strKey + "'";
  			} else {
  				sql = "SELECT * FROM act_teller";
  			}
  			ActTellerInfo info = null;
  			Vector v = null;
  			ResultSet rst = sq.queryselect(sql);
  			v = new Vector();
  			while (rst != null && rst.next()) {
  				info = new ActTellerInfo();
  				info.fetchData(rst);
  				v.add(info);
  			}
  			rst.close();
  			return v;
  		} catch (SQLException e1) {
  			throw new MonitorException(e1);
  		} finally {
  			sq.close();
  		}
  	}

  /**
   * 查询柜员信息
   * @param strKey 柜员号
   * @param strType  未使用参数
   * @return 柜员信息，如果柜员不存在，返回null
   * @throws MonitorException 监控系统异常
   */
  	public ActTellerInfo QueryInfo(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			sql = "SELECT * FROM act_teller WHERE teller_id='" + strKey + "'"; 	
  			ActTellerInfo info = null;
  			ResultSet rst = sq.queryselect(sql);
  			if (rst != null && rst.next()) {
  				info = new ActTellerInfo();
  				info.fetchData(rst);
  			}
  			rst.close();
  			return info;
  		} catch (SQLException e1) {
  			throw new MonitorException(e1);
  		} finally {
  			sq.close();
  		}
  	}

  /**
   * 柜员管理，包括启用和停用。
   * @param strKey 商户编号  
   * @param strFlag 标志"1"-启用"0"-停用
   * @throws MonitorException
   */
  	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
  		SqlAccess conn = new SqlAccess();
  		try {
  			if (strKey == null || strKey.trim().equals("")) {
  				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
  			}
  			String sqlStr = "UPDATE act_teller SET use_flag = '"+strFlag+"' WHERE teller_id = '" +
  			strKey + "'";
  			int affect = conn.queryupdate(sqlStr);
  			if (affect <= 0) {
  				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
  			}
  			return affect;
  		} catch (SQLException sqlex) {
  			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
  		} finally {
  			conn.close();
  		}
  	}
}