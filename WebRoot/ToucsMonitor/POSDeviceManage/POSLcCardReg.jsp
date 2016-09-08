<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //取得消息
	request.removeAttribute("Message");   //删除消息
	if(obj1!=null)msg=(String)obj1;

  	String REGCARD="14701";
  	String CANCEL="CANCEL";
 	String uid=request.getParameter("uid");		
%>

<html>
<head>
<title>Untitled Document</title>
<Script>
function showPopUpOnLoad(){
    var Msg = '<%= msg %>';
    if (Msg != "") {
        alert(Msg);
    }
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

function validateTxt(textName,msg){ 
	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	var msgs1=msg+"不能为空！";
  	var msgs=msg+"必须是数字!";
  
  	if(newLength==0){
  		alert(msgs1);   
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
 	var bankCode= document.all("bankCode").value;
 	var cardType= document.all("cardType").value;
 	var cardClass= document.all("cardClass").value;
      
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

	if(bankCode.length==0){
		alert("请选择银行名称 ！");
		document.all("bankCode").focus(this);
		return false;
	}	

	if(cardType.length==0){
		alert("请选择卡类型！ ");
		document.all("cardType").focus(this);
		return false;
	}

	if(cardClass.length==0){
		alert("请选择卡种类！ ");
		document.all("cardClass").focus(this);
		return false;
	}
		
	if(TextIsNull("cardMatch",15,"卡适配标识"))return false;
	
    if(!confirm("确认要新增卡表信息吗?按下确定按钮后将新增卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/posLcCard?reqCode=<%=REGCARD%>&&uid=<%=uid%>";
 	document.form1.submit();			
}
	
function cancel11(){
	document.form1.action="/ToucsMonitor/posLcCard?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.form1.submit();
}
 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000" onLoad="showPopUpOnLoad()">
<form name="form1" method="post" action="/ToucsMonitor/posLcCard">
<h2><font face="隶书">理财pos卡卡表管理</font></h2>  
	<table width="631" border="0">
    	<tr> 
      		<td  colspan="2" nowrap> <hr noshade></td>
    	</tr>
    	<tr> 
	    	<td  width="40%" align="right">磁道号</td>
	    	<td width="60%"> 
	       		<input type="text" name="track_no" size="20" maxlength="1" >
	    	</td>
	 	</tr>	
  		<tr>
  		 	<td  width="40%" align="right">卡bin</td>
	   		<td width="60%"> 
	      		<input type="text" name="cardMatch" size="20" maxlength="15">
	     	</td> 
		</tr>
		<tr> 
      		<td width="40%" align="right">卡号长度</td>
     		<td width="60%"> 
      			<input type="text" name="cardLen"  size="20">
    		</td>
    	</tr>  
	 	<tr>   
       		<td  width="40%" align="right">银行名称</td>
	    	<td width="60%" > 
	        	<select name="bankCode">
	          	<option value="" selected>--请选择--</option>
	          	<%			
	          		List  bankCodeList=(List)request.getAttribute("bankCode") ;
			   		if(bankCodeList!=null){
			   		String  bankcode="";
					String  bankname="";
					for(int i=0;i<bankCodeList.size();i++){
						BankCodeBean  CodeBean0=null;					
						try{
							  CodeBean0=(BankCodeBean)bankCodeList.get(i);
						}catch(Exception exp){
							System.out.println("error:"+i);
						}	  									
						if(CodeBean0==null) continue;
							bankcode=CodeBean0.getBankCode();
							bankname=CodeBean0.getBankName();
						if(bankcode!=null)bankcode=bankcode.trim();
						if(bankname!=null)bankname=bankname.trim();	
				%>
	          	<option value="<%=bankcode%>" > <%=bankname%></option>
	          	<%
						}
		    		}
				%>
	        	</select>
	     	</td>
  		</tr>
		<tr>     
	     	<td  width="40%" align="right">卡类型</td>
	     	<td width="60%" > 
	        	<select name="cardType"    >
	         	<option value="">--请选择--</option>
	         	<option value="1">本行卡</option>
	         	<option value="7">他行卡</option>
	        	</select>
	     	</td>
  		</tr>
  		<tr>
  		 	<td  width="40%" align="right">卡种类</td>
	     	<td width="60%" > 
	        	<select name="cardClass">
	          	<option value="" selected>请选择</option>
	          	<%			
		           List  cardClassList=(List)request.getAttribute("cardClass") ;
				   if(bankCodeList!=null){
						String cardclass=" ";
						String cardclassDesc=" ";						   		
						for(int i=0;i<cardClassList.size();i++){		 
							CodeBean  codeBean1=null;							
							try{
								codeBean1=(CodeBean)cardClassList.get(i);
							}catch(Exception exp1){
								System.out.println("cardClassList cast error:"+i);
							}
							if(codeBean1==null)continue;
							cardclass=codeBean1.getCodeId();
							cardclassDesc=codeBean1.getCodeDesc();						
							if(cardclass!=null)cardclass=cardclass.trim();
							if(cardclassDesc!=null) cardclassDesc=cardclassDesc.trim();
				%>
	          	<option value="<%=cardclass%>" > <%=cardclassDesc%></option>
	          	<%
							}
		    			}
				%>
	        	</select>
	      	</td>
	 	</tr>
	 	<tr>    
	      	<td  width="40%" align="right">卡表状态</td>
	      	<td width="60%" > 
	        	<input type="radio" name="rec_useflag" value="0" class="radio" checked > 启用 
	        	<input type="radio" name="rec_useflag" class="radio" value="1"  > 不启用 
	     	</td> 
    	</tr>
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
          		<input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">
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