<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%
  String MODCARD="16803";
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
  	var cardmatch_len=document.all("cardmatch_len").value ;
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
 	var company_id= document.all("company_id").value;
 	      
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
	if(company_id.length==0){
		alert("请选择预付卡公司 ！");
		document.all("company_id").focus(this);
		return false;
	}	
	//if(!validateTxt("card_match","卡BIN" ) )return false;
	if(TextIsNull("card_len",5,"卡长度" ) )return false;	
	if(!validateTxt1("card_len","卡长度" ) )return false;
	if(!confirm("确认要修改卡表信息吗?按下确定按钮后将修改卡表信息！")) return false;
	document.form1.action="/ToucsMonitor/posBCCard?reqCode=<%=MODCARD%>&&uid=<%=uid%>";	
	document.form1.submit();			
    }
 
function cancel11(){
	document.form1.action="/ToucsMonitor/posBCCard?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
 	document.form1.submit();			
}
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000">
	<%   		
		Object obj=request.getAttribute("POSBCCardInfo");   
		POSBCCardInfo cardInfo=null;
		if(obj==null)
			cardInfo=new POSBCCardInfo();
		else
			cardInfo=(POSBCCardInfo)obj;
			
		String card_id=cardInfo.getCard_id();
		String track_no=cardInfo.getTrack_no();
   		String card_match=cardInfo.getCard_match();
		String cardmatch_len=cardInfo.getCardmatch_len();
   		String card_len=cardInfo.getCard_len();
   		String company_id=cardInfo.getCompany_id();
   		String datetime=cardInfo.getDatetime();
   		String rec_useflag=cardInfo.getRec_useflag();
   		String memo1=cardInfo.getMemo1();
   		String memo2=cardInfo.getMemo2();  
   	%>
<form name="form1" method="post" action=""  >
  	<h2><font face="隶书">预付卡pos卡卡表修改</font></h2>
  	<table width="671" bordercolor="#CCCCCC">
   		<tr> 
      		<td colspan="5" nowrap> <hr noshade></td>
    	</tr>  
    <!--  	<tr> 
      		<td width="40%" align="right">卡序号</td>
      		<td width="60%"> 
        		<input type="text" name="card_id"  class="readonly" READONLY style="background-color:#cccccc" value="<%=card_id%>" size="20">
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
					String sel=" ";	
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
					if(company_id!=null)company_id=company_id.trim();
					else  company_id=" ";
					
					if(company_id.equals(companyId)) sel="selected";
          	
				%>
          		<option value="<%=companyId%>" <%=sel%> > <%=company_name%></option>
               	<%
						}
		    		}
				%>
        		</select>
        		*</td> 
  		</tr>
    	<tr> 
      		<td width="40%" align="right">磁道号</td>
      		<td width="60%"> 
        		<input type="text" name="track_no" value="<%=track_no%>" size="20" maxlength="1" >
      		*</td> 
    	</tr>
    	<tr>
  			<td width="40%" align="right">卡bin </td>
     		<td width="60%"> 
        		<input type="text" name="card_match" class="readonly" READONLY style="background-color:#cccccc" value="<%=card_match%>" size="20" maxlength="10">
      		*</td>
    	</tr>
    	<tr>
  			<td width="40%" align="right">卡bin长度 </td>
     		<td width="60%"> 
        		<input type="text" name="cardmatch_len" class="readonly" READONLY style="background-color:#cccccc" value="<%=cardmatch_len%>" size="20" >
      		</td>
    	</tr>
    	<tr>
    		<td width="40%" align="right">卡号长度</td>
      		<td width="60%"> 
	        <%        
		  		if(card_len==null || card_len.equals("0") ){
		  	%>
	        	<input type="text" name="card_len"   size="20">
	        <%
		  		}else{
		  	%>
	        	<input type="text" name="card_len"  value="<%=card_len%>" size="20">
	        <%
			  	}
		  	%>
      		*</td>
    	</tr>
    	<tr>   
       		<td  width="40%" align="right">备注1</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo1" value="<%=memo1%>" size="20" maxlength="50">
	     	</td> 
  		</tr>
  		<tr>   
       		<td  width="40%" align="right">备注2</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo2" value="<%=memo2%>" size="20" maxlength="50">
	     	</td> 
  		</tr>
		
	   <tr>  
	   		<td width="40%" align="right">卡表状态</td>
	      	<td width="60%"> 
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
      		<td colspan="5" nowrap> <hr noshade></td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        	<div align="right">
          		<input type="submit" name="regSubmit" value="确定"  class="inputbutton"  onClick="check(); return false;">
          		<input type="reset" name="Submit22" value="重填" class="inputbutton" >
          		<input type="button" name="cancelbutton" value="取消" class="inputbutton"  onClick=history.back()>
          	<!--  <input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;"> -->
        	</div>
      		</td>
    	</tr>
  	</table><p>
  	    <input type="hidden" name="oldCard_id" value="<%=card_id%>">
    	<input type="hidden" name="oldCard_match" value="<%=card_match%>">
    	
  	</p>
</form>
</body>
</html>