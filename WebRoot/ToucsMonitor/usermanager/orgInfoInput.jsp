<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.usermanager.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*"%>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<html>
<head>
	<title>������Ϣ</title>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script>
<!--
function ChkData(){
	var orgid = document.form1.orgid.value;
  	var orgname = document.form1.orgname.value;
  	var orgaddress = document.form1.orgaddress.value;
  	var opertype = document.form1.opertype.value;
  	var p_orgid = document.form1.p_orgid.value;
  	if(orgid == ""){
    	alert("������������룡");
    	document.form1.orgid.focus();
    	return false;
  	}
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
 
  	if(opertype == ""){
    	alert("��ѡ��ҵ�����࣡");
   	 	document.form1.opertype.focus();
    	return false;
  	}
  	if(p_orgid == ""){
    	alert("��ѡ���ϼ�������");
    	document.form1.p_orgid.focus();
    	return false;
  	}
  	return true;
}
//-->
</script>
<%
	userManagerBean UM = new userManagerBean();
	LoginInfo Linfo = (LoginInfo)request.getAttribute("LoginInfo");
	
	List AllOrgV = new Vector();
	Vector operTypeV = new Vector(); //ҵ������
	try{
  		AllOrgV = UM.getSubOrgWithLevel(Linfo.getOrgID());
  		operTypeV = CodeManageBean.queryCodes("0550");
	}catch(MonitorException ex){
  		ex.printStackTrace();
	}
%>
<body  bgcolor="#CCCCCC">
<h2><font face="����">��������</font></h2>
<hr align="left" noshade>
<form name="form1" method="post" action="/ToucsMonitor/orgManager" onsubmit="return ChkData();">
	<table width="558" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="106"><font size="2">�������룺</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="orgid" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">�������ƣ�</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="orgname" size="17">
        		<input type="hidden" name="txcode" value="10101"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">��ϵ�绰��</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="phoneno" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">IP��ַ��</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="ipaddress" size="17"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">������ַ��</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="orgaddress" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">��ϵ�ˣ�</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="connecter" size="17"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">ҵ�����ࣺ</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<select name="opertype" style="width:120">
          		<option value="" selected>��ѡ��ҵ������</option>
				<%
					if(operTypeV != null && operTypeV.size() > 0){
	  					for(int i = 0;i < operTypeV.size();i ++){
	    					CodeBean CB = (CodeBean)operTypeV.get(i);
	    					out.println("<option value=\""+CB.getCodeId()+"\">"+CB.getCodeDesc()+"</option>");
	  					}
					}
				%>
        		</select></font>
        	</td>
      		<td height="25" width="86"><font size="2">�ϼ�������</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<select name="p_orgid" style="width:100">
          		<option value="" selected>��ѡ���ϼ�����</option>
          		<%
  					if(AllOrgV != null && AllOrgV.size() >0){
    					for(int i = 0 ; i < AllOrgV.size(); i++){
	  						CodeBean code=(CodeBean)AllOrgV.get(i);
      						out.println("<option value=\""+code.getCodeId()+"\">"+code.getCodeDesc()+"</option>");
    					}
  					}else {
    					out.println("<option value=\"0\">��߼�����</option>");
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
          		<input type="submit" name="Submit" value="ȷ��"  class="inputbutton">
          		<input type="reset" name="Submit2" value="ȡ��"  class="inputbutton"  onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>