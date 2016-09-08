package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

	/**
	 * <p>Title: ƽ̨���״��������</p>
	 * <p>Description:��װƽ̨�����������ص�ҵ���߼��� </p>
	 * <p>Copyright: Copyright (c) 2002</p>
	 * <p>Company: ADTec </p>
	 * @author liyp
	 * @version 1.0
	 */
public class TxnCodeManageBean {
	//�豸�����б�
	public static String typeList[] = {
      "atm", "cdm", "pos", "mit", "pem"};

	//POS����Ȩ������󳤶�
	private static int POS_MASK_SIZE = 100;

	  /**
	   * ���췽��
	   */
	public TxnCodeManageBean() {
	}

	  /**
	   * ��ѯָ���������
	   * @param sysId ��������
	   * @param sq ���ݿ���ʶ���
	   * @return �����б�
	   * @throws SQLException
	   */
	public static Vector queryCodes(String sysId, SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_code WHERE sys_id='" + sysId + "' ORDER BY txn_code");
		Vector v = new Vector();
		while (rst != null && rst.next()) {
			TxnCodeBean txnCode = new TxnCodeBean();
			txnCode.fetchData(rst);
			Debug.println(txnCode.toString());
			v.add(txnCode);
		}
		rst.close();
		return v;
	}

	  /**
	   * ��ѯָ������Ĵ���
	   * @param type ��������
	   * @return �����б�
	   */
	public static Vector queryCodes(String sysId) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			Vector v = queryCodes(sysId, sq);
			return v;
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

	  /**
	   * ��ѯָ��������ϸ��Ϣ
	   * @param type ��������
	   * @return �����б�
	   */
	public static TxnCodeBean queryCode(String deviceType, String txnCode) throws MonitorException {
		TxnCodeBean code = null;
		SqlAccess sq = SqlAccess.createConn();
		String sql = "SELECT * FROM kernel_txn_code WHERE sys_id='" + deviceType + "' AND txn_code='" + txnCode + "'";
		try {
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				code = new TxnCodeBean();
				code.fetchData(rst);
			}
			rst.close();
			return code;
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

	  /**
	   * ��Ӵ���
	   * @param txnCode ���״�����Ϣ
	   * @return ��ӳɹ�����1
	   * @throws MonitorException
	   */
	public int addCode(TxnCodeBean txnCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = txnCode.insert(sq);
			sq.commit();
			//���´����
			CodeManageBean.codeTableAdd(txnCode.getDeviceType(), txnCode.getTxnCode(),txnCode.getTxnName());
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * �޸Ĵ���
	   * @param code ������Ϣ
	   * @param id �����ţ�key��
	   * @return �޸ĳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int modifyCode(TxnCodeBean txnCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = txnCode.update(sq);
			sq.commit();
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * ɾ������
	   * @param type ��������
	   * @param id ������
	   * @return ɾ���ɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int deleteCode(String sysId, String txnCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "DELETE FROM kernel_txn_code WHERE sys_id=? AND txn_code=?";
			PreparedStatement stm = sq.conn.prepareStatement(sql);
			stm.setString(1, sysId);
			stm.setString(2, txnCode);
			int i = stm.executeUpdate();
			//���´����
			if (i > 0) {
				CodeManageBean.codeTableDel(sysId, txnCode);
			}
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * ��ѯPOS��Ĭ��Ȩ�������ã�ֻ��POS��Ч����Ĭ������Ȩ����
	   * @param isPreAuthorized 0:��Ԥ��Ȩ 1:��Ԥ��Ȩ
	   * @param perm  0�������� 1������������ҽ��� 2����������ҽ���
	   *              3�������������Ҳ������ҽ��� 4-����pos
	   * @return ��Vector ���� 100λ�Ľ���Ȩ�����100λ������Ȩ����
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public static Vector queryPosTxnMask(String isPreAuthorized, char perm, String pos_type) throws SQLException, MonitorException {
		char[] code = new char[POS_MASK_SIZE]; //����Ȩ����
		char[] handMask = new char[POS_MASK_SIZE]; //���俨��Ȩ����
		Vector v = new Vector();
		//׼��100��Ϊ0��Ȩ����
		Debug.println(String.valueOf(code) + "[" + isPreAuthorized + "]["+perm+"]["+pos_type+"]");
		for (int i = 0; i < POS_MASK_SIZE; i++) {
			code[i] = '0';
			handMask[i] = '0';
		}
		Debug.println(String.valueOf(code) + "[" + isPreAuthorized + "]");
		Debug.println(String.valueOf(handMask) + "[" + perm + "]");
		//û��Ȩ�޷���
		if (perm == '0') {
			v.add(String.valueOf(code));
			v.add(String.valueOf(handMask));
			return v;
		}
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="SELECT txn_code, location, hand_flag FROM kernel_txn_code WHERE sys_id='pos' ";
			//����Ȩ�ཻ��ʱ������Ԥ��Ȩ�ཻ��
			if (isPreAuthorized.equals("0")) {
				sql += " AND app_flag2 = '0 ' ";
			}
			ResultSet rs = sq.queryselect(sql);
			String txnCode = "";
			String handFlag = "";
			while (rs != null && rs.next()) {
				int location = rs.getInt("location");
				txnCode = rs.getString("txn_code").trim();
				handFlag = rs.getString("hand_flag").trim();  
				if(pos_type.equals("7")){ 
					/* ֻ�����POS��������ƽ��� */
					if ( txnCode.substring(0,2).equals("LC")||txnCode.equals("ST6330")||txnCode.equals("ST7410")||txnCode.equals("ST7820") ) {
						code[location - 1] = '1';
					}
				}else if(pos_type.equals("8")){
					/* ֻ��Ԥ����POS������Ԥ�������� */
					if ( txnCode.substring(0,2).equals("BC")||txnCode.equals("ST6330")||txnCode.equals("ST7410")||txnCode.equals("ST7820") ) {
						code[location - 1] = '1';
					}
				}else{
					//Ĭ�ϲ�����С�ѵ�������
					if (!txnCode.equals("ST7830")) {
						code[location - 1] = perm;
					}		
					//��ҿ�����������׷��Ԥ��Ȩ������׷��Ԥ��Ȩ����������Ԥ��Ȩ������
					if (txnCode.equals("MC0611") || txnCode.equals("MC0621") ||
							txnCode.equals("MC0510")) {
						if (perm == '2') {
							code[location - 1] = '0';
						} else if (perm == '3') {
							code[location - 1] = '1';
						}
					}
					//��Ԥ��Ȩ�����Ĭ������MC1010��Ȩ���ѣ��������ѣ���MC1020 ��Ȩ���ѳ���
					if (isPreAuthorized.equals("0") && (txnCode.equals("MC1010") || txnCode.equals("MC1020"))) {
						if (perm == '2') {
							code[location - 1] = '0'; //���POSû��Ȩ��
						}
						if (perm == '3') {
							code[location - 1] = '1'; //�����POS���������
						}
					}
					//���Ĭ�ϲ���������������MC3311
					if (txnCode.equals("MC3311")) {
						if (perm == '2') {
							code[location - 1] = '0'; //���POSû��Ȩ��
						}
						if (perm == '3') {
							code[location - 1] = '1'; //�����POS���������
						}
						/* ת��POS Ĭ�Ϻ�������ѯ�������� */
						if (pos_type.equals("1")) {
							handMask[location - 1] = '1';
						}
					}
					//ֻ������Ҹ��ݽ���Ȩ���������俨Ȩ��
					if (handFlag.equals("1") && (perm == '1' || perm == '3')) {
						handMask[location - 1] = '1';
					}
					//���POS����������ѯ����
					if (txnCode.equals("MC3310") && (perm == '2' || perm == '3')) {
						if (perm == '2') {
							code[location - 1] = '0';
						}
						if (perm == '3') {
							code[location - 1] = '1';
						}
					}		
					/* ֻ������POS���������ѽ��� */
					if ( txnCode.equals("MC0010") ||
							txnCode.equals("MC0020") ||
							txnCode.equals("MC2010") ||
							txnCode.equals("MC2020") ||
							txnCode.equals("MC0610") ||
							txnCode.equals("MC0620") ||
							txnCode.equals("MC0611") ||
							txnCode.equals("MC0621") ||
							txnCode.equals("MC0510") ||
							txnCode.equals("MC1010") ||
							txnCode.equals("MC1020") ||
							txnCode.equals("ST7830") ||
							txnCode.equals("MC5010") ||
							txnCode.equals("MC5020") ||
							txnCode.equals("BC0010") ||
							txnCode.equals("BC0020") ||
							txnCode.equals("BC2010") ||
							txnCode.equals("BC2020") ||
							txnCode.equals("BC2110") ||
							txnCode.equals("BC2120") ||
							txnCode.equals("BC3310") ||
							txnCode.equals("MC5310") ) {
						if (pos_type.equals("1")||pos_type.equals("2")) {
							code[location - 1] = '0';
						}
					}		
					/* ֻ��ת��POS������ת�ʽ��� */
					if ( txnCode.equals("MC0040") || txnCode.equals("MC0041") || txnCode.equals("MC0042") )  {
						if (pos_type.equals("1")) {
							code[location - 1] = '1';
						}else {
							code[location - 1] = '0';
						}
					}	
					/* ֻ��ǩԼPOS������ǩԼ���� */
					if ( txnCode.substring(0,2).equals("EN") ) {
						if (pos_type.equals("2")) {
							code[location - 1] = '1';
						}else {
							code[location - 1] = '0';
						}
					}   
					/* ֻ�����POS��������ƽ��� */
					if ( txnCode.substring(0,2).equals("LC") ) {
						code[location - 1] = '0';
					}else if(txnCode.substring(0,2).equals("BC")){
						/* ֻ��Ԥ����POS������Ԥ�������� */
						code[location - 1] = '0';
					}
				}
			}
			rs.close();
			v.add(String.valueOf(code));
			v.add(String.valueOf(handMask));
			return v;
		} catch (SQLException e1) {
			throw e1;
		} finally {
			sq.close();
		}
	}

	  /**
	   * ��ѯ���������б�ֻ��POS��Ч��
	   * @return ���ؽ�����������
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public static Vector queryPosTxnCodes() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		Vector v = new Vector();
		try {
			ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_code WHERE sys_id='pos' ORDER BY location");
			while (rst != null && rst.next()) {
				TxnCodeBean txnCode = new TxnCodeBean();
				txnCode.fetchData(rst);
				v.add(txnCode);
			}
			rst.close();
			return v;
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
	}


	
	public static void main(String[] args) {
	}
}
