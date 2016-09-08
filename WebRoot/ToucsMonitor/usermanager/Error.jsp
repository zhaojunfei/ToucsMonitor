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
<body bgcolor="#CCCCCC">
<h1> <font face="隶书">系统错误</font> !</h1>
<hr>
	<table width="75%">
  		<tr> 
    		<td width="13%" height="35"> 
      			<div align="left"><b>错误码：</b></div>
    		</td>
    		<td width="87%" height="35"> <%=((MonitorException)exception).getErrCode()%> 
    		</td>
  		</tr>
  		<tr> 
    		<td width="13%" height="33"><b>描述：</b></td>
    		<td height="33"><%=exception.getMessage()%></td>
  		</tr>
	</table>
<hr>
<p><a href="javascript:history.go(-1)">[返回]</a></p>
</body>
</html>
