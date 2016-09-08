package com.adtec.toucs.monitor.loginmanage;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.*;

/**
 * <p>Title: ��¼�û���Ϣ��</p>
 * <p>Description:��װ��¼�û��������Ϣ</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author sunyl
 * @version 2.0
 */

public class LoginInfo {
	//���ݿ�����
	private SqlAccess conn = null;
	// �û���¼ʧЧʱ����,��λΪ����(�������ļ���ȡ�ã�ȱʡΪ0����ʾ��������У��)
	public static int timeOut = 0;
	// �û���
	private String userName;
	// �û�ID
	private String userId;
	// ����
	private String userPwd;
	// ��¼����վIP
	private String IP;
	// ������������
	private String orgId;
	// Ȩ����
	private String permission;
	// �û�����
	private String userDesc;
	// �û�״̬
	private int userState;
	// �������
	private int PasswdNum;
	// ��¼(����)ʱ��
	private java.util.Date onLineTime;
	// ��¼ʧЧ(����)ʱ��
	private java.util.Date offLineTime;
	// �û���¼�ỰID(���ID��ʶ�û���һ����Ч��¼����ͨ��Cookie���ؿͻ���)
	private String sessionID;
	// ��½�û��Ļ�������
	private int userLevel;
	//�û��������Ч��:syl
	private int pwdUserfulDate;
	//�û�����Ĵ���ʱ��:syl
	private String pwdCreateDate;	
	//��һ�ε�¼ʱ��:syl
	private String lastLoginDate;

	/**
	 * ���췽��
	 */
	public LoginInfo() {
		onLineTime = new java.util.Date();
		offLineTime = new java.util.Date();
		updateValidTime();
	}

	// �û������Զ�д
	public void setUserName(String name) {
		userName = name;
	}

	public String getUserName() {
		return userName;
	}

	// �û�ID���Զ�д
	public void setUserID(String id) {
		userId = id;
	}

	public String getUserID() {
		return userId;
	}

	// ��¼����վIP���Զ�д
	public void setIP(String ip) {
		IP = ip;
	}

	public String getIP() {
		return IP;
	}

	// ����������Զ�д
	public void setOrgID(String id) {
		orgId = id;
	}

	public String getOrgID() {
		return orgId;
	}

	// Ȩ�������Զ�д
	public void setPermission(String perm) {
		permission = perm;
	}

	public String getPermission() {
		return permission;
	}

	// ���õ�¼ʱ��
	public void setOnLineTime(long time) {
		onLineTime.setTime(time);
		updateValidTime();
	}

	public void setOnLineTime(java.util.Date time) {
		onLineTime = time;
		updateValidTime();
	}

	// ȡ��¼ʱ��
	public String getOnLineTime() {
		return Util.formatDate(onLineTime);
	}

	// ȡ����ʱ��
	public String getOffLineTime() {
		return Util.formatDate(offLineTime);
	}
	
	
	public int getPwdUserfulDate() {
		return pwdUserfulDate;
	}

	public void setPwdUserfulDate(int pwdUserfulDate) {
		this.pwdUserfulDate = pwdUserfulDate;
	}

	public String getPwdCreateDate() {
		return pwdCreateDate;
	}

	public void setPwdCreateDate(String pwdCreateDate) {
		this.pwdCreateDate = pwdCreateDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

  /**
   * У���û���¼�Ƿ���Ч
   * @return true-��Ч,false-��Ч
   */
	public boolean isValid() {
		return offLineTime.getTime() > System.currentTimeMillis();
	}

  /**
   * ��������ʱ��
   */
	public void updateValidTime() {
		offLineTime.setTime(System.currentTimeMillis() + timeOut * 60 * 1000);
	}

  /**
   * ������¼�ỰID
   */
	public void createSessionID() {
		// ���û�ID�͵�¼ʱ����Ϻ���RC6�㷨����
		sessionID = RC6.encrypt(userId + getOnLineTime());
	}

  /**
   * ȡ��¼�ỰID
   * 
   * @return �û���¼�ỰID
   */
	public String getSessionID() {
		return sessionID;
	}

  /**
   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
   * @param buf �ַ����������
   */
	public void toXML(StringBuffer buf) {
		buf.append("<UserInfo>\n");
		buf.append("<UserName>" + userName + "</UserName>\n");
		buf.append("<UserId>" + userId + "</UserId>\n");
		buf.append("<OrgId>" + orgId + "</OrgId>\n");
		buf.append("<PermMask>" + permission + "</PermMask>\n");
		buf.append("<UserDesc>" + userDesc + "</UserDesc>\n");
		buf.append("<UserStat>" + userState + "</UserStat>\n");
		buf.append("<UserLevel>" + userLevel + "</UserLevel>\n");
		buf.append("</UserInfo>\n");
	}

  /**
   * ת��ΪXML��ʽ����
   * @return XML��ʽ����
   */
	public String toXML() {
		StringBuffer buf = new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

  /**
   * ���ǵ�toString()���� 
   * @return
   */
	public String toString() {
		return "[" + userName + "|" + orgId + "|" + permission + "]";
	}

  /**
   * �Ӳ�ѯ�����ȡ�û���Ϣ 
   * @param rst��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException {
		userName = toucsString.unConvert(Util.getString(rst, "user_name", ""));
		userPwd = Util.getString(rst, "user_passwd", "");
		userId = Util.getString(rst, "user_id", "");
		orgId = Util.getString(rst, "org_id", "");
		userDesc = Util.getString(rst, "user_desc", "");
		userState = rst.getInt("user_state");
		PasswdNum = rst.getInt("passwd_num");
		//userLevel = rst.getInt("Org_level");// by zhangyj20031011 add for the
											// login user org_level
		pwdUserfulDate = rst.getInt("pwd_userful_date");
		pwdCreateDate = rst.getString("pwd_createdate");
		lastLoginDate = rst.getString("last_login_date");
	}

  /**
   * У�������Ƿ���ȷ
   * @param pwd ����
   * @return ��ȷ����true
   * @throws SQLException 
   */
	public int isRightPwd(String pwd) throws MonitorException, SQLException {
		int intRet;
	    StringBuffer SqlStr = new StringBuffer();
		DESCryptography DES = new DESCryptography();
		userPwd.equals(DES.encrypt(pwd));
		if ( userPwd.equals(DES.encrypt(pwd)) ){
			intRet = 1;
		    SqlStr.append("UPDATE monit_userinfo SET passwd_num = 0 WHERE user_id = '");
		    SqlStr.append(userId);
		    SqlStr.append("'");
		} else {
			intRet = 0;
			if ( PasswdNum >= 9 ){
			    SqlStr.append("UPDATE monit_userinfo SET user_state = 0 WHERE user_id = '");
			    SqlStr.append(userId);
			    SqlStr.append("'");
			} else {
			    SqlStr.append("UPDATE monit_userinfo SET passwd_num = passwd_num + 1 WHERE user_id = '");
			    SqlStr.append(userId);
			    SqlStr.append("'");
			}
		}
	    conn = new SqlAccess();
	    try {
	    	conn.setAutoCommit(false);
	    	if (conn.queryupdate(SqlStr.toString()) >= 0) {
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
		return intRet;
	}

  /**
   * ����Ա�Ƿ�����Ч״̬
   * @return ��Ч����true
   */
	public int isInUse() {
		return userState;
	}
	
  /**
   * ��ѯ�û��������Ч�� 
   * @return affect
   * @throws ParseException 
   * @throws MonitorException 
   * @author sunyl
   */
	public int selectUserDate() throws ParseException, MonitorException{
		int affect = 0;
		conn = SqlAccess.createConn();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar rightNow = Calendar.getInstance();
		try {
			ResultSet rs = conn.queryselect("select * from monit_pwd_update_history where user_id = '"+userId+"' order by last_login_date desc");
			if(rs.next()){
				java.util.Date date1 = df.parse(rs.getString(4));//�����ݿ���ȥ����Ĵ���ʱ��
				long dill = rightNow.getTimeInMillis()-date1.getTime();//������ʹ���˵�����(ms)
				int day1 = Integer.parseInt(String.valueOf(dill/86400000));
				System.out.println("���޸�������Ѿ�����"+day1+"��");
				System.out.println("�������Ч����"+rs.getInt(3));
				if(day1<rs.getInt(3)&&(rs.getInt(3)-day1)>3)//ʱ���С����Ч�ڡ����ҽ�ֹ����Ч�ڵ���������3��
				{
					affect = 0;//������¼
					
				}else if(day1<rs.getInt(3)&&(rs.getInt(3)-day1)<3){
					affect = 1;//��Ч��ǰ3�����ʾ
				}else {
					affect = 2;//�������Ч����ʧЧ,ϵͳ�Զ������û�״̬
					updateState();//�����û���״̬
	           }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
	      conn.close();
	    }
		return affect;
	}
	
  /**
   * �����û���״̬
   * @throws MonitorException 
   * @throws ParseException 
   * @author sunyl
   * @param  user_state 1-����; 0-ͣ��
   */
	public void updateState() throws MonitorException{
		conn = SqlAccess.createConn();
		try {
			conn.queryupdate("update monit_userinfo set user_state = 0 where user_id = '"+userId+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		      conn.close();
		}
	}
		
  /**
   * ȡ�û�Ȩ������ 
   * @param sq���ݿ���ʶ���
   * @throws SQLException
   */
	public void fetchPermMask(SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM monit_roleresource WHERE role_id IN"
						+ "(SELECT role_id FROM monit_userpower WHERE user_id='"
						+ userName + "')");
		char[] code = new char[LoginManageBean.MASK_SIZE];
		for (int i = 0; i < LoginManageBean.MASK_SIZE; i++)
			code[i] = '0';
		while (rst.next())
			code[rst.getInt("resource_id")] = '1';
		String.valueOf(code);
		rst.close();
	}

	public static void main(String[] args) {
		LoginInfo loginInfo1 = new LoginInfo();
		loginInfo1.setOnLineTime(new java.util.Date());
		Debug.println("OnLineTime:" + loginInfo1.getOnLineTime());
		Debug.println("OffLineTime:" + loginInfo1.getOffLineTime());
	}
}
