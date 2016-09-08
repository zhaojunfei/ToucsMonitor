<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<jsp:useBean id="prompt" class="com.adtec.toucs.monitor.common.PromptBean" scope="request"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.style1 {
	font-size: 14px;
	font-weight: bold;
	color: #0000FF;
}
.style17 {color: #0065CA}
.style18 {color: #FF6633}
.style19 {color: #0066FF}
-->
</style>
</head>

<%
   String uid=request.getParameter("uid");
   Vector PZV = (Vector)request.getAttribute("CheckPQueryVect");
   
%>
<body>
<form name="form1" method="post" action="">
<div align="center">
    <table width="987" height="174" border="1">
    	<tr>
        	<th colspan="9" scope="col"><span class="style1">查 询 抽 奖 结 果</span></th>
      	</tr>
      	<tr>
        	<td width="39" nowrap><div align="center" class="style17"><strong>NO.</strong></div></td>
        	<td width="150" nowrap><span class="style17"><strong>商户号</strong></span></td>
       	 	<td width="180" nowrap><div align="center" class="style17"><strong><strong>POS设备号</strong></strong></div></td>
        	<td width="178" nowrap><div align="center" class="style17"><strong>卡号</strong></div></td>
       	 	<td width="57" nowrap><div align="center" class="style17"><strong>交易额</strong></div></td>
        	<td width="85" nowrap><div align="center" class="style17"><strong>交易日期</strong></div></td>
        	<td width="80" nowrap><div align="center" class="style17"><strong>交易时间</strong></div></td>
        	<td width="61" nowrap><div align="center" class="style17"><strong>卡类型</strong></div></td>
        	<td width="99" nowrap><div align="center" class="style17"><strong>中奖内容</strong></div></td>
      	</tr>
	  	<%
	  		int j =0;
      		for (int i = 0; i < PZV.size(); i++) {	  
	  			j++;
      			Hashtable orgHT = new Hashtable();
      			orgHT = (Hashtable)PZV.get(i);
      			if (orgHT != null) {
      	%>
      	<tr>
        	<td nowrap><span class="style19"><%= j%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("merchant_id")).trim().equals("")?"&nbsp":orgHT.get("merchant_id").toString()%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("pos_dcc_code")).trim().equals("")?"&nbsp":orgHT.get("pos_dcc_code").toString()%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("trans_card_no")).trim().equals("")?"&nbsp":orgHT.get("trans_card_no").toString()%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("trans_amount")).trim().equals("")?"&nbsp":orgHT.get("trans_amount").toString()%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("trans_date")).trim().equals("")?"&nbsp":orgHT.get("trans_date").toString()%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("trans_time")).trim().equals("")?"&nbsp":orgHT.get("trans_time").toString()%></span></td>
        	<td nowrap><span class="style18"><%=CodeManageBean.getCodeDesc("0290",orgHT.get("trans_card_type").toString())%></span></td>
        	<td nowrap><span class="style18"><%=String.valueOf(orgHT.get("demo2 ")).trim().equals("")?"&nbsp":orgHT.get("demo2").toString()%></span></td>
      	</tr>
	  	<%
	    	}
  	  	}
		%>
      	<tr>
        	<td colspan="9">
          		<div align="center">
            	<input type="button" name="Close" value="关闭此次查询" onClick="javascript:location.href='/ToucsMonitor/posmerchantconfig?reqCode=10422&target=prizeQuery&uid=<%=uid%>'"></div>
            </td>
      	</tr>
    </table>
</div>
</form>
</body>
</html>
