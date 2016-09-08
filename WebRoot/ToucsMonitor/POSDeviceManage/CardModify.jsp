<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%
	String MODCARD="13002";
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
	
	if(!validateTxt("matchStart","适配偏移" )) return false;

	if(TextIsNull("cardMatch",15,"发卡行适配标识"))		return false;
	
	if(!validateTxt1("cardStart","卡号起始位" ) )return false;	
	if(!validateTxt1("cardLen" ,"卡号长度") )return false;
	if(!validateTxt1("bankStart","发卡行标示偏移位" )) return false;
	if(!validateTxt1("bankLen","发卡行标识长度" ) )return false;
	if(!validateTxt1("pinoffset","密码偏移位" ) )return false;	
	if(!confirm("确认要修改卡表信息吗?按下确定按钮后将修改卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/posicservlet?reqCode=<%=MODCARD%>&&uid=<%=uid%>";	
	document.form1.submit();			
}
 
function cancel11(){
	document.form1.action="/ToucsMonitor/posicservlet?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
 	document.form1.submit();			
}
</script>

<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
<%   		
	Object obj=request.getAttribute("CardTypeStruct");   
	CardTypeStruct cardTypeStruct=null;
	if(obj==null)
		cardTypeStruct=new CardTypeStruct();
	else
		cardTypeStruct=(CardTypeStruct)obj;
			
	String track_no=cardTypeStruct.getTrack_no();
   	String matchStart=cardTypeStruct.getMatchStart();
   
   	String cardMatch=cardTypeStruct.getcardMatch();
   	String cardStart=cardTypeStruct.getcardStart();
   	String cardLen=cardTypeStruct.getcardLen();
   	String bankStart=cardTypeStruct.getbankStart();
   	String bankLen=cardTypeStruct.getbankLen();
   	String bankMatch=cardTypeStruct.getbankMatch();
   	String strBankCode=cardTypeStruct.getbankCode();
   	String strCardClass=cardTypeStruct.getcardClass();
   	String strCardType=cardTypeStruct.getcardType();
   	String pinoffset=cardTypeStruct.getpinoffset();
   	String cardArea=cardTypeStruct.getcardArea();
   	String rec_datetime=cardTypeStruct.getrec_datetime();
   	String rec_useflag=cardTypeStruct.getrec_useflag();   
%>
   
<form name="form1" method="post" action="/ToucsMonitor/posicservlet"  >
<h2><font face="隶书">商业IC卡卡表管理</font></h2>
  	<table width="671" bordercolor="#CCCCCC">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>  
    	<tr> 
      		<td width="127">磁道号</td>
      		<td width="160"> 
        		<input type="text" name="track_no" value="<%=track_no%>" size="20" maxlength="1" >
      		</td>
      		<td width="155">&nbsp;&nbsp;适配偏移</td>
      		<td width="203"> 
        		<input type="text" name="matchStart" value="<%=matchStart%>" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">卡适配标识 </td>
      		<td width="160"> 
        		<input type="text" name="cardMatch"  value="<%=cardMatch%>" size="20" maxlength="15">
      		</td>
      		<td width="155">&nbsp;&nbsp;卡号起始位</td>
      		<td width="203"> 
        	<%
	  			if(cardStart==null || cardStart.trim().equals("0")){
	  		%>
        		<input type="text" name="cardStart" size="20">
        	<%
	  			}else{
	  		%>
        		<input type="text" name="cardStart" value="<%=cardStart%>" size="20">
        	<%
		  		}
	  		%>
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">卡号长度</td>
      		<td width="160"> 
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
      		<td width="155">&nbsp;&nbsp;发卡行标示偏移位</td>
      		<td width="203"> 
        	<%
	  			if(bankStart==null || bankStart.equals("-1")){
	  		%>
        			<input type="text" name="bankStart"  size="20">
        	<%
	  			}else{
	  		%>
        			<input type="text" name="bankStart" value="<%=bankStart%>" size="20">
        	<%
		  		}
	  		%>
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">发卡行标识长度</td>
      		<td width="160"> 
        	<%
	  			if(bankLen==null || bankLen.equals("0")){
	  		%>
        			<input type="text" name="bankLen"   size="20">
        	<%
	  			}else{
	  		%>
        			<input type="text" name="bankLen"  value="<%=bankLen%>" size="20">
        	<%
		  		}
	  		%>
      		</td>
      		<td width="155">&nbsp;&nbsp;发卡行适配标识</td>
      		<td width="203"> 
        		<input type="text" name="bankMatch"  value="<%=bankMatch%>" size="20" maxlength="8">
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">公司代码</td>
      		<td width="160"> 
        		<select name="bankCode">
          		<option value="" selected>请选择</option>
          		<%			
	           		List  bankCodeList=(List)request.getAttribute("bankCode");
			   		if(bankCodeList!=null){
			   			String  bankcode="";
						String  bankname="";
						for(int i=0;i<bankCodeList.size();i++){
							String sel=" ";				
							POSICCompanyIdInfo CodeBean0=null;					
					  		try{
						  		CodeBean0=(POSICCompanyIdInfo)bankCodeList.get(i);
							}catch(Exception exp){
								System.out.println("error:"+i);
							}	  				
					
							if(CodeBean0==null) continue;
							bankcode=CodeBean0.getCompany_id();
							bankname=CodeBean0.getCompany_name();
									
							if(bankcode!=null)bankcode=bankcode.trim();					
							if(strBankCode!=null)strBankCode=strBankCode.trim();
							else  strBankCode=" ";
					
							if(strBankCode.equals(bankcode)) sel="selected";																 				  
				%>
          		<option value="<%=bankcode%>"  <%=sel%> > <%=bankname%></option>
          		<%
						}
	    			}
				%>
       	 		</select>
      		</td>
      		<td width="155">&nbsp;&nbsp;密码偏移位</td>
      		<td width="203"> 
        	<%
	  			if(pinoffset==null || pinoffset.equals("0")){
	  		%>
        			<input type="text" name="pinoffset"  size="20">
        	<%
	  			}else{
	  		%>
        			<input type="text" name="pinoffset" value="<%=pinoffset%>" size="20">
        	<%
		  		}
	  		%>
      		</td>
    	</tr>
    	<tr> 
      		<td width="127">卡类型</td>
      		<td width="160"> 
        	<p> 
          		<select name="cardType" class="readonly"   style="background-color:#cccccc">
            	<option value="9">预付卡</option>
            	<%			
	           		List cardTypeList=(List)request.getAttribute("cardType") ;
			   		if(cardTypeList!=null){
			   			String cardTypeID="";
			   			String cardTypeDesc="";
						for(int i=0;i<cardTypeList.size();i++){
							String sel=" ";						
							CodeBean  codeBean2=null;							
							try{
								codeBean2=(CodeBean)cardTypeList.get(i);
							}catch(Exception exp2){
								exp2.printStackTrace();
								System.out.println(exp2.getMessage());
							}
							if(codeBean2==null) continue;
							cardTypeID=codeBean2.getCodeId();
							cardTypeDesc=codeBean2.getCodeDesc();

							if(cardTypeID!=null){
								cardTypeID=cardTypeID.trim();
							}
							if(strCardType!=null){
								strCardType=strCardType.trim();
							}else{
								strCardType="";						
							}
					
							if(strCardType.equals(cardTypeID)){
								sel="selected";
							}
				%>
            	<!--<option value="<%=cardTypeID%>" <%=sel%>> <%=cardTypeDesc%></option>-->
            	<%
						}
	    			}
				%>
          		</select>
        	</p>
      		</td>
      		<td width="155">&nbsp;&nbsp;卡地区编码</td>
      		<td width="203"> 
        		<input type="text" name="cardArea" value="<%=cardArea%>" size="20" maxlength="4">
      		</td>
    	</tr>
    	<tr> 
      		<td width="127"> 
        		<p>卡种类 </p>
      		</td>
      		<td width="160"> 
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
						
							if(cardclass!=null){
								cardclass=cardclass.trim();
							}
							if(strCardClass!=null){
								strCardClass=strCardClass.trim();
							}else{
								strCardClass="";						
							}
						
							if(strCardClass.equals(cardclass)){
								sel="selected";
							}
				%>
          		<option value="<%=cardclass%>" <%=sel%>> <%=cardclassDesc%></option>
          		<%
						}
	    			}
				%>
        		</select>
      		</td>
      		<td width="155">&nbsp;&nbsp;卡表状态</td>
      		<td width="203"> 
        	<%
  				String  cardState0=" ",cardState1=" ";
  				if(rec_useflag==null){
  					rec_useflag=" ";
  				}
  				if(rec_useflag.equals("0")){
  					cardState0="checked";
  				}else if(rec_useflag.equals("1")){
  					cardState1="checked";
  				}
  			%>
        		<input type="radio" name="rec_useflag" value="0"  class="radio" <%=cardState0%> >启用 
        		<input type="radio" name="rec_useflag" value="1"   class="radio"  <%=cardState1%> >不启用 
        	</td>
    	</tr>
  	</table>
  	<table width="88%">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
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
  	</table>
<p>
    <input type="hidden" name="oldTrack_no" value="<%=track_no%>">
    <input type="hidden" name="oldMatchStart" value="<%=matchStart%>">
    <input type="hidden" name="oldCardMatch" value="<%=cardMatch%>">
    <input type="hidden" name="oldRec_datetime" value="<%=rec_datetime%>">
    <input type="hidden" name="oldRec_useflag" value="<%=rec_useflag%>">
</p>
</form>
</body>
</html>