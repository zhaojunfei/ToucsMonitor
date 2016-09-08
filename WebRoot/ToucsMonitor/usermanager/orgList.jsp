<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>orgList</title>
<%
	String  MSG = String.valueOf(request.getAttribute("message"));
	Hashtable operVH = (Hashtable)request.getAttribute("operVH");
	Vector orgV = (Vector)request.getAttribute("orgV");
	if(orgV == null || operVH == null) {
  		out.println("传递参数失败！");
 	 	return ;
	}
	if(!String.valueOf(operVH.get("10104")).trim().equals("0")){
  		out.println("对不起，你没有查看此页的权限！");
  		return;	
	}
%>
<Script>
function direct( de ){
  	window.location.href=de;
}

function ConfirmDel(url){
  	if(confirm("将要删除该机构信息和该机构所辖的所有下级机构信息，确实要删除该机构信息？")){
    	window.location.href=url;
  	}
}
function MSG(){
<%
	if(MSG != null && !MSG.trim().equals("null") && !MSG.trim().equals("")){
%>
  		alert("<%=MSG%>");
<%
	}
%>
}
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" onload="javascript:MSG()">
<h2><font face="隶书">机构管理</font> </h2>
<hr align="left" noshade>
	<input type="submit" name="Submit" value="添加机构" <%=operVH.get("10101").equals("0")?"":"style='display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10101')";>
	<input type="submit" name="Submit2" value="查询机构信息" <%=operVH.get("10104").equals("0")?"":"'display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10104')";>
	<input type="submit" name="Submit3" value="修改机构信息" <%=operVH.get("10102").equals("0")?"":"'display:none'"%> class="inputbutton"  onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10104')";>
<hr align="left" noshade>
	<table  cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr>
    		<td >机构编码</td>
		    <td >机构名称</td>
		    <td >机构级别</td>
		    <td >上级机构</td>
		    <td >地图文件编号</td>
		    <td >IP地址</td>
		    <td >机构地址</td>
		    <td >联系人</td>
		    <td >联系电话</td>
		    <td >业务种类</td>
    		<td colspan="2" align="center">操作</td>
  		</tr>
  		<%
			for (int i = 0; i < orgV.size(); i++) {
		  		Hashtable orgHT = new Hashtable();
		  		orgHT = (Hashtable)orgV.get(i);
		  		if (orgHT != null) {
  		%>
  		<tr bgcolor="#FFFFFF">
    		<td ><%=String.valueOf(orgHT.get("org_id")).trim().equals("")?"&nbsp":orgHT.get("org_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_name")).trim().equals("")?"&nbsp":orgHT.get("org_name").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_level")).trim().equals("")?"&nbsp":orgHT.get("org_level").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("p_org_id")).trim().equals("")?"&nbsp":orgHT.get("p_org_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("map_file_id")).trim().equals("")?"&nbsp":orgHT.get("map_file_id").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("ip_address")).trim().equals("")?"&nbsp":orgHT.get("ip_address").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("org_address")).trim().equals("")?"&nbsp":orgHT.get("org_address").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("contractor")).trim().equals("")?"&nbsp":orgHT.get("contractor").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("phoneno")).trim().equals("")?"&nbsp":orgHT.get("phoneno").toString()%></td>
		    <td ><%=String.valueOf(orgHT.get("oper_type")).trim().equals("")?"&nbsp":orgHT.get("oper_type").toString()%></td>
		    <%
      			if(operVH.get("10102").equals("0")){
			%>
    		<td align="center">
    			<a href="/ToucsMonitor/ToucsMonitor/usermanager/orgModify.jsp?orgid=<%=orgHT.get("org_id")%>">修改</a>
      		</td>
    		<%
      			}else{	
			%>
    		<td align="center">&nbsp;</td>
    		<%
      			}
      			if(operVH.get("10103").equals("0")){
			%>
    		<td  align="center">
    			<a href="javascript:ConfirmDel('/ToucsMonitor/orgManager?orgid=<%=orgHT.get("org_id")%>&txcode=10103')">删除</a>
    		</td>
    		<%
      			}else{
			%>
    		<td align="center">&nbsp;</td>
    		<%
      			}
			%>
  		</tr>
  		<%
   			}else {
    			out.println("哈哈哈，出错啦！");
  			}
		}
  		%>
</table>
<hr align="left" noshade>
</body>
</html>