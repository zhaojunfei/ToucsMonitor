<%@ page language="java" contentType="text/html; charset=gb2312"%>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/power.js"></script>
<script language="javascript">
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.start_date.disabled=true;
		document.formQuery.end_date.disabled=true;
	}
	else{
		document.all.condTable.style.display="none";
		document.formQuery.start_date.disabled=false;
		document.formQuery.end_date.disabled=false;
		document.formQuery.start_date.focus();
		document.formQuery.end_date.focus();
	}
}

function doSubmit(){
	if( isNull("company_id", "公司编号")) return false;
	
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		if( isNull("month", "月份")) return false;
	}else{
		document.all.condTable.style.display="none";
		if( isNull("start_date", "起始日期")) return false;
		if( isNull("end_date", "终止日期")) return false;
	
		if (document.forms[0].start_date.value.length != 8)	{
			alert("请输入正确格式的起始日期:YYYYMMDD");
			return false;
		}
		
		if (document.forms[0].end_date.value.length != 8)	{
			alert("请输入正确格式的终止日期:YYYYMMDD");
			return false;
		}
	}
	return true;
}
</script>
<head>
	<title>商业IC卡公司卡交易统计</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
    <style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
    </style>
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<% String uid = request.getParameter("uid"); %>
<form name="formQuery" method="post" action="/ToucsMonitor/poscompany?reqCode=16507&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">商业IC卡公司交易统计</font>
<table width="80%" cellpadding="0" cellspacing="0"><tr><td colspan="4"><hr noshade></td><tr>
	<td width="15%" nowrap height="19" align="right">公司编号：</td><td width="26%" nowrap height="19">
	<input type="text" name="company_id" class="wideinput" value="" maxlength="9" size="9">
	<span class="STYLE1">*</span></td>
	</tr><tr><td width="15%" nowrap height="19" align="right">起始日期：</td><td width="26%" nowrap height="19">
	<input type="text" name="start_date" class="wideinput" value="" maxlength="8" size="8">
	<span class="STYLE1">*</span>(YYYYMMDD)
	</td>
	<td width="18%" nowrap height="19" align="right">终止日期：</td><td width="41%" nowrap height="19">
	<input type="text" name="end_date" class="wideinput" value="" maxlength="8" size="8">
	<span class="STYLE1">*</span>(YYYYMMDD)
	</td>
	</tr><tr><td></td></tr><tr><td colspan="4"><hr noshade></td></tr><tr><td colspan="4" height="20">
	<input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">按月统计 
	</td></tr><tr><td colspan="4" >
<table width="99%" id="condTable" ><tr>
	<td width="17%" height="24" align="right">选择月份：</td>
	<td  height="24">
		<select name="month">
			<option value="">请选择</option>
			<option value="01">一月</option>
			<option value="02">二月</option>
			<option value="03">三月</option>
			<option value="04">四月</option>
			<option value="05">五月</option>
			<option value="06">六月</option>
			<option value="07">七月</option>
			<option value="08">八月</option>
			<option value="09">九月</option>
			<option value="10">十月</option>
			<option value="11">十一月</option>
			<option value="12">十二月</option>
		</select>
		<span class="STYLE1">*</span>(大于或等于当前月份为前一年交易统计信息)</td>
</tr><tr>
	<td width="17%" height="44" align="right">商户编号：</td>
	<td  height="44"><input type=text size=20 MAXLENGTH=40 name="merchant_id" ></td></tr>
</table>
</td></tr><tr><td colspan="4"><div align="right">
	<input type="submit" name="Submit32" value="确定" class="inputbutton">
</div></td></tr></table></form></body><script language="javascript">
	document.all.condTable.style.display="none";
</script></html>