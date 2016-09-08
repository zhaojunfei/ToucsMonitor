<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
POSAccount posAccount=(POSAccount)request.getAttribute("posAccount");
if (posAccount == null)
{
	posAccount = new POSAccount();
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
	if( isNull("account","帐号")) return false;
	if (document.forms[0].account.value.length < 19)
	{
		alert("账号应大于19位");
		return false;
	}
	if (document.forms[0].end_date.value.length != 8)
	{
		alert("终止日期必须为8位");
		return false;
	}
	
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}

</script>
<body bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/posaccountconfig?reqCode=13702&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
  <input type="hidden"  name="key" value="<%=posAccount.getAccount()%>">
<font face="隶书" size="+2">对公帐户修改</font>
  <table id="tab0" width="90%" height="271">
     <tr>
      <td width="20%" nowrap height="19" align="right">帐    号：</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="account" class="wideinput"  value="<%=posAccount.getAccount() %>" READONLY style="background-color:#cccccc" MAXLENGTH="20" size="20">*</td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">单位名称：</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="unit_name" class="wideinput"  value="<%=posAccount.getUnitName() %>" MAXLENGTH="40" size="40">*</td>
      </tr>
     <tr>
      <td width="20%" nowrap height="19" align="right">终止日期：</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="end_date" class="wideinput"  value="<%=posAccount.getEndDate() %>" READONLY style="background-color:#cccccc" MAXLENGTH="8" size="8">*</td>
      </tr>
     <tr>
      <td width="20%" nowrap height="19" align="right">授权有效时间：</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="auth_days" class="wideinput"  value="<%=posAccount.getAuthDays() %>" READONLY style="background-color:#cccccc" MAXLENGTH="3" size="3">天</td>
      </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">备注1：</td>
      <td nowrap height="19">
        <input type="text" name="memo1" value="<%=posAccount.getMemo1() %>" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">备注2：</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo2" value="<%=posAccount.getMemo2() %>" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">备注3：</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo3" value="<%=posAccount.getMemo3() %>" MAXLENGTH="100" size="50">
      </td>
    </tr>
</table>
  <table width="80%">
    <tr>
      <td colspan="5" nowrap>
        <div align="right">
          <input type="submit" name="submitbutton" value="提交" class="inputbutton">
          <input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
        </div>
      </td>
    </tr>
  </table>
  <p/>
</form>
</body>
</html>