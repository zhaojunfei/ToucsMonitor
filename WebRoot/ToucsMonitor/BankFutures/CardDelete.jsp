<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language=javascript>

function submit11(type){
	if(type==1){	
		document.all("RequstCoce").value = "10003";			
		document.form1.action="/ToucsMonitor/bfCardservlet";
	}else if(type==2){
		//document.all("RequstCoce").value = "10004";			
		document.form1.action="/ToucsMonitor/ToucsMonitor/BankFutures/CardQuery.jsp";
	}	
	document.form1.submit();
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<form name="form1" method="post" action="">
	<p> ���ֱ�ʾ�� 
    	<input type="text" name="CardTypeID">
  	</p>
  	<p> 
    	<input type="hidden" name="RequstCoce" value="">
    	<input type="submit" name="Submit" value="ɾ������ʾ��Ϣ" onClick="submit11(1); return false;">
   	 	<input type="submit" name="Submit2" value="��ѯ������Ϣ" onClick="submit11(2); return false;">
  	</p>
</form>
</body>
</html>
