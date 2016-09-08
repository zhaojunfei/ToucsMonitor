package com.adtec.toucs.monitor.ParameterSet;

import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import java.sql.*;
/**
 * ����ҵ����Bean
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */
public class ParameterSetBean {

    private static final String REGCARD="16001";  //����������Ϣ������
    private static final String MODCARD="16002";  //�޸Ŀ�����Ϣ������
    private static final String DELCARD="16003";  //ɾ��������Ϣ������
    private static final String QUERYCARD="16004"; //��ѯ������Ϣ������
    private static final String MG7550="12005";

    public ParameterSetBean() {
    }
    
  /**
   *��ӿ�����Ϣ
   * @param inCardTypeStruct
   * @return
   */
    public void addCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
        if(inCardTypeStruct==null){
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
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
        	Debug.println("Ӱ�������ݿ��¼����"+modifyRow);
        	if(modifyRow<1){
        		throw new MonitorException(ErrorDefine.EFFECTDBISZERO,ErrorDefine.EFFECTDBISZERODESC);
        	}
    }

  /**
   * @param inListCardType  list�ṹ�д��CardTypeStruct�ṹ�����ݡ�
   * @return inCardTypeStruct
   * @throws MonitorException
   */
    public CardTypeStruct updateCardType(CardTypeStruct inCardTypeStruct,int oldTrack_no,
            int oldMatchStart,String oldCardMatch,String oldRec_datetime,String oldRec_useflag)throws MonitorException{
        if(inCardTypeStruct==null){
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        int modifyRow=0;
        Debug.println("��ʼ�������ݿ⣡");
        SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
        Connection conn=sq.conn;
        Debug.println("ȡ�����ݿ����ӣ�");
        try{
            conn.setAutoCommit(false);
            PreparedStatement stm=inCardTypeStruct.makeUpdateStm(conn,oldTrack_no,oldMatchStart,oldCardMatch,oldRec_datetime,oldRec_useflag);
            Debug.println("ȡ���Ķ�̬SQL");
            modifyRow=stm.executeUpdate();

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
        Debug.println("�������ݿ�END��");
        return inCardTypeStruct;
    }

  /**
   * ɾ������
   * @param inCardTypeStruct  Ҫɾ���Ŀ���ṹ������
   * @return
   */
    public void deleteCardType(CardTypeStruct inCardTypeStruct)throws MonitorException{
        //�������Ϊnull�׳��쳣
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
   * �����Ͳ�ѯ����
   * @param track_no  �ŵ���
   * @param matchStart  ����ʼ����λ 
   * @param cardMatch �������ʶ
   * @param bankCode  ��ҵ���д���
   * @param rec_datetime ����ʱ���
   * @param rec_useflag����״̬
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
   * ȡ���û��Ŀ������Ȩ��
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
        Debug.println("����ˢ��Ȩ��У�飺MG7550="+MG7550);
        Debug.println("����ˢ��Ȩ��У�飺maskCode="+maskCode);
        Debug.println("����ˢ��Ȩ��У�飺String.valueOf(errCode)="+String.valueOf(errCode));

        return hashTable;
    }

  /**
   * ȡ��ʱ���
   * @return retSD
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
   * @param inNum  Ҫ��ʽ��������
   * @param dig    ��ʽ���������λ��
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

  /**
   * ���俨��־�޸ĺ���,
   * @param CardStruct CardTypeStruct
   * @return �ɹ�1,ʧ�ܣ�-1||0
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
            String sqlStr = "UPDATE kernel_card_table SET hand_flag=? WHERE track_no=? and matchStart=? and cardMatch=?";
            PreparedStatement psmt = conn.conn.prepareStatement(sqlStr);
            psmt.setString(1,cardStruct.get_hand_flag());
            psmt.setString(2,cardStruct.getTrack_no());
            psmt.setString(3,cardStruct.getMatchStart());
            psmt.setString(4,cardStruct.getcardMatch());
            affect = psmt.executeUpdate();
        } catch (SQLException ex) {
            throw new MonitorException(ex.getErrorCode(),ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return affect;
    }
}