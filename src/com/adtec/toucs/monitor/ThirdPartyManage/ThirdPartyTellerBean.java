package com.adtec.toucs.monitor.ThirdPartyManage;


import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 */

public class ThirdPartyTellerBean {
	
	public ThirdPartyTellerBean() {
	}

  /**
   * �Ǽǵ�������Ա��Ϣ
   * @param info �ǼǵĹ�Ա��Ϣ
   * @param iCount δʹ�ò���
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int AppendInfo(ThirdPartyTellerInfo info, int iCount) throws MonitorException {  
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽǹ�Ա��Ϣʧ�ܣ�");
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
   * ɾ����������Ա��Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param strKey1������
   * @param strKey2 ��Ա��
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		ThirdPartyTellerInfo info = new ThirdPartyTellerInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ����������Ա��Ϣʧ�ܣ�");
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
   * �޸ĵ�������Ա��Ϣ
   * @param info �Ǽǵĵ�������Ա��Ϣ
   * @param strKey δʹ�ò���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int UpdateInfo(ThirdPartyTellerInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸ĵ�������Ա��Ϣʧ�ܣ�");
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
   * ��ѯ��������Ա��Ϣ
   * @param strKey �̻���
   * @param strType δʹ�ò���
   * @return ��Ա��Ϣ�������Ա�����ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			if ( strKey != null && !strKey.trim().equals("")) {
				sql = "SELECT * FROM t_teller_info WHERE branch_id='" + strKey + "'";
			} else {
				sql = "SELECT * FROM t_teller_info";
			}
			ThirdPartyTellerInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new ThirdPartyTellerInfo();
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
   * ��ѯ��������Ա��Ϣ
   * @param strKey ��Ա��
   * @param strType δʹ�ò���
   * @return ��Ա��Ϣ�������Ա�����ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public ThirdPartyTellerInfo QueryInfo(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM t_teller_info WHERE teller_id='" + strKey + "'";
			ThirdPartyTellerInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new ThirdPartyTellerInfo();
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
   * ��Ա�����������ú�ͣ�á�
   * @param strKey ��Ա��
   * @param strFlag ��־"1"-����"0"-ͣ��
   * @throws MonitorException
   */
	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (strKey == null || strKey.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			String sqlStr = "UPDATE t_teller_info SET use_flag = '"+strFlag+"' WHERE teller_id = '" + strKey + "'";
			int affect = conn.queryupdate(sqlStr);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			return affect;
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}
	}
}