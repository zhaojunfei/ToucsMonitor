package com.adtec.toucs.monitor.systemmanage;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class DccCtrlInfo {

	public DccCtrlInfo() {
	}

	// ��������
	private String channel_id = "";
	// ҵ�����
	private String service = "";
	// �����޶�
	private String trans_amt = "";
	// �ۼ��޶�
	private String total_amt = "";
	// ��ע1
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
	 * ��Http������ȡ�̻���Ϣ
	 * @param request Http����
	 */
	public void fetchData(HttpServletRequest request) {
		setChannel_id(request.getParameter("channel_id"));
		setService(request.getParameter("service"));
		setTrans_amt(request.getParameter("trans_amt"));
		setTotal_amt(request.getParameter("total_amt"));
		setMemo1(request.getParameter("memo1"));
	}

	/**
	 * �Ӳ�ѯ�����ȡ�̻���Ϣ
	 * @param rst��ѯ�����
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
	 * ����������ӵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @return ��̬SQL������
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
	 * ��������ɾ���Ķ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param strKey1 ��������
	 * @param strKey2 ҵ������
	 * @return ��̬SQL������
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
	 * ���������޸ĵĶ�̬SQL���
	 * @param conn���ݿ����Ӷ���
	 * @param key�豸��ţ��ؼ��֣�δʹ�ò���
	 * @return ��̬SQL������
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
