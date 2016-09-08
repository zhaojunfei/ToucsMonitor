package com.adtec.toucs.monitor.ParameterSet;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
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
	public void setCardClassValue(String inCardClassValue){
		cardClassValue=inCardClassValue;
	}
	public String getCardClassValue(){
		return cardClassValue;
	}
	public String getCardClassName(){
		return cardClassValue;
	}
}

class CardType {
	private String cardTypeValue="";
	public void setCardTypeValue(String inCardTypeValue){
		cardTypeValue=inCardTypeValue;
	}
	public String getCardTypeValue(){
		return cardTypeValue;
	}

	public String getCardTypeName(){
		return cardTypeValue;
	}
}

