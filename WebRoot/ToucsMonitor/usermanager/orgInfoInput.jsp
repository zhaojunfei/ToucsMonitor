<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.usermanager.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*"%>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<html>
<head>
	<title>机构信息</title>
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
    	alert("请输入机构编码！");
    	document.form1.orgid.focus();
    	return false;
  	}
  	if(orgname == ""){
    	alert("请输入机构名称！");
    	document.form1.orgname.focus();
    	return false;
  	}
  	if(orgaddress == ""){
    	alert("请输入机构地址！");
    	document.form1.orgaddress.focus();
    	return false;
  	}
 
  	if(opertype == ""){
    	alert("请选择业务种类！");
   	 	document.form1.opertype.focus();
    	return false;
  	}
  	if(p_orgid == ""){
    	alert("请选择上级机构！");
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
	Vector operTypeV = new Vector(); //业务种类
	try{
  		AllOrgV = UM.getSubOrgWithLevel(Linfo.getOrgID());
  		operTypeV = CodeManageBean.queryCodes("0550");
	}catch(MonitorException ex){
  		ex.printStackTrace();
	}
%>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">机构管理</font></h2>
<hr align="left" noshade>
<form name="form1" method="post" action="/ToucsMonitor/orgManager" onsubmit="return ChkData();">
	<table width="558" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="106"><font size="2">机构编码：</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="orgid" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">机构名称：</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="orgname" size="17">
        		<input type="hidden" name="txcode" value="10101"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">联系电话：</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="phoneno" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">IP地址：</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="ipaddress" size="17"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">机构地址：</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<input type="text" name="orgaddress" size="17"></font>
        	</td>
      		<td height="25" width="86"><font size="2">联系人：</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<input type="text" name="connecter" size="17"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="106"><font size="2">业务种类：</font></td>
      		<td height="25" width="193"> <font size="2"> 
        		<select name="opertype" style="width:120">
          		<option value="" selected>请选择业务种类</option>
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
      		<td height="25" width="86"><font size="2">上级机构：</font></td>
      		<td height="25" width="213"> <font size="2"> 
        		<select name="p_orgid" style="width:100">
          		<option value="" selected>请选择上级机构</option>
          		<%
  					if(AllOrgV != null && AllOrgV.size() >0){
    					for(int i = 0 ; i < AllOrgV.size(); i++){
	  						CodeBean code=(CodeBean)AllOrgV.get(i);
      						out.println("<option value=\""+code.getCodeId()+"\">"+code.getCodeDesc()+"</option>");
    					}
  					}else {
    					out.println("<option value=\"0\">最高级机构</option>");
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
          		<input type="submit" name="Submit" value="确定"  class="inputbutton">
          		<input type="reset" name="Submit2" value="取消"  class="inputbutton"  onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>