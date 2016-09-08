<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*"%>
<%!
private String getOperLink(String reqCode, String target,String company_id, String uid) {
		String ret = "/ToucsMonitor/posBCCompany?reqCode=" + reqCode + "&uid=" + uid
				+ "&target=" + target + "&company_id=" + company_id;
		return ret;
}
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

POSBCCompanyInfo Info=(POSBCCompanyInfo)request.getAttribute("POSCBCCompany");
if (Info == null){
	Info = new POSBCCompanyInfo();
}

String uid=request.getParameter("uid");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该合作单位吗？")){
		location.href=linkDel;
	}
}
function submit22(){
	if(confirm("确认要删除选择的预付卡公司信息？")!=true) return false;
	var url = "/ToucsMonitor/posBCCompany?reqCode=16902&&uid=<%=uid%>"
	document.form1.action=url;
	document.all("index").value= index;	
	document.form1.submit();
}
function btn1(){
	var its = document.getElementsByName("box1");
	for(var i=0;i<its.length;i++){
		if(its[i].type="checkbox"){
			its[i].checked = true ;
		}
	}
}

function btn2(){
	var its = document.getElementsByName("box1");
	for(var i=0;i<its.length;i++){
		if(its[i].type="checkbox"){
			its[i].checked = false ;
		}
	}
}
</script>
  <head>
  <title>Untitled Document</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">

  </head>
  
  <body  bgcolor="#CCCCCC" text="#000000">

<div>
	<div align="left">
		预付卡公司列表
	</div>
</div>
<form name="form1" method="post" action="/ToucsMonitor/posBCCompany">
<hr align="left" noshade>
	<table width="98%" border="0" cellpadding="0" cellspacing="0" align="left">
<tr>
	 <td width="5%" align="left"> 
      		<input type="button" name="bt1"  value="全选" class="inputbutton"  onClick="btn1()">
			<input type="button" name="bt2"  value="取消" class="inputbutton"  onClick="btn2()">
			<input type="submit" name="delete"  value="删除" class="inputbutton"  onClick="submit22(); return false;">
			</td>
			</tr>
		
    	<tr>
    		<td colspan="2"><hr align="left" noshade></td>
    	</tr> 
  	</table>
  	<br/><br/>
	<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
		<tr bgcolor="#666666">
		    <td width="2"   align="center" nowrap></td>
			<td width="10%" align="center" nowrap>序号</td>
			<td width="20%" align="center" nowrap>单位编号</td>
			<td width="20%" align="center" nowrap>单位名称</td>
			<td width="15%" align="center" nowrap>机构号</td>
			<td width="10%" align="center" nowrap>公司状态</td>
			<td width="25%" align="center" nowrap>管理</td>
			
			
		</tr>
		<%	
			List list = (List)request.getAttribute("POSBCCompanyList");
			String org_id="";
			String company_id="";
		    String company_name="";
		      		
 				if(list!=null){
		        	for(int i=0;i<list.size();i++){
		        		POSBCCompanyInfo info=(POSBCCompanyInfo)list.get(i);
		        		org_id=info.getOrg_id();
		        		company_id=info.getCompany_id();
		        		company_name=info.getCompany_name();
		%>
		<tr bgcolor="#FFFFFF" id=<%=info.getCompany_id()%>>
		    <td align="center" nowrap><input type="checkbox" name="box1" value="<%=info.getCompany_id()%>"></td>
			<td align="center" nowrap><%=i + 1%></td>
			<td align="center" nowrap><%=company_id%></td>
			<td align="center" nowrap><%=company_name%></td>
			<td align="center" nowrap><%=info.getOrg_id()%></td>
			<%
    		String flag=info.getCompany_stat();
			String flagName="停用状态 ";
			if(flag!=null)flag=flag.trim();
			if(flag.equals("1"))  flagName="启用状态 ";	
		%>
      		<td align="center" nowrap><%=flagName%> </td>
			<td align="center">
				<a href="<%=getOperLink("16903", "page", company_id, uid)%>">[修改] </a>
				<a href="javascript:onDelete('<%=getOperLink("16902", "", company_id, uid)%>')">[删除]</a>
				<%if(info.getCompany_stat().equals("0")){%>
        		<a href="<%=getOperLink("16905","",company_id,uid)%>">[启用]</a>
        		<%}%>
        		<%if(info.getCompany_stat().equals("1")){%>
        		<a href="<%=getOperLink("16905","page",company_id,uid)%>">[停用]</a>
        	<%}%>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
	<table width="80%">
    	<tr>
      		<td colspan="5" nowrap>
        	<div align="right">
          		<input type="button" name="cancelbutton" value="返回" class="inputbutton"  onClick=history.back()>
        	</div>
      		</td>
    	</tr>
  	</table>
  	</form>
	</body>
</html>
