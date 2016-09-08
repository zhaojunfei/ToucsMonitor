package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;


import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: �������Ĵ��������</p>
 * <p>Description:��װ�������Ĵ��������ص�ҵ���߼��� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class BankCodeManageBean {

  /**
   * ���췽��
   */
	public BankCodeManageBean() {

	}

  /**
   * ��ѯ���д���
   * @param sq ���ݿ���ʶ���
   * @return v �����б�
   * @throws SQLException
   */
	public static Vector queryCodes(SqlAccess sq) throws SQLException{
		ResultSet rst=sq.queryselect("SELECT * FROM kernel_bank_code ORDER BY code");
		Vector v=new Vector();
		while(rst.next()){
			BankCodeBean bankCode=new BankCodeBean();
			bankCode.fetchData(rst);
			Debug.println(bankCode.toString());
			v.add(bankCode);
		}
		rst.close();
		return v;
	}

  /**
   * ��ѯ�������д���
   * @return v �����б�
   * @throws MonitorException
   */
	public static Vector queryCodes() throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq=SqlAccess.createConn();
		try{
			Vector v=queryCodes(sq);
			return v;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}


  /**
   * ��ѯָ��������ϸ��Ϣ
   * @param bankcode ��������
   * @return �����б�
   * @throws MonitorException
   */
	public static BankCodeBean queryCode(String bankCode) throws MonitorException{
		BankCodeBean code=null;
		SqlAccess sq = SqlAccess.createConn();
		String sql="SELECT * FROM kernel_bank_code WHERE code='"+bankCode+"'";
		try{
			ResultSet rst=sq.queryselect(sql);
			while( rst!=null && rst.next() ){
				code = new BankCodeBean();
				code.fetchData(rst);
			}
			rst.close();
			return code;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}

  /**
   * ��Ӵ���
   * @param bankCode ���д�����Ϣ
   * @return ��ӳɹ�����1
   * @throws MonitorException
   */
	public int addCode(BankCodeBean bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			int i=bankCode.insert(sq);
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

  /**
   * �޸Ĵ���
   * @param bankCode ������Ϣ
   * @return �޸ĳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int modifyCode(BankCodeBean bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			int i=bankCode.update(sq);
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

  /**
   * ɾ������
   * @param bankCode �������Ĵ���
   * @return ɾ���ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int deleteCode(String bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			sq.setAutoCommit(false);
			String sql="DELETE FROM kernel_bank_code WHERE code=?";
			PreparedStatement stm=sq.conn.prepareStatement(sql);
			stm.setString(1,bankCode);
			int i=stm.executeUpdate();
			sq.conn.commit();
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

	public static void main(String[] args) {

	}
}
