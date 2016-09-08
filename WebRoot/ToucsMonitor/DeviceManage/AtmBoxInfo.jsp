<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<jsp:useBean id="boxInfo" class="com.adtec.toucs.monitor.devicemanage.AtmBoxInfo"  scope="request"/>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000">
<%	
	String flag=request.getParameter("flag");	
	if(flag==null) flag="6";	
%>
<h3><font face="隶书">ATM设备钞箱配置信息</font></h3>
	<table width="75%" bgcolor="#333333" border="0" cellpadding="1" cellspacing="1">
  		<tr> 
    		<td nowrap bgcolor="#999999"> 设备编号：</td>
    		<td nowrap colspan="2" bgcolor="#FFFFFF"><%=boxInfo.getAtmCode()%></td>
  		</tr>
  		<tr> 
    		<td colspan="3" nowrap bgcolor="#CCCCCC">&nbsp;</td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#999999">&nbsp;</td>
    		<td width="16%" nowrap bgcolor="#999999">币种</td>
    		<td width="15%" nowrap bgcolor="#999999">面值</td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱1</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(0))%></td>
   	 		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(0)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱2</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(1))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(1)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱3</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(2))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(2)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱4</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(3))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(3)%></td>
  		</tr>
  		<!--如果配置5_8号钞箱-->
  		<%if(boxInfo.getFlagBox5_8()){%>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱5</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(4))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(4)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱6</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(5))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(5)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱7</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(6))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(6)%></td>
  		</tr>
  		<tr> 
    		<td width="12%" nowrap bgcolor="#FFFFFF">钞箱8</td>
    		<td width="16%" nowrap bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(7))%></td>
    		<td width="15%" nowrap bgcolor="#FFFFFF"><%=boxInfo.getBoxPara(7)%></td>
  		</tr>
  		<%}%>
	</table>
	<table width="75%">
  		<tr>
    		<td> 
      		<div align="right">
        		<input type="button" name="close" value="确定" class="inputbutton" onClick="javascript:history.go(-1)">
      		</div>
    		</td>
  		</tr>
	</table>
<h3>&nbsp;</h3>
</body>
</html>