<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>

<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
var curTab;
function selTab(idx,tab){
	//alert(idx);
	radioBtn=document.formReg.rdTab[idx];
	//alert(radioBtn.checked)
	if(radioBtn.checked){
		tab.style.display="";
		if(curTab!=null){
			curTab.style.display="none";
		}
		curTab=tab;		
	}	
}
function viewAtmStat(atmCode){
	var targetUrl="devicemanage?reqCode=02010&atmCode="+atmCode;
	window.open(targetUrl,"atmStat","HEIGHT=450,WIDTH=600,scrollbars,resizable,dependent")
}

function doSubmit(){	
	var formReg=document.formReg;	
	<!--if(formReg.atmCode.value==""){alert("请输入设备编号！");formReg.atmCode.focus();return false;}-->
	if(!onAtmCode(formReg.atmCode,true)){formReg.atmCode.focus(); return false;}
	if(formReg.orgId.value==""){alert("请确定该设备所属机构！");formReg.orgId.focus();return false;}
	if(formReg.areaNo.value==""){alert("请确定安装地点所属区县！");formReg.areaNo.focus();return false;}
	if(formReg.atmType.value==""){alert("请确定设备类型！");formReg.atmType.focus();return false;}
	if(formReg.atmSn.value==""){alert("请确定设备序号（SN）！");formReg.atmSn.focus();return false;}
	if(formReg.atmTn.value==""){alert("请确定设备跟踪号！");formReg.atmTn.focus();return false;}
	if(formReg.cardType.value==""){alert("请确定交易卡种！");formReg.cardType.focus();return false;}
	if(formReg.currencyCode.value==""){alert("请确定币种！");formReg.currencyCode.focus();return false;}
	if(formReg.translateName.value==""){alert("请确定报文传输标准！");formReg.translateName.focus();return false;}
	if(formReg.msgType.value==""){alert("请确定报文类型！");formReg.msgType.focus();return false;}
	if(formReg.netType.value==""){alert("请确定通讯协议类型！");formReg.netType.focus();return false;}
	if(formReg.tellerId.value==""){alert("请输入柜员号！");formReg.tellerId.focus();return false;}
	if(formReg.netAddress.value==""){alert("请输入终端IP（ATM网络通讯地址）！");formReg.netAddress.focus();return false;}
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">	
<%
	String popUp=request.getParameter("popUp");								
%>
<h3><font face="隶书">ATM设备配置信息</font></h3>
	<table width="100%" id="tab0" bgcolor="#333333" border="0" cellpadding="1" cellspacing="1">
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>管理属性 </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999" >编号(联网号)：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmCode()%></td>
      		<td width="25%" nowrap bgcolor="#999999">ATM－C定义编号：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmCCode()%> </td>
    	</tr>
     	<tr> 
      		<td width="25%" nowrap bgcolor="#999999" >ATM－DCC定义编号：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmDccCode()%></td>
      		<td width="25%" nowrap bgcolor="#999999">设备种类：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_TYPE,atmInfo.getAtmType())%> </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">名称：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">重要度：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_LEVEL,atmInfo.getAtmLevel())%> </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">安装单位：</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetUnit()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap height="18" bgcolor="#999999"> 
        		<div align="left">管理单位：</div>
      		</td>
      		<td colspan="3" height="18" bgcolor="#FFFFFF"> 
        		<div align="left"><%=atmInfo.getAtmMngUnit()%></div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">管理员姓名：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getMngName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">电话：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getMngPhone()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">所属机构(支行)：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"> 
        		<div align="left"><%=CodeManageBean.getCodeDesc("orgname",atmInfo.getOrgId())%></div>
      		</td>
      		<td width="25%" bgcolor="#999999">所属区县：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc("areaname",atmInfo.getAreaNo())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">安装地点类别：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ADDR_TYPE,atmInfo.getAtmAddrType())%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left"></div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap height="20" bgcolor="#999999"> 
        		<div align="left">详细地址：</div>
      		</td>
      		<td colspan="3" height="20" bgcolor="#FFFFFF"> 
        		<div align="left"><%=atmInfo.getAtmSetAddr()%></div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">       
      			<div align="left">物理安装时间：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetDate()%></td>
      		<td width="25%" nowrap bgcolor="#999999">         
		    	<div align="left">电子安装时间：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getProgSetDate()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">         
      			<div align="left">开通日期：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getConnectDate()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left"></div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr> 
      		<td nowrap width="25%" bgcolor="#999999">设备状态： </td>
      		<td nowrap width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_STAT,atmInfo.getAtmStat())%></td>
      		<td nowrap width="25%" bgcolor="#999999">运行状态：        
      		</td>
      		<td nowrap width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.RUN_STAT,atmInfo.getRunStat())%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>物理属性 </td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">类型(品牌)：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.ATM_TYPE,atmInfo.getAtmType())%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">型号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmMode()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">SN：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSn()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">TN：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmTn()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">批次：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAtmSetDegree()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">交易卡种：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CARD_REF_TYPE,atmInfo.getCardType())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">币种：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.CENTER_TYPE,atmInfo.getCurrencyCode())%></td>
      		<td width="25%" nowrap bgcolor="#999999">读卡器类型：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.READER_MODE,atmInfo.getCardReaderMode())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">报文传输标准：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getTranslateName()%></td>
      		<td width="25%" nowrap bgcolor="#999999">报文类型：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.MSG_TYPE,atmInfo.getMsgType())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999">通讯协议类型：</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=CodeManageBean.getCodeDesc(CodeDefine.NET_TYPE,atmInfo.getNetType())%></td>
      		<td width="25%" nowrap bgcolor="#999999">　</td>
      		<td width="25%" bgcolor="#FFFFFF">&nbsp; </td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap>帐务属性 </td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">开户行：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getUnionName()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">所号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getBranchNo()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">交换号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getChangeNo()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">联行号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getUnionNo()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">支行号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getAgencyNo()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">机构号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getOrgId()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">帐户号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getBankAcc()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">柜员号：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getTellerId()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap> 网络属性</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">主机IP：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getHostAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">主机端口：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getHostPort()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">终端IP：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getNetAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">子网掩码：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getNetMask()%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">路由IP：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getRouteAddress()%></td>
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">网段数：</div>
      		</td>
      		<td width="25%" bgcolor="#FFFFFF"><%=atmInfo.getJumpNums()%></td>
    	</tr>
    	<tr bgcolor="#CCCCCC"> 
      		<td colspan="4" nowrap> 备注</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">备注1：</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo1())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">备注2：</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo2())%></td>
    	</tr>
    	<tr> 
      		<td width="25%" nowrap bgcolor="#999999"> 
        		<div align="left">备注3：</div>
      		</td>
      		<td colspan="3" bgcolor="#FFFFFF"><%=toucsString.toHtml(atmInfo.getMemo3())%></td>
    	</tr>
  	</table>
  	<table width="100%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<div align="right">
          		<!--input type="button" name="state" value="设备状态" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=13003&content=state&atmCode=<%=atmInfo.getAtmCode()%>'">
          		<input type="button" name="tranFlow" value="交易流水" class="inputbutton" onClick="javascript:alert('查看交易流水!')"-->
		  		<%if(popUp==null||popUp.equals("1")){%>
          		<input type="button" name="cancel" value="确定" class="inputbutton" onClick="javascript:history.go(-1)">
		  		<%}%>
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</body>
</html>