package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

public class TxnAuthMngBean {

	//设备类型列表
	public static String typeList[] = {"atm","cdm","pos","mit","pem"};
	//POS交易权限码最大长度
	private static int POS_MASK_SIZE = 100;

	public TxnAuthMngBean() {
	}

	public static Vector queryAuth(String sys_id, String card_class, String card_type, SqlAccess sq) throws SQLException{
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_auth WHERE sys_id='" + sys_id + "' and card_class = '" + card_class + "' and card_type = '" + card_type + "'");
		Vector v = new Vector();
		while (rst != null && rst.next()){
			TxnAuthBean txnAuth = new TxnAuthBean();
			txnAuth.fetchData(rst);
			Debug.println(txnAuth.toString());
			v.add(txnAuth);
		}
		rst.close();
		return v;
	}

	public static Vector queryAuths(String sys_id, SqlAccess sq) throws SQLException{
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_auth WHERE sys_id='" + sys_id + "' ORDER BY card_type, card_class");
		Vector v = new Vector();
		while (rst != null && rst.next()){
			TxnAuthBean txnAuth = new TxnAuthBean();
			txnAuth.fetchData(rst);
			Debug.println(txnAuth.toString());
			v.add(txnAuth);
		}
		rst.close();
		return v;
	}

	  /**
	   * 查询指定种类的代码
	   * @param type 代码种类
	   * @return 代码列表
	   */
	public static TxnAuthBean queryAuth(String sys_id, String card_class, String card_type) throws MonitorException{
		//取数据库连接
		TxnAuthBean auth = null;
		SqlAccess sq = SqlAccess.createConn();

		String sql="SELECT * FROM kernel_txn_auth WHERE sys_id='" + sys_id + "' AND card_class='" + card_class + "'AND card_type='" + card_type+ "'";
		Debug.println(sql);
		try{
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()){
				auth = new TxnAuthBean();
				auth.fetchData(rst);
			}
			rst.close();
			return auth;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}

	public static Vector queryAuths(String sys_id) throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try{
			Vector v = queryAuths(sys_id, sq);
			return v;
		}catch(SQLException exp){
			throw new MonitorException(exp);
		}finally{
			sq.close();
		}
	}

	  /**
	   * 查询指定交易详细信息
	   * @param type 代码种类
	   * @return 代码列表
	   */
	public static TxnCodeBean queryCode(String deviceType,String txnCode) throws MonitorException{
          TxnCodeBean code=null;
          SqlAccess sq = SqlAccess.createConn();
          String sql="SELECT * FROM kernel_txn_code WHERE sys_id='"+deviceType+"' AND txn_code='"+txnCode+"'";
          try{
        	  ResultSet rst=sq.queryselect(sql);
        	  while( rst!=null &&  rst.next() ){
                    code = new TxnCodeBean();
                    code.fetchData(rst);
        	  }
        	  rst.close();
        	  return code;
          }catch(SQLException exp){
        	  throw new MonitorException(exp);
          }finally{
        	  sq.close();
          }
	}

	  /**
	   * 添加代码
	   * @param txnCode 交易代码信息
	   * @return 添加成功返回1
	   * @throws MonitorException
	   */
	public int addAuth(TxnAuthBean txnAuth) throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try{
			sq.setAutoCommit(false);
			int i = txnAuth.insert(sq);
			sq.commit();
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

	public int modifyAuth(TxnAuthBean txnAuth) throws MonitorException{
		SqlAccess sq = SqlAccess.createConn();
		try{
			sq.setAutoCommit(false);
			int i = txnAuth.update(sq);
			sq.commit();
			return i;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}
	}

	public int deleteAuth(String sysId, String card_class, String card_type) throws MonitorException{
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try{
			String sql = "DELETE FROM kernel_txn_auth WHERE sys_id='" + sysId + "' AND card_class = '"+card_class+"' and card_type = '"+card_type+"'";
			Debug.println(sql);
			sq.setAutoCommit(false);
			int ret = sq.queryupdate(sql);
			sq.commit();
			return ret;
		}catch(SQLException e1){
			throw new MonitorException(e1);
		}finally{
			sq.close();
		}	
	}

   /**
   * 查询POS的默认权限码设置（只对POS有效）和默认手输权限码
   * @param isPreAuthorized 0:无预授权 1:有预授权
   * @param perm  0－不允许 1－允许做人民币交易 2－允许做外币交易 3－既允许人民币也允许外币交易
   * @return 用Vector 返回 100位的交易权限码和100位的手输权限码
   * @throws MonitorException 监控系统异常
   */
	public static Vector queryPosTxnMask(String isPreAuthorized,char perm) throws SQLException,MonitorException{
		char[] code=new char[POS_MASK_SIZE];//交易权限码
		char[] handMask = new char[POS_MASK_SIZE];//手输卡号权限码
		Vector v = new Vector();
		//准备100个为0的权限码
  
		for( int i=0 ; i<POS_MASK_SIZE ; i++){
			code[i] = '0';
			handMask[i] = '0';
		}
		Debug.println(String.valueOf(code));
		Debug.println(String.valueOf(handMask));
        //没有权限返回
		if( perm=='0' ) {
			v.add( String.valueOf(code));
			v.add( String.valueOf(handMask));
			return v;
		}
		SqlAccess sq = SqlAccess.createConn();
		try{
			String sql = "SELECT txn_code,location,hand_flag FROM kernel_txn_code  WHERE sys_id='pos' ";
			//非授权类交易时，过滤预授权类交易
			if( isPreAuthorized.equals("0") )
				sql += " AND app_flag2 = '0 ' ";
			ResultSet rs = sq.queryselect(sql);
			String txnCode = "";
			String handFlag = "";
			while( rs!=null && rs.next() ){
				int location = rs.getInt("location");
                txnCode = rs.getString("txn_code").trim();
                handFlag = rs.getString("hand_flag").trim();
                //默认不允许小费调整交易
                if( !txnCode.equals("ST7830"))
                	code[location-1] = perm;

                /* Added By Rox For 转帐POS 小费调整之前的交易默认不允许 on 20040227 */
                if (perm == '4' && location <= 12)
                	code[location-1] = '0';
                //根据张东要求 外币卡不允许做“追加预授权”、“追加预授权冲正”、“预授权撤销”
                if (txnCode.equals("MC0611") || txnCode.equals("MC0621") || txnCode.equals("MC0510"))
                	if (perm == '2')
                		code[location-1] = '0';
                	else if (perm == '3')
                		code[location-1] = '1';
                /* Add End By Rox */

                //无预授权人民币默认允许MC1010授权消费（离线消费）、MC1020 授权消费冲正
                if( isPreAuthorized.equals("0")&&(txnCode.equals("MC1010") || txnCode.equals("MC1020" ))){
                	if ( perm == '2' )
                        code[location-1] = '0';//外币POS没有权限
                	if ( perm == '3' )
                        code[location-1] = '1';//本外币POS允许人民币
                }
                //外币默认不允许查黑名单交易MC3311
                if(  txnCode.equals("MC3311")) {
                	if ( perm == '2' )
                        code[location-1] = '0';//外币POS没有权限
                	if ( perm == '3' )
                        code[location-1] = '1';//本外币POS允许人民币
                	/* Added By Rox For 转帐POS 默认黑名单查询允许手输 On 20040227 */
                	if (perm == '4')
                		handMask[location-1] = '1';
                	/* Added End By Rox */
                }
                //只有人民币根据交易权限设置手输卡权限
                if( handFlag.equals("1") && (perm =='1'||perm=='3') ) {
                    handMask[location-1] = '1' ;
                }
                /* Added By Rox For 转帐POS On 20040227 */
                //默认黑名单查询允许手输 MC3311
                if (perm == '4' && txnCode.equals("MC3311"))
                	handMask[location - 1] = '1';
                /* Added End By Rox */
                //外币POS不允许做查询余额交易
                if( txnCode.equals("MC3310") && (perm =='2'||perm=='3') ){
                	if( perm == '2' ) {
                		code[location-1] = '0' ;
                    }
                    if( perm == '3' ) {
                        code[location-1] = '1' ;
                    }
                }
			}
			rs.close();
			v.add( String.valueOf(code));
			v.add( String.valueOf(handMask));
			return v;
		}catch(SQLException e1){
			throw e1;
		}finally{
			sq.close();
		}
	}

	   /**
	    * 查询交易名称列表（只对POS有效）
	    * @return 返回交易名称数组
	    * @throws MonitorException 监控系统异常
	    */
	public static Vector queryPosTxnCodes() throws MonitorException{
		SqlAccess sq = SqlAccess.createConn();
		Vector v = new Vector();
		try{
			ResultSet rst=sq.queryselect("SELECT * FROM kernel_txn_code WHERE sys_id='pos' ORDER BY location");
			while( rst!=null && rst.next()){
				TxnCodeBean txnCode = new TxnCodeBean();
				txnCode.fetchData(rst);
				v.add(txnCode);
			}
			rst.close();
			return v;
		}catch( SQLException e){
			throw new MonitorException(e);
		}finally{
			sq.close();
		}
	}
}
