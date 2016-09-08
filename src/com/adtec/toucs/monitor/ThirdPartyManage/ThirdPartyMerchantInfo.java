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
 * @version 1.0
 */

public class ThirdPartyMerchantInfo {

	public ThirdPartyMerchantInfo() {
	}

	// �̻���
	private String merchant_id = "";
	//�̻�����
	private String merchant_code = "";
	// �̻�����
	private String merchant_name = "";
	// �̻�����
	private String merchant_type = "";
	// ��������
	private String branch_id = "";
	// ҵ������//�շ�����
	private String fee_kind = "";
	// ҵ�����
	private String agent_code = "";
	// IP��ַ
	private String ip_addr = "";
	// �˿ں�
	private String port = "";
	// �����ʺ�
	private String bank_acct = "";
	// �����ʺ�1
	private String bank_acct1 = "";
	// ��ȫ����
	private String secu_kind = "";
	// ����Կ
	private String master_key = "";
	//��Կ���
	private String cur_keyid;
	// PIN KEY
	private String pin_key = "";
	// MAC KEY
	private String mac_key = "";
	// �̻�״̬
	private String merchant_stat = "";
	// ��ע1
	private String memo1 = "";
	// ��ע2
	private String memo2 = "";
	// ��ע3
	private String memo3 = "";
	//��ʽ˵��
	private String typeDes = "";
   
	
	// �̻������Զ�д
	public void setMerchantid(String Merchantid) {
		merchant_id = Merchantid;
	}

	public String getMerchantid() {
		return merchant_id;
	}
	//�̻��������Զ�д
	public String getMerchant_code() {
		return merchant_code;
	}

	public void setMerchant_code(String merchantCode) {
		merchant_code = merchantCode;
	}

	// �̻��������Զ�д
	public void setMerchantname(String Merchantname) {
		merchant_name = Merchantname;
	}

	public String getMerchantname() {
		return merchant_name;
	}

	// �̻��������Զ�д
	public void setMerchanttype(String Merchanttype) {
		merchant_type = Merchanttype;
	}

	public String getMerchanttype() {
		return merchant_type;
	}

	// �����������Զ�д
	public void setBranchid(String Branchid) {
		branch_id = Branchid;
	}

	public String getBranchid() {
		return branch_id;
	}

	// ҵ���������Զ�д
	public void setFeekind(String Feekind) {
		fee_kind = Feekind;
	}

	public String getFeekind() {
		return fee_kind;
	}

	// ҵ��������Զ�д
	public void setAgentcode(String Agentcode) {
		agent_code = Agentcode;
	}

	public String getAgentcode() {
		return agent_code;
	}

	// IP��ַ���Զ�д
	public void setIpaddr(String Ipaddr) {
		ip_addr = Ipaddr;
	}

	public String getIpaddr() {
		return ip_addr;
	}

	// �˿ں����Զ�д
	public void setPort(String Port) {
		port = Port;
	}

	public String getPort() {
		return port;
	}

	// �����ʺ����Զ�д
	public void setBankacct(String Bankacct) {
		bank_acct = Bankacct;
	}

	public String getBankacct() {
		return bank_acct;
	}

	// �����ʺ�1���Զ�д
	public void setBankacct1(String Bankacct1) {
		bank_acct1 = Bankacct1;
	}

	public String getBankacct1() {
		return bank_acct1;
	}

	// ��ȫ�������Զ�д
	public void setSecukind(String Secukind) {
		secu_kind = Secukind;
	}

	public String getSecukind() {
		return secu_kind;
	}

	// ����Կ���Զ�д
	public void setMasterkey(String Masterkey) {
		master_key = Masterkey;
	}

	public String getMasterkey() {
		return master_key;
	}
  
	//��Կ������Զ�д
	public String getCur_keyid() {
		return cur_keyid;
	}

	public void setCur_keyid(String curKeyid) {
		cur_keyid = curKeyid;
	}

	// PINKEY���Զ�д
	public void setPinkey(String Pinkey) {
		pin_key = Pinkey;
	}

	public String getPinkey() {
		return pin_key;
	}

	// MACKEY���Զ�д
	public void setMackey(String Mackey) {
		mac_key = Mackey;
	}

	public String getMackey() {
		return mac_key;
	}

	// �̻�״̬���Զ�д
	public void setMerchantstat(String Merchantstat) {
		merchant_stat = Merchantstat;
	}

	public String getMerchantstat() {
		return merchant_stat;
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
	
	//��ʽ˵��
	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}


  /**
   * ��Http������ȡ�̻���Ϣ
   * @param request Http����
   */
	public void fetchData(HttpServletRequest request) {
		setMerchantid(request.getParameter("merchant_id"));
		setMerchant_code(request.getParameter("merchant_code"));
		setMerchantname(request.getParameter("merchant_name"));
		setMerchanttype(request.getParameter("merchant_type"));
		setBranchid(request.getParameter("branch_id"));
		setFeekind(request.getParameter("fee_kind"));
		setAgentcode(request.getParameter("agent_code"));
		setIpaddr(request.getParameter("ip_addr"));
		setPort(request.getParameter("port"));
		setBankacct(request.getParameter("bank_acct"));
		setBankacct1(request.getParameter("bank_acct1"));
		setSecukind(request.getParameter("secu_kind"));
		setMasterkey(request.getParameter("master_key"));
		setCur_keyid(request.getParameter("cur_keyid"));
		setMerchantstat(request.getParameter("merchant_stat"));
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
		setMerchant_code(toucsString.unConvert(rst.getString("merchant_code")));
		setMerchantname(toucsString.unConvert(rst.getString("merchant_name")));
		setMerchanttype(toucsString.unConvert(rst.getString("merchant_type")));
		setBranchid(toucsString.unConvert(rst.getString("branch_id")));
		setFeekind(toucsString.unConvert(rst.getString("fee_kind")));
		setAgentcode(toucsString.unConvert(rst.getString("agent_code")));
		setIpaddr(toucsString.unConvert(rst.getString("ip_addr")));
		setPort(toucsString.unConvert(rst.getString("port")));
		setBankacct(toucsString.unConvert(rst.getString("bank_acct")));
		setBankacct1(toucsString.unConvert(rst.getString("bank_acct1")));
		setSecukind(toucsString.unConvert(rst.getString("secu_kind")));
		setMasterkey(toucsString.unConvert(rst.getString("master_key")));
		setCur_keyid(toucsString.unConvert(rst.getString("cur_keyid")));
		setMerchantstat(toucsString.unConvert(rst.getString("merchant_stat")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
		String type = toucsString.unConvert(rst.getString("memo2"));
		String typeDes = null;
		if("QPAY".equals(type)){
			typeDes="��������|���״���|������ˮ��|���п���|���׽��||�̻���|�ն˺�||���ж�����";
		}else if ("NPS".equals(type)){
			typeDes="��������|����ʱ��|���״���|����|���׽��|������|���ս��|������ˮ��|ԭ������ˮ��|POS�ն˺�|�̻���|������";
		}else if ("ZFB".equals(type)){
			typeDes="��������|���״���|������ˮ��|���п���|���׽��|��Ȩ��(��)|�̻���|�ն˺�|ԭ���״���(����)|�������|�ο���(����)|";
		}
		
		setTypeDes(typeDes);
	}

  /**
   * ����������ӵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO t_merchant_info VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT T_MERCHANT_info:" + sql);
		stm.setString(1, merchant_id);
		stm.setString(2, merchant_code);
		stm.setString(3, merchant_name);
		stm.setString(4, merchant_type);
		stm.setString(5, branch_id);
		stm.setString(6, "");
		stm.setString(7, agent_code);
		stm.setString(8, "");
		stm.setString(9, "");
		stm.setString(10, bank_acct);
		stm.setString(11, bank_acct1);
		stm.setString(12, secu_kind);
		stm.setString(13, "");
		stm.setString(14, "00");
		stm.setString(15, "1");
		stm.setString(16, memo1);
		stm.setString(17, memo2);
		stm.setString(18, memo3);
		return stm;
  }

  /**
   * ��������ɾ���̻���Ϣ�Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param strKey �̻���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey) throws SQLException {
		String sql = "DELETE FROM t_merchant_info WHERE merchant_id = ?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey);
		return stm;
	}
  
  /**
   * ��������ɾ���̻���Կ�Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param strKey �̻���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeDeleteStm1(Connection conn, String strKey) throws SQLException {
		String sql = "DELETE FROM t_merchant_key WHERE merchant_id = ?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey);
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
		String sql = "UPDATE t_merchant_info SET " + "(merchant_code,merchant_name,merchant_type,branch_id,fee_kind,agent_code,"
			       + "ip_addr,port,bank_acct,bank_acct1,secu_kind,memo1,memo2,memo3)"
			       + " =(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + " WHERE merchant_id=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, merchant_code);
		stm.setString(2, merchant_name);
		stm.setString(3, merchant_type);
		stm.setString(4, branch_id);
		stm.setString(5, "");
		stm.setString(6, agent_code);
		stm.setString(7, "");
		stm.setString(8, "");
		stm.setString(9, bank_acct);
		stm.setString(10, bank_acct1);
		stm.setString(11, secu_kind);
		stm.setString(12, memo1);
		stm.setString(13, memo2);
		stm.setString(14, memo3);
		stm.setString(15, merchant_id);
		return stm;
	}

  /**
   * ����������������ͣ��״̬�ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key δʹ�ò���
   * @param bStat ���ñ�ʶ
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeStatStm(Connection conn, String key,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE t_merchant_info SET " + "merchant_stat='0'" + " WHERE merchant_id=?";
		} else {
			sql = "UPDATE t_merchant_info SET " + "merchant_stat='1'" + " WHERE merchant_id=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, merchant_id);
		return stm;
	}

}
