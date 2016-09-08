package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class POSEnrollInfo {

	public POSEnrollInfo() {
	}
	//POS编号
	private String pos_code = "";

	//POS-C编号
	private String pos_c_code = "";

	//POS-DCC编号
	private String pos_dcc_code = "";

	//商户号
	private String merchant_id = "";

	//机构号	
	private String branch_id = "";

	//柜员号
	private String teller_id = "";

	//起始日期
	private String start_date = "";

	//终止日期
	private String end_date = "";

	//起始时间
	private String start_time = "";

	//终止时间
	private String end_time = "";

	//使用标志
	private String use_flag = "";

	//备注1
	private String memo1 = "";

	//备注2
	private String memo2 = "";

	//备注3
	private String memo3 = "";

	//商户名称
	private String mct_name = "";


	//POS编号属性读写
	public void setPoscode(String poscode) {
		pos_code = poscode;
	}

	public String getPoscode() {
		return pos_code;
	}

	//POS编号属性读写
	public void setPosCCode(String posCCode) {
		pos_c_code = posCCode;
	}

	public String getPosCCode() {
		return pos_c_code;
	}

	//POS编号属性读写
	public void setPosDccCode(String posDccCode) {
		pos_dcc_code = posDccCode;
	}

	public String getPosDccCode() {
		return pos_dcc_code;
	}

	//POS所属商户读写
	public void setMerchantid(String Merchartid) {
		merchant_id = Merchartid;
	}

	public String getMerchantid() {
		return merchant_id;
	}

	//机构号读写
	public void setBranchid(String Branchid) {
		branch_id = Branchid;
	}

	public String getBranchid() {
		return branch_id;
	}

	//柜员号读写
	public void setTellerid(String Tellerid) {
		teller_id = Tellerid;
	}

	public String getTellerid() {
		return teller_id;
	}

	//起始日期读写
	public void setStartdate(String Startdate) {
		start_date = Startdate;
	}

	public String getStartdate() {
		return start_date;
	}

	//终止日期读写
	public void setEnddate(String Enddate) {
		end_date = Enddate;
	}

	public String getEnddate() {
		return end_date;
	}

	//起始时间读写
	public void setStarttime(String Starttime) {
		start_time = Starttime;
	}

	public String getStarttime() {
		return start_time;
	}

	//终止时间读写
	public void setEndtime(String Endtime) {
		end_time = Endtime;
	}

	public String getEndtime() {
		return end_time;
	}

	//使用标志读写
	public void setUseflag(String Useflag) {
		use_flag = Useflag;
	}

	public String getUseflag() {
		return use_flag;
	}

	//备注1
	public void setMemo1(String Memo1) {
		memo1 = Memo1;
	}

	public String getMemo1() {
		return memo1;
	}

	//备注2
	public void setMemo2(String Memo2) {
		memo2 = Memo2;
	}

	public String getMemo2() {
		return memo2;
	}

	//备注3
	public void setMemo3(String Memo3) {
		memo3 = Memo3;
	}

	public void setMct_name() throws MonitorException {
		if (merchant_id != "") {
			String MctName = "";
			SqlAccess sq = SqlAccess.createConn();
			if (sq == null) {
				throw new MonitorException(ErrorDefine.FAIL_DB_CONN,
                                   ErrorDefine.FAIL_DB_CONN_DESC);
			}
			try {
				String sql = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + merchant_id + "'";
				ResultSet rs = sq.queryselect(sql);
				while (rs.next()) {
					MctName = rs.getString("mct_name");
				}
				rs.close();
			}catch (SQLException e) {
				throw new MonitorException(e);
			} finally {
				sq.close();
			}
			mct_name = MctName;
		}
	}

	public String getMemo3() {
		return memo3;
	}

	public String getMct_name() {
		return mct_name;
	}

	  /**
	   * 从Http请求中取POS基本信息
	   * @param request Http请求
	   */
	public void fetchData(HttpServletRequest request) {
		setPoscode(request.getParameter("pos_code"));
		setPosCCode(request.getParameter("pos_c_code"));
		setPosDccCode(request.getParameter("pos_dcc_code"));
		setMerchantid(request.getParameter("merchant_id"));
    	setBranchid(request.getParameter("branch_id"));
    	setTellerid(request.getParameter("teller_id"));
    	setStartdate(request.getParameter("start_date"));
    	setEnddate(request.getParameter("end_date"));
    	setStarttime(request.getParameter("start_time"));
    	setEndtime(request.getParameter("end_time"));
    	setUseflag(request.getParameter("use_flag"));
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
		setPoscode(toucsString.unConvert(rst.getString("pos_code")));
		setPosCCode(toucsString.unConvert(rst.getString("pos_c_code")));
		setPosDccCode(toucsString.unConvert(rst.getString("pos_dcc_code")));
		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
		setBranchid(toucsString.unConvert(rst.getString("branch_id")));
    	setTellerid(toucsString.unConvert(rst.getString("teller_id")));
    	setStartdate(toucsString.unConvert(rst.getString("start_date")));
    	setEnddate(toucsString.unConvert(rst.getString("end_date")));
    	setStarttime(toucsString.unConvert(rst.getString("start_time")));
    	setEndtime(toucsString.unConvert(rst.getString("end_time")));
    	setUseflag(toucsString.unConvert(rst.getString("use_flag")));
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
	   * 创建用于添加的动态SQL语句
	   * @param conn 数据库连接对象
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		String sql = "INSERT INTO pos_enrol VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		System.out.println("INSERT POSINFO:" + sql);
		//取得POSP编号
		pos_c_code = pos_code.substring(1, 9);
		stm.setString(1, pos_code);
		stm.setString(2, merchant_id);
		
		//默认状态为启用
		stm.setString(3, "1");
		
		return stm;
	}

	  /**
	   * 创建用于修改的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE pos_enrol SET " + "(branch_id,teller_id,start_date,end_date,start_time,end_time,use_flag,"
				   + "memo1,memo2,memo3)"
				   + " =(?,?,?,?,?,?,?,?,?,?)"
				   + " WHERE pos_code=?";
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
	   * 创建用于设置启用停用状态的的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeStatStm(Connection conn, String key,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		} else {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, key);
		
		return stm;
	}

	  /**
	   * 创建用于根据商户查询的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeQueryByMctStm(Connection conn, String key) throws SQLException {
		String sql = "select * from pos_info WHERE (merchant_id=?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, key);
		return stm;
	}

	  /**
	   * 创建用于删除的动态SQL语句
	   * @param conn 数据库连接对象
	   * @param key 设备编号（关键字）
	   * @return 动态SQL语句对象
	   * @throws SQLException
	   */
	public PreparedStatement makeDeleteStm(Connection conn, String key) throws SQLException {
		String sql = "DELETE FROM pos_enrol WHERE " + "(pos_code = ?)";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		//删除的主键
		stm.setString(1, key);
		return stm;
	}
}