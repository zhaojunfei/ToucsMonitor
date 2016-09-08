package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.common.toucsString;

public class POSPublicKey {
	
	private String rid;
	private String index;
	private String expire_date;
	private String hashcmid;
	private String cmid;	
	private String modulus;
	private String exponent;
	private String sha1h;
	private String create_date;
	private String memo1;
	
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getExponent() {
		return exponent;
	}
	public void setExponent(String exponent) {
		this.exponent = exponent;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getModulus() {
		return modulus;
	}
	public void setModulus(String modulus) {
		this.modulus = modulus;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getCmid() {
		return cmid;
	}
	public void setCmid(String cmid) {
		this.cmid = cmid;
	}
	public String getHashcmid() {
		return hashcmid;
	}
	public void setHashcmid(String hashcmid) {
		this.hashcmid = hashcmid;
	}
	public String getSha1h() {
		return sha1h;
	}
	public void setSha1h(String sha1h) {
		this.sha1h = sha1h;
	}
	
  /**
   * 从Http请求中取公钥信息
   * @param request Http请求
   */
	public void fetchData(HttpServletRequest request) {	
		setRid(request.getParameter("rid"));
		setIndex(request.getParameter("index"));
		setExpire_date(request.getParameter("expire_date"));
		setHashcmid(request.getParameter("hashcmid"));
		setCmid(request.getParameter("cmid"));		
		setModulus(request.getParameter("modulus"));
		setExponent(request.getParameter("exponent"));
		setSha1h(request.getParameter("sha1h"));
		setCreate_date(request.getParameter("create_date"));
		setMemo1(request.getParameter("memo1"));
	}

    /**
	 * 从查询结果中取公钥信息
	 * @param rst查询结果集
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setRid(toucsString.unConvert(rst.getString("rid")));
		setIndex(toucsString.unConvert(rst.getString("index")));
		setExpire_date(toucsString.unConvert(rst.getString("expire_date")));
		
		setHashcmid(toucsString.unConvert(rst.getString("hashcmid")));
		setCmid(toucsString.unConvert(rst.getString("cmid")));
		
		setModulus(toucsString.unConvert(rst.getString("modulus")));
		setExponent(toucsString.unConvert(rst.getString("exponent")));
		setSha1h(toucsString.unConvert(rst.getString("sha1h")));
		
		setCreate_date(toucsString.unConvert(rst.getString("create_date")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
	}

  /**
   * 添加公钥信息
   * @param sq 数据库访问对象
   * @return 成功返回1
   * @throws SQLException
   */
	public int insert(SqlAccess sq) throws SQLException{
		String sql="INSERT INTO pos_public_key VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm=sq.conn.prepareStatement(sql);
		stm.setString(1,rid);
		stm.setString(2,index);
		stm.setString(3,expire_date);
		
		stm.setString(4,hashcmid);
		stm.setString(5,cmid);	     
		
		stm.setString(6,modulus);
		stm.setString(7,exponent);
		stm.setString(8,sha1h);
		stm.setString(9,create_date);
		stm.setString(10,memo1);
		
		return stm.executeUpdate();
	}
  
  /**
   * 创建用于添加的动态SQL语句
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO pos_public_key VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT pos_public_key:" + sql);
		//默认状态为启用
		stm.setString(3, "1");
		
		return stm;
	}

  /**
   * 创建用于修改的动态SQL语句
   * @param conn 数据库连接对象
   * @param rid 公钥RID（关键字）
   * @param index 公钥索引（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn, String rid,String index,String memo1) throws SQLException, MonitorException {
		
		String sql = "UPDATE pos_public_key SET memo1=? " + " WHERE rid=? AND index=?";		
		PreparedStatement stm = conn.prepareStatement(sql);	
		stm.setString(1, memo1);
		stm.setString(2,rid);
		stm.setString(3,index);
		
		return stm;
	}

  /**
   * 创建用于设置启用停用状态的的动态SQL语句
   * @param conn 数据库连接对象
   * @param rid   公钥编号（关键字）
   * @param index 公钥索引（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeStatStm(Connection conn, String rid,String index,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		} else {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		}	
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, rid);
		stm.setString(2, index);
		return stm;
	}

  /**
   * 创建用于根据公钥查询的动态SQL语句
   * @param conn 数据库连接对象
   * @param rid 公钥编号（关键字）
   * @param index 公钥索引（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeQueryByMctStm(Connection conn, String rid,String index) throws SQLException {
		String sql = "SELECT * FROM pos_public_key WHERE rid=? AND index=?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, rid);
		stm.setString(2, index);
		return stm;
	}

  /**
   * 创建用于删除的动态SQL语句
   * @param conn 数据库连接对象
   * @param rid  公钥编号（关键字）
   * @param index 索引（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeDeleteStm(Connection conn, String rid,String index) throws SQLException {
		String sql = "DELETE FROM pos_public_key WHERE rid =? AND index =? ";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//删除的主键
		stm.setString(1, rid);
		stm.setString(2, index);
		return stm;
	}
  
	public String DeleteChar(String temp){	
		String tmp1,tmp2="";		
		for(int i=0;i<temp.length();i++){
			if((temp.charAt(i)>'9'||temp.charAt(i)<'0')&& (temp.charAt(i)<'a'||temp.charAt(i)>'f') &&  (temp.charAt(i)<'A'||temp.charAt(i)>'F')){
				
			}else{
				tmp1=temp.substring(i, i+1);
				tmp2=tmp2+tmp1;
			}
		}
		return tmp2;
	}
}

