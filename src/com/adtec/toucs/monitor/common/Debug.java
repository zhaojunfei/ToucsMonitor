package com.adtec.toucs.monitor.common;

import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Debug {
	public static boolean DEBUG=true;

	protected static String FILE_PATH="ToucsMonitor.log";
	protected static FileDebuger fdebug=null;

	public static void init(){
	}

	public synchronized static void println(String info){
		if(DEBUG)
			System.out.println(convInfo(info));
	}

	public synchronized static void print(String info){
		if(DEBUG)
			System.out.print(convInfo(info));
	}

	public synchronized static void println(int info){
		if(DEBUG)
			System.out.println(convInfo(info));
	}

	public synchronized static void print(int info){
		if(DEBUG)
			System.out.print(convInfo(info));
	}

	public synchronized static void println(long info){
		if(DEBUG)
			System.out.println(convInfo(info));
	}

	public synchronized static void print(long info){
		if(DEBUG)
			System.out.print(convInfo(info));
	}
	public static void setDebugFilePath(String path){
		FILE_PATH=path;
	}

	public synchronized static void fDebug(String info){
		if(DEBUG&&fdebug!=null)
        fdebug.println(convInfo(info));
	}	

	public synchronized static void fPrint(String info){
		fDebug(info);
	}

	public static void close(){
		System.out.print("[Debug]��������...");
	}

	private static String convInfo(String info){
		java.text.SimpleDateFormat dfmt=new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return "["+dfmt.format(new Date())+"]"+info;
	}
	private static String convInfo(int info){
		java.text.SimpleDateFormat dfmt=new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return "["+dfmt.format(new Date())+"]"+info;
	}
	private static String convInfo(long info){
		java.text.SimpleDateFormat dfmt=new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return "["+dfmt.format(new Date())+"]"+info;
	}

	public static void main(String[] args){
		Debug.DEBUG=true;
		Debug.init();
		Debug.fDebug("Items1");
		Debug.fDebug("Items2");
		Debug.fDebug("Items3");
		Debug.close();
	}
}

class FileDebuger{
	private PrintWriter fprint=null;
	public FileDebuger(String filePath) throws IOException{
		File file=new File(filePath);
		//�����Ŀ¼�����Ĭ�ϵ��ļ���
		if(file.isDirectory())
			filePath+="/toucsdebug.log";
		//���Ŀ¼�����ڣ�����Ŀ¼
		File dir=file.getParentFile();
		if(!dir.exists())
			dir.mkdirs();
		//�����ļ������
		fprint=new PrintWriter(new FileOutputStream(filePath,false),true);
	}

	public void println(String info){
		fprint.println(info);
	}

	public void closeFile(){
		if(fprint!=null)
			fprint.close();
	}
}
