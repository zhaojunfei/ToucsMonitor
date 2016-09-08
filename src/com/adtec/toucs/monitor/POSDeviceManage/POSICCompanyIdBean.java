package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSICCompanyIdBean {
	
	public POSICCompanyIdBean() {	
	}
	
	/**
	 * ��ѯ��˾����
	 * @param sq ���ݿ���ʶ���
	 * @return �����б�
	 * @throws SQLException
	 */
	public static Vector queryCodes(SqlAccess sq) throws SQLException{
		ResultSet rst=sq.queryselect("SELECT company_id,company_name FROM pos_company ORDER BY company_id");
		Vector v=new Vector();
		while(rst.next()){
			POSICCompanyIdInfo bankCode=new POSICCompanyIdInfo();
			bankCode.fetchData(rst);
			Debug.println(bankCode.toString());
			v.add(bankCode);
		}
		rst.close();
		return v;
	}
	
	/**
	 * ��ѯ���й�˾����
	 * @return �����б�
	 */
	public static Vector queryCodes() throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq=SqlAccess.createConn();
		try{
			Vector v=queryCodes(sq);
			return v;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}
}
