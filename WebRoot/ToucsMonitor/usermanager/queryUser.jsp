<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.usermanager.*" %>
<%@ page import="com.adtec.toucs.monitor.loginmanage.*" %>
<html>
<head>
	<title>queryUser</title>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">查询用户信息</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/usermanagerservlet">
	<table width="75%" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" height="30"><font size="2">用户编号：</font></td>
      		<td width="66%" height="30"> 
        		<input type="text" name="userid" size="15">
        		<input type="hidden" name="oper_type" value="10204">
      		</td>
    	</tr>
    	<tr> 
      		<td height="30" width="20%"> <font size="2">机构名称</font><font size="2">：</font></td>
      		<td height="30"> 
        		<select name="OrgCodeSel">
          		<%
					LoginInfo loginfo=(LoginInfo)request.getAttribute("LInfo");
					String selOrgID=loginfo.getOrgID();
					userManagerBean um=new userManagerBean();
					List orgList=null;
					orgList=um.queryOrgList(selOrgID);		
	
					if(selOrgID==null){
						selOrgID="";
					}
					selOrgID=selOrgID.trim();
	
					if(orgList!=null){
						String orgcode="";
						String orgname="";
		
	  					for(int i=0;i<orgList.size();i++){
							CodeBean orgDeviceCode=(CodeBean)orgList.get(i);
							if(orgDeviceCode!=null){
								orgcode=orgDeviceCode.getCodeId();
								orgname=orgDeviceCode.getCodeDesc();
								String sel="";
								if(orgcode!=null){
									orgcode=orgcode.trim();
								}
								if(selOrgID.equals(orgcode)) sel="selected";
    			%>
          		<option value="<%=orgcode%>" <%=sel%>> <%=orgname%></option>
          		<%
							}
						}
					}
				%>
        		</select>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2" height="30"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2" height="30"> 
        		<div align="right"> 
          		<input type="submit" name="Submit3" value="查询" class="inputbutton">
          		<input type="reset" name="Submit22" value="取消" class="inputbutton" onclick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>