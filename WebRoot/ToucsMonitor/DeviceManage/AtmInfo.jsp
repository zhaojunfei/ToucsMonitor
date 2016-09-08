<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>

<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
var curTab;
function selTab(idx,tab){
	//alert(idx);
	radioBtn=document.formReg.rdTab[idx];
	//alert(radioBtn.checked)
	if(radioBtn.checked){
		tab.style.display="";
		if(curTab!=null){
			curTab.style.display="none";
		}
		curTab=tab;		
	}	
}
function viewAtmStat(atmCode){
	var targetUrl="devicemanage?reqCode=02010&atmCode="+atmCode;
	window.open(targetUrl,"atmStat","HEIGHT=450,WIDTH=600,scrollbars,resizable,dependent")
}

function doSubmit(){	
	var formReg=document.formReg;	
	<!--if(formReg.atmCode.value==""){alert("�������豸��ţ�");formReg.atmCode.focus();return false;}-->
	if(!onAtmCode(formReg.atmCode,true)){formReg.atmCode.focus(); return false;}
	if(formReg.orgId.value==""){alert("��ȷ�����豸����������");formReg.orgId.focus();return false;}
	if(formReg.areaNo.value==""){alert("��ȷ����װ�ص��������أ�");formReg.areaNo.focus();return false;}
	if(formReg.atmType.value==""){alert("��ȷ���豸���ͣ�");formReg.atmType.focus();return false;}
	if(formReg.atmSn.value==""){alert("��ȷ���豸��ţ�SN����");formReg.atmSn.focus();return false;}
	if(formReg.atmTn.value==""){alert("��ȷ���豸���ٺţ�");formReg.atmTn.focus();return false;}
	if(formReg.cardType.value==""){alert("��ȷ�����׿��֣�");formReg.cardType.focus();return false;}
	if(formReg.currencyCode.value==""){alert("��ȷ�����֣�");formReg.currencyCode.focus();return false;}
	if(formReg.translateName.value==""){alert("��ȷ�����Ĵ����׼��");formReg.translateName.focus();return false;}
	if(formReg.msgType.value==""){alert("��ȷ���������ͣ�");formReg.msgType.focus();return false;}
	if(formReg.netType.value==""){alert("��ȷ��ͨѶЭ�����ͣ�");formReg.netType.focus();return false;}
	if(formReg.tellerId.value==""){alert("�������Ա�ţ�");formReg.tellerId.focus();return false;}
	if(formReg.netAddress.value==""){alert("�������ն�IP��ATM����ͨѶ��ַ����");formReg.netAddress.focus();return false;}
	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">	
<%
	String popUp=request.getParameter("popUp");								
%>
<h3><font face="����">ATM�豸������Ϣ</font></h3>
	<table width="100%" id="tab0" bgcolor="#333333" border="0" cellpadding="1" cellspacing="1">
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>�������� </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999" >���(������)��</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmCode()%></td>
      		<td width="25%" nowrap bgcolor="#999999">ATM��C�����ţ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmCCode()%> </td>
    	</tr>
     	<tr> 
      		<td width="25%" nowrap bgcolor="#999999" >ATM��DCC�����ţ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmDccCode()%></td>
      		<td width="25%" nowrap bgcolor="#999999">�豸���ࣺ</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_TYPE,atmInfo.getAtmType())%> </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">���ƣ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��Ҫ�ȣ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_LEVEL,atmInfo.getAtmLevel())%> </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">��װ��λ��</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetUnit()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap height="18" bgcolor="#999999"> 
        		<div align="left">����λ��</div>
      		</td>
      		<td colspan="3" height="18" bgcolor="#FFFFFF"> 
        		<div align="left"><%=atmInfo.getAtmMngUnit()%></div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">����Ա������</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getMngName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�绰��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getMngPhone()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��������(֧��)��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"> 
        		<div align="left"><%=CodeManageBean.getCodeDesc("orgname",atmInfo.getOrgId())%></div>
      		</td>
      		<td width="25%" bgcolor="#999999">�������أ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc("areaname",atmInfo.getAreaNo())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��װ�ص����</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ADDR_TYPE,atmInfo.getAtmAddrType())%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left"></div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap height="20" bgcolor="#999999"> 
        		<div align="left">��ϸ��ַ��</div>
      		</td>
      		<td colspan="3" height="20" bgcolor="#FFFFFF"> 
        		<div align="left"><%=atmInfo.getAtmSetAddr()%></div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">       
      			<div align="left">����װʱ�䣺</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetDate()%></td>
      		<td width="25%" nowrap bgcolor="#999999">         
		    	<div align="left">���Ӱ�װʱ�䣺</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getProgSetDate()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">         
      			<div align="left">��ͨ���ڣ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getConnectDate()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left"></div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr> 
      		<td nowrap width="25%" bgcolor="#999999">�豸״̬�� </td>
      		<td nowrap width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_STAT,atmInfo.getAtmStat())%></td>
      		<td nowrap width="25%" bgcolor="#999999">����״̬��        
      		</td>
      		<td nowrap width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.RUN_STAT,atmInfo.getRunStat())%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>�������� </td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">����(Ʒ��)��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_TYPE,atmInfo.getAtmType())%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�ͺţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmMode()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">SN��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSn()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">TN��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmTn()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">���Σ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetDegree()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">���׿��֣�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CARD_REF_TYPE,atmInfo.getCardType())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">���֣�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CENTER_TYPE,atmInfo.getCurrencyCode())%></td>
      		<td width="25%" nowrap bgcolor="#999999">���������ͣ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.READER_MODE,atmInfo.getCardReaderMode())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">���Ĵ����׼��</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getTranslateName()%></td>
      		<td width="25%" nowrap bgcolor="#999999">�������ͣ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.MSG_TYPE,atmInfo.getMsgType())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">ͨѶЭ�����ͣ�</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.NET_TYPE,atmInfo.getNetType())%></td>
      		<td width="25%" nowrap bgcolor="#999999">��</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>�������� </td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�����У�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getUnionName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">���ţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getBranchNo()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�����ţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getChangeNo()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">���кţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getUnionNo()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">֧�кţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAgencyNo()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�����ţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getOrgId()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�ʻ��ţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getBankAcc()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��Ա�ţ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getTellerId()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap> ��������</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">����IP��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getHostAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�����˿ڣ�</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getHostPort()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�ն�IP��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getNetAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">�������룺</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getNetMask()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">·��IP��</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getRouteAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��������</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getJumpNums()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap> ��ע</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��ע1��</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo1())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��ע2��</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo2())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">��ע3��</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo3())%></td>
    	</tr>
  	</table>
  	<table width="100%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<div align="right">
          		<!--input type="button" name="state" value="�豸״̬" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=13003&content=state&atmCode=<%=atmInfo.getAtmCode()%>'">
          		<input type="button" name="tranFlow" value="������ˮ" class="inputbutton" onClick="javascript:alert('�鿴������ˮ!')"-->
		  		<%if(popUp==null||popUp.equals("1")){%>
          		<input type="button" name="cancel" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
		  		<%}%>
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</body>
</html>