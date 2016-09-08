<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="POSBCCompanyInfo" class="com.adtec.toucs.monitor.POSDeviceManage.POSBCCompanyInfo"  scope="request"/>

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
<!--页面校验-->
<%String uid=request.getParameter("uid");
	  String post=request.getParameter("post");%>

<html>
<script language="javascript" src="/ToucsMonitor/ToucsMonitor/js/Check.js">
</script>
<script>
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.company_id.disabled=true;
	}
	else{
		document.all.condTable.style.display="none";
		document.formQuery.company_id.disabled=false;
		document.formQuery.company_id.focus();
	}
}

function doSubmit(){
	if(!document.formQuery.conditions.checked){
		company_id=document.formQuery.company_id.value;
		if(trim(company_id)==""){
			alert("请输入预付卡公司编号!");
			document.formQuery.company_id.focus();
			//alert(company_id);
			return false;
		}
	}
	return true;
}
</script>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>

<body bgcolor="#CCCCCC" text="#000000">

<h2><font face="隶书">预付卡公司信息查询</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/posBCCompany?reqCode=16904&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
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
        <table width="668" height="24">
          <tr>
            <td width="106" height="19">预付卡公司编号：</td>
            <td width="386" height="19">
              <input type="text" name="company_id" value="" maxlength="15">
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="4" width="662" height="16">
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="4" width="662" height="20">
        <input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()">
        查询 </td>
    </tr>
    <tr>
      <td colspan="4" width="662" >
        <table width="99%" id="condTable" >
          <tr>
            <td width="24%" height="24" align="right">
                                 机构号： </td>
            <td width="76%"  height="24">
              <select name="org_id">
                <option value="">-----------请选择----------</option>
               <%initList("BranchId","",request,out);%>
              </select>
            </td>
          </tr>
          <tr>
           <td width="24%" height="44" align="right">商户名称：</td>
           <td  height="44"><input type=text size=20 MAXLENGTH=40 name="company_name" ></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="4" width="662" height="16">
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="4" width="662" height="21">
        <div align="right">
          <input type="submit" name="Submit32" value="确定" class="inputbutton">
          <input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/posBCCompany?uid=<%=uid%>'">
          <!--a href="javascript:document.formQuery.submit()">[确定]</a-->
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