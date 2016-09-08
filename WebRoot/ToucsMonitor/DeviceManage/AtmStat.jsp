<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>
<jsp:useBean id="statInfo" class="com.adtec.toucs.monitor.devicemanage.ATMStatInfo"  scope="request"/>
<jsp:useBean id="boxInfo" class="com.adtec.toucs.monitor.devicemanage.AtmBoxInfo"  scope="request"/>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onConfig(atmCode){
	if(atmCode=="")
		return;
	var url="/ToucsMonitor/deviceconfig?reqCode=13003&content=base&atmCode="+atmCode;
	location.href=url;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String popUp=request.getParameter("popUp");
	if(atmInfo.getBoxInfo()!=null)
		boxInfo=atmInfo.getBoxInfo();
	if(atmInfo.getStatInfo()!=null)
		statInfo=atmInfo.getStatInfo();
%>
<h3><font face="����">ATM�豸״̬</font></h3>
	<table width="100%" bgcolor="#333333" border="0" cellpadding="1" cellspacing="1">
 		<tr bgcolor="#CCCCCC"> 
    		<td colspan="6" nowrap> ���� 
      		<%if(popUp!=null&&popUp.equals("1")){%>
      		<input type="button" name="config" value="��ϸ����" class="inputbutton" onClick="onConfig('<%=atmInfo.getAtmCode()%>')">
      		<%}%>
    		</td>
  		</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">�豸��ţ�</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmCode()%></td>
	    	<td width="16%" nowrap bgcolor="#999999">�ͺţ�</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmMode()%></td>
	    	<td width="12%" nowrap bgcolor="#999999">���кţ�</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmSn()%></td>
	  	</tr>
	 	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">��װ�ص㣺</td>
	    	<td colspan="5" nowrap><%=atmInfo.getAtmSetAddr()%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">��װ���ڣ�</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmSetDate()%></td>
	   	 	<td width="16%" nowrap bgcolor="#999999">�����������ڣ�</td>
	   	 	<td width="15%" nowrap><%=atmInfo.getConnectDate()%></td>
	    	<td width="12%" nowrap bgcolor="#999999">&nbsp;</td>
	    	<td width="15%" nowrap>&nbsp;</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">����������</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc("orgname",atmInfo.getOrgId())%></td>
	    	<td width="16%" nowrap bgcolor="#999999">������</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc("areaname",atmInfo.getAreaNo())%></td>
	    	<td nowrap bgcolor="#999999">&nbsp; </td>
	    	<td nowrap bgcolor="#FFFFFF">&nbsp;</td>
	  	</tr>
	  	<tr bgcolor="#CCCCCC"> 
	    	<td colspan="6" nowrap> ����</td>
	  	</tr>
	  	<tr bgcolor="#999999"> 
	  	  	<td width="12%" nowrap>&nbsp;</td>
	    	<td width="15%" nowrap>״̬</td>
	    	<td width="16%" nowrap>����</td>
	    	<td width="15%" nowrap>��ֵ</td>
	    	<td width="12%" nowrap>����</td>
	    	<td width="15%" nowrap>���</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	   	 	<td width="12%" nowrap>����1</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(1))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(0))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(0)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(0)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(0)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>����2</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(2))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(1))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(1)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(1)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(1)%></td>
	  	</tr>
	 	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>����3</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(3))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(2))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(2)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(2)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(2)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>����4</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(4))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(3))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(3)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(3)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(3)%></td>
	  	</tr>
	  	<tr bgcolor="#CCCCCC"> 
	    	<td colspan="6" nowrap> ģ��״̬</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">��ˮ��ӡ����</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.FLOW_PRN_STAT,statInfo.atmJnl)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">ƾ����ӡ����</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.RECPT_PRN_STAT,statInfo.atmRecpt)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">�������� </td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.READW_STAT,statInfo.atmReadW)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">IC������ģ�飺</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.ICREAD_STAT,statInfo.ICRead)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">Ӳ����ģ�飺</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.DEM_STAT, statInfo.atmDem)%></td>
	    	<td nowrap bgcolor="#999999">Ӳ�̣�</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.HD_STAT,statInfo.hardDisk)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">PSAMģ�飺</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.PSAM_STAT,statInfo.atmPsam)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">���̣�</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.KEYBOARD_STAT,statInfo.atmKeyboard)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">�����䣺</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.SAFE_BOX_STAT,statInfo.safeBox)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">���ư壺</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CTRL_FACE_STAT,statInfo.ctrlFace)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">����״̬��</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.NET_STAT,statInfo.netStat)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">&nbsp;</td>
	    	<td width="15%" nowrap>&nbsp;</td>
	  	</tr>
	</table>
	<table width="100%" border="0">
  		<tr> 
    		<td> 
      		<div align="right"> </div>
      		<div align="right"> 
	  		<%if(popUp==null||!popUp.equals("1")){%>
        	<input type="button" name="close" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
			<%}%>
      		</div>
    		</td>
  		</tr>
	</table>
<p/>
</body>
</html>