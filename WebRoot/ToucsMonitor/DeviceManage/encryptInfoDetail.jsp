<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<html>
<head>
	<title>encryptInfoDetail.jsp</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
</head>
<body bgcolor="#CCCCCC" text="#000000" >
<h2><font face="����">���ܻ��豸������Ϣ</font></h2>
<hr>
<%
	DeviceManageBean DM = new DeviceManageBean();
	ATMInfo info = DM.queryEncryptInfo(request.getParameter("encryCode"));
%>
	<table width="100%">
    	<tr> 
      		<td>��ţ�</td>
      		<td><%=info.getAtmCode()%></td>
      		<td>���ƣ� </td>
      		<td><%=info.getAtmName()%></td>
    	</tr>
    	<tr> 
      		<td>IP��ַ��</td>
      		<td> <%=info.getNetAddress()%></td>
      		<td>�˿ںţ�</td>
      		<td><%=info.getHostPort()%></td>
    	</tr>
    	<tr> 
      		<td>���ͣ�</td>
      		<td> 
			<%
				if(info.getAtmType().trim().equals("0")){
			%>
				�������ܻ�
			<%}
				else{
			%>
				������ܻ�
			<%}%>
      		</td>
      		<td>����Ա�绰��</td>
      		<td> <%=info.getMngPhone()%></td>
    	</tr>
    	<tr> 
      		<td>���б�־��</td>
      		<td> 
			<%
				if(info.getRunStat().trim().equals("0")){
			%>
				����
			<%}
				else{
			%>
				����
			<%}%>
      		</td>
      		<td>���ñ�־��</td>
      		<td> 
			<%
				if(info.getUseFlag().trim().equals("2")){
			%>
				δ����
			<%}
				else{
			%>
				����
			<%}%>
      		</td>
    	</tr>
    	<tr> 
      		<td>����Ա��</td>
      		<td> <%=info.getMngName()%></td>
      		<td>��װ���ڣ� </td>
      		<td> <%=info.getAtmSetDate()%> </td>
    	</tr>
  	</table>
<hr>
<div align = 'center'><input type="button" name="close" value="����" class="inputbutton" onClick="javascript:history.go(-1)"></div>
</body>
</html>