package com.adtec.toucs.monitor.BankFutures;

import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

import java.io.UnsupportedEncodingException;
import java.sql.*;
/**
 * 卡表业务处理Bean
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author sunyl
 * @version 1.0
 */
public class BfCardBean {

    private static final String REGCARD="17401";  //新增卡表信息请求码
    private static final String MODCARD="17402";  //修改卡表信息请求码
    private static final String DELCARD="17403";  //删除卡表信息请求码
    private static final String QUERYCARD="17404"; //查询卡表信息请求码
    private static final String MG7550="12005";

    public BfCardBean() {
    }
    
    /**
     * 添加卡表信息
     * @param inCardTypeStruct
	 * @throws MonitorException
     */
    public void addCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
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
            }
            throw new MonitorException(sqlexp);
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally{
            try{
                conn.close();
            }catch(Exception e2){
            	e2.printStackTrace();
            }
        }
        Debug.println("影响了数据库记录数："+modifyRow);
        if(modifyRow<1){
            throw new MonitorException(ErrorDefine.EFFECTDBISZERO,ErrorDefine.EFFECTDBISZERODESC);
        }
    }

    /**
     * @param inListCardType  list结构中存放CardTypeStruct结构体数据。
     * @return inCardTypeStruct
     * @throws MonitorException
     */
    public CardTypeStruct updateCardType(CardTypeStruct inCardTypeStruct,int oldCardStart,
        int oldCardMatch,String oldCardDesc)throws MonitorException{
        if(inCardTypeStruct==null)
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        int modifyRow=0;
        Debug.println("开始更新数据库！");
        SqlAccess sq = SqlAccess.createConn();
        Debug.println("取到数据库连接！");
        try{
        	Debug.println("取到的动态SQL");
        	String sql = inCardTypeStruct.UpdateStr(oldCardStart, oldCardMatch, oldCardDesc);
        	modifyRow = sq.queryupdate(sql);
            if(modifyRow>1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
        }catch(SQLException sqlexp){
        	throw new MonitorException(sqlexp);
        }finally{
            try{
                sq.close();
                }catch(Exception e2){
                	e2.printStackTrace();
                }
        }
        if(modifyRow<1){
            throw new MonitorException(ErrorDefine.EFFECTDBISZERO,ErrorDefine.EFFECTDBISZERODESC);
        }
        Debug.println("更新数据库END！");
        return inCardTypeStruct;
    }

    /**
     *删除卡表
     * @param inCardTypeStruct  要删除的卡表结构体数据
     * @throws MonitorException
     */
    public void deleteCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
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
     *按类型查询卡表
     * @param track_no  磁道号
     * @param matchStart  卡起始适配位
     * @param cardMatch 卡适配标识
     * @param bankCode  商业银行代码
     * @param rec_datetime 卡表时间戳
     * @param rec_useflag卡表状态
     * @throws MonitorException
     */
    public List QueryCardType(CardTypeStruct cardTypeStruct)throws MonitorException{
    	String cardStart = "";
    	String cardMatch = "";
    	String cardDesc = "";
    	if(cardTypeStruct != null){
    		cardStart = cardTypeStruct.getCardStart();
    		cardMatch = cardTypeStruct.getCardMatch();
    		cardDesc  = cardTypeStruct.getCardDesc(); 
    		if(cardDesc == null || cardDesc == " "){
    			cardDesc = "";
    		}
    	}
        QueryCard queryCard=new QueryCard(cardStart,cardMatch,cardDesc);
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
  * 取得用户的卡表操作权限
  * @param maskCode
  * @return Hashtable
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
        Debug.println("卡表刷新权限校验：MG7550="+MG7550);
        Debug.println("卡表刷新权限校验：maskCode="+maskCode);
        Debug.println("卡表刷新权限校验：String.valueOf(errCode)="+String.valueOf(errCode));

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
     * @param inNum  要格式化的数据
     * @param dig    格式化后的数据位数
     * @return retS
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
}