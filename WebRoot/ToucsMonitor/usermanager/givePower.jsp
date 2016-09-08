<%@page language="java" %>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.adtec.toucs.monitor.usermanager.userManagerBean"%>

<html>
<head>
	<title>用户授权</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<script src="js/power.js"></script>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	userManagerBean UM=new userManagerBean();
	Vector oV=new Vector();
	Hashtable Res=new Hashtable();
	int i=0;
	String userId=request.getParameter("userid");
	oV=(Vector)UM.getUserRole(userId);
%>
<div align="center">
<h2><font face="隶书">用户<font color="#0000FF"><%=com.adtec.toucs.monitor.common.toucsString.unConvert(userId)%></font>授权</font></h2>
<form name="powersearch" method="post" action='/ToucsMonitor/usermanagerservlet'>
<hr align="left" noshade>
	<table width="42%" border="0" cellspacing="0" height="163">
    	<tr>
      		<td width="43%">
          		<div align="center"><font size="2">用户所属角色</font></div>
      		</td>
      		<td width="14%">&nbsp;</td>
      		<td width="43%">
          		<div align="center"><font size="2">可选角色</font></div>
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
              		<option value="<%=Res.get("role_id")%>"><%=Res.get("role_name")%></option>
					<%
						}
					%>
            		</select>
          		</div>
      		</td>
        	<td width="14%"> 
          		<div align="center">
          		<p>
					<input type="hidden" name="userid" value="<%=com.adtec.toucs.monitor.common.toucsString.unConvert(userId)%>">
					<input type="hidden" name="oper_type" value="10205">			
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
					oV=(Vector)UM.getAvailableRole(userId);
					out.print(oV.size());
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
				%>
              	<option value="<%=Res.get("role_id")%>"><%=Res.get("role_name")%></option>
				<%
			 		}
				%>
            	</select>
          		</div>
      		</td>
    	</tr>
  	</table>  <hr align="left" noshade>
	<div align="right"><font size="2"> 
	<input type="reset" name="Reset" value="返回"  class="inputbutton" onClick="history.go(-1);">
</font> </div>
</form>
</div>
</body>
</html>