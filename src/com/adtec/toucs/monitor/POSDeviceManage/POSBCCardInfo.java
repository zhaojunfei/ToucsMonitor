package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpServletRequest;

import com.adtec.toucs.monitor.common.Util;
import com.adtec.toucs.monitor.common.toucsString;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;

public class POSBCCardInfo {

	//卡序号
	private String card_id = "";
	//磁道号
	private String track_no = "";
	//卡适配标识(卡BIN)
	private String card_match = "";
	//卡BIN长度
	private String cardmatch_len = "";
	//卡长度
	private String card_len = "";
	//公司编号
	private String company_id = "";
	//日期时间戳
	private String datetime = "";
	//卡使用标识
	private String rec_useflag = "";
	//手输卡标识
	private String hand_flag = "";
	//备注1
	private String memo1 = "";
	//备注2
	private String memo2 = "";
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String cardId) {
		card_id = cardId;
	}
	public String getTrack_no() {
		return track_no;
	}
	public void setTrack_no(String trackNo) {
		track_no = trackNo;
	}
	public String getCard_match() {
		return card_match;
	}
	public void setCard_match(String cardMatch) {
		card_match = cardMatch;
	}
	public String getCardmatch_len() {
		return cardmatch_len;
	}
	public void setCardmatch_len(String cardmatchLen) {
		cardmatch_len = cardmatchLen;
	}
	public String getCard_len() {
		return card_len;
	}
	public void setCard_len(String cardLen) {
		card_len = cardLen;
	}
	
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String companyId) {
		company_id = companyId;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getRec_useflag() {
		return rec_useflag;
	}
	public void setRec_useflag(String recUseflag) {
		rec_useflag = recUseflag;
	}
	public String getHand_flag() {
		return hand_flag;
	}
	public void setHand_flag(String handFlag) {
		hand_flag = handFlag;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	
	/**
	 * 从Http请求中取商户信息
	 * @param request Http请求
	 */
	public void fetchData(HttpServletRequest request) {
		setCard_id(request.getParameter("card_id"));
		setCard_match(request.getParameter("card_match"));
		setCardmatch_len(request.getParameter("cardmatch_len"));
		setCard_len(request.getParameter("card_len"));
		setCompany_id(request.getParameter("company_id"));
		setDatetime(request.getParameter("datetime"));
		setRec_useflag(request.getParameter("rec_useflag"));
		setHand_flag(request.getParameter("hand_flag"));
		setMemo1(request.getParameter("memo1"));
		setMemo2(request.getParameter("memo2"));
	}
	  /**
	   * 从查询结果中取商户信息
	   * @param rst 查询结果集
	   * @throws SQLException
	   */
		public void fetchData(ResultSet rst) throws SQLException {
			setCard_id(toucsString.unConvert(rst.getString("card_id")));
			setCard_match(toucsString.unConvert(rst.getString("card_match")));
			setCardmatch_len(toucsString.unConvert(rst.getString("cardmatch_len")));
			setCard_len(toucsString.unConvert(rst.getString("card_len")));
			setCompany_id(toucsString.unConvert(rst.getString("company_id")));
			setDatetime(toucsString.unConvert(rst.getString("datetime")));
			setRec_useflag(toucsString.unConvert(rst.getString("rec_useflag")));
			setHand_flag(toucsString.unConvert(rst.getString("hand_flag")));
			setMemo1(toucsString.unConvert(rst.getString("memo1")));
			setMemo2(toucsString.unConvert(rst.getString("memo2")));
		}	
	
	/**
	 *取得增加卡表的动态SQL
	 */
	public PreparedStatement makeInserStm(Connection conn) throws SQLException{
		String sql="INSERT INTO pos_yfk_card (track_no,card_match,cardmatch_len,card_len,company_id,datetime,rec_useflag,hand_flag,memo1,memo2) VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm=conn.prepareStatement(sql);
		Util util = new Util();
		//stm.setString(1,card_id);
		stm.setString(1,track_no);
		stm.setString(2,toucsString.Convert(card_match));
		
		if(cardmatch_len==null)stm.setNull(5,Types.INTEGER);
		else   stm.setString(3, cardmatch_len);
		
		if(card_len==null)stm.setNull(5,Types.INTEGER);
		else   stm.setString(4, card_len);
		
		stm.setString(5,toucsString.Convert(company_id));
		Debug.println("===============================company_id:"+company_id);
		stm.setString(6,util.getCurrentDate()+util.getCurrentTime());
		stm.setString(7,"0");
		stm.setString(8,"0");
		stm.setString(9,toucsString.Convert(memo1));
		Debug.println("===============================memo1:"+memo1);
		stm.setString(10,toucsString.Convert(memo2));
		Debug.println("===============================memo2:"+memo2);
		System.out.println("添加SQL语句"+sql);
		return stm;
	}
	
	/**
	 *取得修改卡表的动态SQL
	 */
	public PreparedStatement makeUpdateStm(Connection conn,String oldCard_id,
			String oldCard_match) throws SQLException{
		Util util = new Util();
		String strSql="UPDATE pos_yfk_card SET track_no=?,card_match=?,";
		strSql=strSql+"cardmatch_len=?,card_len=?,company_id=?,datetime=?,";
		strSql=strSql+"rec_useflag=?,hand_flag=?,memo1=?,memo2=?";
		strSql=strSql+" WHERE card_id=? AND card_match=?";
		
		PreparedStatement stm=conn.prepareStatement(strSql);
		
		//stm.setString(1,card_id);
		stm.setString(1,track_no);
		stm.setString(2, toucsString.Convert(card_match));
		
		if(cardmatch_len==null)stm.setNull(3,Types.INTEGER);
		else   stm.setString(3, cardmatch_len);
		
		if(card_len==null)stm.setNull(4,Types.INTEGER);
		else   stm.setString(4, card_len);
		
		Debug.println("toucsString.Convert befor:"+company_id);
		stm.setString(5, toucsString.Convert(company_id));
		Debug.println("toucsString.Convert after"+company_id);
	
		
		stm.setString(6,util.getCurrentDate()+util.getCurrentTime());
		stm.setString(7,toucsString.Convert(rec_useflag));
		stm.setString(8,"0");
		stm.setString(9,toucsString.Convert(memo1));
		stm.setString(10,toucsString.Convert(memo2));
		
		stm.setString(11,oldCard_id);
		stm.setString(12, oldCard_match);
		
		Debug.println("makeUpdateStm 修改SQL语句："+strSql);
		Debug.println(toString());
		
		return stm;
	}
	/**
	 *取得删除卡表的动态SQL
	 */
	public PreparedStatement makeDeleteCard(Connection conn)throws SQLException{
		String sql = "DELETE FROM pos_yfk_card WHERE card_match = ?";
		PreparedStatement stm=conn.prepareStatement(sql);
		stm.setString(1,card_match);
		
		System.out.println("删除SQL语句"+sql);
		return stm;
	}
	
	public String toString(){
		String retS=String.valueOf(card_id)+"|"+String.valueOf(track_no)+"|"+card_match;
		retS=retS+"|"+String.valueOf(cardmatch_len)+"|"+String.valueOf(card_len);
		retS=retS+"|"+String.valueOf(company_id)+"|"+String.valueOf(datetime);
		retS=retS+"|"+rec_useflag+"|"+String.valueOf(memo1)+"|"+String.valueOf(memo2);
		return retS;
	}
}
