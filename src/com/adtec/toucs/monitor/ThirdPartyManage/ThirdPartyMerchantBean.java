package com.adtec.toucs.monitor.ThirdPartyManage;



import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 * @version 1.0
 */

public class ThirdPartyMerchantBean {
	
	public ThirdPartyMerchantBean() {
	}

	private String RetString = "";

  /**
   * �Ǽǵ������̻���Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param iCount δʹ�ò���
   * @return flag �Ǽǳɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int AppendInfo(ThirdPartyMerchantInfo info, int iCount) throws MonitorException {
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
   * �Ǽǵ������̻���Կ(t_merchant_key)
   * @param info �Ǽǵ��̻���Կ
   * @return �Ǽǳɹ�����1
   * @throws MonitorException 
   */
	public void addInfo(ThirdPartyMerchantInfo info) throws MonitorException{
		String date=Util.getCurrentDate();
		String time=Util.getCurrentTime();
		SqlAccess sq = SqlAccess.createConn();
		for(int i=1;i<9;i++){
			StringBuffer SqlStr = new StringBuffer();
			SqlStr.append("INSERT INTO t_merchant_key VALUES(");
			SqlStr.append("'" + info.getMerchantid());
			SqlStr.append("','" + "0"+i);
			SqlStr.append("','" + "0000000000000000");
			SqlStr.append("','" + "1111111111111111");
			SqlStr.append("','" + date);
			SqlStr.append("','" + time);
			SqlStr.append("')");	  
			try {
				sq.queryupdate(SqlStr.toString());		
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
    }
  
  /**
   * ɾ���������̻���Ϣ
   * @param info �Ǽǵ��̻���Ϣ
   * @param strKey �̻���
   * @return flag�ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int DeleteInfo(String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		ThirdPartyMerchantInfo info = new ThirdPartyMerchantInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
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
   * ɾ���������̻���Կ
   * @param info �Ǽǵ��̻���Ϣ
   * @param strKey �̻���
   * @return �ɹ�����1
   * @throws SQLException 
   * @throws MonitorException ���ϵͳ�쳣
   */
	public void Deletekey(String strKey){
		try {
			SqlAccess sq = SqlAccess.createConn();
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("DELETE FROM t_merchant_key WHERE merchant_id = '");
			sqlStr.append(strKey);
			sqlStr.append("'");
			sq.queryupdate(sqlStr.toString());
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

  /**
   * �޸ĵ������̻���Ϣ
   * @param info �Ǽǵ��豸��Ϣ
   * @param strKey δʹ�ò���
   * @return flag �ɹ�����1
   * @throws MonitorException ���ϵͳ�쳣
   */
	public int UpdateInfo(ThirdPartyMerchantInfo info, String strKey) throws MonitorException {
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
   * ��ѯ�������̻���Ϣ�б�
   * @param strKey �̻���
   * @param strType �̻�����
   * @return v �̻���Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "";
			if(strKey != null && !strKey.trim().equals("") && strType != null && !strType.trim().equals("")){
				sql = "SELECT * FROM  t_merchant_info WHERE merchant_id = '"+strKey+"' AND merchant_type = '"+strType+"'";
			}else if ( strKey != null && !strKey.trim().equals("")) {
				sql = "SELECT * FROM t_merchant_info WHERE merchant_id='" + strKey + "' ";          
			} else if (strType != null && !strType.trim().equals("")){
				sql = "SELECT * FROM t_merchant_info WHERE merchant_type='" + strType + "' ORDER BY merchant_id";
			}
			ThirdPartyMerchantInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new ThirdPartyMerchantInfo();
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
   * ��ѯ�������̻���Ϣ
   * @param strKey �̻���
   * @param code_type δʹ�ò���
   * @return info �̻���Ϣ������̻������ڣ�����null
   * @throws MonitorException ���ϵͳ�쳣
   */
	public ThirdPartyMerchantInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM t_merchant_info WHERE merchant_id='" + strKey + "'";
			ThirdPartyMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new ThirdPartyMerchantInfo();
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
			String sqlStr = "UPDATE t_merchant_info SET merchant_stat = '"+strFlag+"' WHERE merchant_id = '" + strKey + "'";
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
		System.out.println("-------ThirdPartyMerchantBean-----------------down_auth-----------------");
		String retStr = "";
		try {
			TranProc(CodeDefine.NEW_MASTER_KEYDOWN, key, use_flag);//����Կ����(new)                            "MG7002"���̻�id��1
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
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {//  "MG7002"���̻�id��1
		System.out.println("-------ThirdPartyMerchantBean-------------TranProc-----------------");
		
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, use_flag);//  "MG7002"���̻�id��1
		String retCode = comm.commitToATMP();
		
		System.out.println("----ThirdPartyMerchantBean----TranProc-----retCode-----"+retCode);
		
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
