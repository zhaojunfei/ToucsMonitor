package com.adtec.toucs.monitor.POSDeviceManage;


import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class POSMerchantInfo {

	//商户号
	private String merchant_id = "";
	//商户名称
	private String mct_name = "";
	//商户英文名
	private String mct_enname = "";
	//POS币种
	private String curr_type = "";
	//标志
	//1正常2停用
	private String flag = "";
	//商户类型
	//1宾馆、2百货、3餐饮娱乐、4公司、5房地产、6汽车、7其他
	private String mct_type = "";
	//主管支行号
	private String manage_bankno = "";
	//主管支行名称
	private String manage_bankname = "";
	//操作员
	private String operator = "";
	//机构号
	private String org_id = "";
	//压卡机数
	private String pcard_machine_no = "";
	//POS机数
	private String pos_machine_no = "";
	//电话
	private String telephone = "";
	//传真
	private String fax = "";
	//邮编
	private String zipcode = "";
	//地址
	private String address = "";
	//法人代表
	private String artificial_person = "";
	//经办人
	private String handle_man = "";
	//签约日期
	private String signature_date = "";
	//撤销日期
	private String cancel_date = "";
	//清理标志
	//1.正常2.清理
	private String clear_flag = "";
	
	public POSMerchantInfo() {
	}

	//商户号读写
	public void setMerchantid(String Merchantid) {
		merchant_id = Merchantid;
	}

	public String getMerchantid() {
		return merchant_id;
	}

	//商户名称读写
	public void setMctname(String Mctname) {
		mct_name = Mctname;
	}

	public String getMctname() {
		return mct_name;
	}

	//商户英文名读写
	public void setMctenname(String Mctenname) {
		mct_enname = Mctenname;
	}

	public String getMctenname() {
		return mct_enname;
	}

	//POS币种读写
	public void setCurrtype(String Currtype) {
		curr_type = Currtype;
	}

	public String getCurrtype() {
		return curr_type;
	}

	//标志读写
	public void setFlag(String Flag) {
		flag = Flag;
	}

	public String getFlag() {
		return flag;
	}

	//商户类型读写
	public void setMcttype(String Mcttype) {
		mct_type = Mcttype;
	}

	public String getMcttype() {
		return mct_type;
	}

	//主管支行号读写
	public void setManagebankno(String Managebankno) {
		manage_bankno = Managebankno;
	}

	public String getManagebankno() {
		return manage_bankno;
	}

	//主管支行名称读写
	public void setManagebankname(String Managebankname) {
		manage_bankname = Managebankname;
	}

	public String getManagebankname() {
		return manage_bankname;
	}

	//操作员读写
	public void setOperator(String Operator) {
		operator = Operator;
	}

	public String getOperator() {
		return operator;
	}

	//机构号读写
	public void setOrgid(String Orgid) {
		org_id = Orgid;
	}

	public String getOrgid() {
		return org_id;
	}

	//压卡机数读写
	public void setPcardmachine_no(String Pcardmachine_no) {
		pcard_machine_no = Pcardmachine_no;
	}

	public String getPcardmachine_no() {
		return pcard_machine_no;
	}

	//POS机数读写
	public void setPosmachine_no(String Posmachine_no) {
		pos_machine_no = Posmachine_no;
	}

	public String getPosmachine_no() {
		return pos_machine_no;
	}

	//电话读写
	public void setTelephone(String Telephone) {
		telephone = Telephone;
	}

	public String getTelephone() {
		return telephone;
	}

	//传真读写
	public void setFax(String Fax) {
		fax = Fax;
	}

	public String getFax() {
		return fax;
	}

	//邮编读写
	public void setZipcode(String Zipcode) {
		zipcode = Zipcode;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	//地址读写
	public void setAddress(String Address) {
		address = Address;
	}

	public String getAddress() {
		return address;
	}

	//法人代表读写
	public void setArtificialperson(String Artificialperson) {
		artificial_person = Artificialperson;
	}

	public String getArtificialperson() {
		return artificial_person;
	}

	//经办人读写
	public void setHandleman(String Handleman) {
		handle_man = Handleman;
	}

	public String getHandleman() {
		return handle_man;
	}

	//签约日期读写
	public void setSignaturedate(String Signaturedate) {
		signature_date = Signaturedate;
	}	

	public String getSignaturedate() {
		return signature_date;
	}

	//撤销日期读写
	public void setCanceldate(String Canceldate) {
		cancel_date = Canceldate;
	}
	
	public String getCanceldate() {
		return cancel_date;
	}

	//清理标志读写
	public void setClearflag(String Clearflag) {
		clear_flag = Clearflag;
	}

	public String getClearflag() {
		return clear_flag;
	}

  /**
   * 从Http请求中取ATM基本信息
   * @param request Http请求
   */
	public void fetchData(HttpServletRequest request) {
		//商户号
		setMerchantid(request.getParameter("merchant_id"));
		//商户名称
		setMctname(request.getParameter("mct_name"));
		//商户英文名
		setMctenname(request.getParameter("mct_enname"));
		//POS票据打印名称
		setCurrtype(request.getParameter("curr_type"));
		//标志
		setFlag(request.getParameter("flag"));
		//商户类型
		setMcttype(request.getParameter("mct_type"));
		//主管支行号
		setManagebankno(request.getParameter("manage_bankno"));
		//主管支行名称
		setManagebankname(request.getParameter("managebankname_temp"));
		//操作员
		setOperator(request.getParameter("operator"));
		//机构号
		setOrgid(request.getParameter("org_id"));
		//压卡机数
		setPcardmachine_no(request.getParameter("pcard_machine_no"));		
		//POS机数
		setPosmachine_no(request.getParameter("pos_machine_no"));
		//电话
		setTelephone(request.getParameter("telephone"));
		//传真
		setFax(request.getParameter("fax"));
		//邮编
		setZipcode(request.getParameter("zipcode"));
		//地址
		setAddress(request.getParameter("address"));
		//经办人
		setHandleman(request.getParameter("handle_man"));
		//法人代表
		setArtificialperson(request.getParameter("artificial_person"));
		//签约日期
		setSignaturedate(request.getParameter("signature_date"));
		//撤销日期
		setCanceldate(request.getParameter("cancel_date"));
		//清理标志
		setClearflag(request.getParameter("clear_flag"));
	}

  /**
   * 从查询结果中取ATM基本信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException {
		//商户号
		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
		//商户名称
		setMctname(toucsString.unConvert(rst.getString("mct_name")));
		//商户英文名	
		setMctenname(toucsString.unConvert(rst.getString("mct_enname")));
		//POS币	种
		setCurrtype(toucsString.unConvert(rst.getString("curr_type")));
		//标志
		setFlag(toucsString.unConvert(rst.getString("flag")));
		//商户类型
		setMcttype(toucsString.unConvert(rst.getString("mct_type")));
		//主管支行号	
		setManagebankno(toucsString.unConvert(rst.getString("manage_bankno")));
		//主管支行名称
		setManagebankname(toucsString.unConvert(rst.getString("manage_bankname")));
		//操作员
		setOperator(toucsString.unConvert(rst.getString("operator")));	
		//机构号
		setOrgid(toucsString.unConvert(rst.getString("org_id")));
		//压卡机数
		setPcardmachine_no(toucsString.unConvert(rst.getString("pcard_machine_no")));
		//POS机数
		setPosmachine_no(toucsString.unConvert(rst.getString("pos_machine_no")));
		//电话
		setTelephone(toucsString.unConvert(rst.getString("telephone")));
		//传真
		setFax(toucsString.unConvert(rst.getString("fax")));
		//邮	编
		setZipcode(toucsString.unConvert(rst.getString("zipcode")));
		//地址
		setAddress(toucsString.unConvert(rst.getString("address")));
		//经办人
		setHandleman(toucsString.unConvert(rst.getString("handle_man")));	
		//法人代表
		setArtificialperson(toucsString.unConvert(rst.getString("artificial_person")));
		//签约日期	
		setSignaturedate(toucsString.unConvert(rst.getString("signature_date")));
		//撤销日期
		setCanceldate(toucsString.unConvert(rst.getString("cancel_date")));
		//清理标志
		setClearflag(toucsString.unConvert(rst.getString("clear_flag")));
	}

  /**
   * 创建用于添加的动态SQL语句
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException {
		String sql = "INSERT INTO pos_merchant("
				   + "merchant_id,mct_name,mct_enname,curr_type,flag,"
				   + "mct_type,manage_bankno,manage_bankname,operator,org_id,"
				   + "pcard_machine_no,pos_machine_no,telephone,fax,zipcode,"
				   + "address,artificial_person,handle_man,signature_date,cancel_date,"
				   + "clear_flag)" + " VALUES(?,?,?,?,?," + "?,?,?,?,?,"
				   + "?,?,?,?,?," + "?,?,?,?,?," + "'1')";
		System.out.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println(stm.toString());
		
		//商户号
		stm.setString(1, merchant_id);
		//商户名称
		stm.setString(2, mct_name);	
		//商户英文名
		stm.setString(3, mct_enname);
		//POS票据打印名称
		stm.setString(4, curr_type);
		//标志
		stm.setString(5, flag);
		//商户类型
		stm.setString(6, mct_type);
		//主管支行号
		stm.setString(7, manage_bankno);
		//主管支行名称
		stm.setString(8, manage_bankname);
		//操作员
		stm.setString(9, operator);
		//机构号
		stm.setString(10, org_id);
		//压卡机数
		stm.setString(11, pcard_machine_no);
		//POS机数
		stm.setString(12, pos_machine_no);
		//电话
		stm.setString(13, telephone);
		//传真
		stm.setString(14, fax);
		//邮编
		stm.setString(15, zipcode);
		//地址
		stm.setString(16, address);
		//经办人
		stm.setString(17, handle_man);
		//法人代表
		stm.setString(18, artificial_person);
		//签约日期
		stm.setString(19, signature_date);
		//撤销日期
		stm.setString(20, cancel_date);
		return stm;
	}

  /**
   * 创建用于修改的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET("
				   + "mct_name,mct_enname,curr_type,flag,"
				   + "mct_type,manage_bankno,manage_bankname,operator,org_id,"
				   + "pcard_machine_no,pos_machine_no,telephone,fax,zipcode,"
				   + "address,artificial_person,handle_man,signature_date,cancel_date"
				   + ")" + " =(?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?,"
				   + "?,?,?,?,?" + ")" + "WHERE merchant_id = ?";
		System.out.println("UPDATE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//商户名称	
		stm.setString(1, mct_name);
		//商户英文名
		stm.setString(2, mct_enname);
		//POS票据打印名称
		stm.setString(3, curr_type);
		//标志
		stm.setString(4, flag);
		//商户类型
		stm.setString(5, mct_type);
		//主管支行号
		stm.setString(6, manage_bankno);
    	//主管支行名称
		stm.setString(7, manage_bankname);
		//操作员
		stm.setString(8, operator);
		//机构号
		stm.setString(9, org_id);
		//压卡机数
		stm.setString(10, pcard_machine_no);
		//POS机数
		stm.setString(11, pos_machine_no);
		//电话
		stm.setString(12, telephone);
		//传真
		stm.setString(13, fax);
		//邮编
		stm.setString(14, zipcode);
		//地址
		stm.setString(15, address);
		//法人代表
		stm.setString(16, artificial_person);
		//经办人
		stm.setString(17, handle_man);
		//签约日期
		stm.setString(18, signature_date);
		//撤销日期
		stm.setString(19, cancel_date);

		//修改的主键
		stm.setString(20, key);
		System.out.println("UPDATE KEY:" + key);
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
		String sql = "DELETE FROM pos_merchant WHERE (clear_flag = '2')" + "AND(merchant_id = ?)";
		Debug.println("it is a new World!!!!!");
		PreparedStatement stm = conn.prepareStatement(sql);
		//修改的主键
		stm.setString(1, key);
		Debug.println("the key is :" + key);	
		System.out.println("DELETE SQL:" + sql);
		return stm;
	}

  /**
   * 创建用于删除的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeClearStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET clear_flag = '2' " + "WHERE (merchant_id = ?)";
		System.out.println("CLEAR SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//修改的主键
		stm.setString(1, key);
		System.out.println("KEY:" + key);
		return stm;
	}

  /**
   * 创建用于删除的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeNormStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET clear_flag = '1' " + "WHERE (merchant_id = ?)";
		System.out.println("CLEAR SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//修改的主键
		stm.setString(1, key);
		System.out.println("KEY:" + key);
		return stm;
	}
	
	public String getOrg_Name() throws MonitorException {
		String OrgName = "";
		SqlAccess sq = SqlAccess.createConn();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT org_name FROM monit_orginfo WHERE org_id = '" + org_id + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				OrgName = rs.getString(1);
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return OrgName;
	}
}
