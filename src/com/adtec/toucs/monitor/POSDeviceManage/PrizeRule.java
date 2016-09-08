package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import com.adtec.toucs.monitor.common.*;

public class PrizeRule {
	private String channel_code;
	private String bit_cardtype;
	private String bit_cardclass;
	private String begin_date;
	private String end_date;
	private String begin_time;
	private String end_time;
	private String businessman_flag;
	private String prizelevel_flag;
	private float lowest_amt;
	private int businessman_num;
	
	public String getBegin_time() {
		return begin_time;
	}
	
	public String getBusinessman_flag() {
		return businessman_flag;
	}

	public String getEnd_date() {
		return end_date;
	}

	public float getLowest_amt() {
		return lowest_amt;
	}

	public int getBusinessman_num() {
		return businessman_num;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public String getBit_cardtype() {
		return bit_cardtype;
	}

	public String getEnd_time() {
		return end_time;
	}

	public String getBit_cardclass() {
		return bit_cardclass;
	}

	public void setPrizelevel_flag(String prizelevel_flag) {
		this.prizelevel_flag = prizelevel_flag;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public void setBusinessman_flag(String businessman_flag) {
		this.businessman_flag = businessman_flag;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setLowest_amt(float lowest_amt) {
		this.lowest_amt = lowest_amt;
	}

	public void setBusinessman_num(int businessman_num) {
		this.businessman_num = businessman_num;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public void setBit_cardtype(String bit_cardtype) {
		this.bit_cardtype = bit_cardtype;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void setBit_cardclass(String bit_cardclass) {
		this.bit_cardclass = bit_cardclass;
	}

	public String getPrizelevel_flag() {
		return prizelevel_flag;
	}

	public PrizeRule() {
	}

	public void fetchData(ResultSet rst) throws SQLException {
		setChannel_code(toucsString.unConvert(rst.getString("channel_code")));
		setBit_cardtype(toucsString.unConvert(rst.getString("bit_cardtype")));
		setBit_cardclass(toucsString.unConvert(rst.getString("bit_cardclass")));
		setBegin_date(toucsString.unConvert(rst.getString("begin_date")));
		setEnd_date(toucsString.unConvert(rst.getString("end_date")));
		setBegin_time(toucsString.unConvert(rst.getString("begin_time")));
		setEnd_time(toucsString.unConvert(rst.getString("end_time")));
		setBusinessman_flag(toucsString.unConvert(rst.getString("businessman_flag")));
		setPrizelevel_flag(toucsString.unConvert(rst.getString("prizelevel_flag")));
		setLowest_amt(rst.getFloat("lowest_amt"));
		setBusinessman_num(rst.getInt("businessman_num"));
	}

}
