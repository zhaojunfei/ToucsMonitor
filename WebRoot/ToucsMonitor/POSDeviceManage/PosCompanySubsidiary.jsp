<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
<script language="javascript"></script>
<head>
<title>公司表交易明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">

<div>
  <div align="left">
    <table width="100%" border="0">
      <tr>
        <td align="center"><font face="隶书" size="+2">交易明细表</font></td>
      </tr>
    </table>
    <a href="./servletunion/dlservlet?path=/home/weblogic/bea/user_projects/domains/adtecdomain/loadfile/loadSubsidiary/&file=company_detail.xls"  target="_blank"> 下载交易明细信息表 </a>
  </div>
</div>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td colspan="6" nowrap></td>
  </tr>
  <tr bgcolor="#666666">
    <td width="7%" align="center" nowrap>平台流水号</td>
    <td width="6%" align="center" nowrap>设备编号</td>
    <td width="5%" align="center" nowrap>票据号</td>
    <td width="5%" align="center" nowrap>批号</td>
    <td width="5%" align="center" nowrap>商户号</td>
	<td width="8%" align="center" nowrap>平台交易序号</td>
	<td width="11%" align="center" nowrap>交易卡号</td>
	<td width="11%" align="center" nowrap>交易类型</td>
	<td width="6%" align="center" nowrap>交易金额</td>
	<td width="6%" align="center" nowrap>交易日期</td>
	<td width="6%" align="center" nowrap>交易时间</td>
	<td width="7%" align="center" nowrap>交易卡类型</td>
	<td width="6%" align="center" nowrap>柜员号</td>
	<td width="4%" align="center" nowrap>手续费</td>
	<td width="6%" align="center" nowrap>机构代码</td>
	<td width="6%" align="center" nowrap>交易标志</td>
  </tr>
  <%
	Vector v=(Vector)request.getAttribute("PCSubsidiary");

	if(v!=null){
		for(int i=0;i<v.size();i++){
		PosCompanySubsidiary info=(PosCompanySubsidiary)v.get(i);
		
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getSeq_no()%>>
    <td align="center" nowrap><%=info.getSeq_no()%></td>
    <td align="center" nowrap><%=info.getPos_code()%></td>
    <td align="center" nowrap><%=info.getBill_no()%></td>
    <td align="center" nowrap><%=info.getPos_batch()%></td>
    <td align="center" nowrap><%=info.getMerchant_id()%></td>
	<td align="center" nowrap><%=info.getSys_serial()%></td>
	<td align="center" nowrap><%=info.getTrans_card_no()%></td>
	<td align="center" nowrap><%=info.getTrans_code()%></td>
	<td align="center" nowrap><%=info.getTrans_amount()%></td>
	<td align="center" nowrap><%=info.getTrans_date()%></td>
	<td align="center" nowrap><%=info.getTrans_time()%></td>
	<td align="center" nowrap><%=info.getTrans_card_type()%></td>
	<td align="center" nowrap><%=info.getOper_num()%></td>
	<td align="center" nowrap><%=info.getTrans_fee()%></td>
	<td align="center" nowrap><%=info.getAcq_id()%></td>
	<td align="center" nowrap><%=info.getTran_flag()%></td>
  </tr>
  	   <%}
  	}%>
</table>
</body>
</html>