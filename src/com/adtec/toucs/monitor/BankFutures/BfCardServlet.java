package com.adtec.toucs.monitor.BankFutures;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.ParameterSet.*;
import com.adtec.toucs.monitor.loginmanage.*;


/**
 * 卡表业务处理Servlet，。用来接受客户请求，并调用相应处理方法处理之
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author sunyl
 * @version 1.0
 */
public class BfCardServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final String REGCARD="17401";  //新增卡表信息请求码
    private static final String MODCARD="17402";  //修改卡表信息请求码
    private static final String MODCARDREQ1="1740201";  //从查询页面发起的修改卡表信息请求码
    private static final String MODCARDREQ2="1740202";  //从管理页面发起的修改卡表信息请求码
    private static final String DELCARD="17403";  //删除卡表信息请求码
    private static final String QUERYCARD="17404";  //查询卡表信息请求码
    private static final String QUERYHANDCARD="17405";	//手输卡号查询
    private static final String CANCEL="CANCEL";  //取消请求码

    private static final String PAGEPROCESSOR="PAGEPROCESSOR";
    private static final String ERRORPAGEURL="";
    private static final String MG7550="12005";

    private static PageProcessor pageProcessor=null;

    //业务Bean的实例
    private static BfCardBean parameterSetBean=new BfCardBean();

    //Initialize global variables
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.removeAttribute("Message");
        try{
            String lTarger=execDoGet(request,response);
            request.setAttribute("uid",request.getParameter("uid"));
            Debug.println("============================="+lTarger);
            RequestDispatcher dp=request.getRequestDispatcher(lTarger);
            dp.forward(request,response);
        }catch(MonitorException exp){
            this.errProc(request,response,exp);
        }
    }

    private String execDoGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException,MonitorException{
        commProcess(request,response);
        String lTarger="";
        String reqCode=request.getParameter("reqCode");
        System.out.println("servlet类接收到的参数是"+reqCode);
        if(reqCode!=null) reqCode=reqCode.trim();
        try{
            if(reqCode.equals(REGCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(0);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("cardClass",queryCode.getCardClass());
                request.setAttribute("cardType",queryCode.getCardType());
                lTarger="/ToucsMonitor/BankFutures/CardReg.jsp";
            } else if(reqCode.equals(MODCARD)) {
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",MODCARDREQ1);
                lTarger="/ToucsMonitor/BankFutures/CardQuery.jsp";
            } else if(reqCode.equals(QUERYCARD) || reqCode.equals(DELCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",QUERYCARD);
                lTarger="/ToucsMonitor/BankFutures/CardQuery.jsp";
            } else if(reqCode.equals(CANCEL)) {
                return cancel(request);
            } else if(reqCode.equals(QUERYHANDCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",QUERYHANDCARD);
                lTarger="/ToucsMonitor/BankFutures/HandCardQuery.jsp";
            }
        }catch(MonitorException e){
            e.printStackTrace();
            throw e;
        }
        return lTarger;
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lTarget="";
        try{
            lTarget=exec(request,response);
            request.setAttribute("uid",request.getAttribute("uid"));
            RequestDispatcher requestDispatcher=request.getRequestDispatcher(lTarget);
            requestDispatcher.forward(request,response);
        }catch(MonitorException exp){
            this.errProc(request,response,exp);
        }
    }

    //Clean up resources
    public void destroy() {
    }

    private String exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,MonitorException{
        LoginInfo loginfo=commProcess(request,response);
        HttpSession session = request.getSession();
        String lTarget="";
        String reqCode=request.getParameter("reqCode");
        if(reqCode==null || reqCode.trim().length()==0) return ERRORPAGEURL;
        reqCode=reqCode.trim();
        if(reqCode.length()==0) return ERRORPAGEURL;
        Debug.println("requset code ======================:"+reqCode);
        try{
            if(reqCode.equals(REGCARD)){
                CardTypeStruct cardTypeStruct=getCardDataA(request);
                parameterSetBean.addCardType(cardTypeStruct);
                String Message="";
                String retMessage="";
                try{
                    retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                }catch(MonitorException mexp){
                    String target="/ToucsMonitor/BfCardServlet?reqCode=17404";
                    String btnTitle="查询卡表";
                    request.setAttribute("targetURL",target);
                    request.setAttribute("btnTitle",btnTitle);
                    Message="成功增加卡表信息到数据中，但";
                    throw new MonitorException(mexp.getErrCode(),Message+mexp.getMessage());
                }finally{
                    String userOperRstDesc="新增加一条卡表信息,卡表信息:";
                    userOperRstDesc=userOperRstDesc+"卡号起始位="+cardTypeStruct.getCardStart()+"卡适配偏移="+cardTypeStruct.getCardMatch();
                    userOperRstDesc=userOperRstDesc+"卡号描述="+cardTypeStruct.getCardDesc();
                    userOperRstDesc=userOperRstDesc+"；已经通知ATMP,返回的处理结果："+retMessage;
                    this.writeLog(loginfo,userOperRstDesc);
                }
                Message="增加卡表信息成功，";
                Message=Message+retMessage;
                request.setAttribute("Message",Message);

                PromptBean prompt=new PromptBean("卡表信息管理");
                prompt.setPrompt(Message);
                String nurl="/ToucsMonitor/bfCardservlet?reqCode="+QUERYCARD;
                prompt.setButtonUrl(0,"确定",nurl);
                request.setAttribute("prompt",prompt);
                lTarget="/ToucsMonitor/Success.jsp";

            } else if(reqCode.equals(MODCARD)) {
                Debug.println("接收到修改卡表信息请求，取得修改的值，提交修改请求");
                CardTypeStruct cardTypeStruct=getCardDataA(request);
                Debug.println("parameterSetBean.updateCardType");

                Integer tmpold=null;
                tmpold = new Integer(request.getParameter("oldCardStart"));
                int oldCardStart = tmpold.intValue();
                
                tmpold = new Integer(request.getParameter("oldCardMatch"));
                int oldCardMatch = tmpold.intValue();
                
                String oldCardDesc = request.getParameter("oldCardDesc");
                System.out.println("页面传递的参数是:"+request.getParameter("cardDesc"));
                System.out.println("页面传递的参数是:"+toucsString.unConvert(oldCardDesc));
                System.out.println("页面传递的参数是:"+toucsString.Convert(oldCardDesc));
                
                parameterSetBean.updateCardType(cardTypeStruct,oldCardStart,oldCardMatch,oldCardDesc);
                Debug.println("parameterSetBean.updateCardType  end");

                String retMessage="";
                String Message="";
                try{
                    retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                }catch(MonitorException mexp){
                    String target="/ToucsMonitor/BfCardServlet?reqCode=17404";
                    String btnTitle="查询卡表";
                    request.setAttribute("targetURL",target);
                    request.setAttribute("btnTitle",btnTitle);
                    Message="成功修改卡表信息，但";
                    throw new MonitorException(mexp.getErrCode(),Message+mexp.getMessage());
                }finally{
                    String userOperRstDesc="修改前卡表信息:";
                    userOperRstDesc=userOperRstDesc+"卡号起始位="+String.valueOf(oldCardStart) +"卡适配标识="+String.valueOf(oldCardMatch);
                    userOperRstDesc=userOperRstDesc+"卡号描述="+oldCardDesc;
                    userOperRstDesc=userOperRstDesc+"修改后:";
                    userOperRstDesc=userOperRstDesc+"卡号起始位="+cardTypeStruct.getCardStart()+"卡适配标识="+cardTypeStruct.getCardMatch();
                    userOperRstDesc=userOperRstDesc+"卡号描述="+cardTypeStruct.getCardDesc();
                    userOperRstDesc=userOperRstDesc+";"+retMessage;
                    this.writeLog(loginfo,userOperRstDesc);
                }
                Message="修改卡表信息成功，";
                Message=Message+retMessage;
                PromptBean prompt=new PromptBean("卡表信息管理");
                prompt.setPrompt(Message);
                String nurl="/ToucsMonitor/bfCardservlet?reqCode="+QUERYCARD;
                prompt.setButtonUrl(0,"确定",nurl);
                request.setAttribute("prompt",prompt);
                lTarget="/ToucsMonitor/Success.jsp";       
            } else if(reqCode.equals(MODCARDREQ1)) {
                //接收到要修改卡表信息请求（用户输入了要修改的卡表索引），用户输入的索引查询，如果查询结果为1，则转到修改叶面，
                //如果查询的结果不为1，则转到查询结果叶面。如果查询结果为0，则返回到修改请求叶面
            	/*查询********************************************************/
                CardTypeStruct queryCardTypeStruct=getCardDataQ(request);
                List queryList=parameterSetBean.QueryCardType(queryCardTypeStruct);
                //把查询条件添加到session中。
                request.getSession().setAttribute("Query_Card_Condion",queryCardTypeStruct);
                /*查询  End******************************************************/
                if(queryList==null || queryList.size()==0){
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(1);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("Message","没有满足你输入条件的记录，请重新输入！");
                    request.setAttribute("reqCode",MODCARD);
                    lTarget="/ToucsMonitor/BankFutures/CardQuery.jsp";
                }else if(queryList.size()==1){  
                    CardTypeStruct cardTypeStruct=(CardTypeStruct)queryList.get(0);
                    request.setAttribute("CardTypeStruct",cardTypeStruct);
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/BankFutures/CardModify.jsp";
                }else if(queryList.size()>1){//查询到的数据〉1，则转到查询结果叶面
                    session.setAttribute("QueryCardRstList",queryList);
                    paginate(queryList,1,request);
                    //查询银行代码，卡种类，卡类型数据
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/BankFutures/CardQueryRst.jsp";
                }
            } else if(reqCode.equals(MODCARDREQ2)) {
                /**
                 *用户操作，选择某一要修改得卡表信息。则先查询用户选择的卡表信息，如果该卡表信息
                 *已经被其他用户删除，则提示该卡表信息已经被删除。否则导向到修改页面
                 **/
            	//取得用户选择的卡表信息
                Debug.println("deal MODIFYCARDTYPEREQUEST2  reuest: begin");
                String strIndex="";
                int index=0;
                List queryList=(List)session.getAttribute("QueryCardRstList");

                if(queryList==null){
                    throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
                }
                strIndex=request.getParameter("index");
                String strCurrPageNum=request.getParameter("CurrPageNum");
                Integer intCurrPageNum=new Integer(strCurrPageNum);
                Debug.println("==========================="+strCurrPageNum);
                Debug.println("==========================="+intCurrPageNum.intValue());
                if(strIndex!=null){
                    Integer tmp=new Integer(strIndex);
                    index=(intCurrPageNum.intValue()-1)*pageProcessor.getPageSize()+tmp.intValue();
                    Debug.println("index==========================="+index);
                }else {
                    throw new MonitorException(ErrorDefine.PLEASESELECTMODCARDINFOISNULL,ErrorDefine.PLEASESELECTMODCARDINFOISNULLDESC);
                }
                if(index>=queryList.size()){
                    throw new MonitorException(ErrorDefine.UNKNOWERROR,ErrorDefine.UNKNOWERRORDESC);
                }
                CardTypeStruct cardTypeStruct=(CardTypeStruct)queryList.get(index);

                //根据用户输入的卡表信息查询
                List queryNewList=parameterSetBean.QueryCardType(cardTypeStruct);
                /*查询  End******************************************************/

                //如果查询结果为0，则导向到错误页面
                if(queryNewList==null || queryNewList.size()==0)
                    throw new MonitorException(ErrorDefine.OTHERDELETETHISCARDID,ErrorDefine.OTHERDELETETHISCARDIDDESC);
                else{
                    //如果查询结果不为0，则导向到修改页面.如果根据用户输入的查询，
                    //查询到多条结果呢？暂时不处理这样的情况（考虑到这样的情况在实际上不存在）
                    CardTypeStruct cardTypeStruct1=(CardTypeStruct)queryNewList.get(0);
                    request.setAttribute("CardTypeStruct",cardTypeStruct1);
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/BankFutures/CardModify.jsp";
                }
                Debug.println("deal MODIFYCARDTYPEREQUEST2  reuest: End");
            } else if(reqCode.equals(DELCARD)) {
                //取得要删除的卡表信息结构体，并删除之。
                //重新从数据库中查询（取出用户在上一个页面设置的查询条件）
                String strIndex="";
                int index=0;

                //取得session中的查询结果集
                List queryList=(List)session.getAttribute("QueryCardRstList");
                //如果session过期，则抛出异常，提示用户重新登陆
                if(queryList==null){
                    throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
                }
                strIndex=request.getParameter("index");
                String strCurrPageNum=request.getParameter("CurrPageNum");
                request.setAttribute("CurrPageNum",strCurrPageNum);
                Integer intCurrPageNum=new Integer(strCurrPageNum);
                if(strIndex!=null){
                    Integer tmp=new Integer(strIndex);
                    index=(intCurrPageNum.intValue()-1)*pageProcessor.getPageSize()+tmp.intValue();
                    Debug.println("index==========================="+index);
                }else {
                    throw new MonitorException(ErrorDefine.PLEASESELECTDELCARDINFOISNULL,ErrorDefine.PLEASESELECTDELCARDINFOISNULLDESC);
                }
                if(index>=queryList.size()){
                    throw new MonitorException(ErrorDefine.UNKNOWERROR,ErrorDefine.UNKNOWERRORDESC);
                }

                CardTypeStruct cardTypeStruct=(CardTypeStruct)queryList.get(index);;
                //删除
                parameterSetBean.deleteCardType(cardTypeStruct);
                //通知ATMP
                String retMessage="";
                try{
                    retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                }catch(MonitorException mexp){
                    String target="/ToucsMonitor/BfCardServlet?reqCode=17404";
                    String btnTitle="查询卡表";
                    request.setAttribute("targetURL",target);
                    request.setAttribute("btnTitle",btnTitle);
                    String Message="成功从数据库中删除卡表信息，但";
                    retMessage=Message+mexp.getMessage();
                }finally{
                    //设置日志信息
                    String userOperRstDesc="删除一条卡表信息,卡表信息:";
                    userOperRstDesc=userOperRstDesc+"卡号起始位="+cardTypeStruct.getCardStart()+"卡适配标识="+cardTypeStruct.getCardMatch();
                    userOperRstDesc=userOperRstDesc+"卡号描述="+cardTypeStruct.getCardDesc();
                    userOperRstDesc=userOperRstDesc+"；已经通知ATMP,返回的处理结果："+retMessage;
                    //写入日志
                    this.writeLog(loginfo,userOperRstDesc);
                }

                //设置通知ATMP返回的信息
                request.setAttribute("Message",retMessage);
                //重新查询
                Object obj2=request.getSession().getAttribute("Query_Card_Condion");

                if(obj2==null){
                    throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
                }else{
                    CardTypeStruct qCardTypeStruct=(CardTypeStruct)obj2;
                    Debug.println("begin QueryCardType(qCardTypeStruct)");
                    List queryListN=parameterSetBean.QueryCardType(qCardTypeStruct);
                    Debug.println("intCurrPageNum.intValue()==========================="+intCurrPageNum.intValue());
                    Debug.println("End QueryCardType(qCardTypeStruct)");
                    //重新设置查询结果集
                    session.setAttribute("QueryCardRstList",queryListN);
                    paginate(queryListN,intCurrPageNum.intValue(),request);
                }
                //查询银行代码，卡种类，卡类型数据
                QueryCode queryCode=new QueryCode();
                queryCode.query(0);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("cardClass",queryCode.getCardClass());
                request.setAttribute("cardType",queryCode.getCardType());

                lTarget="/ToucsMonitor/BankFutures/CardQueryRst.jsp";
                } else if(reqCode.equals(QUERYCARD)) {
                	//查询卡表信息
                    //取得查询条件
                    CardTypeStruct cardTypeStruct=getCardDataQ(request);
                    //查询
                    List queryList=parameterSetBean.QueryCardType(cardTypeStruct);
                    session.setAttribute("QueryCardRstList",queryList);
                    //添加查询条件添加到session中
                    request.getSession().setAttribute("Query_Card_Condion",cardTypeStruct);
                    //查询银行代码，卡种类，卡类型数据
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    paginate(queryList,1,request);

                    lTarget="/ToucsMonitor/BankFutures/CardQueryRst.jsp";
                } else if(reqCode.equals(QUERYHANDCARD)) {
                	//查询手输卡表信息
                    CardTypeStruct cardTypeStruct;
                    String isFirstTime = request.getParameter("isFirstTime");
                    String reQueryFlag = request.getParameter("reQueryFlag");
                    if( reQueryFlag == null )  reQueryFlag ="";
                    if( isFirstTime.trim().equalsIgnoreCase("1") ){ //取得查询条件
                    	cardTypeStruct=getCardDataQ(request);
                    	session.setAttribute("QueryCardType", cardTypeStruct );
                    }else {
                    	cardTypeStruct = (CardTypeStruct)session.getAttribute("QueryCardType");
                    	if( cardTypeStruct == null )
                    		throw new MonitorException(ErrorDefine.NULLPOINTEX,ErrorDefine.NULLPOINTEXDESC);
                    }

                    //查询
                    List queryList = null;
                    if( isFirstTime != null && isFirstTime.trim().equalsIgnoreCase("0")&&!reQueryFlag.trim().equalsIgnoreCase("1")){
                         Debug.println("**********debug: handquery FirstTime = false **********");
                         queryList = (List)session.getAttribute("HandQueryRstList");
                    } else {
                    	Debug.println("**********debug: handquery FirstTime = true **********");
                    	queryList=parameterSetBean.QueryCardType(cardTypeStruct);
                    	session.setAttribute("HandQueryRstList",queryList );
                    }

                    int PAGESIZE=20;
                    int pageCnt = 0;
                    if (queryList != null){
                        pageCnt = (queryList.size() % PAGESIZE == 0) ? (queryList.size()/PAGESIZE):(queryList.size()/PAGESIZE+1);
                    }
                    request.setAttribute("PageCount",String.valueOf(pageCnt));
                    String page=request.getParameter("page");

                    if(page==null||page.trim().equals("")) page="1";
                    request.setAttribute("CurrPageNum",page);
                    int page_num=new Integer(page).intValue();
                    List CurrPageCardList=new ArrayList();
                    for(int i=0; i<PAGESIZE; i++){
                        if((page_num-1)*PAGESIZE+i>=queryList.size()) break;
                        CurrPageCardList.add(queryList.get((page_num-1)*PAGESIZE+i));
                    }
                    request.setAttribute("CurrPageCardList",CurrPageCardList);
                    //查询银行代码，卡种类，卡类型数据
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/BankFutures/HandCardQueryRst.jsp";
                } else if(reqCode.equals(PAGEPROCESSOR)) {
                    List queryList=(List)session.getAttribute("QueryCardRstList");
                    String strCurrPageNum=request.getParameter("CurrPageNum");
                    request.setAttribute("CurrPageNum",strCurrPageNum);
                    Integer intCurrPageNum=new Integer(strCurrPageNum);
                    paginate(queryList,intCurrPageNum.intValue(),request);
                    lTarget="/ToucsMonitor/BankFutures/CardQueryRst.jsp";
                }else if(reqCode.equals(MG7550)){
                    if(loginfo!=null){
                        String Message=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                        //写入日志
                        this.writeLog(loginfo,Message);
                        PromptBean prompt=new PromptBean("ATM设备管理");
                        prompt.setPrompt(Message);
                        //如果是卡表刷新请求，请求提交成功后转到卡表刷新页面
                        //取消操作即是重新查询后回到卡表列表页面
                        String nurl="/ToucsMonitor/bfCardservlet?reqCode="+CANCEL;
                        prompt.setButtonUrl(0,"确定",nurl);
                        request.setAttribute("prompt",prompt);
                        lTarget="/ToucsMonitor/Success.jsp";
                    }else{
                        throw new MonitorException(ErrorDefine.GETUSERINFOERROR,ErrorDefine.GETUSERINFOERRORDESC);
                    }
                } else if(reqCode.equals(CANCEL)) {
                    return cancel(request);
                }
        }catch(MonitorException exp1){
            exp1.printStackTrace();
            throw exp1;
        }
        return lTarget;
    }

    /**
     * 取得新增页面得值
     * @param request
     * @return
     */
    private CardTypeStruct getCardDataA(HttpServletRequest request) throws MonitorException{
        CardTypeStruct cardTypeStruct=new CardTypeStruct();
        String cardStart;
        String cardMatch;
        String cardDesc;
        
        try{
            try{
            	cardStart=request.getParameter("cardStart").trim();
            	System.out.println("***********"+cardStart);
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }

            try{
            	cardMatch=request.getParameter("cardMatch").trim();
           
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
  
            try{
            	cardDesc=request.getParameter("cardDesc").trim();
            }catch(Exception exp1){
            	cardDesc=null;
            }
 
            cardTypeStruct.setCardStart(cardStart);
            cardTypeStruct.setCardMatch(cardMatch);
            cardTypeStruct.setCardDesc(cardDesc);
        }catch(Exception exp){
            exp.printStackTrace();
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardTypeStruct;
    }

    private CardTypeStruct getCardDataQ(HttpServletRequest request) throws MonitorException{
        CardTypeStruct cardTypeStruct=new CardTypeStruct();
        try{
             
            String cardStart = request.getParameter("cardStart");
            if(cardStart==null || cardStart.trim().length() == 0){
            	cardStart=null;
            }   
            String cardMatch = request.getParameter("cardMatch");
            if(cardMatch == null || cardMatch.trim().length() == 0){
            	cardMatch=null;
            }     
            String cardDesc = request.getParameter("cardDesc");
            if(cardDesc == null || cardDesc.trim().length() == 0){
            	cardDesc=null;
            }
            cardTypeStruct.setCardStart(cardStart);
            cardTypeStruct.setCardMatch(cardMatch);
            cardTypeStruct.setCardDesc(cardDesc);
        }catch(Exception exp){
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardTypeStruct;
    }

    /**
     * 取得时间戳
     * @return
     */
    public String getDataTime(){
        Calendar lcaSysNow = Calendar.getInstance();
        int year=lcaSysNow.get(Calendar.YEAR);
        int month=lcaSysNow.get(Calendar.MONTH);
        int day=lcaSysNow.get(Calendar.DAY_OF_MONTH);

        int hh=lcaSysNow.get(Calendar.HOUR_OF_DAY);
        int mm=lcaSysNow.get(Calendar.MINUTE);
        int ss=lcaSysNow.get(Calendar.SECOND);

        String retSD=intToStrFormat(year,4)+intToStrFormat(month,2)+intToStrFormat(day,2);
        retSD=retSD+intToStrFormat(hh,2)+intToStrFormat(mm,2)+intToStrFormat(ss,2);
        return retSD;
    }

    /**
     *
     * @param inNum  要格式化的数据
     * @param dig    格式化后的数据位数
     * @return
     */
    private String intToStrFormat(int inNum,int dig){
        String retS=String.valueOf(inNum);
        int count=dig-retS.length();
        if(inNum==8) Debug.println(count);
        if(count>0){
            String tmp="";
            for(int i=0;i<count;i++){
                tmp=tmp+"0";
            }
            retS=tmp+retS;
        }
        return retS;
    }
    //=====================================================================================//
    /**
     * 通用权限校验
     * @param request
     * @param response
     * @throws MonitorException
     */
    private LoginInfo commProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,MonitorException,MonitorException{
        //登陆校验
        LoginInfo loginfo=checkLogin(request,response);
        //取到用户名
        //取得用户权限码
        String maskCode=loginfo.getPermission();
        //取得用户资源权限
        Hashtable opvHashtable=BfCardBean.getOperRes(maskCode);
        //set用户权限，在jsp叶面取得用户权限
        request.setAttribute("OpervRes",opvHashtable);
        //权限校验
        String reqCode=request.getParameter("reqCode");
        request.setAttribute("login",loginfo);
        if(reqCode==null)reqCode="";
        reqCode=reqCode.trim();
        if(reqCode.equals(CANCEL) || reqCode.equals(PAGEPROCESSOR) || reqCode.equals(QUERYCARD)){
            reqCode=QUERYCARD;
        }
        if(reqCode.length()>5) reqCode=reqCode.substring(0,5);
        int errCode=LoginManageBean.checkPermMask(loginfo.getPermission(),reqCode);
        if(errCode!=0)
            throw new MonitorException(errCode,LoginManageBean.getErrorDesc(errCode));
        return loginfo;
    }

    private void paginate(List inList,int pageSize,HttpServletRequest request)throws MonitorException{
        if(pageProcessor==null){
            Debug.println("pageProcessor==null");
            pageProcessor=new PageProcessor();
        }
        pageProcessor.setItems(inList);
        Debug.println("inList.size()==========================="+inList.size());
        QueryCode queryCode=new QueryCode();
        queryCode.query(0);
        request.setAttribute("bankCode",queryCode.getBankCode()) ;
        request.setAttribute("cardClass",queryCode.getCardClass());
        request.setAttribute("cardType",queryCode.getCardType());
        List list=pageProcessor.getPage(pageSize);
        request.setAttribute("CurrPageCardList",list);
        request.setAttribute("PageCount",String.valueOf(pageProcessor.getPageCount()));
    }
    //用户选择取消操作
    private String cancel(HttpServletRequest request) throws MonitorException{
        //重新查询
        Object obj2=request.getSession().getAttribute("Query_Card_Condion");
        request.getSession().removeAttribute("QueryCardRstList");
        if(obj2!=null){
            CardTypeStruct qCardTypeStruct=(CardTypeStruct)obj2;
            List queryListN=parameterSetBean.QueryCardType(qCardTypeStruct);
            //重新设置查询结果集
            request.getSession().setAttribute("QueryCardRstList",queryListN);
            //查询银行代码，卡种类，卡类型数据
            QueryCode queryCode=new QueryCode();
            queryCode.query(0);
            request.setAttribute("bankCode",queryCode.getBankCode()) ;
            request.setAttribute("cardClass",queryCode.getCardClass());
            request.setAttribute("cardType",queryCode.getCardType());
            paginate(queryListN,1,request);
        }
        return "/ToucsMonitor/BankFutures/CardQueryRst.jsp";
    }

}
