<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<title>logList</title>
<script src="js/power.js"></script>
<script src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
<script>
<!--
function sverify(){
	if( isNull("begindate","��ʼ����") ) return false;
  	if( isNull("enddate","��ֹ����") ) return false;
	
  	if(!validFullDate("begindate","��ʼ����"))	return false;
  	if(!validFullDate("enddate","��ֹ����"))	return false;
	
  	if(confirm("ȷʵҪɾ����Щ��־��"))
   		return true;
  	else
    	return false;
}
//-->
</script>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<h2> <font face="����">��ѯ��־</font>
<%
	Vector logV = (Vector)request.getAttribute("logV");
	if(logV == null ) {
  		out.println("���ݲ���ʧ�ܣ�");
 	 	return;
	}
%>
</h2>
<hr align="left" noshade>
	<table cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td>�û�����</td>
		    <td>�ն˵�ַ</td>
		    <td bgcolor="#666666">��������</td>
		    <td>����ʱ��</td>
		    <td>��������</td>
 		 </tr>
  		<%
  			for (int i = 0; i < logV.size(); i++) {
  				Hashtable logHT = new Hashtable();
  				logHT = (Hashtable)logV.get(i);
  				if (logHT != null) {
  		%>
  		<tr bgcolor="#FFFFFF" >
		    <td width="12%"><font size="2"><%=String.valueOf(logHT.get("user_name")).trim().equals("")?"&nbsp":logHT.get("user_name").toString()%></font></td>
		    <td width="12%"><font size="2"><%=String.valueOf(logHT.get("term_addr")).trim().equals("")?"&nbsp":logHT.get("term_addr").toString()%></font></td>
		    <td width="14%"><font size="2"><%=String.valueOf(logHT.get("oper_date")).trim().equals("")?"&nbsp":logHT.get("oper_date").toString().substring(0,4)+"-"+logHT.get("oper_date").toString().substring(4,6)+"-"+logHT.get("oper_date").toString().substring(6,8)%></font></td>
		    <td width="10%"><font size="2"><%=String.valueOf(logHT.get("oper_time")).trim().equals("")?"&nbsp":logHT.get("oper_time").toString().substring(0,2)+":"+logHT.get("oper_time").toString().substring(2,4)+":"+logHT.get("oper_time").toString().substring(4,6)%></font></td>
		    <td width="52%"><font size="2"><%=String.valueOf(logHT.get("oper_memo")).trim().equals("")?"&nbsp":logHT.get("oper_memo").toString()%></font></td>
		</tr>
  		<%
   				}else {
    				out.println("����������������");
  				}
			}
  		%>
	</table>
<hr align="left" noshade>
<form name="sform" method="post" action="/ToucsMonitor/logManager" onsubmit='return sverify()'>
<h2><font face="����">ɾ����־</font> </h2>
	<table border="0" cellspacing="0" cellpadding="0" align="left" width="75%">
    	<tr valign="bottom">
      		<td height="35" width="23%"><font size="2">��ʼ���ڣ�</font></td>
     	 	<td height="35" width="77%"><font size="2">
        		<input type="text" name="begindate" size="10" maxlength="10">(YYYYMMDD)</font>
        	</td>
    	</tr>
    	<tr valign="bottom">
      		<td height="35" width="23%">��ֹ���ڣ�<font size="2"> </font></td>
      		<td height="35" width="77%">
        		<input type="text" name="enddate"   size="10" maxlength="10">(YYYYMMDD)
        		<font size="2">
        		<input type="hidden" name="txcode" value="14002"></font> 
        	</td>
    	</tr>
    	<tr valign="bottom">
      		<td height="35" width="23%">��</td>
      		<td height="35" width="77%">
        		<div align="left">
         	 	<input type="submit" name="Submit" value="ɾ��" class="inputbutton" >
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>