package com.adtec.toucs.monitor.ThirdPartyManage;



import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author sunyl
 * @version 1.0
 */

public class ThirdPartyMerchantBean {
	
	public ThirdPartyMerchantBean() {
	}

	private String RetString = "";

  /**
   * 登记第三方商户信息
   * @param info 登记的商户信息
   * @param iCount 未使用参数
   * @return flag 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int AppendInfo(ThirdPartyMerchantInfo info, int iCount) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记商户信息失败！");
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
   * 登记第三方商户密钥(t_merchant_key)
   * @param info 登记的商户密钥
   * @return 登记成功返回1
   * @throws MonitorException 
   */
	public void addInfo(ThirdPartyMerchantInfo info) throws MonitorException{
		String date=Util.getCurrentDate();
		String time=Util.getCurrentTime();
		SqlAccess sq = SqlAccess.createConn();
		for(int i=1;i<9;i++){
			StringBuffer SqlStr = new StringBuffer();
			SqlStr.append("INSERT INTO t_merchant_key VALUES(");
			SqlStr.append("'" + info.getMerchantid());
			SqlStr.append("','" + "0"+i);
			SqlStr.append("','" + "0000000000000000");
			SqlStr.append("','" + "1111111111111111");
			SqlStr.append("','" + date);
			SqlStr.append("','" + time);
			SqlStr.append("')");	  
			try {
				sq.queryupdate(SqlStr.toString());		
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
    }
  
  /**
   * 删除第三方商户信息
   * @param info 登记的商户信息
   * @param strKey 商户号
   * @return flag成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int DeleteInfo(String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		ThirdPartyMerchantInfo info = new ThirdPartyMerchantInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除商户信息失败！");
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
   * 删除第三方商户密钥
   * @param info 登记的商户信息
   * @param strKey 商户号
   * @return 成功返回1
   * @throws SQLException 
   * @throws MonitorException 监控系统异常
   */
	public void Deletekey(String strKey){
		try {
			SqlAccess sq = SqlAccess.createConn();
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("DELETE FROM t_merchant_key WHERE merchant_id = '");
			sqlStr.append(strKey);
			sqlStr.append("'");
			sq.queryupdate(sqlStr.toString());
		} catch (MonitorException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

  /**
   * 修改第三方商户信息
   * @param info 登记的设备信息
   * @param strKey 未使用参数
   * @return flag 成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int UpdateInfo(ThirdPartyMerchantInfo info, String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改商户信息失败！");
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
   * 查询第三方商户信息列表
   * @param strKey 商户号
   * @param strType 商户类型
   * @return v 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "";
			if(strKey != null && !strKey.trim().equals("") && strType != null && !strType.trim().equals("")){
				sql = "SELECT * FROM  t_merchant_info WHERE merchant_id = '"+strKey+"' AND merchant_type = '"+strType+"'";
			}else if ( strKey != null && !strKey.trim().equals("")) {
				sql = "SELECT * FROM t_merchant_info WHERE merchant_id='" + strKey + "' ";          
			} else if (strType != null && !strType.trim().equals("")){
				sql = "SELECT * FROM t_merchant_info WHERE merchant_type='" + strType + "' ORDER BY merchant_id";
			}
			ThirdPartyMerchantInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new ThirdPartyMerchantInfo();
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
   * 查询第三方商户信息
   * @param strKey 商户号
   * @param code_type 未使用参数
   * @return info 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public ThirdPartyMerchantInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM t_merchant_info WHERE merchant_id='" + strKey + "'";
			ThirdPartyMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new ThirdPartyMerchantInfo();
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
   * 商户管理，包括启用和停用。
   * @param strKey 商户编号  
   * @param strFlag 标志"1"-启用"0"-停用
   * @throws MonitorException
   */
	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
		SqlAccess conn = SqlAccess.createConn();
		try {
			if (strKey == null || strKey.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			String sqlStr = "UPDATE t_merchant_info SET merchant_stat = '"+strFlag+"' WHERE merchant_id = '" + strKey + "'";
			int affect = conn.queryupdate(sqlStr);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			return affect;
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}
    }

   /**
    * POS密钥下载程式，将P端返回的
    * @param key 设备编号
    * @param use_flag 使用标识
    * @return 包含分解后的3组密钥的字符串数组
    * @throws MonitorException
    */
	public String down_auth(String key, String use_flag) throws MonitorException {
		System.out.println("-------ThirdPartyMerchantBean-----------------down_auth-----------------");
		String retStr = "";
		try {
			TranProc(CodeDefine.NEW_MASTER_KEYDOWN, key, use_flag);//主密钥下载(new)                            "MG7002"，商户id，1
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}
	
  /**
   * 交易处理程式，调用通讯程式与P端通信，因为不提供按机构的批量交易，所以默认为所有机构。
   * @param TxCode 与P端接口中的交易码，以MG开头，例如MG7830为POS签到。
   * @param DeviceCode POS设备编号
   * @throws MonitorException
   */
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {//  "MG7002"，商户id，1
		System.out.println("-------ThirdPartyMerchantBean-------------TranProc-----------------");
		
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, use_flag);//  "MG7002"，商户id，1
		String retCode = comm.commitToATMP();
		
		System.out.println("----ThirdPartyMerchantBean----TranProc-----retCode-----"+retCode);
		
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
   }
   /**
    * 取得ATM返回消息，retMsg域
    * @return ATM返回消息
    */
	public String getRetMsg() {
		return RetString;
	}
}
