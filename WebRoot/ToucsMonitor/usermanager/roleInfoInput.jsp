<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>角色信息</title>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script>
function ChkData(){
	var rolename = document.form1.rolename.value;
  	if(rolename == ""){
    	alert("请输入角色名称！");
    	document.form1.rolename.focus();
    	return false;
  	}
  	return true;
}
</script>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">角色管理</font></h2>
<hr align="left" noshade>
<form name="form1" method="post" action="/ToucsMonitor/roleManager" onsubmit="return ChkData();">
	<table width="529" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="107"><font size="2">角色名称：</font></td>
      		<td height="25" width="422"> <font size="2"> 
        		<input type="text" name="rolename" size="12">
        		<input type="hidden" name="txcode" value="10301"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">角色描述：</font></td>
      		<td height="25" width="422"><font size="2"> 
        		<textarea name="roledesc" cols="30" rows="3"></textarea></font>
        	</td>
    	</tr>
    	<tr valign="bottom"> 
      		<td colspan="2"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr valign="bottom">
      		<td height="35" colspan="2">
        	<div align="right"><font size="2"> 
          		<input type="submit" name="Submit" value="确定" class="inputbutton">
          		<input type="reset" name="Submit2" value="取消" class="inputbutton" onclick="history.go(-1);"></font>
          	</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>