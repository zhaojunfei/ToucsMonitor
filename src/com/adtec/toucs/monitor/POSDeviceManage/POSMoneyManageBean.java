package com.adtec.toucs.monitor.POSDeviceManage;



import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.ErrorDefine;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.SqlAccess;
import com.adtec.toucs.monitor.common.toucsString;


/**
 * <p>Title:理财pos设备管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTec</p>
 * @author syl
 * @version 2.0
 */
public class POSMoneyManageBean {

	public POSMoneyManageBean(){
		
	}
	
//==================================UPDATE   START=====================================================//	
	  /**
	   * 修改理财POS设备信息
	   * @param POSMoneyInfo 设备信息
	   * @return 登记成功返回1
	   * @throws MonitorException 系统异常
	   */
	public int updatePosInfo(POSMoneyInfo info, String key) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//非自动提交
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();      
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改理财POS设备信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
	
//=========================================== END ==========================================================//	  
	  
//==================================INSERT   START==========================================================//	  
	  /**
	   * 增加新的理财pos设备信息
	   * @param POSMoneyInfo 设备信息
	   * @param Poscount pos机数
	   * @return 登记成功返回1
	   * @throws MonitorException 系统异常
	   */
	public int regPosDevice(POSMoneyInfo info, int Poscount) throws MonitorException {
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
				//事务回滚
				//清除掉已插入表中的初始化信息
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "新增理财POS设备信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			sq.close();
			throw new MonitorException(e1);
		} finally {
			if (sq != null) {
				if (sq.isConnectDB()) {
					sq.close();
				}
			}
		}
	}	
//=============================================END=============================================================//	  
	 
//==========================================START  DELETE======================================================//	  
	  /**
	   * 删除理财POS设备信息
	   * @param posCode pos编号
	   * @return 删除成功返回1
	   * @throws MonitorException 系统异常
	   */
	public int deletePosInfo(String posCode) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();    
		POSMoneyInfo info = new POSMoneyInfo();
		try {	    	
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//删除POS设备基本信息
			PreparedStatement stm = info.makeDeleteStm(sq.conn, posCode);
			int flag = stm.executeUpdate();    	
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();	//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除理财POS设备信息失败！");
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
	   * 批量删除理财POS设备信息
	   * @param str 理财pos设备编号数组
	   * @return 删除成功返回1
	   * @throws MonitorException 系统异常
	   */
	public int deletePosInfos(String[] str) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		StringBuffer strbf = new StringBuffer();
		for(int i=0;i<str.length;i++){
			strbf.append("'").append(str[i]).append("',");
		}
		try {
			//设置数据库连接提交方式为非自动提交
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//删除POS设备基本信息(多条)
			String sql="DELETE FROM pos_enrol WHERE pos_code IN("+strbf.substring(0,strbf.length()-1)+")";
			int flag = sq.queryupdate(sql);    	
			if (flag >= 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "删除理财POS设备信息失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
//===========================================END=================================================================//	  
	    
//=============================================START  STATE=====================================================//	  
	  /**
	   * 设置理财POS设备信息启用、停用标志
	   * @param pos_code pos编号
	   * @param State 理财pos设备启停标识
	   * @return 登记成功返回1
	   * @throws MonitorException 系统异常
	   */
	public int setStatPos(String pos_code, boolean State) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			int flag = 0;    	
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//提交
			} else {
				sq.rollback();//事务回滚
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "修改理财POS设备状态失败！");
			}
			return flag;
		} catch (SQLException e1) {
			sq.rollback();
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}  
//===================================END============================================================================//	  
	   
//==================================SELECT MERCHANT INFO==START=======================================================//	  
	  /**
	   * 查询理财POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param code_type 商户类型代码
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 系统异常
	   */
	public POSMoneyInfo qryPosInfo(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { 
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { 
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { 
				sql = "SELECT pos_code,pos_c_code,pos_dcc_code,merchant_id FROM pos_info WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			POSMoneyInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSMoneyInfo();
				info.setPos_code(toucsString.unConvert(rst.getString("pos_code")));
				info.setPos_c_code(toucsString.unConvert(rst.getString("pos_c_code")));
				info.setPos_dcc_code(toucsString.unConvert(rst.getString("pos_dcc_code")));
				info.setMerchant_id(toucsString.unConvert(rst.getString("merchant_id")));
			}
			rst.close();
			return info;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}  
//==================================END=========================================================================//	  
	    
//===============================SELECT POS INFO==START==========================================================//	  
	  /**
	   * 查询理财POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param code_type 商户设备类型
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public POSMoneyInfo qryPosMoney(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			POSMoneyInfo info = null;
			ResultSet rst = sq.queryselect(sql);
			if (rst != null && rst.next()) {
				info = new POSMoneyInfo();
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
//===================================END=======================================================================//	  
	  
//================================SELECT POS2 INFO==START=========================================================//	  
	  /**
	   * 查询POS设备基本配置信息
	   * @param posCode 设备编号
	   * @param code_type 设备类型
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public Vector qryPosInfoByPoscode(String posCode, String code_type) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		try {
			//查询设备信息
			String sql;
			if (code_type == "" || code_type == null || code_type.trim().equals("1")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_code='" + posCode + "'";
			} else if (code_type.trim().equals("2")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_c_code='" + posCode + "'";
			} else if (code_type.trim().equals("3")) { 
				sql = "SELECT * FROM pos_enrol WHERE pos_dcc_code='" + posCode + "'";
			} else {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
			POSMoneyInfo posMoney = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posMoney = new POSMoneyInfo();
				posMoney.fetchData(rst);
				v.add(posMoney);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}  
//=================================================END============================================================//	  
	  
//===================================SELECT MERCHANT INFO==START==================================================//	  
	  /**
	   * 查询理财pos设备基本配置信息
	   * @param merchantNo 商户编号
	   * @param branchId 当前用户所属机构
	   * @return 商户信息，如果商户不存在，返回null
	   * @throws MonitorException 监控系统异常
	   */
	public Vector qryMctPosInfo(String merchantNo ,String branchId) throws MonitorException {
		//取数据库连接
		SqlAccess sq = SqlAccess.createConn();
		String sql ="";
		try {
			//查询设备信息
			if(merchantNo != null && branchId !=null){
				sql = "SELECT * FROM pos_enrol WHERE merchant_id = '"+merchantNo+"' AND branch_id = '"+branchId+"'";
			}else if(merchantNo == null && branchId != null){
				sql = "SELECT * FROM pos_enrol WHERE branch_id = '"+branchId+"'";
			}else if(merchantNo != null && branchId ==null){
				sql = "SELECT * FROM pos_enrol WHERE merchant_id = '"+merchantNo+"'";
			}else{
				sql = "SELECT * FROM pos_enrol";
			}    	
			POSMoneyInfo posMoney = null;
			Vector v = null;
			ResultSet rst = sq.queryselect(sql);
			v = new Vector();
			while (rst != null && rst.next()) {
				posMoney = new POSMoneyInfo();
				posMoney.fetchData(rst);
				v.add(posMoney);
			}
			rst.close();
			return v;
		} catch (SQLException e1) {
			throw new MonitorException(e1);
		} finally {
			sq.close();
		}
	}
//================================END=====================================================================//	  
	  
//================================POS STATE==START==========================================================//	  
	  /**
	   * 理财POS设备管理，包括启用和停用。
	   * @param PosID POS设备编号  
	   * @param UseFalg 标志"1"-启用"0"-停用
	   * @throws MonitorException
	   */
	public int managePosInfo(String PosID, String UseFlag ) throws MonitorException {
		SqlAccess conn = SqlAccess.createConn();
		try {
			if (PosID == null || PosID.trim().equals("")) {
				throw new MonitorException(ErrorDefine.NOTHISDEVICE,ErrorDefine.NOTHISDEVICEDESC);
			}
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
//=======================================END==========================================================================//	  

//=========================================UPLOAD START===============================================================//	  
	/**
	 * 理财pos读取上传的Excel文件
	 * path 文件所在路径
	 * return list
	 * 20121129
	 */
	public List recJXL(String path){
		Workbook book;			
		List list = new ArrayList();
		try {
			book = Workbook.getWorkbook(new File(path));
			//  获得第一个工作表对象 
			Sheet sheet  =  book.getSheet( 0 );
			//  得到第一列第一行的单元格 
			int  columnum  =  sheet.getColumns(); //  得到列数 
			int  rownum  =  sheet.getRows(); //  得到行数 
			System.out.println(columnum);
			System.out.println(rownum);
			for  ( int  i  =   1 ; i  <  rownum; i ++ ) {
				POSMoneyInfo posMoney = new POSMoneyInfo();
				//判断第一列第I行的值是否为空
				if(sheet.getCell(0, i).getContents().trim() != null && sheet.getCell(0, i).getContents().trim() != ""){	        		 
					posMoney.setPos_dcc_code(sheet.getCell(0, i).getContents());
					System.out.println(posMoney.getPos_dcc_code());
					posMoney.setBranch_id(sheet.getCell(1, i).getContents());
					posMoney.setTeller_id(sheet.getCell(2, i).getContents());
					posMoney.setStart_date(sheet.getCell(3, i).getContents());
					posMoney.setEnd_date(sheet.getCell(4, i).getContents());
					posMoney.setStart_time(sheet.getCell(5, i).getContents());
					posMoney.setEnd_time(sheet.getCell(6, i).getContents());
					posMoney.setMemo1(sheet.getCell(7, i).getContents());
					posMoney.setMemo2(sheet.getCell(8, i).getContents());
					posMoney.setMemo3(sheet.getCell(9, i).getContents());	        	 
					list.add(posMoney);
					System.out.println();
					System.out.println(list);
				}        	 
			} 
			book.close(); 
		} catch (BiffException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}  
//============================================END======================================================================	  
	    
//=========================================遍历集合 START================================================================  
	 /**
	  * 创建excel文件
	  * list 要添加的数据 
	  * path 文件路径
	  * 20121129
	  */
	public void createJXL(List list, String path){
		try {
			//指定路径生成文件
			WritableWorkbook book  =  Workbook.createWorkbook( new  File( "D:\\errorInfo.xls" ));
			//生成名为errorInfo的工作表  0标示第一页
			WritableSheet sheet  =  book.createSheet( "errorInfo" ,  0 );
			//遍历集合将数据放到单元格的指定位置上
			Label labe1 = new Label(0,0,"19位pos编号");
			//将定义好的单元格添加到工作表中
			sheet.addCell(labe1);		 
			for(int i=0;i<list.size();i++){				
				POSMoneyInfo info = (POSMoneyInfo)list.get(i);
				System.out.println("19位pos编号为"+info.getPos_dcc_code());
				Label labe2  =   new  Label( 0 , i+1 , info.getPos_dcc_code());
				System.out.println("Labe2 = "+labe2);
				sheet.addCell(labe2);			 
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
//==========================================================END=====================================================//

}
