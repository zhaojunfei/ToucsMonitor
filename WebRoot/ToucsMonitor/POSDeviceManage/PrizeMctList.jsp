<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<jsp:useBean id="prompt" class="com.adtec.toucs.monitor.common.PromptBean" scope="request"/>

<%
	Vector orgV = (Vector)request.getAttribute("PrizeInfo");
	System.out.println("Rox" + orgV.size());
	POSMerchantBean PMB = new POSMerchantBean();
	int curButton = 0;
 	String uid=request.getParameter("uid");
%>
<html>
<head>
<title>MctPrizeList</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body  bgcolor="#CCCCCC">
<span class="style3">
<%
	PrizeRule pr = (PrizeRule)request.getAttribute("PrizeRule");
%>
</span>
	<table cellspacing="1" cellpadding="1" bordercolor="#000000" border="1" bgcolor="#FFFFFF">
  		<tr bgcolor="#FFFFFF">
		    <td nowrap><span class="style1"><font size="2">商户号</font></span></td>
		    <td nowrap><span class="style1"><font size="2">商户名称</font></span></td>
		    <td nowrap colspan="2"><span class="style1"><font size="2">奖别一祝辞</font></span></td>
		    <td nowrap colspan="2"><span class="style1"><font size="2">奖别二祝辞</font></span></td>
		    <td nowrap colspan="2"><span class="style1"><font size="2">奖别三祝辞</font></span></td>
		    <td nowrap colspan="2"><span class="style1"><font size="2">奖别四祝辞</font></span></td>
		    <td nowrap colspan="2"><span class="style1"><font size="2">奖别五祝辞</font></span></td>
		    <td colspan="2" align="center" nowrap><span class="style1"><font size="2">操作</font></span></td>
  		</tr>
  		<%
  			for (int i = 0; i < orgV.size(); i++) {
  				Hashtable orgHT = new Hashtable();
  				orgHT = (Hashtable)orgV.get(i);
  				if (orgHT != null) {
  		%>
  		<tr bgcolor="#FFFFFF">
    		<td nowrap><%=String.valueOf(orgHT.get("businessman_code")).trim().equals("")?"&nbsp":orgHT.get("businessman_code").toString()%></td>
    		<td nowrap><%=toucsString.unConvert(PMB.queryMctName(orgHT.get("businessman_code").toString()))%>&nbsp</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_num1")).trim().equals("")?"&nbsp":orgHT.get("prize_num1").toString()%>个</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_summary1")).trim().equals("")?"&nbsp":orgHT.get("prize_summary1").toString()%></td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_num2")).trim().equals("")?"&nbsp":orgHT.get("prize_num2").toString()%>个</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_summary2")).trim().equals("")?"&nbsp":orgHT.get("prize_summary2").toString()%></td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_num3")).trim().equals("")?"&nbsp":orgHT.get("prize_num3").toString()%>个</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_summary3")).trim().equals("")?"&nbsp":orgHT.get("prize_summary3").toString()%></td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_num4")).trim().equals("")?"&nbsp":orgHT.get("prize_num4").toString()%>个</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_summary4")).trim().equals("")?"&nbsp":orgHT.get("prize_summary4").toString()%></td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_num5")).trim().equals("")?"&nbsp":orgHT.get("prize_num5").toString()%>个</td>
    		<td nowrap><%=String.valueOf(orgHT.get("prize_summary5")).trim().equals("")?"&nbsp":orgHT.get("prize_summary5").toString()%></td>
			<%
				if (i==0) {
			%>
    		<td align="center"><a href="/ToucsMonitor/ToucsMonitor/POSDeviceManage/CheckPZInfomodify.jsp?uid=<%=uid%>&merch_id=<%=orgHT.get("businessman_code")%>&chann_id=010310&merch_name=<%=toucsString.unConvert(PMB.queryMctName(orgHT.get("businessman_code").toString()))%>">修改</a> </td>
			<%
				} else if (pr.getPrizelevel_flag().equals("1")) {
			%>
    		<td align="center"><a href="/ToucsMonitor/POSDeviceManage/CheckPZInfomodify.jsp?uid=<%=uid%>&merch_id=<%=orgHT.get("businessman_code")%>&chann_id=010310&merch_name=<%=toucsString.unConvert(PMB.queryMctName(orgHT.get("businessman_code").toString()))%>">修改</a> </td>
			<%
				}
			%>
  		</tr>
  		<%
   			}else {
    			out.println("出错啦,请与系统管理员联系");
  			}
		}
  		%>
</table>
</body>
</html>