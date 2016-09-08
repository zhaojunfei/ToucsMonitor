package com.adtec.toucs.monitor.BankFutures;
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

public class QueryCard {

	private String cardStart;
	private String cardMatch;
	private String cardDesc;
  	
  /**
   *构造函数
   * @param track_no  磁道号
   * @param matchStart  卡起始适配位
   * @param cardMatch 卡适配标识
   * @param rec_useflag卡表状态
   */
	public QueryCard(String inCardStart,String inCardMatch,String inCardDesc) {
		cardStart=inCardStart;
		cardMatch=inCardMatch;
		cardDesc=inCardDesc;
	}
  /**
   * 查询烟草卡表信息
   * @param conn
   * @return  retCard 查询到卡表信息结构集合
   * @throws MonitorException
   */
	public List query(Connection conn) throws MonitorException{
		String strSql="";
		strSql="select * from bf_card ";
		String strW="";
		int conditionCount=0;
		List tmpList=new ArrayList();
  
		if(cardStart!=null && cardStart.trim().length()!=0)tmpList.add(" cardStart = "+String.valueOf(cardStart)) ;
		if(cardMatch!=null && cardMatch.trim().length()!=0  )tmpList.add(" cardMatch = '"+cardMatch+"'");

		conditionCount=tmpList.size()-1;
		strW=" where  ";
		if(conditionCount>=0){
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
		if(cardStart==null&&cardMatch==null&&cardDesc==""){
			strSql="select * from bf_card ";
		}
		Debug.println("===============================querycard-query:"+strSql);
		List retCard=new ArrayList();
		try{
			Debug.println("SQL:"+strSql);
			Statement stm=conn.createStatement();
			ResultSet rst=stm.executeQuery(strSql);
			while(rst.next()){
				CardTypeStruct cardTypeStruct=new CardTypeStruct();
				cardTypeStruct.setCardStart(String.valueOf(rst.getInt("cardStart")));
				cardTypeStruct.setCardMatch(String.valueOf(rst.getInt("cardMatch")));
				cardTypeStruct.setCardDesc(String.valueOf(rst.getString("cardDesc")));

				retCard.add(cardTypeStruct);
			}
			rst.close();
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}
		return retCard;
  }
}