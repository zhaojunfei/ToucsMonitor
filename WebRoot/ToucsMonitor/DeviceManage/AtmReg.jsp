<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="atmInfo" class="com.adtec.toucs.monitor.devicemanage.ATMInfo"  scope="request"/>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else flag="";
			try{
				out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
					+code.getCodeDesc()+"</option>");
			}catch(IOException exp){
				exp.printStackTrace();
				System.out.println(exp.getMessage());
			}
		}
	}
}%>

<html>
<head>
	<title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">

function orgChange(idx){
	formReg.txOrgId.value=formReg.orgId.value;
	formReg.branchs.selectedIndex=idx;
	formReg.agencyNo.value=formReg.branchs.options[idx].text;
}

function doSubmit(){

	if(!onDeviceCode("A5110","atmCode")) return false;
	if( isNull("atmCCode","ATM-C���")) return false;
	if( isNull("atmDccCode","ATM-DCC���")) return false;
        if( isNull("atmLevel","�豸��Ҫ��")) return false;
	if( isNull("orgId","�豸��������")) return false;
	if( isNull("areaNo","��װ�ص���������")) return false;
	if( isNull("atmType","�豸����")) return false;
	if( isNull("atmSn","�豸��ţ�SN��")) return false;
	if( isNull("atmTn","�豸���ٺ�")) return false;
	if( isNull("cardType","���׿���")) return false;
	if( isNull("currencyCode","����")) return false;
	if( isNull("translateName","���Ĵ����׼")) return false;
	if( isNull("msgType","��������")) return false;
	if( isNull("netType","ͨѶЭ������")) return false;
	if( isNull("tellerId","��Ա��")) return false;
	if( isNull("netAddress","�ն�IP��ATM����ͨѶ��ַ��")) return false;

	//�������
	if(!validFullDate("atmSetDate","����װ����"))	return false;
	if(!validFullDate("progSetDate","���Ӱ�װ����")) return false;
	if(!validFullDate("connectDate","��ͨ����")) 	return false;
	//�������
	if(!isInteger("jumpNums","������") ) return false;

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
</script>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String postCode="";
	String keyFlag="";
	String editTxtFlag="";
	
	switch(atmInfo.oper){
		case 1:
			postCode="13001";
			break;
		case 2:
			postCode="13002";	
			break;
		default:
	editTxtFlag=" READONLY style.backgroundcolor=#CCCCCC";		
	}
  %>
<h3><font face="����">ATM�豸������Ϣ</font></h3>
<form name="formReg" method="post" action="/ToucsMonitor/deviceconfig?reqCode=<%=postCode%>&content=base&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<input type="hidden"  name="key" value="<%=atmInfo.getAtmCode()%>">
<input type="hidden" name="useFlag" value="<%=atmInfo.getUseFlag()%>">
	<table id="tab0" width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right" >���(������)��</td>
      		<td width="30%" nowrap>
        	<%if( atmInfo.getAtmCode().trim() == null||atmInfo.getAtmCode().equalsIgnoreCase("") ){%>
            	<input type="text" name="atmCode" value="A5110" <%=keyFlag%>  class="wideinput" MAXLENGTH="9" size="20">*
        	<%}else{%>
            	<input type="text" name="atmCode" value="<%=atmInfo.getAtmCode()%>" <%=keyFlag%>  class="wideinput" MAXLENGTH="9" size="20">*
        	<%}%>
      		</td>
      		<td width="20%" nowrap align="right">ATM��DCC�����ţ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmDccCode" value="<%=atmInfo.getAtmDccCode()%>" class="wideinput" size="20" MAXLENGTH="20">* </td>
    	</tr>
    	<tr>
    		<td width="20%" nowrap align="right" >ATM��C�����ţ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmCCode" value="<%=atmInfo.getAtmCCode()%>" class="wideinput" size="20" MAXLENGTH="20">*
      		</td>
      		<td width="20%" nowrap align="right">��Ҫ�ȣ�</td>
      		<td width="30%" nowrap>
        		<select name="atmLevel" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("atmLevelList",atmInfo.getAtmLevel(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">���ƣ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmName" value="<%=atmInfo.getAtmName()%>" <%=editTxtFlag%> class="wideinput" size="20" MAXLENGTH="20">
      		</td>
      		<td width="20%" nowrap align="right"> ��װ�ص����</td>
      		<td width="30%" nowrap>
        		<select name="atmAddrType" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("addrTypeList",atmInfo.getAtmAddrType(),request,out);%>
        		</select>
        		<select name="branchs" size="1" DISABLED style="display:none">
		  		<option value="">��ѡ��</option>
          		<%
		  		//����֧�б���б�
		  			String[] branchs=(String[])request.getAttribute("branchs");
					if(branchs!=null){
					for(int i=0;i<branchs.length;i++){%>
          			<option><%=branchs[i]%></option>
          		<%}
					}
		  		%>
        		</select>
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">��װ��λ��</td>
      		<td width="30%" >
        		<input type="text" name="atmSetUnit" class="wideinput" value="<%=atmInfo.getAtmSetUnit()%>" <%=editTxtFlag%> size="20" MAXLENGTH="20">
      		</td>
      		<td width="20%" nowrap align="right">����λ��
      		</td>
      		<td width="30%">
        		<input type="text" name="atmMngUnit" class="wideinput" value="<%=atmInfo.getAtmMngUnit()%>" <%=editTxtFlag%> size="20" MAXLENGTH="20">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">����Ա������
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="mngName" value="<%=atmInfo.getMngName()%>" <%=editTxtFlag%> class="wideinput" size="20" MAXLENGTH="9" >
      		</td>
      		<td width="20%" nowrap align="right">�绰��
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="mngPhone" value="<%=atmInfo.getMngPhone()%>" class="wideinput" size="20" maxlength="20">
      		</td>
    	</tr>
    	<tr>
     		<td width="20%" nowrap align="right">����������
      		</td>
      		<td width="30%" nowrap>
          		<select name="orgId" size="1" onChange="javascript:orgChange(orgId.selectedIndex)">
            	<option value="">��ѡ��</option>
            	<%initList("orgList",atmInfo.getOrgId(),request,out);%>
          		</select>*
      		</td>
      		<td width="20%" nowrap align="right">�������أ�</td>
      		<td width="30%" nowrap>
        		<select name="areaNo" size="1">
         	 	<option value="">��ѡ��</option>
          		<%initList("areaList",atmInfo.getAreaNo(),request,out);%>
        		</select>*
      		</td>
    	</tr>
     	<tr>
      		<td width="20%" nowrap align="right">��ϸ��ַ��
      		</td>
      		<td colspan="3">
        		<input type="text" name="atmSetAddr" class="wideinput" value="<%=atmInfo.getAtmSetAddr()%>" <%=editTxtFlag%> size="20" maxlength="40">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> ����װ���ڣ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSetDate" value="<%=atmInfo.getAtmSetDate()%>" <%=editTxtFlag%>  size="20" maxlength="8">(YYYYMMDD)</td>
      		<td width="20%" nowrap align="right"> ���Ӱ�װ���ڣ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="progSetDate" value="<%=atmInfo.getProgSetDate()%>" size="20"  maxlength="8" >(YYYYMMDD)</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> ��ͨ���ڣ�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="connectDate" value="<%=atmInfo.getConnectDate()%>" <%=editTxtFlag%> size="20"  maxlength="8" >(YYYYMMDD)</td>
      		<td width="20%" nowrap align="right">
      		</td>
      		<td width="30%" nowrap>&nbsp; </td>
    	</tr>
  	</table>
  	<table width="80%" id="tab1">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">����(Ʒ��)��
      		</td>
      		<td width="30%" nowrap>
        		<select name="atmType" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("atmTypeList",atmInfo.getAtmType(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap align="right">�ͺţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmMode" value="<%=atmInfo.getAtmMode()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">SN��
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSn" value="<%=atmInfo.getAtmSn()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="12">*
      		</td>
      		<td width="20%" nowrap align="right">TN��
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmTn" value="<%=atmInfo.getAtmTn()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">*
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">���Σ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="atmSetDegree" value="<%=atmInfo.getAtmSetDegree()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="11">
      		</td>
      		<td width="20%" nowrap align="right">���׿��֣�
      		</td>
      		<td width="30%" nowrap>
        		<select name="cardType" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("cardTypeList",atmInfo.getCardType(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">���֣�</td>
      		<td width="30%" nowrap>
        		<select name="currencyCode" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("currencyTypeList",atmInfo.getCurrencyCode(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap align="right">���������ͣ�</td>
      		<td width="30%" nowrap>
       		 	<select name="cardReaderMode" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("readerModeList",atmInfo.getCardReaderMode(),request,out);%>
        		</select>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">���Ĵ����׼��</td>
      		<td width="30%" nowrap>
        		<input type="text" name="translateName" value="<%=atmInfo.getTranslateName()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="10">*
      		</td>
      		<td width="20%" nowrap align="right">�������ͣ�</td>
      		<td width="30%" nowrap>
        		<select name="msgType" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("msgTypeList",atmInfo.getMsgType(),request,out);%>
        		</select>*
        	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">ͨѶЭ�����ͣ�</td>
      		<td width="30%" nowrap>
        		<select name="netType" size="1">
          		<option value="">��ѡ��</option>
          		<%initList("netTypeList",atmInfo.getNetType(),request,out);%>
        		</select>*
      		</td>
      		<td width="20%" nowrap>��</td>
      		<td width="30%" nowrap>&nbsp; </td>
    	</tr>
  	</table>
  	<table width="80%" id="tab2">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">�����У�</td>
      		<td width="30%" nowrap>
        		<input type="text" name="unionName" value="<%=atmInfo.getUnionName()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="40">
      		</td>
      		<td width="20%" nowrap align="right">���ţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="branchNo" value="<%=atmInfo.getBranchNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="4">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">�����ţ�
	      	</td>
	      	<td width="30%" nowrap>
	        	<input type="text" name="changeNo" value="<%=atmInfo.getChangeNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="4">
	      	</td>
	      	<td width="20%" nowrap align="right">���кţ�
	      	</td>
	      	<td width="30%" nowrap>
	        	<input type="text" name="unionNo" value="<%=atmInfo.getUnionNo()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="6">
	      	</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">֧�кţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="agencyNo" value="<%=atmInfo.getAgencyNo()%>" READONLY  style="background-color:#CCCCCC" class=wideinput size="20">
      		</td>
      		<td width="20%" nowrap align="right">�����ţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" value="<%=atmInfo.getOrgId()%>" name="txOrgId" READONLY  style="background-color:#CCCCCC" class=wideinput size="20"  >
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> �ʻ��ţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="bankAcc" value="<%=atmInfo.getBankAcc()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="15">
      		</td>
      		<td width="20%" nowrap align="right">��Ա�ţ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="tellerId" value="<%=atmInfo.getTellerId()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="5">*
      		</td>
    	</tr>
  	</table>
  	<table width="80%" id="tab3">
    	<tr>
      		<td colspan="4" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right"> ����IP��</td>
      		<td width="30%" nowrap>
        		<input type="text" name="hostAddress" value="<%=atmInfo.getHostAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
      		<td width="20%" nowrap align="right">�����˿ڣ�
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="hostPort" value="<%=atmInfo.getHostPort()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="6">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">�ն�IP��
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="netAddress" value="<%=atmInfo.getNetAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">*
      		</td>
      		<td width="20%" nowrap align="right">�������룺
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="netMask" value="<%=atmInfo.getNetMask()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">·��IP��
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="routeAddress" value="<%=atmInfo.getRouteAddress()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="17">
      		</td>
      		<td width="20%" nowrap align="right">��������
      		</td>
      		<td width="30%" nowrap>
        		<input type="text" name="jumpNums" value="<%=atmInfo.getJumpNums()%>" <%=editTxtFlag%> class="wideinput" size="20"  maxlength="5">
      		</td>
    	</tr>
  	</table>
  	<table width="80%" id="tab4">
    	<tr>
      		<td colspan="2" nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<!--/table>
        <table width="75%"-->
    	<tr>
      		<td width="20%" nowrap align="right">��ע1��
      		</td>
      		<td width="80%">
        		<input type="text" name="memo1" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo1())%>" <%=editTxtFlag%>  size="20"  maxlength="50">
      		</td>
    	</tr>
   	 	<tr>
      		<td width="20%" nowrap align="right">��ע2��
      		</td>
      		<td width="80%">
        		<input type="text" name="memo2" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo2())%>" <%=editTxtFlag%> size="20" maxlength="50">
      		</td>
    	</tr>
    	<tr>
      		<td width="20%" nowrap align="right">��ע3��
      		</td>
      		<td width="80%">
        		<input type="text" name="memo3" class="wideinput" value = "<%=toucsString.toHtml(atmInfo.getMemo3())%>" <%=editTxtFlag%> size="20" maxlength="50">
      		</td>
    	</tr>
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
		  		<%if(atmInfo.oper==1||atmInfo.oper==2){%>
          		<input type="submit" name="submit" value="�ύ" class="inputbutton">
          		<input type="reset" name="reset" value="����" class="inputbutton">
		  		<%}%>
          		<input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?uid=<%=uid%>'">
        		</div>
      		</td>
    	</tr>
  	</table>
<p/>
</form>
</body>
</html>