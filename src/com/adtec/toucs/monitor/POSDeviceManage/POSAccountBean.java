package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class POSAccountBean {
	
	public POSAccountBean() {
	}

	private String RetString = "";

	  /**
	   * �޸�POS�豸��Ϣ
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int updateInfo(POSAccount info, String key) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//����豸������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);		
			if (flag == 1) {
				sq.commit();//�ύ 	
			} else {
				sq.rollback();//����ع�
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
	   * ��ѯPOS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param orgId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public POSAccount queryInfo(String key, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			sql = "SELECT * FROM kernel_acct_auth WHERE account='" + key + "'";
			POSAccount info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSAccount();
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
	   * �Ǽ�POS���豸
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int addInfo(POSAccount info, int nCount) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit(); //�ύ
			} else {
				sq.rollback();//����ع�
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ��˻���Ϣʧ�ܣ�");
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
	   * ��ѯPOS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param orgId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public Vector VqueryInfo(String key, String type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			sql = "SELECT * FROM kernel_acct_auth";
			
			POSAccount Info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				Info = new POSAccount();
				Info.fetchData(rst);
				v.add(Info);
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
	   * ɾ��POS���̻�
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int deleteInfo(String key) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		//pos�̻�ʵ����
		POSAccount dinfo = new POSAccount();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, key);
			int flag = stm.executeUpdate(); 	  
			if (flag == 1) {
				sq.commit();//�ύ
			}  else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ���Թ��ʻ�ʧ�ܣ�");
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
	   * POS��Կ���س�ʽ����P�˷��ص�
	   * @param posID �豸���
	   * @return �����ֽ���3����Կ���ַ�������
	   * @throws MonitorException
	   **/
	public String down_auth(String key) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.POS_AUTHDOWN, key);
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}
	/**
	 * ���״����ʽ������ͨѶ��ʽ��P��ͨ�ţ���Ϊ���ṩ���������������ף�����Ĭ��Ϊ���л�����
	 * @param TxCode ��P�˽ӿ��еĽ����룬��MG��ͷ������MG7830ΪPOSǩ����
	 * @param DeviceCode POS�豸���
	 * @throws MonitorException
	 */
	private void TranProc(String TxCode, String DeviceCode) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, "000000000");
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
	}
	/**
	 * ȡ��ATM������Ϣ��retMsg��
	 * @return ATM������Ϣ
	 **/
	public String getRetMsg() {
		return RetString;
	}
}