package com.adtec.toucs.monitor.systemmanage;


import java.io.Serializable;
import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: �������Ĵ�����</p>
 * <p>Description:��װ���״�����Ϣ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class BankCodeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�������Ĵ���
	private String code="";
	//������������
	private String name="";
	//��������˵��
	private String memo="";
  
  /**
   * ���췽��
   */
	public BankCodeBean() {
	}
	//�������Ĵ������Զ�д
	public String getBankCode(){
		return code;
	}
	public void setBankCode(String bankCode){
		code=bankCode;
	}

	//���������������Զ�д
	public String getBankName(){
		return name;	
	}
	public void setBankName(String bankName){
		name=bankName;
	}

	//���������������Զ�д
	public String getBankMemo(){
		return memo;
	}
	public void setBankMemo(String bankMemo){
		memo=bankMemo;
	}

  /**
   * �Ӳ�ѯ�����ȡ������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		setBankCode(toucsString.unConvert(rst.getString("code")));	
		setBankName(toucsString.unConvert(rst.getString("name")));
		setBankMemo(toucsString.unConvert(rst.getString("memo")));	
  }
  /**
   * ��Http������ȡ������Ϣ
   * @param request Http����
   */
    public void fetchData(HttpServletRequest request){
    	setBankCode(request.getParameter("bankCode"));
    	setBankName(request.getParameter("bankName"));
    	setBankMemo(request.getParameter("bankMemo"));
    	Debug.println("bankCode:"+code);
    	Debug.println("bankName:"+name);
    	Debug.println("bankMemo:"+memo);
    }
  /**
   * ��Ӵ���
   * @param sq ���ݿ���ʶ���
   * @return Ӱ��ļ�¼��
   * @throws SQLException
   */
    public int insert(SqlAccess sq) throws SQLException{
    	String sql="INSERT INTO kernel_bank_code(code,name,memo) " + "VALUES(?,?,?)";
    	PreparedStatement stm=sq.conn.prepareStatement(sql); 
    	stm.setString(1,code);
    	stm.setString(2,name);
    	stm.setString(3,memo);
    	return stm.executeUpdate(); 	
    }

  /**
   * �޸Ĵ���
   * @param sq ���ݿ���ʶ���
   * @param id ����
   * @return Ӱ��ļ�¼��
   * @throws SQLException
   */
    public int update(SqlAccess sq) throws SQLException{
    	String sql="UPDATE kernel_bank_code SET name=?,memo=?" + "WHERE code=?";
    	PreparedStatement stm=sq.conn.prepareStatement(sql);
    	stm.setString(1,name);
    	stm.setString(2,memo);
    	stm.setString(3,code);
    	Debug.println(sql);
    	return stm.executeUpdate();
    }

    public static void main(String[] args) {

    }
}