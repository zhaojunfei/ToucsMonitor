/*
 * 创建日期 2007-7-23
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.adtec.ccbbj.servletunion;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangyechi
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class TradeEngineServlet extends HttpServlet{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";
    //private static final int EpID = 1; 
    //Initialize global variables
    public void init() throws ServletException {
    }
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TradeEngine 开始了!");
        
        ServletContext sc = null;
        //获取上下文环境
        sc = getServletContext();
        response.setContentType(CONTENT_TYPE);
        request.setCharacterEncoding("GBK");
        //PrintWriter out = response.getWriter();
        /*
        out.println("<html><body>");
        out.println("<p>"+book_name+"</p>");
        out.println("<p>"+book_author+"</p>");
        out.println("<p>"+book_price+"</p>");
        out.println("<p>"+book_publish+"</p>");
        out.println("</body></html>");
        */
        //out.print("<a href=showcart.jsp>showcart</a><br>");
        String tradeCode = request.getParameter("tradeCode");
        System.out.println("tradeCode get is:"+tradeCode);
        //response.sendRedirect("/newten/UserLogin.jsp");
        response.sendRedirect("newten/UserLogin.jsp");
        //response.sendRedirect("./UserLogin.jsp");
        /*
        //获得交易码后，首先查找交易页面,查找交易是否与starring通讯
        Hashtable txTable = TxDataPoolManager.txDataPool;
        String txPage = DataPoolOper.getTxPage(txTable,tradeCode);
        String isTranStr = DataPoolOper.getPointText(txTable,"isTransfer",tradeCode);
        System.out.println("txPage: "+txPage);
        System.out.println("isTranStr:"+isTranStr);
        
        //String userId = session.getAttribute("user_ID");
        String userId = request.getParameter("user_ID");
        if(isTranStr == "true"){
            Hashtable txPageTable = TxPageDataPoolManager.txPageDataPool;
            String pageElements = DataPoolOper.getPointText2(txPageTable,"PageInputElements",txPage);
            DataBeanBuild dataBeanBuild = new DataBeanBuild();
            String StarrTxCode = DataPoolOper.getPointText(txTable,"starringCode",tradeCode);
            dataBeanBuild.setBaseElement(EpID,StarrTxCode,"","","",0,0);
            dataBeanBuild.doPost(request,response,pageElements,EpID);
            DataTransfer dataTransfer = new DataTransfer();
            boolean tranResult = dataTransfer.doTransfer(EpID);
            Hashtable pageTable =  PageDataPoolManager.pageDataPool;
        }else{
            txPage += ".jsp";
            response.sendRedirect(txPage+"?userID="+userId+"");
        }
        
//        String userId = request.getParameter("userID");
//        if(tradeCode == null||tradeCode.equals("")){
//            response.sendRedirect("subbook.jsp?userID="+userId+"");
//        }else if(tradeCode.equals("000001")){
//            response.sendRedirect("subbook.jsp?userID="+userId+"");
//        }else if(tradeCode.equals("000002")){
//            response.sendRedirect("subbook.jsp?userID="+userId+"");
//        }
        //response.sendRedirect("subbook.jsp?subook_id="+book_faid+"");
         */
    }
    //Clean up resources
    public void destroy() {
    }
    public static void main(String[] args) {
    }
}
