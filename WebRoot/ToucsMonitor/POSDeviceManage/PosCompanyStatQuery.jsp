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
	if( isNull("company_id", "��˾���")) return false;
	
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		if( isNull("month", "�·�")) return false;
	}else{
		document.all.condTable.style.display="none";
		if( isNull("start_date", "��ʼ����")) return false;
		if( isNull("end_date", "��ֹ����")) return false;
	
		if (document.forms[0].start_date.value.length != 8)	{
			alert("��������ȷ��ʽ����ʼ����:YYYYMMDD");
			return false;
		}
		
		if (document.forms[0].end_date.value.length != 8)	{
			alert("��������ȷ��ʽ����ֹ����:YYYYMMDD");
			return false;
		}
	}
	return true;
}
</script>
<head>
	<title>��ҵIC����˾������ͳ��</title>
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
<font face="����" size="+2">��ҵIC����˾����ͳ��</font>
<table width="80%" cellpadding="0" cellspacing="0"><tr><td colspan="4"><hr noshade></td><tr>
	<td width="15%" nowrap height="19" align="right">��˾��ţ�</td><td width="26%" nowrap height="19">
	<input type="text" name="company_id" class="wideinput" value="" maxlength="9" size="9">
	<span class="STYLE1">*</span></td>
	</tr><tr><td width="15%" nowrap height="19" align="right">��ʼ���ڣ�</td><td width="26%" nowrap height="19">
	<input type="text" name="start_date" class="wideinput" value="" maxlength="8" size="8">
	<span class="STYLE1">*</span>(YYYYMMDD)
	</td>
	<td width="18%" nowrap height="19" align="right">��ֹ���ڣ�</td><td width="41%" nowrap height="19">
	<input type="text" name="end_date" class="wideinput" value="" maxlength="8" size="8">
	<span class="STYLE1">*</span>(YYYYMMDD)
	</td>
	</tr><tr><td></td></tr><tr><td colspan="4"><hr noshade></td></tr><tr><td colspan="4" height="20">
	<input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">����ͳ�� 
	</td></tr><tr><td colspan="4" >
<table width="99%" id="condTable" ><tr>
	<td width="17%" height="24" align="right">ѡ���·ݣ�</td>
	<td  height="24">
		<select name="month">
			<option value="">��ѡ��</option>
			<option value="01">һ��</option>
			<option value="02">����</option>
			<option value="03">����</option>
			<option value="04">����</option>
			<option value="05">����</option>
			<option value="06">����</option>
			<option value="07">����</option>
			<option value="08">����</option>
			<option value="09">����</option>
			<option value="10">ʮ��</option>
			<option value="11">ʮһ��</option>
			<option value="12">ʮ����</option>
		</select>
		<span class="STYLE1">*</span>(���ڻ���ڵ�ǰ�·�Ϊǰһ�꽻��ͳ����Ϣ)</td>
</tr><tr>
	<td width="17%" height="44" align="right">�̻���ţ�</td>
	<td  height="44"><input type=text size=20 MAXLENGTH=40 name="merchant_id" ></td></tr>
</table>
</td></tr><tr><td colspan="4"><div align="right">
	<input type="submit" name="Submit32" value="ȷ��" class="inputbutton">
</div></td></tr></table></form></body><script language="javascript">
	document.all.condTable.style.display="none";
</script></html>