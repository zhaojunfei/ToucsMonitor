package com.adtec.toucs.monitor.devicemanage;
import com.adtec.toucs.monitor.common.*;
import java.sql.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class OrgDeviceQuery {

	public OrgDeviceQuery() {

	}

	  /**
	   *根据输入的机构查询下级的机构ID
	   * 考虑到实际应用的北京建行应用，做到查询三级机构的应用
	   * @param org_id 操作用户的所属机构
	   * @param conn 数据库连接
	   * @return  查询到的机构ID
	   * @throws MonitorException
	   */
	public List queryOrgList(String org_id,Connection conn) throws MonitorException{
		List retList=new ArrayList();
		if(org_id==null || org_id.trim().length()==0) return retList;
		int Org_level=2;//默认机构级别是最低级别
		//查询给定的机构的机构名,机构级别
		String sql="select org_name,org_level from monit_orginfo where org_id='"+org_id+"'";
		Debug.println("OrgDeviceQuery--queryOrgList:查询给定的机构的机构名,机构级别"+sql);
		String orgName=null;
		try{
			Statement stmt= conn.createStatement();
			ResultSet rst=stmt.executeQuery(sql);
			if(rst.next()){
				orgName=rst.getString("org_name");
				Org_level=rst.getInt("Org_level");
				Debug.println(orgName);
				OrgDeviceCode orgDeviceCode=new OrgDeviceCode();
				orgDeviceCode.setCode(org_id);
				orgDeviceCode.setCodeName(orgName);
				retList.add(orgDeviceCode);
			}
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}

		//查询其直接下属机构
		Debug.println("OrgDeviceQuery--queryOrgList:查询其直接下属机构");
		List orgTmpList=new ArrayList();
		if(Org_level<2){  //如果机构级别为0，1才有可能有下级机构。（目前只考虑机构级别有0，1，2三级）
			String sql1="select Org_id,Org_name from monit_orginfo where P_ord_id='"+org_id+"'";
			Debug.println("OrgDeviceQuery--queryOrgList:查询其直接下属机构"+sql1);
			try{
				Statement stmt1= conn.createStatement();
				Debug.println("conn.createStatement");
				ResultSet rst1=stmt1.executeQuery(sql1);
				Debug.println("stmt1.executeQuery");

				String tmpID="";
				String tmpName="";

				while(rst1.next()){
					OrgDeviceCode orgDeviceCode=new OrgDeviceCode();
					tmpID=rst1.getString("Org_id");
					tmpName=rst1.getString("Org_name");
					Debug.println(tmpID);
					if(tmpID==null || tmpID.trim().length()==0) continue;
					orgDeviceCode.setCode(tmpID);
					orgDeviceCode.setCodeName(tmpName);
					if(Org_level==0)   ///只有第0级的用户才可能继续查其间接下属机构。
						orgTmpList.add(tmpID);
					retList.add(orgDeviceCode);
				}
			}catch(SQLException sqle){
				throw new MonitorException(sqle);
			}
		}

		//查询其间接下属机构名称代码
		if(Org_level==0){   ///只有第0级的用户才可能继续查其间接下属机构。
			int condNum=orgTmpList.size()-1;
			String strW="";
			Debug.println("condNum====="+condNum);
			for(int i=0;i<orgTmpList.size();i++){
				if(i==condNum){
					strW=strW+" p_ord_id='"+(String)orgTmpList.get(i)+"'";
					Debug.println("i==condNum stw====="+strW);
				}else{
					strW=strW+" P_ord_id='"+(String)orgTmpList.get(i)+"' or ";
					Debug.println("i!=condNum stw====="+strW);
				}
			}
			if(strW.trim().length()!=0) {
				strW="where " +strW;

				String sql2="";
				sql2="select org_id,org_name from monit_orginfo "+strW;
				Debug.println("OrgDeviceQuery--queryOrgList:查询其间接下属机构名称代码"+sql2);
				try{
					Statement stmt2= conn.createStatement();
					ResultSet rst2=stmt2.executeQuery(sql2);
					String tmpID2="";
					String tmpName2="";

					while(rst2.next()){
						OrgDeviceCode orgDeviceCode=new OrgDeviceCode();
						tmpID2=rst2.getString("Org_id");
						tmpName2=rst2.getString("Org_name");
						if(tmpID2==null || tmpID2.trim().length()==0) continue;
						orgDeviceCode.setCode(tmpID2);
						orgDeviceCode.setCodeName(tmpName2);
						retList.add(orgDeviceCode);
					}
				}catch(SQLException sqle){
					throw new MonitorException(sqle);
				}
			}
		}
		return retList;
	}

	public List queryDiviceList(String orgCode,Connection conn) throws MonitorException{
		List retList=new ArrayList();
		if(orgCode==null || orgCode.trim().length()==0) return retList;
		//取得给定的机构的所有下级机构(包含自己本身)代码
		List tmpOrgList=queryOrgList(orgCode,conn);

		//根据查询到的下级机构集合组织SQL语句
		String strW="";
		int condiNum=tmpOrgList.size()-1;
		for(int i=0;i<tmpOrgList.size();i++){
			OrgDeviceCode orgDeviceCode=(OrgDeviceCode)tmpOrgList.get(i);
			String orgID=orgDeviceCode.getCode();
			if(i==condiNum){
				strW=strW+" org_id='"+orgID+"'";
				Debug.println("queryDiviceList:i==condNum stw====="+strW);
			} else{
				strW=strW+" org_id='"+orgID+"'or ";
				Debug.println("queryDiviceList:i==condNum stw====="+strW);
			}
		}
		if(strW==null || strW.trim().length()==0) return retList;
		String sql="select atm_code,atm_name from atm_info";
		sql=sql+" where "+ strW;
		Debug.println("OrgDeviceQuery--queryDiviceList:"+sql);
		try{
			Statement stmt2= conn.createStatement();
			ResultSet rst=stmt2.executeQuery(sql);
			Debug.println("begin rst");
			while(rst.next()){
				Debug.println("begin getdata");
				OrgDeviceCode orgDeviceCode=new OrgDeviceCode();
				orgDeviceCode.setCode(rst.getString("atm_code"));
				orgDeviceCode.setCodeName(rst.getString("atm_name"));
				retList.add(orgDeviceCode);
			}
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}
		return retList;
	}
}


