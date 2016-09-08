<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="posMerchantInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSMerchantInfo"  scope="request"/>

<!--ҳ��У��-->
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/Check.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.merchant_id.disabled=true;
	}
	else{
		document.all.condTable.style.display="none";
		document.formQuery.merchant_id.disabled=false;
		document.formQuery.merchant_id.focus();
	}
}

<%String uid=request.getParameter("uid");
	  String post=request.getParameter("post");%>
function doSubmit(){
	if(!document.formQuery.conditions.checked){
		merchant_id=document.formQuery.merchant_id.value;
		if(trim(merchant_id)==""){
			alert("�������̻����!");
			document.formQuery.merchant_id.focus();

			return false;
		}
		document.formQuery.action="/ToucsMonitor/posmerchantconfig?reqCode=10402&target=queryPage&uid=<%=uid%>"
	}
	else{
		document.formQuery.action="/ToucsMonitor/posmerchantconfig?reqCode=10404&uid=<%=uid%>"
	}
	return true;
}
</script>

<!--JSP����:��ʼ���б��-->
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
}%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body onContextMenu="return false;" bgcolor="#CCCCCC" text="#000000">
<h2><font face="����">�̻���Ϣ�޸�</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/posmerchantconfig?reqCode=15003&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
  <input type="hidden" name="post" value=<%=post%>>
  <center>
  <table width="80%">
    <tr>
      <td colspan="4">
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <table width="75%">
          <tr>
            <td width="22%">�̻���ţ�</td>
            <td width="78%"><input type="text" name="merchant_id" value="" size="15" maxlength="15" >
            </td>
          </tr>
        </table>      </td>
    </tr>
    <tr>
      <td colspan="4">
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">
        ��ѯ </td>
    </tr>
    <tr>
      <td colspan="4">
        <table width="75%" id="condTable">
          <tr>
            <td width="28%" align="right">
              <!--a href="javascript:document.formQuery.submit()">[�޸�]</a>
        <a href="javascript:document.formQuery.submit()">[����]</a-->
              ���������� </td>
            <td width="72%">
              <select name="orgId">
                <!--option value="-1">��ѡ��</option-->
	        <%initList("orgList",posMerchantInfo.getOrgid(),request,out);%>
              </select>
            </td>
          </tr>
          <tr>
           <td width="28%" height="44" align="right">�̻����ƣ�</td>
           <td width="72%" height="44"><input type=text size=20 MAXLENGTH=40 name="merchant_name" >(֧��ģ����ѯ)</td>
          </tr>
		  <tr>
           <td width="28%" height="44" align="right">�Ƿ�����Ԥ���ѿ���</td>
           <td  height="44">
		   <select name="operator" size="1">
             <option value="">��ѡ��</option>
             <%initList("Operator","",request,out);%>
           </select></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <div align="right">
          <input type="submit" name="Submit32" value="ȷ��" class="inputbutton">
          <!--input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/posmerchantconfig?uid=<%=uid%>'"-->
          <!--a href="javascript:document.formQuery.submit()">[ȷ��]</a-->
        </div>
      </td>
    </tr>
  </table>
  </center>
</form>
</body>
<script language="javascript">
document.all.condTable.style.display="none";
</script>
</html>