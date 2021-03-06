<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%
   POSBCCompanyInfo Info=(POSBCCompanyInfo)request.getAttribute("POSBCCompany");
   if (Info == null){
	Info = new POSBCCompanyInfo();
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js" ></script>
<script>
function onBranchNameChange(){
	document.forms[0].branch_id.value= document.forms[0].branch_name.value;
}
function validateTxt1(textName,msg){ 
  	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	var msgs=msg+"必须是数字!";  
  	if(newLength==0){
		return true;
  	}
 
 	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert(msgs);    	
      		document.all(textName).focus(this); 
	  		return false;
    	}
  	}
  	return true;
}

function doSubmit(){
	if( isNull("company_id","公司编号 ")) return false;
	if( isNull("company_name","商户名称")) return false;
	if( isNull("org_id","清算机构 ")) return false;
	if( isNull("deduct_acct","扣款对用账户 ")) return false;
	if( isNull("in_acct","内部账户 ")) return false;
	if( isNull("deposit_acct","存款对公账户 ")) return false;
	if( isNull("poundage_acct","手续费账户 ")) return false;	
	if( isNull("ip_addr","IP地址")) return false;	
	if( isNull("port","端口号")) return false;
	if( isNull("clear_type","资金清算方式")) return false;
	//if( isNull("clear_time","清算日期 ")) return false;
	
	if(!validateTxt1("deduct_acct","扣款对用账户" ) )return false;	
	if(!validateTxt1("in_acct","内部账户" ) )return false;	
	if(!validateTxt1("deposit_acct","存款对公账户" ) )return false;	
	if(!validateTxt1("poundage_acct","手续费账户" ) )return false;
	
	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}
</script>
  <head>
    <title>My JSP 'POSBCCompanyUpdate.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">	


  </head>
  
  <body bgcolor="#CCCCCC" text="#000000">
  <%
	String uid=request.getParameter("uid");
%>
    <form name="formReg" method="post" action="/ToucsMonitor/posBCCompany?reqCode=16903&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="隶书" size="+2">预付卡公司信息修改</font>
	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >公司编号：</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="company_id" class="readonly" readonly value="<%=Info.getCompany_id()%>" READONLY style="background-color:#cccccc"  maxlength="20"  size="30" >
      		*</td>
   
      		<td width="20%" nowrap height="19" align="right">公司名称：</td>
     		<td width="60%" nowrap height="19" >
       			<input type="text" name="company_name" class="wideinput" value="<%=Info.getCompany_name()%>"  maxlength="50" size="30" >
     		*</td>
    	</tr>
		<tr>
     		
		</tr>
    	<tr>
      		<td width="17%" nowrap align="right">清算机构：</td>
      		<td width="33%" nowrap>
          		<select name="org_id" size="1" >
            	<option value="">----请选择----</option>
            	<%
            		List list=(List)request.getAttribute("BranchId");
            		String flag="";
        			for(int i=0;i<list.size();i++){
    					CodeBean code=(CodeBean)list.get(i);
    					if(Info.getOrg_id().equals(code.getCodeId())){
    						flag="SELECTED";
    					}else{
    						flag="";
    					}
    			%>
    			<option value="<%=code.getCodeId() %>" <%=flag %> > <%=code.getCodeDesc() %></option>   		
    				<%}%>       
          		</select>* </td> 
    	</tr>
    	<tr>
			<td width="17%" nowrap height="19" align="right">扣款对公账户：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="deduct_acct" class="wideinput" value="<%=Info.getDeduct_acct()%>"  maxlength="30" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">内部账户：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="in_acct" class="wideinput" value="<%=Info.getIn_acct()%>"  maxlength="30" size="30" >*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">存款对公账户：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="deposit_acct" class="wideinput" value="<%=Info.getDeposit_acct()%>"  maxlength="30" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">手续费账户：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="poundage_acct" class="wideinput" value="<%=Info.getPoundage_acct()%>"  maxlength="30" size="30" >*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">交换号：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="swop_no" class="wideinput" value="<%=Info.getSwop_no()%>"  maxlength="9" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">费率：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate" class="wideinput" value="<%=Info.getRebate()%>"  maxlength="5" size="5" >
			</td>
		</tr>	
		<tr>
			<td width="17%" nowrap height="19" align="right">费率1：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate1" class="wideinput" value="<%=Info.getRebate1()%>"  maxlength="9" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">费率2：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate2" class="wideinput" value="<%=Info.getRebate2()%>"  maxlength="5" size="5" >
			</td>
		</tr>		
		<tr>
			<td width="17%" nowrap height="19" align="right">资金清算方式：</td>
			<td width="33%" nowrap height="19" >
				<select name="clear_type" size="1">
				  <% String type1="";
				  if(Info.getClear_type().trim().equals("0")){
					  type1="selected";
	            	}
				  %> 
          			<option value="0">实时扣款</option>
        		</select>*
			</td>
			<td width="17%" nowrap height="19" align="right">清算日期：</td>
			<td width="33%" nowrap height="19" >
			<p>	T+<select name="clear_date" size="1" class="readonly">
				<option value="" ></option>
				 <%		
	            	String sel1="";
	            	String sel2="";
	            	String sel3="";
	            	String sel4="";
	            	String sel5="";
	            	String sel6="";
	            	String sel7="";
	            	String sel8="";
	            	String sel9="";
	            	String sel10="";
	            	String sel11="";
	            	String sel12="";
	            	String sel13="";
	            	String sel14="";
	            	String sel15="";
	            	if(Info.getClear_date().trim().equals("1")){
	            		sel1="selected";
	            	}else if(Info.getClear_date().trim().equals("2")){
	            		sel2="selected";
	            	}else if(Info.getClear_date().trim().equals("3")){
	            		sel3="selected";
	            	}else if(Info.getClear_date().trim().equals("4")){
	            		sel4="selected";
	            	}else if(Info.getClear_date().trim().equals("5")){
	            		sel5="selected";
	            	}else if(Info.getClear_date().trim().equals("6")){
	            		sel6="selected";
	            	}else if(Info.getClear_date().trim().equals("7")){
	            		sel7="selected";
	            	}else if(Info.getClear_date().trim().equals("8")){
	            		sel8="selected";
	            	}else if(Info.getClear_date().trim().equals("9")){
	            		sel9="selected";
	            	}else if(Info.getClear_date().trim().equals("10")){
	            		sel10="selected";
	            	}else if(Info.getClear_date().trim().equals("11")){
	            		sel11="selected";
	            	}else if(Info.getClear_date().trim().equals("12")){
	            		sel12="selected";
	            	}else if(Info.getClear_date().trim().equals("13")){
	            		sel13="selected";
	            	}else if(Info.getClear_date().trim().equals("14")){
	            		sel14="selected";
	            	}else if(Info.getClear_date().trim().equals("15")){
	            		sel15="selected";
	            	}
				%>
				<option value="1" <%=sel1%>>1</option>
				<option value="2" <%=sel2%>>2</option>
				<option value="3" <%=sel3%>>3</option>
				<option value="4" <%=sel4%>>4</option>
				<option value="5" <%=sel5%>>5</option>
				<option value="6" <%=sel6%>>6</option>
				<option value="7" <%=sel7%>>7</option>
				<option value="8" <%=sel8%>>8</option>
				<option value="9" <%=sel9%>>9</option>
				<option value="10" <%=sel10%>>10</option>
				<option value="11" <%=sel11%>>11</option>
				<option value="12" <%=sel12%>>12</option>
				<option value="13" <%=sel13%>>13</option>
				<option value="14" <%=sel14%>>14</option>
				<option value="15" <%=sel15%>>15</option>			
				</select>
				</p>
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">IP地址：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="ip_addr" class="wideinput" value="<%=Info.getIp_addr()%>"  maxlength="16" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">端口号：</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="port" class="wideinput" value="<%=Info.getPort()%>"  maxlength="8" size="8" >*
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注1：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo1" class="wideinput" value="<%=Info.getMemo1()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注2：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo2" class="wideinput" value="<%=Info.getMemo2()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
			<td width="20%" nowrap height="19" align="right">备注3：</td>
			<td width="60%" nowrap height="19" >
				<input type="text" name="memo3" class="wideinput" value="<%=Info.getMemo3()%>"  maxlength="40" size="40" >
			</td>
		</tr>
		<tr>
	      	<td colspan="4" nowrap><hr noshade></td>
		</tr>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        	<div align="right">
          		<input type="submit" name="submitbutton" value="提交" class="inputbutton" onClick="return check()">
          		<input type="reset"  name="Submit2" value="重填"  class="inputbutton" >
          		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>
