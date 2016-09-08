function GetObjID(ObjName){
	for (var ObjID=0; ObjID < window.powersearch.elements.length; ObjID++)
		if ( window.powersearch.elements[ObjID].name == ObjName ){  
			return(ObjID);
			break;
		}
	return(-1);
}

function DeleteItem(ObjName,DesName){
	ObjID = GetObjID(ObjName);
	DesID = GetObjID(DesName);
	j=window.powersearch.elements[DesID].length;
	minselected=0;
	if ( ObjID != -1 ){
		for (i=window.powersearch.elements[ObjID].length-1; i>=0; i--){  
			if (window.powersearch.elements[ObjID].options[i].selected){ 
				// window.alert(i);
				if (minselected==0 || i<minselected)
					minselected=i;
				Code = document.powersearch.elements[ObjID].options[i].value;
				Text = document.powersearch.elements[ObjID].options[i].text;
				window.powersearch.elements[DesID].options[j] = new Option(Text, Code);;
				j=j+1;
				window.powersearch.elements[ObjID].options[i] = null;
			}
		}
		i=window.powersearch.elements[ObjID].length;
		if (i>0)  {
			if (minselected>=i)
				minselected=i-1;
			window.powersearch.elements[ObjID].options[minselected].selected=true;
        }
	}
}

//表达式、目标、源
function AddExpression(Expression,ObjName,DesName){
	ObjID = GetObjID(ObjName);
	DesID = GetObjID(DesName);
	//目标
	j=window.powersearch.elements[ObjID].length;
	//源
	k=window.powersearch.elements[DesID].length;
	if(j==0&&k==0)
		alert("请先选择一个表达式所属的表！");
	else{
		Code = document.tablename.Expression.value;
		if(Code=="")
			alert("输入表达式不能为空");
		else{
			window.powersearch.elements[ObjID].options[j] = new Option(Code, Code);
			window.tablename.Expression.value ="";
		}
	}
}

function SelectAll(ObjName){
    ObjID = GetObjID(ObjName);
    i=window.powersearch.elements[ObjID].length
    for (i=window.powersearch.elements[ObjID].length-1; i>=0; i--){
        window.powersearch.elements[ObjID].options[i].selected=true;}
}

function Submited(ObjName){
    SelectAll(ObjName);
    document.powersearch.submit();
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
