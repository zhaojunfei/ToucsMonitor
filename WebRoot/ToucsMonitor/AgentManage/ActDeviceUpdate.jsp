<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.AgentManage.*" %>

<%
ActDeviceInfo Info=(ActDeviceInfo)request.getAttribute("ActInfo");
if (Info == null){
	Info = new ActDeviceInfo();
}
%>

<html>
<script language="javascript">

function doSubmit(){
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

<body   bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/actdevice?reqCode=17703&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">代理设备修改</font>
	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >商户号：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="merchant_id" class="readonly" value="<%=Info.getMerchantid()%>" READONLY style="background-color:#cccccc" maxlength="15"  size="15" >
      		</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">POS设备号：</td>
     		<td width="60%" nowrap height="19" >
       			<input type="text" name="equip_id" class="readonly" value="<%=Info.getEquipid()%>" READONLY style="background-color:#cccccc" maxlength="19" size="19" >
     		</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注1：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo1" class="wideinput" value="<%=Info.getMemo1()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注2：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo2" class="wideinput" value="<%=Info.getMemo2()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注3：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo3" class="wideinput" value="<%=Info.getMemo3()%>"  maxlength="40" size="40" >
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
          		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
  <p/>
</form>
</body>
</html>