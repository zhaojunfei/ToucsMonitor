<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSInfo"  scope="request"/>
<jsp:useBean id="posTxnInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSTxnInfo"  scope="request"/>


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
            	out.println("<option value=\""+code.getCodeId()+"\""+flag+">"+code.getCodeDesc()+"</option>");
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
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function onSelChange(){
	var undefined ;
	if( document.forms[0].txnPermF.length==undefined ) return ;
	var memo1a= document.forms[0].memo1a.value;

  	if ( memo1a != "0" ) document.forms[0].equip_type.value = "1";

	var type= document.forms[0].equip_type.value;

	for(  var i =0; i<document.forms[0].txnPermF.length ; i++){
  	if(  type=="1" ){
    	<!--���ѡ����Ч-->
    	document.forms[0].txnPermF[i].disabled = true;
    	document.forms[0].handFlagF[i].disabled = true;
    	document.forms[0].txnPermR[i].disabled = false;
    	document.forms[0].handFlagR[i].disabled = false;
    }
  	if( type=="2" ){
  		<!--����ѡ����Ч-->
  		document.forms[0].txnPermF[i].disabled = false;
    	document.forms[0].handFlagF[i].disabled = false;
    	document.forms[0].txnPermR[i].disabled = true;
    	document.forms[0].handFlagR[i].disabled = true;
  	}
  	if( type=="3" ){
  		<!--�����ѡ����Ч-->
  		document.forms[0].txnPermF[i].disabled = false;
    	document.forms[0].handFlagF[i].disabled = false;
    	document.forms[0].txnPermR[i].disabled = false;
    	document.forms[0].handFlagR[i].disabled = false;
  	}
  }
}
function onSelChangea(){
	var undefined ;

	var memo1a = document.forms[0].memo1a.value;
	
  if ( memo1a != "0" ) document.forms[0].equip_type.value = "1";

	for (var i=0; i<document.forms[0].txnPermR.length; i++) {
		if (memo1a == "0") {				<!--����POS-->
			onSelChange();
		} else {										<!--����POS-->
			document.forms[0].txnPermR[i].disabled = false;
			document.forms[0].handFlagR[i].disabled = false;
			document.forms[0].txnPermF[i].disabled = true;
			document.forms[0].handFlagF[i].disabled = true;
		}
	}
}


function doSubmit(){
	if( isNull("equip_type","�豸����" )) return false;
	if( isNull("pre_authorized","Ԥ��Ȩ����")) return false;
	var msg="ȷ�����������ύ��";

	if (document.forms[0].memo1a.value != "0") return true;
	if (document.forms[0].pre_authorized.value=="0" || document.forms[0].pre_authorized.value=="1")
	   msg = "��ѡ�����["+document.forms[0].pre_authorized.options[document.forms[0].pre_authorized.selectedIndex].text+"]����ʹ��Ĭ��Ȩ�����ã�ȷ�������ύ��";
	if(confirm(msg))
		return true;
	else
		return false;
}

</script>

<body bgcolor="#CCCCCC" text="#000000">
<%
  	String uid=request.getParameter("uid");
  	String reqCode=request.getParameter("reqCode");
%>
<h2><font face="����">POS�豸����������Ϣ</font></h2>
<form name="eBridge" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=<%=reqCode%>&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
    <table id="tab0" width="82%">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">POS��ţ�</td>
      		<td width="30%" nowrap>
      			<input type="text" name="pos_code" value="<%=posInfo.getPoscode()%>" class="readonly" readonly size="20" ></td>
      		<td width="20%" nowrap align="right">POS-C��ţ�</td>
      		<td width="30%" nowrap>
      			<input type="text" name="pos_c_code" value="<%=posInfo.getPosCCode()%>" class="readonly" readonly size="20"></td>
     	</tr>
     	<tr>
       		<td width="20%" nowrap height="19" align="right" >POS-DCC�����ţ�</td>
      		<td width="30%" nowrap height="19">
        		<input type="text" name="pos_dcc_code" class="readonly" readonly value="<%=posInfo.getPosDccCode()%>" size="20">
      		</td>
      		<td width="20%" nowrap height="19" align="right">POS���ͣ�</td>
      		<td width="30%" nowrap height="19">
      			<select name="memo1a" size="1" onChange=javascript:onSelChangea()>
        		<option value="">��ѡ��</option>
				<% initList("memo1aList",posInfo.getMemo1a(),request,out); %>
      			</select>
      		</td>
     	</tr>
    	<tr>
      		<td width="20%" nowrap height="19" align="right">Ԥ��Ȩ���ã�</td>
      		<td width="30%" nowrap height="19">
      			<select name="pre_authorized" size="1">
       			<option value="">��ѡ��</option>
				<% initList("preAuthorizedList",posInfo.getPreAutorized(),request,out);%>
				<% 	String sel="";
		   			if(  posInfo.getPreAutorized().equals("2") ) sel="SELECTED" ;%>
            	<option value="2" <%=sel%>>�Զ��彻��Ȩ��</option>
				</select>
			</td>	
      		<td width="20%" nowrap height="19" align="right">�豸���ͣ�</td>
      		<td width="30%" nowrap height="19">
      			<select name="equip_type" size="1" onChange=javascript:onSelChange()>
        		<option value="">��ѡ��</option>
				<% initList("equitTypeList",posInfo.getEquiptType(),request,out); %>
      			</select>
      		</td>
     	</tr>
     	<tr>
      		<td colspan="4" nowrap>
       			<hr noshade>
      		</td>
    	</tr>
	</table>
   	<table width="82%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  		<tr bgcolor="#666666">
    		<td width="28%">
      			<div align="center">��������</div>
    		</td>
    		<td width="18%">
      			<div align="center">�����������</div>
    		</td>
    		<td width="18%">
      			<div align="center">�������������</div>
    		</td>
    		<td width="18%">
      			<div align="center">���������</div>
    		</td>
    		<td width="18%">
      			<div align="center">�����������</div>
    		</td>
  		</tr>
		<%
			Vector v= (Vector) request.getAttribute("txnSetList");
    		if( v!= null ){
      			for( int i=0 ; i< v.size() ; i++ ){
        			POSTxnInfo pti = (POSTxnInfo) v.get(i);
		%>
  		<tr bgcolor="#FFFFFF" align="center">
    		<td width="28%"><%=pti.getTxnName()%></td>
    		<td width="18%"><input type="checkbox" name="txnPermR" style="width:20px" value="<%=pti.getLocation()%>"  <%=(posInfo.getEquiptType().equals("3")||posInfo.getEquiptType().equals("1"))?"":"DISABLED"%> <%=((pti.getTxnPerm()=='1')||(pti.getTxnPerm()=='3'))?"CHECKED":""%> />  </td>
    		<td width="18%"><input type="checkbox" name="handFlagR" style="width:20px" value="<%=pti.getLocation()%>" <%=(posInfo.getEquiptType().equals("3")||posInfo.getEquiptType().equals("1"))?"":"DISABLED"%> <%=((pti.getHandPerm()=='1')||(pti.getHandPerm()=='3'))?"CHECKED":""%> />  </td>
    		<td width="18%"><input type="checkbox" name="txnPermF" style="width:20px" value="<%=pti.getLocation()%>"  <%=((posInfo.getEquiptType().equals("3")||posInfo.getEquiptType().equals("2"))&&posInfo.getMemo1a().equals("0"))?"":"DISABLED"%> <%=((pti.getTxnPerm()=='2')||(pti.getTxnPerm()=='3'))?"CHECKED":""%> />    </td>
    		<td width="18%"><input type="checkbox" name="handFlagF" style="width:20px" value="<%=pti.getLocation()%>" <%=((posInfo.getEquiptType().equals("3")||posInfo.getEquiptType().equals("2"))&&posInfo.getMemo1a().equals("0"))?"":"DISABLED"%> <%=((pti.getHandPerm()=='2')||(pti.getHandPerm()=='3'))?"CHECKED":""%> />   </td>
  		</tr>
		<%
      			}
    		}
		%>
	</table>
  	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td colspan="5" nowrap>
        	<!--a href="javascript:doSubmit()">[ȷ��]</a>
		  	<a href="javascript:formReg.reset()">[����]</a>
          	<a href="javascript:location.href='/ToucsMonitor/devicemanage'">[ȡ��]</a-->
        	<div align="right">
          		<input type="submit" name="submit" value="�ύ" class="inputbutton">
          		<input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>