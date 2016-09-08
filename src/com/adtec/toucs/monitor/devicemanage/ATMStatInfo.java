package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ATM״̬��Ϣ��</p>
 * <p>Description: ��װATM�豸����ϸ״̬��Ϣ</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ATMStatInfo {
	//�豸���
	public String atmCode="";
	//�豸����
	public String atmName="";
	//�����������
	public String lastDate="";
	//�������ʱ��
	public String lastTime="";
	//�豸״̬
	public String atmStat="";
	//��ˮ��ӡ��״̬
	public String atmJnl="";
	//ƾ����ӡ��״̬
	public String atmRecpt="";
	//������״̬
	public String atmReadW="";
	//IC������ģ��״̬
	public String ICRead="";
	//����ģ��״̬
	public String atmCdm="";
	//Ӳ��Ǯ��״̬
	public String atmCoin="";
	//���ģ��״̬
	public String atmDep="";
	//Ӳ����ģ��״̬
	public String atmDem="";
	//������������ţ�״̬
	public String atmAlm="";
	//������״̬
	public String touchStat="";
	//8������״̬
	public String feeBox="";
	//����״̬
	public String atmKeyboard="";
	//Ӳ��״̬
	public String hardDisk="";
	//PSAMģ��״̬
	public String atmPsam="";
	//���ư�״̬
	public String ctrlFace="";
	//������״̬
	public String safeBox="";
	//�̿���Ŀ
	public String cardNum="";
	//����״̬
	public String netStat="";
	//��������
	public String atmCmd="";

	  /**
	   * �Ӳ�ѯ�����ȡ�豸״̬��Ϣ
	   * @param rst ��ѯ�����
	   * @throws SQLException
	   */
	public void fetchData(ResultSet rst) throws SQLException{
		atmCode=Util.getString(rst,"atm_code","");
		atmName=Util.getString(rst,"atm_name","");
		lastDate=Util.getString(rst,"last_date","");
		lastTime=Util.getString(rst,"last_time","");
		atmStat=Util.getString(rst,"atm_stat","");
		atmJnl=Util.getString(rst,"atm_jnl","");
		atmRecpt=Util.getString(rst,"atm_recpt","");
		atmReadW=Util.getString(rst,"atm_read_w","");
		ICRead=Util.getString(rst,"IC_read","");
		atmCdm=Util.getString(rst,"atm_cdm","");
		atmCoin=Util.getString(rst,"atm_coin","");
		atmDep=Util.getString(rst,"atm_dep","");
		atmDem=Util.getString(rst,"atm_dem","");
		atmAlm=Util.getString(rst,"atm_alm","");
		touchStat=Util.getString(rst,"touch_stat","");
		feeBox=Util.getString(rst,"fee_box","");
		atmKeyboard=Util.getString(rst,"atm_keyboard","");
		ctrlFace=Util.getString(rst,"ctrl_face","");
		safeBox=Util.getString(rst,"safe_box","");
		hardDisk=Util.getString(rst,"hard_disk","");
		atmPsam=Util.getString(rst,"atm_psam","");
		cardNum=Util.getString(rst,"card_num","");
		netStat=Util.getString(rst,"net_stat","");
		atmCdm=Util.getString(rst,"atm_cdm","");
	}

	  /**
	   * ת��ΪXML��ʽ���ģ���ӵ��ַ������������
	   * @param buf �ַ����������
	   */
	public void toXML(StringBuffer buf){
		buf.append("<NO18Cashbox>"+feeBox+"</NO18Cashbox>\n");
		buf.append("<NOSaveCashbox></NOSaveCashbox>\n");
		buf.append("<FlowPrinterState>"+atmJnl+"</FlowPrinterState>\n");
		buf.append("<VoucherPrinterState>"+atmRecpt+"</VoucherPrinterState>\n");
		buf.append("<SaveMoneyModule>"+atmDep+"</SaveMoneyModule>\n");
		buf.append("<ICRWDevice>"+atmReadW+"</ICRWDevice>\n");
		buf.append("<Keyboard>"+atmKeyboard+"</Keyboard>\n");
		buf.append("<EncryptDevice>"+atmDem+"</EncryptDevice>\n");
		buf.append("<ICRModule>"+ICRead+"</ICRModule>\n");
		buf.append("<PSAMModule>"+atmPsam+"</PSAMModule>\n");
		buf.append("<HD>"+hardDisk+"</HD>\n");
		buf.append("<NetworkState>"+netStat+"</NetworkState>\n");
	}

	  /**
	   * ת��ΪXML��ʽ����(��ϸ״̬��Ϣ)����ӵ��ַ������������
	   * @param buf �ַ����������
	   */
	public void toXMLDetail(StringBuffer buf){
		buf.append("<DeviceCode>"+atmCode+"</DeviceCode>\n");
		buf.append("<DeviceName>"+atmName+"</DeviceName>\n");
		buf.append("<HappenDate>"+lastDate+"</HappenDate>\n");
		buf.append("<HappenTime>"+lastTime+"</HappenTime>\n");
		buf.append("<DeviceStat>"+atmStat+"</DeviceStat>\n");
		buf.append("<NO1Cashbox>"+getChashBoxStat(1)+"</NO1Cashbox>\n");
		buf.append("<NO2Cashbox>"+getChashBoxStat(2)+"</NO2Cashbox>\n");
		buf.append("<NO3Cashbox>"+getChashBoxStat(3)+"</NO3Cashbox>\n");
		buf.append("<NO4Cashbox>"+getChashBoxStat(4)+"</NO4Cashbox>\n");
    	buf.append("<NO58Cashbox>4444</NO58Cashbox>\n");
    	buf.append("<CoinCashbox>4444</CoinCashbox>\n");
    	buf.append("<NO1SaveCashbox>"+getSaveChashBoxStat(1)+"</NO1SaveCashbox>\n");
    	buf.append("<NO2SaveCashbox>"+getSaveChashBoxStat(2)+"</NO2SaveCashbox>\n");
    	buf.append("<NO3SaveCashbox>"+getSaveChashBoxStat(3)+"</NO3SaveCashbox>\n");
    	buf.append("<NO4SaveCashbox>"+getSaveChashBoxStat(4)+"</NO4SaveCashbox>\n");
    	buf.append("<FlowPrinterState>"+atmJnl+"</FlowPrinterState>\n");
    	buf.append("<VoucherPrinterState>"+atmRecpt+"</VoucherPrinterState>\n");
    	buf.append("<SaveMoneyModule></SaveMoneyModule>\n");
    	buf.append("<ICRWDevice>"+atmReadW+"</ICRWDevice>\n");
    	buf.append("<Keyboard>"+atmKeyboard+"</Keyboard>\n");
    	buf.append("<EncryptDevice>"+atmDem+"</EncryptDevice>\n");
    	buf.append("<ICRModule>"+ICRead+"</ICRModule>\n");
    	buf.append("<PSAMModule>"+atmPsam+"</PSAMModule>\n");
    	buf.append("<HD>"+hardDisk+"</HD>\n");
    	buf.append("<SlefDeviceState></SlefDeviceState>\n");
    	buf.append("<NetworkState>"+netStat+"</NetworkState>\n");
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
	   * ���췽��
	   */
	public ATMStatInfo(){
	}

	  /**
	   * ȡ����״̬
	   * @param idx ����������
	   * @return ����״̬
	   */
	public String getChashBoxStat(int idx){
		if(feeBox==null||idx<0||idx>8||idx>feeBox.length())
			return "";
		else{
			return feeBox.substring(idx-1,idx);
		}
	}

	  /**
	   * ȡ����״̬
	   * @param idx ����������
	   * @return ����״̬
	   */
	public String getSaveChashBoxStat(int idx){
		if(atmDep==null||idx<0||idx>8||idx>atmDep.length())
			return "";
		else
			return atmDep.substring(idx,idx);
	}

	  /**
	   * ȡӲ�ҳ���״̬
	   * @param idx Ӳ�ҳ���������
	   * @return Ӳ�ҳ���״̬
	   */
	public String getCoinBoxStat(int idx){
		if(idx<0||idx>4)
			return "";
		else
			return atmCoin.substring(idx,idx);
	}

	public static void main(String[] args) {
     
	}
}