package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.common.toucsString;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author syl
 * @version 2.0
 */

public class POSMoneyInfo {

	public POSMoneyInfo(){
		
	}
	
	private String pos_code = "";//POS编号
	private String pos_c_code = "";//POS_C编号
	private String pos_dcc_code = "";//POS_DCC编号
	private String merchant_id = "";//商户号
	private String branch_id = "";//机构号
	private String teller_id = "";//柜员号
	private String start_date = "";//开始日期
	private String end_date = "";//结束日期
	private String start_time = "";//开始时间
	private String end_time = "";//结束时间
	private String use_flag = "";//使用标志
	private String memo1 = "";//备注1
	private String memo2 = "";//备注2
	private String memo3 = "";//备注3
	private String mct_name = ""; //商户名称
	
	public String getPos_code() {
		return pos_code;
	}
	public void setPos_code(String posCode) {
		pos_code = posCode;
	}
	public String getPos_c_code() {
		return pos_c_code;
	}
	public void setPos_c_code(String posCCode) {
		pos_c_code = posCCode;
	}
	public String getPos_dcc_code() {
		return pos_dcc_code;
	}
	public void setPos_dcc_code(String posDccCode) {
		pos_dcc_code = posDccCode;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchantId) {
		merchant_id = merchantId;
	}
	public String getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(String branchId) {
		branch_id = branchId;
	}
	public String getTeller_id() {
		return teller_id;
	}
	public void setTeller_id(String tellerId) {
		teller_id = tellerId;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String startDate) {
		start_date = startDate;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String endDate) {
		end_date = endDate;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	public String getUse_flag() {
		return use_flag;
	}
	public void setUse_flag(String useFlag) {
		use_flag = useFlag;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public String getMemo3() {
		return memo3;
	}
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	
	public String getMct_name() {
		return mct_name;
	}
	public void setMct_name(String mctName) {
		mct_name = mctName;
	}
	
	/**
	   * 从Http请求中取POS基本信息
	   * @param request Http请求
	   */
	  public void fetchData(HttpServletRequest request) {
		  setPos_code(request.getParameter("pos_code"));
		  setPos_c_code(request.getParameter("pos_c_code"));
		  setPos_dcc_code(request.getParameter("pos_dcc_code"));
		  setMerchant_id(request.getParameter("merchant_id"));
		  setBranch_id(request.getParameter("branch_id"));
		  setTeller_id(request.getParameter("teller_id"));
		  setStart_date(request.getParameter("start_date"));
		  setEnd_date(request.getParameter("end_date"));
		  setStart_time(request.getParameter("start_time"));
		  setEnd_time(request.getParameter("end_time"));
		  setUse_flag(request.getParameter("use_flag"));
		  setMemo1(request.getParameter("memo1"));
		  setMemo2(request.getParameter("memo2"));
		  setMemo3(request.getParameter("memo3"));
	  }

	  /**
	   * 从查询结果中取POS基本信息
	   * @param rst 查询结果集
	   * @throws SQLException
	   */
	  public void fetchData(ResultSet rst) throws SQLException {
		  setPos_code(toucsString.unConvert(rst.getString("pos_code")));
		  setPos_c_code(toucsString.unConvert(rst.getString("pos_c_code")));
		  setPos_dcc_code(toucsString.unConvert(rst.getString("pos_dcc_code")));
		  setMerchant_id(toucsString.unConvert(rst.getString("merchant_id")));
		  setBranch_id(toucsString.unConvert(rst.getString("branch_id")));
		  setTeller_id(toucsString.unConvert(rst.getString("teller_id")));
		  setStart_date(toucsString.unConvert(rst.getString("start_date")));
		  setEnd_date(toucsString.unConvert(rst.getString("end_date")));
		  setStart_time(toucsString.unConvert(rst.getString("start_time")));
		  setEnd_time(toucsString.unConvert(rst.getString("end_time")));
		  setUse_flag(toucsString.unConvert(rst.getString("use_flag")));
		  setMemo1(toucsString.unConvert(rst.getString("memo1")));
		  setMemo2(toucsString.unConvert(rst.getString("memo2")));
		  setMemo3(toucsString.unConvert(rst.getString("memo3")));
	  }

	  /**
	   * 添加签约POS息
	   * @param sq 数据库访问对象
	   * @return 成功返回1
	   * @throws SQLException
	   */
	  public int insert(SqlAccess sq) throws SQLException{
	      String sql="INSERT INTO pos_enrol VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	      PreparedStatement stm=sq.conn.prepareStatement(sql);
	      stm.setString(1,pos_code);
	      stm.setString(2,pos_c_code);
	      stm.setString(3,pos_dcc_code);
	      stm.setString(4,merchant_id);
	      stm.setString(5,branch_id);
	      stm.setString(6,teller_id);
	      stm.setString(7,start_date);
	      stm.setString(8,end_date);
	      stm.setString(9,start_time);
	      stm.setString(10,end_time);
	      stm.setString(11,"1");
	      stm.setString(12,memo1);
	      stm.setString(13,memo2);
	      stm.setString(14,memo3);

	     return stm.executeUpdate();
	  }
	  
	  
	  /**
	   * 创建用于修改的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	  public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		  String sql = "UPDATE pos_enrol SET " + "(branch_id,teller_id,start_date,end_date,start_time,end_time,use_flag," + "memo1,memo2,memo3)" + " =(?,?,?,?,?,?,?,?,?,?)" + " WHERE pos_code=?";
		  Debug.println(sql);
		  PreparedStatement stm = conn.prepareStatement(sql);
		  Debug.println("["+branch_id+"]["+teller_id+"]["+memo1+"]["+memo2+"]["+memo3+"]"+"{"+key+"}");
		  stm.setString(1, branch_id);
		  stm.setString(2, teller_id);
		  stm.setString(3, start_date);
		  stm.setString(4, end_date);
		  stm.setString(5, start_time);
		  stm.setString(6, end_time);
		  stm.setString(7, "1");
		  stm.setString(8, memo1);
		  stm.setString(9, memo2);
		  stm.setString(10, memo3);
		  stm.setString(11, key);
		  return stm;
	  }
	  
	/**
	 * 根据id查询商户名称  
	 * @throws MonitorException
	 */	  
	 public void setMct_name() throws MonitorException {
		 if (merchant_id != "") {
			 String MctName = "";
			 SqlAccess sq = SqlAccess.createConn();
			 if (sq == null) {
				 throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
			 }
			 try {
				 String sql = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + merchant_id + "'";
				 ResultSet rs = sq.queryselect(sql);
				 while (rs.next()) {
					 MctName = rs.getString("mct_name");
				 }
				 rs.close();
			 } catch (SQLException e) {
				 throw new MonitorException(e);
			 } finally {
				 sq.close();
			 }
			 mct_name = MctName;
		 }
	 } 
	  /**
	   * 创建用于删除的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	 public PreparedStatement makeDeleteStm(Connection conn, String key) throws SQLException {
		 String sql = "DELETE FROM pos_enrol WHERE " +  "(pos_code = ?)";
		 System.out.println("DELETE SQL:" + sql);
		 PreparedStatement stm = conn.prepareStatement(sql);
		 //删除的主键
		 stm.setString(1, key);
		 return stm;
	 }
}
