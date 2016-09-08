<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<jsp:useBean id="POSBCCardInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSBCCardInfo"  scope="request"/>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<head>
<title>Untitled Document</title>
<%
	Object obj1=request.getAttribute("Message");
	request.removeAttribute("Message");
	String msg="";
	if(obj1!=null)msg=(String)obj1;

	obj1=request.getAttribute("uid");  
  	String uid=request.getParameter("uid");
  	if(obj1!=null) uid=(String)obj1;

  	String MODCARD = "16803";
  	String QUERYCARD = "16804";
  	String MODCARDREQ1="1680301";
  	
  	String CANCEL="CANCEL";
  
	String reqCode=QUERYCARD;
	obj1=request.getAttribute("reqCode");
	if(obj1!=null)reqCode=(String)obj1;

   	Hashtable opv=(Hashtable)request.getAttribute("OpervRes");
  	String hasM="",hasQ="";
  	if(opv !=null){
    	Object obj2;	
	obj2=opv.get(MODCARD);
	if(obj2!=null)
		hasM=(String)obj2;
	obj2=opv.get(QUERYCARD);
	if(obj2!=null)
		hasQ=(String)obj2;
  	}
  
    System.out.println("hasQ=================="+hasQ);
	System.out.println("hasM=================="+hasM);		
%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script>
function show(i) {
	if (i==1) {
		//document.all("txtCard").style.display = "";
	} else if(i==2){
		//document.all("txtCard").style.display = "none";
	}
}

function showPopUpOnLoad(){
   	var Msg = '<%= msg %>';
   	if (Msg != "") {
       	alert(Msg);
   	}
}

function validateTxt(textName,msg){ 
  	var newValue = document.all(textName).value ;
  	var newLength = newValue.length;  
  	for(var i = 0; i != newLength; i++) {
   		aChar = newValue.substring(i,i+1);
    	if(aChar < "0" || aChar > "9") {
     		alert(msg+"必须是整数数据类型！");    	
      		document.all(textName).focus(this); 
	  		return false;
    	}
  	}
  	return true;
}

function TextIsNull(textName,maxDigit){
 	var newValue = document.all(textName).value ;
	var newLength = newValue.length;
   	if(newLength>maxDigit){
		var msg="请输入的数据位数小于等于"+maxDigit+"位！";		
		alert(msg);
    	document.all(textName).focus(this);
	    return true;
 	} 
 	return false;
 }

 

	
function cancel11(){  
	var url="/ToucsMonitor/posBCCard?reqCode=<%=CANCEL%>&&uid=<%=uid%>";
	document.form1.action=url;
	document.form1.submit();
	return true;
}

function submit11(type){				
	
	var url;
	url="/ToucsMonitor/posBCCard?reqCode=<%=QUERYCARD%>";				
	document.form1.action=url;	
	document.form1.submit();
} 
</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body bgcolor="#CCCCCC" text="#000000" onLoad="showPopUpOnLoad()">
<% 
	  //String post=request.getParameter("post");%>
	<h2><font face="隶书">预付卡pos卡卡表查询</font> </h2>
	<hr noshade>
	<form name="form1" method="post" action="/ToucsMonitor/posBCCard">
<!-- 
  	<input type="hidden" name="post" value=>
 -->	  	
  	<table width="631" border="0">
  	<!--  <tr>
  		 	<td  width="40%" align="right">卡序号</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_id" size="20" maxlength="5">
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
        		</td> 
  		</tr>
    	<tr> 
	    	<td  width="40%" align="right">磁道号</td>
	    	<td width="60%"> 
	       		<input type="text" name="track_no" size="20" maxlength="1" >
	    	</td>
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
    		</td>
    	</tr> 
  		<tr>
  		 	<td  width="40%" align="right">卡BIN</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_match" size="20" maxlength="15">
	     	</td> 
		</tr> 
	 	<tr>   
       		<td  width="40%" align="right">卡长度</td>
	   		<td width="60%"> 
	      		<input type="text" name="card_len" size="20" maxlength="15">
	     	</td> 
  		</tr>
	 	<tr>    
	      	<td  width="40%" align="right">卡表状态</td>
	      	<td width="60%" > 
	      	    <input type="radio" name="rec_useflag"  class="radio" value="3" checked>所有
	        	<input type="radio" name="rec_useflag" class="radio" value="0">启用 
        		<input type="radio" name="rec_useflag"  class="radio" value="1">不启用
  			     </td> 
    	</tr>
	</table>		
<hr noshade>
<p align="right"> 
   <%	
	String opvMQ=hasQ;	
	if(reqCode.equals(MODCARDREQ1)) opvMQ=hasM;
	if(opvMQ.equals("0")){	
%>
    <input type="submit" name="Submit3" value="确定" class="inputbutton" onClick="submit11(2); return false;">
<%
	}%>
	<input type="reset" name="Submit22" value="重填" class="inputbutton" >
  <!--   <input type="submit" name="cancel" value="取消"   class="inputbutton"  onClick="cancel11(); return false;">  -->
    
</p>
</form>
</body>
</html>