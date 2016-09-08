package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class BfInvestorInfo {
	
	public BfInvestorInfo() {
	}

	// 投资人ID
	private String investor_id = "";
	// 投资人名称
	private String name = "";
	// 投资人开户期货公司
	private String fc_id = "";
	// 投资人类型
	private String type = "";
	// 证件类型
	private String id_card_type = "";
	// 证件号码
	private String id_card_code = "";
	// 保证金帐号
	private String deposit_acc_no = "";
	// 投资人地址
	private String addr = "";
	// 投资人邮政编码
	private String pc = "";
	// 投资人电话号码
	private String telephone = "";
	// 投资人传真号码
	private String fax = "";
	// 投资人电子邮件地址
	private String email = "";
	// 重要程度
	private String level = "";
	// 最后修改日期
	private String modify_date = "";
	// 最后修改时间
	private String modify_time = "";
	// 有效标志
	private String use_flag = "";

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDeposit_acc_no() {
		return deposit_acc_no;
	}

	public void setDeposit_acc_no(String deposit_acc_no) {
		this.deposit_acc_no = deposit_acc_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFc_id() {
		return fc_id;
	}

	public void setFc_id(String fc_id) {
		this.fc_id = fc_id;
	}

	public String getId_card_code() {
		return id_card_code;
	}

	public void setId_card_code(String id_card_code) {
		this.id_card_code = id_card_code;
	}

	public String getId_card_type() {
		return id_card_type;
	}

	public void setId_card_type(String id_card_type) {
		this.id_card_type = id_card_type;
	}

	public String getInvestor_id() {
		return investor_id;
	}

	public void setInvestor_id(String investor_id) {
		this.investor_id = investor_id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public String getModify_time() {
		return modify_time;
	}

	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		setInvestor_id(request.getParameter("investor_id"));
		setName(request.getParameter("name"));
		setFc_id(request.getParameter("fc_id"));
		setType(request.getParameter("type"));
		setId_card_type(request.getParameter("id_card_type"));
		setId_card_code(request.getParameter("id_card_code"));
		setDeposit_acc_no(request.getParameter("deposit_acc_no"));
		setAddr(request.getParameter("addr"));
		setPc(request.getParameter("pc"));
		setTelephone(request.getParameter("telephone"));
		setFax(request.getParameter("fax"));
		setEmail(request.getParameter("email"));
		setLevel(request.getParameter("level"));
		setModify_date(request.getParameter("modify_date"));
		setModify_time(request.getParameter("modify_time"));
		setUse_flag(request.getParameter("use_flag"));
	}

	/**
	 * 从查询结果中取商户信息
	 * @param rst 查询结果集
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setInvestor_id(toucsString.unConvert(rst.getString("investor_id")));
		setName(toucsString.unConvert(rst.getString("name")));
		setFc_id(toucsString.unConvert(rst.getString("fc_id")));
		setType(toucsString.unConvert(rst.getString("type")));
		setId_card_type(toucsString.unConvert(rst.getString("id_card_type")));
		setId_card_code(toucsString.unConvert(rst.getString("id_card_code")));
		setDeposit_acc_no(toucsString.unConvert(rst.getString("deposit_acc_no")));
		setAddr(toucsString.unConvert(rst.getString("addr")));
		setPc(toucsString.unConvert(rst.getString("pc")));
		setTelephone(toucsString.unConvert(rst.getString("telephone")));
		setFax(toucsString.unConvert(rst.getString("fax")));
		setEmail(toucsString.unConvert(rst.getString("email")));
		setLevel(toucsString.unConvert(rst.getString("level")));
		setModify_date(toucsString.unConvert(rst.getString("modify_date")));
		setModify_time(toucsString.unConvert(rst.getString("modify_time")));
		setUse_flag(toucsString.unConvert(rst.getString("use_flag")));
	}

	/**
	 * 创建用于添加的动态SQL语句
	 * @param conn数据库连接对象
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO bf_investor VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, investor_id);
		stm.setString(2, name);
		stm.setString(3, fc_id);
		stm.setString(4, type);
		stm.setString(5, id_card_type);
		stm.setString(6, id_card_code);
		stm.setString(7, deposit_acc_no);
		stm.setString(8, addr);
		stm.setString(9, pc);
		stm.setString(10, telephone);
		stm.setString(11, fax);
		stm.setString(12, email);
		stm.setString(13, level);
		stm.setString(14, modify_date);
		stm.setString(15, modify_time);
		stm.setString(16, use_flag);

		return stm;
	}

	/**
	 * 创建用于删除的动态SQL语句
	 * @param conn数据库连接对象
	 * @param strKey1 烟草公司编号
	 * @param strKey2 银行卡号
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey1, String strKey2) throws SQLException {
		String sql = "DELETE FROM bf_investor WHERE fc_id = ? AND investor_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey1);
		stm.setString(2, strKey2);
		return stm;
	}

	/**
	 * 创建用于修改的动态SQL语句 
	 * @param conn数据库连接对象
	 * @param key设备编号（关键字）
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key)
			throws SQLException, MonitorException {
		String sql = "UPDATE bf_investor SET "
				+ "(name,type,id_card_type,id_card_code,deposit_acc_no,addr,"
				+ "pc,telephone,fax,email,level,modify_date,modify_time,use_flag)"
				+ " =(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + " WHERE fc_id=? AND investor_id = ?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, name);
		stm.setString(2, type);
		stm.setString(3, id_card_type);
		stm.setString(4, id_card_code);
		stm.setString(5, deposit_acc_no);
		stm.setString(6, addr);
		stm.setString(7, pc);
		stm.setString(8, telephone);
		stm.setString(9, fax);
		stm.setString(10, email);
		stm.setString(11, level);
		stm.setString(12, modify_date);
		stm.setString(13, modify_time);
		stm.setString(14, use_flag);
		stm.setString(15, fc_id);
		stm.setString(16, investor_id);

		return stm;
	}
}
