<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>orgList</title>
<%
	String  MSG = String.valueOf(request.getAttribute("message"));
	Hashtable operVH = (Hashtable)request.getAttribute("operVH");
	Vector orgV = (Vector)request.getAttribute("orgV");
	if(orgV == null || operVH == null) {
  		out.println("���ݲ���ʧ�ܣ�");
 	 	return ;
	}
	if(!String.valueOf(operVH.get("10104")).trim().equals("0")){
  		out.println("�Բ�����û�в鿴��ҳ��Ȩ�ޣ�");
  		return;	
	}
%>
<Script>
function direct( de ){
  	window.location.href=de;
}

function ConfirmDel(url){
  	if(confirm("��Ҫɾ���û�����Ϣ�͸û�����Ͻ�������¼�������Ϣ��ȷʵҪɾ���û�����Ϣ��")){
    	window.location.href=url;
  	}
}
function MSG(){
<%
	if(MSG != null && !MSG.trim().equals("null") && !MSG.trim().equals("")){
%>
  		alert("<%=MSG%>");
<%
	}
%>
}
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" onload="javascript:MSG()">
<h2><font face="����">��������</font> </h2>
<hr align="left" noshade>
	<input type="submit" name="Submit" value="��ӻ���" <%=operVH.get("10101").equals("0")?"":"style='display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10101')";>
	<input type="submit" name="Submit2" value="��ѯ������Ϣ" <%=operVH.get("10104").equals("0")?"":"'display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10104')";>
	<input type="submit" name="Submit3" value="�޸Ļ�����Ϣ" <%=operVH.get("10102").equals("0")?"":"'display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10104')";>
<hr align="left" noshade>
	<table  cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr>
    		<td >��������</td>
		    <td >��������</td>
		    <td >��������</td>
		    <td >�ϼ�����</td>
		    <td >��ͼ�ļ����</td>
		    <td >IP��ַ</td>
		    <td >������ַ</td>
		    <td >��ϵ��</td>
		    <td >��ϵ�绰</td>
		    <td >ҵ������</td>
    		<td colspan="2" align="center">����</td>
  		</tr>
  		<%
			for (int i = 0; i < orgV.size(); i++) {
		  		Hashtable orgHT = new Hashtable();
		  		orgHT = (Hashtable)orgV.get(i);
		  		if (orgHT != null) {
  		%>
  		<tr bgcolor="#FFFFFF">
    		<td ><%=String.valueOf(orgHT.get("org_id")).trim().equals("")?"&nbsp":orgHT.get("org_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_name")).trim().equals("")?"&nbsp":orgHT.get("org_name").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_level")).trim().equals("")?"&nbsp":orgHT.get("org_level").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("p_org_id")).trim().equals("")?"&nbsp":orgHT.get("p_org_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("map_file_id")).trim().equals("")?"&nbsp":orgHT.get("map_file_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("ip_address")).trim().equals("")?"&nbsp":orgHT.get("ip_address").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_address")).trim().equals("")?"&nbsp":orgHT.get("org_address").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("contractor")).trim().equals("")?"&nbsp":orgHT.get("contractor").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("phoneno")).trim().equals("")?"&nbsp":orgHT.get("phoneno").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("oper_type")).trim().equals("")?"&nbsp":orgHT.get("oper_type").toString()%></td>
		    <%
      			if(operVH.get("10102").equals("0")){
			%>
    		<td align="center">
    			<a href="/ToucsMonitor/ToucsMonitor/usermanager/orgModify.jsp?orgid=<%=orgHT.get("org_id")%>">�޸�</a>
      		</td>
    		<%
      			}else{	
			%>
    		<td align="center">&nbsp;</td>
    		<%
      			}
      			if(operVH.get("10103").equals("0")){
			%>
    		<td  align="center">
    			<a href="javascript:ConfirmDel('/ToucsMonitor/orgManager?orgid=<%=orgHT.get("org_id")%>&txcode=10103')">ɾ��</a>
    		</td>
    		<%
      			}else{
			%>
    		<td align="center">&nbsp;</td>
    		<%
      			}
			%>
  		</tr>
  		<%
   			}else {
    			out.println("����������������");
  			}
		}
  		%>
</table>
<hr align="left" noshade>
</body>
</html>