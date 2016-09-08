<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<jsp:useBean id="prompt" class="com.adtec.toucs.monitor.common.PromptBean" scope="request"/>

<%
Vector orgV = (Vector)request.getAttribute("PrizeInfoV");
POSMerchantBean PMB=new POSMerchantBean();
int  curButton=0;
%>

<script language="javascript">
function ConfirmDel(url){
}
function onOk(){
	var target="<%=request.getParameter("target")%>";
	if(name=="top"){
		parent.location.href=target;
		close();
	}else{
		location.href=target;
	}
}
function   onButtonClk (  ){
        var  target;
        target = "<%=prompt.getBtnURL( curButton )%>";
        document.form1.action= target;
        document.form1.submit();
}
</script>

<%
 	String uid=request.getParameter("uid");
%>
<html>
<head>
<title>Success</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
<style type="text/css">
<!--
.style1 {color: #0000FF}
-->
</style>
</head>
<body onContextMenu="return false;" bgcolor="#CCCCCC">
<span class="style3">
<%
	PrizeRule pr = (PrizeRule)request.getAttribute("PrizeRule");
%>
</span>
<h2> <%=prompt.getTitle()%></h2>
<table  cellspacing="1" cellpadding="1" bordercolor="#000000" border="1" bgcolor="#FFFFFF">
  <tr bgcolor="#FFFFFF">
    <td nowrap><span class="style1"><font size="2">商户号</font></span></td>
    <td nowrap><span class="style1"><font size="2">商户名称</font></span></td>
    <td nowrap><span class="style1"><font size="2">渠道号</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖1</font></span></td>
    <td nowrap><span class="style1"><font size="2">将别1祝辞</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖2</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖别2祝辞</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖3</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖别3祝辞</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖4</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖别4祝辞</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖5</font></span></td>
    <td nowrap><span class="style1"><font size="2">奖别5祝辞</font></span></td>
    <td colspan="2" align="center" nowrap><span class="style1"><font size="2">操作</font></span></td>
  </tr>
  <%
  for (int i = 0; i < orgV.size(); i++) {
  Hashtable orgHT = new Hashtable();
  orgHT = (Hashtable)orgV.get(i);
  if (orgHT != null) {
  %>
  <tr bgcolor="#FFFFFF">
    <td ><%=String.valueOf(orgHT.get("businessman_code")).trim().equals("")?"&nbsp":orgHT.get("businessman_code").toString()%></td>
    <%
       Hashtable merchantNameHT = new Hashtable();
       Vector MerNameV = (Vector)PMB.queryMerchantName(orgHT.get("businessman_code").toString()); 
       merchantNameHT = (Hashtable) MerNameV.get(0);
     if (merchantNameHT != null)
     {
    %>
    <td ><%=String.valueOf(merchantNameHT.get("mct_name")).trim().equals("")?"&nbsp":merchantNameHT.get("mct_name").toString()%></td>
    <%
      }
    %>
    <td ><%=String.valueOf(orgHT.get("channel_code")).trim().equals("")?"&nbsp":orgHT.get("channel_code").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_num1")).trim().equals("")?"&nbsp":orgHT.get("prize_num1").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_summary1")).trim().equals("")?"&nbsp":orgHT.get("prize_summary1").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_num2")).trim().equals("")?"&nbsp":orgHT.get("prize_num2").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_summary2")).trim().equals("")?"&nbsp":orgHT.get("prize_summary2").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_num3")).trim().equals("")?"&nbsp":orgHT.get("prize_num3").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_summary3")).trim().equals("")?"&nbsp":orgHT.get("prize_summary3").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_num4")).trim().equals("")?"&nbsp":orgHT.get("prize_num4").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_summary4")).trim().equals("")?"&nbsp":orgHT.get("prize_summary4").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_num5")).trim().equals("")?"&nbsp":orgHT.get("prize_num5").toString()%></td>
    <td ><%=String.valueOf(orgHT.get("prize_summary5")).trim().equals("")?"&nbsp":orgHT.get("prize_summary5").toString()%></td>
	<%
		if (i==0) {
	%>
    <td align="center"><a href="/ToucsMonitor/POSDeviceManage/CheckPZInfomodify.jsp?uid=<%=uid%>&merch_id=<%=orgHT.get("businessman_code")%>&chann_id=<%=orgHT.get("channel_code")%>&merch_name=<%=merchantNameHT.get("mct_name")%>">修改</a> </td>
	<%
		} else if (pr.getPrizelevel_flag().equals("1")) {
	%>
    <td align="center"><a href="/ToucsMonitor/POSDeviceManage/CheckPZInfomodify.jsp?uid=<%=uid%>&merch_id=<%=orgHT.get("businessman_code")%>&chann_id=<%=orgHT.get("channel_code")%>&merch_name=<%=merchantNameHT.get("mct_name")%>">修改</a> </td>
	<%
		}
	%>
  </tr>
  <%
   }
  else {
    out.println("出错啦,请与系统管理员联系");
  }
}
  %>
  <tr>
    <td colspan="14" nowrap>
      <div align="right">
        <input type="button" name="cancel" value="结束配置" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/pzsucessed.html'">
    </div></td>
  </tr>
</table>
</body>
</html>
