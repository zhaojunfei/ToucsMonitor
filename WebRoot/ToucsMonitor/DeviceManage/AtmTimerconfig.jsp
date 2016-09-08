<%@ page language="java" contentType="text/html; charset=gb2312" %>

<%@ page import="com.adtec.toucs.monitor.devicemanage.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.style1 {
	font-size: 16px;
	font-weight: bold;
	color: #0000FF;
}
.style2 {color: #0000FF}
.style3 {
	font-size: 14px;
	font-weight: bold;
}
.style5 {color: #0000FF; font-size: 14px; }
.style9 {font-size: 14px}
.style10 {color: #CC3300}
.style11 {font-size: 14px; font-weight: bold; color: #CC3300; }
.style12 {
	color: #000000;
	font-size: 14px;
}
.style13 {color: #000000}
-->
</style>
</head>
<%
 String uid=request.getParameter("uid");
 DeviceManageBean DMB = new DeviceManageBean();
 DMB.getTimerConfig();
%>

<body>
<script language="javascript">
function doSubmit(){
    if( isNull("timer1begin"," 时段一开始时间"))     return false; 	
	if( isNull("timer1end"," 时段一结束时间"))       return false;
	if( isNull("timer2begin"," 时段二开始时间"))     return false;
	if( isNull("timer2end"," 时段二结束时间"))       return false;		    
	if( isNull("timer3begin"," 时段三开始时间"))     return false;
	if( isNull("timer3end", " 时段三结束时间"))      return false;
	if( isNull("timer4begin"," 时段四开始时间"))     return false;
	if( isNull("timer4end", " 时段四结束时间"))       return false;
	if( isNull("timer5begin"," 时段五开始时间"))     return false; 	
	if( isNull("timer5end"," 时段五结束时间"))       return false;
	if( isNull("timer6begin"," 时段六开始时间"))     return false;
	if( isNull("timer6end"," 时段六结束时间"))       return false;		    
	if( isNull("timer7begin"," 时段七开始时间"))     return false;
	if( isNull("timer7end", " 时段七结束时间"))      return false;
	if( isNull("timer8begin"," 时段八开始时间"))     return false;
	if( isNull("timer8end", " 时段八结束时间"))       return false;
	     
	if(confirm("确认输入无误并提交吗？")){
		return true;
	}
	else
		return false;
}

</script>
<script language="javascript" src="/ToucsMonitor/js/power.js">
</script>
<div align="center"><span class="style1">ATM时段 起始结束时间 设置页</span>
</div>
<form name="form1" method="post" action="/ToucsMonitor/deviceconfig?reqCode=21011&uid=<%=uid%>"  onSubmit="javascript:return doSubmit();">
<div align="center">
	<table width="732" height="379" border="0">
    	<tr>
        	<th height="15" scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
        	<th scope="col">&nbsp;</th>
      	</tr>
      	<tr>
        	<th width="89" height="32" scope="col">&nbsp;</th>
        	<th width="77" scope="col">&nbsp;</th>
        	<th width="19" scope="col">&nbsp;</th>
        	<th width="221" scope="col"><div align="left" class="style9 style13">
          	<div align="center">起始时间</div>
        	</div>
        	</th>
        	<th width="19" scope="col"><div align="left">
			</div>
			</th>
        	<th width="260" scope="col"><div align="center" class="style9">结束时间</div></th>
        	<th width="17" scope="col">&nbsp;</th>
      	</tr>
      	<tr>
        	<td height="40">&nbsp;</td>
        	<td><div align="left" class="style3 style10">时段一</div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer1begin" type="text" id="timer1begin" size="12" maxlength="6" value=<%=DMB.Atimer1begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer1end" type="text" id="timer1end" size="12" maxlength="6" value=<%=DMB.Atimer1end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="30">&nbsp;</td>
        	<td><span class="style11">时段二</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer2begin" type="text" id="timer2begin" size="12" maxlength="6"value=<%=DMB.Atimer2begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer2end" type="text" id="timer2end" size="12" maxlength="6" value=<%=DMB.Atimer2end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
     	<tr>
        	<td height="34">&nbsp;</td>
        	<td><span class="style11">时段三</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer3begin" type="text" id="timer3begin" size="12" maxlength="6" value=<%=DMB.Atimer3begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer3end" type="text" id="timer3end" size="12" maxlength="6" value=<%=DMB.Atimer3end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
       	 	<td><div align="center"></div></td>
      	</tr>
      	<tr>
        	<td height="40">&nbsp;</td>
        	<td><span class="style11">时段四</span></td>
        	<td>&nbsp;</td>
       	 	<td><div align="center"><span class="style9">
            <input name="timer4begin" type="text" id="timer4begin" size="12" maxlength="6" value=<%=DMB.Atimer4begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
        	<td><div align="center"><span class="style9">
            <input name="timer4end" type="text" id="timer4end" size="12" maxlength="6" value=<%=DMB.Atimer4end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="35">&nbsp;</td>
        	<td><span class="style11">时段五</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer5begin" type="text" id="timer5begin" size="12" maxlength="6" value=<%=DMB.Atimer5begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer5end" type="text" id="timer5end" size="12" maxlength="6" value=<%=DMB.Atimer5end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
     	<tr>
        	<td height="40">&nbsp;</td>
        	<td><span class="style11">时段六</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style5">
            <input name="timer6begin" type="text" id="timer6begin2" size="12" maxlength="6" value=<%=DMB.Atimer6begin%>>(HHMMSS)</span>
            </div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer6end" type="text" id="timer6end" size="12" maxlength="6" value=<%=DMB.Atimer6end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="32">&nbsp;</td>
        	<td><span class="style11">时段七</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer7begin" type="text" id="timer7begin2" size="12" maxlength="6" value=<%=DMB.Atimer7begin%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer7end" type="text" id="timer7end" size="12" maxlength="6" value=<%=DMB.Atimer7end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td height="34">&nbsp;</td>
        	<td><span class="style11">时段八</span></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer8begin" type="text" id="timer8begin2" size="12" maxlength="6" value=<%=DMB.Atimer8begin%>>          
            <span class="style2">(HHMMSS)</span></span></div></td>
        	<td><div align="right" class="style9"></div></td>
        	<td><div align="center"><span class="style9">
            <input name="timer8end" type="text" id="timer8end" size="12" maxlength="6" value=<%=DMB.Atimer8end%>>
          	<span class="style2">(HHMMSS)</span></span></div></td>
        	<td>&nbsp;</td>
      	</tr>
      	<tr>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
        	<td>&nbsp;</td>
        	<td><div align="right">
        	</div></td>
        	<td>        
			<input type="submit" name="Submit" value="确定">
        	<input type="button" name="cancel" value="取消" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/deviceconfig?reqCode=21011&uid=<%=uid%>'"></td>
        	<td>&nbsp;</td>
      	</tr>
    </table>
</div>
</form>
</body>
</html>
