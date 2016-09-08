<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<jsp:useBean id="keyInfo" class="com.adtec.toucs.monitor.devicemanage.AtmKeyInfo"  scope="request"/>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onChangeDesType(selDesType,txtKeyLen){
	var desType=selDesType.value;
	if(desType!="") txtKeyLen.value="8";
	else txtKeyLen.value="";
}
function checkKeyLen(selDesType,txtKeyLen){
	var desType=selDesType.value;
	var keyLen=txtKeyLen.value;
	if(desType=="0"&&txtKeyLen!="8"){txtKeyLen.value="8"; return true;}
	else if(desType=="1"&&keyLen!="8"&&keyLen!="16"){alert("��Կ����Ϊ8��16��"); txtKeyLen.focus(); return false;}
	else if(desType=="2"&&keyLen==""){alert("��ȷ����Կ���ȣ�");  txtKeyLen.focus(); return false;}
	return true;
}
function doSubmit(){
	alert("У��������");
	if(!checkKeyLen(formReg.CDKDes,formReg.CDKLen)) return false;
	if(!checkKeyLen(formReg.PIKDes,formReg.PIKLen)) return false;
	if(!checkKeyLen(formReg.MAKDes,formReg.MAKLen)) return false;
	return confirm("ȷ�����������ύ��");
}	
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%	
	String uid=request.getParameter("uid");
	String flag=request.getParameter("flag");	
	if(flag==null) flag="5";	
%>
<h3>ATM�豸��Կ����</h3>
<form name="formReg" method="post" action="/ToucsMonitor/deviceconfig?reqCode=13002&content=key&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
<input type=hidden name="flag" value=<%=flag%>>
	<table width="80%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4">&nbsp; </td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td width="25%" nowrap bgcolor="#999999">�豸��ţ�</td>
     	 	<td colspan="3" nowrap bgcolor="#FFFFFF"><%=keyInfo.getAtmCode()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4">&nbsp; </td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td width="25%" nowrap bgcolor="#999999">��������Կ���ܷ�ʽ��</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.DES_TYPE,keyInfo.getDesTypeStr(0))%></td>
      		<td width="25%" nowrap bgcolor="#999999">��Կ���ȣ�</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=keyInfo.getCdkLen()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td width="25%" nowrap bgcolor="#999999">����PIN��Կ���ܷ�ʽ��</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.DES_TYPE,keyInfo.getDesTypeStr(1))%></td>
      		<td width="25%" nowrap bgcolor="#999999">��Կ���ȣ�</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=keyInfo.getPikLen()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
     	 	<td width="25%" nowrap bgcolor="#999999">��Ϣ��֤MAC��Կ���ܷ�ʽ��</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.DES_TYPE,keyInfo.getDesTypeStr(2))%></td>
      		<td width="25%" nowrap bgcolor="#999999">��Կ���ȣ�</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=keyInfo.getMakLen()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td width="25%" nowrap bgcolor="#999999">PINBLOCK��ʽ��</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.PINBLOCK,keyInfo.getPinblkMet())%></td>
      		<td width="25%" nowrap bgcolor="#999999">MAC�㷨��</td>
      		<td width="25%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.MAC_METHOD,keyInfo.getMacMet())%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4">&nbsp; </td>
    	</tr>
  	</table>
  	<table width="80%" >
    	<tr> 
      		<td> 
        		<div align="right"> 
          		<input type="button" name="close" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>