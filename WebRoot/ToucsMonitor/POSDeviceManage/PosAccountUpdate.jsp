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
	if( isNull("account","�ʺ�")) return false;
	if (document.forms[0].account.value.length < 19)
	{
		alert("�˺�Ӧ����19λ");
		return false;
	}
	if (document.forms[0].end_date.value.length != 8)
	{
		alert("��ֹ���ڱ���Ϊ8λ");
		return false;
	}
	
	if(confirm("ȷ�����������ύ��"))
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
<font face="����" size="+2">�Թ��ʻ��޸�</font>
  <table id="tab0" width="90%" height="271">
     <tr>
      <td width="20%" nowrap height="19" align="right">��    �ţ�</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="account" class="wideinput"  value="<%=posAccount.getAccount() %>" READONLY style="background-color:#cccccc" MAXLENGTH="20" size="20">*</td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">��λ���ƣ�</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="unit_name" class="wideinput"  value="<%=posAccount.getUnitName() %>" MAXLENGTH="40" size="40">*</td>
      </tr>
     <tr>
      <td width="20%" nowrap height="19" align="right">��ֹ���ڣ�</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="end_date" class="wideinput"  value="<%=posAccount.getEndDate() %>" READONLY style="background-color:#cccccc" MAXLENGTH="8" size="8">*</td>
      </tr>
     <tr>
      <td width="20%" nowrap height="19" align="right">��Ȩ��Чʱ�䣺</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="auth_days" class="wideinput"  value="<%=posAccount.getAuthDays() %>" READONLY style="background-color:#cccccc" MAXLENGTH="3" size="3">��</td>
      </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">��ע1��</td>
      <td nowrap height="19">
        <input type="text" name="memo1" value="<%=posAccount.getMemo1() %>" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">��ע2��</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo2" value="<%=posAccount.getMemo2() %>" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right">��ע3��</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo3" value="<%=posAccount.getMemo3() %>" MAXLENGTH="100" size="50">
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