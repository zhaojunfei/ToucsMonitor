package com.adtec.toucs.monitor.POSDeviceManage;


import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;
/**
 * 查询代码类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: </p>
 * @author linbr
 * @version 1.0
 */

public class POSBCCompanyId {
	private List company_id=null;

	public POSBCCompanyId() {
		company_id=new ArrayList();
		
	}
  /**
   * @param company_id:预付卡公司编号
   *
   */
	public void query(int queryType) throws MonitorException {
		POSBCCompanyBean bcCompanyBean=new POSBCCompanyBean();
		if(queryType==0){
			company_id=bcCompanyBean.queryCompany();
		}
			
	}
public List getCompany_id() {
	return company_id;
}
public void setCompany_id(List companyId) {
	company_id = companyId;
}


}
