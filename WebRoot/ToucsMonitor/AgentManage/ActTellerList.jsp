<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.AgentManage.*" %>

<%!
private String getOperLink(String reqCode,String target,String branch_id,String teller_id,String uid){
	String ret="/ToucsMonitor/actteller?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&branch_id="+branch_id+"&teller_id="+teller_id;
	return ret;
}
%>

<html>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("ȷʵҪɾ���ù�Ա��")){
		location.href=linkDel;
	}
}
</script>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<div>
  <div align="left">������Ա�б� </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="10%" align="center" nowrap>���</td>
    		<td width="10%" align="center" nowrap>������</td>
    		<td width="20%" align="center" nowrap>��Ա��</td>
    		<td width="60%" align="center" nowrap>����</td>
  		</tr>
  		<%
  			Vector v=(Vector)request.getAttribute("ActTellerList");
			String branch_id="";
      		String teller_id="";
  			if(v!=null){
	     		for(int i=0;i<v.size();i++){
					ActTellerInfo info=(ActTellerInfo)v.get(i);
					branch_id=info.getBranchid();
      				teller_id=info.getTellerid();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getTellerid()%>>
    		<td align="center" nowrap><%=i+1%></td>
    		<td align="center" nowrap><%=branch_id%></td>
    		<td align="center" nowrap><%=teller_id%></td>
    		<td align="center">
        		<a href="<%=getOperLink("17603","page",branch_id,teller_id,uid)%>">[�޸�] </a>
        		<a href="javascript:onDelete('<%=getOperLink("17602","",branch_id,teller_id,uid)%>')">[ɾ��]</a>
        		<%if(info.getUseflag().equals("1")){%>
        		<a href="<%=getOperLink("17605","page",branch_id,teller_id,uid)%>">[����]</a>
        		<%}%>
    		</td>
  		</tr>
  		<%} }%>
	</table>
</body>
</html>