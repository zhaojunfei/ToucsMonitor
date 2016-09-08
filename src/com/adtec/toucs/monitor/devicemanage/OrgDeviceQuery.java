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
	   *��������Ļ�����ѯ�¼��Ļ���ID
	   * ���ǵ�ʵ��Ӧ�õı�������Ӧ�ã�������ѯ����������Ӧ��
	   * @param org_id �����û�����������
	   * @param conn ���ݿ�����
	   * @return  ��ѯ���Ļ���ID
	   * @throws MonitorException
	   */
	public List queryOrgList(String org_id,Connection conn) throws MonitorException{
		List retList=new ArrayList();
		if(org_id==null || org_id.trim().length()==0) return retList;
		int Org_level=2;//Ĭ�ϻ�����������ͼ���
		//��ѯ�����Ļ����Ļ�����,��������
		String sql="select org_name,org_level from monit_orginfo where org_id='"+org_id+"'";
		Debug.println("OrgDeviceQuery--queryOrgList:��ѯ�����Ļ����Ļ�����,��������"+sql);
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

		//��ѯ��ֱ����������
		Debug.println("OrgDeviceQuery--queryOrgList:��ѯ��ֱ����������");
		List orgTmpList=new ArrayList();
		if(Org_level<2){  //�����������Ϊ0��1���п������¼���������Ŀǰֻ���ǻ���������0��1��2������
			String sql1="select Org_id,Org_name from monit_orginfo where P_ord_id='"+org_id+"'";
			Debug.println("OrgDeviceQuery--queryOrgList:��ѯ��ֱ����������"+sql1);
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
					if(Org_level==0)   ///ֻ�е�0�����û��ſ��ܼ�������������������
						orgTmpList.add(tmpID);
					retList.add(orgDeviceCode);
				}
			}catch(SQLException sqle){
				throw new MonitorException(sqle);
			}
		}

		//��ѯ���������������ƴ���
		if(Org_level==0){   ///ֻ�е�0�����û��ſ��ܼ�������������������
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
				Debug.println("OrgDeviceQuery--queryOrgList:��ѯ���������������ƴ���"+sql2);
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
		//ȡ�ø����Ļ����������¼�����(�����Լ�����)����
		List tmpOrgList=queryOrgList(orgCode,conn);

		//���ݲ�ѯ�����¼�����������֯SQL���
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


