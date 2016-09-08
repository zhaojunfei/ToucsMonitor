package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.toucsString;

public class BfTellerInfo {
	
	public BfTellerInfo() {
	}

	// 主机柜员号
	private String op_no = "";
	// 机构号
	private String org_id = "";
	// 期货公司代码
	private String fc_id = "";
	// DCC终端类型
	private String dcc_term_type = "";
	// DCC终端序号
	private String dcc_term_srl = "";
	// 密码
	private String passwd = "";
	// 签到标志
	private String sign_flag = "";
	// 使用标志
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
	 * 从Http请求中取商户信息
	 * @param request Http请求
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
	 * 从查询结果中取商户信息
	 * @param rst查询结果集
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
	 * 创建用于添加的动态SQL语句
	 * @param conn数据库连接对象
	 * @return 动态SQL语句对象
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
	 * 创建用于删除的动态SQL语句
	 * @param conn数据库连接对象
	 * @param key 设备编号（关键字）
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey1, String strKey2) throws SQLException {
		String sql = "DELETE FROM bf_host_op WHERE op_no = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey1);
		return stm;
	}

	/**
	 * 创建用于修改的动态SQL语句
	 * @param conn数据库连接对象
	 * @param key设备编号（关键字）
	 * @return 动态SQL语句对象
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
