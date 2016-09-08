package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;

public class POSPublicKeyBean {
	
  /**
   * 修改POS公钥信息
   * @param info 登记的公钥信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int updateInfo(POSPublicKey info, String rid,String index,String memo1) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//添加公钥基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, rid,index,memo1);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
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
   * 查询POS公钥基本配置信息
   * @param rid 公钥编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public POSPublicKey queryInfo(String rid,String index, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询公钥信息
			String sql;
			sql = "SELECT * FROM pos_public_key WHERE rid='" + rid + "'AND index='"+ index +"'";
			POSPublicKey info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSPublicKey();
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
   * 查询公钥
   * @param rid 公钥编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null\
   * @throws MonitorException 监控系统异常
   */
	public List VqueryInfo() throws MonitorException {
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			// 查询公钥信息
			String sql;
			sql = "SELECT * FROM pos_public_key ORDER BY rid,index";
			POSPublicKey Info = null;
			List list = new ArrayList();
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				Info = new POSPublicKey();
				Info.fetchData(rst);
				list.add(Info);
			}
			rst.close();	
			return list;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	  
/**
 * 添加公钥
 * 添加的公钥信息
 * @return 添加成功返回1
 * @throws MonitorException监控系统异常
 */
	public int addInfo(POSPublicKey info, int nCount) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			String date=info.getCreate_date();
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			int flag = info.insert(sq);
			if (flag == 1) {
				sq.commit();
			} else {
				//清除掉已插入表中的初始化信息
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "添加公钥信息失败！");
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
   * 删除POS公钥
   * @param info 登记的公钥信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int deleteInfo(String rid,String index) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSPublicKey dinfo = new POSPublicKey();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, rid,index);
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除公钥失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

}

