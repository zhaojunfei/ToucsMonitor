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

public class ActMerchantInfo {

 	public ActMerchantInfo() {
 	}

 	// 商户号
 	private String merchant_id = "";
 	// 商户名称
 	private String merchant_name = "";
 	// 商户类型
 	private String merchant_type = "";
 	// 所属机构
 	private String branch_id = "";
 	// 业务种类
 	private String fee_kind = "";
 	// 业务代码
 	private String agent_code = "";
 	// IP地址
 	private String ip_addr = "";
 	// 端口号
 	private String port = "";
 	// 银行帐号
 	private String bank_acct = "";
 	// 银行帐号1
 	private String bank_acct1 = "";
 	// 安全种类
 	private String secu_kind = "";
 	// 主密钥
 	private String master_key = "";
 	// PIN KEY
 	private String pin_key = "";
 	// MAC KEY
 	private String mac_key = "";
 	// 商户状态
 	private String merchant_stat = "";
 	// 备注1
 	private String memo1 = "";
 	// 备注2
 	private String memo2 = "";
 	// 备注3
 	private String memo3 = "";


 	public void setMerchantid(String Merchantid) {
 		merchant_id = Merchantid;
 	}

 	public String getMerchantid() {
 		return merchant_id;
 	}

 	public void setMerchantname(String Merchantname) {
 		merchant_name = Merchantname;
 	}

 	public String getMerchantname() {
 		return merchant_name;
 	}

 	public void setMerchanttype(String Merchanttype) {
 		merchant_type = Merchanttype;
 	}

 	public String getMerchanttype() {
 		return merchant_type;
 	}

 	public void setBranchid(String Branchid) {
 		branch_id = Branchid;
 	}

 	public String getBranchid() {
 		return branch_id;
 	}

 	public void setFeekind(String Feekind) {
 		fee_kind = Feekind;
 	}

 	public String getFeekind() {
 		return fee_kind;
 	}

 	public void setAgentcode(String Agentcode) {
 		agent_code = Agentcode;
 	}

 	public String getAgentcode() {
 		return agent_code;
 	}

 	public void setIpaddr(String Ipaddr) {
 		ip_addr = Ipaddr;
 	}

 	public String getIpaddr() {
 		return ip_addr;
 	}

 	public void setPort(String Port) {
 		port = Port;
 	}

 	public String getPort() {
 		return port;
 	}

 	public void setBankacct(String Bankacct) {
 		bank_acct = Bankacct;
 	}

 	public String getBankacct() {
 		return bank_acct;
 	}

 	public void setBankacct1(String Bankacct1) {
 		bank_acct1 = Bankacct1;
 	}

 	public String getBankacct1() {
 		return bank_acct1;
 	}

 	public void setSecukind(String Secukind) {
 		secu_kind = Secukind;
 	}

 	public String getSecukind() {
 		return secu_kind;
 	}

 	public void setMasterkey(String Masterkey) {
 		master_key = Masterkey;
 	}

 	public String getMasterkey() {
 		return master_key;
 	}

 	public void setPinkey(String Pinkey) {
 		pin_key = Pinkey;
 	}	

 	public String getPinkey() {
 		return pin_key;
 	}

 	public void setMackey(String Mackey) {
 		mac_key = Mackey;
 	}

 	public String getMackey() {
 		return mac_key;
 	}

 	public void setMerchantstat(String Merchantstat) {
 		merchant_stat = Merchantstat;
 	}

 	public String getMerchantstat() {
 		return merchant_stat;
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
   * 从Http请求中取商户信息
   * @param request Http请求
   */
  	public void fetchData(HttpServletRequest request) {
  		setMerchantid(request.getParameter("merchant_id"));
  		setMerchantname(request.getParameter("merchant_name"));
  		setMerchanttype(request.getParameter("merchant_type"))	;
  		setBranchid(request.getParameter("branch_id"));
  		setFeekind(request.getParameter("fee_kind"));
  		setAgentcode(request.getParameter("agent_code"));
  		setIpaddr(request.getParameter("ip_addr"));
  		setPort(request.getParameter("port"));
  		setBankacct(request.getParameter("bank_acct"));
  		setBankacct1(request.getParameter("bank_acct1"));
  		setSecukind(request.getParameter("secu_kind"));
  		setMerchantstat(request.getParameter("merchant_stat"));
  		setMemo1(request.getParameter("memo1"));
  		setMemo2(request.getParameter("memo2"));
  		setMemo3(request.getParameter("memo3"));
  	}

  /**
   * 从查询结果中取商户信息
   * @param rst 查询结果集
   * @throws SQLException
   */
  	public void fetchData(ResultSet rst) throws SQLException {
  		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
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
  		setMerchantstat(toucsString.unConvert(rst.getString("merchant_stat")));
  		setMemo1(toucsString.unConvert(rst.getString("memo1")));
  		setMemo2(toucsString.unConvert(rst.getString("memo2")));
  		setMemo3(toucsString.unConvert(rst.getString("memo3")));
  	}

  /**
   * 创建用于添加的动态SQL语句
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException,MonitorException
   */
  	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
  		String sql = "INSERT INTO act_merchant VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  		PreparedStatement stm = conn.prepareStatement(sql);
  		System.out.println("INSERT ACT_MERCHANT:" + sql);

  		stm.setString(1, merchant_id);
  		stm.setString(2, merchant_name);
  		stm.setString(3, merchant_type);
  		stm.setString(4, branch_id);
  		stm.setString(5, fee_kind);
  		stm.setString(6, agent_code);
  		stm.setString(7, ip_addr);
    	stm.setString(8, port);
    	stm.setString(9, bank_acct);
    	stm.setString(10, bank_acct1);
    	stm.setString(11, secu_kind);
    	stm.setString(12, "0000000000000000");
    	stm.setString(13, "0000000000000000");
    	stm.setString(14, "0000000000000000");
    	stm.setString(15, "0");
    	stm.setString(16, memo1);
    	stm.setString(17, memo2);
    	stm.setString(18, memo3);
    	return stm;
  	}

  /**
   * 创建用于删除的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
  	public PreparedStatement makeDeleteStm(Connection conn, String strKey) throws SQLException {
  		String sql = "DELETE FROM act_merchant WHERE merchant_id = ?";
  		System.out.println("DELETE SQL:" + sql);
  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, strKey);
  		return stm;
  	}

  /**
   * 创建用于修改的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException,MonitorException
   */
  	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
  		String sql = "UPDATE act_merchant SET " + "(merchant_name,merchant_type,branch_id,fee_kind,agent_code,"
  				   + "ip_addr,port,bank_acct,bank_acct1,secu_kind,memo1,memo2,memo3)"
                   + " =(?,?,?,?,?,?,?,?,?,?,?,?,?)" + " WHERE merchant_id=?";
  		Debug.println(sql);

  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, merchant_name);
  		stm.setString(2, merchant_type);
  		stm.setString(3, branch_id);
  		stm.setString(4, fee_kind);
  		stm.setString(5, agent_code);
  		stm.setString(6, ip_addr);
  		stm.setString(7, port);
  		stm.setString(8, bank_acct);
  		stm.setString(9, bank_acct1);
  		stm.setString(10, secu_kind);
  		stm.setString(11, memo1);
  		stm.setString(12, memo2);
    	stm.setString(13, memo3);
    	stm.setString(14, merchant_id);
    	return stm;
  	}

  /**
   * 创建用于设置启用停用状态的的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
  	public PreparedStatement makeStatStm(Connection conn, String key, boolean bStat) throws SQLException {
  		String sql = "";
  		if (bStat) {
  			sql = "UPDATE act_merchant SET " + "merchant_stat='0'" + " WHERE merchant_id=?";
  		} else {
  			sql = "UPDATE act_merchant SET " + "merchant_stat='1'" + " WHERE merchant_id=?";
  		}	
  		PreparedStatement stm = conn.prepareStatement(sql);
  		stm.setString(1, merchant_id);		
  		return stm;
  	}

}