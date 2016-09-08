<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);	
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else 
				flag="";
			try{
				out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
					+code.getCodeDesc()+"</option>");
			}catch(IOException exp){
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}			
		}	
	}
}%>

<html>
<head>
	<title>新增终端资料</title>
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
	if (isNull("pos_no", "POS编号")) return false;
	if (document.formReg.pos_no.value.length != 8){
		alert("POS编号必须为8位");
		return false;
	}
	if (isNull("mer_id", "商户编号")) return false;
	if (document.formReg.open_flag3.checked){
	}
	if (document.formReg.open_flag4.checked){
	}
	if (document.formReg.open_flag5.checked){
	}

	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">	
<%
  	String uid=request.getParameter("uid");			
%>
<h2><font face="隶书">外卡POS终端资料新增</font></h2>
<form name="formReg" method="post" action="/ToucsMonitor/fcardposconfig?reqCode=13501&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
  	<table id="tab0" width="76%">
    	<tr>
      		<td colspan="6" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="19%" nowrap align="right" >终端编号：</td>
      		<td width="23%" nowrap>
      			<input type="text" name="pos_no" class="wideinput" value="" maxlength="8" size="20">* 
      		</td>
      		<td width="26%" nowrap align="right">建行商户编号：</td>
      		<td width="32%" nowrap>
      			<input type="text" name="mer_id" class="wideinput" value="" maxlength="15" size="20">* 
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">默认划款账号：</td>
      		<td nowrap>
      			<input type="text" name="acct_no" class="wideinput" value="" maxlength="25" size="20">
      		</td>
      		<td nowrap align="right">默认划款账号开户行：</td>
      		<td nowrap>
      			<input type="text" name="acct_bankno" class="wideinput" value="" maxlength="10" size="20">
      		</td>
    	</tr>
    	<tr>
      		<td nowrap align="right">默认划款账户名称：</td>
      		<td nowrap>
      			<input type="text" name="acct_name" class="wideinput" value="" maxlength="60" size="20">
      		</td>
      		<td nowrap align="right">划款规则名：</td>
      		<td nowrap >
      			<input type="text" name="acct_func" class="wideinput" value="" maxlength="10" size="20">
      		</td>
    	</tr>
    	<tr>
     		<td nowrap align="right">终端状态：</td>
      		<td nowrap>
      			<select name="state" size="1">
				<option value="0" selected>正常状态</option>
				<option value="1" >测试POS</option>
				<option value="2" >重点监控POS</option>
				<option value="7" >风险控制停用POS</option>
				<option value="9" >人工手动停用POS</option>
       			</select>*
	  		</td>
      		<td nowrap align="right">终端读卡能力：</td>
      		<td nowrap>
      			<select name="chip_capability" size="1">
				<option value="0" selected>不具备</option>
				<option value="1" >具备</option>
       			</select>*
	  		</td>
    	</tr>
  	</table>
	<table width="75%" border="1">
		<tr>
	  		<td nowrap colspan="5" align="left">外卡开通标志：</td>
		</tr>
		<tr>
      		<td>
      			<input type="hidden" name="open_flag" value="00000">
      		</td>
      		<td width="13%" nowrap>
      			<input type="checkbox" name="open_flag1" value="checkbox" class="radio" onClick="javascript:onCondition()">Visa 
      		</td>
	  		<td width="22%" align="right" nowrap>&nbsp;</td>
	  		<td nowrap>&nbsp;</td>
	  		<td width="18%" align="right" nowrap>返回率Visa：</td>
      		<td width="21%" nowrap>
      			<input type="text" name="rate_visa2" class="wideinput"  value="" maxlength="20" size="15"  >
      		</td>
    	</tr>
    	<tr>
	  		<td nowrap>
	  			<input type="checkbox" name="open_flag2" value="checkbox" class="radio" onClick="javascript:onCondition()">Master 
	  		</td>
	  		<td nowrap>&nbsp;</td>
	  		<td nowrap>&nbsp;</td>
      		<td align="right" nowrap>返回率Master：</td>
      		<td nowrap>
      			<input type="text" name="rate_master2" class="wideinput"  value="" maxlength="20" size="15"  >
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag3" value="checkbox" class="radio" onClick="javascript:onCondition()">AE 
      		</td>
      		<td nowrap align="right">默认AE商户号：</td>
      		<td nowrap>
      			<input type="text" name="mer_id_ae" class="wideinput"  value="" maxlength="50" size="20"  >
      		</td>
      		<td align="right" nowrap>返回率ae：</td>
      		<td nowrap>
      			<input type="text" name="rate_ae2" class="wideinput"  value="" maxlength="20" size="15"  >
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag4" value="checkbox" class="radio" onClick="javascript:onCondition()">Dinner
      		</td>
      		<td nowrap align="right">默认Dinner商户号：</td>
      		<td nowrap>
      			<input type="text" name="mer_id_dinner" class="wideinput"  value="" maxlength="50" size="20"  >
      		</td>
     	 	<td align="right" nowrap>返回率Dinner：</td>
      		<td nowrap>
      			<input type="text" name="rate_dinner2" class="wideinput"  value="" maxlength="20" size="15"  >
      		</td>
    	</tr>
    	<tr>
      		<td nowrap>
      			<input type="checkbox" name="open_flag5" value="checkbox" class="radio" onClick="javascript:onCondition()">JCB
      		</td>
      		<td nowrap align="right">默认JCB商户号：</td>
      		<td width="26%" nowrap>
      			<input type="text" name="mer_id_jcb" class="wideinput"  value="" maxlength="50" size="20"  >
      		</td>
      		<td align="right" nowrap>返回率Jcb：</td>
      		<td nowrap>
      			<input type="text" name="rate_jcb2" class="wideinput"  value="" maxlength="20" size="15"  >
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
          		<input type="submit" name="submit" value="提交" class="inputbutton">
          		<input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/fcardposconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>