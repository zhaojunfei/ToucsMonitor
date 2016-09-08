package com.adtec.toucs.monitor.POSDeviceManage;

import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: 查询代码类</p>
 * <p>Description: 查询代码类</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p>
 * @author liuxy
 * @version 1.0
 */

public class QueryCode {
	
	private List bankCode=null;
	private List cardClass=null;
	private List cardType=null;
	
	public QueryCode() {
		cardClass=new ArrayList();
		cardType=new ArrayList();
	}
	
	/**
	 * @param queryType:查询请求类型。0：查询银行代码，卡表类型，卡种类代码。
	 *                                1：查询银行代码
	 *                                2：查询卡种类代码
	 *                                3：查询卡表类型代码
	 */
	public void query(int queryType) throws MonitorException {
		CodeManageBean codeManageBean=new CodeManageBean();
		POSICCompanyIdBean bankCodeMng = new POSICCompanyIdBean();
		if(queryType==0){
			bankCode=bankCodeMng.queryCodes();
			cardClass=codeManageBean.queryCodes(CodeDefine.TB_CARD_CLASS);
			cardType=codeManageBean.queryCodes(CodeDefine.TB_CARD_TYPE);
		}else if(queryType==1){
			bankCode=bankCodeMng.queryCodes();
		}else if(queryType==2){
			cardClass=codeManageBean.queryCodes(CodeDefine.TB_CARD_CLASS);
		}else if(queryType==3){
			cardType=codeManageBean.queryCodes(CodeDefine.TB_CARD_TYPE);
		}
	}
	
	public List getBankCode(){
		return bankCode;
	}
	
	public List getCardClass(){
		return cardClass;
	}
	
	public List getCardType(){
		return cardType;
	}
}