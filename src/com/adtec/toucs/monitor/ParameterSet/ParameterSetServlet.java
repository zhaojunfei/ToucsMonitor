package com.adtec.toucs.monitor.ParameterSet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;

import com.adtec.toucs.monitor.loginmanage.*;


/**
 * 卡表业务处理Servlet，。用来接受客户请求，并调用相应处理方法处理之
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lichj
 * @version 1.0
 */
public class ParameterSetServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final String REGCARD="16001";  //新增卡表信息请求码
    private static final String MODCARD="16002";  //修改卡表信息请求码
    private static final String MODCARDREQ1="1600201";  //从查询页面发起的修改卡表信息请求码
    private static final String MODCARDREQ2="1600202";  //从管理页面发起的修改卡表信息请求码
    private static final String DELCARD="16003";  //删除卡表信息请求码
    private static final String QUERYCARD="16004";  //查询卡表信息请求码
    private static final String QUERYHANDCARD="16005";	//手输卡号查询
    private static final String CANCEL="CANCEL";  //取消请求码
    private static final String SETHANDFLAG="16006";//设置手输标志交易

    private static final String PAGEPROCESSOR="PAGEPROCESSOR";
    private static final String ERRORPAGEURL="";
    private static final String MG7550="12005";

    private static PageProcessor pageProcessor=null;

    //业务Bean的实例
    private static ParameterSetBean parameterSetBean=new ParameterSetBean();

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Get request
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
        //权限校验，用户登陆校验
        LoginInfo logInfo=commProcess(request,response);
        String lTarger="";
        String reqCode=request.getParameter("reqCode");
        if(reqCode!=null) reqCode=reqCode.trim();
        try{
            if(reqCode.equals(REGCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(0);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("cardClass",queryCode.getCardClass());
                request.setAttribute("cardType",queryCode.getCardType());
                lTarger="/ToucsMonitor/ParameterSet/CardReg.jsp";
            } else if(reqCode.equals(MODCARD)) {
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",MODCARDREQ1);
                lTarger="/ToucsMonitor/ParameterSet/CardQuery.jsp";
            } else if(reqCode.equals(QUERYCARD) || reqCode.equals(DELCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",QUERYCARD);
                lTarger="/ToucsMonitor/ParameterSet/CardQuery.jsp";
            } else if(reqCode.equals(CANCEL)) {
                return cancel(request);
            } else if(reqCode.equals(QUERYHANDCARD)){
                QueryCode queryCode=new QueryCode();
                queryCode.query(1);
                request.setAttribute("bankCode",queryCode.getBankCode()) ;
                request.setAttribute("reqCode",QUERYHANDCARD);
                lTarger="/ToucsMonitor/ParameterSet/HandCardQuery.jsp";
            } else if (reqCode.equals(SETHANDFLAG)) {
            	String hand_flag = request.getParameter("flag");
            	String Track_no = request.getParameter("track_no");
            	String cardMatch = request.getParameter("cardMatch");
            	String MatchStart = request.getParameter("matchStart");
            	if (hand_flag == null || Track_no == null ||
            			cardMatch == null || MatchStart == null) {
            		throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
            	}
            	CardTypeStruct cardTypeStrue = new CardTypeStruct();
            	cardTypeStrue.set_hand_flag(hand_flag);
            	cardTypeStrue.setTrack_no(Track_no);
            	cardTypeStrue.setcardMatch(cardMatch);
            	cardTypeStrue.setMatchStart(MatchStart);
            	if (parameterSetBean.setCardManuFlag(cardTypeStrue) <= 0) {
            		throw new MonitorException(ErrorDefine.UNKNOWERROR,ErrorDefine.UNKNOWERRORDESC);
            	}
              /***
               	请lyp增加返回按钮 ----Fancy***/
            	PromptBean prompt = new PromptBean("手输卡管理");

            	//转成功提示页面
            	if (hand_flag.trim().equalsIgnoreCase("1") ) {
            		writeLog(logInfo, "已将此种卡设为[ 可进行手输 ]状态！");
            		prompt.setPrompt("已将此种卡设为[ 可进行手输 ]状态！");
            	} else if (hand_flag.trim().equalsIgnoreCase("0") ) {
            		writeLog(logInfo, "已将此种卡设为[ 不可进行手输 ]状态！");
            		prompt.setPrompt("已将此种卡设为[ 不可进行手输 ]状态！");
            	}

            	//修改时所在的页数
            	String showPage = request.getParameter("curPage");
            	String url="/ToucsMonitor/parametersetservlet?reqCode=16005&page="+showPage+"&isFirstTime=0"+"&reQueryFlag=1";
            	Debug.print("*************Debug:promp URL = ");
            	Debug.println( url );
            	prompt.setButtonUrl(0, "返回", url);
            	prompt.setMethod("POST");
            	request.setAttribute("prompt", prompt);
            	lTarger = "/ToucsMonitor/Success.jsp";
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
        request.removeAttribute("Message");
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
        //权限校验，用户登陆校验
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
                //接收到添加新的卡表信息请求
                CardTypeStruct cardTypeStruct=getCardDataA(request);
                parameterSetBean.addCardType(cardTypeStruct);
                //通知ATMP
                //设置通知ATMP返回的信息
                String Message="";
                String retMessage="";
                try{
                    retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                }catch(MonitorException mexp){
                    String target="/ToucsMonitor/parametersetservlet?reqCode=16004";
                    String btnTitle="查询卡表";
                    request.setAttribute("targetURL",target);
                    request.setAttribute("btnTitle",btnTitle);
                    Message="成功增加卡表信息到数据中，但";
                    throw new MonitorException(mexp.getErrCode(),Message+mexp.getMessage());
                }finally{
                    //设置日志信息
                    String userOperRstDesc="新增加一条卡表信息,卡表信息:";
                    userOperRstDesc=userOperRstDesc+"磁道号="+cardTypeStruct.getTrack_no()+"适配偏移="+cardTypeStruct.getMatchStart();
                    userOperRstDesc=userOperRstDesc+"适配标识="+cardTypeStruct.getcardMatch()+"卡表状态="+cardTypeStruct.getrec_useflag();
                    userOperRstDesc=userOperRstDesc+"卡表日期时间戳="+cardTypeStruct.getrec_datetime()+"银行名称="+cardTypeStruct.getbankCode();
                    userOperRstDesc=userOperRstDesc+"；已经通知ATMP,返回的处理结果："+retMessage;
                    //写入日志
                    this.writeLog(loginfo,userOperRstDesc);
                }
                Message="增加卡表信息成功，";
                Message=Message+retMessage;
                request.setAttribute("Message",Message);

                PromptBean prompt=new PromptBean("卡表信息管理");
                prompt.setPrompt(Message);
                prompt.setButtonUrl(0,"添加","/ToucsMonitor/parametersetservlet?reqCode="+REGCARD);
                prompt.setButtonUrl(1,"查询","/ToucsMonitor/parametersetservlet?reqCode="+QUERYCARD);
                request.setAttribute("prompt",prompt);
                lTarget="/ToucsMonitor/Success.jsp";
            } else if(reqCode.equals(MODCARD)) {
                //接收到修改卡表信息请求，取得修改的值，提交修改请求
                Debug.println("接收到修改卡表信息请求，取得修改的值，提交修改请求");
                CardTypeStruct cardTypeStruct=getCardDataA(request);
                Debug.println("parameterSetBean.updateCardType");

                Integer tmpold=null;
                tmpold=new Integer(request.getParameter("oldTrack_no"));
                int oldTrack_no=tmpold.intValue();

                tmpold=new Integer(request.getParameter("oldMatchStart"));
                int oldMatchStart=tmpold.intValue();

                String oldCardMatch=request.getParameter("oldCardMatch");
                String oldRec_datetime=request.getParameter("oldRec_datetime");
                String oldRec_useflag=request.getParameter("oldRec_useflag");

                parameterSetBean.updateCardType(cardTypeStruct,oldTrack_no,oldMatchStart,oldCardMatch,oldRec_datetime,oldRec_useflag);
                Debug.println("parameterSetBean.updateCardType  end");

                //通知ATMP
                String retMessage="";
                String Message="";
                try{
                    retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                }catch(MonitorException mexp){
                    String target="/ToucsMonitor/parametersetservlet?reqCode=16004";
                    String btnTitle="查询卡表";
                    request.setAttribute("targetURL",target);
                    request.setAttribute("btnTitle",btnTitle);
                    Message="成功修改卡表信息，但";
                    throw new MonitorException(mexp.getErrCode(),Message+mexp.getMessage());
                }finally{
                    //设置日志信息
                    String userOperRstDesc="修改前卡表信息:";
                    userOperRstDesc=userOperRstDesc+"磁道号="+String.valueOf(oldTrack_no) +"适配偏移="+String.valueOf(oldMatchStart);
                    userOperRstDesc=userOperRstDesc+"适配标识="+oldCardMatch+"卡表状态="+oldRec_useflag;
                    userOperRstDesc=userOperRstDesc+"时间戳="+oldRec_datetime;
                    userOperRstDesc=userOperRstDesc+"修改后:";
                    userOperRstDesc=userOperRstDesc+"磁道号="+cardTypeStruct.getTrack_no()+"适配偏移="+cardTypeStruct.getMatchStart();
                    userOperRstDesc=userOperRstDesc+"适配标识="+cardTypeStruct.getcardMatch()+"卡表状态="+cardTypeStruct.getrec_useflag();
                    userOperRstDesc=userOperRstDesc+"时间戳="+cardTypeStruct.getrec_datetime()+"银行代码="+cardTypeStruct.getbankCode();
                    userOperRstDesc=userOperRstDesc+";"+retMessage;
                    //写入日志
                    this.writeLog(loginfo,userOperRstDesc);
                }
                //设置通知ATMP返回的信息
                Message="修改卡表信息成功，";
                Message=Message+retMessage;
                PromptBean prompt=new PromptBean("卡表信息管理");
                prompt.setPrompt(Message);
                prompt.setButtonUrl(0,"添加","/ToucsMonitor/parametersetservlet?reqCode="+REGCARD);
                prompt.setButtonUrl(1,"查询","/ToucsMonitor/parametersetservlet?reqCode="+QUERYCARD);
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
                    lTarget="/ToucsMonitor/ParameterSet/CardQuery.jsp";
                }else if(queryList.size()==1){  //查询到的数据为1，则转到修改叶面
                    CardTypeStruct cardTypeStruct=(CardTypeStruct)queryList.get(0);
                    request.setAttribute("CardTypeStruct",cardTypeStruct);

                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/ParameterSet/CardModify.jsp";
                }else if(queryList.size()>1){//查询到的数据〉1，则转到查询结果叶面
                    session.setAttribute("QueryCardRstList",queryList);
                    paginate(queryList,1,request);
                    //查询银行代码，卡种类，卡类型数据
                    QueryCode queryCode=new QueryCode();
                    queryCode.query(0);
                    request.setAttribute("bankCode",queryCode.getBankCode()) ;
                    request.setAttribute("cardClass",queryCode.getCardClass());
                    request.setAttribute("cardType",queryCode.getCardType());
                    lTarget="/ToucsMonitor/ParameterSet/CardQueryRst.jsp";
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

                	//如果session过器，抛出异常，提示用户离开用户离开系统太久
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
                		lTarget="/ToucsMonitor/ParameterSet/CardModify.jsp";
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
            			String target="/ToucsMonitor/parametersetservlet?reqCode=16004";
            			String btnTitle="查询卡表";
            			request.setAttribute("targetURL",target);
            			request.setAttribute("btnTitle",btnTitle);
            			String Message="成功从数据库中删除卡表信息，但";
            			retMessage=Message+mexp.getMessage();
            		}finally{
            			//设置日志信息
            			String userOperRstDesc="删除一条卡表信息,卡表信息:";
            			userOperRstDesc=userOperRstDesc+"磁道号="+cardTypeStruct.getTrack_no()+"适配偏移="+cardTypeStruct.getMatchStart();
            			userOperRstDesc=userOperRstDesc+"适配标识="+cardTypeStruct.getcardMatch()+"卡表状态="+cardTypeStruct.getrec_useflag();
            			userOperRstDesc=userOperRstDesc+"卡表日期时间戳="+cardTypeStruct.getrec_datetime()+"银行名称="+cardTypeStruct.getbankCode();
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

            		lTarget="/ToucsMonitor/ParameterSet/CardQueryRst.jsp";
                	}else if(reqCode.equals(QUERYCARD)) {
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

                		lTarget="/ToucsMonitor/ParameterSet/CardQueryRst.jsp";
                	} else if(reqCode.equals(QUERYHANDCARD)) {
                		//查询手输卡表信息
                		CardTypeStruct cardTypeStruct;
                		String isFirstTime = request.getParameter("isFirstTime");
                		String reQueryFlag = request.getParameter("reQueryFlag");
                		if( reQueryFlag == null )  reQueryFlag ="";
                		if( isFirstTime.trim().equalsIgnoreCase("1") ){ 
                			//取得查询条件
                			cardTypeStruct=getCardDataQ(request);
                			session.setAttribute("QueryCardType", cardTypeStruct );
                		} else {
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
                		lTarget="/ToucsMonitor/ParameterSet/HandCardQueryRst.jsp";
                	} else if(reqCode.equals(PAGEPROCESSOR)) {
                		List queryList=(List)session.getAttribute("QueryCardRstList");
                		String strCurrPageNum=request.getParameter("CurrPageNum");
                		request.setAttribute("CurrPageNum",strCurrPageNum);
                		Integer intCurrPageNum=new Integer(strCurrPageNum);
                		paginate(queryList,intCurrPageNum.intValue(),request);
                		lTarget="/ToucsMonitor/ParameterSet/CardQueryRst.jsp";
                	}else if(reqCode.equals(MG7550)){
                		if(loginfo!=null){
                        String Message=notifyATMP("MG7550","000000000",loginfo.getOrgID());
                        //写入日志
                        this.writeLog(loginfo,Message);
                        PromptBean prompt=new PromptBean("ATM设备管理");
                        prompt.setPrompt(Message);
                        //如果是卡表刷新请求，请求提交成功后转到卡表刷新页面
                        //取消操作即是重新查询后回到卡表列表页面
                        String nurl="/ToucsMonitor/parametersetservlet?reqCode="+CANCEL;
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
        String track_no;
        String matchStart;
        String cardMatch="";
        String cardStart;
        String cardLen;
        String bankStart;
        String bankLen;
        String bankMatch="";
        String bankCode="";
        String cardClass="";
        String cardType="";
        String pinoffset;
        String cardArea="";
        String rec_datetime="";
        String rec_useflag="";
        try{
            try{
                track_no=request.getParameter("track_no").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            try{
                matchStart=request.getParameter("matchStart").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            cardMatch=request.getParameter("cardMatch");
            try{
                cardStart=request.getParameter("cardStart").trim();
            }catch(Exception exp1){
                cardStart=null;
            }
            try{
                cardLen=request.getParameter("cardLen");
                cardLen=cardLen.trim();
            }catch(Exception exp1){
                cardLen=null;
            }
            try{
                bankStart=request.getParameter("bankStart");
                cardLen=cardLen.trim();
            }catch(Exception exp1){
                bankStart="-1";
            }
            try{
                bankLen=request.getParameter("bankLen");
                bankLen=bankLen.trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            Debug.println("==================================befor decode");
            Debug.println(request.getParameter("bankMatch"));
            bankMatch=request.getParameter("bankMatch");
            Debug.println("==================================after decode");
            Debug.println(bankMatch);

            bankCode=request.getParameter("bankCode");
            cardClass=request.getParameter("cardClass");
            cardType=request.getParameter("cardType");

            try{
                pinoffset=request.getParameter("pinoffset");
                pinoffset=pinoffset.trim();
            }catch(Exception exp1){
                pinoffset=null;
            }

            cardArea=request.getParameter("cardArea");
            rec_datetime=getDataTime();
            rec_useflag=request.getParameter("rec_useflag");

            cardTypeStruct.setTrack_no(track_no);
            cardTypeStruct.setMatchStart(matchStart);
            cardTypeStruct.setcardMatch(cardMatch);
            cardTypeStruct.setcardStart(cardStart) ;
            cardTypeStruct.setcardLen(cardLen);
            cardTypeStruct.setbankStart(bankStart) ;
            cardTypeStruct.setbankLen(bankLen) ;
            cardTypeStruct.setbankMatch(bankMatch) ;
            cardTypeStruct.setbankCode(bankCode);
            cardTypeStruct.setcardClass(cardClass) ;
            cardTypeStruct.setcardType(cardType);
            cardTypeStruct.setpinoffset(pinoffset) ;
            cardTypeStruct.setcardArea(cardArea);
            cardTypeStruct.setrec_datetime(rec_datetime) ;
            cardTypeStruct.setrec_useflag(rec_useflag) ;
        }catch(Exception exp){
            exp.printStackTrace();
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardTypeStruct;
    }

    private CardTypeStruct getCardDataQ(HttpServletRequest request) throws MonitorException{
        CardTypeStruct cardTypeStruct=new CardTypeStruct();
        try{
            String track_no;
            track_no=request.getParameter("track_no");
            if(track_no==null || track_no.trim().length()==0 ){
                track_no=null;
            }
            String matchStart=request.getParameter("matchStart");
            if(matchStart==null || matchStart.trim().length()==0 ){
                matchStart=null;
            }
            String cardMatch=request.getParameter("cardMatch");
            String bankCode=request.getParameter("bankCode");
            String rec_datetime=request.getParameter("rec_datetime");
            String rec_useflag=request.getParameter("rec_useflag");

            //如果选择查询所有
            if(rec_useflag == null || rec_useflag.equals("3"))
                rec_useflag=null;

            cardTypeStruct.setTrack_no(track_no);
            cardTypeStruct.setMatchStart(matchStart);
            cardTypeStruct.setcardMatch(cardMatch);
            cardTypeStruct.setbankCode(bankCode);
            cardTypeStruct.setrec_datetime(rec_datetime) ;
            cardTypeStruct.setrec_useflag(rec_useflag) ;
        }catch(Exception exp){
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardTypeStruct;
    }

  /**
   * 取得时间戳
   * @return retSD
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
   * @param inNum  要格式化的数据
   * @param dig    格式化后的数据位数
   * @return retS
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

  /**
   * 通用权限校验
   * @param request
   * @param response
   * @throws MonitorException
   */
    private LoginInfo commProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,MonitorException,MonitorException{
        //登陆校验
        LoginInfo loginfo=checkLogin(request,response);
        //取得用户权限码
        String maskCode=loginfo.getPermission();
        //取得用户资源权限
        Hashtable opvHashtable=ParameterSetBean.getOperRes(maskCode);
        //set用户权限，在jsp叶面取得用户权限
        request.setAttribute("OpervRes",opvHashtable);
        //权限校验
        String reqCode=request.getParameter("reqCode");
        request.setAttribute("login",loginfo);
        if(reqCode==null)reqCode="";
        reqCode=reqCode.trim();
        if(reqCode.equals(CANCEL) || reqCode.equals(PAGEPROCESSOR)){
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
        return "/ToucsMonitor/ParameterSet/CardQueryRst.jsp";
    }

}
