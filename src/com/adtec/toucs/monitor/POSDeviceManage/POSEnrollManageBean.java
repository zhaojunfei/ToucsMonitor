package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;
import com.adtec.toucs.monitor.devicemanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class POSEnrollManageBean {
	
	public POSEnrollManageBean() {
	}
	
	private String RetString = "";
	
	//����������
	private static final String POSPTYPE = "1";
	private static final String POSCTYPE = "2";

	  /**
	   * �޸�POS�豸��Ϣ
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int updatePosInfo(POSEnrollInfo info, String key) throws MonitorException {
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
				sq.commit(); //�ύ
			} else {
				sq.rollback(); //����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�ǩԼPOS��Ϣʧ�ܣ�");
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
	public POSEnrollInfo qryPosInfo(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp���
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC���
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC���
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}

			Debug.println("*****wuye debug; pos SQL = :\n" + sql);
			
			POSEnrollInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSEnrollInfo();
				info.setPoscode(toucsString.unConvert(rst.getString("pos_code")));
				info.setPosCCode(toucsString.unConvert(rst.getString("pos_c_code")));
				info.setPosDccCode(toucsString.unConvert(rst.getString("pos_dcc_code")));
				info.setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
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
	   * ��ѯPOS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param orgId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public POSEnrollInfo qryPosEnroll(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp���
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC���
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC���
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			POSEnrollInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSEnrollInfo();
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
	   * ��ѯPOS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param orgId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public Vector qryPosInfoByPoscode(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp���
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC���
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC���
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,
                                   ErrorDefine.NOTHISDEVICEDESC);
			}
			POSEnrollInfo posenroll = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posenroll = new POSEnrollInfo();
				posenroll.fetchData(rst);
				v.add(posenroll);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * ��ѯ�̻�����������Ϣ
	   * @param posCode �豸���
	   * @param orgId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public Vector qryMctPosInfo(String merchantNo) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql = "SELECT * FROM pos_enrol" + " WHERE merchant_id='" + merchantNo + "' ORDER BY pos_dcc_code ";
			POSEnrollInfo posenroll = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posenroll = new POSEnrollInfo();
				posenroll.fetchData(rst);
				v.add(posenroll);
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
	   * �Ǽ�POS���豸
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int regPosDevice(POSEnrollInfo info, int nPoscount) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			int flag = info.insert(sq);
			
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ�POS�豸��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			Debug.println("\n****wuye debug;���벻�ɹ���delete" + info.getPoscode() + " OK !*****");
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
	public int deletePosInfo(String posCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		//pos�̻�ʵ����
		POSEnrollInfo dinfo = new POSEnrollInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, posCode);
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
	   * ����POS���á�ͣ�ñ�־
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int setStatPos(String pos_code, boolean bStat) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		//pos�̻�ʵ����
		POSEnrollInfo dinfo = new POSEnrollInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//�����̻������־������Ϣ
			PreparedStatement stm = dinfo.makeStatStm(sq.conn, pos_code, bStat);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//�ύ 	
			} else {
				sq.rollback(); //����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�POS�̻���Ϣʧ�ܣ�");
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
	   * ��ѯ��Կ������Ϣ
	   * @param posCode �豸���
	   * @param orgId �������
	   * @return ��Կ������Ϣ
	   * @throws MonitorException
	   */
	public AtmKeyInfo qryKeyInfo(String posCode, String orgId) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//У���豸�Ƿ���ڲ����ڲ���Ա����������Χ��
			if (orgId != null && orgId.trim().length() > 0) {
				if (!deviceInOrg(posCode, orgId, sq)) {
					return null;
				}
			}
			String sql = "SELECT * FROM kernel_key_info WHERE sender_agency='" +
			posCode + "'";
			ResultSet rst = sq.queryselect(sql);
			AtmKeyInfo keyInfo = null;
			if (rst != null && rst.next()) {
				keyInfo = new AtmKeyInfo();
				keyInfo.fetchData(rst);
			}
			rst.close();
			return keyInfo;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * �жϸ������豸���Ƿ��Ǹ����Ļ��������������б����������Ļ��������������������׳��쳣��
	   * @param posCode �豸��
	   * @param orgId  ������
	   * @param sq   ���ݿ��ѯʵ��
	   * @throws MonitorException
	   */
	public boolean deviceInOrg(String posCode, String orgId, SqlAccess sq) throws MonitorException {
		Debug.println("1:" + posCode);
		if (posCode == null || posCode.trim().length() == 0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		Debug.println("2:" + orgId);
		if (orgId == null || orgId.trim().length() == 0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		posCode = posCode.trim();
		
		boolean exist = false;
		String tmpOrgId = "";
		//ȡ���豸��������
		String sql = "SELECT a.org_id FROM pos_merchant a, pos_info b "
				   + "WHERE a.merchant_id =b.merchant_id AND b.pos_code='"
				   + posCode + "'";
		Debug.println("3:" + sql);
		try {
			ResultSet rst = sq.queryselect(sql);
			if (rst.next()) { //����ܲ�ѯATM�豸��Ӧ����֯����ID
				tmpOrgId = rst.getString("org_id").trim();
				rst.close();
				Debug.println("�豸[" + posCode + "]��������:" + tmpOrgId);
				if (orgId.equals(tmpOrgId)) { //�����ѯ������֯����������Ļ�����ȣ����ʾ����
					exist = true;
				} else { //�����ѯ������֯����������Ļ�������ȣ���ѯ�û����Ƿ��Ǹ�����������������
					sql = "SELECT org_id FROM monit_orgref WHERE org_id='" + tmpOrgId +
						  "' AND p_org_id='" + orgId + "'";
					rst = sq.queryselect(sql);
					//�豸���������Ǹ�����������������
					if (rst.next()) {
						exist = true;
					}
					rst.close();
				}
			}
			if (!exist) {
				Debug.println("�豸[" + posCode + "]�����ڻ���[" + orgId + "]��Ͻ��Χ");
			}
			if (!exist) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}	
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		}
		return true;
	}

	  /**
	   * �޸�ATM��Կ������Ϣ
	   * @param keyInfo ��Կ������Ϣ
	   * @return �޸ĳɹ�����1
	   * @throws MonitorException
	   */
	public int updateKeyInfo(AtmKeyInfo keyInfo) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//�޸���Կ������Ϣ
			int ret = keyInfo.update(sq);
			//���ݵ�ǰ�豸�ļ���״̬�޸ļ��ر�־
			if (ret == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸���Կ������Ϣʧ�ܣ�");
			}
			return 1;
		} catch (SQLException exp) {
			sq.rollback();
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
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
	   * ǩԼPOS�豸�����������ú�ͣ�á�
	   * @param PosID POS�豸���  UseFalg ��־"1"-����"0"-ͣ��
	   * @throws MonitorException
	   */
	public int managePosInfo(String PosID, String UseFlag ) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (PosID == null || PosID.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			//UseFlag "1"-start "0"-stop
			String sqlStr = "UPDATE pos_enrol SET use_flag = '"+UseFlag+"' WHERE pos_code = '" + PosID + "'";
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
	   * �豸����ǩ������ȫ��ı�Ŵ��������豸��
	   * @throws MonitorException
	   */
	public void registPOS() throws MonitorException {
		try {
			TranProc(CodeDefine.POS_REGIST, "00000000");
		} catch (MonitorException ex) {
			throw ex;
		}
	}

	  /**
	   * POS��Կ���س�ʽ����P�˷��ص�
	   * @param posID �豸���
	   * @return �����ֽ���3����Կ���ַ�������
	   * @throws MonitorException
	   */
	public String pos_key_down(String posID) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.POS_KEYDOWN, posID);
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}

	  /**
	   * ȡ��ATM������Ϣ��retMsg��
	   * @return ATM������Ϣ
	   */
	public String getRetMsg() {
		return RetString;
	}

	  /**
	   *  ���ݻ��������ѯPOS��Ϣ������LIST����orgidΪnull����org���ڿմ������ѯ���е�POS��Ϣ��
	   * @param orgId ��������
	   * @return ����LIST��
	   * @throws MonitorException
	   */
	public List getPOSInfoByOrg(String orgId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess();
		String sqlStr = "";
		try {
			if (orgId == null || orgId.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_info";
			} else {
				sqlStr = "SELECT a.pos_code,a.merchant_id,a.pos_stat,a.bill_no,a.pos_batch,a.con_num,a.con_amt,a.ref_num,a.ref_amt,a.rebate,a.open_bankno,a.exg_no,a.acct_no,a.vacct_no FROM pos_info a,pos_merchant b, monit_orgref c WHERE b.org_id = c.org_id AND a.Merchant_id = b.Merchant_id AND c.p_org_id = '" + orgId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSEnrollInfo pi = new POSEnrollInfo();
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

	  /**
	   *  �����豸�����ѯPOS��Ϣ������LIST����objIdΪnull����objID����000000�����ѯ���е�POS��Ϣ��
	   * @param objId �豸����
	   * @return ����LIST��
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public List getPOSInfoByObjId(String objId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess();
		String sqlStr = "";
		try {
			if (objId == null || objId.trim().equals("") || objId.trim().equals("000000")) {
				sqlStr = "SELECT * FROM pos_info";
			} else {
				sqlStr = "SELECT * FROM pos_info  WHERE pos_code = '" + objId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSEnrollInfo pi = new POSEnrollInfo();
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

	  /**
	   *  �����豸�����ѯPOS�������ü����俨������Ϣ
	   * @param objId �豸����
	   * @return ����POSTxnInfo��
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public POSEnrollInfo qryPosTxnInfo(String objId, String code_type) throws MonitorException {
		if (code_type == null) {
			code_type = "";
		}
		code_type = code_type.trim();
		SqlAccess conn = new SqlAccess();
		POSEnrollInfo pi = null;
		String sqlStr = "";
		try {
			sqlStr = "SELECT pos_code,pos_dcc_code,pos_c_code,pre_authorized,equip_type,tran_right,hand_flag,memo1 " + "FROM pos_info WHERE ";
			if (code_type.equalsIgnoreCase(POSPTYPE)) {
				sqlStr += " pos_code = '";
			}
			else if (code_type.equalsIgnoreCase(POSCTYPE)) {
				sqlStr += " pos_c_code = '";
			} else {
				sqlStr += " pos_dcc_code = '";
			}
			sqlStr += objId;
			sqlStr += "'";
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				pi = new POSEnrollInfo();
			}
			rs.close();
			return pi;
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
	}

	  /**
	   *  �����豸�����ѯPOS�������ü����俨������Ϣ
	   * @param objId �豸����
	   * @return ����POSTxnInfo���б�
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public Vector qryPosTxnInfoSet(POSEnrollInfo pi) throws MonitorException {
		Vector vReturn = new Vector();
		Vector vTxn = TxnCodeManageBean.queryPosTxnCodes();
		if (vTxn != null) {
			for (int i = 0; i < vTxn.size(); i++) {
				POSTxnInfo pti = new POSTxnInfo();
				TxnCodeBean code = (TxnCodeBean) vTxn.get(i);
				int sn = code.getMaskSerial();
				pti.setLocation(sn - 1);
				pti.setTxnName(code.getTxnName());
				vReturn.add(pti);
			}
		}
		return vReturn;
	}

	  /**
	   * ��POS-DCC���ת��ΪPOS-P���
	   * @param pos_dcc_code DCC���
	   * @return pos_code P�˱��
	   */
	public static String transDCCCodeToPCode(String pos_dcc_code) throws MonitorException {
		String pos_p_code = "";
		SqlAccess sq = new SqlAccess();
		try {
			Statement stm = sq.conn.createStatement();
			String sql;
			sql = "SELECT pos_code FROM pos_info WHERE pos_dcc_code = '" + pos_dcc_code + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				pos_p_code = rs.getString("pos_code");
			}
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return pos_p_code;
	}

	  /**
	   * ��POS-P���ת��ΪPOS-DCC���
	   * @param pos_code P�˱��
	   * @return  pos_dcc_code DCC���
	   */
	public static String transPCodeToDCCCode(String pos_code) throws MonitorException {
		String pos_dcc_code = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql;
			sql = "SELECT pos_dcc_code FROM pos_info WHERE pos_code = '" + pos_code + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				pos_dcc_code = rs.getString("pos_dcc_code");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return pos_dcc_code;
	}

	  /**
	   * ����ֻɾ��pos_info�е���Ϣ
	   * @param pos_code P�˱��
	   * @return  pos_dcc_code DCC���
	   */
	public int delOnlyPos(String posCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		int flag;
		try {
			//ɾ��POS�豸������Ϣ
			sq.setAutoCommit(true);
			PreparedStatement stm = (new POSEnrollInfo()).makeDeleteStm(sq.conn, posCode);
			flag = stm.executeUpdate();
		} catch (SQLException sqe) {
			throw new MonitorException(sqe);
		} finally {
			sq.close();
		}
		return flag;
	}
	
	  /**
	   * ͨ��POS-DCC��Ų����̻����
	   * @param PosDccCode Dcc���
	   * @return  �̻�����
	   *
	   */
	public String getPOSMerchantName(String PosDccCode) throws MonitorException {
		String merchantId = PosDccCode.substring(0, 15);
		String merchantName = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				merchantName = rs.getString("mct_name");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return merchantName;
	}

	  /**
	   * ͨ��POS-DCC��Ų��ҹ�Ͻ֧��
	   * @param PosDccCode Dcc���
	   * @return  ��Ͻ֧��
	   *
	   */
	public String getPOSOrgName(String PosDccCode) throws MonitorException {
		String merchantId = PosDccCode.substring(0, 15);
		String Organname = "", Organno = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT manage_bankno, manage_bankname FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Organno = rs.getString("manage_bankno");
				Organname = rs.getString("manage_bankname");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return Organno + " - " +Organname;
	}
}