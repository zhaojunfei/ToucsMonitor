//************************ҳ�������⺯��**************************
//�Ƿ�����
function isNumber(inputName,msg){
	obj = eval("document.forms[0]."+inputName+".value");
	if( !isNumberNoPrompt(obj) ){
		alert(msg+"���������֣�")                       
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}
//�Ƿ�����
function isInteger(inputName,msg){
	obj = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(obj != null && obj.length > 0){
		for(i = 0 ; i < obj.length ; i++){
			if(obj.charAt(i)>'9'||obj.charAt(i)<'0'){
				alert(msg+"������������")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		//������λ����Ϊ0
		if(obj.charAt(0) == '0' && obj.length > 2){
				alert(msg+"��������ȷ������")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
		}
	}
	return true;
}

//�Ƿ񸡵�
function isFNumber(inputName,msg){
	value = eval("document.forms[0]."+inputName+".value");
	firstIsZero = false;
	if(value != null && value.length > 0){
		nF = 0;
		if(value.charAt(0) == '-' || (value.charAt(0)<='9'&&value.charAt(0)>='0')){
			for(i = 1 ; i < value.length ; i++){
				if((value.charAt(i)>'9'||value.charAt(i)<'0')&&(value.charAt(i)!='.')){
					alert(msg+"���������ֻ���С���㣡")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				}
				if(value.charAt(i)=='.')
				{
				  nF++;
				  if (nF>1)
				  {
					alert(msg+"ֻ�ܰ���һ��С���㣡")
					eval("document.forms[0]."+inputName+".focus()")
					eval("document.forms[0]."+inputName+".select()")
					return false;
				  }
				}
			}
		}
		else{
			alert(msg+"���������ֻ���С���㣡")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
	}
	return true;
}

//�Ƿ�Ϊ��
function isNull(inputName,msg){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);                            		
	if ( newString == null || newString.length <= 0 ){      		
		alert( "����д"+msg+"��");                      		
		eval("document.forms[0]."+inputName+".focus()") 		
		return true;                                    		
	}                                                       		
	return false;                   
}

//ȥ���ո�
function trim(srcString){
	newString = "";
	for(i = 0 ; i < srcString.length ; i++)
		if(srcString.charAt(i) != ' ')
			newString += srcString.charAt(i);
	return newString;
}
//�����
function isYear(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 4){
		alert(msg+"������4λ���")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}
//�����
function isMonth(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert(msg+"������2λ�·�")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1'){
			alert(msg+"��������ȷ�·�")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '1'){
			if(newString.charAt(1) > '2'){
				alert(msg+"��������ȷ�·�")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert(msg+"��������ȷ�·�")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//�����
function isDay(inputName,msg){
	if(!isNumber(inputName,msg))
		return false;
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length < 2){
		alert(msg+"������2λ����")
		eval("document.forms[0]."+inputName+".focus()")
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	else{
		if(newString.charAt(0) != '0' && newString.charAt(0) != '1' && newString.charAt(0) != '2' && newString.charAt(0) != '3'){
			alert(msg+"��������ȷ����")
			eval("document.forms[0]."+inputName+".focus()")
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		else if(newString.charAt(0) == '3'){
			if(newString.charAt(1) > '1'){
				alert(msg+"��������ȷ����")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}
		else if(newString.charAt(0) == '0'){
			if(newString.charAt(1) == '0'){
				alert(msg+"��������ȷ����")
				eval("document.forms[0]."+inputName+".focus()")
				eval("document.forms[0]."+inputName+".select()")
				return false;
			}
		}

	}
	return true;
}
//�����Ƿ�Ϊ��
function isdateNull(inputName){
	oldString = eval("document.forms[0]."+inputName+".value");
	newString = trim(oldString);
	if(newString == null || newString.length <= 0){
		return true;
	}
	return false;
}

//�ж������Ƿ�Ϊ��Чֵ
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
		alert(msg+"������8λ��Ч���ڣ�yyyymmdd��");
        	eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	month = newString.substring(4,6);
	//alert("month="+month);
	day = newString.substring(6,8);
	//alert("day="+day);
    	//У���·�
     	if(month.charAt(0) != '0' && month.charAt(0) != '1'){
		alert(msg+"��������ȷ�·�");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(month.charAt(0) == '1'){
		if(month.charAt(1) > '2'){
			alert(msg+"��������ȷ�·�")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(month.charAt(0) == '0'){
		if(month.charAt(1) == '0'){
			alert(msg+"��������ȷ�·�")
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	//У������
	if(day.charAt(0) != '0' && day.charAt(0) != '1' && day.charAt(0) != '2' && day.charAt(0) != '3'){
		alert(msg+"��������ȷ����");
		eval("document.forms[0]."+sdate+".focus()");
		eval("document.forms[0]."+sdate+".select()");
		return false;
	}
	else if(day.charAt(0) == '3'){
		if(day.charAt(1) > '1'){
			alert(msg+"��������ȷ����");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	else if(day.charAt(0) == '0'){
		if(day.charAt(1) == '0'){
			alert(msg+"��������ȷ����");
			eval("document.forms[0]."+sdate+".focus()");
			eval("document.forms[0]."+sdate+".select()");
			return false;
		}
	}
	return true;
}

//��������򳤶�
function checkLength(inputName,maxLen,msg){
	obj = eval("document.forms[0]."+inputName+".value");
 	var myByteLength = 0;
	if(obj.length > maxLen){
		alert(msg + "���Ȳ��ܳ���"+maxLen+"����ĸ�����֣�����" + maxLen/2 + "������");
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
		alert(msg + "���Ȳ��ܳ���"+maxLen+"����ĸ�����֣�����" + maxLen/2 + "������");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//У���豸��ŵĺϷ���,atm,cdm,mit,pem
function onDeviceCode(prefix,inputName){
	obj = eval("document.forms[0]."+inputName+".value");
	var code= trim(obj);

	if(code.length==4){
		//У��4λ����
		if(!isNumberNoPrompt(code)) {
			alert("4λ��ű���Ϊ����!");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")	
			return false;
		}
		code = prefix+code;
		eval("document.forms[0]."+inputName+".value='"+code+"'");
	}else if(code.length==9){
		//У�������ı��
		var preStr=code.substring(0,5);
		//alert(preStr);
		var nmStr=code.substring(5,9);
		//alert(nmStr);
		if( preStr!= prefix ){
			alert("9λ�豸��ű���Ϊ"+prefix+"��4λ���֣�");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
		if(!isNumberNoPrompt(nmStr)) {
			alert("9λ�豸��ű���Ϊ"+prefix+"��4λ���֣�");
			eval("document.forms[0]."+inputName+".focus()") 
			eval("document.forms[0]."+inputName+".select()")
			return false;
		}
	}else{ 
		alert("������4λ���ı�Ż�9λ��������!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}

	return true;
}

//У��POS�豸��ŵĺϷ���(�ض���ż�飩
function onPosCodeBackup(inputName,doPrompt){
	obj = eval("document.forms[0]."+inputName+".value");
	var code = trim(obj);
	if( code.length != 9 ){
		if( doPrompt)alert("������9λ���豸���!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//���ǰ׺
	var prefix = code.substring(0,5);
	if( prefix!="P5210" && prefix!="P5310" ){
		if( doPrompt) alert("POS�豸��ŵ�ǰ5λ����Ϊ��P5210�����ߡ�P5310����");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//�����
	var subcode = code.substring(5,9);
	if( !isNumberNoPrompt(subcode) ){
		if( doPrompt) alert("POS�豸��ŵ����4λ����Ϊ���֣�");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//У��POS�豸��ŵĺϷ���(�ض���ż�飩
function onPosCode(inputName,doPrompt){
	obj = eval("document.forms[0]."+inputName+".value");
	var code = trim(obj);
	if( code.length != 9 ){
		if( doPrompt)alert("������9λ���豸���!");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	//���ǰ׺
	var prefix = code.substring(0,1);
	if( prefix!="P" ){
		if( doPrompt) alert("POS�豸��ű����ԡ�P����ͷ��");
		eval("document.forms[0]."+inputName+".focus()") 
		eval("document.forms[0]."+inputName+".select()")
		return false;
	}
	return true;
}

//У���Ƿ����֣�����ʾ��
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


//************************�����㴦��������ѯ���ҳ��ʹ�ã�**************************

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


//************************�����㴦��������ѯ���ҳ��ʹ�ã�**************************

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