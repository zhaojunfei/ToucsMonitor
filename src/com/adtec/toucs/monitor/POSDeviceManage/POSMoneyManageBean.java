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
 * <p>Title:���pos�豸����</p>
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
	   * �޸����POS�豸��Ϣ
	   * @param POSMoneyInfo �豸��Ϣ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ϵͳ�쳣
	   */
	public int updatePosInfo(POSMoneyInfo info, String key) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//���Զ��ύ
			sq.setAutoCommit(false);
			PreparedStatement stm = info.makeUpdateStm(sq.conn, key);
			int flag = stm.executeUpdate();      
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸����POS�豸��Ϣʧ�ܣ�");
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
	   * �����µ����pos�豸��Ϣ
	   * @param POSMoneyInfo �豸��Ϣ
	   * @param Poscount pos����
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ϵͳ�쳣
	   */
	public int regPosDevice(POSMoneyInfo info, int Poscount) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			//����豸������Ϣ
			int flag = info.insert(sq);      
			if (flag == 1) {	
				sq.commit();//�ύ
			} else {
				//����ع�
				//������Ѳ�����еĳ�ʼ����Ϣ
				sq.rollback();
				sq.close();
				throw new MonitorException(ErrorDefine.REG_FAILED, "�������POS�豸��Ϣʧ�ܣ�");
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
	   * ɾ�����POS�豸��Ϣ
	   * @param posCode pos���
	   * @return ɾ���ɹ�����1
	   * @throws MonitorException ϵͳ�쳣
	   */
	public int deletePosInfo(String posCode) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();    
		POSMoneyInfo info = new POSMoneyInfo();
		try {	    	
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ
			PreparedStatement stm = info.makeDeleteStm(sq.conn, posCode);
			int flag = stm.executeUpdate();    	
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();	//����ع�
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ�����POS�豸��Ϣʧ�ܣ�");
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
	   * ����ɾ�����POS�豸��Ϣ
	   * @param str ���pos�豸�������
	   * @return ɾ���ɹ�����1
	   * @throws MonitorException ϵͳ�쳣
	   */
	public int deletePosInfos(String[] str) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		StringBuffer strbf = new StringBuffer();
		for(int i=0;i<str.length;i++){
			strbf.append("'").append(str[i]).append("',");
		}
		try {
			//�������ݿ������ύ��ʽΪ���Զ��ύ
			sq.setAutoCommit(false);
			Debug.println("SETAUTOCOMMIT:END");
			//ɾ��POS�豸������Ϣ(����)
			String sql="DELETE FROM pos_enrol WHERE pos_code IN("+strbf.substring(0,strbf.length()-1)+")";
			int flag = sq.queryupdate(sql);    	
			if (flag >= 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.DELETE_FAILED, "ɾ�����POS�豸��Ϣʧ�ܣ�");
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
	   * �������POS�豸��Ϣ���á�ͣ�ñ�־
	   * @param pos_code pos���
	   * @param State ���pos�豸��ͣ��ʶ
	   * @return �Ǽǳɹ�����1
	   * @throws MonitorException ϵͳ�쳣
	   */
	public int setStatPos(String pos_code, boolean State) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			int flag = 0;    	
			Debug.println("FLAG:" + flag);
			if (flag == 1) {
				sq.commit();//�ύ
			} else {
				sq.rollback();//����ع�
				throw new MonitorException(ErrorDefine.UPDATE_FAILED, "�޸����POS�豸״̬ʧ�ܣ�");
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
	   * ��ѯ���POS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param code_type �̻����ʹ���
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ϵͳ�쳣
	   */
	public POSMoneyInfo qryPosInfo(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
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
	   * ��ѯ���POS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param code_type �̻��豸����
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public POSMoneyInfo qryPosMoney(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
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
	   * ��ѯPOS�豸����������Ϣ
	   * @param posCode �豸���
	   * @param code_type �豸����
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public Vector qryPosInfoByPoscode(String posCode, String code_type) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		try {
			//��ѯ�豸��Ϣ
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
	   * ��ѯ���pos�豸����������Ϣ
	   * @param merchantNo �̻����
	   * @param branchId ��ǰ�û���������
	   * @return �̻���Ϣ������̻������ڣ�����null
	   * @throws MonitorException ���ϵͳ�쳣
	   */
	public Vector qryMctPosInfo(String merchantNo ,String branchId) throws MonitorException {
		//ȡ���ݿ�����
		SqlAccess sq = SqlAccess.createConn();
		String sql ="";
		try {
			//��ѯ�豸��Ϣ
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
	   * ���POS�豸�����������ú�ͣ�á�
	   * @param PosID POS�豸���  
	   * @param UseFalg ��־"1"-����"0"-ͣ��
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
	 * ���pos��ȡ�ϴ���Excel�ļ�
	 * path �ļ�����·��
	 * return list
	 * 20121129
	 */
	public List recJXL(String path){
		Workbook book;			
		List list = new ArrayList();
		try {
			book = Workbook.getWorkbook(new File(path));
			//  ��õ�һ����������� 
			Sheet sheet  =  book.getSheet( 0 );
			//  �õ���һ�е�һ�еĵ�Ԫ�� 
			int  columnum  =  sheet.getColumns(); //  �õ����� 
			int  rownum  =  sheet.getRows(); //  �õ����� 
			System.out.println(columnum);
			System.out.println(rownum);
			for  ( int  i  =   1 ; i  <  rownum; i ++ ) {
				POSMoneyInfo posMoney = new POSMoneyInfo();
				//�жϵ�һ�е�I�е�ֵ�Ƿ�Ϊ��
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
	    
//=========================================�������� START================================================================  
	 /**
	  * ����excel�ļ�
	  * list Ҫ��ӵ����� 
	  * path �ļ�·��
	  * 20121129
	  */
	public void createJXL(List list, String path){
		try {
			//ָ��·�������ļ�
			WritableWorkbook book  =  Workbook.createWorkbook( new  File( "D:\\errorInfo.xls" ));
			//������ΪerrorInfo�Ĺ�����  0��ʾ��һҳ
			WritableSheet sheet  =  book.createSheet( "errorInfo" ,  0 );
			//�������Ͻ����ݷŵ���Ԫ���ָ��λ����
			Label labe1 = new Label(0,0,"19λpos���");
			//������õĵ�Ԫ����ӵ���������
			sheet.addCell(labe1);		 
			for(int i=0;i<list.size();i++){				
				POSMoneyInfo info = (POSMoneyInfo)list.get(i);
				System.out.println("19λpos���Ϊ"+info.getPos_dcc_code());
				Label labe2  =   new  Label( 0 , i+1 , info.getPos_dcc_code());
				System.out.println("Labe2 = "+labe2);
				sheet.addCell(labe2);			 
			}
			//д�����ݹر��ļ�
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
