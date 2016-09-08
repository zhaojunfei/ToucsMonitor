<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>

<%!
	private String getOperLink(String reqCode,String target,String pos_code,String uid){
		String ret="/ToucsMonitor/posenrollconfig?reqCode="+reqCode+"&uid="+uid
			+"&target="+target+"&pos_code="+pos_code;
		return ret;
	}
%>

<%
	String uid=request.getParameter("uid");
String msg="";
Object obj1=request.getAttribute("Message");  //取得消息
request.removeAttribute("Message");   //删除消息
if(obj1!=null)msg=(String)obj1;
String PAGEPROCESSOR="PAGEPROCESSOR";

int pageCount=1;  
int currPageNum=1;
obj1=request.getAttribute("PageCount");
if(obj1!=null){
	String strPageCount=(String)obj1;
	Integer tmpPageCount=new Integer(strPageCount);
	pageCount=tmpPageCount.intValue();	
}

obj1=request.getAttribute("CurrPageNum");
if(obj1!=null){
	String strCurrPageNum=(String)obj1;
	Integer tmpCurrPageNum=new Integer(strCurrPageNum);
	currPageNum=tmpCurrPageNum.intValue();	
}



%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript">
function onDelete(linkDel){
	if(window.confirm("确实要删除该设备吗？")){
		location.href=linkDel;
	}
}


function SelPage(){
	var url="/ToucsMonitor/posenrollconfig?reqCode=<%=PAGEPROCESSOR%>";
	//alert(url);
	document.form1.action=url;
	document.form1.submit();		
}

function showPopUpOnLoad(){
    var Msg = '<%= msg %>';
    if (Msg != "") {
        alert(Msg);
    }
}
</script>
<body  bgcolor="#CCCCCC" text="#000000" >

<div>
  <h2><font face="隶书">设备列表 </font></h2>
</div>
<!--
<form name="form1" method="post" action="">

<table  border="0" cellpadding="0" cellspacing="0" align="right">
	<tr>
		<td width="36%" align="right"> 第 
        <select name="CurrPageNum"   onChange ="SelPage();"  style="width:50">
          <%		  	
			for(int i=1;i<=pageCount;i++){
				if(i==currPageNum){   
					out.write("<option selected>");
				}else{
					out.write("<option>");				
				}
				out.print(i);
				out.write("</option>");
			}	
	  	%>
        </select>
        页 </td>
	</tr>
</table>
    <br/>
  -->
<hr align="left" noshade>
<br/>
<table width="100%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
  <tr bgcolor="#666666">
    <td width="4%" align="center" nowrap>序号</td>
    <td width="10%" align="center" nowrap>POS顺序号</td>
    <td width="13%" align="center" nowrap>19位POS编号</td>
    <td width="18%" align="center" nowrap>商户号</td>
    <td width="15%" align="center" nowrap>机构</td>
    <td width="18%" align="center" nowrap>柜员号</td>
    <td width="22%" align="center" nowrap>管理</td>
  </tr>
  <%
  			//Vector v=(Vector)request.getAttribute("posEnrollList");
  			List list = (List)request.getAttribute("posEnrollList");
			String pos_code="";
      String pos_dcc_code="";
  			if(list!=null){
	        	for(int i=0;i<list.size();i++){
				POSEnrollInfo info=(POSEnrollInfo)list.get(i);
				pos_code=info.getPoscode();
        pos_dcc_code=info.getPosDccCode();
   %>
  <tr bgcolor="#FFFFFF" id=<%=info.getPoscode()%>>
    <td align="center" nowrap><%=i+1%></td>
    <td align="center" nowrap><%=pos_code%></td>
    <td align="center" nowrap><%=pos_dcc_code%></td>
    <td align="center" nowrap><%=info.getMerchantid()%></td>
    <td align="center" nowrap><%=info.getBranchid()%></td>
    <td align="center" nowrap><%=info.getTellerid()%></td>
    <td align="center">
        <a href="<%=getOperLink("13602","page",pos_dcc_code,uid)%>">[修改] </a>
        <a href="javascript:onDelete('<%=getOperLink("13604","",pos_code,uid)%>')">[删除]</a>
        <%if(info.getUseflag().equals("0")){%>
        <a href="<%=getOperLink("13605","",pos_code,uid)%>">[启用]</a>
        <%}%>
        <%if(info.getUseflag().equals("1")){%>
        <a href="<%=getOperLink("13605","page",pos_code,uid)%>">[停用]</a>
        <%}%>
    </td>
  </tr>
  <%}
  			}%>
</table>

</body>
</html>