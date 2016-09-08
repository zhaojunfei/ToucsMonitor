<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>
<jsp:useBean id="statInfo" class="com.adtec.toucs.monitor.devicemanage.ATMStatInfo"  scope="request"/>
<jsp:useBean id="boxInfo" class="com.adtec.toucs.monitor.devicemanage.AtmBoxInfo"  scope="request"/>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onConfig(atmCode){
	if(atmCode=="")
		return;
	var url="/ToucsMonitor/deviceconfig?reqCode=13003&content=base&atmCode="+atmCode;
	location.href=url;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String popUp=request.getParameter("popUp");
	if(atmInfo.getBoxInfo()!=null)
		boxInfo=atmInfo.getBoxInfo();
	if(atmInfo.getStatInfo()!=null)
		statInfo=atmInfo.getStatInfo();
%>
<h3><font face="隶书">ATM设备状态</font></h3>
	<table width="100%" bgcolor="#333333" border="0" cellpadding="1" cellspacing="1">
 		<tr bgcolor="#CCCCCC"> 
    		<td colspan="6" nowrap> 属性 
      		<%if(popUp!=null&&popUp.equals("1")){%>
      		<input type="button" name="config" value="详细资料" class="inputbutton" onClick="onConfig('<%=atmInfo.getAtmCode()%>')">
      		<%}%>
    		</td>
  		</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">设备编号：</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmCode()%></td>
	    	<td width="16%" nowrap bgcolor="#999999">型号：</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmMode()%></td>
	    	<td width="12%" nowrap bgcolor="#999999">序列号：</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmSn()%></td>
	  	</tr>
	 	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">安装地点：</td>
	    	<td colspan="5" nowrap><%=atmInfo.getAtmSetAddr()%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">安装日期：</td>
	    	<td width="15%" nowrap><%=atmInfo.getAtmSetDate()%></td>
	   	 	<td width="16%" nowrap bgcolor="#999999">联网运行日期：</td>
	   	 	<td width="15%" nowrap><%=atmInfo.getConnectDate()%></td>
	    	<td width="12%" nowrap bgcolor="#999999">&nbsp;</td>
	    	<td width="15%" nowrap>&nbsp;</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">所属机构：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc("orgname",atmInfo.getOrgId())%></td>
	    	<td width="16%" nowrap bgcolor="#999999">地区：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc("areaname",atmInfo.getAreaNo())%></td>
	    	<td nowrap bgcolor="#999999">&nbsp; </td>
	    	<td nowrap bgcolor="#FFFFFF">&nbsp;</td>
	  	</tr>
	  	<tr bgcolor="#CCCCCC"> 
	    	<td colspan="6" nowrap> 钞箱</td>
	  	</tr>
	  	<tr bgcolor="#999999"> 
	  	  	<td width="12%" nowrap>&nbsp;</td>
	    	<td width="15%" nowrap>状态</td>
	    	<td width="16%" nowrap>币种</td>
	    	<td width="15%" nowrap>面值</td>
	    	<td width="12%" nowrap>张数</td>
	    	<td width="15%" nowrap>金额</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	   	 	<td width="12%" nowrap>钞箱1</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(1))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(0))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(0)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(0)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(0)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>钞箱2</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(2))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(1))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(1)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(1)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(1)%></td>
	  	</tr>
	 	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>钞箱3</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(3))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(2))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(2)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(2)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(2)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap>钞箱4</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.BOX_STAT,statInfo.getChashBoxStat(4))%></td>
	    	<td width="16%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CURRENCY_TYPE,boxInfo.getBoxCode(3))%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxPara(3)%></td>
	    	<td width="12%" nowrap><%=boxInfo.getBoxCnt(3)%></td>
	    	<td width="15%" nowrap><%=boxInfo.getBoxAmt(3)%></td>
	  	</tr>
	  	<tr bgcolor="#CCCCCC"> 
	    	<td colspan="6" nowrap> 模块状态</td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">流水打印机：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.FLOW_PRN_STAT,statInfo.atmJnl)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">凭条打印机：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.RECPT_PRN_STAT,statInfo.atmRecpt)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">读卡器： </td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.READW_STAT,statInfo.atmReadW)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">IC卡读卡模块：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.ICREAD_STAT,statInfo.ICRead)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">硬加密模块：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.DEM_STAT, statInfo.atmDem)%></td>
	    	<td nowrap bgcolor="#999999">硬盘：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.HD_STAT,statInfo.hardDisk)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">PSAM模块：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.PSAM_STAT,statInfo.atmPsam)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">键盘：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.KEYBOARD_STAT,statInfo.atmKeyboard)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">保险箱：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.SAFE_BOX_STAT,statInfo.safeBox)%></td>
	  	</tr>
	  	<tr bgcolor="#FFFFFF"> 
	    	<td width="12%" nowrap bgcolor="#999999">控制板：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.CTRL_FACE_STAT,statInfo.ctrlFace)%></td>
	    	<td width="16%" nowrap bgcolor="#999999">网络状态：</td>
	    	<td width="15%" nowrap><%=CodeManageBean.getCodeDesc(CodeDefine.NET_STAT,statInfo.netStat)%></td>
	    	<td width="12%" nowrap bgcolor="#999999">&nbsp;</td>
	    	<td width="15%" nowrap>&nbsp;</td>
	  	</tr>
	</table>
	<table width="100%" border="0">
  		<tr> 
    		<td> 
      		<div align="right"> </div>
      		<div align="right"> 
	  		<%if(popUp==null||!popUp.equals("1")){%>
        	<input type="button" name="close" value="确定" class="inputbutton" onClick="javascript:history.go(-1)">
			<%}%>
      		</div>
    		</td>
  		</tr>
	</table>
<p/>
</body>
</html>