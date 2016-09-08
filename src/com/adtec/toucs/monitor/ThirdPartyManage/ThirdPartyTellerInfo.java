package com.adtec.toucs.monitor.ThirdPartyManage;


import java.sql.*;

import javax.servlet.http.*;
import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 */

public class ThirdPartyTellerInfo {
	
	public ThirdPartyTellerInfo() {
	}

	//��������
	private String channel_id;
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

	//�����������Զ�д
	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channelId) {
		channel_id = channelId;
	}

	//���������Զ�д
	public void setBranchid(String Branchid) {
		branch_id = Branchid;
	}

	public String getBranchid() {
		return branch_id;
	}

	//��Ա�����Զ�д
	public void setTellerid(String Tellerid) {
		teller_id = Tellerid;
	}

	public String getTellerid() {
		return teller_id;
	}

	// ʹ�ñ�־���Զ�д
	public void setUseflag(String Useflag) {
		use_flag = Useflag;
	}

	public String getUseflag() {
		return use_flag;
	}

	// ��ע1���Զ�д
	public void setMemo1(String Memo1) {
		memo1 = Memo1;
	}

	public String getMemo1() {
		return memo1;
	}

	// ��ע2���Զ�д
	public void setMemo2(String Memo2) {
		memo2 = Memo2;
	}

	public String getMemo2() {
		return memo2;
	}

	// ��ע3���Զ�д
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
		setChannel_id(request.getParameter("channel_id"));  
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
		setChannel_id(toucsString.unConvert(rst.getString("channel_id")));  
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
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException,MonitorException {
		String sql = "INSERT INTO t_teller_info VALUES(?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT T_TELLER_INFO:" + sql);
		stm.setString(1, channel_id);
		stm.setString(2, branch_id);
		stm.setString(3, teller_id);
		stm.setString(4, "0");
		stm.setString(5, "1");
		stm.setString(6, "1");
		stm.setString(7, "1");
		return stm;
	}

  /**
   * ��������ɾ���Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key1 ������
   * @param key2 ��Ա��
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeDeleteStm(Connection conn, String key1, String key2) throws SQLException {
		String sql = "DELETE FROM t_teller_info WHERE branch_id = ? AND teller_id = ?";
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
   * @param key δʹ�ò���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE t_teller_info SET memo1=?,memo2=?,memo3=?,teller_id=?, branch_id=? WHERE  channel_id=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, memo1);
		stm.setString(2, memo2);
		stm.setString(3, memo3);
		stm.setString(4, teller_id);
		stm.setString(5, branch_id);
		stm.setString(6, channel_id);
		return stm;
	}

  /**
   * ����������������ͣ��״̬�ĵĶ�̬SQL���
   * 
   * @param conn ���ݿ����Ӷ���
   * @param key δʹ�ò���
   * @param bStat ʹ�ñ�ʶ
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeStatStm(Connection conn, String key,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE t_teller_info SET " + "use_flag='0'" + " WHERE branch_id=? AND teller_id=?";
		} else {
			sql = "UPDATE t_teller_info SET " + "use_flag='1'" + " WHERE branch_id=? AND teller_id=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, branch_id);
		stm.setString(2, teller_id);
		return stm;
	}
}