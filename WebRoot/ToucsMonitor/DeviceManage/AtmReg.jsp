<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else flag="";
			try{
				out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
					+code.getCodeDesc()+"</option>");
			}catch(IOException exp){
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		}
	}
}%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">

function orgChange(idx){
	formReg.txOrgId.value=formReg.orgId.value;
	formReg.branchs.selectedIndex=idx;
	formReg.agencyNo.value=formReg.branchs.options[idx].text;
}

function doSubmit(){

	if(!onDeviceCode("A5110","atmCode")) return false;
	if( isNull("atmCCode","ATM-C编号")) return false;
	if( isNull("atmDccCode","ATM-DCC编号")) return false;
        if( isNull("atmLevel","设备重要度")) return false;
	if( isNull("orgId","设备所属机构")) return false;
	if( isNull("areaNo","安装地点所属区县")) return false;
	if( isNull("atmType","设备类型")) return false;
	if( isNull("atmSn","设备序号（SN）")) return false;
	if( isNull("atmTn","设备跟踪号")) return false;
	if( isNull("cardType","交易卡种")) return false;
	if( isNull("currencyCode","币种")) return false;
	if( isNull("translateName","报文传输标准")) return false;
	if( isNull("msgType","报文类型")) return false;
	if( isNull("netType","通讯协议类型")) return false;
	if( isNull("tellerId","柜员号")) return false;
	if( isNull("netAddress","终端IP（ATM网络通讯地址）")) return false;

	//检查日期
	if(!validFullDate("atmSetDate","物理安装日期"))	return false;
	if(!validFullDate("progSetDate","电子安装日期")) return false;
	if(!validFullDate("connectDate","开通日期")) 	return false;
	//检查数字
	if(!isInteger("jumpNums","网段数") ) return false;

	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}
</script>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String postCode="";
	String keyFlag="";
	String editTxtFlag="";
	
	switch(atmInfo.oper){
		case 1:
			postCode="13001";
			break;
		case 2:
			postCode="13002";	
			break;
		default:
	editTxtFlag=" READONLY style.backgroundcolor=#CCCCCC";		
	}
  %>
<h3><font face="隶书">ATM设备配置信息</font></h3>
<form name="formReg" method="post" action="/ToucsMonitor/deviceconfig?reqCode=<%=postCode%>&content=base&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden"  name="key" value="<%=atmInfo.getAtmCode()%>">
<input type="hidden" name="useFlag" value="<%=atmInfo.getUseFlag()%>">
	<table id="tab0" width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right" >编号(联网号)：</td>
      		<td width="30%" nowrap>
        	<%if( atmInfo.getAtmCode().trim() == null||atmInfo.getAtmCode().equalsIgnoreCase("") ){%>
            	<input type="text" name="atmCode" value="A5110" <%=keyFlag%>  class="wideinput" MAXLENGTH="9" size="20">*
        	<%}else{%>
            	<input type="text" name="atmCode" value="<%=atmInfo.getAtmCode()%>" <%=keyFlag%>  class="wideinput" MAXLENGTH="9" size="20">*
        	<%}%>
      		</td>
      		<td width="20%" nowrap align="right">ATM－DCC定义编号：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmDccCode" value="<%=atmInfo.getAtmDccCode()%>" class="wideinput" size="20" MAXLENGTH="20">* </td>
    	</tr>
    	<tr>
    		<td width="20%" nowrap align="right" >ATM－C定义编号：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmCCode" value="<%=atmInfo.getAtmCCode()%>" class="wideinput" size="20" MAXLENGTH="20">*
      		</td>
      		<td width="20%" nowrap align="right">重要度：</td>
      		<td width="30%" nowrap>
        		<select name="atmLevel" size="1">
          		<option value="">请选择</option>
          		<%initList("atmLevelList",atmInfo.getAtmLevel(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">名称：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmName" value="<%=atmInfo.getAtmName()%>" <%=editTxtFlag%> class="wideinput" size="20" MAXLENGTH="20">
      		</td>
      		<td width="20%" nowrap align="right"> 安装地点类别：</td>
      		<td width="30%" nowrap>
        		<select name="atmAddrType" size="1">
          		<option value="">请选择</option>
          		<%initList("addrTypeList",atmInfo.getAtmAddrType(),request,out);%>
        		</select>
        		<select name="branchs" size="1" DISABLED style="display:none">
		  		<option value="">请选择</option>
          		<%
		  		//保存支行编号列表
		  			String[] branchs=(String[])request.getAttribute("branchs");
					if(branchs!=null){
					for(int i=0;i<branchs.length;i++){%>
          			<option><%=branchs[i]%></option>
          		<%}
					}
		  		%>
        		</select>
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">安装单位：</td>
      		<td width="30%" >
        		<input type="text" name="atmSetUnit" class="wideinput" value="<%=atmInfo.getAtmSetUnit()%>" <%=editTxtFlag%> size="20" MAXLENGTH="20">
      		</td>
      		<td width="20%" nowrap align="right">管理单位：
      		</td>
      		<td width="30%">
        		<input type="text" name="atmMngUnit" class="wideinput" value="<%=atmInfo.getAtmMngUnit()%>" <%=editTxtFlag%> size="20" MAXLENGTH="20">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">管理员姓名：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="mngName" value="<%=atmInfo.getMngName()%>" <%=editTxtFlag%> class="wideinput" size="20" MAXLENGTH="9" >
      		</td>
      		<td width="20%" nowrap align="right">电话：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="mngPhone" value="<%=atmInfo.getMngPhone()%>" class="wideinput" size="20" maxlength="20">
      		</td>
    	</tr>
    	<tr>
     		<td width="20%" nowrap align="right">所属机构：
      		</td>
      		<td width="30%" nowrap>
          		<select name="orgId" size="1" onChange="javascript:orgChange(orgId.selectedIndex)">
            	<option value="">请选择</option>
            	<%initList("orgList",atmInfo.getOrgId(),request,out);%>
          		</select>*
      		</td>
      		<td width="20%" nowrap align="right">所属区县：</td>
      		<td width="30%" nowrap>
        		<select name="areaNo" size="1">
         	 	<option value="">请选择</option>
          		<%initList("areaList",atmInfo.getAreaNo(),request,out);%>
        		</select>*
      		</td>
    	</tr>
     	<tr>
      		<td width="20%" nowrap align="right">详细地址：
      		</td>
      		<td colspan="3">
        		<input type="text" name="atmSetAddr" class="wideinput" value="<%=atmInfo.getAtmSetAddr()%>" <%=editTxtFlag%> size="20" maxlength="40">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> 物理安装日期：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSetDate" value="<%=atmInfo.getAtmSetDate()%>" <%=editTxtFlag%>  size="20" maxlength="8">(YYYYMMDD)</td>
      		<td width="20%" nowrap align="right"> 电子安装日期：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="progSetDate" value="<%=atmInfo.getProgSetDate()%>" size="20"  maxlength="8" >(YYYYMMDD)</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> 开通日期：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="connectDate" value="<%=atmInfo.getConnectDate()%>" <%=editTxtFlag%> size="20"  maxlength="8" >(YYYYMMDD)</td>
      		<td width="20%" nowrap align="right">
      		</td>
      		<td width="30%" nowrap>&nbsp; </td>
    	</tr>
  	</table>
  	<table width="80%" id="tab1">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">类型(品牌)：
      		</td>
      		<td width="30%" nowrap>
        		<select name="atmType" size="1">
          		<option value="">请选择</option>
          		<%initList("atmTypeList",atmInfo.getAtmType(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap align="right">型号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmMode" value="<%=atmInfo.getAtmMode()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">SN：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSn" value="<%=atmInfo.getAtmSn()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="12">*
      		</td>
      		<td width="20%" nowrap align="right">TN：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmTn" value="<%=atmInfo.getAtmTn()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">*
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">批次：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSetDegree" value="<%=atmInfo.getAtmSetDegree()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="11">
      		</td>
      		<td width="20%" nowrap align="right">交易卡种：
      		</td>
      		<td width="30%" nowrap>
        		<select name="cardType" size="1">
          		<option value="">请选择</option>
          		<%initList("cardTypeList",atmInfo.getCardType(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">币种：</td>
      		<td width="30%" nowrap>
        		<select name="currencyCode" size="1">
          		<option value="">请选择</option>
          		<%initList("currencyTypeList",atmInfo.getCurrencyCode(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap align="right">读卡器类型：</td>
      		<td width="30%" nowrap>
       		 	<select name="cardReaderMode" size="1">
          		<option value="">请选择</option>
          		<%initList("readerModeList",atmInfo.getCardReaderMode(),request,out);%>
        		</select>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">报文传输标准：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="translateName" value="<%=atmInfo.getTranslateName()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">*
      		</td>
      		<td width="20%" nowrap align="right">报文类型：</td>
      		<td width="30%" nowrap>
        		<select name="msgType" size="1">
          		<option value="">请选择</option>
          		<%initList("msgTypeList",atmInfo.getMsgType(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">通讯协议类型：</td>
      		<td width="30%" nowrap>
        		<select name="netType" size="1">
          		<option value="">请选择</option>
          		<%initList("netTypeList",atmInfo.getNetType(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap>　</td>
      		<td width="30%" nowrap>&nbsp; </td>
    	</tr>
  	</table>
  	<table width="80%" id="tab2">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">开户行：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="unionName" value="<%=atmInfo.getUnionName()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="40">
      		</td>
      		<td width="20%" nowrap align="right">所号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="branchNo" value="<%=atmInfo.getBranchNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="4">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">交换号：
	      	</td>
	      	<td width="30%" nowrap>
	        	<input type="text" name="changeNo" value="<%=atmInfo.getChangeNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="4">
	      	</td>
	      	<td width="20%" nowrap align="right">联行号：
	      	</td>
	      	<td width="30%" nowrap>
	        	<input type="text" name="unionNo" value="<%=atmInfo.getUnionNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="6">
	      	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">支行号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="agencyNo" value="<%=atmInfo.getAgencyNo()%>" READONLY  style="background-color:#CCCCCC" class=wideinput size="20">
      		</td>
      		<td width="20%" nowrap align="right">机构号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" value="<%=atmInfo.getOrgId()%>" name="txOrgId" READONLY  style="background-color:#CCCCCC" class=wideinput size="20"  >
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> 帐户号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="bankAcc" value="<%=atmInfo.getBankAcc()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="15">
      		</td>
      		<td width="20%" nowrap align="right">柜员号：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="tellerId" value="<%=atmInfo.getTellerId()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="5">*
      		</td>
    	</tr>
  	</table>
  	<table width="80%" id="tab3">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> 主机IP：</td>
      		<td width="30%" nowrap>
        		<input type="text" name="hostAddress" value="<%=atmInfo.getHostAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
      		<td width="20%" nowrap align="right">主机端口：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="hostPort" value="<%=atmInfo.getHostPort()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="6">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">终端IP：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="netAddress" value="<%=atmInfo.getNetAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">*
      		</td>
      		<td width="20%" nowrap align="right">子网掩码：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="netMask" value="<%=atmInfo.getNetMask()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">路由IP：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="routeAddress" value="<%=atmInfo.getRouteAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
      		<td width="20%" nowrap align="right">网段数：
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="jumpNums" value="<%=atmInfo.getJumpNums()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="5">
      		</td>
    	</tr>
  	</table>
  	<table width="80%" id="tab4">
    	<tr>
      		<td colspan="2" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr>
      		<td width="20%" nowrap align="right">备注1：
      		</td>
      		<td width="80%">
        		<input type="text" name="memo1" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo1())%>" <%=editTxtFlag%>  size="20"  maxlength="50">
      		</td>
    	</tr>
   	 	<tr>
      		<td width="20%" nowrap align="right">备注2：
      		</td>
      		<td width="80%">
        		<input type="text" name="memo2" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo2())%>" <%=editTxtFlag%> size="20" maxlength="50">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">备注3：
      		</td>
      		<td width="80%">
        		<input type="text" name="memo3" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo3())%>" <%=editTxtFlag%> size="20" maxlength="50">
      		</td>
    	</tr>
  	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="5" nowrap>
         		<div align="right">
		  		<%if(atmInfo.oper==1||atmInfo.oper==2){%>
          		<input type="submit" name="submit" value="提交" class="inputbutton">
          		<input type="reset" name="reset" value="重填" class="inputbutton">
		  		<%}%>
          		<input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>