<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%
  String MODCARD="14702";
  String CANCEL="CANCEL";
  String uid=request.getParameter("uid");	
%>
<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script>
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
	if(TextIsNull("cardMatch",15,"发bin"))		return false;
	if(!confirm("确认要修改卡表信息吗?按下确定按钮后将修改卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/posLcCard?reqCode=<%=MODCARD%>&&uid=<%=uid%>";	
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

<body  bgcolor="#CCCCCC" text="#000000">
	<%   		
		Object obj=request.getAttribute("CardTypeStruct");   
		POSLcCardInfo cardInfo=null;
		if(obj==null)
			cardInfo=new POSLcCardInfo();
		else
			cardInfo=(POSLcCardInfo)obj;
			
	   	String track_no=cardInfo.getTrack_no();
   		String matchStart=cardInfo.getMatchStart();
   		String cardMatch=cardInfo.getcardMatch();
		String cardStart=cardInfo.getcardStart();
   		String cardLen=cardInfo.getcardLen();
   		String bankStart=cardInfo.getbankStart();
   		String bankLen=cardInfo.getbankLen();
   		String bankMatch=cardInfo.getbankMatch();
   		String strBankCode=cardInfo.getbankCode();
   		String strCardClass=cardInfo.getcardClass();
   		String strCardType=cardInfo.getcardType();
   		String pinoffset=cardInfo.getpinoffset();
   		String cardArea=cardInfo.getcardArea();
   		String rec_datetime=cardInfo.getrec_datetime();
   		String rec_useflag=cardInfo.getrec_useflag();   
   	%>
<form name="form1" method="post" action="/ToucsMonitor/posLcCard"  >
  	<h2><font face="隶书">理财pos卡卡表管理</font></h2>
  	<table width="671" bordercolor="#CCCCCC">
   		<tr> 
      		<td colspan="5" nowrap> <hr noshade></td>
    	</tr>  
    	<tr> 
      		<td width="40%" align="right">磁道号</td>
      		<td width="60%"> 
        		<input type="text" name="track_no" value="<%=track_no%>" size="20" maxlength="1" >
      		</td> 
    	</tr>
    	<tr>
  			<td width="40%" align="right">卡bin </td>
     		<td width="60%"> 
        		<input type="text" name="cardMatch"  value="<%=cardMatch%>" size="20" maxlength="15">
      		</td>
    	</tr>
    	<tr>
    		<td width="40%" align="right">卡号长度</td>
      		<td width="60%"> 
	        <%        
		  		if(cardLen==null || cardLen.equals("0") ){
		  	%>
	        	<input type="text" name="cardLen"   size="20">
	        <%
		  		}else{
		  	%>
	        	<input type="text" name="cardLen"  value="<%=cardLen%>" size="20">
	        <%
			  	}
		  	%>
      		</td>
    	</tr>
    	<tr>   
      		<td width="40%" align="right">银行名称</td>
	  		<td width="60%"> 
	        	<select name="bankCode">
	          	<option value="" selected>请选择</option>
	          	<%			
		           	List  bankCodeList=(List)request.getAttribute("bankCode");
				   	if(bankCodeList!=null){
				   		String  bankcode="";
						String  bankname="";
						for(int i=0;i<bankCodeList.size();i++){
							String sel=" ";				
							BankCodeBean CodeBean0=null;					
							  try{
								  CodeBean0=(BankCodeBean)bankCodeList.get(i);
							}catch(Exception exp){
									System.out.println("error:"+i);
							}	  				
							
							if(CodeBean0==null) continue;
							bankcode=CodeBean0.getBankCode();
							bankname=CodeBean0.getBankName();
							
							if(bankcode!=null)bankcode=bankcode.trim();					
							if(strBankCode!=null)strBankCode=strBankCode.trim();
							else  strBankCode=" ";
							
							if(strBankCode.equals(bankcode)) sel="selected";																 				  
				%>
	          	<option value="<%=bankcode%>"  <%=sel%> > <%=bankname%></option>
	         	<%		}
			   		}
				%>
	        	</select>
	   		</td>
  		</tr>
    	<tr>	  	
      		<td width="40%" align="right">卡类型</td>
	    	<td width="60%"> 
	        <p> <select name="cardType" class="readonly"  >
	            <option value=" "></option>
	            <%		
	            	String sel1="";
	            	String sel2="";
	            	if(strCardType.trim().equals("1")){
	            		sel1="selected";
	            	}else if(strCardType.trim().equals("7") ){
	            		sel2="selected";
	            	}
				%>
	            <option value="1" <%=sel1 %>> 本行卡</option>
	            <option value="7" <%=sel2 %>> 他行卡</option>
	          	</select>
	        </p>
	    	</td>
  		</tr>
  		<tr>
  			<td width="40%" align="right"> 
        		<p>卡种类 </p>
        	</td>
	      	<td width="60%"> 
	        	<select name="cardClass">
	          	<option value="" selected>请选择</option>
	          	<%			
		           List  cardClassList=(List)request.getAttribute("cardClass") ;
				   if(bankCodeList!=null){
						String cardclass=" ";
						String cardclassDesc=" ";						   		
						for(int i=0;i<cardClassList.size();i++){
							String sel="";
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
							if(strCardClass!=null)strCardClass=strCardClass.trim();
							else  strCardClass="";						
							
							if(strCardClass.equals(cardclass))sel="selected";
				%>
	          	<option value="<%=cardclass%>" <%=sel%>> <%=cardclassDesc%></option>
	          	<%		}
				 	}
				%>
	        	</select>
	      	</td>
	   </tr> 
	   <tr>  
	   		<td width="40%" align="right">卡表状态</td>
	      	<td width="60%"> 
		    <%
		    	String  cardState0=" ",cardState1=" ";
		    	if(rec_useflag==null) rec_useflag=" ";
		  		if(rec_useflag.equals("0"))  cardState0="checked";
		  		else if(rec_useflag.equals("1")) cardState1="checked";	
		  	%>
		        <input type="radio" name="rec_useflag" value="0"  class="radio" <%=cardState0%> >启用 
		        <input type="radio" name="rec_useflag" value="1"   class="radio"  <%=cardState1%> >不启用 
	       	</td>
  	 	</tr>
  	</table>	
  	<table width="88%">
    	<tr> 
      		<td colspan="5" nowrap> <hr noshade></td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        	<div align="right">
          		<input type="submit" name="regSubmit" value="确定"  class="inputbutton"  onClick="check(); return false;">
          		<input type="reset" name="Submit22" value="重填" class="inputbutton" >
          		<input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">
        	</div>
      		</td>
    	</tr>
  	</table><p>
    	<input type="hidden" name="oldTrack_no" value="<%=track_no%>">
    	<input type="hidden" name="oldMatchStart" value="<%=matchStart%>">
    	<input type="hidden" name="oldCardMatch" value="<%=cardMatch%>">
    	<input type="hidden" name="oldRec_datetime" value="<%=rec_datetime%>">
    	<input type="hidden" name="oldRec_useflag" value="<%=rec_useflag%>">
  	</p>
</form>
</body>
</html>