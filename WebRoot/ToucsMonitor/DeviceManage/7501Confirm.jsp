<%@ page language="java" contentType="text/html; charset=GBK" %>
<%
	Object obj=request.getAttribute("ATMDevice");
	String ATMDeviceNO="";
	if(obj==null)	ATMDeviceNO="";
	else ATMDeviceNO=(String)obj;
	
    String DealCode="";
	obj=request.getAttribute("DealCode");
	if(obj==null)DealCode="";
	else DealCode=(String)obj;
	
   	String orgCode;
	obj=request.getAttribute("OrgCodeSel");		
	if(obj==null)orgCode="";
	else orgCode=(String)obj;		
%>
<jsp:useBean id="prompt" class="com.adtec.toucs.monitor.common.PromptBean" scope="request"/>
<html>
<head>
<title>Success</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<script language="javascript">
function onOk(){
	var target="<%=request.getParameter("target")%>";
	if(name=="top"){
		parent.location.href=target;
		close();
	}else{
		location.href=target;
	}
}

function submit11(url){
	var url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=CONFIRMDEAL";
  	document.form1.action=url;		  
  	document.form1.submit();
}	
function cacell11(){
  	var url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=CANCEL";
  	document.form1.action=url;		  
  	document.form1.submit();
}	
		
</script>
<body bgcolor="#CCCCCC">
<h3>&nbsp;</h3>
<form name="form1" method="post" action="">
<h2><%=prompt.getTitle()%></h2>
<%if(!prompt.getPrompt().equals("")){%>
<hr>
<h3><%=prompt.getPrompt()%></h3>
<%}%>
<hr>
  <p> 
    <input type="submit" name="Submit" value="提交"  onClick="submit11()"  class="inputbutton">
    <input type="submit" name="Submit2" value="取消"  onClick="cacell11()" class="inputbutton">
    <input type="hidden" name="ATMDevice" value="<%=ATMDeviceNO%>">
    <input type="hidden" name="OrgCodeSel" value="<%=orgCode%>">
    <input type="hidden" name="DealCode" value="<%=DealCode%>">
  </p>
  </form>
</body>
</html>
