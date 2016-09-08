<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posFCardInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSFCardInfo" scope="request"/>

<html>
<head>
	<title>�����̻�</title>
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
		document.formReg.rate_visa2.disabled=false;
		open_flag1 = "1";
	}else{
		document.formReg.rate_visa2.value="";
		document.formReg.rate_visa2.disabled=true;
	}
//master
	if(document.formReg.open_flag2.checked){
		document.formReg.rate_master2.disabled=false;
		open_flag2 = "1";
	}else{
		document.formReg.rate_master2.value="";
		document.formReg.rate_master2.disabled=true;
	}
//ae
	if(document.formReg.open_flag3.checked){
		document.formReg.mer_id_ae.disabled=false;
		document.formReg.rate_ae2.disabled=false;
		open_flag3 = "1";
	}else{
		document.formReg.mer_id_ae.value="";
		document.formReg.mer_id_ae.disabled=true;
		document.formReg.rate_ae2.value="";
		document.formReg.rate_ae2.disabled=true;
	}
//dinner
	if(document.formReg.open_flag4.checked){
		document.formReg.mer_id_dinner.disabled=false;
		document.formReg.rate_dinner2.disabled=false;
		open_flag4 = "1";
	}else{
		document.formReg.mer_id_dinner.value="";
		document.formReg.mer_id_dinner.disabled=true;
		document.formReg.rate_dinner2.value="";
		document.formReg.rate_dinner2.disabled=true;
	}
//jcb
	if(document.formReg.open_flag5.checked){
		document.formReg.mer_id_jcb.disabled=false;
		document.formReg.rate_jcb2.disabled=false;
		open_flag5 = "1";
	}else{
		document.formReg.mer_id_jcb.value="";
		document.formReg.mer_id_jcb.disabled=true;
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
	if (isNull("pos_no", "POS���")) return false;
	if (document.formReg.pos_no.value.length != 8){
		alert("POS��ű���Ϊ8λ");
		return false;
	}
	if( isNull("mer_id","�̻����")) return false;
	if (document.formReg.open_flag3.checked){
		if (isNull("mer_id_ae", "Ĭ��AE�̻���")) return false;
	}
	if (document.formReg.open_flag4.checked){
		if (isNull("mer_id_dinner", "Ĭ��Dinner�̻���")) return false;
	}
	if (document.formReg.open_flag5.checked){
		if (isNull("mer_id_jcb", "Ĭ��JCB�̻���")) return false;
	}
	if (isNull("acct_no", "Ĭ�ϻ����˺�")) return false;
	if (isNull("acct_bankno", "Ĭ�ϻ����˺ſ�����")) return false;

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
</script>

<body   bgcolor="#CCCCCC" text="#000000">	
<%
	String uid=request.getParameter("uid");
	String postCode="";
	POSFCardInfo info = (POSFCardInfo)request.getAttribute("posInfo");
  %>
<h2><font face="����">�⿨POS�ն�����</font></h2>
<form name="formReg" method="post" action="/ToucsMonitor/fcardposconfig?reqCode=13502&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
	<table id="tab0" width="76%">
    	<tr>
      		<td colspan="6" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="19%" nowrap align="right" >�ն˱�ţ�</td>
      		<td width="23%" nowrap>
      			<input type="text" name="pos_no" class="readonly"   value="<%=info.getPos_no()%>" maxlength="8" size="20"> * 
      		</td>
      		<td width="26%" nowrap align="right">�����̻���ţ�</td>
      		<td width="32%" nowrap>
      			<input type="text" name="mer_id"  class="readonly"   value="<%=info.getMer_id()%>" maxlength="15" size="20">* 
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">Ĭ�ϻ����˺ţ�</td>
      		<td nowrap>
      			<input type="text" name="acct_no" class="readonly"   value="<%=info.getAcct_no()%>" maxlength="10" size="20">
      		</td>
      		<td nowrap align="right">Ĭ�ϻ����˺ſ����У�</td>
      		<td nowrap>
      			<input type="text" name="acct_bankno" class="readonly"   value="<%=info.getAcct_bankno()%>" maxlength="10" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">Ĭ�ϻ����˻����ƣ�</td>
      		<td nowrap>
      			<input type="text" name="acct_name" class="readonly"   value="<%=info.getAcct_name()%>" maxlength="60" size="20">
      		</td>
      		<td nowrap align="right">�����������</td>
      		<td nowrap >
      			<input type="text" name="acct_func" class="readonly"   value="<%=info.getAcct_func()%>" maxlength="10" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">�ն�״̬��</td>
      		<td nowrap>
			<%=info.getState().equals("0")?"����״̬":""%>
			<%=info.getState().equals("1")?"����POS":""%>
			<%=info.getState().equals("2")?"�ص���POS":""%>
			<%=info.getState().equals("7")?"���տ���ͣ��POS":""%>
			<%=info.getState().equals("9")?"�˹�ͣ��POS":""%>
	  		</td>
      		<td nowrap align="right">�ն˶���������</td>
      		<td nowrap>
			<%=info.getChip_capability().equals("0")?"���߱�":""%>
			<%=info.getChip_capability().equals("1")?"�߱�":""%>
	  		</td>
    	</tr>
  	</table>
	<table width="75%" border="1">
		<tr>
	  		<td nowrap colspan="5" align="left">�⿨��ͨ��־��</td>
		</tr>
		<tr>
      		<td>
      			<input type="hidden" name="open_flag" value="00000">
      		</td>	
      		<td width="13%" nowrap>
      			<input type="checkbox" name="open_flag1" value="checkbox"   class="radio" onClick="javascript:onCondition()" <%=(info.getOpen_flag().length()>0 && info.getOpen_flag().charAt(0)=='1')?"checked":""%>> Visa 
      		</td>
	  		<td width="22%" align="right" nowrap>&nbsp;</td>
	  		<td nowrap>&nbsp;</td>
	  		<td width="18%" align="right" nowrap>������Visa��</td>
      		<td width="21%" nowrap>
      			<input type="text" name="rate_visa2" class="readonly"   value="<%=info.getRate_visa2()%>" maxlength="20" size="15" <%=(info.getOpen_flag().length()>0 && info.getOpen_flag().charAt(0)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag2" value="checkbox" class="radio"   onClick="javascript:onCondition()" <%=(info.getOpen_flag().length()>1 && info.getOpen_flag().charAt(1)=='1')?"checked":""%>> Master 
      		</td>
      		<td width="22%" align="right" nowrap>&nbsp;</td>
      		<td nowrap>&nbsp;</td>
      		<td align="right" nowrap>������Master��</td>
      		<td nowrap>
      			<input type="text" name="rate_master2" class="readonly"   value="<%=info.getRate_master2()%>" maxlength="20" size="15" <%=(info.getOpen_flag().length()>1 && info.getOpen_flag().charAt(1)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag3" value="checkbox" class="radio"   onClick="javascript:onCondition()" <%=(info.getOpen_flag().length()>2 && info.getOpen_flag().charAt(2)=='1')?"checked":""%>> AE 
      		</td>
      		<td nowrap align="right">Ĭ��AE�̻��ţ�</td>
      		<td nowrap>
      			<input type="text" name="mer_id_ae" class="readonly"    value="<%=info.getMer_id_ae()%>" maxlength="50" size="20" <%=(info.getOpen_flag().length()>2 && info.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td align="right" nowrap>������ae��</td>
      		<td nowrap>
      			<input type="text" name="rate_ae2" class="readonly"   value="<%=info.getRate_ae2()%>" maxlength="20" size="15" <%=(info.getOpen_flag().length()>2 && info.getOpen_flag().charAt(2)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag4" value="checkbox" class="radio"   onClick="javascript:onCondition()" <%=(info.getOpen_flag().length()>3 && info.getOpen_flag().charAt(3)=='1')?"checked":""%>> Dinner
      		</td>
      		<td nowrap align="right">Ĭ��Dinner�̻��ţ�</td>
      		<td nowrap>
      			<input type="text" name="mer_id_dinner"  class="readonly"   value="<%=info.getMer_id_dinner()%>" maxlength="50" size="20" <%=(info.getOpen_flag().length()>3 && info.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td align="right" nowrap>������Dinner��</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner2" class="readonly"   value="<%=info.getRate_dinner2()%>" maxlength="20" size="15" <%=(info.getOpen_flag().length()>3 && info.getOpen_flag().charAt(3)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag5" value="checkbox" class="radio"   onClick="javascript:onCondition()" <%=(info.getOpen_flag().length()>4 && info.getOpen_flag().charAt(4)=='1')?"checked":""%>>JCB
      		</td>
      		<td nowrap align="right">Ĭ��JCB�̻��ţ�</td>
      		<td width="26%" nowrap>
      			<input type="text" name="mer_id_jcb"  class="readonly"    value="<%=info.getMer_id_jcb()%>" maxlength="50" size="20"  <%=(info.getOpen_flag().length()>4 && info.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
      		<td align="right" nowrap>������Jcb��</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb2"  class="readonly"   value="<%=info.getRate_jcb2()%>" maxlength="20" size="15" <%=(info.getOpen_flag().length()>4 && info.getOpen_flag().charAt(4)=='1')?"":"disabled='true'"%>>
      		</td>
    	</tr>
	</table>
	<table>
    	<tr> 
      		<td width="683" colspan="6" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="6" nowrap> 
        		<div align="right"> 
          		<input type="button" name="cancel" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>