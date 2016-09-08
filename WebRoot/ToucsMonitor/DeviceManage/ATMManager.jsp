<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%	
	Object obj1=request.getAttribute("Message");
	String msg="";
	if(obj1!=null)msg=(String)obj1;

	//取得权限
	String MG7520="style='display:none'",MG7530="style='display:none'",MG7540="style='display:none'",MG7560="style='display:none'";
	String MG7570="style='display:none'",MG7580="style='display:none'",MG7501="style='display:none'";
	
	obj1=request.getAttribute("MG7520");
	if(obj1!=null)MG7520=(String)obj1;
	
	obj1=request.getAttribute("MG7530");
	if(obj1!=null)MG7530=(String)obj1;

	obj1=request.getAttribute("MG7540");
	if(obj1!=null)MG7540=(String)obj1;

		
	obj1=request.getAttribute("MG7560");
	if(obj1!=null)MG7560=(String)obj1;	
	
	obj1=request.getAttribute("MG7570");
	if(obj1!=null)MG7570=(String)obj1;	
	
	obj1=request.getAttribute("MG7580");
	if(obj1!=null)MG7580=(String)obj1;	

	obj1=request.getAttribute("MG7501");
	if(obj1!=null)MG7501=(String)obj1;	
	
	obj1=request.getAttribute("DealCode");
	String selDealCode="";
	if(obj1!=null)
	   selDealCode=(String)obj1;
	selDealCode=selDealCode.trim();

	obj1=request.getAttribute("ATMDevice");
	String selATMDevice="";
	if(obj1!=null)
	   selATMDevice=(String)obj1;
	selATMDevice=selATMDevice.trim();
	  
	String uid=request.getParameter("uid");
	String selmg7520="",selmg7530="",selmg7540="",selmg7560="";
	String selmg7570="",selmg7580="",selmg7501="";
	
	if(selDealCode.equals("MG7520")){
		selmg7520="checked";
	}else if(selDealCode.equals("MG7530")){
		selmg7530="checked";
	}else if(selDealCode.equals("MG7540")){
		selmg7540="checked";
	}else if(selDealCode.equals("MG7560")){
		selmg7560="checked";
	}else if(selDealCode.equals("MG7570")){
		selmg7570="checked";
	}else if(selDealCode.equals("MG7580")){
		selmg7580="checked";
 	}else if(selDealCode.equals("MG7501")){
		selmg7501="checked";
     }		
	System.out.print("【哈哈哈】");
%>
<html>
<head>
<title>设备参数管理</title>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">  
  <META NAME="GENERATOR" CONTENT="Adobe PageMill 3.0J Win">
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=x-sjis">
  <meta http-equiv="Content-Language" content="ja">
  <meta http-equiv="Content-Style-Type" content="text/css">
  <meta http-equiv="Content-Script-Type" content="text/javascript">
 
 <script type="text/javascript">		
function selectorg(){	
	document.all("reqCode").value = "GETATMDEVIC";
	document.all("ATMDevice").value="";			
	url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=GETATMDEVIC&&uid=<%=uid%>";    			
	document.form1.action=url;		  
	document.form1.submit();			
}
function selecDevice(){
	document.all("ATMDevice").value =document.all("ATMDeviceSel").value;
}

function submit11(){
  if(!check()) return false;
  if(!confirm("你确认要提交请求吗？")) return false;
  url="/ToucsMonitor/ATMDeviceManageServlet?reqCode=ATMDEVICMANAGE&&uid=<%=uid%>";    
  document.form1.action=url;		  
  document.form1.submit();
}
function exit11(){
	return false;
}

		
function check(){
	var dev=document.all("ATMDevice").value;			
	var org=document.all("OrgCodeSel").value;
	var dealIsSel=false;
	if(document.form1.DealCode[0].checked){
		dealIsSel=true;
	}else if(document.form1.DealCode[1].checked){
		dealIsSel=true;
	}else if(document.form1.DealCode[2].checked){
		dealIsSel=true;
	}else if(document.form1.DealCode[3].checked){			
		dealIsSel=true;
	}else if(document.form1.DealCode[4].checked){			
		dealIsSel=true;				
	}else if(document.form1.DealCode[5].checked){			
		dealIsSel=true;				
	}else if(document.form1.DealCode[6].checked){			
		dealIsSel=true;				
	}

	if(!dealIsSel)	{
			alert("请选择请求操作类型");
			return false;
	}
	//如果是响应码加载则不需要要输入机构于设备号			
	if(!document.form1.DealCode[3].checked){
		var devorg=0;		
		if(dev=="" || dev==null || dev==" ")  devorg=1;
		if(org=="" || org==null || org==" ")  devorg=devorg+1;
		
		if(devorg==2) {
			alert("ATM设备号与机构号不能全为空，请至少选择一个！");
			return false;
		}
	}	
	//如果是生成密钥并打印交易，必须选择ATM设备号
	if(document.form1.DealCode[6].checked){
		if(dev=="" || dev==null || dev==" "){
			alert("要发起生成密钥并打印交易请求，必须选择ATM设备号！");
			return false;
		}	
	}
	return true;
}
		
function showPopUpOnLoad(){
    var Msg = '<%= msg %>';
    if (Msg != "") {
        alert(Msg);
    }
}
</script>
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000"  onload="showPopUpOnLoad()" >
<form name="form1" method="post"   action="/ToucsMonitor/ATMDeviceManageServlet">
<h2><font face="隶书">ATM设备管理</font></h2>
	<table width="86%">
    	<tr> 
      		<td colspan="5" nowrap> 
        	<hr noshade>
      		</td>
    	</tr>	
    	<tr> 
      		<td width="15%">机构号</td>
      		<td width="85%"> 
        	<select name="OrgCodeSel"  onChange ="selectorg();">
          	<option > </option>
          	<%
			  	List orgList=(List)session.getAttribute("OrgList");	
				Object obj=request.getAttribute("OrgCodeSel");
				String selorg="";
				if(obj!=null)  selorg=(String)obj;	
				if(selorg!=null) selorg=selorg.trim();
				if(orgList!=null){
					String orgcode="";
					String orgname="";
					
				  	for(int i=0;i<orgList.size();i++){
						CodeBean orgDeviceCode=(CodeBean)orgList.get(i);
						if(orgDeviceCode!=null){
							orgcode=orgDeviceCode.getCodeId();
							orgname=orgDeviceCode.getCodeDesc();
							String sel="";
							if(selorg.equals(orgcode)){
									sel="selected";			
							}	
	  		%>
          	<option value="<%=orgcode%>" <%=sel%>> <%=orgname%></option>
          	<%
						}
					}
				}
			%>
        	</select>
        	<input type="hidden" name="OrgCodeSelTmp" value="  ">
      		</td>
    	</tr>
    	<tr> 
      		<td width="15%">ATM设备号</td>
     		<td width="85%"> 
        	<select name="ATMDeviceSel"  onChange ="selecDevice();">
          	<option > </option>
          	<%
		  	List atmList=(List)request.getAttribute("ATMList");
			if(atmList!=null){
				String atmcode="";
			  	for(int i=0;i<atmList.size();i++){
					CodeBean orgDeviceCode=(CodeBean)atmList.get(i);
					if(orgDeviceCode!=null){
						atmcode=orgDeviceCode.getCodeId();
						orgDeviceCode.getCodeDesc();
						String sel="";
						if(selATMDevice.equals(atmcode)) sel="selected";
	  		%>
          	<option value="<%=atmcode%>" <%=sel%>> <%=atmcode%></option>
          	<%
					}
				}
			}
			%>
        	</select>
        	<input type="text" name="ATMDevice" value="<%=selATMDevice%>" class="readonly">
      		</td>
    	</tr>
  	</table>
<br>
  	<table width="86%">
    	<tr> 
      		<td colspan="7">选择要执行的操作</td>
    	</tr>
    	<tr> 
      		<td <%=MG7520%> > 
        		<input type="radio" name="DealCode" class="radio"  <%=selmg7520%> value="MG7520">ATM开机 
        	</td>
      		<td <%=MG7530%>> 
        		<input type="radio" name="DealCode" class="radio"   <%=selmg7530%> value="MG7530">ATM关机
        	</td>
      		<td <%=MG7540%>> 
        		<input type="radio" name="DealCode"  class="radio"  <%=selmg7540%> value="MG7540">密钥传输 
			</td>      
      		<td <%=MG7560%>> 
        		<input type="radio" name="DealCode"   class="radio"   <%=selmg7560%> value="MG7560"> 响应码加载
        	</td>
    		<td <%=MG7501%>> 
        		<input type="radio" name="DealCode" class="radio" <%=selmg7501%> value="MG7501">生成密钥并打印
			</td>
   			<td <%=MG7570%>> 
       			<input type="hidden" name="DealCode" class="radio"  <%=selmg7570%> value="MG7570">
       		</td>
      		<td <%=MG7580%>> 
        		<input type="hidden" name="DealCode" class="radio" <%=selmg7580%> value="MG7580"> 
			</td>
    	</tr>
  	</table>
  	<table width="86%">
   		<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        		<div align="right"> 
          		<input type="submit" name="Submit" value="提交"  class="inputbutton" onClick="submit11(); return false;">
        		</div>
      		</td>
    	</tr>
  	</table>
<br>
  	<input type="hidden" value="ATMDEVICMANAGE" name="reqCode" class="inputbutton">
</form>
</body>
</html>