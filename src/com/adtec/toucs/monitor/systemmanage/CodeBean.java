package com.adtec.toucs.monitor.systemmanage;

import java.io.Serializable;
import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ������</p>
 * <p>Description:��װϵͳ������Ϣ </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//��������
	private String codeType;
	//������
	private String codeId;
	//����˵��
	private String codeDesc;

  /**
   * ���췽��
   */
	public CodeBean() {
	}

	//�����������Զ�д
	public String getCodeType(){
		return codeType;
	}
	public void setCodeType(String type){
		codeType=type;
	}

	//���������Զ�д
	public String getCodeId(){
		return codeId;
	}
	public void setCodeId(String id){
		codeId=id;
	}

	//����˵�����Զ�д
	public String getCodeDesc(){
		return codeDesc;
	}
	public void setCodeDesc(String desc){
		codeDesc=desc;
	}

  /**
   * ȡ������ձ�ؼ��֣������ڴ�����ձ��м�������˵����
   * @return ����ؼ���
   */
	public String getKey(){
		return codeType+"."+codeId;
	}

  /**
   * �Ӳ�ѯ�����ȡ������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		Debug.println(rst.getString("code_type") + "ok le go");
		setCodeType(rst.getString("code_type").trim());
		setCodeId(rst.getString("sys_code").trim());
		setCodeDesc(toucsString.unConvert(rst.getString("code_desc")));
	}
  
  /**
   * �Ӳ�ѯ�����ȡ������Ϣ(new)syl
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchNewData(ResultSet rst) throws SQLException{
		Debug.println(rst.getString("para_val") + "ok le go");
		setCodeType(rst.getString("para_name").trim());
		setCodeId(rst.getString("para_val").trim());
		setCodeDesc(toucsString.unConvert(rst.getString("para_desc")));
	}

  /**
   * ��Ӵ���
   * @param sq ���ݿ���ʶ���
   * @return Ӱ��ļ�¼��
   * @throws SQLException
   */
	public int insert(SqlAccess sq) throws SQLException{
		String sql="INSERT INTO kernel_code(code_type,sys_code,code_desc) VALUES('" +codeType+"','"+codeId+"','" +codeDesc+"')";
		return sq.queryupdate(sql);
	}

  /**
   * �޸Ĵ���
   * @param sq ���ݿ���ʶ���
   * @param id ����
   * @return Ӱ��ļ�¼��
   * @throws SQLException
   */
	public int update(SqlAccess sq,String id) throws SQLException{
		String sql="UPDATE kernel_code SET sys_code=?,code_desc=? WHERE code_type=? AND sys_code=?";
		PreparedStatement stm=sq.conn.prepareStatement(sql);
		stm.setString(1,codeId);
		stm.setString(2,toucsString.Convert(codeDesc));
		stm.setString(3,codeType);
		stm.setString(4,id);
		return stm.executeUpdate();
	}

  /**
   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
   * @param buf �ַ����������
   */
	public void toXML(StringBuffer buf){
		buf.append("<SysCode type=\""+codeType+"\" code=\""+codeId+"\">\n");
		buf.append("<Desc>"+codeDesc+"</Desc>\n");
		buf.append("</SysCode>\n");	
	}

  /**
   * ת��ΪXML��ʽ�ı���
   * @return ת����ı���
   */
	public String toXML(){
		StringBuffer buf=new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

  /**
   * ���ǵ�toString()����
   * @return
   */
	public String toString(){
		return codeType+"|"+codeId+"|"+codeDesc;
	}

	public static void main(String[] args) {
    
	}
}
