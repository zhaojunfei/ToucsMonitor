<%@ page language="java" contentType="text/html; charset=GBK" %>
<!--JSP����:��ʼ���б��-->
<html>
<!--ҳ��У��-->
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	return true;
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
	String uid=request.getParameter("uid");
%>
<form name="formQuery" method="post" action="/ToucsMonitor/actdevice?reqCode=17704&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">�����豸��ѯ</font>
	<table width="80%"   cellpadding="0" cellspacing="0">
    	<tr>
    		<td colspan="4"><hr noshade></td>   
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�̻��ţ�</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="merchant_id" class="wideinput" value="" maxlength="15"  size="15" >
      		</td>
    	</tr>
	    <tr>
      		<td colspan="4"><hr noshade></td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<div align="right">
          		<input type="submit" name="Submit32" value="ȷ��" class="inputbutton">
        		</div>
      		</td>
    	</tr>
	</table>
</form>
</body>
</html>