<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
	<title>机构信息</title>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">查询机构信息</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/orgManager">
	<table width="75%" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" colspan="2">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td height="25" width="20%"><font size="2">机构编码：</font></td>
      		<td height="25" width="365"> <font size="2"> 
        		<input type="text" name="orgid"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="20%"><font size="2">机构名称：</font></td>
      		<td height="25" width="365"> <font size="2"> 
        		<input type="text" name="orgname">
        		<input type="hidden" name="txcode" value="10104"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="2"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="2"> 
        		<div align="right"> 
          		<input type="submit" name="Submit" value="确定" class="inputbutton">
          		<input type="reset" name="Submit2" value="取消" class="inputbutton" onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>