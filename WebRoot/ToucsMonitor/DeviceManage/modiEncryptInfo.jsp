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
<h2><font face="����">���ܻ��豸������Ϣ</font></h2>
<hr>
<%
	DeviceManageBean DM = new DeviceManageBean();
	ATMInfo info = DM.queryEncryptInfo(request.getParameter("encryCode"));
%>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig">
  	<table width="100%">
    	<tr> 
      		<td>��ţ�</td>
      		<td> 
        		<input type="text" name="Code" value = "<%=info.getAtmCode()%>" disabled>
        		<input type="hidden" name="encryCode" value = "<%=info.getAtmCode()%>">
      		</td>
      		<td>���ƣ� </td>
      		<td> 
        		<input type="text" name="encryName" value = "<%=info.getAtmName()%>">
      		</td>
    	</tr>
    	<tr> 
      		<td>IP��ַ��</td>
      		<td> 
        		<input type="text" name="encryIp" value = "<%=info.getNetAddress()%>">
				<font color = "FF0000">*</font>
      		</td>
      		<td>�˿ںţ�</td>
      		<td> 
        		<input type="text" name="encryPort" value = "<%=info.getHostPort()%>">
				<font color = "FF0000">*</font>
      		</td>
    	</tr>
    	<tr> 
      		<td>���ͣ�</td>
      		<td> 
        		<select name="encryType" size="1">
				<%
					if(info.getAtmType().trim().equals("0")){
				%>
				<option value = '0' selected>�������ܻ�</option>
				<option value = '1'>������ܻ�</option>
				<%}else{%>
				<option value = '0'>�������ܻ�</option>
				<option value = '1' selected>������ܻ�</option>
				<%}%>
        		</select>
				<font color = "FF0000">*</font>
      		</td>
      		<td>����Ա�绰��</td>
      		<td> 
        		<input type="text" name="adminPhone" value = "<%=info.getMngPhone()%>">
      		</td>
    	</tr>
  	</table>
<hr>
<input type="hidden" name="reqCode" value = "21007">
<div align = 'center'><input type="button" name="close" value="����" style = 'width:90' class="inputbutton" onClick="javascript:history.go(-1)"> <input type="button" name="Code" value = "ȷ��" style = 'width:90' onclick = 'check()'></div>
</form>
</body>
</html>