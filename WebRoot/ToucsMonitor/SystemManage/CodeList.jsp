<%@ page language="java" contentType="text/html; charset=GBK" %>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">系统代码管理-错误码</font></h2>
<hr>
<form name="formQuery" method="post" action="/ToucsMonitor/ToucsMonitor/DeviceManage/AtmList.jsp">
	<table width="75%">
    	<tr>
      		<td> 
        		<table width="75%" bgcolor="#666666">
          			<tr bgcolor="#666666"> 
            			<td width="42%"> 
              				<div align="center">代码</div>
            			</td>
            			<td width="58%"> 
              				<div align="center">名称</div>
            			</td>
          			</tr>
          			<tr bgcolor="#FFFFFF"> 
            			<td width="42%"> 
              				<div align="center"> 02001 </div>
            			</td>
            			<td width="58%"> 
              				<div align="center">设备不存在</div>
            			</td>
          			</tr>
          			<tr bgcolor="#FFFFFF"> 
            			<td width="42%"> 
              				<div align="center">02002 </div>
            			</td>
            			<td width="58%"> 
              				<div align="center">访问数据库失败</div>
            			</td>
          			</tr>
        		</table>
        		<input type="submit" name="Submit" value="添加">
      		</td>
      		<td>&nbsp;</td>
    	</tr>
  	</table>
</form>
</body>
</html>