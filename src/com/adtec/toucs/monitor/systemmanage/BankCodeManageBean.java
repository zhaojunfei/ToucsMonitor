package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;


import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 银行中心代码管理类</p>
 * <p>Description:封装银行中心代码管理相关的业务逻辑。 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class BankCodeManageBean {

  /**
   * 构造方法
   */
	public BankCodeManageBean() {

	}

  /**
   * 查询银行代码
   * @param sq 数据库访问对象
   * @return v 代码列表
   * @throws SQLException
   */
	public static Vector queryCodes(SqlAccess sq) throws SQLException{
		ResultSet rst=sq.queryselect("SELECT * FROM kernel_bank_code ORDER BY code");
		Vector v=new Vector();
		while(rst.next()){
			BankCodeBean bankCode=new BankCodeBean();
			bankCode.fetchData(rst);
			Debug.println(bankCode.toString());
			v.add(bankCode);
		}
		rst.close();
		return v;
	}

  /**
   * 查询所有银行代码
   * @return v 代码列表
   * @throws MonitorException
   */
	public static Vector queryCodes() throws MonitorException{
		//取数据库连接
		SqlAccess sq=SqlAccess.createConn();
		try{
			Vector v=queryCodes(sq);
			return v;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}


  /**
   * 查询指定交易详细信息
   * @param bankcode 代码种类
   * @return 代码列表
   * @throws MonitorException
   */
	public static BankCodeBean queryCode(String bankCode) throws MonitorException{
		BankCodeBean code=null;
		SqlAccess sq = SqlAccess.createConn();
		String sql="SELECT * FROM kernel_bank_code WHERE code='"+bankCode+"'";
		try{
			ResultSet rst=sq.queryselect(sql);
			while( rst!=null && rst.next() ){
				code = new BankCodeBean();
				code.fetchData(rst);
			}
			rst.close();
			return code;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}

  /**
   * 添加代码
   * @param bankCode 银行代码信息
   * @return 添加成功返回1
   * @throws MonitorException
   */
	public int addCode(BankCodeBean bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			int i=bankCode.insert(sq);
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

  /**
   * 修改代码
   * @param bankCode 代码信息
   * @return 修改成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int modifyCode(BankCodeBean bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			int i=bankCode.update(sq);
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

  /**
   * 删除代码
   * @param bankCode 银行中心代码
   * @return 删除成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int deleteCode(String bankCode) throws MonitorException{
		SqlAccess sq=SqlAccess.createConn();
		try{
			sq.setAutoCommit(false);
			String sql="DELETE FROM kernel_bank_code WHERE code=?";
			PreparedStatement stm=sq.conn.prepareStatement(sql);
			stm.setString(1,bankCode);
			int i=stm.executeUpdate();
			sq.conn.commit();
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

	public static void main(String[] args) {

	}
}
