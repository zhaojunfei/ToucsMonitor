package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class BfFcInfo {
	public BfFcInfo() {
	}

	// �ڻ���˾����
	private String fc_id = "";
	// �ڻ���˾����
	private String name = "";
	// �ڻ���˾���
	private String brief = "";
	// ��ȫ����
	private String secu_kind = "";
	// �ڻ���˾ǰ�û�IP��ַ
	private String ip_addr = "";
	// �ڻ���˾��ϵ��
	private String contractor = "";
	// �ڻ���˾��ַ
	private String addr = "";
	// �ڻ���˾��������
	private String pc = "";
	// �ڻ���˾�绰����
	private String telephone = "";
	// �ڻ���˾�������
	private String fax = "";
	// �ڻ���˾�����ʼ���ַ
	private String email = "";
	// �����ʺ�
	private String bank_acc_no = "";
	// �̻���
	private String merchant_id = "";
	// ������
	private String org_id = "";
	// ����ת����С���
	private String min_sum_of_tran;
	// ����ת�������
	private String max_sum_of_tran;
	// ��Ҫ�̶�
	private String level = "";
	// ��������Կ
	private String master_key = "";
	// ���������Կ
	private String pin_key = "";
	// MACУ����Կ
	private String mac_key = "";
	// ��ʼ����
	private String start_date = "";
	// ��ֹ����
	private String end_date = "";
	// ����޸�����YYYYMMDD
	private String modify_date = "";
	// ����޸�ʱ��HHMMSS
	private String modify_time = "";
	// ǩ����־
	private String sign_flag = "";
	// ���ñ�־
	private String use_flag = "";

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBank_acc_no() {
		return bank_acc_no;
	}

	public void setBank_acc_no(String bank_acc_no) {
		this.bank_acc_no = bank_acc_no;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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

	public String getIp_addr() {
		return ip_addr;
	}

	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMac_key() {
		return mac_key;
	}

	public void setMac_key(String mac_key) {
		this.mac_key = mac_key;
	}

	public String getMaster_key() {
		return master_key;
	}

	public void setMaster_key(String master_key) {
		this.master_key = master_key;
	}

	public String getMax_sum_of_tran() {
		return max_sum_of_tran;
	}

	public void setMax_sum_of_tran(String max_sum_of_tran) {
		this.max_sum_of_tran = max_sum_of_tran;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMin_sum_of_tran() {
		return min_sum_of_tran;
	}

	public void setMin_sum_of_tran(String min_sum_of_tran) {
		this.min_sum_of_tran = min_sum_of_tran;
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

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getPin_key() {
		return pin_key;
	}

	public void setPin_key(String pin_key) {
		this.pin_key = pin_key;
	}

	public String getSecu_kind() {
		return secu_kind;
	}

	public void setSecu_kind(String secu_kind) {
		this.secu_kind = secu_kind;
	}

	public String getSign_flag() {
		return sign_flag;
	}

	public void setSign_flag(String sign_flag) {
		this.sign_flag = sign_flag;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}

	/**
	 * ��Http������ȡ�̻���Ϣ
	 * @param requestHttp����
	 */
	public void fetchData(HttpServletRequest request) {
		setFc_id(request.getParameter("fc_id"));
		setName(request.getParameter("name"));
		setBrief(request.getParameter("brief"));
		setSecu_kind(request.getParameter("secu_kind"));
		setIp_addr(request.getParameter("ip_addr"));
		setContractor(request.getParameter("contractor"));
		setAddr(request.getParameter("addr"));
		setPc(request.getParameter("pc"));
		setTelephone(request.getParameter("telephone"));
		setFax(request.getParameter("fax"));
		setEmail(request.getParameter("email"));
		setBank_acc_no(request.getParameter("bank_acc_no"));
		setMerchant_id(request.getParameter("merchant_id"));
		setOrg_id(request.getParameter("org_id"));
		setMin_sum_of_tran(request.getParameter("min_sum_of_tran"));
		setMax_sum_of_tran(request.getParameter("max_sum_of_tran"));
		setLevel(request.getParameter("level"));
		setStart_date(request.getParameter("start_date"));
		setEnd_date(request.getParameter("end_date"));
		setModify_date(request.getParameter("modify_date"));
		setModify_time(request.getParameter("modify_time"));
		setSign_flag(request.getParameter("sign_flag"));
		setUse_flag(request.getParameter("use_flag"));
	}

	/**
	 * �Ӳ�ѯ�����ȡ�̻���Ϣ
	 * @param rst��ѯ�����
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setFc_id(toucsString.unConvert(rst.getString("fc_id")));
		setName(toucsString.unConvert(rst.getString("name")));
		setBrief(toucsString.unConvert(rst.getString("brief")));
		setSecu_kind(toucsString.unConvert(rst.getString("secu_kind")));
		setIp_addr(toucsString.unConvert(rst.getString("ip_addr")));
		setContractor(toucsString.unConvert(rst.getString("contractor")));
		setAddr(toucsString.unConvert(rst.getString("addr")));
		setPc(toucsString.unConvert(rst.getString("pc")));
		setTelephone(toucsString.unConvert(rst.getString("telephone")));
		setFax(toucsString.unConvert(rst.getString("fax")));
		setEmail(toucsString.unConvert(rst.getString("email")));
		setBank_acc_no(toucsString.unConvert(rst.getString("bank_acc_no")));
		setMerchant_id(toucsString.unConvert(rst.getString("merchant_id")));
		setOrg_id(toucsString.unConvert(rst.getString("org_id")));
		setMin_sum_of_tran(toucsString.unConvert(rst.getString("min_sum_of_tran")));
		setMax_sum_of_tran(toucsString.unConvert(rst.getString("max_sum_of_tran")));
		setLevel(toucsString.unConvert(rst.getString("level")));
		setStart_date(toucsString.unConvert(rst.getString("start_date")));
		setEnd_date(toucsString.unConvert(rst.getString("end_date")));
		setModify_date(toucsString.unConvert(rst.getString("modify_date")));
		setModify_time(toucsString.unConvert(rst.getString("modify_time")));
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
		String sql = "INSERT INTO bf_fc VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setString(1, fc_id);
		stm.setString(2, name);
		stm.setString(3, brief);
		stm.setString(4, secu_kind);
		stm.setString(5, ip_addr);
		stm.setString(6, contractor);
		stm.setString(7, addr);
		stm.setString(8, pc);
		stm.setString(9, telephone);
		stm.setString(10, fax);
		stm.setString(11, email);
		stm.setString(12, bank_acc_no);
		stm.setString(13, merchant_id);
		stm.setString(14, org_id);
		stm.setString(15, min_sum_of_tran);
		stm.setString(16, max_sum_of_tran);
		stm.setString(17, level);
		stm.setString(18, "0000000000000000");
		stm.setString(19, "0000000000000000");
		stm.setString(20, "0000000000000000");
		stm.setString(21, start_date);
		stm.setString(22, end_date);
		stm.setString(23, modify_date);
		stm.setString(24, modify_time);
		stm.setString(25, sign_flag);
		stm.setString(26, use_flag);

		return stm;
	}

	/**
	 * ��������ɾ���Ķ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param strKey�豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey) throws SQLException {
		String sql = "DELETE FROM bf_fc WHERE fc_id = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey);
		return stm;
	}

	/**
	 * ���������޸ĵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param key�豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException,MonitorException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE bf_fc SET "
				   + "(name,brief,secu_kind,ip_addr,contractor,addr,bank_acc_no,org_id,"
			       + "min_sum_of_tran,max_sum_of_tran,start_date,end_date,modify_date,"
				   + "modify_time,sign_flag,use_flag)"
				   + " =(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + " WHERE fc_id=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, name);
		stm.setString(2, brief);
		stm.setString(3, secu_kind);
		stm.setString(4, ip_addr);
		stm.setString(5, contractor);
		stm.setString(6, addr);
		stm.setString(7, bank_acc_no);
		stm.setString(8, org_id);
		stm.setString(9, min_sum_of_tran);
		stm.setString(10, max_sum_of_tran);
		stm.setString(11, start_date);
		stm.setString(12, end_date);
		stm.setString(13, modify_date);
		stm.setString(14, modify_time);
		stm.setString(15, sign_flag);
		stm.setString(16, use_flag);
		stm.setString(17, fc_id);

		return stm;
	}
}
