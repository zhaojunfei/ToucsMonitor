<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body onContextMenu="return false;" bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">商户管理</font></h2>
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
      <input type="button" name="add" value="添加" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10401&target=page&uid=<%=uid%>'
	  	 <%=canAdd?"":"style='display:none'"%>>
	  <%}%>
	 <!-- <%if(canModify){%>
      <input type="button" name="add2" value="修改" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10402&target=page&uid=<%=uid%>'
	  	 <%=canModify?"":"style='display:none'"%>>
	  <%}%>
            -->
	  <%if(canQuery){%>
      <input type="button" name="query" value="查询" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10404&target=page&uid=<%=uid%>'
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
        <jsp:include page="PosMerchantList.jsp" flush="true"/>
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
