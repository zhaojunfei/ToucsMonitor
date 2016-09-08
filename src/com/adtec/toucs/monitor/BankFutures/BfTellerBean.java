package com.adtec.toucs.monitor.BankFutures;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.BankFutures.BfTellerInfo;
import com.adtec.toucs.monitor.common.*;


public class BfTellerBean {
	
	public BfTellerBean() {
	}

	/**
	 * �Ǽ��̲ݹ�Ա��Ϣ 
	 * @param info�Ǽǵ��̲ݹ�Ա��Ϣ
	 * @param iCount δʹ�õĲ���
	 * @return flag�Ǽǳɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.REG_FAILED,"�Ǽ��ڻ���Ա��Ϣʧ�ܣ�");
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
	 * ɾ���̲ݹ�Ա��Ϣ
	 * @param info�Ǽǵ��̲ݹ�Ա��Ϣ
	 * @param strKey1 ��Ա��
	 * @param strKey2 δʹ�õĲ���
	 * @return flag �ɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"ɾ���ڻ���Ա��Ϣʧ�ܣ�");
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
	 * �޸��̲ݹ�Ա��Ϣ
	 * @param info�Ǽǵ��̲ݹ�Ա��Ϣ
	 * @param strKey δʹ�ò���
	 * @return flag �ɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸��ڻ���Ա��Ϣʧ�ܣ�");
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
	 * ��ѯ�̲ݹ�Ա��Ϣ
	 * @param strKeyδʹ�ò���
	 * @param strType δʹ�ò���
	 * @return �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
	 * ��ѯ�̲ݹ�Ա��Ϣ
	 * @param strKey ��Ա��
	 * @param code_type δʹ�ò���
	 * @return info �̲ݹ�Ա��Ϣ������̲ݹ�Ա�����ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
