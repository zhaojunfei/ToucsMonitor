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
	if(window.confirm("ȷʵҪɾ�����豸��")){
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
	<div align="left">�̻��б� </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="4%" align="center" nowrap>���</td>
    		<td width="15%" align="center" nowrap>�̻���</td>
   	 		<td width="40%" align="center" nowrap>�̻�����</td>
    		<td width="11%" align="center" nowrap>��������</td>
    		<td width="30%" align="center" nowrap>����</td>
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
        		<a href="<%=getOperLink("13403","page",merchant_id,uid)%>">[�޸�] </a>
        		<a href="javascript:onDelete('<%=getOperLink("13402","",merchant_id,uid)%>')">[ɾ��]</a>
        		<%if(info.getMerchantstat().equals("0")){%>
        		<a href="<%=getOperLink("13405","",merchant_id,uid)%>">[����]</a>
        		<%}%>
        		<%if(info.getMerchantstat().equals("1")){%>
        		<a href="<%=getOperLink("13405","page",merchant_id,uid)%>">[ͣ��]</a>
        	<%}%>
       		 	<a href="<%=getOperLink("13406","",merchant_id,uid)%>">[��������Կ]</a>
		    </td>
  		</tr>
  		<%		}
  			}
  		%>
	</table>
</body>
</html>