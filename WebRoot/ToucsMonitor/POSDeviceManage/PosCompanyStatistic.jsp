<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<html>
<head>
<title>��˾����ͳ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">

<div>
  <div align="center"><font face="����" size="+2">����ͳ�Ʊ�</font></div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td colspan="6" nowrap></td>
  </tr>
  <%
  Vector v=(Vector)request.getAttribute("PCStatistic");
  if(v!=null){
		PosCompanyStatistic info=(PosCompanyStatistic)v.get(0);
  %>
  <tr bgcolor="#666666">
    <td colspan="6" nowrap>��˾���:<%=info.getCompany_id()%></td>
  </tr>
  <tr bgcolor="#666666">
    <td colspan="6" nowrap>��ʼ����:<%=info.getStart_date()%> ~ ��ֹ����:<%=info.getEnd_date()%>
	<a href="./servletunion/dlservlet?path=/home/weblogic/bea/user_projects/domains/adtecdomain/loadfile/loadStatistic/&file=company_statistics.xls"  target="_blank"> ���ؽ���ͳ����Ϣ�� </a>
	</td>
  </tr>
  <%}%>
  <tr bgcolor="#666666">
    <td colspan="6" nowrap></td>
  </tr>
  <tr bgcolor="#666666">
    <td width="5%" align="center" nowrap>����</td>
    <td width="16%" align="center" nowrap>�̻����</td>
    <td width="21%" align="center" nowrap>���ѱ���</td>
    <td width="20%" align="center" nowrap>���ѽ��</td>
    <td width="18%" align="center" nowrap>�˻�����</td>
	<td width="20%" align="center" nowrap>�˻����</td>
  </tr>
  <%
	/*Vector v=(Vector)request.getAttribute("PCStatistic");*/

	if(v!=null){
		for(int i=0;i<v.size();i++){
		PosCompanyStatistic info=(PosCompanyStatistic)v.get(i);
		 
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getCompany_id()%>>
    <td align="center" nowrap><%=info.getTrans_date()%></td>
    <td align="center" nowrap><%=info.getMerchant_id()%></td>
    <td align="center" nowrap><%=info.getConsume_num()%></td>
    <td align="center" nowrap><%=info.getConsume_amount()%></td>
    <td align="center" nowrap><%=info.getRegoods_num()%></td>
	<td align="center" nowrap><%=info.getRegoods_amount()%></td>
  </tr>
  	   <%}
  	}
  	%>
</table>
</body>
</html>