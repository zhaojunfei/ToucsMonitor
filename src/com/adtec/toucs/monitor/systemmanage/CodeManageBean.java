package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;


import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 代码管理类</p>
 * <p>Description:封装系统代码管理相关的业务逻辑。</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:adtec </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeManageBean {
	// 代码表
	private static Properties codeTable = new Properties();

	// 代码种类列表
	public static List typeList;
	private static final String ORGISNO = "NO"; // 初始系统管理机构代码

  /**
   * 构造方法
   */
	public CodeManageBean() {
	}

  /**
   * 初始化系统代码表 
   * @throws MonitorException
   */
	public static void initCodeTable() throws MonitorException {
		Debug.println("初始化系统代码...");
		codeTable.clear();
		// 取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		String type = "";
		String code = "";
		String desc = "";
		try {
			// 查询代码表
			String sql = "SELECT * FROM kernel_code";
			ResultSet rst = sq.queryselect(sql);
			while (rst.next()) {
				type = Util.getString(rst, "code_type", "");
				code = Util.getString(rst, "sys_code", "");
				desc = Util.getString(rst, "code_desc", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// 查询机构表
			sql = "SELECT org_id,org_name,org_level FROM monit_orginfo ORDER BY org_level,org_id";
			rst = sq.queryselect(sql);
			type = "orgname";
			while (rst.next()) {
				code = Util.getString(rst, "org_id", "");
				desc = Util.getString(rst, "org_name", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// 查询区域表
			sql = "SELECT area_id,area_name FROM monit_area ORDER BY area_id";
			rst = sq.queryselect(sql);
			type = "areaname";
			while (rst.next()) {
				code = Util.getString(rst, "area_id", "");
				desc = Util.getString(rst, "area_name", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// begin add by liyp 20030702
			// 查询交易代码表
			sql = "SELECT sys_id,txn_code,txn_name FROM kernel_txn_code ORDER BY sys_id";
			rst = sq.queryselect(sql);
			while (rst.next()) {
				type = Util.getString(rst, "sys_id", "");// atm,cdm,pos,pem,mit等
				code = Util.getString(rst, "txn_code", "");
				desc = Util.getString(rst, "txn_name", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// end add by liyp 20030702
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
		Debug.println("初始化系统代码完毕...");
	}

  /**
   * 取代码描述
   * @param type代码类型
   * @param code代码
   * @return 代码描述
   */
	public static String getCodeDesc(String type, String code) {
		Debug.println("[" + type + "." + code + "]");
		String desc = (String) codeTable.get(type + "." + code);
		if (desc == null)
			desc = code;
		return desc;
	}

  /**
   * codeTable增加新值
   * @param type代码类型
   * @param code代码
   * @param desc描述
   * @return
   * @author liyp 20030702
   */
	public static void codeTableAdd(String type, String code, String desc) {
		Debug.println("Add Code [" + type + "." + code + "]");
		codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
	}

  /**
   * codeTable删除值
   * @param type代码类型
   * @param code代码
   * @return 代码描述
   * @author liyp 20030702
   */
	public static void codeTableDel(String type, String code) {
		Debug.println("Delete Code [" + type + "." + code + "]");
		codeTable.remove(type + "." + code.trim());
	}

  /**
   * 查询指定种类代码
   * @param type代码种类
   * @param sq数据库访问对象
   * @return 代码列表
   * @throws SQLException
   */
	public static Vector queryCodes(String type, SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_code WHERE code_type='" + type + "' ORDER BY sys_code");
		Vector v = new Vector();
		while (rst.next()) {
			CodeBean code = new CodeBean();
			code.fetchData(rst);
			v.add(code);
		}
		rst.close();
		return v;
	}

  /**
   * 查询指定种类代码(第三方)syl
   * 20120705
   * @param type代码种类
   * @param sq数据库访问对象
   * @return 代码列表
   * @throws SQLException
   */
	public static Vector queryNewCodes(String type, SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT para_name,para_desc,para_val FROM t_para_config WHERE para_name like'" + type + "%'");
		Vector v = new Vector();
		while (rst.next()) {
			CodeBean code = new CodeBean();
			code.fetchNewData(rst);
			v.add(code);
		}
		rst.close();
		return v;
	}
  /**
   * 查询指定种类代码
   * @param type代码种类
   * @param sq数据库访问对象
   * @return 代码列表
   * @throws SQLException
   */
	public static Vector queryMerchantCodes(SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT A.merchant_id,A.mct_name FROM pos_merchant A");
		Vector v = new Vector();
		while (rst.next()) {
			CodeBean code = new CodeBean();
			code.fetchData(rst);
			v.add(code);
		}
		rst.close();
		return v;
	}

  /**
   * 查询指定种类代码
   * @param type代码种类
   * @param sq数据库访问对象
   * @return 代码列表
   * @throws SQLException
   */
	public static Vector queryCodesNo4(String type, SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_code WHERE code_type='" + type + "' ORDER BY sys_code");
		Vector v = new Vector();
		int i = 1;
		while (rst.next()) {
			if (i == 4)
				break;
			CodeBean code = new CodeBean();
			code.fetchData(rst);
			v.add(code);
			i++;
		}
		rst.close();
		return v;
	}

  /**
   * 查询指定种类的代码
   * @param type代码种类
   * @return 代码列表
   */
	public static Vector queryCodes(String type) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			Vector v = queryCodes(type, sq);
			return v;
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

  /**
   * 取区域列表
   * @return 区域列表
   * @throws MonitorException
   */
	public static Vector queryArea(SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM monit_area");
		Vector v = new Vector();
		while (rst.next()) {
			CodeBean code = new CodeBean();
			code.setCodeId(rst.getString("area_id"));
			code.setCodeDesc(toucsString.unConvert(rst.getString("area_name")));
			v.add(code);
		}
		return v;
	}

  /**
   * 添加代码
   * @param code代码信息
   * @return 添加成功返回1
   * @throws MonitorException
   */
	public int addCode(CodeBean code) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = code.insert(sq);
			sq.commit();
			codeTable.put(code.getKey(), code.getCodeDesc());
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 修改代码
   * @param code代码信息
   * @param id代码编号（key）
   * @return 修改成功返回1
   * @throws MonitorException监控系统异常
   */
	public int modeCode(CodeBean code, String id) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = code.update(sq, id);
			sq.commit();
			code.setCodeId(id);
			codeTable.put(code.getKey(), code.getCodeDesc());
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 删除代码
   * @param type代码类型
   * @param id代码编号
   * @return 删除成功返回1
   * @throws MonitorException监控系统异常
   */
	public int deleteCode(String type, String id) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "DELETE FROM kernel_code WHERE code_type=? AND sys_code=?";
			PreparedStatement stm = sq.conn.prepareStatement(sql);
			stm.setString(1, type);
			stm.setString(2, id);
			sq.setAutoCommit(false);
			int i = stm.executeUpdate();
			sq.conn.commit();
			codeTable.remove(type + "." + id);
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 初始化代码种类列表
   */
	public static void initCodeTypes() {
		try {
			List listSys = queryCodes(CodeDefine.SYS_CLASS);
			typeList = new ArrayList(listSys);
		} catch (MonitorException exp) {

		}
	}

	// /lichj add the code
  /**
   * 根据输入的机构查询下级的机构ID
   * @param org_id操作用户的所属机构
   * @param conn数据库连接
   * @return 查询到的机构ID
   * @throws MonitorException
   */
	public static List queryOrgList(String org_id, Connection conn) throws MonitorException {
		List retList = new ArrayList();
		if (org_id == null || org_id.trim().length() == 0)
			return retList;
		org_id = org_id.trim();
		String orgName = null;

		// 查询下属机构名
		String oneSql = "";
		if (org_id.equals(ORGISNO)) {
			oneSql = "select org_id,org_name,org_level from monit_orginfo ";
			oneSql = oneSql + " order by org_level,org_id";
		} else {
			oneSql = "select a.org_id,a.org_name,a.org_level from monit_orginfo a,monit_orgref b";
			oneSql = oneSql + " where a.org_id=b.org_id and b.p_org_id='";
			oneSql = oneSql + org_id + "' order by a.org_level,a.org_id"; 
		}
		try {
			Statement stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(oneSql);
			while (rst.next()) {
				orgName = toucsString.unConvert(rst.getString("org_name"));
				org_id = toucsString.unConvert(rst.getString("org_id"));
				CodeBean orgDeviceCode = new CodeBean();
				orgDeviceCode.setCodeId(org_id);
				orgDeviceCode.setCodeDesc(orgName);
				retList.add(orgDeviceCode);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		}
		return retList;
	}

  /**
   * 根据输入的机构查询下级的机构ID
   * @param org_id操作用户的所属机构
   * @return 查询到的机构ID
   * @throws MonitorException
   */
	public static List queryOrgList(String org_id) throws MonitorException {
		SqlAccess sq = new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn = sq.conn;
		try {
			return queryOrgList(org_id, conn);
		} catch (MonitorException exp) {
			throw exp;
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 查询机构
	 * @param level机构级别列表
	 * @param pOrgId上级机构编号
	 * @param sq数据库访问对象
	 * @return 满足条件的机构列表
	 * @throws SQLException 数据库异常
	 */
	public static List queryOrg(int[] level, String pOrgId, SqlAccess sq) throws SQLException {
		List ret = new ArrayList();
		if (pOrgId == null)
			pOrgId = "";
		String sql = "SELECT a.org_id,a.org_name,a.p_org_id FROM monit_orginfo a";
		String condition = "";
		if (!pOrgId.equals(""))
			sql += ",monit_orgref b";

		// 设定级别范围
		if (level != null && level.length > 0) {
			condition += "a.org_level IN(";
			for (int i = 0; i < level.length; i++)
				condition += String.valueOf(level[i]) + ",";
				condition = condition.substring(0, condition.length() - 1);
				condition += ")";

				if (!pOrgId.equals(""))
					condition += " AND ";
		}
		// 满足上下级关系
		if (!pOrgId.equals("")){
			condition += "b.p_org_id='" + pOrgId + "' AND a.org_id=b.org_id";
		}
		if (!condition.equals("")){
			sql += " WHERE " + condition;
		}
		// add by liyp 20031020 add sort conditon
		sql += " ORDER BY a.org_id";

		Debug.println("[查询机构]" + sql);
		
		ResultSet rst = sq.queryselect(sql);
		String orgID, orgName, superOrgId;
		while (rst.next()) {
			orgID = Util.getString(rst, "org_id", "");
			orgName = toucsString.unConvert(Util.getString(rst, "org_name", ""));
			superOrgId = Util.getString(rst, "p_org_id", "");
			CodeBean code = new CodeBean();
			code.setCodeType(superOrgId);
			code.setCodeId(orgID);
			code.setCodeDesc(orgName);
			ret.add(code);
		}
		return ret;
	}


	public static void main(String[] args) {
		try {
			int[] level = { 2, 3 };
			CodeManageBean.queryOrg(level, "", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List queryOrgListPos(String org_id) throws MonitorException {
		SqlAccess sq = new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn = sq.conn;
		try {
			return queryOrgListPos(org_id, conn);
		} catch (MonitorException exp) {
			throw exp;
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static List queryOrgListPos(String org_id, Connection conn) throws MonitorException {
		List retList = new ArrayList();
		if (org_id == null || org_id.trim().length() == 0)
			return retList;
		org_id = org_id.trim();
		String orgName = null;

		// 查询下属机构名
		String oneSql = "";
		if (org_id.equals(ORGISNO)) {
			oneSql = "select org_id,org_name,org_level from monit_orginfo ";
			oneSql = oneSql + " order by org_level,org_id";
		} else {
			oneSql = "select a.org_id,a.org_name,a.org_level from monit_orginfo a,monit_orgref b";
			oneSql = oneSql + " where a.org_id=b.org_id and b.p_org_id='";
			oneSql = oneSql + org_id + "' order by a.org_level,a.org_id"; 
		}
		try {
			Statement stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery(oneSql);
			while (rst.next()) {
				orgName = toucsString.unConvert(rst.getString("org_name"));
				org_id = toucsString.unConvert(rst.getString("org_id"));
				orgName = org_id + "-" + orgName;
				CodeBean orgDeviceCode = new CodeBean();
				orgDeviceCode.setCodeId(org_id);
				orgDeviceCode.setCodeDesc(orgName);
				retList.add(orgDeviceCode);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		}
		return retList;
	}
}
