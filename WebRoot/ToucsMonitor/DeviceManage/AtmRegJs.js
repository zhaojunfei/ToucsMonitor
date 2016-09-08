<!--默认的设备种类为ATM-->
var deviceClass="A";

<!--去掉字符串两端的空格-->
function trim(src){
	if(src==null)
		return src;
	start=src.search(' ');
	end=src.length-1;
	for(i=end;i>=0;i--){
		if(src.charAt(i)!=' ')
			break;
		end--;
	}
	return src.substring(start,end);
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

<!--校验整型数据-->
function checkIntData(dataStr){
	var char;
	for(i=0;i<dataStr.length;i++){
		char=dataStr.charAt(i);
		if(char<'0'||char>'9')
			return false;		
	}
	return true;
}

<!--生成支行号数组-->
function makeArray(n){
	this.length=n;
	for(var i=1;i<=n;i++){
		this[i]=0;
	}
	return this;
}

<!--改变所属机构的选择-->
function orgChange(idx){	
	formReg.txOrgId.value=formReg.orgId.value;
	formReg.branchs.selectedIndex=idx;
	formReg.agencyNo.value=formReg.branchs.options[idx].text;
}

<!--改变设备种类选择(扩充需要,目前未使用)-->
function classChange(dClass){	
	deviceClass=dClass;
	formReg.atmCode.value="";	
}

<!--校验设备编号的合法性-->
function onAtmCode(input,doPrompt){
	<!--校验设备编号的合法性-->
	var code=input.value;
	var valid=true;
	var err="请输入4位数的编号或9位的联网号!";
	if(code.length==4){
		<!--校验4位数字-->
		if(!checkIntData(code)) valid=false;
		else input.value=deviceClass+"5110"+code;
	}else if(code.length==9){
		<!--校验完整的编号-->
		var preStr=code.substring(0,4);
		var nmStr=code.substring(5,8);
		if(preStr==""||!checkIntData(nmStr)) {
			err="联网号不符合规则!";
			valid=false;
		}
	}else{ valid=false; }
	if(!valid){ 
		if(doPrompt) alert(err); 
		input.value=""; 
	}
	return valid;
}

<!--校验密钥配置信息的合法性-->
function checkKeyInfo(){
	alert("检查密钥配置信息！");
	var formReg=document.formReg;
	if(formReg.CDKDes.value==null||formReg.CDKDes.value==""){
		alert("请选择传输主密钥加密方式！");
		return false;
	}
	if(formReg.PIKDes.value==null||formReg.PIKDes.value==""){
		alert("请选择个人PIN密钥加密方式！");
		return false;
	}
	if(formReg.MAKDes.value==null||formReg.MAKDes.value==""){
		alert("请选择消息验证MAC密钥加密方式！");
		return false;
	}
	return true;
}