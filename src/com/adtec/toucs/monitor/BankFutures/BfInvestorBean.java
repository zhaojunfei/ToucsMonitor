package com.adtec.toucs.monitor.BankFutures;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.BankFutures.BfInvestorInfo;
import com.adtec.toucs.monitor.common.*;


public class BfInvestorBean {
	
	public BfInvestorBean() {
	}

  /**
   * ����̲ݹ�˾�ͻ���Ϣ 
   * @param info�Ǽǵ��̲ݹ�˾�ͻ���Ϣ
   * @param iCount δʹ�ò���
   * @return flag �Ǽǳɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.REG_FAILED,"�Ǽ��̲ݹ�˾�ͻ���Ϣʧ�ܣ�");
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
	 * ɾ���̲ݹ�˾�ͻ���Ϣ
	 * @param info�Ǽǵ��̲ݹ�˾�ͻ���Ϣ
	 * @param strKey1 �̲ݹ�˾���
	 * @param strKey2 ����
	 * @return �ɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"ɾ���̲ݹ�˾�ͻ���Ϣʧ�ܣ�");
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
	 * �޸��̲ݹ�˾�ͻ���Ϣ
	 * @param info�Ǽǵ��̲ݹ�˾�ͻ���Ϣ
	 * @param strKey δʹ�ò���
	 * @return flag �ɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
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
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸��̲ݹ�˾�ͻ���Ϣʧ�ܣ�");
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
	 * ��ѯ�̲ݹ�˾�ͻ���Ϣ�б�
	 * @param strKey�̲ݹ�˾���
	 * @param strType���п���
	 * @return �̲ݹ�˾�ͻ���Ϣ,����̲ݹ�˾�ͻ������ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
	 * ��ѯ�̲ݹ�˾�ͻ���Ϣ
	 * @param strKey �̲ݹ�˾���
	 * @param code_type ���п���
	 * @return �̲ݹ�˾�ͻ���Ϣ,����̲ݹ�˾�ͻ������ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
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
