package com.adtec.toucs.monitor.BankFutures;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.BankFutures.BfInvestorInfo;
import com.adtec.toucs.monitor.common.*;


public class BfInvestorBean {
	
	public BfInvestorBean() {
	}

  /**
   * 添加烟草公司客户信息 
   * @param info登记的烟草公司客户信息
   * @param iCount 未使用参数
   * @return flag 登记成功返回1
   * @throws MonitorException监控系统异常
   */
	public int AppendInfo(BfInvestorInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED,"登记烟草公司客户信息失败！");
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
	 * 删除烟草公司客户信息
	 * @param info登记的烟草公司客户信息
	 * @param strKey1 烟草公司编号
	 * @param strKey2 卡号
	 * @return 成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int DeleteInfo(String strKey1,String strKey2) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		BfInvestorInfo info = new BfInvestorInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"删除烟草公司客户信息失败！");
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
	 * 修改烟草公司客户信息
	 * @param info登记的烟草公司客户信息
	 * @param strKey 未使用参数
	 * @return flag 成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int UpdateInfo(BfInvestorInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"修改烟草公司客户信息失败！");
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
	 * 查询烟草公司客户信息列表
	 * @param strKey烟草公司编号
	 * @param strType银行卡号
	 * @return 烟草公司客户信息,如果烟草公司客户不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			int    sql_flag = 0;
			sql = "SELECT * FROM bf_investor ";
			if ( strKey != null && !strKey.equals("")) {
				sql = sql + "WHERE fc_id =  '" + strKey + "'";
				sql_flag = 1;
			}
			if ( strType != null && !strType.equals("")) {
				if ( sql_flag == 0 ) {
					sql = sql + "WHERE investor_id = '" + strType + "'";
					sql_flag = 1;
				} else {
					sql = sql + "AND investor_id = '" + strType + "'";
				}
			}
			BfInvestorInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new BfInvestorInfo();
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
	 * 查询烟草公司客户信息
	 * @param strKey 烟草公司编号
	 * @param code_type 银行卡号
	 * @return 烟草公司客户信息,如果烟草公司客户不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public BfInvestorInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM bf_investor WHERE fc_id='" + strKey + "' AND investor_id='"+ code_type + "'";
			BfInvestorInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new BfInvestorInfo();
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
