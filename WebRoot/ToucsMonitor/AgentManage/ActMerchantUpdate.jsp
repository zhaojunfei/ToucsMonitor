<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.AgentManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
ActMerchantInfo Info=(ActMerchantInfo)request.getAttribute("ActMerchant");
if (Info == null){
	Info = new ActMerchantInfo();
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
	if( isNull("merchant_id","�̻���")) return false;
	if( isNull("merchant_name","�̻�����")) return false;
	if( isNull("merchant_type","�̻�����")) return false;
	if( isNull("branch_id","��������")) return false;
	if( isNull("fee_kind","�շ�����")) return false;
	if( isNull("secu_kind","��ȫ��������")) return false;
	if( isNull("bank_acct","�����ʺ�")) return false;

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
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
<form name="formReg" method="post" action="/ToucsMonitor/actmerchant?reqCode=17503&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">֧��ƽ̨�̻��޸�</font>
  	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >�̻��ţ�</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="merchant_id" class="readonly" readonly value="<%=Info.getMerchantid()%>" READONLY style="background-color:#cccccc" maxlength="15"  size="16" >
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
        		<select name="merchant_type" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("MchantType",Info.getMerchanttype(),request,out);%>
        		</select>*
        	</td>
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
      		</td>   
    	</tr>
    	<tr>
      		<td width="17%" nowrap align="right">�շ����ࣺ</td>
      		<td width="33%" nowrap>
        		<select name="fee_kind" size="1">
         		<option value="">��ѡ��</option>
          		<%initList("FeeKind",Info.getFeekind(),request,out);%>
        		</select>*
        	</td>
      		<td width="17%" nowrap align="right">��ȫ�������ࣺ</td>
      		<td width="33%" nowrap>
      			<select name="secu_kind" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("SecuKind",Info.getSecukind(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="17%" nowrap align="right">ҵ����룺</td>
      		<td width="33%" nowrap>
        		<select name="agent_code"  size="1">
          		<option value="">��ѡ��</option>
          		<%initList("AgentCode",Info.getAgentcode(),request,out);%>
        		</select>
        	</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">IP��ַ��</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="ip_addr" class="wideinput" value="<%=Info.getIpaddr()%>"  maxlength="16" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">�˿ںţ�</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="port" class="wideinput" value="<%=Info.getPort()%>"  maxlength="6" size="6" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ʺţ�</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct" class="wideinput" value="<%=Info.getBankacct()%>"  maxlength="32" size="32" >
			*</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">�����ʺ�1��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct1" class="wideinput" value="<%=Info.getBankacct1()%>"  maxlength="32" size="32" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ע1��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo1" class="wideinput" value="<%=Info.getMemo1()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ע2��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo2" class="wideinput" value="<%=Info.getMemo2()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">��ע3��</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo3" class="wideinput" value="<%=Info.getMemo3()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
	      <td colspan="4" nowrap>
	        <hr noshade>
	      </td>
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