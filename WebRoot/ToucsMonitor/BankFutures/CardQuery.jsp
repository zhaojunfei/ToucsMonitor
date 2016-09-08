<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>

<html>
<head>
<title>Untitled Document</title>
<%
	Object obj1=request.getAttribute("Message");
	request.removeAttribute("Message");
	String msg="";
	if(obj1!=null)msg=(String)obj1;

 	obj1=request.getAttribute("uid");  
  	String uid=request.getParameter("uid");
  	if(obj1!=null) uid=(String)obj1;

  	String REGCARD="17401";
  	String MODCARD="17402";
 	String MODCARDREQ1="1740201";
  	String DELCARD="17403";
  	String QUERYCARD="17404";
  	String CANCEL="CANCEL";
  
	String reqCode=QUERYCARD;
	obj1=request.getAttribute("reqCode");
	if(obj1!=null)reqCode=(String)obj1;

   	Hashtable opv=(Hashtable)request.getAttribute("OpervRes");
  	String hasM="",hasQ="";
  	if(opv !=null){

    Object obj2;
	
  	obj2=opv.get(REGCARD);
	if(obj2!=null)
			
	obj2=opv.get(DELCARD);
	if(obj2!=null)
		
		
	obj2=opv.get(MODCARD);
	if(obj2!=null)
		hasM=(String)obj2;
		
	obj2=opv.get(QUERYCARD);
	if(obj2!=null)
		hasQ=(String)obj2;
  	}
  
    System.out.println("hasQ=================="+hasQ);
	System.out.println("hasM=================="+hasM);
		
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script>
function show(i) {
	if (i==1) {
		//document.all("txtCard").style.display = "";
	} else if(i==2){
		//document.all("txtCard").style.display = "none";
	}
}
				
function showPopUpOnLoad(){
    var Msg = '<%= msg %>';
    if (Msg != "") {
        alert(Msg);
    }
}
	
function validateTxt(textName,msg){ 
  	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert(msg+"必须是整数数据类型！");    	
      		document.all(textName).focus(this); 
	  		return false;
    	}
  	}
  	return true;
}

function TextIsNull(textName,maxDigit){
 	var newValue = document.all(textName).value ;
	var newLength = newValue.length;
   if(newLength>maxDigit){
		var msg="请输入的数据位数小于等于"+maxDigit+"位！";		
		alert(msg);
    	document.all(textName).focus(this);
	    return true;
 	} 
 	return false;
 }

 
function check(){     
	if(!validateTxt("matchStart","适配偏移" )) return false;		
	return true;
}
	
function cancel11(){  
	var url="/ToucsMonitor/bfCardservlet?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.form1.action=url;
 	document.form1.submit();
	return true;
}
	
function submit11(type){				
	if(!check()) return false;
	var url;

	url="/ToucsMonitor/bfCardservlet?reqCode=<%=reqCode%>";			
	document.form1.action=url;	
	document.form1.submit();
} 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000" onload="showPopUpOnLoad()">
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet?reqCode=17404">
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
<hr noshade>
<p align="right"> 
<%	
	String opvMQ=hasQ;	
	if(reqCode.equals(MODCARDREQ1)) opvMQ=hasM;
	
	if(opvMQ.equals("0")){	
%>
    <input type="submit" name="Submit3" value="确定" class="inputbutton" onClick="submit11(2); return false;">
<%
	 }
	 if(hasQ.equals("0")){%>
    <input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">
    <%}%>
</p>
</form>
</body>
</html>