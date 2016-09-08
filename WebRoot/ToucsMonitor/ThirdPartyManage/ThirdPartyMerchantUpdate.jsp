<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.ThirdPartyManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
	ThirdPartyMerchantInfo Info=(ThirdPartyMerchantInfo)request.getAttribute("ThirdPartyMerchant");
	if (Info == null){
		Info = new ThirdPartyMerchantInfo();
	}
%>
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
}
%>
<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){
	if( isNull("merchant_id","�̻���")) return false;
	if( isNull("merchant_name","�̻�����")) return false;
	if( isNull("merchant_type","�̻�����")) return false;
	if( isNull("branch_id","��������")) return false;
	if( isNull("secu_kind","��ȫ��������")) return false;
	if( isNull("memo1","�����ļ����� ")) return false;
	if( isNull("memo2","�����ļ���ʽ ")) return false;

	if (document.forms[0].merchant_code.value.length > 4)	{
		alert("�̻��������С��4λ");
		return false;
	}
	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}

function relat(){
	var argu1 = document.getElementById("merchant_type").value;
	alert(argu1);
	if(argu1=="0"){
		document.formReg.merchant_code.value="BTH";
	}else if (argu1=="1"){
		document.formReg.merchant_code.value="NPS";
	}
}

function onmemo1(){
	var memo11 = document.getElementById("merchant_code").value;
	var memo12 = document.getElementById("merchant_id").value;
	document.formReg.memo1.value=memo11+memo12;
}
function onTypeChange(){
	var type = document.getElementById("memo2").value;
	alert(type);
	if(type=="QPAY"){
		document.formReg.typeDes.value="��������|���״���|������ˮ��|���п���|���׽��||�̻���|�ն˺�||���ж�����";
	}else if (type=="NPS"){
		document.formReg.typeDes.value="��������|����ʱ��|���״���|����|���׽��|������|���ս��|������ˮ��|ԭ������ˮ��|POS�ն˺�|�̻���|������";
	}else if (type=="ZFB"){
		document.formReg.typeDes.value="��������|���״���|������ˮ��|���п���|���׽��|��Ȩ��(��)|�̻���|�ն˺�|ԭ���״���(����)|�������|�ο���(����)|";
	}
}
</script>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/thirdPartyMerchant?reqCode=13403&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">֧��ƽ̨�̻��޸�</font>
	<table id="tab0" width="90%" height="271">
    	<tr>
    		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�̻��ţ�</td>
      		<td width="60%" nowrap height="19">
      		<input type="text" name="merchant_id" class="readonly" value="<%=Info.getMerchantid()%>" READONLY style="background-color:#cccccc" maxlength="15"  size="16" >
      		*</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">�̻����ƣ�</td>
     		<td width="60%" nowrap height="19" >
       		<input type="text" name="merchant_name" class="wideinput" value="<%=Info.getMerchantname()%>"  maxlength="40" size="40" >
     		*</td>
		</tr>
    	<tr>
      		<td width="17%" nowrap align="right">�̻����ͣ�</td>
      		<td width="33%" nowrap>
        		<!--  <select name="merchant_type" size="1" onchange="relat()">-->
        		<select name="merchant_type" size="1">
          		<option value="">��ѡ��</option>
          			<%initList("MchantType",Info.getMerchanttype(),request,out);%>
        		</select>
        	*</td>
       		<td width="16%" nowrap align="right">�̻����룺</td>
       		<td width="34%" nowrap >
       			<input type="text" name="merchant_code" value="<%=Info.getMerchant_code()%>" MAXLENGTH="4" size="20" class="readonly" READONLY style="background-color:#cccccc" onBlur="onmemo1()">
       		*</td>        
    	</tr>
    	<tr>
      		<td width="17%" nowrap align="right">���������</td>
      		<td width="33%" nowrap>
          		<select name="branch_id" size="1" >
            	<option value="">��ѡ��</option>
            	<%
            		List list=(List)request.getAttribute("BranchId");
            		String flag="";
        			for(int i=0;i<list.size();i++){
    					CodeBean code=(CodeBean)list.get(i);
    					if(Info.getBranchid().equals(code.getCodeId())){
    						flag="SELECTED";
    					}else{
    						flag="";
    					}
    			%>
    			<option value="<%=code.getCodeId() %>" <%=flag %> > <%=code.getCodeDesc() %></option>   		
    				<%}%>       
          		</select>*   
    	</tr>
    	<tr>
      		<!-- <td width="17%" nowrap align="right">�շ����ࣺ</td>
      		<td width="33%" nowrap>
        		<select name="fee_kind" size="1">
          		<option value="">��ѡ��</option>
          		
       	 		</select></td> -->
      		<td width="17%" nowrap align="right">��ȫ�������ࣺ</td>
      		<td width="33%" nowrap>
        		<select name="secu_kind" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("SecuKind",Info.getSecukind(),request,out);%>
        		</select>*</td>
    	</tr>
    	<!-- <tr>
      		<td width="17%" nowrap align="right">ҵ����룺</td>
      		<td width="33%" nowrap>
        		<select name="agent_code"  size="1">
          		<option value="">��ѡ��</option>
          		
        		</select>
        	</td>
		</tr>
		 <tr>
			<td width="17%" nowrap height="19" align="right">IP��ַ��</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="ip_addr" class="wideinput" value=""  maxlength="16" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">�˿ںţ�</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="port" class="wideinput" value=""  maxlength="6" size="6" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ʺţ�</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct" class="wideinput" value=""  maxlength="32" size="32" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ʺ�1��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct1" class="wideinput" value=""  maxlength="32" size="32" >
			</td>
		</tr>-->
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ļ����ƣ�</td>
			<td colspan="3" >
				<input type="text" name="memo1" value="<%=Info.getMemo1()%>"  maxlength="40" size="40">
				<font color="red" size="3">�����ļ��������̻�������̻������(֧��������)</font>
			*</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ļ���ʽ��</td>
			<td width="60%" nowrap height="19" >
				<select name="memo2" id="memo2" onchange="javascript:onTypeChange()">
	            <option value=" "></option>
	            <%		
	            	String sel1="";
	            	String sel2="";
	            	String sel3="";
	            	if(Info.getMemo2().trim().equals("QPAY")){
	            		sel1="selected";
	            	}else if(Info.getMemo2().trim().equals("NPS")){
	            		sel2="selected";
	            	}else if(Info.getMemo2().trim().equals("ZFB")){
	            		sel3="selected";
	            	}
				%>
	            <option value="QPAY" <%=sel1%>> QPAY</option>
	            <option value="NPS" <%=sel2%>> NPS</option>
	            <option value="ZFB" <%=sel3%>> ZFB</option>
	          	</select>
	       
			*</td>
		</tr>
		<tr>
		<td width="60%" nowrap height="19" align="right" >��ʽ˵����</td>
		<td colspan="3">
		<input type="text" name="typeDes" class="wideinput" value="<%=Info.getTypeDes()%>" size="95" readonly >
		</td>
		</tr>
		<!--  <tr>
			<td width="20%" nowrap height="19" align="right">��ע3��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo3" class="wideinput" value="<%=Info.getMemo3()%>"  maxlength="40" size="40" >
			</td>
		</tr>-->
		<tr>
	      	<td colspan="4" nowrap><hr noshade></td>
		</tr>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        	<div align="right">
          		<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
          		<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
        	</div>
     		</td>
    	</tr>
  	</table>
  <p/>
</form>
</body>
</html>