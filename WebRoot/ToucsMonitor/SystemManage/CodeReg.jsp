<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%CodeBean code=(CodeBean)request.getAttribute("code");%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="JavaScript">
function  doSubmit(){
	//У��
	if(formReg.id.value==""){
		alert("��������룡");
		return false;
	}
	if(formReg.desc.value==""){
		alert("���������˵��!");
		return false;
	}	
	return confirm("ȷ�����������ύ��");
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String post=request.getParameter("post");
	String uid=request.getParameter("uid");
%>
<h2><font face="����">  
  ϵͳ�������</font></h2>
<hr align="left">
<form name="formReg" method="post" action="/ToucsMonitor/codeman?reqCode=<%=post%>&uid=<%=uid%>"  onSubmit="javascript:return doSubmit()">
	<table width="100%">
    	<tr> 
      		<td width="13%"> 
        		<input type="hidden" name="codeType" value="<%=code.getCodeType()%>">�������ࣺ
        	</td>
      		<td width="18%"><%=CodeManageBean.getCodeDesc(CodeDefine.SYS_CLASS,code.getCodeType())%></td>
      		<td width="69%">&nbsp;</td>
    	</tr>
    	<tr > 
      		<td width="13%"> ���룺</td>
      		<td width="18%"> 
        		<input type="hidden" name="key" value="<%=code.getCodeId()%>">
        		<input type="text" name="id" value="<%=code.getCodeId()%>" >
      		</td>
      		<td width="69%">&nbsp;</td>
    	</tr>
    	<tr> 
      		<td width="13%" valign="top"> ����˵����</td>
      		<td colspan="2"> 
        		<textarea name="desc"><%=code.getCodeDesc()%></textarea>
      		</td>
    	</tr>
  	</table>  
<hr align="left">
	<table width="75%">
    	<tr> 
      		<td> 
        		<div align="left">		  
          		<input type="submit" name="save" value="����" class="inputbutton">          
          		<input type="button" name="cancel" value="ȡ��" onClick=history.back() class="inputbutton">
        		</div>
      		</td>
    	</tr>
  	</table>
<p>&nbsp;</p>
</form>
</body>
</html>