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
	 * 查询公司代码
	 * @param sq 数据库访问对象
	 * @return 代码列表
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
	 * 查询所有公司代码
	 * @return 代码列表
	 */
	public static Vector queryCodes() throws MonitorException{
		//取数据库连接
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
