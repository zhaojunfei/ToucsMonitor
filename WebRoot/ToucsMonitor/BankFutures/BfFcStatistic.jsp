<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>

<%
	BfFcStatistic Info = (BfFcStatistic) request.getAttribute("bffcstatistic");
	if (Info == null) {
		Info = new BfFcStatistic();
	}
%>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
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
<form name="formReg" method="post" action="/ToucsMonitor/bffc?reqCode=17907&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">������λͳ����Ϣ</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">������λ��ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="readonly" value="<%=Info.getFc_id()%>" READONLY style="background-color:#cccccc" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ʼ���ڣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="start_date" class="readonly" value="<%=Info.getStart_date()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">��ֹ���ڣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="end_date" class="readonly" value="<%=Info.getEnd_date()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="open_num" class="readonly" value="<%=Info.getOpen_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">��������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="close_num" class="readonly" value="<%=Info.getClose_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�ɹ���ת�������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ok_cre_num" class="readonly" value="<%=Info.getOk_cre_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">�ɹ���ת���</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ok_cre_amt" class="readonly" value="<%=Info.getOk_cre_amt()%>" READONLY style="background-color:#cccccc" maxlength="18" size="18">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�ɹ���ת��������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ok_deb_num" class="readonly" value="<%=Info.getOk_deb_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">�ɹ���ת����</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ok_deb_amt" class="readonly" value="<%=Info.getOk_deb_amt()%>" READONLY style="background-color:#cccccc" maxlength="18" size="18">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">������ת�������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="qe_cre_num" class="readonly" value="<%=Info.getQe_cre_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">������ת���</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="qe_cre_amt" class="readonly" value="<%=Info.getQe_cre_amt()%>" READONLY style="background-color:#cccccc" maxlength="18" size="18">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">������ת��������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="qe_deb_num" class="readonly" value="<%=Info.getQe_deb_num()%>" READONLY style="background-color:#cccccc" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">������ת����</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="qe_deb_amt" class="readonly" value="<%=Info.getQe_deb_amt()%>" READONLY style="background-color:#cccccc" maxlength="18" size="18">
			</td>
		</tr>
	</table>
	<table width="80%">
		<tr>
			<td colspan="5" nowrap>
			<div align="right">
				<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
				<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
			</div>
			</td>
		</tr>
	</table>
	<p />
</form>
</body>
</html>
