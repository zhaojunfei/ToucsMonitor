package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class BfInvestorInfo {
	
	public BfInvestorInfo() {
	}

	// Ͷ����ID
	private String investor_id = "";
	// Ͷ��������
	private String name = "";
	// Ͷ���˿����ڻ���˾
	private String fc_id = "";
	// Ͷ��������
	private String type = "";
	// ֤������
	private String id_card_type = "";
	// ֤������
	private String id_card_code = "";
	// ��֤���ʺ�
	private String deposit_acc_no = "";
	// Ͷ���˵�ַ
	private String addr = "";
	// Ͷ������������
	private String pc = "";
	// Ͷ���˵绰����
	private String telephone = "";
	// Ͷ���˴������
	private String fax = "";
	// Ͷ���˵����ʼ���ַ
	private String email = "";
	// ��Ҫ�̶�
	private String level = "";
	// ����޸�����
	private String modify_date = "";
	// ����޸�ʱ��
	private String modify_time = "";
	// ��Ч��־
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
	 * ��Http������ȡ�̻���Ϣ
	 * @param request Http����
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
	 * �Ӳ�ѯ�����ȡ�̻���Ϣ
	 * @param rst ��ѯ�����
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
	 * ����������ӵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @return ��̬SQL������
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
	 * ��������ɾ���Ķ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param strKey1 �̲ݹ�˾���
	 * @param strKey2 ���п���
	 * @return ��̬SQL������
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
	 * ���������޸ĵĶ�̬SQL��� 
	 * @param conn���ݿ����Ӷ���
	 * @param key�豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
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
