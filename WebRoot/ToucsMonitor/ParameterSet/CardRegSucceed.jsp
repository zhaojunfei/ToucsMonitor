<%@ page contentType="text/html; charset=GBK" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
	if(obj1!=null)msg=(String)obj1;
  	String REGCARD="16001";
  	String MODCARD="16002";
  	String MODCARDREQ1="1600201";
  	String MODCARDREQ2="1600202";
  	String DELCARD="16003";
  	String QUERYCARD="16004";
  	String CANCEL="CANCEL";
	String uid=request.getParameter("uid");	
%>		
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<body bgcolor="#CCCCCC" text="#000000">
<p><%=msg%></p>
<form name="form1" method="post" action="">
	<p>
  		<a href="/ToucsMonitor/parametersetservlet?reqCode=<%=REGCARD%>&&uid=<%=uid%>">���</a> 
  	 	<a href="/ToucsMonitor/parametersetservlet?reqCode=<%=QUERYCARD%>&&uid=<%=uid%>">��ѯ</a>
  	</p>
</form>
<p>&nbsp; </p>
</body>
</html>
