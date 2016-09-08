package com.adtec.toucs.monitor.trandetail;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TranInfo {

	private String atm_code = "";
	private String atm_serial = "";
	private String trans_code = "";
	private String trans_card_no = "";
	private String trans_amount = "";
	private String host_response = "";
	private String trans_response = "";
	private String trans_date = "";
	private String trans_time = "";
	private String snd_card_no = "";
	private String trans_card_type = "";

	public TranInfo() {
	}

	public void setAtmCode(String atmcode){
		this.atm_code = atmcode;
	}

	public void setAtmSerial(String atmSerial){
		this.atm_serial = atmSerial;
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

	public void setSndCardNo(String SndCardNo){
		this.snd_card_no = SndCardNo;
	}

	public void setTransCardType(String TransCardType){
		this.trans_card_type = TransCardType;
	}

	public String getAtmCode(){
		return this.atm_code;
	}

	public String getAtmSerial(){
		return this.atm_serial;
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
	
	public String getSndCardNo(){
		return this.snd_card_no;
	}	

	public String getTransCardType(){
		return this.trans_card_type;
	}
}