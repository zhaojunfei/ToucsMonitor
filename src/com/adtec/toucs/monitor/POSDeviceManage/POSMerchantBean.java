package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class POSMerchantBean {
	private ResultSet rs = null;
	private SqlAccess conn1 = null;

	public POSMerchantBean() {
	}

	public int ModifyCheckPZRule(CheckPrizeRuleInfo info) throws MonitorException {
		Debug.println("CREATECONN:BEGIN");
		SqlAccess sq = SqlAccess.createConn();
		Debug.println("CREATECONN:END");
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeModifyPZinfoStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.ADDMCT_FAILED, "添加抽奖规则失败");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 为了进度而把抽奖的这段代码放在这，这段代码和这个Bean没什么牵连关系
	   * @param info POSMerchantInfo
	   * @throws MonitorException
	   * @return int
	   */
	public int addCheckPZRule(CheckPrizeRuleInfo info) throws MonitorException {
		//取数据库连接
		Debug.println("CREATECONN:BEGIN");
		SqlAccess sq = SqlAccess.createConn();
		Debug.println("CREATECONN:END");
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag); 
			if (flag == 1) {
				sq.commit();//提交 	
			} else {
				sq.rollback(); //事务回滚
				throw new MonitorException(ErrorDefine.ADDMCT_FAILED, "添加抽奖规则失败");
			}
			return flag;
		}catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 登记POS新商户
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int addPosMerchant(POSMerchantInfo info) throws MonitorException {
		//取数据库连接
		Debug.println("CREATECONN:BEGIN");
		SqlAccess sq = SqlAccess.createConn();
		Debug.println("CREATECONN:END");
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);  
			if (flag == 1) {
				sq.commit();//提交 
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.ADDMCT_FAILED, "添加POS商户信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	public Vector getChannelId() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.sys_code, A.code_desc FROM kernel_code A WHERE code_type='0567'");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		}catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

	public Vector getCheckPrizeQuery(String Querydate) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT pos_dcc_code, merchant_id, trans_card_no, trans_amount,");
		SqlStr.append("trans_date, trans_time, trans_card_type,demo2 ");
		SqlStr.append("FROM pos_txn_log WHERE demo2 != '' AND trans_code = 'MC0010' AND trans_date MATCHES '");
		SqlStr.append(Querydate + "'");
		
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		String yesterday_table = "";
		String sql = "";
		try {
			sql = "SELECT yesterday_log FROM kernel_sys_para WHERE sys_id = 'pos'";
			rs = sq.queryselect(sql);
			while (rs != null && rs.next()) {
				yesterday_table = rs.getString(1);
			}
			sql = "SELECT pos_dcc_code, merchant_id, trans_card_no, trans_amount, " +
				  "trans_date, trans_time, trans_card_type,demo2 " +
				  " FROM " + yesterday_table + " WHERE demo2 != '' AND trans_code = 'MC0010' AND trans_date MATCHES '" + Querydate + "'";
			rs = sq.queryselect(sql);
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

	public Vector getCardClass() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.sys_code, A.code_desc FROM kernel_code A WHERE code_type='0280'");
		
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

	public Vector getIsAaddedATM() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.atm_id FROM monit_atmposition A WHERE A.fix_flg ='1' ORDER BY A.atm_id");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
		
	}

	public Vector getUnAddedATM() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.atm_id FROM monit_atmposition A WHERE A.fix_flg ='0' ORDER BY A.atm_id");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
		
	}
	
	public Vector getAllMerchant() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT merchant_id, mct_name FROM pos_merchant ORDER BY merchant_id");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println(sqle.getMessage());
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

	public String qeruyMerchantNameByID(String MerchantId) throws MonitorException {
		String SqlStr, MerchantName = "";
		SqlAccess conn = SqlAccess.createConn();	
		
		SqlStr = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + MerchantId + "'";
		try {
			rs = conn.queryselect(SqlStr);
			if (rs != null && rs.next()) {
				MerchantName = rs.getString(1);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn.close();
		}
		return MerchantName;
	}

	public Vector queryMerchantName(String MerchantId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT A.mct_name FROM pos_merchant A WHERE merchant_id MATCHES '");
		SqlStr.append(MerchantId + "'");
		
		Vector RsVect = new Vector();
		Hashtable orgHT = null;
		try {
			conn1 = SqlAccess.createConn();;
			rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			if  ( MerchantId.equals("000000000000000")  ) {
				orgHT = new Hashtable();
				orgHT.put("mct_name","000000000000000");
				RsVect.add(orgHT);
			} else {
				while (rs != null && rs.next()) {
					orgHT = new Hashtable();
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						orgHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1) == null ? " " : rs.getString(i + 1)));
					}
					RsVect.add(orgHT);
				}
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn1.close();
		}
		return RsVect;
	}

	public String queryMctName(String MerchantId) throws MonitorException {
		String SqlStr = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + MerchantId + "'";
		try {
			conn1 = SqlAccess.createConn();
			rs = conn1.queryselect(SqlStr);
			while (rs != null && rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn1.close();
		}
		return "";
	}

	public Vector queryMerchantDetail(String MerchantId, String ChannId) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT businessman_code,channel_code,prize_num1,prize_summary1,prize_num2,prize_summary2,prize_num3,prize_summary3,prize_num4,prize_summary4,prize_num5,prize_summary5 FROM prize_info WHERE businessman_code MATCHES '");
		SqlStr.append(MerchantId + "' AND channel_code MATCHES '");
		SqlStr.append(ChannId + "'");
		
		Vector RsVect = new Vector();
		Hashtable orgHT = null;
		try {
			conn1 = SqlAccess.createConn();
			rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			while (rs != null && rs.next()) {
				orgHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					orgHT.put(rs.getMetaData().getColumnName(i + 1), toucsString.unConvert(rs.getString(i + 1) == null ? " " : rs.getString(i + 1)));
				}
				RsVect.add(orgHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn1.close();
		}
		return RsVect;
	}

	public Vector queryPrizeInfo(String ChannelID) throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		SqlStr.append("SELECT businessman_code,channel_code,prize_num1,");
		SqlStr.append("prize_summary1,prize_num2,prize_summary2,prize_num3,");
		SqlStr.append("prize_summary3, prize_num4,prize_summary4,prize_num5,");
		SqlStr.append("prize_summary5 FROM prize_info WHERE channel_code MATCHES '");
		SqlStr.append(ChannelID + "' ");
		SqlStr.append("ORDER BY prize_num1 desc, prize_num2 desc, prize_num3 desc,");
		SqlStr.append(" prize_num4 desc, prize_num5 desc");
		Vector RsVect = new Vector();
		Hashtable orgHT = null;
		try {
			conn1 = SqlAccess.createConn();
			rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			while (rs != null && rs.next()) {
				orgHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					orgHT.put(rs.getMetaData().getColumnName(i + 1), toucsString.unConvert(rs.getString(i + 1) == null ? " " : rs.getString(i + 1)));
				}
				RsVect.add(orgHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn1.close();
		}
		return RsVect;
	}

	public PrizeRule queryPrizeRule(String ChannelID) throws MonitorException {
		String SqlStr;
		PrizeRule pz = null;
		SqlStr = "SELECT * FROM prize_rule WHERE channel_code = '" + ChannelID + "'";
		try {
			conn1 =  SqlAccess.createConn();
			rs = conn1.queryselect(SqlStr);
			while (rs != null && rs.next()) {
				pz = new PrizeRule();
				pz.setChannel_code(rs.getString("channel_code"));
				pz.setBit_cardtype(rs.getString("bit_cardtype"));
				pz.setBit_cardclass(rs.getString("bit_cardclass"));
				pz.setBegin_date(rs.getString("begin_date"));
				pz.setEnd_date(rs.getString("end_date"));
				pz.setBegin_time(rs.getString("begin_time"));
				pz.setEnd_time(rs.getString("end_time"));
				pz.setBusinessman_flag(rs.getString("businessman_flag"));
				pz.setPrizelevel_flag(rs.getString("prizelevel_flag"));
				pz.setLowest_amt(rs.getFloat("lowest_amt"));
				pz.setBusinessman_num(rs.getInt("businessman_num"));
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			conn1.close();
		}
		return pz;
	}
	
	public Vector getAvailMerchant() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.merchant_id , A.mct_name FROM pos_merchant A ,prize_info B WHERE A.merchant_id = B.businessman_code");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

	public Vector getCardType() throws MonitorException {
		StringBuffer SqlStr = new StringBuffer();
		ResultSet rs = null;
		SqlStr.append("SELECT A.sys_code, A.code_desc FROM kernel_code A WHERE code_type='0290'");
		Vector RoleVect = new Vector();
		Hashtable RoleHT = null;
		SqlAccess sq = SqlAccess.createConn();
		try {
			rs = sq.queryselect(SqlStr.toString());
			while (rs != null && rs.next()) {
				RoleHT = new Hashtable();
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					RoleHT.put(rs.getMetaData().getColumnName(i + 1),toucsString.unConvert(rs.getString(i + 1)));
				}
				RoleVect.add(RoleHT);
			}
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		} finally {
			sq.close();
		}
		return RoleVect;
	}

  /**
   * 修改POS商户
   * @param info 登记的设备信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int updatePosMerchant(POSMerchantInfo info, String key) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			//此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATEMCT_FAILED,"修改POS商户信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 删除POS新商户
   * @param info 登记的设备信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int deletePosMerchant(String merchantID) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSMerchantInfo mctInfo = new POSMerchantInfo();
		//pos实例化
		POSInfo posInfo = new POSInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			//此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false); //opened by liyp 20030616
			Debug.println("SETAUTOCOMMIT:END");
			//查询该商户下Pos设备信息，如果有pos设备，不能删除
			PreparedStatement stm = posInfo.makeQueryByMctStm(sq.conn, merchantID);
			ResultSet rSet = stm.executeQuery();
			if (rSet.next()) {
				throw new MonitorException(ErrorDefine.DELMCT_FAILED,"商户下定义有POS信息，请先删除POS信息！");
			}
			//删除商户信息基本信息
			stm = mctInfo.makeDeleteStm(sq.conn, merchantID);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELMCT_FAILED1,"商户下清理标志为正常，请先清理商户！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 设置商户清理标志
   * @param info 登记的设备信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int clearPosMerchant(String merchantID) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSMerchantInfo mctInfo = new POSMerchantInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			//此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false); //open by liyp 20030616
			Debug.println("SETAUTOCOMMIT:END");
			//设置商户清理标志基本信息
			PreparedStatement stm = mctInfo.makeClearStm(sq.conn, merchantID);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交 	
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATEMCT_FAILED,"修改POS商户信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 设置商户正常标志
   * @param info 登记的设备信息
   * @return 登记成功返回1
   * @throws MonitorException 监控系统异常
   */
	public int normPosMerchant(String merchantID) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSMerchantInfo mctInfo = new POSMerchantInfo();
		//pos实例化
		//POSInfo posInfo = new POSInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			//此处不知道为什么不能执行sq.setAutoCommit(false);，所以注释掉
			sq.setAutoCommit(false); //open by liyp 20030616
			Debug.println("SETAUTOCOMMIT:END");
			//设置商户清理标志基本信息
			PreparedStatement stm = mctInfo.makeNormStm(sq.conn, merchantID);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback(); //事务回滚
				throw new MonitorException(ErrorDefine.UPDATEMCT_FAILED,"修改POS商户信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 查询商户基本配置信息
   * @param atmCode 设备编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public POSMerchantInfo qryPosMerchant(String merchantID) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql = "SELECT * FROM pos_merchant WHERE merchant_id='" + merchantID + "'";
			Debug.println("***********SQL:" + sql + "*********");
			POSMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst.next()) {
				info = new POSMerchantInfo();
				info.fetchData(rst);
			}
			rst.close();
			return info;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 查询商户基本配置信息
   * @param atmCode 设备编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public Vector qryOrgPosMerchant(String orgid) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql = "SELECT * FROM pos_merchant WHERE org_id='" + orgid + "'";
			Debug.println("SQL:" + sql);
			POSMerchantInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst.next()) {
				info = new POSMerchantInfo();
				info.fetchData(rst);
				v.add(info);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 查询该商户下的pos机数
   * @param atmCode 设备编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public int qryPosCount(String merchantID) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql = "SELECT * FROM pos_merchant WHERE merchant_id='" + merchantID + "'";
			Debug.println("SQL:" + sql);
			POSMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			int nReturn = 0;
			if (rst.next()) {
				info = new POSMerchantInfo();
				info.fetchData(rst);
			}
			/**Fancy Add "info == null" ***/
			if (info == null || (info.getPosmachine_no() == null) || info.getPosmachine_no().trim().equals("")) {
				nReturn = 0;
			} else {
				nReturn = Integer.parseInt(info.getPosmachine_no());
			}
			rst.close();
			return nReturn;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 查询所有商户编号和商户代码信息
   * @param atmCode 设备编号
   * @param orgId 当前用户所属机构
   * @return 商户信息，如果商户不存在，返回null
   * @throws MonitorException 监控系统异常
   */
	public Vector qryMerchantVector() throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql = "SELECT * FROM pos_merchant;";
			POSMerchantInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			Vector vReturn = new Vector();
			while (rst.next()) {
				info = new POSMerchantInfo();
				info.fetchData(rst);
				vReturn.add(info.getMerchantid());
				vReturn.add(info.getMctname());
			}
			rst.close();
			Debug.println("SIZE:" + vReturn.size());
			return vReturn;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

  /**
   * 根据机构编码查询该机构以及下级机构所拥有的商户信息。
   * @param orgId 机构号
   * @return LIST列表，每一个元素都是一个POSMerchantInfo对象
   * @throws MonitorException
   */
	public List getMerchantByOrg(String orgId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = SqlAccess.createConn();
		String sqlStr = "";
		try {
			if (orgId == null || orgId.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_merchant ORDER BY merchant_id";
			} else {
				sqlStr = "SELECT * FROM pos_merchant a, monit_orgref b WHERE a.org_id = b.org_id AND b.p_org_id = '" + orgId + "' ORDER BY merchant_id";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSMerchantInfo pmi = new POSMerchantInfo();
				pmi.fetchData(rs);
				list.add(pmi);
			}
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}

  /**
   * 根据选择的所属机构和商户名字的部分描述取得商户
   * @param orgId 机构ID
   * @param merchName 机构名称部分文字
   * @return LIST列表，每一个元素都是一个POSMerchantInfo对象
   */
	public List getMerchantByOrgAndName(String orgId, String merchName, String Operator) throws MonitorException {
		if (merchName == null) {
			merchName = "";
		}
		merchName = merchName.trim();
		List list = new ArrayList();
		SqlAccess conn = SqlAccess.createConn();
		String sqlStr = "";
		try {
			if (orgId == null || orgId.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_merchant a WHERE a.mct_name matches '*" + merchName + "*' ";
			} else {
				if ( Operator == null || Operator.trim().equals("") ){
					sqlStr = "SELECT * FROM pos_merchant a, monit_orgref b " +
    		  		         "WHERE a.org_id = b.org_id AND b.p_org_id = '" + orgId + "'"
    		  	        	+"AND a.mct_name matches '*" + merchName + "*' ";
				}else{
					sqlStr = "SELECT * FROM pos_merchant a, monit_orgref b " +
						     "WHERE a.org_id = b.org_id AND b.p_org_id = '" + orgId + "'"
		  		            +"AND a.mct_name matches '*" + merchName + "*' " +
		  			    	 "AND operator = '" + Operator + "' ";
				}
			}
			//add by liyp 20031022 add sort condition
			sqlStr += " ORDER BY merchant_id ";

			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSMerchantInfo pmi = new POSMerchantInfo();
				pmi.fetchData(rs);
				list.add(pmi);
			}
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}

  /**
   * 查询商户信息。
   * @param objId 商户编号
   * @return LIST列表，每一个元素都是一个POSMerchantInfo对象
   * @throws MonitorException
   *
   */
	public List getMerchantByObjId(String objId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = SqlAccess.createConn();
		String sqlStr = "";
		try {
			if (objId == null || objId.trim().equals("") || objId.trim().equals("000000")) {
				sqlStr = "SELECT * FROM pos_merchant ORDER BY merchant_id";
			} else {
				sqlStr = "SELECT * FROM pos_merchant , monit_orgref  WHERE merchant_id  ='" + objId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSMerchantInfo pmi = new POSMerchantInfo();
				pmi.fetchData(rs);
				list.add(pmi);
			}
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}

	public String getPOSMerchantInfo_Xml(String objId, String DealCode,
                                       String ErrorCode, String ErrorDescribe) throws MonitorException {
		List list = this.getMerchantByObjId(objId);
		StringBuffer xml = new StringBuffer(list.size() * 300);
		xml.append("<Deal>\n");

		xml.append("<DealCode>");
		xml.append(DealCode);
		xml.append("</DealCode>\n");

		xml.append("<ErrorCode>");
		xml.append(ErrorCode);
		xml.append("</ErrorCode>\n");

		xml.append("<ErrorDescribe>");
		xml.append(ErrorDescribe);
		xml.append("</ErrorDescribe>\n");

		if (list != null && list.size() != 0) {
			xml.append("<Data>\n");
			for (int i = 0; i < list.size(); i++) {
				POSMerchantInfo pmi = (POSMerchantInfo) list.get(i);
				xml.append("<MctInfo id=");
				xml.append(pmi.getMerchantid());
				xml.append(">\n");

				xml.append("<Name>");
				xml.append(pmi.getMctname());
				xml.append("</Name>\n");

				xml.append("<Currency>");
				xml.append(pmi.getCurrtype());
				xml.append("</Currency>\n");

				xml.append("<Telephone>");
				xml.append(pmi.getTelephone());
				xml.append("</Telephone>\n");

				xml.append("<BankNo>");
				xml.append(pmi.getManagebankno());
				xml.append("</BankNo>\n");

				xml.append("<BankName>");
				xml.append(pmi.getManagebankname());
				xml.append("</BankName>\n");

				xml.append("<Type>");
				xml.append(pmi.getMcttype());
				xml.append("</Type>\n");

				xml.append("</MctInfo>\n");
			}
			xml.append("</Data>\n");
		}
		xml.append("</Deal>");
		return xml.toString();
	}
}
