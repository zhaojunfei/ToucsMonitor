<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>������������ƽ̨����ϵͳ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<META content=revealTrans(Transition=23,Duration=1.0) http-equiv=Page-Enter>
<META content=revealTrans(Transition=23,Duration=1.0) http-equiv=Page-Exit>

</head>
<%
	String uid = request.getParameter("uid");
%>
<SCRIPT>
function switchSysBar(){
    if (switchPoint.innerText==3){
        switchPoint.innerText=4
        document.all("frmTitle").style.display="none"
    }else{
        switchPoint.innerText=3
        document.all("frmTitle").style.display=""
    }
}
function Yes_Win(URL,Name,Width,Height){
 	if (!Width) { 
 	 	Width =320;
 	}
 	if (!Height){ 
 	 	Height=250;
 	}
 	if (!Name)  { 
 	 	Name  ="broadcast";
 	}
 	var win_cfg = "toolbar=no,directories=no,status=no,menubar=no,scrollbars=no,resilocation=no,scrollbars=no,resizable=no,alwaysRaised=yes,dependent=yes,align=center,valign=center,top=0,left=0,width=" + Width + ",height=" + Height;
 	window.open(URL,Name,win_cfg)
}
</SCRIPT>
<!--
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}
function JM_cc(ob){
var obj=MM_findObj(ob); if (obj) { 
obj.select();js=obj.createTextRange();js.execCommand("Copy");}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}
//-->
<SCRIPT language=JavaScript>
<!--
menuPrefix = 'menu';  // Prefix that all menu layers must start with
                      // All layers with this prefix will be treated
                      // as a part of the menu system.

var menuTree, mouseMenu, hideTimer, doHide,ie4,ns4;


function init() {
  	ie4 = (document.all)?true:false;
  	ns4 = (document.layers)?true:false;
  	document.onmousemove = mouseMove;
  	if (ns4) { document.captureEvents(Event.MOUSEMOVE); }

  	if(<%out.println(request.getParameter("useDate"));%> ==1){
		alert("���������Ч���Ѳ���10��,�뼰ʱ�޸�!");
	}
}

function expandMenu(menuContainer,subContainer,menuLeft,menuTop) {
	
    // Hide all submenus thats's not below the current level
    doHide = false;
  	if (menuContainer != menuTree) {
    	if (ie4) {
     		var menuLayers = document.all.tags("DIV");
      		for (i=0; i<menuLayers.length; i++) {
        		if ((menuLayers[i].id.indexOf(menuContainer) != -1) && (menuLayers[i].id != menuContainer)) {
          			hideObject(menuLayers[i].id);
        		}
      		}
    	}else if (ns4) {
      		for (i=0; i<document.layers.length; i++) {
        		var menuLayer = document.layers[i];
        		if ((menuLayer.id.indexOf(menuContainer) != -1) && (menuLayer.id != menuContainer)) {
          			menuLayer.visibility = "hide";
        		}
      		}
    	}
	}
  	// If this is item has a submenu, display it, or it it's a toplevel menu, open it
  	if (subContainer) {
    	if ((menuLeft) && (menuTop)) {
        	positionObject(subContainer,menuLeft,menuTop);
        	hideAll();
    	} else {
      		if (ie4) {
          		positionObject(subContainer, document.all[menuContainer].offsetWidth + document.all[menuContainer].style.pixelLeft - 10, mouseY);
      		} else {
          		positionObject(subContainer, document.layers[menuContainer].document.width + document.layers[menuContainer].left + 50, mouseY);
      		}
    	}
    	showObject(subContainer);
    	menuTree = subContainer;
  	}
  	showObject("DivShim");
}

function showObject(obj) {
  	if (ie4) { 
  	  	document.all[obj].style.visibility = "visible"; 
  	}else if (ns4) {
  	  	document.layers[obj].visibility = "show";  
  	}
}

function hideObject(obj) {
  	if (ie4) { 
  	  	document.all[obj].style.visibility = "hidden"; 
  	}else if (ns4) { 
  	  	document.layers[obj].visibility = "hide"; 
  	}
}

function positionObject(obj,x,y) {
  	if (ie4) {
    	var foo = document.all[obj].style;
    	foo.left = x;
    	foo.top = y;
  	}else if (ns4) {
    	var foo = document.layers[obj];
    	foo.left = x;
    	foo.top = y;
   }
}

function hideAll() {
 	if (ie4) {
    	var menuLayers = document.all.tags("DIV");
    	for (i=0; i<menuLayers.length; i++) {
      		if (menuLayers[i].id.indexOf(menuPrefix) != -1) {
        		hideObject(menuLayers[i].id);
      		}
    	}
  	}else if (ns4) {
    	for (i=0; i<document.layers.length; i++) {
      		var menuLayer = document.layers[i];
      		if (menuLayer.id.indexOf(menuPrefix) != -1) {
        		hideObject(menuLayer.id);
      		}
    	}
  	}
}

function hideMe(hide) {
    hideObject("DivShim");
    hideMe1(hide);
}
function hideMe1(hide) {
    if (hide) {
        if (doHide) { 
            hideAll(); 
        }
    }else {
        doHide = true;
        hideTimer = window.setTimeout("hideMe1(true);", 100);
    }
}
function mouseMove(e) {
  	if (ie4) { 
  	  	mouseY = window.event.y; 
  	}
  	if (ns4) { 
  	  	mouseY = e.pageY; 
  	}
}

function itemHover(obj,src,text,style) {
  	if (ns4) {
    	var text = '<nobr><a href="' + src + '" class="' + style + '">' + text + '<\/a><\/nobr>'
    	obj.document.open();
    	obj.document.write(text);
    	obj.document.close();
  	}
}

onload = init;
//-->
</SCRIPT>
<style type=text/css>
<!--
  .menu                   { position: absolute; left: 0; top: 2;
                             visibility: hidden; background: #FFFFFF;
                             width: 100px; margin: 0px 0px; padding: 0px 0px;
                             border: 1px silver solid;
                             overflow: visible; ; clip:     rect(   )}
.menu a:visited { font-family: "Tahoma"; font-size: 11px;
                             text-decoration: none; font-weight: normal;
                             color: black; background: #E2E2E2;
                             width: 158px; padding-left: 8px; ; line-height: 22px; border-color: #FFFFFF #666666 #333333 #999999; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; clip:  rect(   )}
  .menu a:hover            { font-family: "Tahoma"; font-size: 11px;
                             text-decoration: none; font-weight: normal;
                             color: #FFFFFF; background: #9999FF;
                             width: 158px; padding-left: 8px; ; line-height: 22px; clip:  rect(   )}
  .menu .border            { border: 1px solid #F4F4F4;
                             border-bottom: 1px solid #808080;
                             border-right: 1px solid #808080; }
  .menu .text              { overflow: hidden;
                             width: 125px; height: 12px; }
  .menu .arrow             { overflow: hidden;
                             width: 15px; height: 12px;
                             padding-left: 5px; padding-top: 3px; }
  .menu .arrow img         { width: 6px; height: 7px;
                             border: 0px; }
  .menu a                  { font-family: "Tahoma"; font-size: 11px;
                             text-decoration: none; font-weight: normal;
                             color: black; background: #E2E2E2;
                             width: 158px; padding-left: 8px;  ; clip:  rect(   ); border-color: #FFFFFF #666666 #333333 #999999; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; line-height: 22px}
  .menu a:link             {  color: #000000; text-decoration: none}

.a {  font-size: 12px; text-decoration: none}
a:link {  color: #FFFFFF; text-decoration: none}
a:hover {  text-decoration: underline}
a:visited {  color: #FFFFFF; text-decoration: none}
-->
</style>
<!--
Added By Rox
.menu a:hover --- background   --  �����˵��ķ���ɫ
.menu a       --- background   --  �����˵���ɫ
-->
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" >
	<table width="780" border="0" cellspacing="0" cellpadding="0" align="center">
  		<tr>
    		<td>
      			<img src="/ToucsMonitor/ToucsMonitor/images/logo1.jpg" width="780" height="40">
    		</td>
  		</tr>
	</table>

	<table border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333" width="778" class="a">
  		<tr bgcolor="#003399" align="center" valign="middle"> 
    		<td ><a href="#;"  onMouseOver="expandMenu(null,'menu1',getPos(this,'Left'),getPos(this,'Top')+this.offsetHeight);"  class="a" onMouseOut="hideMe();">ϵͳ����</a></td>
    		<td ><a href="#;"  onMouseOver="expandMenu(null,'menu2',getPos(this,'Left'),getPos(this,'Top')+this.offsetHeight);"  class="a" onMouseOut="hideMe();">�������</a></td>
    		<td ><a href="#;"  onMouseOver="expandMenu(null,'menu3',getPos(this,'Left'),getPos(this,'Top')+this.offsetHeight);"  class="a" onMouseOut="hideMe();">���̹���</a></td>
    		<td ><a href="#;"  onMouseOver="expandMenu(null,'menu5',getPos(this,'Left'),getPos(this,'Top')+this.offsetHeight);"  class="a" onMouseOut="hideMe();">POS ����</a></td>
			<td ><a href="#;"  onMouseOver="expandMenu(null,'menu6',getPos(this,'Left'),getPos(this,'Top')+this.offsetHeight);"  class="a" onMouseOut="hideMe();">������֧������</a></td>

    		<!--���Ը�����������������Ĳ˵�...-->
    		<td width=120><a href="/ToucsMonitor/ToucsMonitor/" class="a" >�û�(<%=uid%>)���µ�¼</a></td>
  		</tr>
	</table>
<script language="JavaScript">
function getPos(el,sProp) { 
    var iPos = 0
    while (el!=null) {
        iPos+=el["offset" + sProp]
        el = el.offsetParent
    }
    return iPos
}
</script>
<iframe id="DivShim" src="javascript:false;" scrolling="no" frameborder="0" style="position:absolute;top:60px;left:0px;width:778;height:400;filter=progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);"></iframe>

<!-- System -->
<div id="menu1" class="menu" onMouseOut="hideMe();" style="">
	<a href="/ToucsMonitor/codeman?reqCode=17004"              target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">ϵͳ�������</a><br>
	<a href="/ToucsMonitor/bankcodeman?reqCode=17204"          target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">�������Ĵ������</a><br>
	<a href="/ToucsMonitor/parametersetservlet?reqCode=16004"  target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">�������</a><br>
	<a href="/ToucsMonitor/txncodeman?reqCode=17104"           target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">������ά�� </a><br>
	<a href="/ToucsMonitor/userMngServlet?reqCode=10104"       target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">��������</a><br>
	<a href="/ToucsMonitor/userMngServlet?reqCode=14001"       target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">��־����</a><br>
	<a href="/ToucsMonitor/userMngServlet?reqCode=10204"       target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">�û�����</a><br>
	<a href="/ToucsMonitor/userMngServlet?reqCode=10304"       target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">��ɫ����</a><br>
	<a href="/ToucsMonitor/userMngServlet?reqCode=19001"       target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">�޸�����</a>
	<a href="/ToucsMonitor/dccctrl?reqCode=17801&target=page"  target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">������Ϣ����</a><br>
	<a href="/ToucsMonitor/dccctrl?reqCode=17804&target=page"  target="main" style='color:#003399' onMouseOver="expandMenu('menu1');">������Ϣ��ѯ</a><br>
</div>
<!-- Actor -->
<div id="menu2" class="menu" onMouseOut="hideMe();" style="">
	<a href="/ToucsMonitor/actmerchant?reqCode=17501&target=page" target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����̻�����</a><br>
	<a href="/ToucsMonitor/actmerchant?reqCode=17504&target=page" target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����̻���ѯ</a><br>
	<a href="/ToucsMonitor/actdevice?reqCode=17701&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����豸����</a><br>
	<a href="/ToucsMonitor/actdevice?reqCode=17704&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����豸��ѯ</a><br>
	<a href="/ToucsMonitor/actteller?reqCode=17601&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����Ա����</a><br>
	<a href="/ToucsMonitor/actteller?reqCode=17604&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu2');">�����Ա��ѯ</a><br>
</div>
<!-- BankFutures -->
<div  id="menu3" class="menu" onMouseOut="hideMe();"> 
	<a href="/ToucsMonitor/bffc?reqCode=17901&target=page"         target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">�̲ݹ�˾����</a><br>
	<a href="/ToucsMonitor/bffc?reqCode=17904&target=page"         target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">�̲ݹ�˾��ѯ</a><br>
	<a href="/ToucsMonitor/bfinvestor?reqCode=17911&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">�̲ݹ�˾�ͻ�����</a><br>
	<a href="/ToucsMonitor/bfinvestor?reqCode=17914&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">�̲ݹ�˾�ͻ���ѯ</a><br>
	<a href="/ToucsMonitor/bfteller?reqCode=17921&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">���̹�Ա����</a><br>
	<a href="/ToucsMonitor/bfteller?reqCode=17924&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">���̹�Ա��ѯ</a><br>
	<a href="/ToucsMonitor/bffc?reqCode=17907&target=page"         target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">����ͳ��</a><br>
	<a href="/ToucsMonitor/bfCardservlet?reqCode=17404"            target="main" style='color:#003399' onMouseOver="expandMenu('menu3');">�������</a><br>
</div>

<!-- POS -->
<div id="menu5" class="menu" onMouseOut="hideMe();"> 
	<a href="/ToucsMonitor/posmerchantconfig?reqCode=10401&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">������̻�����</a><br>
<!-- 
	<a href="/ToucsMonitor/posmerchantconfig?reqCode=10402&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">������̻��޸�</a><br>
 -->		
	<a href="/ToucsMonitor/posmerchantconfig?reqCode=10404&target=page"   target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">������̻���ѯ</a><br>
	<a href="/ToucsMonitor/posdeviceconfig?reqCode=13101&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">�����POS����</a><br>
<!--  
	<a href="/ToucsMonitor/posdeviceconfig?reqCode=13102&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">�����POS�޸�</a><br>
-->	
	<a href="/ToucsMonitor/posdeviceconfig?reqCode=13103&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">�����POS��ѯ</a><br>
	<a href="/ToucsMonitor/POSDeviceManage"                               target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">POS�豸����</a><br>
	<a href="/ToucsMonitor/checkprize?reqCode=10422"                      target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">POS�齱����</a><br>
	<a href="/ToucsMonitor/parametersetservlet?reqCode=16005"             target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">POS���俨����</a><br>
<!-- 
	<a href="/ToucsMonitor/posenrollconfig?reqCode=13601&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">ǩԼPOS����</a><br>
	<a href="/ToucsMonitor/posenrollconfig?reqCode=13603&target=page"     target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">ǩԼPOS��ѯ</a><br>
 	<a href="/ToucsMonitor/posMoney"     		 						  target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�豸����(�����ϴ�)</a><br>		
 -->	
	<a href="/ToucsMonitor/posMoney?reqCode=14201&target=page"     		  target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�豸����</a><br>
	<a href="/ToucsMonitor/posMoney?reqCode=14203&target=page"            target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�豸��ѯ</a><br>
	
	<a href="/ToucsMonitor/posLcCard?reqCode=14707&target=page"           target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS������Ϣ�ļ��ϴ�</a><br>
	<a href="/ToucsMonitor/posLcCard?reqCode=14703&target=page"           target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�������</a><br>
	
	<a href="/ToucsMonitor/posTransferAccounts?reqCode=14501&target=page" target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POSת�˽�����</a><br>
	<a href="/ToucsMonitor/posTransferAccounts?reqCode=14503&target=page" target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POSת�˽���ѯ</a><br>


	<a href="/ToucsMonitor/posPoundage?reqCode=14601&target=page"     	  target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS���������</a><br>
	<a href="/ToucsMonitor/posPoundage?reqCode=14603&target=page"         target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�����Ѳ�ѯ</a><br>

	<a href="/ToucsMonitor/posInAccount?reqCode=14301&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�ڲ��˻����</a><br>
	<a href="/ToucsMonitor/posInAccount?reqCode=14303&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">���POS�ڲ��˻���ѯ</a><br>

	<a href="/ToucsMonitor/posaccountconfig?reqCode=13701&target=page"    target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">�Թ��ʻ�����</a><br>
	<a href="/ToucsMonitor/posaccountconfig?reqCode=13703&target=page"    target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">�Թ��ʻ���ѯ</a><br>
	
	<a href="/ToucsMonitor/posBCCompany?reqCode=16901&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">Ԥ������˾����</a><br>
	<a href="/ToucsMonitor/posBCCompany?reqCode=16904&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">Ԥ������˾��ѯ</a><br>
	<a href="/ToucsMonitor/posBCCard?reqCode=16804&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">Ԥ�����������</a><br>
	<a href="/ToucsMonitor/posBCCard?reqCode=16807&target=page"        target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">Ԥ����������ϴ�����</a><br>
<!--  	
	<a href="/ToucsMonitor/posicservlet?reqCode=13001"                    target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC����������</a><br>
	<a href="/ToucsMonitor/posicservlet?reqCode=13004"                    target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC�������ѯ</a><br>
	<a href="/ToucsMonitor/poscompany?reqCode=16501&target=page"          target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC����˾����</a><br>
	<a href="/ToucsMonitor/poscompany?reqCode=16504&target=page"          target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC����˾��ѯ</a><br>
	<a href="/ToucsMonitor/poscompany?reqCode=16507&target=page"          target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC����˾����ͳ��</a><br>
	<a href="/ToucsMonitor/poscompany?reqCode=16509&target=page"          target="main" style='color:#003399' onMouseOver="expandMenu('menu5');">��ҵIC����˾������ϸ</a><br>
-->
</div>
<!-- ������֧�� -->
<div id="menu6" class="menu" onMouseOut="hideMe();">
	<a href="/ToucsMonitor/thirdPartyMerchant?reqCode=13401&target=page"  target="main" style='color:#003399' onMouseOver="expandMenu('menu6');">�������̻�����</a><br> 
	<a href="/ToucsMonitor/thirdPartyMerchant?reqCode=13404&target=page"  target="main" style='color:#003399' onMouseOver="expandMenu('menu6');">�������̻���ѯ</a><br> 
	<a href="/ToucsMonitor/thirdPartyTeller?reqCode=13201&target=page"    target="main" style='color:#003399' onMouseOver="expandMenu('menu6');">��������Ա����</a><br>
	<a href="/ToucsMonitor/thirdPartyTeller?reqCode=13204&target=page"    target="main" style='color:#003399' onMouseOver="expandMenu('menu6');">��������Ա��ѯ</a><br>
</div>

<!--���Ը�����������������Ĳ˵�...-->
<table width="780" border="1" cellspacing="0"  cellpadding="0" height="85%" align="center">
  <tr>
    <td>
      <IFRAME name="main" MARGINHEIGHT="0" MARGINWIDTH="0" FRAMEBORDER="0"   WIDTH = "100%" height="100%" SCROLLING="auto"  SRC="ToucsMonitor/welcome.html" >
      </IFRAME>
    </td>
  </tr>
</table>
</body>
</html>