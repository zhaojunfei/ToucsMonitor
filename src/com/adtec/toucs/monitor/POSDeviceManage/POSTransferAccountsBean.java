package com.adtec.toucs.monitor.POSDeviceManage;


import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSTransferAccountsBean {

	/**
	 * ����µ����posת�˽��ǰ��Ψһ�Բ�ѯ(ת�˽��ֻ����һ��)
	 * @param null
	 * @return ����ת�˽����Ϣ
	 * @throws MonitorException
	 */	
	public POSTransferAccountsInfo QueryInfo() throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��Ϣ
			String sql;
			sql = "SELECT * FROM t_para_config WHERE para_name='p_lc_amt'";
			POSTransferAccountsInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
			info = new POSTransferAccountsInfo();
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
	 * ������posת�˽����Ϣ
	 * @param info ת�˽����Ϣ
	 * @return �ɹ�����1
	 * @throws MonitorException
	 */	
	 public int AppendInfo(POSTransferAccountsInfo info) throws MonitorException, UnsupportedEncodingException {
		 SqlAccess sq = SqlAccess.createConn();
		 try {
			 	// �������ݿ������ύ��ʽΪ���Զ��ύ
			 	sq.setAutoCommit(false);
			 	PreparedStatement stm = info.makeInsertStm(sq.conn);
			 	int flag = stm.executeUpdate();
			 	if(flag == 1) {
			 		sq.commit();
			 	}else {
			 		sq.rollback();
			 		throw new MonitorException(ErrorDefine.REG_FAILED, "���ת�˽����Ϣʧ�ܣ�");
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
	 * ��ѯ���posת�˽����Ϣ
	 * @param null
	 * @return ����ת�˽����ϢVector
	 * @throws MonitorException
	 */	
	public Vector QueryInfoByList() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="";
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_amt'";
			POSTransferAccountsInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new POSTransferAccountsInfo();
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
	 * ɾ�����posת�˽����Ϣ
	 * @param null
	 * @return �ɹ�����1
	 * @throws MonitorException
	 */	
	 public int DeleteInfo() throws MonitorException {
		 SqlAccess sq = SqlAccess.createConn();	    
		 try {	    	
			 String sql = "DELETE FROM t_para_config WHERE para_name = 'p_lc_amt'";
			 int flag = sq.queryupdate(sql);  	
			 return flag;
		 }catch (SQLException e1) {
			 throw new MonitorException(e1);
		 }finally {
			 sq.close();
		 }
     }	
		  
		 	  
	/**
	 * �޸����posת�˽����Ϣ
	 * @param info ת�˽����Ϣ
	 * @return �ɹ�����1
	 * @throws MonitorException
	 */	
	public int UpdateInfo(POSTransferAccountsInfo info) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//�����޸��̻����
			PreparedStatement stm = info.makeUpdateStm(sq.conn);
			// �޸��̻���Ϣ
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			}else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�ת�˽����Ϣʧ�ܣ�");
			}
			return flag;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	} 
}
