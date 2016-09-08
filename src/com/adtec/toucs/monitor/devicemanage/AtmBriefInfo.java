package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM������Ϣ</p>
 * <p>Description:��װATM�豸������Ϣ�����ڿͻ���ATM��Ϣ���أ�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmBriefInfo {
	//���
	private String atmCode="";	
	//����
	private String atmName="";
	//�豸����
	private String atmType="";
	//�豸�ͺ�
	private String atmMode="";
	//�������
	private String orgId="";
	//����Ա�绰
	private String mngPhone="";
	//��װ�ص�����
	private String atmAddrType="";
	//����ƽ������
	private float lastMmddAmount=0;
	//��Ҫ��
	private String atmLevel="";
	//�豸״̬
	private String atmStat="";

  /**
   * ȡ�豸���
   * @return �豸���
   */
	public String getAtmCode(){
		return atmCode;
	}

  /**
   * ���췽��
   */
	public AtmBriefInfo() {
	}

  /**
   * �Ӳ�ѯ�����ȡ�豸������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		atmCode=Util.getString(rst,"atm_code","");
		atmName=Util.getString(rst,"atm_name","");
		atmType=Util.getString(rst,"atm_type","");
		atmMode=Util.getString(rst,"atm_mode","");
		orgId=Util.getString(rst,"org_id","");
		mngPhone=Util.getString(rst,"mng_phone","");
		atmAddrType=Util.getString(rst,"atm_addr_type","");
		lastMmddAmount=rst.getFloat("last_mmdd_amount");
		atmLevel=Util.getString(rst,"atm_level","");
		atmStat=Util.getString(rst,"atm_stat","");
	}

  /**
   * ת��ΪXML��ʽ���ģ���䵽�ַ������������
   * @param buf �ַ����������
   */
	public void toXML(StringBuffer buf){
		buf.append("<Name>"+atmName+"</Name>\n");
		buf.append("<OrgId>"+orgId+"</OrgId>\n");
		buf.append("<Type>"+atmType+"</Type>\n");
		buf.append("<Mode>"+atmMode+"</Mode>\n");
		buf.append("<Phone>"+mngPhone+"</Phone>\n");
		buf.append("<InstallType>"+atmAddrType+"</InstallType>\n");
    	buf.append("<LMPerDayDrawing>"+String.valueOf(lastMmddAmount)+"</LMPerDayDrawing>\n");
    	buf.append("<Stress>"+atmLevel+"</Stress>\n");
    	buf.append("<DeviceStat>"+atmStat+"</DeviceStat>\n");
	}
}