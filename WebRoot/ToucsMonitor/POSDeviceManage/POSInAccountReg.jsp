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
    	<title>'POSInAccountReg.jsp'</title>
    	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
		<link rel="stylesheet" href="/v5_css.css" type="text/css">
  </head>
<script>
	function check(){
		var Regs = /^[0-9]\d*$/;//正数 
		var para1 = document.getElementById("para_val").value;
		var para2 = document.getElementById("pre_val").value;
		if(para1 == null || para1 == ""){
			alert("手续费内部账户不能为空,请重新输入!!!!")
			return false;
		}
		if(para2 == null || para2 == ""){
			alert("清算内部账户不能为空,请重新输入 !!!!");
			return false;
		}
		if(!Regs.exec(para1)||!Regs.exec(para2)){
			alert("内部账户只能是数字,请输入正确的内部账户 !!!");	
			return false;
		}
		return true;
	}
</script>
<body>
<form name="formReg" method="post" action="/ToucsMonitor/posInAccount?reqCode=14301&uid=<%=uid%>">
<font face="隶书" size="+2">理财pos内部账户新增</font>
	<table id="tab0" width="90%" height="100" border="0">
    	<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >手续费内部账户：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" id="para_val" name="para_val" class="wideinput" value="" maxlength="30"  size="30" >
      		*</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >清算内部账户：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" id="pre_val" name="pre_val" class="wideinput" value="" maxlength="30"  size="30" >
      		*</td>
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
