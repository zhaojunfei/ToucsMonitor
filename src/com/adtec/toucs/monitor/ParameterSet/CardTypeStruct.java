package com.adtec.toucs.monitor.ParameterSet;
import java.sql.*;
import java.sql.Types;
import com.adtec.toucs.monitor.common.*;
/**
 * 卡表信息结构体
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */
public class CardTypeStruct {

	private String track_no;
	private String matchStart;
	private String cardMatch;
	private String cardStart;
	private String cardLen;
	private String bankStart="-1";
	private String bankLen;
	private String bankMatch;
	private String bankCode;
	private String cardClass;
	private String cardType;
	private String pinoffset;
	private String cardArea;
	private String rec_datetime;
	private String rec_useflag;
	private String hand_flag;
  
	public CardTypeStruct() {
	}

	public String getTrack_no(){
		return track_no;
	}
	public void setTrack_no(String inTrack_no){
		track_no=inTrack_no;
	}

	public String getMatchStart(){
		return matchStart;
	}
	public void setMatchStart(String inMatchStar){
		matchStart=inMatchStar;
	}

	public String getcardMatch(){
		return cardMatch;
	}
	public void setcardMatch(String inmatchStart){
		cardMatch=inmatchStart;
	}

	public String getcardStart(){
		return cardStart;
	}
   public void setcardStart(String inCardStart){
	   cardStart=inCardStart;
   }

   public String getcardLen(){
	   return cardLen;
   }
   public void setcardLen(String incardLen){
	   cardLen=incardLen;
   }

   public String getbankStart(){
	   return bankStart;
   }
   public void setbankStart(String inbankStart){
	   bankStart=inbankStart;
   }

   public String getbankLen(){
	   return bankLen;
   }
   public void setbankLen(String inbankLen){
	   bankLen=inbankLen;
   }

   public String getbankMatch(){
	   return bankMatch;
   }
   public void setbankMatch(String inbankMatch){
	   bankMatch=inbankMatch;
   }

   public String getbankCode(){
	   return bankCode;
   }
   public void setbankCode(String inbankCode){
	   bankCode=inbankCode;
   }

   public String getcardClass(){
	   return cardClass;
   }
   public void setcardClass(String incardClass){
	   cardClass=incardClass;
   }

   public String getcardType(){
	   return cardType;
   }
   public void setcardType(String incardType){
	   cardType=incardType;
   }

   public String getpinoffset(){
	   return pinoffset;
   }
   public void setpinoffset(String inpinoffset){
	   pinoffset=inpinoffset;
   }

   public String getcardArea(){
	   return cardArea;
   }
   public void setcardArea(String incardArea){
	   cardArea=incardArea;
   }

   public String getrec_datetime(){
	   return rec_datetime;
   }
   public void setrec_datetime(String inrec_datetime){
	   rec_datetime=inrec_datetime;
   }

   public String getrec_useflag(){
	   return rec_useflag;
   }
   public void setrec_useflag(String inrec_useflag){
	   rec_useflag=inrec_useflag;
   }
   public String get_hand_flag(){
	   return hand_flag;
   }
   public void set_hand_flag(String in_hand_flag){
	   hand_flag=in_hand_flag;
   }	
	  /**
	   *取得增加卡表的动态SQL
	   *
	   */
   public PreparedStatement makeInserStm(Connection conn) throws SQLException{
	   String strSql="insert into kernel_card_table values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	   PreparedStatement stm=conn.prepareStatement(strSql);
	   stm.setString(1,track_no);
	   stm.setString(2,matchStart);
	   stm.setString(3,toucsString.Convert(cardMatch));
	   if(cardStart==null)stm.setNull(4,Types.INTEGER);
	   else   stm.setString(4,cardStart);

	   if(cardLen==null)stm.setNull(5,Types.INTEGER);
	   else   stm.setString(5, cardLen);

	   if(bankStart==null)stm.setNull(6,Types.INTEGER);
	   else   stm.setString(6,bankStart);

	   if(bankLen==null)stm.setNull(7,Types.INTEGER);
	   else   stm.setString(7, bankLen);

	   //Types.INTEGER
	   stm.setString(8, toucsString.Convert(bankMatch));
	   stm.setString(9, toucsString.Convert(bankCode));
	   stm.setString(10, toucsString.Convert(cardClass));
	   stm.setString(11, toucsString.Convert(cardType));
	   if(pinoffset==null)
		   stm.setNull(12,Types.INTEGER);
	   else
		   stm.setString(12, pinoffset);
	   stm.setString(13,toucsString.Convert(cardArea));
	   stm.setString(14,toucsString.Convert(rec_datetime));
	   stm.setString(15,toucsString.Convert(rec_useflag));
	   stm.setString(16,"0");//手输卡标志 add by liyp 20030722
	   return stm;
   }

	  /**
	   *取得修改卡表的动态SQL
	   *
	   */
   public PreparedStatement makeUpdateStm(Connection conn,int oldTrack_no,
                            int oldMatchStart,String oldCardMatch,String oldRec_datetime,String oldRec_useflag) throws SQLException{
	   String strSql="update kernel_card_table set track_no=?,matchStart=?,cardMatch=?,";
	   strSql=strSql+"cardStart=?,cardLen=?,bankStart=?,bankLen=?,";
	   strSql=strSql+"bankMatch=?,bankCode=?,cardClass=?,cardType=?,";
	   strSql=strSql+"pinoffset=?,cardArea=?, rec_datetime= ?,rec_useflag=? ";
	   strSql=strSql+" where track_no=? and matchStart=? and cardMatch=? and rec_datetime=? and  rec_useflag=?";
	  
	   PreparedStatement stm=conn.prepareStatement(strSql);
	   stm.setString(1,track_no);
	   stm.setString(2,matchStart);
	   stm.setString(3, toucsString.Convert(cardMatch));
	   stm.setString(4,cardStart);

	   if(cardStart==null)stm.setNull(4,Types.INTEGER);
	   else   stm.setString(4, cardStart);

	   if(cardLen==null)stm.setNull(5,Types.INTEGER);
	   else   stm.setString(5, cardLen);

	   if(bankStart==null)stm.setNull(6,Types.INTEGER);
	   else     stm.setString(6,bankStart);

	   if(bankLen==null)stm.setNull(7,Types.INTEGER);
	   else   stm.setString(7, bankLen);

	   Debug.println("toucsString.Convert befor:"+bankMatch);
	   bankMatch=toucsString.Convert(bankMatch);
	   Debug.println("toucsString.Convert after"+bankMatch);

	   stm.setString(8, bankMatch);

	   stm.setString(9, toucsString.Convert(bankCode));
	   stm.setString(10, toucsString.Convert(cardClass));
	   stm.setString(11, toucsString.Convert(cardType));

	   if(pinoffset==null)stm.setNull(12,Types.INTEGER);
	   else stm.setString(12, pinoffset);

	   stm.setString(13,toucsString.Convert(cardArea));
	   stm.setString(14,toucsString.Convert(rec_datetime));
	   stm.setString(15,toucsString.Convert(rec_useflag));
     
	   stm.setInt(16,oldTrack_no);
	   stm.setInt(17, oldMatchStart);
	   stm.setString(18,toucsString.Convert(oldCardMatch));
	   stm.setString(19, toucsString.Convert(oldRec_datetime));
	   stm.setString(20, toucsString.Convert(oldRec_useflag));
	   
	   Debug.println("makeUpdateStm 修改SQL语句："+strSql);
	   Debug.println(toString());
	   return stm;
   }

	  /**
	   *取得删除卡表的动态SQL
	   *
	   */
   public PreparedStatement makeDeleteCard(Connection conn)throws SQLException{
	   String strSql="update kernel_card_table set rec_useflag='2' where track_no=?";
	   strSql=strSql+" and matchStart=? and cardMatch=? and rec_datetime=? and  rec_useflag=?";
	   PreparedStatement stm=conn.prepareStatement(strSql);
	   stm.setString(1,track_no);
	   stm.setString(2,matchStart);
	   stm.setString(3,toucsString.Convert(cardMatch));
	   stm.setString(4,toucsString.Convert(rec_datetime));
	   stm.setString(5,toucsString.Convert(rec_useflag));
	   return stm;
   }
   public String toString(){
	   String retS=String.valueOf(track_no)+"|"+String.valueOf(matchStart)+"|"+cardMatch;
	   retS=retS+"|"+String.valueOf(cardStart)+"|"+String.valueOf(cardLen);
	   retS=retS+"|"+String.valueOf(bankStart)+"|"+String.valueOf(bankLen);
	   retS=retS+"|"+bankMatch+"|"+bankCode+"|"+cardClass+"|"+cardType;
	   retS=retS+"|"+String.valueOf(pinoffset)+"|"+cardArea+"|"+rec_datetime+"|"+rec_useflag;
	   return retS;
   }
}