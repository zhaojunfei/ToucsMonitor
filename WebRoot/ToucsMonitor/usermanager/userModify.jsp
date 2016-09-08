<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*"%>
<html>
<head>
<title>用户登记</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script src="js/power.js"></script>
<script src="/ToucsMonitor/ToucsMonitor/usermanager/js/power.js"></script>
<Script>
<!--
function CheckData(){
  	var username = document.form1.username.value;
  	var orgid = document.form1.orgid.value;
  	if( username == "" ){
    	alert("请输入用户名称！");
    	document.form1.username.focus();
    	return false;
  	}
  	if( orgid == "" ){
    	alert("请选择机构！");
    	document.form1.orgid.focus();
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
	String userId = request.getParameter("userid");
	String orgID = request.getParameter("orgid");
	Vector userV = new Vector();
	Hashtable userH = new Hashtable();
	Vector OV = new Vector();
	Hashtable OH = new Hashtable();
	try{
  		userV = UM.queryUserInfo(userId);
  		if(userV == null || userV.isEmpty()){
    		out.println("<script>alert(\"没有查到用户信息！\");history.go(-1);</script>");
    		return;
  		}
  		userH = (Hashtable)userV.get(0);
  		OV = UM.getSubOrg(orgID);
	}catch(MonitorException ex){
  		out.println("<script>alert(\"系统异常,代码："+ex.getErrCode()+"\");</script>");
  		return;
	}
%>
<h2><font face="隶书">用户管理</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/usermanagerservlet" onsubmit="return CheckData()">
  	<table width="700" border="0" cellspacing="0" cellpadding="0" height="143">
    	<tr> 
      		<td colspan="4"> 
        		<div align="center">
          		<hr align="left" noshade>
        		</div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="121" height="30" align="right"><font size="2">用户帐号：</font></td>
      		<td width="257" height="30"><font size="2"><%=userH.get("user_id")%></font> 
        		<input type="hidden" name="oper_type" value="10202">
        		<input type="hidden" name="userid" size="12" value="<%=userH.get("user_id")%>">
      		</td>
      		<td width="117" height="30" align="right"><font size="2">用户姓名：</font></td>
      		<td width="256" height="30"> 
        		<input type="text" name="username" class="wideinput" size="12" maxlength="20" value="<%=userH.get("user_name")%>">
      		</td>
    	</tr>
    	<tr> 
      		<td width="121" height="30" align="right"><font size="2">雇用时间：</font></td>
      		<td width="257" height="30"> 
        		<input type="text" name="employdate" style="width:100" size="10" maxlength="10" value="<%=String.valueOf(userH.get("employ_date")).trim().equals("")?"":userH.get("employ_date").toString().substring(0,4)+"-"+userH.get("employ_date").toString().substring(4,6)+"-"+userH.get("employ_date").toString().substring(6,8)%>">
        		<font size="2" color="#0000FF">(yyyy-mm-dd)</font> 
        	</td>
      		<td width="117" height="30" align="right"><font size="2">解雇时间：</font></td>
      		<td width="256" height="30"> 
        		<input type="text" name="firedate" style="width:100" size="10" maxlength="10" value="<%=String.valueOf(userH.get("fire_date")).trim().equals("")?"":userH.get("fire_date").toString().substring(0,4)+"-"+userH.get("fire_date").toString().substring(4,6)+"-"+userH.get("fire_date").toString().substring(6,8)%>">
        		<font size="2" color="#0000FF">(yyyy-mm-dd)</font>
        	</td>
    	</tr>
    	<tr> 
      		<td width="121" height="30" align="right"><font size="2">机构编码：</font></td>
      		<td width="257" height="30"> 
        		<select name="orgid">
          		<option value="">请选择机构编码</option>
          		<%
  					if(OV != null){
    					String Content = " selected";
    					for(int i=0;i<OV.size();i++){
      						OH = new Hashtable();
     	 					OH = (Hashtable)OV.get(i);
      						out.print("<option value=\""+OH.get("org_id")+"\"");
      						if(String.valueOf(OH.get("org_id")).trim().equals(String.valueOf(userH.get("org_id")).trim())){
        						out.print(Content);
     	 					}
      						out.println(">"+OH.get("org_name")+"</option>");
    					}
  					}
				%>
        		</select>
      		</td>
      		<td width="117" height="30" align="right">　</td>
      		<td width="256" height="30">&nbsp; </td>
    	</tr>
    	<tr> 
      		<td width="121" height="69" align="right" > 
				<font size="2">用户描述：</font>
      		</td>
      		<td colspan="3" height="69"> 
        		<textarea name="userdesc" cols="25" rows="3"><%=userH.get("user_desc")%></textarea>
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
          		<input type="submit" name="Submit" value="确定" class="inputbutton">
          		<input type="reset" name="Submit2" value="取消" class="inputbutton" onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>