<%@ page language="java" contentType="text/html; charset=gb2312" %>

<html>
<head>
<title>Success</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<!--删除成功提示-->

<body bgcolor="#CCCCCC" text="#000000">
<%String uid=request.getParameter("uid");%>
<p>设备删除成功!</p>
<hr noshade>
<p> 
  <input type="button" name="end" value="确定" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?uid=<%=uid%>'" class="inputbutton">
</p>
</body>
</html>
