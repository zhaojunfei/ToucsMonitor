<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.AgentManage.*" %>
<%!
private String getOperLink(String reqCode,String target,String merchant_id,String equip_id,String uid){
	String ret="/ToucsMonitor/actdevice?reqCode="+reqCode+"&uid="+uid
		+"&target="+target+"&merchant_id="+merchant_id+"&equip_id="+equip_id;
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
  <div align="left">�����豸�б� </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="10%" align="center" nowrap>���</td>
    		<td width="20%" align="center" nowrap>�̻���</td>
    		<td width="40%" align="center" nowrap>�豸��</td>
    		<td width="30%" align="center" nowrap>����</td>
  		</tr>
  		<%
  			Vector v=(Vector)request.getAttribute("ActInfoList");
			String merchant_id="";
      		String equip_id="";
  			if(v!=null){
	     		for(int i=0;i<v.size();i++){
					ActDeviceInfo info=(ActDeviceInfo)v.get(i);
					merchant_id=info.getMerchantid();
      				equip_id=info.getEquipid();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getEquipid()%>>
    		<td align="center" nowrap><%=i+1%></td>
    		<td align="center" nowrap><%=merchant_id%></td>
    		<td align="center" nowrap><%=equip_id%></td>
    		<td align="center">
        		<a href="<%=getOperLink("17703","page",merchant_id,equip_id,uid)%>">[�޸�] </a>
        		<a href="javascript:onDelete('<%=getOperLink("17702","",merchant_id,equip_id,uid)%>')">[ɾ��]</a>
        		<%if(info.getUseflag().equals("1")){%>
        		<a href="<%=getOperLink("17705","page",merchant_id,equip_id,uid)%>">[����]</a>
        		<%}%>
    		</td>
  		</tr>
  		<%}  }%>
	</table>
</body>
</html>