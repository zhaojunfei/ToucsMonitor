<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.trandetail.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<html>
<head>
<title>���ս�����ˮ</title>
<%
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
<h2><font face="����">��ѯ���ս�����ˮ</font></h2>
�����ڣ�<%=SysDate%>��
��  <%if(request.getParameter("pos_kind").trim().equalsIgnoreCase("1"))%>POS-P���:
                    <%if(request.getParameter("pos_kind").trim().equalsIgnoreCase("2"))%>POS-C���:
                    <%if(request.getParameter("pos_kind").trim().equalsIgnoreCase("3"))%>POS-DCC���:
                    <%=request.getParameter("poscode")%>��

<hr align="left" noshade>
<input type="button" name="Button" value="����"  class="inputbutton"  onClick="history.go(-1)";>
<hr align="left" noshade>
	<table  width="850" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666" >
   			<td><font color="#ffffff" >����ʱ��</font></td>
  			<!--<td>������Ȩ</td> -->
    		<td><font color="#ffffff" >POSP��Ȩ</font></td>
    		<td><font color="#ffffff" >����</font></td>
    		<td><font color="#ffffff" >���׽��</font></td>
    		<td><font color="#ffffff" >���</font></td>
    		<td><font color="#ffffff" >��������</font></td>
    		<td><font color="#ffffff" >�����ͣ����֣�</font></td>
    		<td><font color="#ffffff" >���׽��</font></td>
  		</tr>
  		<%
  			for (int i = 0; i < tranV.size(); i++) {
    			PosTranInfo PTI = new PosTranInfo();
    			PTI = (PosTranInfo)tranV.get(i);
    			if (PTI != null) {
  		%>
  		<tr bgcolor="#FFFFFF">
    		<td><%=PTI.getTransTime().trim().equals("")?"&nbsp":PTI.getTransTime()%></td>
    		<td><%=PTI.getTransResponse().trim().equals("")?"&nbsp":PTI.getTransResponse()%></td>
    		<td><%=PTI.getTransCardNo().trim().equals("")?"&nbsp":PTI.getTransCardNo()%></td>
    		<td><%=PTI.getTransAmount().trim().equals("")?"&nbsp":PTI.getTransAmount()%></td>
    		<td><%=PTI.getSysSerial().trim().equals("")?"&nbsp":PTI.getSysSerial()%></td>
    		<td><%=PTI.getTransCode().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc("pos",PTI.getTransCode())%></td>
    		<td><%=PTI.getTransCardType().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(CodeDefine.TB_CARD_TYPE,PTI.getTransCardType())%></td>
    		<td><%=PTI.getTransResponse().trim().equals("00")?"�ɹ�":"ʧ��"%></td>
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