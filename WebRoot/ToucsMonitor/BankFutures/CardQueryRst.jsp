<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.BankFutures.*" %>

<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
	if(obj1!=null)msg=(String)obj1;
 	String REGCARD="17401";
  	String MODCARD="17402";

  	String MODCARDREQ2="1740202";
  	String DELCARD="17403";
  	String QUERYCARD="17404";
  	String MG7550="12005";
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
  	String hasA="",hasD="",hasM="",hasQ="";
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

function submit11(type,index){
    if(type==2)
		if(confirm("ȷ��Ҫɾ��ѡ��Ŀ�����Ϣ��")!=true) return false;
	var url="";
	if(type==1){					
		url="/ToucsMonitor/bfCardservlet?reqCode=<%=MODCARDREQ2%>&&uid=<%=uid%>";
	}else{
		url="/ToucsMonitor/bfCardservlet?reqCode=<%=DELCARD%>&&uid=<%=uid%>";
	}
	//alert(url);
	document.form1.action=url;
	document.all("index").value= index;	
	document.form1.submit();		
}

function refreshsubmit(){
	if(confirm("ȷ��Ҫˢ�¿�����Ϣ��")!=true) return false;
	var url="/ToucsMonitor/bfCardservlet?reqCode=<%=MG7550%>";
	//alert(url);
	document.form1.action=url;
	document.form1.submit();		
	return true;
}


function SelPage(){
	var url="/ToucsMonitor/bfCardservlet?reqCode=<%=PAGEPROCESSOR%>";
	//alert(url);
	document.form1.action=url;
	document.form1.submit();		
}

</script>
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<body  bgcolor="#CCCCCC" text="#000000"  onload="showPopUpOnLoad()">
<h2><font face="����">�������</font> </h2>
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet">
<hr align="left" noshade>
	<table width="98%" border="0" cellpadding="0" cellspacing="0" align="left">
    	<tr> 
      		<td width="64%" align="left"> 
        	<%
	     		String  nUrl="";
	     		if(hasQ.equals("0")){
		 		nUrl="/ToucsMonitor/bfCardservlet?reqCode="+QUERYCARD+"&&uid="+uid;
	    	%>
        		<input type="button" name="query"  value="��ѯ" class="inputbutton"  onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        	<%if(hasA.equals("0")){
				nUrl="/ToucsMonitor/bfCardservlet?reqCode="+REGCARD+"&&uid="+uid;
			%>
        		<input type="button" name="add"  value="���" class="inputbutton"   onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        	<%if(hasM.equals("0")){
				nUrl="/ToucsMonitor/bfCardservlet?reqCode="+MODCARD+"&&uid="+uid;
			%>
        		<input type="button" name="modify"  value="�޸�" class="inputbutton"  onClick=javascript:location.href='<%=nUrl%>'>
        	<%}%>
        		<input type="hidden" name="index">		
        	<%
		    //hasMG7550="0";
				if(hasMG7550.equals("0")){				
			%>
        	<input type="button" name="MG7550"  value="����ˢ��" class="inputbutton"   onClick=refreshsubmit()>
        	<%}%>
      		</td>
      		<td width="36%" align="right"> �� 
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
        	</select>ҳ
        	</td>
    	</tr>  
    </table>
    <br/><br/>
<hr align="left" noshade>
  	<table width="98%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
    	<tr bgcolor="#666666"> 
      		<td nowrap align="center">������ʼλ</td>
      		<td nowrap align="center"> �������ʶ</td>
      		<td nowrap align="center"> ��������</td> 	 
		    <td nowrap align="center"> ����</td>
    	</tr>
    	<%			
			List queryRst=(List)request.getAttribute("CurrPageCardList");
			if(queryRst!=null){
				//out.println("queryRst.size()=="+queryRst.size());
		    	for(int i=0;i<queryRst.size();i++){
					CardTypeStruct cardTypeStruct=(CardTypeStruct)queryRst.get(i);		
					if(cardTypeStruct==null) continue;		
		%>
    	<tr  bgcolor="#FFFFFF" align="center" > 
      		<td nowrap><%=cardTypeStruct.getCardStart()%> </td>
      		<td nowrap><%=cardTypeStruct.getCardMatch() %> </td>
      		<td nowrap><%=cardTypeStruct.getCardDesc() %> </td>  
	      	<td width="85" nowrap> 
        	<%if(hasM.equals("0")){%>
        		<a href=" "  onClick="submit11(1,<%=i%>); return false;">[�޸�]</a> 
        	<%}%>
        	<%if(hasD.equals("0")){%>
        		<a href="/ToucsMonitor/bfCardservlet" onClick="submit11(2,<%=i%>); return false;">[ɾ��]</a> 
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