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
<h2><font face="����">���ܻ��豸������Ϣ</font></h2>
<hr>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig">
  	<table width="100%">
    	<tr> 
      		<td>��ţ�</td>
      		<td> 
        		<input type="text" name="encryCode" value = "">
				<font color = "FF0000">*</font>
      		</td>
      		<td>���ƣ� </td>
      		<td> 
        		<input type="text" name="encryName" value = "">
      		</td>
    	</tr>
    	<tr> 
      		<td>IP��ַ��</td>
      		<td> 
        		<input type="text" name="encryIp" value = "">
				<font color = "FF0000">*</font>
      		</td>
      		<td>�˿ںţ�</td>
      		<td> 
        		<input type="text" name="encryPort" value = "">
				<font color = "FF0000">*</font>
      		</td>
    	</tr>
    	<tr> 
      		<td>���ͣ�</td>
      		<td> 
        		<select name="encryType" size="1">
				<option value = '0'>�������ܻ�</option>
				<option value = '1'>������ܻ�</option>
        		</select>
				<font color = "FF0000">*</font>
      		</td>
      		<td>����Ա�绰��</td>
      		<td> 
        		<input type="text" name="adminPhone" value = "">
      		</td>
    	</tr>
  	</table>
<hr>
<input type="hidden" name="reqCode" value = "21001">
<div align = 'center'><input type="button" name="butt" value = "ȷ��" style = 'width:45' onclick = 'check()'></div>
</form>
</body>
</html>