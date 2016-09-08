<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	TxnCodeBean code=(TxnCodeBean)request.getAttribute("code");
%>

<html>
<head>
	<title>平台交易码维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="JavaScript">
function  doSubmit(){
	if( isNull("txn_code","交易代码"))return false;
  	if( isNull("txn_name","交易名称"))return false;
  	if( isNull("hand_flag","是否手输"))return false;
  	if( isNull("dc_flag","借贷标志"))return false;
  	if( isNull("reverse_flag","是否冲正"))return false;
  	if( isNull("insert_flag","是否插入流水"))return false;
  	if( formReg.deviceType.value == "pos" ){
    	if( isNull("app_flag1","是否进行票据检查"))return false;
  	}
  	return confirm("确认输入无误并提交吗？");
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String post=request.getParameter("post");
	String uid=request.getParameter("uid");
%>
<h2><font face="隶书">
平台交易码管理</font></h2>
<hr align="left">
<form name="formReg" method="post" action="/ToucsMonitor/txncodeman?reqCode=<%=post%>&uid=<%=uid%>"  onSubmit="javascript:return doSubmit()">
	<table width="400" >
    	<tr>
      		<td width="148" align="right" ><input type="hidden" name="deviceType" value="<%=code.getDeviceType()%>">设备类型： </td>
      		<td width="242" ><%=code.getDeviceType().toUpperCase()%></td>
    	</tr>
    	<tr >
      		<td width="148" align="right" > 交易代码：</td>
      		<td width="242" >
       			<input type="text" name="txn_code" class="wideinput" value="<%=code.getTxnCode()%>" size="20" maxlength="20"  >
      		</td>
      	</tr>
      	<tr>
       		<td width="148" align="right"> 交易名称：</td>
      		<td width="242" >
        		<input type="text" name="txn_name" class="wideinput" value="<%=code.getTxnName()%>" size="20" maxlength="30">
      		</td>
    	</tr>
     	<tr >
      		<td width="148" align="right" > 是否手输交易：</td>
      		<td width="242" >
	        	<select size="1" name="hand_flag">
	        	<%{    
	        		String yesSel="";
	               	String noSel="";
	               	if(code.getHandFlag().equals("1"))
	                	yesSel="SELECTED";
	               	if(code.getHandFlag().equals("0"))
	                 	noSel="SELECTED";
	        	%>
	        	<option value="">选择</option>
	        	<option value="1" <%=yesSel%>>是</option>
	        	<option value="0" <%=noSel%>>否</option>
	        	<% } %>
	      		</select>
      		</td>
      	</tr>
	 	<tr >
       		<td width="148" align="right"> 借贷方标志：</td>
      		<td width="242" >
        		<select size="1" name="dc_flag">
        		<%{    
        			String yesSel="";
               		String noSel="";
               		if(code.getDCFlag().equals("1"))
                  		yesSel="SELECTED";
               		if(code.getDCFlag().equals("0"))
                  		noSel="SELECTED";
        		%>
                <option value="">选择</option>
                <option value="1"<%=yesSel%>>借方</option>
        		<option value="0"<%=noSel%>>贷方</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
		<tr >
      		<td width="148" align="right" > 是否冲正交易：</td>
      		<td width="242" >
       			<select size="1" name="reverse_flag">
        		<%{    
        			String yesSel="";
               		String noSel="";
               		if(code.getReverseFlag().equals("1"))
                  		yesSel="SELECTED";
               		if(code.getReverseFlag().equals("0"))
                  		noSel="SELECTED";
        		%>
                <option value="">选择</option>
                <option value="1" <%=yesSel%>>是</option>
                <option value="0" <%=noSel%>>否</option>
        		<% } %>
      			</select>
      		</td>
        </tr>
	 	<tr >
       		<td width="148" align="right"> 是否插入流水：</td>
      		<td width="242" >
        		<select size="1" name="insert_flag">
        		<%{    
        			String yesSel="";
               		String noSel="";
               		if(code.getInsertFlag().equals("1"))
                  		yesSel="SELECTED";
               		if(code.getInsertFlag().equals("0"))
                  		noSel="SELECTED";
        		%>
                <option value="">选择</option>
                <option value="1" <%=yesSel%>>是</option>
                <option value="0" <%=noSel%>>否</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
    		<% if(code.getDeviceType().equals("pos") ){%>
    	<tr >
      		<td width="148" align="right" > 是否进行票据检查：</td>
      		<td width="242" >
       			<select size="1" name="app_flag1">
         		<%{   
         			String yesSel="";
               		String noSel="";
               		if(code.getAppFlag1().equals("1"))
                  		yesSel="SELECTED";
               		if(code.getAppFlag1().equals("0"))
                  		noSel="SELECTED";
        		%>
                <option value="">选择</option>
                <option value="1" <%=yesSel%>>是</option>
                <option value="0" <%=noSel%>>否</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
    	<tr >
      		<td width="148" align="right" > 是否预授权交易：</td>
      		<td width="242" >
       			<select size="1" name="app_flag2">
         		<%{   
         			String yesSel="";
               		String noSel="";
               		if(code.getAppFlag2().equals("1"))
                  		yesSel="SELECTED";
               		if(code.getAppFlag2().equals("0"))
                  		noSel="SELECTED";
        		%>
                <option value="">选择</option>
                <option value="1" <%=yesSel%>>是</option>
                <option value="0" <%=noSel%>>否</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
    	<% } %>
 	</table>
<hr align="left">
  	<table width="490">
    	<tr>
      		<td width="484">
          		<input type="submit" name="save" value="保存" class="inputbutton">
          		<input type="button" name="cancel" value="取消" onClick=history.back() class="inputbutton">
      		</td>
    	</tr>
  	</table>
<p>　</p>
</form>
</body>
</html>