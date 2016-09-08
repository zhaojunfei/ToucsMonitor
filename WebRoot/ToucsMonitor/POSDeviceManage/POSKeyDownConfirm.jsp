<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.common.*,com.adtec.toucs.monitor.loginmanage.*"%>
<%
    try{
        LoginInfo login = (LoginInfo)request.getAttribute("Login");
        if (login == null) {
            throw new MonitorException(ErrorDefine.NOT_AUTHORIZED,"");
        }
%>
<html>
<head>
<title>POS设备管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body   bgcolor="#CCCCCC" text="#000000">
<form name="form1" method="post"   action="/ToucsMonitor/POSDeviceManage" >
  <h2><font face="隶书">POS密钥下载</font></h2>
<%
    String pos_p_code =(String) request.getAttribute("posPCode");
    String pos_dcc_code = (String) request.getAttribute("posDccCode");
    String pos_merchant_name = (String) request.getAttribute("posMerchantName");
	String pos_organ_name = (String) request.getAttribute("posOrgName");
%>
<input name="pos_p_code" type="hidden" value=<%=pos_p_code%> >
<input name="reqCode" type="hidden" value="12103" >
<input name="target" type="hidden" value="keyDown" >

<table width = "80%">
<tr><td colspan="2"><font color="#FF0000">请确认是否下载POS密钥</font></td></tr>
<tr>
<td width = "20%">19位POS编号：</td>
<td width = "80%"><%=pos_dcc_code%></td>
</tr>
<tr>
<td width = "20%">POS顺序号：</td>
<td width = "80%"><%=pos_p_code%></td>
</tr>
<tr>
<td width = "20%">商户名称：</td>
<td width = "80%"><%=toucsString.unConvert(pos_merchant_name)%></td>
</tr>
<tr>
<td width = "20%">管辖支行：</td>
<td width = "80%"><%=toucsString.unConvert(pos_organ_name)%></td>
</tr>
<tr>
<td colspan="2"><input type="button" name="返回" value="返回" onClick="history.go(-1);" class="inputbutton">  <input type="submit" name="Submit" value="提交"  class="inputbutton">
</td>
</tr>
</table>
</form>
</body>
</html>
<%
    }
    catch (MonitorException ex){
        ex.errProc(request,response);
    }
%>