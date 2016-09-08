package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPoundageBean {

	/**
	 * 查询理财pos手续费信息
	 * @param null
	 * @return 返回手续费信息
	 * @throws MonitorException
	 */
	public POSPoundageInfo QueryInfo() throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询手续费信息
			String sql;
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_fee'";
			POSPoundageInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPoundageInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}

  /**
   * 添加理财pos手续费信息
   * @param info 手续费信息
   * @throws UnsupportedEncodingException 
   * @throws MonitorException
   */
	public int AppendInfo(POSPoundageInfo info) throws MonitorException, UnsupportedEncodingException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			}else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.REG_FAILED, "添加手续费信息失败！");
			}
			return flag;
		}catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
	
	/**
	 * 查询理财pos手续费信息
	 * @param null
	 * @throws MonitorException
	 * @return 成功返回Vector
	 */
	public Vector QueryInfoByList()throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="";
			sql = "SELECT * FROM t_para_config WHERE para_name = 'p_lc_fee'";
			POSPoundageInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new POSPoundageInfo();
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
	 * 查询理财pos手续费信息
	 * @param null
	 * @throws MonitorException
	 * @return  成功返回手续费信息
	 */
	public POSPoundageInfo QueryInfos() throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询手续费信息
			String sql;
			sql = "SELECT * FROM t_para_config WHERE para_name='p_lc_fee'";
			POSPoundageInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPoundageInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
		
	 /**
	   * 修改理财pos手续费信息
	   * @param info 手续费信息
	   * @throws MonitorException	
	   * @return 成功返回1
	   */
	public int UpdateInfo(POSPoundageInfo info) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			// 生成修改商户语句
			PreparedStatement stm = info.makeUpdateStm(sq.conn);
			// 修改商户信息
			int flag = stm.executeUpdate();	
			if (flag == 1) {
				sq.commit();// 提交
			}else {
				sq.rollback();// 事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改手续费信息失败！");
			}
			return flag;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	} 

	  /**
	   * 删除理财pos手续费信息
	   * @param null 
	   * @throws MonitorException	
	   * @return 成功返回1
	   */
	public int DeleteInfo() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();    
		try {
			String sql = "DELETE  FROM t_para_config WHERE para_name = 'p_lc_fee'";
			int flag = sq.queryupdate(sql);    	
			return flag;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
		  	  
}
