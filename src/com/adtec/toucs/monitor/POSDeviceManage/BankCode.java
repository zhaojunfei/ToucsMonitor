package com.adtec.toucs.monitor.POSDeviceManage;

/**
 * <p>Title: BankCode</p>
 * <p>Description: BankCode</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p>
 * @author liuxy
 * @version 1.0
 */

public class BankCode {
	
	private String bankCodeValue="";
	private String bankCodeName="";
	
	public BankCode() {
	}
	
	public void setBankCodeValue(String inBankCodeValue){
		bankCodeValue=inBankCodeValue;
	}
	
	public String getBankCodeValue(){
		return bankCodeValue;
	}
	
	public void setBankCodeName(String inBankCodeName){
		bankCodeName=inBankCodeName;
	}
	public String getBankCodeName(){
		return bankCodeName;
	}
}

class CardClass {
	
	private String cardClassValue="";
	private String cardClassName="";
	
	public void setCardClassValue(String inCardClassValue){
		cardClassValue=inCardClassValue;
	}
	
	public String getCardClassValue(){
		return cardClassValue;
	}
	
	public void setCardClassName(String inCardClassName){
		cardClassName=inCardClassName;
	}
	public String getCardClassName(){
		return cardClassValue;
	}
}

class CardType {
	
	private String cardTypeValue="";
	private String cardTypeName="";
	
	public void setCardTypeValue(String inCardTypeValue){
		cardTypeValue=inCardTypeValue;
	}
	
	public String getCardTypeValue(){
		return cardTypeValue;
	}
	
	public void setCardTypeName(String inCardTypeName){
		cardTypeName=inCardTypeName;
	}
	public String getCardTypeName(){
		return cardTypeValue;
	}
}
