<%@page language="java" %>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.adtec.toucs.monitor.usermanager.userManagerBean,com.adtec.toucs.monitor.common.toucsString"%>

<html>
<head>
	<title>角色授权</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/style/v5_css.css" type="text/css">
	<script src="js/power.js"></script>
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	userManagerBean UM=new userManagerBean();
	Vector oV=new Vector();
	Hashtable Res=new Hashtable();
	int i=0;
	String roleId=request.getParameter("roleid");
	String roleName=request.getParameter("rolename");
	oV=(Vector)UM.getRoleRes(roleId);
%>
<div align="center"> 
<h2><font face="隶书">角色<font color="#0000FF"><%=toucsString.unConvert(roleName)%></font>授权</font></h2>
<hr align="left" noshade>
<form name="powersearch" method="post" action='/ToucsMonitor/roleManager'>
    <table width="42%" border="0" cellspacing="0" height="163">
    	<tr>
      		<td width="43%">
          		<div align="center"><font size="2">角色所拥有的权限</font></div>
      		</td>
      		<td width="14%">　</td>
      		<td width="43%">
          		<div align="center"><font size="2">可选权限</font></div>
      		</td>
    	</tr>
      	<tr valign="top"> 
        	<td width="43%"> 
          		<div align="center">
            	<select name="Power" size="12" multiple style="width:150;height:150;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
              	<%
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
				%>
              	<option value="<%=Res.get("resource_id")%>"><%=Res.get("resource_name")%></option>
				<%
					}
				%>
            	</select>
          		</div>
      		</td>
        	<td width="14%"> 
          		<div align="center">
          		<p>
					<input type="hidden" name="roleid" value="<%=roleId%>">
					<input type="hidden" name="rolename" value="<%=toucsString.unConvert(roleName)%>">
			  		<input type="hidden" name="txcode" value="10305">
              		<input type="button" name="Button" value="&lt;&lt;&lt;添加" onclick="JavaScript:DeleteItem('availablePower','Power')" style="height:20;width:50;font-size:12" >
          		</p>
          		<p>
              		<input type="button" name="Submit2" value="删除&gt;&gt;&gt;" onclick="JavaScript:DeleteItem('Power','availablePower')" style="height:20;width:50;font-size:12" >
          		</p>
            	<p>
              		<input type="button" name="Submit3" value="确定" onclick="JavaScript:Submited('Power')" style="height:20;width:70;font-size:12" >
            	</p>
            	</div>
      		</td>
        	<td width="43%"> 
          		<div align="center">
            		<select name="availablePower" size="12" multiple style="width:150;height:150;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
              		<%
						i=0;
						oV=(Vector)UM.getAvailableRes(roleId);
						out.print(oV.size());
						while(i<oV.size()){
							Res=(Hashtable)oV.get(i);
							i++;
					%>
              		<option value="<%=Res.get("resource_id")%>"><%=Res.get("resource_name")%></option>
					<%
			 			}
					%>
            		</select>
          		</div>
      		</td>
    	</tr>
  	</table>
</form>
<hr align="left" noshade>
<div align="right">
    <input type="button" name="Button" value="返回" class="inputbutton" onclick="history.go(-1);">
</div>
</div>
</body>
</html>