package com.adtec.toucs.monitor.BankFutures;

import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;

import com.adtec.toucs.monitor.BankFutures.BfFcInfo;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;


public class BfFcBean {
	public BfFcBean() {
	}

	private String RetString = "";

	/**
	 * 登记烟草公司信息
	 * @param info登记的烟草公司信息
	 * @return 登记成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int AppendInfo(BfFcInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED,"登记烟草公司信息失败！");
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
	 * 删除烟草公司信息
	 * @param info登记的烟草公司信息
	 * @param strKey 烟草公司编号
	 * @return 成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int DeleteInfo(String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		BfFcInfo info = new BfFcInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"删除烟草公司信息失败！");
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
	 * 修改烟草公司信息
	 * @param info登记的设备信息
	 * @param strKey 未使用参数
	 * @return 成功返回1
	 * @throws MonitorException监控系统异常
	 */
	public int UpdateInfo(BfFcInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"修改烟草公司信息失败！");
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
	 * 查询烟草公司信息列表
	 * @param strKey 未使用参数
	 * @param strType未使用参数
	 * @return 烟草公司信息，如果烟草公司不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM bf_fc ORDER BY fc_id";
			BfFcInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new BfFcInfo();
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
	 * 查询烟草公司信息
	 * @param strKey 烟草公司编号
	 * @param code_type 未使用参数
	 * @return info 烟草公司信息，如果烟草公司不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public BfFcInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM bf_fc WHERE fc_id='" + strKey + "'";
			BfFcInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new BfFcInfo();
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
	 * 查询烟草公司信息
	 * @param strKey 烟草公司编号
	 * @param strStartDate起始时间
	 * @param strEndDate 结束时间
	 * @return info 烟草公司信息，如果烟草公司不存在，返回null
	 * @throws MonitorException监控系统异常
	 */
	public BfFcStatistic QueryStatisticInfo(String strKey, String strStartDate,String strEndDate) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			BfFcStatistic info = new BfFcStatistic();
			ResultSet rst;
			int intValue;
			double dblValue;
			String strValue = null;

			info.setFc_id(strKey);
			info.setStart_date(strStartDate);
			info.setEnd_date(strEndDate);

			// 开户数
			sql = "SELECT COUNT(*) FROM bf_investor ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql + "AND use_flag = '1' ";
			sql = sql + "AND modify_date >= '" + strStartDate + "' ";
			sql = sql + "AND modify_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setOpen_num(strValue);
			}
			rst.close();

			// 销户数
			sql = "SELECT COUNT(*) FROM bf_investor ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql + "AND use_flag = '2' ";
			sql = sql + "AND modify_date >= '" + strStartDate + "' ";
			sql = sql + "AND modify_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setClose_num(strValue);
			}
			rst.close();

			// 成功转入笔数和金额
			sql = "SELECT COUNT(*), SUM(tran_amt) FROM bf_txnlog_hist ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql
					+ "AND tran_code ='BF0200' AND tran_flag = '0' AND tran_resp = '00' ";
			sql = sql + "AND tran_date >= '" + strStartDate + "' ";
			sql = sql + "AND tran_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setOk_cre_num(strValue);
				dblValue = rst.getDouble(2);
				strValue = doubleToStr(dblValue, 0, 2);
				info.setOk_cre_amt(strValue);
			}
			rst.close();

			// 成功转出笔数和金额
			sql = "SELECT COUNT(*), SUM(tran_amt) FROM bf_txnlog_hist ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql + "AND tran_code ='BF0300' AND tran_flag = '0' AND tran_resp = '00' ";
			sql = sql + "AND tran_date >= '" + strStartDate + "' ";
			sql = sql + "AND tran_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setOk_deb_num(strValue);
				dblValue = rst.getDouble(2);
				strValue = doubleToStr(dblValue, 0, 2);
				info.setOk_deb_amt(strValue);
			}
			rst.close();

			// 可疑转入笔数和金额
			sql = "SELECT COUNT(*), SUM(tran_amt) FROM bf_txnlog_hist ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql + "AND tran_code ='BF0200' AND tran_flag = '3' ";
			sql = sql + "AND tran_date >= '" + strStartDate + "' ";
			sql = sql + "AND tran_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setQe_cre_num(strValue);
				dblValue = rst.getDouble(2);
				strValue = doubleToStr(dblValue, 0, 2);
				info.setQe_cre_amt(strValue);
			}
			rst.close();

			// 可疑转出笔数和金额
			sql = "SELECT COUNT(*), SUM(tran_amt) FROM bf_txnlog_hist ";
			sql = sql + "WHERE fc_id matches '" + strKey + "' ";
			sql = sql + "AND tran_code ='BF0300' AND tran_flag = '3' ";
			sql = sql + "AND tran_date >= '" + strStartDate + "' ";
			sql = sql + "AND tran_date <= '" + strEndDate + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setQe_deb_num(strValue);
				dblValue = rst.getDouble(2);
				strValue = doubleToStr(dblValue, 0, 2);
				info.setQe_deb_amt(strValue);
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
	 * 密钥下载程式，将P端返回的
	 * @param posID 设备编号
	 * @return 包含分解后的3组密钥的字符串数组
	 * @throws MonitorException
	 */
	public String down_auth(String key, String use_flag) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.MASTER_KEYDOWN, key, use_flag);
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}

	/**
	 * 交易处理程式，调用通讯程式与P端通信，因为不提供按机构的批量交易，所以默认为所有机构。
	 * @param TxCode与P端接口中的交易码，以MG开头，例如MG7830为POS签到。
	 * @param DeviceCode 设备编号
	 * @throws MonitorException
	 */
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, "BF");
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
	}

	/**
	 * 取得ATM返回消息，retMsg域
	 * @return ATM返回消息
	 */
	public String getRetMsg() {
		return RetString;
	}

	public static String doubleToStr(double d, int vNumber, int fNumber) {
		int i = 0;
		if (vNumber <= 0)
			vNumber = 1;
		if (fNumber < 0)
			fNumber = 0;
		String pattern = "";
		for (i = 0; i < vNumber; i++) {
			pattern = pattern + "#";
		}
		pattern = pattern + "0";

		switch (fNumber) {
		case 0:
			break;
		default:
			pattern += ".";
			for (i = 0; i < fNumber; i++) {
				pattern = pattern + "0";
			}
			break;
		}

		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern(pattern);
		String value = formatter.format(d);

		return value;
	}
}
