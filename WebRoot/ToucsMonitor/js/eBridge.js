//************************页面输入检测函数**************************
//是否数字
function isNumber(inputName,msg){
	obj = eval("document.forms[0]."+inputName+".value");
	if( !isNumberNoPrompt(obj) ){
		alert(msg+"请输入数字！")                       
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}
//是否整数
function isInteger(inputName,msg){
	obj = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(obj != null && obj.length > 0){
		for(i = 0 ; i < obj.length ; i++){
			if(obj.charAt(i)>'9'||obj.charAt(i)<'0'){
				alert(msg+"请输入整数！")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		//数字首位不能为0
		if(obj.charAt(0) == '0' && obj.length > 2){
				alert(msg+"请输入正确整数！")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
		}
	}
	return true;
}

//是否浮点
function isFNumber(inputName,msg){
	value = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(value != null && value.length > 0){
		nF = 0;
		if(value.charAt(0) == '-' || (value.charAt(0)<='9'&&value.charAt(0)>='0')){
			for(i = 1 ; i < value.length ; i++){
				if((value.charAt(i)>'9'||value.charAt(i)<'0')&&(value.charAt(i)!='.')){
					alert(msg+"请输入数字或者小数点！")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				}
				if(value.charAt(i)=='.')
				{
				  nF++;
				  if (nF>1)
				  {
					alert(msg+"只能包含一个小数点！")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				  }
				}
			}
		}
		else{
			alert(msg+"请输入数字或者小数点！")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
	}
	return true;
}

//是否为空
function isNull(inputName,msg){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);                            		
	if ( newString == null || newString.length <= 0 ){      		
		alert( "请填写"+msg+"！");                      		
		eval("document.forms[0]."+inputName+".focus()") 		
		return true;                                    		
	}                                                       		
	return false;                   
}

//去除空格
function trim(srcString){
	newString = "";
	for(i = 0 ; i < srcString.length ; i++)
		if(srcString.charAt(i) != ' ')
			newString += srcString.charAt(i);
	return newString;
}
//检测年
function isYear(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 4){
		alert(msg+"请输入4位年份")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}
//检测月
function isMonth(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert(msg+"请输入2位月份")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1'){
			alert(msg+"请输入正确月份")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '1'){
			if(newString.charAt(1) > '2'){
				alert(msg+"请输入正确月份")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert(msg+"请输入正确月份")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//检测日
function isDay(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert(msg+"请输入2位日期")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1' && newString.charAt(0) != '2' && newString.charAt(0) != '3'){
			alert(msg+"请输入正确日期")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '3'){
			if(newString.charAt(1) > '1'){
				alert(msg+"请输入正确日期")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert(msg+"请输入正确日期")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//日期是否为空
function isdateNull(inputName){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length <= 0){
		return true;
	}
	return false;
}

//判断日期是否为有效值
function validFullDate(sdate,msg)
{
	if (isdateNull(sdate))
	{ return true;}
	if(!isNumber(sdate,msg))
	{ 
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	oldString = eval("document.forms[0]."+sdate+".value");
	newString = trim(oldString);
 	if(newString == null || newString.length != 8)
	{
		alert(msg+"请输入8位有效日期（yyyymmdd）");
        	eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	month = newString.substring(4,6);
	//alert("month="+month);
	day = newString.substring(6,8);
	//alert("day="+day);
    	//校验月份
     	if(month.charAt(0) != '0' && month.charAt(0) != '1'){
		alert(msg+"请输入正确月份");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(month.charAt(0) == '1'){
		if(month.charAt(1) > '2'){
			alert(msg+"请输入正确月份")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(month.charAt(0) == '0'){
		if(month.charAt(1) == '0'){
			alert(msg+"请输入正确月份")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	//校验日期
	if(day.charAt(0) != '0' && day.charAt(0) != '1' && day.charAt(0) != '2' && day.charAt(0) != '3'){
		alert(msg+"请输入正确日期");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(day.charAt(0) == '3'){
		if(day.charAt(1) > '1'){
			alert(msg+"请输入正确日期");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(day.charAt(0) == '0'){
		if(day.charAt(1) == '0'){
			alert(msg+"请输入正确日期");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	return true;
}

//检查输入域长度
function checkLength(inputName,maxLen,msg){
	obj = eval("document.forms[0]."+inputName+".value");
 	var myByteLength = 0;
	if(obj.length > maxLen){
		alert(msg + "长度不能超过"+maxLen+"个字母和数字，或者" + maxLen/2 + "个汉字");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")	
		return false;
	}			
	for(i_loop=0;i_loop<obj.length;i_loop++)   
	{
		if(obj.charAt(i_loop)>"~"){
			myByteLength = myByteLength + 2;
		}else{
			myByteLength = myByteLength + 1;
		}
        }	
	if(myByteLength>maxLen){
		alert(msg + "长度不能超过"+maxLen+"个字母和数字，或者" + maxLen/2 + "个汉字");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//校验设备编号的合法性,atm,cdm,mit,pem
function onDeviceCode(prefix,inputName){
	obj = eval("document.forms[0]."+inputName+".value");
	var code= trim(obj);

	if(code.length==4){
		//校验4位数字
		if(!isNumberNoPrompt(code)) {
			alert("4位编号必须为数字!");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")	
			return false;
		}
		code = prefix+code;
		eval("document.forms[0]."+inputName+".value='"+code+"'");
	}else if(code.length==9){
		//校验完整的编号
		var preStr=code.substring(0,5);
		//alert(preStr);
		var nmStr=code.substring(5,9);
		//alert(nmStr);
		if( preStr!= prefix ){
			alert("9位设备编号必须为"+prefix+"加4位数字！");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		if(!isNumberNoPrompt(nmStr)) {
			alert("9位设备编号必须为"+prefix+"加4位数字！");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
	}else{ 
		alert("请输入4位数的编号或9位的联网号!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}

	return true;
}

//校验POS设备编号的合法性(特定编号检查）
function onPosCodeBackup(inputName,doPrompt){
	obj = eval("document.forms[0]."+inputName+".value");
	var code = trim(obj);
	if( code.length != 9 ){
		if( doPrompt)alert("请输入9位的设备编号!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//检查前缀
	var prefix = code.substring(0,5);
	if( prefix!="P5210" && prefix!="P5310" ){
		if( doPrompt) alert("POS设备编号的前5位必须为“P5210”或者“P5310”！");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//检查编号
	var subcode = code.substring(5,9);
	if( !isNumberNoPrompt(subcode) ){
		if( doPrompt) alert("POS设备编号的最后4位必须为数字！");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//校验POS设备编号的合法性(特定编号检查）
function onPosCode(inputName,doPrompt){
	obj = eval("document.forms[0]."+inputName+".value");
	var code = trim(obj);
	if( code.length != 9 ){
		if( doPrompt)alert("请输入9位的设备编号!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//检查前缀
	var prefix = code.substring(0,1);
	if( prefix!="P" ){
		if( doPrompt) alert("POS设备编号必须以“P”开头！");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//校验是否数字（无提示）
function isNumberNoPrompt(obj){
	if( obj==null || obj.length <0 ) return false;
	var char;                       
	for(i=0;i<obj.length;i++){      	
		char=obj.charAt(i);     	
		if(char<'0'||char>'9')  	
			return false;		
		}	                	
	
	return true;
}


//************************弹出层处理函数（查询结果页面使用）**************************

function Do_Layer(choice,thisForm){
	var A2 = eval(thisForm);
	if	(choice=='visible'){
		if (navigator.userAgent.indexOf('MSIE') > -1){
			A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-A2.offsetWidth/2;
			A2.style.top=document.body.scrollTop+45;
			A2.style.visibility='visible';
		}
		else{
			document.A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-document.A2.offsetWidth/2;
			document.A2.style.top=document.body.scrollTop+45;
			document.A2.visibility='visible';
		}
	}
	if	(choice=='hidden'){
		if (navigator.userAgent.indexOf('MSIE') > -1)
			A2.style.visibility='hidden';
		else
			document.A2.visibility='hidden';
	}
}

function Do_Layer0(choice,thisForm){
	if (thisForm=='Layer0'){
		if	(choice=='visible'){
			if (navigator.userAgent.indexOf('MSIE') > -1)
				Layer0.style.visibility='visible';
			else
				document.Layer0.visibility='visible';
		}
		if(choice=='hidden'){
			if (navigator.userAgent.indexOf('MSIE') > -1)
				Layer0.style.visibility='hidden';
			else
				document.Layer0.visibility='hidden';
		}
	}
}


//************************弹出层处理函数（查询结果页面使用）**************************

function Do_Layer(choice,thisForm){
	var A2 = eval(thisForm);
	if	(choice=='visible'){
		if (navigator.userAgent.indexOf('MSIE') > -1){
			A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-A2.offsetWidth/2;
			A2.style.top=document.body.scrollTop+45;
			A2.style.visibility='visible';
		}
		else{
			document.A2.style.left=document.body.scrollLeft+document.body.clientWidth/2-document.A2.offsetWidth/2;
			document.A2.style.top=document.body.scrollTop+45;
			document.A2.visibility='visible';
		}
	}
	if	(choice=='hidden'){
		if (navigator.userAgent.indexOf('MSIE') > -1)
			A2.style.visibility='hidden';
		else
			document.A2.visibility='hidden';
	}
}

function Do_Layer0(choice,thisForm){
	if (thisForm=='Layer0'){
		if	(choice=='visible'){
			if (navigator.userAgent.indexOf('MSIE') > -1)
				Layer0.style.visibility='visible';
			else
				document.Layer0.visibility='visible';
		}
		if(choice=='hidden'){
			if (navigator.userAgent.indexOf('MSIE') > -1)
				Layer0.style.visibility='hidden';
			else
				document.Layer0.visibility='hidden';
		}
	}
}