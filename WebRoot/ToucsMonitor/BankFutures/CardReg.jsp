<%@ page contentType="text/html; charset=GBK" %>

<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
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
 	var cardMatch=document.all("cardMatch").value ;
       
   if(cardStart.length==0){
   		alert("�����뿨����ʼλ��");    
		document.all("cardStart").focus(this);
		return false;
	}   
	
	if(cardMatch.length == 0){
		alert("�����뿨�����ʶ!");
		document.all("cardMatch").focus(this);
	}	

	if(!validateTxt1("pinoffset","����ƫ��λ" ) )return false;	
    if(!confirm("ȷ��Ҫ����������Ϣ��?����ȷ����ť������������Ϣ���ύ��Ӱ�����е�ATM������Ϣ��")) return false;
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
<h2><font face="����">�������</font> </h2>
<hr noshade>
	<table width="49%" border="0">
    	<tr> 
      		<td width="32%">������ʼλ</td>
      		<td width="68%"> 
        		<input type="text" name="cardStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">�������ʶ</td>
      		<td width="68%"> 
        		<input type="text" name="cardMatch" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">��������</td>
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
          		<input type="submit" name="regSubmit" value="ȷ��" class="inputbutton"  onClick="check(); return false;">       
          		<input type="submit" name="cancel" value="ȡ��"   class="inputbutton"  onClick="cancel11(); return false;">
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