package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.toucsString;

public class POSICCompanyIdInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//´úÂë
	private String company_id="";
	//Ãû³Æ
	private String company_name="";
	
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	public void fetchData(ResultSet rst) throws SQLException{
		setCompany_id(toucsString.unConvert(rst.getString("company_id")));
		setCompany_name(toucsString.unConvert(rst.getString("company_name")));
	}
	
	public void fetchData(HttpServletRequest request){
		setCompany_id(request.getParameter("company_id"));
		setCompany_name(request.getParameter("company_name"));
	}
	
}
