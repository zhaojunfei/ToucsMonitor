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
		info="设备登记成功！";btn="配置密钥";url="/ToucsMonitor/deviceconfig?reqCode=13002&content=key&flag=2";
	}else if(flag.equals("2")){
		info="密钥配置成功！";btn="配置钞箱";url="/ToucsMonitor/deviceconfig?reqCode=13002&content=box&flag=3";
	}else if(flag.equals("3")){
		info="钞箱配置成功！";btn="加载设备";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("4")){
		info="设备修改成功！";btn="加载设备";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("5")){
		info="密钥修改成功！";btn="加载设备";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("6")){
		info="钞箱修改成功！";btn="加载设备";url="/ToucsMonitor/deviceconfig?reqCode=12003";
	}else if(flag.equals("7")){
		info="设备删除成功！";
	}else if(flag.equals("8")){
		info="设备加载成功！";
	}
	if(!url.equals(""))
		url+="&atmCode="+atmCode+"&uid="+uid;
%>

<body  bgcolor="#CCCCCC" text="#000000">
<p><%=info%></p>
<hr noshade>
<p>
 <%if(!btn.equals("")){%>下一步  <input type="button" name="btn" value=<%=btn%> onClick="javascript:location.href='<%=url%>'" class="inputbutton"><%}%>
  <input type="button" name="end" value="结束配置" onClick="javascript:location.href='<%=urlEnd%>'" class="inputbutton">
</p>
</body>
</html>
