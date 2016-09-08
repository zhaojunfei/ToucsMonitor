package com.adtec.toucs.monitor.loginmanage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 登录控制Servlet</p>
 * <p>Description: 处理登录、签退等Http请求</p>
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
    //交易码：用户登录
    private static final String LOGIN="20001";
    //交易码：用户签退
    private static final String LOGOUT="20005";
    //交易码：登录用户查询（未使用）
    private static final String QUERYLOGIN="10204";//与查询用户的资源号相同
    //交易码：强制签退用户（未使用）
    private static final String FORCELOGOUT="10203";//与删除用户的资源号相同
    
    public void init() throws ServletException {
        //取登录用户有效时间
        String sTimeOut=getServletContext().getInitParameter("LoginTimeOut");
        try{
            LoginInfo.timeOut=Integer.parseInt(sTimeOut);
            }catch(Exception e1){}
            //初始化资源列表
            SqlAccess sq=null;
            try{
                Debug.println("初始化资源列表...");
                sq=SqlAccess.createConn();
                LoginManageBean.initMaskTable(sq);
                Debug.println("初始化资源列表结束...");
            }catch(Exception e2){
                //记录日志：初始化资源列表失败
            }finally{
                sq.close();
            }
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取请求参数
        String reqCode=request.getParameter("reqCode");
        String uid=request.getParameter("uid");
        String IP=request.getRemoteAddr();
        String tarJsp=null;

        //用户登录,转登录页面
        if(reqCode.equals(LOGIN))
            tarJsp="/LoginManage/Login.htm";

        //登录用户查询(以后扩展需要,目前未提供)
        else if(reqCode.equals(QUERYLOGIN)){
            try{
                LoginInfo login=checkLogin(request,response);
                //校验用户是否有登录用户查询的权限
                LoginManageBean.operValidate(login,QUERYLOGIN);
                Vector v=LoginManageBean.qryAllLogin();
                request.setAttribute("login_list",v);
                tarJsp="/ToucsMonitor/LoginManage/LoginList.jsp?uid="+uid;
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }

        //强制签退用户(以后扩展需要,目前未提供)
        else if(reqCode.equals(FORCELOGOUT)){
            String tagUid=request.getParameter("tagUid");
            try{
                LoginInfo login=checkLogin(request,response);
                //校验用户是否有强制签退用户的权限
                LoginManageBean.operValidate(login,FORCELOGOUT);
                LoginManageBean.LogOut(tagUid);
                PromptBean prompt=new PromptBean("登录用户管理");
                prompt.setPrompt("用户签退成功!IP地址为["+IP+"]");
                prompt.setButtonUrl(0,"确定","/ToucsMonitor/login?reqCode="+QUERYLOGIN
                                    +"&uid="+login.getUserID());
                request.setAttribute("prompt",prompt);
                tarJsp="/ToucsMonitor/Success.jsp";
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }

        //转向目标页面
        if(tarJsp!=null)
            toPage(request,response,tarJsp);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取请求参数
        String reqCode=request.getParameter("reqCode");
        String uid=request.getParameter("uid");
        String IP=request.getRemoteAddr();
        String tarJsp=null;

        //用户登录
        if(reqCode.equals(LOGIN)){
            String pwd=request.getParameter("pwd");
            try{
                LoginInfo logIn=LoginManageBean.demoLogIn(uid,pwd,IP);
                //清除Session
                HttpSession session=request.getSession();
                if(session!=null)
                    session.invalidate();
                //将标识用户会话的ID写入Cookie,这个会话ID是通过对用户ID进行随机加密获得
                String sid=logIn.getSessionID();
                Cookie cookie=new Cookie("sid",sid);
                cookie.setPath("/ToucsMonitor/");
                //设置Cookie的有效期
                cookie.setMaxAge(-7*14*60*60);
                response.addCookie(cookie);
                //回送客户端
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
                //记录日志：用户登录成功
                writeLog(uid,IP,"用户["+uid+"]登录,IP地址为["+IP+"]");
            }catch(MonitorException exp){
                //记录日志：用户登录失败
                //writeLog(uid,IP,"登录失败");
                errProc(request,response,exp);
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        //用户签退
        else if(reqCode.equals(LOGOUT)){
            try{
                //校验用户是否登录
                LoginInfo login=checkLogin(request,response);
                //签退用户
                LoginManageBean.LogOut(login.getUserID());
                //if(client!=null&&client.equals("1")){
                response.setContentType(CONTENT_TYPE);
                PrintWriter out=response.getWriter();
                out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
                out.println("<Deal>");
                out.println("<DealCode>"+LOGIN+"</DealCode>");
                out.println("<ErrCode>0000</ErrCode>");
                out.println("</Deal>");
                writeLog(uid,IP,"用户["+uid+"]签退,IP地址为["+IP+"]");
            }catch(MonitorException exp){
                writeLog(uid,IP,"用户签退失败，IP地址为["+IP+"]");
                errProc(request,response,exp);
            }
        }
        //转向目标页面
        if(tarJsp!=null)
            toPage(request,response,tarJsp);
    }
    //Clean up resources
    public void destroy() {
    }
}