<!--Ĭ�ϵ��豸����ΪATM-->
var deviceClass="A";

<!--ȥ���ַ������˵Ŀո�-->
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

<!--У����������-->
function checkIntData(dataStr){
	var char;
	for(i=0;i<dataStr.length;i++){
		char=dataStr.charAt(i);
		if(char<'0'||char>'9')
			return false;		
	}
	return true;
}

<!--����֧�к�����-->
function makeArray(n){
	this.length=n;
	for(var i=1;i<=n;i++){
		this[i]=0;
	}
	return this;
}

<!--�ı�����������ѡ��-->
function orgChange(idx){	
	formReg.txOrgId.value=formReg.orgId.value;
	formReg.branchs.selectedIndex=idx;
	formReg.agencyNo.value=formReg.branchs.options[idx].text;
}

<!--�ı��豸����ѡ��(������Ҫ,Ŀǰδʹ��)-->
function classChange(dClass){	
	deviceClass=dClass;
	formReg.atmCode.value="";	
}

<!--У���豸��ŵĺϷ���-->
function onAtmCode(input,doPrompt){
	<!--У���豸��ŵĺϷ���-->
	var code=input.value;
	var valid=true;
	var err="������4λ���ı�Ż�9λ��������!";
	if(code.length==4){
		<!--У��4λ����-->
		if(!checkIntData(code)) valid=false;
		else input.value=deviceClass+"5110"+code;
	}else if(code.length==9){
		<!--У�������ı��-->
		var preStr=code.substring(0,4);
		var nmStr=code.substring(5,8);
		if(preStr==""||!checkIntData(nmStr)) {
			err="�����Ų����Ϲ���!";
			valid=false;
		}
	}else{ valid=false; }
	if(!valid){ 
		if(doPrompt) alert(err); 
		input.value=""; 
	}
	return valid;
}

<!--У����Կ������Ϣ�ĺϷ���-->
function checkKeyInfo(){
	alert("�����Կ������Ϣ��");
	var formReg=document.formReg;
	if(formReg.CDKDes.value==null||formReg.CDKDes.value==""){
		alert("��ѡ��������Կ���ܷ�ʽ��");
		return false;
	}
	if(formReg.PIKDes.value==null||formReg.PIKDes.value==""){
		alert("��ѡ�����PIN��Կ���ܷ�ʽ��");
		return false;
	}
	if(formReg.MAKDes.value==null||formReg.MAKDes.value==""){
		alert("��ѡ����Ϣ��֤MAC��Կ���ܷ�ʽ��");
		return false;
	}
	return true;
}