package com.adtec.toucs.monitor.systemmanage;

import java.sql.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;


public class DccCtrlBean {
	public DccCtrlBean() {
	}

  /**
   * 登记控制信息
   * @param info 控制信息
   * @param iCount 未使用参数
   * @return 登记成功返回1
   * @throws MonitorException监控系统异常
   */
	public int AppendInfo(DccCtrlInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记控制信息失败！");
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
   * 删除控制信息
   * @param info控制信息类
   * @param sreKey1渠道代号
   * @param strKey2业务种类
   * @return 成功返回1
   * @throws MonitorException监控系统异常
   */
	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		DccCtrlInfo info = new DccCtrlInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1,strKey2);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"删除控制信息失败！");
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
   * 修改控制信息
   * @param info登记的控制信息
   * @param strKey 未使用参数
   * @return 成功返回1
   * @throws MonitorException监控系统异常
   */
	public int UpdateInfo(DccCtrlInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"修改控制信息失败！");
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
   * 查询控制信息
   * @return v控制信息，如果控制信息不存在，返回null
   * @throws MonitorException监控系统异常
   */
	public Vector QueryInfoByList() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM dcc_ctrl";
			DccCtrlInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new DccCtrlInfo();
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
   * 查询控制信息
   * @param strKey1	设备编号
   * @param strKey2	业务种类
   * @return 控制信息，如果控制信息不存在，返回null
   * @throws MonitorException监控系统异常
   */
	public DccCtrlInfo QueryInfo(String strKey1, String strKey2) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM dcc_ctrl WHERE channel_id='" + strKey1 + "' AND service ='"+ strKey2+"'";
			DccCtrlInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new DccCtrlInfo();
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
}
