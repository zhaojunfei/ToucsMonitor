<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!--JSP����:��ʼ���б��-->
<%!
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else flag="";
			try{
				out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
					+code.getCodeDesc()+"</option>");
			}catch(IOException exp){}
		}
	}
}
%>

<html>
<!--ҳ��У��-->
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">

function check(){
	var merchantID = document.getElementById("merchant_id").value;
	var merchantType = document.getElementById("merchant_type").value;
	if(merchantID == "" && merchantType == ""){
		alert("�̻��ź��̻����Ͳ���ͬʱΪ��.");
		return false;
	}
	return true;
}
</script>

<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formQuery" method="post" action="/ToucsMonitor/actmerchant?reqCode=17504&uid=<%=uid%>" >
<font face="����" size="+2">֧��ƽ̨�̻���ѯ</font>
	<table width="80%"   cellpadding="0" cellspacing="0">
    	<tr>
    		<td colspan="4"  >
        		<hr noshade>
      		</td>   
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�̻��ţ�</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="merchant_id" class="wideinput" value="" maxlength="15"  size="16" >
      		</td>
    	</tr>
    	<tr>
      		<td width="17%" nowrap align="right">�̻����ͣ�</td>
      		<td width="33%" nowrap>
        		<select name="merchant_type" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("MchantType","",request,out);%>
        		</select>*</td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="4">
        		<div align="right">
          		<input type="submit" name="Submit32" value="ȷ��" class="inputbutton" onClick = "return check()">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>