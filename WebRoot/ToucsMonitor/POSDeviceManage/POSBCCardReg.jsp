<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
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

function validateTxt(textName,msg){ 
  	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	var cardmatch_len=document.all("cardmatch_len").value;
  	var msgs1=msg+"����Ϊ�գ�";
  	var msgs=msg+"����������!";
  	var msgs2=msg+"������λ�������"+cardmatch_len+"λ��!";
  	
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
    var msg1=msg+"����Ϊ�գ�";
    var msg2=msg+"������λ��С�ڵ���"+maxDigit+"λ��"
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
    	alert("��������ȷ��ʽ���ݣ�");    	
      	textObj.focus(this);
	  	return false;
 	}
  
  	for(var i = 0; i != newLength; i++) {
    	aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert("��������ȷ��ʽ���ݣ�");    	
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
   		alert("������ŵ��ţ�");    
		document.all("track_no").focus(this);
		return false;
	}   
	//if(!isInt(track_no)) return false;
	if(track_no!=2 && track_no!=3){
		alert("�ŵ���ֻ��Ϊ2��3��");
		document.all("track_no").focus(this);
		return false;
	}

	if(company_id.length==0){
		alert("��ѡ��Ԥ������˾ ��");
		document.all("company_id").focus(this);
		return false;
	}	

	if(cardmatch_len.length==0){
		alert("��ѡ��BIN���� ��");
		document.all("cardmatch_len").focus(this);
		return false;
	}			
	
	//if(TextIsNull("card_match",15,"��BIN"))return false;
	if(TextIsNull("card_len",5,"������" ) )return false;	
	if(!validateTxt1("card_len","������" ) )return false;	
	if(!validateTxt("card_match","��BIN" ) )return false;		
	//if(!validateTxt1("cardmatch_len","��BIN����" ) )return false;
	//if(!validateTxt1("memo1","��ע1" ) )return false;
	//if(!validateTxt1("memo2","��ע2" ) )return false;
	
    if(!confirm("ȷ��Ҫ����������Ϣ��?����ȷ����ť������������Ϣ��")) return false;
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
<h2><font face="����">Ԥ�����������</font></h2>  
	<table width="631" border="0">
    	<tr> 
      		<td  colspan="2" nowrap> <hr noshade></td>
    	</tr>
    <!--  	<tr> 
	    	<td  width="40%" align="right">�����</td>
	    	<td width="60%"> 
	       		<input type="text" name="card_id" size="20" >
	    	</td>
	 	</tr>-->
	 	<tr>     
  		 	<td  width="40%" align="right">��˾���</td>
	   		<td width="60%"> 
	      	<select name="company_id">
          		<option value="" selected>--��ѡ��-- </option>
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
	    	<td  width="40%" align="right">�ŵ���</td>
	    	<td width="60%"> 
	       		<input type="text" name="track_no" size="20" maxlength="1" >
	    	*</td>
	 	</tr>	
	 		<tr> 
      		<td width="40%" align="right">��BIN����</td>
     		<td width="60%"> 
      			<select name="cardmatch_len"    >
      			<option value="">--��ѡ��--</option>
	         	<option value="6">6</option>
	         	<option value="7">7</option>
	         	<option value="8">8</option>
	         	<option value="9">9</option>
	         	<option value="10">10</option>
	        	</select>
    		*</td>
    	</tr> 
  		<tr>
  		 	<td  width="40%" align="right">�������ʶ(��BIN)</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_match" size="20" maxlength="10"><font color="red" size="3">(��bin����6~10λ)</font>
	     	*</td> 
		</tr>
	 
	 	<tr>   
       		<td  width="40%" align="right">������</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_len" size="20">
	     	*</td> 
  		</tr>
  		<tr>   
       		<td  width="40%" align="right">��ע1</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo1" size="20" maxlength="50">
	     	</td> 
  		</tr>
  		<tr>   
       		<td  width="40%" align="right">��ע2</td>
	   		<td width="60%"> 
	      		<input type="text" name="memo2" size="20" maxlength="50">
	     	</td> 
  		</tr>
		
	 	<!-- <tr>    
	      	<td  width="40%" align="right">����״̬</td>
	      	<td width="60%" > 
	        	<input type="radio" name="rec_useflag" value="0" class="radio" checked > ���� 
	        	<input type="radio" name="rec_useflag" class="radio" value="1"  > ������ 
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
          		<input type="submit" name="regSubmit" value="ȷ��" class="inputbutton"  onClick="check(); return false;">
          		<input type="reset"  name="Submit2" value="����"  class="inputbutton" >
           		<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick=history.back()>
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