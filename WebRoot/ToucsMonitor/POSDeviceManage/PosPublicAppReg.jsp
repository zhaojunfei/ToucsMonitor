<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*"%>
<%@ page import="com.adtec.toucs.monitor.common.toucsString"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>


<jsp:useBean id="POSPublicAppBean" class="com.adtec.toucs.monitor.POSDeviceManage.POSPublicAppBean" scope="request" />
<!--jsp:useBean id="posEnroll" class="com.adtec.toucs.monitor.POSDeviceManage.POSEnrollInfo"  scope="request"/-->
<%
	POSPublicAppBean posEnroll = (POSPublicAppBean) request.getAttribute("posEnroll");
	System.out.println("RRRRRRRRRRRRRRRRR");
	if (posEnroll == null) {
		System.out.println("RRRRRRRRRRRRRRRRR");
		posEnroll = new POSPublicAppBean();
	}
%>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	String date = formatter.format(new Date());
%>

<html>
<head>
	<title>EMV����������</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">
function doSubmit(){

	if( isNull("aid","AID")) return false;
	if (document.forms[0].aid.value.length <= 9 || document.forms[0].aid.value.length >= 33){
		alert("AID������10λ��32λ֮��");
		return false;
	}	
	
	if( isNull("asi","Ӧ��ѡ��ָʾ��")) return false;	
	if (document.forms[0].asi.value.length != 2){
		alert("Ӧ��ѡ��ָʾ������Ϊ2λ");
		return false;
	}	
	
	if( isNull("appedition","Ӧ�ð汾��")) return false;
	if (document.forms[0].appedition.value.length != 4){
		alert("Ӧ�ð汾�ű���Ϊ4λ");
		return false;
	}	
	
	if( isNull("tac_default","TAC-ȱʡ")) return false;
	if (document.forms[0].tac_default.value.length != 10){
		alert("TAC-ȱʡ����Ϊ10λ");
		return false;
	}	
	
	if( isNull("tac_online","TAC-����")) return false;	
	if (document.forms[0].tac_online.value.length != 10){
		alert("TAC-��������Ϊ10λ");
		return false;
	}	
	
	if( isNull("tac_refuse","TAC-�ܾ�")) return false;
	if (document.forms[0].tac_refuse.value.length != 10){
		alert("TAC-�ܾ�����Ϊ10λ");
		return false;
	}	
	
	if( isNull("floorlimit","�ն�����޶�")) return false;
	if (document.forms[0].floorlimit.value.length != 8){
		alert("�ն�����޶����Ϊ8λ");
		return false;
	}	
	
	if( isNull("cdoor","ƫ�����ѡ��ķ�ֵ")) return false;
	if (document.forms[0].cdoor.value.length != 8){
		alert("ƫ�����ѡ��ķ�ֵ����Ϊ8λ");
		return false;
	}	
	
	if( isNull("maxpercent","ƫ�����ѡ������Ŀ��ٷ���")) return false;
	if (document.forms[0].maxpercent.value.length != 2){
		alert("ƫ�����ѡ������Ŀ��ٷ�������Ϊ2λ");
		return false;
	}	
	
	if( isNull("percent","���ѡ���Ŀ��ٷ���")) return false;
	if (document.forms[0].percent.value.length != 2){
		alert("���ѡ���Ŀ��ٷ�������Ϊ2λ");
		return false;
	}	
	
	if( isNull("ddol","ȱʡDDOL")) return false;
	
	if( isNull("pinability","�ն�����PIN֧������")) return false;
	if (document.forms[0].pinability.value.length != 2){
		alert("�ն�����PIN֧����������Ϊ2λ");
		return false;
	}	

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}

</script>

<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid = request.getParameter("uid");
%>
<form name="formReg" method="post" action="/ToucsMonitor/pospublicappconfig?reqCode=13901&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
<font face="����" size="+2">EMV����������</font>
	<table id="tab0" width="90%" height="313">
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">AID��</td>
			<td height="19" colspan="3" nowrap>
				<input type="text" name="aid" class="wideinput" value="" MAXLENGTH="32" size="32">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">Ӧ��ѡ��ָʾ����</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="asi" class="wideinput" value="" maxlength="2" size="2">*
			</td>
			<td width="30%" nowrap height="19" align="right">Ӧ�ð汾�ţ�</td>
			<td nowrap height="19">
				<input type="text" name="appedition" value="" MAXLENGTH="4" size="4">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">TAC-ȱʡ��</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="tac_default" value="" MAXLENGTH="10" size="10">*
			</td>
			<td width="30%" nowrap height="19" align="right">TAC-������</td>
			<td colspan="3" nowrap height="19">
				<input type="text" name="tac_online" value="" MAXLENGTH="10" size="10">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">TAC-�ܾ���</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="tac_refuse" value="" MAXLENGTH="10" size="10">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">�ն�����޶</td>
			<td width="30%" height="19" nowrap>
				<input type="text" name="floorlimit" class="wideinput" value="" MAXLENGTH="8" size="8">*
			</td>
			<td width="30%" nowrap height="19" align="right">ƫ�����ѡ��ķ�ֵ��</td>
			<td nowrap height="19"  colspan="3">
				<input type="text" name="cdoor" value="" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">ƫ�����ѡ������Ŀ��ٷ�����</td>
			<td width="30%" nowrap height="19">
				<input type="text" name="maxpercent" value="" MAXLENGTH="2" size="2">*
			</td>
			<td width="30%" nowrap height="19" align="right">���ѡ���Ŀ��ٷ�����</td>
			<td nowrap height="19"  colspan="3">
				<input type="text" name="percent" value="" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">ȱʡDDOL��</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="ddol" value="" MAXLENGTH="8" size="8">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">�ն�����PIN֧��������</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="pinability" value="" MAXLENGTH="2" size="2">*
			</td>
		</tr>
		<tr>
			<td valign="top">
				<br>
			</td>
			<td colspan="3" valign="top">
				<br>
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">�������ڣ�</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="create_date" value=<%out.print(date);%> READONLY style="background-color:#cccccc" MAXLENGTH="14" size="14">*
			</td>
		</tr>
		<tr>
			<td width="30%" nowrap height="19" align="right">��ע1��</td>
			<td colspan="5" nowrap height="19">
				<input type="text" name="memo1" value="" MAXLENGTH="32" size="32">
			</td>
		</tr>
		<tr>
			<td colspan="4" nowrap>
				<hr noshade>
			</td>
		</tr>
	</table>
	<table width="89%">
		<tr>
			<td colspan="5" nowrap>
				<div align="right">
					<input type="submit" name="submitbutton" value="�ύ" class="inputbutton">
					<input type="reset" name="reset" value="����" class="inputbutton">
					<%--<input type="button" name="cancelbutton" value="ȡ��" class="inputbutton"  onClick="javascript:location.href='/ToucsMonitor/POSDeviceManage/PosAccountManage.jsp'">--%>
				</div>
			</td>
		</tr>
	</table>
	<p />
</form>
</body>
</html>