package com.adtec.toucs.monitor.loginmanage;

import java.text.ParseException;
import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;




/**
 * <p>Title: ��¼�û�������</p>
 * <p>Description: ��װ�û�������ص�ҵ���߼�</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: adtec</p>
 * @author sunyl
 * @version 2.0
 * �޸ļ�¼��
 * 9��20�ţ���ɾ��޸ģ�Ŀ�ģ����ݻ�����ѯ��ѯ��½�û�(qryLogin)��
 * ��½ʱ����У��(LogIn)
 */

public class LoginManageBean {
	//Ȩ�����볤�ȣ�������Դ������Դ��ŵ����ֵ��
	//public static final int MASK_SIZE=100; marked by liyp 20030617
	public static final int MASK_SIZE=300;
	//�û���Ϣ�ǼǱ�
	private static Hashtable userTable;
	//��Դ��Ȩ������λ���ձ�
	private static Hashtable maskTable;

	//��̬��ʼ������:��ʼ���û���Ϣ�ǼǱ�����Դ������ձ�
	static {
		userTable=new Hashtable();
		maskTable=new Hashtable();
	}

  /**
   * ���췽��
   */
	public LoginManageBean() {
	}

  /**
   * �û���¼
   * @param uid �û�ID
   * @param pwd ����
   * @return ��¼�û���Ϣ
   * @throws ParseException 
   */
	public static synchronized LoginInfo LogIn(String uid,String pwd,String ip) throws MonitorException, ParseException{
		LoginInfo info=getUserInfo(uid,pwd);
		//���õ�¼ʱ��͵�¼IP
		info.setOnLineTime(System.currentTimeMillis());
		info.setIP(ip);
		//ȥ��ԭ���û���Ϣ,���ܽ�uid��Ϊkey���û��ǼǱ��г�ȥ�û���¼��Ϣ
		removeUser(uid);
		//�Ǽ��û�
		//Ϊ���û����ε�¼���ɻỰID
		info.createSessionID();
		//�û��ǼǱ��е�key���û���¼�ỰID�������û�ID
		userTable.put(info.getSessionID(),info);
		//userTable.put(uid,info);
		return info;
	}
	
	public static synchronized LoginInfo demoLogIn(String uid,String pwd,String ip) throws MonitorException, ParseException{
		LoginInfo info=new LoginInfo();
		info.setUserID(uid);
		info.setPermission("1,2,3");
		//���õ�¼ʱ��͵�¼IP
		info.setOnLineTime(System.currentTimeMillis());
		info.setIP(ip);
		//ȥ��ԭ���û���Ϣ,���ܽ�uid��Ϊkey���û��ǼǱ��г�ȥ�û���¼��Ϣ
		
		//�Ǽ��û�
		//Ϊ���û����ε�¼���ɻỰID
		info.createSessionID();
		//�û��ǼǱ��е�key���û���¼�ỰID�������û�ID
		userTable.put(info.getSessionID(),info);
		//userTable.put(uid,info);
		return info;
	}

  /**
   * �û�ǩ��
   * @param uid �û���
   * @return �ɹ�����0
   */
	public static synchronized int LogOut(String uid){
		removeUser(uid);
		return 0;
	}

  /**
   * ȡ��¼�û���Ϣ
   * @param uid �û�ID
   * @return �û���Ϣ
   */
	public static LoginInfo getUserInfo(String uid){
		return (LoginInfo)userTable.get(uid);
	}

  /**
   * ���û�����ȥ���û�
   * @param uid �û���
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
   * У���û��Ϸ��ԣ�ȡ���û���Ϣ
   * @param uid �û�ID
   * @param pwd ����
   * @return �û���Ϣ
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
			//У���û��Ƿ���ڣ������Ƿ���ȷ
			if(rst.next()){
				info=new LoginInfo();
				info.fetchData(rst);
			}else{
				flag=ErrorDefine.INVALID;
			}
			rst.close();
			//У���û��Ƿ�����Ч״̬
			if(flag==0){
				if(info.isInUse() != 1 )
					flag=ErrorDefine.NOT_USE;
			}
			//У���û�����
			if(flag==0){
				if(info.isRightPwd(pwd) != 1 )
					flag=ErrorDefine.ERRORPW;
			}
			//У���û�����Ч��
			if(flag==0){
				if(info.selectUserDate()==2){
					flag=ErrorDefine.CONGEALUSER;
					//ҳ����ʾ�ʺ�������ʧЧ,����!
				}
			}
			if(flag!=0) throw new MonitorException(flag,getErrorDesc(flag));
			//ȡȨ����
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
   * У���û��Ƿ��¼
   * @param uid �û�ID
   * @param ip ��¼����վIP
   * @return У��ͨ������0��ʧ�ܷ��ش�����
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
   * ����У��
   * @param uid �û���
   * @return У��ͨ������0��ʧ�ܷ��ش�����
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
   * Ȩ��У��
   * @param uid �û���
   * @param reqCode ������Դ��
   * @return У��ͨ������0
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
   * Ȩ��У��
   * @param info ��¼�û���Ϣ
   * @param reqCode ������Դ��
   * @return У��ͨ������0
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
   * ����Ȩ����У����Դ�Ƿ����
   * @param maskCode Ȩ����
   * @param reqCode ��Դ����
   * @return ��Դ���÷���0,���򷵻ش�����
   */
	public static int checkPermMask(String maskCode,String reqCode){
		//ȡ��Դ��Ӧ����λ
		int serialNo=getMaskSerial(reqCode);
		//��Դ�Ƿ����
		if(serialNo<0||serialNo>=maskCode.length())
			return ErrorDefine.NOT_AUTHORIZED;
		if(maskCode.charAt(serialNo)!='1')
			return ErrorDefine.NOT_AUTHORIZED;
		return 0;
	}

  /**
   * ����operValidate������������operValidateһ��������Ϊ�����룬���׳��쳣��
   * @param uid �û�ID
   * @param reqCode ������Դ��
   * @return ��Դ���÷���0�����򷵻ش�����
   */
	public static int checkPerm(String uid,String reqCode){
		//ȡ�û���Ϣ
		LoginInfo info=getUserInfo(uid);
		if(info==null)
			return ErrorDefine.NOT_LOGIN;
		//ȡȨ������
		String permMask=info.getPermission();
		if(permMask==null)
			return ErrorDefine.NOT_AUTHORIZED;
		//У����Դ�Ƿ����
		return checkPermMask(permMask,reqCode);
	}

  /**
   * ��ѯ�����Ļ����ĵ�¼�û��б�
   * @param orgID ��¼�û������������
   * @return ��¼�û��б�
   * lichj �޸ģ�
   * Ŀ�ģ����ƣ�����ԭ��û�п��ǵ������ּ����
   */
	public  static Vector qryLogin(String orgID){
		if(orgID!=null)orgID=orgID.trim();
		//ȡ�����е��û��б�
		Vector v=new Vector(userTable.values());

		//ȡ�����������б�(�����Լ�)
		CodeManageBean codeManageBean=new CodeManageBean();
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		List orgList=null;
		try{
			orgList=codeManageBean.queryOrgList(orgID,conn);
		}catch(Exception exp){
			orgList=new ArrayList();
		}
		//ȥ�����Ǳ����������Լ�������������б���ȥ����
		String strOrg="";
		String infoOrg="";
		for(int i=v.size()-1;i>=0;i--){
			LoginInfo info=(LoginInfo)v.elementAt(i);
			//�Ƿ��Ǳ������������������ı�ʾ����ʶΪfalse,��ʾ���Ǳ�����������������
			boolean isTheOrg=false;
			infoOrg=info.getOrgID();
			if(infoOrg!=null) infoOrg=infoOrg.trim();
			for(int j=0;j<orgList.size();j++){
				CodeBean orgDeviceCode=(CodeBean)orgList.get(j);
				if(orgDeviceCode==null) continue;
				strOrg=orgDeviceCode.getCodeId();
				if(strOrg!=null)strOrg=strOrg.trim();
				else strOrg="";
				//����ǵ�ǰѡ��Ļ��������˳�����˳��
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
   * ��ѯ���е�¼�û�
   * @return ��¼�û��б�
   */
	public static Vector qryAllLogin(){
		Vector v=new Vector(userTable.values());
		return v;
	}

  /**
   * ȡ��������
   * @param code ������
   * @return ��������
   */
	public static String getErrorDesc(int code){
		String desc="";
		switch(code){
		case ErrorDefine.INVALID:
			desc="�û�������";
			break;
		case ErrorDefine.ERRORPW:
			desc="�������";
			break;
		case ErrorDefine.NOT_LOGIN:
			desc="�û�δ��¼��Ự����";
			break;
		case ErrorDefine.MULTI_LOGIN:
			desc="�û��ڲ�ͬ����վ��¼";
			break;
		case ErrorDefine.TIMEOUT:
			desc="�û��Ự���ڣ���Ҫ���µ�¼";
			break;
		case ErrorDefine.NOT_AUTHORIZED:
			desc="�û�Ȩ�޲���";
			break;
		case ErrorDefine.NOT_USE:
			desc="�û�������Ч״̬";
			break;
		case ErrorDefine.CONGEALUSER:
			desc="�����û���ʱ��δ��¼�ʺ�,�ʺ��Ѷ���,�뼰ʱ��ϵ����Ա!!";
			break;	
		default:
			break;
		}
		return desc;
	}

  /**
   * ��ʼ����Դ���ձ�
   * @param sq ���ݿ�����
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
   * ȡ�û�Ȩ����
   * @param uid �û�ID
   * @param conn ���ݿ�����
   * @return �û�Ȩ����
   * @throws SQLException
   */
	private static String getPermMaskCode(String uid,SqlAccess sq) throws SQLException{
		//��ѯ���û���ӵ�е���Դ����Դ�������
		String sql="SELECT DISTINCT monit_resource.resource_id,monit_resource.serialNo"
				+" FROM monit_resource, monit_roleResource WHERE"
				+" monit_resource.resource_id=monit_roleResource.resource_id"
				+"  AND monit_roleResource.role_id IN"
				+"(SELECT role_id FROM monit_userpower WHERE user_id='"+uid+"')";
		ResultSet rst=sq.queryselect(sql);
		String permMask;
		char[] code=new char[MASK_SIZE];
		//��ʼ������
		for(int i=0;i<MASK_SIZE;i++)
			code[i]='0';
		//ȡ��Դ������ţ��޸�����

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
   * ȡ��Դ����λ(����Դ���ձ���)
   * @param reqCode ��Դ����
   * @return ��Դ����λ,����-1��ʾδȡ��
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