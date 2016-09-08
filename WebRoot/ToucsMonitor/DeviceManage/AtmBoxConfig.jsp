<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="boxInfo" class="com.adtec.toucs.monitor.devicemanage.AtmBoxInfo"  scope="request"/>

<%! 
private void initList(String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute("currencyList");	
	String flag="";
	String flag1="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue.equals(code.getCodeId())){
				flag1="SELECTED";
				break;
			}
		}
		if(flag1 == ""){
			for(int i=0;i<list.size();i++){
				CodeBean code=(CodeBean)list.get(i);
				if(defValue!=null&& i==1)
					flag=" SELECTED";
				else 
					flag="";
				try{
						out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
							+code.getCodeDesc()+"</option>");
				}catch(IOException exp){
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}			
			}
		}else{
			for(int i=0;i<list.size();i++){
				CodeBean code=(CodeBean)list.get(i);
				if(defValue!=null&&defValue.equals(code.getCodeId()))
					flag=" SELECTED";
				else 
					flag="";
				try{
					out.println("<option value=\""+code.getCodeId()+"\""+flag+">"+code.getCodeDesc()+"</option>");
				}catch(IOException exp){
					exp.printStackTrace();
					System.out.println(exp.getMessage());
				}			
			}
		}	
	}
}
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onBox5_8(){
	if(document.formConfig.box5_8.checked){	
		document.all("tr5").style.display="";
		document.all("tr6").style.display="";
		document.all("tr7").style.display="";
		document.all("tr8").style.display="";
	}else{
		document.all("tr5").style.display="none";
		document.all("tr6").style.display="none";
		document.all("tr7").style.display="none";
		document.all("tr8").style.display="none";
	}			
}
function checkBoxPara(selBoxCode,selBoxPara){
	if(selBoxCode.value==""){alert("«Î»∑∂®±“÷÷£°"); selBoxCode.focus(); return false;}
	return true;
}
function doSubmit(){
	if(!checkBoxPara(formConfig.box1Code,formConfig.box1Para)){return false;}
	if(!checkBoxPara(formConfig.box2Code,formConfig.box2Para)){return false;}
	if(!checkBoxPara(formConfig.box3Code,formConfig.box3Para)){return false;}
	if(!checkBoxPara(formConfig.box4Code,formConfig.box4Para)){return false;}
	if(formConfig.box5_8.checked){
		if(!checkBoxPara(formConfig.box5Code,formConfig.box5Para)){return false;}
		if(!checkBoxPara(formConfig.box6Code,formConfig.box6Para)){return false;}
		if(!checkBoxPara(formConfig.box7Code,formConfig.box7Para)){return false;}
		if(!checkBoxPara(formConfig.box8Code,formConfig.box8Para)){return false;}
	}
	return confirm("»∑»œ ‰»ÎŒﬁŒÛ≤¢Ã·Ωª¬£ø");		
}
</script>
<script language="javascript">
onBox5_8();
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String flag=request.getParameter("flag");	
	if(flag==null) flag="6";	
%>
<h3><font face="¡• È">ATM…Ë±∏≥Æœ‰≈‰÷√–≈œ¢</font></h3>
<form name="formConfig" method="post" action="/ToucsMonitor/deviceconfig?reqCode=<%=request.getParameter("post")%>&content=box&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
<input type="hidden" name="flag" value=<%=flag%>>
	<table width="75%">
    	<tr> 
      		<td nowrap colspan="5"> 
        		<hr noshade align="left">
      		</td>
    	</tr>
    	<tr> 
      		<td nowrap width="20%"> …Ë±∏±‡∫≈£∫</td>
      		<td nowrap width="20%"> 
        		<input type="text" name="atmCode" READONLY class="readonly" value="<%=boxInfo.getAtmCode()%>">
      		</td>
      		<td nowrap width="20%">&nbsp; </td>
      		<td nowrap width="20%">&nbsp; </td>
      		<td nowrap width="20%">&nbsp; </td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade align="left">
      		</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr> 
      		<td width="20%" nowrap height="27"> ≥Æœ‰1±“÷÷£∫</td>
      		<td width="20%" height="27"> 
        		<select name="box1Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(0),request,out);%>
        		</select>
      		</td>
     		<td width="20%" nowrap height="27">&nbsp; </td>
      		<td width="20%" nowrap height="27"> ≥Æœ‰1±“÷µ£∫</td>
      		<td width="20%" height="27"> 
        	<div align="left"> 
          		<select name="box1Para" size="1">
            	<%if(boxInfo.getBoxPara(0)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==4) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(0)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" nowrap> ≥Æœ‰2±“÷÷£∫</td>
      		<td width="20%"> 
        	<select name="box2Code" size="1">
          	<option value="">«Î—°‘Ò</option>
          	<%initList(boxInfo.getBoxCode(1),request,out);%>
        	</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰2±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box2Para" size="1">
            	<%if(boxInfo.getBoxPara(1)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==4) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(1)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" nowrap> ≥Æœ‰3±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box3Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(2),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰3±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select size="1" name="box3Para">
            	<%if(boxInfo.getBoxPara(2)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==1) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(2)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" nowrap> ≥Æœ‰4±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box4Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(3),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰4±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box4Para" size="1">
            	<%if(boxInfo.getBoxPara(3)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==1) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(3)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
   	 	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade align="left">
      		</td>
    	</tr>
    	<tr> 
      		<td width="20%" nowrap> 
        		<input type="checkbox" name="box5_8" value="used" class="radio" onClick=javascript:onBox5_8() <%=boxInfo.getFlagBox5_8()?"checked":""%>>
      		</td>
      		<td width="20%">≥Æœ‰5-8 </td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%">&nbsp; </td>
    	</tr>
    	<tr  id="tr5" > 
      		<td width="20%" nowrap> ≥Æœ‰5±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box5Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(4),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰5±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box5Para" size="1">
            	<%if(boxInfo.getBoxPara(4)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==4) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(4)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr id="tr6"> 
      		<td width="20%" nowrap> ≥Æœ‰6±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box6Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(5),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰6±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box6Para" size="1">
            	<%if(boxInfo.getBoxPara(5)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==4) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(5)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr id="tr7"> 
      		<td width="20%" nowrap> ≥Æœ‰7±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box7Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(6),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰7±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box7Para" size="1">
            	<%if(boxInfo.getBoxPara(6)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==1) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(6)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr id="tr8"> 
      		<td width="20%" nowrap> ≥Æœ‰8±“÷÷£∫</td>
      		<td width="20%"> 
        		<select name="box8Code" size="1">
          		<option value="">«Î—°‘Ò</option>
          		<%initList(boxInfo.getBoxCode(7),request,out);%>
        		</select>
      		</td>
      		<td width="20%" nowrap>&nbsp; </td>
      		<td width="20%" nowrap> ≥Æœ‰8±“÷µ£∫</td>
      		<td width="20%"> 
        	<div align="left"> 
          		<select name="box8Para" size="1">
            	<%if(boxInfo.getBoxPara(7)==boxInfo.valueList[0]){
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(i==1) selFlag="SELECTED";%>
				<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
				<%}}else{
				for(int i=0;i<boxInfo.valueList.length;i++){String selFlag="";if(boxInfo.getBoxPara(7)==boxInfo.valueList[i]) selFlag="SELECTED";%>
            	<option <%=selFlag%>><%=boxInfo.valueList[i]%></option>
            	<%}}%>
          		</select>
        	</div>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        		<hr noshade align="left">
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="5" nowrap> 
        	<div align="right">
          		<input type="submit" name="submit" value="»∑∂®" class="inputbutton">
          		<input type="reset" name="reset" value="÷ÿÃÓ"  onClick="javascript:formConfig.reset()" class="inputbutton">
          		<input type="button" name="cancel" value="»°œ˚" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?uid=<%=uid%>'" class="inputbutton">
        	</div>
      		</td>
    	</tr>
	</table>
</form>
</body>
</html>

