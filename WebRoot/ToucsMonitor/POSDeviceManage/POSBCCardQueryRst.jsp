<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>

<%
	String msg="";
	Object obj1=request.getAttribute("Message");  //ȡ����Ϣ
	request.removeAttribute("Message");   //ɾ����Ϣ
	if(obj1!=null)msg=(String)obj1;
	String MODCARD = "16803";
  	String QUERYCARD = "16804";
    String REGCARD = "16801";
    String DELCARD = "16802";
	
   String MODCARDREQ1="1680301";  //�Ӳ�ѯҳ�淢����޸�Ԥ����pos��������Ϣ������
   String MODCARDREQ2="1680302";  //�ӹ���ҳ�淢����޸�Ԥ����pos��������Ϣ������
   
  	
  	//String UPLOAD="16707";
  	
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
  //	String hasMG7550="";
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
			//obj2=opv.get(MG7550);
		//if(obj2!=null)
			//hasMG7550=(String)obj2;	
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
	if(confirm("ȷ��Ҫɾ��ѡ���Ԥ����pos��������Ϣ��")!=true) return false;
	var url = "/ToucsMonitor/posBCCard?reqCode=<%=DELCARD%>&&uid=<%=uid%>"
	document.form1.action=url;
	document.all("index").value= index;	
	document.form1.submit();
}

function submit11(type,index){
    if(type==2)
		if(confirm("ȷ��Ҫɾ��ѡ���Ԥ����pos��������Ϣ��")!=true) return false;
	var url="";
	if(type==1){					
		url="/ToucsMonitor/posBCCard?reqCode=<%=MODCARDREQ2%>&&uid=<%=uid%>";
	}else{
		url="/ToucsMonitor/posBCCard?reqCode=<%=DELCARD%>&&uid=<%=uid%>";
	}
	document.form1.action=url;
	document.all("index").value= index;	
	document.form1.submit();		
}
	
function SelPage(){
	var url="/ToucsMonitor/posBCCard?reqCode=<%=PAGEPROCESSOR%>&&uid=<%=uid%>";
	document.form1.action=url;
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
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>

<body  bgcolor="#CCCCCC" text="#000000"  onload="showPopUpOnLoad()">
<h2><font face="����">Ԥ����pos���������</font> </h2>
<form name="form1" method="post" action="/ToucsMonitor/posBCCard">
<hr align="left" noshade>
	<table width="98%" border="0" cellpadding="0" cellspacing="0" align="left">
    	<tr> 
      		<td width="64%" align="left"> 
      		<input type="button" name="bt1"  value="ȫѡ" class="inputbutton"  onClick="btn1()">
			<input type="button" name="bt2"  value="ȡ��" class="inputbutton"  onClick="btn2()">
      		<%
	    		String  nUrl="";
	     		if(hasQ.equals("0")){
		 		nUrl="/ToucsMonitor/posBCCard?reqCode="+QUERYCARD+"&&uid="+uid;
	    	%>
        		<input type="button" name="query"  value="��ѯ" class="inputbutton"  onClick=javascript:location.href='<%="/ToucsMonitor/posBCCard?reqCode="+QUERYCARD+"&&uid="+uid%>'>
            <%}%>
            
            <%if(hasA.equals("0")){
				nUrl="/ToucsMonitor/posBCCard?reqCode="+REGCARD+"&&uid="+uid;
			%>
        		<input type="button" name="add"  value="���" class="inputbutton"   onClick=javascript:location.href='<%="/ToucsMonitor/posBCCard?reqCode="+REGCARD+"&&uid="+uid%>'>
        	<%}%>
        		<input type="hidden" name="index">	  
        	<%
        		if(hasD.equals("0")){
        			nUrl="/ToucsMonitor/posBCCard?reqCode="+DELCARD+"&&uid="+uid;
        	%>
            	<input type="submit" name="delete"  value="ɾ��" class="inputbutton"  onClick="submit22(); return false;">
       		<%
        		}
       		%>	
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
    	<tr>
    		<td colspan="2"><hr align="left" noshade></td>
    	</tr> 
  	</table>
  	<br/><br/>
  	<table width="98%" cellspacing="1" cellpadding="1" bordercolor="#000000" border="0" bgcolor="#000000">
    	<tr bgcolor="#666666">
      		<td width="2"   align="center" nowrap></td>
      		<td nowrap align="center"> �����</td> 
      		<td nowrap align="center"> �ŵ���</td>
      		<td nowrap align="center"> ��bin</td>
      		<td nowrap align="center"> ��bin����</td>
	  		<td nowrap align="center"> ����</td>
      		<td nowrap align="center"> ��˾���</td>
      		<td nowrap align="center"> ����״̬</td>
      		<td nowrap align="center"> ����</td>
    	</tr>
    	<%
			List queryRst=(List)request.getAttribute("CurrPageCardList");
			if(queryRst!=null){
				for(int i=0;i<queryRst.size();i++){
					POSBCCardInfo cardInfo=(POSBCCardInfo)queryRst.get(i);		
					if(cardInfo==null) continue;		
		%>
    	<tr  bgcolor="#FFFFFF" align="center" > 
      		<td align="center" nowrap><input type="checkbox" name="box1" value="<%=cardInfo.getCard_match() %>"></td>
      		<td nowrap><%=i+1%> </td>
      		<td nowrap><%=cardInfo.getTrack_no()%> </td>
      		<td nowrap><%=cardInfo.getCard_match()%> </td>
      		<td nowrap><%=cardInfo.getCardmatch_len()%> </td>
      		<td nowrap><%=cardInfo.getCard_len()%> </td>
	  		<td nowrap><%=cardInfo.getCompany_id()%> </td>
      	<%
    		String flag=cardInfo.getRec_useflag();
			String flagName="���� ";
			if(flag!=null)flag=flag.trim();
			if(flag.equals("1"))  flagName="������ ";	
		%>
      		<td width="80" nowrap><%=flagName%> </td>
      		<td width="85" nowrap> 
        	<%if(hasM.equals("0")){%>
        		<a href="/ToucsMonitor/posBCCard"  onClick="submit11(1,<%=i%>); return false;">[�޸�]</a> 
        	<%}%>
            <%if(hasD.equals("0")){%>
        		<a href="/ToucsMonitor/posBCCard" onClick="submit11(2,<%=i%>); return false;">[ɾ��]</a> 
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