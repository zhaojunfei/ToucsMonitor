
package com.adtec.toucs.monitor.common;

import java.util.*;
import com.adtec.toucs.monitor.comm.*;


/**
 * 通过Socket发送数据到ATMP。
 * 该类封装了报文格式组织实现，只需要传入指定的参数，则可组织向ATMP发起的报文
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 */

public class CommToATMP {   //implements Runnable
	String message="";
	public static String ATMPHost="";  //ATMP主机名
	public static int ATMPPort=0;  //ATMP通讯端口
	public static int timeOut=1000; //通讯超时时间
	private  List retXMLMessage=null; //返回报文
	private  String retErrorDesc=null;//返回的错误描述
	private  String getStrBuff=null;
	private  String getResCode=null;

  /**
   * 构造函数
   * @param DealCode  交易码
   * @param ATMDeviceNO  设备号
   * @param orgCode   组织机构号
   */
	public CommToATMP(String DealCode,String ATMDeviceNO,String orgCode) {
		message=makeMessage(DealCode,ATMDeviceNO,orgCode);
		System.out.println("-----CommToATMP-----------CommToATMP构造-------------");
	}

  /**
   * 生成报文
   * @param DealCode  交易码
   * @param ATMDeviceNO  设备号
   * @param orgCode    组织Code
   * @return  生成的报文
   */
	private String makeMessage(String DealCode,String ATMDeviceNO,String orgCode){
		System.out.println("-----------CommToATMP-------------makeMessage----------");
		String message="";
		message="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		message=message+"<Deal>\n";
		message=message+"<DealCode>"+DealCode+"</DealCode>\n";
		message=message+"<DeviceNO>"+ATMDeviceNO+"</DeviceNO>\n";
		message=message+"<OrgId>"+orgCode+"</OrgId>\n";
		message=message+"</Deal> |\n";

		Debug.println("*********wuye debug ***makemessage() "+ message +"******");
		return message;
	}

  /**
   * 向ATMP发起交易请求
   * @return 交易请求返回码
   */
	public String commitToATMP() throws MonitorException{
		System.out.println("------------CommToATMP-----------------public String commitToATMP() --------");
		String retReqCode="";
		//如果ATMP要求重发，则重发三次。
		//如果三次都不成功则抛出异常，告诉用户与ATMP通讯失败
		for(int i=0;i<3;i++){
			retReqCode=commATM(message);
			if(retReqCode.equals("19")) continue;  //如果ATMP要求重发,则继续重发
			else return retReqCode;   //否则返回错误码
		}
		//如果三次都不成功则抛出异常，告诉用户与ATMP通讯失败
		throw new MonitorException(retErrorDesc,retErrorDesc);
	}

  /**
   * 向ATMP发起交易请求，返回交易处理代代码
   * @param inMessage
   * @return retCode 
   * @throws MonitorException
   */
	private String commATM(String inMessage) throws MonitorException{
		System.out.println("--------------------CommToATMP-----------------------private String commATM(String inMessage) -----------------");
		StringBuffer bufMessage=new StringBuffer(inMessage);
		StringBuffer retStrBuff=null;
		ATMPComm atmComm=new ATMPComm(ATMPHost,ATMPPort,timeOut);
		//<?xml version="1.0" encoding="ISO-8859-1"?>
	//	<Deal>
	//	<DealCode>MG7002</DealCode>
	//	<DeviceNO>105110060121264</DeviceNO>
		//<OrgId>1</OrgId>
		//</Deal> |
		//
		System.out.println("----------bufMessage------"+bufMessage);
		retStrBuff=atmComm.commWithATMP(bufMessage);//此方法生成密钥。
	//	?xml version="1.0" encoding="ISO-8859-1"?>
	//	<Deal>
	//	<DealCode>MG7002</DealCode>
	//	<RequestCode>00</RequestCode>
	//	<RequestMsg>商户[105110060121264] 主密钥[a6a29e9b97938f8b] 密钥序号[01] PINKey[c4133e9a9c4b4ba6] MACKey[cff1f38f69cc771c]</RequestMsg>
	//	</Deal>
		//解析ATMP返回的报文
		Debug.println("Received From ATMP：["+retStrBuff.toString()+"] 长度为：["+retStrBuff.toString().length()+"]\n");
		MessageParse messageParse=null;
		messageParse=new MessageParse(retStrBuff);
		messageParse.xmlParse();
		List retXMLMessage=messageParse.getRootData();
		String retCode="";
		if(retXMLMessage==null){
			throw new MonitorException(ErrorDefine.RETMESSAGEISNULL,ErrorDefine.RETMESSAGEISNULLDESC);
		}
		getResCode = messageParse.getRequestCode();
		getStrBuff = messageParse.getRequestMsg();
		//取得报文中的错误码。如果为0，表示成功。
		retCode=messageParse.getRequestCode();
		retErrorDesc=messageParse.getRequestMsg();

		Debug.println("Caxsiel request msg"+getStrBuff);
		Debug.println("Caxsiel request err"+retErrorDesc);
		return retCode;
	}

  /**
   * 取得错误返回报文的错误描述
   * @return
   */
	public String getErrorDesc(){
		return retErrorDesc;
	}
  /**
   * 取得ATMP返回报文
   * @return
   */
	public List getRetXMLMessage(){
		return retXMLMessage;
	}
  
	public String getStrBuffer(){
		return getStrBuff;
	}
  
	public String getRequestCode(){
		return getResCode;
	}
}