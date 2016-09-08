
package com.adtec.toucs.monitor.common;

import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;

/**
 * XMl���Ľ�����
 * �ɴ��뱨�����ݻ��������ļ�����ȫ·���������뱨���ļ������ֻ�û����ȫʵ��
 * ʵ��������󣬵���xmlParse������������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 */

public class MessageParse {
	private boolean isParse=false;   //�Ƿ��Ѿ��������뱨�ı�־
	private java.util.List listDom=null;  //�������Dom���ṹ
	private StringReader stringReader=null;  //XML����������
  /**
   * ���캯��
   * @param inStrReader xml�ַ���
   */
	public MessageParse(StringBuffer inXMLStrBuff){
		stringReader=XMLStringToStream(inXMLStrBuff);
	}
	
  /**
   *���캯��
   * @param inXMLFilePath XML�ļ���
   */
	public MessageParse(String inXMLFilePath) {
		stringReader=readXMLFile(inXMLFilePath);
	}

	public void xmlParse ()throws MonitorException {
		
		if(stringReader==null){
			MonitorException defexp=new MonitorException(ErrorDefine.XMLSTREAMISEMPTY,ErrorDefine.XMLSTREAMISEMPTYDESC);
			throw defexp;
		}
		try{
			SAXBuilder sb = new SAXBuilder();
			//���ļ�����һ��Document����ΪXML�ļ����Ѿ�ָ���˱��룬�������ﲻ����
			Document doc=sb.build(stringReader);
			org.jdom.Element  root = doc.getRootElement();        //�õ���Ԫ��
			//�õ���Ԫ��������Ԫ�صļ���
			if(root==null)  listDom=null;
			else{
				listDom=root.getChildren();
			}
		}catch(JDOMException jdomexp){
			MonitorException defexp=new MonitorException(ErrorDefine.XMLPARSEERROR,ErrorDefine.XMLPARSEERRORDESC);
			throw defexp;
		}
	}

  /**
   * ��xml�ļ�����ת���ַ���
   * @param inXMLFilePath xml�ļ���
   * @return
   */
	private StringReader readXMLFile (String inXMLFilePath){
		return null;
	}

  /**
   * ���ַ���ת���ַ�������
   * @param inXMLstr xml�ַ���
   * @return
   */
	private StringReader  XMLStringToStream(StringBuffer inXMLstr){
		if(inXMLstr==null) return null;
		StringReader strread=null;
		try{
			strread=new StringReader(inXMLstr.toString());
		}catch(Exception exp){
			return null;
		}
		return strread;
	}

  /**
   * ȡ�ý������XM���ڵ���
   * @return
   */
	public List getRootData(){
		if(isParse=false) return null;
		return listDom;
	}
  /**
   * ȡ�ý��״���
   * @return
   */
	public  String getDealCode(){
		String strRet=null;
		if(listDom==null){
			return null;
		}else{
			//ȡ�ý�����
			Element element = (Element)listDom.get(0);
			strRet=element.getText();
		}
		return strRet;
	}
	
  /**
   * ȡ�÷�����
   * @return
   */
	public  String getRequestCode(){
		String strRet=null;
		if(listDom==null){
			return null;
		}else{
			//ȡ�ý�����
			Element element = (Element)listDom.get(1);
			strRet=element.getText();
		}
		return strRet;
	}
  /**
   * ȡ�÷��ش�������
   * @return
   */
	public  String getRequestMsg(){
		String strRet=null;
		if(listDom==null){
			return null;
		}else{
			Element element = (Element)listDom.get(2);
			strRet=element.getText();
		}
		return strRet;
	}

}