<%@ page contentType = "text/html; charset=gb2312"%>
<html>
<head>
	<title>�޸����롭��</title>
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
		alert("���벻��Ϊ��!");
		return false;		
	}
	
	//��֤��������
	if (!reg1.test(oldpwd.value)){	
		alert("����ֻ������ĸ��������ɣ� ");
		return false;
	}
	//��֤����ĳ���
	if(oldpwd.value.length<8||oldpwd.value.length>16){
		alert("����ĳ���ֻ����8-16λ!");
		return false;
	}
	//��֤uisepwd��confirm�Ƿ�һ��
	if(oldpwd.value != newpwd.value){
		alert("2����������벻һ��,����������");
		return false;
	}

	return true;
}
</script>
<body  bgcolor="#CCCCCC">
<h2><font face="����">�޸�����</font></h2>
<hr align="left" noshade width="510">
<form name="form1" method="post" action="/ToucsMonitor/usermanagerservlet">  
	<table width="506" border="0" cellspacing="0" cellpadding="0" class="userinfo" bgcolor="#CCCCCC">
    	<tr> 
      		<td width="159" height="35"><font size="2">����������룺</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="hidden" name="oper_type" value="19001">
        		<input type="password" name="oldpasswd" size="12" ></font>
        	</td>
    	</tr>
    	<tr> 
      		<td width="159" height="35"><font size="2">�����������룺</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="password" id="userpwd" name="userpwd" size="12"   ></font>
        		<font color = "FF0000">*</font>
        	</td>
    	</tr>
    	<tr> 
      		<td width="159" height="35"><font size="2">��ȷ�������룺</font></td>
      		<td width="347" height="35"> <font size="2"> 
        		<input type="password" id="confirm" name="confirm"  size="12" ></font>
        		<font color = "FF0000">*</font>
        	</td>
    	</tr>
       	<tr> 
      		<td width="159" height="35"><font size="2">��Ч�ڣ�</font></td>
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
          		<input type="submit" name="Submit" value="�޸�"  class="inputbutton" onClick = "return check()">
          		<input type="reset" name="Reset" value="ȡ��"  class="inputbutton" onclick="history.go(-1);"></font>
          		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>