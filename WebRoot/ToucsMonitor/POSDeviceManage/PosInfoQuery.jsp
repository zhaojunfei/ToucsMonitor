<%@ page language="java" contentType="text/html; charset=gb2312" %>

<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!--页面校验-->

<%String uid=request.getParameter("uid");
	  String post=request.getParameter("post");%>

<!--JSP方法:初始化列表框-->
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

function doSubmit(){
	if(!document.formQuery.conditions.checked){
		pos_kind = document.formQuery.pos_kind[0].checked;
		pos_code = document.formQuery.pos_code.value;
		if (trim(pos_code) == "") {
			alert("请输入POS编号!");
			document.formQuery.pos_code.focus();
			return false;
		}
		if (document.formQuery.pos_kind[0].checked && pos_code.length != 8)
		{
			alert("8位POS顺序号必须为8位!");
			document.formQuery.pos_code.focus();
			return false;
		}
		if (document.formQuery.pos_kind[1].checked && pos_code.length != 19)
		{
			alert("19位POS顺序号必须为19位!");
			document.formQuery.pos_code.focus();
			return false;
		}
	}
	return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<h2><font face="隶书">POS设备信息查询</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=13103&uid=<%=uid%>" onSubmit="javascript:return doSubmit();">
  <input type="hidden" name="post" value=<%=post%>>
  <center>
  <table width="80%"   cellpadding="0" cellspacing="0">
    <tr>
    	<td colspan="4"  >
        	<hr noshade>
      	</td>   
    </tr>
    <tr>
      <td colspan="4"  >
        <table width="62%" cellpadding="0" cellspacing="0" >
          <tr>
				<td width="23%"> 选择编号种类：</td>
           		<td width="77%">
            		<input type=radio name="pos_kind" value="2" style="width:20px" >8位POS顺序编号
					<input type=radio name="pos_kind" value="3" style="width:20px"  checked>19位POS编号
				</td>
          </tr>
          <tr>
            <td width="23%">设备编号：</td>
            <td width="77%">
              <input type="text" name="pos_code" value="" size="20"  maxlength="19">
            </td>
          </tr>
          <tr>
            <td width="23%">内容：</td>
            <td width="77%">
              <input type="radio" name="target" value="" class="radio" checked>基本信息
	      	 <input type="radio" name="target" value="keyPage" class="radio">密钥配置
	      	 <input type="radio" name="target" value="txnPage" class="radio">交易权限

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
        <input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">查询 </td>
    </tr>
    <tr>
      <td colspan="4">
        <table id="condTable" >
          <tr>
            <td width="50%">
           <!--    所属商户： </td>
            <td width="78%">
              <select name="merchant_id">
                <option value="-1">请选择</option>
				<%initList("PosMerchantList","",request,out);%>-->
          	所属商户编号：</td>
          	<td width="50%">
          	<input type="text" name="merchant_id" size=15 maxlength=15 >
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
      <td>
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