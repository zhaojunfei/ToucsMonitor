<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.net.*" %>
<%BankCodeBean code=(BankCodeBean)request.getAttribute("code");%>
<html>
<head>
	<title>�������Ĵ������</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
<script language="JavaScript">
function  doSubmit(){
	if( isNull("bankCode","�������Ĵ���")) return false;
	if( isNull("bankName","������������")) return false;
	if( !checkLength("bankMemo",100,"��ע")) return false;	
	return confirm("ȷ�����������ύ��");
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String post=request.getParameter("post");
	String uid=request.getParameter("uid");
	String isMod=request.getParameter("isMod");
%>
<h2><font face="����">�������Ĵ������</font></h2>
<hr align="left">
<form name="formReg" method="post" action="/ToucsMonitor/bankcodeman?reqCode=<%=post%>&uid=<%=uid%>"  onSubmit="javascript:return doSubmit()">
	<table width="100%">
    	<tr> 
      		<td width="13%" align="right"> �������Ĵ��룺</td>
      		<td width="18%">
      			<input type="text" name="bankCode" value="<%=code.getBankCode()%>" <%=(isMod.equals("true"))?"readonly class=\"readonly\"":"class=\"wideinput\""%>  maxlength="20" size="20"></td>
      		<td width="69%"></td>
    	</tr>
    	<tr > 
      		<td width="13%" align="right">�����������ƣ�</td>
      		<td width="18%">
      			<input type="text" name="bankName" value="<%=code.getBankName()%>" class="wideinput" maxlength="50" size="20"></td>
      		<td width="69%">��</td>
    	</tr>
    	<tr> 
      		<td width="13%" valign="top" align="right"> ��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ע��</td>
      		<td colspan="2"><textarea name="bankMemo" rows="1" cols="20"><%=code.getBankMemo()%></textarea>
      		</td>
    	</tr>
  	</table>
<hr align="left">    
  	<table width="75%">
    	<tr> 
      		<td> 
        		<div align="left">		  
          		<input type="submit" name="save" value="����" class="inputbutton">          
          		<input type="button" name="cancel" value="ȡ��" onClick=history.back() class="inputbutton">
        		</div>
      		</td>
    	</tr>
  	</table>
<p>��</p>
</form>
</body>
</html>