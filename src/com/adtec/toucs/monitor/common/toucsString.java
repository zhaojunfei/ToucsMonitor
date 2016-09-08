package com.adtec.toucs.monitor.common;



/**
 * <p>Title: toucsString</p>
 * <p>Description: convert String's codeSet</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author Fancy
 * @version 1.0
 */

public class toucsString {

    public toucsString(){
    }

    public toucsString(String str, String oldCodeSet, String newCodeSet) {
        convert(str,oldCodeSet,newCodeSet);
    }

    public toucsString(String str) {
        convert(str,"GBK","ISO-8859-1");
    }

    public String convert(String str, String oldCodeSet, String newCodeSet) {
        try{
            str = new String(str.trim().getBytes(oldCodeSet),newCodeSet);
            return str;
        } catch(java.io.UnsupportedEncodingException e){
            Debug.println(e.toString());
        }
        return "null";
    }

    public static String Convert(String str) {
        if (str == null){
            return "";
        }
        if (str.trim().equals("")) {
            return str.trim();
        }
        try{
            str = new String(str.trim().getBytes("GBK"),"ISO-8859-1");
            str = str.replace('\n',' ');
            str = str.replace('\r',' ');
            return str;
        } catch(java.io.UnsupportedEncodingException e){
            Debug.println(e.toString());
        }
        return str;
    }
    
    public static String unConvert(String str) {
        if (str == null){
            return "";
        }
        if (str.trim().equals("")) {
            return str.trim();
        }
        try{
            str = new String(str.trim().getBytes("ISO-8859-1"),"GBK");
            return str;
        } catch(java.io.UnsupportedEncodingException e){
            Debug.println(e.toString());
        }
        return "null";
    }
    
  /**
   *  �ַ����滻����  source  �е�  oldString  ȫ������  newString
   *  @param  source  Դ�ַ���
   *  @param  oldString  �ϵ��ַ���
   *  @param  newString  �µ��ַ���
   *  @return  �滻����ַ���
   */
    public  static String  replace(String  source,  String  oldString,  String  newString)  {
        try{
            StringBuffer  output  =  new  StringBuffer();
            int  lengthOfSource  =  source.length();      //  Դ�ַ�������
            int  lengthOfOld  =  oldString.length();      //  ���ַ�������
            int  posStart  =  0;      //  ��ʼ����λ��
            int  pos;                        //  ���������ַ�����λ��

            while  ((pos  =  source.indexOf(oldString,  posStart))    >=  0)  {
                output.append(source.substring(posStart,  pos));
                output.append(newString);
                posStart  =  pos  +  lengthOfOld;
            }
            if  (posStart    <  lengthOfSource)  {
                output.append(source.substring(posStart));
            }
            return  output.toString();
        } catch(Exception  e)  {
            return  source;
        }
    }

  /**
   *  ���ַ�����ʽ����  HTML  �������
   *  @param  str  Ҫ��ʽ�����ַ���
   *  @return  ��ʽ������ַ���
   */
    public  static String  toHtml(String  str)  {
        String  html  =  str;
        if  (str==null    ||  str.length()==0)  {
            return  str;
        }
        html  =  replace(html,    "&",    "&amp;");
        html  =  replace(html,    "<",    "&lt;");
        html  =  replace(html,    ">",    "&gt;");
        html  =  replace(html,    "\r\n",    "\n");
        html  =  replace(html,    "\n",    "<br>\n");
        html  =  replace(html,    "\"",   "&quot;");
        html  =  replace(html,    " ",   "&nbsp;");
        return  html;
    }


  /**
   * ����ڲ����е������ַ�������ÿ��λ��һ�����ŵĹ����ʽ��
   * @param number ��Ҫ��ʽ��������
   * @return ��ʽ���������
   */
    public static String formatNumber(String number){
        String retNumber = "";
        int numLen = number.length();
        int hasNag = number.indexOf("-");
        String absnumber = "";
        if(hasNag > 0){
            return number;
        } else {
            if(hasNag == 0){
                absnumber = number.substring(1,numLen);
            } else { 
                absnumber = number;
            }
        }
        int pointPos = absnumber.indexOf(".");
        if(pointPos <= 0){
            pointPos = absnumber.length();
        }
        String tailStr = absnumber.substring(pointPos);
        String newNumber = "";
        if(pointPos > 3){
            if(pointPos%3 != 0){
                newNumber = absnumber.substring(0,pointPos%3)+",";
                for(int i=0;i< pointPos/3-1;i++){
                    newNumber += absnumber.substring(pointPos%3+i*3,pointPos%3+(i+1)*3)+",";
                }
                newNumber += absnumber.substring(pointPos-3,pointPos)+tailStr;
            } else {
                for(int i=0;i< pointPos/3-1;i++){
                    newNumber += absnumber.substring(i*3,(i+1)*3)+",";
                }
                newNumber += absnumber.substring(pointPos-3,pointPos)+tailStr;
            }
        } else { 
            newNumber = absnumber;
        }
        if(hasNag == 0){
            retNumber = "-"+newNumber;
        } else {
            retNumber = newNumber;
        }
        return retNumber;
    }
  /**
   *  ���ַ�����ʽ����  text  �������
   *  @param  str  Ҫ��ʽ�����ַ���
   *  @return  ��ʽ������ַ���
   */
    public  static String  toText(String  str)  {
        String  text  =  str;
        if  (str==null    ||  str.length()==0)  {
            return  str;
        }
        text  =  replace(text,    "&amp;",    "&");
        text  =  replace(text,    "&lt;",    "<");
        text  =  replace(text,    "&gt;",    ">");
        text  =  replace(text,    "\n",    "\r\n");
        text  =  replace(text,    "<br>\n",    "\n");
        text  =  replace(text,    "<br>",    "\n");
        text  =  replace(text,    "&quot;",   "\"");
        text  =  replace(text,    "&nbsp;",   " ");

        return  text;
    }

  /**
   * ȥ������
   * @param numValue Դ�ַ���
   * @return ȥ�����Ź�����ַ���
   */
    public static String removeDH(String numValue){
        if(numValue == null || numValue.length()<=0){
            return numValue;
        }
        try {
            int indexOfDH = numValue.indexOf(",");
            while(indexOfDH >=0){
                numValue = numValue.substring(0,indexOfDH)+numValue.substring(indexOfDH+1);
                indexOfDH = numValue.indexOf(",");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	return numValue;
        }
    }

  /**
   * ����ָ���ָ����ָ����ַ����ֿ����γ�һ�����鷵�ء�
   * @param SourceStr ���ָ����ַ���
   * @param limiter �ָ���
   * @return �ָ���Ϊnullʱ������null���ɹ������ַ������顣
   */
    public static String[] StrSplit(String SourceStr,String limiter){
        int startPos = 0;
        int endPos = 0;
        String[] retStr = null;
        if (limiter == null) {
            return null;
        }
        if (!SourceStr.endsWith(limiter)) {
            SourceStr += limiter;
        }

        int i = 0;
        while (startPos < SourceStr.length()) {
            endPos = SourceStr.indexOf(limiter,startPos);
            retStr[i] = SourceStr.substring(startPos,endPos);
            startPos = endPos+1;
            i++;
        }
        return retStr;
    }
}
