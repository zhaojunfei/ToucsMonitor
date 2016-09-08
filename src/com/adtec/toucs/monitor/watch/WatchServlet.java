package com.adtec.toucs.monitor.watch;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.adtec.toucs.monitor.comm.*;
import com.adtec.toucs.monitor.common.*;
/**
 * 接受实时监控信息
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */

public class WatchServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
    int acceptCount=0;
    //Initialize global variables
    FileOutputStream fout=null;
    public void init() throws ServletException {
        try{
            int port=0;
            Integer tmp=new Integer(this.getServletContext().getInitParameter("WatchPort"));
            port=tmp.intValue();
            fout=new FileOutputStream("watchunix.txt");
            if(this.getServletContext().getAttribute("WatchServer")==null){
                WatchServer watchServer=new WatchServer(port);
                this.getServletContext().setAttribute("WatchServer",watchServer);
                Thread thr=new Thread(watchServer);
                thr.start();
            }
        }catch(Exception exp){
            exp.printStackTrace();
        }
        acceptCount=0;
        ////Debug.println("acceptCount=0");
    }
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>WatchServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");

 
    }
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ////Debug.println("watch:accept data");
        //StringBuffer bufMsg=null;
        ServletInputStream in=null;
        //Messages msg=null;
        BufferedReader buffis=null;
        ////Debug.println("=============受到报文长度  ："+request.getContentLength());
        int length=request.getContentLength();
        in=request.getInputStream();
        if(length==-1){
            long lb;
            lb=System.currentTimeMillis();
            in=request.getInputStream();
            String strOut="read error,time="+String.valueOf(lb)+"\n";
            fout.write(strOut.getBytes());
            fout.flush();
            strOut="in.toString():+"+in.toString()+"\n";
            fout.write(strOut.getBytes());
            fout.flush();
        }
        try{

            //如果in为null将出错，打印出错误信息
            ////Debug.println(in.toString());
            Debug.fPrint("["+Util.getCurrentTime()+"] WatchServlet Received：["+String.valueOf(in.toString())+"]\n");
            WatchServer watchServer=(WatchServer)this.getServletContext().getAttribute("WatchServer");
            int port=0;
            Integer tmp=new Integer(this.getServletContext().getInitParameter("WatchPort"));
            port=tmp.intValue();
            if(watchServer==null){
                watchServer=new WatchServer(port);
                this.getServletContext().setAttribute("WatchServer",watchServer);
                Thread thr=new Thread(watchServer);
                thr.start();
            }
            buffis=new BufferedReader(new InputStreamReader(in));
            watchServer.sendStremDataToAllClient(buffis);
        }
        catch(Exception exp){
            Debug.println("受到报文长度为为空！");
            exp.printStackTrace();
        }
    }
    //Clean up resources
    public void destroy() {
        try{
            fout.close();
            Object obj1=this.getServletContext().getAttribute("WatchServer");
            if(obj1!=null){
                WatchServer watchServer=(WatchServer)obj1;
                watchServer.SrvSocketClose();
            }
        }catch (Exception exp){
        }
    }
}