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

//��ȡ������Ϣ����
private void getListDesc(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId())){
			try{
				out.println(code.getCodeDesc());
			}catch(IOException exp){}
			}
		}
	}
}
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/eBridge.js">
</script>
<script language="javascript">

function doSubmit(){
	if( !onPosCode("pos_code",true)) return false;
	if( isNull("pos_c_code","POS-C������")) return false;
	if( isNull("pos_dcc_code","POS-DCC������")) return false;
	if( isNull("merchant_id","�̻�")) return false;
	if( isNull("equip_type","�豸����")) return false;
	if( isNull("pre_authorized","Ԥ��Ȩ����")) return false;
	if( isNull("rebate","���һؿ���")) return false;

	if( !isFNumber("rebate","�ؿ���")) return false;

	if(!validFullDate("fix_date","��װ����")) return false;
	
	if( isNull("memo1","��ע1")) return false;
	if( document.forms[0].memo1.value=="1" && document.forms[0].equip_type.value=="4" )
	{
		alert("��ע1����Ϣ���豸���Ͳ�ƥ��!����POS���ײ���ƥ��ת�ʣ�");
		return false;
	}
	if( document.forms[0].memo1.value=="2" && document.forms[0].equip_type.value!="4" )
	{
		alert("��ע1����Ϣ���豸���Ͳ�ƥ��!ת��POS����ֻ��ƥ��ת�ʣ�");
		return false;
	}

	if(confirm("ȷ�����������ύ��"))
		return true;
	else
		return false;
}
function onMctIDBlur(){
   var  find = false;
   for( var i=0 ;i < formReg.merchant_info.length; i++ )
   {
       if( formReg.merchant_id.value == formReg.merchant_info.options[ i ].value )
       {

          formReg.merchant_name.value = formReg.merchant_info.options[ i ].text;
          find = true;
          break;
       }
   }
   if( !find )
   {
       alert("�ޱ��Ϊ"+formReg.merchant_id.value+"���̻�!");
       formReg.merchant_id.value="";
       //formReg.merchant_id.focus();
       //formReg.merchant_id.select();
       formReg.merchant_name.value="";
   }
   return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
  			String uid=request.getParameter("uid");
			posInfo.setMct_name();
%>

<form name="formReg" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=13102&content=base&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
  <input type="hidden"  name="key" value="<%=posInfo.getPoscode()%>">
<font face="����" size="+2">POS�豸��Ϣ</font>

    <table id="tab0" width="90%">
     <select name="merchant_info" size="1" DISABLED style="display:none">
         <!-- <option value="">��ѡ��</option>-->
          <%initList("PosMerchantList","",request,out);%>
    </select>

    <tr>
      <td width="137" nowrap align="right" >19λPOS��ţ�</td>
      <td width="225" nowrap><input type="text" name="pos_dcc_code" class="readonly"   value="<%=posInfo.getPosDccCode()%>" maxlength="19"  size="20">
        </td>
      <td width="107" nowrap height="19" align="right">POS˳��ţ�</td>
      <td height="19" colspan="3" nowrap>
       <input type="hidden" name="pos_c_code" value="<%=posInfo.getPosCCode()%>">
       <input type="text" name="pos_code" class="readonly"    readonly value="<%=posInfo.getPoscode()%>"  maxlength="9" size="20">
</td>
    </tr>
     <tr>
      <td width="137" nowrap align="right"> �̻���ţ�
      </td>
      <td width="225" nowrap>
       <%-- <select name="merchant_id" size="1">
        <option value="">��ѡ��</option>
          <%initList("PosMerchantList",posInfo.getMerchantid(),request,out);%>
        </select> --%>
     <input type="text" class="readonly"    value=<%=posInfo.getMerchantid() %> name="merchant_id"  size=20 MAXLENGTH=15  onblur="onMctIDBlur()">
     </td>
      <td width="107" nowrap align="right"> �̻����ƣ�
      </td>
      <td colspan="3" nowrap>
        <input type="text" class="readonly"    value="<%=toucsString.unConvert(posInfo.getMct_name())%>" READONLY  name="merchant_name"  size=40 MAXLENGTH=40 >
      </td>
    </tr>
        <tr>
    <%{ String classStr ="";
    	if( posInfo.getPreAutorized().equals("2") ) classStr ="disabled"; %>

      <td width="137" nowrap height="19" align="right">�豸���ͣ�</td>
      <td width="225" nowrap height="19">
        <input type="text" class="readonly"    value=<%getListDesc("equitTypeList",posInfo.getEquiptType(),request,out);%> READONLY  name="merchant_name"  size=20 MAXLENGTH=15 >
      <td width="107" nowrap height="19" align="right">Ԥ��Ȩ���ã�</td>
      <td height="19" colspan="3" nowrap>
        <input type="text" class="readonly"    value=<%getListDesc("preAuthorizedList",posInfo.getPreAutorized(),request,out);%> READONLY  name="merchant_name"  size=20 MAXLENGTH=15 >
     <% } %>
    </td>
    </tr>

    <tr>

      <%--td width="20%" nowrap>
        <div align="left">POS״̬��</div>
      </td>
      <td width="30%" nowrap>
        <select name="pos_stat" size="1" disabled>
          <%initList("PosstatList",posInfo.getPosstat(),request,out);%>
        </select>
      </td --%>

      <td width="137" nowrap align="right">���һؿ��ʣ�</td>
      <td width="225" nowrap>
        <input type="text" name="rebate" class="readonly" disabled="true"   value="<%=posInfo.getRebate()%>" MAXLENGTH="10" size="20"></td>
      <td width="107" nowrap align="right">��һؿ��ʣ�</td>
      <td colspan="3" nowrap>
         <input type="text" name="frn_rebate" class="readonly" disabled="true"   value="<%=posInfo.getFrnRebate()%>" MAXLENGTH="10" size="20"></td>
      </tr>
    <tr>
      <td width="137" nowrap align="right">����������</td>
      <td width="225" nowrap>
        <input type="text" name="open_bankno" class="readonly" disabled="true"   value="<%=posInfo.getOpenbankno()%>" MAXLENGTH="50" size="20">
      </td>

      <td width="107" nowrap align="right">�����ţ�</td>
      <td colspan="3" nowrap>
        <input type="text" name="exg_no" class="readonly" disabled="true"   value="<%=posInfo.getExgno()%>" MAXLENGTH="10" size="20">
      </td>
    </tr>
    <tr>
      <td width="137" nowrap align="right">�˺ţ�</td>
      <td width="225" nowrap>
        <input type="text" name="acct_no" class="readonly" disabled="true"  value="<%=posInfo.getAcctno()%>" MAXLENGTH="30" size="20">
      </td>

      <td width="107" nowrap align="right">&nbsp;</td>
      <td colspan="3" nowrap>&nbsp;
      </td>
     </tr>
     <tr>
      <td width="137" nowrap align="right">��װ���ͣ�</td>
      <td width="225" nowrap>
        <input type="text" name="model" class="readonly" disabled="true"  value="<%=posInfo.getModel()%>" MAXLENGTH="30" size="20">
      </td>

      <td width="107" nowrap align="right">��װ��̨��</td>
      <td colspan="3" nowrap>
        <input type="text" name="counter" class="readonly" disabled="true"  value="<%=posInfo.getCounter()%>" MAXLENGTH="30" size="20">
      </td>
      </tr>
    <tr>
      <td width="137" nowrap align="right">��װ��ϵ�ˣ�</td>
      <td width="225" nowrap><input type="text" name="fix_person" class="readonly" disabled="true"  value="<%=posInfo.getFixperson()%>" maxlength="30" size="20">
      </td>

      <td width="107" nowrap align="right">��װ��ַ��</td>
      <td colspan="3" nowrap>
        <input type="text" name="fix_address" class="readonly" disabled="true"  value="<%=posInfo.getFixaddress()%>" maxlength="50" size="40">
      </td>
     </tr>
    <tr>
      <td width="137" nowrap align="right">��װ���ڣ�</td>
      <td width="225" nowrap>
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value="<%=posInfo.getFixdate()%>" MAXLENGTH="8" size="20">(YYYYMMDD)
      </td>

      <td width="107" nowrap align="right">ͨѶ���ͣ�</td>
      <td width="130" nowrap>
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value="<%getListDesc("netTypeList",posInfo.getNettype(),request,out);%>" MAXLENGTH="8" size="20">
      </td>
    </tr>
    <tr>
      <td width="137" nowrap align="right">�������ڣ�</td>
      <td width="225" nowrap>
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value="<%=posInfo.getRecorddate()%>" MAXLENGTH="8" size="20">
      </td>
      <td width="107" nowrap align="right">�����˻���־��</td>
      <td height="19" colspan="3" nowrap>
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value=<%getListDesc("memo1cList",posInfo.getMemo1c(),request,out);%> MAXLENGTH="8" size="20">
	  </td>
    </tr>
    <tr>
      <td width="137" nowrap align="right">POS���ͣ�</td>
      <td width="225" nowrap height="19">
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value="<%getListDesc("memo1aList",posInfo.getMemo1a(),request,out);%>" MAXLENGTH="8" size="20">
	  </td>
      <td width="107" nowrap align="right">���ӷ�ʽ��</td>
      <td height="19" colspan="3" nowrap>
        <input type="text" name="fix_date" class="readonly" disabled="true"  style="width:100" value=<%getListDesc("memo1bList",posInfo.getMemo1b(),request,out);%> MAXLENGTH="8" size="20">
	  </td>
    </tr>
    <tr>
      <td width="137" nowrap align="right">��ע��</td>
      <td nowrap>
        <input type="text" class="readonly" disabled="true" name="memo2" value="<%=toucsString.toHtml(posInfo.getMemo2())%>" MAXLENGTH="50" size="25">
      </td>
      <td width="107" nowrap align="right">��ע��</td>
      <td colspan="5" nowrap>
        <input type="text" class="readonly"  disabled="true"  name="memo3" value="<%=toucsString.toHtml(posInfo.getMemo3())%>" MAXLENGTH="50" size="25">
      </td>    </tr>
</table>
  <table width="80%">
    <tr>
      <td colspan="5" nowrap>
        <!--a href="javascript:doSubmit()">[ȷ��]</a>
		  <a href="javascript:formReg.reset()">[����]</a>
          <a href="javascript:location.href='/ToucsMonitor/devicemanage'">[ȡ��]</a-->
        <div align="right">
           <input type="button" name="cancel" value="ȷ��" class="inputbutton" onClick="javascript:history.go(-1)">
        </div>
      </td>
    </tr>
  </table>
  <p/>
</form>
</body>
</html>