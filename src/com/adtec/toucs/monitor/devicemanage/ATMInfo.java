package com.adtec.toucs.monitor.devicemanage;


import java.sql.*;

import java.io.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM�豸������Ϣ�� </p>
 * <p>Description: ��װATM�豸������Ϣ</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ATMInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//�豸���
	private String atmCode="";

	//begin add by liyp 20030626
	//atm_c_code
	private String atmCCode="";
	//atm_dcc_code
	private String atmDccCode="";
	//end add by liyp 20030626

	//�豸���
	private String atmSn="";
	//�豸���ٺ�
	private String atmTn="";
	//�豸����
	private String atmName="";
	//�豸����
	private String atmType="";
	//�豸�ͺ�
	private String atmMode="";
	//���Ĵ����׼����
	private String translateName="";
	//��������
	private String msgType="";
	//ͨѶ����
	private String netType="";
	//����ͨѶ��ַ
	private String netAddress="";
	//��������
	private String netMask="";
	//ATM������ַ(ǰ�û���ַ)
	private String hostAddress="";
	//ATM�����˿�(ǰ�û��˿�)
	private String hostPort="";
	//ATM·������ַ(���ص�ַ)
	private String routeAddress="";
	//������(Ծ����)
	private int jumpNums=0;
	//�豸���ں�
	private int atmCycle=1;
	//���׿���
	private String cardType="";
	//���ر�־
	private String useFlag="2";
	//����
	private String currencyCode="";
	//���ͱ�־
	private String sendFlag="0";
	//�豸��������
	private String atmBuyDate="";
	//�豸��װ��ϸ��ַ
	private String atmSetAddr="";
	//�豸��װ�ص�����
	private String atmAddrType="";
	//�豸��װ����
	private String atmSetDate="";
	//ATM�豸��װ����
	private String atmSetDegree="";
	//ATM�豸����֪ͨ����
	private String atmNotifyDate="";
	//SDLC
	private String atmSdlc="";
	//�豸��װ��λ
	private String atmSetUnit="";
	//�豸����λ
	private String atmMngUnit="";
	//֧�к�
	private String agencyNo="";
	//��������
	private String branchNo="";
	//����������
	private String branchName="";
	//������
	private String unionName="";
	//��������
	private String orgId="";
	//ATM��Ա��
	private String tellerId="";
	//��������
	private String areaNo="";
	//�����ʺ�
	private String bankAcc="";
	//������
	private String changeNo="";
	//���к�
	private String unionNo="";
	//�����װ����
	private String progSetDate="";
	//�����װ����Ա����
	private String programer="";
	//�����װ����Ա�绰
	private String programerPhone="";
	//����Ա����
	private String mngName="";
	//����Ա�绰
	private String mngPhone="";
	//�豸��ͨ����
	private String connectDate="";
	//����������
	private String cardReaderMode="";
	//������
	private String mngCardNo="";
	//����Ա��Ա��
	private String operNo="";
	//�豸״̬
	private String atmStat="";
	//�豸����״̬
	private String runStat="";
	//�豸��Ҫ�̶�
	private String atmLevel="";
	//����ƽ����ȡ����
	private float lastMmddAmount=0;
	//����ƽ����ȡ�����
	private int lastMmddCount=0;
	//��ע1
	private String memo1="";
	//��ע2
	private String memo2="";
	//��ע3
	private String memo3="";
	//��������
	private AtmBoxInfo boxInfo=null;
	//��Կ����
	private AtmKeyInfo keyInfo=null;
	//״̬��Ϣ
	private ATMStatInfo statInfo=null;

	  //���췽��
	  public ATMInfo() {
	    oper=1;
	  }
	
	  //�豸������Զ�д
	  public void setAtmCode(String code){atmCode=code;}
	  public String getAtmCode(){return atmCode;}
	
	  //�豸C������Զ�д add by liyp 20030626
	  public void setAtmCCode(String code){atmCCode=code;}
	  public String getAtmCCode(){return atmCCode;}
	
	  //�豸DCC������Զ�д add by liyp 20030626
	  public void setAtmDccCode(String code){atmDccCode=code;}
	  public String getAtmDccCode(){return atmDccCode;}
	
	  //�豸�������Զ�д
	  public void setAtmName(String name){atmName=name;}
	  public String getAtmName(){return atmName;}
	
	  //ͨѶ��ַ���Զ�д
	  public void setNetAddress(String address){netAddress=address;}
	  public String getNetAddress(){return netAddress;}
	
	  //�����������Զ�д
	  public void setNetMask(String mask){netMask=mask;}
	  public String getNetMask(){return netMask;}

	  //ATM������ַ(ǰ�û���ַ)���Զ�д
	  public void setHostAddress(String address){hostAddress=address;}
	  public String getHostAddress(){return hostAddress;}
	
	  //ATM�����˿�(ǰ�û��˿�)���Զ�д
	  public void setHostPort(String port){hostPort=port;}
	  public String getHostPort(){return hostPort;}
	
	  //ATM·������ַ(���ص�ַ)���Զ�д
	  public void setRouteAddress(String address){routeAddress=address;}
	  public String getRouteAddress(){return routeAddress;}
	
	  //������(Ծ����)���Զ�д
	  public void setJumpNums(int nums){jumpNums=nums;}
	  public int getJumpNums(){return jumpNums;}
	
	  //�豸�������Զ�д
	  public void setAtmCycle(int cycle){atmCycle=cycle;}
	  public int getAtmCycle(){return atmCycle;}
	
	  //���׿������Զ�д
	  public void setCardType(String type){cardType=type;}
	  public String getCardType(){return cardType;}
	
	  //���ر�־���Զ�д
	  public void setUseFlag(String flag){useFlag=flag;}
	  public String getUseFlag(){return useFlag;}
	
	  //�������Զ�д
	  public void setCurrencyCode(String code){currencyCode=code;}
	  public String getCurrencyCode(){return currencyCode;}
	
	  //���ͱ�־���Զ�д
	  public void setSendFlag(String flag){sendFlag=flag;}
	  public String getSendFlag(){return sendFlag;}
	
	  //�豸�������Զ�д
	  public void setAtmType(String type){atmType=type;}
	  public String getAtmType(){return atmType;}
	
	  //�豸�ͺ����Զ�д
	  public void setAtmMode(String mode){atmMode=mode;}
	  public String getAtmMode(){return atmMode;}
	
	  //���Ĵ����׼�������Զ�д
	  public void setTranslateName(String name){translateName=name;}
	  public String getTranslateName(){return translateName;}
	
	  //�����������Զ�д
	  public void setMsgType(String type){msgType=type;}
	  public String getMsgType(){return msgType;}
	
	  //ͨѶ�������Զ�д
	  public void setNetType(String type){netType=type;}
	  public String getNetType(){return netType;}
	
	  //�豸���ٺ����Զ�д
	  public void setAtmTn(String tn){atmTn=tn;}
	  public String getAtmTn(){return atmTn;}
	
	  //�豸������Զ�д
	  public void setAtmSn(String sn){atmSn=sn;}
	  public String getAtmSn(){return atmSn;}
	
	  //�豸�����������Զ�д
	  public void setAtmBuyDate(String date){atmBuyDate=date;}
	  public String getAtmBuyDate(){return atmBuyDate;}
	
	  //�豸��װ��ϸ��ַ���Զ�д
	  public void setAtmSetAddr(String addr){atmSetAddr=addr;}
	  public String getAtmSetAddr(){return atmSetAddr;}
	
	  //�豸��װ�ص��������Զ�д
	  public void setAtmAddrType(String type){atmAddrType=type;}
	  public String getAtmAddrType(){return atmAddrType;}
	
	  //�豸��װ�������Զ�д
	  public void setAtmSetDate(String date){atmSetDate=date;}
	  public String getAtmSetDate(){return atmSetDate;}
	
	  //ATM�豸��װ�������Զ�д
	  public void setAtmSetDegree(String degree){atmSetDegree=degree;}
	  public String getAtmSetDegree(){return atmSetDegree;}
	
	  //ATM�豸����֪ͨ�������Զ�д
	  public void setAtmNotifyDate(String date){atmNotifyDate=date;}
	
	  //SDLC���Զ�д
	  public void setAtmSdlc(String sdlc){atmSdlc=sdlc;}
	  public String getAtmSdlc(){return atmSdlc;}
	
	  //�豸��װ��λ���Զ�д
	  public void setAtmSetUnit(String unit){atmSetUnit=unit;}
	  public String getAtmSetUnit(){return atmSetUnit;}
	
	  //�豸��װ����λ���Զ�д
	  public void setAtmMngUnit(String unit){atmMngUnit=unit;}
	  public String getAtmMngUnit(){return atmMngUnit;}
	
	  //֧�к����Զ�д
	  public void setAgencyNo(String no){agencyNo=no;}
	  public String getAgencyNo(){return agencyNo;}
	
	  //�����������Զ�д
	  public void setBranchNo(String no){branchNo=no;}
	  public String getBranchNo(){return branchNo;}
	
	  //�������������Զ�д
	  public void setBranchName(String name){branchName=name;}
	  public String getBranchName(){return branchName;}
	
	  //���������Զ�д
	  public void setUnionName(String name){unionName=name;}
	  public String getUnionName(){return unionName;}
	
	  //�����������Զ�д
	  public void setOrgId(String id){orgId=id;}
	  public String getOrgId(){return orgId;}
	
	  //��Ա�����Զ�д
	  public void setTellerId(String id){tellerId=id;}
	  public String getTellerId(){return tellerId;}
	
	  //�����������Զ�д
	  public void setAreaNo(String no){areaNo=no;}
	  public String getAreaNo(){return areaNo;}
	
	  //�����ʺ����Զ�д
	  public void setBankAcc(String acc){bankAcc=acc;}
	  public String getBankAcc(){return bankAcc;}
	
	  //���������Զ�д
	  public void setChangeNo(String no){changeNo=no;}
	  public String getChangeNo(){return changeNo;}
	
	  //���к����Զ�д
	  public void setUnionNo(String no){unionNo=no;}
	  public String getUnionNo(){return unionNo;}
	
	  //�����װ�������Զ�д
	  public void setProgSetDate(String date){progSetDate=date;}
	  public String getProgSetDate(){return progSetDate;}
	
	  //�����װ����Ա�������Զ�д
	  public void setProgramer(String pgr){programer=pgr;}
	  public String getProgramer(){return programer;}
	
	  //�����װ����Ա�绰���Զ�д
	  public void setProgramerPhone(String phone){programerPhone=phone;}
	  public String getProgramerPhone(){return programerPhone;}
	
	  //����Ա�������Զ�д
	  public void setMngName(String name){mngName=name;}
	  public String getMngName(){return mngName;}
	
	  //����Ա�绰���Զ�д
	  public void setMngPhone(String phone){mngPhone=phone;}
	  public String getMngPhone(){return mngPhone;}
	
	  //�豸��ͨ�������Զ�д
	  public void setConnectDate(String date){connectDate=date;}
	  public String getConnectDate(){return connectDate;}
	
	  //�������������Զ�д
	  public void setCardReaderMode(String mode){cardReaderMode=mode;}
	  public String getCardReaderMode(){return cardReaderMode;}
	
	  //���������Զ�д
	  public void setMngCardNo(String no){mngCardNo=no;}
	  public String getMngCardNo(){return mngCardNo;}
	
	  //��Ա�����Զ�д
	  public void setOperNo(String no){operNo=no;}
	  public String getOperNo(){return operNo;}
	
	  //�豸״̬���Զ�д
	  public void setAtmStat(String stat){atmStat=stat;}
	  public String getAtmStat(){return atmStat;}
	
	  //�豸����״̬���Զ�д
	  public void setRunStat(String stat){runStat=stat;}
	  public String getRunStat(){return runStat;}
	
	  //�豸��Ҫ�̶����Զ�д
	  public void setAtmLevel(String level){atmLevel=level;}
	  public String getAtmLevel(){return atmLevel;}
	
	  //����ƽ����ȡ�������Զ�д
	  public void setLastMmddAmount(float amount){lastMmddAmount=amount;}
	  public float getLastMmddAmount(){return lastMmddAmount;}
	
	  //����ƽ����ȡ��������Զ�д
	  public void setLastMmddCount(int count){lastMmddCount=count;}
	  public int getLastMmddCount(){return lastMmddCount;}
	
	  //��ע1���Զ�д
	  public void setMemo1(String m){memo1=m;}
	  public String getMemo1(){return memo1;}
	
	  //��ע2���Զ�д
	  public void setMemo2(String m){memo2=m;}
	  public String getMemo2(){return memo2;}
	
	  //��ע3���Զ�д
	  public void setMemo3(String m){memo3=m;}
	  public String getMemo3(){return memo3;}
	
	  //������Ϣ���Զ�д
	  public void setBoxInfo(AtmBoxInfo info){boxInfo=info;}
	  public AtmBoxInfo getBoxInfo(){return boxInfo;}
	
	  //��Կ������Ϣ���Զ�д
	  public void setKeyInfo(AtmKeyInfo info){
	    keyInfo=info;
	    if(info!=null)
	      info.setAtmCode(atmCode);
	  }
	  public AtmKeyInfo getKeyInfo(){return keyInfo;}
	
	  //״̬��Ϣ���Զ�д
	  public void setStatInfo(ATMStatInfo info){statInfo=info;}
	  public ATMStatInfo getStatInfo(){return statInfo;}

	  /**
	   * ����toString()����
	   * @return
	   */
	  public String toString(){
		  String str="atmCode="+atmCode+"|"+"atmCCode="+atmCCode+"|"+"atmDccCode="+atmDccCode+"|"+
               "atmSn="+atmSn+"|"+"atmTn="+atmTn+"|"+"atmName="+atmName+"|"+"atmType="+atmType+"|"+"atmMode="+atmMode+"|"+
               "translateName="+translateName+"|"+"msgType="+msgType+"|"+"netType="+netType+"|"+"netAddress="+netAddress+"|"+"atmCycle="+atmCycle+"|"+
               "cardType="+cardType+"|"+"useFlag="+useFlag+"|"+"currencyCode="+currencyCode+"|"+"sendFlag="+sendFlag+"|"+"atmBuyDate="+atmBuyDate+"|"+
               "atmAddrType="+atmAddrType+"|"+"atmSetAddr="+atmSetAddr+"|"+"atmSetDate="+atmSetDate+"|"+"atmSetUnit="+atmSetUnit+"|"+"atmMngUnit="+atmMngUnit+"|"+
               "agencyNo="+agencyNo+"|"+"branchNo="+branchNo+"|"+"branchName="+branchName+"|"+"orgId="+orgId+"|"+"areaNo="+areaNo+"|"+
               "changeNo="+changeNo+"|"+"unionNo="+unionNo+"|"+"progSetDate="+progSetDate+"|"+"programer="+programer+"|"+"programerPhone="+programerPhone+"|"+
               "mngName="+mngName+"|"+"mngPhone="+mngPhone+"|"+"connectDate="+connectDate+"|"+"cardReaderMode="+cardReaderMode+"|"+"mngCardNo="+mngCardNo+"|"+
               "operNo="+operNo+"|"+"atmStat="+atmStat;
		  return str;
	  }

  /**
   * ��Http������ȡATM������Ϣ
   * @param request Http����
   */
	  public void fetchData(HttpServletRequest request){
		  setAtmCode(request.getParameter("atmCode"));
		  //begin add by liyp 20030626
		  setAtmCCode(request.getParameter("atmCCode"));
		  setAtmDccCode(request.getParameter("atmDccCode"));
		  //end add by liyp 20030626

		  setAtmSn(request.getParameter("atmSn"));
		  setAtmTn(request.getParameter("atmTn"));
		  setAtmName(request.getParameter("atmName"));
		  setAtmType(request.getParameter("atmType"));
		  setAtmMode(request.getParameter("atmMode"));
		  setTranslateName(request.getParameter("translateName"));
		  setMsgType(request.getParameter("msgType"));
		  setNetType(request.getParameter("netType"));
		  setNetAddress(request.getParameter("netAddress"));
		  setNetMask(request.getParameter("netMask"));
		  setHostAddress(request.getParameter("hostAddress"));
		  setHostPort(request.getParameter("hostPort"));
		  setRouteAddress(request.getParameter("routeAddress"));
		  setJumpNums(Util.getIntPara(request,"jumpNums",0));
		  setCardType(request.getParameter("cardType"));
		  setUseFlag(request.getParameter("useFlag"));
		  setCurrencyCode(request.getParameter("currencyCode"));
		  setAtmBuyDate(request.getParameter("atmBuyDate"));
		  setAtmAddrType(request.getParameter("atmAddrType"));
		  setAtmSetAddr(request.getParameter("atmSetAddr"));
		  setAtmSetDate(request.getParameter("atmSetDate"));
		  setAtmSetDegree(request.getParameter("atmSetDegree"));
		  setAtmNotifyDate(request.getParameter("atmNotifyDate"));
		  setAtmSdlc(request.getParameter("atmSdlc"));
		  setAtmSetUnit(request.getParameter("atmSetUnit"));
		  setAtmMngUnit(request.getParameter("atmMngUnit"));
		  setAgencyNo(request.getParameter("agencyNo"));
		  setBranchNo(request.getParameter("branchNo"));
		  setBranchName(request.getParameter("branchName"));
		  setUnionName(request.getParameter("unionName"));
		  setOrgId(request.getParameter("orgId"));
		  setTellerId(request.getParameter("tellerId"));
		  setAreaNo(request.getParameter("areaNo"));
		  setBankAcc(request.getParameter("bankAcc"));
		  setChangeNo(request.getParameter("changeNo"));
		  setUnionNo(request.getParameter("unionNo"));
		  setProgSetDate(request.getParameter("progSetDate"));
		  setProgramer(request.getParameter("programer"));
		  setProgramerPhone(request.getParameter("programerPhone"));
		  setMngName(request.getParameter("mngName"));
		  setMngPhone(request.getParameter("mngPhone"));
		  setConnectDate(request.getParameter("connectDate"));
		  setCardReaderMode(request.getParameter("cardReaderMode"));
		  setMngCardNo(request.getParameter("mngCardNo"));
		  setOperNo(request.getParameter("operNo"));
		  setAtmLevel(request.getParameter("atmLevel"));
		  setMemo1(request.getParameter("memo1"));
		  setMemo2(request.getParameter("memo2"));
		  setMemo3(request.getParameter("memo3"));
	  }

  /**
   * �Ӳ�ѯ�����ȡATM������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	  public void fetchData(ResultSet rst) throws SQLException{
		  setAtmCode(toucsString.unConvert(rst.getString("atm_code")));
		  //begin add by liyp 20030626
		  setAtmCCode(toucsString.unConvert(rst.getString("atm_c_code")));
		  setAtmDccCode(toucsString.unConvert(rst.getString("atm_dcc_code")));
		  //end addy by liyp
		  setAtmSn(toucsString.unConvert(rst.getString("atm_sn")));
		  setAtmTn(toucsString.unConvert(rst.getString("atm_tn")));
		  setAtmName(toucsString.unConvert(rst.getString("atm_name")));
		  setAtmType(toucsString.unConvert(rst.getString("atm_type")));
		  setAtmMode(toucsString.unConvert(rst.getString("atm_mode")));
		  setTranslateName(toucsString.unConvert(rst.getString("translate_name")));
		  setMsgType(toucsString.unConvert(rst.getString("msg_type")));
		  setNetType(toucsString.unConvert(rst.getString("net_type")));
		  setNetAddress(toucsString.unConvert(rst.getString("net_address")));
		  setNetMask(toucsString.unConvert(rst.getString("net_mask")));
		  setHostAddress(toucsString.unConvert(rst.getString("host_address")));
		  setHostPort(toucsString.unConvert(rst.getString("host_port")));
		  setRouteAddress(toucsString.unConvert(rst.getString("route_address")));
		  setJumpNums(rst.getInt("jump_nums"));
		  setCardType(toucsString.unConvert(rst.getString("card_type")));
		  setUseFlag(toucsString.unConvert(rst.getString("use_flag")));
		  setCurrencyCode(toucsString.unConvert(rst.getString("currency_code")));
		  setSendFlag(toucsString.unConvert(rst.getString("send_flag")));
		  setAtmBuyDate(toucsString.unConvert(rst.getString("atm_buy_date")));
		  setAtmAddrType(toucsString.unConvert(rst.getString("atm_addr_type")));
		  setAtmSetAddr(toucsString.unConvert(rst.getString("atm_set_addr")));
		  setAtmSetDate(toucsString.unConvert(rst.getString("atm_set_date")));
		  setAtmSetDegree(toucsString.unConvert(rst.getString("atm_set_degree")));
		  setAtmNotifyDate(toucsString.unConvert(rst.getString("atm_notify_date")));
		  setAtmSdlc(toucsString.unConvert(rst.getString("atm_sdlc")));
		  setAtmSetUnit(toucsString.unConvert(rst.getString("atm_set_unit")));
		  setAtmMngUnit(toucsString.unConvert(rst.getString("atm_mng_unit")));
		  setAgencyNo(toucsString.unConvert(rst.getString("agency_no")));
		  setBranchNo(toucsString.unConvert(rst.getString("branch_no")));
		  setBranchName(toucsString.unConvert(rst.getString("branch_name")));
		  setUnionName(toucsString.unConvert(rst.getString("union_name")));
		  setOrgId(toucsString.unConvert(rst.getString("org_id")));
		  setTellerId(toucsString.unConvert(rst.getString("teller_id")));
		  setAreaNo(toucsString.unConvert(rst.getString("area_no")));
		  setBankAcc(toucsString.unConvert(rst.getString("bank_acc")));
		  setChangeNo(toucsString.unConvert(rst.getString("change_no")));
		  setUnionNo(toucsString.unConvert(rst.getString("union_no")));
		  setProgSetDate(toucsString.unConvert(rst.getString("prog_set_date")));
		  setProgramer(toucsString.unConvert(rst.getString("programmer")));
		  setProgramerPhone(toucsString.unConvert(rst.getString("programmerphone")));
		  setMngName(toucsString.unConvert(rst.getString("mng_name")));
		  setMngPhone(toucsString.unConvert(rst.getString("mng_phone")));
		  setConnectDate(toucsString.unConvert(rst.getString("connect_date")));
		  setCardReaderMode(toucsString.unConvert(rst.getString("card_reader_mode")));
		  setMngCardNo(toucsString.unConvert(rst.getString("mng_card_no")));
		  setOperNo(toucsString.unConvert(rst.getString("oper_no")));
		  setAtmLevel(toucsString.unConvert(rst.getString("atm_level")));
		  setRunStat(toucsString.unConvert(rst.getString("run_stat")));
		  setMemo1(toucsString.unConvert(rst.getString("memo1")));
		  setMemo2(toucsString.unConvert(rst.getString("memo2")));
		  setMemo3(toucsString.unConvert(rst.getString("memo3")));
	  }

  /**
   * ����������ӵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException
   */
	  public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
		  //52���ֶ�
		  String sql="INSERT INTO atm_info(atm_code,"
              +"atm_sn,atm_tn,atm_name,atm_type,atm_mode,"
              +"translate_name,msg_type,net_type,net_address,net_mask,"
              +"host_address,host_port,route_address,jump_nums,card_type,"
              +"use_flag,currency_code,send_flag,atm_buy_date,atm_addr_type,"
              +"atm_set_addr,atm_set_date,atm_set_degree,atm_notify_date,atm_sdlc,"
              +"atm_set_unit,atm_mng_unit,agency_no,branch_no,branch_name,"
              +"union_name,org_id,teller_id,area_no,bank_acc,"
              +"change_no,union_no,prog_set_date,programmer,programmerphone,"
              +"mng_name,mng_phone,connect_date,card_reader_mode,mng_card_no,"
              +"oper_no,atm_level,memo1,memo2,memo3,atm_cycle,sign_flag,atm_c_code,atm_dcc_code)"
              +" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
              +"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
              +"?,?,?,?,?,?,?,?,?,?,?,?,?)";

		  PreparedStatement stm=conn.prepareStatement(sql);

		  stm.setString(1,atmCode);
		  stm.setString(2,atmSn);
		  stm.setString(3,atmTn);
		  stm.setString(4,atmName);
		  stm.setString(5,atmType);
		  stm.setString(6,atmMode);
	
		  stm.setString(7,translateName);
		  stm.setString(8,msgType);
		  stm.setString(9,netType);
		  stm.setString(10,netAddress);
		  stm.setString(11,netMask);
	
		  stm.setString(12,hostAddress);
		  stm.setString(13,hostPort);
		  stm.setString(14,routeAddress);
		  stm.setInt(15,jumpNums);
		  stm.setString(16,cardType);
	
		  stm.setString(17,useFlag);//����δ���ã����������
		  stm.setString(18,currencyCode);
		  stm.setString(19,sendFlag);
		  stm.setString(20,atmBuyDate);
		  stm.setString(21,atmAddrType);
	
		  stm.setString(22,atmSetAddr);
		  stm.setString(23,atmSetDate);
		  stm.setString(24,atmSetDegree);
		  stm.setString(25,atmNotifyDate);
		  stm.setString(26,atmSdlc);
	
		  stm.setString(27,atmSetUnit);
		  stm.setString(28,atmMngUnit);
		  stm.setString(29,agencyNo);
		  stm.setString(30,branchNo);
		  stm.setString(31,branchName);

		  stm.setString(32,unionName);
		  stm.setString(33,orgId);
		  stm.setString(34,tellerId);
		  stm.setString(35,areaNo);
		  stm.setString(36,bankAcc);
	
		  stm.setString(37,changeNo);
		  stm.setString(38,unionNo);
		  stm.setString(39,progSetDate);
		  stm.setString(40,programer);
		  stm.setString(41,programerPhone);
	
		  stm.setString(42,mngName);
		  stm.setString(43,mngPhone);
		  stm.setString(44,connectDate);
		  stm.setString(45,cardReaderMode);
		  stm.setString(46,mngCardNo);
	
		  stm.setString(47,operNo);
		  stm.setString(48,atmLevel);
		  stm.setString(49,memo1);
		  stm.setString(50,memo2);
		  stm.setString(51,memo3);
		  stm.setInt(52,atmCycle);//���ںų�ʼֵ��1
	
		  stm.setString(53,"0");//ǩ����־����ʼֵΪ"0"(δǩ��)
	
		  //add by liyp 20030626
		  stm.setString(54,atmCCode);
		  stm.setString(55,atmDccCode);

		  return stm;
	  }

  /**
   * ���������޸ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	  public PreparedStatement makeUpdateStm(Connection conn,String key) throws SQLException{
		  //50���ֶΣ�keyָ��Ҫ���µ�ATM�ı��
		  String sql="UPDATE atm_info SET "
	              +"atm_sn=?,atm_tn=?,atm_name=?,atm_type=?,atm_mode=?,"
	              +"translate_name=?,msg_type=?,net_type=?,net_address=?,net_mask=?,"
	              +"host_address=?,host_port=?,route_address=?,jump_nums=?,card_type=?,"
	              +"currency_code=?,atm_buy_date=?,atm_addr_type=?,"
	              +"atm_set_addr=?,atm_set_date=?,atm_set_degree=?,atm_notify_date=?,atm_sdlc=?,"
	              +"atm_set_unit=?,atm_mng_unit=?,agency_no=?,branch_no=?,branch_name=?,"
	              +"union_name=?,org_id=?,teller_id=?,area_no=?,bank_acc=?,"
	              +"change_no=?,union_no=?,prog_set_date=?,programmer=?,programmerphone=?,"
	              +"mng_name=?,mng_phone=?,connect_date=?,card_reader_mode=?,mng_card_no=?,"
	              +"oper_no=?,atm_level=?,memo1=?,memo2=?,memo3=?,atm_c_code=?,atm_dcc_code=?"
	              +" WHERE atm_code=?";

		  PreparedStatement stm=conn.prepareStatement(sql);

	    stm.setString(1,atmSn);
	    stm.setString(2,atmTn);
	    stm.setString(3,atmName);
	    stm.setString(4,atmType);
	    stm.setString(5,atmMode);
	
	    stm.setString(6,translateName);
	    stm.setString(7,msgType);
	    stm.setString(8,netType);
	    stm.setString(9,netAddress);
	    stm.setString(10,netMask);
	
	    stm.setString(11,hostAddress);
	    stm.setString(12,hostPort);
	    stm.setString(13,routeAddress);
	    stm.setInt(14,jumpNums);
	    stm.setString(15,cardType);
	
	    stm.setString(16,currencyCode);
	    stm.setString(17,atmBuyDate);
	    stm.setString(18,atmAddrType);

	    stm.setString(19,atmSetAddr);
	    stm.setString(20,atmSetDate);
	    stm.setString(21,atmSetDegree);
	    stm.setString(22,atmNotifyDate);
	    stm.setString(23,atmSdlc);
	
	    stm.setString(24,atmSetUnit);
	    stm.setString(25,atmMngUnit);
	    stm.setString(26,agencyNo);
	    stm.setString(27,branchNo);
	    stm.setString(28,branchName);
	
	    stm.setString(29,unionName);
	    stm.setString(30,orgId);
	    stm.setString(31,tellerId);
	    stm.setString(32,areaNo);
	    stm.setString(33,bankAcc);
	
	    stm.setString(34,changeNo);
	    stm.setString(35,unionNo);
	    stm.setString(36,progSetDate);
	    stm.setString(37,programer);
	    stm.setString(38,programerPhone);
	
	    stm.setString(39,mngName);
	    stm.setString(40,mngPhone);
	    stm.setString(41,connectDate);
	    stm.setString(42,cardReaderMode);
	    stm.setString(43,mngCardNo);

	    stm.setString(44,operNo);
	    stm.setString(45,atmLevel);
	    stm.setString(46,memo1);
	    stm.setString(47,memo2);
	    stm.setString(48,memo3);

        //add by liyp 20030626
        stm.setString(49,atmCCode);
        stm.setString(50,atmDccCode);

        stm.setString(51,key);

        return stm;
	  }

	  public boolean DataIsChange() throws SQLException{
		  SqlAccess sqtmp = new SqlAccess();
		  String sql="SELECT atm_dcc_code,atm_c_code,atm_level,org_id,area_no,"
			  +"atm_type, card_type,currency_code,translate_name,"
			  +"msg_type,net_type,teller_id,net_address"
			  +" FROM atm_info WHERE atm_code='"+atmCode+"'";
		  ResultSet rsttmp=sqtmp.queryselect(sql);
		  String buf = "";
		  if(rsttmp.next()){
			  buf = toucsString.unConvert(rsttmp.getString(1));
			  if(!buf.equals(atmDccCode)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(2));
			  if(!buf.equals(atmCCode)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(3));
			  if(!buf.equals(atmLevel)) {
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(4));
			  if(!buf.equals(orgId)) {
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(5));
			  if(!buf.equals(areaNo)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(6));
			  if(!buf.equals(atmType)){
				  sqtmp.close();
				  return true;
			  }

			  buf = toucsString.unConvert(rsttmp.getString(7));
			  if(!buf.equals(cardType)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(8));
			  if(!buf.equals(currencyCode)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(9));
			  if(!buf.equals(translateName)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(10));
			  if(!buf.equals(msgType)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(11));
			  if(!buf.equals(netType)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(12));
			  if(!buf.equals(tellerId)){
				  sqtmp.close();
				  return true;
			  }
			  buf = toucsString.unConvert(rsttmp.getString(13));
			  if(!buf.equals(netAddress)){
				  sqtmp.close();
				  return true;
			  }
		  } else {
			  sqtmp.close();
			  return false;
		  }
		  sqtmp.close();
		  return false;
	  }
	  public static void main(String[] args) {

	  }

	  //�������ͣ�1Ϊ��ӣ�2Ϊ�޸�
	  public int oper;	
	}
