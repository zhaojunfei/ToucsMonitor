<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*"%>
<html>
<head>
<title>�û��Ǽ�</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="js/power.js"></script>
<script src="/ToucsMonitor/ToucsMonitor/usermanager/js/power.js"></script>
<Script>
<!--
function CheckData(){
  	var userid = document.form1.userid.value;
  	var userpwd = document.form1.userpwd.value;
  	var username = document.form1.username.value;
  	var confirmpwd = document.form1.confirm.value;
  	var employdate = document.form1.employdate.value;
  	var firedate = document.form1.firedate.value;
  	var orgid = document.form1.orgid.value;
  	var userdesc = document.form1.userdesc.value;
  	if(userid == ""){
    	alert("�������û��ʺţ�");
    	document.form1.userid.focus();
    	return false;
  	}
  	if( userpwd =="" ){
    	alert("���������룡");
   	 	document.form1.userpwd.focus();
    	return false;
  	}
  	if( username == "" ){
    	alert("�������û����ƣ�");
    	document.form1.username.focus();
    	return false;
  	}
  	if( orgid == "NO" ){
    	alert("��ѡ�������");
    	document.form1.orgid.focus();
    	return false;
  	}
  	if( userpwd != confirmpwd ){
    	alert("����ȷ�ϴ���");
    	document.form1.confirm.focus();
    	return false;
  	}
  	if(!verifyDate(document.form1.employdate) || !verifyDate(document.form1.firedate)){
    	return false;
  	}
  	return true;
}
-->
</Script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<jsp:useBean id="UM" scope="page" class="com.adtec.toucs.monitor.usermanager.userManagerBean" />
<jsp:setProperty name="UM" property="*" />
<body  bgcolor="#CCCCCC" text="#000000">
<%
	Vector OV = new Vector();
	LoginInfo Linfo = (LoginInfo)request.getAttribute("LInfo");	
	Hashtable OH = new Hashtable();
	try{
  		OV = UM.getSubOrg(Linfo.getOrgID());
	}catch(MonitorException ex){
  		out.println("<script>alert(\"ϵͳ�쳣,���룺"+ex.getErrCode()+"\");</script>");
  		return;
	}
%>
<h2><font face="����">�û��Ǽ�</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/usermanagerservlet" onsubmit='return CheckData()'>
	<table width="700" border="0" cellspacing="0" cellpadding="0" height="143">
    	<tr> 
      		<td colspan="4"> 
        		<div align="center">
          		<hr align="left" noshade>
        		</div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="136" height="30" align="right"><font size="2">�û��ʺţ�</font></td>
      		<td width="195" height="30"> 
        		<input type="hidden" name="oper_type" value="10201">
        		<input type="text" name="userid"  size="12" maxlength="20">
      		</td>
      		<td width="117" height="30" align="right"><font size="2">�û�������</font></td>
      		<td width="206" height="30"> 
        		<input type="text" name="username" class="wideinput" size="12" maxlength="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="136" height="30" align="right"><font size="2">�û����룺</font></td>
      		<td width="195" height="30"> 
        		<input type="password" name="userpwd" class="wideinput" size="12" >
      		</td>
      		<td width="117" height="30" align="right"><font size="2">ȷ�����룺</font></td>
     	 	<td width="206" height="30"> 
        		<input type="password" name="confirm" class="wideinput" size="12" >
      		</td>
    	</tr>
    	<tr> 
      		<td width="136" height="30" align="right"><font size="2">����ʱ�䣺</font></td>
      		<td width="195" height="30"> 
        		<input type="text" name="employdate" style="width:100" size="10" maxlength="10">
        		<font size="2" color="#0000FF">(yyyy-mm-dd)</font> </td>
      		<td width="117" height="30" align="right"><font size="2">���ʱ�䣺</font></td>
      		<td width="206" height="30"> 
        		<input type="text" name="firedate" style="width:100" size="10" maxlength="10">
        		<font size="2" color="#0000FF">(yyyy-mm-dd)</font></td>
    	</tr>
    	<tr> 
      		<td width="136" height="30" align="right"><font size="2">�������룺</font></td>
      		<td width="195" height="30"> 
        		<select name="orgid">
          		<option value="NO" selected>��ѡ�����</option>
          		<%
  					if(OV != null){
    					for(int i=0;i<OV.size();i++){
      						OH = new Hashtable();
      						OH = (Hashtable)OV.get(i);
      						out.println("<option value=\""+OH.get("org_id")+"\">"+OH.get("org_name")+"</option>");
    					}
  					}
				%>
        		</select>
      		</td>
      		<td width="117" height="30" align="right"><font size="2">��Ч�ڣ�</font></td>
      		<td width="206" height="30">
      			<select name="usedate">
      			<option value="30">30</option>
      			<option value="60">60</option>
      			<option value="90">90</option>		
      			</select> 
      		</td>
    	</tr>
    	<tr> 
      		<td width="136" height="69" align="right"> 
				<font size="2">�û�������</font>
      		</td>
      		<td colspan="3" height="69"> 
        		<textarea name="userdesc" cols="25" rows="3"></textarea>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="4"> 
        		<div align="center">
          		<hr align="left" noshade>
        		</div>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4"> 
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