<%@ page language="java" contentType="text/html; charset=gb2312" %>
<html>
<head>
	<title>Success</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<%
	String flag=request.getParameter("flag");
	String uid=request.getParameter("uid");
	String atmCode=request.getParameter("atmCode");
	String info="";
	String btn="";
	String url="";
	String urlEnd="/ToucsMonitor/deviceconfig?uid="+uid;
	if(flag==null){
		flag="";
	}
	if(flag.equals("1") ){
		info="�豸�Ǽǳɹ���";btn="������Կ";url="/ToucsMonitor/deviceconfig?reqCode=13002&content=key&flag=2";
	}else if(flag.equals("2")){
		info="��Կ���óɹ���";btn="���ó���";url="/ToucsMonitor/deviceconfig?reqCode=13002&content=box&flag=3";
	}else if(flag.equals("3")){
		info="�������óɹ���";btn="�����豸";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("4")){
		info="�豸�޸ĳɹ���";btn="�����豸";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("5")){
		info="��Կ�޸ĳɹ���";btn="�����豸";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("6")){
		info="�����޸ĳɹ���";btn="�����豸";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("7")){
		info="�豸ɾ���ɹ���";
	}else if(flag.equals("8")){
		info="�豸���سɹ���";
	}
	if(!url.equals(""))
		url+="&atmCode="+atmCode+"&uid="+uid;
%>

<body  bgcolor="#CCCCCC" text="#000000">
<p><%=info%></p>
<hr noshade>
<p>
 <%if(!btn.equals("")){%>��һ��  <input type="button" name="btn" value=<%=btn%> onClick="javascript:location.href='<%=url%>'" class="inputbutton"><%}%>
  <input type="button" name="end" value="��������" onClick="javascript:location.href='<%=urlEnd%>'" class="inputbutton">
</p>
</body>
</html>
