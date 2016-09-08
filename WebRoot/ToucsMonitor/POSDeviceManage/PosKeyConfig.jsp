<%@ page language="java" contentType="text/html; charset=gb2312" %>

<%@ page import="com.adtec.toucs.monitor.systemmanage.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="keyInfo" class="com.adtec.toucs.monitor.devicemanage.AtmKeyInfo"  scope="request"/>



<%! private void initList(String listName,String defValue,HttpServletRequest req,JspWriter out){
	List list=(List)req.getAttribute(listName);
	String flag="";
	if(list!=null){
		for(int i=0;i<list.size();i++){
			CodeBean code=(CodeBean)list.get(i);
			if(defValue!=null&&defValue.equals(code.getCodeId()))
				flag=" SELECTED";
			else flag="";
			try{
				out.println("<option value=\""+code.getCodeId()+"\""+flag+">"
					+code.getCodeDesc()+"</option>");
			}catch(IOException exp){}
		}
	}
}%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
<link rel="stylesheet" href="/v5_css.css" type="text/css">
</head>
<script language="javascript" src="/ToucsMonitor/DeviceManage/AtmRegJs.js">
</script>

<script language="javascript">
function onChangeDesType(selDesType,txtKeyLen){
	var desType=selDesType.value;
	if(desType=="0") txtKeyLen.value="8";
        else if( desType=="1") txtKeyLen.value="16";
	else txtKeyLen.value="";
}
function checkKeyLen(selDesType,txtKeyLen){
	var desType=selDesType.value;
	var keyLen=txtKeyLen.value;
	if(desType=="0"&&txtKeyLen!="8"){txtKeyLen.value="8"; return true;}
	else if(desType=="1"&&keyLen!="8"&&keyLen!="16"){alert("��Կ����Ϊ8��16��"); txtKeyLen.focus(); return false;}
	else if(desType=="2"&&keyLen==""){alert("��ȷ����Կ���ȣ�");  txtKeyLen.focus(); return false;}
	return true;
}
function doSubmit(){
	//alert("У��������");
	if(!checkKeyLen(formReg.CDKDes,formReg.CDKLen)) return false;
	if(!checkKeyLen(formReg.PIKDes,formReg.PIKLen)) return false;
	if(!checkKeyLen(formReg.MAKDes,formReg.MAKLen)) return false;
	return confirm("ȷ�����������ύ��");
}
</script>
<body   bgcolor="#CCCCCC" text="#000000">
<%
	String uid=request.getParameter("uid");
	String flag=request.getParameter("flag");
	if(flag==null) flag="5";
%>
<h2><font face="����">POS�豸��Կ����</font></h2>
<form name="formReg" method="post" action="/ToucsMonitor/posdeviceconfig?reqCode=13102&target=keySet&uid=<%=uid%>" onSubmit="javascript:return doSubmit()">
  <input type=hidden name="flag" value=<%=flag%>>
  <table width="80%" border="0" cellpadding="1" cellspacing="1">
    <tr bgcolor="#CCCCCC">
      <td colspan="5">
        <hr noshade>
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="20%" nowrap align="right">POS˳��ţ�</td>
      <td width="20%" nowrap>
        <input type="text" name="atmCode" value="<%=keyInfo.getAtmCode()%>" READONLY class="readOnly" size="20">
      </td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap>��</td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td colspan="5">
        <hr noshade>
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="20%" nowrap align="right">��������Կ���ܷ�ʽ��</td>
      <td width="20%" nowrap>
        <select name="CDKDes" size="1" onChange="javascript:onChangeDesType(CDKDes,CDKLen)">
          <option>��ѡ��</option>
          <%initList("desTypeList",keyInfo.getDesTypeStr(0),request,out);%>
        </select>
      </td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap align="right">��Կ���ȣ�</td>
      <td width="20%" nowrap>
        <input type="text" name="CDKLen" onBlur="javascript:checkKeyLen(CDKDes,CDKLen)" value="<%=keyInfo.getCdkLen()%>" size="20">
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="20%" nowrap align="right">����PIN��Կ���ܷ�ʽ��</td>
      <td width="20%" nowrap>
        <select name="PIKDes" size="1" onChange="javascript:onChangeDesType(PIKDes,PIKLen)">
          <option>��ѡ��</option>
          <%initList("desTypeList",keyInfo.getDesTypeStr(1),request,out);%>
        </select>
      </td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap align="right">��Կ���ȣ�</td>
      <td width="20%" nowrap>
        <input type="text" name="PIKLen" onBlur="javascript:checkKeyLen(PIKDes,PIKLen)" value="<%=keyInfo.getPikLen()%>" size="20">
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="20%" nowrap align="right">��Ϣ��֤MAC��Կ���ܷ�ʽ��</td>
      <td width="20%" nowrap>
        <select name="MAKDes" size="1" onChange="javascript:onChangeDesType(MAKDes,MAKLen)">
          <option>��ѡ��</option>
          <%initList("desTypeList",keyInfo.getDesTypeStr(2),request,out);%>
        </select>
      </td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap align="right">��Կ���ȣ�</td>
      <td width="20%" nowrap>
        <input type="text" name="MAKLen" onBlur="javascript:checkKeyLen(MAKDes,MAKLen)" value="<%=keyInfo.getMakLen()%>" size="20">
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td width="20%" nowrap align="right">PINBLOCK��ʽ��</td>
      <td width="20%" nowrap>
        <select name="pinblkMet" value=<%=keyInfo.getPinblkMet()%>>
          <option>��ѡ��</option>
          <%initList("pinblkList",keyInfo.getPinblkMet(),request,out);%>
        </select>
      </td>
      <td width="20%" nowrap>��</td>
      <td width="20%" nowrap align="right">MAC�㷨��</td>
      <td width="20%" nowrap>
        <select name="macMet">
          <option>��ѡ��</option>
          <%initList("macList",keyInfo.getMacMet(),request,out);%>
        </select>
      </td>
    </tr>
    <tr bgcolor="#CCCCCC">
      <td colspan="5">
        <hr noshade>
      </td>
    </tr>
  </table>
  <table width="80%" >
    <tr>
      <td>
        <div align="right">
          <input type="submit" name="submit" value="�ύ" class="inputbutton">
          <input type="button" name="cancel" value="ȡ��" class="inputbutton" onClick="javascript:location.href='/ToucsMonitor/posdeviceconfig?uid=<%=uid%>'">
        </div>
      </td>
    </tr>
  </table>
</form>
</body>
</html>