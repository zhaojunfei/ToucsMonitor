<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%
BfInvestorInfo Info = (BfInvestorInfo) request.getAttribute("BfInvestor");
if (Info == null) {
	Info = new BfInvestorInfo();
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
	if( isNull("investor_id","Ͷ����ID")) return false;
	if( isNull("name","Ͷ��������")) return false;
	if( isNull("fc_id","Ͷ���˿����ڻ���˾")) return false;
	if( isNull("type","Ͷ��������")) return false;
	if( isNull("id_card_type","֤������")) return false;
	if( isNull("id_card_code","֤������")) return false;
	if( isNull("deposit_acc_no","��֤���ʺ�")) return false;
	if( isNull("level","��Ҫ�̶�")) return false;
	if( isNull("use_flag","��Ч��־")) return false;

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
<form name="formReg" method="post" action="/ToucsMonitor/bfinvestor?reqCode=17913&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">������λ�ͻ��޸�</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�̲����ع�˾���룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="readonly" value="<%=Info.getFc_id()%>" READONLY style="background-color:#cccccc" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap height="19" align="right">���ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="investor_id" class="readonly" value="<%=Info.getInvestor_id()%>" READONLY style="background-color:#cccccc" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�ͻ�������</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="name" class="wideinput" value="<%=Info.getName()%>" maxlength="40" size="40">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">���֤���룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="id_card_code" class="wideinput" value="<%=Info.getId_card_code()%>" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap>
				<input type="hidden" name="type" class="wideinput" value="0" maxlength="1" size="1">
			</td>
			<td width="30%" nowrap>
				<input type="hidden" name="id_card_type" class="wideinput" value="0" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�̲ݿͻ��ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="deposit_acc_no" class="wideinput" value="<%=Info.getDeposit_acc_no()%>" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap>
				<input type="hidden" name="level" class="wideinput" value="0" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ַ��</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="addr" class="wideinput" value="<%=Info.getAddr()%>" maxlength="100" size="40">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�������룺</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="pc" class="wideinput" value="<%=Info.getPc()%>" maxlength="6" size="6">
			</td>
			<td width="20%" nowrap height="19" align="right">�Թ��˺�ת�����ߣ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="telephone" class="wideinput" value="<%=Info.getTelephone()%>" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">���ÿ��󶨽�ǿ����ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fax" class="wideinput" value="<%=Info.getFax()%>" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">�̲�ר���ţ�</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="email" class="wideinput" value="<%=Info.getEmail()%>" maxlength="50" size="30">
			</td>
		</tr>
		<tr>
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
			    <input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
			</div>
			</td>
		</tr>
	</table>
<p/>
</form>
</body>
</html>