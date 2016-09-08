package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.adtec.toucs.monitor.common.CommToATMP;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.systemmanage.CodeDefine;

/**
 * <p>Title: 商业IC卡公司表管理</p>
 * <p>Description: 商业IC卡公司表管理</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p>
 * @author liuxy
 * @version 1.0
 */

public class PosCompanyBean {
	
	public PosCompanyBean() {
		
	}
	
	private String RetString = "";
	
	/**
	 * 登记商业IC卡公司表信息
	 * @param info 登记的商业IC卡公司表信息
	 * @return 登记成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int AppendInfo(PosCompanyInfo info, int iCount) throws MonitorException {	
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//生成增加语句
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			//增加商业IC卡公司表信息
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.REG_FAILED, "登记商业IC卡公司表信息失败！");
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
	 * 删除商业IC卡公司表
	 * @param info 登记的商业IC卡公司表信息
	 * @return 成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int DeleteInfo(String strKey) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		//pos商户实例化
		PosCompanyInfo info = new PosCompanyInfo();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			//生成删除商业IC卡公司表信息语句
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			//删除商业IC卡公司表信息
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除商业IC卡公司表信息失败！");
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
	 * 修改商业IC卡公司表信息
	 * @param info 登记的商业IC卡公司表信息
	 * @return 成功返回1
	 * @throws MonitorException 监控系统异常
	 */
	public int UpdateInfo(PosCompanyInfo info, String strKey) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			// 生成修改商业IC卡公司表语句
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			//修改商业IC卡公司表信息
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改商业IC卡公司表信息失败！");
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
	 * 查询商业IC卡公司表信息
	 * @param strKey 公司编号
	 * @param strKing 代理种类
	 * @return 商业IC卡公司表信息，如果商业IC卡公司表不存在，返回null
	 * @throws MonitorException 监控系统异常
	 */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {	
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询商业IC卡公司表信息
			String sql;
			if ( strKey != null && !strKey.trim().equals("")) {
				sql = "SELECT * FROM pos_company WHERE company_id='" + strKey + "' AND company_type='" + strType + "'"
				+ "ORDER BY company_id";
			} else  if (strType!=null && !strType.trim().equals("")){
				sql = "SELECT * FROM pos_company WHERE company_type='" + strType + "' ORDER BY company_id";
			} else {
				sql = "SELECT * FROM pos_company ORDER BY company_id";
			}
			
			PosCompanyInfo info = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				info = new PosCompanyInfo();
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
	 * 查询商业IC卡公司表信息
	 * @param posCode 设备编号
	 * @param orgId 当前用户所属机构
	 * @return 公司表信息，如果公司表不存在，返回null
	 * @throws MonitorException 监控系统异常
	 */
	public PosCompanyInfo QueryInfo(String strKey, String code_type) throws MonitorException {	
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询商业IC卡公司表信息
			String sql;
			sql = "SELECT * FROM pos_company WHERE company_id='" + strKey + "'";
			
			PosCompanyInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new PosCompanyInfo();
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
	 * 商业IC卡公司表管理，包括启用和停用。
	 * @param strKey 公司编号  strFlag 标志"1"-启用"0"-停用
	 * @throws MonitorException
	 */
	public int ManageInfo(String strKey, String strFlag ) throws MonitorException {
		SqlAccess conn = new SqlAccess();
		try {
			if (strKey == null || strKey.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			//company_stat "1"-start "0"-stop
			String sqlStr = "UPDATE pos_company SET company_stat = '"+strFlag+"' WHERE company_id = '" +
			strKey + "'";
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
	 * POS密钥下载程式，将P端返回的
	 * @param posID 设备编号
	 * @return 包含分解后的3组密钥的字符串数组
	 * @throws MonitorException
	 */
	public String down_auth(String key, String use_flag) throws MonitorException {
		String retStr = "";
		try {
			TranProc(CodeDefine.MASTER_KEYDOWN, key, use_flag);
			retStr = getRetMsg();
		} catch (MonitorException ex) {
			throw ex;
		}
		return retStr;
	}
	
	/**
	 * 交易处理程式，调用通讯程式与P端通信，因为不提供按机构的批量交易，所以默认为所有机构。
	 * @param TxCode 与P端接口中的交易码，以MG开头，例如MG7830为POS签到。
	 * @param DeviceCode POS设备编号
	 * @throws MonitorException
	 */
	private void TranProc(String TxCode, String DeviceCode, String use_flag) throws MonitorException {
		CommToATMP comm = new CommToATMP(TxCode, DeviceCode, use_flag);
		String retCode = comm.commitToATMP();
		if (!retCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			throw new MonitorException(retCode, comm.getErrorDesc());
		}
		RetString = comm.getErrorDesc();
	}
	
	/**
	 * 查询是否需要获取 昨日表 数据
	 * @param strStartDate 起始日期
	 * @param strEndDate 终止日期
	 * @return yestoday 昨日表表名
	 * @throws MonitorException
	 */
	public String YestodayName (String strStartDate, String strEndDate) throws MonitorException {
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		String yestoday = "";
		int yday,sday,eday;
		try {
			sql = "SELECT yesterday_log FROM kernel_sys_para WHERE sys_id='pos'";
			rst = sq.queryselect(sql);
			if ( rst != null && rst.next()) {
				yestoday = rst.getString(1).trim();
			}
			yday = Integer.parseInt(yestoday.substring(3,11));
			sday = Integer.parseInt(strStartDate);
			eday = Integer.parseInt(strEndDate);
			if ( yday>=sday && yday<=eday ) {
				return yestoday;
			}else{ 
				return null;
			}
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
	
	/**
	 * 商业IC卡交易统计
	 *     根据公司编号从历史表(昨日表)中按日取得交易统计信息
	 * @param strKey 公司编号
	 * @param strStartDate 起始日期
	 * @param strEndDate 终止日期
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QueryStatisticInfo(String strKey, String strStartDate, String strEndDate, String strMerchant_id) throws MonitorException {		
		SqlAccess sq = SqlAccess.createConn();		
		String sql;
		ResultSet rst; 
		Vector v1 = new Vector();
		try {
			//从pos_company表中查询是否有strKey公司编号
			sql = "SELECT company_id FROM pos_company WHERE company_id = '" + strKey + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				//从 历史表 中取得统计信息 QueryStatisticInfo1(表名,公司编号,起始日期,终止日期,商户编号)
				v1 = QueryStatisticInfo1("pos_cpy_log",rst.getString(1).trim(),strStartDate,strEndDate,strMerchant_id);
			}
			return v1;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	 * 查询该公司下的商户编号与交易日期
	 * @param tableName 表名
	 * @param strKey 公司编号
	 * @param strStartDate 起始日期
	 * @param strEndDate 终止日期
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QueryStatisticInfo1(String tableName, String strKey, 
			String strStartDate, String strEndDate, String strMerchant_id) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		int sumConsume=0, tolConsume=0;                //小计消费笔数,总计消费笔数
		int sumRegoods=0, tolRegoods=0;                //小计退货笔数,总计退货笔数
		double dbsumConsume=0.00,dbtolConsume=0.00;   //小计消费金额,总计消费金额
		double dbsumRegoods=0.00,dbtolRegoods=0.00;   //小计退货金额,总计退货金额
		String init_date = null;
		String strTrans_date="", strMerchant_id1;
		Vector v = new Vector();
		PosCompanyStatistic info = null;
		
		try {
			if ( strMerchant_id == null || strMerchant_id.equals("") ){
				sql = "SELECT trans_date,merchant_id FROM " + tableName + " ";
				sql = sql + "WHERE demo3 = '" + strKey + "' ";
				sql = sql + "AND ( trans_code = 'MC0010' OR trans_code = 'MC2010' ) ";
				sql = sql + "AND tran_flag ='0' ";
				sql = sql + "AND trans_date >= '" + strStartDate + "' ";
				sql = sql + "AND trans_date <= '" + strEndDate + "' ";
				sql = sql + "GROUP BY trans_date,merchant_id ORDER BY trans_date ";
			}else{
				sql = "SELECT trans_date,merchant_id FROM " + tableName + " ";
				sql = sql + "WHERE demo3 = '" + strKey + "' ";
				sql = sql + "AND ( trans_code = 'MC0010' OR trans_code = 'MC2010' ) ";
				sql = sql + "AND tran_flag ='0' AND merchant_id = '"+strMerchant_id + "' ";
				sql = sql + "AND trans_date >= '" + strStartDate + "' ";
				sql = sql + "AND trans_date <= '" + strEndDate + "' ";
				sql = sql + "GROUP BY trans_date,merchant_id ORDER BY trans_date ";
			}
			rst = sq.queryselect(sql);
			while (rst != null && rst.next()) {
				strTrans_date= rst.getString(1);
				strMerchant_id1 = rst.getString(2);
				if ( init_date != strTrans_date && init_date != null ) {
					info = new PosCompanyStatistic();
					info.setConsume_num(sumConsume+"");
					info.setConsume_amount(doubleToStr(dbsumConsume, 0, 2));
					info.setRegoods_num(sumRegoods+"");
					info.setRegoods_amount(doubleToStr(dbsumRegoods, 0, 2));
					
					sumConsume = 0;
					dbsumConsume = 0.00;
					sumRegoods = 0;
					dbsumRegoods = 0.00;
					
					info.setTrans_date(init_date);
					info.setMerchant_id("小计");
					v.add(info);
				}
				info = new PosCompanyStatistic();
				info = QueryStatisticInfo2(tableName, strKey, strTrans_date, strMerchant_id1);
				
				sumConsume = Integer.parseInt(info.getConsume_num()) + sumConsume;
				dbsumConsume = Double.parseDouble(info.getConsume_amount()) + dbsumConsume;
				sumRegoods = Integer.parseInt(info.getRegoods_num()) + sumRegoods;
				dbsumRegoods = Double.parseDouble(info.getRegoods_amount()) + dbsumRegoods;
				
				tolConsume = Integer.parseInt(info.getConsume_num()) + tolConsume;
				dbtolConsume = Double.parseDouble(info.getConsume_amount()) + dbtolConsume;
				tolRegoods = Integer.parseInt(info.getRegoods_num()) + tolRegoods;
				dbtolRegoods = Double.parseDouble(info.getRegoods_amount()) + dbtolRegoods;
				
				info.setCompany_id(strKey);            //公司编号
				info.setStart_date(strStartDate);      //起始日期
				info.setEnd_date(strEndDate);          //终止日期
				info.setTrans_date(strTrans_date);     //交易日期
				info.setMerchant_id(strMerchant_id1);  //商户编号
				
				v.add(info);
				init_date = strTrans_date;
			}
			//查询是否从 昨日表 中取数据 YestodayName(起始日期,终止日期)
			tableName = YestodayName(strStartDate,strEndDate);
			if ( tableName != null ) {	
				if ( strMerchant_id == null || strMerchant_id.equals("") ){
					sql = "SELECT trans_date,merchant_id FROM " + tableName + " ";
					sql = sql + "WHERE demo3 = '" + strKey + "' ";
					sql = sql + "AND ( trans_code = 'MC0010' OR trans_code = 'MC2010' ) ";
					sql = sql + "AND tran_flag ='0' ";
					sql = sql + "AND trans_date >= '" + strStartDate + "' ";
					sql = sql + "AND trans_date <= '" + strEndDate + "' ";
					sql = sql + "GROUP BY trans_date,merchant_id ORDER BY trans_date ";
				}else{
					sql = "SELECT trans_date,merchant_id FROM " + tableName + " ";
					sql = sql + "WHERE demo3 = '" + strKey + "' ";
					sql = sql + "AND ( trans_code = 'MC0010' OR trans_code = 'MC2010' ) ";
					sql = sql + "AND tran_flag ='0' AND merchant_id = '"+strMerchant_id + "' ";
					sql = sql + "AND trans_date >= '" + strStartDate + "' ";
					sql = sql + "AND trans_date <= '" + strEndDate + "' ";
					sql = sql + "GROUP BY trans_date,merchant_id ORDER BY trans_date ";
				}
				rst = sq.queryselect(sql);
				while (rst != null && rst.next()) {
					strTrans_date= rst.getString(1);
					strMerchant_id1 = rst.getString(2);
					if ( init_date != strTrans_date  && init_date != null ){
						info = new PosCompanyStatistic();
						info.setConsume_num(sumConsume+"");
						info.setConsume_amount(doubleToStr(dbsumConsume, 0, 2));
						info.setRegoods_num(sumRegoods+"");
						info.setRegoods_amount(doubleToStr(dbsumRegoods, 0, 2));
						
						sumConsume = 0;
						dbsumConsume = 0.00;
						sumRegoods = 0;
						dbsumRegoods = 0.00;
						
						info.setTrans_date(init_date);
						info.setMerchant_id("小计");
						v.add(info);
					}
					info = new PosCompanyStatistic();
					info = QueryStatisticInfo2(tableName, strKey, strTrans_date, strMerchant_id1);
					
					sumConsume = Integer.parseInt(info.getConsume_num()) + sumConsume;
					dbsumConsume = Double.parseDouble(info.getConsume_amount()) + dbsumConsume;
					sumRegoods = Integer.parseInt(info.getRegoods_num()) + sumRegoods;
					dbsumRegoods = Double.parseDouble(info.getRegoods_amount()) + dbsumRegoods;
					
					tolConsume = Integer.parseInt(info.getConsume_num()) + tolConsume;
					dbtolConsume = Double.parseDouble(info.getConsume_amount()) + dbtolConsume;
					tolRegoods = Integer.parseInt(info.getRegoods_num()) + tolRegoods;
					dbtolRegoods = Double.parseDouble(info.getRegoods_amount()) + dbtolRegoods;
					
					info.setCompany_id(strKey);            //公司编号
					info.setStart_date(strStartDate);      //起始日期
					info.setEnd_date(strEndDate);          //终止日期
					info.setTrans_date(strTrans_date);     //交易日期
					info.setMerchant_id(strMerchant_id1);  //商户编号
					v.add(info);
					init_date = strTrans_date;
				}
			}
			info = new PosCompanyStatistic();
			info.setConsume_num(sumConsume+"");
			info.setConsume_amount(doubleToStr(dbsumConsume, 0, 2));
			info.setRegoods_num(sumRegoods+"");
			info.setRegoods_amount(doubleToStr(dbsumRegoods, 0, 2));
			
			sumConsume = 0;
			dbsumConsume = 0.00;
			sumRegoods = 0;
			dbsumRegoods = 0.00;
			
			info.setTrans_date(strTrans_date);
			info.setMerchant_id("小计");
			v.add(info);
			
			info = new PosCompanyStatistic();
			info.setConsume_num(tolConsume+"");
			info.setConsume_amount(doubleToStr(dbtolConsume, 0, 2));
			info.setRegoods_num(tolRegoods+"");
			info.setRegoods_amount(doubleToStr(dbtolRegoods, 0, 2));
			info.setMerchant_id("总计");
			v.add(info);
			
			rst.close();
			return v;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		}	finally {
			sq.close();
		}		
	}
	
	/**
	 * 获取 消费与退货 的交易统计信息
	 * @param tableName 表名
	 * @param strKey 公司编号
	 * @param strStartDate 交易日期
	 * @param strEndDate 商户编号
	 * @return PosCompanyStatistic 结构
	 * @throws MonitorException
	 */
	public PosCompanyStatistic QueryStatisticInfo2(String tableName, String strKey, 
			String strStartDate, String strEndDate) throws MonitorException {	
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		
		PosCompanyStatistic info = null;
		int intValue=0,intValues=0;
		double dblValue=0,dblValues=0;
		String strValue = null;
		
		try {
			info = new PosCompanyStatistic();
			
			//MC0010消费 交易笔数与交易金额
			sql = "SELECT COUNT(*),SUM(trans_amount) FROM " + tableName +" ";
			sql = sql + "WHERE demo3='" + strKey + "' ";
			sql = sql + "AND trans_code ='MC0010' ";
			sql = sql + "AND tran_flag ='0' ";
			sql = sql + "AND trans_date = '" + strStartDate + "' ";
			sql = sql + "AND merchant_id = '" + strEndDate + "' ";
			
			rst = sq.queryselect(sql);
			if ( rst != null && rst.next()) {
				intValue = rst.getInt(1);
				strValue = "" + intValue;
				info.setConsume_num(strValue);
				dblValue = rst.getDouble(2);
				strValue = doubleToStr(dblValue, 0, 2);
				info.setConsume_amount(strValue);
			}
			rst.close();
			
			//MC2010退货 交易笔数与交易金额
			sql = "SELECT COUNT(*),SUM(trans_amount) FROM " + tableName + " ";
			sql = sql + "WHERE demo3='" + strKey + "' ";
			sql = sql + "AND trans_code ='MC2010' ";
			sql = sql + "AND tran_flag ='0' ";
			sql = sql + "AND trans_date = '" + strStartDate + "' ";
			sql = sql + "AND merchant_id = '" + strEndDate + "' ";
			
			rst = sq.queryselect(sql);
			if ( rst != null && rst.next()) {
				intValues = rst.getInt(1);
				strValue = "" + intValues;
				info.setRegoods_num(strValue);
				dblValues = rst.getDouble(2);
				strValue = doubleToStr(dblValues, 0, 2);
				info.setRegoods_amount(strValue);
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
	 * double型数据格式化
	 * @param d 被格式化数据
	 * @param vNumber 整数部分
	 * @param fNumber 小数部分
	 * @return
	 */
	public static String doubleToStr(double d, int vNumber, int fNumber) {
		int i = 0;
		if (vNumber <= 0){
			vNumber = 1;
		}	
		if (fNumber < 0){
			fNumber = 0;
		}	
		
		String pattern = "";	
		for (i = 0; i < vNumber; i++) {
			pattern = pattern + "#";
		}
		pattern = pattern + "0";
		switch (fNumber) {
			case 0:
				break;
			default:
				pattern += ".";
				for (i = 0; i < fNumber; i++) {
					pattern = pattern + "0";
				}
				break;
		}
		
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern(pattern);
		
		String value = formatter.format(d);	
		return value;
	}
		
	/**
	 * 商业IC卡交易明细
	 *     根据公司编号从历史表(昨日表)中按日取得交易明细
	 * @param strKey            公司编号
	 * @param strStartDate      起始日期
	 * @param strEndDate        终止日期
	 * @param strMerchant_id    商户编号
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QuerySubsidiaryInfo(String strKey, String strStartDate, 
			String strEndDate, String strMerchant_id) throws MonitorException {	
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst; 
		Vector v = new Vector();
		try {
			//从pos_company表中查询是否有strKey公司编号
			sql = "SELECT company_id FROM pos_company WHERE company_id = '" + strKey + "'";
			rst = sq.queryselect(sql);			
			if (rst != null && rst.next()) {
				//从 历史表 中取得统计信息 QueryStatisticInfo1(表名,公司编号,起始日期,终止日期,商户编号)
				v = QuerySubsidiaryInfo2("pos_cpy_log",rst.getString(1).trim(),strStartDate,strEndDate,strMerchant_id);
			}
			return v;
		}
		catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
	/**
	 * 商业IC卡交易明细
	 * @param tableName
	 * @param strKey
	 * @param strStartDate
	 * @param strEndDate
	 * @param strMerchant_id
	 * @return
	 * @throws MonitorException
	 */
	public Vector QuerySubsidiaryInfo2(String tableName, String strKey, 
			String strStartDate, String strEndDate, String strMerchant_id) throws MonitorException {		
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		Vector v = new Vector();
		
		try {
			if ( strMerchant_id == null || strMerchant_id.equals("") ){
				sql = "SELECT * FROM " + tableName + " ";
				sql = sql + "WHERE demo3='" + strKey + "' ";
				sql = sql + "AND tran_flag ='0' ";
				sql = sql + "AND trans_date >= '" + strStartDate + "' ";
				sql = sql + "AND trans_date <= '" + strEndDate + "' ";
			}else{
				sql = "SELECT * FROM " + tableName + " ";
				sql = sql + "WHERE demo3='" + strKey + "' ";
				sql = sql + "AND tran_flag ='0' ";
				sql = sql + "AND trans_date >= '" + strStartDate + "' ";
				sql = sql + "AND trans_date <= '" + strEndDate + "' ";
				sql = sql + "AND merchant_id = '"+strMerchant_id + "' ";		
			}
			
			rst = sq.queryselect(sql);
			while ( rst != null && rst.next()) {
				PosCompanySubsidiary info = new PosCompanySubsidiary();
				info.setCompany_id(strKey);
				info.setStart_date(strStartDate);
				info.setEnd_date(strEndDate);
				info.fetchData(rst);
				v.add(info);
			}
			
			//查询是否从 昨日表 中取数据 YestodayName(起始日期,终止日期)
			tableName = YestodayName(strStartDate,strEndDate);
			if ( tableName != null ) {	
				if ( strMerchant_id == null || strMerchant_id.equals("") ){
					sql = "SELECT * FROM " + tableName + " ";
					sql = sql + "WHERE demo3='" + strKey + "' ";
					sql = sql + "AND tran_flag ='0' ";
					sql = sql + "AND trans_date >= '" + strStartDate + "' ";
					sql = sql + "AND trans_date <= '" + strEndDate + "' ";
				}else{
					sql = "SELECT * FROM " + tableName + " ";
					sql = sql + "WHERE demo3='" + strKey + "' ";
					sql = sql + "AND tran_flag ='0' ";
					sql = sql + "AND trans_date >= '" + strStartDate + "' ";
					sql = sql + "AND trans_date <= '" + strEndDate + "' ";
					sql = sql + "AND merchant_id = '"+strMerchant_id + "' ";
				}
				
				rst = sq.queryselect(sql);
				while ( rst != null && rst.next()) {
					PosCompanySubsidiary info = new PosCompanySubsidiary();
					info.setCompany_id(strKey);
					info.setStart_date(strStartDate);
					info.setEnd_date(strEndDate);
					info.fetchData(rst);
					v.add(info);
				}
			}
			rst.close();
			
			return v;
		}catch (SQLException e1) {
			throw new MonitorException(e1);
		}finally {
			sq.close();
		}
	}
	
	/**
	 * 商业IC卡公司卡交易
	 *     统计信息 生成xls
	 * @param v
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void StatisticFile(Vector v, String strKey, 
			String strStartDate, String strEndDate) throws WriteException {
		try {
			String filePath = "/home/weblogic/bea/user_projects/domains/adtecdomain/loadfile/loadSubsidiary/company_statistics.xls";
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));			
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			Label label = new Label( 1, 0, "商业IC卡公司卡交易统计信息"); 
			sheet.addCell(label);
			Label company_idLabel = new Label( 0, 2, "公司编号:"); 
			sheet.addCell(company_idLabel);
			Label company_id = new Label( 1, 2, strKey ); 
			sheet.addCell(company_id);
			Label start_dateLabel = new Label( 0, 3, "起止日期:"); 
			sheet.addCell(start_dateLabel);
			Label start_date = new Label( 1, 3, strStartDate ); 
			sheet.addCell(start_date);
			Label end_dateLabel = new Label( 0, 4, "终止日期:"); 
			sheet.addCell(end_dateLabel);
			Label end_date = new Label( 1, 4, strEndDate ); 
			sheet.addCell(end_date);
			Label numberLabel = new Label( 0, 6, "序号"); 
			sheet.addCell(numberLabel);
			Label trans_dateLabel = new Label( 1, 6, "日期"); 
			sheet.addCell(trans_dateLabel);
			Label merchant_idLabel = new Label( 2, 6, "商户编号"); 
			sheet.addCell(merchant_idLabel);
			Label consume_numLabel = new Label( 3, 6, "消费笔数"); 
			sheet.addCell(consume_numLabel);
			Label consume_amountLabel = new Label( 4, 6, "消费金额"); 
			sheet.addCell(consume_amountLabel);
			Label regoods_numLabel = new Label( 5, 6, "退货笔数"); 
			sheet.addCell(regoods_numLabel);
			Label regoods_amountLabel = new Label( 6, 6, "退货金额"); 
			sheet.addCell(regoods_amountLabel);
			
			for(int i = 0; i < v.size(); i++){
				PosCompanyStatistic info=(PosCompanyStatistic)v.get(i);
				Number number = new Number( 0, i+7, i+1); 
				sheet.addCell(number);
				//日期
				Label trans_date = new Label( 1, i+7, info.getTrans_date() ); 
				sheet.addCell(trans_date);
				//商户编号
				Label merchant_id = new Label( 2, i+7, info.getMerchant_id() ); 
				sheet.addCell(merchant_id);
				//消费笔数
				Label consume_num = new Label( 3, i+7, info.getConsume_num() );
				sheet.addCell(consume_num);
				//消费金额
				Label consume_amount = new Label( 4, i+7, info.getConsume_amount() );
				sheet.addCell(consume_amount);
				//退货笔数
				Label regoods_num = new Label( 5, i+7, info.getRegoods_num() );
				sheet.addCell(regoods_num);
				//退货金额
				Label regoods_amount = new Label( 6, i+7, info.getRegoods_amount() ); 
				sheet.addCell(regoods_amount);
			}
			System.out.println("页面表格另存为EXCEL成功,请到"+filePath+"中查看");
			workbook.write(); 
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 商业IC卡公司卡交易
	 *     明细信息 生成xls
	 * @param v
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void SubsidiaryFile(Vector v, String strKey, 
			String strStartDate, String strEndDate) throws WriteException {
		try {
			String filePath = "/home/weblogic/bea/user_projects/domains/adtecdomain/loadfile/loadSubsidiary/company_detail.xls";
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));			
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			Label label = new Label( 1, 0, "商业IC卡公司卡交易明细信息"); 
			sheet.addCell(label);			
			Label company_idLabel = new Label( 0, 2, "公司编号:"); 
			sheet.addCell(company_idLabel);			
			Label company_id = new Label( 1, 2, strKey ); 
			sheet.addCell(company_id);			
			Label start_dateLabel = new Label( 0, 3, "起止日期:"); 
			sheet.addCell(start_dateLabel);		
			Label start_date = new Label( 1, 3, strStartDate ); 
			sheet.addCell(start_date);		
			Label end_dateLabel = new Label( 0, 4, "终止日期:"); 
			sheet.addCell(end_dateLabel);			
			Label end_date = new Label( 1, 4, strEndDate ); 
			sheet.addCell(end_date);			
			Label numberLabel = new Label( 0, 6, "序号"); 
			sheet.addCell(numberLabel);			
			Label seq_noLabel = new Label( 1, 6, "平台流水号"); 
			sheet.addCell(seq_noLabel);			
			Label pos_codeLabel = new Label( 2, 6, "设备编号"); 
			sheet.addCell(pos_codeLabel);			
			Label bill_noLabel = new Label( 3, 6, "票据号"); 
			sheet.addCell(bill_noLabel);			
			Label pos_batchLabel = new Label( 4, 6, "批号"); 
			sheet.addCell(pos_batchLabel);		
			Label merchant_idLabel = new Label( 5, 6, "商户号"); 
			sheet.addCell(merchant_idLabel);		
			Label sys_serialLabel = new Label( 6, 6, "平台交易序号"); 
			sheet.addCell(sys_serialLabel);			
			Label trans_card_noLabel = new Label( 7, 6, "交易卡号"); 
			sheet.addCell(trans_card_noLabel);			
			Label trans_codeLabel = new Label( 8, 6, "交易类型"); 
			sheet.addCell(trans_codeLabel);		
			Label trans_amountLabel = new Label( 9, 6, "交易金额"); 
			sheet.addCell(trans_amountLabel);		
			Label trans_dateLabel = new Label( 10, 6, "交易日期"); 
			sheet.addCell(trans_dateLabel);		
			Label trans_timeLabel = new Label( 11, 6, "交易时间"); 
			sheet.addCell(trans_timeLabel);		
			Label trans_card_typeLabel = new Label( 12, 6, "交易卡类型"); 
			sheet.addCell(trans_card_typeLabel);		
			Label oper_numLabel = new Label( 13, 6, "柜员号"); 
			sheet.addCell(oper_numLabel);			
			Label trans_feeLabel = new Label( 14, 6, "手续费"); 
			sheet.addCell(trans_feeLabel);		
			Label acq_idLabel = new Label( 15, 6, "机构代码"); 
			sheet.addCell(acq_idLabel);		
			Label tran_flagLabel = new Label( 16, 6, "交易标志"); 
			sheet.addCell(tran_flagLabel);		
			for(int i = 0; i < v.size(); i++){			
				PosCompanySubsidiary info=(PosCompanySubsidiary)v.get(i);				
				Number number = new Number( 0, i+7, i+1); 
				sheet.addCell(number);				
				//平台流水号
				Number seq_no = new Number( 1, i+7, info.getSeq_no() ); 
				sheet.addCell(seq_no);			
				//设备编号
				Label pos_code = new Label( 2, i+7, info.getPos_code() ); 
				sheet.addCell(pos_code);			
				//票据号
				Label bill_no = new Label( 3, i+7, info.getBill_no() );
				sheet.addCell(bill_no);			
				//批号
				Label pos_batch = new Label( 4, i+7, info.getPos_batch() );
				sheet.addCell(pos_batch);			
				//商户号
				Label merchant_id = new Label( 5, i+7, info.getMerchant_id() );
				sheet.addCell(merchant_id);			
				//平台交易序号
				Number sys_serial = new Number( 6, i+7, info.getSys_serial() ); 
				sheet.addCell(sys_serial);			
				//交易卡号
				Label trans_card_no = new Label( 7, i+7, info.getTrans_card_no() );
				sheet.addCell(trans_card_no);			
				//交易类型
				Label trans_code = new Label( 8, i+7, info.getTrans_code() );
				sheet.addCell(trans_code);			
				//交易金额
				Number trans_amount = new Number( 9, i+7, info.getTrans_amount() ); 
				sheet.addCell(trans_amount);			
				//交易日期
				Label trans_date = new Label( 10, i+7, info.getTrans_date() );
				sheet.addCell(trans_date);	
				//交易时间
				Label trans_time = new Label( 11, i+7, info.getTrans_time());
				sheet.addCell(trans_time);
				//交易卡类型
				Label trans_card_type = new Label( 12, i+7, info.getTrans_card_type());
				sheet.addCell(trans_card_type);
				//柜员号
				Label oper_num = new Label( 13, i+7, info.getOper_num() );
				sheet.addCell(oper_num);				
				//手续费
				Number trans_fee = new Number( 14, i+7, info.getTrans_fee() ); 
				sheet.addCell(trans_fee);				
				//机构代码
				Label acq_id = new Label( 15, i+7, info.getAcq_id() );
				sheet.addCell(acq_id);				
				//交易标志
				Label tran_flag = new Label( 16, i+7, info.getTran_flag() );
				sheet.addCell(tran_flag);				
			}			
			System.out.println("页面表格另存为EXCEL成功,请到"+filePath+"中查看");			
			workbook.write(); 
			workbook.close();			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 取当前系统时间并格式化
	 * 返回 date (yyyymmdd)
	 */
	public String sysDate(){
		Date date = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
		String times = from.format(date);
		return times;
	}
	
	/**
	 * 取得ATM返回消息，retMsg域
	 * @return ATM返回消息
	 */
	public String getRetMsg(){
		return RetString;
	}
}