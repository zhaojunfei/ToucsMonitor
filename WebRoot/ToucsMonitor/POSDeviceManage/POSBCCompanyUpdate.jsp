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
  	var msgs=msg+"����������!";  
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
	if( isNull("company_id","��˾��� ")) return false;
	if( isNull("company_name","�̻�����")) return false;
	if( isNull("org_id","������� ")) return false;
	if( isNull("deduct_acct","�ۿ�����˻� ")) return false;
	if( isNull("in_acct","�ڲ��˻� ")) return false;
	if( isNull("deposit_acct","���Թ��˻� ")) return false;
	if( isNull("poundage_acct","�������˻� ")) return false;	
	if( isNull("ip_addr","IP��ַ")) return false;	
	if( isNull("port","�˿ں�")) return false;
	if( isNull("clear_type","�ʽ����㷽ʽ")) return false;
	//if( isNull("clear_time","�������� ")) return false;
	
	if(!validateTxt1("deduct_acct","�ۿ�����˻�" ) )return false;	
	if(!validateTxt1("in_acct","�ڲ��˻�" ) )return false;	
	if(!validateTxt1("deposit_acct","���Թ��˻�" ) )return false;	
	if(!validateTxt1("poundage_acct","�������˻�" ) )return false;
	
	if(confirm("ȷ�����������ύ��"))
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
<font face="����" size="+2">Ԥ������˾��Ϣ�޸�</font>
	<table id="tab0" width="90%" height="271">
    	<tr>
      		<td colspan="4" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right" >��˾��ţ�</td>
      		<td width="60%" nowrap height="19">
      			<input type="text" name="company_id" class="readonly" readonly value="<%=Info.getCompany_id()%>" READONLY style="background-color:#cccccc"  maxlength="20"  size="30" >
      		*</td>
   
      		<td width="20%" nowrap height="19" align="right">��˾���ƣ�</td>
     		<td width="60%" nowrap height="19" >
       			<input type="text" name="company_name" class="wideinput" value="<%=Info.getCompany_name()%>"  maxlength="50" size="30" >
     		*</td>
    	</tr>
		<tr>
     		
		</tr>
    	<tr>
      		<td width="17%" nowrap align="right">���������</td>
      		<td width="33%" nowrap>
          		<select name="org_id" size="1" >
            	<option value="">----��ѡ��----</option>
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
			<td width="17%" nowrap height="19" align="right">�ۿ�Թ��˻���</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="deduct_acct" class="wideinput" value="<%=Info.getDeduct_acct()%>"  maxlength="30" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">�ڲ��˻���</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="in_acct" class="wideinput" value="<%=Info.getIn_acct()%>"  maxlength="30" size="30" >*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">���Թ��˻���</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="deposit_acct" class="wideinput" value="<%=Info.getDeposit_acct()%>"  maxlength="30" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">�������˻���</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="poundage_acct" class="wideinput" value="<%=Info.getPoundage_acct()%>"  maxlength="30" size="30" >*
			</td>
		</tr>
		<tr>
			<td width="17%" nowrap height="19" align="right">�����ţ�</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="swop_no" class="wideinput" value="<%=Info.getSwop_no()%>"  maxlength="9" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">���ʣ�</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate" class="wideinput" value="<%=Info.getRebate()%>"  maxlength="5" size="5" >
			</td>
		</tr>	
		<tr>
			<td width="17%" nowrap height="19" align="right">����1��</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate1" class="wideinput" value="<%=Info.getRebate1()%>"  maxlength="9" size="16" >
			</td>
			<td width="17%" nowrap height="19" align="right">����2��</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="rebate2" class="wideinput" value="<%=Info.getRebate2()%>"  maxlength="5" size="5" >
			</td>
		</tr>		
		<tr>
			<td width="17%" nowrap height="19" align="right">�ʽ����㷽ʽ��</td>
			<td width="33%" nowrap height="19" >
				<select name="clear_type" size="1">
				  <% String type1="";
				  if(Info.getClear_type().trim().equals("0")){
					  type1="selected";
	            	}
				  %> 
          			<option value="0">ʵʱ�ۿ�</option>
        		</select>*
			</td>
			<td width="17%" nowrap height="19" align="right">�������ڣ�</td>
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
			<td width="17%" nowrap height="19" align="right">IP��ַ��</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="ip_addr" class="wideinput" value="<%=Info.getIp_addr()%>"  maxlength="16" size="30" >*
			</td>
			<td width="17%" nowrap height="19" align="right">�˿ںţ�</td>
			<td width="33%" nowrap height="19" >
				<input type="text" name="port" class="wideinput" value="<%=Info.getPort()%>"  maxlength="8" size="8" >*
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
	      	<td colspan="4" nowrap><hr noshade></td>
		</tr>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        	<div align="right">
          		<input type="submit" name="submitbutton" value="�ύ" class="inputbutton" onClick="return check()">
          		<input type="reset"  name="Submit2" value="����"  class="inputbutton" >
          		<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>
