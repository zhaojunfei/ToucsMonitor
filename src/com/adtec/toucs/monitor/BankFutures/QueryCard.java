package com.adtec.toucs.monitor.BankFutures;
import java.util.*;
import java.sql.*;
import com.adtec.toucs.monitor.common.*;
/**
 * ��ѯ������Ϣ����
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
   *���캯��
   * @param track_no  �ŵ���
   * @param matchStart  ����ʼ����λ
   * @param cardMatch �������ʶ
   * @param rec_useflag����״̬
   */
	public QueryCard(String inCardStart,String inCardMatch,String inCardDesc) {
		cardStart=inCardStart;
		cardMatch=inCardMatch;
		cardDesc=inCardDesc;
	}
  /**
   * ��ѯ�̲ݿ�����Ϣ
   * @param conn
   * @return  retCard ��ѯ��������Ϣ�ṹ����
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