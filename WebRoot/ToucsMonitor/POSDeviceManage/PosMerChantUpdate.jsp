<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>

<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js"></script>
<script language="javascript">
function onOrgChange(){
	document.forms[0].org_id.value= document.forms[0].org_name.value;
}

function onBankOrgChange(){
	document.forms[0].manage_bankno.value= document.forms[0].manage_bankname.value;
}

function doSubmit(){
	
	if( isNull("merchant_id","商户编号")) return false;
	if (document.forms[0].merchant_id.value.length != 15)
	{
		alert("商户号必须为15位");
		return false;
	}
	if( isNull("mct_name","商户名称")) return false;
	if( isNull("mct_type","商户类型")) return false;
	if( isNull("flag","使用标志")) return false;
	if( isNull("clear_flag","清理标志")) return false;
	if( isNull("manage_bankno","主管支行号")) return false;
	if( isNull("manage_bankname","主管支行名称")) return false;
	if( isNull("org_id","机构号")) return false;

	if(!isNumber("pcard_machine_no"," 压卡机台数"))	return false;
	if(!isNumber("pos_machine_no","POS机数")) return false;
	if(!isNumber("zipcode","邮政编码"))	return false;
	if (document.forms[0].zipcode.value.length != 6)
	{
		alert("邮政编码必须为6位");
		return false;
	}
	if(!validFullDate("signature_date","签约日期"))	return false;
	if(!validFullDate("cancel_date","撤销日期")) return false;
	
	if(confirm("确认输入无误并提交吗？"))
	{
		var x = 0;
		var temp = "";
		temp = document.forms[0].manage_bankname.options[document.forms[0].manage_bankname.selectedIndex].text;
		x = temp.indexOf("-");
		if (x >= 0)
			temp = temp.substr(x+1);
		document.forms[0].managebankname_temp.value = temp;
		return true;
	}
	else
		return false;
}
</script>

<%! private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
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
			}catch(IOException exp){}			
		}	
	}
}%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body onContextMenu="return false;" bgcolor="#CCCCCC" text="#000000">	
<%
  			String uid=request.getParameter("uid");			
 %>

<form name="formReg" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=10402&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="managebankname_temp" value="">
  <input type="hidden"  name="key" value="<%=posMerchantInfo.getMerchantid()%>">
<font face="隶书" size="+2">商户修改</font>
   <table id="tab0" width="80%">
    <tr><td colspan="4" nowrap><hr noshade></td></tr>
    <tr> 
      <td width="20%" nowrap align="right" >商户编号：</td>
      <td width="23%" nowrap> 
        <input type="text" name="merchant_id" class="readonly" readonly style="background-color:#cccccc"  value="<%=posMerchantInfo.getMerchantid()%>" size="20"></td>
      <td width="17%" nowrap align="right"> 
        商户名称：</td>
      <td width="40%" nowrap> 
        <input type="text" name="mct_name" class="wideinput" value="<%=posMerchantInfo.getMctname()%>" MAXLENGTH="40" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right"> 
        商户英文名称：      </td>
      <td width="23%" nowrap> 
        <input type="text" name="mct_enname" class="wideinput"  value="<%=posMerchantInfo.getMctenname()%>" MAXLENGTH="50" size="20"></td>
      <td width="17%" nowrap align="right">商户类型：</td>
      <td width="40%" nowrap>
        <select name="mct_type" size="1">
          <option value="">请选择</option>
          <%initList("McttypeList",posMerchantInfo.getMcttype(),request,out);%>
        </select></td>
    </tr>
	<input type="hidden" name="flag" value="1">
	<input type="hidden" name="clear_flag" value="1">
    <!--tr> 
      <td width="17%" nowrap align="right">使用标志：</td>
      <td width="33%" nowrap> 
        <select name="flag" size="1">
          <option value="">请选择</option>
          <%initList("FlagList",posMerchantInfo.getFlag(),request,out);%>
        </select>
      </td>
      <td width="16%" nowrap align="right">清理标志：</td>
      <td width="34%" nowrap>
      	<select name="clear_flag" size="1">
          <option value="">请选择</option>
      	<%initList("ClearFlagList",posMerchantInfo.getClearflag(),request,out);%>
      	 </select>
    　</td>
    </tr-->
    <tr> 
      <td width="20%" nowrap align="right">主管机构名称：</td>
      <td width="23%" nowrap> 
      	<select name="manage_bankname" size="1" onChange="javascript:onBankOrgChange()">
            <option value="">请选择</option>
            <%initList("orgList",posMerchantInfo.getManagebankno(),request,out);%>			
          </select></td>
      <td width="17%" nowrap align="right">主管机构号：</td>
      <td width="40%" nowrap> 
        <input type="text" name="manage_bankno" class="readonly" readonly value="<%=posMerchantInfo.getManagebankno()%>" MAXLENGTH="15" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">清算机构名称：</td>
      <td width="23%" nowrap> 
        <div align="left"> 
          <select name="org_name" size="1" onChange="javascript:onOrgChange()">
            <option value="">请选择</option>
            <%initList("cardOrgList",posMerchantInfo.getOrgid(),request,out);%>			
          </select>
        </div></td>
      <td width="17%" nowrap align="right">清算机构号：</td>
      <td width="40%" nowrap> 
        <input type="text" name="org_id" class="readonly" readonly value="<%=posMerchantInfo.getOrgid()%>" MAXLENGTH="10" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">手续费收费类型：</td>
      <td width="23%" nowrap> 
        <select name="pcard_machine_no" size="1">
          <option value="">请选择</option>
          <%initList("FeeType",posMerchantInfo.getPcardmachine_no(),request,out);%>
        </select>	  </td>
      <td width="17%" nowrap align="right">是否允许预付费卡：</td>
      <td width="40%" nowrap>
	    <select name="operator" size="1">
          <option value="">请选择</option>
          <%initList("Operator",posMerchantInfo.getOperator(),request,out);%>
        </select>
	  </td>
    </tr>
	<tr>
      <td nowrap align="right">POS机数：</td>
      <td nowrap><input type="text" name="pos_machine_no" class="wideinput" value="<%=posMerchantInfo.getPosmachine_no()%>" size="20" >      </td> 
      <td width="17%" nowrap align="right">&nbsp;</td>
      <td width="40%" nowrap>&nbsp;</td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">法人代表：</td>
      <td width="23%" nowrap> 
        <input type="text" name="artificial_person" class="wideinput" value="<%=posMerchantInfo.getArtificialperson()%>" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">经办人：</td>
      <td width="40%" nowrap> 
        <input type="text" name="handle_man" class="wideinput" value="<%=posMerchantInfo.getHandleman()%>" MAXLENGTH="30" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">电话：</td>
      <td width="23%" nowrap> 
        <input type="text" name="telephone" class="wideinput" value="<%=posMerchantInfo.getTelephone()%>" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">传真：</td>
      <td width="40%" nowrap> 
        <input type="text" name="fax" class="wideinput" value="<%=posMerchantInfo.getFax()%>" MAXLENGTH="15" size="20">      </td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">邮编：</td>
      <td width="23%" nowrap> 
        <input type="text" name="zipcode" class="wideinput" value="<%=posMerchantInfo.getZipcode()%>" MAXLENGTH="6" size="20"></td>
      <td width="17%" nowrap align="right">地址：</td>
      <td width="40%" nowrap> 
        <input type="text" name="address" class="wideinput" value="<%=posMerchantInfo.getAddress()%>" MAXLENGTH="20" size="40"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">签约日期：</td>
      <td width="23%" nowrap> 
        <input type="text" name="signature_date" style="width:80" value="<%=posMerchantInfo.getSignaturedate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
      <td width="17%" nowrap align="right">撤销日期：</td>
      <td width="40%" nowrap> 
        <input type="text" name="cancel_date" style="width:80" value="<%=posMerchantInfo.getCanceldate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
    </tr>
</table>
<table width="80%"><tr><td colspan="5" nowrap> 
<hr noshade></td></tr><tr><td colspan="5" nowrap> <div align="right"> 
          <input type="submit" name="submit" value="提交" class="inputbutton">
          <input type="button" name="cancel" value="取消" class="inputbutton" onClick=history.back()>
</div></td></tr></table><p/></form></body></html>