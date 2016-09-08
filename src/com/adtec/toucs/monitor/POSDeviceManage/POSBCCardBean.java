package com.adtec.toucs.monitor.POSDeviceManage;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.adtec.toucs.monitor.ParameterSet.QueryCard;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.JNDINames;

import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;



/**
 * <p>Title: 预付卡卡表业务处理Bean</p>
 * <p>Description: 预付卡卡表业务处理Bean</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: adtec</p>
 * @author syl
 */
public class POSBCCardBean {
	
	private static final String REGCARD="16801";  //新增预付卡pos卡卡表信息请求码
	private static final String MODCARD="16803";  //修改预付卡pos卡卡表信息请求码
	private static final String DELCARD="16802";  //删除预付卡pos卡卡表信息请求码
	private static final String QUERYCARD="16804"; //查询预付卡pos卡卡表信息请求码
	//private static final String MG7550="12015";
	
	/**
	 * 查询当前表中最大的card_id
	 * @return int flag
	 * @throws MonitorException 
	 * @throws SQLException 
	 */
	public String QueryCardId() throws MonitorException, SQLException{
		SqlAccess sq  = SqlAccess.createConn();
		String str = "0";
		int flag = 0;
		String sql = "select card_id from pos_yfk_card order by desc";
		ResultSet rst = sq.queryselect(sql);
		if(rst.next()){
			str = rst.getString(1);
			if(str !=null){
				flag = Integer.parseInt(str)+1;
			}
		}
		str = flag+"";
		rst.close();
		return str;
	}
	
	/**
	 * 添加预付卡卡表信息
	 * @param cardInfo卡表信息
	 * @return flag
	 * @throws MonitorException,SQLException
	 */	
	public void addCardType(POSBCCardInfo cardInfo)throws MonitorException{
		if(cardInfo==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		int modifyRow=0;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=cardInfo.makeInserStm(conn);
			Debug.println("POSBCCardBean--addCardType:intsert into"+stm.toString());
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
		Debug.println("影响了数据库记录数："+modifyRow);
		if(modifyRow<1){
			throw new MonitorException(ErrorDefine.EFFECTDBISZERO,"数据添加失败,请检查输入的数据是否有误.");
		}
	}
	
	
	/**
	 * 修改预付卡卡表信息
	 * @param inListCardType  list结构中存放POSBCCardInfo结构体数据。
	 * @return null
	 * @throws MonitorException,SQLException
	 */
	public POSBCCardInfo updateCardType(POSBCCardInfo cardInfo,String oldCard_id,
			String oldCard_match)throws MonitorException{
		if(cardInfo==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);	
		int modifyRow=0;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=cardInfo.makeUpdateStm(conn,oldCard_id,oldCard_match);
			Debug.println("POSBCCardBean--updateCardType "+stm.toString());
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
			throw new MonitorException(ErrorDefine.EFFECTDBISZERO,"数据修改失败,请检查输入的数据是否有误.");
		}
		Debug.println("更新数据库END！");
		return cardInfo;
	}

	
	/**
	 * 删除预付卡卡表信息
	 * @param cardInfo 卡表信息
	 * @return null
	 * @throws MonitorException,SQLException
	 */	
	public void deleteCardType(POSBCCardInfo cardInfo)throws MonitorException{
		if(cardInfo==null)
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		int modifyRow=0;
		SqlAccess sq=new SqlAccess(JNDINames.MONITOR_DATASOURCE);
		Connection conn=sq.conn;
		try{
			conn.setAutoCommit(false);
			PreparedStatement stm=cardInfo.makeDeleteCard(conn);
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
	 * 删除预付卡pos卡表信息(批量删除)
	 * @param strArray 卡表信息数组
	 * @return null
	 * @throws MonitorException
	 */	
	public void deleteBCCards(String[] strArray)throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		StringBuffer strbf = new StringBuffer();
		for(int i=0;i<strArray.length;i++){
			strbf.append("'").append(strArray[i]).append("',");
		}
		try {
			//设置数据库连接提交方式为非自动提交
			//删除POS设备基本信息(多条)
			String sql="DELETE FROM pos_yfk_card WHERE card_match IN("+strbf.substring(0,strbf.length()-1)+")";
			int flag = sq.queryupdate(sql);
			System.out.println("flag="+flag);	    	 
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	
	/**
	 * 按类型查询预付卡卡表信息
	 * @param track_no  磁道号
	 * @param card_match  卡适配标识(BIN)
	 * @param cardmatch_len 卡适配标识长度(卡BIN长度)
	 * @param company_id  预付卡公司编号
	 * @param rec_datetime 卡表时间戳
	 * @param rec_useflag卡表状态
	 * @return List 返回条件查询结果的集合
	 * @throws MonitorException
	 */
	public List QueryCardType(POSBCCardInfo cardInfo)throws MonitorException{
		String card_id=null;
		String track_no=null;
		String card_match=null;
		String cardmatch_len=null;
		String card_len=null;
		String company_id=null;
		String datetime=null;
		String rec_useflag=null;
		String memo1=null;
		String memo2=null;
		if(cardInfo!=null){
			card_id=cardInfo.getCard_id();
			track_no=cardInfo.getTrack_no();
			card_match=cardInfo.getCard_match();
			cardmatch_len=cardInfo.getCardmatch_len();
			card_len=cardInfo.getCard_len();
			company_id=cardInfo.getCompany_id();
			rec_useflag=cardInfo.getRec_useflag();
			memo1=cardInfo.getMemo1();
			memo2=cardInfo.getMemo2();
		}
		POSBCQueryCard queryCard=new POSBCQueryCard(card_id,track_no,card_match,cardmatch_len,card_len,company_id,datetime,rec_useflag);
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
 * 取得用户的预付卡pos卡卡表操作权限
 * @param maskCode
 * @return Hashtable
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
	
	//errCode=LoginManageBean.checkPermMask(maskCode,MG7550);
	//hashTable.put(MG7550,String.valueOf(errCode));
	//Debug.println("预付卡pos卡卡表刷新权限校验：MG7550="+MG7550);
	Debug.println("预付卡pos卡卡表刷新权限校验：maskCode="+maskCode);
	Debug.println("预付卡pos卡卡表刷新权限校验：String.valueOf(errCode)="+String.valueOf(errCode));
	
	return hashTable;
}

/**
 * 取得时间戳
 * @return
 */
public String getDataTime(){
	Calendar lcaSysNow = Calendar.getInstance();
	int year=lcaSysNow.get(Calendar.YEAR);
	int month=lcaSysNow.get(Calendar.MONTH)+1;
	int day=lcaSysNow.get(Calendar.DAY_OF_MONTH);
	
	int hh=lcaSysNow.get(Calendar.HOUR_OF_DAY);
	int mm=lcaSysNow.get(Calendar.MINUTE);
	int ss=lcaSysNow.get(Calendar.SECOND);
	
	String retSD=intToStrFormat(year,4)+intToStrFormat(month,2)+intToStrFormat(day,2);
	retSD=retSD+intToStrFormat(hh,2)+intToStrFormat(mm,2)+intToStrFormat(ss,2);
	return retSD;
} 

/**
 * @param inNum  要格式化的数据
 * @param dig    格式化后的数据位数
 * @return string
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
 * Description: 向FTP服务器上传文件     
 * @Version1.0     
 * @param url FTP服务器hostname     
 * @param port FTP服务器端口     
 * @param username FTP登录账号     
 * @param password FTP登录密码     
 * @param path FTP服务器保存目录     
 * @param filename 上传到FTP服务器上的文件名     
 * @param input 输入流     
 * @return 成功返回true，否则返回false     
 */     
public static boolean uploadFile(    
        String url,//FTP服务器hostname     
        int port,//FTP服务器端口    
        String username, // FTP登录账号     
        String password, //FTP登录密码    
        String path, //FTP服务器保存目录    
        String filename, //上传到FTP服务器上的文件名     
        InputStream input // 输入流     
        ) {      
    boolean success = false;      
    FTPClient ftp = new FTPClient();      
    try {      
        int reply;      
        ftp.connect(url, port);//连接FTP服务器       
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器       
        ftp.login(username, password);//登录       
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return success;      
        }      
        ftp.changeWorkingDirectory(path);      
        ftp.storeFile(filename, input);               
        input.close();      
        ftp.logout();      
        success = true;      
    } catch (IOException e) {      
        e.printStackTrace();  
        System.out.println(e.getMessage());
    } finally {      
        if (ftp.isConnected()) {      
            try {      
                ftp.disconnect();      
            } catch (IOException ioe) {
            	ioe.printStackTrace();
            	System.out.println(ioe.getMessage());
            }      
        }      
    }      
    return success;      
}     

/**
 * 理财pos卡表读取上传的Excel文件
 * path 文件路径
 * return list
 * 20121220  syl
 * @throws MonitorException 
 */
public List recJXL(String path) throws MonitorException{
	Workbook book;		
	List list = new ArrayList();
	try {
		book = Workbook.getWorkbook(new File(path));
		//  获得第一个工作表对象 
		Sheet sheet  =  book.getSheet( 0 );
		//  得到第一列第一行的单元格 
		int  columnum  =  sheet.getColumns(); //  得到列数 
		int  rownum  =  sheet.getRows(); //  得到行数 
		for  ( int  i  =   1 ; i  <  rownum; i ++ ) {
			POSLcCardInfo posLcCard = new POSLcCardInfo();
			//判断第2列第i行的值(卡bin)是否为空
			if(sheet.getCell(1, i).getContents().trim() != null && sheet.getCell(0, i).getContents().trim() != ""){        		 
				posLcCard.setTrack_no(sheet.getCell(0, i).getContents());		        		
				posLcCard.setcardMatch(sheet.getCell(1, i).getContents());		        		
				posLcCard.setcardLen(sheet.getCell(2, i).getContents());		        		 
				posLcCard.setbankCode(sheet.getCell(3, i).getContents());
				posLcCard.setcardClass(sheet.getCell(4, i).getContents());
				posLcCard.setcardType(sheet.getCell(5, i).getContents());		        		
				posLcCard.setrec_useflag(sheet.getCell(6, i).getContents());	        	 
				list.add(posLcCard);
			}        	 
		} 
		book.close(); 
	} catch (BiffException e) {
		throw new MonitorException(ErrorDefine.DELETE_FAILED, "无法找到正确的文件路径,请确认文件和上传路径是否正确！");
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	return list;
}

/**
  * 创建理财pos卡表excel文件
  * list 要添加的数据 
  * path 文件路径
  * 20121129
  */
public void createJXL(List list, String path){
	try {
		//指定路径生成文件
		WritableWorkbook book  =  Workbook.createWorkbook( new  File( "D:\\ERROR.xls" ));
		//生成名为error的工作表  0标示第一页
		WritableSheet sheet  =  book.createSheet( "error" ,  0 );
		//遍历集合将数据放到单元格的指定位置上
		Label label1 = new Label(0,0,"磁道号");
		Label label2 = new Label(1,0,"卡bin");
		Label label3 = new Label(2,0,"卡号长度");
		Label label4 = new Label(3,0,"银行代码");
		Label label5 = new Label(4,0,"卡种类代码");
		Label label6 = new Label(5,0,"卡类型代码");
		Label label7 = new Label(6,0,"卡表状态标识");
		//将定义好的单元格添加到工作表中
		sheet.addCell(label1);
		sheet.addCell(label2);
		sheet.addCell(label3);
		sheet.addCell(label4);
		sheet.addCell(label5);
		sheet.addCell(label6);
		sheet.addCell(label7);
		for(int i=0;i<list.size();i++){			
			POSLcCardInfo posLcCard = (POSLcCardInfo)list.get(i);
			Label labe1   =   new  Label( 0 , i+1 , posLcCard.getTrack_no());
			Label labe2   =   new  Label( 1 , i+1 , posLcCard.getcardMatch());
			Label labe3   =   new  Label( 2 , i+1 , posLcCard.getcardLen());
			Label labe4   =   new  Label( 3 , i+1 , posLcCard.getbankCode());
			Label labe5   =   new  Label( 4 , i+1 , posLcCard.getcardClass());
			Label labe6   =   new  Label( 5 , i+1 , posLcCard.getcardType());
			Label labe7   =   new  Label( 6 , i+1 , posLcCard.getrec_useflag());
			System.out.println("Labe2 = "+labe2);
			sheet.addCell(labe1);
			sheet.addCell(labe2);
			sheet.addCell(labe3);
			sheet.addCell(labe4);
			sheet.addCell(labe5);
			sheet.addCell(labe6);
			sheet.addCell(labe7);
		}
		//写入数据关闭文件
		book.write();
		book.close();
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	} catch (RowsExceededException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	} catch (WriteException e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
}
/**
 * 手输卡标志修改函数,
 * @param CardStruct CardTypeStruct
 * @return 成功1,失败：-1||0
 * @throws MonitorException
 */
public int setCardManuFlag( POSBCCardInfo cardInfo) throws MonitorException{
	if (cardInfo == null ) {
		return 0;
	}
	int affect = 0;
	SqlAccess conn = null;
	try {
		conn = new SqlAccess();
		String sqlStr = "UPDATE pos_yfk_card SET hand_flag=? WHERE card_id=? AND track_no=? AND card_match=?";
		PreparedStatement psmt = conn.conn.prepareStatement(sqlStr);
		psmt.setString(1,cardInfo.getHand_flag());
		psmt.setString(2,cardInfo.getCard_id());
		psmt.setString(3,cardInfo.getTrack_no());
		psmt.setString(4,cardInfo.getCard_match());
		affect = psmt.executeUpdate();	
	}
	catch (SQLException ex) {
		throw new MonitorException(ex.getErrorCode(),ex.getMessage());
	}
	finally {
		if (conn != null) {
			conn.close();
		}
	}
	return affect;
}
}
