<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.trandetail.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<html>
<head>
<title>���ս�����ˮ</title>
<%
	String DeviceType="cdm";
	String  SysDate = String.valueOf(request.getAttribute("SysDate"));
	Vector tranV = (Vector)request.getAttribute("tranV");
	if(tranV == null ) {
  		out.println("���ݲ���ʧ�ܣ�");
  		return ;
	}
%>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" >
<h2><font face="����">��ѯ���ս�����ˮ</font></h2>�����ڣ�<%=SysDate%>��
<hr align="left" noshade>
<input type="button" name="Button" value="����"  class="inputbutton"  onClick="history.go(-1)";>
<hr align="left" noshade>
	<table  width="850" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666"> 
   	 		<td>CDM�豸���</td>
    		<td>�豸���ں�</td>
    		<td>�������</td>
    		<td>��������</td>
    		<td>����ʱ��</td>
    		<td>������Ȩ</td>
    		<td>CDMP��Ȩ</td>
    		<td>����</td>
    		<td>���׽��</td>
    		<td>������</td>
    		<td>��������</td>
    		<td>�����ͣ����֣�</td>
    		<td>�ڶ�����</td>
    		<td>���׽��</td>
  		</tr>
  		<%
  			for (int i = 0; i < tranV.size(); i++) {
    		CDMTranInfo CTI = new CDMTranInfo();
    		CTI = (CDMTranInfo)tranV.get(i);
    		if (CTI != null) {
  		%>
  		<tr bgcolor="#FFFFFF"> 
    		<td><%=CTI.getCdmCode().trim().equals("")?"&nbsp":CTI.getCdmCode()%></td>
    		<td><%=CTI.getCdmCycle().trim().equals("")?"&nbsp":CTI.getCdmCycle()%></td>
    		<td><%=CTI.getCdmSerial().trim().equals("")?"&nbsp":CTI.getCdmSerial()%></td>
    		<td><%=CTI.getTransDate().trim().equals("")?"&nbsp":CTI.getTransDate()%></td>
    		<td><%=CTI.getTransTime().trim().equals("")?"&nbsp":CTI.getTransTime()%></td>
    		<td><%=CTI.getHostResponse().trim().equals("")?"&nbsp":CTI.getHostResponse()%></td> 
    		<td><%=CTI.getTransResponse().trim().equals("")?"&nbsp":CTI.getTransResponse()%></td>
    		<td><%=CTI.getTransCardNo().trim().equals("")?"&nbsp":CTI.getTransCardNo()%></td>
    		<td><%=CTI.getTransAmount().trim().equals("")?"&nbsp":CTI.getTransAmount()%></td>
    		<td><%=CTI.getTransCode().trim().equals("")?"&nbsp":CTI.getTransCode()%></td>
    		<td><%=CTI.getTransCode().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(DeviceType,CTI.getTransCode())%></td>
    		<td><%=CTI.getTransCardType().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(CodeDefine.TB_CARD_TYPE,CTI.getTransCardType())%></td>
    		<td><%=CTI.getSndCardNo().trim().equals("")?"&nbsp":CTI.getSndCardNo()%></td>
    		<td><%=CTI.getTransResponse().trim().equals("00")?"�ɹ�":"ʧ��"%></td>
  		</tr>
  		<%
   			}else {
    			out.println("����������������");
  			}
		}
  		%>
</table>
<hr align="left" noshade>
</body>
</html>