package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPublicAppBean {

	/**
	 * 修改EMV卡参数信息
	 * @param info 登记的EMV卡参数信息
	 * @return 登记成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int updateInfo(POSPublicApp info, String aid, String memo1) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			// 添加EMV卡参数信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, aid, memo1);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"修改EMV卡参数信息失败！");
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
	 * 查询EMV卡参数信息
	 * @param aid EMV卡参数编号
	 * @param orgId 当前用户所属机构
	 * @return 商户信息，如果商户不存在，返回null
	 * @throws MonitorException 监控系统异常
	 */
	public POSPublicApp queryInfo(String aid, String code_type) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 查询EMV卡参数信息
			String sql;
			sql = "SELECT * FROM pos_public_app WHERE aid='" + aid + "'";
			POSPublicApp info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPublicApp();
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
	 * 查询EMV卡参数 
	 * @param aid EMV卡参数编号
	 * @param orgId  当前用户所属机构
	 * @return 商户信息，如果商户不存在，返回null
	 * @throws MonitorException  监控系统异常
	 */
	public List VqueryInfo() throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 查询EMV卡参数信息
			String sql;
			sql = "SELECT * FROM pos_public_app ORDER BY aid";
			POSPublicApp Info = null;
			List list = new ArrayList();
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				Info = new POSPublicApp();
				Info.fetchData(rst);
				list.add(Info);
			}
			rst.close();
			return list;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	/**
	 * 添加EMV卡参数信息
	 * @return 添加成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int addInfo(POSPublicApp info, int nCount) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			// 添加EMV卡参数信息
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit();
			}else {
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED,
						"添加EMV卡参数信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			throw new MonitorException(e1);
		} finally {
			if (sq != null) {
				if (sq.isConnectDB()) {
					sq.close();
				}
			}
		}
	}

	/**
	 * 删除EMV卡参数
	 * @param info 登记的EMV卡参数信息
	 * @return 登记成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int deleteInfo(String aid) throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		// pos商户实例化
		POSPublicApp dinfo = new POSPublicApp();
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, aid);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"删除EMV卡参数失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
}
