package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.toucsString;

public class BfTellerInfo {
	
	public BfTellerInfo() {
	}

	// ������Ա��
	private String op_no = "";
	// ������
	private String org_id = "";
	// �ڻ���˾����
	private String fc_id = "";
	// DCC�ն�����
	private String dcc_term_type = "";
	// DCC�ն����
	private String dcc_term_srl = "";
	// ����
	private String passwd = "";
	// ǩ����־
	private String sign_flag = "";
	// ʹ�ñ�־
	private String use_flag = "";

	public String getDcc_term_srl() {
		return dcc_term_srl;
	}

	public void setDcc_term_srl(String dcc_term_srl) {
		this.dcc_term_srl = dcc_term_srl;
	}

	public String getDcc_term_type() {
		return dcc_term_type;
	}

	public void setDcc_term_type(String dcc_term_type) {
		this.dcc_term_type = dcc_term_type;
	}

	public String getFc_id() {
		return fc_id;
	}

	public void setFc_id(String fc_id) {
		this.fc_id = fc_id;
	}

	public String getOp_no() {
		return op_no;
	}

	public void setOp_no(String op_no) {
		this.op_no = op_no;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getSign_flag() {
		return sign_flag;
	}

	public void setSign_flag(String sign_flag) {
		this.sign_flag = sign_flag;
	}

	public String getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}

	/**
	 * ��Http������ȡ�̻���Ϣ
	 * @param request Http����
	 */
	public void fetchData(HttpServletRequest request) {
		setOp_no(request.getParameter("op_no"));
		setOrg_id(request.getParameter("org_id"));
		setFc_id(request.getParameter("fc_id"));
		setDcc_term_type(request.getParameter("dcc_term_type"));
		setDcc_term_srl(request.getParameter("dcc_term_srl"));
		setPasswd(request.getParameter("passwd"));
		setSign_flag(request.getParameter("sign_flag"));
		setUse_flag(request.getParameter("use_flag"));
	}

	/**
	 * �Ӳ�ѯ�����ȡ�̻���Ϣ
	 * @param rst��ѯ�����
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setOp_no(toucsString.unConvert(rst.getString("op_no")));
		setOrg_id(toucsString.unConvert(rst.getString("org_id")));
		setFc_id(toucsString.unConvert(rst.getString("fc_id")));
		setDcc_term_type(toucsString.unConvert(rst.getString("dcc_term_type")));
		setDcc_term_srl(toucsString.unConvert(rst.getString("dcc_term_srl")));
		setPasswd(toucsString.unConvert(rst.getString("passwd")));
		setSign_flag(toucsString.unConvert(rst.getString("sign_flag")));
		setUse_flag(toucsString.unConvert(rst.getString("use_flag")));
	}

	/**
	 * ����������ӵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO bf_host_op VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, op_no);
		stm.setString(2, org_id);
		stm.setString(3, fc_id);
		stm.setString(4, dcc_term_type);
		stm.setString(5, dcc_term_srl);
		stm.setString(6, passwd);
		stm.setString(7, sign_flag);
		stm.setString(8, use_flag);

		return stm;
	}

	/**
	 * ��������ɾ���Ķ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param key �豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey1, String strKey2) throws SQLException {
		String sql = "DELETE FROM bf_host_op WHERE op_no = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey1);
		return stm;
	}

	/**
	 * ���������޸ĵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param key�豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE bf_host_op SET "
				+ "(org_id,fc_id,dcc_term_type,dcc_term_srl,sign_flag,use_flag)"
				+ " =(?,?,?,?,?,?)"
				+ " WHERE op_no=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, org_id);
		stm.setString(2, fc_id);
		stm.setString(3, dcc_term_type);
		stm.setString(4, dcc_term_srl);
		stm.setString(5, sign_flag);
		stm.setString(6, use_flag);
		stm.setString(7, op_no);

		return stm;
	}
}
