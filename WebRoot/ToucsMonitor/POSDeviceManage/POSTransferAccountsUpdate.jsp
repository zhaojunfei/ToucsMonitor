<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
	POSTransferAccountsInfo Info=(POSTransferAccountsInfo)request.getAttribute("posTransferAccountsInfo");
	if (Info == null){
		Info = new POSTransferAccountsInfo();
	}
	String uid=request.getParameter("uid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    	<title>My JSP 'POSTransferAccountsUpdate.jsp' starting page</title>
		<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
		<link rel="stylesheet" href="/v5_css.css" type="text/css">
		<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
  	</head>
<script language="javascript">

function doSubmit(){

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
<form name="formReg" method="post" action="/ToucsMonitor/posTransferAccounts?reqCode=14502&uid=<%=uid%>" >
<font face="隶书" size="+2">理财pos转账金额修改</font>
	<table id="tab0" width="90%" height="100">
		<tr>
	    	<td colspan="4" nowrap> <hr noshade> </td>
	    </tr>
	    <tr>
	      	<td width="20%" nowrap height="19" align="right" >转账金额上限：</td>
	     	<td width="60%" nowrap height="19"> 
	     		<input type="text" name="para_val"  value="<%=Info.getParaVal() %>"   maxlength="20"  size="20" >元
	     	</td>
	    </tr>
		<tr>
	    	<td width="20%" nowrap height="19" align="right">转账金额下限：</td>
	    	<td width="60%" nowrap height="19" >
	       		<input type="text" name="pre_val"  value="<%=Info.getPreVal() %>"   maxlength="20" size="20" >元
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
          		<input type="submit" name="submitbutton" value="提交" class="inputbutton" onclick="return doSubmit()">
          		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>