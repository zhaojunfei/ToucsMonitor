<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //取得消息
	request.removeAttribute("Message");   //删除消息
	if(obj1!=null)msg=(String)obj1;
	
  	String REGCARD="16001";
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
			
	if(!validateTxt("matchStart","适配偏移" )) return false;
	if(TextIsNull("cardMatch",15,"发卡行适配标识"))return false;
	if(!validateTxt1("cardStart","卡号起始位" ) )return false;	
    if(!validateTxt1("cardLen" ,"卡号长度") )return false;
	if(!validateTxt1("bankStart","发卡行标示偏移位" )) return false;
	if(!validateTxt1("bankLen","发卡行标识长度" ) )return false;
	if(!validateTxt1("pinoffset","密码偏移位" ) )return false;	
    if(!confirm("确认要新增卡表信息吗?按下确定按钮后将新增卡表信息，提交后将影响所有的ATM卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/parametersetservlet?reqCode=<%=REGCARD%>&&uid=<%=uid%>";
 	document.form1.submit();			
}
	
function cancel11(){
	document.form1.action="/ToucsMonitor/parametersetservlet?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.form1.submit();				
}
 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000" onload="showPopUpOnLoad()">
<form name="form1" method="post" action="/ToucsMonitor/parametersetservlet">
<h2><font face="隶书">卡表管理</font></h2>
	<table width="631">
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">磁道号</td>
      		<td width="157"> 
        		<input type="text" name="track_no" size="20" maxlength="1" >
      		</td>
      		<td width="158"> &nbsp;&nbsp;适配偏移</td>
      		<td width="172"> 
        		<input type="text" name="matchStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">卡适配标识</td>
      		<td width="157"> 
        		<input type="text" name="cardMatch" size="20" maxlength="15">
      		</td>
      		<td width="158">&nbsp;&nbsp;卡号起始位</td>
      		<td width="172"> 
        		<input type="text" name="cardStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">卡号长度</td>
      		<td width="157"> 
        		<input type="text" name="cardLen" size="20">
      		</td>
      		<td width="158">&nbsp;&nbsp;发卡行标示偏移位</td>
      		<td width="172"> 
        		<input type="text" name="bankStart" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">发卡行标识长度</td>
      		<td width="157"> 
        		<input type="text" name="bankLen" size="20">
      		</td>
      		<td width="158">&nbsp;&nbsp;发卡行适配标识</td>
      		<td width="172"> 
        		<input type="text" name="bankMatch" size="20" maxlength="8">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">银行代码</td>
      		<td width="157"> 
        		<select name="bankCode">
          		<option value=" "> </option>
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
							if(bankcode!=null){
								bankcode=bankcode.trim();
							}
							if(bankname!=null){
								bankname=bankname.trim();
							}
				%>
          		<option value="<%=bankcode%>" > <%=bankname%></option>
          		<%
						}
	    			}
				%>
        		</select>
      		</td>
      		<td width="158">&nbsp;&nbsp;密码偏移位</td>
      		<td width="172"> 
        		<input type="text" name="pinoffset" size="20">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">卡类型</td>
      		<td width="157"> 
        		<select name="cardType">
          		<option value=" "> </option>
          		<%			
	           		List cardTypeList=(List)request.getAttribute("cardType") ;
			   		if(cardTypeList!=null){
			   			String cardTypeID="";
			   			String cardTypeDesc="";
						for(int i=0;i<cardTypeList.size();i++){				
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
							if(cardTypeDesc!=null){
								cardTypeDesc=cardTypeDesc.trim();
							}
				%>
          		<option value="<%=cardTypeID%>"> <%=cardTypeDesc%></option>
          		<%
						}
	    			}
				%>
        		</select>
      		</td>
      		<td width="158">&nbsp;&nbsp;卡地区编码</td>
      		<td width="172"> 
        		<input type="text" name="cardArea" size="20" maxlength="4">
      		</td>
    	</tr>
    	<tr> 
      		<td width="118">卡种类</td>
      		<td width="157"> 
        		<select name="cardClass">
          		<option value=" "> </option>
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
							if(cardclass!=null){
								cardclass=cardclass.trim();
							}
							if(cardclassDesc!=null){
								cardclassDesc=cardclassDesc.trim();
							}
				%>
          		<option value="<%=cardclass%>" > <%=cardclassDesc%></option>
          		<%
						}
	    			}
				%>
        		</select>
      		</td>
      		<td width="158"> &nbsp;&nbsp;卡表状态</td>
      		<td width="172"> 
        		<input type="radio" name="rec_useflag" value="0" class="radio" checked >启用 
        		<input type="radio" name="rec_useflag" class="radio" value="1"  >不启用 
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
          			<input type="reset" name="Submit2" value="重填"  class="inputbutton" >
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