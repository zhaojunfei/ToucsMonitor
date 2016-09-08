<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*" %>

<%
  	String MODCARD="17402";
  	String CANCEL="CANCEL";
  	String uid=request.getParameter("uid");	
%>
<html>
<head>
<title>Untitled Document</title>
<script>
function validateTxt(textName,msg){ 
	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	var msgs1=msg+"不能为空！";
  	var msgs=msg+"必须是数字!";
  
  	if(newLength==0){
  		alert(msgs1);   
		document.all(textName).focus(this); 
		return false;
  	}
 
 	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert(msgs);    	
      		document.all(textName).focus(this); 
	  		return false;
    	}
 	}
  	return true;
}

function validateTxt1(textName,msg){ 
  	var newValue = document.all(textName).value ;
 	var newLength = newValue.length;  
  	var msgs=msg+"必须是数字!";  
  	if(newLength==0){
		return true;
  	}
 
 	for(var i = 0; i != newLength; i++) {
   		aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert(msgs);    	
      		document.all(textName).focus(this); 
	  		return false;
    	}
  	}
  	return true;
}

function TextIsNull(textName,maxDigit,msg){
 	var newValue = document.all(textName).value ;
	var newLength = newValue.length;
	//alert(newLength);
	var isNull=true;
	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
	    if(aChar !=" ") {
			isNull=false;
			break;
    	}
  	}
    var msg1=msg+"不能为空！";
    var msg2=msg+"的数据位数小于等于"+maxDigit+"位！"
	if(isNull){
      	alert(msg1);    	
      	document.all(textName).focus(this);
	  	return true;	
	}
	
	if(newLength==0  ){
      	alert(msg1);    	
      	document.all(textName).focus(this);
	  	return true;
 	}
   if(newLength>maxDigit){
		alert(msg2);
    	document.all(textName).focus(this);
	    return true;
 	} 
 	return false;
 }

function isInt(newValue) {
 // var newValue = document.all(textName).value ;
	var newLength = newValue.length;
	if(newLength==0){
      alert("请输入正确格式数据！");    	
      textObj.focus(this);
	  return false;
 	}
  
  	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert("请输入正确格式数据！");    	
      		textObj.focus(this);
	  		return false;
    	}
  	}
  	return true;
}

function isNumber(aChar) {
  	if(aChar < "0" || aChar > "9") {
      	return false;
  	}
  	return true;
}

function check(){    
 	var cardStart=document.all("cardStart").value ;
 	var cardMatch=document.all("cardMatch").value;     
   	if(cardStart.length==0){
   		alert("请输入卡起始位！");    
		document.all("cardStart").focus(this);
		return false;
	}   

	if(cardMatch.length==0){
		alert("请输入卡适配标识!");
		document.all("cardMatch").focus(this);
		return false;
	}
   
	if(!confirm("确认要修改卡表信息吗?按下确定按钮后将修改卡表信息，此次修改将影响所有的卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/bfCardservlet?reqCode=<%=MODCARD%>&&uid=<%=uid%>";	
 	document.form1.submit();			
    }
 
  	function cancel11(){
		//document.all("reqCode").value="CANCEL"; 
		document.form1.action="/ToucsMonitor/bfCardservlet?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
 		document.form1.submit();			
	}
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%   		
	Object obj=request.getAttribute("CardTypeStruct");   
	CardTypeStruct cardTypeStruct=null;
	if(obj==null)
		cardTypeStruct=new CardTypeStruct();
	else
		cardTypeStruct=(CardTypeStruct)obj;

 	String cardStart = cardTypeStruct.getCardStart();
 	String cardMatch = cardTypeStruct.getCardMatch();
 	String cardDesc  = cardTypeStruct.getCardDesc();
%>
   
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet"  >
<h2><font face="隶书">卡表管理</font></h2>
	<table width="671" bordercolor="#CCCCCC">
    	<tr> 
      		<td colspan="5" nowrap> 
       	 		<hr noshade>
      		</td>
    	</tr>  
    	<tr> 
      		<td width="127">卡号起始位</td>
      		<td width="160"> 
        		<input type="text" name="cardStart" value="<%=cardStart%>" size="20" maxlength="1" >
      		</td>
      		<td width="155">&nbsp;&nbsp;卡适配标识</td>
      		<td width="203"> 
        		<input type="text" name="cardMatch" value="<%=cardMatch%>" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">卡号描述 </td>
      		<td width="160"> 
        		<input type="text" name="cardDesc"  value="<%=cardDesc%>" size="20" >
      		</td>
    	</tr>
  	</table>
  	<table width="88%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        	<div align="right">
          		<input type="submit" name="regSubmit" value="确定"  class="inputbutton"  onClick="check(); return false;">
          		<input type="reset" name="Submit22" value="重填" class="inputbutton" >
          		<input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">
        	</div>
      		</td>
    	</tr>
  	</table>
<p>
    <input type="hidden" name="oldCardStart" value="<%=cardStart%>">
    <input type="hidden" name="oldCardMatch" value="<%=cardMatch%>">
    <input type="hidden" name="oldCardDesc" value="<%=cardDesc%>">
</p>
</form>
</body>
</html>