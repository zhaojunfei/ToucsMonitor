<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*,com.adtec.toucs.monitor.loginmanage.*" %>

<%
  String msg="";
  Object obj1=request.getAttribute("Message");  //取得消息
  request.removeAttribute("Message");   //删除消息
  if(obj1!=null)msg=(String)obj1;
  String QUERYHANDCARD="13005";
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
  LoginInfo login=(LoginInfo)request.getAttribute("login");
  String DisStr = " disabled";
  if(login != null){
    try{
    if( LoginManageBean.operValidate(login,"13006") == 0) DisStr = "";
    }catch(Exception ex){
    }
  }

%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script language=javascript>

	function showPopUpOnLoad(){
	    var Msg = '<%= msg %>';
	    if (Msg != "") {
	        alert(Msg);
	    }
	}

function SelPage(){

	var page=document.form1.CurrPageNum.value;
	var url="/ToucsMonitor/posicservlet?reqCode=13005&page="+page+"&isFirstTime=0";
	document.form1.action=url;
	document.form1.submit();
}
function UpdateSubMit(){
    if(confirm("确实要修改？")){
        var url="/ToucsMonitor/posicservlet?reqCode=13006";
        document.form1.action=url;
        document.form1.submit();
        return true;
    }
    return false;
}

</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000"  onload="showPopUpOnLoad()">
<h2><font face="隶书">商业IC卡手输卡管理</font> </h2>
<form name="form1" method="post" action="/ToucsMonitor/posicservlet">
<input type="hidden" name="index">
<table width="100%" cellpadding="0" cellspacing="0" >
<tr>
<td >
  <table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
  <tr> <td colspan="2">  <hr align="left" noshade> </td><tr>
    <tr>
      <td width="80%" align="left">
        <%
	  		if(DisStr.equals("")){
		%>
        <%
        	}
        %>
        <input type="button" value="返回" name="cmdCancel"  class="inputbutton" style="width: 120"
             onClick=javascript:location.href="/ToucsMonitor/posicservlet?reqCode="+"<%= QUERYHANDCARD %>"></td>
      <td width="36%" align="right"> 第
        <select name="CurrPageNum"   onChange ="SelPage();"  style="width:50">
          <%
			for(int i=1;i<=pageCount;i++){
				if(i==currPageNum){
					out.write("<option value="+i+" selected>");
				}else{
					out.write("<option value="+i+">");
				}
				out.print(i);
				out.write("</option>");
			}
	  	%>
        </select>
        页 </td>
    </tr>
    <tr> <td colspan="2">  <hr align="left" noshade> </td><tr>
  </table>
</td></tr>
<tr><td>
  <table width="745" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
    <tr bgcolor="#666666">
      <td nowrap align="center" width="40"> 磁道号</td>
      <td nowrap align="center" width="60"> 适配偏移</td>
      <td nowrap align="center" width="70"> 适配标识</td>
      <td nowrap align="center" width="70"> 银行名称</td>
      <td nowrap align="center" width="160"> 卡种类</td>
      <td nowrap align="center" width="90"> 卡类型</td>
      <td nowrap align="center" width="60"> 卡表状态</td>
      <td nowrap align="center"  width="90"> 允许手输卡号</td>
    </tr>
    <%
			List queryRst=(List)request.getAttribute("CurrPageCardList");
			if(queryRst!=null){
				for(int i=0;i<queryRst.size();i++){
					CardTypeStruct cardTypeStruct=(CardTypeStruct)queryRst.get(i);
					if(cardTypeStruct==null) continue;
	%>
    <tr  bgcolor="#FFFFFF" align="center" >
      <td nowrap><%=cardTypeStruct.getTrack_no()%> </td>
      <td nowrap><%=cardTypeStruct.getMatchStart()%> </td>
      <td nowrap><%=cardTypeStruct.getcardMatch()%> </td>
      <%
	String bankCode11="";
	int length=0;

	bankCode11=cardTypeStruct.getbankCode();
	if(bankCode11!=null)bankCode11=bankCode11.trim();
	else bankCode11="";
	length=bankCode11.length();
	if(length==0){
	  %>
      <td nowrap> </td>
      <%
	}else{
	  String  bankcode="";
	  String  bankname="";
	  List  bankCodeList=(List)request.getAttribute("bankCode");
	  if(bankCodeList==null)bankname="&nbsp;&nbsp;";
	  else{
		for(int J=0;J<bankCodeList.size();J++){
			try{
			   BankCodeBean  CodeBean0=(BankCodeBean)bankCodeList.get(J);
			   if(CodeBean0==null) continue;
			   bankcode=CodeBean0.getBankCode();
			   if(bankcode!=null)bankcode=bankcode.trim();
			   if(bankCode11.equals(bankcode)) bankname=CodeBean0.getBankName();
			}catch(Exception exp){
				System.out.println("error:"+i);
			}

		}
	}
%>
      <td nowrap><%=bankname%> </td>
      <%
		}
		%>
      <%
	String cardClass11="";
	length=0;

	cardClass11=cardTypeStruct.getcardClass();
	if(cardClass11!=null)cardClass11=cardClass11.trim();
	else cardClass11="";
	length=cardClass11.length();

	if(length==0){
	  %>
      <td nowrap> </td>
      <%
	}else{
	  String  cardClass="";
	  String  cardClassName="";
	  List  cardClassList=(List)request.getAttribute("cardClass");
	  if(cardClassList==null)cardClassName="&nbsp;&nbsp;";
	  else{
		for(int J=0;J<cardClassList.size();J++){
			try{
			   CodeBean  CodeBean0=(CodeBean)cardClassList.get(J);
			   if(CodeBean0==null) continue;
			   cardClass=CodeBean0.getCodeId();
			   if(cardClass!=null)cardClass=cardClass.trim();
			   if(cardClass11.equals(cardClass)) cardClassName=CodeBean0.getCodeDesc();
			}catch(Exception exp){
				System.out.println("error:"+i);
			}

		}
	}
%>
      <td nowrap><%=cardClassName%> </td>
      <%
		}
		%>
      <%
	String cardType11="";
	length=0;

	cardType11=cardTypeStruct.getcardType();
	if(cardType11!=null)cardType11=cardType11.trim();
	else cardType11="";
	length=cardType11.length();

	if(length==0){
	  %>
      <td nowrap> </td>
      <%
	}else{
	  String  cardType="";
	  String cardTypeName="";
	  List  cardTypeList=(List)request.getAttribute("cardType");
	  if(cardTypeList==null)cardTypeName="&nbsp;&nbsp;";
	  else{
		for(int J=0;J<cardTypeList.size();J++){
			try{
			   CodeBean  CodeBean0=(CodeBean)cardTypeList.get(J);
			   if(CodeBean0==null) continue;
			   cardType=CodeBean0.getCodeId();
			   if(cardType!=null)cardType=cardType.trim();
			   if(cardType11.equals(cardType)) cardTypeName=CodeBean0.getCodeDesc();
			}catch(Exception exp){
				System.out.println("error:"+i);
			}

		}
	}
%>
      <td width="94" nowrap><%=cardTypeName%> </td>
      <%
		}
		%>
      <%
    String flag=cardTypeStruct.getrec_useflag();
	String flagName="启用 ";
	if(flag!=null)flag=flag.trim();
	if(flag.equals("1"))  flagName="不启用 ";
%>
      <td width="80" nowrap><%=flagName%> </td>

    <%
    // 手输卡号处理过程
    String handflag=cardTypeStruct.get_hand_flag();
    Debug.println( "****************handflag = :"+ handflag +"************");
	if(handflag!=null)handflag=handflag.trim();
	if( handflag.equalsIgnoreCase("1")) {
		%>
		<td nowrap > <a href="posicservlet?reqCode=13006&track_no=<%=cardTypeStruct.getTrack_no()%>&matchStart=<%=cardTypeStruct.getMatchStart()%>&cardMatch=<%=cardTypeStruct.getcardMatch()%>&curPage=<%=currPageNum%>&flag=0"><font color="#FF0000">设置不可手输</font></a></td>
		<%
	} else {
		%>
		<td nowrap > <a href="posicservlet?reqCode=13006&track_no=<%=cardTypeStruct.getTrack_no()%>&matchStart=<%=cardTypeStruct.getMatchStart()%>&cardMatch=<%=cardTypeStruct.getcardMatch()%>&curPage=<%=currPageNum%>&flag=1">设置可手输</a></td>
		<%
	}
	%>

      <%}%>
    </tr>
    <%
		}
	%>
  </table>
  </td></tr>
  </table>
  <hr align="left" noshade>
  <p>　</p>
</form>
</body>
</html>