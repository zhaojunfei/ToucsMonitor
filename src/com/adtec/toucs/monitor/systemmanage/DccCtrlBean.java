package com.adtec.toucs.monitor.systemmanage;

import java.sql.*;
import java.util.*;


import com.adtec.toucs.monitor.common.*;


public class DccCtrlBean {
	public DccCtrlBean() {
	}

  /**
   * �Ǽǿ�����Ϣ
   * @param info ������Ϣ
   * @param iCount δʹ�ò���
   * @return �Ǽǳɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽǿ�����Ϣʧ�ܣ�");
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
   * ɾ��������Ϣ
   * @param info������Ϣ��
   * @param sreKey1��������
   * @param strKey2ҵ������
   * @return �ɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"ɾ��������Ϣʧ�ܣ�");
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
   * �޸Ŀ�����Ϣ
   * @param info�ǼǵĿ�����Ϣ
   * @param strKey δʹ�ò���
   * @return �ɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸Ŀ�����Ϣʧ�ܣ�");
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
   * ��ѯ������Ϣ
   * @return v������Ϣ�����������Ϣ�����ڣ�����null
   * @throws MonitorException���ϵͳ�쳣
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
   * ��ѯ������Ϣ
   * @param strKey1	�豸���
   * @param strKey2	ҵ������
   * @return ������Ϣ�����������Ϣ�����ڣ�����null
   * @throws MonitorException���ϵͳ�쳣
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
