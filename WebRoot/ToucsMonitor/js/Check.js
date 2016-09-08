//************************页面输入检测函数**************************
//是否数字
function isNumber(inputName){
	value = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(value != null && value.length > 0){
		for(i = 0 ; i < value.length ; i++){
			if(value.charAt(i)>'9'||value.charAt(i)<'0'){
				alert("请输入数字！")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		if(value.charAt(0) == '0' && value.length > 2){
				alert("请输正确数字！")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
		}
	}
	return true;
}
//是否浮点
function isFNumber(inputName){
	value = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(value != null && value.length > 0){
		nF = 0;
		if(value.charAt(0) == '-' || (value.charAt(0)<='9'&&value.charAt(0)>='0')){
			for(i = 1 ; i < value.length ; i++){
				if((value.charAt(i)>'9'||value.charAt(i)<'0')&&(value.charAt(i)!='.')){
					alert("请输入数字或者小数点！")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				}
				if(value.charAt(i)=='.')
				{
				  nF++;
				  if (nF>1)
				  {
					alert("只能包含一个小数点！")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				  }
				}
			}
		}
		else{
			alert("请输入数字或者小数点！")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
	}
	return true;
}

//是否为空
function isNull(inputName){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length <= 0){
		alert("请输入带“ * ”的必输内容")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
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
function isYear(inputName){
	if(!isNumber(inputName))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 4){
		alert("请输入4位年份")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}
//检测月
function isMonth(inputName){
	if(!isNumber(inputName))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert("请输入2位月份")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1'){
			alert("请输入正确月份")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '1'){
			if(newString.charAt(1) > '2'){
				alert("请输入正确月份")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert("请输入正确月份")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//检测日
function isDay(inputName){
	if(!isNumber(inputName))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert("请输入2位日期")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1' && newString.charAt(0) != '2' && newString.charAt(0) != '3'){
			alert("请输入正确日期")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '3'){
			if(newString.charAt(1) > '1'){
				alert("请输入正确日期")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert("请输入正确日期")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//是否为空
function isdateNull(inputName){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length <= 0){
		return true;
	}
	return false;
}

//判断时间是否为有效值
function validFullDate(sdate)
{
	if (isdateNull(sdate))
	{ return true;}
	if(!isNumber(sdate))
	{ 
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
		}
	oldString = eval("document.forms[0]."+sdate+".value");
	newString = trim(oldString);
 
	if(newString == null || newString.length != 8)
	{
		alert("请输入8位有效日期（yyyymmdd）");
        	eval("document.forms[0]"+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	month = newString.substring(4,6);
	day = newString.substring(6,8);
    	 //校验月份
     	if(month.charAt(0) != '0' && month.charAt(0) != '1'){
		alert("请输入正确月份");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(month.charAt(0) == '1'){
		if(month.charAt(1) > '2'){
			alert("请输入正确月份")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(month.charAt(0) == '0'){
		if(month.charAt(1) == '0'){
			alert("请输入正确月份")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	//校验日期
	if(day.charAt(0) != '0' && day.charAt(0) != '1' && day.charAt(0) != '2' && day.charAt(0) != '3'){
		alert("请输入正确日期");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(day.charAt(0) == '3'){
		if(day.charAt(1) > '1'){
			alert("请输入正确日期");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(day.charAt(0) == '0'){
		if(day.charAt(1) == '0'){
			alert("请输入正确日期");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
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