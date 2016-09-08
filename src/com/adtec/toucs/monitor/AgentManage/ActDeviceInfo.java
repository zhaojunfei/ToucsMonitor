package com.adtec.toucs.monitor.AgentManage;


import java.sql.*;

import javax.servlet.http.*;
import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class ActDeviceInfo {
	
	public ActDeviceInfo() {
	}

	// �̻���
	private String merchant_id = "";
	// �豸��
	private String equip_id = "";
	// ʹ�ñ�־
	private String use_flag = "";
	// ��ע1
	private String memo1 = "";
	// ��ע2
	private String memo2 = "";
	// ��ע3
	private String memo3 = "";

	public void setMerchantid(String Merchantid) {
		merchant_id = Merchantid;
	}
	
	public String getMerchantid() {
		return merchant_id;
	}

	public void setEquipid(String Equipid) {
		equip_id = Equipid;
	}
	
	public String getEquipid() {
		return equip_id;
	}

	public void setUseflag(String Useflag) {
		use_flag = Useflag;
	}

	public String getUseflag() {
		return use_flag;
	}

	public void setMemo1(String Memo1) {
		memo1 = Memo1;
	}

	public String getMemo1() {
		return memo1;
	}

	public void setMemo2(String Memo2) {
		memo2 = Memo2;
	}

	public String getMemo2() {
		return memo2;
	}

	public void setMemo3(String Memo3) {
		memo3 = Memo3;
	}

	public String getMemo3() {
		return memo3;
	}

  /**
   * ��Http������ȡ�̻���Ϣ
   * @param request Http����
   */
	public void fetchData(HttpServletRequest request) {
		setMerchantid(request.getParameter("merchant_id"));
		setEquipid(request.getParameter("equip_id"));
		setUseflag(request.getParameter("use_flag"));
		setMemo1(request.getParameter("memo1"));
		setMemo2(request.getParameter("memo2"));
		setMemo3(request.getParameter("memo3"));
	}

  /**
   * �Ӳ�ѯ�����ȡ�̻���Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException {
		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
		setEquipid(toucsString.unConvert(rst.getString("equip_id")));
		setUseflag(toucsString.unConvert(rst.getString("use_flag")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
	}

  /**
   * ����������ӵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException,MonitorException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException,MonitorException {
  		String sql = "INSERT INTO act_info VALUES(?,?,?,?,?,?)";
  		PreparedStatement stm = conn.prepareStatement(sql);
  		System.out.println("INSERT ACT_MERCHANT:" + sql);

  		stm.setString(1, merchant_id);
  		stm.setString(2, equip_id);
  		stm.setString(3, "0");
  		stm.setString(4, memo1);
  		stm.setString(5, memo2);
  		stm.setString(6, memo3);

  		return stm;
  	}

  /**
   * ��������ɾ���Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
  	public PreparedStatement makeDeleteStm(Connection conn, String key1, String key2) throws SQLException {
  		String sql = "DELETE FROM act_info WHERE merchant_id = ? AND equip_id = ?";
  		System.out.println("DELETE SQL:" + sql);
  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, key1);
  		stm.setString(2, key2);

  		return stm;
  	}

  /**
   * ���������޸ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException,MonitorException
   */
  	public PreparedStatement makeUpdateStm(Connection conn, String strKey) throws SQLException, MonitorException {
  		String sql = "UPDATE act_info SET " + "(memo1,memo2,memo3)" + " =(?,?,?)" + " WHERE merchant_id=? AND equip_id=?";
  		Debug.println(sql);
  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, memo1);
  		stm.setString(2, memo2);
  		stm.setString(3, memo3);
  		stm.setString(4, merchant_id);
  		stm.setString(5, equip_id);

  		return stm;
  	}

  /**
   * ����������������ͣ��״̬�ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
  	public PreparedStatement makeStatStm(Connection conn, String strKey,boolean bStat) throws SQLException {
  		String sql = "";
  		if (bStat) {
  			sql = "UPDATE act_info SET " + "use_flag='0'" + " WHERE merchant_id=? AND equip_id=?";
  		} else {
  			sql = "UPDATE act_info SET " + "use_flag='1'" + " WHERE merchant_id=? AND equip_id=?";
  		}
  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, merchant_id);
  		stm.setString(2, equip_id);

  		return stm;
  	}
}