package com.adtec.toucs.monitor.systemmanage;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class DccCtrlInfo {

	public DccCtrlInfo() {
	}

	// 渠道代号
	private String channel_id = "";
	// 业务代码
	private String service = "";
	// 交易限额
	private String trans_amt = "";
	// 累计限额
	private String total_amt = "";
	// 备注1
	private String memo1 = "";

	
	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getMemo1() {
		return memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTotal_amt() {
		return total_amt;
	}

	public void setTotal_amt(String total_amt) {
		this.total_amt = total_amt;
	}

	public String getTrans_amt() {
		return trans_amt;
	}

	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}

	/**
	 * 从Http请求中取商户信息
	 * @param request Http请求
	 */
	public void fetchData(HttpServletRequest request) {
		setChannel_id(request.getParameter("channel_id"));
		setService(request.getParameter("service"));
		setTrans_amt(request.getParameter("trans_amt"));
		setTotal_amt(request.getParameter("total_amt"));
		setMemo1(request.getParameter("memo1"));
	}

	/**
	 * 从查询结果中取商户信息
	 * @param rst查询结果集
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setChannel_id(toucsString.unConvert(rst.getString("channel_id")));
		setService(toucsString.unConvert(rst.getString("service")));
		setTrans_amt(toucsString.unConvert(rst.getString("trans_amt")));
		setTotal_amt(toucsString.unConvert(rst.getString("total_amt")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
	}

	/**
	 * 创建用于添加的动态SQL语句
	 * @param conn数据库连接对象
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO dcc_ctrl VALUES(?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT DCC_CTRL:" + sql);
		stm.setString(1, channel_id);
		stm.setString(2, service);
		stm.setString(3, trans_amt);
		stm.setString(4, total_amt);
		stm.setString(5, memo1);
		return stm;
	}

	/**
	 * 创建用于删除的动态SQL语句
	 * @param conn数据库连接对象
	 * @param strKey1 渠道代号
	 * @param strKey2 业务种类
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey1,String strKey2) throws SQLException {
		String sql = "DELETE FROM dcc_ctrl WHERE channel_id = ? AND service = ?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, strKey1);
		stm.setString(2, strKey2);
		return stm;
	}

	/**
	 * 创建用于修改的动态SQL语句
	 * @param conn数据库连接对象
	 * @param key设备编号（关键字）未使用参数
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE dcc_ctrl SET " + "(trans_amt,total_amt,memo1)=(?,?,?)" + " WHERE channel_id=? AND service=?";
		Debug.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, trans_amt);
		stm.setString(2, total_amt);
		stm.setString(3, memo1);
		stm.setString(4, channel_id);
		stm.setString(5, service);
		return stm;
	}

}
