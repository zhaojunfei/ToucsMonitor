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
	 * �Ǽ��̲ݹ�˾��Ϣ
	 * @param info�Ǽǵ��̲ݹ�˾��Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.REG_FAILED,"�Ǽ��̲ݹ�˾��Ϣʧ�ܣ�");
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
	 * ɾ���̲ݹ�˾��Ϣ
	 * @param info�Ǽǵ��̲ݹ�˾��Ϣ
	 * @param strKey �̲ݹ�˾���
	 * @return �ɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"ɾ���̲ݹ�˾��Ϣʧ�ܣ�");
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
	 * �޸��̲ݹ�˾��Ϣ
	 * @param info�Ǽǵ��豸��Ϣ
	 * @param strKey δʹ�ò���
	 * @return �ɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸��̲ݹ�˾��Ϣʧ�ܣ�");
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
	 * ��ѯ�̲ݹ�˾��Ϣ�б�
	 * @param strKey δʹ�ò���
	 * @param strTypeδʹ�ò���
	 * @return �̲ݹ�˾��Ϣ������̲ݹ�˾�����ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
	 * ��ѯ�̲ݹ�˾��Ϣ
	 * @param strKey �̲ݹ�˾���
	 * @param code_type δʹ�ò���
	 * @return info �̲ݹ�˾��Ϣ������̲ݹ�˾�����ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
	 * ��ѯ�̲ݹ�˾��Ϣ
	 * @param strKey �̲ݹ�˾���
	 * @param strStartDate��ʼʱ��
	 * @param strEndDate ����ʱ��
	 * @return info �̲ݹ�˾��Ϣ������̲ݹ�˾�����ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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

			// ������
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

			// ������
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

			// �ɹ�ת������ͽ��
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

			// �ɹ�ת�������ͽ��
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

			// ����ת������ͽ��
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

			// ����ת�������ͽ��
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
	 * ��Կ���س�ʽ����P�˷��ص�
	 * @param posID �豸���
	 * @return �����ֽ���3����Կ���ַ�������
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
	 * ���״����ʽ������ͨѶ��ʽ��P��ͨ�ţ���Ϊ���ṩ���������������ף�����Ĭ��Ϊ���л�����
	 * @param TxCode��P�˽ӿ��еĽ����룬��MG��ͷ������MG7830ΪPOSǩ����
	 * @param DeviceCode �豸���
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
	 * ȡ��ATM������Ϣ��retMsg��
	 * @return ATM������Ϣ
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
