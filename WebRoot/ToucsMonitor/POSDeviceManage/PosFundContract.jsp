<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<html>
<head>
<title>����ǩԼͳ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">

<div>
  <div align="center"><font face="����" size="+2">����ǩԼͳ�Ʊ�</font></div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td colspan="3" nowrap></td>
  </tr>
  <%
  Vector v=(Vector)request.getAttribute("PFC");
  if(v!=null){
		PosFundContract info=(PosFundContract)v.get(0);
  %>
  <tr bgcolor="#666666">
    <td colspan="3" nowrap>��ʼ����:<%=info.getStart_date()%> ~ ��ֹ����:<%=info.getEnd_date()%></td>
  </tr>
  <%}%>
  <tr bgcolor="#666666">
    <td colspan="3" nowrap></td>
  </tr>
  <tr bgcolor="#666666">
    <td width="30%" align="center" nowrap>������</td>
    <td width="35%" align="center" nowrap>ǩԼ����</td>
    <td width="35%" align="center" nowrap>��Լ����</td>
  </tr>
  <%

	if(v!=null){
		for(int i=0;i<v.size();i++){
		PosFundContract info=(PosFundContract)v.get(i);
		
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getBranch_id()%>>
    <td align="center" nowrap><%=info.getBranch_id()%></td>
    <td align="center" nowrap><%=info.getContranct_num()%></td>
    <td align="center" nowrap><%=info.getNocontranct_num()%></td>
  </tr>
  	   <%}
  	}
  	%>
</table>
</body>
</html>