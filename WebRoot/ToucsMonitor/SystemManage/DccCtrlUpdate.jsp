<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%
	DccCtrlInfo info = (DccCtrlInfo) request.getAttribute("DccCtrl");
	if (info == null) {
		info = new DccCtrlInfo();
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
				out.println("<option value=\"" + code.getCodeId() + "\"" + flag + ">" + code.getCodeDesc() + "</option>");
			} catch (IOException exp) {
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		}
	}
}
%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if( isNull("channel_id","��������")) return false;
	if( isNull("service","ҵ������")) return false;

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
<form name="formReg" method="post" action="/ToucsMonitor/dccctrl?reqCode=17803&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">������Ϣ�޸�</font>
	<table id="tab0" width="90%" height="271">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap align="right">�������ţ�</td>
			<td width="33%" nowrap>
				<select name="channel_id"  size="1">
					<option value="">��ѡ��</option>
					<%
						initList("ChannelId", info.getChannel_id(), request, out);
					%>
				</select>*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap align="right">ҵ�����ࣺ</td>
			<td width="33%" nowrap>
				<select name="service" ��size="1">
					<option value="">��ѡ��</option>
					<%
						initList("ServiceKind", info.getService(), request, out);
					%>
				</select>*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">�����޶</td>
			<td width="33%" nowrap height="19">
				<input type="text" name="trans_amt" class="wideinput" value="<%=info.getTrans_amt()%>" maxlength="15" size="15">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�ۼ��޶</td>
			<td width="60%" nowrap height="19">
				<input type="text" name="total_amt" class="wideinput" value="<%=info.getTotal_amt()%>" maxlength="15" size="15">
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ע1��</td>
			<td width="60%" nowrap height="19">
				<input type="text" name="memo1" class="wideinput" value="" maxlength="40" size="40">
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
	</table>
	<table width="80%">
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
					<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
					<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"
						onClick="javascript:location.href='/ToucsMonitor/dccctrl?reqCode=17804&target=page'">
				</div>
			</td>
		</tr>
	</table>
<p />
</form>
</body>
</html>