<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%
	TxnCodeBean code=(TxnCodeBean)request.getAttribute("code");
%>

<html>
<head>
	<title>ƽ̨������ά��</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="JavaScript">
function  doSubmit(){
	if( isNull("txn_code","���״���"))return false;
  	if( isNull("txn_name","��������"))return false;
  	if( isNull("hand_flag","�Ƿ�����"))return false;
  	if( isNull("dc_flag","�����־"))return false;
  	if( isNull("reverse_flag","�Ƿ����"))return false;
  	if( isNull("insert_flag","�Ƿ������ˮ"))return false;
  	if( formReg.deviceType.value == "pos" ){
    	if( isNull("app_flag1","�Ƿ����Ʊ�ݼ��"))return false;
  	}
  	return confirm("ȷ�����������ύ��");
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String post=request.getParameter("post");
	String uid=request.getParameter("uid");
%>
<h2><font face="����">
ƽ̨���������</font></h2>
<hr align="left">
<form name="formReg" method="post" action="/ToucsMonitor/txncodeman?reqCode=<%=post%>&uid=<%=uid%>"  onSubmit="javascript:return doSubmit()">
	<table width="400" >
    	<tr>
      		<td width="148" align="right" ><input type="hidden" name="deviceType" value="<%=code.getDeviceType()%>">�豸���ͣ� </td>
      		<td width="242" ><%=code.getDeviceType().toUpperCase()%></td>
    	</tr>
    	<tr >
      		<td width="148" align="right" > ���״��룺</td>
      		<td width="242" >
       			<input type="text" name="txn_code" class="wideinput" value="<%=code.getTxnCode()%>" size="20" maxlength="20"  >
      		</td>
      	</tr>
      	<tr>
       		<td width="148" align="right"> �������ƣ�</td>
      		<td width="242" >
        		<input type="text" name="txn_name" class="wideinput" value="<%=code.getTxnName()%>" size="20" maxlength="30">
      		</td>
    	</tr>
     	<tr >
      		<td width="148" align="right" > �Ƿ����佻�ף�</td>
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
	        	<option value="">ѡ��</option>
	        	<option value="1" <%=yesSel%>>��</option>
	        	<option value="0" <%=noSel%>>��</option>
	        	<% } %>
	      		</select>
      		</td>
      	</tr>
	 	<tr >
       		<td width="148" align="right"> �������־��</td>
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
                <option value="">ѡ��</option>
                <option value="1"<%=yesSel%>>�跽</option>
        		<option value="0"<%=noSel%>>����</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
		<tr >
      		<td width="148" align="right" > �Ƿ�������ף�</td>
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
                <option value="">ѡ��</option>
                <option value="1" <%=yesSel%>>��</option>
                <option value="0" <%=noSel%>>��</option>
        		<% } %>
      			</select>
      		</td>
        </tr>
	 	<tr >
       		<td width="148" align="right"> �Ƿ������ˮ��</td>
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
                <option value="">ѡ��</option>
                <option value="1" <%=yesSel%>>��</option>
                <option value="0" <%=noSel%>>��</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
    		<% if(code.getDeviceType().equals("pos") ){%>
    	<tr >
      		<td width="148" align="right" > �Ƿ����Ʊ�ݼ�飺</td>
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
                <option value="">ѡ��</option>
                <option value="1" <%=yesSel%>>��</option>
                <option value="0" <%=noSel%>>��</option>
        		<% } %>
      			</select>
      		</td>
    	</tr>
    	<tr >
      		<td width="148" align="right" > �Ƿ�Ԥ��Ȩ���ף�</td>
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
                <option value="">ѡ��</option>
                <option value="1" <%=yesSel%>>��</option>
                <option value="0" <%=noSel%>>��</option>
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
          		<input type="submit" name="save" value="����" class="inputbutton">
          		<input type="button" name="cancel" value="ȡ��" onClick=history.back() class="inputbutton">
      		</td>
    	</tr>
  	</table>
<p>��</p>
</form>
</body>
</html>