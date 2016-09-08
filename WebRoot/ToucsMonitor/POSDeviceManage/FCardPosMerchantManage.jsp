<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body   bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">外卡商户管理</font></h2>
<%
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSMERCHANTADD");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTDELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTCLEAR");
	boolean canClear=(permFlag!=null&&permFlag.equals("1"));
	canAdd = true;
	canModify=true;
	canDel=true;
	canQuery=true;
%>
	<table width="100%" cellspacing="0">
  		<tr>
    		<td height="10">
      			<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr>
    		<td>
      		<%
      			if(canAdd){
      		%>
      				<input type="button" name="add" value="添加" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/fcardmerchantconfig?reqCode=10501&target=page&uid=<%=uid%>' <%=canAdd?"":"style='display:none'"%> >
	  		<%
	  			}
	  		%>
    		</td>
  		</tr>
  		<tr>
    		<td height="10">
      			<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr>
    		<td height="20">
      			<div align="center">
        		<jsp:include page="FCardPosMerchantList.jsp" flush="true"/>
      			</div>
    		</td>
  		</tr>
  		<tr>
    		<td height="10">
      			<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr>
    		<td>
      			<div align="right"> </div>
    		</td>
  		</tr>
	</table>
<p>&nbsp;</p>
</body>
</html>