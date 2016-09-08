<%@ page language="java" contentType="text/html; charset=gb2312" %>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/Check.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.merchant_id.disabled=true;
	}else{
		document.all.condTable.style.display="none";
		document.formQuery.merchant_id.disabled=false;
		document.formQuery.merchant_id.focus();
	}
}

function doSubmit(){
	merchant_id=document.formQuery.merchant_id.value;
	if(merchant_id.length != 15){
		alert("请输入15位POS商户编号!");
		document.formQuery.merchant_id.focus();
		return false;
	}
	return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String post=request.getParameter("post");
%>
<h2><font face="隶书">外卡商户信息查询</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/fcardmerchantconfig?reqCode=10504&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
<input type="hidden" name="post" value=<%=post%>>
<center>
	<table width="80%">
    	<tr>
      		<td colspan="4" width="662">
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<table width="668" height="24">
          			<tr>
           	 			<td width="106" height="19">外卡商户编号：</td>
            			<td width="386" height="19">
              				<input type="text" name="merchant_id" value="" size="20" maxlength="15">
            			</td>
          			</tr>
        		</table>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4" width="662" height="16">
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4" width="662" height="21">
        		<div align="right">
          		<input type="submit" name="Submit32" value="确定" class="inputbutton">
				</div>
      		</td>
    	</tr>
  	</table>
 </center>
</form>
</body>
</html>