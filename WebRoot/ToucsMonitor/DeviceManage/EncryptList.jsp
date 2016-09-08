<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>

<%!
private String getOperLink(String reqCode,String content,String atmCode,String uid){
        String ret="/ToucsMonitor/deviceconfig?reqCode="+reqCode+"&uid="+uid
                  +"&content="+content+"&atmCode="+atmCode;
        return ret;
}
%>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onDelete(linkDel){
    if(window.confirm("ȷʵҪɾ�����豸��")){
        location.href=linkDel;
    }
}

function onStart(linkStart){
    if(window.confirm("ȷʵҪ���ø��豸��")){
        location.href=linkStart;
    }
}

function onStop(linkStop){
    if(window.confirm("ȷʵҪͣ�ø��豸��")){
        location.href=linkStop;
    }
}

var fresh = false;
function refresh(){
    setTimeout("refresh();",5000)
	if(fresh){
		document.form1.submit();
	}else{
		fresh = true;
	}
}
</script>

<body bgcolor="#CCCCCC" text="#000000"  onload = "refresh()">
<%
    String uid=request.getParameter("uid");
	Vector encryptInfoV=(Vector)request.getAttribute("encryptInfo");
%>
<div>
  	<div align="left">�豸�б� </div>
</div>
<form name = "form1" action = "/ToucsMonitor/deviceconfig">
<input type = "hidden" name = "reqCode" value = "21006">
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="10%">
      			<div align="center">���</div>
    		</td>
    		<td width="30%">
      			<div align="center">����</div>
    		</td>
    		<td width="10%">
      			<div align="center">����</div>
    		</td>
    		<td width="10%">
      			<div align="center">�Ƿ�����</div>
    		</td>
    		<td width="10%">
      			<div align="center">����״̬</div>
    		</td>
    		<td width="30%">
      			<div align="center">����</div>
    		</td>
  		</tr>
  		<%
      		String atmCode="";
			if(encryptInfoV!=null){
    			for(int i=0;i<encryptInfoV.size();i++){
        			ATMInfo info=(ATMInfo)encryptInfoV.get(i);
        			atmCode=info.getAtmCode();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getAtmCode()%>>
    		<td width="10%" >
      			<div align="center"><%=atmCode%> </div>
    		</td>
    		<td width="30%">
      			<div align="center"><%=info.getAtmName()%></div>
    		</td>
    		<td width="10%">
       		<%
           		if(info.getAtmType().trim().equals("0")){
			%>
      		<div align="center">����</div>
			<%}else{%>
      		<div align="center">����</div>
			<%}%>
    		</td>
    		<td width="10%">
      		<div align="center">
			<%
				if(info.getUseFlag().trim().equals("2")){
			%>
				ͣ��
			<%}else{%>
				����
			<%}%>
	  		</div>
    		</td>
    		<td width="10%">
      			<div align="center">
			<%
				if(info.getRunStat().trim().equals("0")){
			%>
				<font color="FF000000">����</font>
			<%}else{%>
				����
			<%}%>
	  		</div>
    		</td>
    		<td width="30%">
      		<div align="center"><a href="/ToucsMonitor/DeviceManage/encryptInfoDetail.jsp?encryCode=<%=atmCode%>">[�鿴]</a>
	  		<%
				if(info.getUseFlag().trim().equals("2")){
			%>
			<a href="javascript:onStart('<%=getOperLink("21002","",atmCode,uid)%>')">[����] </a>
			<%}else{%>
			<a href="javascript:onStop('<%=getOperLink("21003","",atmCode,uid)%>')">[ͣ��]</a>
			<%}%>
        	<a href="/ToucsMonitor/DeviceManage/modiEncryptInfo.jsp?encryCode=<%=atmCode%>">[�޸�]</a><a href="javascript:onDelete('<%=getOperLink("21004","",atmCode,uid)%>')">[ɾ��]</a>
      		</div>
    		</td>
  		</tr>
  		<%}
     		}%>
	</table>
</form>
</body>
</html>