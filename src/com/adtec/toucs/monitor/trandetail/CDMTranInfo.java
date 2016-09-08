package com.adtec.toucs.monitor.trandetail;

/**
 * <p>Title: cdm_txn_log info</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lihl
 * @version 1.0
 */

public class CDMTranInfo {

	private String cdm_code = "";
	private String cdm_cycle = "";
	private String cdm_serial = "";
	private String trans_date = "";
	private String trans_time = "";
	private String host_response = "";
	private String trans_response = "";
	private String trans_card_no = "";
	private String trans_amount = "";
	private String trans_code = "";
	private String trans_card_type = "";
	private String snd_card_no = "";

	public CDMTranInfo() {
	}

	public void setCdmCode(String cdmcode){
		this.cdm_code = cdmcode;
	}

	public void setCdmSerial(String cdmSerial){
		this.cdm_serial = cdmSerial;
	}

	public void setCdmCycle(String cdmCycle){
		this.cdm_cycle = cdmCycle;
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

	public String getCdmCode(){
		return this.cdm_code;
	}

	public String getCdmCycle(){
		return this.cdm_cycle;
	}

	public String getCdmSerial(){
		return this.cdm_serial;
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