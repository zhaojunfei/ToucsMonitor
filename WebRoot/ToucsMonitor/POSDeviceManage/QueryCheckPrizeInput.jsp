<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.style1 {
	font-size: 18px;
	color: #0000FF;
	font-weight: bold;
}
.style2 {
	font-size: 14px;
	color: #CC6600;
}
.style3 {
	color: #006600;
	font-weight: bold;
}
-->
</style>
</head>
<%
  String uid=request.getParameter("uid");
%>

<body>
<form action="/ToucsMonitor/posmerchantconfig?reqCode=10422&target=checkPZQuery&uid=<%=uid%>&InputDate="+document.form1.dateInput.value name="form1" method="post">
<div align="center">
    <table width="891" height="181" border="0">
    	<tr>
        	<td height="49" colspan="6"> <div align="center"><span class="style1">POS 抽 奖 查 询 输 入 页</span></div></td>
      	</tr>
      	<tr>
        	<td width="24" height="70">&nbsp;</td>
        	<td width="44">&nbsp;</td>
        	<td width="316"><div align="center" class="style2">
          		<div align="right" class="style3">请输入查询日期:</div></div>
          	</td>
        	<td width="356"> <input name="dateInput" type="text" id="dateInput" size="16" maxlength="8">*(YYYYMMDD)</td>
        	<td width="87">&nbsp;</td>
        	<td width="38">&nbsp;</td>
      	</tr>
      	<tr>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
        	<td><div align="right">
          		<input type="submit" name="Submit" value="确定">
          		<input type="button" name="cancel" value="取消" onClick="history.go(-1);"></div>
          	</td>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
      	</tr>
    </table>
</div>
</form>
</body>
</html>