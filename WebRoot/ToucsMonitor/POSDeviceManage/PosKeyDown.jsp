<%@ page language="java" contentType="text/html; charset=GBK" %>

<html>
<head>
<title>POS密钥下载</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h3>POS密钥下载</h3>
<hr>
<%
    String poskey =(String) request.getAttribute("poskey");
%>
密钥为：[<font color="#FF0000"><%=poskey%></font>]
<hr>
<input type="button" name="返回" value="返回" onClick="history.go(-2);" class="inputbutton">
</body>
</html>
