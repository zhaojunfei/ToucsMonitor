/*
 * �������� 2007-7-23
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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
        System.out.println("TradeEngine ��ʼ��!");
        
        ServletContext sc = null;
        //��ȡ�����Ļ���
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
        //��ý���������Ȳ��ҽ���ҳ��,���ҽ����Ƿ���starringͨѶ
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
