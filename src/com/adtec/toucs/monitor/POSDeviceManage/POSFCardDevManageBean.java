package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: POS设备管理类</p>
 * <p>Description:POS封装设备管理相关的业务逻辑</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSFCardDevManageBean {
	public POSFCardDevManageBean() {
	}
	
	  /**
	   * 修改POS设备信息
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int updatePosInfo(POSFCardInfo info, String mer_id, String pos_no) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, mer_id, pos_no);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag); 	 
			if (flag == 1) {
				sq.commit();//提交 	
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改外卡POS失败！");
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
	public POSFCardInfo qryPosInfo(String merId, String posCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//查询设备信息
			String sql;
			sql = "SELECT * FROM pos_list WHERE mer_id = '" + merId + "' AND pos_no='" + posCode + "'";
			POSFCardInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSFCardInfo();
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
	public int regPosDevice(POSFCardInfo info) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//添加设备基本信息
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate(); 	     
			if (flag == 1) {
				sq.commit();//提交
			} else {
				Debug.println("in regpos*****wuye");
				sq.rollback();//事务回滚
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记POS设备信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			Debug.println("\n****wuye debug;插入不成功，delete" + info.getPos_no() + " OK !*****");
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
	   * 删除POS新商户
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int deletePosInfo(String merId, String posCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn("FCardPosPool");
		POSFCardInfo posInfo = new POSFCardInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			//此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//删除POS设备基本信息
			PreparedStatement stm = posInfo.makeDeleteStm(sq.conn, merId, posCode);
			int flag = stm.executeUpdate(); 	  
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除POS商户信息失败！");
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
	   *  根据设备编码查询POS信息，返回LIST。若为null或者objID等于000000，则查询所有的POS信息。
	   * @param objId 设备编码
	   * @return 返回LIST。
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public List getPOSInfoByObjId(String merId, String posNo) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess("FCardPosPool");
		String sqlStr = "";

		if ( (merId == null || merId.trim().equals("")) &&
				! (posNo == null || posNo.trim().equals(""))) {
			MonitorException ex = new MonitorException("必须指定商户号", 0, "");
			throw ex;
		}
		try {
			if ( (merId == null || merId.trim().equals("")) &&
					(posNo == null || posNo.trim().equals(""))) {
				sqlStr = "SELECT * FROM pos_list ORDER BY mer_id, pos_no";
			} else if (posNo == null || posNo.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_list  WHERE mer_id = '" + merId + "' ORDER BY pos_no";
			} else {
				sqlStr = "SELECT * FROM pos_list  WHERE mer_id = '" + merId + "' AND pos_no = '" + posNo + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSFCardInfo pi = new POSFCardInfo();
				pi.fetchData(rs);
				list.add(pi);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}
}
