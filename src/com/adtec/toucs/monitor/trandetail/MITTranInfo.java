package com.adtec.toucs.monitor.trandetail;

/**
 * <p>Title: mit_txn_log info</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lihl
 * @version 1.0
 */

public class MITTranInfo {

	private String mit_code = "";
	private String mit_cycle = "";
	private String mit_serial = "";
	private String trans_date = "";
	private String trans_time = "";
	private String host_response = "";
	private String trans_response = "";
	private String trans_card_no = "";
	private String trans_amount = "";
	private String trans_code = "";
	private String trans_card_type = "";
	private String snd_card_no = "";

	public MITTranInfo() {
	}

	public void setMitCode(String mitcode){
		this.mit_code = mitcode;
	}

	public void setMitSerial(String mitSerial){
		this.mit_serial = mitSerial;
	}

	public void setMitCycle(String mitCycle){
		this.mit_cycle = mitCycle;
	}

	public void setTransCode(String transCode){
		this.trans_code = transCode;
	}

	public void setTransCardNo(String TransCardNo){
		this.trans_card_no = TransCardNo;
	}

	public void setTransAmount(String TransAmount){
		this.trans_amount = TransAmount;
	}	

	public void setHostResponse(String HostResponse){
		this.host_response = HostResponse;
	}

	public void setTransRespons(String TransResponse){
		this.trans_response = TransResponse;
	}

	public void setTransDate(String TransDate){
		this.trans_date = TransDate;
	}

	public void setTransTime(String TransTime){
		this.trans_time = TransTime;
	}

	public void setTransCardType(String TransCardType){
		this.trans_card_type = TransCardType;
	}

	public void setSndCardNo(String sndCardno){
		this.snd_card_no = sndCardno;
	}

	public String getMitCode(){
		return this.mit_code;
	}

	public String getMitCycle(){
		return this.mit_cycle;
	}

	public String getMitSerial(){
		return this.mit_serial;
	}

	public String getTransCode(){
		return this.trans_code;
	}

	public String getTransCardNo(){
		return this.trans_card_no;
	}

	public String getTransAmount(){
		return this.trans_amount;
	}

	public String getHostResponse(){
		return this.host_response;
	}

	public String getTransResponse(){
		return this.trans_response;
	}	

	public String getTransDate(){
		return this.trans_date;
	}

	public String getTransTime(){
		return this.trans_time;
	}

	public String getTransCardType(){
		return this.trans_card_type;
	}

	public String getSndCardNo(){
		return this.snd_card_no;
	}
}