package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.Util;
import com.adtec.toucs.monitor.common.toucsString;

public class POSTransferAccountsInfo {

	private String paraName;
	private String paraDesc;
	private String paraVal;
	private String preDate;
	private String preTime;
	private String preVal;
	private String memo;
	private String dac;
	
	
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public String getParaDesc() {
		return paraDesc;
	}
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}
	public String getParaVal() {
		return paraVal;
	}
	public void setParaVal(String paraVal) {
		this.paraVal = paraVal;
	}
	public String getPreDate() {
		return preDate;
	}
	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}
	public String getPreTime() {
		return preTime;
	}
	public void setPreTime(String preTime) {
		this.preTime = preTime;
	}
	public String getPreVal() {
		return preVal;
	}
	public void setPreVal(String preVal) {
		this.preVal = preVal;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDac() {
		return dac;
	}
	public void setDac(String dac) {
		this.dac = dac;
	}
	

/**
 * �Ӳ�ѯ�����ȡ�̻���Ϣ
 * 
 * @param rst
 *            ��ѯ�����
 * @throws SQLException
 */
	public void fetchData(ResultSet rst) throws SQLException {
		setParaName(toucsString.unConvert(rst.getString("para_name")));
		setParaDesc(toucsString.unConvert(rst.getString("para_desc")));
		setParaVal(toucsString.unConvert(rst.getString("para_val")));
		setPreDate(toucsString.unConvert(rst.getString("pre_date")));
		setPreTime(toucsString.unConvert(rst.getString("pre_time")));
		setPreVal(toucsString.unConvert(rst.getString("pre_val")));
		setMemo(toucsString.unConvert(rst.getString("memo")));
		setDac(toucsString.unConvert(rst.getString("dac")));
	}
	
  /**
   * ��Http������ȡ�̻���Ϣ
   * @param request Http����
   */
	public void fetchData(HttpServletRequest request) {
		setParaName(request.getParameter("para_name"));
		setParaDesc(request.getParameter("para_desc"));
		setParaVal(request.getParameter("para_val"));
		setPreDate(request.getParameter("pre_date"));
		setPreTime(request.getParameter("pre_time"));
		setPreVal(request.getParameter("pre_val"));
		setMemo(request.getParameter("memo"));
		setDac(request.getParameter("dac"));
	}
  
  /**
   * ���������޸ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn) throws SQLException, MonitorException {
		String sql = "UPDATE t_para_config SET para_val =? , pre_val = ? ,pre_date =? , pre_time = ? WHERE para_name ='p_lc_amt'";
		Debug.println(sql);
		Util util = new Util();	
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, paraVal);	
		stm.setString(2, preVal);
		stm.setString(3, util.getCurrentDate());
		stm.setString(4, util.getCurrentTime());
		return stm;
	}
	   
  /**
   * ����������ӵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException
   * @throws UnsupportedEncodingException 
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException,MonitorException, UnsupportedEncodingException {
		Util util = new Util();
		String sql = "INSERT INTO t_para_config VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		String str = "ת�˽��";
		str = new String(str.trim().getBytes("GBK"),"ISO-8859-1");
		String desc = "para_val��ת�˽������,pre_val��ת�˽������";
		desc = new String(desc.trim().getBytes("GBK"),"ISO-8859-1");
		System.out.println("INSERT T_PARA_CONFIG:" + sql);
		
		stm.setString(1, "p_lc_amt");
		stm.setString(2, str);
		stm.setString(3, paraVal);
		stm.setString(4, util.getCurrentDate());
		stm.setString(5, util.getCurrentTime());
		stm.setString(6, preVal);
		stm.setString(7, desc);
		stm.setString(8, dac);
		
		System.out.println(sql);
		return stm;
	}
}
