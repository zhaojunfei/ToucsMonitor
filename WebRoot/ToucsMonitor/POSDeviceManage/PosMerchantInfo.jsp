<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>



<%! 
//获取描述信息方法
private void getListDesc(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);	
	 
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId())){
			try{
				out.println(code.getCodeDesc());
			}catch(IOException exp){}			
			}
		}	
	}
}
											
 %>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
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
		document.forms[0].managebankname_temp.value = document.forms[0].manage_bankname.options[document.forms[0].manage_bankname.selectedIndex].text;
		return true;
	}
	else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">	
<%
  			String uid=request.getParameter("uid");			
 %>

<form name="formReg" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=10402&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="managebankname_temp" value="">
  <input type="hidden"  name="key" value="<%=posMerchantInfo.getMerchantid()%>">
<font face="隶书" size="+2">商户信息</font>
   <table id="tab0" width="80%">
    <tr> 
      <td colspan="4" nowrap> 
        <hr noshade>      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right" >商户编号：</td>
      <td width="26%" nowrap> 
        <input type="text" name="merchant_id" class="readonly" readonly value="<%=posMerchantInfo.getMerchantid()%>" size="20"   >      </td>
      <td width="17%" nowrap align="right"> 
        商户名称：      </td>
      <td width="40%" nowrap> 
        <input type="text" name="mct_name" class="readonly" readonly value="<%=posMerchantInfo.getMctname()%>" MAXLENGTH="40" size="20"  >      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right"> 
        商户英文名称：      </td>
      <td width="26%" nowrap> 
        <input type="text" name="mct_enname" class="readonly" readonly value="<%=posMerchantInfo.getMctenname()%>" MAXLENGTH="50" size="20"  >      </td>
      <td width="17%" nowrap align="right">商户类型：</td>
      <td width="40%" nowrap>
	 <input type="text" name="mct_enname" class="readonly" readonly value="<%getListDesc("McttypeList",posMerchantInfo.getMcttype(),request,out);%>" MAXLENGTH="50" size="20"  >    </tr>

    <tr> 
      <td width="17%" nowrap align="right">主管机构名称：</td>
      <td width="26%" nowrap> 
		<input type="text" name="mct_enname" class="readonly" readonly value="<%=posMerchantInfo.getManagebankname()%>" MAXLENGTH="50" size="20"  >       </td>
      <td width="17%" nowrap align="right">主管机构号：</td>
      <td width="40%" nowrap> 
        <input type="text" name="manage_bankno" class="readonly" readonly value="<%=posMerchantInfo.getManagebankno()%>" MAXLENGTH="15" size="20"  >      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right">清算机构名称：</td>
      <td width="26%" nowrap> 
        <input type="text" name="manage_bankno" class="readonly" readonly value="<%=toucsString.unConvert(posMerchantInfo.getOrg_Name())%>" MAXLENGTH="15" size="20"  >      </td>
      <td width="17%" nowrap align="right">清算机构号：</td>
      <td width="40%" nowrap><input type="text" name="org_id" class="readonly" readonly value="<%=posMerchantInfo.getOrgid()%>" maxlength="10" size="20"  ></td>
    </tr>
        <input type="hidden" name="operator" value="<%=posMerchantInfo.getOperator()%>" >

    <tr> 
      <td width="17%" nowrap align="right">手续费收费类型：</td>
      <td width="26%" nowrap> 
		<input type="text" name="pcard_machine_no" class="readonly" readonly value="<%getListDesc("FeeType",posMerchantInfo.getPcardmachine_no(),request,out);%>" size="20"  ></td>
      <td width="17%" nowrap align="right">是否允许预付费卡：</td>
      <td width="40%" nowrap>
	    <input type="text" name="operator" class="readonly" readonly value="<%getListDesc("Operator",posMerchantInfo.getOperator(),request,out);%>" size="20"  ></td>
    </tr>
	<tr>
	  <td nowrap align="right">POS机数：</td>
	  <td nowrap><input type="text" name="pos_machine_no" class="readonly" readonly value="<%=posMerchantInfo.getPosmachine_no()%>" size="20"   ></td> 
      <td width="17%" nowrap align="right">&nbsp;</td>
      <td width="40%" nowrap>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right">法人代表：</td>
      <td width="26%" nowrap> 
        <input type="text" name="artificial_person" class="readonly" readonly value="<%=posMerchantInfo.getArtificialperson()%>" MAXLENGTH="20" size="20"  >      </td>
      <td width="17%" nowrap align="right">经办人：</td>
      <td width="40%" nowrap> 
        <input type="text" name="handle_man" class="readonly" readonly value="<%=posMerchantInfo.getHandleman()%>" MAXLENGTH="30" size="20"  >      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right">电话：</td>
      <td width="26%" nowrap> 
        <input type="text" name="telephone" class="readonly" readonly value="<%=posMerchantInfo.getTelephone()%>" MAXLENGTH="20" size="20"  >      </td>
      <td width="17%" nowrap align="right">传真：</td>
      <td width="40%" nowrap> 
        <input type="text" name="fax" class="readonly" readonly value="<%=posMerchantInfo.getFax()%>" MAXLENGTH="15" size="20"  >      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right">邮编：</td>
      <td width="26%" nowrap> 
        <input type="text" name="zipcode" class="readonly" readonly value="<%=posMerchantInfo.getZipcode()%>" MAXLENGTH="6" size="20"  >      </td>
      <td width="17%" nowrap align="right">地址：</td>
      <td width="40%" nowrap> 
        <input type="text" name="address" class="readonly" readonly value="<%=posMerchantInfo.getAddress()%>" MAXLENGTH="40" size="40"  >      </td>
    </tr>
    <tr> 
      <td width="17%" nowrap align="right">签约日期：</td>
      <td width="26%" nowrap> 
        <input type="text" name="signature_date" class="readonly" readonly style="width:80pt" value="<%=posMerchantInfo.getSignaturedate()%>" MAXLENGTH="8" size="20"  >(YYYYMMDD)      </td>
      <td width="17%" nowrap align="right">撤销日期：</td>
      <td width="40%" nowrap> 
        <input type="text" name="cancel_date" class="readonly" readonly style="width:80pt" value="<%=posMerchantInfo.getCanceldate()%>" MAXLENGTH="8" size="20"  >(YYYYMMDD)      </td>
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
           <input type="button" name="cancel" value="确定" class="inputbutton" onClick="javascript:history.go(-1)">
        </div>
      </td>
    </tr>
  </table>
  <p/>
</form>
</body>
</html>