<%@ page contentType="text/html; charset=GBK" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
	if(obj1!=null)msg=(String)obj1;
  	String REGCARD="17001";
  	String MODCARD="17002";
  	String MODCARDREQ1="1700201";
  	String MODCARDREQ2="1700202";
  	String DELCARD="17003";
  	String QUERYCARD="17004";
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
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet?reqCode=<%=QUERYCARD%>&&uid=<%=uid%>">
<!--   
<p><a href="/ToucsMonitor/bfCardservlet?reqCode=<%=REGCARD%>&&uid=<%=uid%>">���</a> <a href="/ToucsMonitor/bfCardservlet?reqCode=<%=QUERYCARD%>&&uid=<%=uid%>">��ѯ</a></p>
 -->
	<input type="submit" name="submit" value="ȷ��">
</form>
<p>&nbsp; </p>
</body>
</html>
