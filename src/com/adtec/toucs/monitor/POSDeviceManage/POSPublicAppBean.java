package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPublicAppBean {

	/**
	 * �޸�EMV��������Ϣ
	 * @param info �Ǽǵ�EMV��������Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int updateInfo(POSPublicApp info, String aid, String memo1) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			// ���EMV��������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, aid, memo1);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸�EMV��������Ϣʧ�ܣ�");
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
	 * ��ѯEMV��������Ϣ
	 * @param aid EMV���������
	 * @param orgId ��ǰ�û���������
	 * @return �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public POSPublicApp queryInfo(String aid, String code_type) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			// ��ѯEMV��������Ϣ
			String sql;
			sql = "SELECT * FROM pos_public_app WHERE aid='" + aid + "'";
			POSPublicApp info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPublicApp();
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
	 * ��ѯEMV������ 
	 * @param aid EMV���������
	 * @param orgId  ��ǰ�û���������
	 * @return �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException  ���ϵͳ�쳣
	 */
	public List VqueryInfo() throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			// ��ѯEMV��������Ϣ
			String sql;
			sql = "SELECT * FROM pos_public_app ORDER BY aid";
			POSPublicApp Info = null;
			List list = new ArrayList();
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				Info = new POSPublicApp();
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
	 * ���EMV��������Ϣ
	 * @return ��ӳɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int addInfo(POSPublicApp info, int nCount) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			// ���EMV��������Ϣ
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit();
			}else {
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED,
						"���EMV��������Ϣʧ�ܣ�");
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
	 * ɾ��EMV������
	 * @param info �Ǽǵ�EMV��������Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int deleteInfo(String aid) throws MonitorException {
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		// pos�̻�ʵ����
		POSPublicApp dinfo = new POSPublicApp();
		try {
			// �������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, aid);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED,"ɾ��EMV������ʧ�ܣ�");
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
