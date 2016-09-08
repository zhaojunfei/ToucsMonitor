<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //取得消息
	request.removeAttribute("Message");   //删除消息
	if(obj1!=null)msg=(String)obj1;

  	String REGCARD="16801";
  	String CANCEL="CANCEL";
 	String uid=request.getParameter("uid");		
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   <title>Untitled Document</title>
   <script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js" ></script>
<Script>

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

function validateTxt(textName,msg){ 
  	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	var cardmatch_len=document.all("cardmatch_len").value;
  	var msgs1=msg+"不能为空！";
  	var msgs=msg+"必须是数字!";
  	var msgs2=msg+"的数据位数须等于"+cardmatch_len+"位！!";
  	
  	if(newLength==0){
  		alert(msgs1);   
		document.all(textName).focus(this); 
		return false;
  	}
  	if(newLength!=cardmatch_len){
  		alert(msgs2);   
		document.all(textName).focus(this); 
		return false;
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

function TextIsNull(textName,maxDigit,msg){
 	var newValue = document.all(textName).value ;
	var newLength = newValue.length;
	//alert(newLength);
	var isNull=true;
	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
	    if(aChar !=" ") {
			isNull=false;
			break;
    	}
  	}
    var msg1=msg+"不能为空！";
    var msg2=msg+"的数据位数小于等于"+maxDigit+"位！"
	if(isNull){
    	alert(msg1);    	
      	document.all(textName).focus(this);
	  	return true;	
	}
	
	if(newLength==0  ){
      	alert(msg1);    	
      	document.all(textName).focus(this);
	  	return true;
 	}
   	if(newLength>maxDigit){
		alert(msg2);
    	document.all(textName).focus(this);
	    return true;
 	} 
 	return false;
}

function isInt(newValue) {
 	// var newValue = document.all(textName).value ;
 	var newLength = newValue.length;
 	if(newLength==0){
    	alert("请输入正确格式数据！");    	
      	textObj.focus(this);
	  	return false;
 	}
  
  	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert("请输入正确格式数据！");    	
      		textObj.focus(this);
	  		return false;
    	}
  	}
  	return true;
}

function isNumber(aChar) {
	if(aChar < "0" || aChar > "9") {
    	return false;
  	}
  	return true;
}

function check(){    
	var track_no=document.all("track_no").value ;  
	var company_id= document.all("company_id").value;
	var cardmatch_len= document.all("cardmatch_len").value;
   	if(track_no.length==0){
   		alert("请输入磁道号！");    
		document.all("track_no").focus(this);
		return false;
	}   
	//if(!isInt(track_no)) return false;
	if(track_no!=2 && track_no!=3){
		alert("磁道号只能为2或3！");
		document.all("track_no").focus(this);
		return false;
	}

	if(company_id.length==0){
		alert("请选择预付卡公司 ！");
		document.all("company_id").focus(this);
		return false;
	}	

	if(cardmatch_len.length==0){
		alert("请选择卡BIN长度 ！");
		document.all("cardmatch_len").focus(this);
		return false;
	}			
	
	//if(TextIsNull("card_match",15,"卡BIN"))return false;
	if(TextIsNull("card_len",5,"卡长度" ) )return false;	
	if(!validateTxt1("card_len","卡长度" ) )return false;	
	if(!validateTxt("card_match","卡BIN" ) )return false;		
	//if(!validateTxt1("cardmatch_len","卡BIN长度" ) )return false;
	//if(!validateTxt1("memo1","备注1" ) )return false;
	//if(!validateTxt1("memo2","备注2" ) )return false;
	
    if(!confirm("确认要新增卡表信息吗?按下确定按钮后将新增卡表信息！")) return false;
	document.formReg.action="/ToucsMonitor/posBCCard?reqCode=<%=REGCARD%>&&uid=<%=uid%>";
 	document.formReg.submit();			
}
	
function cancel11(){
	document.formReg.action="/ToucsMonitor/posBCCard?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.formReg.submit();				
}
 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
 </head>
<body bgcolor="#CCCCCC" text="#000000"> 

<form name="formReg" method="post" action="/ToucsMonitor/posBCCard">
<h2><font face="隶书">预付卡卡表管理</font></h2>  
	<table width="631" border="0">
    	<tr> 
      		<td  colspan="2" nowrap> <hr noshade></td>
    	</tr>
    <!--  	<tr> 
	    	<td  width="40%" align="right">卡序号</td>
	    	<td width="60%"> 
	       		<input type="text" name="card_id" size="20" >
	    	</td>
	 	</tr>-->
	 	<tr>     
  		 	<td  width="40%" align="right">公司编号</td>
	   		<td width="60%"> 
	      	<select name="company_id">
          		<option value="" selected>--请选择-- </option>
          		<%			
          		List  companyIdList=(List)request.getAttribute("company_id") ;
		   		if(companyIdList!=null){
		   		String  companyId="";
				String  company_name="";
				for(int i=0;i<companyIdList.size();i++){
					POSBCCompanyInfo  info=null;					
					try{
						info=(POSBCCompanyInfo)companyIdList.get(i);
					}catch(Exception exp){
						System.out.println("error:"+i);
					}	  									
					if(info==null) continue;
					companyId=info.getCompany_id();
					company_name=info.getCompany_name();
					if(companyId!=null)companyId=companyId.trim();
					if(company_name!=null)company_name=company_name.trim();	
          	
				%>
          		<option value="<%=companyId%>" > <%=company_name%></option>
               	<%
						}
		    		}
				%>
        		</select>
        		*</td> 
  		</tr>
    	<tr> 
	    	<td  width="40%" align="right">磁道号</td>
	    	<td width="60%"> 
	       		<input type="text" name="track_no" size="20" maxlength="1" >
	    	*</td>
	 	</tr>	
	 		<tr> 
      		<td width="40%" align="right">卡BIN长度</td>
     		<td width="60%"> 
      			<select name="cardmatch_len"    >
      			<option value="">--请选择--</option>
	         	<option value="6">6</option>
	         	<option value="7">7</option>
	         	<option value="8">8</option>
	         	<option value="9">9</option>
	         	<option value="10">10</option>
	        	</select>
    		*</td>
    	</tr> 
  		<tr>
  		 	<td  width="40%" align="right">卡适配标识(卡BIN)</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_match" size="20" maxlength="10"><font color="red" size="3">(卡bin长度6~10位)</font>
	     	*</td> 
		</tr>
	 
	 	<tr>   
       		<td  width="40%" align="right">卡长度</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_len" size="20">
	     	*</td> 
  		</tr>
  		<tr>   
       		<td  width="40%" align="right">备注1</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo1" size="20" maxlength="50">
	     	</td> 
  		</tr>
  		<tr>   
       		<td  width="40%" align="right">备注2</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo2" size="20" maxlength="50">
	     	</td> 
  		</tr>
		
	 	<!-- <tr>    
	      	<td  width="40%" align="right">卡表状态</td>
	      	<td width="60%" > 
	        	<input type="radio" name="rec_useflag" value="0" class="radio" checked > 启用 
	        	<input type="radio" name="rec_useflag" class="radio" value="1"  > 不启用 
	     	</td> 
    	</tr> -->
	</table>		
  	<table width="81%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        		<div align="right"> 
          		<input type="submit" name="regSubmit" value="确定" class="inputbutton"  onClick="check(); return false;">
          		<input type="reset"  name="Submit2" value="重填"  class="inputbutton" >
           		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
				</div>
      		</td>
    	</tr>
  	</table>
  	<p>
    <input type="hidden" name="reqCode" value=<%=REGCARD%>>
</p>
<p>&nbsp; </p>
</form>
</body>
</html>