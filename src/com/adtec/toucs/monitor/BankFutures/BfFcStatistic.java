package com.adtec.toucs.monitor.BankFutures;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

public class BfFcStatistic {
	
	public BfFcStatistic() {
	}

	// �ڻ���˾����
	private String fc_id = "";
	// ��ʼ����
	private String start_date = "";
	// ��ֹ����
	private String end_date = "";
	// ������
	private String open_num = "";
	// ������
	private String close_num = "";
	// �ɹ���ת���ܱ���
	private String ok_cre_num = "";
	// �ɹ���ת���ܽ��
	private String ok_cre_amt = "";
	// �ɹ���ת���ܱ���
	private String ok_deb_num = "";
	// �ɹ���ת���ܽ��
	private String ok_deb_amt = "";
	// ������ת���ܱ���
	private String qe_cre_num = "";
	// ������ת���ܽ��
	private String qe_cre_amt = "";
	// ������ת���ܱ���
	private String qe_deb_num = "";
	// ������ת���ܽ��
	private String qe_deb_amt = "";

	public String getClose_num() {
		return close_num;
	}

	public void setClose_num(String close_num) {
		this.close_num = close_num;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getFc_id() {
		return fc_id;
	}

	public void setFc_id(String fc_id) {
		this.fc_id = fc_id;
	}

	public String getOk_cre_amt() {
		return ok_cre_amt;
	}

	public void setOk_cre_amt(String ok_cre_amt) {
		this.ok_cre_amt = ok_cre_amt;
	}

	public String getOk_cre_num() {
		return ok_cre_num;
	}

	public void setOk_cre_num(String ok_cre_num) {
		this.ok_cre_num = ok_cre_num;
	}

	public String getOk_deb_amt() {
		return ok_deb_amt;
	}

	public void setOk_deb_amt(String ok_deb_amt) {
		this.ok_deb_amt = ok_deb_amt;
	}

	public String getOk_deb_num() {
		return ok_deb_num;
	}

	public void setOk_deb_num(String ok_deb_num) {
		this.ok_deb_num = ok_deb_num;
	}

	public String getOpen_num() {
		return open_num;
	}

	public void setOpen_num(String open_num) {
		this.open_num = open_num;
	}

	public String getQe_cre_amt() {
		return qe_cre_amt;
	}

	public void setQe_cre_amt(String qe_cre_amt) {
		this.qe_cre_amt = qe_cre_amt;
	}

	public String getQe_cre_num() {
		return qe_cre_num;
	}

	public void setQe_cre_num(String qe_cre_num) {
		this.qe_cre_num = qe_cre_num;
	}

	public String getQe_deb_amt() {
		return qe_deb_amt;
	}

	public void setQe_deb_amt(String qe_deb_amt) {
		this.qe_deb_amt = qe_deb_amt;
	}

	public String getQe_deb_num() {
		return qe_deb_num;
	}

	public void setQe_deb_num(String qe_deb_num) {
		this.qe_deb_num = qe_deb_num;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	/**
	 * ��Http������ȡ�̻���Ϣ
	 * @param request Http����
	 */
	public void fetchData(HttpServletRequest request) {
		setFc_id(request.getParameter("fc_id"));
		setStart_date(request.getParameter("start_date"));
		setEnd_date(request.getParameter("end_date"));
		setOpen_num(request.getParameter("open_num"));
		setClose_num(request.getParameter("close_num"));
		setOk_cre_num(request.getParameter("ok_cre_num"));
		setOk_cre_amt(request.getParameter("ok_cre_amt"));
		setOk_deb_num(request.getParameter("ok_deb_num"));
		setOk_deb_amt(request.getParameter("ok_deb_amt"));
		setQe_cre_num(request.getParameter("qe_cre_num"));
		setQe_cre_amt(request.getParameter("qe_cre_amt"));
		setQe_deb_num(request.getParameter("qe_deb_num"));
		setQe_deb_amt(request.getParameter("qe_deb_amt"));
	}

	/**
	 * �Ӳ�ѯ�����ȡ�̻���Ϣ
	 * @param rst ��ѯ�����
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setFc_id(toucsString.unConvert(rst.getString("fc_id")));
		setStart_date(toucsString.unConvert(rst.getString("start_date")));
		setEnd_date(toucsString.unConvert(rst.getString("end_date")));
		setOpen_num(toucsString.unConvert(rst.getString("open_num")));
		setClose_num(toucsString.unConvert(rst.getString("close_num")));
		setOk_cre_num(toucsString.unConvert(rst.getString("ok_cre_num")));
		setOk_cre_amt(toucsString.unConvert(rst.getString("ok_cre_amt")));
		setOk_deb_num(toucsString.unConvert(rst.getString("ok_deb_num")));
		setOk_deb_amt(toucsString.unConvert(rst.getString("ok_deb_amt")));
		setQe_cre_num(toucsString.unConvert(rst.getString("qe_cre_num")));
		setQe_cre_amt(toucsString.unConvert(rst.getString("qe_cre_amt")));
		setQe_deb_num(toucsString.unConvert(rst.getString("qe_deb_num")));
		setQe_deb_amt(toucsString.unConvert(rst.getString("qe_deb_amt")));
	}
}
