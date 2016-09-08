//package comm;
package com.adtec.toucs.monitor.comm;


import java.net.*;

import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;

/**
 * 该类用来处理与客户端连接请求。
 * 线程启动后，如果没有信息需要发送则阻塞等待
 * 可以通过调用addDataToQueue方法添加数据到发送队列中，添加数据到发送队列后需要唤醒该线程
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 *
 */
public class WatchProcess implements Runnable{

	private Socket soc=null;   //通讯Socket
	private List listSendData=null;  //要发送的数据队列
	private List watchProcessQue=null;//与客户端通讯线程队列
	private BufferedWriter buffos=null;//输入流

	public WatchProcess(List inWatchProcessQue,Socket inSoc) {
		this.soc=inSoc;
		watchProcessQue=inWatchProcessQue;
		listSendData=new java.util.ArrayList();
		try{
			buffos=new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
		}catch(Exception exp){
			buffos=null;
		}
	}
	
	public void run(){
		boolean isrun=true;
		while(isrun){
			if(listSendData.size()>0){
				try{
					sendData();
				}catch(MonitorException exp){
					System.out.println("send data error then exit");
					isrun=false;
					watchProcessQue.remove(this);
					try{
						soc.close();
					}catch(Exception exp1){
						exp1.printStackTrace();
					}
					break;
				}
			}else{
				try{
					synchronized(this){
						wait();
					}
				}catch(Exception e){ 
					e.printStackTrace();
				}
			}
		}
	}
  /**
   * 添加新数据到数据队列中
   * @param inBuff 要发送的数据
   */
	public void addDataToQueue (StringBuffer inBuff){
		if(inBuff==null) return ;
		if(inBuff.length()==0) return ;
		//加密数据
		listSendData.add(inBuff);
	}

   /**
    *发送数据到客户端
    * @param
    */
	private void sendData() throws MonitorException{
		for(int i=0;i<listSendData.size();){
			StringBuffer tmpSendData=(StringBuffer)listSendData.get(i);
			try{
				Messages.writeMessage(tmpSendData,buffos);
				synchronized(listSendData){
					listSendData.remove(0);
				}
			}catch(MonitorException exp){
				exp.printStackTrace();
				throw exp;
			}
		}
	}

   /**
    *清除数据队列中的数据
    */
	public void clearSendData(){
		listSendData.clear();
	}

   /**
    * 发送通知给指定IP（暂时不做）
    */
	public int NotifyClien(String buff){
		return 0;
	}

   /**
    * 取得客户端IP地址
    */
	public String getClientIP(){
		return "";
	}
   /**
    * 取得客户端连接的套接字
    */
   	public Socket getSoket(){
   		return soc;
   	}
}