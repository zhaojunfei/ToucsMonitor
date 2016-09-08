<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>



<%!
	private String getOperLink(String reqCode,String target,String pos_code,String uid){
		String ret="/ToucsMonitor/posdeviceconfig?reqCode="+reqCode+"&uid="+uid
			+"&target="+target+"&pos_code="+pos_code;
		return ret;
	}

%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
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
	System.out.println("PosMerchantList.jsp begin");
	String uid=request.getParameter("uid");
	String permFlag=(String)request.getAttribute("POSINFOREG");
	boolean canAdd=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOUPDATE");
	boolean canModify=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFODELETE");
	boolean canDel=(permFlag!=null&&permFlag.equals("1"));

	permFlag=(String)request.getAttribute("POSINFOQUERY");
	boolean canQuery=(permFlag!=null&&permFlag.equals("1"));

    permFlag=(String)request.getAttribute("POSTXNSET");
    boolean canSetTxn=(permFlag!=null&&permFlag.equals("1"));

    permFlag=(String)request.getAttribute("POSINFOSTART");
    boolean canStart=(permFlag!=null&&permFlag.equals("1"));

    permFlag=(String)request.getAttribute("POSINFOSTOP");
	boolean canStop=(permFlag!=null&&permFlag.equals("1"));

%>
<div>
  <div align="left">�豸�б� </div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td width="4%" align="center" nowrap>���</td>
    <td width="10%" align="center" nowrap>POS˳���</td>
    <td width="13%" align="center" nowrap>19λPOS���</td>
    <td width="15%" align="center" nowrap>��������</td>
    <td width="18%" align="center" nowrap>�̻�</td>
    <td width="7%" align="center" nowrap>����״̬</td>
    <td width="33%" align="center" nowrap>����</td>
  </tr>
  <%
  			Vector v=(Vector)request.getAttribute("posInfoList");
			String pos_code="";
                        String posdcc_code="";
  			if(v!=null){
	        	for(int i=0;i<v.size();i++){
				POSExInfo info=(POSExInfo)v.get(i);
				pos_code=info.getPoscode();
                                posdcc_code=info.getdcccode();
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getPoscode()%>>
    <td align="center" nowrap><%=i+1%></td>
    <td align="center" nowrap><%=pos_code%></td>
    <td align="center" nowrap><%=posdcc_code%></td>
    <td align="center" nowrap><%=info.getOrgname()%></td>
    <td align="center" nowrap><%=info.getMctname()%></td>
    <td align="center" nowrap><%=info.getPosstat().equals("1")?"����":"<font style='color:#FF0000' >ͣ��</font>"%></td>
    <td align="center">
        <%if(canModify){%>
        <a href="<%=getOperLink("13102","queryPage",posdcc_code,uid)%>">[�޸�] </a>
        <a href="<%=getOperLink("13102","keyPage",posdcc_code,uid)%>">[��Կ]</a>
        <%}%>
        <%if(canDel){%>
        <a href="javascript:onDelete('<%=getOperLink("13104","",pos_code,uid)%>')">[ɾ��]</a>
        <%}%>
        <%if(canStart){%>
        <a href="<%=getOperLink("13102","setStart",pos_code,uid)%>">[����]</a>
        <%}%>
        <%if(canStop){%>
        <a href="<%=getOperLink("13102","setStop",pos_code,uid)%>">[ͣ��]</a>
        <%}%>
        <%if(canSetTxn){%>
        <a href="<%=getOperLink("13105","txnSet",posdcc_code,uid)%>">[����Ȩ��]</a>
        <%}%>
    </td>
  </tr>
  <%}
  			}%>
</table>
</body>
</html>