<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>queryLog</title>
<script src="js/power.js"></script>
<script src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
<script>
<!--
function sverify(){
  	if(!validFullDate("begindate","��ʼ����"))	return false;
  	if(!validFullDate("enddate","��ֹ����"))	return false;
  	return true;
}
//-->
</script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h2><font face="����">��ѯϵͳ��־��Ϣ</font></h2>
<form name="form1" method="post" action="/ToucsMonitor/logManager" onsubmit="return sverify();">
	<table width="75%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td colspan="2" height="30">
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" height="30"><font size="2">��ʼ���ڣ�</font></td>
      		<td width="66%" height="30">
        		<input type="text" name="begindate" size="10">(YYYYMMDD)
        		<input type="hidden" name="txcode" value="14001">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" height="30"><font size="2">��ֹ���ڣ�</font></td>
      		<td width="66%" height="30">
        		<input type="text" name="enddate" size="10">(YYYYMMDD)
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2" height="30">
        		<div align="right">
          		<hr align="left" noshade>
        		</div>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2" height="30">
        		<div align="right">
          		<input type="submit" name="Submit" value="��ѯ" class="inputbutton">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>