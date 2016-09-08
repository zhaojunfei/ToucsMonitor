<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.toucsString" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<jsp:useBean id="posInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSInfo"  scope="request"/>




<%! private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
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
			}catch(IOException exp){}
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

function doSubmit(){
	if( !onPosCode("pos_code",true)) return false;
	if( isNull("pos_c_code","POS-C定义编号")) return false;
	if( isNull("pos_dcc_code","19位POS编号")) return false;
	if (document.forms[0].pos_dcc_code.value.length != 19)
	{
		alert("19位POS编号必须为19位" + document.forms[0].pos_dcc_code.value.length);
		return false;
	}
	if( isNull("merchant_id","商户")) return false;
	if( isNull("equip_type","设备类型")) return false;
	if( isNull("pre_authorized","预授权设置")) return false;
	if( isNull("rebate","本币回扣率")) return false;

	if( !isFNumber("rebate","回扣率")) return false;

	if(!validFullDate("fix_date","安装日期")) return false;
	
	if( isNull("memo1a","POS类型")) return false;
	if( isNull("memo1b", "连接方式")) return false;
	if( isNull("memo1c", "跨行退货标志")) return false;

	if(confirm("确认输入无误并提交吗？"))
		return true;
	else
		return false;
}
function onMctIDBlur(){
	var pos_dcc_code = document.formReg.pos_dcc_code.value;
	var merchant_id = "";
	if (pos_dcc_code.length == 19)
	{
		merchant_id = pos_dcc_code.substr(0, 15);
		document.formReg.merchant_id.value = merchant_id;
		document.formReg.action = document.formReg.action + "&target=querypage"
		document.formReg.submit();
	}
   return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
  			String uid=request.getParameter("uid");
%>

<form name="formReg" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=13102&content=base&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
  <input type="hidden"  name="key" value="<%=posInfo.getPoscode()%>">
<font face="隶书" size="+2">POS设备修改</font>

    <table id="tab0" width="90%">
     <select name="merchant_info" size="1" DISABLED style="display:none">
         <!-- <option value="">请选择</option>-->
          <%initList("PosMerchantList","",request,out);%>
    </select>

    <tr>
      <td width="125" nowrap align="right" >19位POS编号：</td>
      <td width="237" nowrap><input type="text" name="pos_dcc_code" class="readonly" readonly  style="background-color:#cccccc" value="<%=posInfo.getPosDccCode()%>" maxlength="19"  size="20" >
        </td>
      <td width="107" nowrap height="19" align="right">POS顺序号：</td>
      <td height="19" colspan="3" nowrap>
       <input type="hidden" name="pos_c_code" value="<%=posInfo.getPosCCode()%>">
       <input type="text" name="pos_code" class="readonly" readonly value="<%=posInfo.getPoscode()%>"  style="background-color:#cccccc" maxlength="9" size="20">
</td>
    </tr>
     <tr>
      <td width="125" nowrap align="right"> 商户编号：
      </td>
      <td width="237" nowrap>
       <%-- <select name="merchant_id" size="1">
        <option value="">请选择</option>
          <%initList("PosMerchantList",posInfo.getMerchantid(),request,out);%>
        </select> --%>
     <input type="text" class="readonly" readonly value=<%=posInfo.getMerchantid() %> name="merchant_id" style="background-color:#cccccc" size=20 MAXLENGTH=15 ></td>
      <td width="107" nowrap align="right"> 商户名称：
      </td>
      <td colspan="3" nowrap>
        <input type="text" class="readonly" value="<%=toucsString.unConvert(posInfo.getMct_name()) %>" style="background-color:#cccccc" name="merchant_name"  size=40 MAXLENGTH=40 >
      </td>
    </tr>
        <tr>
    <%{ String classStr ="";
    	if( posInfo.getPreAutorized().equals("2") ) classStr ="disabled"; %>

      <td width="125" nowrap height="19" align="right">设备类型：</td>
      <td width="237" nowrap height="19">
      	<select name="equip_type" size="1" <%=classStr %>>
        <option value="">请选择</option>
       	<%initList("equitTypeList",posInfo.getEquiptType(),request,out);%>
       	</select>*</td>
      <td width="107" nowrap height="19" align="right">预授权设置：</td>
      <td height="19" colspan="3" nowrap>
        <select name="pre_authorized" size="1" <%=classStr %>>
        <% if(  posInfo.getPreAutorized().equals("2") ) {%>
            <option value="<%=posInfo.getPreAutorized()%>" selected >自定义交易权限</option>
        <%} else { %>
        	<option value="">请选择</option>
		<%
			initList("preAuthorizedList",posInfo.getPreAutorized(),request,out);
        }%>
		</select>*
     <% } %>
    </td>
    </tr>

    <tr>

      <td width="125" nowrap align="right">本币回扣率：</td>
      <td width="237" nowrap>
        <input type="text" name="rebate" class="wideinput"  value="<%=posInfo.getRebate()%>" MAXLENGTH="10" size="20">*</td>
      <td width="107" nowrap align="right">外币回扣率：</td>
      <td colspan="3" nowrap>
         <input type="text" name="frn_rebate" class="wideinput"  value="<%=posInfo.getFrnRebate()%>" MAXLENGTH="10" size="20"></td>
      </tr>
    <tr>
      <td width="125" nowrap align="right">开户行名：</td>
      <td width="237" nowrap>
        <input type="text" name="open_bankno" class="wideinput"  value="<%=posInfo.getOpenbankno()%>" MAXLENGTH="50" size="20">
      </td>

      <td width="107" nowrap align="right">交换号：</td>
      <td colspan="3" nowrap>
        <input type="text" name="exg_no" class="wideinput"  value="<%=posInfo.getExgno()%>" MAXLENGTH="10" size="20">
      </td>
    </tr>
    <tr>
      <td width="125" nowrap align="right">账号：</td>
      <td width="237" nowrap>
        <input type="text" name="acct_no" class="wideinput" value="<%=posInfo.getAcctno()%>" MAXLENGTH="30" size="20">
      </td>

      <td width="107" nowrap align="right">&nbsp;</td>
      <td colspan="3" nowrap>
        <input type="hidden" name="vacct_no" class="hidden" value="<%=posInfo.getVacctno()%>" MAXLENGTH="30" size="20">
      </td>
     </tr>
     <tr>
      <td width="125" nowrap align="right">安装机型：</td>
      <td width="237" nowrap>
        <input type="text" name="model" class="wideinput" value="<%=posInfo.getModel()%>" MAXLENGTH="30" size="20">
      </td>

      <td width="107" nowrap align="right">安装柜台：</td>
      <td colspan="3" nowrap>
        <input type="text" name="counter" class="wideinput" value="<%=posInfo.getCounter()%>" MAXLENGTH="30" size="20">
      </td>
      </tr>
    <tr>
      <td width="125" nowrap align="right">安装联系人：</td>
      <td width="237" nowrap><input type="text" name="fix_person" class="wideinput" value="<%=posInfo.getFixperson()%>" maxlength="30" size="20">
      </td>

      <td width="107" nowrap align="right">安装地址：</td>
      <td colspan="3" nowrap>
        <input type="text" name="fix_address" class="wideinput" value="<%=posInfo.getFixaddress()%>" maxlength="50" size="40">
      </td>
     </tr>
    <tr>
      <td width="125" nowrap align="right">安装日期：</td>
      <td width="237" nowrap>
        <input type="text" name="fix_date" style="width:100" value="<%=posInfo.getFixdate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)
      </td>

      <td width="107" nowrap align="right">通讯类型：</td>
      <td width="130" nowrap>
        <select name="net_type" size="1">
          <option value="">请选择</option>
       	<%initList("netTypeList",posInfo.getNettype(),request,out);%>
       	</select>
      </td>
    </tr>
    <tr>
      <td width="107" nowrap align="right">建档日期：</td>
      <td width="237" nowrap><input type="text" class="readonly" readonly  style="background-color:#cccccc" value="<%=posInfo.getRecorddate()%>" maxlength="8"  size="20" >
        </td>
      <td width="107" nowrap align="right">跨行退货标志：</td>
      <td height="19" colspan="3" nowrap>
	  <select name="memo1c" size="1">
        <option value="">请选择</option>
       	<%initList("memo1cList",posInfo.getMemo1c(),request,out);%>
       	</select>*
	  </td>
    </tr>
    <tr>
      <td width="125" nowrap align="right">POS类型：</td>
      <td width="237" nowrap height="19">
	  <select name="memo1a" size="1">
        <option value="">请选择</option>
       	<%initList("memo1aList",posInfo.getMemo1a(),request,out);%>
       	</select>*
	  </td>
      <td width="107" nowrap align="right">连接方式：</td>
      <td height="19" colspan="3" nowrap>
	  <select name="memo1b" size="1">
        <option value="">请选择</option>
       	<%initList("memo1bList",posInfo.getMemo1b(),request,out);%>
       	</select>*
	  </td>
    </tr>
    <tr>
      <td width="125" nowrap align="right">备注：</td>
      <td nowrap>
        <input type="text" name="memo2" value="<%=toucsString.toHtml(posInfo.getMemo2())%>" MAXLENGTH="50" size="25">
      </td>
      <td width="107" nowrap align="right">备注：</td>
      <td colspan="5" nowrap>
        <input type="text" name="memo3" value="<%=toucsString.toHtml(posInfo.getMemo3())%>" MAXLENGTH="50" size="25">
      </td>    </tr>
</table>
  <table width="80%">
    <tr>
      <td colspan="5" nowrap>
        <!--a href="javascript:doSubmit()">[确定]</a>
		  <a href="javascript:formReg.reset()">[重填]</a>
          <a href="javascript:location.href='/ToucsMonitor/devicemanage'">[取消]</a-->
        <div align="right">
          <input type="submit" name="submitbutton" value="提交" class="inputbutton">
          <input type="button" name="cancelbutton" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/posdeviceconfig?uid=<%=uid%>'">
        </div>
      </td>
    </tr>
  </table>
  <p/>
</form>
</body>
</html>