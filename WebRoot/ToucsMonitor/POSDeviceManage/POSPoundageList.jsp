<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%!
private String getOperLink(String reqCode,String target,String uid){
	String ret="/ToucsMonitor/posPoundage?reqCode="+reqCode+"&uid="+uid+"&target="+target;
	return ret;
}
%>

<%
	String uid=request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head> 
	<title>My JSP 'POSPoundageList.jsp' starting page</title>
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css"> 
</head>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("ȷʵҪɾ������������Ϣ �� ")){
		location.href=linkDel;
	}
}
</script>
<body>
<div>
	<div align="left">���pos�������б�</div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
	  	<tr bgcolor="#666666">
	    	<td width="4%"  align="center" nowrap>���</td>
	    	<td width="30%" align="center" nowrap>�����ѱ���</td>
	    	<td width="30%" align="center" nowrap>����������</td>
	    	<td width="36%" align="center" nowrap>����</td>
	  	</tr>
	  	<%
  			Vector v=(Vector)request.getAttribute("POSPoundageList");
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
					POSPoundageInfo info=(POSPoundageInfo)v.get(i);
	   	%>
	  	<tr bgcolor="#FFFFFF" >
	    	<td align="center" nowrap><%=i+1%></td>
	    	<td align="center" nowrap id="para_name"><%=info.getParaVal() %>%</td>
	    	<td align="center" nowrap id="pre_val"><%=info.getPreVal() %></td>
	    	<td align="center">
	        	<a href="<%=getOperLink("14602","page",uid)%>">[�޸�] </a>
	        	<a href="javascript:onDelete('<%=getOperLink("14604","",uid)%>')">[ɾ��]</a>   
	    	</td>
	  	</tr>
	  	<%		
	  			}
	  		}
	  	%>
	</table>
</body>
</html>