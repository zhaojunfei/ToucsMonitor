<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!--jsp:useBean	id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/-->
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int	i=0;i<list.size();i++){
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
}%>

<html>
<head>
<title>�齱��Ϣ��ѯ</title>
<meta http-equiv="Content-Type"	content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
<style type="text/css">
<!--
.style3	{color:	#0000FF}
-->
</style>
</head>
<script	language="javascript" src="/ToucsMonitor/ToucsMonitor/js/power.js">
</script>

<body  bgcolor="#FFFFFF" text="#000000">
<span class="style3">
<%
	String uid = request.getParameter("uid");
	POSMerchantBean PMB = new POSMerchantBean();
	Vector cc = PMB.getCardClass();
	Vector ct = PMB.getCardType();
	Hashtable Res = null;
	int i = 0;

	Vector pi = (Vector)request.getAttribute("PrizeInfo");
	PrizeRule pr = (PrizeRule)request.getAttribute("PrizeRule");
	System.out.println("ҳ����յ��Ĳ�����:"+pr);
%>
</span>

<h2 align="center" class="style3"><font	face="����">�齱�����ѯ</font></h2>
<form action="/ToucsMonitor/posmerchantconfig?reqCode=10422&target=checkPZAdded&uid=<%=uid%>" method="post" name="checkpztable"	class="style3">
<input type="hidden" name="managebankname_temp"	value="">
	<table width="80%" align="center" bgcolor="#FFFFFF" id="tab0">
    	<tr>
      		<td colspan="6" nowrap><hr noshade></td>
    	</tr>
    	<tr>
      		<td align="center" nowrap colspan=2>������</td>
      		<td align="center" nowrap colspan=2>������</td>
    	</tr>
    	<tr	valign="top">
      		<td colspan=2 align="center">
        	<select name="CardType"  size="12" multiple style="width:160pt;height:80pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
          	<%
				i = 0;
				String bct = Long.toString(Long.parseLong(pr.getBit_cardtype(), 16), 2);
				while(bct.length() < 64)
					bct = " " + bct;
				while (i<ct.size()) {
					Res = (Hashtable)ct.get(i);
					if (bct.charAt(Integer.parseInt((String)Res.get("sys_code"), 16)) == '1') {
		 	%>
          	<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
          	<%
		 		}
				i ++;
			 }
		 	%>
        	</select>
			</td>
      		<td colspan=2 align="center">
	    	<select name="CardClass" size="12" multiple style="width:160pt;height:80pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
        	<%
				i = 0;
				String bcc = Long.toString(Long.parseLong(pr.getBit_cardclass(), 16), 2);
				while(bcc.length() < 64)
					bcc = " " + bcc;
				while (i<ct.size()) {
					Res = (Hashtable)cc.get(i);
					if (bcc.charAt(Integer.parseInt((String)Res.get("sys_code"), 10)) == '1') {
		 	%>
				<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
         	<%
		 		}
				i ++;
			 }
		 	%>
        	</select>
      		</td>
    	</tr>
    	<tr	class="style3">
      		<td width="20%" align="right" nowrap>��ʼ����:</td>
      		<td width="30%" nowrap><input type="text" name="PZbegin_date" class="readonly" readonly value="<%=pr.getBegin_date()%>" maxlength="8" size="12"></td>
      		<td width="20%" align="right" nowrap>��������:</td>
      		<td width="30%" nowrap><input type="text" name="PZend_date" class="readonly" readonly value="<%=pr.getEnd_date()%>" maxlength="8" size="12"></td>
    	</tr>
    	<tr	class="style3">
      		<td align="right"	nowrap>��ʼʱ��:</td>
      		<td nowrap><input type="text" name="PZbegin_time" class="readonly" readonly value="<%=pr.getBegin_time()%>" maxlength="6" size="10"></td>
      		<td align="right" nowrap>����ʱ��:</td>
      		<td nowrap><input type="text" name="PZend_time" class="readonly" readonly value="<%=pr.getEnd_time()%>" maxlength="6" size="10"></td>
    	</tr>
    	<tr	class="style3">
      		<td align="right"	nowrap><div align="right">������ѽ��:</div></td>
      		<td nowrap><input type="text" name="PZ_theMinMoney" class="readonly" readonly value="<%=pr.getLowest_amt()%>" maxlength="10" size="10"></td>
	  		<td colspan=2 align="right" nowrap>&nbsp;</td>
    	</tr>
		<tr>
		<%
			if (pr.getBusinessman_flag().equals("0")) {
		%>
			<td colspan="4" align="left">ȫ���̻���ͳһ����</td>
		<%
			} else if (pr.getPrizelevel_flag().equals("0")) {
		%>
	  		<td colspan="4" align="left">ָ���̻���ͳһ����(��һ���̻�ָ���Ľ���Ϊ�ܽ���)</td>
		<%
			} else {
		%>
			<td colspan="4" align="left">ָ���̻���ָ������</td>
		<%
			}
		%>
		</tr>
		<tr>
			<td colspan="4">
			<table id="MctList" style="" width="100%">
				<tr>
					<td>
			  			<div align="center">
						<jsp:include page="PrizeMctList.jsp" flush="true"/>
			  			</div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right"><input type="button" name="cancel" value="����" class="inputbutton"  onClick="history.go(-1);"></td>
		</tr>
  	</table>
</form>
</body>
</html>
