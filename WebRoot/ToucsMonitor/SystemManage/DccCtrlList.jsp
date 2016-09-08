<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>

<%!
private String getOperLink(String reqCode, String target,String channel_id, String service, String uid) {
		String ret = "/ToucsMonitor/dccctrl?reqCode=" + reqCode + "&uid=" + uid
				+ "&target=" + target + "&channel_id=" + channel_id
				+ "&service=" + service;
		return ret;
}
%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该控制信息吗？")){
		location.href=linkDel;
	}
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<font face="隶书" size="+2">控制信息列表</font>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="5%" align="center" nowrap>序号</td>
			<td width="20%" align="center" nowrap>渠道代号</td>
			<td width="20%" align="center" nowrap>服务种类</td>
			<td width="15%" align="center" nowrap>单笔限额</td>
			<td width="15%" align="center" nowrap>累计限额</td>
			<td width="25%" align="center" nowrap>管理</td>
		</tr>
		<%
			Vector v = (Vector) request.getAttribute("DccCtrlList");
			String channel_id = "";
			String service = "";
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					DccCtrlInfo info = (DccCtrlInfo) v.get(i);
					channel_id = info.getChannel_id();
					service = info.getService();
		%>
		<tr bgcolor="#FFFFFF" id=<%=info.getChannel_id() + info.getService()%>>
			<td align="center" nowrap><%=i + 1%></td>
			<td align="center" nowrap><%=channel_id%></td>
			<td align="center" nowrap><%=service%></td>
			<td align="center" nowrap><%=info.getTrans_amt()%></td>
			<td align="center" nowrap><%=info.getTotal_amt()%></td>
			<td align="center">
				<a href="<%=getOperLink("17803", "page", channel_id, service,uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("17802", "", channel_id,service, uid)%>')">[删除]</a>
			</td>
		</tr>
		<%
				}
			}
		%>
	</table>
</body>
</html>