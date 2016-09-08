<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

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
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("ATMREG");
	
	permFlag=(String)request.getAttribute("ATMUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMDEL");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));
	permFlag=(String)request.getAttribute("ATMQUERY");
	
	permFlag=(String)request.getAttribute("ATMLOAD");
	boolean canLoad=(permFlag!=null&&permFlag.equals("1"));

	Vector v=(Vector)request.getAttribute("atmList");

%>
<div>
  	<div align="left">�豸�б�:�����豸<font color=#FF0000>[<%=v.size()%>]</font>̨ </div>
</div>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="10%">
      			<div align="center">���</div>
   			</td>
    		<td width="30%">
      			<div align="center">��������</div>
    		</td>
    		<td width="10%">
      			<div align="center">����</div>
    		</td>
    		<td width="20%">
      			<div align="center">����״̬</div>
    		</td>
    		<td width="30%">
      			<div align="center">����</div>
    		</td>
  		</tr>
  		<%
			String atmCode="";
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
				ATMInfo info=(ATMInfo)v.get(i);
				atmCode=info.getAtmCode();
   		%>
  		<tr bgcolor="#FFFFFF" id=<%=info.getAtmCode()%>>
    		<td width="10%" >
      			<div align="center"><%=atmCode%> </div>
    		</td>
    		<td width="30%">
      			<div align="center"><%=CodeManageBean.getCodeDesc("orgname",info.getOrgId())%></div>
    		</td>
    		<td width="10%">
      			<div align="center"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_TYPE,info.getAtmType())%></div>
    		</td>
    		<td width="20%">
      			<div align="center">
        		<!--��ʾ�豸״̬�����״̬-->
        		<%
     	    		String load_flag =  CodeManageBean.getCodeDesc(CodeDefine.LOAD_FLAG,info.getUseFlag());
        			if( !info.getUseFlag().equals("1")) {
           			try{
                		out.print("<font color=#FF0000>[" +load_flag +"]</font>" );
           			}catch(IOException exp){
           				exp.printStackTrace();
           				System.out.println(exp.getMessage());
           			}
           		}else {
					try{
                		out.print("[" +load_flag +"]" );
           			}catch(IOException exp){
           				exp.printStackTrace();
           				System.out.println(exp.getMessage());
           			}
           		}
        		%>
        		<a href="<%=getOperLink("13003","state",atmCode,uid)%>">[�鿴]</a> </div>
    		</td>
    		<td width="30%">
      			<div align="center">
        		<%if(canModify){%>
        		<a href="<%=getOperLink("13002","base",atmCode,uid)%>">[����] </a><a href="<%=getOperLink("13002","key",atmCode,uid)%>">[��Կ]</a><a href="<%=getOperLink("13002","box",atmCode,uid)%>">[����]</a>
        		<%}else {%>
        		<a href="<%=getOperLink("13003","base",atmCode,uid)%>">[����] </a><a href="<%=getOperLink("13003","key",atmCode,uid)%>">[��Կ]</a><a href="<%=getOperLink("13003","box",atmCode,uid)%>">[����]</a>
        		<%}%>
        		<%if(canDel){%>
        		<a href="javascript:onDelete('<%=getOperLink("12008","",atmCode,uid)%>')">[ɾ��]</a>
        		<%}%>
        		<%if(canLoad){%>
        		<a href="<%=getOperLink("12003","",atmCode,uid)%>">[����]</a>
       	 		<%}%>
      			</div>
    		</td>
  		</tr>
  		<%}
  			}%>
</table>
</body>
</html>