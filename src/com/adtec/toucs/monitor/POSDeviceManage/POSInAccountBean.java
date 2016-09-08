package com.adtec.toucs.monitor.POSDeviceManage;


import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSInAccountBean {

	public POSInAccountBean(){
		
	}

	  /**
	   * ������pos�ڲ��˻�
	   * @param info �ڲ��˻���Ϣ
	   * @throws UnsupportedEncodingException 
	   * @return ��ӳɹ�����1 
	   */
	public int AppendInfo(POSInAccountInfo info) throws MonitorException, UnsupportedEncodingException {
		SqlAccess sq = SqlAccess.createConn();
	    try {
	    	// �������ݿ������ύ��ʽΪ���Զ��ύ
	    	sq.setAutoCommit(false);
	    	PreparedStatement stm = info.makeInsertStm(sq.conn);
	    	int flag = stm.executeUpdate();
	    	if (flag == 1) {
	    		sq.commit();//�ύ
	    	}else {
	    		sq.rollback();//����ع�
	    		throw new MonitorException(ErrorDefine.REG_FAILED, "������pos�ڲ��˻���Ϣʧ�ܣ�");
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
	 * ��ѯ���pos�ڲ��˻���ϢLIST
	 * @param null
	 * @return �����ڲ��˻��ļ���
	 * @throws MonitorException
	 */
	public Vector QueryInfoByList() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="";
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_acct'";
			POSInAccountInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new POSInAccountInfo();
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
	 * ��ѯ���pos�ڲ��˻���Ϣ
	 * @param  null
	 * @return �ɹ������ڲ��˻���Ϣ
	 * @throws MonitorException
	 */	
	public POSInAccountInfo QueryInfo() throws MonitorException{
	    //ȡ���ݿ�����
	    SqlAccess sq = SqlAccess.createConn();
	    try {
	    	//��ѯ�ڲ��˻���Ϣ
	    	String sql;
	    	sql = "SELECT * FROM t_para_config WHERE para_name= 'p_lc_acct'";
	    	POSInAccountInfo info = null;
	    	ResultSet rst = sq.queryselect(sql);
	    	if (rst != null && rst.next()) {
	    		info = new POSInAccountInfo();
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
	 * �޸����pos�ڲ��˻���Ϣ
	 * @param info �ڲ��˻���Ϣ
	 * @return �޸ĳɹ�����1
	 * @throws MonitorException
	 */
	  public int UpdateInfo(POSInAccountInfo info) throws MonitorException {
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
		    	} else {
		    		sq.rollback();// ����ع�
		    		throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸����pos�ڲ��˻���Ϣʧ�ܣ�");
		    	}
		    	return flag;
		    } catch (SQLException e1) {
		    	throw new MonitorException(e1);
		    } finally {
		    	sq.close();
		    }
	  } 

		/**
		 * ɾ�����pos�ڲ��˻���Ϣ
		 * @param null
		 * @return �ɹ�����1
		 * @throws MonitorException
		 */
	  public int DeleteInfo() throws MonitorException {
		  SqlAccess sq = SqlAccess.createConn();  
		  try {
			  String sql = "DELETE FROM t_para_config WHERE para_name = 'p_lc_acct'";
			  int flag = sq.queryupdate(sql);
			  return flag;
		  } catch (SQLException e1) {
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }
}
