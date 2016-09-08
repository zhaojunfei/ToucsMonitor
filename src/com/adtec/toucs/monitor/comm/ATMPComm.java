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
 * 该类用来连接ATMP主机系统，getCommKey方法没有实现，留带下期工程实现
 */

public class ATMPComm {
	private Socket soc=null;  //通讯Soket
	private String ip="";  //主机Ip
	private int port=0;    //主机端口
	private BufferedWriter buffos=null;  //输出流
	private BufferedReader buffis=null;  // 输入流
	private int ConnATMPTimeOut=10000;   //超时时间

  /**
   *构造函数
   * @param inHost  主机名
   * @param inPort  主机端口
   * @param timOut  连接超时时间
   */
	public ATMPComm(String inHost,int inPort,int timOut) {
		System.out.println("-----ATMPComm------------------public ATMPComm(String inHost,int inPort,int timOut) {------------------");
		ip=inHost;
		port=inPort;
		ConnATMPTimeOut=timOut;
	}
  /**
   * 连接ATMP
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
   * 断开连接
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
   *与ATMP通讯，向ATMP发起交易请求，并取得结果
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
   *从ATMP取得通讯密钥
   * @return strbuff
   * @throws MonitorException
   */
	public StringBuffer getCommKey () throws MonitorException{
		connect();
		String getKeyData=null; //密钥取得报文
		StringBuffer inMSG=new StringBuffer();
		inMSG.append(getKeyData);
		StringBuffer strBuff=null;
		Messages.writeMessage(inMSG,buffos);
		strBuff=Messages.readMessage(buffis,"</Deal>");
		diconnect();
		return strBuff;
	}
}