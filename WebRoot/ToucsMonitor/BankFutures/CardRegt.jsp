<%@ page contentType="text/html; charset=gb2312" %>

<html>
<head>
<title>Untitled Document</title>
<!---
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
-->
<Script Language="JavaScript">
function check(){
}
</Script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<div align="left"></div>   
<form name="form1" method="post" action="/ToucsMonitor/bfCardservlet">
	<p>磁道号 
		<input type="text" name="track_no">
	</p>
  	<p>卡起始适配位 
  		<input type="text" name="matchStar">
  	</p>
  	<p>卡适配标识 
  		<input type="text" name="cardMatch">
  	</p>
  	<p>卡号起始位 
  		<input type="text" name="cardStart">
  	</p>
  	<p>卡号（截取）长度 
    	<input type="text" name="cardLen">
  	</p>
  	<p>发卡行标识起始适配位 
    	<input type="text" name="bankStart">
  	</p>
  	<p>发卡行标识长度 
    	<input type="text" name="bankLen">
  	</p>
  	<p>发卡行适配标识 
    	<input type="text" name="bankMatch">
  	</p>
  	<p>商业银行中心代码 
    	<select name="bankCode">
		<option value=" ">	</option>
    	</select>
  	</p>
  	<p>卡种类 
    	<select name="cardClass">
		<option value=" ">	</option>
    	</select>
  	</p>
  	<p>卡类型 
    	<select name="cardType">
		</select>
  	</p>
  	<p>个人密码偏移位 
    	<input type="text" name="pinoffset">
  	</p>
  	<p> 卡地区编码 
    	<input type="text" name="cardArea">
  	</p>
  	<p>卡表状态 
    	<input type="radio" name="cardState" value="0">
   	 启用 
    	<input type="radio" name="cardState" value="1">
            不启用 
    	<input type="radio" name="cardState" value="2">
   	 删除</p>
  	<p> 
    	<input type="submit" name="regSubmit" value="登记">
    	<input type="reset" name="Submit2" value="重新填写">
  	</p>
</form>
</body>
</html>
