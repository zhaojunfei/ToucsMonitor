package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ATM状态信息类</p>
 * <p>Description: 封装ATM设备的详细状态信息</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ATMStatInfo {
	//设备编号
	public String atmCode="";
	//设备名称
	public String atmName="";
	//最后上送日期
	public String lastDate="";
	//最后上送时间
	public String lastTime="";
	//设备状态
	public String atmStat="";
	//流水打印机状态
	public String atmJnl="";
	//凭条打印机状态
	public String atmRecpt="";
	//读卡器状态
	public String atmReadW="";
	//IC卡读卡模块状态
	public String ICRead="";
	//发钞模块状态
	public String atmCdm="";
	//硬币钱箱状态
	public String atmCoin="";
	//存款模块状态
	public String atmDep="";
	//硬加密模块状态
	public String atmDem="";
	//报警器（金库门）状态
	public String atmAlm="";
	//触摸屏状态
	public String touchStat="";
	//8个钞箱状态
	public String feeBox="";
	//键盘状态
	public String atmKeyboard="";
	//硬盘状态
	public String hardDisk="";
	//PSAM模块状态
	public String atmPsam="";
	//控制板状态
	public String ctrlFace="";
	//保险箱状态
	public String safeBox="";
	//吞卡数目
	public String cardNum="";
	//网络状态
	public String netStat="";
	//发送命令
	public String atmCmd="";

	  /**
	   * 从查询结果中取设备状态信息
	   * @param rst 查询结果集
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
	   * 转换为XML格式报文，添加到字符串缓冲对象中
	   * @param buf 字符串缓冲对象
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
	   * 转换为XML格式报文(详细状态信息)，添加到字符串缓冲对象中
	   * @param buf 字符串缓冲对象
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
	   * 转换为XML格式报文
	   * @return XML格式报文
	   */
	public String toXML(){
		StringBuffer buf=new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

	  /**
	   * 构造方法
	   */
	public ATMStatInfo(){
	}

	  /**
	   * 取钞箱状态
	   * @param idx 钞箱索引号
	   * @return 钞箱状态
	   */
	public String getChashBoxStat(int idx){
		if(feeBox==null||idx<0||idx>8||idx>feeBox.length())
			return "";
		else{
			return feeBox.substring(idx-1,idx);
		}
	}

	  /**
	   * 取存款钞箱状态
	   * @param idx 存款钞箱索引号
	   * @return 存款钞箱状态
	   */
	public String getSaveChashBoxStat(int idx){
		if(atmDep==null||idx<0||idx>8||idx>atmDep.length())
			return "";
		else
			return atmDep.substring(idx,idx);
	}

	  /**
	   * 取硬币钞箱状态
	   * @param idx 硬币钞箱索引号
	   * @return 硬币钞箱状态
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