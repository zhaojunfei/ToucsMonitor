<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.AgentManage.*" %>

<%
ActTellerInfo Info=(ActTellerInfo)request.getAttribute("ActTeller");
if (Info == null){
	Info = new ActTellerInfo();
}
%>


<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function onBranchNameChange(){
	document.forms[0].branch_id.value= document.forms[0].branch_name.value;
}

function doSubmit(){
	if( isNull("branch_id","机构号")) return false;
	if( isNull("teller_id","柜员号")) return false;

	if (document.forms[0].branch_id.value.length != 9)	{
		alert("机构号必须为9位");
		return false;
	}
	if (document.forms[0].teller_id.value.length != 12) {
		alert("柜员号必须为12位");
		return false;
	}
	
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
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
<form name="formReg" method="post" action="/ToucsMonitor/actteller?reqCode=17601&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">代理柜员新增</font>
  	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >机构号：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="branch_id" class="wideinput" value="" maxlength="9"  size="9" >
      		*</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">柜员号：</td>
     		<td width="60%" nowrap height="19" >
       			<input type="text" name="teller_id" class="wideinput" value=""  maxlength="12" size="12" >
     		*</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注1：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo1" class="wideinput" value=""  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注2：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo2" class="wideinput" value=""  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注3：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo3" class="wideinput" value=""  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
	      	<td colspan="4" nowrap>
	        	<hr noshade>
	      	</td>
		</tr>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<div align="right">
          		<input type="submit" name="submitbutton" value="提交" class="inputbutton">
          		<input type="reset" name="cancelbutton" value="重置" class="inputbutton"  >
        		</div>
      		</td>
    	</tr>
  	</table>
  <p/>
</form>
</body>
</html>