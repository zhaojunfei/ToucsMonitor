package com.adtec.toucs.monitor.AgentManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class ActTellerBean {
	
	public ActTellerBean() {
	}

  /**
   * �Ǽǹ�Ա��Ϣ
   * @param info �ǼǵĹ�Ա��Ϣ
   * @param iCount δʹ�ò���
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int AppendInfo(ActTellerInfo info, int iCount) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeInsertStm(sq.conn);
  			int flag = stm.executeUpdate();		
  			if (flag == 1) {
  				sq.commit();
  			}     else {
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
   * ɾ����Ա��Ϣ
   * @param strKey1  δʹ�ò���
   * @param strKey2  δʹ�ò���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		ActTellerInfo info = new ActTellerInfo();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
  			int flag = stm.executeUpdate(); 	 
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ����Ա��Ϣʧ�ܣ�");
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
   * �޸Ĺ�Ա��Ϣ
   * @param info �ǼǵĹ�Ա��Ϣ
   * @param strKey δʹ�ò���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int UpdateInfo(ActTellerInfo info, String strKey) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
  			int flag = stm.executeUpdate(); 	  
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸Ĺ�Ա��Ϣʧ�ܣ�");
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
   * ��ѯ��Ա��Ϣ
   * @param strKey ������
   * @param strType δʹ�ò���
   * @return v ��Ա��Ϣ�������Ա�����ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			if ( strKey != null && !strKey.trim().equals("")) {
  				sql = "SELECT * FROM act_teller WHERE branch_id='" + strKey + "'";
  			} else {
  				sql = "SELECT * FROM act_teller";
  			}
  			ActTellerInfo info = null;
  			Vector v = null;
  			ResultSet rst = sq.queryselect(sql);
  			v = new Vector();
  			while (rst != null && rst.next()) {
  				info = new ActTellerInfo();
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
   * ��ѯ��Ա��Ϣ
   * @param strKey ��Ա��
   * @param strType  δʹ�ò���
   * @return ��Ա��Ϣ�������Ա�����ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public ActTellerInfo QueryInfo(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			sql = "SELECT * FROM act_teller WHERE teller_id='" + strKey + "'"; 	
  			ActTellerInfo info = null;
  			ResultSet rst = sq.queryselect(sql);
  			if (rst != null && rst.next()) {
  				info = new ActTellerInfo();
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
   * @param strKey �̻����  
   * @param strFlag ��־"1"-����"0"-ͣ��
   * @throws MonitorException
   */
  	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
  		SqlAccess conn = new SqlAccess();
  		try {
  			if (strKey == null || strKey.trim().equals("")) {
  				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
  			}
  			String sqlStr = "UPDATE act_teller SET use_flag = '"+strFlag+"' WHERE teller_id = '" +
  			strKey + "'";
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