package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.toucsString;

public class PosCompanySubsidiary {
	
	public PosCompanySubsidiary (){		
	}
	
	private String company_id = "";      //公司编号
	private String start_date = "";      //起始日期
	private String end_date = "";        //终止日期
	private String month = "";           //月份(查询,统计)
	private int seq_no = 0;              //平台流水号
	private String pos_code = "";        //设备编号
	private String bill_no = "";         //票据号
	private String pos_batch = "";       //批号
	private String merchant_id = "";     //商户号
	private int sys_serial = 0;          //平台交易序号
	private String trans_card_no = "";   //交易卡号
	private String trans_code = "";      //交易码
	private float trans_amount = 0;     //交易金额
	private String trans_date = "";      //交易日期
	private String trans_time = "";      //交易时间
	private String trans_card_type = ""; //交易卡类型
	private String oper_num = "";        //柜员号
	private float trans_fee = 0;        //手续费
	private String acq_id = "";          //机构代码
	private String tran_flag = "";       //交易标志
	
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		if ( trans_code.equals("MC0010") ){
			this.trans_code = "消费";
		}else if ( trans_code.equals("MC2010") ){
			this.trans_code = "退货";
		}else{
			this.trans_code = "未知";
		}
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	
	public String getAcq_id() {
		return acq_id;
	}
	public void setAcq_id(String acq_id) {
		this.acq_id = acq_id;
	}
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getOper_num() {
		return oper_num;
	}
	public void setOper_num(String oper_num) {
		this.oper_num = oper_num;
	}
	public String getPos_batch() {
		return pos_batch;
	}
	public void setPos_batch(String pos_batch) {
		this.pos_batch = pos_batch;
	}
	public String getPos_code() {
		return pos_code;
	}
	public void setPos_code(String pos_code) {
		this.pos_code = pos_code;
	}
	public int getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(int seq_no) {
		this.seq_no = seq_no;
	}
	public int getSys_serial() {
		return sys_serial;
	}
	public void setSys_serial(int sys_serial) {
		this.sys_serial = sys_serial;
	}
	public String getTran_flag() {
		return tran_flag;
	}
	public void setTran_flag(String tran_flag) {
		if ( tran_flag.equals("0") ){
			this.tran_flag = "正常";
		}else if ( tran_flag.equals("1") ){
			this.tran_flag = "被冲正流水";
		}else if ( tran_flag.equals("2") ){
			this.tran_flag = "冲正流水";
		}else {
			this.tran_flag = "未知标志";
		}
	}
	public float getTrans_amount() {
		return trans_amount;
	}
	public void setTrans_amount(float trans_amount) {
		if ( getTrans_code().equals("退货")){
			this.trans_amount = 0 - trans_amount;
		}else{
			this.trans_amount = trans_amount;
		}
	}
	public String getTrans_card_no() {
		return trans_card_no;
	}
	public void setTrans_card_no(String trans_card_no) {
		this.trans_card_no = trans_card_no;
	}
	
	public String getTrans_card_type() {
		return trans_card_type;
	}
	public void setTrans_card_type(String trans_card_type) {	
		if ( trans_card_type.equals("1") ){
			this.trans_card_type = "本地个人卡";
		}else if ( trans_card_type.equals("2") ){
			this.trans_card_type = "本地公司卡";
		}else if ( trans_card_type.equals("3") ){
			this.trans_card_type = "本地转账卡";
		}else if ( trans_card_type.equals("4") ){
			this.trans_card_type = "本地其他卡";
		}else if ( trans_card_type.equals("5") ){
			this.trans_card_type = "北京公积金卡";
		}else if ( trans_card_type.equals("6") ){
			this.trans_card_type = "本地异地卡";
		}else if ( trans_card_type.equals("7") ){
			this.trans_card_type = "国内他行卡";
		}else if ( trans_card_type.equals("8") ){
			this.trans_card_type = "国际卡";
		}else{
			this.trans_card_type = "未知类型卡";
		}
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public float getTrans_fee() {
		return trans_fee;
	}
	public void setTrans_fee(float trans_fee) {
		this.trans_fee = trans_fee;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public void fetchData(HttpServletRequest request) {
		setCompany_id(request.getParameter("company_id"));
		setStart_date(request.getParameter("start_date"));
		setEnd_date(request.getParameter("end_date"));
		setMonth(request.getParameter("month"));
		setMerchant_id(request.getParameter("merchant_id"));
	}
	
	public void fetchData(ResultSet rst) throws SQLException {
		setSeq_no(rst.getInt("sys_serial"));
		setPos_code(toucsString.unConvert(rst.getString("pos_code")));
		setBill_no(toucsString.unConvert(rst.getString("bill_no")));
		setPos_batch(toucsString.unConvert(rst.getString("pos_batch")));
		setMerchant_id(toucsString.unConvert(rst.getString("merchant_id")));
		setSys_serial(rst.getInt("sys_serial"));
		setTrans_card_no(toucsString.unConvert(rst.getString("trans_card_no")));
		setTrans_code(toucsString.unConvert(rst.getString("trans_code")));
		setTrans_amount(rst.getFloat("trans_amount"));
		setTrans_date(toucsString.unConvert(rst.getString("trans_date")));
		setTrans_time(toucsString.unConvert(rst.getString("trans_time")));
		setTrans_card_type(toucsString.unConvert(rst.getString("trans_card_type")));
		setOper_num(toucsString.unConvert(rst.getString("oper_num")));
		setTrans_fee(rst.getFloat("trans_fee"));
		setAcq_id(toucsString.unConvert(rst.getString("acq_id")));
		setTran_flag(toucsString.unConvert(rst.getString("tran_flag")));
	}
}