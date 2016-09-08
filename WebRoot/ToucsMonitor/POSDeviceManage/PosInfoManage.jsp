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

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">POS设备信息管理</font></h2>
<%
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSINFOREG");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFODELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOCLEAR");
	boolean canClear=(permFlag!=null&&permFlag.equals("1"));

        permFlag=(String)request.getAttribute("POSTXNSET");
	boolean canSetTxn=(permFlag!=null&&permFlag.equals("1"));

        permFlag=(String)request.getAttribute("POSINFOSTART");
	boolean canStart=(permFlag!=null&&permFlag.equals("1"));

        permFlag=(String)request.getAttribute("POSINFOSTOP");
	boolean canStop=(permFlag!=null&&permFlag.equals("1"));
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
      <input type="button" name="add" value="添加" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posdeviceconfig?reqCode=13101&target=page&uid=<%=uid%>'
	  	 <%=canAdd?"":"style='display:none'"%>>
	  <%}%>

	  <%if(canQuery){%>
      <input type="button" name="query" value="查询" class="inputbutton" onClick=javascript:location.href='/ToucsMonitor/posdeviceconfig?reqCode=13103&target=page&uid=<%=uid%>'
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
        <jsp:include page="PosInfoList.jsp" flush="true"/>
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
<p>　</p>
</body>
</html>