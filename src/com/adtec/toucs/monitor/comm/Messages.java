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
 * ��������������Ϣ��������ʹ��������ض�ȡ��Ϣ
 */

public class Messages {

	public Messages() {
	}
	
  /**
   * ��������������һ���ж�������
   * @param in  ������
   * @param endFlag ��������������ݽ�����־
   * @return  ���������ж��������
   */
	
	public static  synchronized StringBuffer  readMessage(BufferedReader in,String endFlag) throws MonitorException{         ///�����׳��Զ����쳣
		System.out.println("-----------Messages-------------readMessage(BufferedReader in,String endFlag)-------");
		StringBuffer strBuff=new StringBuffer();
		System.out.println("----------in-----------------"+in);

			try{
				int readNum=0;
				char []cbuf=new char[100]; 
				while((readNum=in.read(cbuf))>0){ 
					
					strBuff.append(cbuf,0,readNum);//�ַ���ת�����ֽ�
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
   * �����������д�����ݡ�����ʱû���ǣ��������̫��һ��д���Ƿ������⣩
   * @param inMSG Ҫ����������ַ���
   * @param len   ����������ַ����ĳ���
   * @param out   �����
   */
	public static synchronized void writeMessage(StringBuffer inMSG,int len, BufferedWriter out) throws MonitorException{
		if(inMSG==null) return ;
		try{
			out.write(inMSG.toString());//�����������̫��?
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
   * �����������д�����ݡ�����ʱû���ǣ��������̫��һ��д���Ƿ������⣩
   * @param inMSG Ҫ����������ַ���
   * @param out   �����
   */
	public static synchronized void writeMessage(StringBuffer inMSG,BufferedWriter out) throws MonitorException{ 
		System.out.println("--------Messages------------------writeMessage---------");
		if(inMSG==null) return ;
		try{ 
			System.out.println("---------inMSG.toString()---------"+inMSG.toString());
			out.write(inMSG.toString());//�����������̫��?
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