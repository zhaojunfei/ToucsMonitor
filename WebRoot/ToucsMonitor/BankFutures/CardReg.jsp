<%@ page contentType="text/html; charset=GBK" %>

<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //取得消息
	request.removeAttribute("Message");   //删除消息
	if(obj1!=null)msg=(String)obj1;
		
  	String REGCARD="17401";
  	String CANCEL="CANCEL";
 	String uid=request.getParameter("uid");		
%>

<html>
<head>
<title>Untitled Document</title>
<Script>
function showPopUpOnLoad(){
    var Msg = '<%= msg %>';
    if (Msg != "") {
        alert(Msg);
    }
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
 	var cardMatch=document.all("cardMatch").value ;
       
   if(cardStart.length==0){
   		alert("请输入卡号起始位！");    
		document.all("cardStart").focus(this);
		return false;
	}   
	
	if(cardMatch.length == 0){
		alert("请输入卡适配标识!");
		document.all("cardMatch").focus(this);
	}	

	if(!validateTxt1("pinoffset","密码偏移位" ) )return false;	
    if(!confirm("确认要新增卡表信息吗?按下确定按钮后将新增卡表信息，提交后将影响所有的ATM卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/parametersetservlet?reqCode=<%=REGCARD%>&&uid=<%=uid%>";
 	document.form1.submit();			
}
	
function cancel11(){		
	document.form1.action="/ToucsMonitor/bfCardservlet?reqCode=<%=CANCEL%>&&uid=<%=uid%>";	
 	document.form1.submit();						
}
 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000" onload="showPopUpOnLoad()">
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet">
<h2><font face="隶书">卡表管理</font> </h2>
<hr noshade>
	<table width="49%" border="0">
    	<tr> 
      		<td width="32%">卡号起始位</td>
      		<td width="68%"> 
        		<input type="text" name="cardStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">卡适配标识</td>
      		<td width="68%"> 
        		<input type="text" name="cardMatch" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">卡表描述</td>
      		<td width="68%"> 
        		<input type="text" name="cardDesc" size="20">
      		</td>
    	</tr>    
    </table>
  	<table width="81%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        	<div align="right"> 
          		<input type="submit" name="regSubmit" value="确定" class="inputbutton"  onClick="check(); return false;">       
          		<input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">
        	</div>
      		</td>
    	</tr>
  	</table>
<p>
<input type="hidden" name="reqCode" value=<%=REGCARD%>>
</p>
<p>&nbsp; </p>
</form>
</body>
</html>