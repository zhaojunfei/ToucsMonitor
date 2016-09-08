<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSInfo"  scope="request"/>
<jsp:useBean id="posTxnInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSTxnInfo"  scope="request"/>

<%!
//��ȡ������Ϣ����
private void getListDesc(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId())){
				try{
				out.println(code.getCodeDesc());
			    }catch(IOException exp){
			    	exp.printStackTrace();
			    	System.out.println(exp.getMessage());
			    }
			}
		}
	}
}
%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<body bgcolor="#CCCCCC" text="#000000">
<%
  	String uid=request.getParameter("uid");
  	String reqCode=request.getParameter("reqCode");
%>
<h2><font face="����">POS�豸����Ȩ��������Ϣ</font></h2>
<form name="eBridge" method="post" action="" >
    <table width="80%" >
    	<tr>
      		<td colspan="4" nowrap >
        		<hr noshade>
      		</td>
    	</tr>
    </table>
  	<table width="80%" border="0" cellpadding="1" cellspacing="1"  bgcolor="#333333">
    	<tr>
      		<td width="20%" nowrap align="right" bgcolor="#999999">POS��ţ�</td>
      		<td width="30%" nowrap bgcolor="#FFFFFF"><%=posInfo.getPoscode()%></td>
      		<td width="20%" nowrap align="right" bgcolor="#999999">POS-C��ţ�</td>
      		<td width="30%" nowrap bgcolor="#FFFFFF"><%=posInfo.getPosCCode()%></td>
     	</tr>
     	<tr>
       		<td width="20%" nowrap height="19" align="right" bgcolor="#999999" >POS-DCC�����ţ�</td>
      		<td width="30%" nowrap height="19" bgcolor="#FFFFFF"><%=posInfo.getPosDccCode()%></td>
      		<td width="20%" nowrap height="19" align="right" bgcolor="#999999">�豸���ͣ�</td>
      		<td width="30%" nowrap height="19" bgcolor="#FFFFFF">
      			<%getListDesc("equitTypeList",posInfo.getEquiptType(),request,out);%>
     		</td>
     	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" bgcolor="#999999">Ԥ��Ȩ���ã�</td>
      		<td width="30%" nowrap height="19" bgcolor="#FFFFFF">
      			<% if(  posInfo.getPreAutorized().equals("2") ) {%>�Զ��彻��Ȩ��
      			<%}else{
      				getListDesc("preAuthorizedList",posInfo.getPreAutorized(),request,out);
      			}%>     
      		</td>
	     	<td width="20%" nowrap height="19" align="right" bgcolor="#999999" >POS���ͣ�</td>
         	<td width="30%" nowrap height="19" bgcolor="#FFFFFF"><%getListDesc("memo1aList",posInfo.getMemo1a(),request,out);%></td>
    	</tr>
	</table>
    <table width="80%" >
     	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
   	</table>
   	<table width="80%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="25%" bgcolor="#999999">
      			<div align="center">��������</div>
    		</td>
    		<td width="15%" bgcolor="#999999">
      			<div align="center">�����������</div>
    		</td>
    		<td width="15%" bgcolor="#999999">
      			<div align="center">�������������</div>
    		</td>
    		<td width="15%" bgcolor="#999999">
      			<div align="center">���������</div>
    		</td>
    		<td width="15%" bgcolor="#999999">
      			<div align="center">�����������</div>
    		</td>
    		<td width="15%" bgcolor="#999999">
      			<div align="center">ת��POS������</div>
    		</td>
  		</tr>
		<%
			Vector v= (Vector) request.getAttribute("txnSetList");
    		if( v!= null ){
      			for( int i=0 ; i< v.size() ; i++ ){
        			POSTxnInfo pti = (POSTxnInfo) v.get(i);
		%>
  		<tr bgcolor="#FFFFFF" align="center">
   			<td width="25%"><%=pti.getTxnName()%></td>
    		<td width="15%"><input type="checkbox" name="txnPermR"  style="width:20px" value="<%=pti.getLocation()%>" DISABLED <%=((pti.getTxnPerm()=='1')||(pti.getTxnPerm()=='3'))?"CHECKED":""%> />   </td>
    		<td width="15%"><input type="checkbox" name="handFlagR" style="width:20px" value="<%=pti.getLocation()%>" DISABLED <%=((pti.getHandPerm()=='1')||(pti.getHandPerm()=='3'))?"CHECKED":""%> /> </td>
    		<td width="15%"><input type="checkbox" name="txnPermF"  style="width:20px" value="<%=pti.getLocation()%>" DISABLED <%=((pti.getTxnPerm()=='2')||(pti.getTxnPerm()=='3'))?"CHECKED":""%> />   </td>
    		<td width="15%"><input type="checkbox" name="handFlagF" style="width:20px" value="<%=pti.getLocation()%>" DISABLED <%=((pti.getHandPerm()=='2')||(pti.getHandPerm()=='3'))?"CHECKED":""%> /> </td>
    		<td width="15%"><input type="checkbox" name="txnPermC"  style="width:20px" value="<%=pti.getLocation()%>" DISABLED <%=pti.getTxnPerm()=='4'?"CHECKED":""%> />    </td>
  		</tr>
		<%
      			}
    		}
		%>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="5" nowrap>
        	<!--a href="javascript:doSubmit()">[ȷ��]</a>
		  	<a href="javascript:formReg.reset()">[����]</a>
          	<a href="javascript:location.href='/ToucsMonitor/devicemanage'">[ȡ��]</a-->
        	<div align="right">
          		<input type="button" name="ok" value="ȷ��" class="inputbutton" onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>