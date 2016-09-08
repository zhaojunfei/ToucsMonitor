<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="TxnManageBean" class="com.adtec.toucs.monitor.systemmanage.TxnCodeManageBean"  scope="request"/>

<%! 
private String getModLink(TxnCodeBean code,String uid){
	String ret="/ToucsMonitor/txncodeman?reqCode=17102&deviceType="+code.getDeviceType()
		+"&txnCode="+code.getTxnCode()+"&txnName="+URLEncoder.encode(code.getTxnName())+"&uid="+uid;
	return ret;	
}
	
private String getDelLink(TxnCodeBean code,String uid){
	String ret="/ToucsMonitor/txncodeman?reqCode=17103&deviceType="+code.getDeviceType()
		+"&txnCode="+code.getTxnCode()+"&uid="+uid;
	return ret;
}
%>

<html>
<head>
	<title>ƽ̨�������ѯ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="JavaScript">
function doAdd(addLink){
	type=formQuery.deviceType.value;
	if(type=="")
		alert("��ѡ���豸����!");
	else
		location.href=addLink+"&deviceType="+type;
}
function  doDel(delLink){
	if(confirm("ȷʵҪɾ��������"))
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
	 String type=(String)request.getAttribute("deviceType");	 
%>
<h2><font face="����">ϵͳ�������</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/txncodeman?reqCode=17104&uid=<%=uid%>">
	<table width="75%">
    	<tr> 
      		<td colspan="2"> 
        		<hr align="left" width="100%" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="30%">��ѡ���豸���ͣ�</td>
      		<td width="70%"> 
        		<select name="deviceType" size="1" onChange=javascript:document.formQuery.submit() <%=(queryPerm!=null&&queryPerm.equals("1"))?"":"DISABLED"%>>
          		<option value="">��ѡ��</option>
          		<%
          			String selFlag,value;
          			for ( int i=0 ; i<TxnManageBean.typeList.length ; i++ ){
          				value=TxnManageBean.typeList[i];
          				selFlag=(type!=null&&type.equals(value))?"SELECTED":"";
          		%>
          		<option value = <%=value%> <%=selFlag%>> <%=value.toUpperCase()%> </option>
          		<%} %>
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
              				<div align="center">���״���</div>
            			</td>
            			<td width="70%"> 
              				<div align="center">��������</div>
            			</td>
            			<td width="20%"> 
              				<div align="center">����</div>
            			</td>
          			</tr>	  
		  			<%
		  				List list=(List)request.getAttribute("list");
        				if(list!=null){
          					for(int i=0;i<list.size();i++){
	      						TxnCodeBean code=(TxnCodeBean)list.get(i);
		  			%>
          			<tr bgcolor="#FFFFFF"> 
            			<td width="10%"  > 
              				<div align="center"><%=code.getTxnCode()%></div>
            			</td>
            			<td width="70%"> 
              				<div align="center"><%=code.getTxnName()%></div>
            			</td>
            			<td width="20%"> 
              				<div align="center">
			  				<%if(modPerm!=null&&modPerm.equals("1")){%>
			  				<a href="<%=getModLink(code,uid)%>">[�޸�]</a>
			  				<%}%>
			  				<%if(delPerm!=null&&delPerm.equals("1")){%>
			  				<a href="javascript:doDel('<%=getDelLink(code,uid)%>')">[ɾ��]</a>
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
        		<input type="button" name="add" value="���"  onClick="javascript:doAdd('/ToucsMonitor/txncodeman?reqCode=17101&uid=<%=uid%>')" class="inputbutton" 
				<%=(addPerm!=null&&addPerm.equals("1"))?"":"DISABLED"%>>
        		<%}%>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>