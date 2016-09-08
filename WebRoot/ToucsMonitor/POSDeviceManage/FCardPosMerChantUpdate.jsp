<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>

<html>
<head>
	<title>修改外卡商户</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function onCondition(){
//visa
	open_flag1 = "0";
	open_flag2 = "0";
	open_flag3 = "0";
	open_flag4 = "0";
	open_flag5 = "0";
	if(document.formReg.open_flag1.checked){
		document.formReg.rate_visa.disabled=false;
		document.formReg.rate_visa2.disabled=false;
		open_flag1 = "1";
	}else{
		document.formReg.rate_visa.value="";
		document.formReg.rate_visa.disabled=true;
		document.formReg.rate_visa2.value="";
		document.formReg.rate_visa2.disabled=true;
	}
//master
	if(document.formReg.open_flag2.checked){
		document.formReg.rate_master.disabled=false;
		document.formReg.rate_master2.disabled=false;
		open_flag2 = "1";
	}else{
		document.formReg.rate_master.value="";
		document.formReg.rate_master.disabled=true;
		document.formReg.rate_master2.value="";
		document.formReg.rate_master2.disabled=true;
	}
//visa & master
	if(document.formReg.open_flag1.checked || document.formReg.open_flag2.checked){
		document.formReg.mcc.disabled=false;
	}else{
		document.formReg.mcc.value="";
		document.formReg.mcc.disabled=true;
	}

//ae
	if(document.formReg.open_flag3.checked){
		document.formReg.mer_id_ae.disabled=false;
		document.formReg.rate_ae.disabled=false;
		document.formReg.rate_ae2.disabled=false;
		open_flag3 = "1";
	}else{
		document.formReg.mer_id_ae.value="";
		document.formReg.mer_id_ae.disabled=true;
		document.formReg.rate_ae.value="";
		document.formReg.rate_ae.disabled=true;
		document.formReg.rate_ae2.value="";
		document.formReg.rate_ae2.disabled=true;
	}
//dinner
	if(document.formReg.open_flag4.checked){
		document.formReg.mer_id_dinner.disabled=false;
		document.formReg.rate_dinner.disabled=false;
		document.formReg.rate_dinner2.disabled=false;
		open_flag4 = "1";
	}else{
		document.formReg.mer_id_dinner.value="";
		document.formReg.mer_id_dinner.disabled=true;
		document.formReg.rate_dinner.value="";
		document.formReg.rate_dinner.disabled=true;
		document.formReg.rate_dinner2.value="";
		document.formReg.rate_dinner2.disabled=true;
	}
//jcb
	if(document.formReg.open_flag5.checked){
		document.formReg.mer_id_jcb.disabled=false;
		document.formReg.rate_jcb.disabled=false;
		document.formReg.rate_jcb2.disabled=false;
		open_flag5 = "1";
	}else{
		document.formReg.mer_id_jcb.value="";
		document.formReg.mer_id_jcb.disabled=true;
		document.formReg.rate_jcb.value="";
		document.formReg.rate_jcb.disabled=true;
		document.formReg.rate_jcb2.value="";
		document.formReg.rate_jcb2.disabled=true;
	}
	document.formReg.open_flag.value=open_flag1+open_flag2+open_flag3+open_flag4+open_flag5;
}

function onOrgChange(){
	document.forms[0].org_id.value= document.forms[0].org_name.value;
}

function onBankOrgChange(){
	document.forms[0].manage_bankno.value= document.forms[0].manage_bankname.value;
}

function doSubmit(){	
	if (isNull("mer_id","商户编号")) return false;
	if (isNull("mer_eg","商户英文名称")) return false;
	if (isNull("mer_city", "所在城市拼音")) return false;
	if (document.formReg.open_flag1.checked){
		if (isNull("mcc", "MCC")) return false;
		if (isNull("rate_visa", "回扣率visa")) return false;
		if (!isFNumber("rate_visa", "回扣率visa")) return false;
		if (!isFNumber("rate_visa2", "返回率visa")) return false;
	}
	if (document.formReg.open_flag2.checked){
		if (isNull("tcc", "终端类型码（TCC）")) return false;
		if (isNull("mcc", "MCC")) return false;
		if (isNull("rate_master", "回扣率master")) return false;
		if (!isFNumber("rate_master", "回扣率master")) return false;
		if (!isFNumber("rate_master2", "返回率master")) return false;
	}
	if (document.formReg.open_flag3.checked){
		if (isNull("mer_id_ae", "默认AE商户号")) return false;
		if (isNull("rate_ae", "回扣率ae")) return false;
		if (!isFNumber("rate_ae", "回扣率ae")) return false;
		if (!isFNumber("rate_ae2", "返回率ae")) return false;
	}
	if (document.formReg.open_flag4.checked){
		if (isNull("mer_id_dinner", "默认Dinner商户号")) return false;
		if (isNull("rate_dinner", "回扣率dinner")) return false;
		if (!isFNumber("rate_dinner", "回扣率dinner")) return false;
		if (!isFNumber("rate_dinner2", "返回率dinner")) return false;
	}
	if (document.formReg.open_flag5.checked){
		if (isNull("mer_id_jcb", "默认JCB商户号")) return false;
		if (isNull("rate_jcb", "回扣率jcb")) return false;
		if (!isFNumber("rate_jcb", "回扣率jcb")) return false;
		if (!isFNumber("rate_jcb2", "返回率jcb")) return false;
	}
	if (!isInteger("settle_date", "资金结算日期")) return false;
	if (isNull("acct_no", "默认划款账号")) return false;
	if (isNull("acct_bankno", "默认划款账号开户行")) return false;
	if (isNull("acct_name", "默认划款账户名称")) return false;
	
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}

function doPreSubmit() {
	var merid = trim(document.formReg.mer_id.value);

	if (merid == null || merid.length <= 0)	
		return false;

	if (document.formReg.stat.value == "0"){
		document.formReg.action = document.formReg.action + "&target=page"
		document.formReg.submit();
	}
	//return true;
}

</script>
<body   bgcolor="#CCCCCC" text="#000000">	
<%
	String uid=request.getParameter("uid");			
	String postCode="";		
	String keyFlag="";
	String editTxtFlag="";
	String editListFlag="";
	POSFCardMerchantInfo FCPosMerchant = (POSFCardMerchantInfo)request.getAttribute("PosMercant");
	if (FCPosMerchant == null)
		FCPosMerchant = new POSFCardMerchantInfo();
	String open_flag = FCPosMerchant.getOpen_flag();
			
  %>
<h2><font face="隶书">修改外卡POS商户信息</font></h2>
<form name="formReg" method="post" action="/ToucsMonitor/fcardmerchantconfig?reqCode=10502&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="stat" value="0">
  	<table id="tab0" width="90%">
    	<tr>
      		<td colspan=4 nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="15%" nowrap align="right" >建行商户编号：</td>
      		<td width="28%" nowrap>
        		<input name="mer_id" type="text" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_id()%>" onBlur="javascript:doPreSubmit()" size="20" maxlength="15">*</td>
      		<td width="14%" nowrap align="right"> 商户中文名称： </td>
      		<td nowrap>
        		<input type="text" name="mer_name" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_name()%>" maxlength="50" size="20">* 
        	</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">商户英文名称：</td>
      		<td nowrap>
      			<input type="text" name="mer_eg" class="wideinput"  value="<%=FCPosMerchant.getMer_eg()%>" maxlength="40" size="20">*
      		</td>
      		<td nowrap align="right">所在城市(拼音)：</td>
      		<td  nowrap>
      			<input type="text" name="mer_city" class='wideinput' value="<%=FCPosMerchant.getMer_city()%>" maxlength="20" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">商户地址：</td>
      		<td nowrap>
      			<input type="text" name="mer_addr" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_addr()%>" maxlength="50" size="20">
      		</td>
      		<td nowrap align="right">邮编：</td>
      		<td  nowrap>
      			<input type="text" name="mer_zip" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_zip()%>" maxlength="6" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">电话：</td>
      		<td nowrap>
      			<input type="text" name="mct_tel" class="readonly" style="background-color:#DDDDDD" readonly value="<%=FCPosMerchant.getMer_tel()%>" maxlength="20" size="20">
      		</td>
      		<td nowrap align="right">公司网站地址：</td>
      		<td  nowrap>
      			<input type="text" name="mct_url" class='wideinput' value="<%=FCPosMerchant.getMer_url()%>" maxlength="50" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">法人代表：</td>
      		<td nowrap>
        		<input type="text" name="manager_a" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getManager_a()%>" maxlength="15" size="20">
			</td>
      		<td nowrap align="right">法人代表电话：</td>
      		<td  nowrap>
      			<input type="text" name="tel_a" class='wideinput' value="<%=FCPosMerchant.getTel_a()%>" maxlength="15" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">经办人：</td>
      		<td nowrap>
      			<input type="text" name="manager_b" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getManager_b()%>" maxlength="15" size="20">
      		</td>
      		<td nowrap align="right">经办人电话：</td>
      		<td  nowrap>
      			<input type="text" name="tel_b" class='wideinput' value="<%=FCPosMerchant.getTel_b()%>" maxlength="15" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">发展行号：</td>
      		<td nowrap>
        		<input type="text" name="dev_bankno" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getDev_bankno()%>" maxlength="15" size="20">* 
        	</td>
      		<td nowrap align="right">管辖行号：</td>
      		<td  nowrap>
        		<input type="text" name="man_bankno" class="readonly" style="background-color:#DDDDDD" readonly value="<%=FCPosMerchant.getMan_bankno()%>" maxlength="15" size="20">* 
        	</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">终端类型码（TCC）:</td>
      		<td nowrap>
      			<input type="text" name="tcc" class='wideinput' value="<%=FCPosMerchant.getTcc()%>" maxlength="1" size="20">*
      		</td>
      		<td nowrap align="right">商户状态：</td>
      		<td  nowrap>
      			<select name="mer_state" size="1">
				<option value="0" <%=FCPosMerchant.getMer_state().equals("0")?"selected":""%>>正常商户</option>
				<option value="1" <%=FCPosMerchant.getMer_state().equals("1")?"selected":""%>>测试商户</option>
				<option value="2" <%=FCPosMerchant.getMer_state().equals("2")?"selected":""%>>监控商户</option>
				<option value="7" <%=FCPosMerchant.getMer_state().equals("7")?"selected":""%>>风险控制停用商户</option>
				<option value="9" <%=FCPosMerchant.getMer_state().equals("9")?"selected":""%>>人工停用商户</option>
       			</select>*
	  		</td>
    	</tr>
  	</table>
	<table border=1>
		<tr>
      		<td colspan="7" align="left" nowrap>外卡开通标志：</td>
	  		<td><input type="hidden" name="open_flag" value="<%=FCPosMerchant.getOpen_flag()%>"></td>
		</tr>
    	<tr>
      		<td width="70" nowrap>
        		<input name="open_flag1" type="checkbox" class="radio" onClick="javascript:onCondition()" value="" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"checked":""%>>Visa 
        	</td>
      		<td width="100" rowspan="2" align="right" nowrap>MCC：</td>
      		<td width="100" rowspan="2" nowrap>
      			<input type="text" name="mcc" class="wideinput"  value="<%=FCPosMerchant.getMcc()%>" maxlength="4" size="10" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(0)=='1' || FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td width="80" nowrap>回扣率Visa</td>
      		<td width="150" nowrap>
      			<input type="text" name="rate_visa" class="wideinput"  value="<%=FCPosMerchant.getRate_visa()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td width="80" nowrap>返回率Visa</td>
      		<td width="150" nowrap>
      			<input type="text" name="rate_visa2" class="wideinput"  value="<%=FCPosMerchant.getRate_visa2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag2" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"checked":""%>>Master 
      		</td>
      		<td nowrap>回扣率Master</td>
      		<td nowrap>
      			<input type="text" name="rate_master" class="wideinput"  value="<%=FCPosMerchant.getRate_master()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>返回率Master</td>
      		<td nowrap>
      			<input type="text" name="rate_master2" class="wideinput"  value="<%=FCPosMerchant.getRate_master2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag3" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"checked":""%>>AE 
      		</td>
      		<td nowrap align="right">默认AE商户号：</td>
      		<td nowrap>
      			<input type="text" name="mer_id_ae" class="wideinput"  value="<%=FCPosMerchant.getMer_id_ae()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>回扣率AE</td>
     	 	<td nowrap>
     	 		<input type="text" name="rate_ae" class="wideinput"  value="<%=FCPosMerchant.getRate_ae()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
     	 	</td>
      		<td nowrap>返回率AE</td>
      		<td nowrap>
      			<input type="text" name="rate_ae2" class="wideinput"  value="<%=FCPosMerchant.getRate_ae2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag4" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"checked":""%>>Dinner
      		</td>
      		<td nowrap align="right">默认Dinner商户号：</td>
     	 	<td nowrap>
     	 		<input type="text" name="mer_id_dinner" class="wideinput"  value="<%=FCPosMerchant.getMer_id_dinner()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
     	 	</td>
     	 	<td nowrap>回扣率Dinner</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner" class="wideinput"  value="<%=FCPosMerchant.getRate_dinner()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>返回率Dinner</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner2" class="wideinput"  value="<%=FCPosMerchant.getRate_dinner2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag5" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"checked":""%>>JCB
      		</td>
      		<td nowrap align="right">默认JCB商户号：</td>
      		<td nowrap>
      			<input type="text" name="mer_id_jcb" class="wideinput"  value="<%=FCPosMerchant.getMer_id_jcb()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>回扣率JCB</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb" class="wideinput"  value="<%=FCPosMerchant.getRate_jcb()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>返回率JCB</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb2" class="wideinput"  value="<%=FCPosMerchant.getRate_jcb2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
  	</table>
	<table width="90%">
    	<tr>
      		<td width="15%" align="right" nowrap>默认划款账号：</td>
      		<td width="28%" nowrap>
      			<input type="text" name="acct_no" class="wideinput" value="<%=FCPosMerchant.getAcct_no()%>" maxlength="50" size="20">*</td>
      		<td width="15%" align="right" nowrap>默认划款账号开户行：</td>
      		<td width="42%" colspan="3" nowrap>
      			<input type="text" name="acct_bankno" class="wideinput" value="<%=FCPosMerchant.getAcct_bankno()%>" maxlength="9" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">默认划款账户名称：</td>
      		<td nowrap>
      			<input type="text" name="acct_name" class="wideinput" value="<%=FCPosMerchant.getAcct_name()%>" maxlength="40" size="20">*
      		</td>
      		<td nowrap align="right">对帐单地址：</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_addr" class="wideinput" value="<%=FCPosMerchant.getBill_ck_addr()%>" maxlength="100" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">对帐单邮编：</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_zip" class="wideinput" value="<%=FCPosMerchant.getBill_ck_zip()%>" maxlength="6" size="20">
      		</td>
      		<td nowrap align="right">对帐单收件人：</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_rcv" class="wideinput" value="<%=FCPosMerchant.getBill_ck_rcv()%>" maxlength="20" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">对帐单email：</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_email" class="wideinput" value="<%=FCPosMerchant.getBill_ck_email()%>" maxlength="40" size="20">
      		</td>
      		<td nowrap align="right">对帐单传真：</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_fax" class="wideinput" value="<%=FCPosMerchant.getBill_ck_fax()%>" maxlength="40" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">商户级别：</td>
      		<td nowrap>
      			<input type="text" name="mer_level" class="wideinput" value="<%=FCPosMerchant.getMer_level()%>" maxlength="1" size="20">
      		</td>
      		<td nowrap align="right">商户状态：</td>
      		<td nowrap>	
      			<input type="text" name="mer_state" class="wideinput" value="<%=FCPosMerchant.getMer_state()%>" maxlength="1" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">资金结算日期：</td>
      		<td nowrap>
      			<input type="text" name="settle_date" class="wideinput" value="<%=FCPosMerchant.getSettle_date()%>" maxlength="8" size="20">
      		</td>
      		<td nowrap align="right">&nbsp;</td>
      		<td nowrap>&nbsp;</td>
    	</tr>
  	</table>
  	<table width="82%">
    	<tr> 
      		<td  nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td  nowrap> 
        		<div align="right"> 
          		<input type="submit" name="submit1" value="提交" class="inputbutton">
          		<input type="button" name="cancel1" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/fcardmerchantconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>