package com.adtec.toucs.monitor.POSDeviceManage;

import java.util.*;
import java.sql.*;
import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ��ѯ������Ϣ����</p>
 * <p>Description: ��ѯ������Ϣ����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p>
 * @author liuxy
 * @version 1.0
 */

public class QueryCard {
	
	//private static int INTISNULL=-9999;
	private String track_no,matchStart;
	private String cardMatch,rec_useflag;
	String bankCode,rec_datetime;
	
	/**
	 * ���캯��
	 * @param track_no  �ŵ���
	 * @param matchStart  ����ʼ����λ
	 * @param cardMatch �������ʶ
	 * @param rec_useflag����״̬
	 */
	public QueryCard(String inTrack_no,String inMatchStart,String inCardMatch,String inBankCode,String inRec_datetime,String inRec_useflag) {
		track_no=inTrack_no;
		matchStart=inMatchStart;
		cardMatch=inCardMatch;
		bankCode=inBankCode;
		rec_datetime=inRec_datetime;
		rec_useflag=inRec_useflag;
	}
	
	/**
	 * ��ѯ������Ϣ
	 * @param conn
	 * @return  ��ѯ��������Ϣ�ṹ����
	 * @throws MonitorException
	 */
	public List query(Connection conn) throws MonitorException{
		String strSql="SELECT * FROM pos_cpy_card ";
		String strW="";
		int conditionCount=0;
		List tmpList=new ArrayList();
		if(track_no!=null && track_no.trim().length()!=0)tmpList.add(" track_no = "+String.valueOf(track_no)) ;
		if(matchStart!=null && matchStart.trim().length()!=0  )tmpList.add(" matchStart = '"+matchStart+"'");
		if(cardMatch!=null)
			if(cardMatch.length()!=0) tmpList.add(" cardMatch='"+cardMatch+"'");
		if(bankCode!=null )
			if(bankCode.trim().length()!=0) tmpList.add(" bankCode='"+bankCode+"'");
		if(rec_datetime!=null)
			if(rec_datetime.trim().length()!=0)tmpList.add(" rec_datetime='"+rec_datetime+"'");
		if(rec_useflag!=null)
			if(rec_useflag.trim().length()!=0) tmpList.add(" rec_useflag='"+rec_useflag+"'");
		conditionCount=tmpList.size()-1;
		strW=" where  rec_useflag!='2'";
		if(conditionCount>=0){
			strW=strW+" and  ";
			String strTmp="";
			for(int i=0;i<tmpList.size();i++){
				strTmp=(String)tmpList.get(i);
				if(i==conditionCount){
					strW=strW+" "+strTmp+" ";
				}else{
					strW=strW+strTmp+" and ";
				}
			}
		}
		strSql=strSql+strW;
		Debug.println("===============================querycard-query:"+strSql);
		List retCard=new ArrayList();
		try{
			Debug.println("SQL:"+strSql);
			Statement stm=conn.createStatement();
			ResultSet rst=stm.executeQuery(strSql);
			while(rst.next()){
				CardTypeStruct cardTypeStruct=new CardTypeStruct();
				cardTypeStruct.setTrack_no(String.valueOf(rst.getInt("track_no")));
				cardTypeStruct.setMatchStart(String.valueOf(rst.getInt("matchStart")));
				cardTypeStruct.setcardMatch(toucsString.unConvert(rst.getString("cardMatch")));
				cardTypeStruct.setcardStart(String.valueOf(rst.getInt("cardStart")));
				cardTypeStruct.setcardLen(String.valueOf(rst.getInt("cardLen")));
				cardTypeStruct.setbankStart(String.valueOf(rst.getInt("bankStart")));
				cardTypeStruct.setbankLen(String.valueOf(rst.getInt("bankLen")));
				cardTypeStruct.setbankMatch(toucsString.unConvert(rst.getString("bankMatch")));
				cardTypeStruct.setbankCode(toucsString.unConvert(rst.getString("bankCode")));
				cardTypeStruct.setcardClass(toucsString.unConvert(rst.getString("cardClass")));
				cardTypeStruct.setcardType(toucsString.unConvert(rst.getString("cardType")));
				cardTypeStruct.setpinoffset(String.valueOf(rst.getInt("pinoffset")));
				cardTypeStruct.setcardArea(toucsString.unConvert(rst.getString("cardArea")));
				cardTypeStruct.setrec_datetime(toucsString.unConvert(rst.getString("rec_datetime")));
				cardTypeStruct.setrec_useflag(toucsString.unConvert(rst.getString("rec_useflag")));
				cardTypeStruct.set_hand_flag(toucsString.unConvert(rst.getString("hand_flag")));
				retCard.add(cardTypeStruct);
			}
			rst.close();
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}
		return retCard;
	}
}