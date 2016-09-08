package com.adtec.toucs.monitor.systemmanage;

import java.util.*;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

	/**
	 * <p>Title: 平台交易代码管理类</p>
	 * <p>Description:封装平台交易码管理相关的业务逻辑。 </p>
	 * <p>Copyright: Copyright (c) 2002</p>
	 * <p>Company: ADTec </p>
	 * @author liyp
	 * @version 1.0
	 */
public class TxnCodeManageBean {
	//设备类型列表
	public static String typeList[] = {
      "atm", "cdm", "pos", "mit", "pem"};

	//POS交易权限码最大长度
	private static int POS_MASK_SIZE = 100;

	  /**
	   * 构造方法
	   */
	public TxnCodeManageBean() {
	}

	  /**
	   * 查询指定种类代码
	   * @param sysId 代码种类
	   * @param sq 数据库访问对象
	   * @return 代码列表
	   * @throws SQLException
	   */
	public static Vector queryCodes(String sysId, SqlAccess sq) throws SQLException {
		ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_code WHERE sys_id='" + sysId + "' ORDER BY txn_code");
		Vector v = new Vector();
		while (rst != null && rst.next()) {
			TxnCodeBean txnCode = new TxnCodeBean();
			txnCode.fetchData(rst);
			Debug.println(txnCode.toString());
			v.add(txnCode);
		}
		rst.close();
		return v;
	}

	  /**
	   * 查询指定种类的代码
	   * @param type 代码种类
	   * @return 代码列表
	   */
	public static Vector queryCodes(String sysId) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			Vector v = queryCodes(sysId, sq);
			return v;
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 查询指定交易详细信息
	   * @param type 代码种类
	   * @return 代码列表
	   */
	public static TxnCodeBean queryCode(String deviceType, String txnCode) throws MonitorException {
		TxnCodeBean code = null;
		SqlAccess sq = SqlAccess.createConn();
		String sql = "SELECT * FROM kernel_txn_code WHERE sys_id='" + deviceType + "' AND txn_code='" + txnCode + "'";
		try {
			ResultSet rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				code = new TxnCodeBean();
				code.fetchData(rst);
			}
			rst.close();
			return code;
		} catch (SQLException exp) {
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 添加代码
	   * @param txnCode 交易代码信息
	   * @return 添加成功返回1
	   * @throws MonitorException
	   */
	public int addCode(TxnCodeBean txnCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = txnCode.insert(sq);
			sq.commit();
			//更新代码表
			CodeManageBean.codeTableAdd(txnCode.getDeviceType(), txnCode.getTxnCode(),txnCode.getTxnName());
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 修改代码
	   * @param code 代码信息
	   * @param id 代码编号（key）
	   * @return 修改成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int modifyCode(TxnCodeBean txnCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			sq.setAutoCommit(false);
			int i = txnCode.update(sq);
			sq.commit();
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 删除代码
	   * @param type 代码类型
	   * @param id 代码编号
	   * @return 删除成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int deleteCode(String sysId, String txnCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql = "DELETE FROM kernel_txn_code WHERE sys_id=? AND txn_code=?";
			PreparedStatement stm = sq.conn.prepareStatement(sql);
			stm.setString(1, sysId);
			stm.setString(2, txnCode);
			int i = stm.executeUpdate();
			//更新代码表
			if (i > 0) {
				CodeManageBean.codeTableDel(sysId, txnCode);
			}
			return i;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 查询POS的默认权限码设置（只对POS有效）和默认手输权限码
	   * @param isPreAuthorized 0:无预授权 1:有预授权
	   * @param perm  0－不允许 1－允许做人民币交易 2－允许做外币交易
	   *              3－既允许人民币也允许外币交易 4-财务pos
	   * @return 用Vector 返回 100位的交易权限码和100位的手输权限码
	   * @throws MonitorException 监控系统异常
	   */
	public static Vector queryPosTxnMask(String isPreAuthorized, char perm, String pos_type) throws SQLException, MonitorException {
		char[] code = new char[POS_MASK_SIZE]; //交易权限码
		char[] handMask = new char[POS_MASK_SIZE]; //手输卡号权限码
		Vector v = new Vector();
		//准备100个为0的权限码
		Debug.println(String.valueOf(code) + "[" + isPreAuthorized + "]["+perm+"]["+pos_type+"]");
		for (int i = 0; i < POS_MASK_SIZE; i++) {
			code[i] = '0';
			handMask[i] = '0';
		}
		Debug.println(String.valueOf(code) + "[" + isPreAuthorized + "]");
		Debug.println(String.valueOf(handMask) + "[" + perm + "]");
		//没有权限返回
		if (perm == '0') {
			v.add(String.valueOf(code));
			v.add(String.valueOf(handMask));
			return v;
		}
		SqlAccess sq = SqlAccess.createConn();
		try {
			String sql ="SELECT txn_code, location, hand_flag FROM kernel_txn_code WHERE sys_id='pos' ";
			//非授权类交易时，过滤预授权类交易
			if (isPreAuthorized.equals("0")) {
				sql += " AND app_flag2 = '0 ' ";
			}
			ResultSet rs = sq.queryselect(sql);
			String txnCode = "";
			String handFlag = "";
			while (rs != null && rs.next()) {
				int location = rs.getInt("location");
				txnCode = rs.getString("txn_code").trim();
				handFlag = rs.getString("hand_flag").trim();  
				if(pos_type.equals("7")){ 
					/* 只有理财POS才能做理财交易 */
					if ( txnCode.substring(0,2).equals("LC")||txnCode.equals("ST6330")||txnCode.equals("ST7410")||txnCode.equals("ST7820") ) {
						code[location - 1] = '1';
					}
				}else if(pos_type.equals("8")){
					/* 只有预付卡POS才能做预付卡交易 */
					if ( txnCode.substring(0,2).equals("BC")||txnCode.equals("ST6330")||txnCode.equals("ST7410")||txnCode.equals("ST7820") ) {
						code[location - 1] = '1';
					}
				}else{
					//默认不允许小费调整交易
					if (!txnCode.equals("ST7830")) {
						code[location - 1] = perm;
					}		
					//外币卡不允许做“追加预授权”、“追加预授权冲正”、“预授权撤销”
					if (txnCode.equals("MC0611") || txnCode.equals("MC0621") ||
							txnCode.equals("MC0510")) {
						if (perm == '2') {
							code[location - 1] = '0';
						} else if (perm == '3') {
							code[location - 1] = '1';
						}
					}
					//无预授权人民币默认允许MC1010授权消费（离线消费）、MC1020 授权消费冲正
					if (isPreAuthorized.equals("0") && (txnCode.equals("MC1010") || txnCode.equals("MC1020"))) {
						if (perm == '2') {
							code[location - 1] = '0'; //外币POS没有权限
						}
						if (perm == '3') {
							code[location - 1] = '1'; //本外币POS允许人民币
						}
					}
					//外币默认不允许查黑名单交易MC3311
					if (txnCode.equals("MC3311")) {
						if (perm == '2') {
							code[location - 1] = '0'; //外币POS没有权限
						}
						if (perm == '3') {
							code[location - 1] = '1'; //本外币POS允许人民币
						}
						/* 转帐POS 默认黑名单查询允许手输 */
						if (pos_type.equals("1")) {
							handMask[location - 1] = '1';
						}
					}
					//只有人民币根据交易权限设置手输卡权限
					if (handFlag.equals("1") && (perm == '1' || perm == '3')) {
						handMask[location - 1] = '1';
					}
					//外币POS不允许做查询余额交易
					if (txnCode.equals("MC3310") && (perm == '2' || perm == '3')) {
						if (perm == '2') {
							code[location - 1] = '0';
						}
						if (perm == '3') {
							code[location - 1] = '1';
						}
					}		
					/* 只有消费POS才能做消费交易 */
					if ( txnCode.equals("MC0010") ||
							txnCode.equals("MC0020") ||
							txnCode.equals("MC2010") ||
							txnCode.equals("MC2020") ||
							txnCode.equals("MC0610") ||
							txnCode.equals("MC0620") ||
							txnCode.equals("MC0611") ||
							txnCode.equals("MC0621") ||
							txnCode.equals("MC0510") ||
							txnCode.equals("MC1010") ||
							txnCode.equals("MC1020") ||
							txnCode.equals("ST7830") ||
							txnCode.equals("MC5010") ||
							txnCode.equals("MC5020") ||
							txnCode.equals("BC0010") ||
							txnCode.equals("BC0020") ||
							txnCode.equals("BC2010") ||
							txnCode.equals("BC2020") ||
							txnCode.equals("BC2110") ||
							txnCode.equals("BC2120") ||
							txnCode.equals("BC3310") ||
							txnCode.equals("MC5310") ) {
						if (pos_type.equals("1")||pos_type.equals("2")) {
							code[location - 1] = '0';
						}
					}		
					/* 只有转帐POS才能做转帐交易 */
					if ( txnCode.equals("MC0040") || txnCode.equals("MC0041") || txnCode.equals("MC0042") )  {
						if (pos_type.equals("1")) {
							code[location - 1] = '1';
						}else {
							code[location - 1] = '0';
						}
					}	
					/* 只有签约POS才能做签约交易 */
					if ( txnCode.substring(0,2).equals("EN") ) {
						if (pos_type.equals("2")) {
							code[location - 1] = '1';
						}else {
							code[location - 1] = '0';
						}
					}   
					/* 只有理财POS才能做理财交易 */
					if ( txnCode.substring(0,2).equals("LC") ) {
						code[location - 1] = '0';
					}else if(txnCode.substring(0,2).equals("BC")){
						/* 只有预付卡POS才能做预付卡交易 */
						code[location - 1] = '0';
					}
				}
			}
			rs.close();
			v.add(String.valueOf(code));
			v.add(String.valueOf(handMask));
			return v;
		} catch (SQLException e1) {
			throw e1;
		} finally {
			sq.close();
		}
	}

	  /**
	   * 查询交易名称列表（只对POS有效）
	   * @return 返回交易名称数组
	   * @throws MonitorException 监控系统异常
	   */
	public static Vector queryPosTxnCodes() throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		Vector v = new Vector();
		try {
			ResultSet rst = sq.queryselect("SELECT * FROM kernel_txn_code WHERE sys_id='pos' ORDER BY location");
			while (rst != null && rst.next()) {
				TxnCodeBean txnCode = new TxnCodeBean();
				txnCode.fetchData(rst);
				v.add(txnCode);
			}
			rst.close();
			return v;
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
	}


	
	public static void main(String[] args) {
	}
}
