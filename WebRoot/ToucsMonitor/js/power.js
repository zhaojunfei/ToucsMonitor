//是否数字
function isNumberOk(inputName){
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

function GetObjID(ObjName)
{
  for (var ObjID=0; ObjID < window.checkpztable.elements.length; ObjID++)
    if ( window.checkpztable.elements[ObjID].name == ObjName )
    {  return(ObjID);
       break;
    }
  return(-1);
}

function DeleteItem(ObjName,DesName)
{
  ObjID = GetObjID(ObjName);
  DesID = GetObjID(DesName);
  j=window.checkpztable.elements[DesID].length;
  minselected=0;
  if ( ObjID != -1 )
  {
    for (i=window.checkpztable.elements[ObjID].length-1; i>=0; i--)
    {  if (window.checkpztable.elements[ObjID].options[i].selected)
       { // window.alert(i);
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

//表达式、目标、源
function AddExpression(Expression,ObjName,DesName)
{
  ObjID = GetObjID(ObjName);
  DesID = GetObjID(DesName);
  //目标
  j=window.checkpztable.elements[ObjID].length;
  //源
  k=window.checkpztable.elements[DesID].length;
  if(j==0&&k==0)
    alert("请先选择一个表达式所属的表！");
  else{
      Code = document.tablename.Expression.value;
      if(Code=="")
        alert("输入表达式不能为空");
      else{
          window.checkpztable.elements[ObjID].options[j] = new Option(Code, Code);
          window.tablename.Expression.value ="";
      }
  }
}

function SelectAll(ObjName)
{
    ObjID = GetObjID(ObjName);
    i=window.checkpztable.elements[ObjID].length
    for (i=window.checkpztable.elements[ObjID].length-1; i>=0; i--){
        window.checkpztable.elements[ObjID].options[i].selected=true;}
}

function Submited(ObjName)
{
    SelectAll(ObjName);
    //document.checkpztable.submit();
}
function verifyDate(textObj){
  var tmpDateValue = textObj.value;
  var tmpLength = tmpDateValue.length;

  if (tmpLength == 0){
    return true;
  }
  for (var i = 0; i < tmpLength;i++){
    aChar = tmpDateValue.substring(i,i+1);
    if(aChar != "-" && (aChar < "0" || aChar > "9")) {
      alert ("请按照格式输入日期(yyyy-mm-dd)。");
      textObj.focus(this);
      textObj.select(this);
      return false;
    }
  }
  if (tmpLength != 10) {
    alert ("请按照格式输入日期(yyyy-mm-dd)。");
      textObj.focus(this);
      textObj.select(this);
      return false;
  }
  for (var j= 0; j < 4;j++){
    aChar = tmpDateValue.substring(j,j+1);
    if(aChar < "0" || aChar > "9") {
      alert ("请按照格式输入日期(yyyy-mm-dd)。");
      textObj.focus(this);
      textObj.select(this);
      return false;
    }
  }
  
  if (tmpDateValue.substring(4,5) != "-" || tmpDateValue.substring(5,6) == "-"){
    alert ("请按规定格式输入日期(yyyy-mm-dd)。");
    textObj.focus(this);
    textObj.select(this);
    return false;

  }
  if (tmpLength == 10){
    if (tmpDateValue.substring(7,8) != "-" || tmpDateValue.substring(6,7) == "-" || tmpDateValue.substring(8,9) == "-" || tmpDateValue.substring(9,10) == "-" ){
      alert ("请按规定格式输入日期(yyyy-mm-dd)。");
      textObj.focus(this);
      textObj.select(this);
      return false;
    }
  }
  var count=0;
  for (var k = 0; k < tmpLength;k++){
    aChar = tmpDateValue.substring(k,k+1);
    if(aChar == "-") {
        count++;
    }
  }
  if (count!=2){
    alert("请按照格式输入日期!(yyyy－mm-dd)");
    textObj.focus(this);
    textObj.select(this);
    return false;
  }
  var iYear = tmpDateValue.substring(0,4);
  var iMonth = tmpDateValue.substring(5,7);
  var iDate = tmpDateValue.substring(8,10);  
  if (!isValidDate(iYear,iMonth,iDate)){
    alert("请输入正确的日期！");
    textObj.focus(this);
    textObj.select(this);
    return false;
  }
  if(iYear <= 1949){
    alert(iYear+"日期中的年份太小了！");
    textObj.focus(this);
    textObj.select(this);
    return false;
  }
  if(iYear >= 2050){
    alert(iYear+"日期中的年份太大了！");
    textObj.focus(this);
    textObj.select(this);
    return false;
  }
  
  return true;
}

function isLeapYear(iYear) {
  var undefined						
  if ( iYear != undefined && !isNaN(iYear) && iYear > 0 &&		
       (iYear%4==0 && iYear%100 !=0 || iYear%400==0)   )		
      return true												
	else return false;
}											

function isValidDate(iY, iM, iD) {
  var undefined									
  if ( iY != undefined && !isNaN(iY) && iY >=0 && iY<=9999 &&						
       iM != undefined && !isNaN(iM) && iM >=1   && iM<=12   &&					
       iD != undefined && !isNaN(iD) && iD >=1   && iD<=31  )  {					
       if (iY<50) iY = 2000+iY; else if (iY<100) iY=1900+iY;						
    if (iM == 2  && (isLeapYear(iY)  && iD > 29 || !isLeapYear(iY) && iD>28) ||	
        iD == 31 && (iM<7 && iM%2==0 || iM>7 && iM%2==1) )							
		return false																
	else	return true   
 }														
 else  return false
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

