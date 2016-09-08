package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPublicKeyBean {
	
  /**
   * �޸�POS��Կ��Ϣ
   * @param info �ǼǵĹ�Կ��Ϣ
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int updateInfo(POSPublicKey info, String rid,String index,String memo1) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//��ӹ�Կ������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, rid,index,memo1);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸��˻���Ϣʧ�ܣ�");
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
   * ��ѯPOS��Կ����������Ϣ
   * @param rid ��Կ���
   * @param orgId ��ǰ�û���������
   * @return �̻���Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public POSPublicKey queryInfo(String rid,String index, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��Կ��Ϣ
			String sql;
			sql = "SELECT * FROM pos_public_key WHERE rid='" + rid + "'AND index='"+ index +"'";
			POSPublicKey info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPublicKey();
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
   * ��ѯ��Կ
   * @param rid ��Կ���
   * @param orgId ��ǰ�û���������
   * @return �̻���Ϣ������̻������ڣ�����null\
   * @throws MonitorException ���ϵͳ�쳣
   */
	public List VqueryInfo() throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			// ��ѯ��Կ��Ϣ
			String sql;
			sql = "SELECT * FROM pos_public_key ORDER BY rid,index";
			POSPublicKey Info = null;
			List list = new ArrayList();
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				Info = new POSPublicKey();
				Info.fetchData(rst);
				list.add(Info);
			}
			rst.close();	
			return list;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	  
/**
 * ��ӹ�Կ
 * ��ӵĹ�Կ��Ϣ
 * @return ��ӳɹ�����1
 * @throws MonitorException���ϵͳ�쳣
 */
	public int addInfo(POSPublicKey info, int nCount) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			String date=info.getCreate_date();
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit();
			} else {
				//������Ѳ�����еĳ�ʼ����Ϣ
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "��ӹ�Կ��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			throw new MonitorException(e1);
		} finally {
			if (sq != null) {
				if (sq.isConnectDB()) {
					sq.close();
				}
			}
		}
	}
  
  /**
   * ɾ��POS��Կ
   * @param info �ǼǵĹ�Կ��Ϣ
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int deleteInfo(String rid,String index) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		//pos�̻�ʵ����
		POSPublicKey dinfo = new POSPublicKey();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, rid,index);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ����Կʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

}

