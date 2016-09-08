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
	if( isNull("fc_id","烟草公司编号")) return false;
	if( isNull("name","烟草公司名称")) return false;
	if( isNull("brief","烟草公司简称")) return false;
	if( isNull("secu_kind","安全种类")) return false;
	if( isNull("bank_acc_no","银行帐号")) return false;
	if( isNull("min_sum_of_tran","单笔转帐最小金额")) return false;
	if( isNull("max_sum_of_tran","单笔转帐最大金额")) return false;
	if( isNull("level","重要程度")) return false;

	if(confirm("确认输入无误并提交吗？"))
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
<font face="隶书" size="+2">合作单位新增</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">烟草公司编号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="wideinput" value="" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap height="19" align="right">烟草公司简称：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="brief" class="wideinput" value="" maxlength="20" size="20">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">烟草公司名称：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="name" class="wideinput" value="" maxlength="40" size="40">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap align="right">安全种类：</td>
			<td width="30%" nowrap>
				<select name="secu_kind" size="1">
				<option value="">请选择</option>
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
			<td width="20%" nowrap height="19" align="right">IP地址：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="ip_addr" class="wideinput" value="" maxlength="16" size="16">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">联系人：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="contractor" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">地址：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="addr" class="wideinput" value="" maxlength="100" size="40">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">邮政编码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="pc" class="wideinput" value="" maxlength="6" size="6">
			</td>
			<td width="20%" nowrap height="19" align="right">电话号码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="telephone" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">传真号码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fax" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">电子邮箱：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="email" class="wideinput" value="" maxlength="50" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">银行帐号：</td>
			<td width="60%" nowrap height="19">
				<input type="text" name="bank_acc_no" class="wideinput" value="" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">商户号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="merchant_id" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">摘要码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="org_id" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">最小金额：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="min_sum_of_tran" class="wideinput" value="" maxlength="10" size="10">*
			</td>
			<td width="20%" nowrap height="19" align="right">最大金额：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="max_sum_of_tran" class="wideinput" value="" maxlength="10" size="10">
				*
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
			<td width="20%" nowrap align="right">签到标志：</td>
			<td width="30%" nowrap>
				<select name="sign_flag" size="1">
				<option value="">请选择</option>
				<%
					initList("SignFlag", "", request, out);
				%>
				</select>*
			</td>
			<td width="20%" nowrap align="right">启用标志：</td>
			<td width="30%" nowrap>
				<select name="use_flag" size="1">
				<option value="">请选择</option>
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
				<input type="submit" name="submitbutton" value="提交" class="inputbutton">
				<input type="reset" name="cancelbutton" value="重置" class="inputbutton"  >
				</div>
			</td>
		</tr>
	</table>
	<p />
</form>
</body>
</html>
