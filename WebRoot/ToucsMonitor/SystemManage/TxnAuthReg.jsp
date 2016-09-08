<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="TxnAuth" class="com.adtec.toucs.monitor.systemmanage.TxnAuthBean" scope="request"/>
<%
	TxnAuthBean auth=(TxnAuthBean)request.getAttribute("auth");
%>
<%! 
private void initList(String listName, String defValue, HttpServletRequest req, JspWriter out){
	List list = (List)req.getAttribute(listName);
	if (defValue != null)
		defValue = defValue.trim();
	Debug.println("RJsp::" + listName + ":" + defValue + "]");
	String flag = "";
	if (list != null) {
		for (int i=0; i<list.size(); i++) {
			CodeBean code = (CodeBean)list.get(i);
			if (defValue != null && defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else 
				flag = "";
			try{
				out.println("<option value=\"" + code.getCodeId() + "\"" + flag + ">" + code.getCodeDesc() + "</option>");
			}catch(IOException exp){
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		}
	}
}
%>

<html>
<head>
	<title>ATM交易权限设置</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function trim(srcString){
	newString = "";
	for(i = 0 ; i < srcString.length ; i++)
		if(srcString.charAt(i) != ' ')
			newString += srcString.charAt(i);
	return newString;
}
function isNull(inputName, msg){
	oldString = eval("document.forms[0]." + inputName + ".value");
	newString = trim(oldString);
	if (oldString == null || newString.length == 0) {
		alert("请选择" + msg + "！");
		eval("document.forms[0]." + inputName+".focus()")
		return true;
	}
	return false;
}

function doSubmit(){
	if (isNull("CardClass", "卡种类")) return false;
	if (isNull("CardType", "卡类型")) return false;	
	return true;
}

function onSelChange(){
	var undefined ;
	if (document.forms[0].CardClass.length == undefined) return ;
	if (document.forms[0].CardType.length == undefined) return;

	var cardClass = document.forms[0].CardClass.value;
	var cardType = document.forms[0].CardType.value;
}

function onSelAll(){
	var i;
	if (document.forms[0].TxnAll.value == "0") {
		for(i=0; i<document.forms[0].TxnAuth.length; i++){
    		document.forms[0].TxnAuth[i].checked = true;
    	}
		document.forms[0].TxnAll.value = "1";
	}else {
		for (i=0; i<document.forms[0].TxnAuth.length; i++){
    		document.forms[0].TxnAuth[i].checked = false;
    	}
		document.forms[0].TxnAll.value = "0";
	}
	return ;
}
function onSelAlll(){
	var i;
	if (document.forms[0].TxnAll.value == "0") {
		for(i=0; i<document.forms[0].TxnAuth.length; i++){
    		document.forms[0].TxnAuth[i].checked = true;
    	}
		document.forms[0].TxnAll.value = "1";
	}else {
		for (i=0; i<document.forms[0].TxnAuth.length; i++){
    		document.forms[0].TxnAuth[i].checked = false;
    	}
		document.forms[0].TxnAll.value = "0";
	}
	return ;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
	String reqCode = request.getParameter("reqCode");
%>
<h2><font face="隶书">ATM交易权限设置</font></h2>
<form name="TxnAuth" method="post" action="/ToucsMonitor/txnauthman?reqCode=<%=reqCode%>&uid=<%=uid%>&SysId=atm" onSubmit="javascript:return doSubmit();">
	<table id="tab0" width="80%">
		<tr>
			<td colspan="4" nowrap><hr noshade></td>
		</tr>
		<tr>
			<td width="15%" nowrap height="19" align="right">卡类型：</td>
			<td width="35%" nowrap height="19">
				<select name="CardType" size="1" onChange=javascript:onSelChange()>
                <option value="">请选择</option>
                <% initList("CardTypeList",auth.getCardType(),request,out);%>
                </select>*
			</td>
			<td width="15%" nowrap align="right">卡种类：</td>
			<td width="35%" nowrap>
				<select name="CardClass" size="1" onChange=javascript:onSelChange() > 
				<option value="">请选择</option>
				<%initList("CardClassList",auth.getCardClass(),request,out);%>
			  	</select>*
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap><hr noshade></td>
		</tr>
	</table>
	<table width="80%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
			<td width="50%">
				<div align="center">交易名称</div>
			</td>
			<td width="50%">
				<div align="center">是否允许<input name="TxnAll" type="checkbox" value="0" onClick=javascript:onSelAll() onChange=javascript:onSelAlll()/> </div> 
				<!--打死我也想不明白为啥一定要同时有onClick和onChange才起作用！！！！！！-->
			</td>
		</tr>
		<%
			int i, x, t;
			String s = auth.getTxnAuth();
			Vector v = auth.getTxnList();
			Debug.println("Rjsp::"+s);
			if (s == null)
				s = "0000000000000000000000000000000000000000000000000000000000000000"; 
			if (v == null) {
				x = 0;
			} else {
		 		x = (s.length() < v.size()) ? s.length() : v.size();
			}
			Debug.println("Rjsp::"+x);
			for (i=0; i<x; i++){
				TxnCodeBean tcb = (TxnCodeBean) v.get(i);
				t = tcb.getMaskSerial();
		%>
		<tr bgcolor="#FFFFFF" align="center">
			<td width="50%"><%= tcb.getTxnName() %></td>
			<td width="50%"><input type="checkbox" name="TxnAuth" style="width:20px" value="<%=t%>" <%=(s.charAt(t)=='1')?"CHECKED":""%> /></td>
		</tr>
		<%
			}
		%>
	</table>
	<table width="80%">
		<tr>
			<td colspan="5" nowrap><hr noshade></td>
		</tr>
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
					<input type="submit" name="submit" value="提交" class="inputbutton">
					<input type="reset" name="reset" value="重填" class="inputbutton">
					<input type="button" name="cancel" value="取消" class="inputbutton" onClick=history.back()>
				</div>
			</td>
		</tr>
	</table>
<p/>
</form>
</body>
</html>