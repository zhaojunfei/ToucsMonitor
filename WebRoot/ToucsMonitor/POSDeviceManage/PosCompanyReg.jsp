<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
PosCompanyInfo Info=(PosCompanyInfo)request.getAttribute("PosCompany");
if (Info == null)
{
	Info = new PosCompanyInfo();
}
%>
<%!
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out)
{
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
}
%>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function onBranchNameChange(){
	document.forms[0].branch_id.value= document.forms[0].branch_name.value;
}

function doSubmit(){
	if( isNull("company_id","��˾���")) return false;
	if( isNull("company_name","��˾����")) return false;
	if( isNull("company_sname","��˾���")) return false;
	if( isNull("company_type","��˾����")) return false;
	if( isNull("secu_kind","��ȫ��������")) return false;

	if (document.forms[0].company_id.value.length != 9)	{
		alert("��˾��ű���Ϊ9λ");
		return false;
	}
	
	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
</script>
<head>
<title>��ҵIC����˾��</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/poscompany?reqCode=16501&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">��ҵIC����˾����</font>
  <table id="tab0" width="90%" height="271">
    <tr>
      <td colspan="3" nowrap>
        <hr noshade>
	  </td>
    </tr>
    <tr>
      <td width="20%" nowrap height="19" align="right" >��˾��ţ�</td>
      <td width="60%" nowrap height="19">
      	<input type="text" name="company_id" class="wideinput" value="" maxlength="9"  size="10" >
      *</td>
    </tr>
<tr>
     <td width="20%" nowrap height="19" align="right">��˾���ƣ�</td>
     <td width="60%" nowrap height="19" >
       <input type="text" name="company_name" class="wideinput" value=""  maxlength="40" size="40" >
     *</td>
</tr>
<tr>
     <td width="20%" nowrap height="19" align="right">��˾��ƣ�</td>
     <td width="60%" nowrap height="19" >
       <input type="text" name="company_sname" class="wideinput" value=""  maxlength="10" size="12" >
     *</td>
</tr>
    <tr>
      <td width="20%" nowrap align="right">��˾���ͣ�</td>
      <td width="60%" nowrap>
	    <select name="company_type" size="1">
          <option value="">��ѡ��</option>
            <%initList("CompanyKind","",request,out);%>
        </select>*</td>
    </tr>
     <tr>
      <td width="20%" nowrap align="right">����������</td>
      <td width="60%" nowrap>
          <input type="text" name="branch_id" class="wideinput" value="" maxlength="15"  size="16" >
	  </td>
    </tr>
    <tr>
      <td nowrap align="right">��ȫ�������ࣺ</td>
      <td nowrap><select name="secu_kind" size="1">
          <option value="">��ѡ��</option>
          <%initList("SecuKind","",request,out);%>
        </select>*</td>
</tr><tr><td width="20%" nowrap height="19" align="right">IP��ַ��</td><td width="60%" nowrap height="19" >
	 <input type="text" name="ip_addr" class="wideinput" value=""  maxlength="16" size="16">
	 *</td>
</tr><tr><td nowrap height="19" align="right">�˿ںţ�</td><td nowrap height="19" >
	<input type="text" name="port" class="wideinput" value=""  maxlength="6" size="6" >
	*</td>
</tr><tr><td nowrap height="19" align="right">����״̬��</td><td nowrap height="19" >
	<input type="radio" name="health_stat" value="1" class="radio" checked >����
	<input type="radio" name="health_stat" class="radio" value="0">������ </td>
</tr><tr><td width="20%" nowrap height="19" align="right">��ע1��</td><td width="60%" nowrap height="19" >
	<input type="text" name="memo1" class="wideinput" value=""  maxlength="40" size="40"></td>
</tr><tr><td width="20%" nowrap height="19" align="right">��ע2��</td><td width="60%" nowrap height="19" >
	<input type="text" name="memo2" class="wideinput" value=""  maxlength="40" size="40"></td>
</tr><tr><td width="20%" nowrap height="19" align="right">��ע3��</td><td width="60%" nowrap height="19" >
	<input type="text" name="memo3" class="wideinput" value=""  maxlength="40" size="40"></td>
</tr><tr><td colspan="3" nowrap><hr noshade></td></tr></table>
<table width="80%"><tr><td colspan="5" nowrap><div align="right">
    <input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
	<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick="javascript:location.href='/ToucsMonitor/POSDeviceManage/PosCompanyManage.jsp'">
</div></td></tr></table><p/></form></body></html>