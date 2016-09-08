<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>

<%!
private String getOperLink(String reqCode, String target,String key1, String key2, String uid) {
		String ret = "/ToucsMonitor/bfteller?reqCode=" + reqCode + "&uid=" + uid
				+ "&target=" + target + "&op_no=" + key1 + "&fc_id=" + key2;
		return ret;
}
%>

<html>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该期货柜员吗？")){
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
	String uid = request.getParameter("uid");
%>
<div>
	<div align="left">期货柜员列表</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="10%" align="center" nowrap>序号</td>
			<td width="30%" align="center" nowrap>柜员号</td>
			<td width="40%" align="center" nowrap>合作单位编号</td>
			<td width="20%" align="center" nowrap>管理</td>
		</tr>
		<%
			Vector v = (Vector) request.getAttribute("BfTellerList");
			String op_no = "";
			String fc_id = "";
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					BfTellerInfo info = (BfTellerInfo) v.get(i);
					op_no = info.getOp_no();
					fc_id = info.getFc_id();
		%>
		<tr bgcolor="#FFFFFF" id=<%=info.getOp_no()%>>
			<td align="center" nowrap><%=i + 1%></td>
			<td align="center" nowrap><%=op_no%></td>
			<td align="center" nowrap><%=fc_id%></td>
			<td align="center">
				<a href="<%=getOperLink("17923", "page", op_no, fc_id, uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("17922", "", op_no, fc_id, uid)%>')">[删除]</a>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>