<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*"%>
<html>
<head>
<title>userList</title>
<%
	String MSG = String.valueOf(request.getAttribute("message"));
	LoginInfo Linfo = (LoginInfo)request.getAttribute("LoginInfo");
	Vector userV = (Vector)request.getAttribute("userV");
	Hashtable operVH = (Hashtable)request.getAttribute("operVH");
	if(userV == null || operVH == null) {
  		out.println("���ݲ���ʧ�ܣ�");
  		return;
	}
	if(!String.valueOf(operVH.get("10204")).trim().equals("0")){
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
  	if(confirm("ȷʵҪɾ�����û���")){
    	window.location.href=url;
  	}
}
function ConfirmUnlock(url){
  	if(confirm("ȷʵҪ�������û���")){
    	window.location.href=url;
  	}
}
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" onload="javascript:MSG()">
<h2><font face="����">�û�����</font></h2>
<hr align="left" noshade>
<input type="submit" name="Submit" value="����û�" class="inputbutton" <%=operVH.get("10201").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10201');">
<input type="submit" name="Submit2" value="��ѯ�û���Ϣ" class="inputbutton"  <%=operVH.get("10204").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10204');">
<input type="submit" name="Submit3" value="�޸��û���Ϣ"  class="inputbutton"  <%=operVH.get("10202").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10204');">
<hr align="left" noshade>
	<table cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666"> 
    		<td height="17">�û��ʺ�</td>
    		<td height="17">�û�����</td>
    		<td height="17">����ʱ��</td>
    		<td height="17">���ʱ��</td>
    		<td height="17">��������</td>
    		<td height="17">�û�˵��</td>
    		<td colspan="3" align="center" height="17">����</td>
  		</tr>
  		<%
  			for (int i = 0; i < userV.size(); i++) {
    			Hashtable userHT = new Hashtable();
    			userHT = (Hashtable)userV.get(i);
    			if (userHT != null) {
		%>
  		<tr bgcolor="#FFFFFF" > 
    		<td><%=String.valueOf(userHT.get("user_id")).trim().equals("")?"&nbsp;":userHT.get("user_id").toString()%></td>
    		<td><%=String.valueOf(userHT.get("user_name")).trim().equals("")?"&nbsp;":userHT.get("user_name").toString()%></td>
    		<td><%=String.valueOf(userHT.get("employ_date")).trim().equals("")?"&nbsp;":userHT.get("employ_date").toString().substring(0,4)+"-"+userHT.get("employ_date").toString().substring(4,6)+"-"+userHT.get("employ_date").toString().substring(6,8)%></td>
    		<td><%=String.valueOf(userHT.get("fire_date")).trim().equals("")?"&nbsp;":userHT.get("fire_date").toString().substring(0,4)+"-"+userHT.get("fire_date").toString().substring(4,6)+"-"+userHT.get("fire_date").toString().substring(6,8)%></td>
    		<td><%=String.valueOf(userHT.get("org_id")).trim().equals("")?"&nbsp;":userHT.get("org_id").toString()%></td>
    		<td><%=String.valueOf(userHT.get("user_desc")).trim().equals("")?"&nbsp;":userHT.get("user_desc").toString()%></td>
    		<%
      			if(operVH.get("10202").equals("0")){
			%>
    				<td align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/userModify.jsp?userid=<%=userHT.get("user_id")%>&orgid=<%=Linfo.getOrgID()%>">�޸�</a></td>
    		<%
      			}else{
			%>
    		<td align="center">��</td>
    		<%
      			}
      			if (String.valueOf(userHT.get("user_state")).trim().equals("1") ) { 
      				if(operVH.get("10203").equals("0")){
			%>
    					<td align="center"><a href="javascript:ConfirmDel('/ToucsMonitor/usermanagerservlet?userid=<%=userHT.get("user_id")%>&oper_type=10203')">ɾ��</a></td>
    		<%
      				}else{
			%>
    		<td align="center">��</td>
    		<%
      				}
      			}else {
    		%>
    		<td align="center"><a href="javascript:ConfirmUnlock('/ToucsMonitor/usermanagerservlet?userid=<%=userHT.get("user_id")%>&oper_type=10203&op_code=unlock')">����</a></td>
    		<%
      			}
      			if(operVH.get("10205").equals("0")){
			%>
    				<td align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/givePower.jsp?userid=<%=userHT.get("user_id")%>">Ȩ��</a></td>
    		<% 
      			}else{
			%>
    		<td align="center">��</td>
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