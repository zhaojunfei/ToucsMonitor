<%@ page language="java" contentType="text/html; charset=GBK" %>
 
<html>
<head>
<title>�Թ��ʻ�����</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h3>�Թ��ʻ�����</h3>
<hr>
<%
    String auth_code =(String) request.getAttribute("auth_code");
%>
��Ȩ��Ϊ��[<font color="#FF0000"><%=auth_code%></font>]
<hr>
<input type="button" name="����" value="����" onClick="history.go(-1);" class="inputbutton">
</body>
</html>
<!--%
    }
    catch (MonitorException ex){
        ex.errProc(request,response);
    }
%-->