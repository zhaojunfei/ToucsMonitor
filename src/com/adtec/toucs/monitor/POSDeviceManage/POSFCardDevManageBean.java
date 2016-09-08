package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: POS�豸������</p>
 * <p>Description:POS��װ�豸������ص�ҵ���߼�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSFCardDevManageBean {
	public POSFCardDevManageBean() {
	}
	
	  /**
	   * �޸�POS�豸��Ϣ
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int updatePosInfo(POSFCardInfo info, String mer_id, String pos_no) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//����豸������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, mer_id, pos_no);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag); 	 
			if (flag == 1) {
				sq.commit();//�ύ 	
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸��⿨POSʧ�ܣ�");
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
	public POSFCardInfo qryPosInfo(String merId, String posCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//��ѯ�豸��Ϣ
			String sql;
			sql = "SELECT * FROM pos_list WHERE mer_id = '" + merId + "' AND pos_no='" + posCode + "'";
			POSFCardInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSFCardInfo();
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
	public int regPosDevice(POSFCardInfo info) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate(); 	     
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				Debug.println("in regpos*****wuye");
				sq.rollback();//����ع�
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ�POS�豸��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			Debug.println("\n****wuye debug;���벻�ɹ���delete" + info.getPos_no() + " OK !*****");
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
	   * ɾ��POS���̻�
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int deletePosInfo(String merId, String posCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		POSFCardInfo posInfo = new POSFCardInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			//�˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ
			PreparedStatement stm = posInfo.makeDeleteStm(sq.conn, merId, posCode);
			int flag = stm.executeUpdate(); 	  
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ��POS�̻���Ϣʧ�ܣ�");
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
	   *  �����豸�����ѯPOS��Ϣ������LIST����Ϊnull����objID����000000�����ѯ���е�POS��Ϣ��
	   * @param objId �豸����
	   * @return ����LIST��
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public List getPOSInfoByObjId(String merId, String posNo) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess("FCardPosPool");
		String sqlStr = "";

		if ( (merId == null || merId.trim().equals("")) &&
				! (posNo == null || posNo.trim().equals(""))) {
			MonitorException ex = new MonitorException("����ָ���̻���", 0, "");
			throw ex;
		}
		try {
			if ( (merId == null || merId.trim().equals("")) &&
					(posNo == null || posNo.trim().equals(""))) {
				sqlStr = "SELECT * FROM pos_list ORDER BY mer_id, pos_no";
			} else if (posNo == null || posNo.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_list  WHERE mer_id = '" + merId + "' ORDER BY pos_no";
			} else {
				sqlStr = "SELECT * FROM pos_list  WHERE mer_id = '" + merId + "' AND pos_no = '" + posNo + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSFCardInfo pi = new POSFCardInfo();
				pi.fetchData(rs);
				list.add(pi);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}
}
