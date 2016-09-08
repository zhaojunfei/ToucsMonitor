package com.adtec.toucs.monitor.systemmanage;


import java.io.Serializable;
import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 银行中心代码类</p>
 * <p>Description:封装交易代码信息 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class BankCodeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//银行中心代码
	private String code="";
	//银行中心名称
	private String name="";
	//银行中心说明
	private String memo="";
  
  /**
   * 构造方法
   */
	public BankCodeBean() {
	}
	//银行中心代码属性读写
	public String getBankCode(){
		return code;
	}
	public void setBankCode(String bankCode){
		code=bankCode;
	}

	//银行中心名称属性读写
	public String getBankName(){
		return name;	
	}
	public void setBankName(String bankName){
		name=bankName;
	}

	//银行中心描述属性读写
	public String getBankMemo(){
		return memo;
	}
	public void setBankMemo(String bankMemo){
		memo=bankMemo;
	}

  /**
   * 从查询结果中取代码信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		setBankCode(toucsString.unConvert(rst.getString("code")));	
		setBankName(toucsString.unConvert(rst.getString("name")));
		setBankMemo(toucsString.unConvert(rst.getString("memo")));	
  }
  /**
   * 从Http请求中取代码信息
   * @param request Http请求
   */
    public void fetchData(HttpServletRequest request){
    	setBankCode(request.getParameter("bankCode"));
    	setBankName(request.getParameter("bankName"));
    	setBankMemo(request.getParameter("bankMemo"));
    	Debug.println("bankCode:"+code);
    	Debug.println("bankName:"+name);
    	Debug.println("bankMemo:"+memo);
    }
  /**
   * 添加代码
   * @param sq 数据库访问对象
   * @return 影响的记录数
   * @throws SQLException
   */
    public int insert(SqlAccess sq) throws SQLException{
    	String sql="INSERT INTO kernel_bank_code(code,name,memo) " + "VALUES(?,?,?)";
    	PreparedStatement stm=sq.conn.prepareStatement(sql); 
    	stm.setString(1,code);
    	stm.setString(2,name);
    	stm.setString(3,memo);
    	return stm.executeUpdate(); 	
    }

  /**
   * 修改代码
   * @param sq 数据库访问对象
   * @param id 代码
   * @return 影响的记录数
   * @throws SQLException
   */
    public int update(SqlAccess sq) throws SQLException{
    	String sql="UPDATE kernel_bank_code SET name=?,memo=?" + "WHERE code=?";
    	PreparedStatement stm=sq.conn.prepareStatement(sql);
    	stm.setString(1,name);
    	stm.setString(2,memo);
    	stm.setString(3,code);
    	Debug.println(sql);
    	return stm.executeUpdate();
    }

    public static void main(String[] args) {

    }
}