<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
POSEnrollInfo posEnroll=(POSEnrollInfo)request.getAttribute("posEnroll");
System.out.println("RRRRRRRRRRRRRRRRR");
if (posEnroll == null)
{
System.out.println("RRRRRRRRRRRRRRRRR");
	posEnroll = new POSEnrollInfo();
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
	if (document.forms[0].account.value.length < 20)
	{
		alert("�˺�Ӧ����19λ");
		return false;
	}
	
	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}

</script>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/posaccountconfig?reqCode=13701&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">�Թ��ʻ�����</font>
<hr noshade>
  <table id="tab0" width="90%" height="271" >
     <tr>
      <td width="5%" nowrap height="19" align="right">��    �ţ�</td>
      <td width="80%" nowrap height="19" >
        <input type="text" name="account" class="wideinput"  value="" MAXLENGTH="20" size="20">*</td>
		</tr>
		<tr >
		  <td width="20%" nowrap height="19" align="right">��λ���ƣ�</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="unit_name" class="wideinput"  value="" MAXLENGTH="40" size="40"></td>
      </tr>
     <tr >
      <td width="20%" nowrap height="19" align="right">��ֹ���ڣ�</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="end_date" class="wideinput" value="" READONLY style="background-color:#cccccc"  value="" MAXLENGTH="8" size="8"></td>
      </tr>
     <tr >
      <td width="20%" nowrap height="19" align="right">��Ȩ��Чʱ�䣺</td>
      <td width="20%" nowrap height="19">
        <input type="text" name="auth_days" class="wideinput" value="" READONLY style="background-color:#cccccc" MAXLENGTH="3" size="3">��</td>
      </tr>
    <tr >
      <td width="20%" nowrap height="19" align="right">��ע1��</td>
      <td nowrap height="19">
        <input type="text" name="memo1" value="" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr >
      <td width="20%" nowrap height="19" align="right">��ע2��</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo2" value="" MAXLENGTH="100" size="50">
      </td>
    </tr>
    <tr >
      <td width="20%" nowrap height="19" align="right">��ע3��</td>
      <td colspan="3" nowrap height="19">
        <input type="text" name="memo3" value="" MAXLENGTH="100" size="50">
      </td>
    </tr>
</table>
<hr noshade>
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
  <p/>
</form>
</body>
</html>