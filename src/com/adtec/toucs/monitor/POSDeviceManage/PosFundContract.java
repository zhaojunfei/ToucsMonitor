package com.adtec.toucs.monitor.POSDeviceManage;

public class PosFundContract {

	/**
	 * <p>Title: 基金签约统计</p>
	 * <p>Description: 基金签约统计</p>
	 * <p>Copyright: Copyright (c) 2009</p>
	 * <p>Company: ADTEC</p> 
	 * @author liuxy
	 * @version 1.0
	 */

	public PosFundContract() {	
	}
	
	private String start_date = "";
	private String end_date = "";
	
	private String branch_id = "";
	private String contranct_num = "";
	private String nocontranct_num = "";
	
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	public String getContranct_num() {
		return contranct_num;
	}
	public void setContranct_num(String contranct_num) {
		this.contranct_num = contranct_num;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getNocontranct_num() {
		return nocontranct_num;
	}
	public void setNocontranct_num(String nocontranct_num) {
		this.nocontranct_num = nocontranct_num;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
}