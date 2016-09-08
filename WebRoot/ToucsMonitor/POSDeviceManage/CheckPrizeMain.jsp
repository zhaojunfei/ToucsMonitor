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
<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">抽奖管理</font></h2>
<%
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("CheckPrize");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTDELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTCLEAR");
	boolean canClear=(permFlag!=null&&permFlag.equals("1"));
%>
	<table width="100%" cellspacing="0">
  		<tr>
    		<td>
      			<div align="center">
        		<%if(canAdd){%>
        		<input type="button" name="add" value="添加抽奖规则" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10422&target=prize&uid=<%=uid%>'
	  	 		<%=canAdd?"":"style='display:none'"%>>
        		<input type="button" name="qry" value="查询抽奖规则" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10422&target=RuleQry&uid=<%=uid%>'>
        		<input type="button" name="query" value="查询中奖记录" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10422&target=prizeQuery&uid=<%=uid%>' >
        		<%}%>
        		<!-- <%if(canModify){%>
      			<input type="button" name="add2" value="修改" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10402&target=page&uid=<%=uid%>'
	  	 		<%=canModify?"":"style='display:none'"%>>
	  			<%}%>
            		-->
        		<%if(canQuery){%>
        		<input type="button" name="query" value="查询" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10404&target=&uid=<%=uid%>'
	 			<%=canQuery?"":"style='display:none'"%>>
        		<%}%>    
      			</div>
      			<div align="right"> </div>
      		</td>
  		</tr>
	</table>
<p>&nbsp;</p>
</body>
</html>