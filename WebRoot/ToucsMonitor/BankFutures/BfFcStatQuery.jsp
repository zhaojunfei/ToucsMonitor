<%@ page language="java" contentType="text/html; charset=GBK"%>

<!--JSP方法:初始化列表框-->
<html>
<!--页面校验-->
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if( isNull("fc_id", "投资人开户期货公司")) return false;
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
	String uid = request.getParameter("uid");
%>
<form name="formQuery" method="post" action="/ToucsMonitor/bffc?reqCode=17907&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">合作单位交易统计</font>
	<table width="80%" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="4"><hr noshade></td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">合作单位编号：</td>
			<td width="60%" nowrap height="19">
				<input type="text" name="fc_id" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">起始日期：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="start_date" class="wideinput" value="" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">终止日期：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="end_date" class="wideinput" value="" maxlength="8" size="8">
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