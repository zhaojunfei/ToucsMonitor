package com.adtec.toucs.monitor.devicemanage;
import java.util.*;
import java.sql.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * 设备管理业务处理Bean
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */

public class ATMDeviceManageBean {
	public ATMDeviceManageBean() {
	}

  /**
   * 查询给定机构的所属设备列表
   * @param org_id 机构编码
   * @return List
   * @throws MonitorException
   */
	public List queryATMList(String org_id) throws MonitorException{
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			return queryDiviceList(org_id,conn);
		}catch(MonitorException exp){
			throw exp;
		}finally{
			try{
				conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}

  /**
   * 判断给定的设备号是否是给定的机构的下属机构列表（包括给定的机构本身）所属，不是则抛出异常。
   * @param atmCode 设备号
   * @param orgId  机构号
   * @param sq   数据库查询实例
   * @throws MonitorException
   */
	public void deviceInOrg(String atmCode,String orgId,SqlAccess sq)throws MonitorException{
		if(atmCode==null || atmCode.trim().length()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		if(orgId==null || orgId.trim().length()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		atmCode=atmCode.trim();
		boolean exist=false;
		String tmpOrgId="";
		String sql="SELECT org_id FROM atm_info WHERE atm_code='"+atmCode+"'";
		try{
			ResultSet rst=sq.queryselect(sql);
			if(rst.next()){  
				tmpOrgId=rst.getString("org_id").trim();
				rst.close();
				Debug.println("设备["+atmCode+"]所属机构:"+tmpOrgId);
				if(orgId.equals(tmpOrgId))  
					exist=true;
				else{   
					sql="SELECT org_id FROM monit_orgref WHERE org_id='"+tmpOrgId+"' AND p_org_id='"+orgId+"'";
					rst=sq.queryselect(sql);
					if(rst.next()){
						exist=true;
					}
					rst.close();
				}
			}
			if(!exist)
				Debug.println("设备["+atmCode+"]不属于机构["+orgId+"]管辖范围");
			if(!exist)
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}
	}

  /**
   * 判断给定的设备号是否是给定的机构列表所属，不是则抛出异常。
   * @param orgList 机构列表
   * @param atmDeviceNO 设备号
   * @param conn 数据库连接
   */
	public void deviceInOrgList(List orgList,String atmDeviceNO,Connection conn) throws MonitorException{
		//校验输入参数，如果有错则抛出异常
		if(atmDeviceNO==null || atmDeviceNO.trim().length()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		if(orgList==null || orgList.size()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		atmDeviceNO=atmDeviceNO.trim();
		//取得给定的设备号的机构号
		String org_id=null;
		String sql="select org_id from atm_info where  atm_code='"+atmDeviceNO+"'";
		try{
			Statement stmt2= conn.createStatement();
			ResultSet rst=stmt2.executeQuery(sql);
			if(rst==null) throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			if(rst.next())
				org_id=rst.getString("org_id");
			//如果没有该设备号，则抛出异常
			//Debug.println("选择的设备的所属机构号是："+org_id);
			if(org_id==null || org_id.trim().length()==0)
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}catch(SQLException exp){
			exp.printStackTrace();
		}

		//判断查询到的机构是否是存在于机构列表中
		org_id=org_id.trim();
		for(int i=0;i<orgList.size();i++){
			CodeBean orgDeviceCode=(CodeBean)orgList.get(i);
			if(orgDeviceCode==null) continue ;
			String tmpOrgID=orgDeviceCode.getCodeId();
			if(tmpOrgID==null) continue ;
			tmpOrgID=tmpOrgID.trim();

			if(tmpOrgID.equals(org_id)) return ;
		}
		//如果在机构列表中没有查到，则抛出异常说明该不存在该设备号
		throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
	}
	
  /**
   * 根据给定的组织CODE查询想应的设备列表
   * @param orgCode
   * @param conn 数据库连接
   * @return retList
   * @throws MonitorException
   */
	public List queryDiviceList(String orgCode,Connection conn) throws MonitorException{
		List retList=new ArrayList();
		if(orgCode==null || orgCode.trim().length()==0) return retList;
		//取得给定的机构的所有下级机构(包含自己本身)代码
		CodeManageBean orgDeviceQuery=new CodeManageBean();
		List tmpOrgList=orgDeviceQuery.queryOrgList(orgCode,conn);

		//根据查询到的下级机构集合组织SQL语句
		String strW="";
		int condiNum=tmpOrgList.size()-1;
		for(int i=0;i<tmpOrgList.size();i++){
			CodeBean orgDeviceCode=(CodeBean)tmpOrgList.get(i);
			String orgID=orgDeviceCode.getCodeId();
			if(i==condiNum){
				strW=strW+" org_id='"+orgID+"'";
			} else{
				strW=strW+" org_id='"+orgID+"'or ";
			}
		}
		//如果没有机构列表信息为空则直接返回
		if(strW==null || strW.trim().length()==0) return retList;
		strW=" atm_code[1]='A'" + " and ("+strW+")";
		String sql="select atm_code,atm_name from atm_info";
		sql=sql+" where "+ strW;
		Debug.println("OrgDeviceQuery--queryDiviceList:"+sql);

		try{
			Statement stmt2= conn.createStatement();
			ResultSet rst=stmt2.executeQuery(sql);
			Debug.println("begin rst");
			while(rst.next()){
				Debug.println("begin getdata");
				CodeBean orgDeviceCode=new CodeBean();
				orgDeviceCode.setCodeId(toucsString.unConvert(rst.getString("atm_code")));
				orgDeviceCode.setCodeDesc(toucsString.unConvert(rst.getString("atm_name")));
				retList.add(orgDeviceCode);
			}
			rst.close();
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}
		return retList;
	}

  /**
   * 查询给定的设备是否已经
   * @param deviceNo
   * @return
   */
	public boolean DeviceIsUse(String deviceNo) throws MonitorException{
		if(deviceNo==null) return false;
		if(deviceNo.trim().length()==0) return false;
		String sql="select use_flag from atm_info where atm_code='"+deviceNo.trim()+"'";
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			Statement stmt2= conn.createStatement();
			ResultSet rst=stmt2.executeQuery(sql);
			int use_flag=0;
			if(rst.next())
				use_flag=rst.getInt("use_flag");
			if(use_flag==1)
				return true;
			else
				return false;
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}finally{
			sq.close();
		}
	}

	private boolean OrgDeviceIsUse(String deviceNo){
		return false;
	}

}