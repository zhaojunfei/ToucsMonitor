<%@page language="java" %>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.adtec.toucs.monitor.usermanager.userManagerBean"%>
<%
 userManagerBean UM = new userManagerBean();
 String  xmlStr = UM.getOrgInfoXml("010000000","10001","00000","Ã»ÓÐ´íÎó");
 out.println(xmlStr);
%>
