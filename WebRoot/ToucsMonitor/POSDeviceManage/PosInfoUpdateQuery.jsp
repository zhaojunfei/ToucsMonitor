<%@ page language="java" contentType="text/html; charset=gb2312" %>

<html>
<head>
<title>POS</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/Check.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.pos_code.disabled=true;
		for (i=0 ; i<document.formQuery.target.length;i++){
			document.formQuery.target[i].disabled=true;
		}
	}
	else{
		document.all.condTable.style.display="none";
		document.formQuery.pos_code.disabled=false;
		for (i=0 ; i<document.formQuery.target.length;i++){
			document.formQuery.target[i].disabled=false;
		}
		document.formQuery.pos_code.focus();
	}
}

<%String uid=request.getParameter("uid");
 String post=request.getParameter("post");
 String permFlag=(String)request.getAttribute("POSTXNSET");
 boolean canSetTxn=(permFlag!=null&&permFlag.equals("1"));%>

function doSubmit(){
	if(!document.formQuery.conditions.checked){
		pos_code=document.formQuery.pos_code.value;
		if(trim(pos_code)==""){
			alert("请输入19位POS编号!");
			document.formQuery.pos_code.focus();
			return false;
		}
		document.formQuery.action="/ToucsMonitor/posdeviceconfig?reqCode=13102&uid=<%=uid%>"
	}
	else{
		document.formQuery.action="/ToucsMonitor/posdeviceconfig?reqCode=13103&uid=<%=uid%>"
	}
	return true;
}
</script>
<body   bgcolor="#CCCCCC" text="#000000">


<h2><font face="隶书">POS设备信息修改</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=13103&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
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
            <td width="22%">19位POS编号：</td>
            <td width="78%">
              <input type="text" name="pos_code" value="" size="19" maxlength="19" >
            </td>
          </tr>
          <tr>
            <td width="22%">内容：</td>
            <td width="78%">
              <input type="radio" name="target" value="queryPage" class="radio" checked >基本信息
	      	  <input type="radio" name="target" value="keyPage" class="radio" >密钥配置
	      	  <% if(canSetTxn){ %>
	      	  <input type="radio" name="target" value="txnSet" class="radio" >交易权限
	      	  <%}%>
	    </td>
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
        <input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">按商户查询 </td>
    </tr>
    <tr>
      <td colspan="4">
        <table width="75%" id="condTable">
          <tr>
            <td width="22%">
              <!--a href="javascript:document.formQuery.submit()">[修改]</a>
        <a href="javascript:document.formQuery.submit()">[加载]</a-->
              所属商户编号： </td>
            <td width="78%">
              <%--lect name="merchant_id">
                <!--option value="-1">请选择</option-->
          	<%initList("PosMerchantList","",request,out);%>
              </select>--%>
             <input type="text" name="merchant_id" size="15" maxlength="15" >
            </td>
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
          <input type="submit" name="Submit32" value="确定" class="inputbutton">
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