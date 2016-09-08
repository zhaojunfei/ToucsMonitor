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

<%
	String uid=request.getParameter("uid");
	String post=request.getParameter("post");
%>
function doSubmit(){
	merchant_id = document.formQuery.merchant_id.value;
	if (merchant_id.length!=15){
		alert("请输入15位商户编号!");
		document.formQuery.merchant_id.focus();
		return false;
	}
	document.formQuery.action="/ToucsMonitor/fcardmerchantconfig?reqCode=10502&target=queryPage&uid=<%=uid%>"
	return true;
}
</script>

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">外卡商户信息修改</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/fcardmerchantconfig?reqCode=10502&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
<input type="hidden" name="post" value=<%=post%>>
<center>
  	<table width="80%">
    	<tr>
      		<td colspan="4">
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<table width="75%">
          			<tr>
            			<td width="22%">外卡商户编号：</td>
            			<td width="78%"><input type="text" name="merchant_id" value="" size="20" maxlength="15" ></td>
          			</tr>
        		</table>      
        	</td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4">
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