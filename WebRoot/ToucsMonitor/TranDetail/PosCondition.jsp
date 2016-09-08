<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
	<title>查询POS当日交易流水</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script>
function CHK(){
	var poscode = document.form1.poscode.value;
    if(poscode ==""){
    	alert("请输入POS编号！");
    	document.form1.poscode.focus();
    	return false;
    }
    return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">查询POS当日交易流水</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/TranDetailQuery" onsubmit="return CHK();">
  	<table width="75%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr>
        	<td width="20%"> 选择编号种类：</td>
           	<td width="80%" height="20">
          		<input type=radio name="pos_kind" value="1" style="width:20px" >POS-P编号
            	<input type=radio name="pos_kind" value="2" style="width:20px" >POS-C编号
            	<input type=radio name="pos_kind" value="3" style="width:20px"  checked>POS-DCC编号</td>
    	</tr>
    	<tr>
      		<td width="20%" height="30"><font size="2">POS设备编号：</font></td>
      		<td width="80%" height="30">
        		<input type="text" name="poscode" value="" size="15">
        		<input type="hidden" name="reqCode" value="18101">
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2" height="30">
        		<div align="right">
          		<input type="submit" name="Submit3" value="查询" class="inputbutton">
				</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>