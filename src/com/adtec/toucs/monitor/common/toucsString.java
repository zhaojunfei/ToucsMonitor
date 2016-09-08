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
   *  字符串替换，将  source  中的  oldString  全部换成  newString
   *  @param  source  源字符串
   *  @param  oldString  老的字符串
   *  @param  newString  新的字符串
   *  @return  替换后的字符串
   */
    public  static String  replace(String  source,  String  oldString,  String  newString)  {
        try{
            StringBuffer  output  =  new  StringBuffer();
            int  lengthOfSource  =  source.length();      //  源字符串长度
            int  lengthOfOld  =  oldString.length();      //  老字符串长度
            int  posStart  =  0;      //  开始搜索位置
            int  pos;                        //  搜索到老字符串的位置

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
   *  将字符串格式化成  HTML  代码输出
   *  @param  str  要格式化的字符串
   *  @return  格式化后的字符串
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
   * 将入口参数中的数字字符串按照每三位加一个逗号的规则格式化
   * @param number 需要格式化的数字
   * @return 格式化后的数字
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
   *  将字符串格式化成  text  代码输出
   *  @param  str  要格式化的字符串
   *  @return  格式化后的字符串
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
   * 去掉逗号
   * @param numValue 源字符串
   * @return 去掉逗号过后的字符串
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
   * 把以指定分隔符分隔的字符串分开，形成一个数组返回。
   * @param SourceStr 待分隔的字符串
   * @param limiter 分隔符
   * @return 分隔符为null时，返回null，成功返回字符串数组。
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
