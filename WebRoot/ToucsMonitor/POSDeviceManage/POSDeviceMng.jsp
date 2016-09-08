<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@ page import="java.util.*,com.adtec.toucs.monitor.POSDeviceManage.*" %>
<%@ page import="com.adtec.toucs.monitor.common.*,com.adtec.toucs.monitor.loginmanage.*"%>
<%
    try{
        LoginInfo login = (LoginInfo)request.getAttribute("Login");
        if (login == null) {
            throw new MonitorException(ErrorDefine.NOT_AUTHORIZED,"");
        }
%>
<html>
<head>
<title>POS�豸����</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../v5_css.css" type="text/css">
<link rel="stylesheet" href="/ToucsMonitor/ToucsMonitor/v5_css.css" type="text/css">
</head>
<script>
function check(){
	if (document.form1.pos_code.value.length != 19){
		alert("19λPOS��ű���Ϊ19λ");
		return false;
	}
    var IsReqCodeSel = false;
    for(i=0;i<5;i++){
        if(document.form1.reqCode[i].checked){
            IsReqCodeSel = true;
            break;
        }
    }
    if(!IsReqCodeSel){
        alert("��ѡ��������ͣ�");
        return false;
    }
    if(document.form1.pos_code.value==""){
        alert("������POS�豸��ţ�");
        return false;
    }
    if( !document.form1.reqCode[2].checked) {
    	if(!confirm("ȷʵҪ�ύ�ý��ף�")){
        	return false;
    	}
    }
    return true;

}
</script>
<body  bgcolor="#CCCCCC" text="#000000">
<form name="form1" method="post"   action="/ToucsMonitor/POSDeviceManage" onsubmit="return check();">
  <h2><font face="����">POS�豸����</font></h2>
  <table width="86%">
    <tr>
      <td colspan="5" nowrap>
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td width="20%" >19λPOS��ţ�</td>
      <td width="80%">
        <input type="text" name="pos_code" size="19" maxlength="19">
      </td>
    </tr>
  </table>
  <br>
  <table width="86%">
    <tr>
      <td colspan="10">ѡ��Ҫִ�еĲ���</td>
    </tr>
    <tr>
      <td <%=LoginManageBean.checkPermMask(login.getPermission(),"12101")==0?"":"style=\"display:none\""%> >
        <input type="radio" name="reqCode" class="radio" value="12101">
        POS����</td>
      <td <%=LoginManageBean.checkPermMask(login.getPermission(),"12102")==0?"":"style=\"display:none\""%>>
        <input type="radio" name="reqCode" class="radio" value="12102">
        POSͣ��</td>
      <td <%=LoginManageBean.checkPermMask(login.getPermission(),"12103")==0?"":"style=\"display:none\""%>>
        <input type="radio" name="reqCode" class="radio" value="12103" checked>
        POS��Կ����</td>
      <td <%=LoginManageBean.checkPermMask(login.getPermission(),"12106")==0?"":"style=\"display:none\""%>>
        <input type="radio" name="reqCode" class="radio" value="12106" disabled>
        �̳���Կ����</td>
      <!--td <%=LoginManageBean.checkPermMask(login.getPermission(),"12104")==0?"":"style=\"display:none\""%>>
        <input type="hidden" name="reqCode" class="radio" value="12104">
       </td-->
	<td <%=LoginManageBean.checkPermMask(login.getPermission(),"14003")==0?"":"style=\"display:none\""%>>
        <input type="radio" name="reqCode" class="radio" value="14003">
        ������Կ��־��ѯ </td>
    </tr>
  </table>
  <table width="86%">
    <tr>
      <td colspan="5" nowrap>
        <hr noshade>
      </td>
    </tr>
    <tr>
      <td colspan="5" nowrap>
        <div align="right">
          <input type="submit" name="Submit" value="�ύ"  class="inputbutton">
        </div>
      </td>
    </tr>
  </table>
  <br>
</form>
</body>
</html>
<%
    }
    catch (MonitorException ex){
        ex.errProc(request,response);
    }
%>