package com.adtec.toucs.monitor.loginmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ��¼����Servlet</p>
 * <p>Description: �����¼��ǩ�˵�Http����</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author sunyl
 * @version 2.0
 */

public class LoginManageServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
    //�����룺�û���¼
    private static final String LOGIN="20001";
    //�����룺�û�ǩ��
    private static final String LOGOUT="20005";
    //�����룺��¼�û���ѯ��δʹ�ã�
    private static final String QUERYLOGIN="10204";//���ѯ�û�����Դ����ͬ
    //�����룺ǿ��ǩ���û���δʹ�ã�
    private static final String FORCELOGOUT="10203";//��ɾ���û�����Դ����ͬ
    
    public void init() throws ServletException {
        //ȡ��¼�û���Чʱ��
        String sTimeOut=getServletContext().getInitParameter("LoginTimeOut");
        try{
            LoginInfo.timeOut=Integer.parseInt(sTimeOut);
            }catch(Exception e1){}
            //��ʼ����Դ�б�
            SqlAccess sq=null;
            try{
                Debug.println("��ʼ����Դ�б�...");
                sq=SqlAccess.createConn();
                LoginManageBean.initMaskTable(sq);
                Debug.println("��ʼ����Դ�б����...");
            }catch(Exception e2){
                //��¼��־����ʼ����Դ�б�ʧ��
            }finally{
                sq.close();
            }
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ȡ�������
        String reqCode=request.getParameter("reqCode");
        String uid=request.getParameter("uid");
        String IP=request.getRemoteAddr();
        String tarJsp=null;

        //�û���¼,ת��¼ҳ��
        if(reqCode.equals(LOGIN))
            tarJsp="/LoginManage/Login.htm";

        //��¼�û���ѯ(�Ժ���չ��Ҫ,Ŀǰδ�ṩ)
        else if(reqCode.equals(QUERYLOGIN)){
            try{
                LoginInfo login=checkLogin(request,response);
                //У���û��Ƿ��е�¼�û���ѯ��Ȩ��
                LoginManageBean.operValidate(login,QUERYLOGIN);
                Vector v=LoginManageBean.qryAllLogin();
                request.setAttribute("login_list",v);
                tarJsp="/ToucsMonitor/LoginManage/LoginList.jsp?uid="+uid;
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }

        //ǿ��ǩ���û�(�Ժ���չ��Ҫ,Ŀǰδ�ṩ)
        else if(reqCode.equals(FORCELOGOUT)){
            String tagUid=request.getParameter("tagUid");
            try{
                LoginInfo login=checkLogin(request,response);
                //У���û��Ƿ���ǿ��ǩ���û���Ȩ��
                LoginManageBean.operValidate(login,FORCELOGOUT);
                LoginManageBean.LogOut(tagUid);
                PromptBean prompt=new PromptBean("��¼�û�����");
                prompt.setPrompt("�û�ǩ�˳ɹ�!IP��ַΪ["+IP+"]");
                prompt.setButtonUrl(0,"ȷ��","/ToucsMonitor/login?reqCode="+QUERYLOGIN
                                    +"&uid="+login.getUserID());
                request.setAttribute("prompt",prompt);
                tarJsp="/ToucsMonitor/Success.jsp";
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }

        //ת��Ŀ��ҳ��
        if(tarJsp!=null)
            toPage(request,response,tarJsp);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ȡ�������
        String reqCode=request.getParameter("reqCode");
        String uid=request.getParameter("uid");
        String IP=request.getRemoteAddr();
        String tarJsp=null;

        //�û���¼
        if(reqCode.equals(LOGIN)){
            String pwd=request.getParameter("pwd");
            try{
                LoginInfo logIn=LoginManageBean.demoLogIn(uid,pwd,IP);
                //���Session
                HttpSession session=request.getSession();
                if(session!=null)
                    session.invalidate();
                //����ʶ�û��Ự��IDд��Cookie,����ỰID��ͨ�����û�ID����������ܻ��
                String sid=logIn.getSessionID();
                Cookie cookie=new Cookie("sid",sid);
                cookie.setPath("/ToucsMonitor/");
                //����Cookie����Ч��
                cookie.setMaxAge(-7*14*60*60);
                response.addCookie(cookie);
                //���Ϳͻ���
                String client=request.getParameter("clientType");
                if(client!=null&&client.equals("1")){
                    response.setContentType(CONTENT_TYPE);
                    PrintWriter out=response.getWriter();
                    out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
                    out.println("<Deal>");
                    out.println("<DealCode>"+LOGIN+"</DealCode>");
                    out.println("<ErrCode>0000</ErrCode>");
                    out.println(logIn.toXML());
                    out.println("</Deal>");
                }else{
                    tarJsp="/ToucsMonitor/Main.jsp?uid="+uid+ "&useDate="+logIn.selectUserDate();
                }
                //��¼��־���û���¼�ɹ�
                writeLog(uid,IP,"�û�["+uid+"]��¼,IP��ַΪ["+IP+"]");
            }catch(MonitorException exp){
                //��¼��־���û���¼ʧ��
                //writeLog(uid,IP,"��¼ʧ��");
                errProc(request,response,exp);
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        //�û�ǩ��
        else if(reqCode.equals(LOGOUT)){
            try{
                //У���û��Ƿ��¼
                LoginInfo login=checkLogin(request,response);
                //ǩ���û�
                LoginManageBean.LogOut(login.getUserID());
                //if(client!=null&&client.equals("1")){
                response.setContentType(CONTENT_TYPE);
                PrintWriter out=response.getWriter();
                out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
                out.println("<Deal>");
                out.println("<DealCode>"+LOGIN+"</DealCode>");
                out.println("<ErrCode>0000</ErrCode>");
                out.println("</Deal>");
                writeLog(uid,IP,"�û�["+uid+"]ǩ��,IP��ַΪ["+IP+"]");
            }catch(MonitorException exp){
                writeLog(uid,IP,"�û�ǩ��ʧ�ܣ�IP��ַΪ["+IP+"]");
                errProc(request,response,exp);
            }
        }
        //ת��Ŀ��ҳ��
        if(tarJsp!=null)
            toPage(request,response,tarJsp);
    }
    //Clean up resources
    public void destroy() {
    }
}