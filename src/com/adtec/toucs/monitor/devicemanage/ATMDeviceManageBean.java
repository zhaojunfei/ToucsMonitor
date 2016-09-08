package com.adtec.toucs.monitor.devicemanage;
import java.util.*;
import java.sql.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * �豸����ҵ����Bean
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
   * ��ѯ���������������豸�б�
   * @param org_id ��������
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
   * �жϸ������豸���Ƿ��Ǹ����Ļ��������������б����������Ļ��������������������׳��쳣��
   * @param atmCode �豸��
   * @param orgId  ������
   * @param sq   ���ݿ��ѯʵ��
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
				Debug.println("�豸["+atmCode+"]��������:"+tmpOrgId);
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
				Debug.println("�豸["+atmCode+"]�����ڻ���["+orgId+"]��Ͻ��Χ");
			if(!exist)
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}catch(SQLException sqle){
			throw new MonitorException(sqle);
		}
	}

  /**
   * �жϸ������豸���Ƿ��Ǹ����Ļ����б��������������׳��쳣��
   * @param orgList �����б�
   * @param atmDeviceNO �豸��
   * @param conn ���ݿ�����
   */
	public void deviceInOrgList(List orgList,String atmDeviceNO,Connection conn) throws MonitorException{
		//У���������������д����׳��쳣
		if(atmDeviceNO==null || atmDeviceNO.trim().length()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		if(orgList==null || orgList.size()==0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		atmDeviceNO=atmDeviceNO.trim();
		//ȡ�ø������豸�ŵĻ�����
		String org_id=null;
		String sql="select org_id from atm_info where  atm_code='"+atmDeviceNO+"'";
		try{
			Statement stmt2= conn.createStatement();
			ResultSet rst=stmt2.executeQuery(sql);
			if(rst==null) throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			if(rst.next())
				org_id=rst.getString("org_id");
			//���û�и��豸�ţ����׳��쳣
			//Debug.println("ѡ����豸�������������ǣ�"+org_id);
			if(org_id==null || org_id.trim().length()==0)
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}catch(SQLException exp){
			exp.printStackTrace();
		}

		//�жϲ�ѯ���Ļ����Ƿ��Ǵ����ڻ����б���
		org_id=org_id.trim();
		for(int i=0;i<orgList.size();i++){
			CodeBean orgDeviceCode=(CodeBean)orgList.get(i);
			if(orgDeviceCode==null) continue ;
			String tmpOrgID=orgDeviceCode.getCodeId();
			if(tmpOrgID==null) continue ;
			tmpOrgID=tmpOrgID.trim();

			if(tmpOrgID.equals(org_id)) return ;
		}
		//����ڻ����б���û�в鵽�����׳��쳣˵���ò����ڸ��豸��
		throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
	}
	
  /**
   * ���ݸ�������֯CODE��ѯ��Ӧ���豸�б�
   * @param orgCode
   * @param conn ���ݿ�����
   * @return retList
   * @throws MonitorException
   */
	public List queryDiviceList(String orgCode,Connection conn) throws MonitorException{
		List retList=new ArrayList();
		if(orgCode==null || orgCode.trim().length()==0) return retList;
		//ȡ�ø����Ļ����������¼�����(�����Լ�����)����
		CodeManageBean orgDeviceQuery=new CodeManageBean();
		List tmpOrgList=orgDeviceQuery.queryOrgList(orgCode,conn);

		//���ݲ�ѯ�����¼�����������֯SQL���
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
		//���û�л����б���ϢΪ����ֱ�ӷ���
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
   * ��ѯ�������豸�Ƿ��Ѿ�
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