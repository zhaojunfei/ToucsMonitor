<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>

<%!
private String getModLink(BankCodeBean code,String uid){
		String ret="/ToucsMonitor/bankcodeman?reqCode=17202&bankCode="+code.getBankCode()+"&uid="+uid;
		return ret;
}

private String getDelLink(BankCodeBean code,String uid){
		String ret="/ToucsMonitor/bankcodeman?reqCode=17203&bankCode="+code.getBankCode()+"&uid="+uid;
		return ret;
}
%>

<html>
<head>
	<title>银行中心代码管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="JavaScript">
function doAdd(addLink){
	location.href=addLink;
}

function  doDel(delLink){
	if(confirm("确实要删除吗？"))
		location.href=delLink;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String modPerm=(String)request.getAttribute("MODPERM");
	String delPerm=(String)request.getAttribute("DELPERM");
%>
<h2><font face="隶书">银行中心代码管理</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/bankcodeman?reqCode=17204&uid=<%=uid%>">
	<table width="75%">
    	<tr>
      		<td colspan="2">
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2">
        		<table width="100%" bgcolor="#000000" cellspacing="1" >
          			<tr bgcolor="#666666">
            			<td width="30%" align="center">银行中心代码</td>
            			<td width="50%" align="center">银行中心名称</td>
            			<td width="20%" align="center">操作</td>
          			</tr>
		  			<%
		  				List list=(List)request.getAttribute("list");
        				if(list!=null){
          					for(int i=0;i<list.size();i++){
	      						BankCodeBean code=(BankCodeBean)list.get(i);
		  			%>
          			<tr bgcolor="#FFFFFF">
            			<td width="30%"  >
              				<div align="center"><%=code.getBankCode()%></div>
            			</td>
            			<td width="50%">
              				<div align="center"><%=code.getBankName()%></div>
            			</td>
            			<td width="20%">
              				<div align="center">
		 						<%if(modPerm!=null&&modPerm.equals("1")){%>                           	 
								<a href="<%=getModLink(code,uid)%>">[修改]</a>                        	 
		 						<%}%>                                                                 	 
		 						<%if(delPerm!=null&&delPerm.equals("1")){%>                           	 
		 						<a href="javascript:doDel('<%=getDelLink(code,uid)%>')">[删除]</a>    	 
		 						<%}%>                                                                 	 
							</div>
            			</td>
          			</tr>
          			<%
		  				}
					}
		  			%>
        		</table>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2">
        		<hr align="left" width="100%" noshade>
     		 </td>
    	</tr>
    	<tr>
      		<td colspan="2">
        		<input type="button" name="add" value="添加"  onClick="javascript:doAdd('/ToucsMonitor/bankcodeman?reqCode=17201&uid=<%=uid%>')" class="inputbutton">
     		</td>
    	</tr>
</table>
</form>
</body>
</html>