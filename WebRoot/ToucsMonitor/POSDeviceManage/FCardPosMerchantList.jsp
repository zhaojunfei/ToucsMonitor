<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%!
private String getOperLink(String reqCode,String target,String merchantid,String uid){
	String ret="/ToucsMonitor/fcardmerchantconfig?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&merchant_id="+merchantid;
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
	if(window.confirm("确实要删除该商户吗？")){
		location.href=linkDel;
	}
}
</script>
<body   bgcolor="#CCCCCC" text="#000000">
<%
	System.out.println("FCardPosMerchantList.jsp begin");
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSMERCHANTADD");
	 
	permFlag=(String)request.getAttribute("POSMERCHANTUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTDELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTQUERY");
	 
	permFlag=(String)request.getAttribute("POSMERCHANTCLEAR");
	 
	canModify=true;
	canDel=true;
	 
%>
<div>
  	<div align="left">设备列表 </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="3%" >
      			<div align="center">序号</div>
    		</td>
    		<td width="15%">
      			<div align="center">商户编号</div>
    		</td>
    		<td width="37%">
      			<div align="center">商户名称</div>
    		</td>
    		<td width="26%">
      			<div align="center">所属机构</div>
    		</td>
    		<td width="19%">
      			<div align="center">管理</div>
    		</td>
  		</tr>
  		<%
  			List l=(List)request.getAttribute("posFCardMerchantList");
			String merchantid="";
  			if(l!=null){
	        	for(int i=0;i<l.size();i++){
					POSFCardMerchantInfo info=(POSFCardMerchantInfo)l.get(i);
					merchantid=info.getMer_id();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getMer_id()%>>
    		<td >
      			<div align="center"><%=i+1%> </div>
    		</td>
    		<td width="15%" >
      			<div align="center"><%=merchantid%> </div>
    		</td>
    		<td width="37%">
      			<div align="center"><%=info.getMer_name()%></div>
    		</td>
    		<td width="26%">
      			<div align="center"><%=CodeManageBean.getCodeDesc("orgname",info.getDev_bankno())%></div>
    		</td>
    		<td width="19%">
      			<div align="center">
        		<%
        			if(canModify){
        		%>
        				<a href="<%=getOperLink("10502","queryPage",merchantid,uid)%>">[修改] </a>
        		<%
        			}
        		%>
        		<%	
        			if(canDel){
        		%>
        				<a href="javascript:onDelete('<%=getOperLink("10503","",merchantid,uid)%>')">[删除]</a>
        		<%
        			}
        		%>
        				<a href="<%=getOperLink("10504","",merchantid,uid)%>">[查询]</a>
      			</div>
    		</td>
  		</tr>
  		<%
  				}
  			}
  		%>
	</table>
</body>
</html>