<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%
Vector IsMapedATM = (Vector)request.getAttribute("AtmIsMaped");
Vector UnMapedATM = (Vector)request.getAttribute("AtmUnMaped");
String ImageUrl   = (String)request.getAttribute("AtmImg");
String ImageXp    = (String)request.getAttribute("imageXposi");
String ImageYp    = (String)request.getAttribute("imageYposi");
//String AtmCode    = (String)request.getAttribute("theAtmId");
DeviceManageBean DMB = new DeviceManageBean();
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

var NS = ( document.layers ) ? 1 : 0;
var IE = ( document.all ) ? 1 : 0;
function display(e){
	var x;
	var y;
	var obj=event.srcElement;
	if (obj.id.indexOf("mapfile") != -1) {
		document.form1.Xposi.value=""+event.offsetX;
		document.form1.Yposi.value=""+event.offsetY;
	} else {
		document.form1.Xposi.value="鼠标不在图片中";
		document.form1.Yposi.value="鼠标不在图片中";
	}
}
function checkOk(){
	var x, y;
	x = document.form1.Xposi.value;
	y = document.form1.Yposi.value;
	if ( x >= 0 && y >= 0){
	   var url="/ToucsMonitor/deviceconfig?reqCode=21009&&Mapid=<%=ImageUrl%>&&Xposition="+document.form1.Xposi.value+"&&Yposition="+document.form1.Yposi.value+"&&Imgwidth="+document.form1.mapfile.width+"&&ImgHeight="+document.form1.mapfile.height;
	   document.form1.action=url;
	   document.form1.submit();
		return true;		
	  //document.form1.submit();
	}  
}

document.onmousemove = display;
document.onmousedown = checkOk;

//-->
</script>

<script language="javascript">
function onUnATMChange(){
	
}
</script>
<script>
function submitM(){	    	 		
		document.form1.submit();
		return true;
}
</script>
</head>
<body  bgcolor="#FFFFFF" text="#000000">
<h2 align="center" class="style3"><font face="隶书"></font></h2>
<form name="form1" method="post" action="" >
<div align="justify">
	<table width="844" height="442" border="0">
    	<tr>
			<td width="8" rowspan="2"></td>
			<td width="97" rowspan="2"><div align="center">
		  	<div id="Layer2" style="position:absolute; width:94px; height:288px; z-index:2; left: 26px; top: 63px;">
		  	<table  width="66" height="150" border="0">
          		<tr>
          			<td>以添加的ATM设备</td>
          		</tr>
          		<%
            		for (int i = 0; i < IsMapedATM.size(); i++) {
            			Hashtable orgHT = new Hashtable();
            			orgHT = (Hashtable)IsMapedATM.get(i);
            			if (orgHT != null) {
          		%>
		 		<tr>
         			<td > 
		  				<a href="/ToucsMonitor/deviceconfig?reqCode=21008&Atmid=<%=String.valueOf(orgHT.get("atm_id")).trim().equals("")?"&nbsp":orgHT.get("atm_id").toString()%>">					
		 				<%=String.valueOf(orgHT.get("atm_id")).trim().equals("")?"&nbsp":orgHT.get("atm_id").toString()%></a></td>
		 		</tr>
         		<%
		    			}
	      			}	
		 		%>
        	</table>
			</div>
			</div>
			</td>
        	<td width="8" height="295" rowspan="2">
        		<div align="center">          
        	</div></td>
        	<td width="8" rowspan="2">	<div >		
		    <div id="Layer1" style="position:absolute; width:71px; height:278px; z-index:1; left: 152px; top: 68px;">
		    <table  width="66" height="150" border="0">
            	<tr>
            		<td>未添加的ATM设备</td>
            	</tr>
                <%
                    for (int i = 0; i < UnMapedATM.size(); i++) {
						Hashtable orgHT1 = new Hashtable();
                    	orgHT1 = (Hashtable)UnMapedATM.get(i);
                   		if (orgHT1 != null) {
                %>	
                <tr>               
                  	<td>
				    	<a href="/ToucsMonitor/deviceconfig?reqCode=21008&Atmid=<%=String.valueOf(orgHT1.get("atm_id")).trim().equals("")?"&nbsp":orgHT1.get("atm_id").toString()%>">
				    	<%=String.valueOf(orgHT1.get("atm_id")).trim().equals("")?"&nbsp":orgHT1.get("atm_id").toString()%>
				    	</a>				  
				  	</td> 
                </tr>
                <%
						}
	  				}	
				%>
            </table>
			</div>
			</div></td>
        	<td colspan="5" rowspan="2">
			<div align="center"></div>
			</td>

			<td width="90" height="27"><div align="right">X 坐标</div></td>
        	<td width="70"><input name="Xposi" type="text" id="Xposi" size="10"></td>
        	<td width="55"><div align="right">Y 坐标</div></td>
        	<td width="104"><input name="Yposi" type="text" id="Yposi" size="10"></td>
        	<td width="198">&nbsp;</td>
        	<td width="85" rowspan="2">&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="409" colspan="5"><div id="Layer3" style="position:absolute; width:1229px; height:1924px; z-index:3; left: 233px; top: 106px;"><img src="/ToucsMonitor/map/<%=ImageUrl.toString()%>.jpg" id="mapfile" >
          	<div id="Layer4"  style="position:absolute; width:20px; height:21px; z-index:1; left: 15px ; top: 65px; visibility: hidden;"><img name="atm" src="/ToucsMonitor/Atm.jpg" width="19" height="20"></div>
        	</div>
        	</td>
      	</tr>
	</table>	
  	</div>
  	<div align="center"></div>
</form>
</body>
</html>

