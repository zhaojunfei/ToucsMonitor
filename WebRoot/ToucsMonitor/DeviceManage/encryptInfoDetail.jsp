<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<html>
<head>
	<title>encryptInfoDetail.jsp</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
</head>
<body bgcolor="#CCCCCC" text="#000000" >
<h2><font face="隶书">加密机设备配置信息</font></h2>
<hr>
<%
	DeviceManageBean DM = new DeviceManageBean();
	ATMInfo info = DM.queryEncryptInfo(request.getParameter("encryCode"));
%>
	<table width="100%">
    	<tr> 
      		<td>编号：</td>
      		<td><%=info.getAtmCode()%></td>
      		<td>名称： </td>
      		<td><%=info.getAtmName()%></td>
    	</tr>
    	<tr> 
      		<td>IP地址：</td>
      		<td> <%=info.getNetAddress()%></td>
      		<td>端口号：</td>
      		<td><%=info.getHostPort()%></td>
    	</tr>
    	<tr> 
      		<td>类型：</td>
      		<td> 
			<%
				if(info.getAtmType().trim().equals("0")){
			%>
				生产加密机
			<%}
				else{
			%>
				管理加密机
			<%}%>
      		</td>
      		<td>管理员电话：</td>
      		<td> <%=info.getMngPhone()%></td>
    	</tr>
    	<tr> 
      		<td>运行标志：</td>
      		<td> 
			<%
				if(info.getRunStat().trim().equals("0")){
			%>
				故障
			<%}
				else{
			%>
				正常
			<%}%>
      		</td>
      		<td>启用标志：</td>
      		<td> 
			<%
				if(info.getUseFlag().trim().equals("2")){
			%>
				未启用
			<%}
				else{
			%>
				启用
			<%}%>
      		</td>
    	</tr>
    	<tr> 
      		<td>管理员：</td>
      		<td> <%=info.getMngName()%></td>
      		<td>安装日期： </td>
      		<td> <%=info.getAtmSetDate()%> </td>
    	</tr>
  	</table>
<hr>
<div align = 'center'><input type="button" name="close" value="返回" class="inputbutton" onClick="javascript:history.go(-1)"></div>
</body>
</html>