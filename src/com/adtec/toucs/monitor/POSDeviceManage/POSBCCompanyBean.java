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
	  * 添加预付卡公司信息
	  * @param info 注册的公司信息
	  * @return flag 登记成功返回1
	  * @throws MonitorException 监控系统异常
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
				throw new MonitorException(ErrorDefine.REG_FAILED, "添加预付卡公司信息失败！");
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
	 * 删除预付卡公司信息
	 * @param info 预付卡公司信息
	 * @param company_id 公司编号
	 * @return flag成功返回1
	 * @throws MonitorException 监控系统异常
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
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除预付卡公司信息失败！");
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
	 * 删除预付卡公司信息(批量删除)
	 * @param strArray 预付卡公司数组
	 * @return null
	 * @throws MonitorException
	 */	
	public void deleteBCCards(String[] strArray)throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		StringBuffer strbf = new StringBuffer();
		for(int i=0;i<strArray.length;i++){
			strbf.append("'").append(strArray[i]).append("',");
		}
		try {
			//设置数据库连接提交方式为非自动提交
			//删除POS设备基本信息(多条)
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
	 * 修改第三方商户信息
	 * @param info 登记的设备信息
	 * @param strKey 未使用参数
	 * @return flag 成功返回1
	 * @throws MonitorException 监控系统异常
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
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改预付卡公司信息失败！");
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
	 * 根据公司编号查询预付卡公司信息
	 * @param company_id 公司编号
	 * @param org_id 机构号
	 * @return v 商户信息，如果商户不存在，返回null
	 * @throws MonitorException 监控系统异常
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
	   * 根据选择的所属机构和商户名字的部分描述取得预付卡公司
	   * @param orgId 机构ID
	   * @param merchName 机构名称部分文字
	   * @return LIST列表，每一个元素都是一个POSMerchantInfo对象
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
	 * 查询预付卡公司信息
	 * @param strKey 公司编号 
	 * @return info 公司信息，如果商户不存在，返回null
	 * @throws MonitorException 监控系统异常
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
	 * 公司管理，包括启用和停用。
	 * @param company_id 公司编号  
	 * @param strFlag 标志"1"-启用"0"-停用
	 * @throws MonitorException
	 */
	public int ManageInfo(String company_id, String strFlag ) throws MonitorException {
		SqlAccess conn = SqlAccess.createConn();
		try {
			if (company_id == null || company_id.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,"公司编号不存在,请确认输入的信息.");
			}
			String sql = "UPDATE pos_yfk_company SET company_stat = '"+strFlag+"' WHERE company_id = '" + company_id + "'";
			int affect = conn.queryupdate(sql);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,"公司编号不存在,请确认输入的信息");
			}
			return affect;
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}
    }


/**
 * 查询公司编号
 * @param company_id预付卡公司编号
 * @param sq数据库访问对象
 * @return 代码列表
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
	   * 查询指公司编号
	   * @param company_id预付卡公司编号
	   * @return 代码列表
	   */
		public static Vector queryCompany() throws MonitorException {
		
		//取数据库连接
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