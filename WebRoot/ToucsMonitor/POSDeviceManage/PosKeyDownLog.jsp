<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.* " %>
<%@ page import="com.adtec.toucs.monitor.common.*,com.adtec.toucs.monitor.usermanager.*,com.adtec.toucs.monitor.loginmanage.*"%>
<%
    try{
        LoginInfo login = (LoginInfo)request.getAttribute("Login");
        if (login == null) {
            throw new MonitorException(ErrorDefine.NOT_AUTHORIZED,"");
        }
%>
<html>
<head>
<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">POS密钥下载日志查询结果</font> </h2>
<hr align="left" noshade>
<%
	List list = (List)request.getAttribute("poskeydownlog");
	if(list != null && list.size() != 0){
%>
<table  cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000" width="500">
  <tr bgcolor="#666666">
    <td >
      <div align="center"><font size="2">日期</font></div>
    </td>
    <td >
      <div align="center"><font size="2">时间</font></div>
    </td>
    <td >
      <div align="center"><font size="2">操作员</font></div>
    </td>
    <td >
      <div align="center"><font size="2">POS顺序号</font></div>
    </td>
  </tr>
<%
	for(int i = 0; i < list.size(); i++){
	    logInfo li = (logInfo)list.get(i);
%>
  <tr bgcolor="#FFFFFF">
    <td ><%=li.getOper_Date()%></td>
    <td ><%=li.getOper_Time()%></td>
    <td ><%=li.getUser_Name()%></td>
    <td ><%=li.getMemo()%></td>
  </tr>
<%
	}
%>
</table>
<%
	}
	else {
	    out.println("暂时没有任何关于POS密钥下载的记录！");
	}
%>
<hr align="left" noshade>
<input type="button" value="返回" onclick="javascript:location.href='/ToucsMonitor/POSDeviceManage'" >
</body>
</html>
<%
    }
    catch (MonitorException ex){
        ex.errProc(request,response);
    }
%>