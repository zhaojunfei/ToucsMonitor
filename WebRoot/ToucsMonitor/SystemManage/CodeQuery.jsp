<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>

<%! 
private String getModLink(CodeBean code,String uid){
		String ret="/ToucsMonitor/codeman?reqCode=17002&codeType="+code.getCodeType()
			+"&id="+code.getCodeId()+"&desc="+code.getCodeDesc()+"&uid="+uid;
		return ret;	
}
	
private String getDelLink(CodeBean code,String uid){
		String ret="/ToucsMonitor/codeman?reqCode=17003&codeType="+code.getCodeType()
			+"&id="+code.getCodeId()+"&uid="+uid;
		return ret;
}
%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="JavaScript">
function doAdd(addLink){
	type=formQuery.codeType.value;
	if(type=="")
		alert("请选择代码种类");
	else
		location.href=addLink+"&codeType="+type;
}

function  doDel(delLink){
	if(confirm("确实要删除吗？"))
		location.href=delLink;	
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String queryPerm=(String)request.getAttribute("QUERYPERM");
	String addPerm=(String)request.getAttribute("ADDPERM");
	String modPerm=(String)request.getAttribute("MODPERM");
	String delPerm=(String)request.getAttribute("DELPERM");
	String type=(String)request.getAttribute("codeType");	 
%>
<h2><font face="隶书">系统代码管理</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/codeman?reqCode=17004&uid=<%=uid%>">
  	<table width="75%">
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="30%">请选择代码种类：</td>
      		<td width="70%"> 
        		<select name="codeType" size="1" onChange=javascript:document.formQuery.submit() <%=(queryPerm!=null&&queryPerm.equals("1"))?"":"DISABLED"%>>
          		<option value="">请选择</option>          
		  		<%
		  			List typeList=CodeManageBean.typeList;
		  			String key,value,selected;				
		  			if(typeList!=null){
						for(int i=0;i<typeList.size();i++){
							CodeBean code=(CodeBean)typeList.get(i);
							key=code.getCodeId();
							value=code.getCodeDesc();
							selected=(type!=null&&type.equals(key))?"selected":"";
		  		%>          
          		<option value="<%=key%>"  <%=selected%>><%=value%> </option>
          		<%		}
		  			}
		  		%>
        		</select>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2">        
        		<table width="100%" bgcolor="#000000" cellspacing="1" >
          			<tr bgcolor="#666666"> 
            			<td width="10%"> 
              				<div align="center">代码</div>
            			</td>
            			<td width="70%"> 
              				<div align="center">名称</div>
            			</td>
            			<td width="20%"> 
              				<div align="center">操作</div>
            			</td>
          			</tr>		  
		  			<%
		  				List list=(List)request.getAttribute("list");
        				if(list!=null){
          					for(int i=0;i<list.size();i++){
	      						CodeBean code=(CodeBean)list.get(i);
		  			%>	
          			<tr bgcolor="#FFFFFF"> 
            			<td width="10%"  > 
              				<div align="center"><%=code.getCodeId()%></div>
            			</td>
            			<td width="70%"> 
              				<div align="center"><%=code.getCodeDesc()%></div>
            			</td>
            			<td width="20%"> 
              				<div align="center">
			  				<%if(modPerm!=null&&modPerm.equals("1")){%>
			  				<a href="<%=getModLink(code,uid)%>">[修改]</a>
			  				<%}%>
			  				<%if(delPerm!=null&&delPerm.equals("1")){%>
			  				<a href="javascript:doDel('<%=getDelLink(code,uid)%>')">[删除]</a>
			  				<%}%>
			  				</div>
            			</td>
          			</tr>		  
          			<%
		  				}
					}
		  			%>
        		</table>		
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2">
        		<%if(type!=null&&!type.equals("")){%>
        		<input type="button" name="add" value="添加"  onClick="javascript:doAdd('/ToucsMonitor/codeman?reqCode=17001&uid=<%=uid%>')" class="inputbutton" 
				<%=(addPerm!=null&&addPerm.equals("1"))?"":"DISABLED"%>>
        		<%}%>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>