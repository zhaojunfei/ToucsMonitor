<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.style5 {color: #990000}
.style6 {
	font-size: 16px;
	color: #0000FF;
	font-weight: bold;
}
.style7 {font-size: 14px}
.style8 {color: #990000; font-size: 14px; }
.style12 {font-size: 12px; color: #FF6600; }
.style13 {font-size: +7}
-->
</style>
</head>
<%
   String uid=request.getParameter("uid");
   DeviceManageBean DMB = new DeviceManageBean();
   String reAtmId =request.getParameter("RAtmId");
   DMB.getTimerConfig();
   Vector Pov1 = new Vector();
   Vector Pov2 = new Vector();
   Vector Pov3 = new Vector();
   Vector Pov4 = new Vector();
   Vector Pov5 = new Vector();
   Vector Pov6 = new Vector();
   Vector Pov7 = new Vector();
   Vector Pov8 = new Vector();
   Vector UnPov1 = new Vector();
   Vector UnPov2 = new Vector();  
   Vector UnPov3 = new Vector();
   Vector UnPov4 = new Vector();  
   Vector UnPov5 = new Vector();
   Vector UnPov6 = new Vector();  
   Vector UnPov7 = new Vector();
   Vector UnPov8 = new Vector();  
   
   if (!reAtmId.equals("00")){      
   	  //Pov1 = (Vector)DMB.PowAtm1;
	  Pov1 = (Vector)request.getAttribute("AtmPower1");
	  Pov2 = (Vector)request.getAttribute("AtmPower2");
	  Pov3 = (Vector)request.getAttribute("AtmPower3");
	  Pov4 = (Vector)request.getAttribute("AtmPower4");
	  Pov5 = (Vector)request.getAttribute("AtmPower5");
	  Pov6 = (Vector)request.getAttribute("AtmPower6");
	  Pov7 = (Vector)request.getAttribute("AtmPower7");
	  Pov8 = (Vector)request.getAttribute("AtmPower8");	
	  UnPov1 = (Vector)request.getAttribute("AtmUnPower1");
	  UnPov2 = (Vector)request.getAttribute("AtmUnPower2");  	
	  UnPov3 = (Vector)request.getAttribute("AtmUnPower3");  	
	  UnPov4 = (Vector)request.getAttribute("AtmUnPower4");  	
	  UnPov5 = (Vector)request.getAttribute("AtmUnPower5");  	
	  UnPov6 = (Vector)request.getAttribute("AtmUnPower6");  	
	  UnPov7 = (Vector)request.getAttribute("AtmUnPower7");  	
	  UnPov8 = (Vector)request.getAttribute("AtmUnPower8");  		  
   }
     
   Vector oV  = new Vector();
   Vector oV1 = new Vector();
   Vector oV2 = new Vector();
   Hashtable Res=new Hashtable();   
   oV  = (Vector)DMB.getUnselectPower();
   oV1 = (Vector)DMB.getAllAtmcode(); 
   oV2 = (Vector)DMB.getAllAtmAddrType();                 
   int i;
%>
<script language="javascript">
function onChecktype(){
   if(document.form1.checkbox.checked){
		document.all("Atmtype").style.display ="";
		document.form1.ischecktype.value      ="1";
	}else{
		document.all("Atmtype").style.display ="none";
		document.form1.ischecktype.value      ="0";
	}
}

function onCheck2Click(){
  	if (document.form1.checkbox2.checked){
	   document.form1.isTotalDay.value        ="1";
	   document.form1.timer2selected.disabled =true;
	   document.form1.timer3selected.disabled =true;
	   document.form1.timer4selected.disabled =true;
	   document.form1.timer5selected.disabled =true;
	   document.form1.timer6selected.disabled =true;
	   document.form1.timer7selected.disabled =true;
	   document.form1.timer8selected.disabled =true;
	   document.form1.timer2unselect.disabled =true;
	   document.form1.timer3unselect.disabled =true;
	   document.form1.timer4unselect.disabled =true;
	   document.form1.timer5unselect.disabled =true;
	   document.form1.timer6unselect.disabled =true;
	   document.form1.timer7unselect.disabled =true;
	   document.form1.timer8unselect.disabled =true;
	   document.form1.Button3.disabled        =true;
	   document.form1.Submit23.disabled       =true;
	   document.form1.Button4.disabled        =true;
	   document.form1.Submit24.disabled       =true;    
	   document.form1.Button5.disabled        =true;
	   document.form1.Submit25.disabled       =true;  
	   document.form1.Button6.disabled        =true;
	   document.form1.Submit26.disabled       =true;     
	   document.form1.Button7.disabled        =true;
	   document.form1.Submit27.disabled       =true;  
	   document.form1.Button8.disabled        =true;
	   document.form1.Submit28.disabled       =true;
	   document.form1.Button.disabled         =true;
	   document.form1.Submit2.disabled        =true;      
  	}else{
   		document.form1.isTotalDay.value        ="0";
   		document.form1.timer2selected.disabled =false;
  	 	document.form1.timer3selected.disabled =false;
   		document.form1.timer4selected.disabled =false;
   		document.form1.timer5selected.disabled =false;
   		document.form1.timer6selected.disabled =false;
   		document.form1.timer7selected.disabled =false;
   		document.form1.timer8selected.disabled =false;
   		document.form1.timer2unselect.disabled =false;
   		document.form1.timer3unselect.disabled =false;
   		document.form1.timer4unselect.disabled =false;
   		document.form1.timer5unselect.disabled =false;
   		document.form1.timer6unselect.disabled =false;
   		document.form1.timer7unselect.disabled =false;
   		document.form1.timer8unselect.disabled =false;  
   		document.form1.Button3.disabled        =false;
   		document.form1.Submit23.disabled       =false;
   		document.form1.Button4.disabled        =false;
   		document.form1.Submit24.disabled       =false;
   		document.form1.Button5.disabled        =false;
   		document.form1.Submit25.disabled       =false;
   		document.form1.Button6.disabled        =false;
   		document.form1.Submit26.disabled       =false;
   		document.form1.Button7.disabled        =false;
   		document.form1.Submit27.disabled       =false;
   		document.form1.Button8.disabled        =false;
   		document.form1.Submit28.disabled       =false;
   		document.form1.Button.disabled         =false;
   		document.form1.Submit2.disabled        =false;
  	}   
}

function onAtmchange(){
   	//document.form1.isAtmchanged.value =document.form1.atmid.options[atmid.selectedIndex].value;   
	document.form1.atmid.disabled =true;
   	for (i=window.form1.atmid.length-1; i>=0; i--){ 	   
	  	if (window.form1.atmid.options[i].selected){		      
	    	document.form1.isAtmchanged.value =window.form1.atmid.options[i].value;
	   	}	
	}     
   	window.location.href="/ToucsMonitor/deviceconfig?reqCode=21010&uid=<%=uid%>&isAtm=222&Atmcode="+document.form1.isAtmchanged.value; 
   	//doSubmit();            				
}

function SelectAll(ObjName){
    ObjID = GetObjID(ObjName);
    i=window.form1.elements[ObjID].length
    for (i=window.form1.elements[ObjID].length-1; i>=0; i--){
        window.form1.elements[ObjID].options[i].selected=true;}
}

function doSubmit(){
    SelectAll('timer1selected');
	SelectAll('timer2selected');
    SelectAll('timer3selected');
	SelectAll('timer4selected');
    SelectAll('timer5selected');
	SelectAll('timer6selected');
    SelectAll('timer7selected');
	SelectAll('timer8selected');	
    //document.checkpztable.submit();
}

function GetObjID(ObjName){
	for (var ObjID=0; ObjID < window.form1.elements.length; ObjID++)
    	if ( window.form1.elements[ObjID].name == ObjName ){  
        	return(ObjID);
       		break;
    	}
  	return(-1);
}

function DeleteItem(ObjName,DesName){
	ObjID = GetObjID(ObjName);
  	DesID = GetObjID(DesName);
 	j=window.form1.elements[DesID].length;
  	minselected=0;
  	if ( ObjID != -1 ){
    	for (i=window.form1.elements[ObjID].length-1; i>=0; i--){  
        	if (window.form1.elements[ObjID].options[i].selected){ // window.alert(i);
          		if (minselected==0 || i<minselected)
            		minselected=i;
          	Code = document.form1.elements[ObjID].options[i].value;
          	Text = document.form1.elements[ObjID].options[i].text;
         	window.form1.elements[DesID].options[j] = new Option(Text, Code);;
          	j=j+1;
          	window.form1.elements[ObjID].options[i] = null;
       	}
    }
    i=window.form1.elements[ObjID].length;

    if (i>0)  {
        if (minselected>=i)
           minselected=i-1;
           window.form1.elements[ObjID].options[minselected].selected=true;
        }
  	}
}

</script>
<body> 
<div align="center" class="style6">
  ATM 时 段 权 限 设 置 页
</div>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig?reqCode=21010&uid=<%=uid%>" onSubmit="return doSubmit();">
<div align="center">
	<table width="713" height="1144" border="0" align="center">
    	<tr>
        	<th width="156" height="52" rowspan="2" ><div align="right" class="style7">ATM号:</div></th>
        	<th width="182" rowspan="2" scope="col"><div align="left"><span class="style6">
        	<select name="atmid" id="select7" style="width:100;" onChange="javascript:onAtmchange()" >
          	<option value="请选择">请选择ATM号</option>
          	<%
				i=0;			
				while(i<oV1.size()){
					Res=(Hashtable)oV1.get(i);
					i++;
			%>
          	<option value="<%=Res.get("atm_code")%>"  <%if ( reAtmId.equals(Res.get("atm_code"))){%> selected <%}%>><%=Res.get("atm_code")%></option>
          	<%
			 	}
			%>
        	</select>
        	</span>
        	</div></th>
        	<th width="118" rowspan="2" >
          	<div align="left" class="style7">
            <input type="checkbox" name="checkbox" value="checkbox" class="radio" onClick="javascript:onChecktype()"> 按种类
            </div></th>
        	<th  width="222" height="30">		  
        	<select name="Atmtype" id="Atmtype" style="width:120;display:none">
          	<option value="请选择">请选择ATM类别</option>
		    <%
			  i=0;			
			  while(i<oV2.size()){
				Res=(Hashtable)oV2.get(i);
				i++;
			%>
            <option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
            <%
			 }
			%>
        	</select></th>
        	<th width="13" rowspan="2" ><span class="style13">
        	</span></th>
      	</tr>
      	<tr>
        	<th height="34"><span class="style13">
          	<input type="checkbox" name="checkbox2" value="checkbox" class="radio" onClick="javascript:onCheck2Click()">
          	<span class="style7">按一个时段设置全天权限</span>
          	<input name="isAtmchanged" type="hidden" id="isAtmchanged4" value="0">
        	</span></th>
      	</tr>
      	<tr>
        	<td rowspan="2"><div align="center">
          	<p class="style7">时段一权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer1begin%>到<%=DMB.Atimer1end%> )</span></p>
        	</div>
        	</td>
        	<td>        
        	<div align="center" class="style8">已添加的权限</div></td>
        	<td rowspan="2"><p align="center" class="style7">&nbsp;
  			</p>
          	<p align="center" class="style7">
            <input type="button" name="Button2" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer1unselect','timer1selected')" style="height:20;width:50" >
			</p>
          	<p align="center" class="style7">
            <input type="button" name="Submit22" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer1selected','timer1unselect')" style="height:20;width:50" >
			</p>
			</td>
        	<td width="222" ><div align="center" class="style7"><span class="style5"> 可供选择的权限 </span></div></td>
        	<td rowspan="2">&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="117" >
          	<div align="center">
            <select name="timer1selected" size="6" multiple  style="width:150;">
	    	<%
				if (!reAtmId.equals("00") && Pov1!=null){
		    		i=0;			
					while(i<Pov1.size()){
						Res=(Hashtable)Pov1.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
             <%
			 		}
		 		}else{	 		  
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 
			%>          
            </select>
          	</div></td><td  width="222" >
		    <div align="center">
			<select name="timer1unselect" size="6" multiple style="width:150;height:100;border:1 ">
            <%
		    	if (!reAtmId.equals("00") && UnPov1!=null){
		     		i=0;			
		  	 		while(i<UnPov1.size()){
						Res=(Hashtable)UnPov1.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}	
		 	%>
            </select>  
	        </div>
	        </td>
      	</tr>
      	<tr>
        	<td><div align="center">
          	<p class="style7">时段二权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer2begin%>到<%=DMB.Atimer2end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer2selected" size="6" multiple id="timer2selected" style="width:150;">
			<%
		    	if (!reAtmId.equals("00") && Pov2!=null){
		      		i=0;			
			  		while(i<Pov2.size()){
						Res=(Hashtable)Pov2.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		    	}else{	 		  
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>                 
            </select>
          	</div></td>
        	<td><p align="center">&nbsp;
			</p>
          	<p align="center">
            <input type="button" name="Button3" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer2unselect','timer2selected')" style="height:20;width:50;font-size:12" >
          	</p>
          	<p align="center">
            <input type="button" name="Submit23" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer2selected','timer2unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer2unselect" size="6" multiple  style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov2!=null){
		    		i=0;			
					while(i<UnPov2.size()){
						Res=(Hashtable)UnPov2.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
       		</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td><div align="center">
          	<p class="style7">时段三权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer3begin%>到<%=DMB.Atimer3end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer3selected" size="6" multiple id="timer3selected" style="width:150;">
            <%
		    	if (!reAtmId.equals("00") && Pov3!=null){
		      		i=0;			
			  		while(i<Pov3.size()){
						Res=(Hashtable)Pov3.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	}else{	 		  
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>           			                
	        </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button4" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer3unselect','timer3selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit24" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer3selected','timer3unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer3unselect" size="6" multiple id="timer3unselect" style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov3!=null){
		    		i=0;			
					while(i<UnPov3.size()){
						Res=(Hashtable)UnPov3.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td><div align="center">
          	<p class="style7">时段四权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer4begin%>到<%=DMB.Atimer4end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer4selected" size="6" multiple id="select" style="width:150;">
			<%
		    	if (!reAtmId.equals("00") && Pov4!=null){
		      		i=0;			
			  		while(i<Pov4.size()){
						Res=(Hashtable)Pov4.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	} else {	 		  
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>    
            </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button5" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer4unselect','timer4selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit25" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer4selected','timer4unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer4unselect" size="6" multiple id="timer4unselect" style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov4!=null){
		    		i=0;			
					while(i<UnPov4.size()){
						Res=(Hashtable)UnPov4.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td><div align="center">
          	<p class="style7">时段五权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer5begin%>到<%=DMB.Atimer5end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer5selected" size="6" multiple id="timer5selected" style="width:150;">
            <%
		    	if (!reAtmId.equals("00") && Pov5!=null){
		      		i=0;			
			  		while(i<Pov5.size()){
						Res=(Hashtable)Pov5.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	} else{ 	 		  
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>    
		    </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button6" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer5unselect','timer5selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit26" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer5selected','timer5unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer5unselect" size="6" multiple id="timer5unselect" style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov5!=null){
		   			i=0;			
					while(i<UnPov5.size()){
						Res=(Hashtable)UnPov5.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td><div align="center">
         	<p class="style7">时段六权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer6begin%>到<%=DMB.Atimer6end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer6selected" size="6" multiple id="timer6selected" style="width:150;">
            <%
		    	if (!reAtmId.equals("00") && Pov6!=null){
		      		i=0;			
			  		while(i<Pov6.size()){
						Res=(Hashtable)Pov6.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	}else{ 	 		  
			 		i=0;			
			 		while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>    
		    </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button7" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer6unselect','timer6selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit27" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer6selected','timer6unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer6unselect" size="6" multiple id="timer6unselect" style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov6!=null){
		    		i=0;			
					while(i<UnPov6.size()){
						Res=(Hashtable)UnPov6.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td><div align="center">
          	<p class="style7">时段七权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer7begin%>到<%=DMB.Atimer7end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer7selected" size="6" multiple id="timer7selected" style="width:150;">
            <%
		    	if (!reAtmId.equals("00") && Pov7!=null){
		      		i=0;			
			  		while(i<Pov7.size()){
						Res=(Hashtable)Pov7.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	}else{	 		  
		     		i=0;			
			 		while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		   		}	 		 	 
		  	%>    
		    </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button8" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer7unselect','timer7selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit28" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer7selected','timer7unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer7unselect" size="6" multiple id="timer7unselect" style="width:150;">
            <%
				if (!reAtmId.equals("00") && UnPov7!=null){
		    		i=0;			
					while(i<UnPov7.size()){
						Res=(Hashtable)UnPov7.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
     		</tr>
      		<tr>
        	<td><div align="center">
          	<p class="style7">时段八权限：</p>
          	<p class="style7"><span class="style12">( 时间从<%=DMB.Atimer8begin%>到<%=DMB.Atimer8end%> )</span></p>
        	</div></td>
        	<td>
          	<div align="center">
            <select name="timer8selected" size="6" multiple id="timer8selected" style="width:150;">
            <%
		    	if (!reAtmId.equals("00") && Pov8!=null){
		      		i=0;			
			  		while(i<Pov8.size()){
						Res=(Hashtable)Pov8.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			   		}
		     	}else{	 		  
			  		i=0;			
			  		while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
			%>
          	<option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
          	<%
			 		}
		   		}	 		 	 
		 	%>    
		    </select>
          	</div></td>
        	<td><p align="center">&nbsp;          </p>
          	<p align="center">
            <input type="button" name="Button" value="&lt;&lt;&lt;添加" onClick="JavaScript:DeleteItem('timer8unselect','timer8selected')" style="height:20;width:50;font-size:12" >
			</p>
          	<p align="center">
            <input type="button" name="Submit2" value="删除&gt;&gt;&gt;" onClick="JavaScript:DeleteItem('timer8selected','timer8unselect')" style="height:20;width:50;font-size:12" >
			</p></td>
        	<td width="222"><div align="center">
          	<select name="timer8unselect" size="6" multiple id="timer8unselect" style="width:150;">
		    <%
				if (!reAtmId.equals("00") && UnPov8!=null){
		    		i=0;			
					while(i<UnPov8.size()){
						Res=(Hashtable)UnPov8.get(i);
						i++;
			%>
            <option value="<%=Res.get("location")%>"><%=Res.get("txn_name")%></option>
            <%
			 		}
		 		}
		 	%>
          	</select>
        	</div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="27" colspan="5">
          	<div align="right">
            <input type="hidden" name="isTotalDay" value="0">
            <input type="hidden" name="ischecktype" value="0">          
            <input type="submit" name="Submit" value="确定">
            <input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=21010&uid=<%=uid%>'">
          	</div></td>
      	</tr>
      	<tr>
        	<td height="23" colspan="4">&nbsp;</td>
        	<td height="23"></td>
      	</tr>
      	<tr>
        	<td height="98" colspan="5">
        	<div align="right">  </div>          <p align="center">&nbsp;          </p>          <p align="center">&nbsp;            </p>          <p align="center">&nbsp;          </p></td>
      	</tr>
    </table>
</div>
<div align="left"></div>
</form>
</body>
</html>
