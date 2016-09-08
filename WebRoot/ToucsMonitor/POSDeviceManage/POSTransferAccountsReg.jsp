<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String uid = request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   	<title>My JSP 'POSTransferAccountsReg.jsp' starting page</title>
   	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script>
function check(){

	var Regs = /^\d+\.{0,1}\d*$/;//正数 
	var para1 = document.getElementById("para_val").value;
	var para2 = document.getElementById("pre_val").value;

	if(para1 == null || para1 == ""){
		alert("转账金额上限不能为空,请重新输入!!!!")
		return false;
	}
	
	if(para2 == null || para2 == ""){
		alert("转账金额下限不能为空,请重新输入 !!!!");
		return false;
	}
	
	if(!Regs.exec(para1)||!Regs.exec(para2)){
		alert("请填写正确的转账金额 !!!");
		return false;
	}

	return true;
}
</script>
<body>
<form name="formReg" method="post" action="/ToucsMonitor/posTransferAccounts?reqCode=14501&uid=<%=uid%>">
<font face="隶书" size="+2">理财pos转账金额新增</font>
	<table id="tab0" width="90%" height="100" border="0">
   		<tr>
     		<td colspan="4" nowrap><hr noshade></td>
   		</tr>
   		<tr>
    		<td width="20%" nowrap height="19" align="right" >转账金额上限:</td>
    		<td width="60%" nowrap height="19">
     			<input type="text" id="para_val" name="para_val" class="wideinput" value="" maxlength="19"  size="19" >元
     		</td>
   		</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right" >转账金额下限:</td>
    		<td width="60%" nowrap height="19">
     			<input type="text" id="pre_val" name="pre_val" class="wideinput" value="" maxlength="19"  size="19" >元
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
         		<input type="submit" name="submitbutton" value="提交" class="inputbutton" onclick="return check()">
         		<input type="reset" name="cancelbutton" value="重置" class="inputbutton"  >
       		</div>
     		</td>
   		</tr>
 	</table>
<p/>
</form>
</body>
</html>
