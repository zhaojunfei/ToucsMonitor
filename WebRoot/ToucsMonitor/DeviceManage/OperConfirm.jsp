<%@ page contentType="text/html; charset=GBK" %>

<%
	String uid=request.getParameter("uid");	
	Object obj1=request.getAttribute("Message");
	
	String msg="";
	if(obj1!=null){
		msg=(String)obj1;	
	}
	System.out.println("operconfirm msg========"+msg);
	String pwd="";
	obj1=request.getAttribute("pwd");
	request.removeAttribute("pwd");
	request.removeAttribute("Message");
	if(obj1!=null){
		pwd=(String)obj1;	
	}
	pwd=pwd.trim();
	Object obj=request.getAttribute("ATMDevice");
	String ATMDeviceNO="";
	if(obj==null){
		ATMDeviceNO="";
	}else{
		ATMDeviceNO=(String)obj;
	}	
	String DealCode="";
	obj=request.getAttribute("DealCode");
	if(obj==null){
		DealCode="";
	}else{
		DealCode=(String)obj;
	}	
    String orgCode;
	obj=request.getAttribute("OrgCodeSel");		
	if(obj==null){
		orgCode="";
	}else{
		orgCode=(String)obj;		
	}
	
%>
<html>
<head>
	<title>无标题文档</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="v5_css.css" type="text/css">
</head>
<script  Language="JavaScript">
function submit11(){
	var url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=VALIDATE&&uid=<%=uid%>";    
	document.form1.action=url;		  
	document.form1.submit();
}

function submitConfirm(){
	var url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=CONFIRMDEAL&&ATMDevice"+document.all("ATMDevice").value ;
	url=url+"&&DealCode="+document.all("DealCode").value 		
	document.form1.action=url;		  
	//document.forms1.method="POST";		
   	document.form1.submit();
	return true;
}
function showPopUpOnLoad(){
	var Msg1 ="<%=msg%>";
	if(Msg1!=""){
		if(confirm(Msg1) ==true)  submitConfirm();
	}		
	return true;
}		

function cancel11(){
	var url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=CANCEL";
	document.form1.action=url;		  
   	document.form1.submit();
	return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000" onload="showPopUpOnLoad()">
<form name="form1" method="post" action="" onSubmit="submit11();">
  	<table width="63%" border="0" cellpadding="0" cellspacing="0">
    	<tr> 
      		<td width="13%">密码</td>
      		<td width="87%"> 
        		<input type="password" name="pwd" >
      		</td>
    	</tr>
  	</table>
  	<p>
    <input type="hidden" name="ATMDevice" value="<%=ATMDeviceNO%>">
    <input type="hidden" name="OrgCodeSel" value="<%=orgCode%>">
    <input type="hidden" name="DealCode" value="<%=DealCode%>">
    <input type="hidden" name="reqCode" value="VALIDATE">
    <input type="submit" name="Submit" value="提交"  class="inputbutton"onClick="submit11(); ">
    <input type="reset" name="Submit2" value="重置" class="inputbutton">
    <input type="submit" name="Submit3" value="取消"  class="inputbutton" onClick="cancel11()">
  	</p>
</form>
</body>
</html>