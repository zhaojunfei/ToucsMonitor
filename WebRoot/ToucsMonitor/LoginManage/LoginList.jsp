<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*" %>



<html>
<head>
<title>
LoginList
</title>
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onForceLogout(url){
	if(confirm("ȷʵҪǩ�˸��û���"))
		location.href=url;	
}
</script>
<body  bgcolor="#CCCCCC">
<h2> <font face="����">��¼�û��б�:</font></h2>
<% Vector list=(Vector)request.getAttribute("login_list");
   String uid=request.getParameter("uid");%>
��ǰ��¼��:<%=list.size()%>
<hr>
<table width="100%" border="0" bgcolor="#000000" cellpadding="1" cellspacing="1">
  <tr bgcolor="#666666"> 
    <td> 
      <div align="center">�û���</div>
    </td>
    <td> 
      <div align="center">��������</div>
    </td>
    <td> 
      <div align="center">��¼����վIP</div>
    </td>
    <td> 
      <div align="center">��¼ʱ��</div>
    </td>
    <td> 
      <div align="center"></div>
    </td>
  </tr>
  <% for(int i=0;i<list.size();i++){
       LoginInfo info=(LoginInfo)list.elementAt(i);
  %>
  <tr bgcolor="#FFFFFF"> 
    <td height="7"> 
      <div align="center"><%=info.getUserName()%></div>
    </td>
    <td height="7"> 
      <div align="center"><%=info.getOrgID()%></div>
    </td>
    <td height="7"> 
      <div align="center"><%=info.getIP()%></div>
    </td>
    <td height="7"> 
      <div align="center"><%=info.getOnLineTime()%></div>
    </td>
    <td height="7"> 
	  <div align="center">
      <%String url="login?reqCode=10203&uid="+uid+"&tagUid="+info.getUserID();%>
      <a href=javascript:onForceLogout("<%=url%>")>[ǩ��]</a>	  
	  </div>
    </td>
  </tr>
  <% } %>
</table>
</html>
