<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
	<title>��ѯ���ս�����ˮ����</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script>
function CHK(){
	var atmcode = document.form1.atmcode.value;
    if(atmcode ==""){
    	alert("������ATM��ţ�");
      	document.form1.atmcode.focus();
      	return false;
    }
    return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="����">��ѯ���ս�����ˮ</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/TranDetailQuery" onsubmit="return CHK();">
  	<table width="75%" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" height="30"><font size="2">ATM�����ţ�</font></td>
      		<td width="73%" height="30"> 
        		<input type="text" name="atmcode" value="A5110" size="15">
        		<input type="hidden" name="reqCode" value="18001">
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
          		<input type="submit" name="Submit3" value="��ѯ" class="inputbutton">
          		<input type="reset" name="Submit22" value="ȡ��" class="inputbutton" onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>