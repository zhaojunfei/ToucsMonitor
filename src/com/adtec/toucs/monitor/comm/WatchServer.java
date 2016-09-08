//package comm;
package com.adtec.toucs.monitor.comm;


import java.net.*;

import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.security.*;


/**
 * �ṩ��ط������ͻ��˵ļ�ط����߳�������socket����Ҳ�����ˡ�
 * ���Ҫ��ӷ���ĳ�����ݣ���ͨ������sendDataToAllClient
 * �����������ݵ����е�ǰ���ӵĿͻ���
 * ���Ҫ����һ�����ݵ�����ĳһ�ͻ��������NotifyClien���͵�����ĳһָ��IP,��ǰsendDataToOneClient
 * �������⣬��ʱ������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed lichj
 * @version 1.0
 */
public class WatchServer implements Runnable{
	//���ݼ��ܱ�־
	public static boolean EncryptData=false;
	//��Ϣ������־
	public static String MSG_FLAG=" |";
	//��ͻ���ͨѶ�̶߳���
	private List  watchProcessQue=null;
	//��ͻ���ͨѶ����˿�
	private static ServerSocket sslSrvSocket=null;

	//ͨѶ�˿�
	int port=0;
	//�������
	static int ERROECODE=0;

	public WatchServer(int inPort) {
		watchProcessQue=new ArrayList();
		this.port=inPort;
		try{
			if(sslSrvSocket==null)
				sslSrvSocket=new ServerSocket(port);
		}catch(Exception e){
			ERROECODE=-1;//���SSLSocket���������ô�����
		}
	}

	public void run() {
		if(ERROECODE<0) return ;//���SSLSocket��ʼ��������ֱ���Ƴ���
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
	   * �������ݵ��������ӿͻ���
	   * @param buff  Ҫ���͵�����
	   */
	public void sendDataToAllClient(StringBuffer buff){
		StringBuffer buffData;
		if(EncryptData)
    	 buffData=new StringBuffer(RC6.encrypt(buff.toString()));
		else
			buffData=buff;

		//����Ϣ������
		buffData.append(MSG_FLAG);
 
		//��ӡ���ݵ�������־�ļ�
		Debug.fDebug("[֪ͨ�߳�]�������ݵ��ͻ���...Data:"+buffData);

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
	   * �������ݵ�ָ��IP�Ŀͻ���
	   * @param buff  Ҫ���͵�����
	   * @param buff  Ŀ��IP
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
	   * ��������֪ͨ��Ϣ��Ŀǰû��ʵ��
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