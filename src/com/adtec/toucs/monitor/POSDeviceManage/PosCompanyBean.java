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
 * <p>Title: ��ҵIC����˾�����</p>
 * <p>Description: ��ҵIC����˾�����</p>
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
	 * �Ǽ���ҵIC����˾����Ϣ
	 * @param info �Ǽǵ���ҵIC����˾����Ϣ
	 * @return �Ǽǳɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int AppendInfo(PosCompanyInfo info, int iCount) throws MonitorException {	
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//�����������
			PreparedStatement stm = info.makeInsertStm(sq.conn);
			//������ҵIC����˾����Ϣ
			int flag = stm.executeUpdate();
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.REG_FAILED, "�Ǽ���ҵIC����˾����Ϣʧ�ܣ�");
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
	 * ɾ����ҵIC����˾��
	 * @param info �Ǽǵ���ҵIC����˾����Ϣ
	 * @return �ɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int DeleteInfo(String strKey) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		//pos�̻�ʵ����
		PosCompanyInfo info = new PosCompanyInfo();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����ɾ����ҵIC����˾����Ϣ���
			PreparedStatement stm = info.makeDeleteStm(sq.conn, strKey);
			//ɾ����ҵIC����˾����Ϣ
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ����ҵIC����˾����Ϣʧ�ܣ�");
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
	 * �޸���ҵIC����˾����Ϣ
	 * @param info �Ǽǵ���ҵIC����˾����Ϣ
	 * @return �ɹ�����1
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public int UpdateInfo(PosCompanyInfo info, String strKey) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			// �����޸���ҵIC����˾�����
			PreparedStatement stm = info.makeUpdateStm(sq.conn, strKey);
			//�޸���ҵIC����˾����Ϣ
			int flag = stm.executeUpdate();		
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸���ҵIC����˾����Ϣʧ�ܣ�");
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
	 * ��ѯ��ҵIC����˾����Ϣ
	 * @param strKey ��˾���
	 * @param strKing ��������
	 * @return ��ҵIC����˾����Ϣ�������ҵIC����˾�����ڣ�����null
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public Vector QueryInfoByList(String strKey, String strType) throws MonitorException {	
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��ҵIC����˾����Ϣ
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
	 * ��ѯ��ҵIC����˾����Ϣ
	 * @param posCode �豸���
	 * @param orgId ��ǰ�û���������
	 * @return ��˾����Ϣ�������˾�����ڣ�����null
	 * @throws MonitorException ���ϵͳ�쳣
	 */
	public PosCompanyInfo QueryInfo(String strKey, String code_type) throws MonitorException {	
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ��ҵIC����˾����Ϣ
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
	 * ��ҵIC����˾������������ú�ͣ�á�
	 * @param strKey ��˾���  strFlag ��־"1"-����"0"-ͣ��
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
	 * POS��Կ���س�ʽ����P�˷��ص�
	 * @param posID �豸���
	 * @return �����ֽ���3����Կ���ַ�������
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
	 * ���״����ʽ������ͨѶ��ʽ��P��ͨ�ţ���Ϊ���ṩ���������������ף�����Ĭ��Ϊ���л�����
	 * @param TxCode ��P�˽ӿ��еĽ����룬��MG��ͷ������MG7830ΪPOSǩ����
	 * @param DeviceCode POS�豸���
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
	 * ��ѯ�Ƿ���Ҫ��ȡ ���ձ� ����
	 * @param strStartDate ��ʼ����
	 * @param strEndDate ��ֹ����
	 * @return yestoday ���ձ����
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
	 * ��ҵIC������ͳ��
	 *     ���ݹ�˾��Ŵ���ʷ��(���ձ�)�а���ȡ�ý���ͳ����Ϣ
	 * @param strKey ��˾���
	 * @param strStartDate ��ʼ����
	 * @param strEndDate ��ֹ����
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QueryStatisticInfo(String strKey, String strStartDate, String strEndDate, String strMerchant_id) throws MonitorException {		
		SqlAccess sq = SqlAccess.createConn();		
		String sql;
		ResultSet rst; 
		Vector v1 = new Vector();
		try {
			//��pos_company���в�ѯ�Ƿ���strKey��˾���
			sql = "SELECT company_id FROM pos_company WHERE company_id = '" + strKey + "'";
			rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				//�� ��ʷ�� ��ȡ��ͳ����Ϣ QueryStatisticInfo1(����,��˾���,��ʼ����,��ֹ����,�̻����)
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
	 * ��ѯ�ù�˾�µ��̻�����뽻������
	 * @param tableName ����
	 * @param strKey ��˾���
	 * @param strStartDate ��ʼ����
	 * @param strEndDate ��ֹ����
	 * @return Vector
	 * @throws MonitorException
	 */
	public Vector QueryStatisticInfo1(String tableName, String strKey, 
			String strStartDate, String strEndDate, String strMerchant_id) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		int sumConsume=0, tolConsume=0;                //С�����ѱ���,�ܼ����ѱ���
		int sumRegoods=0, tolRegoods=0;                //С���˻�����,�ܼ��˻�����
		double dbsumConsume=0.00,dbtolConsume=0.00;   //С�����ѽ��,�ܼ����ѽ��
		double dbsumRegoods=0.00,dbtolRegoods=0.00;   //С���˻����,�ܼ��˻����
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
					info.setMerchant_id("С��");
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
				
				info.setCompany_id(strKey);            //��˾���
				info.setStart_date(strStartDate);      //��ʼ����
				info.setEnd_date(strEndDate);          //��ֹ����
				info.setTrans_date(strTrans_date);     //��������
				info.setMerchant_id(strMerchant_id1);  //�̻����
				
				v.add(info);
				init_date = strTrans_date;
			}
			//��ѯ�Ƿ�� ���ձ� ��ȡ���� YestodayName(��ʼ����,��ֹ����)
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
						info.setMerchant_id("С��");
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
					
					info.setCompany_id(strKey);            //��˾���
					info.setStart_date(strStartDate);      //��ʼ����
					info.setEnd_date(strEndDate);          //��ֹ����
					info.setTrans_date(strTrans_date);     //��������
					info.setMerchant_id(strMerchant_id1);  //�̻����
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
			info.setMerchant_id("С��");
			v.add(info);
			
			info = new PosCompanyStatistic();
			info.setConsume_num(tolConsume+"");
			info.setConsume_amount(doubleToStr(dbtolConsume, 0, 2));
			info.setRegoods_num(tolRegoods+"");
			info.setRegoods_amount(doubleToStr(dbtolRegoods, 0, 2));
			info.setMerchant_id("�ܼ�");
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
	 * ��ȡ �������˻� �Ľ���ͳ����Ϣ
	 * @param tableName ����
	 * @param strKey ��˾���
	 * @param strStartDate ��������
	 * @param strEndDate �̻����
	 * @return PosCompanyStatistic �ṹ
	 * @throws MonitorException
	 */
	public PosCompanyStatistic QueryStatisticInfo2(String tableName, String strKey, 
			String strStartDate, String strEndDate) throws MonitorException {	
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		String sql;
		ResultSet rst;
		
		PosCompanyStatistic info = null;
		int intValue=0,intValues=0;
		double dblValue=0,dblValues=0;
		String strValue = null;
		
		try {
			info = new PosCompanyStatistic();
			
			//MC0010���� ���ױ����뽻�׽��
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
			
			//MC2010�˻� ���ױ����뽻�׽��
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
	 * double�����ݸ�ʽ��
	 * @param d ����ʽ������
	 * @param vNumber ��������
	 * @param fNumber С������
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
	 * ��ҵIC��������ϸ
	 *     ���ݹ�˾��Ŵ���ʷ��(���ձ�)�а���ȡ�ý�����ϸ
	 * @param strKey            ��˾���
	 * @param strStartDate      ��ʼ����
	 * @param strEndDate        ��ֹ����
	 * @param strMerchant_id    �̻����
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
			//��pos_company���в�ѯ�Ƿ���strKey��˾���
			sql = "SELECT company_id FROM pos_company WHERE company_id = '" + strKey + "'";
			rst = sq.queryselect(sql);			
			if (rst != null && rst.next()) {
				//�� ��ʷ�� ��ȡ��ͳ����Ϣ QueryStatisticInfo1(����,��˾���,��ʼ����,��ֹ����,�̻����)
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
	 * ��ҵIC��������ϸ
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
		//ȡ���ݿ�����
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
			
			//��ѯ�Ƿ�� ���ձ� ��ȡ���� YestodayName(��ʼ����,��ֹ����)
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
	 * ��ҵIC����˾������
	 *     ͳ����Ϣ ����xls
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
			Label label = new Label( 1, 0, "��ҵIC����˾������ͳ����Ϣ"); 
			sheet.addCell(label);
			Label company_idLabel = new Label( 0, 2, "��˾���:"); 
			sheet.addCell(company_idLabel);
			Label company_id = new Label( 1, 2, strKey ); 
			sheet.addCell(company_id);
			Label start_dateLabel = new Label( 0, 3, "��ֹ����:"); 
			sheet.addCell(start_dateLabel);
			Label start_date = new Label( 1, 3, strStartDate ); 
			sheet.addCell(start_date);
			Label end_dateLabel = new Label( 0, 4, "��ֹ����:"); 
			sheet.addCell(end_dateLabel);
			Label end_date = new Label( 1, 4, strEndDate ); 
			sheet.addCell(end_date);
			Label numberLabel = new Label( 0, 6, "���"); 
			sheet.addCell(numberLabel);
			Label trans_dateLabel = new Label( 1, 6, "����"); 
			sheet.addCell(trans_dateLabel);
			Label merchant_idLabel = new Label( 2, 6, "�̻����"); 
			sheet.addCell(merchant_idLabel);
			Label consume_numLabel = new Label( 3, 6, "���ѱ���"); 
			sheet.addCell(consume_numLabel);
			Label consume_amountLabel = new Label( 4, 6, "���ѽ��"); 
			sheet.addCell(consume_amountLabel);
			Label regoods_numLabel = new Label( 5, 6, "�˻�����"); 
			sheet.addCell(regoods_numLabel);
			Label regoods_amountLabel = new Label( 6, 6, "�˻����"); 
			sheet.addCell(regoods_amountLabel);
			
			for(int i = 0; i < v.size(); i++){
				PosCompanyStatistic info=(PosCompanyStatistic)v.get(i);
				Number number = new Number( 0, i+7, i+1); 
				sheet.addCell(number);
				//����
				Label trans_date = new Label( 1, i+7, info.getTrans_date() ); 
				sheet.addCell(trans_date);
				//�̻����
				Label merchant_id = new Label( 2, i+7, info.getMerchant_id() ); 
				sheet.addCell(merchant_id);
				//���ѱ���
				Label consume_num = new Label( 3, i+7, info.getConsume_num() );
				sheet.addCell(consume_num);
				//���ѽ��
				Label consume_amount = new Label( 4, i+7, info.getConsume_amount() );
				sheet.addCell(consume_amount);
				//�˻�����
				Label regoods_num = new Label( 5, i+7, info.getRegoods_num() );
				sheet.addCell(regoods_num);
				//�˻����
				Label regoods_amount = new Label( 6, i+7, info.getRegoods_amount() ); 
				sheet.addCell(regoods_amount);
			}
			System.out.println("ҳ�������ΪEXCEL�ɹ�,�뵽"+filePath+"�в鿴");
			workbook.write(); 
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ҵIC����˾������
	 *     ��ϸ��Ϣ ����xls
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
			Label label = new Label( 1, 0, "��ҵIC����˾��������ϸ��Ϣ"); 
			sheet.addCell(label);			
			Label company_idLabel = new Label( 0, 2, "��˾���:"); 
			sheet.addCell(company_idLabel);			
			Label company_id = new Label( 1, 2, strKey ); 
			sheet.addCell(company_id);			
			Label start_dateLabel = new Label( 0, 3, "��ֹ����:"); 
			sheet.addCell(start_dateLabel);		
			Label start_date = new Label( 1, 3, strStartDate ); 
			sheet.addCell(start_date);		
			Label end_dateLabel = new Label( 0, 4, "��ֹ����:"); 
			sheet.addCell(end_dateLabel);			
			Label end_date = new Label( 1, 4, strEndDate ); 
			sheet.addCell(end_date);			
			Label numberLabel = new Label( 0, 6, "���"); 
			sheet.addCell(numberLabel);			
			Label seq_noLabel = new Label( 1, 6, "ƽ̨��ˮ��"); 
			sheet.addCell(seq_noLabel);			
			Label pos_codeLabel = new Label( 2, 6, "�豸���"); 
			sheet.addCell(pos_codeLabel);			
			Label bill_noLabel = new Label( 3, 6, "Ʊ�ݺ�"); 
			sheet.addCell(bill_noLabel);			
			Label pos_batchLabel = new Label( 4, 6, "����"); 
			sheet.addCell(pos_batchLabel);		
			Label merchant_idLabel = new Label( 5, 6, "�̻���"); 
			sheet.addCell(merchant_idLabel);		
			Label sys_serialLabel = new Label( 6, 6, "ƽ̨�������"); 
			sheet.addCell(sys_serialLabel);			
			Label trans_card_noLabel = new Label( 7, 6, "���׿���"); 
			sheet.addCell(trans_card_noLabel);			
			Label trans_codeLabel = new Label( 8, 6, "��������"); 
			sheet.addCell(trans_codeLabel);		
			Label trans_amountLabel = new Label( 9, 6, "���׽��"); 
			sheet.addCell(trans_amountLabel);		
			Label trans_dateLabel = new Label( 10, 6, "��������"); 
			sheet.addCell(trans_dateLabel);		
			Label trans_timeLabel = new Label( 11, 6, "����ʱ��"); 
			sheet.addCell(trans_timeLabel);		
			Label trans_card_typeLabel = new Label( 12, 6, "���׿�����"); 
			sheet.addCell(trans_card_typeLabel);		
			Label oper_numLabel = new Label( 13, 6, "��Ա��"); 
			sheet.addCell(oper_numLabel);			
			Label trans_feeLabel = new Label( 14, 6, "������"); 
			sheet.addCell(trans_feeLabel);		
			Label acq_idLabel = new Label( 15, 6, "��������"); 
			sheet.addCell(acq_idLabel);		
			Label tran_flagLabel = new Label( 16, 6, "���ױ�־"); 
			sheet.addCell(tran_flagLabel);		
			for(int i = 0; i < v.size(); i++){			
				PosCompanySubsidiary info=(PosCompanySubsidiary)v.get(i);				
				Number number = new Number( 0, i+7, i+1); 
				sheet.addCell(number);				
				//ƽ̨��ˮ��
				Number seq_no = new Number( 1, i+7, info.getSeq_no() ); 
				sheet.addCell(seq_no);			
				//�豸���
				Label pos_code = new Label( 2, i+7, info.getPos_code() ); 
				sheet.addCell(pos_code);			
				//Ʊ�ݺ�
				Label bill_no = new Label( 3, i+7, info.getBill_no() );
				sheet.addCell(bill_no);			
				//����
				Label pos_batch = new Label( 4, i+7, info.getPos_batch() );
				sheet.addCell(pos_batch);			
				//�̻���
				Label merchant_id = new Label( 5, i+7, info.getMerchant_id() );
				sheet.addCell(merchant_id);			
				//ƽ̨�������
				Number sys_serial = new Number( 6, i+7, info.getSys_serial() ); 
				sheet.addCell(sys_serial);			
				//���׿���
				Label trans_card_no = new Label( 7, i+7, info.getTrans_card_no() );
				sheet.addCell(trans_card_no);			
				//��������
				Label trans_code = new Label( 8, i+7, info.getTrans_code() );
				sheet.addCell(trans_code);			
				//���׽��
				Number trans_amount = new Number( 9, i+7, info.getTrans_amount() ); 
				sheet.addCell(trans_amount);			
				//��������
				Label trans_date = new Label( 10, i+7, info.getTrans_date() );
				sheet.addCell(trans_date);	
				//����ʱ��
				Label trans_time = new Label( 11, i+7, info.getTrans_time());
				sheet.addCell(trans_time);
				//���׿�����
				Label trans_card_type = new Label( 12, i+7, info.getTrans_card_type());
				sheet.addCell(trans_card_type);
				//��Ա��
				Label oper_num = new Label( 13, i+7, info.getOper_num() );
				sheet.addCell(oper_num);				
				//������
				Number trans_fee = new Number( 14, i+7, info.getTrans_fee() ); 
				sheet.addCell(trans_fee);				
				//��������
				Label acq_id = new Label( 15, i+7, info.getAcq_id() );
				sheet.addCell(acq_id);				
				//���ױ�־
				Label tran_flag = new Label( 16, i+7, info.getTran_flag() );
				sheet.addCell(tran_flag);				
			}			
			System.out.println("ҳ�������ΪEXCEL�ɹ�,�뵽"+filePath+"�в鿴");			
			workbook.write(); 
			workbook.close();			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * ȡ��ǰϵͳʱ�䲢��ʽ��
	 * ���� date (yyyymmdd)
	 */
	public String sysDate(){
		Date date = new Date();
		SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
		String times = from.format(date);
		return times;
	}
	
	/**
	 * ȡ��ATM������Ϣ��retMsg��
	 * @return ATM������Ϣ
	 */
	public String getRetMsg(){
		return RetString;
	}
}