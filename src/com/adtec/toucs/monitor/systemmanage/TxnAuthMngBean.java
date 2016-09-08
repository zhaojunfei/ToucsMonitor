package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

public class TxnAuthMngBean {

	//�豸�����б�
	public static String typeList[] = {"atm","cdm","pos","mit","pem"};
	//POS����Ȩ������󳤶�
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
	   * ��ѯָ������Ĵ���
	   * @param type ��������
	   * @return �����б�
	   */
	public static TxnAuthBean queryAuth(String sys_id, String card_class, String card_type) throws MonitorException{
		//ȡ���ݿ�����
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
		//ȡ���ݿ�����
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
	   * ��ѯָ��������ϸ��Ϣ
	   * @param type ��������
	   * @return �����б�
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
	   * ��Ӵ���
	   * @param txnCode ���״�����Ϣ
	   * @return ��ӳɹ�����1
	   * @throws MonitorException
	   */
	public int addAuth(TxnAuthBean txnAuth) throws MonitorException{
		//ȡ���ݿ�����
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
		//ȡ���ݿ�����
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
   * ��ѯPOS��Ĭ��Ȩ�������ã�ֻ��POS��Ч����Ĭ������Ȩ����
   * @param isPreAuthorized 0:��Ԥ��Ȩ 1:��Ԥ��Ȩ
   * @param perm  0�������� 1������������ҽ��� 2����������ҽ��� 3�������������Ҳ������ҽ���
   * @return ��Vector ���� 100λ�Ľ���Ȩ�����100λ������Ȩ����
   * @throws MonitorException ���ϵͳ�쳣
   */
	public static Vector queryPosTxnMask(String isPreAuthorized,char perm) throws SQLException,MonitorException{
		char[] code=new char[POS_MASK_SIZE];//����Ȩ����
		char[] handMask = new char[POS_MASK_SIZE];//���俨��Ȩ����
		Vector v = new Vector();
		//׼��100��Ϊ0��Ȩ����
  
		for( int i=0 ; i<POS_MASK_SIZE ; i++){
			code[i] = '0';
			handMask[i] = '0';
		}
		Debug.println(String.valueOf(code));
		Debug.println(String.valueOf(handMask));
        //û��Ȩ�޷���
		if( perm=='0' ) {
			v.add( String.valueOf(code));
			v.add( String.valueOf(handMask));
			return v;
		}
		SqlAccess sq = SqlAccess.createConn();
		try{
			String sql = "SELECT txn_code,location,hand_flag FROM kernel_txn_code  WHERE sys_id='pos' ";
			//����Ȩ�ཻ��ʱ������Ԥ��Ȩ�ཻ��
			if( isPreAuthorized.equals("0") )
				sql += " AND app_flag2 = '0 ' ";
			ResultSet rs = sq.queryselect(sql);
			String txnCode = "";
			String handFlag = "";
			while( rs!=null && rs.next() ){
				int location = rs.getInt("location");
                txnCode = rs.getString("txn_code").trim();
                handFlag = rs.getString("hand_flag").trim();
                //Ĭ�ϲ�����С�ѵ�������
                if( !txnCode.equals("ST7830"))
                	code[location-1] = perm;

                /* Added By Rox For ת��POS С�ѵ���֮ǰ�Ľ���Ĭ�ϲ����� on 20040227 */
                if (perm == '4' && location <= 12)
                	code[location-1] = '0';
                //�����Ŷ�Ҫ�� ��ҿ�����������׷��Ԥ��Ȩ������׷��Ԥ��Ȩ����������Ԥ��Ȩ������
                if (txnCode.equals("MC0611") || txnCode.equals("MC0621") || txnCode.equals("MC0510"))
                	if (perm == '2')
                		code[location-1] = '0';
                	else if (perm == '3')
                		code[location-1] = '1';
                /* Add End By Rox */

                //��Ԥ��Ȩ�����Ĭ������MC1010��Ȩ���ѣ��������ѣ���MC1020 ��Ȩ���ѳ���
                if( isPreAuthorized.equals("0")&&(txnCode.equals("MC1010") || txnCode.equals("MC1020" ))){
                	if ( perm == '2' )
                        code[location-1] = '0';//���POSû��Ȩ��
                	if ( perm == '3' )
                        code[location-1] = '1';//�����POS���������
                }
                //���Ĭ�ϲ���������������MC3311
                if(  txnCode.equals("MC3311")) {
                	if ( perm == '2' )
                        code[location-1] = '0';//���POSû��Ȩ��
                	if ( perm == '3' )
                        code[location-1] = '1';//�����POS���������
                	/* Added By Rox For ת��POS Ĭ�Ϻ�������ѯ�������� On 20040227 */
                	if (perm == '4')
                		handMask[location-1] = '1';
                	/* Added End By Rox */
                }
                //ֻ������Ҹ��ݽ���Ȩ���������俨Ȩ��
                if( handFlag.equals("1") && (perm =='1'||perm=='3') ) {
                    handMask[location-1] = '1' ;
                }
                /* Added By Rox For ת��POS On 20040227 */
                //Ĭ�Ϻ�������ѯ�������� MC3311
                if (perm == '4' && txnCode.equals("MC3311"))
                	handMask[location - 1] = '1';
                /* Added End By Rox */
                //���POS����������ѯ����
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
	    * ��ѯ���������б�ֻ��POS��Ч��
	    * @return ���ؽ�����������
	    * @throws MonitorException ���ϵͳ�쳣
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
