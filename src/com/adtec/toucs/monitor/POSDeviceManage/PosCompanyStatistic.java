package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.http.HttpServletRequest;

public class PosCompanyStatistic {
	
	public PosCompanyStatistic() {		
	}

	private String company_id = "";     //��˾���
	private String start_date = "";     //��ʼ����
	private String end_date = "";       //��ֹ����
	private String trans_date = "";      //��������
	private String month = "";           //�·�(��ѯ,ͳ��)
	private String merchant_id = "";     //�̻���	
    private String consume_num = "";     //���ѱ���
    private String regoods_num = "";     //�˻�����
    private String consume_amount = "";  //���ѽ��
    private String regoods_amount = "";  //�˻����
    
    
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
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
    
	public String getConsume_amount() {
		return consume_amount;
	}
	public void setConsume_amount(String consume_amount) {
		this.consume_amount = consume_amount;
	}
	
	public String getConsume_num() {
		return consume_num;
	}
	public void setConsume_num(String consume_num) {
		this.consume_num = consume_num;
	}
	
	public String getRegoods_amount() {
		return regoods_amount;
	}
	public void setRegoods_amount(String regoods_amount) {
		this.regoods_amount = regoods_amount;
	}
	
	public String getRegoods_num() {
		return regoods_num;
	}
	public void setRegoods_num(String regoods_num) {
		this.regoods_num = regoods_num;
	}
	
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	/**
	 * ��Http������ȡ������Ϣ
	 * @param request Http����
	 */
	public void fetchData(HttpServletRequest request) {
		setCompany_id(request.getParameter("company_id"));
		setStart_date(request.getParameter("start_date"));
		setEnd_date(request.getParameter("end_date"));
		setMonth(request.getParameter("month"));
		setMerchant_id(request.getParameter("merchant_id"));
	}
}