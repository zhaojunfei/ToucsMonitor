<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<jsp:directive.page import="com.adtec.toucs.monitor.common.Debug"/>

<%!
private String getOperLink(String reqCode, String target, String aid, String uid) {		
	String ret = "/ToucsMonitor/pospublicappconfig?reqCode=" + reqCode
		+ "&uid=" + uid + "&target=" + target + "&aid=" + aid;
	return ret;
}
%>

<html>
<head>
	<title>EMV�������б�</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("ȷʵҪɾ����")){
		location.href=linkDel;
	}
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<div>
	<div align="left">
		EMV�������б�
	</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="10%" align="center" nowrap>��¼��</td>
			<td width="10%" align="center" nowrap>AID</td>
			<td width="15%" align="center" nowrap>Ӧ��ѡ��ָʾ��</td>
			<td width="50%" align="center" nowrap>Ӧ�ð汾��</td>
			<td width="15%" align="center" nowrap>����</td>
		</tr>
		<%
			List l=(List)request.getAttribute("publicapplist");
			String aid="";
			String asi="";
			String appedition="";
			
			if(l!=null){
       			for(int i=0;i<l.size();i++){
					POSPublicApp info=(POSPublicApp)l.get(i);		
		%>
	
		<%--
		<%
			Vector v = (Vector) request.getAttribute("publicapplist");
			String rid = "";
			String index = "";
			String expire_date = "";
			
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					POSPublicApp info = (POSPublicApp) v.get(i);
		%>
		--%>
		<tr bgcolor="#FFFFFF" id=<%=info.getAid()%>>
	    	<td width="6%" >
   				<div align="center"><%=i+1%> </div>
 			</td>
			<td align="center" nowrap>
				<%=info.getAid()%>
			</td>
			<td align="center" nowrap>
				<%=info.getAsi()%>
			</td>
			<td align="center" nowrap>
				<%=info.getAppedition()%>
			</td>
			<td align="center">
				<a href="<%=getOperLink("13903", "page", info.getAid(),uid)%>">[�޸�] </a>
				<a href="javascript:onDelete('<%=getOperLink("13904", "", info.getAid(),uid)%>')">[ɾ��]</a>
			</td>
		</tr>
		<%
				}
			}
		%>
	</table>
</body>
</html>