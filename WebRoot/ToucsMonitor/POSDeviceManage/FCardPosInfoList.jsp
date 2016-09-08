<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<%!
private String getOperLink(String reqCode, String target, String mer_id, String pos_code, String uid){
	String ret="/ToucsMonitor/fcardposconfig?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&mer_id="+mer_id+"&pos_no="+pos_code;
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
<body  bgcolor="#CCCCCC" text="#000000">
<%
	System.out.println("PosMerchantList.jsp begin");
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSINFOREG");
	
	permFlag=(String)request.getAttribute("POSINFOUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFODELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOQUERY");
	
%>
<div>
	<div align="left">设备列表 </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td align="center">序号</td>
    		<td width="47%">
      			<div align="center">商户编号</div>
    		</td>
    		<td width="22%">
      			<div align="center">POS编号</div>
    		</td>
    		<td width="27%">
      			<div align="center">管理</div>
    		</td>
  		</tr>
  		<%
  			List l = (List)request.getAttribute("posInfoList");
			String mer_id, pos_no;
  			if (l != null) {
	        	for (int i=0; i<l.size(); i++) {
					POSFCardInfo info=(POSFCardInfo)l.get(i);
					mer_id = info.getMer_id();
					pos_no = info.getPos_no();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=pos_no%>>
    		<td width="4%" >
      			<div align="center"><%=i+1%> </div>
    		</td>
    		<td width="47%" >
      			<div align="center"><%=info.getMer_id()%> </div>
    		</td>
    		<td width="22%">
      			<div align="center"><%=pos_no%></div>
    		</td>
    		<td width="27%">
      			<div align="center">
        		<%
        			if(canModify){
        		%>
        				<a href="<%=getOperLink("13502","queryPage", mer_id, pos_no, uid)%>">[修改] </a>
        		<%
        			}
        		%>
        		<%
        			if(canDel){
        		%>
        				<a href="javascript:onDelete('<%=getOperLink("13504", "", mer_id, pos_no, uid)%>')">[删除]</a>
        		<%
        			}
        		%>
						<a href="<%=getOperLink("13503", "", mer_id, pos_no, uid)%>">[查询]</a>
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