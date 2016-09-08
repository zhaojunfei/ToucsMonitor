<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.trandetail.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<html>
<head>
<title>当日交易流水</title>
<%
	String DeviceType="mit";
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
    		<td>MIT设备编号</td>
    		<td>设备周期号</td>
    		<td>交易序号</td>
    		<td>交易日期</td>
    		<td>交易时间</td>
    		<td>主机授权</td>
    		<td>MITP授权</td>
    		<td>卡号</td>
    		<td>交易金额</td>
    		<td>交易名称</td>
    		<td>卡类型（卡种）</td>
    		<td>第二卡号</td>
    		<td>交易结果</td>
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
    		<td><%=MITI.getTransResponse().trim().equals("0000")?"成功":"失败"%></td>
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