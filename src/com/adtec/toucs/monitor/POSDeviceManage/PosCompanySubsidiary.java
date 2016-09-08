package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.toucsString;

public class PosCompanySubsidiary {
	
	public PosCompanySubsidiary (){		
	}
	
	private String company_id = "";      //��˾���
	private String start_date = "";      //��ʼ����
	private String end_date = "";        //��ֹ����
	private String month = "";           //�·�(��ѯ,ͳ��)
	private int seq_no = 0;              //ƽ̨��ˮ��
	private String pos_code = "";        //�豸���
	private String bill_no = "";         //Ʊ�ݺ�
	private String pos_batch = "";       //����
	private String merchant_id = "";     //�̻���
	private int sys_serial = 0;          //ƽ̨�������
	private String trans_card_no = "";   //���׿���
	private String trans_code = "";      //������
	private float trans_amount = 0;     //���׽��
	private String trans_date = "";      //��������
	private String trans_time = "";      //����ʱ��
	private String trans_card_type = ""; //���׿�����
	private String oper_num = "";        //��Ա��
	private float trans_fee = 0;        //������
	private String acq_id = "";          //��������
	private String tran_flag = "";       //���ױ�־
	
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		if ( trans_code.equals("MC0010") ){
			this.trans_code = "����";
		}else if ( trans_code.equals("MC2010") ){
			this.trans_code = "�˻�";
		}else{
			this.trans_code = "δ֪";
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
			this.tran_flag = "����";
		}else if ( tran_flag.equals("1") ){
			this.tran_flag = "��������ˮ";
		}else if ( tran_flag.equals("2") ){
			this.tran_flag = "������ˮ";
		}else {
			this.tran_flag = "δ֪��־";
		}
	}
	public float getTrans_amount() {
		return trans_amount;
	}
	public void setTrans_amount(float trans_amount) {
		if ( getTrans_code().equals("�˻�")){
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
			this.trans_card_type = "���ظ��˿�";
		}else if ( trans_card_type.equals("2") ){
			this.trans_card_type = "���ع�˾��";
		}else if ( trans_card_type.equals("3") ){
			this.trans_card_type = "����ת�˿�";
		}else if ( trans_card_type.equals("4") ){
			this.trans_card_type = "����������";
		}else if ( trans_card_type.equals("5") ){
			this.trans_card_type = "����������";
		}else if ( trans_card_type.equals("6") ){
			this.trans_card_type = "������ؿ�";
		}else if ( trans_card_type.equals("7") ){
			this.trans_card_type = "�������п�";
		}else if ( trans_card_type.equals("8") ){
			this.trans_card_type = "���ʿ�";
		}else{
			this.trans_card_type = "δ֪���Ϳ�";
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