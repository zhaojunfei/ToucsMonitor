<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>roleList</title>
<%
	String MSG = String.valueOf(request.getAttribute("message"));
	Vector roleV = (Vector)request.getAttribute("roleV");
	Hashtable operVH = (Hashtable)request.getAttribute("operVH");
	if(roleV == null || operVH == null) {
  		out.println("���ݲ���ʧ�ܣ�");
  		return;
	}
	if(!String.valueOf(operVH.get("10304")).trim().equals("0")){
  		out.println("�Բ�����û�в鿴��ҳ��Ȩ�ޣ�");
  		return;
	}
%>
<script>
function MSG(){
<%
	if(MSG != null && !MSG.trim().equals("null")){
%>
  	alert("<%=MSG%>");
<%
	}
%>
}  
</script>
<Script>
function direct( de ){
	window.location.href=de;
}
function ConfirmDel(url){
  	if(confirm("ȷʵҪɾ���ý�ɫ��")){
    	window.location.href=url;
  	}
}
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" onload="javascript:MSG()">
<h2><font face="����">��ɫ����</font></h2>
<hr align="left" noshade>
<p>
	<input type="submit" name="Submit" value="��ӽ�ɫ" <%=operVH.get("10301").equals("0")?"":"style='display:none'"%> class="inputbutton" onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10301');">
  	<input type="submit" name="Submit2" value="��ѯ��ɫ��Ϣ" <%=operVH.get("10304").equals("0")?"":"style='display:none'"%> class="inputbutton" onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10304');">
  	<input type="submit" name="Submit3" value="�޸Ľ�ɫ��Ϣ" <%=operVH.get("10302").equals("0")?"":"style='display:none'"%> class="inputbutton" onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10304');">
</p>
<hr align="left" noshade>
	<table cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr> 
    		<td >��ɫ����</td>
    		<td >��ɫ����</td>
    		<td colspan="3" align="center">����</td>
  		</tr>
  		<%
  			for (int i = 0; i < roleV.size(); i++) {
  				Hashtable roleHT = new Hashtable();
  				roleHT = (Hashtable)roleV.get(i);
  				if (roleHT != null) {
  		%>
  		<tr bgcolor="#FFFFFF" > 
    		<td ><%=String.valueOf(roleHT.get("role_name")).trim().equals("")?"&nbsp":roleHT.get("role_name").toString()%></td>
    		<td  height="25"><%=String.valueOf(roleHT.get("role_desc")).trim().equals("")?"&nbsp":roleHT.get("role_desc").toString()%></td>
    	<%
      		if(operVH.get("10302").equals("0")){
		%>
    		<td  height="25" align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/roleModify.jsp?roleid=<%=roleHT.get("role_id").toString()%>">�޸�</a></td>
    	<%
      		}else{
		%>
    		<td  height="25" align="center">&nbsp;</td>
    	<%
      		}
      		if(operVH.get("10303").equals("0")){
		%>
    		<td height="25" align="center"><a href="javascript:ConfirmDel('/ToucsMonitor/roleManager?roleid=<%=roleHT.get("role_id").toString()%>&rolename=<%=roleHT.get("role_name").toString()%>&txcode=10303')">ɾ��</a></td>
    	<%
      		}else{
		%>
    		<td height="25" align="center">&nbsp;</td>
    	<%
     	 	}
      		if(operVH.get("10305").equals("0")){
		%>
    		<td height="25" align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/roleResource.jsp?roleid=<%=roleHT.get("role_id").toString()%>&rolename=<%=roleHT.get("role_name").toString()%>">Ȩ��</a></td>
    	<% 
      		}else{
		%>
    		<td height="25" align="center">&nbsp;</td>
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