<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
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
	}
}
%>

<html>
<head>
	<title>�����̻�</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/power.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.checkpztable.merchantIsOk.checked){
		document.all.merchantok.style.display="";
	}else{
		document.all.merchantok.style.display="none";
	}
}

function onOrgChange(){
}

function onBankOrgChange(){
}

function doSubmit(){	
	if(!isNumber("PZ_theMinMoney","������ѽ��")) return false;
	if(!validFullDate("PZbegin_date","��ʼ����"))	return false;
	if(!validFullDate("PZend_date","��������")) return false;

	if(confirm("ȷ�����������ύ��")){
		document.forms[0].managebankname_temp.value = document.forms[0].manage_bankname.options[document.forms[0].manage_bankname.selectedIndex].text;
		return true;
	}else
		return false;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
    POSMerchantBean PMB=new POSMerchantBean();
	Vector oV  =new Vector();
	Vector oV1 =new Vector();
	Vector oV2 =new Vector();
	Hashtable Res=new Hashtable();
	int i=0;
	oV  = (Vector)PMB.getCardClass();
	oV1 = (Vector)PMB.getCardType(); 
	oV2 = (Vector)PMB.getAllMerchant();
  %>
<h2><font face="����">�齱��������</font></h2>
<form name="checkpztable" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=10422&target=checkPZAdd&uid=<%=uid%>">
<input type="hidden" name="managebankname_temp" value="">  
  	<table id="tab0" width="80%">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right" >�����ţ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="channel_id" class="wideinput" value="" MAXLENGTH="15" size="20">*
      		</td>
      		<td width="20%" nowrap align="right"></td>
      		<td width="30%" nowrap></td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">��ѡ������</td>      
    	</tr>
    	<tr valign="top"> 
        	<td width="43%"> 
          		<div align="center">
            		<select name="newcheckprize" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
            		</select>
          		</div>
      		</td>
        	<td width="14%"> 
          		<div align="center">
          		<p>         
              		<input type="button" name="Button" value="&lt;&lt;&lt;���" onclick="javascript:DeleteItem('availcheckprize','newcheckprize')" style="height:16;width:50;font-size:12" >   
	              	<input type="button" name="Submit2" value="ɾ��&gt;&gt;&gt;" onclick="javascript:DeleteItem('newcheckprize','availcheckprize')" style="height:16;width:50;font-size:12" >	      
              		<input type="button" name="Submit3" value="ȷ��" onclick="javascript:Submited('newcheckprize')" style="height:16;width:70;font-size:12" >
            	</p>
            	</div>
      		</td>
        	<td width="43%"> 
          		<div align="center">
            		<select name="availcheckprize" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
              		<%
						i=0;			
						while(i<oV.size()){
							Res=(Hashtable)oV.get(i);
							i++;
					%>
              		<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
					<%
			 			}
					%>
            		</select>
          		</div>
      		</td>
    	</tr> 
     	<tr>
      		<td width="20%" nowrap align="right">��ѡ������</td>      
    	</tr>
    	<tr valign="top"> 
        	<td width="43%"> 
          		<div align="center">
            		<select name="newcardtype" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
            		</select>
          		</div>
      		</td>
        	<td width="14%"> 
          		<div align="center">
          		<p>
              		<input type="button" name="Button" value="&lt;&lt;&lt;���" onclick="javascript:DeleteItem('availcardtype','newcardtype')" style="height:16;width:50;font-size:12" >         
              		<input type="button" name="Submit2" value="ɾ��&gt;&gt;&gt;" onclick="javascript:DeleteItem('newcardtype','availcardtype')" style="height:16;width:50;font-size:12" >       
              		<input type="button" name="Submit3" value="ȷ��" onclick="javascript:Submited('newcardtype')" style="height:16;width:70;font-size:12" >
          		</p>
            	</div>
      		</td>
        	<td width="43%"> 
          		<div align="center">
            		<select name="availcardtype" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
              		<%
						i=0;			
						while(i<oV1.size()){
							Res=(Hashtable)oV1.get(i);
							i++;
					%>
              		<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
					<%
			 			}
					%>
            		</select>
          		</div>
      		</td>
    	</tr> 
    	<tr>
      		<td width="20%" nowrap align="right">��ʼ����</td>
      		<td width="30%" nowrap>
        		<input type="text" name="PZbegin_date" class="wideinput"  value="" MAXLENGTH="50" size="20">*
      		</td>
      		<td width="20%" nowrap align="right">��������</td>
      		<td width="30%" nowrap>
        		<input type="text" name="PZend_date" class="wideinput"  value="" MAXLENGTH="50" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">��ʼʱ��</td>
      		<td width="30%" nowrap>
        		<input type="text" name="PZbegin_time" class="wideinput"  value="" MAXLENGTH="50" size="20">*
      		</td>
      		<td width="20%" nowrap align="right">����ʱ��</td>
      		<td width="30%" nowrap>
        		<input type="text" name="PZend_time" class="wideinput"  value="" MAXLENGTH="50" size="20">*
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">�Ƿ�ָ������</td>
      		<td width="30%" nowrap>
      			<select name="manage_prizetype" size="1" onchange="javascript:onBankOrgChange()">
            	<option value="">��ѡ��</option>
            	<%initList("IsCardTypeList","",request,out);%>
          		</select>*
       		</td>     
      		<td width="20%" nowrap align="right" >����</td>
      		<td width="30%" nowrap align="right" >
      			<select name="manage_prizetype" size="1" onchange="javascript:onBankOrgChange()">
            	<option value="">��������</option>
            	<%initList("IsCardTypeList","",request,out);%>
          		</select>*
       		</td>     
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">������ѽ��</td>
      		<td width="30%" nowrap>
        		<input type="text" name="PZ_theMinMoney" class="wideinput"  value="" MAXLENGTH="50" size="20">*
      		</td>      
    	</tr> 
   		<tr>
      		<td colspan="4" width="662" height="16">
        		<input type="checkbox" name="merchantIsOk" value="checkbox" class="radio" onClick="javascript:onCondition()">ָ���̻�
        	</td>
   		</tr>
   	<table width="99%" id="merchantok"  style="display:none">   
   		<tr>
          	<td width="17%" height="20">�̻������б�</td>
   		</tr>
   		<tr valign="top"> 
        	<td width="43%"> 
          		<div align="center">
            	<select name="newmerchantlist" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
            	</select>
          		</div>
      		</td>
        	<td width="14%"> 
          		<div align="center">
          		<p>
              		<input type="button" name="Button" value="&lt;&lt;&lt;���" onclick="javascript:DeleteItem('availmerchantlist','newmerchantlist')" style="height:16;width:50;font-size:12" >
        	      	<input type="button" name="Submit2" value="ɾ��&gt;&gt;&gt;" onclick="javascript:DeleteItem('newmerchantlist','availmerchantlist')" style="height:16;width:50;font-size:12" >
              		<input type="button" name="Submit3" value="ȷ��" onclick="javascript:Submited('newmerchantlist')" style="height:16;width:70;font-size:12" >
            	</p>
            	</div>
      		</td>
        	<td width="43%"> 
          		<div align="center">
            		<select name="availmerchantlist" size="12" multiple style="width:180;height:50;border:1 solid #9a9999; font-size:12px; background-color:#DDDDDD">
              		<%
						i=0;			
						while(i<oV2.size()){
							Res=(Hashtable)oV2.get(i);
							i++;
					%>
             	 	<option value="<%=Res.get("merchant_id")%>"><%=Res.get("mct_name")%></option>
					<%
			 			}
					%>
            		</select>
          		</div>
      		</td>      
   		</tr>
   	</table>
	</table> 
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="5" nowrap>
        		<div align="right">
          		<input type="submit" name="submit" value="�ύ" class="inputbutton">
          		<input type="reset" name="reset" value="����" class="inputbutton">
          		<input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/posmerchantconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>