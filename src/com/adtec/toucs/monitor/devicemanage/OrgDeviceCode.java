package com.adtec.toucs.monitor.devicemanage;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class OrgDeviceCode{
	private String code="";
	private String codeName="";

	public void setCode(String inCode){
		code=inCode;
	}
	public String getCode(){
		return code;
	}
	public void setCodeName(String inCodeName){
		codeName=inCodeName;
	}
	public String getCodeName(){
		return codeName;
	}
}