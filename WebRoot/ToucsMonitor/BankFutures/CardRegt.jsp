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
	<p>�ŵ��� 
		<input type="text" name="track_no">
	</p>
  	<p>����ʼ����λ 
  		<input type="text" name="matchStar">
  	</p>
  	<p>�������ʶ 
  		<input type="text" name="cardMatch">
  	</p>
  	<p>������ʼλ 
  		<input type="text" name="cardStart">
  	</p>
  	<p>���ţ���ȡ������ 
    	<input type="text" name="cardLen">
  	</p>
  	<p>�����б�ʶ��ʼ����λ 
    	<input type="text" name="bankStart">
  	</p>
  	<p>�����б�ʶ���� 
    	<input type="text" name="bankLen">
  	</p>
  	<p>�����������ʶ 
    	<input type="text" name="bankMatch">
  	</p>
  	<p>��ҵ�������Ĵ��� 
    	<select name="bankCode">
		<option value=" ">	</option>
    	</select>
  	</p>
  	<p>������ 
    	<select name="cardClass">
		<option value=" ">	</option>
    	</select>
  	</p>
  	<p>������ 
    	<select name="cardType">
		</select>
  	</p>
  	<p>��������ƫ��λ 
    	<input type="text" name="pinoffset">
  	</p>
  	<p> ���������� 
    	<input type="text" name="cardArea">
  	</p>
  	<p>����״̬ 
    	<input type="radio" name="cardState" value="0">
   	 ���� 
    	<input type="radio" name="cardState" value="1">
            ������ 
    	<input type="radio" name="cardState" value="2">
   	 ɾ��</p>
  	<p> 
    	<input type="submit" name="regSubmit" value="�Ǽ�">
    	<input type="reset" name="Submit2" value="������д">
  	</p>
</form>
</body>
</html>
