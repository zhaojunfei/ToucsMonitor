<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="TxnAuthMngBean" class="com.adtec.toucs.monitor.systemmanage.TxnAuthMngBean"  scope="request"/>

<%! 
private String getModLink(TxnAuthBean auth, String uid){
	String ret="/ToucsMonitor/txnauthman?reqCode=17402&deviceType=" + auth.getSysId()
		+"&CardClass="+auth.getCardClass()+"&CardType="+auth.getCardType()+"&uid="+uid;
	return ret;	
}
	
private String getDelLink(TxnAuthBean auth,String uid){
	String ret="/ToucsMonitor/txnauthman?reqCode=17403&SysId="+auth.getSysId()
		+"&CardClass="+auth.getCardClass()+"&CardType="+auth.getCardType()+"&uid="+uid;
	return ret;
}
%>

<html>
<head>
	<title>交易权限查询</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="JavaScript">
function doAdd(addLink){
	location.href = addLink + "&SysId=atm";
}
function doDel(delLink){
	if (confirm("确实要删除权限信息吗？"))
		location.href = delLink;	
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	 String uid=request.getParameter("uid");
	 String queryPerm=(String)request.getAttribute("QUERYPERM");
	 String addPerm=(String)request.getAttribute("ADDPERM");
	 String modPerm=(String)request.getAttribute("MODPERM");
	 String delPerm=(String)request.getAttribute("DELPERM");
	 String type=(String)request.getAttribute("SysId");	 
%>
<h2><font face="隶书">ATM交易权限管理</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/txnauthman?reqCode=17404&uid=<%=uid%>">
	<table width="75%">
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>  
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2">        
        		<table width="100%" bgcolor="#000000" cellspacing="1" >
          			<tr bgcolor="#666666"> 
            			<td width="37%"><div align="center">卡种类</div></td>
            			<td width="43%"><div align="center">卡类型</div></td>
            			<td width="20%"><div align="center">操作</div></td>
          			</tr>
		  			<%
		  				List list=(List)request.getAttribute("list");
        				if(list!=null){
          					for(int i=0;i<list.size();i++){
	      						TxnAuthBean auth=(TxnAuthBean)list.get(i);
		  			%>			
          			<tr bgcolor="#FFFFFF"> 
            			<td width="43%"> 
              				<div align="center"><%=toucsString.unConvert(auth.getCardTypeName())%></div>
            			</td>
            			<td width="37%"  > 
              				<div align="center"><%=toucsString.unConvert(auth.getCardClassName())%></div>
            			</td>
            			<td width="20%"> 
              				<div align="center">
			  				<%if(modPerm!=null&&modPerm.equals("1")){%>
			 		 		<a href="<%=getModLink(auth,uid)%>">[修改]</a>
			  				<%}%>
			  				<%if(delPerm!=null&&delPerm.equals("1")){%>
			  				<a href="javascript:doDel('<%=getDelLink(auth,uid)%>')">[删除]</a>
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
        		<%if(type!=null&&!type.equals("")){%>
        		<input type="button" name="add" value="添加"  onClick="javascript:doAdd('/ToucsMonitor/txnauthman?reqCode=17401&uid=<%=uid%>')" class="inputbutton" 
				<%=(addPerm!=null&&addPerm.equals("1"))?"":"DISABLED"%>>
        		<%}%>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>