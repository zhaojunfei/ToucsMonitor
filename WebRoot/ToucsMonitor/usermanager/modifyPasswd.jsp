<%@ page contentType = "text/html; charset=gb2312"%>
<html>
<head>
	<title>修改密码……</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script src="/ToucsMonitor/ToucsMonitor/js/Check.js"></script>
<script>
function check(){
	var oldpwd = document.getElementById("userpwd");
	var newpwd = document.getElementById("confirm");
	var reg1 = /^[0-9a-zA-Z]*$/;
		
	if(oldpwd.value == null || oldpwd.value == ""){
		alert("密码不能为空!");
		return false;		
	}
	
	//验证口令的组成
	if (!reg1.test(oldpwd.value)){	
		alert("口令只能由字母、数字组成！ ");
		return false;
	}
	//验证口令的长度
	if(oldpwd.value.length<8||oldpwd.value.length>16){
		alert("口令的长度只能是8-16位!");
		return false;
	}
	//验证uisepwd和confirm是否一致
	if(oldpwd.value != newpwd.value){
		alert("2次输入的密码不一致,请重新输入");
		return false;
	}

	return true;
}
</script>
<body  bgcolor="#CCCCCC">
<h2><font face="隶书">修改密码</font></h2>
<hr align="left" noshade width="510">
<form name="form1" method="post" action="/ToucsMonitor/usermanagerservlet">  
	<table width="506" border="0" cellspacing="0" cellpadding="0" class="userinfo" bgcolor="#CCCCCC">
    	<tr> 
      		<td width="159" height="35"><font size="2">请输入旧密码：</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="hidden" name="oper_type" value="19001">
        		<input type="password" name="oldpasswd" size="12" ></font>
        	</td>
    	</tr>
    	<tr> 
      		<td width="159" height="35"><font size="2">请输入新密码：</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="password" id="userpwd" name="userpwd" size="12"   ></font>
        		<font color = "FF0000">*</font>
        	</td>
    	</tr>
    	<tr> 
      		<td width="159" height="35"><font size="2">请确认新密码：</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="password" id="confirm" name="confirm"  size="12" ></font>
        		<font color = "FF0000">*</font>
        	</td>
    	</tr>
       	<tr> 
      		<td width="159" height="35"><font size="2">有效期：</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<select name="usedate">
        		<option value="30">30</option>
        		<option value="60">60</option>
        		<option value="90">90</option>
        		</select></font>
        		<font color = "FF0000">*</font>
        	</td>
    	</tr>
  	</table>
<hr align="left" noshade width="510">
  	<table width="50%" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td> 
        		<div align="right"><font size="2"> 
          		<input type="submit" name="Submit" value="修改"  class="inputbutton" onClick = "return check()">
          		<input type="reset" name="Reset" value="取消"  class="inputbutton" onclick="history.go(-1);"></font>
          		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>