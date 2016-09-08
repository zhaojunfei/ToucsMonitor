<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.net.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<jsp:useBean id="prompt" class="com.adtec.toucs.monitor.common.PromptBean" scope="request"/>
<%
	int  curButton=0;
%>
<html>
<head>
<title>Success</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
<script language="javascript">
function onOk(){
	var target="<%=request.getParameter("target")%>";
	if(name=="top"){
		parent.location.href=target;
		close();
	}else{
		location.href=target;
	}
}
function   onButtonClk (  ){
        var  target;
        target = "<%=prompt.getBtnURL( curButton )%>";
        document.form1.action= target;
        document.form1.submit();
}
</script>
</head>
<body  bgcolor="#CCCCCC">
<h2> <%=prompt.getTitle()%></h2>
<%if(!prompt.getPrompt().equals("")){
	String str = prompt.getPrompt();
	String para = str.substring(0);
	String para1 = "";
	String para2 = "";
	String para3 = "";
	String para4 = "";
	String para5 = "";
	if(str.length()>76){
		para1=str.substring(0,20);
		para2=str.substring(20,41);
		para3=str.substring(41,51);
		para4=str.substring(51,76);
		para5=str.substring(76);
	

%>
<hr>
<h3><%=para1%></h3><p>
<h3><%=para2%></h3><p>
<h3><%=para3%></h3><p>
<h3><%=para4%></h3><p>
<h3><%=para5%></h3>
<%}else{%>
<hr>
<h3><%=para %></h3>
<%} }%>
<hr>


<%
	if( prompt.getMethod() == "POST" ){
%>
<form  name="form1" method="POST" >
<%
	for(int i=0;i<prompt.getBtnSize();i++){
  		curButton = i;
  		if(prompt.getBtnLabel(i)!=null){
%>
			<input type="button" name="btn<%=i%>" value="<%=prompt.getBtnLabel(i)%>" onClick=onButtonClk() class="inputbutton">
<%
		}
  	}
%>
</form>
<%
	}
%>


<%
	if( prompt.getMethod() == "GET" ){
%>
<%
	for(int i=0;i<prompt.getBtnSize();i++){
  		if(prompt.getBtnLabel(i)!=null){
%>
			<input type="button" name="btn<%=i%>" value="<%=prompt.getBtnLabel(i)%>" onClick="<%=prompt.getBtnTarget(i)%>" class="inputbutton">
<%
		}
  	}
%>
<%
	}
%>
</body>
</html>
