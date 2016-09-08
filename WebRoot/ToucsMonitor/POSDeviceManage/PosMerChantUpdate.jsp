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
	
	if( isNull("merchant_id","�̻����")) return false;
	if (document.forms[0].merchant_id.value.length != 15)
	{
		alert("�̻��ű���Ϊ15λ");
		return false;
	}
	if( isNull("mct_name","�̻�����")) return false;
	if( isNull("mct_type","�̻�����")) return false;
	if( isNull("flag","ʹ�ñ�־")) return false;
	if( isNull("clear_flag","������־")) return false;
	if( isNull("manage_bankno","����֧�к�")) return false;
	if( isNull("manage_bankname","����֧������")) return false;
	if( isNull("org_id","������")) return false;

	if(!isNumber("pcard_machine_no"," ѹ����̨��"))	return false;
	if(!isNumber("pos_machine_no","POS����")) return false;
	if(!isNumber("zipcode","��������"))	return false;
	if (document.forms[0].zipcode.value.length != 6)
	{
		alert("�����������Ϊ6λ");
		return false;
	}
	if(!validFullDate("signature_date","ǩԼ����"))	return false;
	if(!validFullDate("cancel_date","��������")) return false;
	
	if(confirm("ȷ�����������ύ��"))
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
<font face="����" size="+2">�̻��޸�</font>
   <table id="tab0" width="80%">
    <tr><td colspan="4" nowrap><hr noshade></td></tr>
    <tr> 
      <td width="20%" nowrap align="right" >�̻���ţ�</td>
      <td width="23%" nowrap> 
        <input type="text" name="merchant_id" class="readonly" readonly style="background-color:#cccccc"  value="<%=posMerchantInfo.getMerchantid()%>" size="20"></td>
      <td width="17%" nowrap align="right"> 
        �̻����ƣ�</td>
      <td width="40%" nowrap> 
        <input type="text" name="mct_name" class="wideinput" value="<%=posMerchantInfo.getMctname()%>" MAXLENGTH="40" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right"> 
        �̻�Ӣ�����ƣ�      </td>
      <td width="23%" nowrap> 
        <input type="text" name="mct_enname" class="wideinput"  value="<%=posMerchantInfo.getMctenname()%>" MAXLENGTH="50" size="20"></td>
      <td width="17%" nowrap align="right">�̻����ͣ�</td>
      <td width="40%" nowrap>
        <select name="mct_type" size="1">
          <option value="">��ѡ��</option>
          <%initList("McttypeList",posMerchantInfo.getMcttype(),request,out);%>
        </select></td>
    </tr>
	<input type="hidden" name="flag" value="1">
	<input type="hidden" name="clear_flag" value="1">
    <!--tr> 
      <td width="17%" nowrap align="right">ʹ�ñ�־��</td>
      <td width="33%" nowrap> 
        <select name="flag" size="1">
          <option value="">��ѡ��</option>
          <%initList("FlagList",posMerchantInfo.getFlag(),request,out);%>
        </select>
      </td>
      <td width="16%" nowrap align="right">������־��</td>
      <td width="34%" nowrap>
      	<select name="clear_flag" size="1">
          <option value="">��ѡ��</option>
      	<%initList("ClearFlagList",posMerchantInfo.getClearflag(),request,out);%>
      	 </select>
    ��</td>
    </tr-->
    <tr> 
      <td width="20%" nowrap align="right">���ܻ������ƣ�</td>
      <td width="23%" nowrap> 
      	<select name="manage_bankname" size="1" onChange="javascript:onBankOrgChange()">
            <option value="">��ѡ��</option>
            <%initList("orgList",posMerchantInfo.getManagebankno(),request,out);%>			
          </select></td>
      <td width="17%" nowrap align="right">���ܻ����ţ�</td>
      <td width="40%" nowrap> 
        <input type="text" name="manage_bankno" class="readonly" readonly value="<%=posMerchantInfo.getManagebankno()%>" MAXLENGTH="15" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">����������ƣ�</td>
      <td width="23%" nowrap> 
        <div align="left"> 
          <select name="org_name" size="1" onChange="javascript:onOrgChange()">
            <option value="">��ѡ��</option>
            <%initList("cardOrgList",posMerchantInfo.getOrgid(),request,out);%>			
          </select>
        </div></td>
      <td width="17%" nowrap align="right">��������ţ�</td>
      <td width="40%" nowrap> 
        <input type="text" name="org_id" class="readonly" readonly value="<%=posMerchantInfo.getOrgid()%>" MAXLENGTH="10" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">�������շ����ͣ�</td>
      <td width="23%" nowrap> 
        <select name="pcard_machine_no" size="1">
          <option value="">��ѡ��</option>
          <%initList("FeeType",posMerchantInfo.getPcardmachine_no(),request,out);%>
        </select>	  </td>
      <td width="17%" nowrap align="right">�Ƿ�����Ԥ���ѿ���</td>
      <td width="40%" nowrap>
	    <select name="operator" size="1">
          <option value="">��ѡ��</option>
          <%initList("Operator",posMerchantInfo.getOperator(),request,out);%>
        </select>
	  </td>
    </tr>
	<tr>
      <td nowrap align="right">POS������</td>
      <td nowrap><input type="text" name="pos_machine_no" class="wideinput" value="<%=posMerchantInfo.getPosmachine_no()%>" size="20" >      </td> 
      <td width="17%" nowrap align="right">&nbsp;</td>
      <td width="40%" nowrap>&nbsp;</td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">���˴�����</td>
      <td width="23%" nowrap> 
        <input type="text" name="artificial_person" class="wideinput" value="<%=posMerchantInfo.getArtificialperson()%>" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">�����ˣ�</td>
      <td width="40%" nowrap> 
        <input type="text" name="handle_man" class="wideinput" value="<%=posMerchantInfo.getHandleman()%>" MAXLENGTH="30" size="20"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">�绰��</td>
      <td width="23%" nowrap> 
        <input type="text" name="telephone" class="wideinput" value="<%=posMerchantInfo.getTelephone()%>" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">���棺</td>
      <td width="40%" nowrap> 
        <input type="text" name="fax" class="wideinput" value="<%=posMerchantInfo.getFax()%>" MAXLENGTH="15" size="20">      </td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">�ʱࣺ</td>
      <td width="23%" nowrap> 
        <input type="text" name="zipcode" class="wideinput" value="<%=posMerchantInfo.getZipcode()%>" MAXLENGTH="6" size="20"></td>
      <td width="17%" nowrap align="right">��ַ��</td>
      <td width="40%" nowrap> 
        <input type="text" name="address" class="wideinput" value="<%=posMerchantInfo.getAddress()%>" MAXLENGTH="20" size="40"></td>
    </tr>
    <tr> 
      <td width="20%" nowrap align="right">ǩԼ���ڣ�</td>
      <td width="23%" nowrap> 
        <input type="text" name="signature_date" style="width:80" value="<%=posMerchantInfo.getSignaturedate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
      <td width="17%" nowrap align="right">�������ڣ�</td>
      <td width="40%" nowrap> 
        <input type="text" name="cancel_date" style="width:80" value="<%=posMerchantInfo.getCanceldate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
    </tr>
</table>
<table width="80%"><tr><td colspan="5" nowrap> 
<hr noshade></td></tr><tr><td colspan="5" nowrap> <div align="right"> 
          <input type="submit" name="submit" value="�ύ" class="inputbutton">
          <input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick=history.back()>
</div></td></tr></table><p/></form></body></html>