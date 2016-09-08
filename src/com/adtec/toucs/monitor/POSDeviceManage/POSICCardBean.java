package com.adtec.toucs.monitor.POSDeviceManage;

import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import java.sql.*;

/**
 * <p>Title: 商业IC卡卡表业务处理Bean</p>
 * <p>Description: 商业IC卡卡表业务处理Bean</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author liuxy
 * @version 1.0
 */
public class POSICCardBean {
	
	private static final String REGCARD="13001";  //新增商业IC卡卡表信息请求码
	private static final String MODCARD="13002";  //修改商业IC卡卡表信息请求码
	private static final String DELCARD="13003";  //删除商业IC卡卡表信息请求码
	private static final String QUERYCARD="13004"; //查询商业IC卡卡表信息请求码
	private static final String MG7550="12015";
	
	public POSICCardBean() {
	}
	
	/**
	 *添加商业IC卡卡表信息
	 * @param inCardTypeStruct
	 * @return
	 */
	public void addCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
		//如果输入为null抛出异常
		if(inCardTypeStruct==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		int modifyRow=0;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=inCardTypeStruct.makeInserStm(conn);
			Debug.println("ParameterSetBean--addCardType:intsert into"+stm.toString());
			modifyRow=stm.executeUpdate();
			conn.commit();
		}catch(SQLException sqlexp){
			try{
				conn.rollback();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
			throw new MonitorException(sqlexp);
		}finally{
			try{
				conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
		}
		Debug.println("影响了数据库记录数："+modifyRow);
		if(modifyRow<1){
			throw new MonitorException(ErrorDefine.EFFECTDBISZERO,ErrorDefine.EFFECTDBISZERODESC);
		}
	}
	
	/**
	 *
	 * @param inListCardType  list结构中存放CardTypeStruct结构体数据。
	 * @return
	 * @throws MonitorException
	 */
	public CardTypeStruct updateCardType(CardTypeStruct inCardTypeStruct,int oldTrack_no,
			int oldMatchStart,String oldCardMatch,String oldRec_datetime,String oldRec_useflag)throws MonitorException{
		//如果输入为null抛出异常
		if(inCardTypeStruct==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		int modifyRow=0;
		Debug.println("开始更新数据库！");
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		Debug.println("取到数据库连接！");
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=inCardTypeStruct.makeUpdateStm(conn,oldTrack_no,oldMatchStart,oldCardMatch,oldRec_datetime,oldRec_useflag);
			Debug.println("取到的动态SQL");
			modifyRow=stm.executeUpdate();
			//如果修改的数据库值不只一条，说明属于数据不完整。回滚事务，抛出异常
			if(modifyRow>1){
				conn.rollback();
				throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
			}
			conn.commit();
		}catch(SQLException sqlexp){
			try{
				conn.rollback();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
			throw new MonitorException(sqlexp);
		}finally{
			try{
				conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
		}
		
		if(modifyRow<1){
			throw new MonitorException(ErrorDefine.EFFECTDBISZERO,ErrorDefine.EFFECTDBISZERODESC);
		}
		Debug.println("更新数据库END！");
		return inCardTypeStruct;
	}
	
	/**
	 *删除商业IC卡卡表
	 * @param inCardTypeStruct  要删除的商业IC卡卡表结构体数据
	 * @return
	 */
	public void deleteCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
		//如果输入为null抛出异常
		if(inCardTypeStruct==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		int modifyRow=0;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=inCardTypeStruct.makeDeleteCard(conn);
			modifyRow=stm.executeUpdate();
			conn.commit();
		}catch(SQLException sqlexp){
			try{
				conn.rollback();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
			throw new MonitorException(sqlexp);
		}finally{
			try{
				conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
		}
		
		if(modifyRow<1){
			throw new MonitorException(ErrorDefine.DELETEEFFECTDBISZERO,ErrorDefine.DELETEEFFECTDBISZERODESC);
		}
	}
	
	
	/**
	 *按类型查询商业IC卡卡表
	 * @param track_no  磁道号
	 * @param matchStart  卡起始适配位
	 * @param cardMatch 卡适配标识
	 * @param bankCode  商业银行代码
	 * @param rec_datetime 卡表时间戳
	 * @param rec_useflag卡表状态
	 * @return
	 * @throws MonitorException
	 */
	public List QueryCardType(CardTypeStruct cardTypeStruct)throws MonitorException{
		String track_no=null,matchStart=null;
		String cardMatch=null, bankCode=null,rec_datetime=null,rec_useflag=null;
		if(cardTypeStruct!=null){
			track_no=cardTypeStruct.getTrack_no();
			matchStart=cardTypeStruct.getMatchStart();
			cardMatch=cardTypeStruct.getcardMatch();
			bankCode=cardTypeStruct.getbankCode();
			rec_datetime=cardTypeStruct.getrec_datetime();
			rec_useflag=cardTypeStruct.getrec_useflag();
		}
		QueryCard queryCard=new QueryCard(track_no,matchStart,cardMatch,bankCode,rec_datetime,rec_useflag);
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		
		try{
			return queryCard.query(conn);
		}catch(MonitorException exp){
			throw exp;
		}finally{
			try{
				conn.close();
			}catch(Exception e2){
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
		}
	}
	
	/**
	 * 取得用户的商业IC卡卡表操作权限
	 * @param uid
	 * @return
	 */
	public static Hashtable getOperRes(String maskCode){
		Hashtable hashTable=new Hashtable();
		if(maskCode==null) return hashTable;
		int errCode;
		Debug.println("getOperRes:maskCode========"+maskCode);
		errCode=LoginManageBean.checkPermMask(maskCode,REGCARD);
		hashTable.put(REGCARD,String.valueOf(errCode));
		
		errCode=LoginManageBean.checkPermMask(maskCode,MODCARD);
		hashTable.put(MODCARD,String.valueOf(errCode));
		
		errCode=LoginManageBean.checkPermMask(maskCode,DELCARD);
		hashTable.put(DELCARD,String.valueOf(errCode));
		
		errCode=LoginManageBean.checkPermMask(maskCode,QUERYCARD);
		hashTable.put(QUERYCARD,String.valueOf(errCode));
		
		errCode=LoginManageBean.checkPermMask(maskCode,MG7550);
		hashTable.put(MG7550,String.valueOf(errCode));
		Debug.println("商业IC卡卡表刷新权限校验：MG7550="+MG7550);
		Debug.println("商业IC卡卡表刷新权限校验：maskCode="+maskCode);
		Debug.println("商业IC卡卡表刷新权限校验：String.valueOf(errCode)="+String.valueOf(errCode));
		
		return hashTable;
	}
	
	/**
	 * 取得时间戳
	 * @return
	 */
	public String getDataTime(){
		Calendar lcaSysNow = Calendar.getInstance();
		int year=lcaSysNow.get(Calendar.YEAR);
		int month=lcaSysNow.get(Calendar.MONTH);
		int day=lcaSysNow.get(Calendar.DAY_OF_MONTH);
		
		int hh=lcaSysNow.get(Calendar.HOUR_OF_DAY);
		int mm=lcaSysNow.get(Calendar.MINUTE);
		int ss=lcaSysNow.get(Calendar.SECOND);
		
		String retSD=intToStrFormat(year,4)+intToStrFormat(month,2)+intToStrFormat(day,2);
		retSD=retSD+intToStrFormat(hh,2)+intToStrFormat(mm,2)+intToStrFormat(ss,2);
		return retSD;
	}
	
	/**
	 *
	 * @param inNum  要格式化的数据
	 * @param dig    格式化后的数据位数
	 * @return
	 */
	private String intToStrFormat(int inNum,int dig){
		String retS=String.valueOf(inNum);
		int count=dig-retS.length();
		if(inNum==8) Debug.println(count);
		if(count>0){
			String tmp="";
			for(int i=0;i<count;i++){
				tmp=tmp+"0";
			}
			retS=tmp+retS;
		}
		return retS;
	}
	
	/**
	 * 手输卡标志修改函数,
	 * @param CardStruct CardTypeStruct
	 * @return 成功1,失败：-1||0
	 * @throws MonitorException
	 */
	public int setCardManuFlag( CardTypeStruct cardStruct) throws MonitorException{
		if (cardStruct == null ) {
			return 0;
		}
		int affect = 0;
		SqlAccess conn = null;
		try {
			conn = new SqlAccess();
			String sqlStr = "UPDATE kernel_card_table SET hand_flag=? WHERE track_no=? AND matchStart=? AND cardMatch=?";
			PreparedStatement psmt = conn.conn.prepareStatement(sqlStr);
			psmt.setString(1,cardStruct.get_hand_flag());
			psmt.setString(2,cardStruct.getTrack_no());
			psmt.setString(3,cardStruct.getMatchStart());
			psmt.setString(4,cardStruct.getcardMatch());
			affect = psmt.executeUpdate();
		}
		catch (SQLException ex) {
			throw new MonitorException(ex.getErrorCode(),ex.getMessage());
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
		return affect;
	}
}