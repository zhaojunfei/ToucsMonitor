<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page isErrorPage="true" %>
<html>
<head>
	<title>Error</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h1> <font face="����">ϵͳ����</font> !</h1>
<hr>
<%
	MonitorException exp=(MonitorException)exception;
	String btnTitle="����";
	String target=(String)request.getAttribute("targetURL");
	Object obj=request.getAttribute("btnTitle");
	if(obj!=null)btnTitle=(String)obj;
	if(target==null)
		target="javascript:history.go(-1)";
	else
		target="javascript:location.href='"+target+"'";
%>
	<table width="75%">
  		<tr> 
    		<td width="13%" height="35"> 
      			<div align="left"><b>�����룺</b></div>
    		</td>
    		<td width="87%" height="35">
    			<%=exp.getErrCode()%> 
    		</td>
  		</tr>
  		<tr> 
    		<td width="13%" height="33"><b>������</b></td>
    		<td height="33"><%=exp.getMessage()%></td>
  		</tr>
	</table>
<hr>
<p>
  <!--a href="javascript:history.go(-1)">[����]</a-->
  <input type=button value="<%=btnTitle%>" name="return" onClick="<%=target%>" class="inputbutton" >
  <!-- input type=button value="�ر�" name="return2" onClick="javascript:location.href='/ToucsMonitor'" class="inputbutton" SELECTED-->
</p>
</body>
</html>
