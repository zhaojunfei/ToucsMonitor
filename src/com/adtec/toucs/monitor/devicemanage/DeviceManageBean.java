package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;
 
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: 设备管理类</p>
 * <p>Description:封装设备管理相关的业务逻辑</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DeviceManageBean {
	  private SqlAccess conn1 = null;
	  private ResultSet rs = null;
	  public int posi_x;
	  public int posi_y;
	  public int mapcx;
	  public int mapcy;
	  public String AtmCode;
	  public static char globechar;
	  public String[] Atmtimer1Power;
	  public String[] Atmtimer2Power;
	  public String[] Atmtimer3Power;
	  public String[] Atmtimer4Power;
	  public String[] Atmtimer5Power;
	  public String[] Atmtimer6Power;
	  public String[] Atmtimer7Power;
	  public String[] Atmtimer8Power;
	  public String[] istypechecked;
	  public String[] Atmid;
	  public String[] AtmType;
	  public String TotalDayChecked;
	  public String Atimer1Pow;
	  public String Atimer2Pow;
	  public String Atimer3Pow;
	  public String Atimer4Pow;
	  public String Atimer5Pow;
	  public String Atimer6Pow;
	  public String Atimer7Pow;
	  public String Atimer8Pow;
	  public String Atimer1begin;
	  public String Atimer1end;
	  public String Atimer2begin;
	  public String Atimer2end;
	  public String Atimer3begin;
	  public String Atimer3end;
	  public String Atimer4begin;
	  public String Atimer4end;
	  public String Atimer5begin;
	  public String Atimer5end;
	  public String Atimer6begin;
	  public String Atimer6end;
	  public String Atimer7begin;
	  public String Atimer7end;
	  public String Atimer8begin;
	  public String Atimer8end;
	  public String tStr;
	  public String ReturnedAtmID = new String("");
	  public String ReturnAtimer1Pow;
	  public String ReturnAtimer2Pow;
	  public String ReturnAtimer3Pow;
	  public String ReturnAtimer4Pow;
	  public String ReturnAtimer5Pow;
	  public String ReturnAtimer6Pow;
	  public String ReturnAtimer7Pow;
	  public String ReturnAtimer8Pow;
	  public byte[] Powcode = new byte[64];
	  public Vector PowAtm1 = null;
	  public Vector UnSelecPow1 = null;
	  public byte[] UnselecPowAtm1 = new byte[64];
	  public Vector PowAtm2 = null;
	  public Vector UnSelecPow2 = null;
	  public byte[] UnselecPowAtm2 = new byte[64];
	  public Vector PowAtm3 = null;
	  public Vector UnSelecPow3 = null;
	  public byte[] UnselecPowAtm3 = new byte[64];
	  public Vector PowAtm4 = null;
	  public Vector UnSelecPow4 = null;
	  public byte[] UnselecPowAtm4 = new byte[64];
	  public Vector PowAtm5 = null;
	  public Vector UnSelecPow5 = null;
	  public byte[] UnselecPowAtm5 = new byte[64];
	  public Vector PowAtm6 = null;
	  public Vector UnSelecPow6 = null;
	  public byte[] UnselecPowAtm6 = new byte[64];
	  public Vector PowAtm7 = null;
	  public Vector UnSelecPow7 = null;
	  public byte[] UnselecPowAtm7 = new byte[64];
	  public Vector PowAtm8 = null;
	  public Vector UnSelecPow8 = null;
	  public byte[] UnselecPowAtm8 = new byte[64];
	  public String TheAllATMPower;

	  /**
	   * 构造方法
	   */
	  public DeviceManageBean() {
	  }

	  /**
	   * 登记新设备
	   * @param info 登记的设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int regDevice(ATMInfo info) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //添加设备基本信息
			  PreparedStatement stm = info.makeInsertStm(sq.conn);
			  int flag = stm.executeUpdate();
			  sq.setAutoCommit(false);
			  //添加钞箱配置信息记录
			  if (flag == 1) {
				  flag = sq.queryupdate("INSERT INTO atm_box_config(atm_code)" + " VALUES('" + info.getAtmCode() + "')");
				  //添加密钥管理表记录
			  }
			  if (flag == 1) {
				  AtmKeyInfo keyInfo = new AtmKeyInfo(info.getAtmCode());
				  flag = keyInfo.insert(sq);
			  }

			  //添加位置信息
			  if (flag == 1) {
				  flag = sq.queryupdate("INSERT INTO monit_atmposition VALUES('" + info.getAtmCode() + "',0,0,0)");
				  //添加状态信息
			  }
			  if (flag == 1) {
				  flag = sq.queryupdate("INSERT INTO atm_stat(atm_code) VALUES('" + info.getAtmCode() + "')");
			  }
			  if (flag == 1) {
				  sq.commit();//提交
			  } else {
				  sq.rollback();  
				  //事务回滚
				  throw new MonitorException(ErrorDefine.REG_FAILED, "登记设备信息失败！");
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
	   * 修改设备信息
	   * @param info 设备登记信息
	   * @return 修改成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int updateDevice(ATMInfo info, String key) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //更新设备信息
			  boolean b = info.DataIsChange();
			  PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			  int ret = stm.executeUpdate();
			  //根据当前设备的加载状态修改加载标志
			  if (ret == 1) {
				  ret = setDeviceUseFlagWichCompare(info.getAtmCode(), sq, b);  
			  }
			  if (ret == 1) {
				  sq.commit();//提交
			  } else {
				  sq.rollback();
				  throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改设备信息失败！");
			  }
			  return 1;
		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
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
				  ret = setUseFlagWichCompare(keyInfo.getAtmCode(), sq, "0");
			  }
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
	   * 修改钞箱设置
	   * @param boxInfo 钞箱设置信息
	   * @return 修改成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int updateBoxConfig(AtmBoxInfo boxInfo) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //更新钞箱配置信息
			  PreparedStatement stm = boxInfo.makeUpdateStm(sq.conn);
			  int ret = stm.executeUpdate();
			  //更新成功并且钞箱配置符合加载要求
			  //根据当前设备的加载状态修改加载标志
			  if (ret == 1) {
				  ret = setUseFlagWichCompare(boxInfo.getAtmCode(), sq, "0");
			  }
			  if (ret == 1) {
				  sq.commit(); //提交 
			  } else {
				  sq.rollback();//事务回滚
				  throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改钞箱配置信息失败！");
			  }
			  return 1;
		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  /**
	   * 删除设备（未使用）
	   * @param atmCode 设备编号
	   * @return 删除成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int delDevice1(String atmCode) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //校验设备是否存在并且在操作员所属机构范围内
			  //删除设备基本信息
			  String sql = "DELETE FROM atm_info WHERE atm_code=?";
			  PreparedStatement stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, atmCode);
			  int flag = stm.executeUpdate();
			  //删除钞箱配置信息
			  //if(flag==1)
			  flag = sq.queryupdate("DELETE FROM atm_box_config WHERE atm_code='" + atmCode + "'");
			  //删除密钥管理表记录
			  //if(flag==1)
			  flag = sq.queryupdate("DELETE FROM kernel_key_info WHERE sender_agency='" + atmCode + "'");
			  //删除位置信息
			  //if(flag==1)
			  flag = sq.queryupdate("DELETE FROM monit_atmposition WHERE atm_id='" + atmCode + "'");
			  //删除状态信息
			  flag = sq.queryupdate("DELETE FROM atm_stat WHERE atm_code='" + atmCode + "'");
			  sq.commit();  //提交
			  return flag;
		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  public void getTimerConfig() throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT A.start_time, A.end_time FROM kernel_phase A where A.sys_id = 'atm' ");
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  rs = sq.queryselect(SqlStr.toString());
			  rs.next();
			  Atimer1begin = toucsString.unConvert(rs.getString(1));
			  Atimer1end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer2begin = toucsString.unConvert(rs.getString(1));
			  Atimer2end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer3begin = toucsString.unConvert(rs.getString(1));
			  Atimer3end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer4begin = toucsString.unConvert(rs.getString(1));
			  Atimer4end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer5begin = toucsString.unConvert(rs.getString(1));
			  Atimer5end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer6begin = toucsString.unConvert(rs.getString(1));
			  Atimer6end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer7begin = toucsString.unConvert(rs.getString(1));
			  Atimer7end = toucsString.unConvert(rs.getString(2));
			  rs.next();
			  Atimer8begin = toucsString.unConvert(rs.getString(1));
			  Atimer8end = toucsString.unConvert(rs.getString(2));	
			  Debug.println("it is a special day");
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  sq.close();
		  }
	  }

	  public void setAtmTimerConfig() throws MonitorException {
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  String sql;
			  PreparedStatement stm;
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //校验设备是否存在并且在操作员所属机构范围内
			  //删除设备基本信息
			  sql = "DELETE FROM kernel_phase";
			  stm = sq.conn.prepareStatement(sql);
			  stm.executeUpdate();
			  //sq.commit();
			  sql ="Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer1begin);
			  stm.setString(3, Atimer1end);
			  stm.setString(4, "1");
			  stm.executeUpdate();
			  //sq.commit();
			  sql ="Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer2begin);
			  stm.setString(3, Atimer2end);
			  stm.setString(4, "2");
			  stm.executeUpdate();
			  //sq.commit();
			  sql ="Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer3begin);
			  stm.setString(3, Atimer3end);
			  stm.setString(4, "3");
			  stm.executeUpdate();
			  //sq.commit();
			  sql = "Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer4begin);
			  stm.setString(3, Atimer4end);
			  stm.setString(4, "4");
			  stm.executeUpdate();
			  //sq.commit()
			  sql = "Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer5begin);
			  stm.setString(3, Atimer5end);
			  stm.setString(4, "5");
			  stm.executeUpdate();
			  //sq.commit();
			  sql ="Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer6begin);
			  stm.setString(3, Atimer6end);
			  stm.setString(4, "6");
			  stm.executeUpdate();
			  //sq.commit();
			  sql = "Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer7begin);
			  stm.setString(3, Atimer7end);
			  stm.setString(4, "7");
			  stm.executeUpdate();
			  //sq.commit();
			  
			  sql ="Insert INto kernel_phase(sys_id,start_time,end_time,phase) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, "atm");
			  stm.setString(2, Atimer8begin);
			  stm.setString(3, Atimer8end);
			  stm.setString(4, "8");
			  stm.executeUpdate();
			  sq.commit();

		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  public void getTheAllPower() throws MonitorException {
		  byte[] tempbyte = new byte[4];
		  String tempStr;
		  byte[] tempAllPower = new byte[64];
		  char[] tempchar = new char[16];
		  for (int i = 0; i < 64; i++) {
			  tempAllPower[i] = '0';
		  }
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT A.txn_name,A.location FROM kernel_txn_code A where A.sys_id='atm'");
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  rs = sq.queryselect(SqlStr.toString());
			  while (rs != null && rs.next()) {
				  tempAllPower[java.lang.Integer.valueOf(rs.getString(2)).intValue()] = '1';
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  sq.close();
		  }
 
		  for (int i = 0; i < 16; i++) {
			  tempStr = new String(tempAllPower, i * 4, 4);
			  tempbyte = tempStr.getBytes();
			  bytetochar(tempbyte);
			  tempchar[i] = globechar;
		  }
		  TheAllATMPower = String.valueOf(tempchar);
		  Debug.println("GGGGGGGGGGGG" + TheAllATMPower);
	  }

	  public void setRealAtmPower() throws MonitorException {
		  getTheAllPower();
		  if (Atmtimer1Power != null) {
			  setAtmPower(Atmtimer1Power);
			  Debug.println("~~~~~~~~~~~~~" + tStr);
			  Atimer1Pow = tStr;
		  } else {
			  Atimer1Pow = TheAllATMPower;
		  }
		  if (Atmtimer2Power != null) {
			  setAtmPower(Atmtimer2Power);
			  Atimer2Pow = tStr;
		  } else {
			  Atimer2Pow = TheAllATMPower;
		  }	
		  if (Atmtimer3Power != null) {
			  setAtmPower(Atmtimer3Power);
			  Atimer3Pow = tStr;
		  } else {
			  Atimer3Pow = TheAllATMPower;
		  }
		  if (Atmtimer4Power != null) {
			  setAtmPower(Atmtimer4Power);
			  Atimer4Pow = tStr;
		  } else {
			  Atimer4Pow = TheAllATMPower;
		  }
		  if (Atmtimer5Power != null) {
			  setAtmPower(Atmtimer5Power);
			  Atimer5Pow = tStr;
		  } else {
			  Atimer5Pow = TheAllATMPower;
		  }
		  if (Atmtimer6Power != null) {
			  setAtmPower(Atmtimer6Power);
			  Atimer6Pow = tStr;
		  } else {
			  Atimer6Pow = TheAllATMPower;
		  }
		  if (Atmtimer7Power != null) {
			  setAtmPower(Atmtimer7Power);
			  Atimer7Pow = tStr;
		  } else {
			  Atimer7Pow = TheAllATMPower;
		  }
		  if (Atmtimer8Power != null) {
			  setAtmPower(Atmtimer8Power);
			  Atimer8Pow = tStr;
		  } else {
			  Atimer8Pow = TheAllATMPower;
		  }
		  SqlAccess sq = SqlAccess.createConn();
		  SqlAccess sqtemp = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  sqtemp.setAutoCommit(false);
			  ResultSet rs = null;
			  //校验设备是否存在并且在操作员所属机构范围内
			  //删除设备基本信息
			  String sql = new String();
			  PreparedStatement stm = null;
 
			  Debug.println("Niu");
			  Debug.println(Atmid[0]);

			  if (istypechecked[0].equals("0")) {
				  sql = "DELETE FROM kernel_auth WHERE equip_code =?";
				  stm = sq.conn.prepareStatement(sql);
				  stm.setString(1, Atmid[0].toString());
				  stm.executeUpdate();
				  sq.commit();
				  Debug.println(TotalDayChecked);
				  sql = "Insert INto kernel_auth(sys_id, equip_code, phase1, phase2, phase3, phase4, phase5, phase6, phase7, phase8) Values(?,?,?,?,?,?,?,?,?,?)";
				  stm = sq.conn.prepareStatement(sql);
				  stm.setString(1, "atm");
				  stm.setString(2, Atmid[0]);
				  stm.setString(3, Atimer1Pow);
				  stm.setString(4, Atimer2Pow);
				  stm.setString(5, Atimer3Pow);
				  stm.setString(6, Atimer4Pow);
				  stm.setString(7, Atimer5Pow);
				  stm.setString(8, Atimer6Pow);
				  stm.setString(9, Atimer7Pow);
				  stm.setString(10, Atimer8Pow);
				  stm.executeUpdate();
				  sq.commit();
			  } else {
				  sql ="select A.atm_code from atm_info A where A.atm_addr_type matches '" + AtmType[0] + "' ";
				  rs = sqtemp.queryselect(sql);
				  while (rs != null && rs.next()) {
					  Debug.println(rs.getString(1));
					  sql = "DELETE FROM kernel_auth WHERE equip_code =?";
					  stm = sq.conn.prepareStatement(sql);
					  stm.setString(1, rs.getString(1));
					  stm.executeUpdate();
					  sq.commit();

					  sql = "Insert INto kernel_auth(sys_id, equip_code, phase1, phase2, phase3, phase4, phase5, phase6, phase7, phase8) Values(?,?,?,?,?,?,?,?,?,?)";
					  stm = sq.conn.prepareStatement(sql);
					  stm.setString(1, "atm");
					  stm.setString(2, rs.getString(1));
					  stm.setString(3, Atimer1Pow);
					  stm.setString(4, Atimer2Pow);
					  stm.setString(5, Atimer3Pow);
					  stm.setString(6, Atimer4Pow);
					  stm.setString(7, Atimer5Pow);
					  stm.setString(8, Atimer6Pow);
					  stm.setString(9, Atimer7Pow);
					  stm.setString(10, Atimer8Pow);
					  stm.executeUpdate();
					  sq.commit();
				  }
			  }
		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
			  sqtemp.close();
		  }

		  Debug.println(Atimer8Pow);
		  Debug.println(Atimer7Pow);
		  Debug.println(Atimer5Pow);
		  Debug.println(Atimer1Pow);
	  }

	  public void getPowDetail(String equipid) throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
 
		  SqlStr.append("SELECT * FROM kernel_auth A where A.equip_code matches '");
		  SqlStr.append(equipid + "'");
		  Debug.println("Gooooood" + equipid);
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  rs = sq.queryselect(SqlStr.toString());
			  ReturnAtimer1Pow = null;
			  ReturnAtimer2Pow = null;
			  ReturnAtimer3Pow = null;
			  ReturnAtimer4Pow = null;
			  ReturnAtimer5Pow = null;
			  ReturnAtimer6Pow = null;
			  ReturnAtimer7Pow = null;
			  ReturnAtimer8Pow = null;

			  if (rs != null && rs.next()) {
				  Vector ret;
				  ReturnAtimer1Pow = rs.getString(3);
				  ret = Stringtobyte(ReturnAtimer1Pow);
				  PowAtm1 = (Vector)ret.get(0);
				  UnSelecPow1 = (Vector)ret.get(1);
				  Debug.println("RRRRRRRRRRRRRRRRRRR[" + PowAtm1 + "][" + UnSelecPow1 + "]");

				  ReturnAtimer2Pow = rs.getString(4);
				  ret = Stringtobyte(ReturnAtimer2Pow);
				  PowAtm2 = (Vector)ret.get(0);
				  UnSelecPow2 = (Vector)ret.get(1);

				  ReturnAtimer3Pow = rs.getString(5);
				  ret = Stringtobyte(ReturnAtimer3Pow);
				  PowAtm3 = (Vector)ret.get(0);
				  UnSelecPow3 = (Vector)ret.get(1);

				  ReturnAtimer4Pow = rs.getString(6);
				  ret = Stringtobyte(ReturnAtimer4Pow);
				  PowAtm4 = (Vector)ret.get(0);
				  UnSelecPow4 = (Vector)ret.get(1);

				  ReturnAtimer5Pow = rs.getString(7);
				  ret = Stringtobyte(ReturnAtimer5Pow);
				  PowAtm5 = (Vector)ret.get(0);
				  UnSelecPow5 = (Vector)ret.get(1);

				  ReturnAtimer6Pow = rs.getString(8);
				  ret = Stringtobyte(ReturnAtimer6Pow);
				  PowAtm6 = (Vector)ret.get(0);
				  UnSelecPow6 = (Vector)ret.get(1);

				  ReturnAtimer7Pow = rs.getString(9);
				  ret = Stringtobyte(ReturnAtimer7Pow);
				  PowAtm7 = (Vector)ret.get(0);
				  UnSelecPow7 = (Vector)ret.get(1);

				  ReturnAtimer8Pow = rs.getString(10);
				  ret = Stringtobyte(ReturnAtimer8Pow);
				  PowAtm8 = (Vector)ret.get(0);
				  UnSelecPow8 = (Vector)ret.get(1);
				  Debug.println("en....... it is a good Day");
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  sq.close();
		  }
	  }

	  public void getUnselectedPower() throws MonitorException {
		  Vector tempOV = new Vector();
		  char[] tempchs = new char[16];
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT A.txn_name,A.location FROM kernel_txn_code A where A.sys_id='atm'");
		  Hashtable RoleHT = null;

		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  rs = sq.queryselect(SqlStr.toString());
			  while (rs != null && rs.next()) {
				  RoleHT = new Hashtable();
				  for (int h = 0; h < rs.getMetaData().getColumnCount(); h++) {
					  RoleHT.put(rs.getMetaData().getColumnName(h + 1),toucsString.unConvert(rs.getString(h + 1)));
				  }
				  tempOV.add(RoleHT);
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  sq.close();
		  }

		  if (ReturnAtimer1Pow != null) {
			  UnSelecPow1 = new Vector(tempOV);
			  UnSelecPow2 = new Vector(tempOV);
			  UnSelecPow3 = new Vector(tempOV);
			  UnSelecPow4 = new Vector(tempOV);
			  UnSelecPow5 = new Vector(tempOV);
			  UnSelecPow6 = new Vector(tempOV);
			  UnSelecPow7 = new Vector(tempOV);
			  UnSelecPow8 = new Vector(tempOV);

			  for (int i = 0; i < 64; i++) {
				  UnselecPowAtm1[i] = '1';
				  UnselecPowAtm2[i] = '1';
				  UnselecPowAtm3[i] = '1';
				  UnselecPowAtm4[i] = '1';
				  UnselecPowAtm5[i] = '1';
				  UnselecPowAtm6[i] = '1';
				  UnselecPowAtm7[i] = '1';
				  UnselecPowAtm8[i] = '1';
			  }

			  tempchs = ReturnAtimer1Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm1[i] = '0';
				  }
			  }

			  Debug.println("Doooooo it");
			  Debug.println(ReturnAtimer1Pow);
			  Debug.println(ReturnAtimer2Pow);
			  int counts;
			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm1[i] == '0') {
					  counts++;
					  UnSelecPow1.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer2Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm2[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm2[i] == '0') {
					  counts++;
					  UnSelecPow2.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer3Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm3[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm3[i] == '0') {
					  counts++;
					  UnSelecPow3.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer4Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm4[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm4[i] == '0') {
					  counts++;
					  UnSelecPow4.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer5Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm5[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm5[i] == '0') {
					  counts++;
					  UnSelecPow5.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer6Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm6[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm6[i] == '0') {
					  counts++;
					  UnSelecPow6.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer7Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm7[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm7[i] == '0') {
					  counts++;
					  UnSelecPow7.removeElementAt(i - counts);
				  }
			  }

			  tempchs = ReturnAtimer8Pow.toCharArray();
			  for (int i = 0; i < 16; i++) {
				  chartobyte(tempchs[i], i);
			  }

			  for (int i = 0; i < 64; i++) {
				  if (Powcode[i] == '1') {
					  UnselecPowAtm8[i] = '0';
				  }
			  }

			  counts = 0;
			  for (int i = 0; i < 64; i++) {
				  if (UnselecPowAtm8[i] == '0') {
					  counts++;
					  UnSelecPow8.removeElementAt(i - counts);
				  }
			  }
		  }
	  }

	  public Vector Stringtobyte(String tempstr) throws MonitorException {
		  char[] tempchs = new char[16];
		  Debug.println("!!!!!!!!!!!!!!!!!!!!!!!" + tempstr)	;
		  tempchs = tempstr.toCharArray();
		  for (int i = 0; i < 16; i++) {
			  chartobyte(tempchs[i], i);
		  }
		  Vector retV = new Vector();
		  Vector PowVect = new Vector();
		  Vector unPowVect = new Vector();

		  SqlAccess sq = SqlAccess.createConn();
		  ResultSet rs = null;
		  Hashtable RoleHT = null;
		  String txnName, location;
		  String SqlStr = "SELECT txn_name, location FROM kernel_txn_code where sys_id='atm' order by location";
		  try {
			  rs = sq.queryselect(SqlStr);
			  while (rs.next()) {
				  RoleHT = new Hashtable();
				  txnName = toucsString.unConvert(rs.getString(1));
				  location = rs.getString(2);
				  RoleHT.put("txn_name", txnName);
				  RoleHT.put("location", location);
				  if (Powcode[Integer.parseInt(location)] == '1') {
					  PowVect.add(RoleHT);
				  } else {
					  unPowVect.add(RoleHT);
				  }
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  sq.close();
		  }
		  retV.add(PowVect);
		  retV.add(unPowVect);
		  return retV;
	  }

	  public void setAtmPower(String[] InputPower) {
		  byte[] code = new byte[64];
		  char[] resultchars = new char[16];
		  byte[] tempbyte = new byte[4];
		  for (int i = 0; i < 64; i++) {
			  code[i] = '0';
		  }
		  Integer tempInt;
		  String tempStr;
		  int tempi;
		  int i;

		  Vector resV = new Vector();
		  if (InputPower != null) {
			  for (i = 0; i < InputPower.length; i++) {
				  resV.add(InputPower[i]);
				  tempInt = java.lang.Integer.decode(resV.get(i).toString());
				  tempi = tempInt.intValue();
				  Debug.print(tempi);
				  code[tempi] = '1';
			  }
		  }
 
		  for (i = 0; i < 16; i++) {
			  tempStr = new String(code, i * 4, 4);
			  tempbyte = tempStr.getBytes();
			  bytetochar(tempbyte);
			  resultchars[i] = globechar;
		  }
		  tStr = String.valueOf(resultchars);
	  }

	  public void chartobyte(char ch, int position) {
		  if (ch == '0') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == '1') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == '2') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == '3') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == '4') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == '5') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == '6') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == '7') {
			  Powcode[4 * position] = '0';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == '8') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == '9') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == 'a') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == 'b') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '0';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '1';
		  }
	
		  if (ch == 'c') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == 'd') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '0';
			  Powcode[4 * position + 3] = '1';
		  }
		  if (ch == 'e') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '0';
		  }
		  if (ch == 'f') {
			  Powcode[4 * position] = '1';
			  Powcode[4 * position + 1] = '1';
			  Powcode[4 * position + 2] = '1';
			  Powcode[4 * position + 3] = '1';
		  }
	  }

	  public static void bytetochar1(byte[] ttempbyte) {
		  int tempi = 0;
		  char tempchar = '0';
		  int len = ttempbyte.length;
		  for (int i = 0; i < len; i++) {
			  if (ttempbyte[i] == '1') {
				  if (i == 0) {
					  tempi = tempi + 1;
				  }
				  if (i == 1) {
					  tempi = tempi + 2;
				  }
				  if (i == 2) {
					  tempi = tempi + 4;
				  }
				  if (i == 3) {
					  tempi = tempi + 8;
				  }
			  }
		  }

		  if (tempi == 0) {
			  tempchar = '0';
		  }
		  if (tempi == 1) {
			  tempchar = '1';
		  }
		  if (tempi == 2) {
			  tempchar = '2';
		  }
		  if (tempi == 3) {
			  tempchar = '3';
		  }
		  if (tempi == 4) {
			  tempchar = '4';
		  }
		  if (tempi == 5) {
			  tempchar = '5';
		  }
		  if (tempi == 6) {
			  tempchar = '6';
		  }
		  if (tempi == 7) {
			  tempchar = '7';
		  }
		  if (tempi == 8) {
			  tempchar = '8';
		  }
		  if (tempi == 9) {
			  tempchar = '9';
		  }
		  if (tempi == 10) {
			  tempchar = 'a';
		  }
		  if (tempi == 11) {
			  tempchar = 'b';
		  }
		  if (tempi == 12) {
			  tempchar = 'c';
		  }
		  if (tempi == 13) {
			  tempchar = 'd';
		  }
		  if (tempi == 14) {
			  tempchar = 'e';
		  }
		  if (tempi == 15) {
			  tempchar = 'f';
		  }
		  globechar = tempchar;
		  String.valueOf(tempchar);
	  }

	  public static void bytetochar(byte[] ttempbyte) {
		  int tempi = 0; 
		  char tempchar = '0';
		  int len = ttempbyte.length;
		  for (int i = 0; i < len; i++) {
			  if (ttempbyte[i] == '1') {
				  if (i == 0) {
					  tempi = tempi + 8;
				  }
				  if (i == 1) {
					  tempi = tempi + 4;
				  }
				  if (i == 2) {
					  tempi = tempi + 2;
				  }
				  if (i == 3) {
					  tempi = tempi + 1;
				  }
			  }
		  }

		  if (tempi == 0) {
			  tempchar = '0';
		  }
		  if (tempi == 1) {
			  tempchar = '1';
		  }
		  if (tempi == 2) {
			  tempchar = '2';
		  }
		  if (tempi == 3) {
			  tempchar = '3';
		  }
		  if (tempi == 4) {
			  tempchar = '4';
		  }
		  if (tempi == 5) {
			  tempchar = '5';
		  }
		  if (tempi == 6) {
			  tempchar = '6';
		  }
		  if (tempi == 7) {
			  tempchar = '7';
		  }
		  if (tempi == 8) {
			  tempchar = '8';
		  }
		  if (tempi == 9) {
			  tempchar = '9';
		  }
		  if (tempi == 10) {
			  tempchar = 'a';
		  }
		  if (tempi == 11) {
			  tempchar = 'b';
		  }
		  if (tempi == 12) {
			  tempchar = 'c';
		  }
		  if (tempi == 13) {
			  tempchar = 'd';
		  }
		  if (tempi == 14) {
			  tempchar = 'e';
		  }
		  if (tempi == 15) {
			  tempchar = 'f';
		  }
		  globechar = tempchar;
		  String.valueOf(tempchar);
	  }

	  public Vector getAllAtmcode() throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT atm_code FROM atm_info WHERE atm_code matches 'A*' order by atm_code");
		  Vector RoleVect = new Vector();
		  Hashtable RoleHT = null;
		  Debug.println(SqlStr.toString());
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

	  public Vector getAllAtmAddrType() throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT A.sys_code, A.code_desc FROM kernel_code A WHERE code_type='0370'");
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

	  public Vector getUnselectPower() throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  ResultSet rs = null;
		  SqlStr.append("SELECT A.location, A.txn_name FROM kernel_txn_code A WHERE A.sys_id='atm'");
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

	  public int InsertATMMap(String atmCode, int atmxposi, int atmyposi) throws MonitorException {
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //校验设备是否存在并且在操作员所属机构范围内
			  //删除设备基本信息
			  String sql = "DELETE FROM monit_atmposition WHERE atm_id=?";
			  PreparedStatement stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, atmCode);
			  int flag = stm.executeUpdate();

			  sql = "Insert INto monit_atmposition(atm_id, atm_point_x, atm_point_y, fix_flg) Values(?,?,?,?)";
			  stm = sq.conn.prepareStatement(sql);
			  stm.setString(1, atmCode);
			  stm.setInt(2, atmxposi);
			  stm.setInt(3, atmyposi);
			  stm.setInt(4, 1);
			  flag = stm.executeUpdate();
			  sq.commit();
			  return flag;
		  } catch (SQLException e1) {
			  sq.rollback();
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  /**
	   * 删除设备
	   * @param atmCode 设备编号
	   * @return 删除成功返回1
	   * @throws MonitorException 监控系统异常
	   **/
	  public int delDevice(String atmCode) throws MonitorException {
		  //向ATMP发起删除设备交易
		  //校验设备是否存在并且在操作员所属机构范围内
		  atmpProc(atmCode, CodeDefine.ATMPREQ_ATMDEL);
		  return 1;
	  }

	  public void SetAtmCode(String AtmId) {
		  AtmCode = AtmId;
	  }

	  /**
	   * 根据查询条件查询设备
	   * @param opt 查询选项
	   * @return 设备摘要信息列表
	   * @throws MonitorException 监控系统异常
	   **/
	  public Vector qryDevice(QueryOption opt) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  ResultSet rst = sq.queryselect(opt.genCondition());
			  Vector v = new Vector();
			  while (rst.next()) {
				  ATMInfo info = new ATMInfo();
				  info.setAtmCode(Util.getString(rst, "atm_code", ""));
				  info.setOrgId(Util.getString(rst, "org_id", ""));
				  info.setAtmType(Util.getString(rst, "atm_type", ""));
				  info.setUseFlag(Util.getString(rst, "use_flag", ""));
				  info.setAtmStat(Util.getString(rst, "atm_stat", ""));
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

	  public String getTheAgentId(String atmcode) throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer()	;
		  String tempstr = new String();	
		  SqlStr.append("SELECT A.agency_no FROM atm_info A WHERE A.atm_code MATCHES '");
		  SqlStr.append(atmcode + "'");
		  try {
			  Debug.println("ICanD");
			  Debug.println(atmcode);
			  conn1 = new SqlAccess();
			  rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			  if (rs != null && rs.next()) {
				  tempstr = toucsString.unConvert(rs.getString(1) == null ? " " : rs.getString(1));
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  } catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  conn1.close();
		  }
		  return tempstr;
	  }

	  public String getTheMapDetail(String Mapid) throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  String tempstr = new String();
		  SqlStr.append("SELECT A.right_down_x, A.right_down_y, A.scale_width, A.scale_length FROM monit_mapfile A WHERE A.map_file_id MATCHES '");
		  SqlStr.append(Mapid + "'");

		  try {
			  Debug.println("ICanDDDDDDDDDD");
			  Debug.println(Mapid);
			  conn1 = new SqlAccess();
			  rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			  if (rs != null && rs.next()) {
				  posi_x = rs.getInt(1);
				  posi_y = rs.getInt(2);
				  mapcx = rs.getInt(3);
				  mapcy = rs.getInt(4);
			  }
		  } catch (SQLException sqle) {
			  throw new MonitorException(sqle);
		  }catch (Exception ex) {
			  ex.printStackTrace();
			  throw new MonitorException(ErrorDefine.UNKNOWDBERR,ErrorDefine.UNKNOWDBERRDESC);
		  } finally {
			  conn1.close();
		  }
		  return tempstr;
	  }

	  public Vector queryATMDevice(String flag) throws MonitorException {
		  StringBuffer SqlStr = new StringBuffer();
		  SqlStr.append("SELECT A.atm_id FROM monit_atmposition A WHERE A.fix_flg ='");
		  SqlStr.append(flag + "' order by A.atm_id");
		  Vector RsVect = new Vector();
		  Hashtable orgHT = null;
		  try {
			  conn1 = new SqlAccess();
			  rs = conn1.queryselect(toucsString.Convert(SqlStr.toString()));
			  while (rs != null && rs.next()) {
				  orgHT = new Hashtable();
				  for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					  orgHT.put(rs.getMetaData().getColumnName(i + 1),
							    toucsString.unConvert(rs.getString(i + 1) == null ? " " : rs.getString(i + 1)));
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

	  /**
	   * 查询设备配置信息
	   * @param atmCode 设备编号
	   * @param orgId 当前用户所属机构
	   * @return 设备配置信息，如果设备不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	  public ATMInfo qryDevice(String atmCode, String orgId) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //校验设备是否存在并且在用户所属机构范围内
			  if (orgId != null && orgId.trim().length() > 0) {
				  if (!deviceInOrg(atmCode, orgId, sq)) {
					  return null;
				  }
			  }
			  //查询设备信息
			  String sql = "SELECT * FROM atm_info WHERE atm_code='" + atmCode + "'";
			  ATMInfo info = null;
			  ResultSet rst = sq.queryselect(sql);
			  if (rst.next()) {
				  info = new ATMInfo();
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
	   * 查询加密机信息
	   * @return 包含加密机信息的相量
	   * @throws MonitorException
	   */
	  public Vector queryEncryptInfo() throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  Vector encryptInfoV = new Vector();
		  try {
			  //查询设备信息
			  String sql = "SELECT * FROM atm_info WHERE atm_code MATCHES 'E*'";
			  ATMInfo info = null;
			  ResultSet rst = sq.queryselect(sql);
			  while (rst.next()) {
				  info = new ATMInfo();
				  info.fetchData(rst);
				  encryptInfoV.add(info);
			  }
			  rst.close();
		  }	 catch (Exception e) {
			  e.printStackTrace();
		  } finally {
			  sq.close();
		  }
		  return encryptInfoV;
	  }

	  /**
	   * 查询加密机信息
	   * @return 包含加密机信息的相量
	   * @throws MonitorException
	   */
	  public ATMInfo queryEncryptInfo(String encryCode) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn(); 
		  ATMInfo info = new ATMInfo();
		  try {
			  //查询设备信息
			  String sql = "SELECT * FROM atm_info WHERE atm_code = '" + encryCode + "'";
			  ResultSet rst = sq.queryselect(sql);
			  if (rst.next()) {
				  info.fetchData(rst);
			  }
			  rst.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally {
			  sq.close();
		  }
		  return info;
	  }

	  /**
	   * 新增加密机信息
	   * @return true：成功；false：失败
	   * @throws MonitorException
	   */
	  public boolean insertEncryptInfo(HttpServletRequest request, String mng_name) throws MonitorException {
		  boolean isSuccess = false;
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //查询设备信息
			  String sql = "INSERT INTO atm_info(atm_code,atm_name,atm_type,net_address,host_port,use_flag,atm_set_date,mng_name,mng_phone,run_stat,atm_sn,atm_tn,translate_name,msg_type,net_type,atm_cycle,card_type,currency_code,send_flag,org_id,teller_id,area_no) " +
			  			   "VALUES('" +
			  			   request.getParameter("encryCode") + "','" +
			  			   request.getParameter("encryName") + "','" +
			  			   request.getParameter("encryType") + "','" +
			  			   request.getParameter("encryIp") + "','" +
			  			   request.getParameter("encryPort") + "','" + "2','" +
			  			   Calendar.getInstance().get(Calendar.YEAR) +
			  			   Calendar.getInstance().get(Calendar.MONTH) +
			  			   Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "','" +
			  			   toucsString.unConvert(mng_name) + "','" +
			  			   request.getParameter("adminPhone") + "','" + "1','','','','','','','','','','','','')";
			  if (sq.queryupdate(sql) == 1) {
				  isSuccess = true;
			  }
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally {
			  sq.close();
		  }
		  return isSuccess;
	  }

	  /**
	   * 新增加密机信息
	   * @return true：成功；false：失败
	   * @throws MonitorException
	   */
	  public boolean modifyEncryptInfo(HttpServletRequest request, String mng_name) throws MonitorException {
		  boolean isSuccess = false;
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //查询设备信息
			  String sql = "UPDATE atm_info SET (atm_name,atm_type,net_address,host_port,mng_name,mng_phone) = " + "('" +
			  request.getParameter("encryName") + "','" +
			  request.getParameter("encryType") + "','" +
			  request.getParameter("encryIp") + "','" +
			  request.getParameter("encryPort") + "','" +
			  toucsString.Convert(mng_name) + "','" +
			  request.getParameter("adminPhone") + "') WHERE atm_code = '" + request.getParameter("encryCode") + "'";
			  if (sq.queryupdate(sql) == 1) {
				  isSuccess = true;
			  }
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally {
			  sq.close();
		  }
		  return isSuccess;
	  }

	  /**
	   * 删除加密机信息
	   * @return true 成功；false：失败
	   * @throws MonitorException
	   */
	  public boolean deleteEncryptInfo(String ATMCode) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  boolean isSuccess = false;
		  try {
			  //查询设备信息
			  String sql = "DELETE FROM atm_info WHERE atm_code = '" + ATMCode + "'";
			  if (sq.queryupdate(sql) == 1) {
				  isSuccess = true;
			  }
		  } catch (SQLException e1) {
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
		  return isSuccess;
	  }

	  /**
	   * 停用加密机
	   * @return true 成功；false：失败
	   * @throws MonitorException
	   */
	  public boolean stopEncrypt(String ATMCode) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  boolean isSuccess = false;
		  try {
			  //查询设备信息
			  String sql = "UPDATE atm_info SET use_flag = '2' WHERE atm_code = '" + ATMCode + "'";
			  if (sq.queryupdate(sql) == 1) {
				  isSuccess = true;
			  }
		  } catch (SQLException e1) {
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
		  return isSuccess;
	  }

	  /**
	   * ATM设备投产（加载）
	   * @param atmCode 设备编号
	   * @return 加载成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int loadEncrypt(String encryCode) throws MonitorException {
		  //向ATMP发起加载设备交易
		  atmpProc(encryCode, CodeDefine.ATMPREQ_ENCRYLOAD);	
		  return 1;
	  }
	
	  /**
	   * 查询设备信息
	   * @param atmCode 设备编号
	   * @param orgId 机构编号
	   * @return 设备信息（包括基本信息、钞箱配置与状态信息）
	   * @throws MonitorException
	   */
	  public ATMInfo qryDeviceWithStat(String atmCode, String orgId) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //校验设备是否存在并且在操作员所属机构范围内
			  if (orgId != null && orgId.trim().length() > 0) {
				  if (!deviceInOrg(atmCode, orgId, sq)) {
					  return null;
				  }
			  }
			  //查询基本信息、钞箱配置信息、设备状态信息
			  String sql =
				  "SELECT * FROM atm_info,atm_box_config,atm_stat WHERE atm_info.atm_code='"
				  + atmCode + "' AND atm_info.atm_code=atm_box_config.atm_code"
				  + " AND atm_info.atm_code=atm_stat.atm_code";
			  ATMInfo info = null;
			  ResultSet rst = sq.queryselect(sql);
			  if (rst.next()) {
				  info = new ATMInfo();
				  info.fetchData(rst);
				  AtmBoxInfo boxInfo = new AtmBoxInfo(atmCode);
				  boxInfo.fetchStateData(rst);
				  info.setBoxInfo(boxInfo);
				  ATMStatInfo statInfo = new ATMStatInfo();
				  statInfo.fetchData(rst);
				  info.setStatInfo(statInfo);
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
	   * 查询设备状态
	   * @param atmCode 设备编号
	   * @return 设备状态信息
	   * @throws MonitorException 监控系统异常
	   */
	  public ATMStatInfo qryDeviceStat(String atmCode, String orgId) throws MonitorException {	
		  //取数据库连接		
		  SqlAccess sq = SqlAccess.createConn(JNDINames.MONITOR_DATASOURCE);
		  try {
			  //校验设备是否存在并且在操作员所属机构范围内
			  if (orgId != null && orgId.trim().length() > 0) {
				  if (!deviceInOrg(atmCode, orgId, sq)) {
					  return null;
				  }
			  }
			  //查询设备状态信息
			  String sql = "SELECT * FROM atm_stat WHERE atm_code='" + atmCode + "'";
			  ResultSet rst = sq.queryselect(sql);
			  ATMStatInfo statInfo = null;
			  if (rst.next()) {
				  statInfo = new ATMStatInfo();
				  statInfo.atmCode = atmCode;
				  statInfo.fetchData(rst);
			  }
			  rst.close();
			  return statInfo;
		  } catch (SQLException e1) {
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  /**
	   * 查询密钥配置信息
	   * @param atmCode 设备编号
	   * @param orgId 机构编号
	   * @return 密钥配置信息
	   * @throws MonitorException
	   */
	  public AtmKeyInfo qryKeyInfo(String atmCode, String orgId) throws MonitorException {	
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //校验设备是否存在并且在操作员所属机构范围内
			  if (orgId != null && orgId.trim().length() > 0) {
				  if (!deviceInOrg(atmCode, orgId, sq)) {
					  return null;
				  }
			  }
			  String sql = "SELECT * FROM kernel_key_info WHERE sender_agency='" + atmCode + "'";
			  ResultSet rst = sq.queryselect(sql);
			  AtmKeyInfo keyInfo = null;
			  if (rst.next()) {
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
	   * 查询钞箱配置信息
	   * @param atmCode 设备编号
	   * @param orgId 机构编号
	   * @return 钞箱配置信息
	   * @throws MonitorException
	   */
	  public AtmBoxInfo qryBoxConfig(String atmCode, String orgId) throws MonitorException {
		  //取数据库连接
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //校验设备是否存在并且在操作员所属机构范围内
			  if (orgId != null && orgId.trim().length() > 0) {
				  if (!deviceInOrg(atmCode, orgId, sq)) {
					  return null;
				  }	
			  }
			  String sql = "SELECT * FROM atm_box_config WHERE atm_code='" + atmCode + "'";
			  ResultSet rst = sq.queryselect(sql);
			  AtmBoxInfo boxInfo = null;
			  if (rst.next()) {
				  boxInfo = new AtmBoxInfo();
				  boxInfo.setAtmCode(atmCode);
				  boxInfo.fetchConfigData(rst);
			  }
			  rst.close();
			  return boxInfo;
		  } catch (SQLException e1) {
			  throw new MonitorException(e1);
		  } finally {
			  sq.close();
		  }
	  }

	  /**
	   * ATM设备投产（加载）
	   * @param atmCode 设备编号
	   * @return 加载成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int loadDevice(String atmCode) throws MonitorException {
		  //先校验是否符合加载条件
		  SqlAccess sq = SqlAccess.createConn();
		  try {
			  //设置数据库连接提交方式为非自动提交
			  sq.setAutoCommit(false);
			  //校验设备是否存在并且在操作员所属机构范围内
			  //deviceInOrg(amtCode,orgId,sq);
			  //校验设备是否满足加载条件
			  loadEnabled(atmCode, sq);
			  //修改加载标志
			  setUseFlag(atmCode, sq, "0");
			  sq.commit();
		  } catch (SQLException exp) {
			  throw new MonitorException(exp);
		  } finally {
			  sq.close();
		  }
		  //向ATMP发起加载设备交易
		  atmpProc(atmCode, CodeDefine.ATMPREQ_ATMLOAD);
		  return 1;
	  }

	  /**
	   * 加载、删除设备交易
	   * @param atmCode 设备编号
	   * @param tranCode 交易码（加载、删除）
	   * @throws MonitorException 监控系统异常
	   */
	  private void atmpProc(String atmCode, String tranCode) throws MonitorException { 
		  Debug.println("-----------------------tranCode:" + tranCode + "---");
		  Debug.println("-----------------------atmCode:" + atmCode + "---");
		  //创建通讯对象
		  CommToATMP commAtmp = new CommToATMP(tranCode, atmCode, "000000000");
		  //与ATMP通讯，取得处理结果
		  String procCode = commAtmp.commitToATMP();
		  //--test
		  Debug.println("-----------------------recCode:" + procCode);
		  //--test
		  //分析处理结果
		  if (!procCode.equals(CodeDefine.ATMPCODE_SUCCESS)) {
			  throw new MonitorException(procCode, commAtmp.getErrorDesc());
		  }
	  }

	  /**
	   * 校验设备是否满足加载条件
	   * @param atmCode 设备编号
	   * @param sq 数据库连接对象
	   * @throws MonitorException 如果不满足加载条件，抛出系统异常
	   */
	  private void loadEnabled(String atmCode, SqlAccess sq) throws MonitorException {
		  AtmKeyInfo keyInfo = null;
		  AtmBoxInfo boxInfo = null;
		  try {
			  //校验密钥配置是否满足加载条件
			  keyInfo = new AtmKeyInfo(atmCode);
			  if (!keyInfo.loadEnabled(sq)) {
				  throw new MonitorException(ErrorDefine.KEY_ERROR, "密钥未配置或信息不完整");
			  }
			  //校验钞箱配置是否满足加载条件
			  boxInfo = new AtmBoxInfo(atmCode);
			  if (!boxInfo.loadEnabled(sq)) {
				  throw new MonitorException(ErrorDefine.BOX_ERROR, "钞箱未配置或信息不完整");
			  }
		  } catch (SQLException exp) {
			  throw new MonitorException(exp);
		  }
	  }

	  /**
	   * 校验设备是否属于某个机构
	   * @param atmCode 设备编号
	   * @param orgId 机构编号
	   * @param sq 数据库访问对象
	   * @return 属于该机构返回true，否则返回false
	   * @throws MonitorException
	   */
	  private boolean deviceInOrg(String atmCode, String orgId, SqlAccess sq) throws MonitorException {
		  ATMDeviceManageBean dvManage = new ATMDeviceManageBean();
		  dvManage.deviceInOrg(atmCode, orgId, sq);
		  return true;
	  }

	  /**
	   * 设置加载标志
	   * @param atmCode 设备编号
	   * @param sq 数据库访问对象
	   * @param flag 加载标志
	   * @return 设置成功返回1
	   * @throws SQLException
	   */
	  private int setUseFlag(String atmCode, SqlAccess sq, String flag) throws SQLException {
		  String sql = "UPDATE atm_info SET use_flag='" + flag + "' WHERE atm_code='" + atmCode + "'";
		  return sq.queryupdate(sql);
	  }
	
	  /**
	   * 设置加载标志(与当前加载标志比较)
	   * @param atmCode 设备编号
	   * @param sq 数据库访问对象
	   * @param flag 加载标志
	   * @return 设置成功返回1
	   * @throws SQLException
	   */
	  private int setUseFlagWichCompare(String atmCode, SqlAccess sq, String flag) throws SQLException {
		  //取当前加载标志
		  String sql = "SELECT use_flag FROM atm_info WHERE atm_code='" + atmCode + "'";
		  String cflag = "";
		  ResultSet rst = sq.queryselect(sql);
		  if (rst.next()) {
			  cflag = Util.getString(rst, "use_flag", "");
		  } else {
			  return 0;
		  }
		  //如果当前加载标志为"2",不做处理
		  if (cflag.equals("2")) {
			  return 1;
		  }	
		  //修改加载标志
		  return setUseFlag(atmCode, sq, flag);
	  }

	  private int setDeviceUseFlagWichCompare(String atmCode, SqlAccess sq, boolean isChange) throws SQLException {
		  //取当前加载标志
		  String sql = "SELECT use_flag FROM atm_info WHERE atm_code='" + atmCode + "'";
		  String cflag = "";
		  ResultSet rst = sq.queryselect(sql);
		  if (rst.next()) {
			  cflag = Util.getString(rst, "use_flag", "");
		  } else {
			  return 0;
		  }
		  //如果当前加载标志为"2",不做处理
		  if (cflag.equals("2")) {
			  return 1;
		  }
		  //如果*号提示未修改，不做处理
		  if (!isChange) {
			  return 1;
		  }
		  //修改加载标志
		  return setUseFlag(atmCode, sq, "0");
	  }

	  /**
	   * 设备故障报停登记
	   * @param atmCode 设备编号
	   * @return 报停成功返回1
	   * @throws MonitorException 监控系统异常
	   */
	  public int regFault(String atmCode) throws MonitorException {
		  return 1;
	  }

	  public static void main(String[] args) {
	  }
}
