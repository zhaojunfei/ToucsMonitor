<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.net.*" %>
<%

String uid = request.getParameter("uid");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>My JSP 'POSBCUpload.jsp' starting page</title>  
		<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
		<link rel="stylesheet" href="/v5_css.css" type="text/css">
  	</head>
<script>
	function check(){
		var path = document.getElementById("f1").value;
		if(path==null){
			alert("请指定正确的路径 ！");
			return false;
		}
		return true;
	}
</script>
<body>
<form name="form1" method="post" action="/ToucsMonitor/posBCCard?reqCode=16807&uid=<%=uid%>">
	<p><font face="隶书" size="+2">预付卡批结果上传下载</font>	
	<table id="tb1" border="0" width="100%">
		<tr>
			<td width="80%" align="right"><a href="D:/CCB/pos_lc_card.xls">预付卡文件下载</a></td>
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
			<td width="40%" align="left"><input type="submit" id="bt2" value="上传" onclick="return check()"></td>
		</tr> 
	</table>
		  上传文件前，请先下载预付卡批结果信息统一的上传模板，然后添加数据进行上传。
</form>  
</body>
</html>