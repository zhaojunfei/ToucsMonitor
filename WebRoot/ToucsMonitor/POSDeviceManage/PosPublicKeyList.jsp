<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<jsp:directive.page import="com.adtec.toucs.monitor.common.Debug"/>


<%!
private String getOperLink(String reqCode, String target, String rid, String index, String uid) {		
	String ret = "/ToucsMonitor/poskeyconfig?reqCode=" + reqCode
			+ "&uid=" + uid + "&target=" + target + "&rid=" + rid+ "&index=" + index;
	return ret;
}
%>

<html>
<head>
	<title>公钥列表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除吗？")){
		location.href=linkDel;
	}
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<div>
	<div align="left">公钥列表</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666"><%--
			<td width="10%" align="center" nowrap>
				记录数
			</td>--%>
			<td width="10%" align="center" nowrap>RID</td>
			<td width="25%" align="center" nowrap>公钥索引</td>
			<td width="50%" align="center" nowrap>公钥有效期</td>
			<td width="15%" align="center" nowrap>管理</td>
		</tr>
	  	<%
			List l=(List)request.getAttribute("publickeylist");
			String rid="";
			String index="";
			String expire_date="";
			if(l!=null){
       			for(int i=0;i<l.size();i++){
					POSPublicKey info=(POSPublicKey)l.get(i);		
 		%>
	
		<%----%><%--
		<%
		Vector v = (Vector) request.getAttribute("publickeyList");
		String rid = "";
		String index = "";
		String expire_date = "";

		if (v != null) {
			for (int i = 0; i < v.size(); i++) {
				POSPublicKey info = (POSPublicKey) v.get(i);
		%>--%>
		<tr bgcolor="#FFFFFF" id=<%=info.getRid()%>><%--
	    	<td width="6%" >
   				<div align="center"><%=i+1%> </div>
 				</td>--%>
 			<td align="center" nowrap>
				<%=info.getRid()%>
			</td>
			<td align="center" nowrap>
				<%=info.getIndex()%>
			</td>
			<td align="center" nowrap>
				<%=info.getExpire_date()%>
			</td>
			<td align="center">
				<a href="<%=getOperLink("13803", "page", info.getRid(),info.getIndex(),uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("13804", "", info.getRid(),info.getIndex(),uid)%>')">[删除]</a>
			</td>
		</tr>
		<%
				}
			}
		%>
	</table>
</body>
</html>