<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.trandetail.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<html>
<head>
<title>���ս�����ˮ</title>
<%
	String DeviceType="mit";
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
    		<td>MIT�豸���</td>
    		<td>�豸���ں�</td>
    		<td>�������</td>
    		<td>��������</td>
    		<td>����ʱ��</td>
    		<td>������Ȩ</td>
    		<td>MITP��Ȩ</td>
    		<td>����</td>
    		<td>���׽��</td>
    		<td>��������</td>
    		<td>�����ͣ����֣�</td>
    		<td>�ڶ�����</td>
    		<td>���׽��</td>
  		</tr>
  		<%
  			for (int i = 0; i < tranV.size(); i++) {
    			MITTranInfo MITI = new MITTranInfo();
    			MITI = (MITTranInfo)tranV.get(i);
    			if (MITI != null) {
  		%>
  		<tr bgcolor="#FFFFFF"> 
    		<td><%=MITI.getMitCode().trim().equals("")?"&nbsp":MITI.getMitCode()%></td>
    		<td><%=MITI.getMitCycle().trim().equals("")?"&nbsp":MITI.getMitCycle()%></td>
    		<td><%=MITI.getMitSerial().trim().equals("")?"&nbsp":MITI.getMitSerial()%></td>
    		<td><%=MITI.getTransDate().trim().equals("")?"&nbsp":MITI.getTransDate()%></td>
    		<td><%=MITI.getTransTime().trim().equals("")?"&nbsp":MITI.getTransTime()%></td>
    		<td><%=MITI.getHostResponse().trim().equals("")?"&nbsp":MITI.getHostResponse()%></td> 
    		<td><%=MITI.getTransResponse().trim().equals("")?"&nbsp":MITI.getTransResponse()%></td>
    		<td><%=MITI.getTransCardNo().trim().equals("")?"&nbsp":MITI.getTransCardNo()%></td>
    		<td><%=MITI.getTransAmount().trim().equals("")?"&nbsp":MITI.getTransAmount()%></td>
    		<td><%=MITI.getTransCode().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(DeviceType,MITI.getTransCode())%></td>
    		<td><%=MITI.getTransCardType().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(CodeDefine.TB_CARD_TYPE,MITI.getTransCardType())%></td>
    		<td><%=MITI.getSndCardNo().trim().equals("")?"&nbsp":MITI.getSndCardNo()%></td>
    		<td><%=MITI.getTransResponse().trim().equals("0000")?"�ɹ�":"ʧ��"%></td>
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