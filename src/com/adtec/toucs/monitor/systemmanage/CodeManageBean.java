package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;


import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ���������</p>
 * <p>Description:��װϵͳ���������ص�ҵ���߼���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:adtec </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeManageBean {
	// �����
	private static Properties codeTable = new Properties();

	// ���������б�
	public static List typeList;
	private static final String ORGISNO = "NO"; // ��ʼϵͳ�����������

  /**
   * ���췽��
   */
	public CodeManageBean() {
	}

  /**
   * ��ʼ��ϵͳ����� 
   * @throws MonitorException
   */
	public static void initCodeTable() throws MonitorException {
		Debug.println("��ʼ��ϵͳ����...");
		codeTable.clear();
		// ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		String type = "";
		String code = "";
		String desc = "";
		try {
			// ��ѯ�����
			String sql = "SELECT * FROM kernel_code";
			ResultSet rst = sq.queryselect(sql);
			while (rst.next()) {
				type = Util.getString(rst, "code_type", "");
				code = Util.getString(rst, "sys_code", "");
				desc = Util.getString(rst, "code_desc", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// ��ѯ������
			sql = "SELECT org_id,org_name,org_level FROM monit_orginfo ORDER BY org_level,org_id";
			rst = sq.queryselect(sql);
			type = "orgname";
			while (rst.next()) {
				code = Util.getString(rst, "org_id", "");
				desc = Util.getString(rst, "org_name", "");
				codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
			}
			rst.close();
			// ��ѯ�����
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
			// ��ѯ���״����
			sql = "SELECT sys_id,txn_code,txn_name FROM kernel_txn_code ORDER BY sys_id";
			rst = sq.queryselect(sql);
			while (rst.next()) {
				type = Util.getString(rst, "sys_id", "");// atm,cdm,pos,pem,mit��
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
		Debug.println("��ʼ��ϵͳ�������...");
	}

  /**
   * ȡ��������
   * @param type��������
   * @param code����
   * @return ��������
   */
	public static String getCodeDesc(String type, String code) {
		Debug.println("[" + type + "." + code + "]");
		String desc = (String) codeTable.get(type + "." + code);
		if (desc == null)
			desc = code;
		return desc;
	}

  /**
   * codeTable������ֵ
   * @param type��������
   * @param code����
   * @param desc����
   * @return
   * @author liyp 20030702
   */
	public static void codeTableAdd(String type, String code, String desc) {
		Debug.println("Add Code [" + type + "." + code + "]");
		codeTable.put(type + "." + code.trim(), toucsString.unConvert(desc));
	}

  /**
   * codeTableɾ��ֵ
   * @param type��������
   * @param code����
   * @return ��������
   * @author liyp 20030702
   */
	public static void codeTableDel(String type, String code) {
		Debug.println("Delete Code [" + type + "." + code + "]");
		codeTable.remove(type + "." + code.trim());
	}

  /**
   * ��ѯָ���������
   * @param type��������
   * @param sq���ݿ���ʶ���
   * @return �����б�
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
   * ��ѯָ���������(������)syl
   * 20120705
   * @param type��������
   * @param sq���ݿ���ʶ���
   * @return �����б�
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
   * ��ѯָ���������
   * @param type��������
   * @param sq���ݿ���ʶ���
   * @return �����б�
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
   * ��ѯָ���������
   * @param type��������
   * @param sq���ݿ���ʶ���
   * @return �����б�
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
   * ��ѯָ������Ĵ���
   * @param type��������
   * @return �����б�
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
   * ȡ�����б�
   * @return �����б�
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
   * ��Ӵ���
   * @param code������Ϣ
   * @return ��ӳɹ�����1
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
   * �޸Ĵ���
   * @param code������Ϣ
   * @param id�����ţ�key��
   * @return �޸ĳɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
   * ɾ������
   * @param type��������
   * @param id������
   * @return ɾ���ɹ�����1
   * @throws MonitorException���ϵͳ�쳣
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
   * ��ʼ�����������б�
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
   * ��������Ļ�����ѯ�¼��Ļ���ID
   * @param org_id�����û�����������
   * @param conn���ݿ�����
   * @return ��ѯ���Ļ���ID
   * @throws MonitorException
   */
	public static List queryOrgList(String org_id, Connection conn) throws MonitorException {
		List retList = new ArrayList();
		if (org_id == null || org_id.trim().length() == 0)
			return retList;
		org_id = org_id.trim();
		String orgName = null;

		// ��ѯ����������
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
   * ��������Ļ�����ѯ�¼��Ļ���ID
   * @param org_id�����û�����������
   * @return ��ѯ���Ļ���ID
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
	 * ��ѯ����
	 * @param level���������б�
	 * @param pOrgId�ϼ��������
	 * @param sq���ݿ���ʶ���
	 * @return ���������Ļ����б�
	 * @throws SQLException ���ݿ��쳣
	 */
	public static List queryOrg(int[] level, String pOrgId, SqlAccess sq) throws SQLException {
		List ret = new ArrayList();
		if (pOrgId == null)
			pOrgId = "";
		String sql = "SELECT a.org_id,a.org_name,a.p_org_id FROM monit_orginfo a";
		String condition = "";
		if (!pOrgId.equals(""))
			sql += ",monit_orgref b";

		// �趨����Χ
		if (level != null && level.length > 0) {
			condition += "a.org_level IN(";
			for (int i = 0; i < level.length; i++)
				condition += String.valueOf(level[i]) + ",";
				condition = condition.substring(0, condition.length() - 1);
				condition += ")";

				if (!pOrgId.equals(""))
					condition += " AND ";
		}
		// �������¼���ϵ
		if (!pOrgId.equals("")){
			condition += "b.p_org_id='" + pOrgId + "' AND a.org_id=b.org_id";
		}
		if (!condition.equals("")){
			sql += " WHERE " + condition;
		}
		// add by liyp 20031020 add sort conditon
		sql += " ORDER BY a.org_id";

		Debug.println("[��ѯ����]" + sql);
		
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

		// ��ѯ����������
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
