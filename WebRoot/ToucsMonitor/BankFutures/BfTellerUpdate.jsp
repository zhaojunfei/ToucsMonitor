<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%
BfTellerInfo Info = (BfTellerInfo) request.getAttribute("BfTeller");
if (Info == null) {
	Info = new BfTellerInfo();
}
%>
<%!
private void initList(String listName, String defValue,HttpServletRequest req, JspWriter out) {
	List list = (List) req.getAttribute(listName);
	String flag = "";
	if (list != null) {
		for (int i = 0; i < list.size(); i++) {
			CodeBean code = (CodeBean) list.get(i);
			if (defValue != null && defValue.equals(code.getCodeId()))
				flag = " SELECTED";
			else
				flag = "";
			try {
				out.println("<option value=\"" + code.getCodeId() + "\""
						+ flag + ">" + code.getCodeDesc() + "</option>");
			} catch (IOException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		}
	}
}
%>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function onBranchNameChange(){
	document.forms[0].branch_id.value= document.forms[0].branch_name.value;
}

function doSubmit(){
	if( isNull("op_no","��Ա��")) return false;
	if( isNull("org_id","������")) return false;
	if( isNull("fc_id","������λ���")) return false;
	if( isNull("sign_flag","ǩ����־")) return false;
	if( isNull("use_flag","ʹ�ñ�־")) return false;

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
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
<form name="formReg" method="post" action="/ToucsMonitor/bfteller?reqCode=17923&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">�ڻ���Ա����</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��Ա�ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="op_no" class="readonly" value="<%=Info.getOp_no()%>" READONLY style="background-color:#cccccc" maxlength="20" size="20">*
			</td>
			<td width="20%" nowrap height="19" align="right">�����ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="org_id" class="wideinput" value="<%=Info.getOrg_id()%>" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">������λ��ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="wideinput" value="<%=Info.getFc_id()%>" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">DCC�ն����ͣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="dcc_term_type" class="wideinput" value="<%=Info.getDcc_term_type()%>" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">DCC�ն���ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="dcc_term_srl" class="wideinput" value="<%=Info.getDcc_term_srl()%>" maxlength="3" size="3">
			</td>
			<td width="30%" nowrap>
				<input type="hidden" name="passwd" class="wideinput" value="" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap align="right">ǩ����־��</td>
			<td width="30%" nowrap>
				<select name="sign_flag" size="1">
				<option value="">��ѡ��</option>
				<%
					initList("SignFlag", Info.getSign_flag(), request, out);
				%>
				</select>*
			</td>
			<td width="20%" nowrap align="right">���ñ�־��</td>
			<td width="30%" nowrap>
				<select name="use_flag" size="1">
				<option value="">��ѡ��</option>
				<%
					initList("UseFlag", Info.getUse_flag(), request, out);
				%>
				</select>*
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
<p/>
</form>
</body>
</html>
