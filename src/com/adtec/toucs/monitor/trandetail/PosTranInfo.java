package com.adtec.toucs.monitor.trandetail;

/**
 * <p>Title: pos_txn_log info</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lihl
 * @version 1.0
 */

public class PosTranInfo {

	private String pos_code = "";//设备编号
	private String pos_serial = "";//设备交易序号
	private String merchant_id = "";//商户号
	private String trans_date = "";//交易日期
	private String trans_time = "";//交易时间
	private String host_response = "";//主机应答码(主机返回码)
	private String trans_response = "";//交易应答码
	private String trans_card_no = "";//交易卡号
	private String trans_amount = "";//交易金额
	private String trans_code = "";//平台交易识别码
	private String trans_card_type = "";//交易卡类型
	private String trans_flag = "";//交易标识
	private String dc_flag = "";//借贷标志
	private String operate_num = "";//本笔流水被操作次数
	private String sys_serial = "";//平台交易序号

	public PosTranInfo() {
	}

	public void setPosCode(String poscode){
		this.pos_code = poscode;
	}

	public void setPosSerial(String atmSerial){
		this.pos_serial = atmSerial;
	}

	public void setMerchantid(String Merchantid){
		this.merchant_id = Merchantid;
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

	public void setTransFlag(String Transflag){
		this.trans_flag = Transflag;
	}

	public void setDcFlag(String Dcflag){
		this.dc_flag = Dcflag;
	}

	public void setOperateNum(String OperateNum){
		this.operate_num = OperateNum;
	}

	public void setSysSerial(String sysSerial){
		this.sys_serial = sysSerial;
	}

	public String getPosCode(){
		return this.pos_code;
	}

	public String getMerchantid(){
		return this.merchant_id;
	}

	public String getPosSerial(){
		return this.pos_serial;
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

	public String getTransFlag(){
		return this.trans_flag;
	}	

	public String getDcFlag(){
		return this.dc_flag;
	}	

	public String getOperateNum(){
		return this.operate_num;
	}
  
	public String getSysSerial(){
		return this.sys_serial;
	}	
}