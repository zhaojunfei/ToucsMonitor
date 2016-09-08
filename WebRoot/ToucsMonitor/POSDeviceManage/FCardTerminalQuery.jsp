<%@ page language="java" contentType="text/html; charset=gb2312" %>


<html>
<head>
<title>Untitled Document</title>
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
                for (i=0 ; i<document.formQuery.pos_kind.length;i++){
			document.formQuery.pos_kind[i].disabled=true;
		}
		for (i=0 ; i<document.formQuery.target.length;i++){
			document.formQuery.target[i].disabled=true;
		}
	}
	else{
		document.all.condTable.style.display="none";
		document.formQuery.pos_code.disabled=false;
		document.formQuery.pos_code.focus();
                for (i=0 ; i<document.formQuery.pos_kind.length;i++){
			document.formQuery.pos_kind[i].disabled=false;
		}
		for (i=0 ; i<document.formQuery.target.length;i++){
			document.formQuery.target[i].disabled=false;
		}
	}
}

<%String uid=request.getParameter("uid");
  String post=request.getParameter("post");%>

function doSubmit(){
	mer_id = document.formQuery.mer_id.value;
	pos_no = document.formQuery.pos_no.value;
	if (mer_id != null && mer_id.length != 0 && mer_id.length != 15)
	{
		alert("外卡商户编号必须为15位!");
		document.formQuery.mer_id.focus();
		return false;
	}
	if (pos_no != null && pos_no.length != 0 && pos_no.length != 8) {
		alert("外卡POS编号必须为8位!");
		document.formQuery.pos_no.focus();
		return false;
	}
	return true;
}
</script>

<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">外卡POS设备信息查询</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/fcardposconfig?reqCode=13503&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
  <input type="hidden" name="post" value=<%=post%>>
  <center>
  <table width="75%" >
    <tr>
    	<td colspan="4"  >
        	<hr noshade>
      	</td>   
    </tr>
          <tr>
            <td width="25%">所属外卡商户编号：</td>
          	<td width="75%"><input type="text" name="mer_id" size=20 maxlength=15 ></td>
          </tr>
          <tr>
            <td width="25%">8位外卡POS编号：</td>
            <td width="75%">
              <input type="text" name="pos_no" value="" size="10" maxlength="8">
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
</html>