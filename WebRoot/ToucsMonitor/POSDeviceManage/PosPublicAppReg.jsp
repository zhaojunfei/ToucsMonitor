<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.common.toucsString"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>


<jsp:useBean id="POSPublicAppBean" class="com.adtec.toucs.monitor.POSDeviceManage.POSPublicAppBean" scope="request" />
<!--jsp:useBean id="posEnroll" class="com.adtec.toucs.monitor.POSDeviceManage.POSEnrollInfo"  scope="request"/-->
<%
	POSPublicAppBean posEnroll = (POSPublicAppBean) request.getAttribute("posEnroll");
	System.out.println("RRRRRRRRRRRRRRRRR");
	if (posEnroll == null) {
		System.out.println("RRRRRRRRRRRRRRRRR");
		posEnroll = new POSPublicAppBean();
	}
%>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	String date = formatter.format(new Date());
%>

<html>
<head>
	<title>EMV卡参数新增</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){

	if( isNull("aid","AID")) return false;
	if (document.forms[0].aid.value.length <= 9 || document.forms[0].aid.value.length >= 33){
		alert("AID必须在10位到32位之间");
		return false;
	}	
	
	if( isNull("asi","应用选择指示符")) return false;	
	if (document.forms[0].asi.value.length != 2){
		alert("应用选择指示符必须为2位");
		return false;
	}	
	
	if( isNull("appedition","应用版本号")) return false;
	if (document.forms[0].appedition.value.length != 4){
		alert("应用版本号必须为4位");
		return false;
	}	
	
	if( isNull("tac_default","TAC-缺省")) return false;
	if (document.forms[0].tac_default.value.length != 10){
		alert("TAC-缺省必须为10位");
		return false;
	}	
	
	if( isNull("tac_online","TAC-联机")) return false;	
	if (document.forms[0].tac_online.value.length != 10){
		alert("TAC-联机必须为10位");
		return false;
	}	
	
	if( isNull("tac_refuse","TAC-拒绝")) return false;
	if (document.forms[0].tac_refuse.value.length != 10){
		alert("TAC-拒绝必须为10位");
		return false;
	}	
	
	if( isNull("floorlimit","终端最低限额")) return false;
	if (document.forms[0].floorlimit.value.length != 8){
		alert("终端最低限额必须为8位");
		return false;
	}	
	
	if( isNull("cdoor","偏置随机选择的阀值")) return false;
	if (document.forms[0].cdoor.value.length != 8){
		alert("偏置随机选择的阀值必须为8位");
		return false;
	}	
	
	if( isNull("maxpercent","偏置随机选择的最大目标百分数")) return false;
	if (document.forms[0].maxpercent.value.length != 2){
		alert("偏置随机选择的最大目标百分数必须为2位");
		return false;
	}	
	
	if( isNull("percent","随机选择的目标百分数")) return false;
	if (document.forms[0].percent.value.length != 2){
		alert("随机选择的目标百分数必须为2位");
		return false;
	}	
	
	if( isNull("ddol","缺省DDOL")) return false;
	
	if( isNull("pinability","终端联机PIN支持能力")) return false;
	if (document.forms[0].pinability.value.length != 2){
		alert("终端联机PIN支持能力必须为2位");
		return false;
	}	

	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}

</script>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/pospublicappconfig?reqCode=13901&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">EMV卡参数新增</font>
	<table id="tab0" width="90%" height="313">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">AID：</td>
			<td height="19" colspan="3" nowrap>
				<input type="text" name="aid" class="wideinput" value="" MAXLENGTH="32" size="32">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">应用选择指示符：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="asi" class="wideinput" value="" maxlength="2" size="2">*
			</td>
			<td width="30%" nowrap height="19" align="right">应用版本号：</td>
			<td nowrap height="19">
				<input type="text" name="appedition" value="" MAXLENGTH="4" size="4">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">TAC-缺省：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="tac_default" value="" MAXLENGTH="10" size="10">*
			</td>
			<td width="30%" nowrap height="19" align="right">TAC-联机：</td>
			<td colspan="3" nowrap height="19">
				<input type="text" name="tac_online" value="" MAXLENGTH="10" size="10">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">TAC-拒绝：</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="tac_refuse" value="" MAXLENGTH="10" size="10">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">终端最低限额：</td>
			<td width="30%" height="19" nowrap>
				<input type="text" name="floorlimit" class="wideinput" value="" MAXLENGTH="8" size="8">*
			</td>
			<td width="30%" nowrap height="19" align="right">偏置随机选择的阀值：</td>
			<td nowrap height="19"  colspan="3">
				<input type="text" name="cdoor" value="" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">偏置随机选择的最大目标百分数：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="maxpercent" value="" MAXLENGTH="2" size="2">*
			</td>
			<td width="30%" nowrap height="19" align="right">随机选择的目标百分数：</td>
			<td nowrap height="19"  colspan="3">
				<input type="text" name="percent" value="" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">缺省DDOL：</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="ddol" value="" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">终端联机PIN支持能力：</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="pinability" value="" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">创建日期：</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="create_date" value=<%out.print(date);%> READONLY style="background-color:#cccccc" MAXLENGTH="14" size="14">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">备注1：</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="memo1" value="" MAXLENGTH="32" size="32">
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
	</table>
	<table width="89%">
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
					<input type="submit" name="submitbutton" value="提交" class="inputbutton">
					<input type="reset" name="reset" value="重填" class="inputbutton">
					<%--<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick="javascript:location.href='/ToucsMonitor/POSDeviceManage/PosAccountManage.jsp'">--%>
				</div>
			</td>
		</tr>
	</table>
	<p />
</form>
</body>
</html>