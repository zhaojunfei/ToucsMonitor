//package comm;
package com.adtec.toucs.monitor.comm;


import java.net.*;

import java.io.*;
import java.util.*;
import com.adtec.toucs.monitor.common.*;

/**
 * ��������������ͻ�����������
 * �߳����������û����Ϣ��Ҫ�����������ȴ�
 * ����ͨ������addDataToQueue����������ݵ����Ͷ����У�������ݵ����Ͷ��к���Ҫ���Ѹ��߳�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 *
 */
public class WatchProcess implements Runnable{

	private Socket soc=null;   //ͨѶSocket
	private List listSendData=null;  //Ҫ���͵����ݶ���
	private List watchProcessQue=null;//��ͻ���ͨѶ�̶߳���
	private BufferedWriter buffos=null;//������

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
   * ��������ݵ����ݶ�����
   * @param inBuff Ҫ���͵�����
   */
	public void addDataToQueue (StringBuffer inBuff){
		if(inBuff==null) return ;
		if(inBuff.length()==0) return ;
		//��������
		listSendData.add(inBuff);
	}

   /**
    *�������ݵ��ͻ���
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
    *������ݶ����е�����
    */
	public void clearSendData(){
		listSendData.clear();
	}

   /**
    * ����֪ͨ��ָ��IP����ʱ������
    */
	public int NotifyClien(String buff){
		return 0;
	}

   /**
    * ȡ�ÿͻ���IP��ַ
    */
	public String getClientIP(){
		return "";
	}
   /**
    * ȡ�ÿͻ������ӵ��׽���
    */
   	public Socket getSoket(){
   		return soc;
   	}
}