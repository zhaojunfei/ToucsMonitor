//package comm;
package com.adtec.toucs.monitor.comm;


import java.net.*;

import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.*;


/**
 * 提供监控服务端与客户端的监控服务，线程启动后，socket服务也启动了。
 * 如果要添加发送某类数据，则通过调用sendDataToAllClient
 * 方法发送数据到所有当前连接的客户端
 * 如果要发送一个数据到具体某一客户，则调用NotifyClien发送到具体某一指定IP,当前sendDataToOneClient
 * 还有问题，暂时不能用
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed lichj
 * @version 1.0
 */
public class WatchServer implements Runnable{
	//数据加密标志
	public static boolean EncryptData=false;
	//消息结束标志
	public static String MSG_FLAG=" |";
	//与客户端通讯线程队列
	private List  watchProcessQue=null;
	//与客户端通讯服务端口
	private static ServerSocket sslSrvSocket=null;

	//通讯端口
	int port=0;
	//错误代码
	static int ERROECODE=0;

	public WatchServer(int inPort) {
		watchProcessQue=new ArrayList();
		this.port=inPort;
		try{
			if(sslSrvSocket==null)
				sslSrvSocket=new ServerSocket(port);
		}catch(Exception e){
			ERROECODE=-1;//如果SSLSocket出错，则设置错误码
		}
	}

	public void run() {
		if(ERROECODE<0) return ;//如果SSLSocket初始化出错，则直接推出。
		int i=0;
		try{
			while(true){
				Socket socket=null;
				socket=sslSrvSocket.accept();
				try{
					i++;
					WatchProcess watchProcess=new WatchProcess(watchProcessQue,socket);
					watchProcessQue.add(watchProcess);
					Thread t=new Thread(watchProcess);
					t.start();
				}catch(Exception exp1){
					exp1.printStackTrace();
					try{
						socket.close();
					}
					catch(Exception exp){
						exp.printStackTrace();
					}
				}
			}  
		} catch(Exception expSrv){ 
			expSrv.printStackTrace();
		} finally {
			try{
				sslSrvSocket.close();
			}catch(Exception exp){
				exp.printStackTrace();
			}
		}
	}

	  /**
	   * 发送数据到所有连接客户端
	   * @param buff  要发送的数据
	   */
	public void sendDataToAllClient(StringBuffer buff){
		StringBuffer buffData;
		if(EncryptData)
    	 buffData=new StringBuffer(RC6.encrypt(buff.toString()));
		else
			buffData=buff;

		//加消息结束符
		buffData.append(MSG_FLAG);
 
		//打印数据到调试日志文件
		Debug.fDebug("[通知线程]发送数据到客户端...Data:"+buffData);

		synchronized(watchProcessQue){
			Iterator iterator=watchProcessQue.iterator();
			while(iterator.hasNext()){
				WatchProcess watchProcess=null;
				watchProcess=(WatchProcess)iterator.next();
				synchronized(watchProcess){
					watchProcess.addDataToQueue(buffData);
					watchProcess.notify();
				}
			}
		}
	}

	public void sendStremDataToAllClient(BufferedReader in){
		if(in==null) return ;
		try{
			StringBuffer bufMsg=Messages.readMessage(in,"</Deal>");
			sendDataToAllClient(bufMsg);
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	  /**
	   * 发送数据到指定IP的客户端
	   * @param buff  要发送的数据
	   * @param buff  目的IP
	   */
	private void sendDataToOneClient(String inIP,StringBuffer buff) throws MonitorException{
		if(inIP==null) return ;
		if(buff==null) return ;
		if(buff.length()==0) return ;

		String ip="";
		inIP=inIP.trim();
		synchronized(watchProcessQue){
			Iterator iterator=watchProcessQue.iterator();
			while(iterator.hasNext()){
				WatchProcess watchProcess=null;
				watchProcess=(WatchProcess)iterator.next();
				ip=watchProcess.getClientIP();
				if(ip==null) continue ;
				ip=ip.trim();
				if(ip.equals(inIP)){
					synchronized(watchProcess){
						watchProcess.addDataToQueue(buff);
						watchProcess.notify();
					}
					break;
				}
			}
		}
	}

	  /**
	   * 用来发送通知消息，目前没有实现
	   */
	public int NotifyClien(String inIP,StringBuffer buff) throws MonitorException{
		sendDataToOneClient(inIP,buff);
		return 0;
	}

	public void SrvSocketClose(){
		try{
			sslSrvSocket.close();
		}catch(Exception exp){
			exp.printStackTrace();
		}
	}   
 }