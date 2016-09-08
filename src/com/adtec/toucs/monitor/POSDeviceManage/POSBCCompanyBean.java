package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.adtec.toucs.monitor.AgentManage.ActMerchantInfo;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSBCCompanyBean {

	/**
	  * ���Ԥ������˾��Ϣ
	  * @param info ע��Ĺ�˾��Ϣ
	  * @return flag �Ǽǳɹ�����1
	  * @throws MonitorException ���ϵͳ�쳣
	  */
	public int AppendInfo(POSBCCompanyInfo info) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "���Ԥ������˾��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	 * ɾ��Ԥ������˾��Ϣ
	 * @param info Ԥ������˾��Ϣ
	 * @param company_id ��˾���
	 * @return flag�ɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int DeleteInfo(String company_id) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		POSBCCompanyInfo info = new POSBCCompanyInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, company_id);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ��Ԥ������˾��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	/**
	 * ɾ��Ԥ������˾��Ϣ(����ɾ��)
	 * @param strArray Ԥ������˾����
	 * @return null
	 * @throws MonitorException
	 */	
	public void deleteBCCards(String[] strArray)throws MonitorException{
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		StringBuffer strbf = new StringBuffer();
		for(int i=0;i<strArray.length;i++){
			strbf.append("'").append(strArray[i]).append("',");
		}
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			//ɾ��POS�豸������Ϣ(����)
			String sql="DELETE FROM pos_yfk_company WHERE company_id IN("+strbf.substring(0,strbf.length()-1)+")";
			int flag = sq.queryupdate(sql);
			System.out.println("flag="+flag);	    	 
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	 * �޸ĵ������̻���Ϣ
	 * @param info �Ǽǵ��豸��Ϣ
	 * @param strKey δʹ�ò���
	 * @return flag �ɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int UpdateInfo(POSBCCompanyInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸�Ԥ������˾��Ϣʧ�ܣ�");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
    }
	
	
	/**
	 * ���ݹ�˾��Ų�ѯԤ������˾��Ϣ
	 * @param company_id ��˾���
	 * @param org_id ������
	 * @return v �̻���Ϣ������̻������ڣ�����null
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public Vector QueryInfoByList(String company_id	, String org_id) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "";
			if ( company_id != null && !company_id.trim().equals("")) {
				sql = "SELECT * FROM  pos_yfk_company WHERE company_id='" + company_id + "' ";          
			}
			POSBCCompanyInfo info = new POSBCCompanyInfo();
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info.fetchData(rst);
				v.add(info);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	   * ����ѡ��������������̻����ֵĲ�������ȡ��Ԥ������˾
	   * @param orgId ����ID
	   * @param merchName �������Ʋ�������
	   * @return LIST�б�ÿһ��Ԫ�ض���һ��POSMerchantInfo����
	   */
		public List getBCByOrgAndName(String org_id, String company_name) throws MonitorException {
			if (company_name == null) {
				company_name = "";
			}/*else if(org_id == null) {
				org_id = "";
			}
			org_id = org_id.trim();*/
			company_name = company_name.trim();
			
			//List list = new ArrayList();
			SqlAccess conn = SqlAccess.createConn();
			String sql= "";
			try {
				if(company_name != null && !company_name.trim().equals("") && org_id != null && !org_id.trim().equals("")){
					sql= "SELECT * FROM  pos_yfk_company WHERE company_name = '"+company_name+"' AND org_id = '"+org_id+"'";
				}else if (org_id != null && !org_id.trim().equals("")) {
					sql= "SELECT * FROM  pos_yfk_company WHERE org_id='" + org_id+ "' ";
				} else if ( company_name != null && !company_name.trim().equals("")) {
					sql= "SELECT * FROM  pos_yfk_company WHERE company_name='" + company_name + "' ";          
				}else{
					sql="SELECT * FROM  pos_yfk_company";
				}
				
				//add by liyp 20031022 add sort condition
				//sqlStr += " ORDER BY org_id ";
				Debug.println("===============================querycompany-query:"+sql);
				POSBCCompanyInfo info = null;
				Vector v = null;
				ResultSet rst = conn.queryselect(sql);
				v = new Vector();
				while (rst != null && rst.next()) {
					info = new POSBCCompanyInfo();
					info.fetchData(rst);
					v.add(info);
				}
				rst.close();
				return v;
			} catch (SQLException e1) {
				throw new MonitorException(e1);
			} finally {
				conn.close();
			}
		}

	/**
	 * ��ѯԤ������˾��Ϣ
	 * @param strKey ��˾��� 
	 * @return info ��˾��Ϣ������̻������ڣ�����null
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public POSBCCompanyInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			System.out.println("55555555555555555:");
			sql = "SELECT * FROM pos_yfk_company WHERE company_id='" + strKey + "'";
			POSBCCompanyInfo info =null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSBCCompanyInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	 * ��˾�����������ú�ͣ�á�
	 * @param company_id ��˾���  
	 * @param strFlag ��־"1"-����"0"-ͣ��
	 * @throws MonitorException
	 */
	public int ManageInfo(String company_id, String strFlag ) throws MonitorException {
		SqlAccess conn = SqlAccess.createConn();
		try {
			if (company_id == null || company_id.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,"��˾��Ų�����,��ȷ���������Ϣ.");
			}
			String sql = "UPDATE pos_yfk_company SET company_stat = '"+strFlag+"' WHERE company_id = '" + company_id + "'";
			int affect = conn.queryupdate(sql);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,"��˾��Ų�����,��ȷ���������Ϣ");
			}
			return affect;
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}
    }


/**
 * ��ѯ��˾���
 * @param company_idԤ������˾���
 * @param sq���ݿ���ʶ���
 * @return �����б�
 * @throws SQLException
 */
	public static Vector queryCompany(SqlAccess sq) throws SQLException {
		ResultSet rst=sq.queryselect("SELECT * FROM pos_yfk_company");
		Vector v=new Vector();
		while(rst.next()){
			POSBCCompanyInfo company_id= new POSBCCompanyInfo();
			company_id.fetchData(rst);
			Debug.println(company_id.toString());
			v.add(company_id);
		}
		rst.close();
		return v;
	}
	

	  /**
	   * ��ѯָ��˾���
	   * @param company_idԤ������˾���
	   * @return �����б�
	   */
		public static Vector queryCompany() throws MonitorException {
		
		//ȡ���ݿ�����
		SqlAccess sq=SqlAccess.createConn();
		try{
			Vector v=queryCompany(sq);
			return v;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
		}
}