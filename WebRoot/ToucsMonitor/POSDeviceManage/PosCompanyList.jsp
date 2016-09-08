<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<%!
	private String getOperLink(String reqCode,String target,String company_id,String uid){
		    String ret="/ToucsMonitor/poscompany?reqCode="+reqCode+"&uid="+uid+"&target="+target+"&company_id="+company_id;
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
<title>��ҵIC����˾��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<div>
  <div align="left">��ҵIC����˾�б�</div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td width="4%" align="center" nowrap>���</td>
    <td width="15%" align="center" nowrap>��˾���</td>
    <td width="40%" align="center" nowrap>��˾����</td>
    <td width="11%" align="center" nowrap>��������</td>
    <td width="30%" align="center" nowrap>����</td>
  </tr>
  <%
  			Vector v=(Vector)request.getAttribute("PosCompanyList");
			String company_id="";
            String company_name="";
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
				PosCompanyInfo info=(PosCompanyInfo)v.get(i);
				company_id=info.getCompany_id();
                company_name=info.getCompany_name();
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getCompany_id()%>>
    <td align="center" nowrap><%=i+1%></td>
    <td align="center" nowrap><%=company_id%></td>
    <td align="center" nowrap><%=company_name%></td>
    <td align="center" nowrap><%=info.getBranch_id()%></td>
    <td align="center">
        <a href="<%=getOperLink("16503","page",company_id,uid)%>">[�޸�] </a>
        <a href="javascript:onDelete('<%=getOperLink("16502","",company_id,uid)%>')">[ɾ��]</a>
        <%if(info.getCompany_stat().equals("0")){%>
        <a href="<%=getOperLink("16505","",company_id,uid)%>">[����]</a>
        <%}%>
        <%if(info.getCompany_stat().equals("1")){%>
        <a href="<%=getOperLink("16505","page",company_id,uid)%>">[ͣ��]</a>
        <%}%>
        <%if(info.getSecu_kind().equals("0")){%>
        <a href="<%=getOperLink("16506","",company_id,uid)%>">[��������Կ]</a>
        <a href="<%=getOperLink("16506","page",company_id,uid)%>">[ǩ��]</a>
        <%} else {%>
        <a href="<%=getOperLink("16506","",company_id,uid)%>">[��������Կ����ӡ]</a>
        <%}%>
    </td>
  </tr>
  <%}
  			}%>
</table>
</body>
</html>