
package com.adtec.toucs.monitor.common;

import java.util.*;
import com.adtec.toucs.monitor.comm.*;


/**
 * ͨ��Socket�������ݵ�ATMP��
 * �����װ�˱��ĸ�ʽ��֯ʵ�֣�ֻ��Ҫ����ָ���Ĳ����������֯��ATMP����ı���
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 */

public class CommToATMP {   //implements Runnable
	String message="";
	public static String ATMPHost="";  //ATMP������
	public static int ATMPPort=0;  //ATMPͨѶ�˿�
	public static int timeOut=1000; //ͨѶ��ʱʱ��
	private  List retXMLMessage=null; //���ر���
	private  String retErrorDesc=null;//���صĴ�������
	private  String getStrBuff=null;
	private  String getResCode=null;

  /**
   * ���캯��
   * @param DealCode  ������
   * @param ATMDeviceNO  �豸��
   * @param orgCode   ��֯������
   */
	public CommToATMP(String DealCode,String ATMDeviceNO,String orgCode) {
		message=makeMessage(DealCode,ATMDeviceNO,orgCode);
		System.out.println("-----CommToATMP-----------CommToATMP����-------------");
	}

  /**
   * ���ɱ���
   * @param DealCode  ������
   * @param ATMDeviceNO  �豸��
   * @param orgCode    ��֯Code
   * @return  ���ɵı���
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
   * ��ATMP����������
   * @return �������󷵻���
   */
	public String commitToATMP() throws MonitorException{
		System.out.println("------------CommToATMP-----------------public String commitToATMP() --------");
		String retReqCode="";
		//���ATMPҪ���ط������ط����Ρ�
		//������ζ����ɹ����׳��쳣�������û���ATMPͨѶʧ��
		for(int i=0;i<3;i++){
			retReqCode=commATM(message);
			if(retReqCode.equals("19")) continue;  //���ATMPҪ���ط�,������ط�
			else return retReqCode;   //���򷵻ش�����
		}
		//������ζ����ɹ����׳��쳣�������û���ATMPͨѶʧ��
		throw new MonitorException(retErrorDesc,retErrorDesc);
	}

  /**
   * ��ATMP���������󣬷��ؽ��״��������
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
		retStrBuff=atmComm.commWithATMP(bufMessage);//�˷���������Կ��
	//	?xml version="1.0" encoding="ISO-8859-1"?>
	//	<Deal>
	//	<DealCode>MG7002</DealCode>
	//	<RequestCode>00</RequestCode>
	//	<RequestMsg>�̻�[105110060121264] ����Կ[a6a29e9b97938f8b] ��Կ���[01] PINKey[c4133e9a9c4b4ba6] MACKey[cff1f38f69cc771c]</RequestMsg>
	//	</Deal>
		//����ATMP���صı���
		Debug.println("Received From ATMP��["+retStrBuff.toString()+"] ����Ϊ��["+retStrBuff.toString().length()+"]\n");
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
		//ȡ�ñ����еĴ����롣���Ϊ0����ʾ�ɹ���
		retCode=messageParse.getRequestCode();
		retErrorDesc=messageParse.getRequestMsg();

		Debug.println("Caxsiel request msg"+getStrBuff);
		Debug.println("Caxsiel request err"+retErrorDesc);
		return retCode;
	}

  /**
   * ȡ�ô��󷵻ر��ĵĴ�������
   * @return
   */
	public String getErrorDesc(){
		return retErrorDesc;
	}
  /**
   * ȡ��ATMP���ر���
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