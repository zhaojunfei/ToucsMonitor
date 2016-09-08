package com.adtec.toucs.monitor.systemmanage;

import java.io.Serializable;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 代码类</p>
 * <p>Description:封装系统代码信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//代码种类
	private String codeType;
	//代码编号
	private String codeId;
	//代码说明
	private String codeDesc;

  /**
   * 构造方法
   */
	public CodeBean() {
	}

	//代码种类属性读写
	public String getCodeType(){
		return codeType;
	}
	public void setCodeType(String type){
		codeType=type;
	}

	//代码编号属性读写
	public String getCodeId(){
		return codeId;
	}
	public void setCodeId(String id){
		codeId=id;
	}

	//代码说明属性读写
	public String getCodeDesc(){
		return codeDesc;
	}
	public void setCodeDesc(String desc){
		codeDesc=desc;
	}

  /**
   * 取代码对照表关键字（用于在代码对照表中检索代码说明）
   * @return 代码关键字
   */
	public String getKey(){
		return codeType+"."+codeId;
	}

  /**
   * 从查询结果中取代码信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		Debug.println(rst.getString("code_type") + "ok le go");
		setCodeType(rst.getString("code_type").trim());
		setCodeId(rst.getString("sys_code").trim());
		setCodeDesc(toucsString.unConvert(rst.getString("code_desc")));
	}
  
  /**
   * 从查询结果中取代码信息(new)syl
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchNewData(ResultSet rst) throws SQLException{
		Debug.println(rst.getString("para_val") + "ok le go");
		setCodeType(rst.getString("para_name").trim());
		setCodeId(rst.getString("para_val").trim());
		setCodeDesc(toucsString.unConvert(rst.getString("para_desc")));
	}

  /**
   * 添加代码
   * @param sq 数据库访问对象
   * @return 影响的记录数
   * @throws SQLException
   */
	public int insert(SqlAccess sq) throws SQLException{
		String sql="INSERT INTO kernel_code(code_type,sys_code,code_desc) VALUES('" +codeType+"','"+codeId+"','" +codeDesc+"')";
		return sq.queryupdate(sql);
	}

  /**
   * 修改代码
   * @param sq 数据库访问对象
   * @param id 代码
   * @return 影响的记录数
   * @throws SQLException
   */
	public int update(SqlAccess sq,String id) throws SQLException{
		String sql="UPDATE kernel_code SET sys_code=?,code_desc=? WHERE code_type=? AND sys_code=?";
		PreparedStatement stm=sq.conn.prepareStatement(sql);
		stm.setString(1,codeId);
		stm.setString(2,toucsString.Convert(codeDesc));
		stm.setString(3,codeType);
		stm.setString(4,id);
		return stm.executeUpdate();
	}

  /**
   * 转换为XML格式报文，填充到字符串缓冲对象中
   * @param buf 字符串缓冲对象
   */
	public void toXML(StringBuffer buf){
		buf.append("<SysCode type=\""+codeType+"\" code=\""+codeId+"\">\n");
		buf.append("<Desc>"+codeDesc+"</Desc>\n");
		buf.append("</SysCode>\n");	
	}

  /**
   * 转换为XML格式的报文
   * @return 转换后的报文
   */
	public String toXML(){
		StringBuffer buf=new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

  /**
   * 覆盖的toString()方法
   * @return
   */
	public String toString(){
		return codeType+"|"+codeId+"|"+codeDesc;
	}

	public static void main(String[] args) {
    
	}
}
