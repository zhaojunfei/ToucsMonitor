<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<html>
<head>
	<title>�⿨�̻���Ϣ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<body bgcolor="#CCCCCC" text="#000000">	
<%
	String uid=request.getParameter("uid");			
	POSFCardMerchantInfo FCPosMerchant = (POSFCardMerchantInfo)request.getAttribute("PosMercant");
	if (FCPosMerchant == null)
		FCPosMerchant = new POSFCardMerchantInfo();
	String open_flag = FCPosMerchant.getOpen_flag();
%>
<h2><font face="����">�⿨POS�̻���Ϣ</font></h2>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
    	<tr bgcolor="#cccccc">
      		<td colspan=4 nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="15%" nowrap align="right" bgcolor="#999999">�����̻���ţ�</td>
      		<td width="35%" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id()%></td>
      		<td width="15%" nowrap align="right" bgcolor="#999999"> �̻��������ƣ� </td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_name()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�̻�Ӣ�����ƣ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_eg()%></td>
      		<td nowrap align="right" bgcolor="#999999">���ڳ���(ƴ��)��</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_city()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�̻���ַ��</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_addr()%></td>
      		<td nowrap align="right" bgcolor="#999999">�ʱࣺ</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_zip()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�绰��</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_tel()%></td>
      		<td nowrap align="right" bgcolor="#999999">��˾��վ��ַ��</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_url()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">���˴���</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getManager_a()%></td>
      		<td nowrap align="right" bgcolor="#999999">���˴���绰��</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTel_a()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�����ˣ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getManager_b()%></td>
      		<td nowrap align="right" bgcolor="#999999">�����˵绰��</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTel_b()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">��չ�кţ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getDev_bankno()%></td>
      		<td nowrap align="right" bgcolor="#999999">��Ͻ�кţ�</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMan_bankno()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�ն������루TCC����</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTcc()%></td>
      		<td nowrap align="right" bgcolor="#999999">�̻�״̬��</td>
      		<td  nowrap bgcolor="#FFFFFF">
			<%=FCPosMerchant.getMer_state().equals("0")?"�����̻�":""%>
			<%=FCPosMerchant.getMer_state().equals("1")?"�����̻�":""%>
			<%=FCPosMerchant.getMer_state().equals("2")?"����̻�":""%>
			<%=FCPosMerchant.getMer_state().equals("7")?"���տ���ͣ���̻�":""%>
			<%=FCPosMerchant.getMer_state().equals("9")?"�˹�ͣ���̻�":""%>
	  		</td>
    	</tr>  
    </table>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
		<tr>
      		<td colspan="7" align="left" nowrap bgcolor="#999999">�⿨��ͨ��־��</td>
		</tr>
    	<tr>
      		<td nowrap bgcolor="#999999" width="10%">
        		<input name="open_flag1" type="checkbox"  class="radio" value="" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"checked":""%>> Visa 
        	</td>
      		<td rowspan="2" align="right" nowrap bgcolor="#999999" width="15%">MCC��</td>
      		<td rowspan="2" nowrap bgcolor="#FFFFFF" width="25%"><%=FCPosMerchant.getMcc()%></td>
      		<td nowrap bgcolor="#999999" width="15%">�ؿ���Visa</td>
      		<td nowrap bgcolor="#FFFFFF" width="10%"><%=FCPosMerchant.getRate_visa()%></td>
      		<td nowrap bgcolor="#999999" width="15%">������Visa</td>
      		<td nowrap bgcolor="#FFFFFF" width="10%"><%=FCPosMerchant.getRate_visa2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag2" type="checkbox"  class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"checked":""%>>  Master 
      		</td>
      		<td nowrap bgcolor="#999999">�ؿ���Master</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_master()%></td>
      		<td nowrap bgcolor="#999999">������Master</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_master2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag3" type="checkbox"  class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"checked":""%>> AE 
      		</td>
      		<td nowrap align="right" bgcolor="#999999">Ĭ��AE�̻��ţ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_ae()%></td>
      		<td nowrap bgcolor="#999999">�ؿ���AE</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_ae()%></td>
      		<td nowrap bgcolor="#999999">������AE</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_ae2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag4" type="checkbox"   class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"checked":""%>>Dinner
      		</td>
      		<td nowrap align="right" bgcolor="#999999">Ĭ��Dinner�̻��ţ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_dinner()%></td>
      		<td nowrap bgcolor="#999999">�ؿ���Dinner</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_dinner()%></td>
      		<td nowrap bgcolor="#999999">������Dinner</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_dinner2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag5" type="checkbox"   class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"checked":""%>>JCB
      		</td>
      		<td nowrap align="right" bgcolor="#999999">Ĭ��JCB�̻��ţ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_jcb()%></td>
      		<td nowrap bgcolor="#999999">�ؿ���JCB</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_jcb()%></td>
      		<td nowrap bgcolor="#999999">������JCB</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_jcb2()%></td>
    	</tr>
	</table>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
   	 	<tr>
      		<td width="15%" align="right" nowrap bgcolor="#999999">Ĭ�ϻ����˺ţ�</td>
      		<td width="35%" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_no()%></td>
      		<td width="15%" align="right" nowrap bgcolor="#999999">Ĭ�ϻ����˺ſ����У�</td>
      		<td width="35%" colspan="3" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_bankno()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">Ĭ�ϻ����˻����ƣ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_name()%></td>
      		<td nowrap align="right" bgcolor="#999999">���ʵ���ַ��</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_addr()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">���ʵ��ʱࣺ</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_zip()%></td>
      		<td nowrap align="right" bgcolor="#999999">���ʵ��ռ��ˣ�</td>
     		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_rcv()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">���ʵ�email��</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_email()%></td>
      		<td nowrap align="right" bgcolor="#999999">���ʵ����棺</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_fax()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">�̻�����</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_level()%></td>
      		<td nowrap align="right" bgcolor="#999999">�ʽ�������ڣ�</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getSettle_date()%></td>
    	</tr>
  	</table>
  	<table width="90%">
    	<tr> 
      		<td  nowrap> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td  nowrap> 
        		<div align="right"> 
          			<input type="button" name="cancel1" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
        		</div>
      		</td>
   	 	</tr>
  	</table>
<p/>
</body>
</html>