<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*"%>
<html>
<head>
<title>userList</title>
<%
	String MSG = String.valueOf(request.getAttribute("message"));
	LoginInfo Linfo = (LoginInfo)request.getAttribute("LoginInfo");
	Vector userV = (Vector)request.getAttribute("userV");
	Hashtable operVH = (Hashtable)request.getAttribute("operVH");
	if(userV == null || operVH == null) {
  		out.println("传递参数失败！");
  		return;
	}
	if(!String.valueOf(operVH.get("10204")).trim().equals("0")){
  		out.println("对不起，你没有查看此页的权限！");
  		return;
	}
%>
<script>
	function MSG(){
		<%
			if(MSG != null && !MSG.trim().equals("null")){
		%>
  		alert("<%=MSG%>");
		<%
			}
		%>
	}  
</script>
<Script>
function direct( de ){
  	window.location.href=de;
}
function ConfirmDel(url){
  	if(confirm("确实要删除该用户？")){
    	window.location.href=url;
  	}
}
function ConfirmUnlock(url){
  	if(confirm("确实要解锁该用户？")){
    	window.location.href=url;
  	}
}
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" onload="javascript:MSG()">
<h2><font face="隶书">用户管理</font></h2>
<hr align="left" noshade>
<input type="submit" name="Submit" value="添加用户" class="inputbutton" <%=operVH.get("10201").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10201');">
<input type="submit" name="Submit2" value="查询用户信息" class="inputbutton"  <%=operVH.get("10204").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10204');">
<input type="submit" name="Submit3" value="修改用户信息"  class="inputbutton"  <%=operVH.get("10202").equals("0")?"":"style='display:none'"%> onClick="direct('/ToucsMonitor/userMngServlet?reqCode=10204');">
<hr align="left" noshade>
	<table cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666"> 
    		<td height="17">用户帐号</td>
    		<td height="17">用户名称</td>
    		<td height="17">雇用时间</td>
    		<td height="17">解雇时间</td>
    		<td height="17">所属机构</td>
    		<td height="17">用户说明</td>
    		<td colspan="3" align="center" height="17">操作</td>
  		</tr>
  		<%
  			for (int i = 0; i < userV.size(); i++) {
    			Hashtable userHT = new Hashtable();
    			userHT = (Hashtable)userV.get(i);
    			if (userHT != null) {
		%>
  		<tr bgcolor="#FFFFFF" > 
    		<td><%=String.valueOf(userHT.get("user_id")).trim().equals("")?"&nbsp;":userHT.get("user_id").toString()%></td>
    		<td><%=String.valueOf(userHT.get("user_name")).trim().equals("")?"&nbsp;":userHT.get("user_name").toString()%></td>
    		<td><%=String.valueOf(userHT.get("employ_date")).trim().equals("")?"&nbsp;":userHT.get("employ_date").toString().substring(0,4)+"-"+userHT.get("employ_date").toString().substring(4,6)+"-"+userHT.get("employ_date").toString().substring(6,8)%></td>
    		<td><%=String.valueOf(userHT.get("fire_date")).trim().equals("")?"&nbsp;":userHT.get("fire_date").toString().substring(0,4)+"-"+userHT.get("fire_date").toString().substring(4,6)+"-"+userHT.get("fire_date").toString().substring(6,8)%></td>
    		<td><%=String.valueOf(userHT.get("org_id")).trim().equals("")?"&nbsp;":userHT.get("org_id").toString()%></td>
    		<td><%=String.valueOf(userHT.get("user_desc")).trim().equals("")?"&nbsp;":userHT.get("user_desc").toString()%></td>
    		<%
      			if(operVH.get("10202").equals("0")){
			%>
    				<td align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/userModify.jsp?userid=<%=userHT.get("user_id")%>&orgid=<%=Linfo.getOrgID()%>">修改</a></td>
    		<%
      			}else{
			%>
    		<td align="center">　</td>
    		<%
      			}
      			if (String.valueOf(userHT.get("user_state")).trim().equals("1") ) { 
      				if(operVH.get("10203").equals("0")){
			%>
    					<td align="center"><a href="javascript:ConfirmDel('/ToucsMonitor/usermanagerservlet?userid=<%=userHT.get("user_id")%>&oper_type=10203')">删除</a></td>
    		<%
      				}else{
			%>
    		<td align="center">　</td>
    		<%
      				}
      			}else {
    		%>
    		<td align="center"><a href="javascript:ConfirmUnlock('/ToucsMonitor/usermanagerservlet?userid=<%=userHT.get("user_id")%>&oper_type=10203&op_code=unlock')">解锁</a></td>
    		<%
      			}
      			if(operVH.get("10205").equals("0")){
			%>
    				<td align="center"><a href="/ToucsMonitor/ToucsMonitor/usermanager/givePower.jsp?userid=<%=userHT.get("user_id")%>">权限</a></td>
    		<% 
      			}else{
			%>
    		<td align="center">　</td>
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