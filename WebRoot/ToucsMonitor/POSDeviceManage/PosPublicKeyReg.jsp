<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.common.toucsString"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.swing.*"%>
<%@ page import="java.io.*"%>

<jsp:directive.page import="java.text.SimpleDateFormat" />
<jsp:directive.page import="javax.swing.text.DateFormatter" />
<jsp:directive.page import="javax.swing.JSpinner.DateEditor" />
<jsp:directive.page import="javax.swing.JSpinner.DateEditor" />
<jsp:useBean id="POSPublicKeyBean" class="com.adtec.toucs.monitor.POSDeviceManage.POSPublicKeyBean" scope="request" />
<!--jsp:useBean id="posEnroll" class="com.adtec.toucs.monitor.POSDeviceManage.POSEnrollInfo"  scope="request"/-->
<%
	POSPublicKeyBean posEnroll = (POSPublicKeyBean) request.getAttribute("posEnroll");
	if (posEnroll == null) {
		posEnroll = new POSPublicKeyBean();
	}

	Date currentTime = new Date();
%>



<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	String date = formatter.format(new Date());
%>

<html>
<head>
	<title>��Կ����</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if( isNull("rid","RID")) return false;
	if (document.forms[0].rid.value.length < 10 ){
		alert("RID���벻��С��10λ");
		return false;
	}	
	
	if( isNull("index","��Կ����")) return false;
	if (document.forms[0].index.value.length != 2){
		alert("��Կ��������Ϊ2λ");
		return false;
	}	
	
	if( isNull("expire_date","��Կ��Ч��")) return false;
	if (document.forms[0].expire_date.value.length != 8){
		alert("��Կ��Ч�ڱ���Ϊ8λ");
		return false;
	}	
	
	if( isNull("hashcmid","��Կ��ʲ�㷨��ʶ")) return false;
	if (document.forms[0].hashcmid.value.length != 2){
		alert("��Կ��ʲ�㷨��ʶ����Ϊ2λ");
		return false;
	}	
	
	if( isNull("cmid","��Կ�㷨��ʶ")) return false;	
	if (document.forms[0].cmid.value.length != 2){
		alert("��Կ�㷨��ʶ����Ϊ2λ");
		return false;
	}	
	
	if( isNull("modulus","��Կģ")) return false;
	
	if( isNull("exponent","��Կָ��")) return false;
	if (!(document.forms[0].exponent.value.length == 2 || document.forms[0].exponent.value.length == 6)){
		alert("��Կָ������Ϊ2λ��6λ");
		return false;
	}	
	
	if( isNull("sha1h","��ԿУ��ֵ")) return false;
	
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
<form name="formReg" method="post" action="/ToucsMonitor/poskeyconfig?reqCode=13801&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">��Կ����</font>
	<table id="tab0" width="90%">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">RID��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="rid" class="wideinput" value="" MAXLENGTH="14" size="14">*
			</td>
			<td width="25%" nowrap height="19" align="right">��Կ����(Index)��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="index" class="wideinput" value="" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կ��Ч�ڣ�</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="expire_date" value="" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կ��ʲ�㷨��ʶ��</td>
			<td width="25%" height="19" nowrap>
				<input type="text" name="hashcmid" value="01" MAXLENGTH="2" size="2">*
			</td>
			<td width="25%" nowrap height="19" align="right">��Կ�㷨��ʶ��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="cmid" value="01" MAXLENGTH="2" size="2">*
			</td>
		</tr>
	</table>
	<table width="90%">
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կģ(Modulus)��</td>
			<td nowrap height="19">
				<textarea name="modulus" rows="16" cols="50"></textarea>*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��Կָ��(Exponent)��</td>
			<td width="25%" nowrap height="19">
				<input type="text" name="exponent" class="wideinput" value="" MAXLENGTH="6" size="6">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��ԿУ��ֵ(SHA)��
			</td>
			<td nowrap height="19">
				<textarea name="sha1h" rows="2" cols="50"></textarea>*
			</td>
		</tr>
		<tr>
			<td nowrap height="19" align="right">�������ڣ�
			</td>
			<td width="75%" nowrap height="19">
				<input type="text" name="create_date" value=<%out.print(date);%> READONLY style="background-color:#cccccc" MAXLENGTH="14" size="14">*
			</td>
		</tr>
		<tr>
			<td width="25%" nowrap height="19" align="right">��ע1��</td>
			<td nowrap height="19">
				<input type="text" name="memo1" value="" MAXLENGTH="32" size="32">
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
					<input type="reset" name="reset" value="����" class="inputbutton">
				</div>
			</td>
		</tr>
	</table>
<p />
</form>
</body>
</html>