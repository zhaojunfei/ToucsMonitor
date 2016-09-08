<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>

<%!
private String getOperLink(String reqCode, String target,String key, String uid) {
		String ret = "/ToucsMonitor/bffc?reqCode=" + reqCode + "&uid=" + uid
				+ "&target=" + target + "&fc_id=" + key;
		return ret;
}
%>

<html>
	<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该合作单位吗？")){
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
	<div align="left">
		烟草公司列表
	</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="10%" align="center" nowrap>序号</td>
			<td width="20%" align="center" nowrap>单位编号</td>
			<td width="40%" align="center" nowrap>单位名称</td>
			<td width="30%" align="center" nowrap>管理</td>
		</tr>
		<%
			Vector v = (Vector) request.getAttribute("BfFcList");
			String fc_id = "";
			String name = "";
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					BfFcInfo info = (BfFcInfo) v.get(i);
					fc_id = info.getFc_id();
					name = info.getName();
		%>
		<tr bgcolor="#FFFFFF" id=<%=info.getFc_id()%>>
			<td align="center" nowrap><%=i + 1%></td>
			<td align="center" nowrap><%=fc_id%></td>
			<td align="center" nowrap><%=name%></td>
			<td align="center">
				<a href="<%=getOperLink("17903", "page", fc_id, uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("17902", "", fc_id, uid)%>')">[删除]</a>
				<%
				if (info.getSecu_kind().equals("0")) {
				%>
				<a href="<%=getOperLink("17906", "", fc_id, uid)%>">[生成主密钥]</a>
				<%
				} else {
					if (info.getSecu_kind().equals("1")) {
				%>
				<a href="<%=getOperLink("17906", "", fc_id,
												uid)%>">[生成主密钥并打印]</a>
				<%
					}
				}
				%>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
	</body>
</html>
