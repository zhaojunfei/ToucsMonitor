<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<%!
	private String getOperLink(String reqCode,String target,String pos_code,String uid){
		String ret="/ToucsMonitor/posMoney?reqCode="+reqCode+"&uid="+uid
			+"&target="+target+"&pos_code="+pos_code;
		return ret;
	}
%>

<%
	String uid=request.getParameter("uid");
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

function btn1(){
	var its = document.getElementsByName("box1");
	for(var i=0;i<its.length;i++){
		if(its[i].type="checkbox"){
			its[i].checked = true ;
		}
	}
}

function btn2(){
	var its = document.getElementsByName("box1");
	for(var i=0;i<its.length;i++){
		if(its[i].type="checkbox"){
			its[i].checked = false ;
		}
	}
}

</script>
<body  bgcolor="#CCCCCC" text="#000000" >
<div>
	<h2><font face="隶书">理财pos设备信息列表 </font></h2>
</div>

<hr>
<br/>
<form method="post" action="/ToucsMonitor/posMoney?reqCode=14204&target=page&uid="+<%=uid %> >
	<table border="0" width="98%" height="5%">
		<tr valign="middle">
			<td width="64%" align="left">
				<input type="button" name="bt1"  value="全选" class="inputbutton"  onClick="btn1()">
				<input type="button" name="bt2"  value="取消" class="inputbutton"  onClick="btn2()">
				<%  
					String  nUrl="/ToucsMonitor/posMoney?reqCode=14204&target=page&uid="+uid;
		    	%>
				<input type="submit" name="del"  value="删除" class="inputbutton"  >
				<%
					String url2="/ToucsMonitor/posMoney?reqCode=14201&target=page&uid="+uid;
				%>
				<input type="button" name="insert" value="添加" class="inputbutton" onClick=javascript:location.href='<%=url2%>'>
				<%
					String url3="/ToucsMonitor/posMoney";
				%>
				<input type="button" name="insert" value="文件上传下载" class="inputbutton" onClick=javascript:location.href='<%=url3%>'>		
			</td>
		</tr>
	</table>
	<hr >
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
  			<td width="2"   align="center" nowrap></td>
    		<td width="4%"  align="center" nowrap>序号</td>
    		<td width="10%" align="center" nowrap>POS顺序号</td>
    		<td width="13%" align="center" nowrap>19位POS编号</td>
    		<td width="18%" align="center" nowrap>商户号</td>
    		<td width="15%" align="center" nowrap>机构</td>
    		<td width="18%" align="center" nowrap>柜员号</td>
    		<td width="20%" align="center" nowrap>管理</td>
  		</tr>
  		<%
  			List list = (List)request.getAttribute("posMoneyList");
			String pos_code="";
      		String pos_dcc_code="";
  			if(list!=null){
	        	for(int i=0;i<list.size();i++){
					POSMoneyInfo info=(POSMoneyInfo)list.get(i);
					pos_code=info.getPos_code();
        			pos_dcc_code=info.getPos_dcc_code();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getPos_code()%>>
  			<td align="center" nowrap><input type="checkbox" name="box1" value="<%=pos_code%>"></td>
    		<td align="center" nowrap><%=i+1%></td>
    		<td align="center" nowrap><%=pos_code%></td>
    		<td align="center" nowrap><%=pos_dcc_code%></td>
    		<td align="center" nowrap><%=info.getMerchant_id()%></td>
    		<td align="center" nowrap><%=info.getBranch_id()%></td>
    		<td align="center" nowrap><%=info.getTeller_id()%></td>
    		<td align="center">
        		<a href="<%=getOperLink("14202","page",pos_dcc_code,uid)%>">[修改] </a>
        		<a href="javascript:onDelete('<%=getOperLink("14204","",pos_code,uid)%>')">[删除]</a>
        		<%
        			if(info.getUse_flag().equals("0")){
        		%>
        		<a href="<%=getOperLink("14205","",pos_code,uid)%>">[启用]</a>
        		<%}%>
        		<%if(info.getUse_flag().equals("1")){%>
        		<a href="<%=getOperLink("14205","page",pos_code,uid)%>">[停用]</a>
        		<%}%>
    		</td>
  		</tr>
  		<%		}
  			}%>
	</table>
</form>
</body>
</html>