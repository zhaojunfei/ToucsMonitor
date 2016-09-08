<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<%!
	private String getOperLink(String reqCode,String target,String key,String uid){
		String ret="/ToucsMonitor/posaccountconfig?reqCode="+reqCode+"&uid="+uid
			+"&target="+target+"&account="+key;
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
	if(window.confirm("确实要删除该设备吗？")){
		location.href=linkDel;
	}
}
</script>
<body   bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<div>
  <div align="left">对公帐户列表 </div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td width="10%" align="center" nowrap>序号</td>
    <td width="25%" align="center" nowrap>帐号</td>
    <td width="50%" align="center" nowrap>单位名称</td>
    <td width="15%" align="center" nowrap>管理</td>
  </tr>
  <%
  			Vector v=(Vector)request.getAttribute("accountList");
			String account="";
      String unit_name="";
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
				POSAccount info=(POSAccount)v.get(i);
				account=info.getAccount();
        unit_name=info.getUnitName();
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getAccount()%>>
    <td align="center" nowrap><%=i+1%></td>
    <td align="center" nowrap><%=account%></td>
    <td align="center" nowrap><%=unit_name%></td>
    <td align="center">
        <a href="<%=getOperLink("13702","page",account,uid)%>">[修改] </a>
        <a href="javascript:onDelete('<%=getOperLink("13704","",account,uid)%>')">[删除]</a>
    </td>
  </tr>
  <%}
  			}%>
</table>
</body>
</html>