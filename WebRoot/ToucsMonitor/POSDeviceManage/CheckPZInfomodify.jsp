<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>
<html>
<head>
	<title>�̻�������Ϣ����</title>
	<link rel="stylesheet" href="../v5_css.css" type="text/css">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC">
<script>
function ChkData(){
  	return true;
}
</script>
<%
  	String uid=request.getParameter("uid");
  	String merchantId  = request.getParameter("merch_id");
  	String merchantName = toucsString.unConvert(request.getParameter("merch_name"));
  	String channelid   = request.getParameter("chann_id");
  	POSMerchantBean PMB=new POSMerchantBean();
  	Vector oV  =new Vector();
  	Hashtable merchantH = new Hashtable();
  	oV = (Vector) PMB.queryMerchantDetail(merchantId,channelid); 
  	merchantH = (Hashtable) oV.get(0);
%>
<h2><font face="����">�޸��̻�<%=merchantName%>�齱��Ϣ</font></h2> 
<hr align="left" noshade>
<form name="merdetailform" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=10422&uid=<%=uid%>&target=Modifypzinfo&channel_id=<%=channelid%>" onsubmit="return ChkData();" >
  	<table width="623" border="0" cellspacing="0" cellpadding="0">
    	<tr> 
      		<td height="25" width="107"><font size="2">�̻����룺</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="merid" size="20" value="<%=merchantH.get("businessman_code")%>" readonly style="BACKGROUND-COLOR: #cccccc"></font>
        	</td>	
      		<td height="25" width="99"><font size="2">�̻����ƣ�</font></td>
      		<td height="25" width="182"> <div align="center"><font size="2">
        		<input type="text" name="mername" size="28" value="<%=merchantName %>" readonly style="BACKGROUND-COLOR: #cccccc"></font></div>
        	</td>	      
    	</tr>
    		<input type="hidden" name="Savedchannel_id" value="<%=channelid%>">
    	<tr> 
      		<td height="25" width="107"><font size="2">һ�Ƚ��ܸ�����</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="PZlevel1num" size="10" value="<%=merchantH.get("prize_num1")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">һ�Ƚ�ף�ǣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="PZlevel1summary" size="12" value="<%=merchantH.get("prize_summary1")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">���Ƚ��ܸ�����</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="PZlevel2num" size="10" value="<%=merchantH.get("prize_num2")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">���Ƚ�ף�ǣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="PZlevel2summary" size="12" value="<%=merchantH.get("prize_summary2")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">���Ƚ��ܸ�����</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="PZlevel3num" size="10" value="<%=merchantH.get("prize_num3")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">���Ƚ�ף�ǣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="PZlevel3summary" size="12" value="<%=merchantH.get("prize_summary3")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">�ĵȽ��ܸ�����</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="PZlevel4num" size="10" value="<%=merchantH.get("prize_num4")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">�ĵȽ�ף�ǣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="PZlevel4summary" size="12" value="<%=merchantH.get("prize_summary4")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" width="107"><font size="2">��Ƚ��ܸ�����</font></td>
      		<td height="25" width="182"> <font size="2"> 
        		<input type="text" name="PZlevel5num" size="10" value="<%=merchantH.get("prize_num5")%>"></font>
        	</td>
      		<td height="25" width="99"><font size="2">��Ƚ�ף�ǣ�</font></td>
      		<td height="25" width="235"> <font size="2"> 
        		<input type="text" name="PZlevel5summary" size="12" value="<%=merchantH.get("prize_summary5")%>"></font>
        	</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="4"> 
        		<hr align="left" noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td height="25" colspan="4"> 
        		<div align="right"> 
          		<input type="submit" name="Submit" value="ȷ��" class="inputbutton">
          		<input type="reset" name="Submit2" value="ȡ��" class="inputbutton" onClick="history.go(-1);">
        		</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
</html>