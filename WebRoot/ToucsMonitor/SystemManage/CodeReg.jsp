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
	//校验
	if(formReg.id.value==""){
		alert("请输入代码！");
		return false;
	}
	if(formReg.desc.value==""){
		alert("请输入代码说明!");
		return false;
	}	
	return confirm("确认输入无误并提交吗？");
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String post=request.getParameter("post");
	String uid=request.getParameter("uid");
%>
<h2><font face="隶书">  
  系统代码管理</font></h2>
<hr align="left">
<form name="formReg" method="post" action="/ToucsMonitor/codeman?reqCode=<%=post%>&uid=<%=uid%>"  onSubmit="javascript:return doSubmit()">
	<table width="100%">
    	<tr> 
      		<td width="13%"> 
        		<input type="hidden" name="codeType" value="<%=code.getCodeType()%>">代码种类：
        	</td>
      		<td width="18%"><%=CodeManageBean.getCodeDesc(CodeDefine.SYS_CLASS,code.getCodeType())%></td>
      		<td width="69%">&nbsp;</td>
    	</tr>
    	<tr > 
      		<td width="13%"> 代码：</td>
      		<td width="18%"> 
        		<input type="hidden" name="key" value="<%=code.getCodeId()%>">
        		<input type="text" name="id" value="<%=code.getCodeId()%>" >
      		</td>
      		<td width="69%">&nbsp;</td>
    	</tr>
    	<tr> 
      		<td width="13%" valign="top"> 代码说明：</td>
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
          		<input type="submit" name="save" value="保存" class="inputbutton">          
          		<input type="button" name="cancel" value="取消" onClick=history.back() class="inputbutton">
        		</div>
      		</td>
    	</tr>
  	</table>
<p>&nbsp;</p>
</form>
</body>
</html>