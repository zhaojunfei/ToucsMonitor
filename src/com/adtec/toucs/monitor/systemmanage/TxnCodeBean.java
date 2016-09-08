package com.adtec.toucs.monitor.systemmanage;

import java.io.Serializable;
import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ���״�����</p>
 * <p>Description:��װ���״�����Ϣ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class TxnCodeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//�豸���ʹ��룺(����Сд)
	private String sys_id="";
	//��������
	private String txn_code="";
	//��������
	private String txn_name="";
	//��������λ��
	private int location=0;
	//�Ƿ����佻�����ԣ�0��ʾû�д�����
	private String hand_flag="";
	//�����������ԣ� 0���ǳ������ף�1����������
	private String reverse_flag="";
	//�Ƿ������ˮ�������ԣ� 1���� 0����
	private String insert_flag="";
	//����״̬1����Ч 0����Ч
	//private String validflag;
	//�������־��1Ϊ�跽��0Ϊ����
	private String dc_flag="";
	//Ӧ������1�� ����pos����ʾ�Ƿ����Ʊ�ݼ�飬0���� 1����
	private String app_flag1="0";
	//Ӧ������2������pos��Ч��0:��ͨ���� 1:Ԥ��Ȩ����
	private String app_flag2="0";

	//�豸�������Զ�д
	public String getAppFlag2(){
		return app_flag2;
	}
	public void setAppFlag2(String flag){
		app_flag2=flag;
	}


	  /**
	   * ���췽��
	   */
	public TxnCodeBean() {
	}

	//�豸�������Զ�д
	public String getDeviceType(){
		return sys_id;
	}
	public void setDeviceType(String type){
		sys_id=type;
	}

	//���״������Զ�д
	public String getTxnCode(){
		return txn_code;
	}
	public void setTxnCode(String txnCode){
		txn_code=txnCode;
	}

	//�����������Զ�д
	public String getTxnName(){
		return txn_name;
	}
	public void setTxnName(String txnName){
		txn_name=txnName;
	}

	//�Ƿ����佻�����Զ�д
	public String getHandFlag(){
		return hand_flag;
	}	
	public void setHandFlag(String handFlag){
		hand_flag=handFlag;
	}

	//��������λ�����Զ�д
	public int getMaskSerial(){
		return location;
	}
	public void setMaskSerial(int serial){
		location=serial;
	}

	//�����������Զ�д
	public String getReverseFlag(){
		return reverse_flag;
	}
	public void setReverseFlag(String reverseFlag){
		reverse_flag=reverseFlag;
	}

	//�����������Զ�д
	public String getInsertFlag(){
		return insert_flag;
	}
	public void setInsertFlag(String insertFlag){
		insert_flag=insertFlag;
	}

	//Ӧ������1��д
	public String getAppFlag1(){
		return app_flag1;
	}
	public void setAppFlag1(String appFlag){
		app_flag1=appFlag;
	}

	//�������־��д
	public String getDCFlag(){
		return dc_flag;
	}
	public void setDCFlag(String dcFlag){
		dc_flag=dcFlag;
	}	


  /**
   * �Ӳ�ѯ�����ȡ������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
          setDeviceType(rst.getString("sys_id").trim());
          setTxnCode(rst.getString("txn_code").trim());
          setTxnName(toucsString.unConvert(rst.getString("txn_name").trim()));
          setHandFlag(rst.getString("hand_flag").trim());
          setInsertFlag(rst.getString("insert_flag").trim());
          setMaskSerial(rst.getInt("location"));
          setReverseFlag(rst.getString("reverse_flag").trim());
          setAppFlag1(rst.getString("app_flag1").trim());
          setAppFlag2(rst.getString("app_flag2").trim());
          setDCFlag(rst.getString("dc_flag").trim());
  }
   /**
    * ��Http������ȡCDM������Ϣ
    * @param request Http����
    */
    public void fetchData(HttpServletRequest request){
    	setDeviceType(request.getParameter("deviceType"));
    	setTxnCode(request.getParameter("txn_code"));
    	setTxnName(request.getParameter("txn_name"));
    	setHandFlag(request.getParameter("hand_flag"));
    	setInsertFlag(request.getParameter("insert_flag"));
    	setReverseFlag(request.getParameter("reverse_flag"));
    	setDCFlag(request.getParameter("dc_flag"));
    	if( sys_id.equals("pos") ){
    		setAppFlag1(request.getParameter("app_flag1"));
    		setAppFlag2(request.getParameter("app_flag2"));
    	}

    }
	  /**
	   * ��Ӵ���
	   * @param sq ���ݿ���ʶ���
	   * @return Ӱ��ļ�¼��
	   * @throws SQLException
	   */
    public int insert(SqlAccess sq) throws SQLException{
         //��������λ��
        String sql="SELECT MAX(location) FROM kernel_txn_code WHERE sys_id='"+sys_id+"'";
        ResultSet rs=sq.queryselect(sql);
        while(rs.next()){
                location = rs.getInt(1);
        }
        rs.close();
        location++;
        sql="INSERT INTO kernel_txn_code(sys_id,txn_code,txn_name,location,hand_flag,dc_flag,"+
           "reverse_flag,insert_flag,app_flag1,app_flag2,app_flag3,flag1,flag2,flag3,valid_flag) "+
           "VALUES('"+sys_id+"','"+txn_code+"','"+txn_name+"',"+location+",'"+hand_flag+"','"+dc_flag+
           "','"+reverse_flag+"','"+insert_flag+"','"+app_flag1+"','"+app_flag2+"','0','0','0','0','1')";
        return sq.queryupdate(sql);
  }

	  /**
	   * �޸Ĵ���
	   * @param sq ���ݿ���ʶ���
	   * @param id ����
	   * @return Ӱ��ļ�¼��
	   * @throws SQLException
	   */
    public int update(SqlAccess sq) throws SQLException{
    	String sql="UPDATE kernel_txn_code SET txn_name=?,hand_flag=?,dc_flag=?,reverse_flag=?,"+
               "insert_flag=?,app_flag1=?,app_flag2=?"+
               "WHERE sys_id=? AND txn_code=?";
    	PreparedStatement stm=sq.conn.prepareStatement(sql);
    	stm.setString(1,txn_name);
    	stm.setString(2,hand_flag);
    	stm.setString(3,dc_flag);
    	stm.setString(4,reverse_flag);
    	stm.setString(5,insert_flag);
    	stm.setString(6,app_flag1);
    	stm.setString(7,app_flag2);
    	stm.setString(8,sys_id);
    	stm.setString(9,txn_code);

        Debug.println(sql);
        return stm.executeUpdate();
  }

	  /**
	   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
	   * @param buf �ַ����������
	   */
    public void toXML(StringBuffer buf){
    	buf.append("<TxnCodeInfo  type=\""+sys_id+"\" code=\""+txn_code+"\">\n");
    	buf.append("<Name>"+txn_name+"</Name>\n");
    	buf.append("</TxnCodeInfo >\n");
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

    public static void main(String[] args) {
    }
}
