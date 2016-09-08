package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.Util;
import com.adtec.toucs.monitor.common.toucsString;

public class POSBCCompanyInfo {
	
	//公司编号
	private String company_id = "";
	//公司名称
	private String company_name = "";
	//清算机构
	private String org_id = "";
	//扣款对公账户
	private String deduct_acct= "";
	//内部账户
	private String in_acct = "";
	//存款对公账户
	private String deposit_acct = "";
	//交换号
	private String swop_no = "";
	//手续费账户
	private String poundage_acct = "";
	//费率
	private String rebate = "";
	//备用费率1
	private String rebate1 = "";
	//备用费率2
	private	String rebate2 = "";
	//安全处理处理
	private String secu_kind = "";
	//主密钥
	private String master_key = "";
	//pin_key
	private String pin_key = "";
	//mac_key
	private String mac_key = "";
	//IP地址
	private String ip_addr = "";
	//端口号
	private String port = "";
	//资金清算方式
	private String clear_type = "";
	//清算日期
	private String clear_date = "";
	//日期时间戳
	private String datetime = "";
	//公司状态
	private String company_stat = "";
	//备注1
	private String memo1 = "";
	//备注2
	private String memo2 = "";
	//备注3
	private String memo3 = "";
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String companyId) {
		company_id = companyId;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String companyName) {
		company_name = companyName;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String orgId) {
		org_id = orgId;
	}
	public String getDeduct_acct() {
		return deduct_acct;
	}
	public void setDeduct_acct(String deductAcct) {
		deduct_acct = deductAcct;
	}
	public String getIn_acct() {
		return in_acct;
	}
	public void setIn_acct(String inAcct) {
		in_acct = inAcct;
	}
	public String getDeposit_acct() {
		return deposit_acct;
	}
	public void setDeposit_acct(String depositAcct) {
		deposit_acct = depositAcct;
	}
	public String getSwop_no() {
		return swop_no;
	}
	public void setSwop_no(String swopNo) {
		swop_no = swopNo;
	}
	public String getPoundage_acct() {
		return poundage_acct;
	}
	public void setPoundage_acct(String poundageAcct) {
		poundage_acct = poundageAcct;
	}
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	
	public String getRebate1() {
		return rebate1;
	}
	public void setRebate1(String rebate1) {
		this.rebate1 = rebate1;
	}
	public String getRebate2() {
		return rebate2;
	}
	public void setRebate2(String rebate2) {
		this.rebate2 = rebate2;
	}
	public String getSecu_kind() {
		return secu_kind;
	}
	public void setSecu_kind(String secuKind) {
		secu_kind = secuKind;
	}
	public String getMaster_key() {
		return master_key;
	}
	public void setMaster_key(String masterKey) {
		master_key = masterKey;
	}
	public String getPin_key() {
		return pin_key;
	}
	public void setPin_key(String pinKey) {
		pin_key = pinKey;
	}
	public String getMac_key() {
		return mac_key;
	}
	public void setMac_key(String macKey) {
		mac_key = macKey;
	}
	public String getIp_addr() {
		return ip_addr;
	}
	public void setIp_addr(String ipAddr) {
		ip_addr = ipAddr;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getClear_type() {
		return clear_type;
	}
	public void setClear_type(String clearType) {
		clear_type = clearType;
	}
	public String getClear_date() {
		return clear_date;
	}
	public void setClear_date(String clearDate) {
		clear_date = clearDate;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getCompany_stat() {
		return company_stat;
	}
	public void setCompany_stat(String companyStat) {
		company_stat = companyStat;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public String getMemo3() {
		return memo3;
	}
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	
	/**
	 * 从Http请求中取商户信息
	 * @param request Http请求
	 */
	public void fetchData(HttpServletRequest request) {
		setCompany_id(request.getParameter("company_id"));
		setCompany_name(request.getParameter("company_name"));
		setOrg_id(request.getParameter("org_id"));
		setDeduct_acct(request.getParameter("deduct_acct"));
		setIn_acct(request.getParameter("in_acct"));
		setDeposit_acct(request.getParameter("deposit_acct"));
		setSwop_no(request.getParameter("swop_no"));
		setPoundage_acct(request.getParameter("poundage_acct"));
		setRebate(request.getParameter("rebate"));
		setRebate1(request.getParameter("rebate1"));
		setRebate2(request.getParameter("rebate2"));
		setSecu_kind(request.getParameter("secu_kind"));
		setMaster_key(request.getParameter("master_key"));
		setPin_key(request.getParameter("pin_key"));
		setMac_key(request.getParameter("mac_key"));
		setIp_addr(request.getParameter("ip_addr"));
		setPort(request.getParameter("port"));
		setClear_type(request.getParameter("clear_type"));
		setClear_date(request.getParameter("clear_date"));
		setDatetime(request.getParameter("datetime"));
		setCompany_stat(request.getParameter("company_stat"));
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
		setCompany_id(toucsString.unConvert(rst.getString("company_id")));
		setCompany_name(toucsString.unConvert(rst.getString("company_name")));
		setOrg_id(toucsString.unConvert(rst.getString("org_id")));
		setDeduct_acct(toucsString.unConvert(rst.getString("deduct_acct")));
		setIn_acct(toucsString.unConvert(rst.getString("in_acct")));
		setDeposit_acct(toucsString.unConvert(rst.getString("deposit_acct")));
		setSwop_no(toucsString.unConvert(rst.getString("swop_no")));
		setPoundage_acct(toucsString.unConvert(rst.getString("poundage_acct")));
		setRebate(toucsString.unConvert(rst.getString("rebate")));
		setRebate1(toucsString.unConvert(rst.getString("rebate1")));
		setRebate2(toucsString.unConvert(rst.getString("rebate2")));
		setSecu_kind(toucsString.unConvert(rst.getString("secu_kind")));
		setMaster_key(toucsString.unConvert(rst.getString("master_key")));
		setPin_key(toucsString.unConvert(rst.getString("pin_key")));
		setMac_key(toucsString.unConvert(rst.getString("mac_key")));
		setIp_addr(toucsString.unConvert(rst.getString("ip_addr")));
		setPort(toucsString.unConvert(rst.getString("port")));
		setClear_type(toucsString.unConvert(rst.getString("clear_type")));
		setClear_date(toucsString.unConvert(rst.getString("clear_date")));
		setDatetime(toucsString.unConvert(rst.getString("datetime")));
		setCompany_stat(toucsString.unConvert(rst.getString("company_stat")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
	}	
	
	 /**
	   * 创建用于添加的动态SQL语句
	   * @param conn 数据库连接对象
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
		public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
			Util util = new Util();
			String sql = "INSERT INTO pos_yfk_company VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			System.out.println("INSERT T_MERCHANT_info:" + sql);
			stm.setString(1,company_id);
			stm.setString(2,company_name);
			stm.setString(3,org_id);
			stm.setString(4,deduct_acct);
			stm.setString(5,in_acct);
			stm.setString(6,deposit_acct);
			stm.setString(7,swop_no);
			stm.setString(8,poundage_acct);
			stm.setString(9,rebate);
			stm.setString(10,rebate1);
			stm.setString(11,rebate2);
			stm.setString(12,"0");
			stm.setString(13,master_key);
			stm.setString(14,pin_key);
			stm.setString(15,mac_key);
			stm.setString(16,ip_addr);
			stm.setString(17,port);
			stm.setString(18,"0");
			stm.setString(19,"1");
			stm.setString(20,util.getCurrentDate()+util.getCurrentTime());
			stm.setString(21,"1");
			stm.setString(22,memo1);
			stm.setString(23,memo2);
			stm.setString(24,memo3);
			
			return stm;
	  }
		
	/**
	 * 创建用于删除预付卡卡表信息的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param company_id 商户号
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String company_id) throws SQLException {
		String sql = "DELETE FROM pos_yfk_company WHERE company_id = ?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, company_id);
		return stm;
	}		
	
	/**
	 * 创建用于修改的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param key 未使用参数
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		Util util =new Util();
		String sql = "UPDATE pos_yfk_company SET " + "(company_name,org_id,deduct_acct,in_acct,deposit_acct,swop_no,"
	       + "poundage_acct,rebate,rebate1,rebate2,ip_addr,port,clear_type,clear_date,company_stat,memo1,memo2,memo3)"
	       + " =(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + " WHERE company_id=?";
           Debug.println(sql);
           PreparedStatement stm = conn.prepareStatement(sql);
           Debug.println("["+company_name+"]["+org_id+"]["+memo1+"]["+memo2+"]["+memo3+"]"+"{"+key+"}");
           stm.setString(1, company_name);
           stm.setString(2,org_id);
           stm.setString(3,deduct_acct);
           stm.setString(4,in_acct);
           stm.setString(5,deposit_acct);
           stm.setString(6,swop_no);
           stm.setString(7,poundage_acct);
           stm.setString(8,rebate);
           stm.setString(9,rebate1);
           stm.setString(10,rebate2);
           stm.setString(11,ip_addr);
           stm.setString(12,port);
           stm.setString(13,clear_type);
           stm.setString(14,clear_date);
         //  stm.setString(19,util.getCurrentDate()+util.getCurrentTime());
           stm.setString(15,"1");
           stm.setString(16,memo1);
           stm.setString(17,memo2);
           stm.setString(18,memo3);
           stm.setString(19,company_id);
		
		return stm;
	}
	
	/**
	 * 创建用于设置启用停用状态的的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param bStat 启用标识
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeStatStm(Connection conn,boolean stat) throws SQLException {
		String sql = "";
		if (stat) {
			sql = "UPDATE pos_yfk_company SET " + "company_stat='0'" + " WHERE company_id=?";
		} else {
			sql = "UPDATE pos_yfk_company SET " + "company_stat='1'" + " WHERE company_id=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, company_id);
		return stm;
	}
}
