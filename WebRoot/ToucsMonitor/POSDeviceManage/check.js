//去除空格
function trim(srcString){
	newString = "";
	for(i = 0 ; i < srcString.length ; i++)
		if(srcString.charAt(i) != ' ')
			newString += srcString.charAt(i);
	return newString;
}

//是否为空
function isdateNull(inputName){
	oldString = eval("document.formReg."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length <= 0){
		return true;
	}
	return false;
}

//是否数字
function isNumber(inputName){
	value = eval("document.formReg."+inputName+".value");
	firstIsZero = false;
	if(value != null && value.length > 0){
		for(i = 0 ; i < value.length ; i++){
			if(value.charAt(i)>'9'||value.charAt(i)<'0'){
				alert("请输入数字！")
				eval("document.formReg."+inputName+".focus()")
				eval("document.formReg."+inputName+".select()")
				return false;
			}
		}
		if(value.charAt(0) == '0' && value.length > 2){
				alert("请输正确数字！")
				eval("document.formReg."+inputName+".focus()")
				eval("document.formReg."+inputName+".select()")
				return false;
		}
	}
	return true;
}

//判断时间是否为有效值
function validFullDate(sdate){
	if (isdateNull(sdate)){ 
		return true;
	}
	if(!isNumber(sdate))
		return false;
	oldString = eval("document.formReg."+sdate+".value");
	newString = trim(oldString);
 
	if(newString == null || newString.length != 8){
		alert("请输入8位有效日期（yyyymmdd）");
        eval("document.formReg."+sdate+".focus()");
		eval("document.formReg."+sdate+".select()");
		return false;
	}
	month = newString.substring(4,6);
	day = newString.substring(6,8);
     //校验月份
    if(month.charAt(0) != '0' && month.charAt(0) != '1'){
			alert("请输入正确月份");
			eval("document.formReg."+sdate+".focus()");
			eval("document.formReg."+sdate+".select()");
			return false;
	}else if(month.charAt(0) == '1'){
		if(month.charAt(1) > '2'){
			alert("请输入正确月份")
			eval("document.formReg."+sdate+".focus()");
			eval("document.formReg."+sdate+".select()");
			return false;
		}
	}else if(month.charAt(0) == '0'){
		if(month.charAt(1) == '0'){
			alert("请输入正确月份")
			eval("document.formReg."+sdate+".focus()");
			eval("document.formReg."+sdate+".select()");
			return false;
		}
	}
	//校验日期
	if(day.charAt(0) != '0' && day.charAt(0) != '1' && day.charAt(0) != '2' && day.charAt(0) != '3'){
		alert("请输入正确日期");
		eval("document.formReg."+sdate+".focus()");
		eval("document.formReg."+sdate+".select()");
		return false;
	}else if(day.charAt(0) == '3'){
		if(day.charAt(1) > '1'){
			alert("请输入正确日期");
			eval("document.formReg."+sdate+".focus()");
			eval("document.formReg."+sdate+".select()");
			return false;
		}
	}else if(day.charAt(0) == '0'){
		if(day.charAt(1) == '0'){
			alert("请输入正确日期");
			eval("document.formReg."+sdate+".focus()");
			eval("document.formReg."+sdate+".select()");
			return false;
		}
	}
	return true;
}
