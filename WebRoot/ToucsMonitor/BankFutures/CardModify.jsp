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
  	var msgs1=msg+"����Ϊ�գ�";
  	var msgs=msg+"����������!";
  
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
  	var msgs=msg+"����������!";  
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
    var msg1=msg+"����Ϊ�գ�";
    var msg2=msg+"������λ��С�ڵ���"+maxDigit+"λ��"
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
      alert("��������ȷ��ʽ���ݣ�");    	
      textObj.focus(this);
	  return false;
 	}
  
  	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert("��������ȷ��ʽ���ݣ�");    	
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
   		alert("�����뿨��ʼλ��");    
		document.all("cardStart").focus(this);
		return false;
	}   

	if(cardMatch.length==0){
		alert("�����뿨�����ʶ!");
		document.all("cardMatch").focus(this);
		return false;
	}
   
	if(!confirm("ȷ��Ҫ�޸Ŀ�����Ϣ��?����ȷ����ť���޸Ŀ�����Ϣ���˴��޸Ľ�Ӱ�����еĿ�����Ϣ��")) return false;
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
<h2><font face="����">�������</font></h2>
	<table width="671" bordercolor="#CCCCCC">
    	<tr> 
      		<td colspan="5" nowrap> 
       	 		<hr noshade>
      		</td>
    	</tr>  
    	<tr> 
      		<td width="127">������ʼλ</td>
      		<td width="160"> 
        		<input type="text" name="cardStart" value="<%=cardStart%>" size="20" maxlength="1" >
      		</td>
      		<td width="155">&nbsp;&nbsp;�������ʶ</td>
      		<td width="203"> 
        		<input type="text" name="cardMatch" value="<%=cardMatch%>" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">�������� </td>
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
          		<input type="submit" name="regSubmit" value="ȷ��"  class="inputbutton"  onClick="check(); return false;">
          		<input type="reset" name="Submit22" value="����" class="inputbutton" >
          		<input type="submit" name="cancel" value="ȡ��"   class="inputbutton"  onClick="cancel11(); return false;">
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