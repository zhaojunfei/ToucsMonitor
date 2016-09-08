package com.adtec.toucs.monitor.AgentManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class ActMerchantBean {
	
	public ActMerchantBean() {
	}

	private String RetString = "";

  /**
   * 登记商户信息
   * @param info 登记的商户信息
   * @param iCount 未使用参数
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int AppendInfo(ActMerchantInfo info, int iCount) throws MonitorException {
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
   * 删除商户信息
   * @param info 登记的商户信息
   * @param strKey 商户号
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int DeleteInfo(String strKey) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		ActMerchantInfo info = new ActMerchantInfo();
		try {
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();  
			}else {
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
   * 修改商户信息
   * @param info 登记的商户信息
   * @param strKey 商户号
   * @return 成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int UpdateInfo(ActMerchantInfo info, String strKey) throws MonitorException {
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
   * 查询商户信息
   * @param strKey 商户号
   * @param strType 商户类型
   * @return v 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "";
			if ( strKey != null && !strKey.trim().equals("") && strType != null && !strType.trim().equals("")) {
				sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "' AND merchant_type='" + strType + "'";
			} else if(strKey != null && !strKey.trim().equals("")){
				sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "' ORDER BY merchant_id";
			}else if (strType != null && !strType.trim().equals("") ){
				sql = "SELECT * FROM act_merchant WHERE merchant_type = '"+ strType+"'";
			}
			ActMerchantInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new ActMerchantInfo();
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
   * 查询商户信息
   * @param strKey 商户号
   * @param code_type  为使用参数
   * @return info商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public ActMerchantInfo QueryInfo(String strKey, String code_type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql;
			sql = "SELECT * FROM act_merchant WHERE merchant_id='" + strKey + "'";
			ActMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new ActMerchantInfo();
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
			String sqlStr = "UPDATE act_merchant SET merchant_stat = '"+strFlag+"' WHERE merchant_id = '" + strKey + "'";
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
		String retStr = "";
		try {
			TranProc(CodeDefine.MASTER_KEYDOWN, key, use_flag);
			retStr = getRetMsg();
		}catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}
	/**
	 * 交易处理程式，调用通讯程式与P端通信，因为不提供按机构的批量交易，所以默认为所有机构。
	 * @param TxCode 与P端接口中的交易码，以MG开头，例如MG7830为POS签到。
	 * @param use_flag 使用标识
	 * @param DeviceCode POS设备编号
	 * @throws MonitorException
	 */
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, use_flag);	
		String retCode = comm.commitToATMP();
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