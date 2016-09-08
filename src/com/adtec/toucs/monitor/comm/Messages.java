//package comm;
package com.adtec.toucs.monitor.comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;

import com.adtec.toucs.monitor.common.*;
/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 * 该类用来发送信息到输出流和从输入流重读取信息
 */

public class Messages {

	public Messages() {
	}
	
  /**
   * 从输入数据流中一行行读入数据
   * @param in  输入流
   * @param endFlag 输入流读入的数据结束标志
   * @return  从输入流中读入的数据
   */
	
	public static  synchronized StringBuffer  readMessage(BufferedReader in,String endFlag) throws MonitorException{         ///方法抛出自定义异常
		System.out.println("-----------Messages-------------readMessage(BufferedReader in,String endFlag)-------");
		StringBuffer strBuff=new StringBuffer();
		System.out.println("----------in-----------------"+in);

			try{
				int readNum=0;
				char []cbuf=new char[100]; 
				while((readNum=in.read(cbuf))>0){ 
					
					strBuff.append(cbuf,0,readNum);//字符串转换成字节
					System.out.println("---------cbuf-------------"+cbuf);
					System.out.println("--readNum=in.read(cbuf)---"+readNum);
					System.out.println("---------strBuff-------------"+strBuff);
					
					int index=strBuff.toString().indexOf(endFlag);
					System.out.println("---------index-------------"+index);
					if(index>0 || index==0) break;
				} 
			}catch(IOException exp){
				exp.printStackTrace();
				MonitorException defexp=new MonitorException(ErrorDefine.INPUTSTREAMISCLOSE,ErrorDefine.INPUTSTREAMISCLOSEDESC);
				throw defexp;
			}catch(Exception ex){
				MonitorException defexp=new MonitorException(ErrorDefine.RECEVICEDATAERROR,ErrorDefine.RECEVICEDATAERRORDESC);
				throw defexp;
			}
			strBuff.append("\n");  
			System.out.println("---Messages------- readMessage(BufferedReader in,String endFlag)---strBuff----"+strBuff+"---------!!!");		
			return strBuff;
	}

	public static synchronized  StringBuffer readMessage(BufferedReader in,int inLength) throws MonitorException{
		StringBuffer strBuff=new StringBuffer();
        try{
        	int readNum=0;
        	char []cbuf=new char[50];
        	while((readNum=in.read(cbuf))>0){
        		strBuff.append(cbuf,0,readNum);
        		if(strBuff.length()>=inLength) break;
        	}
        }catch(IOException exp){
            exp.printStackTrace();
            MonitorException defexp=new MonitorException(ErrorDefine.INPUTSTREAMISCLOSE,ErrorDefine.INPUTSTREAMISCLOSEDESC);
            throw defexp;
        }catch(Exception e){
            e.printStackTrace() ;
            MonitorException defexp=new MonitorException(ErrorDefine.RECEVICEDATAERROR,ErrorDefine.RECEVICEDATAERRORDESC);
            throw defexp;
        }
        strBuff.append("\n");
        return strBuff;
	}

  /**
   * 向输出数据流写入数据。（暂时没考虑，如果数据太大，一次写入是否有问题）
   * @param inMSG 要输出的数据字符串
   * @param len   输出的数据字符串的长度
   * @param out   输出流
   */
	public static synchronized void writeMessage(StringBuffer inMSG,int len, BufferedWriter out) throws MonitorException{
		if(inMSG==null) return ;
		try{
			out.write(inMSG.toString());//考虑如果数据太大?
			out.flush();
		}catch(IOException exp){
			exp.printStackTrace();
			MonitorException defexp=new MonitorException(ErrorDefine.OUTPUTSTREAMISCLOSE,ErrorDefine.OUTPUTSTREAMISCLOSEDESC);
			throw defexp;
		}catch(Exception e){
			e.printStackTrace() ;
			MonitorException defexp=new MonitorException(ErrorDefine.SENDDATAERROR,ErrorDefine.SENDDATAERRORDESC);
			throw defexp;
		}
	}

  /**
   * 向输出数据流写入数据。（暂时没考虑，如果数据太大，一次写入是否有问题）
   * @param inMSG 要输出的数据字符串
   * @param out   输出流
   */
	public static synchronized void writeMessage(StringBuffer inMSG,BufferedWriter out) throws MonitorException{ 
		System.out.println("--------Messages------------------writeMessage---------");
		if(inMSG==null) return ;
		try{ 
			System.out.println("---------inMSG.toString()---------"+inMSG.toString());
			out.write(inMSG.toString());//考虑如果数据太大?
			out.flush();
		}catch(IOException exp){
			exp.printStackTrace();
			MonitorException defexp=new MonitorException(ErrorDefine.OUTPUTSTREAMISCLOSE,ErrorDefine.OUTPUTSTREAMISCLOSEDESC);
			throw defexp;
		}catch(Exception e){
			e.printStackTrace() ;
			MonitorException defexp=new MonitorException(ErrorDefine.SENDDATAERROR,ErrorDefine.SENDDATAERRORDESC);
			throw defexp;
		}
	}
}