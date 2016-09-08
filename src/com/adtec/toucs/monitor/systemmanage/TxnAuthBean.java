package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;


public class TxnAuthBean {
	//渠道名称，暂时仅使用atm
	private String sys_id;
	//卡种类
	private String card_class;
	//卡类型
	private String card_type;
	//权限串16进制(16位)
	private String txn_auth;
	//权限串2进制(64位)
	private String txn_autha;
	//交易列表
	private Vector txn_list;

	public TxnAuthBean() {
		txn_list = new Vector();
	}

	public TxnAuthBean(String sysId) {
		sys_id = sysId;
	}

	public int insert(SqlAccess sq) throws SQLException{
		String sql = "INSERT INTO kernel_txn_auth VALUES('"+sys_id+"','"+card_class+"','"+card_type+"','"+txn_auth+"','')";
		return sq.queryupdate(sql);
  }

	public int update(SqlAccess sq) throws SQLException{
		String sql="UPDATE kernel_txn_auth SET txn_auth = ?" + "WHERE sys_id = ? AND card_class = ? AND card_type = ?";
		PreparedStatement stm=sq.conn.prepareStatement(sql);
		stm.setString(1, txn_auth);
		stm.setString(2, sys_id);
		stm.setString(3, card_class);
		stm.setString(4, card_type);

		return stm.executeUpdate();
	}

	public PreparedStatement makeDeleteStm(Connection conn,String key) throws SQLException{
		String sql = "delete from pos_info where card_class = '" + card_class +"' and card_type = '" + card_type + "' and channel = '" + sys_id + "';";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("DeLeTe SQL:" + sql);

		return stm;
	}

	public void fetchData(HttpServletRequest request) throws MonitorException {
		sys_id = request.getParameter("SysId");
		card_class = request.getParameter("CardClass");
		card_type = request.getParameter("CardType");

		String[] s = (String[])request.getParameterValues("TxnAuth");
		int i, x = 0;
		char tt[] = new char[64];

		for (i=0; i<64; i++) {
			tt[i] = '0';
		}
		if (s != null) {
			for (i = 0; i < s.length; i++) {
				tt[Integer.parseInt(s[i])] = '1';
			}
		}

		x = 0;
		txn_auth = "";
		for (i=0; i<16; i++) {
			x = (tt[i*4] - '0') * 8 + (tt[i*4+1] - '0') * 4 + (tt[i*4+2] - '0') * 2 + tt[i*4+3] - '0';
			txn_auth += Integer.toHexString(x);
		}
		txn_auth = txn_auth.toUpperCase();
		Debug.println("************txnMask=" + txn_auth + ":" + card_class + ":" + card_type);
	}


	public void fetchData(ResultSet rst) throws SQLException{
		sys_id = (toucsString.unConvert(rst.getString("sys_id")));
		card_class = rst.getString("card_class");
		card_type = rst.getString("card_type");
		txn_auth = rst.getString("txn_auth");

		String temp;
		int x;
		txn_autha = "";
		for (int i=0; i<4; i++) {
			temp = txn_auth.substring(i*4, i*4+4);
			x = Integer.parseInt(temp, 16);
			temp = Integer.toBinaryString(x);

			while (temp.length() < 16){
				temp = "0" + temp;
			}

			txn_autha += temp;
		}
		Debug.println("RRR:" + txn_autha);
	}

	public void qryAllTxn() throws MonitorException{
		SqlAccess sq = SqlAccess.createConn();
		int i = 0;
		if (!txn_list.isEmpty()) {
			txn_list.clear();
		}
		try {
			ResultSet rst = sq.queryselect(
			"SELECT * FROM kernel_txn_code WHERE sys_id='atm' ORDER BY location");
			while (rst != null && rst.next()) {
				TxnCodeBean txnCode = new TxnCodeBean();
				txnCode.fetchData(rst);
				txn_list.add(txnCode);
				Debug.println("AA" + i++);
			}
			rst.close();
			return;
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
	}

	public void setSysId(String SysId) {
		sys_id = SysId;
	}
	public String getSysId() {
		return sys_id;
	}

	public String getCardClass() {
		return card_class;
	}

	public String getCardClassName() {
		SqlAccess sq = new SqlAccess();
		String s = "";

		String sql = "Select code_desc from kernel_code where code_type = '0280' and sys_code = '" + card_class.trim() + "'";
		try {
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				s = rst.getString("code_desc").trim();
			}
			rst.close();
			return s;
		} catch (SQLException e) {
			Debug.println(e.toString());
		} finally {
			sq.close();
		}
		return "";
	}

	public String getCardType() {
		return card_type;
	}

	public String getCardTypeName() {
		SqlAccess sq = new SqlAccess();
		String s = "";

		String sql = "Select code_desc from kernel_code where code_type = '0290' and sys_code = '" + card_type.trim() + "'";
		try {
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				s = rst.getString("code_desc").trim();
			}
			rst.close();
			return s;
		} catch (SQLException e) {
			Debug.println(e.toString());
		} finally {
			sq.close();
		}
		return "";
	}

	public String getTxnAuth() {
		return txn_autha;
	}

	public Vector getTxnList() {
		return txn_list;
	}
}
