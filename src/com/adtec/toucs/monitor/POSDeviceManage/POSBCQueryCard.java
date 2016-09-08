package com.adtec.toucs.monitor.POSDeviceManage;
import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;
/**
 * 查询卡表信息集合
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */

public class POSBCQueryCard {


	private String card_id,track_no;
	private String card_match,cardmatch_len;
	private String card_len,company_id;
	String datetime,rec_useflag;

  /**
   *构造函数
   * @param track_no  磁道号
   * @param matchStart  卡起始适配位
   * @param cardMatch 卡适配标识
   * @param rec_useflag卡表状态
   */
	public POSBCQueryCard(String inCard_id,String inTrack_no,String inCard_match,String inCardmatch_len,String inCard_len,String inCompany_id,String inDatetime,String inRec_useflag) 
	{
		card_id=inCard_id;
		track_no=inTrack_no;
		card_match=inCard_match;
		cardmatch_len=inCardmatch_len;
		card_len=inCard_len;
		company_id=inCompany_id;
		datetime=inDatetime;
		rec_useflag=inRec_useflag;
	}
  /**
   * 查询卡表信息
   * @param conn
   * @return  查询到卡表信息结构集合
   * @throws MonitorException
   */
	public List query(Connection conn) throws MonitorException{
		String strSql="SELECT * FROM pos_yfk_card";
		String strW="";
		String strOrd="";
		int conditionCount=0;
		List tmpList=new ArrayList();
		if(card_id!=null && card_id.trim().length()!=0  ){
			tmpList.add(" card_id = '"+card_id+"'");
		}
		if(track_no!=null && track_no.trim().length()!=0){
			tmpList.add(" track_no = "+String.valueOf(track_no)) ;
		}
		if(card_match!=null && card_match.trim().length()!=0  ){
			tmpList.add(" card_match = '"+card_match+"'");
		}
		if(cardmatch_len!=null && cardmatch_len.trim().length()!=0){
			tmpList.add(" cardmatch_len='"+cardmatch_len+"'");
		}
		if(card_len!=null && card_len.trim().length()!=0){
			tmpList.add(" card_len='"+card_len+"'");
		}
		if(company_id!=null && company_id.trim().length()!=0){
			tmpList.add(" company_id='"+company_id+"'");
		}
		if(datetime!=null && datetime.trim().length()!=0){
			tmpList.add(" datetime='"+datetime+"'");
		}
		if(rec_useflag!=null && rec_useflag.trim().length()!=0){
			tmpList.add(" rec_useflag='"+rec_useflag+"'");
		}
		conditionCount=tmpList.size()-1;
		strW=" where  rec_useflag!='2'";
		if(conditionCount>=0){
			strW=strW+" AND  ";
			String strTmp="";
			for(int i=0;i<tmpList.size();i++){
				strTmp=(String)tmpList.get(i);
				if(i==conditionCount){
					strW=strW+" "+strTmp+" ";
				}else{
					strW=strW+strTmp+" AND ";
				}
			}
		}
		strOrd="order by card_id+0";
		strSql=strSql+strW+strOrd;
		//strSql=strSql+strW;
		Debug.println("===============================querycard-query:"+strSql);
		List list=new ArrayList();
		try{
			Debug.println("SQL:"+strSql);
			Statement stm=conn.createStatement();
			ResultSet rst=stm.executeQuery(strSql);
			while(rst.next()){
				POSBCCardInfo cardTypeStruct=new POSBCCardInfo();
				cardTypeStruct.setCard_id(rst.getString("card_id"));
				cardTypeStruct.setTrack_no(String.valueOf(rst.getInt("track_no")));
				cardTypeStruct.setCard_match(rst.getString("card_match"));
				cardTypeStruct.setCardmatch_len(rst.getString("cardmatch_len"));
				cardTypeStruct.setCard_len(rst.getString("card_len"));
				cardTypeStruct.setCompany_id(rst.getString("company_id"));
				cardTypeStruct.setDatetime(rst.getString("datetime"));
				cardTypeStruct.setRec_useflag(rst.getString("rec_useflag"));
				cardTypeStruct.setHand_flag(rst.getString("hand_flag"));
				cardTypeStruct.setMemo1(rst.getString("memo1"));
				cardTypeStruct.setMemo2(rst.getString("memo2"));

				list.add(cardTypeStruct);
			}
			rst.close();
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}
		return list;
	}
}
