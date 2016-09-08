<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<html>
<head>
	<title>外卡商户信息</title>
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
<h2><font face="隶书">外卡POS商户信息</font></h2>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
    	<tr bgcolor="#cccccc">
      		<td colspan=4 nowrap>
        		<hr noshade>
      		</td>
    	</tr>
    	<tr>
      		<td width="15%" nowrap align="right" bgcolor="#999999">建行商户编号：</td>
      		<td width="35%" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id()%></td>
      		<td width="15%" nowrap align="right" bgcolor="#999999"> 商户中文名称： </td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_name()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">商户英文名称：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_eg()%></td>
      		<td nowrap align="right" bgcolor="#999999">所在城市(拼音)：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_city()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">商户地址：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_addr()%></td>
      		<td nowrap align="right" bgcolor="#999999">邮编：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_zip()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">电话：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_tel()%></td>
      		<td nowrap align="right" bgcolor="#999999">公司网站地址：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_url()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">法人代表：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getManager_a()%></td>
      		<td nowrap align="right" bgcolor="#999999">法人代表电话：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTel_a()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">经办人：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getManager_b()%></td>
      		<td nowrap align="right" bgcolor="#999999">经办人电话：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTel_b()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">发展行号：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getDev_bankno()%></td>
      		<td nowrap align="right" bgcolor="#999999">管辖行号：</td>
      		<td  nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMan_bankno()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">终端类型码（TCC）：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getTcc()%></td>
      		<td nowrap align="right" bgcolor="#999999">商户状态：</td>
      		<td  nowrap bgcolor="#FFFFFF">
			<%=FCPosMerchant.getMer_state().equals("0")?"正常商户":""%>
			<%=FCPosMerchant.getMer_state().equals("1")?"测试商户":""%>
			<%=FCPosMerchant.getMer_state().equals("2")?"监控商户":""%>
			<%=FCPosMerchant.getMer_state().equals("7")?"风险控制停用商户":""%>
			<%=FCPosMerchant.getMer_state().equals("9")?"人工停用商户":""%>
	  		</td>
    	</tr>  
    </table>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
		<tr>
      		<td colspan="7" align="left" nowrap bgcolor="#999999">外卡开通标志：</td>
		</tr>
    	<tr>
      		<td nowrap bgcolor="#999999" width="10%">
        		<input name="open_flag1" type="checkbox"  class="radio" value="" <%=(FCPosMerchant.getOpen_flag().length()>0 && FCPosMerchant.getOpen_flag().charAt(0)=='1')?"checked":""%>> Visa 
        	</td>
      		<td rowspan="2" align="right" nowrap bgcolor="#999999" width="15%">MCC：</td>
      		<td rowspan="2" nowrap bgcolor="#FFFFFF" width="25%"><%=FCPosMerchant.getMcc()%></td>
      		<td nowrap bgcolor="#999999" width="15%">回扣率Visa</td>
      		<td nowrap bgcolor="#FFFFFF" width="10%"><%=FCPosMerchant.getRate_visa()%></td>
      		<td nowrap bgcolor="#999999" width="15%">返回率Visa</td>
      		<td nowrap bgcolor="#FFFFFF" width="10%"><%=FCPosMerchant.getRate_visa2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag2" type="checkbox"  class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>1 && FCPosMerchant.getOpen_flag().charAt(1)=='1')?"checked":""%>>  Master 
      		</td>
      		<td nowrap bgcolor="#999999">回扣率Master</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_master()%></td>
      		<td nowrap bgcolor="#999999">返回率Master</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_master2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag3" type="checkbox"  class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>2 && FCPosMerchant.getOpen_flag().charAt(2)=='1')?"checked":""%>> AE 
      		</td>
      		<td nowrap align="right" bgcolor="#999999">默认AE商户号：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_ae()%></td>
      		<td nowrap bgcolor="#999999">回扣率AE</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_ae()%></td>
      		<td nowrap bgcolor="#999999">返回率AE</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_ae2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag4" type="checkbox"   class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>3 && FCPosMerchant.getOpen_flag().charAt(3)=='1')?"checked":""%>>Dinner
      		</td>
      		<td nowrap align="right" bgcolor="#999999">默认Dinner商户号：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_dinner()%></td>
      		<td nowrap bgcolor="#999999">回扣率Dinner</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_dinner()%></td>
      		<td nowrap bgcolor="#999999">返回率Dinner</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_dinner2()%></td>
    	</tr>
    	<tr>
      		<td nowrap bgcolor="#999999">
      			<input name="open_flag5" type="checkbox"   class="radio" value=""  <%=(FCPosMerchant.getOpen_flag().length()>4 && FCPosMerchant.getOpen_flag().charAt(4)=='1')?"checked":""%>>JCB
      		</td>
      		<td nowrap align="right" bgcolor="#999999">默认JCB商户号：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_id_jcb()%></td>
      		<td nowrap bgcolor="#999999">回扣率JCB</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_jcb()%></td>
      		<td nowrap bgcolor="#999999">返回率JCB</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getRate_jcb2()%></td>
    	</tr>
	</table>
  	<table width="90%" border="0" cellpadding="1" cellspacing="1" bgcolor="#333333">
   	 	<tr>
      		<td width="15%" align="right" nowrap bgcolor="#999999">默认划款账号：</td>
      		<td width="35%" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_no()%></td>
      		<td width="15%" align="right" nowrap bgcolor="#999999">默认划款账号开户行：</td>
      		<td width="35%" colspan="3" nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_bankno()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">默认划款账户名称：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getAcct_name()%></td>
      		<td nowrap align="right" bgcolor="#999999">对帐单地址：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_addr()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">对帐单邮编：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_zip()%></td>
      		<td nowrap align="right" bgcolor="#999999">对帐单收件人：</td>
     		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_rcv()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">对帐单email：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_email()%></td>
      		<td nowrap align="right" bgcolor="#999999">对帐单传真：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getBill_ck_fax()%></td>
    	</tr>
    	<tr>
      		<td nowrap align="right" bgcolor="#999999">商户级别：</td>
      		<td nowrap bgcolor="#FFFFFF"><%=FCPosMerchant.getMer_level()%></td>
      		<td nowrap align="right" bgcolor="#999999">资金结算日期：</td>
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
          			<input type="button" name="cancel1" value="确定" class="inputbutton" onClick="javascript:history.go(-1)">
        		</div>
      		</td>
   	 	</tr>
  	</table>
<p/>
</body>
</html>