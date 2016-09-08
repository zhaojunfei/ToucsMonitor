package com.adtec.toucs.monitor.BankFutures;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;

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
   *取得增加卡表的动态SQL
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
	 * 从Http请求中取卡表基本信息
	 * @param request Http请求
	 **/
	public void fetchData(HttpServletRequest request) {
		setCardStart(request.getParameter(cardStart));
		setCardMatch(request.getParameter(cardMatch));
		setCardDesc(request.getParameter(cardDesc));
	}
  
  /**
   *取得修改卡表的动态SQL
   * @throws UnsupportedEncodingException 
   **/
	public PreparedStatement makeUpdateStm(Connection conn,int oldCardStart,
                            int oldCardMatch,String oldCardDesc) throws SQLException, UnsupportedEncodingException{
		String strSql="update bf_card set cardstart=?,carddesc=?";
		strSql=strSql+" where cardmatch=? ";
		String str2 =cardDesc;
		str2 = new String(str2.trim().getBytes("GBK"),"ISO-8859-1");
		Debug.println("makeUpdateStm 修改SQL语句："+strSql);
		PreparedStatement stm=conn.prepareStatement(strSql);
		stm.setString(1,cardStart);
		stm.setString(2,str2);
		stm.setInt(3,oldCardStart);
		Debug.println("makeUpdateStm 修改SQL语句："+strSql);
		Debug.println(toString());
		System.out.println("返回的stm="+stm);
		return stm;
	}
  
  /**
   * 取得修改卡表的动态SQL
   * @param oldCardStart
   * @param oldCardMatch
   * @param oldCardDesc
   */
	public String UpdateStr(int oldCardStart, int oldCardMatch,String oldCardDesc) throws SQLException{
		String strSql="update bf_card set cardstart='"+cardStart+"',carddesc='"+toucsString.unConvert(cardDesc)+"' where cardmatch='"+oldCardMatch+"'";
		System.out.println("页面输入的参数是1:"+cardDesc);
		System.out.println("页面输入的参数是2"+toucsString.Convert(cardDesc)); 
		System.out.println("页面输入的参数是3"+toucsString.unConvert(cardDesc));
		return strSql;
	}

  /**
   * 取得删除卡表的动态SQL
   * @param conn
   */
	public PreparedStatement makeDeleteCard(Connection conn)throws SQLException{
		String strSql="delete from bf_card where cardmatch=?";
		PreparedStatement stm=conn.prepareStatement(strSql);
		stm.setString(1,cardMatch);
		return stm;
	}
}