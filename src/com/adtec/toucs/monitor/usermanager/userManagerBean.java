package com.adtec.toucs.monitor.usermanager;

/**
 * <p>Title: UserManager</p>
 * <p>Description: �û���Ϣ����</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author syl
 * @version 2.0
 */

import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.*;
import com.adtec.toucs.monitor.systemmanage.*;


public class userManagerBean {
	/* ���ݿ����� */

	private SqlAccess conn = null;

	private ResultSet rs = null;

	/**
	 * ���췽��
	 */
	public userManagerBean() {
	}

	/**
	 * �����û�ʱ�����ݿ��в���һ����¼
	 * 
	 * @param userId
	 *            �û�ID
	 * @param userName
	 *            �û�����
	 * @param orgId
	 *            ��������
	 * @param userPwd
	 *            ����
	 * @param employDate
	 *            ��Ӷʱ��
	 * @param fireDate
	 *            ���ʱ��
	 * @param userDesc
	 *            ������Ϣ
	 * @return �ɹ���Ӱ���¼��������ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int insertUserInfo(String userId, String userName, String orgId,
			String userPwd, String employDate, String fireDate, String userDesc)
			throws MonitorException {

		DESCryptography DES = new DESCryptography();
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("INSERT INTO monit_userinfo (user_id,user_name,org_id,user_passwd,employ_date,fire_date,user_desc,passwd_num,user_state) VALUES (");
		SqlStr.append("'" + userId);
		SqlStr.append("','" + userName);
		SqlStr.append("','" + orgId);
		SqlStr.append("','" + DES.encrypt(userPwd));
		SqlStr.append("','" + employDate);
		SqlStr.append("','" + fireDate);
		SqlStr.append("','" + userDesc);
		SqlStr.append("', 0, 1");
		SqlStr.append(")");

		conn =  SqlAccess.createConn();
		Debug.println(SqlStr.toString());
		int effect = -1;
		try {
			effect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return effect;
	}

	/**
	 * �����ݿ��в���һ����¼(monit_pwd_update_history)
	 * 
	 * @param userId
	 *            �û�ID
	 * @return �ɹ���Ӱ���¼��������ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 *             sunyl
	 */
	public int insertPwdUpdateHistory(String userId, String userPwd, String pwd_userful_date)
			throws MonitorException {

		DESCryptography DES = new DESCryptography();
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("INSERT INTO monit_pwd_update_history (user_id,user_passwd,pwd_userful_date,pwd_createdate,last_login_date,data1,data2,data3) VALUES (");
		SqlStr.append("'" + userId);
		SqlStr.append("','" + DES.encrypt(userPwd));
		SqlStr.append("','" + pwd_userful_date);
		SqlStr.append("','" + Util.getCurrentDate());
		SqlStr.append("','" + Util.getCurrentDate());
		SqlStr.append("','" + "");
		SqlStr.append("','" + "");
		SqlStr.append("','" + "'");
		SqlStr.append(")");

		conn = SqlAccess.createConn();
		Debug.println(SqlStr.toString());
		int effect = -1;
			try {
				effect = conn.queryupdate(toucsString.Convert(SqlStr.toString()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return effect;
	}
	
	
	/**
	 * У�������޸ļ�¼monit_pwd_update_history
	 * @throws SQLException 
	 *             �쳣
	 * @throws SQLException 
	 * @throws MonitorException 
	 * suyl
	 */	
	public int checkPwdHistory(String userId , String passwd) throws SQLException, MonitorException{
		conn = SqlAccess.createConn();
	    int affect = 0;
	    String oldPasswd = null;

	    try {
	    	rs = conn.queryselect("select first 3 * from monit_pwd_update_history where user_id = '"+userId+"' order by last_login_date desc");
			while (rs != null && rs.next()) {
				oldPasswd = new String(rs.getString(2));
				if (oldPasswd != null) {
					DESCryptography DES = new DESCryptography();
					if (oldPasswd.trim().equals(DES.encrypt(passwd).trim())) {
						affect = 1;
						break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return affect;
	}
	
	
	/**
	 * �޸��û���Ϣ
	 * 
	 * @param userId
	 *            �û�ID
	 * @param orgId
	 *            ��������
	 * @param userName
	 *            �û�����
	 * @param employDate
	 *            ��Ӷʱ��
	 * @param fireDate
	 *            ���ʱ��
	 * @param userDesc
	 *            �û�����
	 * @return �ɹ���Ӱ���¼��������ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int modifyUserInfo(String userId, String orgId, String userName,
			String employDate, String fireDate, String userDesc)
			throws MonitorException {

		StringBuffer SqlStr = new StringBuffer(1024);
		SqlStr.append("UPDATE monit_userinfo SET (user_name,employ_date,fire_date,org_id,user_desc) = ('");
		SqlStr.append(userName + "','");
		SqlStr.append(employDate + "','");
		SqlStr.append(fireDate + "','");
		SqlStr.append(orgId + "','");
		SqlStr.append(userDesc + "') WHERE user_id = '");
		SqlStr.append(userId + "'");

		conn = SqlAccess.createConn();
		int effect = -1;
		try {
			effect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return effect;
	}

	/**
	 * ���û�Ȩ�ޱ�����Ӽ�¼
	 * 
	 * @param userId
	 *            �û�ID
	 * @param roleVect
	 *            ��ɫID
	 * @return �ɹ������ؼ�¼Ӱ��������ʧ�ܣ���1����0��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int addUserRole(String userId, Vector roleVect)
			throws MonitorException {
		if (roleVect == null || roleVect.size() <= 0)
			return 0;
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("INSERT INTO monit_userpower (user_id,role_id) VALUES (?,?)");
		conn = SqlAccess.createConn();
		int effect = -1;
		try {
			PreparedStatement psmt = conn.conn.prepareStatement(SqlStr.toString());
			for (effect = 0; effect < roleVect.size(); effect++) {
				psmt.clearParameters();
				psmt.setString(1, userId);
				psmt.setString(2, roleVect.get(effect).toString());
				psmt.execute();
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return effect;
	}

	/**
	 * ���û�Ȩ�ޱ���ɾ���û���ӵ�еĽ�ɫ��
	 * 
	 * @param userId
	 *            �û�ID;
	 * @return �ɹ���ɾ����¼��������ʧ�ܣ���1��
	 * @throws MonitorException
	 */
	public int delUserRole(String userId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer(1024);
		SqlStr.append("DELETE FROM monit_userpower WHERE user_id = '");
		SqlStr.append(userId);
		SqlStr.append("'");

		int effect = -1;
		conn = SqlAccess.createConn();
		try {
			effect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {

			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.close();
		}
		return effect;
	}

	/**
	 * ɾ���û���Ϣ��ͬʱɾ���û�Ȩ�ޱ�����Ϣ
	 * 
	 * @param userId
	 *            �û�ID��
	 * @return �ɹ���1��ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int delUserInfo(String userId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("DELETE FROM monit_userinfo WHERE user_id = '");
		SqlStr.append(userId);
		SqlStr.append("'");

		StringBuffer SqlStr1 = new StringBuffer();
		SqlStr1.append("DELETE FROM monit_userpower WHERE user_id = '");
		SqlStr1.append(userId);
		SqlStr1.append("'");

		conn = SqlAccess.createConn();
		int effect = -1;
		try {
			conn.setAutoCommit(false);
			if (conn.queryupdate(SqlStr.toString()) >= 0
					&& conn.queryupdate(SqlStr1.toString()) >= 0) {
				effect = 1;
				conn.commit();
			} else
				conn.rollback();
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.close();
		}
		return effect;
	}

	/**
	 * �޸��û���״̬
	 * 
	 * @param userId
	 *            �û�ID��
	 * @return �ɹ���1��ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int UnlockUserInfo(String userId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("UPDATE monit_userinfo SET user_state = 1 WHERE user_id = '");
		SqlStr.append(userId);
		SqlStr.append("'");

		conn = SqlAccess.createConn();
		int effect = -1;
		try {
			conn.setAutoCommit(false);
			if (conn.queryupdate(SqlStr.toString()) >= 0) {
				effect = 1;
				conn.commit();
			} else
				conn.rollback();
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.close();
		}
		return effect;
	}
	
	/**
	 * �޸�monit_pwd_update_history
	 * 
	 * @param userId
	 *            �û�ID��
	 * 
	 * @throws MonitorException SQLException
	 *             �쳣
	 *             sunyl
	 */
	public void updateDate(String userId) throws MonitorException {
		String sql1 = "select first 1 pwd_createdate from monit_pwd_update_history where user_id = '"+userId+"' order by pwd_createdate desc";		
		conn = SqlAccess.createConn();
		try {
			 rs = conn.queryselect(sql1);
			if(rs.next()){
				System.out.println("*******************���һ���޸ĵ�������:"+rs.getString(1));
			}
			String sql2 = "update monit_pwd_update_history set pwd_createdate = '"+Util.getCurrentDate()+"' , last_login_date = '"+Util.getCurrentDate()+"' where user_id = '"+userId+"' and pwd_createdate ='"+rs.getString(1)+"'";
			conn.queryupdate(sql2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		      conn.close();
	    }
	}

	public List queryOrgList(String org_id) {
		CodeManageBean cd = new CodeManageBean();
		SqlAccess sq = new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn = sq.conn;
		try {
			return cd.queryOrgList(org_id, conn);
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			sq.close();
		}
		return null;
	}

	/**
	 * ��ѯ�û���ϸ��Ϣ
	 * 
	 * @param userId
	 *            �û�ID
	 * @return �û���ϸ��Ϣ��������
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector queryUserInfo(String userId) throws MonitorException {
		Vector rsVect = new Vector();
		Hashtable userInfo = null;
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT user_id,user_name,org_id,employ_date,fire_date,user_state,user_desc FROM monit_userinfo WHERE user_id !='Admin' AND user_id MATCHES '");
		SqlStr.append(userId);
		SqlStr.append("'");

		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				userInfo = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					userInfo.put(rs.getMetaData().getColumnName(i + 1),
							toucsString.unConvert(rs.getString(i + 1)));
				}
				rsVect.add(userInfo);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			conn.close();
		}
		return rsVect;
	}

	/**
	 * ��ѯ�û���ϸ��Ϣ
	 * 
	 * @param userId
	 *            �û�ID
	 * @param orgID
	 *            ����ID
	 * @return �û���ϸ��Ϣ��������
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector queryUserInfo(String userId, String orgID)
			throws MonitorException {
		CodeManageBean codeManageBean = new CodeManageBean();
		conn = SqlAccess.createConn();
		List orgList = codeManageBean.queryOrgList(orgID, conn.conn);

		// ���ݲ�ѯ�����¼�����������֯SQL���(where����)
		String strW = "";
		int condiNum = orgList.size() - 1;
		for (int i = 0; i < orgList.size(); i++) {
			CodeBean orgDeviceCode = (CodeBean) orgList.get(i);
			String orgIDTmp = orgDeviceCode.getCodeId();
			if (i == condiNum)
				strW = strW + " org_id='" + orgIDTmp + "'";
			else
				strW = strW + " org_id='" + orgIDTmp + "'or ";
		}
		if (strW.trim().length() > 0)
			strW = " AND (" + strW + ") ";

		Vector rsVect = new Vector();
		Hashtable userInfo = null;
		StringBuffer SqlStr = new StringBuffer();
		String sql = "SELECT user_id,user_name,org_id,employ_date,fire_date,";
		sql = sql
				+ "user_state,user_desc FROM monit_userinfo WHERE user_id !='Admin' ";
		sql = sql + strW;
		sql = sql + "AND user_id MATCHES '";
		SqlStr.append(sql);
		SqlStr.append(userId);
		SqlStr.append("'");
		Debug.println("��ѯ��SQL���:" + SqlStr.toString());
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				userInfo = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					userInfo.put(rs.getMetaData().getColumnName(i + 1),
							toucsString.unConvert(rs.getString(i + 1)));
				}
				rsVect.add(userInfo);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return rsVect;
	}

	/**
	 * ��ѯ��ɫ��Ϣ
	 * 
	 * @param RoleId
	 *            ��ɫID
	 * @param RoleName
	 *            ��ɫ����
	 * @return ��ɫ��Ϣ����
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector queryRoleInfo(String RoleId, String RoleName)
			throws MonitorException {
		Vector Condition = new Vector();
		Condition.add(RoleId);
		Condition.add(RoleName);

		Vector DbCol = new Vector();
		DbCol.add("role_id");
		DbCol.add("role_name");

		Vector RsVect = new Vector();
		Hashtable RoleHT = null;
		int flag = 0;
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT role_id,role_name,role_desc FROM monit_roleinfo WHERE role_id != '0'");
		for (int i = 0; i < Condition.size(); i++) {
			if (Condition.get(i).equals("*")) {
				continue;
			}
			SqlStr.append(" AND ");
			SqlStr.append(DbCol.get(i));
			SqlStr.append(" MATCHES '*");
			SqlStr.append(Condition.get(i));
			SqlStr.append("*'");
			flag++;
		}

		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),
							toucsString.unConvert(rs.getString(i + 1)));
				}
				RsVect.add(RoleHT);
			}

		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return RsVect;
	}

	/**
	 * ��ӻ���
	 * 
	 * @param orgId
	 *            ��������
	 * @param orgName
	 *            ��������
	 * @param p_orgId
	 *            �ϼ���������
	 * @param ipAddress
	 *            IP��ַ
	 * @param orgAddress
	 *            ������ַ
	 * @param connecter
	 *            ��ϵ��
	 * @param phoneNo
	 *            ��ϵ�˵绰
	 * @param operType
	 *            ҵ������
	 * @return �ɹ���Ӱ���¼��������ʧ�ܣ���1
	 * @throws MonitorException
	 *             �쳣
	 */
	public int addOrg(String orgId, String orgName, String p_orgId,
			String ipAddress, String orgAddress, String connecter,
			String phoneNo, String operType) throws MonitorException {

		int affect = -1;
		try {
			int orgLevel = Integer.parseInt(getOrgLevel(p_orgId)) + 1;
			StringBuffer SqlStr = new StringBuffer();
			SqlStr.append("INSERT INTO monit_orginfo (org_id,org_name,org_level,p_org_id,ip_address,org_address,contractor,phoneNo,oper_type) VALUES ('");
			SqlStr.append(orgId + "','");
			SqlStr.append(orgName + "',");
			SqlStr.append(orgLevel + ",'");
			SqlStr.append(p_orgId + "','");
			SqlStr.append(ipAddress + "','");
			SqlStr.append(orgAddress + "','");
			SqlStr.append(connecter + "','");
			SqlStr.append(phoneNo + "',");
			SqlStr.append(operType + ")");
			String SqlStr0 = "INSERT INTO monit_orgref (org_id,p_org_id) values (?,?)";
			Vector porgV = getAllParentOrgId(p_orgId);
			if (porgV == null) {
				porgV = new Vector();
			}

			Debug.println("orgV:[" + porgV.toString() + "]");
			porgV.add(orgId);
			Debug.println("orgV:[" + porgV.toString() + "]");
			conn = SqlAccess.createConn();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.conn.prepareStatement(SqlStr0);

			affect = conn.queryupdate(SqlStr.toString());
			if (affect > 0) {
				for (int i = 0; i < porgV.size(); i++) {
					psmt.clearParameters();
					psmt.setString(1, orgId);
					psmt.setString(2, porgV.get(i).toString());
					Debug.println("\norgId[" + orgId + "]\np_org_id[" + p_orgId
							+ "]");
					affect = psmt.executeUpdate();
				}
				if (affect > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (MonitorException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * �޸Ļ�����Ϣ
	 * 
	 * @param orgId
	 *            ��������
	 * @param orgName
	 *            ��������
	 * @param ipAddress
	 *            IP��ַ
	 * @param orgAddress
	 *            ������ַ
	 * @param connecter
	 *            ��ϵ��
	 * @param phoneNo
	 *            ��ϵ�˵绰
	 * @param operType
	 *            ҵ������
	 * @return �ɹ���Ӱ���¼������ʧ�ܣ���1
	 * @throws MonitorException
	 *             �쳣
	 */
	public int modifyOrgInfo(String orgId, String orgName, String ipAddress,
			String orgAddress, String connecter, String phoneNo, String operType)
			throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("UPDATE monit_orginfo SET(org_name,ip_address,org_address,contractor,phoneno,oper_type) = ('");
		SqlStr.append(orgName + "','");
		SqlStr.append(ipAddress + "','");
		SqlStr.append(orgAddress + "','");
		SqlStr.append(connecter + "','");
		SqlStr.append(phoneNo + "',");
		SqlStr.append(operType + ") WHERE org_id = '");
		SqlStr.append(orgId + "'");

		int affect = -1;
		conn = SqlAccess.createConn();
		try {
			affect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param orgId
	 *            ��������
	 * @return ������Ϣ����
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector queryOrgInfo(String orgId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT org_id,org_name,org_level,p_org_id,ip_address,org_address,contractor,phoneno,map_file_id,oper_type FROM monit_orginfo WHERE org_id MATCHES '");
		SqlStr.append(orgId + "'");

		Vector RsVect = new Vector();
		Hashtable orgHT = null;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(toucsString.Convert(SqlStr.toString()));
			while (rs != null && rs.next()) {
				orgHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					orgHT.put(
									rs.getMetaData().getColumnName(i + 1),
									toucsString.unConvert(rs.getString(i + 1) == null ? " "
													: rs.getString(i + 1)));
				}
				RsVect.add(orgHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return RsVect;
	}

	/**
	 * �޸��û�Ȩ�ޱ�
	 * 
	 * @param userId
	 *            �û�ID
	 * @param roleIdV
	 *            ��ɫID(Vector)
	 * @return �ɹ���������ʧ�ܣ�����
	 * @throws MonitorException
	 *             �쳣
	 */
	public int modifyUserPower(String userId, Vector roleIdV)
			throws MonitorException {
		StringBuffer SqlStr1 = new StringBuffer();
		StringBuffer SqlStr2 = new StringBuffer();

		SqlStr1.append("DELETE FROM monit_userpower WHERE user_id = '" + userId + "'");
		SqlStr2.append("INSERT INTO monit_userpower (user_id,role_id) VALUES (?,?)");
		conn = SqlAccess.createConn();
		int affect = -1;
		try {
			conn.setAutoCommit(false);
			affect = conn.queryupdate(SqlStr1.toString());
			if (affect >= 0 && roleIdV != null) {
				conn.conn.setAutoCommit(false);
				PreparedStatement psmt = conn.conn.prepareStatement(SqlStr2.toString());
				for (int i = 0; i < roleIdV.size(); i++) {
					psmt.clearParameters();
					psmt.setString(1, userId);
					psmt.setString(2, roleIdV.get(i).toString());
					affect = psmt.executeUpdate();
					if (affect < 0) {
						continue;
					}
				}
			}
			if (affect >= 0) {
				conn.conn.commit();
				conn.commit();
			} else {
				conn.conn.rollback();
				conn.rollback();
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * �޸Ľ�ɫ��Ϣ
	 * 
	 * @param roleId
	 *            ��ɫ����
	 * @param roleName
	 *            ��ɫ����
	 * @param roleDesc
	 *            ��ɫ����
	 * @return �ɹ���Ӱ���¼����������������ʧ�ܣ���1
	 * @throws MonitorException
	 *             �쳣
	 */
	public int modifyRoleInfo(String roleId, String roleName, String roleDesc)
			throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("UPDATE monit_roleinfo SET (role_name,role_desc) = ('");
		SqlStr.append(roleName + "','");
		SqlStr.append(roleDesc + "') WHERE role_id='");
		SqlStr.append(roleId + "'");

		int affect = -1;
		conn = SqlAccess.createConn();
		try {
			affect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * ������ɫID���������ݿ���н�ɫID���������ֵ��1
	 * 
	 * @return ��ɫID
	 * @throws MonitorException
	 *             �쳣
	 */
	private String BuildRoleId() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT max(round(role_id/1.0)) FROM monit_roleinfo");
		int MaxId = 0;
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			if (rs == null) {
				MaxId = 0;
			} else {
				while (rs.next()) {
					MaxId = rs.getInt(1);
					Debug.println("OrgId:" + (MaxId + 1));
				}
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return String.valueOf(MaxId + 1);
	}

	/**
	 * ���ɫ��Ϣ���в���һ����¼
	 * 
	 * @param roleName
	 *            ��ɫ����
	 * @param roleDesc
	 *            ��ɫ˵��
	 * @return �ɹ���1��ʧ�ܣ���1
	 * @throws MonitorException
	 *             �쳣
	 */
	public int addRole(String roleName, String roleDesc)
			throws MonitorException {
		String roleId = this.BuildRoleId();
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("INSERT INTO monit_roleinfo (role_id,role_name,role_desc) VALUES ('");
		SqlStr.append(roleId + "','");
		SqlStr.append(roleName + "','");
		SqlStr.append(roleDesc + "')");
		int affect = -1;
		conn = SqlAccess.createConn();
		try {
			affect = conn.queryupdate(SqlStr.toString());
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * ɾ��һ����ɫ
	 * 
	 * @param roleId
	 *            ��ɫID
	 * @return �ɹ���1��ʧ�ܣ���1
	 * @throws MonitorException
	 *             �쳣
	 */
	public int delRoleInfo(String roleId) throws MonitorException {
		StringBuffer SqlStr1 = new StringBuffer();
		SqlStr1.append("DELETE FROM monit_roleinfo WHERE role_id = '");
		SqlStr1.append(roleId + "'");
		StringBuffer SqlStr2 = new StringBuffer();
		SqlStr2.append("DELETE FROM monit_userpower WHERE role_id = '");
		SqlStr2.append(roleId + "'");
		StringBuffer SqlStr3 = new StringBuffer();
		SqlStr3.append("DELETE FROM monit_roleresource WHERE role_id = '");
		SqlStr3.append(roleId + "'");
		int affect = -1;
		conn = SqlAccess.createConn();
		try {
			conn.setAutoCommit(false);
			affect = conn.queryupdate(toucsString.Convert(SqlStr1.toString()));
			if (affect > 0) {
				affect = conn.queryupdate(toucsString.Convert(SqlStr2.toString()));
				if (affect >= 0) {
					affect = conn.queryupdate(toucsString.Convert(SqlStr3.toString()));
				}
				if (affect >= 0) {
					conn.commit();
					affect = 1;
				} else {
					conn.rollback();
				}
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * �޸�����
	 * 
	 * @param password
	 *            ������
	 * @param userId
	 *            �û�ID
	 * @return �ɹ���1��ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int passwd(String password, String userId) throws MonitorException {
		DESCryptography DES = new DESCryptography();
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("UPDATE monit_userinfo SET user_passwd = '");
		SqlStr.append(DES.encrypt(password));
		SqlStr.append("' WHERE user_id = '");
		SqlStr.append(userId);
		SqlStr.append("'");

		int affect = -1;
		conn = SqlAccess.createConn();
		try {
			affect = conn.queryupdate(toucsString.Convert(SqlStr.toString()));
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * У������
	 * 
	 * @param userName
	 *            �û�ID
	 * @param passwd
	 *            ����
	 * @return �ɹ���true��ʧ�ܣ�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean checkUserPwd(String userName, String passwd)
			throws MonitorException {
		boolean isSucces = false;
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT user_passwd FROM monit_userinfo WHERE user_id = '");
		SqlStr.append(userName);
		SqlStr.append("'");

		conn = SqlAccess.createConn();
		try {
			String oldPasswd = null;
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				oldPasswd = new String(rs.getString(1));
			}
			if (oldPasswd != null) {
				DESCryptography DES = new DESCryptography();
				if (DES.decrypt(oldPasswd.trim()).equals(passwd.trim())) {
					isSucces = true;
				}
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return isSucces;
	}

	/**
	 * ȡ���û������Ľ�ɫ
	 * 
	 * @param userId
	 *            �û�ID
	 * @return �û���ӵ�еĽ�ɫ��vector��
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector getUserRole(String userId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT A.role_id,B.role_name FROM monit_userpower A, monit_roleinfo B WHERE B.role_id = A.role_id AND A.user_id = '");
		SqlStr.append(userId);
		SqlStr.append("'");

		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),
							toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return RoleVect;
	}

	/**
	 * ȡ���û���δӵ�еĽ�ɫ
	 * 
	 * @param userId
	 *            �û�ID
	 * @return �û���ӵ�еĵĽ�ɫ����
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector getAvailableRole(String userId) throws MonitorException {
		Hashtable Res = null;
		String Sql = "SELECT role_id,role_name FROM monit_roleinfo  WHERE role_id not in"
				+ " (SELECT role_id FROM monit_userpower WHERE user_id='"
				+ userId + "') AND role_id != '0'";
		Vector oV = new Vector();
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(toucsString.Convert(Sql));
			while (rs != null && rs.next()) {
				Res = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					Res.put(rs.getMetaData().getColumnName(i + 1), toucsString
							.unConvert(rs.getString(i + 1)));
				}
				oV.add(Res);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return oV;
	}

	/**
	 * ȡ�ý�ɫ��ӵ�е���Դ
	 * 
	 * @param roleId
	 *            ��ɫ���
	 * @return ��ɫ��ӵ�е���Դ��vector)
	 * @throws MonitorException
	 */
	public Vector getRoleRes(String roleId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT A.resource_id,B.resource_name FROM monit_roleresource A, monit_resource B WHERE B.resource_id = A.resource_id AND A.role_id = '");
		SqlStr.append(roleId);
		SqlStr.append("'");

		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),
							toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return RoleVect;
	}

	/**
	 * ȡ�ý�ɫ����δӵ�е���Դ
	 * 
	 * @param roleId
	 *            ��ɫID
	 * @return ��ɫ��ӵ�еĵ���Դ����
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector getAvailableRes(String roleId) throws MonitorException {
		Hashtable Res = null;
		String Sql = "SELECT resource_id,resource_name FROM monit_resource  WHERE resource_id not in"
				+ " (SELECT resource_id FROM monit_roleresource WHERE role_id='"
				+ roleId + "')";
		Vector oV = new Vector();
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(toucsString.Convert(Sql));
			while (rs != null && rs.next()) {
				Res = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					Res.put(rs.getMetaData().getColumnName(i + 1), toucsString.unConvert(rs.getString(i + 1)));
				}
				oV.add(Res);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return oV;
	}

	/**
	 * ��ѯ������ϸ��Ϣ
	 * 
	 * @param orgId
	 *            ��������
	 * @param orgName
	 *            ��������
	 * @param p_orgId
	 *            ��������
	 * @return ������ϸ��Ϣ
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector queryOrgInfo(String orgId, String orgName, String p_orgId)
			throws MonitorException {
		Vector Condition = new Vector();
		Condition.add(orgId);
		Condition.add(orgName);

		Vector DbCol = new Vector();
		DbCol.add("a.org_id");
		DbCol.add("a.org_name");

		Vector OrgV = new Vector();
		Hashtable OrgH = null;
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT a.org_id,a.org_name,a.org_level,a.p_org_id,a.map_file_id,a.ip_address,a.org_address,a.contractor,a.phoneno,a.oper_type FROM monit_orginfo a,monit_orgref b ");
		int flag = 0;
		for (int i = 0; i < Condition.size(); i++) {
			if (Condition.get(i).equals("*")) {
				continue;
			}
			if (flag == 0) {
				SqlStr.append(" WHERE ");
			} else {
				SqlStr.append(" AND ");
			}
			SqlStr.append(DbCol.get(i));
			SqlStr.append(" MATCHES '*");
			SqlStr.append(Condition.get(i));
			SqlStr.append("*'");
			flag++;
		}
		if (flag == 0) {
			SqlStr.append(" WHERE ");
		} else {
			SqlStr.append(" AND ");
		}
		SqlStr.append("a.org_id=b.org_id AND b.p_org_id='");
		SqlStr.append(p_orgId);
		SqlStr.append("'");
		if (p_orgId.equals("NO")) {
			SqlStr = new StringBuffer();
			SqlStr.append("SELECT org_id,org_name,org_level,p_org_id,map_file_id,ip_address,org_address,contractor,phoneno,oper_type FROM monit_orginfo");
		}
		// add by liyp 20031020 ������������
		SqlStr.append(" ORDER BY a.org_id");

		Debug.println(SqlStr.toString());
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				OrgH = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					OrgH.put(rs.getMetaData().getColumnName(i + 1), toucsString.unConvert(rs.getString(i + 1)));
				}
				OrgV.add(OrgH);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return OrgV;
	}

	/**
	 * ȡ�û�����Ϣ����xml��ʽ����
	 * 
	 * @param orgId
	 *            ��������
	 * @param DealCode
	 *            ���״���
	 * @param ErrorCode
	 *            ������
	 * @param ErrorDescribe
	 *            ��������
	 * @return xml�ַ���
	 * @throws MonitorException
	 *             �쳣
	 */
	public String getOrgInfoXml(String orgId, String DealCode,
			String ErrorCode, String ErrorDescribe) throws MonitorException {

		Hashtable OrgH = null;
		StringBuffer XmlStr = new StringBuffer();
		XmlStr.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
		XmlStr.append("<Deal>\n");
		XmlStr.append("  <DealCode>" + DealCode + "</DealCode>\n");
		XmlStr.append("  <ErrorCode>" + ErrorCode + "</ErrorCode>\n");
		XmlStr.append("  <ErrorDescribe>" + ErrorDescribe + "</ErrorDescribe>\n");

		int i = 0;
		try {
			Vector OrgV = this.queryOrgInfo("*", "*", orgId);
			if (OrgV != null) {
				XmlStr.append("  <Data>\n");
				while (OrgV != null && i < OrgV.size()) {
					OrgH = (Hashtable) OrgV.get(i);
					XmlStr.append("    <OrgInfo id=" + OrgH.get("org_id")
							+ ">\n");
					XmlStr.append("      <Name>" + OrgH.get("org_name")
							+ "</Name>\n");
					XmlStr.append("      <Parentid>" + OrgH.get("p_org_id")
							+ "</Parentid>\n");
					XmlStr.append("      <OrgLevel>" + OrgH.get("org_level")
							+ "</OrgLevel>\n");
					XmlStr.append("      <mapCode>" + OrgH.get("map_file_id")
							+ "</mapCode>\n");
					XmlStr.append("    </OrgInfo>\n");
					i++;
				}
				XmlStr.append("  </Data>\n");
			}
			XmlStr.append("</Deal>\n");
		} catch (MonitorException mex) {
			throw mex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		}
		return XmlStr.toString();
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param orgId
	 *            ��������
	 * @return Ӱ���¼������
	 * @throws MonitorException
	 *             �쳣
	 */
	public int delOrgInfo(String orgId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("DELETE FROM monit_orginfo WHERE org_id in (SELECT org_id FROM monit_orgref WHERE p_org_id ='");
		SqlStr.append(orgId);
		SqlStr.append("')");
		String SqlStr0 = "DELETE FROM monit_orgref WHERE org_id = '" + orgId + "' OR p_org_id = '" + orgId + "'";

		int affect = -1;
		conn = SqlAccess.createConn();

		try {
			conn.setAutoCommit(false);
			affect = conn.queryupdate(SqlStr.toString());
			if (affect >= 0) {
				affect = conn.queryupdate(SqlStr0);
			}
			if (affect >= 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * ȡ�ô���������¼������ͱ�������Ϣ
	 * 
	 * @param orgId
	 *            ��������
	 * @return �¼������Լ�����������Ϣ
	 * @throws MonitorException
	 *             �쳣
	 */
	public Vector getSubOrg(String orgId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		if (orgId != null && orgId.equals("NO")) {
			SqlStr.append("SELECT org_id,org_name,org_level FROM monit_orginfo order by org_level");
		} else {
			SqlStr.append("SELECT a.org_id,b.org_name,b.org_level FROM monit_orgref a,monit_orginfo b WHERE b.org_id = a.org_id AND a.p_org_id='");
			SqlStr.append(orgId);
			SqlStr.append("' order by b.org_level");
		}
		conn = SqlAccess.createConn();
		Vector orgV = new Vector();
		Hashtable orgH = null;
		try {
			rs = conn.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				orgH = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					orgH.put(rs.getMetaData().getColumnName(i + 1), toucsString.unConvert(rs.getString(i + 1)));
				}
				Debug.println("orgH===" + orgH.toString());
				orgV.add(orgH);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return orgV;
	}

	/**
	 * ȡ�ô���������¼������ͱ�������Ϣ���м�������
	 * 
	 * @param pOrgId
	 *            �������
	 * @return ���������Ļ����б�
	 * @throws MonitorException
	 *             ϵͳ�쳣
	 */
	public List getSubOrgWithLevel(String pOrgId) throws MonitorException {
		SqlAccess sq = null;
		try {
			// �޶���һ����������
			int[] level = { 0, 1, 2 };
			sq = SqlAccess.createConn();
			List ret = CodeManageBean.queryOrg(level, pOrgId, sq);
			return ret;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} catch (Exception e2) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			if (sq != null)
				sq.close();
		}
	}

	/**
	 * �����û�IDȡ���û���Ϣ
	 * 
	 * @param userId
	 *            �û�ID
	 * @return �û���Ϣ�ࣨuserInfo��
	 * @throws MonitorException
	 *             �쳣
	 */
	public userInfo getUserInfo(String userId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT user_id,user_name,org_id,employ_date,fire_date,user_state,user_desc FROM monit_userinfo WHERE user_id MATCHES '");
		SqlStr.append(userId);
		SqlStr.append("'");

		userInfo UI = new userInfo();
		conn = SqlAccess.createConn();
		try {
			rs = conn.queryselect(SqlStr.toString());
			if (rs != null && rs.next()) {
				UI.setUserId(rs.getString(0));
				UI.setUserName(rs.getString(1));
				UI.setOrgId(rs.getString(2));
				UI.setEmployDate(rs.getString(3));
				UI.setFireDate(rs.getString(4));
				UI.setUserState(rs.getString(5));
				UI.setUserDesc(rs.getString(6));
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return UI;
	}

	/**
	 * �޸Ľ�ɫ��Դ��Ӧ��ϵ
	 * 
	 * @param roleId
	 *            ��ɫ���
	 * @param resIdV
	 *            ��Դ��ż�
	 * @return �ɹ���1��ʧ�ܣ���1��
	 * @throws MonitorException
	 *             �쳣
	 */
	public int modifyRoleRes(String roleId, Vector resIdV)
			throws MonitorException {
		StringBuffer SqlStr1 = new StringBuffer();
		StringBuffer SqlStr2 = new StringBuffer();

		SqlStr1.append("DELETE FROM monit_roleresource WHERE role_id = '" + roleId + "'");
		SqlStr2.append("INSERT INTO monit_roleresource (role_id,resource_id) VALUES (?,?)");
		conn = SqlAccess.createConn();
		int affect = -1;
		try {
			conn.setAutoCommit(false);
			affect = conn.queryupdate(SqlStr1.toString());
			if (affect >= 0 && resIdV != null) {
				conn.conn.setAutoCommit(false);
				PreparedStatement psmt = conn.conn.prepareStatement(SqlStr2.toString());
				for (int i = 0; i < resIdV.size(); i++) {
					psmt.clearParameters();
					psmt.setString(1, roleId);
					psmt.setString(2, resIdV.get(i).toString());
					affect = psmt.executeUpdate();
					if (affect < 0) {
						continue;
					}
				}
			}
			if (affect >= 0) {
				conn.conn.commit();
				conn.commit();
			} else {
				conn.conn.rollback();
				conn.rollback();
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return affect;
	}

	/**
	 * ���ݻ�������ȡ�û�������
	 * 
	 * @param orgid
	 *            ��������
	 * @return ��������
	 * @throws MonitorException
	 */
	private String getOrgLevel(String orgid) throws MonitorException {
		String orgL = "";
		try {
			Vector ov = (Vector) this.queryOrgInfo(orgid);
			if (ov == null || ov.size() <= 0) {
				orgL = "0";
			} else {
				Hashtable oh = (Hashtable) ov.get(0);
				orgL = oh.get("org_level").toString();
			}
		} catch (MonitorException ex) {
			throw ex;
		}
		return orgL;
	}

	/**
	 * ���ݻ�������ȡ�������ϼ���������
	 * 
	 * @param org_id
	 *            ��������
	 * @return �������뼯
	 * @throws MonitorException
	 */
	private Vector getAllParentOrgId(String org_id) throws MonitorException {
		Vector orgidV = new Vector();
		try {
			String SqlStr = "SELECT p_org_id FROM monit_orgref WHERE org_id = '" + org_id + "'";
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			while (rs != null && rs.next()) {
				orgidV.add(rs.getString("p_org_id"));
			}
			// orgidV.add(org_id);
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return orgidV;
	}

	/**
	 * ��ѯ�������Ƿ���ATM��Ϣ
	 * 
	 * @param orgid
	 *            ��������
	 * @return �У�true��û�У�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean isAtmExist(String orgid) throws MonitorException {
		String SqlStr = "SELECT a.atm_code FROM atm_info a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgid + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	/**
	 * ��ѯ�������Ƿ���PEM��Ϣ
	 * 
	 * @param orgid
	 *            ��������
	 * @return �У�true��û�У�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean isPemExist(String orgid) throws MonitorException {
		String SqlStr = "SELECT a.pem_code FROM pem_info a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgid + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	/**
	 * ��ѯ�������Ƿ���MIT��Ϣ
	 * 
	 * @param orgid
	 *            ��������
	 * @return �У�true��û�У�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean isMitExist(String orgid) throws MonitorException {
		String SqlStr = "SELECT a.mit_code FROM mit_info a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgid + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	/**
	 * ��ѯ�������Ƿ���CDM��Ϣ
	 * 
	 * @param orgid
	 *            ��������
	 * @return �У�true��û�У�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean isCdmExist(String orgid) throws MonitorException {
		String SqlStr = "SELECT a.cdm_code FROM cdm_info a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgid + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	/**
	 * ��ѯ�������Ƿ����̻���Ϣ
	 * 
	 * @param orgid
	 *            ��������
	 * @return �У�true��û�У�false
	 * @throws MonitorException
	 *             �쳣
	 */
	public boolean isMctExist(String orgid) throws MonitorException {
		String SqlStr = "SELECT a.merchant_id FROM pos_merchant a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgid + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	// ��ѯ�û��Ƿ����
	public boolean isUserExist(String userID) throws MonitorException {
		boolean ret = false;
		if (userID == null) {
			return ret;
		}

		String SqlStr = "SELECT * FROM monit_userinfo WHERE user_id='" + userID + "'";
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	public boolean isRoleExist(String roleID) throws MonitorException {
		boolean ret = false;
		if (roleID == null) {
			return ret;
		}
		String SqlStr = "SELECT * FROM monit_roleinfo WHERE role_id='" + roleID
				+ "'";
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,
					ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}

	public boolean isUserExistInOrg(String orgId) throws MonitorException {
		String SqlStr = "SELECT a.user_id FROM monit_userinfo a,monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id ='" + orgId + "'";
		boolean ret = false;
		try {
			conn = SqlAccess.createConn();
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				ret = true;
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return ret;
	}
}
