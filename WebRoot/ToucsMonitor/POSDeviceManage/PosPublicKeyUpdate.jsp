<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.common.toucsString"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<jsp:useBean id="POSPublicKey" class="com.adtec.toucs.monitor.POSDeviceManage.POSPublicKey" scope="request" />
<!--jsp:useBean id="posAccount" class="com.adtec.toucs.monitor.POSDeviceManage.POSAccount"  scope="request"/-->
<%
	POSPublicKey Key = (POSPublicKey) request.getAttribute("pospublickey");
	if (Key == null) {
		Key = new POSPublicKey();
	}
%>

<html>
<head>
	<title>��Կ�޸�</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/poskeyconfig?reqCode=13803&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="key" value="<%=Key.getRid()%>">
<font face="����" size="+2">��Կ�޸�</font>
	<table id="tab0" width="90%" height="95">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">RID��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="rid" class="wideinput" value="<%=Key.getRid()%>" READONLY style="background-color:#cccccc" MAXLENGTH="10" size="10">*</td>
			<td width="25%" nowrap height="19" align="right">��Կ����(Index)��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="index" class="wideinput" value="<%=Key.getIndex()%>" READONLY style="background-color:#cccccc" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կ��Ч�ڣ�</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="expire_date" value="<%=Key.getExpire_date()%>" READONLY style="background-color:#cccccc" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կ��ʲ�㷨��ʶ��</td>
			<td width="25%" nowrap height="19" nowrap>
				<input type="text" name="hashcmid" value="<%=Key.getHashcmid()%>" READONLY style="background-color:#cccccc" MAXLENGTH="2" size="2">*</td>
			<td width="25%" nowrap height="19" align="right">��Կ�㷨��ʶ��</td>
			<td width="25%" nowrap height="19" nowrap>
				<input type="text" name="cmid" value="<%=Key.getCmid()%>" READONLY style="background-color:#cccccc" MAXLENGTH="2"size="2">*
			</td>
		</tr>
	</table>
	<table id="tab1" width="90%">
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կģ(Modulus)��</td>
			<td width="75%" nowrap height="19">
				<textarea name="modulus" rows="16" cols="50" READONLY style="background-color:#cccccc"><%=Key.getModulus()%></textarea>*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կָ��(Exponent)��</td>
			<td width="25%" nowrap height="19" nowrap>
				<input type="text" name="exponent" class="wideinput" value="<%=Key.getExponent()%>" READONLY style="background-color:#cccccc" MAXLENGTH="6" size="6">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��ԿУ��ֵ(SHA)��</td>
			<td width="25%" nowrap height="19" nowrap>
				<input type="text" name="sha1h" class="wideinput" value="<%=Key.getSha1h()%>" READONLY style="background-color:#cccccc" MAXLENGTH="40" size="40">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">�������ڣ�</td>
			<td width="75% nowrap height="19">
				<input type="text" name="create_date" value="<%=Key.getCreate_date()%>" READONLY style="background-color:#cccccc" MAXLENGTH="14" size="14">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��ע1��</td>
			<td width="75%" nowrap height="19">
				<input type="text" name="memo1" value="<%=Key.getMemo1()%>" MAXLENGTH="32" size="32">
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
				<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
				<input type="reset" name="Submit2" value="ȡ��" class="inputbutton" onClick="history.go(-1)";>
				<%--<input type="button" name="cancelbutton" value="ȡ��"
				class="inputbutton"
				onClick="javascript:location.href='/ToucsMonitor/POSDeviceManage/PosPublicKeyList.jsp'">
				--%>
				</div>
			</td>
		</tr>
	</table>
<p />
</form>
</body>
</html>