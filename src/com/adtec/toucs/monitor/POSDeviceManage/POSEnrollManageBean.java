package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;
import com.adtec.toucs.monitor.devicemanage.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec</p>
 * @author not attributable
 * @version 1.0
 */

public class POSEnrollManageBean {
	
	public POSEnrollManageBean() {
	}
	
	private String RetString = "";
	
	//编号种类参数
	private static final String POSPTYPE = "1";
	private static final String POSCTYPE = "2";

	  /**
	   * 修改POS设备信息
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int updatePosInfo(POSEnrollInfo info, String key) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//添加设备基本信息
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit(); //提交
			} else {
				sq.rollback(); //事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改签约POS信息失败！");
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
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public POSEnrollInfo qryPosInfo(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp编号
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC编号
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC编号
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}

			Debug.println("*****wuye debug; pos SQL = :\n" + sql);
			
			POSEnrollInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSEnrollInfo();
				info.setPoscode(toucsString.unConvert(rst.getString("pos_code")));
				info.setPosCCode(toucsString.unConvert(rst.getString("pos_c_code")));
				info.setPosDccCode(toucsString.unConvert(rst.getString("pos_dcc_code")));
				info.setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
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
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public POSEnrollInfo qryPosEnroll(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp编号
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC编号
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC编号
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			POSEnrollInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSEnrollInfo();
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
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public Vector qryPosInfoByPoscode(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { //posp编号
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { //POSC编号
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { //DCC编号
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,
                                   ErrorDefine.NOTHISDEVICEDESC);
			}
			POSEnrollInfo posenroll = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posenroll = new POSEnrollInfo();
				posenroll.fetchData(rst);
				v.add(posenroll);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1.getMessage());
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 查询商户基本配置信息
	   * @param posCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public Vector qryMctPosInfo(String merchantNo) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql = "SELECT * FROM pos_enrol" + " WHERE merchant_id='" + merchantNo + "' ORDER BY pos_dcc_code ";
			POSEnrollInfo posenroll = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posenroll = new POSEnrollInfo();
				posenroll.fetchData(rst);
				v.add(posenroll);
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
	   * 登记POS新设备
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int regPosDevice(POSEnrollInfo info, int nPoscount) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//添加设备基本信息
			int flag = info.insert(sq);
			
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记POS设备信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			Debug.println("\n****wuye debug;插入不成功，delete" + info.getPoscode() + " OK !*****");
			throw new MonitorException(e1);
		} finally {
			if (sq != null) {
				if (sq.isConnectDB()) {
					sq.close();
				}
			}
		}
	}

	  /**
	   * 删除POS新商户
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int deletePosInfo(String posCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSEnrollInfo dinfo = new POSEnrollInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//删除POS设备基本信息
			PreparedStatement stm = dinfo.makeDeleteStm(sq.conn, posCode);
			int flag = stm.executeUpdate(); 	  
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除POS商户信息失败！");
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
	   * 设置POS启用、停用标志
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	public int setStatPos(String pos_code, boolean bStat) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		POSEnrollInfo dinfo = new POSEnrollInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//设置商户清理标志基本信息
			PreparedStatement stm = dinfo.makeStatStm(sq.conn, pos_code, bStat);
			int flag = stm.executeUpdate();
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交 	
			} else {
				sq.rollback(); //事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改POS商户信息失败！");
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
	   * 查询密钥配置信息
	   * @param posCode 设备编号
	   * @param orgId 机构编号
	   * @return 密钥配置信息
	   * @throws MonitorException
	   */
	public AtmKeyInfo qryKeyInfo(String posCode, String orgId) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//校验设备是否存在并且在操作员所属机构范围内
			if (orgId != null && orgId.trim().length() > 0) {
				if (!deviceInOrg(posCode, orgId, sq)) {
					return null;
				}
			}
			String sql = "SELECT * FROM kernel_key_info WHERE sender_agency='" +
			posCode + "'";
			ResultSet rst = sq.queryselect(sql);
			AtmKeyInfo keyInfo = null;
			if (rst != null && rst.next()) {
				keyInfo = new AtmKeyInfo();
				keyInfo.fetchData(rst);
			}
			rst.close();
			return keyInfo;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 判断给定的设备号是否是给定的机构的下属机构列表（包括给定的机构本身）所属，不是则抛出异常。
	   * @param posCode 设备号
	   * @param orgId  机构号
	   * @param sq   数据库查询实例
	   * @throws MonitorException
	   */
	public boolean deviceInOrg(String posCode, String orgId, SqlAccess sq) throws MonitorException {
		Debug.println("1:" + posCode);
		if (posCode == null || posCode.trim().length() == 0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		Debug.println("2:" + orgId);
		if (orgId == null || orgId.trim().length() == 0) {
			throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
		}
		posCode = posCode.trim();
		
		boolean exist = false;
		String tmpOrgId = "";
		//取出设备所属机构
		String sql = "SELECT a.org_id FROM pos_merchant a, pos_info b "
				   + "WHERE a.merchant_id =b.merchant_id AND b.pos_code='"
				   + posCode + "'";
		Debug.println("3:" + sql);
		try {
			ResultSet rst = sq.queryselect(sql);
			if (rst.next()) { //如果能查询ATM设备对应的组织机构ID
				tmpOrgId = rst.getString("org_id").trim();
				rst.close();
				Debug.println("设备[" + posCode + "]所属机构:" + tmpOrgId);
				if (orgId.equals(tmpOrgId)) { //如果查询到的组织机构与给定的机构相等，则表示存在
					exist = true;
				} else { //如果查询到的组织机构与给定的机构不相等，查询该机构是否是给定机构的下属机构
					sql = "SELECT org_id FROM monit_orgref WHERE org_id='" + tmpOrgId +
						  "' AND p_org_id='" + orgId + "'";
					rst = sq.queryselect(sql);
					//设备所属机构是给定机构的下属机构
					if (rst.next()) {
						exist = true;
					}
					rst.close();
				}
			}
			if (!exist) {
				Debug.println("设备[" + posCode + "]不属于机构[" + orgId + "]管辖范围");
			}
			if (!exist) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}	
		} catch (SQLException sqle) {
			throw new MonitorException(sqle);
		}
		return true;
	}

	  /**
	   * 修改ATM密钥配置信息
	   * @param keyInfo 密钥配置信息
	   * @return 修改成功返回1
	   * @throws MonitorException
	   */
	public int updateKeyInfo(AtmKeyInfo keyInfo) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//修改密钥配置信息
			int ret = keyInfo.update(sq);
			//根据当前设备的加载状态修改加载标志
			if (ret == 1) {
				sq.commit();
			} else {
				sq.rollback();
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改密钥配置信息失败！");
			}
			return 1;
		} catch (SQLException exp) {
			sq.rollback();
			throw new MonitorException(exp);
		} finally {
			sq.close();
		}
	}

	  /**
	   * 交易处理程式，调用通讯程式与P端通信，因为不提供按机构的批量交易，所以默认为所有机构。
	   * @param TxCode 与P端接口中的交易码，以MG开头，例如MG7830为POS签到。
	   * @param DeviceCode POS设备编号
	   * @throws MonitorException
	   */
	private void TranProc(String TxCode, String DeviceCode) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, "000000000");
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
	}

	  /**
	   * 签约POS设备管理，包括启用和停用。
	   * @param PosID POS设备编号  UseFalg 标志"1"-启用"0"-停用
	   * @throws MonitorException
	   */
	public int managePosInfo(String PosID, String UseFlag ) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (PosID == null || PosID.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			//UseFlag "1"-start "0"-stop
			String sqlStr = "UPDATE pos_enrol SET use_flag = '"+UseFlag+"' WHERE pos_code = '" + PosID + "'";
			int affect = conn.queryupdate(sqlStr);
			if (affect <= 0) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			return affect;
		} catch (SQLException sqlex) {
			throw new MonitorException(sqlex.getErrorCode(), sqlex.getMessage());
		} finally {
			conn.close();
		}
	}

	  /**
	   * 设备批量签到，以全零的编号代表所有设备。
	   * @throws MonitorException
	   */
	public void registPOS() throws MonitorException {
		try {
			TranProc(CodeDefine.POS_REGIST, "00000000");
		} catch (MonitorException ex) {
			throw ex;
		}
	}

	  /**
	   * POS密钥下载程式，将P端返回的
	   * @param posID 设备编号
	   * @return 包含分解后的3组密钥的字符串数组
	   * @throws MonitorException
	   */
	public String pos_key_down(String posID) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.POS_KEYDOWN, posID);
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}

	  /**
	   * 取得ATM返回消息，retMsg域
	   * @return ATM返回消息
	   */
	public String getRetMsg() {
		return RetString;
	}

	  /**
	   *  根据机构编码查询POS信息，返回LIST。若orgid为null或者org等于空串，则查询所有的POS信息。
	   * @param orgId 机构编码
	   * @return 返回LIST。
	   * @throws MonitorException
	   */
	public List getPOSInfoByOrg(String orgId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess();
		String sqlStr = "";
		try {
			if (orgId == null || orgId.trim().equals("")) {
				sqlStr = "SELECT * FROM pos_info";
			} else {
				sqlStr = "SELECT a.pos_code,a.merchant_id,a.pos_stat,a.bill_no,a.pos_batch,a.con_num,a.con_amt,a.ref_num,a.ref_amt,a.rebate,a.open_bankno,a.exg_no,a.acct_no,a.vacct_no FROM pos_info a,pos_merchant b, monit_orgref c WHERE b.org_id = c.org_id AND a.Merchant_id = b.Merchant_id AND c.p_org_id = '" + orgId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSEnrollInfo pi = new POSEnrollInfo();
				pi.fetchData(rs);
				list.add(pi);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}

	  /**
	   *  根据设备编码查询POS信息，返回LIST。若objId为null或者objID等于000000，则查询所有的POS信息。
	   * @param objId 设备编码
	   * @return 返回LIST。
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public List getPOSInfoByObjId(String objId) throws MonitorException {
		List list = new ArrayList();
		SqlAccess conn = new SqlAccess();
		String sqlStr = "";
		try {
			if (objId == null || objId.trim().equals("") || objId.trim().equals("000000")) {
				sqlStr = "SELECT * FROM pos_info";
			} else {
				sqlStr = "SELECT * FROM pos_info  WHERE pos_code = '" + objId + "'";
			}
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				POSEnrollInfo pi = new POSEnrollInfo();
				pi.fetchData(rs);
				list.add(pi);
			}
			rs.close();
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
		return list;
	}

	  /**
	   *  根据设备编码查询POS交易配置及手输卡设置信息
	   * @param objId 设备编码
	   * @return 返回POSTxnInfo。
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public POSEnrollInfo qryPosTxnInfo(String objId, String code_type) throws MonitorException {
		if (code_type == null) {
			code_type = "";
		}
		code_type = code_type.trim();
		SqlAccess conn = new SqlAccess();
		POSEnrollInfo pi = null;
		String sqlStr = "";
		try {
			sqlStr = "SELECT pos_code,pos_dcc_code,pos_c_code,pre_authorized,equip_type,tran_right,hand_flag,memo1 " + "FROM pos_info WHERE ";
			if (code_type.equalsIgnoreCase(POSPTYPE)) {
				sqlStr += " pos_code = '";
			}
			else if (code_type.equalsIgnoreCase(POSCTYPE)) {
				sqlStr += " pos_c_code = '";
			} else {
				sqlStr += " pos_dcc_code = '";
			}
			sqlStr += objId;
			sqlStr += "'";
			ResultSet rs = conn.queryselect(sqlStr);
			while (rs != null && rs.next()) {
				pi = new POSEnrollInfo();
			}
			rs.close();
			return pi;
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			conn.close();
		}
	}

	  /**
	   *  根据设备编码查询POS交易配置及手输卡设置信息
	   * @param objId 设备编码
	   * @return 返回POSTxnInfo的列表。
	   * @throws MonitorException
	   * added by liyp 20030616
	   */
	public Vector qryPosTxnInfoSet(POSEnrollInfo pi) throws MonitorException {
		Vector vReturn = new Vector();
		Vector vTxn = TxnCodeManageBean.queryPosTxnCodes();
		if (vTxn != null) {
			for (int i = 0; i < vTxn.size(); i++) {
				POSTxnInfo pti = new POSTxnInfo();
				TxnCodeBean code = (TxnCodeBean) vTxn.get(i);
				int sn = code.getMaskSerial();
				pti.setLocation(sn - 1);
				pti.setTxnName(code.getTxnName());
				vReturn.add(pti);
			}
		}
		return vReturn;
	}

	  /**
	   * 将POS-DCC编号转换为POS-P编号
	   * @param pos_dcc_code DCC编号
	   * @return pos_code P端编号
	   */
	public static String transDCCCodeToPCode(String pos_dcc_code) throws MonitorException {
		String pos_p_code = "";
		SqlAccess sq = new SqlAccess();
		try {
			Statement stm = sq.conn.createStatement();
			String sql;
			sql = "SELECT pos_code FROM pos_info WHERE pos_dcc_code = '" + pos_dcc_code + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				pos_p_code = rs.getString("pos_code");
			}
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return pos_p_code;
	}

	  /**
	   * 将POS-P编号转换为POS-DCC编号
	   * @param pos_code P端编号
	   * @return  pos_dcc_code DCC编号
	   */
	public static String transPCodeToDCCCode(String pos_code) throws MonitorException {
		String pos_dcc_code = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql;
			sql = "SELECT pos_dcc_code FROM pos_info WHERE pos_code = '" + pos_code + "'";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				pos_dcc_code = rs.getString("pos_dcc_code");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return pos_dcc_code;
	}

	  /**
	   * 用于只删除pos_info中的信息
	   * @param pos_code P端编号
	   * @return  pos_dcc_code DCC编号
	   */
	public int delOnlyPos(String posCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		int flag;
		try {
			//删除POS设备基本信息
			sq.setAutoCommit(true);
			PreparedStatement stm = (new POSEnrollInfo()).makeDeleteStm(sq.conn, posCode);
			flag = stm.executeUpdate();
		} catch (SQLException sqe) {
			throw new MonitorException(sqe);
		} finally {
			sq.close();
		}
		return flag;
	}
	
	  /**
	   * 通过POS-DCC编号查找商户编号
	   * @param PosDccCode Dcc编号
	   * @return  商户名称
	   *
	   */
	public String getPOSMerchantName(String PosDccCode) throws MonitorException {
		String merchantId = PosDccCode.substring(0, 15);
		String merchantName = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT mct_name FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				merchantName = rs.getString("mct_name");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return merchantName;
	}

	  /**
	   * 通过POS-DCC编号查找管辖支行
	   * @param PosDccCode Dcc编号
	   * @return  管辖支行
	   *
	   */
	public String getPOSOrgName(String PosDccCode) throws MonitorException {
		String merchantId = PosDccCode.substring(0, 15);
		String Organname = "", Organno = "";
		SqlAccess sq = new SqlAccess();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT manage_bankno, manage_bankname FROM pos_merchant WHERE merchant_id = '" + merchantId + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Organno = rs.getString("manage_bankno");
				Organname = rs.getString("manage_bankname");
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return Organno + " - " +Organname;
	}
}