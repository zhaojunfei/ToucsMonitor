<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*"%>
<%@ page import="com.adtec.toucs.monitor.usermanager.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<html>
<head>
<title>������Ϣ</title>
<script>
<!--
function ChkData(){
	var orgname = document.form1.orgname.value;
  	var orgaddress = document.form1.orgaddress.value;
  	var opertype = document.form1.opertype.value;
  	var p_orgid = document.form1.p_orgid.value;
  	if(orgname == ""){
    	alert("������������ƣ�");
    	document.form1.orgname.focus();
    	return false;
  	}
  	if(orgaddress == ""){
    	alert("�����������ַ��");
    	document.form1.orgaddress.focus();
    	return false;
  	}
  	if(opertype == "NO"){
    	alert("��ѡ��ҵ�����࣡");
    	document.form1.opertype.focus();
    	return false;
  	}
  	if(p_orgid == "NO"){
    	alert("��ѡ���ϼ�������");
    	document.form1.p_orgid.focus();
    	return false;
  	}
  	return true;
}
//-->
</script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<%
  	String orgId = request.getParameter("orgid");
  	userManagerBean UM = new userManagerBean();
  	Vector orgV = new Vector();
  	Hashtable orgH = new Hashtable();
  	Vector AllOrgV = new Vector();
  	Hashtable AllOrgH = new Hashtable();

  	Vector operTypeV = new Vector(); //ҵ������
  	try {
    	orgV = UM.queryOrgInfo(orgId);
    	if (orgV == null || orgV.size()<= 0) {
      		throw new MonitorException(123,"û��ȡ���κ���Ϣ��");
    	}
    	orgH = (Hashtable)orgV.get(0);
    	if (orgH == null || orgH.isEmpty()){
      		throw new MonitorException(123,"û��ȡ���κ���Ϣ��");
    	}
		
    	AllOrgV = UM.queryOrgInfo("*");
    	operTypeV = CodeManageBean.queryCodes("0550");
  	}catch (MonitorException ex) {
    	ex.printStackTrace();
  }

%>
<h2><font face="����">�޸Ļ�����Ϣ</font></h2>
<hr align="left" noshade>
<form name="form1" method="post" action="/ToucsMonitor/orgManager" onsubmit="return ChkData();" >
	<table width="623" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="107"><font size="2">�������룺</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="orgid" size="10" value="<%=orgH.get("org_id")%>" readonly style="BACKGROUND-COLOR: #cccccc">
        		</font></td>
      		<td height="25" width="99"><font size="2">�������ƣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="orgname" size="10" value="<%=orgH.get("org_name")%>">
        		<input type="hidden" name="txcode" value="10102">
        		<input type="hidden" name="p_orgid" value="<%=String.valueOf(orgH.get("p_org_id")).trim()%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">��ϵ�绰��</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="phoneno" size="10" value="<%=orgH.get("phoneno")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">IP��ַ��</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="ipaddress" size="10" value="<%=orgH.get("ip_address")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">������ַ��</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="orgaddress" size="10" value="<%=orgH.get("org_address")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">��ϵ�ˣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="connecter" size="10" value="<%=orgH.get("contractor")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">ҵ�����ࣺ</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<select name="opertype" style="width:100">
				<%
					if(operTypeV != null && operTypeV.size() > 0){
	  					for(int i = 0;i < operTypeV.size();i ++){
	    					CodeBean CB = (CodeBean)operTypeV.get(i);
	    					if(orgH.get("oper_type").equals(CB.getCodeId()))
	      						out.println("<option value=\""+CB.getCodeId()+"\" selected>"+CB.getCodeDesc()+"</option>");
	    					else
	      						out.println("<option value=\""+CB.getCodeId()+"\">"+CB.getCodeDesc()+"</option>");
	  					}
					}
				%>
        		</select></font>
        	</td>
      		<td height="25" width="99"><font size="2">�ϼ�������</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<select name="select" style="width:100" disabled>
          		<%
  					if(AllOrgV != null){
    					for(int i = 0 ; i < AllOrgV.size(); i++){
      						AllOrgH = (Hashtable)AllOrgV.get(i);
      						String Context = "";
      						if (AllOrgH.get("org_id").equals(String.valueOf(orgH.get("p_org_id")).trim())) 
        						Context = " selected";
      							out.println("<option value=\""+AllOrgH.get("org_id")+"\""+Context+">"+AllOrgH.get("org_name")+"</option>");
    					}
  					}
				%>
        		</select></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="4"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="4"> 
        		<div align="right"> 
          		<input type="submit" name="Submit" value="ȷ��" class="inputbutton">
          		<input type="reset" name="Submit2" value="ȡ��" class="inputbutton" onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>