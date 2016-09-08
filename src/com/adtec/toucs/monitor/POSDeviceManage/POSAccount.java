package com.adtec.toucs.monitor.POSDeviceManage;



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

public class POSAccount {
	
	public POSAccount() {
	}

	// �˺�
	private String account;
	// ��λ����
	private String unit_name;
	// ��Ȩ��
	private String auth_code;
	// ��Ȩ����Ч����
	private String end_date;
	// ��Ч����
	private String auth_days;
	//��ע1
	private String memo1 = "";
	//��ע2
	private String memo2 = "";
	//��ע3
	private String memo3 = "";

	// �˺�
	public void setAccount(String Account) {
		account = Account;
	}
	public String getAccount() {
		return account;
	}
	// ��λ����
	public void setUnitName(String UnitName) {
		unit_name = UnitName;
	}
	public String getUnitName() {
		return unit_name;
	}
	// ��Ȩ��
	public void setAuthCode(String AuthCode) {
		auth_code = AuthCode;
	}
	public String getAuthCode() {
		return auth_code;
	}
	// ��Ȩ����Ч����
	public void setEndDate(String EndDate) {
		end_date = EndDate;
	}
	public String getEndDate() {
		return end_date;
	}
	// ��Ч����
	public void setAuthDays(String AuthDays) {
		auth_days = AuthDays;
	}
	public String getAuthDays() {
		return auth_days;
	}
	//��ע1
	public void setMemo1(String Memo1) {
		memo1 = Memo1;
	}
	public String getMemo1() {
		return memo1;
	}
	//��ע2
	public void setMemo2(String Memo2) {
		memo2 = Memo2;
	}
	public String getMemo2() {
		return memo2;
	}
	//��ע3
	public void setMemo3(String Memo3) {
		memo3 = Memo3;
	}
	public String getMemo3() {
		return memo3;
	}

	  /**
	   * ��Http������ȡ�˻���Ϣ
	   * @param request Http����
	   */
	public void fetchData(HttpServletRequest request) {
		setAccount(request.getParameter("account"));
		setUnitName(request.getParameter("unit_name"));
		setAuthCode(request.getParameter("auth_code"));
		setEndDate(request.getParameter("end_date"));
		setAuthDays(request.getParameter("auth_days"));
		setMemo1(request.getParameter("memo1"));
		setMemo2(request.getParameter("memo2"));
		setMemo3(request.getParameter("memo3"));
	}

	  /**
	   * �Ӳ�ѯ�����ȡ�˻���Ϣ
	   * @param rst ��ѯ�����
	   * @throws SQLException
	   */
	public void fetchData(ResultSet rst) throws SQLException {
		setAccount(toucsString.unConvert(rst.getString("account")));
		setUnitName(toucsString.unConvert(rst.getString("unit_name")));
		setAuthCode(toucsString.unConvert(rst.getString("auth_code")));
		setEndDate(toucsString.unConvert(rst.getString("end_date")));
		setAuthDays(toucsString.unConvert(rst.getString("auth_days")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
	}

	  /**
	   * ����˻���Ϣ
	   * @param sq ���ݿ���ʶ���
	   * @return �ɹ�����1
	   * @throws SQLException
	   */
	public int insert(SqlAccess sq) throws SQLException{
		String sql="INSERT INTO kernel_acct_auth VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement stm=sq.conn.prepareStatement(sql);
		stm.setString(1,account);
		stm.setString(2,unit_name);
		stm.setString(3,auth_code);
		stm.setString(4,end_date);
		stm.setString(5,auth_days);
		stm.setString(6,memo1);
		stm.setString(7,memo2);
		stm.setString(8,memo3);

		return stm.executeUpdate();
	}

	  /**
	   * ����������ӵĶ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO kernel_acct_auth VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT ACCT_AUTH:" + sql);
		
		//Ĭ��״̬Ϊ����
		stm.setString(3, "1");
		
		return stm;
	}

	  /**
	   * ���������޸ĵĶ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE kernel_acct_auth SET "
				   + "(unit_name,end_date,auth_days,memo1,memo2,memo3)"
				   + " =(?,?,?,?,?,?)"
				   + " WHERE account=?";
		Debug.println(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		Debug.println("["+unit_name+"]["+auth_days+"]"+"{"+key+"}");
		
		stm.setString(1,unit_name);
		stm.setString(2,end_date);
		stm.setString(3,auth_days);
		stm.setString(4,memo1);
		stm.setString(5,memo2);
		stm.setString(6,memo3);
		stm.setString(7, key);
		
		return stm;
	}
	
	  /**
	   * ����������������ͣ��״̬�ĵĶ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeStatStm(Connection conn, String key,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		} else {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		}
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, key);
		
		return stm;
	}

	  /**
	   * �������ڸ����̻���ѯ�Ķ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeQueryByMctStm(Connection conn, String key) throws SQLException {
		String sql = "select * from pos_info WHERE (merchant_id=?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, key);
		return stm;
	}

	  /**
	   * ��������ɾ���Ķ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeDeleteStm(Connection conn, String key) throws SQLException {
		String sql = "DELETE FROM kernel_acct_auth WHERE account = ?";
		System.out.println("DELETE SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//ɾ��������
		stm.setString(1, key);
		return stm;
	}
}