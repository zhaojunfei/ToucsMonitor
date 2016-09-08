package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title:</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:</p>
 * @author unascribed
 * @version 1.0
 */

public class POSFCardMerchantBean {

	public POSFCardMerchantBean() {
	}

	/**
	 * �Ǽ�POS���̻�
	 * @param info�Ǽǵ��豸��Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
	 */
	public int addPosMerchant(POSFCardMerchantInfo info) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// ����豸������Ϣ
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);			
			if (flag == 1) {
				sq.commit();// �ύ
			} else {
				sq.rollback();// ����ع�
				throw new MonitorException(ErrorDefine.ADDMCT_FAILED,"���POS�̻���Ϣʧ�ܣ�");
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
	 * �޸�POS�̻�
	 * @param info�Ǽǵ��豸��Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
	 */
	public int updatePosMerchant(POSFCardMerchantInfo info, String key) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			// �˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// ����豸������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("[????]" + stm.toString() + "FLAG:" + flag);		
			if (flag == 1) {
				sq.commit();// �ύ
			} else {
				sq.rollback();// ����ع�
				throw new MonitorException(ErrorDefine.UPDATEMCT_FAILED,"�޸�POS�̻���Ϣʧ�ܣ�");
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
	 * ɾ��POS���̻�
	 * @param info�Ǽǵ��豸��Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException���ϵͳ�쳣
	 */
	public int deletePosMerchant(String merchantID) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		// pos�̻�ʵ����
		POSFCardMerchantInfo mctInfo = new POSFCardMerchantInfo();
		// posʵ����
		POSFCardInfo fposInfo = new POSFCardInfo();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			// �˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			// ��ѯ���̻���Pos�豸��Ϣ�������pos�豸������ɾ��
			PreparedStatement stm = fposInfo.makeQueryByMctStm(sq.conn,merchantID);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				throw new MonitorException(ErrorDefine.DELMCT_FAILED,"�̻��¶������ն���Ϣ������ɾ���ն���Ϣ��");
			}
			// ɾ���̻���Ϣ������Ϣ
			stm = mctInfo.makeDeleteStm(sq.conn, merchantID);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);			
			if (flag == 1) {
				sq.commit();// �ύ
			} else {
				sq.rollback();// ����ع�
				throw new MonitorException(ErrorDefine.DELMCT_FAILED1,"�̻��������־Ϊ���������������̻���");
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
	 * ��ѯ�̻�����������Ϣ 
	 * @param atmCode�豸���
	 * @param orgId��ǰ�û���������
	 * @return �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
	 */
	public POSFCardMerchantInfo qryPosMerchant(String merchantID) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// ��ѯ�豸��Ϣ
			String sql = "SELECT * FROM merchant_list WHERE mer_id='" + merchantID + "'";
			Debug.println("***********SQL:" + sql + "*********");
			POSFCardMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst.next()) {
				info = new POSFCardMerchantInfo();
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
	 * ��ѯ�����̻���ź��̻�������Ϣ
	 * @param atmCode�豸���
	 * @param orgId��ǰ�û���������
	 * @return �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException���ϵͳ�쳣
	 */
	public Vector qryMerchantVector() throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			// ��ѯ�豸��Ϣ
			String sql = "SELECT * FROM merchant_list;";
			POSFCardMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			Vector vReturn = new Vector();
			while (rst.next()) {
				info = new POSFCardMerchantInfo();
				info.fetchData(rst);
				vReturn.add(info.getMer_id());
				vReturn.add(info.getMer_name());
			}
			rst.close();
			Debug.println("SIZE:" + vReturn.size());
			return vReturn;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	/**
	 * ���ݻ��������ѯ�û����Լ��¼�������ӵ�е��̻���Ϣ��
	 * @param orgId������
	 * @return LIST�б�ÿһ��Ԫ�ض���һ��POSFCardMerchantInfo����
	 * @throws MonitorException
	 */
	public List getMerchantList() throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess("FCardPosPool");
		String sqlStr = "";
		try {
			sqlStr = "SELECT * FROM merchant_list ORDER BY mer_id";
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSFCardMerchantInfo pmi = new POSFCardMerchantInfo();
				pmi.fetchData(rs);
				list.add(pmi);
			}
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}
}
