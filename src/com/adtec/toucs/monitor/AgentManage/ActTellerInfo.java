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

public class ActTellerInfo {
	
	public ActTellerInfo() {
	}

	// ������
	private String branch_id = "";
	// ��Ա��
	private String teller_id = "";
	// ʹ�ñ�־
	private String use_flag = "";
	// ��ע1
	private String memo1 = "";
	// ��ע2
	private String memo2 = "";
	// ��ע3
	private String memo3 = "";

	public void setBranchid(String Branchid) {
		branch_id = Branchid;
	}

	public String getBranchid() {
		return branch_id;
	}

	public void setTellerid(String Tellerid) {
		teller_id = Tellerid;
	}

	public String getTellerid() {
		return teller_id;
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
		setBranchid(request.getParameter("branch_id"));
		setTellerid(request.getParameter("teller_id"));
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
		setBranchid(toucsString.unConvert(rst.getString("branch_id")));
		setTellerid(toucsString.unConvert(rst.getString("teller_id")));
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
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO act_teller VALUES(?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT ACT_TELLER:" + sql);

		stm.setString(1, branch_id);
		stm.setString(2, teller_id);
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
		String sql = "DELETE FROM act_teller WHERE branch_id = ? AND teller_id = ?";
		System.out.println("DELETE SQL:" + sql);
		System.out.println("BRANCH_ID:" + branch_id);
		System.out.println("TELLER_ID:" + teller_id);
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
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE act_teller SET " + "(memo1,memo2,memo3)" + " =(?,?,?)" + " WHERE branch_id=? AND teller_id=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, memo1);
		stm.setString(2, memo2);
		stm.setString(3, memo3);
		stm.setString(4, branch_id);
		stm.setString(5, teller_id);
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
			sql = "UPDATE act_teller SET " + "use_flag='0'" + " WHERE branch_id=? AND teller_id=?";
		} else {
			sql = "UPDATE act_teller SET " + "use_flag='1'" + " WHERE branch_id=? AND teller_id=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, branch_id);
		stm.setString(2, teller_id);		
		return stm;
	}
}