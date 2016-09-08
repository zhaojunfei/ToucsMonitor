<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="qryOpt" class="com.adtec.toucs.monitor.devicemanage.QueryOption"/>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">ATM设备参数管理</font></h2>
<%
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("ATMREG");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMDEL");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMLOAD");
	boolean canLoad=(permFlag!=null&&permFlag.equals("1"));	
%>
	<table width="100%" cellspacing="0">
  		<tr> 
    		<td height="10"> 
      			<hr align="left" noshade>
    		</td>
  		</tr>
  		<tr> 
    		<td> 
      			<%if(canAdd){%>
      			<input type="button" name="add" value="添加" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=13001&uid=<%=uid%>'
	  	 		<%=canAdd?"":"style='display:none'"%>>
	  			<%}%>
	  			<%if(canModify){%>
      			<input type="button" name="add2" value="修改" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=13002&uid=<%=uid%>'
	  	 		<%=canModify?"":"style='display:none'"%>>
	  			<%}%>
	  			<%if(canQuery){%>
      			<input type="button" name="query" value="查询" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=13003&uid=<%=uid%>'
	 			<%=canQuery?"":"style='display:none'"%>>
      			<%}%>
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
        		<jsp:include page="AtmList.jsp" flush="true"/>
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
