<%@ page language="java" contentType="text/html; charset=GBK" %>

<!--JSP方法:初始化列表框-->

<html>
<!--页面校验-->
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if (document.forms[0].fc_id.value.length == 0 && document.forms[0].investor_id.value.length == 0){
		alert("烟草区县公司代码和卡号不能全为空");
		return false;
	}
	return true;
}
</script>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formQuery" method="post" action="/ToucsMonitor/bfinvestor?reqCode=17914&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">烟草公司客户查询</font>
	<table width="80%"   cellpadding="0" cellspacing="0">
    	<tr>
    		<td colspan="4"  ><hr noshade></td>   
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >烟草区县公司代码：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="fc_id" class="wideinput" value="" maxlength="30"  size="30" >
      		</td>
    	</tr>
   	 	<tr>
      		<td width="20%" nowrap height="19" align="right" >卡号：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="investor_id" class="wideinput" value="" maxlength="30"  size="30" >
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4"><hr noshade></td>
    	</tr>
    	<tr>
      		<td colspan="4">
        	<div align="right">
          		<input type="submit" name="Submit32" value="确定" class="inputbutton">
        	</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>