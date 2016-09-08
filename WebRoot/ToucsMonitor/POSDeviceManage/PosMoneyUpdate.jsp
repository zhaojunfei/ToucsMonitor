<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%
POSMoneyInfo posMoney=(POSMoneyInfo)request.getAttribute("posMoney");
if (posMoney == null){
	posMoney = new POSMoneyInfo();
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
	if( isNull("pos_dcc_code","19位POS编号")) return false;
	if( isNull("branch_id","9位机构号")) return false;
	if( isNull("teller_id","12位柜员号")) return false;
	if( isNull("start_date","8位起始日期")) return false;
	if( isNull("end_date","8位终止日期")) return false;
	if (document.forms[0].pos_dcc_code.value.length != 19){
		alert("POS设备编号必须为19位");
		return false;
	}
	if (document.forms[0].branch_id.value.length != 9){
		alert("机构号必须为9位");
		return false;
	}
	if (document.forms[0].teller_id.value.length != 12){
		alert("柜员号必须为12位");
		return false;
	}
	if (document.forms[0].start_date.value.length != 8){
		alert("起始日期必须为8位");
		return false;
	}
	if (document.forms[0].end_date.value.length != 8){
		alert("终止日期必须为8位");
		return false;
	}
	
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}

</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/posMoney?reqCode=14202&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden"  name="key" value="<%=posMoney.getPos_code() %>">
<font face="隶书" size="+2">理财POS设备信息修改</font>
<hr noshade>
	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >POS顺序号：</td>
      		<td width="20%" nowrap height="19">
      			<input type="text" class="readonly" value="<%=posMoney.getPos_code() %>"  READONLY style="background-color:#cccccc" name="pos_code" maxlength="9"  size="9"></td>
      		<td width="20%" nowrap height="19" align="right">POS终端号：</td>
      		<td width="20%" nowrap height="19">
       			<input type="text" class="readonly" value="<%=posMoney.getPos_c_code() %>"  READONLY style="background-color:#cccccc" name="pos_c_code" maxlength="9"  size="8"></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >19位POS编号：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" class="wideinput" value="<%=posMoney.getPos_dcc_code() %>" READONLY style="background-color:#cccccc" name="pos_dcc_code" maxlength="19"  size="20" >
      		*</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">商户编号：</td>
     		<td width="60%" nowrap height="19">
       			<input type="text" class="readonly" value="<%=posMoney.getMerchant_id() %>"  READONLY style="background-color:#cccccc" name="merchant_id"  size=20 MAXLENGTH=15 >
       		</td>
		</tr>
     	<tr>
      		<td width="20%" nowrap height="19" align="right">机构号：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="branch_id" class="wideinput"  value="<%=posMoney.getBranch_id() %>" MAXLENGTH="9" size="20">*</td>
      		<td width="20%" nowrap height="19" align="right">柜员号：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="teller_id" class="wideinput"  value="<%=posMoney.getTeller_id() %>" MAXLENGTH="12" size="20">*</td>
      	</tr>
     	<tr>
      		<td width="20%" nowrap height="19" align="right">起始日期：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="start_date" class="wideinput"  value="<%=posMoney.getStart_date() %>" MAXLENGTH="8" size="8">*</td>
      		<td width="20%" nowrap height="19" align="right">终止日期：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="end_date" class="wideinput"  value="<%=posMoney.getEnd_date() %>" MAXLENGTH="8" size="8">*</td>
      	</tr>
     	<tr>
      		<td width="20%" nowrap height="19" align="right">起始时间：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="start_time" class="wideinput"  value="<%=posMoney.getStart_time() %>" MAXLENGTH="6" size="6"></td>
      		<td width="20%" nowrap height="19" align="right">终止时间：</td>
      		<td width="20%" nowrap height="19">
        		<input type="text" name="end_time" class="wideinput"  value="<%=posMoney.getEnd_time() %>" MAXLENGTH="6" size="6"></td>
      	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right">备注1：</td>
      		<td nowrap height="19">
        		<input type="text" name="memo1" value="<%=posMoney.getMemo1() %>" MAXLENGTH="100" size="50">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right">备注2：</td>
      		<td colspan="3" nowrap height="19">
        		<input type="text" name="memo2" value="<%=posMoney.getMemo2() %>" MAXLENGTH="100" size="50">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right">备注3：</td>
      		<td colspan="3" nowrap height="19">
        		<input type="text" name="memo3" value="<%=posMoney.getMemo3() %>" MAXLENGTH="100" size="50">
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