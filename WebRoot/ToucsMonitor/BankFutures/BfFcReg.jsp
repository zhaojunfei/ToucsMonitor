<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%
BfFcInfo Info = (BfFcInfo) request.getAttribute("BfFc");
if (Info == null) {
	Info = new BfFcInfo();
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
<%
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
	String date = dateformat.format(new Date());
	String time = timeformat.format(new Date());
%>
<script language="javascript">
function onBranchNameChange(){
	document.forms[0].branch_id.value= document.forms[0].branch_name.value;
}

function doSubmit(){
	if( isNull("fc_id","�̲ݹ�˾���")) return false;
	if( isNull("name","�̲ݹ�˾����")) return false;
	if( isNull("brief","�̲ݹ�˾���")) return false;
	if( isNull("secu_kind","��ȫ����")) return false;
	if( isNull("bank_acc_no","�����ʺ�")) return false;
	if( isNull("min_sum_of_tran","����ת����С���")) return false;
	if( isNull("max_sum_of_tran","����ת�������")) return false;
	if( isNull("level","��Ҫ�̶�")) return false;

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
<body bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/bffc?reqCode=17901&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">������λ����</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�̲ݹ�˾��ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="wideinput" value="" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap height="19" align="right">�̲ݹ�˾��ƣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="brief" class="wideinput" value="" maxlength="20" size="20">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�̲ݹ�˾���ƣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="name" class="wideinput" value="" maxlength="40" size="40">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap align="right">��ȫ���ࣺ</td>
			<td width="30%" nowrap>
				<select name="secu_kind" size="1">
				<option value="">��ѡ��</option>
				<%
					initList("SecuKind", "", request, out);
				%>
				</select>*
			</td>
			<td width="30%" nowrap>
				<input type="hidden" name="level" class="wideinput" value="1" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">IP��ַ��</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ip_addr" class="wideinput" value="" maxlength="16" size="16">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ϵ�ˣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="contractor" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ַ��</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="addr" class="wideinput" value="" maxlength="100" size="40">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�������룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="pc" class="wideinput" value="" maxlength="6" size="6">
			</td>
			<td width="20%" nowrap height="19" align="right">�绰���룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="telephone" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">������룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fax" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">�������䣺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="email" class="wideinput" value="" maxlength="50" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ʺţ�</td>
			<td width="60%" nowrap height="19">
				<input type="text" name="bank_acc_no" class="wideinput" value="" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�̻��ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="merchant_id" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">ժҪ�룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="org_id" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��С��</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="min_sum_of_tran" class="wideinput" value="" maxlength="10" size="10">*
			</td>
			<td width="20%" nowrap height="19" align="right">����</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="max_sum_of_tran" class="wideinput" value="" maxlength="10" size="10">
				*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ʼ���ڣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="start_date" class="wideinput" value="" maxlength="8" size="8">
			</td>
			<td width="20%" nowrap height="19" align="right">��ֹ���ڣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="end_date" class="wideinput" value="" maxlength="8" size="8">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap align="right">ǩ����־��</td>
			<td width="30%" nowrap>
				<select name="sign_flag" size="1">
				<option value="">��ѡ��</option>
				<%
					initList("SignFlag", "", request, out);
				%>
				</select>*
			</td>
			<td width="20%" nowrap align="right">���ñ�־��</td>
			<td width="30%" nowrap>
				<select name="use_flag" size="1">
				<option value="">��ѡ��</option>
				<%
					initList("UseFlag", "", request, out);
				%>
				</select>*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap>
				<input type="hidden" name="modify_date" class="wideinput" value="<%out.print(date);%>" maxlength="8" size="1">
			</td>
			<td width="30%" nowrap>
				<input type="hidden" name="modify_time" class="wideinput" value="<%out.print(time);%>" maxlength="8" size="1">
			</td>
		</tr>
	</table>
	<table width="80%">
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
				<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
				<input type="reset" name="cancelbutton" value="����" class="inputbutton"  >
				</div>
			</td>
		</tr>
	</table>
	<p />
</form>
</body>
</html>
