package com.adtec.toucs.monitor.BankFutures;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.BankFutures.BfTellerInfo;
import com.adtec.toucs.monitor.common.*;


public class BfTellerBean {
	
	public BfTellerBean() {
	}

	/**
	 * 登记烟草柜员信息 
	 * @param info登记的烟草柜员信息
	 * @param iCount 未使用的参数
	 * @return flag登记成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int AppendInfo(BfTellerInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();	
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED,"登记期货柜员信息失败！");
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
	 * 删除烟草柜员信息
	 * @param info登记的烟草柜员信息
	 * @param strKey1 柜员号
	 * @param strKey2 未使用的参数
	 * @return flag 成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int DeleteInfo(String strKey1,String strKey2) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		BfTellerInfo info = new BfTellerInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"删除期货柜员信息失败！");
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
	 * 修改烟草柜员信息
	 * @param info登记的烟草柜员信息
	 * @param strKey 未使用参数
	 * @return flag 成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int UpdateInfo(BfTellerInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"修改期货柜员信息失败！");
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
	 * 查询烟草柜员信息
	 * @param strKey未使用参数
	 * @param strType 未使用参数
	 * @return 商户信息，如果商户不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM bf_host_op";
			BfTellerInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new BfTellerInfo();
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
	 * 查询烟草柜员信息
	 * @param strKey 柜员号
	 * @param code_type 未使用参数
	 * @return info 烟草柜员信息，如果烟草柜员不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public BfTellerInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM bf_host_op WHERE op_no='" + strKey + "'";
			BfTellerInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new BfTellerInfo();
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
