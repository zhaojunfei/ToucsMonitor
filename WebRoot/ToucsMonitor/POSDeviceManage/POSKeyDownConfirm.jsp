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
<title>POS�豸����</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body   bgcolor="#CCCCCC" text="#000000">
<form name="form1" method="post"   action="/ToucsMonitor/POSDeviceManage" >
  <h2><font face="����">POS��Կ����</font></h2>
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
<tr><td colspan="2"><font color="#FF0000">��ȷ���Ƿ�����POS��Կ</font></td></tr>
<tr>
<td width = "20%">19λPOS��ţ�</td>
<td width = "80%"><%=pos_dcc_code%></td>
</tr>
<tr>
<td width = "20%">POS˳��ţ�</td>
<td width = "80%"><%=pos_p_code%></td>
</tr>
<tr>
<td width = "20%">�̻����ƣ�</td>
<td width = "80%"><%=toucsString.unConvert(pos_merchant_name)%></td>
</tr>
<tr>
<td width = "20%">��Ͻ֧�У�</td>
<td width = "80%"><%=toucsString.unConvert(pos_organ_name)%></td>
</tr>
<tr>
<td colspan="2"><input type="button" name="����" value="����" onClick="history.go(-1);" class="inputbutton">  <input type="submit" name="Submit" value="�ύ"  class="inputbutton">
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