package com.adtec.toucs.monitor.AgentManage;

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

public class ActMerchantBean {
	
	public ActMerchantBean() {
	}

	private String RetString = "";

  /**
   * �Ǽ��̻���Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param iCount δʹ�ò���
   * @return �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int AppendInfo(ActMerchantInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();			
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback(); 
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ��̻���Ϣʧ�ܣ�");
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
   * ɾ���̻���Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param strKey �̻���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int DeleteInfo(String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		ActMerchantInfo info = new ActMerchantInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();  
			}else {
				sq.rollback();  
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ���̻���Ϣʧ�ܣ�");
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
   * �޸��̻���Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param strKey �̻���
   * @return �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int UpdateInfo(ActMerchantInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸��̻���Ϣʧ�ܣ�");
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
   * ��ѯ�̻���Ϣ
   * @param strKey �̻���
   * @param strType �̻�����
   * @return v �̻���Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "";
			if ( strKey != null && !strKey.trim().equals("") && strType != null && !strType.trim().equals("")) {
				sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "' AND merchant_type='" + strType + "'";
			} else if(strKey != null && !strKey.trim().equals("")){
				sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "' ORDER BY merchant_id";
			}else if (strType != null && !strType.trim().equals("") ){
				sql = "SELECT * FROM act_merchant WHERE merchant_type = '"+ strType+"'";
			}
			ActMerchantInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new ActMerchantInfo();
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
   * ��ѯ�̻���Ϣ
   * @param strKey �̻���
   * @param code_type  Ϊʹ�ò���
   * @return info�̻���Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public ActMerchantInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "'";
			ActMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new ActMerchantInfo();
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
   * �̻������������ú�ͣ�á�
   * @param strKey �̻����  
   * @param strFlag ��־"1"-����"0"-ͣ��
   * @throws MonitorException
   */
	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
		SqlAccess conn = SqlAccess.createConn();
		try {
			if (strKey == null || strKey.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			String sqlStr = "UPDATE act_merchant SET merchant_stat = '"+strFlag+"' WHERE merchant_id = '" + strKey + "'";
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
	
	/**
	 * POS��Կ���س�ʽ����P�˷��ص�
	 * @param key �豸���
	 * @param use_flag ʹ�ñ�ʶ
	 * @return �����ֽ���3����Կ���ַ�������
	 * @throws MonitorException
	 */
	public String down_auth(String key, String use_flag) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.MASTER_KEYDOWN, key, use_flag);
			retStr = getRetMsg();
		}catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}
	/**
	 * ���״����ʽ������ͨѶ��ʽ��P��ͨ�ţ���Ϊ���ṩ���������������ף�����Ĭ��Ϊ���л�����
	 * @param TxCode ��P�˽ӿ��еĽ����룬��MG��ͷ������MG7830ΪPOSǩ����
	 * @param use_flag ʹ�ñ�ʶ
	 * @param DeviceCode POS�豸���
	 * @throws MonitorException
	 */
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, use_flag);	
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}	
		RetString = comm.getErrorDesc();
	}
	/**
	 * ȡ��ATM������Ϣ��retMsg��
	 * @return ATM������Ϣ
	 */
	public String getRetMsg() {
		return RetString;
	}

}