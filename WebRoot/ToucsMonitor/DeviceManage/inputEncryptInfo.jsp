<%@ page language="java" contentType="text/html; charset=GBK" %>
<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
</head>
<script src="/ToucsMonitor/js/Check.js"></script>
<script>
function check(){
	if(isNull("encryCode")||isNull("encryIp")||isNull("encryPort")||isNull("encryCode")){
		
	}else{
		document.form1.submit();
	}
}
</script>
<body bgcolor="#CCCCCC" text="#000000" >
<h2><font face="隶书">加密机设备配置信息</font></h2>
<hr>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig">
  	<table width="100%">
    	<tr> 
      		<td>编号：</td>
      		<td> 
        		<input type="text" name="encryCode" value = "">
				<font color = "FF0000">*</font>
      		</td>
      		<td>名称： </td>
      		<td> 
        		<input type="text" name="encryName" value = "">
      		</td>
    	</tr>
    	<tr> 
      		<td>IP地址：</td>
      		<td> 
        		<input type="text" name="encryIp" value = "">
				<font color = "FF0000">*</font>
      		</td>
      		<td>端口号：</td>
      		<td> 
        		<input type="text" name="encryPort" value = "">
				<font color = "FF0000">*</font>
      		</td>
    	</tr>
    	<tr> 
      		<td>类型：</td>
      		<td> 
        		<select name="encryType" size="1">
				<option value = '0'>生产加密机</option>
				<option value = '1'>管理加密机</option>
        		</select>
				<font color = "FF0000">*</font>
      		</td>
      		<td>管理员电话：</td>
      		<td> 
        		<input type="text" name="adminPhone" value = "">
      		</td>
    	</tr>
  	</table>
<hr>
<input type="hidden" name="reqCode" value = "21001">
<div align = 'center'><input type="button" name="butt" value = "确定" style = 'width:45' onclick = 'check()'></div>
</form>
</body>
</html>