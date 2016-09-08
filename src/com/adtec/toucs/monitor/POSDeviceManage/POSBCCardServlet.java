package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import com.adtec.toucs.monitor.POSDeviceManage.POSBCCardBean;
//import com.adtec.toucs.monitor.POSDeviceManage.QueryCode;
import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.LoginInfo;
import com.adtec.toucs.monitor.loginmanage.LoginManageBean;



/**
 * <p>Title: 预付卡卡卡表业务处理Servlet</p>
 * <p>Description: 用来接受客户请求，并调用相应处理方法处理</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTEC</p>
 * @author syl
 */
public class POSBCCardServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
	
	private static final String REGCARD="16801";  //新增预付卡pos卡卡表信息请求码
	private static final String MODCARD="16803";  //修改预付卡pos卡卡表信息请求码
	private static final String MODCARDREQ1="1680301";  //从查询页面发起的修改预付卡pos卡卡表信息请求码
	private static final String MODCARDREQ2="1680302";  //从管理页面发起的修改预付卡pos卡卡表信息请求码
	private static final String DELCARD="16802";  //删除预付卡pos卡卡表信息请求码
	private static final String QUERYCARD="16804";  //查询预付卡pos卡卡表信息请求码
	private static final String QUERYHANDCARD="16805";	//预付卡pos卡手输卡号查询
	private static final String CANCEL="CANCEL";  //取消请求码
	private static final String SETHANDFLAG="16806";//设置预付卡pos卡手输标志交易
	private static final String UPLOAD="16807";//理财pos卡表信息文件上传下载
	
	private static final String PAGEPROCESSOR="PAGEPROCESSOR";	
	private static PageProcessor pageProcessor=null;
	
   

	private static final String ERRORPAGEURL="";
	private static final String PARMGETPAGE = "page";
	// 预付卡信息管理根路径
	private static final String MANAGE_HOME = "/ToucsMonitor/posBCCard";
	// 预付卡信息管理页面根路径
    private static final String PAGE_HOME = "/ToucsMonitor/POSDeviceManage/";
    private POSBCCardBean posManage = new POSBCCardBean();
	
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
        if(reqCode == null){
			lTarger = "/ToucsMonitor/POSDeviceManage/POSBCUpload.jsp";			
		}
        if(reqCode!=null) reqCode=reqCode.trim();
        try{
            if(reqCode.equals(REGCARD)){
                
                POSBCCompanyId companyId=new POSBCCompanyId();
                companyId.query(0);
                request.setAttribute("company_id",companyId.getCompany_id());

                lTarger="/ToucsMonitor/POSDeviceManage/POSBCCardReg.jsp";
            } else if(reqCode.equals(MODCARD)) {
            	POSBCCompanyId companyId=new POSBCCompanyId();
                companyId.query(0);
                request.setAttribute("company_id",companyId.getCompany_id());
                request.setAttribute("reqCode",MODCARDREQ2);
                lTarger="/ToucsMonitor/POSDeviceManage/POSBCCardModify.jsp";
            } else if(reqCode.equals(QUERYCARD) || reqCode.equals(DELCARD)){
            	POSBCCompanyId companyId=new POSBCCompanyId();
                companyId.query(0);
                request.setAttribute("company_id",companyId.getCompany_id());
                request.setAttribute("reqCode",QUERYCARD);
                lTarger="/ToucsMonitor/POSDeviceManage/POSBCCardQuery.jsp";
            } else if(reqCode.equals(CANCEL)) {
                return cancel(request);
            }else if(reqCode.equals(UPLOAD)){
				lTarger = "/ToucsMonitor/POSDeviceManage/POSBCUpload.jsp";
			}else if(reqCode.equals(QUERYHANDCARD)){
            	POSBCCompanyId companyId=new POSBCCompanyId();
                companyId.query(0);
                request.setAttribute("company_id",companyId.getCompany_id());
                request.setAttribute("reqCode",QUERYHANDCARD);
                lTarger="/ToucsMonitor/POSDeviceManage/HandPOSBCCardQuery.jsp";
       } else if (reqCode.equals(SETHANDFLAG)) {
            	String hand_flag = request.getParameter("flag");
            	String Track_no = request.getParameter("track_no");
            	String card_match = request.getParameter("card_match");
            	if (hand_flag == null || Track_no == null ||
            			card_match == null) {
            		throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
            	}
            	POSBCCardInfo cardInfo = new POSBCCardInfo();
            	
            	cardInfo.setHand_flag(hand_flag);
            	cardInfo.setTrack_no(Track_no);
            	cardInfo.setCard_match(card_match);
       
            	if (posManage.setCardManuFlag(cardInfo)<= 0) {
            		throw new MonitorException(ErrorDefine.UNKNOWERROR,ErrorDefine.UNKNOWERRORDESC);
            	}
             
            	 //请lyp增加返回按钮 ----Fancy
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
            	String url="/ToucsMonitor/posBCCard?reqCode=16005&page="+showPage+"&isFirstTime=0"+"&reQueryFlag=1";
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
        	if(reqCode.equals(QUERYCARD)) {
        		
        		//取得查询条件
				POSBCCardInfo cardInfo = getCardDataQ(request);
				
				//查询
			   List queryList=posManage.QueryCardType(cardInfo);
				session.setAttribute("QueryCardRstList",queryList);
        		//添加查询条件添加到session中
        		request.getSession().setAttribute("Query_Card_Condion",cardInfo);
        		
        		//查询预付卡公司编号
        		 POSBCCompanyId companyId=new POSBCCompanyId();
                 companyId.query(0);
                 request.setAttribute("company_id",companyId.getCompany_id());
        		paginate(queryList,1,request);

        		lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
        	}else if(reqCode.equals(REGCARD)){
                //接收到添加新的卡表信息请求
        		POSBCCardInfo cardInfo=getCardDataA(request);
        		posManage.addCardType(cardInfo);
                //通知ATMP
                //设置通知ATMP返回的信息
        		String target="/ToucsMonitor/posBCCard?reqCode=16004";
        		String btnTitle="查询卡表";
                request.setAttribute("targetURL",target);
                request.setAttribute("btnTitle",btnTitle);
                String Message="";
                String retMessage="";
              
                    //设置日志信息
                    String userOperRstDesc="新增加一条卡表信息,卡表信息:";
                    userOperRstDesc=userOperRstDesc+"卡序号="+cardInfo.getCard_id()+"磁道号="+cardInfo.getTrack_no();
                    userOperRstDesc=userOperRstDesc+"适配标识="+cardInfo.getCard_match()+"卡表状态="+cardInfo.getRec_useflag();
                    userOperRstDesc=userOperRstDesc+"卡表日期时间戳="+cardInfo.getDatetime()+"公司编号="+cardInfo.getCompany_id();
                    userOperRstDesc=userOperRstDesc+"备注1="+cardInfo.getMemo1()+"备注2="+cardInfo.getMemo2();
                    userOperRstDesc=userOperRstDesc+"；返回的处理结果："+retMessage;
                    //写入日志
                    this.writeLog(loginfo,userOperRstDesc);
                
                Message="增加卡表信息成功，";
                Message=Message+retMessage;
                request.setAttribute("Message",Message);

                PromptBean prompt=new PromptBean("卡表信息管理");
                prompt.setPrompt(Message);
                prompt.setButtonUrl(0,"继续添加","/ToucsMonitor/posBCCard?reqCode="+REGCARD);
                prompt.setButtonUrl(1,"继续查询","/ToucsMonitor/posBCCard?reqCode="+QUERYCARD);
                request.setAttribute("prompt",prompt);
                lTarget="/ToucsMonitor/Success.jsp";
        	}else if(reqCode.equals(MODCARD)){
				//接收到修改卡表信息请求，取得修改的值，提交修改请求
				Debug.println("接收到修改预付卡pos卡卡表信息请求，取得修改的值，提交修改请求");
				POSBCCardInfo cardInfo=getCardDataQ(request);
				Debug.println("POSBCCardBean.updateCardType");
				
				String oldCard_id=request.getParameter("oldCard_id");
				
				String oldCard_match=request.getParameter("oldCard_match");
				System.out.println("ceshi+++++++++++"+oldCard_match);
				posManage.updateCardType(cardInfo,oldCard_id,oldCard_match);
				Debug.println("POSBCCardBean.updateCardType  end");
				
				//通知ATMP
				String retMessage="";
				String Message="";
				Util util = new Util();
				int time = Integer.parseInt(util.getCurrentTime());
				System.out.println("当前Integer的时间"+time);
				
						String userOperRstDesc="修改前预付卡pos卡卡表信息:";
						

						userOperRstDesc=userOperRstDesc+"卡序号="+cardInfo.getCard_id()+"磁道号="+cardInfo.getTrack_no();
	                    userOperRstDesc=userOperRstDesc+"适配标识="+cardInfo.getCard_match()+"卡表状态="+cardInfo.getRec_useflag();
	                    userOperRstDesc=userOperRstDesc+"卡表日期时间戳="+cardInfo.getDatetime()+"公司编号="+cardInfo.getCompany_id();
	                    userOperRstDesc=userOperRstDesc+"；已经通知ATMP,返回的处理结果："+retMessage;
						
					
						//写入日志
						this.writeLog(loginfo,userOperRstDesc);
					
				//设置通知ATMP返回的信息

				PromptBean prompt=new PromptBean("预付卡pos卡卡表信息管理");
				prompt.setPrompt(Message);
				prompt.setPrompt("预付卡卡表修改成功！");
				prompt.setButtonUrl(0,"添加","/ToucsMonitor/posBCCard?reqCode="+REGCARD);
				prompt.setButtonUrl(1,"查询","/ToucsMonitor/posBCCard?reqCode="+QUERYCARD);
				request.setAttribute("prompt",prompt);
				lTarget="/ToucsMonitor/Success.jsp";
        	}else if(reqCode.equals(MODCARDREQ1)){
				//接收到要修改卡表信息请求（用户输入了要修改的卡表索引），用户输入的索引查询，如果查询结果为1，则转到修改叶面，
				//如果查询的结果不为1，则转到查询结果叶面。如果查询结果为0，则返回到修改请求叶面
				/*查询********************************************************/
				POSBCCardInfo cardInfo = getCardDataQ(request);
				List queryList=posManage.QueryCardType(cardInfo);
				//把查询条件添加到session中。
				request.getSession().setAttribute("Query_Card_Condion",cardInfo);
				/*查询  End******************************************************/
				if(queryList==null || queryList.size()==0){
				    POSBCCompanyId companyId=new POSBCCompanyId();
		            companyId.query(0);
		            request.setAttribute("company_id",companyId.getCompany_id());
					request.setAttribute("Message","没有满足你输入条件的记录，请重新输入！");
					request.setAttribute("reqCode",MODCARD);
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQuery.jsp";
				}else if(queryList.size()==1){  //查询到的数据为1，则转到修改叶面
					POSBCCardInfo cardTypeStruct=(POSBCCardInfo)queryList.get(0);
					request.setAttribute("CardTypeStruct",cardTypeStruct);
					 POSBCCompanyId companyId=new POSBCCompanyId();
		             companyId.query(0);
		             request.setAttribute("company_id",companyId.getCompany_id());
	
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardModify.jsp";
				}else if(queryList.size()>1){//查询到的数据〉1，则转到查询结果叶面
					session.setAttribute("QueryCardRstList",queryList);
					paginate(queryList,1,request);
					//查询预付卡公司编号
					 POSBCCompanyId companyId=new POSBCCompanyId();
		             companyId.query(0);
		             request.setAttribute("company_id",companyId.getCompany_id());
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
				}
			}else if(reqCode.equals(MODCARDREQ2)){
				/**
				 *用户操作，选择某一要修改得卡表信息。则先查询用户选择的卡表信息，如果该卡表信息
				 *已经被其他用户删除，则提示该卡表信息已经被删除。否则导向到修改页面
				 **/
				//取得用户选择的卡表信息
				Debug.println("deal MODIFYCARDTYPEREQUEST2  reuest: begin");
				String strIndex="";
				int index=0;
				List queryList=(List)session.getAttribute("QueryCardRstList");
				
				//如果session过期，抛出异常，提示用户离开用户离开系统太久
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
			
				POSBCCardInfo cardInfo = (POSBCCardInfo)queryList.get(index);
				//根据用户输入的卡表信息查询
				List queryNewList=posManage.QueryCardType(cardInfo);
				/*查询  End******************************************************/
				
				//如果查询结果为0，则导向到错误页面
				if(queryNewList==null || queryNewList.size()==0){
					throw new MonitorException(ErrorDefine.OTHERDELETETHISCARDID,ErrorDefine.OTHERDELETETHISCARDIDDESC);
				}else{
					//如果查询结果不为0，则导向到修改页面.如果根据用户输入的查询，		
					//查询到多条结果呢？暂时不处理这样的情况（考虑到这样的情况在实际上不存在）
					POSBCCardInfo cardTypeStruct1=(POSBCCardInfo)queryNewList.get(0);
					request.setAttribute("POSBCCardInfo",cardTypeStruct1);
					 POSBCCompanyId companyId=new POSBCCompanyId();
		             companyId.query(0);
		             request.setAttribute("company_id",companyId.getCompany_id());
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardModify.jsp";
				}
				Debug.println("deal MODIFYCARDTYPEREQUEST2  reuest: End");
			}else if(reqCode.equals(DELCARD)){													
			    String[] strArray = request.getParameterValues("box1");
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
				if(strArray != null){
					//页面传递复选框中的值不为空则进行批量删除
					posManage.deleteBCCards(strArray);
				//	String retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					//jsp跳转
					PromptBean prompt = new PromptBean("预付卡POS卡表信息管理");
					prompt.setPrompt("预付卡POS卡表信息删除成功！");
					prompt.setButtonUrl(0, "结束配置", MANAGE_HOME);
					request.setAttribute("prompt", prompt);
			  	    lTarget="/ToucsMonitor/Success.jsp";
				}else{
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
					
					POSBCCardInfo cardInfo = (POSBCCardInfo)queryList.get(index);
					//删除
					posManage.deleteCardType(cardInfo);
					String Message="";
	                String retMessage="";
					//通知ATMP

						
							//设置日志信息
							String userOperRstDesc="删除预付卡pos卡卡表信息,卡表信息:";
							userOperRstDesc=userOperRstDesc+"卡序号="+cardInfo.getCard_id()+"磁道号="+cardInfo.getTrack_no();
		                    userOperRstDesc=userOperRstDesc+"适配标识="+cardInfo.getCard_match()+"卡表状态="+cardInfo.getRec_useflag();
		                    userOperRstDesc=userOperRstDesc+"卡表日期时间戳="+cardInfo.getDatetime()+"公司编号="+cardInfo.getCompany_id();
		                    userOperRstDesc=userOperRstDesc+"；已经通知ATMP,返回的处理结果："+retMessage;
							//写入日志
							this.writeLog(loginfo,userOperRstDesc);
					
						//retMessage="删除预付卡pos卡卡表信息成功.";
						  
		                Message="删除预付卡pos卡卡表信息成功!";
		                Message=Message+retMessage;
		                request.setAttribute("Message",Message);
                        
		                PromptBean prompt=new PromptBean("卡表信息管理");
		                prompt.setPrompt(Message);
		              
		                request.setAttribute("prompt",prompt);
		                lTarget="/ToucsMonitor/Success.jsp";
					}	
					
					
					//重新查询
					Object obj2=request.getSession().getAttribute("Query_Card_Condion");
					
					if(obj2==null){
						throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
					}else{
						POSBCCardInfo cardInfo2 = (POSBCCardInfo)obj2;
						Debug.println("begin QueryCardType(qCardTypeStruct)");
						List queryListN=posManage.QueryCardType(cardInfo2);
						//Debug.println("intCurrPageNum.intValue()==========================="+intCurrPageNum.intValue());
						Debug.println("End QueryCardType(qCardTypeStruct)");
						//重新设置查询结果集
						session.setAttribute("QueryCardRstList",queryListN);
						//paginate(queryListN,intCurrPageNum.intValue(),request);
					}
					
					//查询预付卡公司
					POSBCCompanyId companyId=new POSBCCompanyId();
		            companyId.query(0);
		            request.setAttribute("company_id",companyId.getCompany_id());
	
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
					
				}else if(reqCode.equals(PAGEPROCESSOR)){
					List queryList=(List)session.getAttribute("QueryCardRstList");
					String strCurrPageNum=request.getParameter("CurrPageNum");
					request.setAttribute("CurrPageNum",strCurrPageNum);
					Integer intCurrPageNum=new Integer(strCurrPageNum);
					paginate(queryList,intCurrPageNum.intValue(),request);
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
				}else if(reqCode.equals(UPLOAD)){

				}
			
        	}catch(MonitorException e){
    			e.printStackTrace();
    			throw e;
    		}
			return lTarget;
        
            
        
    }

  /**
   * 取得新增页面得值
   * @param request
   * @return
   */
    private POSBCCardInfo getCardDataA(HttpServletRequest request) throws MonitorException{
    	POSBCCardInfo cardInfo=new POSBCCardInfo();
        String card_id="";
        String track_no="";
		String card_match="";
		String cardmatch_len="";
		String card_len="";
		String company_id="";
		String datetime="";
		String rec_useflag="";
		String memo1="";
		String memo2="";
        try{
        /*	try{
        		card_id=request.getParameter("card_id").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }*/
            try{
                track_no=request.getParameter("track_no").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            try{
            	card_match=request.getParameter("card_match").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            card_match=request.getParameter("card_match");
            try{
            	cardmatch_len=request.getParameter("cardmatch_len");
            	cardmatch_len=cardmatch_len.trim();
            }catch(Exception exp1){
            	cardmatch_len=null;
            }
            try{
            	card_len=request.getParameter("card_len");
            	card_len=card_len.trim();
            }catch(Exception exp1){
            	card_len=null;
            }
            try{
            	company_id=request.getParameter("company_id").trim();
            }catch(Exception exp1){
                throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
            }
            datetime=getDataTime();
            rec_useflag=request.getParameter("rec_useflag");
            memo1=request.getParameter("memo1");
            memo2=request.getParameter("memo2");
            
            cardInfo.setCard_id(card_id);
            cardInfo.setTrack_no(track_no);
            cardInfo.setCard_match(card_match);
            cardInfo.setCardmatch_len(cardmatch_len);
            cardInfo.setCard_len(card_len);
            cardInfo.setCompany_id(company_id);
            cardInfo.setDatetime(datetime);
            cardInfo.setRec_useflag(rec_useflag);
            cardInfo.setMemo1(memo1);
            cardInfo.setMemo2(memo2);
        }catch(Exception exp){
            exp.printStackTrace();
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardInfo;
    }

    private POSBCCardInfo getCardDataQ(HttpServletRequest request) throws MonitorException{
    	POSBCCardInfo cardInfo=new POSBCCardInfo();
        try{
        	String card_id;
        	card_id=request.getParameter("card_id");
            if(card_id==null || card_id.trim().length()==0 ){
            	card_id=null;
            }
            String track_no;
            track_no=request.getParameter("track_no");
            if(track_no==null || track_no.trim().length()==0 ){
                track_no=null;
            }
            String company_id=request.getParameter("company_id");
            if(company_id==null || company_id.trim().length()==0 ){
            	company_id=null;
            }
            String card_match=request.getParameter("card_match");
            String cardmatch_len=request.getParameter("cardmatch_len");
            String card_len=request.getParameter("card_len");
            String datetime=request.getParameter("datetime");
            String rec_useflag=request.getParameter("rec_useflag");
            String memo1=request.getParameter("memo1");
            String memo2=request.getParameter("memo2");

            //如果选择查询所有
            if(rec_useflag == null || rec_useflag.equals("3"))
                rec_useflag=null;
            
            cardInfo.setCard_id(card_id);
            cardInfo.setTrack_no(track_no);
            cardInfo.setCard_match(card_match);
            cardInfo.setCardmatch_len(cardmatch_len);
            cardInfo.setCard_len(card_len);
            cardInfo.setCompany_id(company_id);
            cardInfo.setDatetime(datetime);
            cardInfo.setRec_useflag(rec_useflag);
            cardInfo.setMemo1(memo1);
            cardInfo.setMemo2(memo2);
        }catch(Exception exp){
            throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
        }
        return cardInfo;
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
		Hashtable opvHashtable=POSBCCardBean.getOperRes(maskCode);
		//set用户权限，在jsp叶面取得用户权限
		request.setAttribute("OpervRes",opvHashtable);
		//权限校验
		String reqCode=request.getParameter("reqCode");
		request.setAttribute("login",loginfo);
		if(reqCode==null){
			reqCode="";
		}
		reqCode=reqCode.trim();
		if(reqCode.equals(CANCEL) || reqCode.equals(PAGEPROCESSOR)){
			reqCode=QUERYCARD;
		}
		if(reqCode.length()>5) reqCode=reqCode.substring(0,5);
		int errCode=LoginManageBean.checkPermMask(loginfo.getPermission(),reqCode);
		if(errCode!=0){
			throw new MonitorException(errCode,LoginManageBean.getErrorDesc(errCode));
		}
		return loginfo;
	}
    private void paginate(List inList,int pageSize,HttpServletRequest request)throws MonitorException{
        if(pageProcessor==null){
            Debug.println("pageProcessor==null");
            pageProcessor=new PageProcessor();
        }
        pageProcessor.setItems(inList);
        Debug.println("inList.size()==========================="+inList.size());
        POSBCCompanyId companyId=new POSBCCompanyId();
        companyId.query(0);
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
        	POSBCCardInfo qcardInfo=(POSBCCardInfo)obj2;
            List queryListN=posManage.QueryCardType(qcardInfo);
            //重新设置查询结果集
            request.getSession().setAttribute("QueryCardRstList",queryListN);
            //查询预付卡公司
            POSBCCompanyId companyId=new POSBCCompanyId();
            companyId.query(0);
            request.setAttribute("company_id",companyId.getCompany_id());
            paginate(queryListN,1,request);
        }
        return "/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
    }

}
