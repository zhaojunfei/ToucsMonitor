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

public class ActDeviceBean {
	
	public ActDeviceBean() {
	}
  
  /**
   * �Ǽ��̻��豸��Ϣ
   * @param info �Ǽǵ��̻��豸��Ϣ
   * @param iCount Ϊʹ�õ��ò���
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int AppendInfo(ActDeviceInfo info, int iCount) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeInsertStm(sq.conn);
  			int flag = stm.executeUpdate(); 	 
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ��豸��Ϣʧ�ܣ�");
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
   * ɾ���̻��豸��Ϣ
   * @param info �Ǽǵ��̻��豸��Ϣ
   * @param strkey1 �̻���
   * @param strkey2 �̻��豸��
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int DeleteInfo(String strKey1, String strKey2) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		ActDeviceInfo info = new ActDeviceInfo();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey1, strKey2);
  			int flag = stm.executeUpdate(); 	   
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback(); 
  				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ���豸��Ϣʧ�ܣ�");
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
   * �޸��̻��豸��Ϣ
   * @param info �Ǽǵ��豸��Ϣ
   * @param strKey δʹ�õĲ���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public int UpdateInfo(ActDeviceInfo info, String strKey) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			sq.setAutoCommit(false);
  			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
  			int flag = stm.executeUpdate();
  			if (flag == 1) {
  				sq.commit();
  			} else {
  				sq.rollback();
  				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸��豸��Ϣʧ�ܣ�");
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
   * ��ѯ�̻��豸��Ϣ����
   * @param strKey �̻���
   * @param strType ��������,Ϊʹ�õĲ���
   * @param info �̻���Ϣ
   * @return v �̻��豸��Ϣ�б�,����̻�������,����null
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			if ( strKey != null && !strKey.trim().equals("")) {
  				sql = "SELECT * FROM act_info WHERE merchant_id='" + strKey + "'";
  			} else {
  				sql = "SELECT * FROM act_info";
  			}
  			ActDeviceInfo info = null;
  			Vector v = null;
  			ResultSet rst = sq.queryselect(sql);
  			v = new Vector();
  			while (rst != null && rst.next()) {
  				info = new ActDeviceInfo();
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
   * ��ѯ�̻��豸��Ϣ
   * @param strKey �̻��豸���
   * @param strType Ϊʹ�ò���
   * @return info �̻��豸��Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
  	public ActDeviceInfo QueryInfo(String strKey, String strType) throws MonitorException {
  		SqlAccess sq = SqlAccess.createConn();
  		try {
  			String sql;
  			sql = "SELECT * FROM act_info WHERE equip_id='" + strKey + "'";
  			ActDeviceInfo info = null;
  			ResultSet rst = sq.queryselect(sql);
  			if (rst != null && rst.next()) {
  				info = new ActDeviceInfo();
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
   * �̻��豸����,�������ú�ͣ�á�
   * @param strKey �̻��豸���  
   * @param strFlag ��־"1"-����"0"-ͣ��
   * @return affect �ɹ�����1
   * @throws MonitorException
   */
  	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
  		SqlAccess conn = new SqlAccess();
  		try {
  			if (strKey == null || strKey.trim().equals("")) {
  				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
  			}
  			String sqlStr = "UPDATE act_info SET use_flag = '"+strFlag+"' WHERE equip_id = '" + strKey + "'";
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