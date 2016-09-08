package com.adtec.toucs.monitor.POSDeviceManage;

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

public class POSAccountBean {
	
	public POSAccountBean() {
	}

	private String RetString = "";

	  /**
	   * 修改POS设备信息
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int updateInfo(POSAccount info, String key) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);		
			if (flag == 1) {
				sq.commit();//提交 	
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改账户信息失败！");
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
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public POSAccount queryInfo(String key, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			sql = "SELECT * FROM kernel_acct_auth WHERE account='" + key + "'";
			POSAccount info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSAccount();
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
	   * 登记POS新设备
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int addInfo(POSAccount info, int nCount) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//添加设备基本信息
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit(); //提交
			} else {
				sq.rollback();//事务回滚
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记账户信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			throw new MonitorException(e1);
		} finally {
			if (sq != null) {
				if (sq.isConnectDB()) {
					sq.close();
				}
			}
		}
	}

	  /**
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public Vector VqueryInfo(String key, String type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			sql = "SELECT * FROM kernel_acct_auth";
			
			POSAccount Info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				Info = new POSAccount();
				Info.fetchData(rst);
				v.add(Info);
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
	   * 删除POS新商户
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int deleteInfo(String key) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSAccount dinfo = new POSAccount();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//删除POS设备基本信息
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, key);
			int flag = stm.executeUpdate(); 	  
			if (flag == 1) {
				sq.commit();//提交
			}  else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除对公帐户失败！");
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
	   * POS密钥下载程式，将P端返回的
	   * @param posID 设备编号
	   * @return 包含分解后的3组密钥的字符串数组
	   * @throws MonitorException
	   **/
	public String down_auth(String key) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.POS_AUTHDOWN, key);
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
	private void TranProc(String TxCode, String DeviceCode) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, "000000000");
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
	}
	/**
	 * 取得ATM返回消息，retMsg域
	 * @return ATM返回消息
	 **/
	public String getRetMsg() {
		return RetString;
	}
}