package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPoundageBean {

	/**
	 * ��ѯ���pos��������Ϣ
	 * @param null
	 * @return ������������Ϣ
	 * @throws MonitorException
	 */
	public POSPoundageInfo QueryInfo() throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��������Ϣ
			String sql;
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_fee'";
			POSPoundageInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPoundageInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}

  /**
   * ������pos��������Ϣ
   * @param info ��������Ϣ
   * @throws UnsupportedEncodingException 
   * @throws MonitorException
   */
	public int AppendInfo(POSPoundageInfo info) throws MonitorException, UnsupportedEncodingException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			}else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�����������Ϣʧ�ܣ�");
			}
			return flag;
		}catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
	
	/**
	 * ��ѯ���pos��������Ϣ
	 * @param null
	 * @throws MonitorException
	 * @return �ɹ�����Vector
	 */
	public Vector QueryInfoByList()throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="";
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_fee'";
			POSPoundageInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new POSPoundageInfo();
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
	 * ��ѯ���pos��������Ϣ
	 * @param null
	 * @throws MonitorException
	 * @return  �ɹ�������������Ϣ
	 */
	public POSPoundageInfo QueryInfos() throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��������Ϣ
			String sql;
			sql = "SELECT * FROM t_para_config WHERE para_name='p_lc_fee'";
			POSPoundageInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPoundageInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
		
	 /**
	   * �޸����pos��������Ϣ
	   * @param info ��������Ϣ
	   * @throws MonitorException	
	   * @return �ɹ�����1
	   */
	public int UpdateInfo(POSPoundageInfo info) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			// �����޸��̻����
			PreparedStatement stm = info.makeUpdateStm(sq.conn);
			// �޸��̻���Ϣ
			int flag = stm.executeUpdate();	
			if (flag == 1) {
				sq.commit();// �ύ
			}else {
				sq.rollback();// ����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸���������Ϣʧ�ܣ�");
			}
			return flag;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	} 

	  /**
	   * ɾ�����pos��������Ϣ
	   * @param null 
	   * @throws MonitorException	
	   * @return �ɹ�����1
	   */
	public int DeleteInfo() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();    
		try {
			String sql = "DELETE  FROM t_para_config WHERE para_name = 'p_lc_fee'";
			int flag = sq.queryupdate(sql);    	
			return flag;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
		  	  
}
