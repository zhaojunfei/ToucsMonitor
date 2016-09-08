<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title>无标题文档</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#FFFFFF" text="#000000">
<%
  POSMerchantBean PMB=new POSMerchantBean(); 
  Vector oV  =new Vector();
  Hashtable Res=new Hashtable();
  int i=0;
  oV  = (Vector)PMB.getIsAaddedATM();
%>
<h2 align="center" class="style3"><font face="隶书"></font></h2>
<form name="form1" method="post" action="">
<div align="justify">
	<table width="236" height="336" border="1">
    	<tr>
        	<td width="110" height="33"> <div align="center">已添加ATM设备</div></td>
        	<td width="110"><div align="center">待添加ATM设备</div></td>
      	</tr>
      	<tr>
        	<td height="295"><div align="center">
          		<select name="select" size="10" multiple>
		   		<%
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
				%>
            	<option value="<%=Res.get("atm_id")%>"><%=Res.get("atm_id")%></option>
            	<%
			 	}
				%>
          		</select>
        	</div></td>
        	<td>
        		<div align="center">
          		<select name="select2" size="20" multiple>
		    	<%
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
				%>
            	<option value="<%=Res.get("atm_id")%>"><%=Res.get("atm_id")%></option>
            	<%
					 }
				%>		  
          		</select>
			</div></td>
      	</tr>
	</table>
</div>
<div align="center"></div>
</form>
</body>
</html>
