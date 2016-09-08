<%@ page language="java" contentType="text/html; charset=GBK" %>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">加密机设备参数管理</font></h2>
	<table width="100%" cellspacing="0">
  		<tr> 
    		<td height="10"> 
      		<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr> 
    		<td> 
      			<input type="button" name="add" value="添加" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/DeviceManage/inputEncryptInfo.jsp'>
    		</td>
  		</tr>
  		<tr> 
    		<td height="10"> 
      		<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr> 
    		<td height="20"> 
      		<div align="center"> 
        	<jsp:include page="EncryptList.jsp" flush="true"/>
      		</div>
    		</td>
  		</tr>
  		<tr> 
    		<td height="10"> 
      		<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr> 
    		<td> 
      		<div align="right"> </div>
    		</td>
  		</tr>
	</table>
<p>&nbsp;</p>
</body>
</html>
