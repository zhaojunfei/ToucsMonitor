<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.ThirdPartyManage.*" %>

<%!
private String getOperLink(String reqCode,String target,String merchant_id,String uid){
	String ret="/ToucsMonitor/thirdPartyMerchant?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&merchant_id="+merchant_id;
	return ret;
}
%>

<html>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该设备吗？")){
		location.href=linkDel;
	}
}
</script>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<div>
	<div align="left">商户列表 </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="4%" align="center" nowrap>序号</td>
    		<td width="15%" align="center" nowrap>商户号</td>
   	 		<td width="40%" align="center" nowrap>商户名称</td>
    		<td width="11%" align="center" nowrap>所属机构</td>
    		<td width="30%" align="center" nowrap>管理</td>
  		</tr>
  		<%
  			Vector v=(Vector)request.getAttribute("ThirdPartyMerchantList");
			String merchant_id="";
      		String merchant_name="";
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
	        		ThirdPartyMerchantInfo info=(ThirdPartyMerchantInfo)v.get(i);
					merchant_id=info.getMerchantid();
        			merchant_name=info.getMerchantname();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getMerchantid()%>>
    		<td align="center" nowrap><%=i+1%></td>
    		<td align="center" nowrap><%=merchant_id%></td>
    		<td align="center" nowrap><%=merchant_name%></td>
    		<td align="center" nowrap><%=info.getBranchid()%></td>
    		<td align="center">
        		<a href="<%=getOperLink("13403","page",merchant_id,uid)%>">[修改] </a>
        		<a href="javascript:onDelete('<%=getOperLink("13402","",merchant_id,uid)%>')">[删除]</a>
        		<%if(info.getMerchantstat().equals("0")){%>
        		<a href="<%=getOperLink("13405","",merchant_id,uid)%>">[启用]</a>
        		<%}%>
        		<%if(info.getMerchantstat().equals("1")){%>
        		<a href="<%=getOperLink("13405","page",merchant_id,uid)%>">[停用]</a>
        	<%}%>
       		 	<a href="<%=getOperLink("13406","",merchant_id,uid)%>">[生成主密钥]</a>
		    </td>
  		</tr>
  		<%		}
  			}
  		%>
	</table>
</body>
</html>