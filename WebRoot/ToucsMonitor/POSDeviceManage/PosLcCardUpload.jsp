<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%

//String path = "./servletunion/dlservlet?path="+File.separator+"home"+File.separator+"weblogic"+File.separator+"bea"+File.separator+"user_projects"+File.separator+"domains"+"adtecdomain"+File.separator+"&file=pos_lc_card.xls";
String path="./servletunion/dlservlet?path=/home/weblogic/bea/user_projects/domains/adtecdomain/&file=pos_lc_card.xls";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'PosLcCardUpload.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">

  </head>
  <script type="text/javascript">

	function check(){
		var path = document.getElementById("f1").value;
		if(path==null||path=="" ){
			alert("请指定正确的路径 ！");
			return false;
		}
		return true;
	}

  </script>
  
  <body>
    <form  method="post"  enctype="multipart/form-data"  action="posLcCard.do?method=uploadFile">
		<p><font face="隶书" size="+2">理财POS卡表信息 excel文件上传下载</font></p>
		
		<table id="tb1" border="0" width="100%">
			<tr>
				<td width="80%" align="right"><a href="posLcCard.do?method=loadFile">excel模板下载</a></td>
				<td width="20"></td>
			</tr>
			<tr>
				<td colspan="2"><hr noshade></td>
			</tr>
		</table>
		<table id="tb2" border="0" width="100%">
			<tr>
				<td width="60%" align="right"><input type="file" name="file" id="f1"></td>
				<td width="40"></td>
			</tr>
			<tr>
				<td width="60%"></td>
				<td width="40%" align="left"><input type="submit" id="bt2" value="上传" onclick="return check()"></td>
			 </tr> 
		</table>
		 上传文件前，请先下载理财pos卡表信息统一的excel上传模板，然后添加数据进行上传!<br>
		 注意:文件上传成功后,请在规定的时间内(18:00-23:59或00:00-8:00)进行卡表刷新操作。
	</form>	
  </body>
</html>