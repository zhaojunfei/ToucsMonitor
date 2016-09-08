package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.common.toucsString;

public class POSPublicApp {

	// AID
	private String aid;

	// 应用选择指示符
	private String asi;

	// 应用版本号
	private String appedition;

	// TAC-缺省
	private String tac_default;

	// TAC-联机
	private String tac_online;

	// TAC-拒绝
	private String tac_refuse;

	// 终端最低限额
	private String floorlimit;

	// 偏置随机选择的阀值
	private String cdoor;

	// 偏置随机选择的最大目标百分数
	private String maxpercent;

	// 随机选择的目标百分数
	private String percent;

	// 缺省DDOL
	private String ddol;

	// 终端联机PIN支持能力
	private String pinability;

	// 创建日期
	private String create_date;

	// 备注1
	private String memo1;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAppedition() {
		return appedition;
	}

	public void setAppedition(String appedition) {
		this.appedition = appedition;
	}

	public String getAsi() {
		return asi;
	}

	public void setAsi(String asi) {
		this.asi = asi;
	}

	public String getCdoor() {
		return cdoor;
	}

	public void setCdoor(String cdoor) {
		this.cdoor = cdoor;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getDdol() {
		return ddol;
	}

	public void setDdol(String ddol) {
		this.ddol = ddol;
	}

	public String getFloorlimit() {
		return floorlimit;
	}

	public void setFloorlimit(String floorlimit) {
		this.floorlimit = floorlimit;
	}

	public String getMaxpercent() {
		return maxpercent;
	}

	public void setMaxpercent(String maxpercent) {
		this.maxpercent = maxpercent;
	}

	public String getMemo1() {
		return memo1;
	}

	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getPinability() {
		return pinability;
	}

	public void setPinability(String pinability) {
		this.pinability = pinability;
	}

	public String getTac_default() {
		return tac_default;
	}

	public void setTac_default(String tac_default) {
		this.tac_default = tac_default;
	}

	public String getTac_online() {
		return tac_online;
	}

	public void setTac_online(String tac_online) {
		this.tac_online = tac_online;
	}

	public String getTac_refuse() {
		return tac_refuse;
	}

	public void setTac_refuse(String tac_refuse) {
		this.tac_refuse = tac_refuse;
	}

	/**
	 * 从Http请求中取EMV卡参数信息
	 * @param request Http请求
	 */
	public void fetchData(HttpServletRequest request) {
		setAid(request.getParameter("aid"));
		setAsi(request.getParameter("asi"));
		setAppedition(request.getParameter("appedition"));
		setTac_default(request.getParameter("tac_default"));
		setTac_online(request.getParameter("tac_online"));
		setTac_refuse(request.getParameter("tac_refuse"));
		setFloorlimit(request.getParameter("floorlimit"));
		setCdoor(request.getParameter("cdoor"));
		setMaxpercent(request.getParameter("maxpercent"));
		setPercent(request.getParameter("percent"));
		setDdol(request.getParameter("ddol"));
		setPinability(request.getParameter("pinability"));
		setCreate_date(request.getParameter("create_date"));
		setMemo1(request.getParameter("memo1"));
	}

	/**
	 * 从查询结果中取EMV卡参数信息
	 * @param rst 查询结果集
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setAid(toucsString.unConvert(rst.getString("aid")));
		setAsi(toucsString.unConvert(rst.getString("asi")));
		setAppedition(toucsString.unConvert(rst.getString("appedition")));
		setTac_default(toucsString.unConvert(rst.getString("tac_default")));
		setTac_online(toucsString.unConvert(rst.getString("tac_online")));
		setTac_refuse(toucsString.unConvert(rst.getString("tac_refuse")));
		setFloorlimit(toucsString.unConvert(rst.getString("floorlimit")));
		setCdoor(toucsString.unConvert(rst.getString("cdoor")));
		setMaxpercent(toucsString.unConvert(rst.getString("maxpercent")));
		setPercent(toucsString.unConvert(rst.getString("percent")));
		setDdol(toucsString.unConvert(rst.getString("ddol")));
		setPinability(toucsString.unConvert(rst.getString("pinability")));
		setCreate_date(toucsString.unConvert(rst.getString("create_date")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
	}

	/**
	 * 添加EMV卡参数信息
	 * @param sq  数据库访问对象
	 * @return 成功返回1
	 * @throws SQLException
	 */
	public int insert(SqlAccess sq) throws SQLException {
		String sql = "INSERT INTO pos_public_app VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = sq.conn.prepareStatement(sql);
		stm.setString(1, aid);
		stm.setString(2, asi);
		stm.setString(3, appedition);
		stm.setString(4, tac_default);
		stm.setString(5, tac_online);
		stm.setString(6, tac_refuse);
		stm.setString(7, floorlimit);
		stm.setString(8, cdoor);
		stm.setString(9, maxpercent);
		stm.setString(10, percent);
		stm.setString(11, ddol);
		stm.setString(12, pinability);
		stm.setString(13, create_date);
		stm.setString(14, memo1);

		return stm.executeUpdate();
	}

	/**
	 * 创建用于添加的动态SQL语句 
	 * @param conn 数据库连接对象
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeInsertStm(Connection conn)
			throws SQLException, MonitorException {
		String sql = "INSERT INTO pos_public_app VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT pos_public_app:" + sql);
		// 默认状态为启用
		stm.setString(3, "1");
		return stm;
	}

	/**
	 * 创建用于修改的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param aid EMV卡参数编号（关键字）
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String aid,String memo1) throws SQLException, MonitorException {
		String sql = "UPDATE pos_public_app SET "
				   + "(asi,appedition,tac_default,tac_online,tac_refuse,"
			       + "floorlimit,cdoor,maxpercent,percent,ddol,pinability,create_date,memo1)"
			       + "=(?,?,?,?,?,?,?,?,?,?,?,?,?) " + " WHERE aid=?";
		PreparedStatement stm = conn.prepareStatement(sql);

		stm.setString( 1, asi);
		stm.setString( 2, appedition);
		stm.setString( 3, tac_default);
		stm.setString( 4, tac_online);
		stm.setString( 5, tac_refuse);
		stm.setString( 6, floorlimit);
		stm.setString( 7, cdoor);
		stm.setString( 8, maxpercent);
		stm.setString( 9, percent);
		stm.setString(10, ddol);
		stm.setString(11, pinability);
		stm.setString(12, create_date);
		stm.setString(13, memo1);
		stm.setString(14, aid);

		return stm;
	}

	/**
	 * 创建用于设置启用停用状态的的动态SQL语句
	 * @param conn 数据库连接对象 aid EMV卡参数编号（关键字）
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeStatStm(Connection conn, String aid,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		} else {
			sql = "UPDATE pos_enrol SET " + "use_flag='0'" + " WHERE pos_code=?";
		}

		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, aid);
		return stm;
	}

	/**
	 * 创建用于根据EMV编号查询的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param aid EMV编号（关键字）
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeQueryByMctStm(Connection conn, String aid) throws SQLException {
		String sql = "SELECT * FROM pos_public_app WHERE aid=?";
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, aid);
		return stm;
	}

	/**
	 * 创建用于删除的动态SQL语句
	 * @param conn 数据库连接对象
	 * @param aid EMV编号（关键字
	 * @return 动态SQL语句对象
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String aid) throws SQLException {
		String sql = "DELETE FROM pos_public_app WHERE aid =?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		// 删除的主键
		stm.setString(1, aid);
		return stm;
	}
}