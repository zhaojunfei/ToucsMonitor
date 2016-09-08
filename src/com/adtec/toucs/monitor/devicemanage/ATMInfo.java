package com.adtec.toucs.monitor.devicemanage;


import java.sql.*;

import java.io.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM设备配置信息类 </p>
 * <p>Description: 封装ATM设备配置信息</p>
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

	//设备编号
	private String atmCode="";

	//begin add by liyp 20030626
	//atm_c_code
	private String atmCCode="";
	//atm_dcc_code
	private String atmDccCode="";
	//end add by liyp 20030626

	//设备序号
	private String atmSn="";
	//设备跟踪号
	private String atmTn="";
	//设备名称
	private String atmName="";
	//设备类型
	private String atmType="";
	//设备型号
	private String atmMode="";
	//报文传输标准名称
	private String translateName="";
	//报文类型
	private String msgType="";
	//通讯类型
	private String netType="";
	//网络通讯地址
	private String netAddress="";
	//子网掩码
	private String netMask="";
	//ATM主机地址(前置机地址)
	private String hostAddress="";
	//ATM主机端口(前置机端口)
	private String hostPort="";
	//ATM路由器地址(网关地址)
	private String routeAddress="";
	//网段数(跃点数)
	private int jumpNums=0;
	//设备周期号
	private int atmCycle=1;
	//交易卡种
	private String cardType="";
	//加载标志
	private String useFlag="2";
	//币种
	private String currencyCode="";
	//发送标志
	private String sendFlag="0";
	//设备购入日期
	private String atmBuyDate="";
	//设备安装详细地址
	private String atmSetAddr="";
	//设备安装地点类型
	private String atmAddrType="";
	//设备安装日期
	private String atmSetDate="";
	//ATM设备安装批次
	private String atmSetDegree="";
	//ATM设备物理通知日期
	private String atmNotifyDate="";
	//SDLC
	private String atmSdlc="";
	//设备安装单位
	private String atmSetUnit="";
	//设备管理单位
	private String atmMngUnit="";
	//支行号
	private String agencyNo="";
	//储蓄所号
	private String branchNo="";
	//储蓄所名称
	private String branchName="";
	//开户行
	private String unionName="";
	//机构编码
	private String orgId="";
	//ATM柜员号
	private String tellerId="";
	//地区编码
	private String areaNo="";
	//银行帐号
	private String bankAcc="";
	//交换号
	private String changeNo="";
	//联行号
	private String unionNo="";
	//软件安装日期
	private String progSetDate="";
	//软件安装程序员姓名
	private String programer="";
	//软件安装程序员电话
	private String programerPhone="";
	//管理员姓名
	private String mngName="";
	//管理员电话
	private String mngPhone="";
	//设备开通日期
	private String connectDate="";
	//读卡器类型
	private String cardReaderMode="";
	//管理卡号
	private String mngCardNo="";
	//操作员柜员号
	private String operNo="";
	//设备状态
	private String atmStat="";
	//设备运行状态
	private String runStat="";
	//设备重要程度
	private String atmLevel="";
	//上月平均日取款金额
	private float lastMmddAmount=0;
	//上月平均日取款笔数
	private int lastMmddCount=0;
	//备注1
	private String memo1="";
	//备注2
	private String memo2="";
	//备注3
	private String memo3="";
	//钞箱配置
	private AtmBoxInfo boxInfo=null;
	//密钥配置
	private AtmKeyInfo keyInfo=null;
	//状态信息
	private ATMStatInfo statInfo=null;

	  //构造方法
	  public ATMInfo() {
	    oper=1;
	  }
	
	  //设备编号属性读写
	  public void setAtmCode(String code){atmCode=code;}
	  public String getAtmCode(){return atmCode;}
	
	  //设备C编号属性读写 add by liyp 20030626
	  public void setAtmCCode(String code){atmCCode=code;}
	  public String getAtmCCode(){return atmCCode;}
	
	  //设备DCC编号属性读写 add by liyp 20030626
	  public void setAtmDccCode(String code){atmDccCode=code;}
	  public String getAtmDccCode(){return atmDccCode;}
	
	  //设备名称属性读写
	  public void setAtmName(String name){atmName=name;}
	  public String getAtmName(){return atmName;}
	
	  //通讯地址属性读写
	  public void setNetAddress(String address){netAddress=address;}
	  public String getNetAddress(){return netAddress;}
	
	  //子网掩码属性读写
	  public void setNetMask(String mask){netMask=mask;}
	  public String getNetMask(){return netMask;}

	  //ATM主机地址(前置机地址)属性读写
	  public void setHostAddress(String address){hostAddress=address;}
	  public String getHostAddress(){return hostAddress;}
	
	  //ATM主机端口(前置机端口)属性读写
	  public void setHostPort(String port){hostPort=port;}
	  public String getHostPort(){return hostPort;}
	
	  //ATM路由器地址(网关地址)属性读写
	  public void setRouteAddress(String address){routeAddress=address;}
	  public String getRouteAddress(){return routeAddress;}
	
	  //网段数(跃点数)属性读写
	  public void setJumpNums(int nums){jumpNums=nums;}
	  public int getJumpNums(){return jumpNums;}
	
	  //设备周期属性读写
	  public void setAtmCycle(int cycle){atmCycle=cycle;}
	  public int getAtmCycle(){return atmCycle;}
	
	  //交易卡种属性读写
	  public void setCardType(String type){cardType=type;}
	  public String getCardType(){return cardType;}
	
	  //加载标志属性读写
	  public void setUseFlag(String flag){useFlag=flag;}
	  public String getUseFlag(){return useFlag;}
	
	  //币种属性读写
	  public void setCurrencyCode(String code){currencyCode=code;}
	  public String getCurrencyCode(){return currencyCode;}
	
	  //发送标志属性读写
	  public void setSendFlag(String flag){sendFlag=flag;}
	  public String getSendFlag(){return sendFlag;}
	
	  //设备类型属性读写
	  public void setAtmType(String type){atmType=type;}
	  public String getAtmType(){return atmType;}
	
	  //设备型号属性读写
	  public void setAtmMode(String mode){atmMode=mode;}
	  public String getAtmMode(){return atmMode;}
	
	  //报文传输标准名称属性读写
	  public void setTranslateName(String name){translateName=name;}
	  public String getTranslateName(){return translateName;}
	
	  //报文类型属性读写
	  public void setMsgType(String type){msgType=type;}
	  public String getMsgType(){return msgType;}
	
	  //通讯类型属性读写
	  public void setNetType(String type){netType=type;}
	  public String getNetType(){return netType;}
	
	  //设备跟踪号属性读写
	  public void setAtmTn(String tn){atmTn=tn;}
	  public String getAtmTn(){return atmTn;}
	
	  //设备序号属性读写
	  public void setAtmSn(String sn){atmSn=sn;}
	  public String getAtmSn(){return atmSn;}
	
	  //设备购入日期属性读写
	  public void setAtmBuyDate(String date){atmBuyDate=date;}
	  public String getAtmBuyDate(){return atmBuyDate;}
	
	  //设备安装详细地址属性读写
	  public void setAtmSetAddr(String addr){atmSetAddr=addr;}
	  public String getAtmSetAddr(){return atmSetAddr;}
	
	  //设备安装地点类型属性读写
	  public void setAtmAddrType(String type){atmAddrType=type;}
	  public String getAtmAddrType(){return atmAddrType;}
	
	  //设备安装日期属性读写
	  public void setAtmSetDate(String date){atmSetDate=date;}
	  public String getAtmSetDate(){return atmSetDate;}
	
	  //ATM设备安装批次属性读写
	  public void setAtmSetDegree(String degree){atmSetDegree=degree;}
	  public String getAtmSetDegree(){return atmSetDegree;}
	
	  //ATM设备物理通知日期属性读写
	  public void setAtmNotifyDate(String date){atmNotifyDate=date;}
	
	  //SDLC属性读写
	  public void setAtmSdlc(String sdlc){atmSdlc=sdlc;}
	  public String getAtmSdlc(){return atmSdlc;}
	
	  //设备安装单位属性读写
	  public void setAtmSetUnit(String unit){atmSetUnit=unit;}
	  public String getAtmSetUnit(){return atmSetUnit;}
	
	  //设备安装管理单位属性读写
	  public void setAtmMngUnit(String unit){atmMngUnit=unit;}
	  public String getAtmMngUnit(){return atmMngUnit;}
	
	  //支行号属性读写
	  public void setAgencyNo(String no){agencyNo=no;}
	  public String getAgencyNo(){return agencyNo;}
	
	  //储蓄所号属性读写
	  public void setBranchNo(String no){branchNo=no;}
	  public String getBranchNo(){return branchNo;}
	
	  //储蓄所名称属性读写
	  public void setBranchName(String name){branchName=name;}
	  public String getBranchName(){return branchName;}
	
	  //开户行属性读写
	  public void setUnionName(String name){unionName=name;}
	  public String getUnionName(){return unionName;}
	
	  //机构编码属性读写
	  public void setOrgId(String id){orgId=id;}
	  public String getOrgId(){return orgId;}
	
	  //柜员号属性读写
	  public void setTellerId(String id){tellerId=id;}
	  public String getTellerId(){return tellerId;}
	
	  //地区编码属性读写
	  public void setAreaNo(String no){areaNo=no;}
	  public String getAreaNo(){return areaNo;}
	
	  //银行帐号属性读写
	  public void setBankAcc(String acc){bankAcc=acc;}
	  public String getBankAcc(){return bankAcc;}
	
	  //交换号属性读写
	  public void setChangeNo(String no){changeNo=no;}
	  public String getChangeNo(){return changeNo;}
	
	  //联行号属性读写
	  public void setUnionNo(String no){unionNo=no;}
	  public String getUnionNo(){return unionNo;}
	
	  //软件安装日期属性读写
	  public void setProgSetDate(String date){progSetDate=date;}
	  public String getProgSetDate(){return progSetDate;}
	
	  //软件安装程序员姓名属性读写
	  public void setProgramer(String pgr){programer=pgr;}
	  public String getProgramer(){return programer;}
	
	  //软件安装程序员电话属性读写
	  public void setProgramerPhone(String phone){programerPhone=phone;}
	  public String getProgramerPhone(){return programerPhone;}
	
	  //管理员姓名属性读写
	  public void setMngName(String name){mngName=name;}
	  public String getMngName(){return mngName;}
	
	  //管理员电话属性读写
	  public void setMngPhone(String phone){mngPhone=phone;}
	  public String getMngPhone(){return mngPhone;}
	
	  //设备开通日期属性读写
	  public void setConnectDate(String date){connectDate=date;}
	  public String getConnectDate(){return connectDate;}
	
	  //读卡器类型属性读写
	  public void setCardReaderMode(String mode){cardReaderMode=mode;}
	  public String getCardReaderMode(){return cardReaderMode;}
	
	  //管理卡号属性读写
	  public void setMngCardNo(String no){mngCardNo=no;}
	  public String getMngCardNo(){return mngCardNo;}
	
	  //柜员号属性读写
	  public void setOperNo(String no){operNo=no;}
	  public String getOperNo(){return operNo;}
	
	  //设备状态属性读写
	  public void setAtmStat(String stat){atmStat=stat;}
	  public String getAtmStat(){return atmStat;}
	
	  //设备运行状态属性读写
	  public void setRunStat(String stat){runStat=stat;}
	  public String getRunStat(){return runStat;}
	
	  //设备重要程度属性读写
	  public void setAtmLevel(String level){atmLevel=level;}
	  public String getAtmLevel(){return atmLevel;}
	
	  //上月平均日取款金额属性读写
	  public void setLastMmddAmount(float amount){lastMmddAmount=amount;}
	  public float getLastMmddAmount(){return lastMmddAmount;}
	
	  //上月平均日取款笔数属性读写
	  public void setLastMmddCount(int count){lastMmddCount=count;}
	  public int getLastMmddCount(){return lastMmddCount;}
	
	  //备注1属性读写
	  public void setMemo1(String m){memo1=m;}
	  public String getMemo1(){return memo1;}
	
	  //备注2属性读写
	  public void setMemo2(String m){memo2=m;}
	  public String getMemo2(){return memo2;}
	
	  //备注3属性读写
	  public void setMemo3(String m){memo3=m;}
	  public String getMemo3(){return memo3;}
	
	  //钞箱信息属性读写
	  public void setBoxInfo(AtmBoxInfo info){boxInfo=info;}
	  public AtmBoxInfo getBoxInfo(){return boxInfo;}
	
	  //密钥配置信息属性读写
	  public void setKeyInfo(AtmKeyInfo info){
	    keyInfo=info;
	    if(info!=null)
	      info.setAtmCode(atmCode);
	  }
	  public AtmKeyInfo getKeyInfo(){return keyInfo;}
	
	  //状态信息属性读写
	  public void setStatInfo(ATMStatInfo info){statInfo=info;}
	  public ATMStatInfo getStatInfo(){return statInfo;}

	  /**
	   * 覆盖toString()方法
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
   * 从Http请求中取ATM基本信息
   * @param request Http请求
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
   * 从查询结果中取ATM基本信息
   * @param rst 查询结果集
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
   * 创建用于添加的动态SQL语句
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	  public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
		  //52个字段
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
	
		  stm.setString(17,useFlag);//钞箱未配置，不允许加载
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
		  stm.setInt(52,atmCycle);//周期号初始值是1
	
		  stm.setString(53,"0");//签到标志，初始值为"0"(未签到)
	
		  //add by liyp 20030626
		  stm.setString(54,atmCCode);
		  stm.setString(55,atmDccCode);

		  return stm;
	  }

  /**
   * 创建用于修改的动态SQL语句
   * @param conn 数据库连接对象
   * @param key 设备编号（关键字）
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	  public PreparedStatement makeUpdateStm(Connection conn,String key) throws SQLException{
		  //50个字段，key指定要更新的ATM的编号
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

	  //操作类型：1为添加，2为修改
	  public int oper;	
	}
