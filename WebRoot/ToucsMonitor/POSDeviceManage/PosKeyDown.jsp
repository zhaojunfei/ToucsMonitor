<%@ page language="java" contentType="text/html; charset=GBK" %>

<html>
<head>
<title>POS��Կ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h3>POS��Կ����</h3>
<hr>
<%
    String poskey =(String) request.getAttribute("poskey");
%>
��ԿΪ��[<font color="#FF0000"><%=poskey%></font>]
<hr>
<input type="button" name="����" value="����" onClick="history.go(-2);" class="inputbutton">
</body>
</html>
