package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:</p>
 * @author unascribed
 * @version 1.0
 */

public class POSFCardMerchantBean {

	public POSFCardMerchantBean() {
	}

	/**
	 * 登记POS新商户
	 * @param info登记的设备信息
	 * @return 登记成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int addPosMerchant(POSFCardMerchantInfo info) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// 添加设备基本信息
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);			
			if (flag == 1) {
				sq.commit();// 提交
			} else {
				sq.rollback();// 事务回滚
				throw new MonitorException(ErrorDefine.ADDMCT_FAILED,"添加POS商户信息失败！");
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
	 * 修改POS商户
	 * @param info登记的设备信息
	 * @return 登记成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int updatePosMerchant(POSFCardMerchantInfo info, String key) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// 设置数据库连接提交方式为非自动提交
			// 此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// 添加设备基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("[????]" + stm.toString() + "FLAG:" + flag);		
			if (flag == 1) {
				sq.commit();// 提交
			} else {
				sq.rollback();// 事务回滚
				throw new MonitorException(ErrorDefine.UPDATEMCT_FAILED,"修改POS商户信息失败！");
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
	 * 删除POS新商户
	 * @param info登记的设备信息
	 * @return 登记成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int deletePosMerchant(String merchantID) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		// pos商户实例化
		POSFCardMerchantInfo mctInfo = new POSFCardMerchantInfo();
		// pos实例化
		POSFCardInfo fposInfo = new POSFCardInfo();
		try {
			// 设置数据库连接提交方式为非自动提交
			// 此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// 查询该商户下Pos设备信息，如果有pos设备，不能删除
			PreparedStatement stm = fposInfo.makeQueryByMctStm(sq.conn,merchantID);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				throw new MonitorException(ErrorDefine.DELMCT_FAILED,"商户下定义有终端信息，请先删除终端信息！");
			}
			// 删除商户信息基本信息
			stm = mctInfo.makeDeleteStm(sq.conn, merchantID);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);			
			if (flag == 1) {
				sq.commit();// 提交
			} else {
				sq.rollback();// 事务回滚
				throw new MonitorException(ErrorDefine.DELMCT_FAILED1,"商户下清理标志为正常，请先清理商户！");
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
	 * 查询商户基本配置信息 
	 * @param atmCode设备编号
	 * @param orgId当前用户所属机构
	 * @return 商户信息，如果商户不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public POSFCardMerchantInfo qryPosMerchant(String merchantID) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// 查询设备信息
			String sql = "SELECT * FROM merchant_list WHERE mer_id='" + merchantID + "'";
			Debug.println("***********SQL:" + sql + "*********");
			POSFCardMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst.next()) {
				info = new POSFCardMerchantInfo();
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
	 * 查询所有商户编号和商户代码信息
	 * @param atmCode设备编号
	 * @param orgId当前用户所属机构
	 * @return 商户信息，如果商户不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public Vector qryMerchantVector() throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// 查询设备信息
			String sql = "SELECT * FROM merchant_list;";
			POSFCardMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			Vector vReturn = new Vector();
			while (rst.next()) {
				info = new POSFCardMerchantInfo();
				info.fetchData(rst);
				vReturn.add(info.getMer_id());
				vReturn.add(info.getMer_name());
			}
			rst.close();
			Debug.println("SIZE:" + vReturn.size());
			return vReturn;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	/**
	 * 根据机构编码查询该机构以及下级机构所拥有的商户信息。
	 * @param orgId机构号
	 * @return LIST列表，每一个元素都是一个POSFCardMerchantInfo对象
	 * @throws MonitorException
	 */
	public List getMerchantList() throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess("FCardPosPool");
		String sqlStr = "";
		try {
			sqlStr = "SELECT * FROM merchant_list ORDER BY mer_id";
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSFCardMerchantInfo pmi = new POSFCardMerchantInfo();
				pmi.fetchData(rs);
				list.add(pmi);
			}
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}
}
