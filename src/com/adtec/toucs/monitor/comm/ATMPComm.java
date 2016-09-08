//package comm;

package com.adtec.toucs.monitor.comm;

import java.net.*;
import java.io.*;
import com.adtec.toucs.monitor.common.*;
/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 * ������������ATMP����ϵͳ��getCommKey����û��ʵ�֣��������ڹ���ʵ��
 */

public class ATMPComm {
	private Socket soc=null;  //ͨѶSoket
	private String ip="";  //����Ip
	private int port=0;    //�����˿�
	private BufferedWriter buffos=null;  //�����
	private BufferedReader buffis=null;  // ������
	private int ConnATMPTimeOut=10000;   //��ʱʱ��

  /**
   *���캯��
   * @param inHost  ������
   * @param inPort  �����˿�
   * @param timOut  ���ӳ�ʱʱ��
   */
	public ATMPComm(String inHost,int inPort,int timOut) {
		System.out.println("-----ATMPComm------------------public ATMPComm(String inHost,int inPort,int timOut) {------------------");
		ip=inHost;
		port=inPort;
		ConnATMPTimeOut=timOut;
	}
  /**
   * ����ATMP
   */
	private void connect() throws MonitorException{
		try{
			
			if(soc==null) soc=new Socket(ip,port);
			soc.setSoTimeout(ConnATMPTimeOut);
			buffos=new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
			buffis=new BufferedReader(new InputStreamReader(soc.getInputStream()));
			System.out.println("--ATMPComm----connect()---buffis--"+buffis);
		}catch(Exception exp){
			soc=null;
			buffos=null;
			buffis=null;
			exp.printStackTrace();
			MonitorException def=new MonitorException(ErrorDefine.CONNECTERROR,ErrorDefine.CONNECTERRORDESC);
			throw def;
		}
	}
  /**
   * �Ͽ�����
   */
	private void diconnect() {
		try{
			buffos.flush() ;
			buffos.close() ;
			buffis.close();
			soc.close();
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}

  /**
   *��ATMPͨѶ����ATMP���������󣬲�ȡ�ý��
   * @param inMSG
   * @return strBuff
   * @throws MonitorException
   */
	public StringBuffer commWithATMP (StringBuffer inMSG) throws MonitorException{
	
		connect();
		if(inMSG==null) {
			throw new MonitorException(ErrorDefine.SENDDATAISNULL,ErrorDefine.SENDDATAISNULLDESC);
		}
		StringBuffer strBuff=null;
		
		Messages.writeMessage(inMSG,buffos);
		System.out.println("----commWithATMP---buffis--"+buffis);
		strBuff=Messages.readMessage(buffis,"</Deal>");
		
		diconnect();
		
		return strBuff;
	}
	
  /**
   *��ATMPȡ��ͨѶ��Կ
   * @return strbuff
   * @throws MonitorException
   */
	public StringBuffer getCommKey () throws MonitorException{
		connect();
		String getKeyData=null; //��Կȡ�ñ���
		StringBuffer inMSG=new StringBuffer();
		inMSG.append(getKeyData);
		StringBuffer strBuff=null;
		Messages.writeMessage(inMSG,buffos);
		strBuff=Messages.readMessage(buffis,"</Deal>");
		diconnect();
		return strBuff;
	}
}