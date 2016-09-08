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
	if( isNull("fc_id","烟草区县公司代码")) return false;
	if( isNull("investor_id","卡号")) return false;
	if( isNull("name","客户姓名")) return false;
	if( isNull("type","投资人类型")) return false;
	if( isNull("id_card_type","证件类型")) return false;
	if( isNull("id_card_code","证件号码")) return false;
	if( isNull("level","重要程度")) return false;
	if( isNull("use_flag","有效标志")) return false;

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
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/bfinvestor?reqCode=17911&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">烟草公司客户新增</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap><hr noshade></td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">烟草区县公司代码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fc_id" class="wideinput" value="" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap height="19" align="right">卡号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="investor_id" class="wideinput" value="" maxlength="30" size="30">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">客户姓名：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="name" class="wideinput" value="" maxlength="40" size="40">*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">身份证号码：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="id_card_code" class="wideinput" value="" maxlength="30" size="30">*
			</td>
			<td width="20%" nowrap>
				<input type="hidden" name="type" class="wideinput" value="0" maxlength="1" size="1">
			</td>
			<td width="30%" nowrap>
				<input type="hidden" name="id_card_type" class="wideinput" value="0" maxlength="1" size="1">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">烟草客户号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="deposit_acc_no" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap>
				<input type="hidden" name="level" class="wideinput" value="0" maxlength="1" size="1">
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
			<td width="20%" nowrap height="19" align="right">对公账号转账上线：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="telephone" class="wideinput" value="" maxlength="30" size="30">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">信用卡绑定借记卡卡号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="fax" class="wideinput" value="" maxlength="30" size="30">
			</td>
			<td width="20%" nowrap height="19" align="right">烟草专卖号：</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="email" class="wideinput" value="" maxlength="50" size="30">
			</td>
		</tr>
		<tr>
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