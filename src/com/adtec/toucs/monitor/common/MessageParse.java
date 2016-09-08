
package com.adtec.toucs.monitor.common;

import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;

/**
 * XMl报文解析类
 * 可传入报文数据或报文所在文件名（全路径）。传入报文文件名部分还没有完全实现
 * 实例化该类后，调用xmlParse方法解析报文
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: adtec</p>
 * @author unascribed lichj
 * @version 1.0
 */

public class MessageParse {
	private boolean isParse=false;   //是否已经解析传入报文标志
	private java.util.List listDom=null;  //解析后的Dom数结构
	private StringReader stringReader=null;  //XML报文输入流
  /**
   * 构造函数
   * @param inStrReader xml字符串
   */
	public MessageParse(StringBuffer inXMLStrBuff){
		stringReader=XMLStringToStream(inXMLStrBuff);
	}
	
  /**
   *构造函数
   * @param inXMLFilePath XML文件名
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
			//从文件构造一个Document，因为XML文件中已经指定了编码，所以这里不必了
			Document doc=sb.build(stringReader);
			org.jdom.Element  root = doc.getRootElement();        //得到根元素
			//得到根元素所有子元素的集合
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
   * 把xml文件内容转成字符流
   * @param inXMLFilePath xml文件名
   * @return
   */
	private StringReader readXMLFile (String inXMLFilePath){
		return null;
	}

  /**
   * 把字符串转成字符输入流
   * @param inXMLstr xml字符串
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
   * 取得解析后的XM根节点树
   * @return
   */
	public List getRootData(){
		if(isParse=false) return null;
		return listDom;
	}
  /**
   * 取得交易代码
   * @return
   */
	public  String getDealCode(){
		String strRet=null;
		if(listDom==null){
			return null;
		}else{
			//取得交易码
			Element element = (Element)listDom.get(0);
			strRet=element.getText();
		}
		return strRet;
	}
	
  /**
   * 取得返回玛
   * @return
   */
	public  String getRequestCode(){
		String strRet=null;
		if(listDom==null){
			return null;
		}else{
			//取得交易码
			Element element = (Element)listDom.get(1);
			strRet=element.getText();
		}
		return strRet;
	}
  /**
   * 取得返回错误描述
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