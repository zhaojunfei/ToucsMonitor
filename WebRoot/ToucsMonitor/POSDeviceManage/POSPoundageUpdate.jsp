<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
POSPoundageInfo Info=(POSPoundageInfo)request.getAttribute("poundageInfo");
if (Info == null){
	Info = new POSPoundageInfo();
}
	String uid=request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   	<title>My JSP 'POSPoundageUpdate.jsp' starting page</title>
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
	<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
</head>
<script>
function check(){
	var Regs = /^\d+\.{0,1}\d*$/;//���� 
	var para1 = document.getElementById("para_val").value;
	var para2 = document.getElementById("pre_val").value;

	if(para1 == null || para1 == ""){
		alert("�����ѱ��� ����Ϊ��,����������!!!!")
		return false;
	}

	if(para2 == null || para2 == ""){
		alert("���������� ����Ϊ��!!!!");
		return false;
	}
	
	if(!Regs.exec(para1)||!Regs.exec(para2)){
		alert("����д��ȷ��������");
		return false;
	}
	
	return true;
}
</script>
<body>
<form name="formReg" method="post" action="/ToucsMonitor/posPoundage?reqCode=14602&uid=<%=uid%>">
<font face="����" size="+2">���pos��������Ϣ�޸�</font>
	<table id="tab0" width="90%" height="100">
    	<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�����ѱ���:</td>
      		<td width="60%" nowrap height="19" >
      			<input type="text" name="para_val"  value="<%=Info.getParaVal() %>"   maxlength="20"  size="20" >
      		%</td>
    	</tr>
		<tr>
	  		<td width="20%" nowrap height="19" align="right">����������:</td>
	  		<td width="60%" nowrap height="19" >
	     		<input type="text" name="pre_val"  value="<%=Info.getPreVal() %>"   maxlength="20" size="20" > Ԫ
	     	</td>
		</tr>
		<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
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