<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.trandetail.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<html>
<head>
<title>当日交易流水</title>
<%
	String DeviceType="atm";
	String  SysDate = String.valueOf(request.getAttribute("SysDate"));
	Vector tranV = (Vector)request.getAttribute("tranV");
	if(tranV == null ) {
		out.println("传递参数失败！");
	  	return ;
	}
%>
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" >
<h2><font face="隶书">查询当日交易流水</font></h2>【日期：<%=SysDate%>】 
<hr align="left" noshade>
<input type="button" name="Button" value="返回"  class="inputbutton"  onClick="history.go(-1)";>
<hr align="left" noshade>
	<table  width="850" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666"> 
    		<td>ATM联网号</td>
    		<td>交易序号</td>
    		<td>交易日期</td>
    		<td>交易时间</td>
    		<td>主机授权</td>
    		<td>ATMP授权</td>
    		<td>卡号</td>
    		<td>第二卡号</td>
    		<td>交易金额</td>
    		<td>交易名称</td>
    		<td>卡类型（卡种）</td>
    		<td>交易结果</td>
  		</tr>
  		<%
  			for (int i = 0; i < tranV.size(); i++) {
    			TranInfo TI = new TranInfo();
    			TI = (TranInfo)tranV.get(i);
    			if (TI != null) {
  		%>
  		<tr bgcolor="#FFFFFF"> 
    		<td><%=TI.getAtmCode().trim().equals("")?"&nbsp":TI.getAtmCode()%></td>
    		<td><%=TI.getAtmSerial().trim().equals("")?"&nbsp":TI.getAtmSerial()%></td>
    		<td><%=TI.getTransDate().trim().equals("")?"&nbsp":TI.getTransDate()%></td>
    		<td><%=TI.getTransTime().trim().equals("")?"&nbsp":TI.getTransTime()%></td>
    		<td><%=TI.getHostResponse().trim().equals("")?"&nbsp":TI.getHostResponse()%></td> 
    		<td><%=TI.getTransResponse().trim().equals("")?"&nbsp":TI.getTransResponse()%></td>
    		<td><%=TI.getTransCardNo().trim().equals("")?"&nbsp":TI.getTransCardNo()%></td>
    		<td><%=TI.getSndCardNo().trim().equals("")?"&nbsp":TI.getSndCardNo()%></td>
    		<td><%=TI.getTransAmount().trim().equals("")?"&nbsp":TI.getTransAmount()%></td>
    		<td><%=TI.getTransCode().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(DeviceType,TI.getTransCode())%></td>
    		<td><%=TI.getTransCardType().trim().equals("")?"&nbsp":CodeManageBean.getCodeDesc(CodeDefine.TB_CARD_TYPE,TI.getTransCardType())%></td>
    		<td><%=TI.getTransResponse().trim().equals("00")?"成功":"失败"%></td>
  		</tr>
  		<%
   			}else {
    			out.println("哈哈哈，出错啦！");
  			}
		}
  		%>
</table>
<hr align="left" noshade>
</body>
</html>