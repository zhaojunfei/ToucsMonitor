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

public class ActDeviceBean {
	
	public ActDeviceBean() {
	}
  
  /**
   * 登记商户设备信息
   * @param info 登记的商户设备信息
   * @param iCount 为使用到得参数
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int AppendInfo(ActDeviceInfo info, int iCount) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeInsertStm(sq.conn);
  			int flag = stm.executeUpdate(); 	 
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.REG_FAILED, "登记设备信息失败！");
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
   * 删除商户设备信息
   * @param info 登记的商户设备信息
   * @param strkey1 商户号
   * @param strkey2 商户设备号
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		ActDeviceInfo info = new ActDeviceInfo();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
  			int flag = stm.executeUpdate(); 	   
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback(); 
  				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除设备信息失败！");
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
   * 修改商户设备信息
   * @param info 登记的设备信息
   * @param strKey 未使用的参数
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
  	public int UpdateInfo(ActDeviceInfo info, String strKey) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
  			int flag = stm.executeUpdate();
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改设备信息失败！");
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
   * 查询商户设备信息集合
   * @param strKey 商户号
   * @param strType 代理种类,为使用的参数
   * @param info 商户信息
   * @return v 商户设备信息列表,如果商户不存在,返回null
   * @throws MonitorException 监控系统异常
   */
  	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			if ( strKey != null && !strKey.trim().equals("")) {
  				sql = "SELECT * FROM act_info WHERE merchant_id='" + strKey + "'";
  			} else {
  				sql = "SELECT * FROM act_info";
  			}
  			ActDeviceInfo info = null;
  			Vector v = null;
  			ResultSet rst = sq.queryselect(sql);
  			v = new Vector();
  			while (rst != null && rst.next()) {
  				info = new ActDeviceInfo();
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
   * 查询商户设备信息
   * @param strKey 商户设备编号
   * @param strType 为使用参数
   * @return info 商户设备信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
  	public ActDeviceInfo QueryInfo(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			sql = "SELECT * FROM act_info WHERE equip_id='" + strKey + "'";
  			ActDeviceInfo info = null;
  			ResultSet rst = sq.queryselect(sql);
  			if (rst != null && rst.next()) {
  				info = new ActDeviceInfo();
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
   * 商户设备管理,包括启用和停用。
   * @param strKey 商户设备编号  
   * @param strFlag 标志"1"-启用"0"-停用
   * @return affect 成功返回1
   * @throws MonitorException
   */
  	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
  		SqlAccess conn = new SqlAccess();
  		try {
  			if (strKey == null || strKey.trim().equals("")) {
  				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
  			}
  			String sqlStr = "UPDATE act_info SET use_flag = '"+strFlag+"' WHERE equip_id = '" + strKey + "'";
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