<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //取得消息
	request.removeAttribute("Message");   //删除消息
	if(obj1!=null)msg=(String)obj1;
  	String REGCARD="14701";
  	String MODCARD="14702";
  	String MODCARDREQ2="1470202";
  	String DELCARD="14704";
  	String QUERYCARD="14703";
  	String MG7550="12015";
  	String UPLOAD="14707";
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
  
  	Hashtable opv=(Hashtable)request.getAttribute("OpervRes");
  	String hasA="",hasD="",hasM="",hasQ="" , hasU="";
  	String hasMG7550="";
  	if(opv !=null){
   		Object obj2;	
  		obj2=opv.get(REGCARD);
		if(obj2!=null)
			hasA=(String)obj2;	
			obj2=opv.get(DELCARD);
		if(obj2!=null)
			hasD=(String)obj2;	
			obj2=opv.get(MODCARD);
		if(obj2!=null)
			hasM=(String)obj2;	
			obj2=opv.get(QUERYCARD);
		if(obj2!=null)
			hasQ=(String)obj2;		
			obj2=opv.get(MG7550);
		if(obj2!=null)
			hasMG7550=(String)obj2;	
  	}
  	String uid=request.getParameter("uid");	 
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

function submit22(){
	var url = "/ToucsMonitor/posLcCard?reqCode=<%=DELCARD%>&&uid=<%=uid%>"
	document.form1.action=url;
	document.form1.submit();
}

function submit11(type,index){
    if(type==2)
		if(confirm("确认要删除选择的理财pos卡卡表信息？")!=true) return false;
	var url="";
	if(type==1){					
		url="/ToucsMonitor/posLcCard?reqCode=<%=MODCARDREQ2%>&&uid=<%=uid%>";
	}else{
		url="/ToucsMonitor/posLcCard?reqCode=<%=DELCARD%>&&uid=<%=uid%>";
	}
	document.form1.action=url;
	document.all("index").value= index;	
	document.form1.submit();		
}

function refreshsubmit(){
	if(confirm("确认要刷新理财pos卡卡表信息吗？")!=true) return false;
	var url="/ToucsMonitor/posLcCard?reqCode=<%=MG7550%>";
	document.form1.action=url;
	document.form1.submit();		
	return true;
}

function SelPage(){
	var url="/ToucsMonitor/posLcCard?reqCode=<%=PAGEPROCESSOR%>";
	document.form1.action=url;
	document.form1.submit();		
}

</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000"  onload="showPopUpOnLoad()">
<h2><font face="隶书">理财pos卡卡表管理</font> </h2>
<form name="form1" method="post" action="/ToucsMonitor/posLcCard">
<hr align="left" noshade>
	<table width="98%" border="0" cellpadding="0" cellspacing="0" align="left">
    	<tr> 
      		<td width="64%" align="left"> 
        	<%
	    		String  nUrl="";
	     		if(hasQ.equals("0")){
		 		nUrl="/ToucsMonitor/posLcCard?reqCode="+QUERYCARD+"&&uid="+uid;
	    	%>
        		<input type="button" name="query"  value="查询" class="inputbutton"  onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        	<%if(hasA.equals("0")){
				nUrl="/ToucsMonitor/posLcCard?reqCode="+REGCARD+"&&uid="+uid;
			%>
        		<input type="button" name="add"  value="添加" class="inputbutton"   onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        	<%if(hasM.equals("0")){
				nUrl="/ToucsMonitor/posLcCard?reqCode="+MODCARD+"&&uid="+uid;
			%>
        		<input type="button" name="modify"  value="修改" class="inputbutton"  onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        		<input type="hidden" name="index">	  
        	<%
        		if(hasD.equals("0")){
        			nUrl="/ToucsMonitor/posLcCard?reqCode="+DELCARD+"&&uid="+uid;
        	%>
            	<input type="submit" name="delete"  value="删除" class="inputbutton"  onClick="submit22()">
       		<%
        		}
       		%>	
        	<%
		   		if(hasMG7550.equals("0")){				
			%>
        		<input type="button" name="MG7550"  value="卡表刷新" class="inputbutton"   onClick=refreshsubmit()>
        	<%}%>
      		</td>
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
        		</select>页
        	</td>
    	</tr> 
    	<tr>
    		<td colspan="2"><hr align="left" noshade></td>
    	</tr> 
  	</table>
  	<br/><br/>
  	<table width="98%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
    	<tr bgcolor="#666666">
      		<td width="2"   align="center" nowrap></td> 
      		<td nowrap align="center"> 磁道号</td>
      		<td nowrap align="center"> 适配偏移</td>
      		<td nowrap align="center"> 卡bin</td>
	  		<td nowrap align="center"> 卡长度</td>
      		<td nowrap align="center"> 银行名称</td>
      		<td nowrap align="center"> 卡种类</td>
     		<td nowrap align="center"> 卡类型</td>
      		<td nowrap align="center"> 卡表状态</td>
      		<td nowrap align="center"> 操作</td>
    	</tr>
    	<%
			List queryRst=(List)request.getAttribute("CurrPageCardList");
			if(queryRst!=null){
				for(int i=0;i<queryRst.size();i++){
					POSLcCardInfo cardInfo=(POSLcCardInfo)queryRst.get(i);		
					if(cardInfo==null) continue;		
		%>
    	<tr  bgcolor="#FFFFFF" align="center" > 
      		<td align="center" nowrap><input type="checkbox" name="box1" value="<%=cardInfo.getcardMatch() %>"></td>
      		<td nowrap><%=cardInfo.getTrack_no()%> </td>
      		<td nowrap><%=cardInfo.getMatchStart()%> </td>
      		<td nowrap><%=cardInfo.getcardMatch()%> </td>
	  		<td nowrap><%=cardInfo.getcardLen()%> </td>
      	<%
			String bankCode11="";
			int length=0;	
			bankCode11=cardInfo.getbankCode();
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
      		<td nowrap><%=bankname%></td>
      	<%
			}
		%>
      	<%
			String cardClass11="";
			length=0;		
			cardClass11=cardInfo.getcardClass();
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
			cardType11=cardInfo.getcardType();
			if(cardType11!=null){
				cardType11=cardType11.trim();
			}else{
				cardType11="";
			}	
			if(cardType11.trim().equals("1") ){		
		%>
    		<td width="94" nowrap>本行卡 </td>
     	<%
			}else if(cardType11.trim().equals("7")) {
		%>
			<td width="94" nowrap>他行卡 </td>
		<%
			}else{
		%>
			<td width="94" nowrap> </td>
		<%
			}
		%>
      	<%
    		String flag=cardInfo.getrec_useflag();
			String flagName="启用 ";
			if(flag!=null)flag=flag.trim();
			if(flag.equals("1"))  flagName="不启用 ";	
		%>
      		<td width="80" nowrap><%=flagName%> </td>
      		<td width="85" nowrap> 
        	<%if(hasM.equals("0")){%>
        		<a href="/ToucsMonitor/posLcCard"  onClick="submit11(1,<%=i%>); return false;">[修改]</a> 
        	<%}%>
        	<%if(hasD.equals("0")){%>
        		<a href="/ToucsMonitor/posLcCard" onClick="submit11(2,<%=i%>); return false;">[删除]</a> 
      		</td>
      		<%}%>
    	</tr>
    	<%
			}
			}	
		%>
  	</table>
<hr align="left" noshade>
</form>
</body>
</html>