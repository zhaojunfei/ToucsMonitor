<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.usermanager.*"%>
<%@ page import="com.adtec.toucs.monitor.common.*"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>��ɫ��Ϣ</title>
<script>
function ChkData(){
  	var rolename = document.form1.rolename.value;
  	if(rolename == ""){
    	alert("�������ɫ���ƣ�");
    	document.form1.rolename.focus();
    	return false;
  	}
  	return true;
}
</script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<%
  	userManagerBean UM = new userManagerBean();
 	String roleId = request.getParameter("roleid");
  	if ( roleId == null ) {
    	out.println("<script>alert(\"ȡϵͳ����ʧ�ܣ�\");</script>");
    	return;
  	}
  	Vector roleV = new Vector();
  	Hashtable roleH = new Hashtable();
  	try {
    	roleV = UM.queryRoleInfo(roleId,"*");
    	if ( roleV == null || roleV.size() == 0) {
      		out.println("<script>alert(\"û��ȡ���û���Ϣ��\");</script>");
      		return;
    	}
    	roleH = (Hashtable)roleV.get(0);
    	if (roleH == null || roleH.size() == 0) {
      		out.println("<script>alert(\"û��ȡ���û���Ϣ\");</script>");
      		return;
    	}
  	}catch (MonitorException ex){
    		out.println("<script>alert(\"ϵͳ�쳣�����룺"+ex.getErrCode()+"\");</script>");
    		return;
  	}
%>
<h2><font face="����">��ɫ����</font> </h2>
<hr align="left" noshade>
<form name="form1" method="post" action="/ToucsMonitor/roleManager" onsubmit="return ChkData();">
	<table width="590" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="143"><font size="2">��ɫ���ƣ�</font></td>
      		<td height="25" width="413"> <font size="2"> 
        		<input type="hidden" name="roleid" size="12" value="<%=roleId%>">
        		<input type="text" name="rolename" size="12" value="<%=roleH.get("role_name")%>">
        		<input type="hidden" name="txcode" value="10302"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="143"><font size="2">��ɫ������</font></td>
      		<td height="25" width="413"><font size="2"> 
        		<textarea name="roledesc" cols="30" rows="3"><%=roleH.get("role_desc")%></textarea></font>
        	</td>
    	</tr>
    	<tr valign="bottom"> 
      		<td height="35" colspan="2"> 
        		<hr align="left" noshade>
        		<font size="2"> </font> 
        	</td>
    	</tr>
    	<tr valign="bottom">
      		<td height="35" colspan="2">
        		<div align="right"><font size="2"> 
          		<input type="submit" name="Submit" value="ȷ��" class="inputbutton">
          		<input type="reset" name="Submit2" value="ȡ��" class="inputbutton" onclick="history.go(-1)";></font>
          		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>