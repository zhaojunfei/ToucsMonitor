<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>

<html>
<head>
	<title>�޸��⿨�̻�</title>
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
	if (isNull("mer_id","�̻����")) return false;
	if (isNull("mer_eg","�̻�Ӣ������")) return false;
	if (isNull("mer_city", "���ڳ���ƴ��")) return false;
	if (document.formReg.open_flag1.checked){
		if (isNull("mcc", "MCC")) return false;
		if (isNull("rate_visa", "�ؿ���visa")) return false;
		if (!isFNumber("rate_visa", "�ؿ���visa")) return false;
		if (!isFNumber("rate_visa2", "������visa")) return false;
	}
	if (document.formReg.open_flag2.checked){
		if (isNull("tcc", "�ն������루TCC��")) return false;
		if (isNull("mcc", "MCC")) return false;
		if (isNull("rate_master", "�ؿ���master")) return false;
		if (!isFNumber("rate_master", "�ؿ���master")) return false;
		if (!isFNumber("rate_master2", "������master")) return false;
	}
	if (document.formReg.open_flag3.checked){
		if (isNull("mer_id_ae", "Ĭ��AE�̻���")) return false;
		if (isNull("rate_ae", "�ؿ���ae")) return false;
		if (!isFNumber("rate_ae", "�ؿ���ae")) return false;
		if (!isFNumber("rate_ae2", "������ae")) return false;
	}
	if (document.formReg.open_flag4.checked){
		if (isNull("mer_id_dinner", "Ĭ��Dinner�̻���")) return false;
		if (isNull("rate_dinner", "�ؿ���dinner")) return false;
		if (!isFNumber("rate_dinner", "�ؿ���dinner")) return false;
		if (!isFNumber("rate_dinner2", "������dinner")) return false;
	}
	if (document.formReg.open_flag5.checked){
		if (isNull("mer_id_jcb", "Ĭ��JCB�̻���")) return false;
		if (isNull("rate_jcb", "�ؿ���jcb")) return false;
		if (!isFNumber("rate_jcb", "�ؿ���jcb")) return false;
		if (!isFNumber("rate_jcb2", "������jcb")) return false;
	}
	if (!isInteger("settle_date", "�ʽ��������")) return false;
	if (isNull("acct_no", "Ĭ�ϻ����˺�")) return false;
	if (isNull("acct_bankno", "Ĭ�ϻ����˺ſ�����")) return false;
	if (isNull("acct_name", "Ĭ�ϻ����˻�����")) return false;
	
	if(confirm("ȷ�����������ύ��"))
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
<h2><font face="����">�޸��⿨POS�̻���Ϣ</font></h2>
<form name="formReg" method="post" action="/ToucsMonitor/fcardmerchantconfig?reqCode=10502&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="stat" value="0">
  	<table id="tab0" width="90%">
    	<tr>
      		<td colspan=4 nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="15%" nowrap align="right" >�����̻���ţ�</td>
      		<td width="28%" nowrap>
        		<input name="mer_id" type="text" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_id()%>" onBlur="javascript:doPreSubmit()" size="20" maxlength="15">*</td>
      		<td width="14%" nowrap align="right"> �̻��������ƣ� </td>
      		<td nowrap>
        		<input type="text" name="mer_name" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_name()%>" maxlength="50" size="20">* 
        	</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�̻�Ӣ�����ƣ�</td>
      		<td nowrap>
      			<input type="text" name="mer_eg" class="wideinput"  value="<%=FCPosMerchant.getMer_eg()%>" maxlength="40" size="20">*
      		</td>
      		<td nowrap align="right">���ڳ���(ƴ��)��</td>
      		<td  nowrap>
      			<input type="text" name="mer_city" class='wideinput' value="<%=FCPosMerchant.getMer_city()%>" maxlength="20" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�̻���ַ��</td>
      		<td nowrap>
      			<input type="text" name="mer_addr" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_addr()%>" maxlength="50" size="20">
      		</td>
      		<td nowrap align="right">�ʱࣺ</td>
      		<td  nowrap>
      			<input type="text" name="mer_zip" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getMer_zip()%>" maxlength="6" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�绰��</td>
      		<td nowrap>
      			<input type="text" name="mct_tel" class="readonly" style="background-color:#DDDDDD" readonly value="<%=FCPosMerchant.getMer_tel()%>" maxlength="20" size="20">
      		</td>
      		<td nowrap align="right">��˾��վ��ַ��</td>
      		<td  nowrap>
      			<input type="text" name="mct_url" class='wideinput' value="<%=FCPosMerchant.getMer_url()%>" maxlength="50" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">���˴���</td>
      		<td nowrap>
        		<input type="text" name="manager_a" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getManager_a()%>" maxlength="15" size="20">
			</td>
      		<td nowrap align="right">���˴���绰��</td>
      		<td  nowrap>
      			<input type="text" name="tel_a" class='wideinput' value="<%=FCPosMerchant.getTel_a()%>" maxlength="15" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�����ˣ�</td>
      		<td nowrap>
      			<input type="text" name="manager_b" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getManager_b()%>" maxlength="15" size="20">
      		</td>
      		<td nowrap align="right">�����˵绰��</td>
      		<td  nowrap>
      			<input type="text" name="tel_b" class='wideinput' value="<%=FCPosMerchant.getTel_b()%>" maxlength="15" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">��չ�кţ�</td>
      		<td nowrap>
        		<input type="text" name="dev_bankno" class="readonly" style="background-color:#DDDDDD" readonly  value="<%=FCPosMerchant.getDev_bankno()%>" maxlength="15" size="20">* 
        	</td>
      		<td nowrap align="right">��Ͻ�кţ�</td>
      		<td  nowrap>
        		<input type="text" name="man_bankno" class="readonly" style="background-color:#DDDDDD" readonly value="<%=FCPosMerchant.getMan_bankno()%>" maxlength="15" size="20">* 
        	</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�ն������루TCC��:</td>
      		<td nowrap>
      			<input type="text" name="tcc" class='wideinput' value="<%=FCPosMerchant.getTcc()%>" maxlength="1" size="20">*
      		</td>
      		<td nowrap align="right">�̻�״̬��</td>
      		<td  nowrap>
      			<select name="mer_state" size="1">
				<option value="0" <%=FCPosMerchant.getMer_state().equals("0")?"selected":""%>>�����̻�</option>
				<option value="1" <%=FCPosMerchant.getMer_state().equals("1")?"selected":""%>>�����̻�</option>
				<option value="2" <%=FCPosMerchant.getMer_state().equals("2")?"selected":""%>>����̻�</option>
				<option value="7" <%=FCPosMerchant.getMer_state().equals("7")?"selected":""%>>���տ���ͣ���̻�</option>
				<option value="9" <%=FCPosMerchant.getMer_state().equals("9")?"selected":""%>>�˹�ͣ���̻�</option>
       			</select>*
	  		</td>
    	</tr>
  	</table>
	<table border=1>
		<tr>
      		<td colspan="7" align="left" nowrap>�⿨��ͨ��־��</td>
	  		<td><input type="hidden" name="open_flag" value="<%=FCPosMerchant.getOpen_flag()%>"></td>
		</tr>
    	<tr>
      		<td width="70" nowrap>
        		<input name="open_flag1" type="checkbox" class="radio" onClick="javascript:onCondition()" value="" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"checked":""%>>Visa 
        	</td>
      		<td width="100" rowspan="2" align="right" nowrap>MCC��</td>
      		<td width="100" rowspan="2" nowrap>
      			<input type="text" name="mcc" class="wideinput"  value="<%=FCPosMerchant.getMcc()%>" maxlength="4" size="10" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(0)=='1' || FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td width="80" nowrap>�ؿ���Visa</td>
      		<td width="150" nowrap>
      			<input type="text" name="rate_visa" class="wideinput"  value="<%=FCPosMerchant.getRate_visa()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td width="80" nowrap>������Visa</td>
      		<td width="150" nowrap>
      			<input type="text" name="rate_visa2" class="wideinput"  value="<%=FCPosMerchant.getRate_visa2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag2" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"checked":""%>>Master 
      		</td>
      		<td nowrap>�ؿ���Master</td>
      		<td nowrap>
      			<input type="text" name="rate_master" class="wideinput"  value="<%=FCPosMerchant.getRate_master()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>������Master</td>
      		<td nowrap>
      			<input type="text" name="rate_master2" class="wideinput"  value="<%=FCPosMerchant.getRate_master2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag3" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"checked":""%>>AE 
      		</td>
      		<td nowrap align="right">Ĭ��AE�̻��ţ�</td>
      		<td nowrap>
      			<input type="text" name="mer_id_ae" class="wideinput"  value="<%=FCPosMerchant.getMer_id_ae()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>�ؿ���AE</td>
     	 	<td nowrap>
     	 		<input type="text" name="rate_ae" class="wideinput"  value="<%=FCPosMerchant.getRate_ae()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
     	 	</td>
      		<td nowrap>������AE</td>
      		<td nowrap>
      			<input type="text" name="rate_ae2" class="wideinput"  value="<%=FCPosMerchant.getRate_ae2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag4" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"checked":""%>>Dinner
      		</td>
      		<td nowrap align="right">Ĭ��Dinner�̻��ţ�</td>
     	 	<td nowrap>
     	 		<input type="text" name="mer_id_dinner" class="wideinput"  value="<%=FCPosMerchant.getMer_id_dinner()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
     	 	</td>
     	 	<td nowrap>�ؿ���Dinner</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner" class="wideinput"  value="<%=FCPosMerchant.getRate_dinner()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>������Dinner</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner2" class="wideinput"  value="<%=FCPosMerchant.getRate_dinner2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag5" value="" class="radio" onClick="javascript:onCondition()" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"checked":""%>>JCB
      		</td>
      		<td nowrap align="right">Ĭ��JCB�̻��ţ�</td>
      		<td nowrap>
      			<input type="text" name="mer_id_jcb" class="wideinput"  value="<%=FCPosMerchant.getMer_id_jcb()%>" maxlength="15" size="15" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>�ؿ���JCB</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb" class="wideinput"  value="<%=FCPosMerchant.getRate_jcb()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td nowrap>������JCB</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb2" class="wideinput"  value="<%=FCPosMerchant.getRate_jcb2()%>" maxlength="20" size="20" <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
  	</table>
	<table width="90%">
    	<tr>
      		<td width="15%" align="right" nowrap>Ĭ�ϻ����˺ţ�</td>
      		<td width="28%" nowrap>
      			<input type="text" name="acct_no" class="wideinput" value="<%=FCPosMerchant.getAcct_no()%>" maxlength="50" size="20">*</td>
      		<td width="15%" align="right" nowrap>Ĭ�ϻ����˺ſ����У�</td>
      		<td width="42%" colspan="3" nowrap>
      			<input type="text" name="acct_bankno" class="wideinput" value="<%=FCPosMerchant.getAcct_bankno()%>" maxlength="9" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">Ĭ�ϻ����˻����ƣ�</td>
      		<td nowrap>
      			<input type="text" name="acct_name" class="wideinput" value="<%=FCPosMerchant.getAcct_name()%>" maxlength="40" size="20">*
      		</td>
      		<td nowrap align="right">���ʵ���ַ��</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_addr" class="wideinput" value="<%=FCPosMerchant.getBill_ck_addr()%>" maxlength="100" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">���ʵ��ʱࣺ</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_zip" class="wideinput" value="<%=FCPosMerchant.getBill_ck_zip()%>" maxlength="6" size="20">
      		</td>
      		<td nowrap align="right">���ʵ��ռ��ˣ�</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_rcv" class="wideinput" value="<%=FCPosMerchant.getBill_ck_rcv()%>" maxlength="20" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">���ʵ�email��</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_email" class="wideinput" value="<%=FCPosMerchant.getBill_ck_email()%>" maxlength="40" size="20">
      		</td>
      		<td nowrap align="right">���ʵ����棺</td>
      		<td nowrap>
      			<input type="text" name="bill_ck_fax" class="wideinput" value="<%=FCPosMerchant.getBill_ck_fax()%>" maxlength="40" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�̻�����</td>
      		<td nowrap>
      			<input type="text" name="mer_level" class="wideinput" value="<%=FCPosMerchant.getMer_level()%>" maxlength="1" size="20">
      		</td>
      		<td nowrap align="right">�̻�״̬��</td>
      		<td nowrap>	
      			<input type="text" name="mer_state" class="wideinput" value="<%=FCPosMerchant.getMer_state()%>" maxlength="1" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�ʽ�������ڣ�</td>
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
          		<input type="submit" name="submit1" value="�ύ" class="inputbutton">
          		<input type="button" name="cancel1" value="ȡ��" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/fcardmerchantconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>