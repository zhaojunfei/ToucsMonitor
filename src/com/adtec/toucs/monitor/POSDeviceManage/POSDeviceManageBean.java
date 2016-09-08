package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;
import com.adtec.toucs.monitor.devicemanage.*;

/**
 * <p>Title: POS�豸������</p>
 * <p>Description:POS��װ�豸������ص�ҵ���߼�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSDeviceManageBean {

	public POSDeviceManageBean() {
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
	public int updatePosInfo(POSInfo info, String key) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			//�˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//����豸������Ϣ
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
     
			if (flag == 1) {
				sq.commit(); //�ύ 
			}else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�POS�̻���Ϣʧ�ܣ�");
			}
			return flag;
		}catch (SQLException e1) {
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
	public POSInfo qryPosInfo(String posCode, String code_type) throws MonitorException {
		Debug.println("*****wuye debug; pos code type = :" + code_type + "*******");
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp���
				sql = "SELECT * FROM pos_info WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC���
				sql = "SELECT * FROM pos_info WHERE pos_c_code='" + posCode + "'";
			}else if (code_type.trim().equals("3")) { //DCC���
				sql = "SELECT * FROM pos_info WHERE pos_dcc_code='" + posCode + "'";
			}else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,
                                   ErrorDefine.NOTHISDEVICEDESC);
			}

			Debug.println("*****wuye debug; pos SQL = :\n" + sql);

			POSInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
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
			String sql = "SELECT a.pos_code ,a.pos_dcc_code,c.org_name , b.mct_name , a.pos_stat , "
						+ "a.merchant_id "
						+ "FROM pos_info a,pos_merchant b , monit_orginfo c"
						+ " WHERE (a.merchant_id='" + merchantNo
						+ "')AND(a.merchant_id = b.merchant_id)"
						+ "AND(b.org_id = c.org_id)";

			//add by liyp 20031022 add sort condition
			sql += " ORDER BY pos_dcc_code ";
			Debug.println("SQL:" + sql);
			POSExInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new POSExInfo();
				info.fetchData(rst);
				v.add(info);
			}
			rst.close();
			return v;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}

	  /**
	   * �Ǽ�POS���豸
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int regPosDevice(POSInfo info, int nPoscount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			//�����Կ������¼
			if (flag == 1) {
				AtmKeyInfo keyInfo = new AtmKeyInfo(info.getPoscode());
				//Ĭ������ add  by liyp 20030812
				keyInfo.setDesType(0, '0');
				keyInfo.setDesType(1, '0');
				keyInfo.setDesType(2, '0');
				keyInfo.setPikLen("8");
				keyInfo.setCdkLen("8");
				keyInfo.setMakLen("8");
				keyInfo.setPinblkMet("1");
				keyInfo.setMacMet("2");

				flag = keyInfo.insert(sq);
			}
			if (flag == 1) {
				sq.commit(); //�ύ
			} else {
				Debug.println("in regpos*****wuye");
				sq.rollback();//����ع�
				sq.close();
				this.delOnlyPos(info.getPoscode());
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ�POS�豸��Ϣʧ�ܣ�");
			}
			return flag;
		}catch (SQLException e1) {
			sq.rollback();
			sq.close();
			this.delOnlyPos(info.getPoscode());
			Debug.println("\n****wuye debug;���벻�ɹ���delete" + info.getPoscode() + " OK !*****");
			throw new MonitorException(e1);
		}finally {
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
		POSInfo posInfo = new POSInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			//�˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ
			PreparedStatement stm = posInfo.makeDeleteStm(sq.conn, posCode);
			int flag = stm.executeUpdate();
			//ɾ����Կ��
			if (flag == 1) {
				String delKeySql = "DELETE FROM kernel_key_info WHERE sender_agency ='" + posCode.trim() + "'";
				PreparedStatement st = sq.conn.prepareStatement(delKeySql);
				flag = st.executeUpdate();
			} 
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
		POSInfo posInfo = new POSInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			//�˴���֪��Ϊʲô����ִ��sq.setAutoCommit(false);������ע�͵�
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//�����̻������־������Ϣ
			PreparedStatement stm = posInfo.makeStatStm(sq.conn, pos_code, bStat);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag); 	  
			if (flag == 1) {
				sq.commit();//�ύ 	
			} else {
				sq.rollback();//����ع�
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
			String sql = "SELECT * FROM kernel_key_info WHERE sender_agency='" + posCode + "'";
			ResultSet rst = sq.queryselect(sql);
			AtmKeyInfo keyInfo = null;
			if (rst != null && rst.next()) {
				keyInfo = new AtmKeyInfo();
				keyInfo.fetchData(rst);
			}
			rst.close();
			return keyInfo;
		}catch (SQLException e1) {
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
		String sql = "SELECT a.org_id FROM pos_merchant a, pos_info b " + "WHERE a.merchant_id =b.merchant_id AND b.pos_code='" + posCode + "'";
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
					sql = "SELECT org_id FROM monit_orgref WHERE org_id='" + tmpOrgId + "' AND p_org_id='" + orgId + "'";
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
	   * ����POS�豸���޸����ݿ��־��
	   * @param PosID POS�豸���
	   * @throws MonitorException
	   */
	public void startPOS(String PosID) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (PosID == null || PosID.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			String sqlStr = "UPDATE pos_info SET pos_stat = '1' WHERE pos_code = '" + PosID + "'";
			int affect = conn.queryupdate(sqlStr);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}	
	}	

	  /**
	   * ͣ��POS�豸�������ݿ��pos_info�б��Ϊ�����豸��ŵļ�¼pos_stat����Ϊ0��
	   * @param PosID POS�豸���
	   * @throws MonitorException
	   */
	public void stopPOS(String PosID) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (PosID == null || PosID.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			String sqlStr = "UPDATE pos_info SET pos_stat = '0' WHERE pos_code = '" + PosID + "'";
			int affect = conn.queryupdate(sqlStr);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,
                                   ErrorDefine.NOTHISDEVICEDESC);
			}
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
		}catch (MonitorException ex) {
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
				POSInfo pi = new POSInfo();
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
		SqlAccess conn = SqlAccess.createConn();
		String sqlStr = "";
		try {
			if (objId == null || objId.trim().equals("") || objId.trim().equals("000000")) {
				sqlStr = "SELECT * FROM pos_info";
			} else {
				//modify by liyp 20030918
				sqlStr = "SELECT * FROM pos_info  WHERE pos_code = '" + objId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSInfo pi = new POSInfo();
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
	   * Ϊ�ͻ��˳�ʼ��POS��Ϣ׼�����ݣ�����XML��ʽ���ַ�����
	   * @param orgId ��������
	   * @param DealCode ������
	   * @param ErrorCode ������
	   * @param ErrorDescribe ��������
	   * @return xml��ʽ���ַ���
	   * @throws MonitorException
	   */
	public String getPOSInfo_Xml(String objId, String DealCode, String ErrorCode,
                               String ErrorDescribe) throws MonitorException {
		List list = this.getPOSInfoByObjId(objId);
		StringBuffer retXml = new StringBuffer(list.size() * 250);
		retXml.append("<Deal>\n");

		retXml.append("<DealCode>");
		retXml.append(DealCode);
		retXml.append("</DealCode>\n");

		retXml.append("<ErrorCode>");
		retXml.append(ErrorCode);
		retXml.append("</ErrorCode>\n");

		retXml.append("<ErrorDescribe>");
		retXml.append(ErrorDescribe);
		retXml.append("</ErrorDescribe>\n");

		if (list != null || list.size() != 0) {
			retXml.append("<Data>\n");
			for (int i = 0; i < list.size(); i++) {
				POSInfo pi = (POSInfo) list.get(i);
				retXml.append("<POSInfo id=");
				retXml.append(pi.getPoscode());
				retXml.append(">\n");

				retXml.append("<MctId>");
				retXml.append(pi.getMerchantid());
				retXml.append("</MctId>\n");

				retXml.append("<State>");
				retXml.append(pi.getPosstat());
				retXml.append("</State>\n");

				retXml.append("<FixDate>");
				retXml.append(pi.getFixdate());
				retXml.append("</FixDate>\n");

				retXml.append("<DeviceDCCCode>");
				retXml.append(pi.getPosDccCode());
				retXml.append("</DeviceDCCCode>\n");

				retXml.append("<DeviceCCode>");
				retXml.append(pi.getPosCCode());
				retXml.append("</DeviceCCode>\n");

				retXml.append("</POSInfo>\n");
			}
			retXml.append("</Data>\n");
		}
		retXml.append("</Deal>");
		return retXml.toString();
	}

	  /**
	   *  �����豸�����ѯPOS�������ü����俨������Ϣ
	   * @param objId �豸����
	   * @return ����POSTxnInfo��
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public POSInfo qryPosTxnInfo(String objId, String code_type) throws MonitorException {
		if (code_type == null) {
			code_type = "";
		}
		code_type = code_type.trim();
		SqlAccess conn = SqlAccess.createConn();
		POSInfo pi = null;
		String sqlStr = "";
		try {
			sqlStr = "SELECT pos_code,pos_dcc_code,pos_c_code,pre_authorized,equip_type,tran_right,hand_flag,memo1 " + "FROM pos_info WHERE ";
			if (code_type.equalsIgnoreCase(POSPTYPE)) {
				sqlStr += " pos_code = '";
			} else if (code_type.equalsIgnoreCase(POSCTYPE)) {
				sqlStr += " pos_c_code = '";
			} else {
				sqlStr += " pos_dcc_code = '";
			}
			sqlStr += objId;
			sqlStr += "'";
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				pi = new POSInfo();
				pi.fetchTxnData(rs);
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
	public Vector qryPosTxnInfoSet(POSInfo pi) throws MonitorException {
		Vector vReturn = new Vector();
		Vector vTxn = TxnCodeManageBean.queryPosTxnCodes();
		String txnRight = pi.getTxnRight();
		String handFlag = pi.getHandFlag();
		System.out.println(vTxn.size());
		if (vTxn != null) {
			for (int i = 0; i < vTxn.size(); i++) {
				POSTxnInfo pti = new POSTxnInfo();
				TxnCodeBean code = (TxnCodeBean) vTxn.get(i);
				int sn = code.getMaskSerial();
				pti.setLocation(sn - 1);
				pti.setTxnName(code.getTxnName());
				pti.setTxnPerm(txnRight.charAt(sn - 1));
				pti.setHandPerm(handFlag.charAt(sn - 1));
				
				vReturn.add(pti);
			}
		}
		return vReturn;
	}

	  /**
	   * �޸�POS�豸�������ü����俨������Ϣ
	   * @param info �Ǽǵ��豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public int updatePosTxnInfo(POSInfo info) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			PreparedStatement stm = info.makeTxnUpdateStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
      
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED,"�޸�POS���俨��������Ϣʧ�ܣ�");
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
	   * ��POS-DCC���ת��ΪPOS-P���
	   * @param pos_dcc_code DCC���
	   * @return pos_code P�˱��
	   */
	public static String transDCCCodeToPCode(String pos_dcc_code) throws MonitorException {
		String pos_p_code = "";
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT pos_code FROM pos_info WHERE pos_dcc_code = '" + pos_dcc_code + "'";
			ResultSet rs = sq.queryselect(sql);
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
		SqlAccess sq = SqlAccess.createConn();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			String sql;
			sql = "SELECT pos_dcc_code FROM pos_info WHERE pos_code = '" + pos_code + "'";
			ResultSet rs = sq.queryselect(sql);
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
			PreparedStatement stm = (new POSInfo()).makeDeleteStm(sq.conn, posCode);
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
		SqlAccess sq = SqlAccess.createConn();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			String sql = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = sq.queryselect(sql);
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
	   */
	public String getPOSOrgName(String PosDccCode) throws MonitorException {
		String merchantId = PosDccCode.substring(0, 15);
		String Organname = "", Organno = "";
		SqlAccess sq =  SqlAccess.createConn();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			String sql = "SELECT manage_bankno, manage_bankname FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = sq.queryselect(sql);
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
