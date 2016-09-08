<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>

<%!
private String getOperLink(String reqCode, String target,String key1, String key2, String uid) {
		String ret = "/ToucsMonitor/bfinvestor?reqCode=" + reqCode + "&uid=" + uid
				+ "&target=" + target + "&fc_id=" + key1 + "&investor_id=" + key2;
		return ret;
}
%>

<html>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该烟草公司客户吗？")){
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
	<div align="left">烟草公司客户列表</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="5%" align="center" nowrap>序号</td>
			<td width="15%" align="center" nowrap>烟草区县公司代码</td>
			<td width="30%" align="center" nowrap>卡号</td>
			<td width="30%" align="center" nowrap>烟草客户号</td>
			<td width="20%" align="center" nowrap>管理</td>
		</tr>
		<%
			Vector v = (Vector) request.getAttribute("BfInvestorList");
			String fc_id = "";
			String investor_id = "";
			String deposit_acc_no = "";
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					BfInvestorInfo info = (BfInvestorInfo) v.get(i);
					fc_id = info.getFc_id();
					investor_id = info.getInvestor_id();
					deposit_acc_no = info.getDeposit_acc_no();
		%>
		<tr bgcolor="#FFFFFF" id=<%=info.getInvestor_id()%>>
			<td align="center" nowrap><%=i + 1%></td>
			<td align="center" nowrap><%=fc_id%></td>
			<td align="center" nowrap><%=investor_id%></td>
			<td align="center" nowrap><%=deposit_acc_no%></td>
			<td align="center">
				<a href="<%=getOperLink("17913", "page", fc_id, investor_id, uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("17912", "", fc_id, investor_id, uid)%>')">[删除]</a>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>