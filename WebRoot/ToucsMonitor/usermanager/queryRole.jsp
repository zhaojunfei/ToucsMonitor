<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
	<title>queryRole</title>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">查询角色信息</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/roleManager">
	<table width="75%" border="0" cellspacing="0" cellpadding="0">
   		<tr> 
     		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" height="30"><font size="2">角色名称：</font></td>
      		<td width="66%" height="30"> 
        		<input type="text" name="rolename">
        		<input type="hidden" name="txcode" value="10304">
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2" height="30"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2" height="30"> 
        		<div align="right"> 
          		<input type="submit" name="Submit" value="查询" class="inputbutton">
          		<input type="reset" name="Submit2" value="取消" class="inputbutton" onclick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>