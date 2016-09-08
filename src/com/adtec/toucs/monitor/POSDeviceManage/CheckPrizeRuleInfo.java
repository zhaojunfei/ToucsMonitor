package com.adtec.toucs.monitor.POSDeviceManage;


import java.util.*;
import java.sql.*;

import javax.servlet.http.*;
import com.adtec.toucs.monitor.common.*;




/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CheckPrizeRuleInfo{

    private SqlAccess conn;
    private SqlAccess conn1;
    public static char globechar;
    //渠道号
    private String channel_id = "";
    //卡类型
    private String  cardtype = "";
    //卡种类
    private String cardclass = "";
    //开始日期
    private String begin_date= "";
    //结束日期
    private String end_date= "";
    //开始时间
    private String  begin_time= "";
    //结束时间
    private String  end_time ="";
    //指定商户 1指定  0 不指定
    private String ischeckMerchant= "0";
    //指定奖别 1指定  0 不指定
    private String isPrizetype = "0";
    //最低金额
    private float theMinMoney = 0;
    //参加抽奖商户总个数
    private  int   PZlevel1Counts = 0;
    //将别1个数
    private String  PZlevel1bless = "";
    //将别1祝词
    private  int   PZlevel2Counts = 0;
    //将别2个数
    private String  PZleve12bless = "";
    //将别2祝词
    private  int   PZlevel3Counts = 0;
    //将别3个数
    private String  PZlevel3bless = "";
    //将别3祝词
    private  int   PZlevel4Counts = 0;
    //将别4个数
    private String  PZleve14bless = "";
    //将别4祝词
    private  int   PZlevel5Counts = 0;
    //将别5个数
    private String  PZleve15bless = "";
    //将别5祝词

    private  int  theMerchantsCounts =  0;
    private  String[] cardclaString;
    private  String[] cardtypeString;
    public   String[]  merchantlist;
    private  Float   minMoney;
    private  Integer tempInt;
    private  String  ModifyChannelId;
    private  String  ModifyMerchantId;

    public CheckPrizeRuleInfo() {
    }

    public void setPZ1Counts(String PZ1C){
    	tempInt = java.lang.Integer.valueOf(PZ1C);
    	PZlevel1Counts = tempInt.intValue();
    }

    public void setPZ1bless(String PZ1B){
    	PZlevel1bless = PZ1B;
    }

    public void setPZ2Counts(String PZ2C){
    	tempInt = java.lang.Integer.valueOf(PZ2C);
    	PZlevel2Counts = tempInt.intValue();
    }

    public void setPZ2bless(String PZ2B){
    	PZleve12bless = PZ2B;
    }

    public void setPZ3Counts(String PZ3C){
        tempInt = java.lang.Integer.valueOf(PZ3C);
        PZlevel3Counts = tempInt.intValue();
    }

    public void setPZ3bless(String PZ3B){
    	PZlevel3bless = PZ3B;
    }

    public void setPZ4Counts(String PZ4C){
        tempInt = java.lang.Integer.valueOf(PZ4C);
        PZlevel4Counts = tempInt.intValue();
    }

    public void setPZ4bless(String PZ4B){
       PZleve14bless  = PZ4B;
    }

    public void setPZ5Counts(String PZ5C){
       tempInt = java.lang.Integer.valueOf(PZ5C);
       PZlevel5Counts = tempInt.intValue();
    }

    public void setPZ5bless(String PZ5B){
       PZleve15bless = PZ5B;
    }

    public void setchannelid(String channelID){
        channel_id = channelID;
    }

    public String getchannelid(){
        return  channel_id;
    }

    public String[] getMerchantList(){
       return  merchantlist;
    }

    public void  setMerchantList(String[]  tmerchantlist){
       Vector merchlist = new Vector();
       if (tmerchantlist != null){
    	   for (int i = 0; i < tmerchantlist.length; i++) {
    		   merchlist.add(tmerchantlist[i]);
    	   }
       }

       theMerchantsCounts = merchlist.size();
       Debug.println("Guys Go 11111111111111");
       Debug.println(theMerchantsCounts);

       StringBuffer SqlStr1 = new StringBuffer();
       StringBuffer SqlStr2 = new StringBuffer();

       SqlStr1.append("DELETE  FROM  prize_info WHERE channel_code= '"+channel_id+"'");
       int affect = -1;
       conn1 = new SqlAccess();
       try{
    	   conn1.setAutoCommit(false);
    	   affect = conn1.queryupdate(SqlStr1.toString());
    	   conn1.commit();
       }catch(SQLException e1){
           conn1.rollback();
       }finally{
           conn1.close();
       }

       SqlStr2.append("INSERT INTO prize_info(businessman_code,channel_code,prize_num1,prize_summary1,prize_num2,prize_summary2,prize_num3,prize_summary3,prize_num4,prize_summary4,prize_num5,prize_summary5) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
       conn = new SqlAccess();
       try {
    	   conn.setAutoCommit(false);
    	   PreparedStatement psmt = conn.conn.prepareStatement(SqlStr2.toString());

    	   if (theMerchantsCounts > 0){
    		   Debug.println("Fire Fire Fire");
    		   for (int i = 0; i < merchlist.size(); i++) {
    			   if (isPrizetype.equalsIgnoreCase("1")) {
    				   Debug.println("the bad job");
    				   psmt.clearParameters();
    				   psmt.setString(1, merchlist.get(i).toString());
    				   psmt.setString(2, channel_id);
    				   psmt.setInt(3, PZlevel1Counts);
    				   psmt.setString(4, PZlevel1bless);
    				   psmt.setInt(5, PZlevel2Counts);
    				   psmt.setString(6, PZleve12bless);
    				   psmt.setInt(7, PZlevel3Counts);
    				   psmt.setString(8, PZlevel3bless);
    				   psmt.setInt(9, PZlevel4Counts);
    				   psmt.setString(10, PZleve14bless);
    				   psmt.setInt(11, PZlevel5Counts);
    				   psmt.setString(12, PZleve15bless);
    				   affect = psmt.executeUpdate();
    			   }
    			   if (isPrizetype.equalsIgnoreCase("0")) {
    				   if (i == 0) {
    					   Debug.println("the 0 is ok");
    					   psmt.clearParameters();
    					   psmt.setString(1, merchlist.get(i).toString());
    					   psmt.setString(2, channel_id);
    					   psmt.setInt(3, PZlevel1Counts);
    					   psmt.setString(4, PZlevel1bless);
    					   psmt.setInt(5, PZlevel2Counts);
    					   psmt.setString(6, PZleve12bless);
    					   psmt.setInt(7, PZlevel3Counts);
    					   psmt.setString(8, PZlevel3bless);
    					   psmt.setInt(9, PZlevel4Counts);
    					   psmt.setString(10, PZleve14bless);
    					   psmt.setInt(11, PZlevel5Counts);
    					   psmt.setString(12, PZleve15bless);
    					   affect = psmt.executeUpdate();
    					   Debug.println("the 0 Has done");
    				   } else {
    					   Debug.println("the great job has done");
    					   psmt.clearParameters();
    					   psmt.setString(1, merchlist.get(i).toString());
    					   psmt.setString(2, channel_id);
    					   psmt.setInt(3, 0);
    					   psmt.setString(4, "");
    					   psmt.setInt(5, 0);
    					   psmt.setString(6, "");
    					   psmt.setInt(7, 0);
    					   psmt.setString(8, "");
    					   psmt.setInt(9, 0);
    					   psmt.setString(10, "");
    					   psmt.setInt(11, 0);
    					   psmt.setString(12, "");
    					   affect = psmt.executeUpdate();
    				   }
    			   }
    			   if (affect < 0) {
    				   continue;
    			   }
    		   }
    	   }
    	   if (theMerchantsCounts == 0){
    		   Debug.println("netcomm");
    		   psmt.clearParameters();
    		   psmt.setString(1, "000000000000000");
    		   psmt.setString(2, channel_id);
    		   psmt.setInt (3,  PZlevel1Counts);
    		   psmt.setString(4, PZlevel1bless);
    		   psmt.setInt (5,  PZlevel2Counts);
    		   psmt.setString(6, PZleve12bless);
    		   psmt.setInt (7,  PZlevel3Counts);
    		   psmt.setString(8, PZlevel3bless);
    		   psmt.setInt (9,  PZlevel4Counts);
    		   psmt.setString(10, PZleve14bless);
    		   psmt.setInt (11,  PZlevel5Counts);
    		   psmt.setString(12, PZleve15bless);
    		   affect = psmt.executeUpdate();
    	   }
    	   if (affect >= 0) {
    		   conn.conn.commit();
    		   conn.commit();
    	   } else {
    		   conn.conn.rollback();
    		   conn.rollback();
    	   }
       } catch (SQLException sqle) {
    	   sqle.printStackTrace();
    	   System.out.println(sqle.getMessage());
       } catch (Exception ex) {
    	   ex.printStackTrace();
    	   System.out.println(ex.getMessage());
       } finally {
    	   conn.close();
       }
    }

    public void setcardtype(String[] tcardtype){
    	byte[] code=new byte[64];
    	char[] resultchars = new char[16];
    	byte[] tempbyte = new byte[4];
    	for(int i=0;i<64;i++)
    		code[i]='0';
    	Integer  tempInt;
    	String   tempStr;
    	int      tempi;
    	int       i;

    	Vector resV = new Vector();
    	if (tcardtype != null){
    		for ( i =0; i< tcardtype.length; i++) {
    			resV.add(tcardtype[i]);
    			tempInt = java.lang.Integer.decode("0x"+resV.get(i).toString());
    			tempi   = tempInt.intValue();
    			code[tempi] ='1';
    		}
    	}
    	for (i =0; i<16; i++){
    		tempStr = new String(code,i*4,4);
    		tempbyte = tempStr.getBytes();
    		bytetochar(tempbyte);
    		resultchars[i] = globechar;
    	}
    	String tStr = String.valueOf(resultchars);
    	Debug.println(tStr);
    	cardtype = tStr;
    }

    public static void bytetochar(byte[] ttempbyte){
    	int tempi  =0;
    	char tempchar ='0';
    	int len = ttempbyte.length;
    	for (int i=0; i<len; i++){
    		if (ttempbyte[i] =='1'){
    			if ( i == 0){
    				tempi = tempi + 8;
    			}else if ( i == 1){
    				tempi = tempi + 4;
    			}else if ( i == 2){
    				tempi = tempi + 2;
    			}else if ( i ==3 ){
    				tempi = tempi + 1;
    			}	
    		}
    	}

    	if (tempi == 0){
    		tempchar ='0';
    	}else if (tempi ==1){
    		tempchar ='1';
    	}else if (tempi == 2){
    		tempchar ='2';
    	}else if (tempi ==3){
    		tempchar ='3';
    	}else if (tempi == 4){
    		tempchar ='4';
    	}else if (tempi ==5 ){
    		tempchar ='5';
    	}else if (tempi == 6){
    		tempchar ='6';
    	}else if (tempi ==7){
    		tempchar ='7';
    	}else if (tempi == 8){
    		tempchar ='8';
    	}else if (tempi ==9){
    		tempchar ='9';
    	}else if (tempi == 10){
    		tempchar = 'a';
    	}else if (tempi == 11){
    		tempchar = 'b';
    	}else if (tempi == 12){
    		tempchar = 'c';
    	}else if (tempi == 13){
    		tempchar = 'd';
    	}else if (tempi == 14){
    		tempchar = 'e';
    	}else if (tempi == 15){
    		tempchar = 'f';
    	}	
    	globechar = tempchar;
    }

    final static char[] HEX_DIGIT = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public String getcardtype(){
        return  cardtype;
    }

    public void setcardclass(String[] tcardclass){
    	byte[] code=new byte[64];
    	char[] resultchars = new char[16];
    	byte[] tempbyte = new byte[4];
    	for(int i=0;i<64;i++){
    		code[i]='0';
    	}	
    	Integer  tempInt;
    	String   tempStr;
    	int      tempi;
    	int       i;

    	Vector resV = new Vector();
    	if (tcardclass != null){
    		for ( i =0; i< tcardclass.length; i++) {
    			resV.add(tcardclass[i]);
    			tempInt = java.lang.Integer.valueOf(resV.get(i).toString());
    			tempi   = tempInt.intValue();
    			Debug.print(tempi);
    			code[tempi] ='1';
    		}
    	}

    	for (i =0; i<16; i++) {
    		tempStr = new String(code,i*4,4);
    		tempbyte = tempStr.getBytes();
    		bytetochar(tempbyte);
    		resultchars[i] = globechar;
    	}

    	String tStr = String.valueOf(resultchars);
    	Debug.println(tStr);
    	cardclass = tStr;
    }

    public String getcardclass(){
        return cardclass;
    }

    public void setbegin_date(String tbegin_date){
        begin_date=tbegin_date;
    }

    public String getbegin_date(){
        return begin_date;
    }

    public void setend_date(String tend_date){
        end_date=tend_date;
    }

    public String getend_date(){
        return end_date;
    }

    public void setbegin_time(String tbegin_time){
        begin_time=tbegin_time;
    }
    public String getbegin_time(){
        return begin_time;
    }

    public void  setend_time(String  tend_time)
    {
       end_time = tend_time;
    }

    public String getend_time()
    {
       return end_time;
    }

    public void setischeckMerchant(String tischeckMerchant){
    	if (tischeckMerchant.equalsIgnoreCase("ok"))
    		ischeckMerchant ="1";
    	if (tischeckMerchant.equalsIgnoreCase("no"))
    		ischeckMerchant ="0";
    	Debug.println(tischeckMerchant);
    	Debug.println(ischeckMerchant);
    }
    public String getischeckMerchant(){
        return ischeckMerchant;
    }
    
    public void setisPrizetype(String tisPrizetype){
    	if (tisPrizetype.equalsIgnoreCase("ok"))
    		isPrizetype="1";
    	if (tisPrizetype.equalsIgnoreCase("no"))
    		isPrizetype="0";
        Debug.println(tisPrizetype);
        Debug.println(isPrizetype);
    }
    
    public String getisPrizetype(){
        return isPrizetype;
    }
    
    public void settheMinMoney(String ttheMinMoney){
    	minMoney = java.lang.Float.valueOf(ttheMinMoney);
    	theMinMoney = minMoney.floatValue();
    }
    public float gettheMinMoney(){
        return theMinMoney;
    }

    public void settheMerchantsCounts(int ttheMerchantsCounts){
       theMerchantsCounts = ttheMerchantsCounts;
    }
    public int gettheMerchantsCounts(){
        return theMerchantsCounts;
    }

    public void  fetchModifyPZinfoData(HttpServletRequest request){
        ModifyChannelId = request.getParameter("Savedchannel_id");
        ModifyMerchantId = request.getParameter("merid");
        setPZ1Counts(request.getParameter("PZlevel1num"));
        setPZ1bless(request.getParameter("PZlevel1summary"));
        setPZ2Counts(request.getParameter("PZlevel2num"));
        setPZ2bless(request.getParameter("PZlevel2summary"));
        setPZ3Counts(request.getParameter("PZlevel3num"));
        setPZ3bless(request.getParameter("PZlevel3summary"));
        setPZ4Counts(request.getParameter("PZlevel4num"));
        setPZ4bless(request.getParameter("PZlevel4summary"));
        setPZ5Counts(request.getParameter("PZlevel5num"));
        setPZ5bless(request.getParameter("PZlevel5summary"));
    }
    /**
     * 从Http请求中取ATM基本信息
     * @param request Http请求
     */
    public void fetchData(HttpServletRequest request){
        setchannelid(request.getParameter("channel_id"));
        setbegin_date(request.getParameter("PZbegin_date"));
        setend_date(request.getParameter("PZend_date"));
        setbegin_time(request.getParameter("PZbegin_time"));
        setend_time(request.getParameter("PZend_time"));
        setischeckMerchant(request.getParameter("ischeckedMercnt"));
        setisPrizetype(request.getParameter("ischeckedMercntTypeOk"));
        settheMinMoney(request.getParameter("PZ_theMinMoney"));
        setPZ1Counts(request.getParameter("PZLeve1Counts"));
        setPZ1bless(request.getParameter("PZLeve1bless"));
        setPZ2Counts(request.getParameter("PZLeve2Counts"));
        setPZ2bless(request.getParameter("PZLeve2bless"));
        setPZ3Counts(request.getParameter("PZLeve3Counts"));
        setPZ3bless(request.getParameter("PZLeve3bless"));
        setPZ4Counts(request.getParameter("PZLeve4Counts"));
        setPZ4bless(request.getParameter("PZLeve4bless"));
        setPZ5Counts(request.getParameter("PZLeve5Counts"));
        setPZ5bless(request.getParameter("PZLeve5bless"));
        cardclaString = request.getParameterValues("newcheckprize");
        cardtypeString = request.getParameterValues("newcardtype");
        merchantlist   = request.getParameterValues("newmerchantlist");
        setcardtype(cardtypeString);
        setcardclass(cardclaString);
        setMerchantList(merchantlist);
    }


    public PreparedStatement makeModifyPZinfoStm(Connection conn) throws SQLException{
    	String sql="UPDATE prize_info SET("
                  +"prize_num1,prize_summary1,"
                  +"prize_num2,prize_summary2,prize_num3,prize_summary3,"
                  +"prize_num4,prize_summary4,prize_num5,prize_summary5"
                  +")"
                  +" =(?,?,?,?,?,"
                  +"?,?,?,?,?"
                  +")"
                  +"WHERE channel_code = ?"
                  +" AND businessman_code =?";
    	System.out.println(sql);
    	PreparedStatement stm=conn.prepareStatement(sql);
    	System.out.println(stm.toString());

    	stm.setInt(1,PZlevel1Counts);
    	stm.setString(2,PZlevel1bless);
    	stm.setInt(3,PZlevel2Counts);
    	stm.setString(4,PZleve12bless);
    	stm.setInt(5,PZlevel3Counts);
    	stm.setString(6,PZlevel3bless);
    	stm.setInt(7,PZlevel4Counts);
    	stm.setString(8,PZleve14bless);
    	stm.setInt(9,PZlevel5Counts);
    	stm.setString(10,PZleve15bless);
    	stm.setString(11,ModifyChannelId);
    	stm.setString(12,ModifyMerchantId);
    	return stm;
    }

    /**
     * 创建用于添加的动态SQL语句
     * @param conn 数据库连接对象
     * @return 动态SQL语句对象
     * @throws SQLException
     */
    public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
    	StringBuffer SqlStr1 = new StringBuffer();
    	SqlStr1.append("DELETE  FROM prize_rule WHERE channel_code= '"+channel_id+"'");
    	int affect = -1;
    	conn1 = new SqlAccess();
    	try{
    		conn1.setAutoCommit(false);
    		affect = conn1.queryupdate(SqlStr1.toString());
    		conn1.commit();
    	}catch(SQLException e1){
    		conn1.rollback();
        }finally{
        	conn1.close();
        }

        String sql="INSERT INTO prize_rule("
        		  +"channel_code,bit_cardtype,bit_cardclass,begin_date,"
                  +"end_date,begin_time,end_time,businessman_flag,prizelevel_flag,"
                  +"lowest_amt,businessman_num)"
                  +" VALUES(?,?,?,?,?,"
                  +"?,?,?,?,?,"
                  +"?)";
        System.out.println(sql);
        PreparedStatement stm=conn.prepareStatement(sql);
        System.out.println(stm.toString());

        stm.setString(1,channel_id);
        stm.setString(2,cardtype);
        stm.setString(3,cardclass);
        stm.setString(4,begin_date);
        stm.setString(5,end_date);
        stm.setString(6,begin_time);
        stm.setString(7,end_time);
        stm.setString(8,ischeckMerchant);
        stm.setString(9,isPrizetype);
        stm.setFloat (10,theMinMoney);
        stm.setInt (11,theMerchantsCounts);
        return stm;
    }
}
