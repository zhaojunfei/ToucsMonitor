<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.net.*" %>
<%
	String uid = request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'PosMoneyUpload.jsp' starting page</title>  
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
  </head>
<script>
function check(){
	var path = document.getElementById("f1").value;
	if(path==null){
		alert("��ָ����ȷ��·�� ��");
		return false;
	}
	return true;
}
</script>
<body>
<form name="form1" method="post" action="/ToucsMonitor/posMoney?reqCode=14206&uid=<%=uid%>">
	<p><font face="����" size="+2">����POS�豸��Ϣ excel�ļ��ϴ�����</font>	
	<table id="tb1" border="0" width="100%">
		<tr>
			<td width="80%" align="right"><a href="/ToucsMonitor/ToucsMonitor/upload/upload_pos.xls">excelģ������</a></td>
			<td width="20"></td>
		</tr>
		<tr>
			<td colspan="2"><hr noshade></td>	
		</tr>
	</table>
	<table id="tb2" border="0" width="100%">
		<tr>
			<td width="60%" align="right"><input type="file" name="excel" id="f1"></td>
			<td width="40"></td>
		</tr>
		<tr>
			<td width="60%"></td>
			<td width="40%" align="left"><input type="submit" id="bt2" value="�ϴ�" onclick="return check()"></td>
		</tr> 
	</table>
		  �ϴ��ļ�ǰ��������������pos�豸��Ϣͳһ��excel�ϴ�ģ�壬Ȼ���������ݽ����ϴ���
</form>  
</body>
</html>