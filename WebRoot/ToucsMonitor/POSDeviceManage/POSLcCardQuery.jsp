<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

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

  	String MODCARD="14702";
  	String MODCARDREQ1="1470201";
  	String QUERYCARD="14704";
  	String CANCEL="CANCEL";
  
	String reqCode=QUERYCARD;
	obj1=request.getAttribute("reqCode");
	if(obj1!=null)reqCode=(String)obj1;

   	Hashtable opv=(Hashtable)request.getAttribute("OpervRes");
  	String hasM="",hasQ="";
  	if(opv !=null){
    	Object obj2;	
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
	if(!validateTxt("matchStart","卡bin" )) return false;	
	return true;
}
	
function cancel11(){  
	var url="/ToucsMonitor/posLcCard?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.form1.action=url;
	document.form1.submit();
	return true;
}

function submit11(type){				
	if(!check()) return false;
	var url;
	url="/ToucsMonitor/posLcCard?reqCode=<%=reqCode%>";				
	document.form1.action=url;	
	document.form1.submit();
} 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body   bgcolor="#CCCCCC" text="#000000" onLoad="showPopUpOnLoad()">
<form name="form1" method="post" action="/ToucsMonitor/posLcCard">
	<h2><font face="隶书">理财pos卡卡表管理</font> </h2>
  	<hr noshade>
  	<table width="49%" border="0">
    	<tr> 
      		<td width="32%">磁道号</td>
      		<td width="68%"> 
        		<input type="text" name="track_no" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">适配偏移</td>
      		<td width="68%"> 
       			<input type="text" name="matchStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">卡bin</td>
      		<td width="68%"> 
        		<input type="text" name="cardMatch" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">银行名称</td>
      		<td width="68%"> 
        		<select name="bankCode">
          		<option value="" selected>--请选择--</option>
          		<%			
          			List  bankCodeList=(List)request.getAttribute("bankCode") ;
		   			if(bankCodeList!=null){
		   				String  bankcode="";
						String  bankname="";
						for(int i=0;i<bankCodeList.size();i++){
							BankCodeBean  CodeBean0=null;					
							try{
						  		CodeBean0=(BankCodeBean)bankCodeList.get(i);
							}catch(Exception exp){
								System.out.println("error:"+i);
							}	  									
						if(CodeBean0==null) continue;
						bankcode=CodeBean0.getBankCode();
						bankname=CodeBean0.getBankName();
						if(bankcode!=null)bankcode=bankcode.trim();
						if(bankname!=null)bankname=bankname.trim();												
				%>
          		<option value="<%=bankcode%>"> <%=bankname%></option>
          		<%		}
	    			}
	    		%>
        		</select>
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">卡表日期时间戳</td>
      		<td width="68%"> 
        		<input type="text" name="rec_datetime" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="32%">卡表状态</td>
      		<td width="68%"> 
        		<input type="radio" name="rec_useflag" class="radio" value="0" checked>启用 
        		<input type="radio" name="rec_useflag"  class="radio" value="1">不启用
  			    <input type="radio" name="rec_useflag"  class="radio" value="3">所有 </td>
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