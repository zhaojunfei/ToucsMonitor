<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>



<%!
	private String getOperLink(String reqCode,String target,String merchantid,String uid){
		String ret="/ToucsMonitor/posmerchantconfig?reqCode="+reqCode+"&uid="+uid
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
	System.out.println("PosMerchantList.jsp begin");
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSMERCHANTADD");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTDELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("POSMERCHANTCLEAR");
	boolean canClear=(permFlag!=null&&permFlag.equals("1"));
%>
<div>
  <div align="left">设备列表 </div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td width="6%">
      <div align="center">序号</div>
    </td>
    <td width="14%">
      <div align="center">商户编号</div>
    </td>
    <td width="37%">
      <div align="center">商户名称</div>
    </td>
    <td width="20%">
      <div align="center">所属机构</div>
    </td>
    <td width="23%">
      <div align="center">管理</div>
    </td>
  </tr>
  <%
  			List l=(List)request.getAttribute("posMerchantList");
			String merchantid="";
  			if(l!=null){
	        	for(int i=0;i<l.size();i++){
				POSMerchantInfo info=(POSMerchantInfo)l.get(i);
				merchantid=info.getMerchantid();
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getMerchantid()%>>
    <td width="6%" >
      <div align="center"><%=i+1%> </div>
    </td>
    <td width="14%" >
      <div align="center"><%=merchantid%> </div>
    </td>
    <td width="37%">
      <div align="center"><%=info.getMctname()%></div>
    </td>
    <td width="20%">
      <div align="center"><%=CodeManageBean.getCodeDesc("orgname",info.getOrgid())%></div>
    </td>
    <td width="23%">
      <div align="center">
        <%if(canModify){%>
        <a href="<%=getOperLink("10402","queryPage",merchantid,uid)%>">[修改] </a>
        <%}%>
        <%if(canDel){%>
        <a href="javascript:onDelete('<%=getOperLink("10403","",merchantid,uid)%>')">[删除]</a>
        <%}%>
        <%if(canModify){%>

        <% if( info.getClearflag().equals("1")){ %>
            <a href="<%=getOperLink("10402","clear",merchantid,uid)%>">[清理]</a>
        <%}else{%>
           <a href="<%=getOperLink("10402","normal",merchantid,uid)%>"><font style="color:#FF0000" >[正常]</font></a>
        <%}%>

        <%}%>
        <a href="<%=getOperLink("10404","",merchantid,uid)%>">[查询]</a>
      </div>
    </td>
  </tr>
  <%}
  			}%>
</table>
</body>
</html>