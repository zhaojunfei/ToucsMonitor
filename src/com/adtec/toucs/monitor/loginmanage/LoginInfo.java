package com.adtec.toucs.monitor.loginmanage;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.*;

/**
 * <p>Title: 登录用户信息类</p>
 * <p>Description:封装登录用户的相关信息</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author sunyl
 * @version 2.0
 */

public class LoginInfo {
	//数据库联接
	private SqlAccess conn = null;
	// 用户登录失效时间间隔,单位为分钟(从配置文件中取得，缺省为0，表示不做离线校验)
	public static int timeOut = 0;
	// 用户名
	private String userName;
	// 用户ID
	private String userId;
	// 密码
	private String userPwd;
	// 登录工作站IP
	private String IP;
	// 所属机构编码
	private String orgId;
	// 权限码
	private String permission;
	// 用户描述
	private String userDesc;
	// 用户状态
	private int userState;
	// 密码次数
	private int PasswdNum;
	// 登录(上线)时刻
	private java.util.Date onLineTime;
	// 登录失效(离线)时刻
	private java.util.Date offLineTime;
	// 用户登录会话ID(这个ID标识用户的一次有效登录，会通过Cookie返回客户端)
	private String sessionID;
	// 登陆用户的机构级别
	private int userLevel;
	//用户密码的有效期:syl
	private int pwdUserfulDate;
	//用户密码的创建时间:syl
	private String pwdCreateDate;	
	//上一次登录时间:syl
	private String lastLoginDate;

	/**
	 * 构造方法
	 */
	public LoginInfo() {
		onLineTime = new java.util.Date();
		offLineTime = new java.util.Date();
		updateValidTime();
	}

	// 用户名属性读写
	public void setUserName(String name) {
		userName = name;
	}

	public String getUserName() {
		return userName;
	}

	// 用户ID属性读写
	public void setUserID(String id) {
		userId = id;
	}

	public String getUserID() {
		return userId;
	}

	// 登录工作站IP属性读写
	public void setIP(String ip) {
		IP = ip;
	}

	public String getIP() {
		return IP;
	}

	// 机构编号属性读写
	public void setOrgID(String id) {
		orgId = id;
	}

	public String getOrgID() {
		return orgId;
	}

	// 权限码属性读写
	public void setPermission(String perm) {
		permission = perm;
	}

	public String getPermission() {
		return permission;
	}

	// 设置登录时间
	public void setOnLineTime(long time) {
		onLineTime.setTime(time);
		updateValidTime();
	}

	public void setOnLineTime(java.util.Date time) {
		onLineTime = time;
		updateValidTime();
	}

	// 取登录时间
	public String getOnLineTime() {
		return Util.formatDate(onLineTime);
	}

	// 取离线时间
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
   * 校验用户登录是否有效
   * @return true-有效,false-无效
   */
	public boolean isValid() {
		return offLineTime.getTime() > System.currentTimeMillis();
	}

  /**
   * 更新离线时间
   */
	public void updateValidTime() {
		offLineTime.setTime(System.currentTimeMillis() + timeOut * 60 * 1000);
	}

  /**
   * 产生登录会话ID
   */
	public void createSessionID() {
		// 将用户ID和登录时间组合后用RC6算法加密
		sessionID = RC6.encrypt(userId + getOnLineTime());
	}

  /**
   * 取登录会话ID
   * 
   * @return 用户登录会话ID
   */
	public String getSessionID() {
		return sessionID;
	}

  /**
   * 转换为XML格式报文，填充到字符串缓冲对象中
   * @param buf 字符串缓冲对象
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
   * 转换为XML格式报文
   * @return XML格式报文
   */
	public String toXML() {
		StringBuffer buf = new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

  /**
   * 覆盖的toString()方法 
   * @return
   */
	public String toString() {
		return "[" + userName + "|" + orgId + "|" + permission + "]";
	}

  /**
   * 从查询结果中取用户信息 
   * @param rst查询结果集
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
   * 校验密码是否正确
   * @param pwd 密码
   * @return 正确返回true
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
   * 操作员是否处于有效状态
   * @return 有效返回true
   */
	public int isInUse() {
		return userState;
	}
	
  /**
   * 查询用户密码的有效期 
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
				java.util.Date date1 = df.parse(rs.getString(4));//在数据库中去密码的创建时间
				long dill = rightNow.getTimeInMillis()-date1.getTime();//密码已使用了的期限(ms)
				int day1 = Integer.parseInt(String.valueOf(dill/86400000));
				System.out.println("自修改密码后已经过了"+day1+"天");
				System.out.println("密码的有效期是"+rs.getInt(3));
				if(day1<rs.getInt(3)&&(rs.getInt(3)-day1)>3)//时间差小于有效期、并且截止到有效期的天数大于3天
				{
					affect = 0;//正常登录
					
				}else if(day1<rs.getInt(3)&&(rs.getInt(3)-day1)<3){
					affect = 1;//有效期前3天的提示
				}else {
					affect = 2;//密码的有效期已失效,系统自动冻结用户状态
					updateState();//冻结用户的状态
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
   * 冻结用户的状态
   * @throws MonitorException 
   * @throws ParseException 
   * @author sunyl
   * @param  user_state 1-正常; 0-停用
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
   * 取用户权限掩码 
   * @param sq数据库访问对象
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
