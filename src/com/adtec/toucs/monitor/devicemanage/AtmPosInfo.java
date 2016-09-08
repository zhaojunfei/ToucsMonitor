package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM��װλ����Ϣ�� </p>
 * <p>Description:��װATM�İ�װλ����Ϣ </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmPosInfo {
	//ATM���
	private String atmId;
	//X����
	private int atmPointX;
	//Y����
	private int atmPointY;
	//�Ƿ�װ��־
	private String installFlag;

	//ATM������Զ�д
	public void setAtmId(String id){
		atmId=id;
	}
	public String getAtmId(){
		return atmId;
	}

	//X�������Զ�д
	public void setAtmPointX(int x){
		atmPointX=x;
	}
	public int getAtmPointX(){
		return atmPointX;
	}

	//Y�������Զ�д
	public void setAtmPointY(int y){
		atmPointY=y;
	}
	public int getAtmPointY(){
		return atmPointY;
	}

	//��װ��־���Զ�д
	public void setInstallFlag(String flag){
		installFlag=flag;
	}
	public String getInstallFlag(){
		return installFlag;
	}

	  /**
	   * ���ǵ�toString()����
	   * @return
	   */
	public String toString(){
		String ret="[AtmPosInfo]";
		ret+=atmId+"|"+atmPointX+"|"+atmPointY+"|"+installFlag;
		return ret;
	}

	  /**
	   * ת��ΪXML��ʽ����
	   * @return XML��ʽ����
	   */
	public String toXML(){
		StringBuffer buf=new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

	  /**
	   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
	   * @param buf �ַ����������
	   * @param withRoot ���ڵ��־
	   */
	public void toXML(StringBuffer buf,boolean withRoot){
		if(withRoot)
			buf.append("<AtmInfo id=\""+atmId+"\">\n");
		toXML(buf);
		if(withRoot)
			buf.append("</AtmInfo>\n");
	}

	  /**
	   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
	   * @param buf �ַ����������
	   */
	public void toXML(StringBuffer buf){
		buf.append("<Pos x=\""+atmPointX+"\" y=\""+atmPointY+"\"/>\n");
		buf.append("<InstallFlag>"+installFlag+"</InstallFlag>\n");
	}

	  /**
	   * �Ӳ�ѯ�����ȡ����
	   * @param rst ��ѯ�����
	   * @throws SQLException SQL�쳣
	   */
	public void fetchData(ResultSet rst) throws SQLException{
		atmId=Util.getString(rst,"atm_id","");
		atmPointX=rst.getInt("atm_point_x");
		atmPointY=rst.getInt("atm_point_y");
		installFlag=Util.getString(rst,"fix_flg","");
	}

	  /**
	   * ���췽��
	   */
	public AtmPosInfo() {
	}

	public static void main(String[] args) {
   
	}
}