<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%!
	private String getOperLink(String reqCode,String target,String account_id,String uid){
		    String ret="/ToucsMonitor/posInAccount?reqCode="+reqCode+"&uid="+uid+"&target="+target+"&name="+account_id;
		    return ret;
	}
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String uid=request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	  <title>���pos�ڲ��˻�����</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
  </head>
  
<script language="javascript">
	function onDelete(linkDel){
		if(window.confirm("ȷʵҪɾ�����˻��� ")){
			location.href=linkDel;
		}
	}
</script>
<body>
	<div>
  		<div align="left">���pos�ڲ��˻��б�</div>
	</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="4%" align="center" nowrap>���</td>
    		<td width="30%" align="center" nowrap>�������ڲ��˻�</td>
    		<td width="30%" align="center" nowrap>�����ڲ��˻�</td>
    		<td width="36%" align="center" nowrap>����</td>
  		</tr>
  		<%
  			Vector v=(Vector)request.getAttribute("POSInAccountList");
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
				POSInAccountInfo info=(POSInAccountInfo)v.get(i);
   		%>
  		<tr bgcolor="#FFFFFF" >
    		<td align="center" nowrap><%=i+1%></td>
   		 	<td align="center" nowrap id="para_val"><%=info.getParaVal() %></td>
    		<td align="center" nowrap id="pre_val"><%=info.getPreVal() %></td>
    		<td align="center">
        		<a href="<%=getOperLink("14302","page",info.getParaName(),uid)%>">[�޸�] </a>
        		<a href="javascript:onDelete('<%=getOperLink("14304","",info.getParaName(),uid)%>')">[ɾ��]</a>  
    		</td>
  		</tr>
  				<%}
  			}%>
	</table>
</body>
</html>
