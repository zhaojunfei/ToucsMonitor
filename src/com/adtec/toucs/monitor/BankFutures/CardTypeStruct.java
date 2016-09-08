package com.adtec.toucs.monitor.BankFutures;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.*;
/**
 * ������Ϣ�ṹ��
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */
public class CardTypeStruct {
	
	private String cardStart;
	private String cardMatch;
	private String cardDesc;
	
	public String getCardStart() {
		return cardStart;
	}

	public void setCardStart(String cardStart) {
		this.cardStart = cardStart;
	}

	public String getCardMatch() {
		return cardMatch;
	}

	public void setCardMatch(String cardMatch) {
		this.cardMatch = cardMatch;
	}

	public String getCardDesc() {
		return cardDesc;
	}

	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}


  /**
   *ȡ�����ӿ���Ķ�̬SQL
   * @throws UnsupportedEncodingException,SQLException 
   **/
	public PreparedStatement makeInserStm(Connection conn) throws SQLException, UnsupportedEncodingException{
		String strSql="insert into bf_card values(?,?,?)";
		String str2 =cardDesc;
		str2 = new String(str2.trim().getBytes("GBK"),"ISO-8859-1");
		PreparedStatement stm=conn.prepareStatement(strSql);
		stm.setString(1,cardStart);
		stm.setString(2,cardMatch);
		stm.setString(3,str2);
   
		return stm;
	}

	/**
	 * ��Http������ȡ���������Ϣ
	 * @param request Http����
	 **/
	public void fetchData(HttpServletRequest request) {
		setCardStart(request.getParameter(cardStart));
		setCardMatch(request.getParameter(cardMatch));
		setCardDesc(request.getParameter(cardDesc));
	}
  
  /**
   *ȡ���޸Ŀ���Ķ�̬SQL
   * @throws UnsupportedEncodingException 
   **/
	public PreparedStatement makeUpdateStm(Connection conn,int oldCardStart,
                            int oldCardMatch,String oldCardDesc) throws SQLException, UnsupportedEncodingException{
		String strSql="update bf_card set cardstart=?,carddesc=?";
		strSql=strSql+" where cardmatch=? ";
		String str2 =cardDesc;
		str2 = new String(str2.trim().getBytes("GBK"),"ISO-8859-1");
		Debug.println("makeUpdateStm �޸�SQL��䣺"+strSql);
		PreparedStatement stm=conn.prepareStatement(strSql);
		stm.setString(1,cardStart);
		stm.setString(2,str2);
		stm.setInt(3,oldCardStart);
		Debug.println("makeUpdateStm �޸�SQL��䣺"+strSql);
		Debug.println(toString());
		System.out.println("���ص�stm="+stm);
		return stm;
	}
  
  /**
   * ȡ���޸Ŀ���Ķ�̬SQL
   * @param oldCardStart
   * @param oldCardMatch
   * @param oldCardDesc
   */
	public String UpdateStr(int oldCardStart, int oldCardMatch,String oldCardDesc) throws SQLException{
		String strSql="update bf_card set cardstart='"+cardStart+"',carddesc='"+toucsString.unConvert(cardDesc)+"' where cardmatch='"+oldCardMatch+"'";
		System.out.println("ҳ������Ĳ�����1:"+cardDesc);
		System.out.println("ҳ������Ĳ�����2"+toucsString.Convert(cardDesc)); 
		System.out.println("ҳ������Ĳ�����3"+toucsString.unConvert(cardDesc));
		return strSql;
	}

  /**
   * ȡ��ɾ������Ķ�̬SQL
   * @param conn
   */
	public PreparedStatement makeDeleteCard(Connection conn)throws SQLException{
		String strSql="delete from bf_card where cardmatch=?";
		PreparedStatement stm=conn.prepareStatement(strSql);
		stm.setString(1,cardMatch);
		return stm;
	}
}