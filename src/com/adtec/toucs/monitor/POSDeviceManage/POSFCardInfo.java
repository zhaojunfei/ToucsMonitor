package com.adtec.toucs.monitor.POSDeviceManage;


import java.sql.*;
import javax.servlet.http.*;
import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title:POS设备基本配置信息类 </p>
 * <p>Description: 封装POS设备配置信息</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSFCardInfo {
	
	//POS编号
	String pos_no;	
	//商户编号
	String mer_id;	
	//默认jcb商户号
	String mer_id_jcb;
	//默认dinner商户号
	String mer_id_dinner;
	//默认ae商户号
	String mer_id_ae;
	//返回率Visa
	String rate_visa2;	
	//返回率Master
	String rate_master2;
	//返回率Jcb
	String rate_jcb2;
	//返回率Dinner
	String rate_dinner2;
	//返回率ae
	String rate_ae2;
	//开通标志
	String open_flag;
	//默认划款帐户
	String acct_no;
	//默认划款帐户开户行
	String acct_bankno;
	//默认划款帐户名称
	String acct_name;
	//划款规则名
	String acct_func;
	//终端状态
	String state;
	//标志位
	String flag;
	//终端读写能力
	String chip_capability;

	public String getAcct_no() {
		return acct_no;
	}

	public String getMer_id() {
		return mer_id;
	}

	public String getState() {
		return state;
	}

	public String getAcct_bankno() {
		return acct_bankno;
	}	
	
	public String getOpen_flag() {
		return open_flag;
	}

	public String getMer_id_dinner() {
		return mer_id_dinner;
	}

	public String getAcct_func() {
		return acct_func;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public String getMer_id_ae() {
		return mer_id_ae;
	}

	public String getMer_id_jcb() {
		return mer_id_jcb;
	}

	public String getFlag() {
		return flag;
	}

	public void setPos_no(String pos_no) {
		this.pos_no = pos_no;
	}

	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setChip_capability(String chip_capability) {	
		this.chip_capability = chip_capability;
	}

	public void setAcct_bankno(String acct_bankno) {
		this.acct_bankno = acct_bankno;
	}

	public void setOpen_flag(String open_flag) {
		this.open_flag = open_flag;
	}

	public void setMer_id_dinner(String mer_id_dinner) {
		this.mer_id_dinner = mer_id_dinner;
	}

	public void setAcct_func(String acct_func) {
		this.acct_func = acct_func;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public void setMer_id_ae(String mer_id_ae) {
		this.mer_id_ae = mer_id_ae;
	}

	public void setMer_id_jcb(String mer_id_jcb) {
		this.mer_id_jcb = mer_id_jcb;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setRate_ae2(String rate_ae2) {
		this.rate_ae2 = rate_ae2;
	}

	public void setRate_jcb2(String rate_jcb2) {
		this.rate_jcb2 = rate_jcb2;
	}
	
	public void setRate_master2(String rate_master2) {
		this.rate_master2 = rate_master2;
	}

	public void setRate_dinner2(String rate_dinner2) {
		this.rate_dinner2 = rate_dinner2;
	}

	public void setRate_visa2(String rate_visa2) {
		this.rate_visa2 = rate_visa2;
	}

	public String getPos_no() {
		return pos_no;
	}

	public String getRate_ae2() {
		return rate_ae2;
	}

	public String getRate_jcb2() {
		return rate_jcb2;
	}

	public String getRate_master2() {
		return rate_master2;
	}

	public String getRate_dinner2() {
		return rate_dinner2;
	}

	public String getRate_visa2() {
		return rate_visa2;
	}

	public String getChip_capability() {
		return chip_capability;
	}

	public POSFCardInfo() {
	}

	  /**
	   * 从Http请求中取POS基本信息
	   * @param request Http请求
	   */
	public void fetchData(HttpServletRequest request) {
		setPos_no(request.getParameter("pos_no"));
		setMer_id(request.getParameter("mer_id"));
		setRate_visa2(request.getParameter("rate_visa2"));
		setRate_master2(request.getParameter("rate_master2"));
		setRate_jcb2(request.getParameter("rate_jcb2"));
		setRate_dinner2(request.getParameter("rate_dinner2"));
		setRate_ae2(request.getParameter("rate_ae2"));
		setMer_id_jcb(request.getParameter("mer_id_jcb"));
		setMer_id_dinner(request.getParameter("mer_id_dinner"));
		setMer_id_ae(request.getParameter("mer_id_ae"));
		setOpen_flag(request.getParameter("open_flag"));
		setAcct_no(request.getParameter("acct_no"));
		setAcct_bankno(request.getParameter("acct_bankno"));
		setAcct_name(request.getParameter("acct_name"));
		setAcct_func(request.getParameter("acct_func"));
		setState(request.getParameter("state"));
		setFlag(request.getParameter("flag"));
		setChip_capability(request.getParameter("chip_capability"));
	}

	  /**
	   * 从查询结果中取POS基本信息
	   * @param rst 查询结果集
	   * @throws SQLException
	   */
	public void fetchData(ResultSet rst) throws SQLException {
		setPos_no(toucsString.unConvert(rst.getString("pos_no")));
		setMer_id(toucsString.unConvert(rst.getString("mer_id")));
		setRate_visa2(toucsString.unConvert(rst.getString("rate_visa2")));
		setRate_master2(toucsString.unConvert(rst.getString("rate_master2")));
		setRate_jcb2(toucsString.unConvert(rst.getString("rate_jcb2")));
		setRate_dinner2(toucsString.unConvert(rst.getString("rate_dinner2")));
		setRate_ae2(toucsString.unConvert(rst.getString("rate_ae2")));
		setMer_id_jcb(toucsString.unConvert(rst.getString("mer_id_jcb")));
		setMer_id_dinner(toucsString.unConvert(rst.getString("mer_id_dinner")));
		setMer_id_ae(toucsString.unConvert(rst.getString("mer_id_ae")));
		setOpen_flag(toucsString.unConvert(rst.getString("open_flag")));
		setAcct_no(toucsString.unConvert(rst.getString("acct_no")));
		setAcct_bankno(toucsString.unConvert(rst.getString("acct_bankno")));	
		setAcct_name(toucsString.unConvert(rst.getString("acct_name")));
		setAcct_func(toucsString.unConvert(rst.getString("acct_func")));
		setState(toucsString.unConvert(rst.getString("state")));
		setFlag(toucsString.unConvert(rst.getString("flag")));
		setChip_capability(toucsString.unConvert(rst.getString("chip_capability")));
	}

	  /**
	   * 创建用于添加的动态SQL语句
	   * @param conn 数据库连接对象
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		//modify by liyp 20031011 add last_date , last_serial
		String sql = "INSERT INTO pos_list (pos_no,mer_id,mer_id_jcb,mer_id_dinner,mer_id_ae,rate_visa2,rate_master2,rate_jcb2,rate_dinner2,rate_ae2,open_flag,acct_no,acct_bankno,acct_name,acct_func,state,chip_capability) VALUES" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		System.out.println("INSERT POSINFO:" + sql);
		//取得POSP编号
		stm.setString(1, pos_no);
		stm.setString(2, mer_id);
		stm.setString(3, mer_id_jcb);
		stm.setString(4, mer_id_dinner);
		stm.setString(5, mer_id_ae);
		stm.setString(6, rate_visa2);
		stm.setString(7, rate_master2);
		stm.setString(8, rate_jcb2);
		stm.setString(9, rate_dinner2);
		stm.setString(10, rate_ae2);
		stm.setString(11, open_flag);
		stm.setString(12, acct_no);
		stm.setString(13, acct_bankno);
		stm.setString(14, acct_name);
		stm.setString(15, acct_func);	
		stm.setString(16, state);
		stm.setString(17, chip_capability);

		return stm;
	}

	  /**
	   * 创建用于修改的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeUpdateStm(Connection conn, String mer_id, String pos_no) throws SQLException, MonitorException {
		String sql = "UPDATE pos_list SET "
				   + "(mer_id,mer_id_jcb,mer_id_dinner,mer_id_ae,rate_visa2,rate_master2,rate_jcb2,rate_dinner2,rate_ae2,open_flag,acct_no,acct_bankno,acct_name,acct_func,state,chip_capability) ="
				   + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) WHERE mer_id = ? AND pos_no = ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, mer_id);
		stm.setString(2, mer_id_jcb);
		stm.setString(3, mer_id_dinner);
		stm.setString(4, mer_id_ae);
		stm.setString(5, rate_visa2);
		stm.setString(6, rate_master2);
		stm.setString(7, rate_jcb2);
		stm.setString(8, rate_dinner2);
		stm.setString(9, rate_ae2);
		stm.setString(10, open_flag);
		stm.setString(11, acct_no);	
		stm.setString(12, acct_bankno);
		stm.setString(13, acct_name);
		stm.setString(14, acct_func);
		stm.setString(15, state);
		stm.setString(16, chip_capability);
		stm.setString(17, mer_id);
		stm.setString(18, pos_no);
		
		return stm;
	}

	  /**
	   * 创建用于根据商户查询的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeQueryByMctStm(Connection conn, String key) throws SQLException {
		String sql = "SELECT * FROM pos_list WHERE mer_id=?";
		System.out.println("POSINFO:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, key);
		System.out.println("POSINFO QUERY:" + key);
		return stm;
	}

	  /**
	   * 创建用于删除的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeDeleteStm(Connection conn, String mer_id, String pos_no) throws SQLException {
		//17个字段，key指定要删除的POS的编号
		String sql = "DELETE FROM pos_list WHERE mer_id = ? AND pos_no = ?";
		System.out.println("DELETE SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//删除的主键
		stm.setString(1, mer_id);
		stm.setString(2, pos_no);
		return stm;
	}
}
