<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
	POSInAccountInfo Info=(POSInAccountInfo)request.getAttribute("InAccountInfo");
	if (Info == null){
		Info = new POSInAccountInfo();
	}
	String uid=request.getParameter("uid");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>���pos�ڲ��˻�����</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
	<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
  </head>
<script language="javascript">

function check(){
	var Regs = /^[0-9]\d*$/;//���� 
	var para1 = document.getElementById("para_val").value;
	var para2 = document.getElementById("pre_val").value;
	//alert(para2);
	if(para1 == null || para1 == ""){
		alert("�������ڲ��˻�����Ϊ��,����������!!!!")
		return false;
	}
	
	if(para2 == null || para2 == ""){
		alert("�����ڲ��˻�����Ϊ��,���������� !!!!");
		return false;
	}

	if(!Regs.exec(para1)||!Regs.exec(para2)){
		alert("�ڲ��˻�ֻ��������,��������ȷ���ڲ��˻� !!!");	
		return false;
	}
	
	return true;
}
</script>
<body bgcolor="#CCCCCC" text="#000000">
<form name="formReg" method="post" action="/ToucsMonitor/posInAccount?reqCode=14302&uid=<%=uid%>" >

<font face="����" size="+2">���pos�ڲ��˻��޸�</font>
	<table id="tab0" width="90%" height="100">
    	<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�������ڲ��˻���</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="para_val"  value="<%=Info.getParaVal() %>"   maxlength="30"  size="30" >
      		</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">�����ڲ��˻���</td>
     		<td width="60%" nowrap height="19" >
       			<input type="text" name="pre_val"  value="<%=Info.getPreVal() %>"   maxlength="30" size="30" >
     		</td>
		</tr>
		<tr>
      		<td colspan="4" nowrap><hr noshade></td>
	</table>
  	<table width="80%">
   		<tr>
      		<td colspan="5" nowrap>
        	<div align="right">
          		<input type="submit" name="submitbutton" value="�ύ" class="inputbutton" onclick="return check()">
          		<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>
