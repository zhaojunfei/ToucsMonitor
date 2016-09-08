<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
</head>
<script src="/ToucsMonitor/js/Check.js"></script>
<script>
function check(){
	if(isNull('encryIp')||isNull('encryPort')||isNull('encryCode')){

	}else{
		document.form1.submit();
	}
}
</script>
<body bgcolor="#CCCCCC" text="#000000" >
<h2><font face="隶书">加密机设备配置信息</font></h2>
<hr>
<%
	DeviceManageBean DM = new DeviceManageBean();
	ATMInfo info = DM.queryEncryptInfo(request.getParameter("encryCode"));
%>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig">
  	<table width="100%">
    	<tr> 
      		<td>编号：</td>
      		<td> 
        		<input type="text" name="Code" value = "<%=info.getAtmCode()%>" disabled>
        		<input type="hidden" name="encryCode" value = "<%=info.getAtmCode()%>">
      		</td>
      		<td>名称： </td>
      		<td> 
        		<input type="text" name="encryName" value = "<%=info.getAtmName()%>">
      		</td>
    	</tr>
    	<tr> 
      		<td>IP地址：</td>
      		<td> 
        		<input type="text" name="encryIp" value = "<%=info.getNetAddress()%>">
				<font color = "FF0000">*</font>
      		</td>
      		<td>端口号：</td>
      		<td> 
        		<input type="text" name="encryPort" value = "<%=info.getHostPort()%>">
				<font color = "FF0000">*</font>
      		</td>
    	</tr>
    	<tr> 
      		<td>类型：</td>
      		<td> 
        		<select name="encryType" size="1">
				<%
					if(info.getAtmType().trim().equals("0")){
				%>
				<option value = '0' selected>生产加密机</option>
				<option value = '1'>管理加密机</option>
				<%}else{%>
				<option value = '0'>生产加密机</option>
				<option value = '1' selected>管理加密机</option>
				<%}%>
        		</select>
				<font color = "FF0000">*</font>
      		</td>
      		<td>管理员电话：</td>
      		<td> 
        		<input type="text" name="adminPhone" value = "<%=info.getMngPhone()%>">
      		</td>
    	</tr>
  	</table>
<hr>
<input type="hidden" name="reqCode" value = "21007">
<div align = 'center'><input type="button" name="close" value="返回" style = 'width:90' class="inputbutton" onClick="javascript:history.go(-1)"> <input type="button" name="Code" value = "确定" style = 'width:90' onclick = 'check()'></div>
</form>
</body>
</html>