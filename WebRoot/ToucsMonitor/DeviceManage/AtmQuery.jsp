<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="option" class="com.adtec.toucs.monitor.devicemanage.QueryOption"/>

<!--页面校验-->
<!--JSP方法:初始化列表框-->
<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
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
			}catch(IOException exp){
				exp.printStackTrace();
				System.out.println(exp.getMessage());
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
<script language="javascript" src="/ToucsMonitor/DeviceManage/AtmRegJs.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.formQuery.conditions.checked){
		document.all.condTable.style.display="";
		document.formQuery.atmCode.disabled=true;				
	}else{
		document.all.condTable.style.display="none";
		document.formQuery.atmCode.disabled=false;
		document.formQuery.atmCode.focus();
	}
}

function doSubmit(){
	if(!document.formQuery.conditions.checked){
		atmCode=document.formQuery.atmCode.value;		
		if(trim(atmCode)==""){
			alert("请输入ATM编号或设定查询条件查询ATM设备!");
			document.formQuery.atmCode.focus();
			return false;
		}
	}
	return true;
}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String post=request.getParameter("post");
%>
<h2><font face="隶书">ATM设备参数管理</font></h2>
<form name="formQuery" method="post" action="/ToucsMonitor/deviceconfig?reqCode=13003&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
<input type="hidden" name="post" value=<%=post%>>
	<table width="100%">
    	<tr> 
      		<td colspan="4"> 
        		<hr noshade>
      		</td>
    	</tr>
    	<tr> 
      		<td colspan="4"> 
        		<table width="75%">
          			<tr> 
            			<td width="22%">设备编号：</td>
            			<td width="78%"> 
              			<input type="text" name="atmCode" value="A5110" onBlur="javascript:onAtmCode(atmCode,false)">
            			</td>
          			</tr>
          			<tr> 
            			<td width="22%">内容：</td>
            			<td width="78%"> 
              			<input type="radio" name="content" value="base" class="radio" checked>基本信息
			  			<input type="radio" name="content" value="key" class="radio">密钥配置 
              			<input type="radio" name="content" value="box" class="radio">钞箱配置 
			  			<%if(post.equals("13003")){%>
              			<input type="radio" name="content" value="state" class="radio">状态
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
        		<input type="checkbox" name="conditions" value="checkbox" class="radio" onClick="javascript:onCondition()"> 查询
        	 </td>
    	</tr>
    	<tr> 
      		<td colspan="4"> 
        		<table width="75%" id="condTable">
          			<tr> 
            			<td width="22%"> 所属机构： 
            			</td>
            			<td width="78%"> 
              				<select name="orgId">
                			<!--option value="-1">请选择</option-->
                			<%initList("orgList",option.getOrgId(),request,out);%>
              				</select>
            			</td>
          			</tr>
          			<tr> 
            			<td width="22%">设备类型：</td>
            			<td width="78%"> 
              				<select name="atmType" size="1">
                			<option value="" selected>所有类型</option>
                			<%initList("atmTypeList",option.getAtmType(),request,out);%>
              				</select>
            			</td>
          			</tr>
          			<tr> 
            			<td width="22%">安装地点类型：</td>
            			<td width="78%"> 
              				<select name="atmAddrType">
                			<option value="" selected>所有类型</option>
                			<%initList("addrTypeList",option.getAtmAddrType(),request,out);%>
             			 	</select>
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
          		<input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?uid=<%=uid%>'">
				</div>
      		</td>
    	</tr>
  	</table>
</form>
</body>
<script language="javascript">
	document.all.condTable.style.display="none";
</script>
</html>
