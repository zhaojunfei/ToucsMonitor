package com.adtec.toucs.monitor.loginmanage;

import java.text.ParseException;
import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;




/**
 * <p>Title: 登录用户管理类</p>
 * <p>Description: 封装用户管理相关的业务逻辑</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author sunyl
 * @version 2.0
 * 修改纪录：
 * 9。20号：李成军修改，目的：根据机构查询查询登陆用户(qryLogin)，
 * 登陆时密码校验(LogIn)
 */

public class LoginManageBean {
	//权限掩码长度（大于资源表中资源序号的最大值）
	//public static final int MASK_SIZE=100; marked by liyp 20030617
	public static final int MASK_SIZE=300;
	//用户信息登记表
	private static Hashtable userTable;
	//资源与权限掩码位对照表
	private static Hashtable maskTable;

	//静态初始化代码:初始化用户信息登记表与资源掩码对照表
	static {
		userTable=new Hashtable();
		maskTable=new Hashtable();
	}

  /**
   * 构造方法
   */
	public LoginManageBean() {
	}

  /**
   * 用户登录
   * @param uid 用户ID
   * @param pwd 密码
   * @return 登录用户信息
   * @throws ParseException 
   */
	public static synchronized LoginInfo LogIn(String uid,String pwd,String ip) throws MonitorException, ParseException{
		LoginInfo info=getUserInfo(uid,pwd);
		//设置登录时间和登录IP
		info.setOnLineTime(System.currentTimeMillis());
		info.setIP(ip);
		//去除原有用户信息,不能将uid作为key从用户登记表中除去用户登录信息
		removeUser(uid);
		//登记用户
		//为该用户本次登录生成会话ID
		info.createSessionID();
		//用户登记表中的key是用户登录会话ID，不是用户ID
		userTable.put(info.getSessionID(),info);
		//userTable.put(uid,info);
		return info;
	}
	
	public static synchronized LoginInfo demoLogIn(String uid,String pwd,String ip) throws MonitorException, ParseException{
		LoginInfo info=new LoginInfo();
		info.setUserID(uid);
		info.setPermission("1,2,3");
		//设置登录时间和登录IP
		info.setOnLineTime(System.currentTimeMillis());
		info.setIP(ip);
		//去除原有用户信息,不能将uid作为key从用户登记表中除去用户登录信息
		
		//登记用户
		//为该用户本次登录生成会话ID
		info.createSessionID();
		//用户登记表中的key是用户登录会话ID，不是用户ID
		userTable.put(info.getSessionID(),info);
		//userTable.put(uid,info);
		return info;
	}

  /**
   * 用户签退
   * @param uid 用户名
   * @return 成功返回0
   */
	public static synchronized int LogOut(String uid){
		removeUser(uid);
		return 0;
	}

  /**
   * 取登录用户信息
   * @param uid 用户ID
   * @return 用户信息
   */
	public static LoginInfo getUserInfo(String uid){
		return (LoginInfo)userTable.get(uid);
	}

  /**
   * 从用户表中去除用户
   * @param uid 用户名
   */
	public static void removeUser(String uid){
		Set entrySet=userTable.entrySet();
		Iterator itr=entrySet.iterator();
		Map.Entry entry=null;
		boolean find=false;
		while(itr.hasNext()){
			entry=(Map.Entry)itr.next();
			LoginInfo logIn=(LoginInfo)entry.getValue();
			if(logIn!=null&&logIn.getUserID().equals(uid)){
				find=true;
				break;
			}
		}
		if(find){
			entrySet.remove(entry);
		}
	}

  /**
   * 校验用户合法性，取得用户信息
   * @param uid 用户ID
   * @param pwd 密码
   * @return 用户信息
   * @throws MonitorException
   * @throws ParseException 
   */
	public static LoginInfo getUserInfo(String uid,String pwd) throws MonitorException, ParseException{
		LoginInfo info=null;
		int flag=0;
		SqlAccess sq=SqlAccess.createConn();
		try{
			ResultSet  rst = sq.queryselect("SELECT monit_userinfo.*,monit_pwd_update_history.pwd_userful_date,monit_pwd_update_history.pwd_createdate,monit_pwd_update_history.last_login_date FROM monit_userinfo,monit_pwd_update_history WHERE monit_userinfo.user_id='"
                                   +uid+"' and monit_userinfo.user_id =monit_pwd_update_history.user_id");
			//校验用户是否存在，密码是否正确
			if(rst.next()){
				info=new LoginInfo();
				info.fetchData(rst);
			}else{
				flag=ErrorDefine.INVALID;
			}
			rst.close();
			//校验用户是否处于有效状态
			if(flag==0){
				if(info.isInUse() != 1 )
					flag=ErrorDefine.NOT_USE;
			}
			//校验用户密码
			if(flag==0){
				if(info.isRightPwd(pwd) != 1 )
					flag=ErrorDefine.ERRORPW;
			}
			//校验用户的有效期
			if(flag==0){
				if(info.selectUserDate()==2){
					flag=ErrorDefine.CONGEALUSER;
					//页面提示帐号密码已失效,冻结!
				}
			}
			if(flag!=0) throw new MonitorException(flag,getErrorDesc(flag));
			//取权限码
			if(flag==0){
				info.setPermission(getPermMaskCode(uid,sq));
			}
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}    
		return info;
	}

  /**
   * 校验用户是否登录
   * @param uid 用户ID
   * @param ip 登录工作站IP
   * @return 校验通过返回0，失败返回错误码
   */
	public static int logInValidate(String uid,String ip) throws MonitorException{
		LoginInfo info=getUserInfo(uid);
		if(info==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,getErrorDesc(ErrorDefine.NOT_LOGIN));
		if(!info.getIP().equals(ip))
			throw new MonitorException(ErrorDefine.MULTI_LOGIN,getErrorDesc(ErrorDefine.MULTI_LOGIN));
		return 0;
	}

  /**
   * 离线校验
   * @param uid 用户名
   * @return 校验通过返回0，失败返回错误码
   */
	public static synchronized int offLineValidate(String uid) throws MonitorException{
		LoginInfo info=getUserInfo(uid);
		if(info==null)
			throw new MonitorException(ErrorDefine.NOT_LOGIN,getErrorDesc(ErrorDefine.NOT_LOGIN));
		if(!info.isValid()){
			userTable.remove(uid);
			throw new MonitorException(ErrorDefine.TIMEOUT,getErrorDesc(ErrorDefine.TIMEOUT));
		}
		info.updateValidTime();
		return 0;
	}

  /**
   * 权限校验
   * @param uid 用户名
   * @param reqCode 请求资源号
   * @return 校验通过返回0
   */
	public static int operValidate(String uid,String reqCode) throws MonitorException{
		return 0;
		/*
		int errCode=checkPerm(uid,reqCode);
		if(errCode!=0)
			throw new MonitorException(errCode,getErrorDesc(errCode));
		return 0;
		*/
	}

  /**
   * 权限校验
   * @param info 登录用户信息
   * @param reqCode 请求资源号
   * @return 校验通过返回0
   * @throws MonitorException
   */
	public static int operValidate(LoginInfo info,String reqCode) throws MonitorException{
		return 0;
		/*
		int errCode=checkPermMask(info.getPermission(),reqCode);
		if(errCode!=0)
			throw new MonitorException(errCode,getErrorDesc(errCode));
		return 0;
		*/
	}

  /**
   * 根据权限码校验资源是否可用
   * @param maskCode 权限码
   * @param reqCode 资源代码
   * @return 资源可用返回0,否则返回错误码
   */
	public static int checkPermMask(String maskCode,String reqCode){
		//取资源对应掩码位
		int serialNo=getMaskSerial(reqCode);
		//资源是否可用
		if(serialNo<0||serialNo>=maskCode.length())
			return ErrorDefine.NOT_AUTHORIZED;
		if(maskCode.charAt(serialNo)!='1')
			return ErrorDefine.NOT_AUTHORIZED;
		return 0;
	}

  /**
   * 调用operValidate方法，功能与operValidate一样，返回为错误码，不抛出异常。
   * @param uid 用户ID
   * @param reqCode 请求资源号
   * @return 资源可用返回0，否则返回错误码
   */
	public static int checkPerm(String uid,String reqCode){
		//取用户信息
		LoginInfo info=getUserInfo(uid);
		if(info==null)
			return ErrorDefine.NOT_LOGIN;
		//取权限掩码
		String permMask=info.getPermission();
		if(permMask==null)
			return ErrorDefine.NOT_AUTHORIZED;
		//校验资源是否可用
		return checkPermMask(permMask,reqCode);
	}

  /**
   * 查询给定的机构的登录用户列表
   * @param orgID 登录用户所属机构编号
   * @return 登录用户列表
   * lichj 修改，
   * 目的：完善，由于原来没有考虑到机构分级情况
   */
	public  static Vector qryLogin(String orgID){
		if(orgID!=null)orgID=orgID.trim();
		//取得所有的用户列表
		Vector v=new Vector(userTable.values());

		//取得所属机构列表(包括自己)
		CodeManageBean codeManageBean=new CodeManageBean();
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		List orgList=null;
		try{
			orgList=codeManageBean.queryOrgList(orgID,conn);
		}catch(Exception exp){
			orgList=new ArrayList();
		}
		//去掉不是本机构构，以及下属机构则从列表中去掉。
		String strOrg="";
		String infoOrg="";
		for(int i=v.size()-1;i>=0;i--){
			LoginInfo info=(LoginInfo)v.elementAt(i);
			//是否是本机构及其下属机构的标示。初识为false,表示不是本机构及其下属机构
			boolean isTheOrg=false;
			infoOrg=info.getOrgID();
			if(infoOrg!=null) infoOrg=infoOrg.trim();
			for(int j=0;j<orgList.size();j++){
				CodeBean orgDeviceCode=(CodeBean)orgList.get(j);
				if(orgDeviceCode==null) continue;
				strOrg=orgDeviceCode.getCodeId();
				if(strOrg!=null)strOrg=strOrg.trim();
				else strOrg="";
				//如果是当前选择的机构，则退出本次顺环
				if(strOrg.equals(infoOrg)){
					isTheOrg=true;
					break;
				}
			}
			if(!isTheOrg){
				v.remove(i);
			}
		}
		return v;
	}

  /**
   * 查询所有登录用户
   * @return 登录用户列表
   */
	public static Vector qryAllLogin(){
		Vector v=new Vector(userTable.values());
		return v;
	}

  /**
   * 取错误描述
   * @param code 错误码
   * @return 错误描述
   */
	public static String getErrorDesc(int code){
		String desc="";
		switch(code){
		case ErrorDefine.INVALID:
			desc="用户不存在";
			break;
		case ErrorDefine.ERRORPW:
			desc="密码错误";
			break;
		case ErrorDefine.NOT_LOGIN:
			desc="用户未登录或会话过期";
			break;
		case ErrorDefine.MULTI_LOGIN:
			desc="用户在不同工作站登录";
			break;
		case ErrorDefine.TIMEOUT:
			desc="用户会话过期，需要重新登录";
			break;
		case ErrorDefine.NOT_AUTHORIZED:
			desc="用户权限不足";
			break;
		case ErrorDefine.NOT_USE:
			desc="用户处于无效状态";
			break;
		case ErrorDefine.CONGEALUSER:
			desc="由于用户长时间未登录帐号,帐号已冻结,请及时联系管理员!!";
			break;	
		default:
			break;
		}
		return desc;
	}

  /**
   * 初始化资源对照表
   * @param sq 数据库连接
   * @throws SQLException
   */
	public static synchronized void initMaskTable(SqlAccess sq) throws SQLException{
		maskTable.clear();
		ResultSet rst=sq.queryselect("SELECT resource_id,serialNo FROM monit_resource");
		String resourceId;
		int serialNo;
		while(rst.next()){
			resourceId=rst.getString("resource_id");
			serialNo=rst.getInt("serialNo");
			maskTable.put(resourceId,new Integer(serialNo));
		}
		rst.close();
	}

  /**
   * 取用户权限码
   * @param uid 用户ID
   * @param conn 数据库连接
   * @return 用户权限码
   * @throws SQLException
   */
	private static String getPermMaskCode(String uid,SqlAccess sq) throws SQLException{
		//查询出用户所拥有的资源和资源掩码序号
		String sql="SELECT DISTINCT monit_resource.resource_id,monit_resource.serialNo"
				+" FROM monit_resource, monit_roleResource WHERE"
				+" monit_resource.resource_id=monit_roleResource.resource_id"
				+"  AND monit_roleResource.role_id IN"
				+"(SELECT role_id FROM monit_userpower WHERE user_id='"+uid+"')";
		ResultSet rst=sq.queryselect(sql);
		String permMask;
		char[] code=new char[MASK_SIZE];
		//初始化掩码
		for(int i=0;i<MASK_SIZE;i++)
			code[i]='0';
		//取资源掩码序号，修改掩码

		int serialNo;
		while(rst.next()){
			rst.getString("resource_id");
			serialNo=rst.getInt("serialNo");
			if(serialNo>=0&&serialNo<MASK_SIZE)
				code[serialNo]='1';
		}
		permMask=String.valueOf(code);
		rst.close();
		return permMask;
	}

  /**
   * 取资源掩码位(从资源对照表中)
   * @param reqCode 资源代码
   * @return 资源掩码位,返回-1表示未取到
   */
	private static int getMaskSerial(String reqCode){
		int ret=-1;
		if(reqCode!=null){
			Integer aInt=(Integer)maskTable.get(reqCode);
			if(aInt!=null)
				ret=aInt.intValue();
			System.out.println("-------ret----------"+ret);
		}
		return ret;
	}

	public static void main(String[] args){
	}
}