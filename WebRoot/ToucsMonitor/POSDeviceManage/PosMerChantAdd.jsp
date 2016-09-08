<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>


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
<title>�����̻�</title>
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
	if( isNull("merchant_id","�̻����")) return false;
	if (document.forms[0].merchant_id.value.length != 15)
	{
		alert("�̻��ű���Ϊ15λ");
		return false;
	}
	if( isNull("mct_name","�̻�����")) return false;
	if( isNull("mct_type","�̻�����")) return false;
	if( isNull("flag","ʹ�ñ�־")) return false;
	if( isNull("clear_flag","�����־")) return false;
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

<body  bgcolor="#CCCCCC" text="#000000">
<%
  			String uid=request.getParameter("uid");

  %>
<form name="formReg" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=10401&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="managebankname_temp" value="">
<font face="����" size="+2">�̻�����</font>
  <table id="tab0" width="80%">
    <tr>
      <td colspan="4" nowrap>
        <hr noshade>      </td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right" >�̻���ţ�</td>
      <td width="29%" nowrap>
        <input type="text" name="merchant_id" class="wideinput" value="" MAXLENGTH="15" size="20">*</td>
      <td width="17%" nowrap align="right">
        �̻����ƣ�      </td>
      <td width="40%" nowrap>
        <input type="text" name="mct_name" class="wideinput" value="" MAXLENGTH="40" size="20">*</td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">
        �̻�Ӣ�����ƣ�      </td>
      <td width="29%" nowrap>
        <input type="text" name="mct_enname" class="wideinput"  value="" MAXLENGTH="50" size="20"></td>
     <td width="17%" nowrap align="right">�̻����ͣ�</td>
      <td width="40%" nowrap>
        <select name="mct_type" size="1">
          <option value="">��ѡ��</option>
          <%initList("McttypeList","",request,out);%>
        </select>*</td>
    </tr>
	<input type="hidden" name="flag" value="1">
	<input type="hidden" name="clear_flag" value="1">

    <tr>
      <td width="14%" nowrap align="right">���ܻ������ƣ�</td>
      <td width="29%" nowrap>
      	<select name="manage_bankname" size="1" onChange="javascript:onBankOrgChange()">
            <option value="">��ѡ��</option>
            <%initList("orgList","",request,out);%>
          </select>*       </td>
      <td width="17%" nowrap align="right">���ܻ����ţ�</td>
      <td width="40%" nowrap>
        <input type="text" name="manage_bankno" class="readonly" readonly value="" MAXLENGTH="15" size="20"></td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">����������ƣ�</td>
      <td width="29%" nowrap>
          <select name="org_name" size="1" onChange="javascript:onOrgChange()">
            <option value="">��ѡ��</option>
            <%initList("cardOrgList","",request,out);%>
          </select>*</td>
      <td width="17%" nowrap align="right">��������ţ�</td>
      <td width="40%" nowrap>
        <input type="text" name="org_id" class="readonly" readonly value="" MAXLENGTH="10" size="20"></td>
    </tr>
         <input type="hidden" name="operator" value="">
    <tr>
      <td width="14%" nowrap align="right">�������շ����ͣ�</td>
      <td width="29%" nowrap>
        <select name="pcard_machine_no" size="1">
          <option value="">��ѡ��</option>
          <%initList("FeeType","0",request,out);%>
        </select>*</td>
      <td width="17%" nowrap align="right">�Ƿ�����Ԥ���ѿ���</td>
      <td width="40%" nowrap>
	    <select name="operator" size="1">
          <option value="">��ѡ��</option>
          <%initList("Operator","",request,out);%>
        </select></td>
    </tr>
	<tr>
	  <td nowrap align="right">POS������</td>
	  <td nowrap><input type="text" name="pos_machine_no" class="wideinput" value="" size="20" >
      </td>
      <td width="17%" nowrap align="right">&nbsp;</td>
      <td width="40%" nowrap>&nbsp;</td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">���˴���</td>
      <td width="29%" nowrap>
        <input type="text" name="artificial_person" class="wideinput" value="" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">�����ˣ�</td>
      <td width="40%" nowrap>
        <input type="text" name="handle_man" class="wideinput" value="" MAXLENGTH="30" size="20"></td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">�绰��</td>
      <td width="29%" nowrap>
        <input type="text" name="telephone" class="wideinput" value="" MAXLENGTH="20" size="20"></td>
      <td width="17%" nowrap align="right">���棺</td>
      <td width="40%" nowrap>
        <input type="text" name="fax" class="wideinput" value="" MAXLENGTH="15" size="20"></td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">�ʱࣺ</td>
      <td width="29%" nowrap>
        <input type="text" name="zipcode" class="wideinput" value="" MAXLENGTH="6" size="20">*</td>
      <td width="17%" nowrap align="right">��ַ��</td>
      <td width="40%" nowrap>
        <input type="text" name="address" class="wideinput" value="" MAXLENGTH="20" size="40"></td>
    </tr>
    <tr>
      <td width="14%" nowrap align="right">ǩԼ���ڣ�</td>
      <td width="29%" nowrap>
        <input type="text" name="signature_date" style="width:80" value="" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
      <td width="17%" nowrap align="right">�������ڣ�</td>
      <td width="40%" nowrap>
        <input type="text" name="cancel_date" style="width:80" value="" MAXLENGTH="8" size="20">(YYYYMMDD)</td>
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
          <input type="submit" name="submit" value="�ύ" class="inputbutton">
          <input type="reset" name="cancel" value="����" class="inputbutton" >
        </div>
      </td>
    </tr>
  </table>
  <p/>
</form>
</body>
</html>