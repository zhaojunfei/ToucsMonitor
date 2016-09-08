<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.ThirdPartyManage.*" %>

<%!
private String getOperLink(String reqCode,String target,String branch_id,String teller_id,String uid){
	String ret="/ToucsMonitor/thirdPartyTeller?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&branch_id="+branch_id+"&teller_id="+teller_id;
	return ret;
}
%>

<html>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该柜员吗？")){
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
	<div align="left">第三方柜员列表 </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
	    	<td width="10%" align="center" nowrap>序号</td>
	    	<td width="20%" align="center" nowrap>渠道号</td>
	    	<td width="20%" align="center" nowrap>机构号</td>
	    	<td width="20%" align="center" nowrap>柜员号</td>
	    	<td width="30%" align="center" nowrap>管理</td>
	 	</tr>
		<%
		Vector v=(Vector)request.getAttribute("ThirdPartyTellerList");
		String branch_id="";
	    String teller_id="";
	    String channel_id ="";
	  	if(v!=null){
			for(int i=0;i<v.size();i++){
				ThirdPartyTellerInfo info=(ThirdPartyTellerInfo)v.get(i);
				channel_id= info.getChannel_id();
				branch_id=info.getBranchid();
	      		teller_id=info.getTellerid();
	   %>
	  	<tr bgcolor="#FFFFFF" id=<%=info.getTellerid()%>>
	    	<td align="center" nowrap><%=i+1%></td>
	    	<td align="center" nowrap><%=channel_id%></td>
	    	<td align="center" nowrap><%=branch_id%></td>
	    	<td align="center" nowrap><%=teller_id%></td>
	    	<td align="center">
	        	<a href="<%=getOperLink("13203","page",branch_id,teller_id,uid)%>">[修改] </a>
	        	<a href="javascript:onDelete('<%=getOperLink("13202","",branch_id,teller_id,uid)%>')">[删除]</a>
	  		 	<%if(info.getUseflag().equals("1")){%>
	        	<a href="<%=getOperLink("13205","page",branch_id,teller_id,uid)%>">[清理]</a>
	        	<%}%>
	    	</td>
	  	</tr>
	  	<%	}
	  	}
	  	%>
	</table>
</body>
</html>