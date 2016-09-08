<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<%! 
private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else 
				flag="";
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
	<title>新增商户</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
	<link rel="stylesheet" href="/v5_css.css" type="text/css">
	<style type="text/css">
	<!--
	.style3 {color: #0000FF}
	-->
	</style>
</head>
<script language="javascript" src="/ToucsMonitor/js/power.js">
</script>
<script language="javascript">
function onCondition(){
	if(document.checkpztable.merchantIsOk.checked){
		document.all.merchantok.style.display="";
		document.checkpztable.ischeckedMercnt.value="ok";
	}else{
		document.all.merchantok.style.display="none";
		document.checkpztable.ischeckedMercnt.value="no";
	}
}

function onTypechange(){
	j = 0;   
  	for (i=window.checkpztable.newcardtype.length-1; i>=0; i--){
    	if (window.checkpztable.newcardtype.options[i].text =="本地龙卡个人卡"){		      
		 	j = 1;		 
	   	}
  	}	
  	if ( j == 1){
     	document.checkpztable.availcheckprize.disabled = false;
  	}else{
     	document.checkpztable.availcheckprize.disabled = true;  
	 	window.checkpztable.newcheckprize.length = 0;
   	}     	 	 
}

function onCondition2(){
	if(document.checkpztable.IsmerchantTypeOk.checked){		
		document.checkpztable.ischeckedMercntTypeOk.value="ok";
	}else{
		document.checkpztable.ischeckedMercntTypeOk.value="no";
	}
}


function onOrgChange(){
	//document.forms[0].org_id.value= document.forms[0].org_name.value;
}

function onPTChange(){
	//document.forms[0].manage_bankno.value= document.forms[0].prizetypevalue.value;
}

function doSubmit(){
    if( isNull("channel_id","渠道号"))           return false; 	
	if( isNull("PZbegin_date","开始日期"))       return false;
	if( isNull("PZend_date","结束日期"))         return false;
	if( isNull("PZbegin_time","开始时间"))       return false;	
	if( isNull("PZend_time","结束时间"))         return false;
	time = document.forms[0].PZbegin_time.value;
	if (time < "020000" || time > "230000"){
		alert("抽奖时间必须在02:00～23：00之间");
		return false; 
	}
	time = document.forms[0].PZend_time.value;
	if (time < "020000" || time > "230000"){
		alert("抽奖时间必须在02:00～23：00之间");
		return false; 
	}
    if( isNull("PZ_theMinMoney","最低消费金额")) return false;
	if(!isFNumber("PZ_theMinMoney","最低消费金额")) return false;
	if( isNull("PZLeve1bless","一等奖祝词"))     return false;
	if( isNull("PZLeve2bless","二等奖祝词"))     return false;
	if( isNull("PZLeve3bless","三等奖祝词"))     return false;
    if( isNull("PZLeve4bless","四等奖祝词"))     return false;
	if( isNull("PZLeve5bless","五等奖祝词"))     return false;
	if( !isNumberOk("PZLeve1Counts"))            return false;
	if( !isNumberOk("PZLeve2Counts"))            return false;
	if( !isNumberOk("PZLeve3Counts"))            return false;
	if( !isNumberOk("PZLeve4Counts"))            return false;
    if( !isNumberOk("PZLeve5Counts"))            return false;
    if( !checkLength("PZLeve1bless",12,"一等奖别祝辞")) return false;
	if( !checkLength("PZLeve2bless",12,"二等奖别祝辞")) return false;
	if( !checkLength("PZLeve3bless",12,"三等奖别祝辞")) return false;
	if( !checkLength("PZLeve4bless",12,"四等奖别祝辞")) return false;
	if( !checkLength("PZLeve5bless",12,"五等奖别祝辞")) return false;    
	
	SelectAll1('newcheckprize');
	SelectAll1('newcardtype');
	SelectAll1('newmerchantlist');   
	if(confirm("确认输入无误并提交吗？")){
		return true;
	}else{
		return false;
	}	
}

function DeleteItem1(ObjName,DesName){
	ObjID = GetObjID1(ObjName);
  	DesID = GetObjID1(DesName);
  	j=window.checkpztable.elements[DesID].length;
  	if ( ObjName == 'availmerchantlist'){
        if ( j >= 100 ) {
            alert("指定商户个数超过100");
            return false;
        }
  	}
  	minselected=0;
  	if ( ObjID != -1 ){
    	for (i=window.checkpztable.elements[ObjID].length-1; i>=0; i--){  
        	if (window.checkpztable.elements[ObjID].options[i].selected){ // window.alert(i);
          		if (minselected==0 || i<minselected)
            		minselected=i;
	          	Code = document.checkpztable.elements[ObjID].options[i].value;
	          	Text = document.checkpztable.elements[ObjID].options[i].text;
	          	window.checkpztable.elements[DesID].options[j] = new Option(Text, Code);;
	          	j=j+1;
	          	window.checkpztable.elements[ObjID].options[i] = null;
       		}
    	}
    	i=window.checkpztable.elements[ObjID].length;

    	if (i>0)  {
        	if (minselected>=i)
           	minselected=i-1;
           	window.checkpztable.elements[ObjID].options[minselected].selected=true;
        }
  	}
}

function SelectAll1(ObjName){
    ObjID = GetObjID(ObjName);
    i=window.checkpztable.elements[ObjID].length
    for (i=window.checkpztable.elements[ObjID].length-1; i>=0; i--){
        window.checkpztable.elements[ObjID].options[i].selected=true;
    }
}

function GetObjID1(ObjName){
  	for (var ObjID=0; ObjID < window.checkpztable.elements.length; ObjID++)
    	if ( window.checkpztable.elements[ObjID].name == ObjName ){  
        	return(ObjID);
       		break;
    	}
  	return(-1);
}

</script>
<body  bgcolor="#FFFFFF" text="#000000">
<span class="style3">
<%
	String uid=request.getParameter("uid");
    POSMerchantBean PMB=new POSMerchantBean();
	Vector oV  =new Vector();
	Vector oV1 =new Vector();
	Vector oV2 =new Vector();

	Hashtable Res=new Hashtable();
	int i=0;
	oV  = (Vector)PMB.getCardClass();
	oV1 = (Vector)PMB.getCardType(); 
	oV2 = (Vector)PMB.getAllMerchant();

  %>
</span>
<h2 align="center" class="style3"><font face="隶书">抽奖规则新增</font></h2>
<form action="/ToucsMonitor/posmerchantconfig?reqCode=10422&target=checkPZAdded&uid=<%=uid%>" method="post" name="checkpztable" class="style3" onSubmit="javascript:return doSubmit();">
<input type="hidden" name="managebankname_temp" value="">  
	<table height="297" bgcolor="#FFFFFF" id="tab0">
   		<tr>
      		<td colspan="7" nowrap>        <hr noshade></td>
   		</tr>
    	<tr>
      		<td align="right" nowrap >&nbsp;</td>
      		<td align="right" nowrap >&nbsp;</td>
      		<td width="81" nowrap>
      			<div align="center" class="style3">渠道号: </div>
      		</td>
      		<td width="97" nowrap>
      			<select name="channel_id" size="1">
        		<option value="">请选择</option>
        		<%initList("channelIdList","010310",request,out);%>
      			</select>*
      		</td>
      		<td colspan="2" align="right" nowrap>&nbsp;</td>
      		<td width="92" nowrap> </td>
    	</tr>
    	<tr>
      		<td align="right" nowrap class="style3">&nbsp;</td>
      		<td align="right" nowrap class="style3"><div align="right">请选择卡类型</div></td>
    	</tr>
    	<tr valign="top">
      		<td width="85" rowspan="3">
        		<div align="center"></div>
        	</td>
      		<td width="180" rowspan="3">
      			<div align="right"></div>
      		</td>
      		<td rowspan="3">
        		<div align="center">
          		<p>
            	<select  name="newcardtype" size="12" multiple style="width:160pt;height:50pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
            	</select>
         		</p>
      			</div>
      			<div align="center"></div>
      		</td>
      		<td>
      			<div align="center">
        		<input type="button" name="Submit23" value="删除&gt;&gt;&gt;" onClick="javascript:DeleteItem1('newcardtype','availcardtype');onTypechange();" style="height:16pt;width:50pt;font-size:12pt" >
      			</div>
      		</td>
      		<td colspan="2" rowspan="3">        
      			<div align="center">
          		<select name="availcardtype" size="12" multiple style="width:160pt;height:50pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
            	<%
					i=0;			
					while(i<oV1.size()){
						Res=(Hashtable)oV1.get(i);
						i++;
				%>
            	<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
            	<%
			 		}
				%>
         		</select>
				</div>
			</td>
    	</tr>
    	<tr valign="top">
      		<td>
      			<div align="center">
        		<input type="button" name="Button3" value="&lt;&lt;&lt;添加" onClick="javascript:DeleteItem1('availcardtype','newcardtype');onTypechange();" style="height:16pt;width:50pt;font-size:12pt" >
      			</div>
      		</td>
    	</tr>
    	<tr valign="top">
      		<td height="20">&nbsp;</td>
    	</tr>
    	<tr>
      		<td align="right" nowrap class="style3">&nbsp;</td>
      		<td align="right" nowrap class="style3"><div align="right">请选择卡种类</div></td>
    	</tr>
    	<tr valign="top">
      		<td width="85" rowspan="3">
        		<div align="center"></div>
        	</td>
      		<td width="180" rowspan="3">&nbsp;</td>
      		<td rowspan="3">
        		<div align="center">
          		<p>
            		<select name="newcheckprize" size="10" multiple style="width:160pt;height:50pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
            		</select>
          		</p>
      			</div>        
      			<div align="center"></div>
      		</td>
      		<td>
      			<div align="center">
        		<input type="button" name="Submit22" value="删除&gt;&gt;&gt;" onClick="javascript:DeleteItem1('newcheckprize','availcheckprize')" style="height:16pt;width:50pt;font-size:12pt" >
      			</div>
      		</td>
      		<td colspan="2" rowspan="3">
        		<div align="center">
          		<select name="availcheckprize" size="12"  multiple style="width:160pt;height:50pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
            	<%
					i=0;			
					while(i<oV.size()){
						Res=(Hashtable)oV.get(i);
						i++;
				%>
            	<option value="<%=Res.get("sys_code")%>"><%=Res.get("code_desc")%></option>
            	<%
			 		}
				%>
          		</select>
				</div>
			</td>
    	</tr>
    	<tr valign="top">
      		<td>
      			<div align="center">
        		<input type="button" name="Button2" value="&lt;&lt;&lt;添加" onClick="javascript:DeleteItem1('availcheckprize','newcheckprize')" style="height:16pt;width:50pt;font-size:12pt" >
      			</div>
      		</td>
    	</tr>
    	<tr valign="top">
      		<td>&nbsp;</td>
    	</tr>
    	<tr class="style3">
      		<td align="right" nowrap><div align="right"></div></td>
      		<td align="right" nowrap>开始日期:</td>
      		<td colspan="2" nowrap>
        		<input type="text" name="PZbegin_date" class="wideinput"  value="" maxlength="8" size="12">* (YYYYMMDD) 
        	</td>
      		<td width="91" nowrap align="right">结束日期:</td>
      		<td width="84" nowrap align="right"><input type="text" name="PZend_date" class="wideinput"  value="" maxlength="8" size="12"></td>
      		<td width="92" nowrap>* (YYYYMMDD) 
      		</td>
    	</tr>
    	<tr class="style3">
      		<td align="right" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;</td>
      		<td align="right" nowrap>开始时间:</td>
      		<td colspan="2" nowrap>
        		<input type="text" name="PZbegin_time" class="wideinput"  value="" maxlength="6" size="10">* (HHMMSS) 
        	</td>
      		<td width="91" align="right" nowrap>结束时间:</td>
      		<td width="84" align="right" nowrap><input type="text" name="PZend_time" class="wideinput"  value="" maxlength="6" size="10"></td>
      		<td width="92" nowrap>* (HHMMSS) </td>
    	</tr>
    	<tr class="style3">
      		<td align="right" nowrap>&nbsp;</td>
      		<td align="right" nowrap><div align="right">最低消费金额:</div></td>
      		<td colspan="2" nowrap>
        		<input type="text" name="PZ_theMinMoney" class="wideinput"  value="" maxlength="10" size="10">* 
        	</td>
      		<td colspan="2" align="right" nowrap >&nbsp;</td>
      		<td width="92" nowrap align="right" >&nbsp;</td>
    	</tr>
    <table width="99%" name ="prizetypeDetail" id="PZdetail">
      <tr>
        <td width="20%" nowrap align="right">详细抽奖类别填写: </td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">一等奖设置:</td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">一等奖总个数:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve1Counts" class="wideinput"  value="0" maxlength="5" size="6">
        </td>
        <td width="20%" nowrap align="right">一等奖祝词:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve1bless" class="wideinput"  value="恭喜!特等奖" maxlength="12" size="10">
        </td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">二等奖设置:</td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">二等奖总个数:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve2Counts" class="wideinput"  value="0" maxlength="5" size="6">
        </td>
        <td width="20%" nowrap align="right">二等奖祝词:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve2bless" class="wideinput"  value="恭喜!一等奖" maxlength="12" size="10">
        </td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">三等奖设置:</td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">三等奖总个数:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve3Counts" class="wideinput"  value="0" maxlength="5" size="6">
        </td>
        <td width="20%" nowrap align="right">三等奖祝词:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve3bless" class="wideinput"  value="恭喜!二等奖" maxlength="12" size="10">
        </td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">四等奖设置:</td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">四等奖总个数:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve4Counts" class="wideinput"  value="0" maxlength="5" size="6">
        </td>
        <td width="20%" nowrap align="right">四等奖祝词:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve4bless" class="wideinput"  value="恭喜!三等奖" maxlength="12" size="10">
        </td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">五等奖设置:</td>
        <td></td>
      </tr>
      <tr>
        <td width="20%" nowrap align="right">五等奖总个数:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve5Counts" class="wideinput"  value="0" maxlength="5" size="6">
        </td>
        <td width="20%" nowrap align="right">五等奖祝词:</td>
        <td width="30%" nowrap>
          <input type="text" name="PZLeve5bless" class="wideinput"  value="恭喜!四等奖" maxlength="12" size="10">
        </td>
      </tr>
    </table>
    <tr>
      <td height="16" colspan="2" class="style3">
        <div align="left">
          <input type="checkbox" name="merchantIsOk" value="checkbox" class="radio" onClick="javascript:onCondition()">
        指定商户
        <input type="hidden" name="ischeckedMercnt" value="">
      </div></td>      
      <td height="16" class="style3">&nbsp;</td>
      <td height="16" class="style3">&nbsp;</td>
      <td height="16" class="style3">&nbsp;</td>
      <td height="16" class="style3">&nbsp;</td>
      <td height="16" class="style3">&nbsp;</td>
    </tr>
    <table width="99%" id="merchantok"  style="display:none">
      <tr>
        <td width="17%" height="20">商户名称列表：</td>
      </tr>
      <tr valign="top">
        <td width="43%" align="center">
            <select name="newmerchantlist" size="12" multiple style="width:250pt;height:200pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
            </select>
        </td>
        <td width="14%">
          <div align="center">
            <p>
              <input type="button" name="Button" value="&lt;&lt;&lt;添加" onClick="javascript:DeleteItem1('availmerchantlist','newmerchantlist')" style="height:20pt;width:50pt;font-size:12pt" >
			 <br> 
              <input type="button" name="Submit2" value="删除&gt;&gt;&gt;" onClick="javascript:DeleteItem1('newmerchantlist','availmerchantlist')" style="height:20pt;width:50pt;font-size:12pt" >              
            </p>
        </div></td>
        <td width="43%" align="center">
            <select name="availmerchantlist" size="12" multiple  style="width:250pt;height:200pt;border:1pt solid #9a9999; font-size:12px; background-color:#DDDDDD">
              <%
			i=0;			
			while(i<oV2.size())
			{
				Res=(Hashtable)oV2.get(i);
				i++;
			%>
              <option value="<%=Res.get("merchant_id")%>"><%=Res.get("merchant_id")+"-"+Res.get("mct_name")%></option>
              <%
			 }
			%>
            </select>
        </td>
      </tr>
      <tr>
        <td colspan="4" width="662" height="16">
          <input type="checkbox" name="IsmerchantTypeOk" value="checkbox" class="radio" onClick="javascript:onCondition2()">
        是否指定各个商户奖别</td>
        <input type="hidden" name="ischeckedMercntTypeOk" value="no">
      </tr>
    </table>
  </table>
  <table width="770" height="61">
    <tr>
      <td width="762" colspan="6" nowrap>
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td height="23" colspan="6" nowrap>
        <div align="center">
          <input type="submit"  border="1px solid Black" name="submit" value="提交" class="inputbutton">          
          <input type="button"  border="1px solid Black" name="cancel" value="取消" class="inputbutton" onClick="history.go(-1);">
        </div></td>
  </table>
  <p/>
</form>
</body>
</html>
