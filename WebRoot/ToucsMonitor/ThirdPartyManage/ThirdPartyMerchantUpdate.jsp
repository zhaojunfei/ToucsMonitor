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
	if( isNull("merchant_id","商户号")) return false;
	if( isNull("merchant_name","商户名称")) return false;
	if( isNull("merchant_type","商户类型")) return false;
	if( isNull("branch_id","所属机构")) return false;
	if( isNull("secu_kind","安全处理种类")) return false;
	if( isNull("memo1","对账文件名称 ")) return false;
	if( isNull("memo2","对账文件格式 ")) return false;

	if (document.forms[0].merchant_code.value.length > 4)	{
		alert("商户代码必须小于4位");
		return false;
	}
	if(confirm("确认输入无误并提交吗？"))
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
		document.formReg.typeDes.value="交易日期|交易代码|交易流水号|银行卡号|交易金额||商户号|终端号||银行订单号";
	}else if (type=="NPS"){
		document.formReg.typeDes.value="交易日期|交易时间|交易代码|卡号|交易金额|手续费|净收金额|交易流水号|原交易流水号|POS终端号|商户号|订单号";
	}else if (type=="ZFB"){
		document.formReg.typeDes.value="交易日期|交易代码|交易流水号|银行卡号|交易金额|授权号(无)|商户号|终端号|原交易代码(暂无)|订单编号|参考号(暂无)|";
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
<font face="隶书" size="+2">支付平台商户修改</font>
	<table id="tab0" width="90%" height="271">
    	<tr>
    		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >商户号：</td>
      		<td width="60%" nowrap height="19">
      		<input type="text" name="merchant_id" class="readonly" value="<%=Info.getMerchantid()%>" READONLY style="background-color:#cccccc" maxlength="15"  size="16" >
      		*</td>
    	</tr>
		<tr>
     		<td width="20%" nowrap height="19" align="right">商户名称：</td>
     		<td width="60%" nowrap height="19" >
       		<input type="text" name="merchant_name" class="wideinput" value="<%=Info.getMerchantname()%>"  maxlength="40" size="40" >
     		*</td>
		</tr>
    	<tr>
      		<td width="17%" nowrap align="right">商户类型：</td>
      		<td width="33%" nowrap>
        		<!--  <select name="merchant_type" size="1" onchange="relat()">-->
        		<select name="merchant_type" size="1">
          		<option value="">请选择</option>
          			<%initList("MchantType",Info.getMerchanttype(),request,out);%>
        		</select>
        	*</td>
       		<td width="16%" nowrap align="right">商户代码：</td>
       		<td width="34%" nowrap >
       			<input type="text" name="merchant_code" value="<%=Info.getMerchant_code()%>" MAXLENGTH="4" size="20" class="readonly" READONLY style="background-color:#cccccc" onBlur="onmemo1()">
       		*</td>        
    	</tr>
    	<tr>
      		<td width="17%" nowrap align="right">清算机构：</td>
      		<td width="33%" nowrap>
          		<select name="branch_id" size="1" >
            	<option value="">请选择</option>
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
      		<!-- <td width="17%" nowrap align="right">收费种类：</td>
      		<td width="33%" nowrap>
        		<select name="fee_kind" size="1">
          		<option value="">请选择</option>
          		
       	 		</select></td> -->
      		<td width="17%" nowrap align="right">安全处理种类：</td>
      		<td width="33%" nowrap>
        		<select name="secu_kind" size="1">
          		<option value="">请选择</option>
          		<%initList("SecuKind",Info.getSecukind(),request,out);%>
        		</select>*</td>
    	</tr>
    	<!-- <tr>
      		<td width="17%" nowrap align="right">业务代码：</td>
      		<td width="33%" nowrap>
        		<select name="agent_code"  size="1">
          		<option value="">请选择</option>
          		
        		</select>
        	</td>
		</tr>
		 <tr>
			<td width="17%" nowrap height="19" align="right">IP地址：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="ip_addr" class="wideinput" value=""  maxlength="16" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">端口号：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="port" class="wideinput" value=""  maxlength="6" size="6" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">银行帐号：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct" class="wideinput" value=""  maxlength="32" size="32" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">银行帐号1：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="bank_acct1" class="wideinput" value=""  maxlength="32" size="32" >
			</td>
		</tr>-->
		<tr>
			<td width="20%" nowrap height="19" align="right">对账文件名称：</td>
			<td colspan="3" >
				<input type="text" name="memo1" value="<%=Info.getMemo1()%>"  maxlength="40" size="40">
				<font color="red" size="3">对账文件名称由商户代码和商户号组合(支付宝除外)</font>
			*</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">对账文件格式：</td>
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
		<td width="60%" nowrap height="19" align="right" >格式说明：</td>
		<td colspan="3">
		<input type="text" name="typeDes" class="wideinput" value="<%=Info.getTypeDes()%>" size="95" readonly >
		</td>
		</tr>
		<!--  <tr>
			<td width="20%" nowrap height="19" align="right">备注3：</td>
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
          		<input type="submit" name="submitbutton" value="提交" class="inputbutton">
          		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
        	</div>
     		</td>
    	</tr>
  	</table>
  <p/>
</form>
</body>
</html>