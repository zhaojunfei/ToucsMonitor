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
 * <p>Title: Ԥ����������ҵ����Servlet</p>
 * <p>Description: �������ܿͻ����󣬲�������Ӧ����������</p>
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
	
	private static final String REGCARD="16801";  //����Ԥ����pos��������Ϣ������
	private static final String MODCARD="16803";  //�޸�Ԥ����pos��������Ϣ������
	private static final String MODCARDREQ1="1680301";  //�Ӳ�ѯҳ�淢����޸�Ԥ����pos��������Ϣ������
	private static final String MODCARDREQ2="1680302";  //�ӹ���ҳ�淢����޸�Ԥ����pos��������Ϣ������
	private static final String DELCARD="16802";  //ɾ��Ԥ����pos��������Ϣ������
	private static final String QUERYCARD="16804";  //��ѯԤ����pos��������Ϣ������
	private static final String QUERYHANDCARD="16805";	//Ԥ����pos�����俨�Ų�ѯ
	private static final String CANCEL="CANCEL";  //ȡ��������
	private static final String SETHANDFLAG="16806";//����Ԥ����pos�������־����
	private static final String UPLOAD="16807";//���pos������Ϣ�ļ��ϴ�����
	
	private static final String PAGEPROCESSOR="PAGEPROCESSOR";	
	private static PageProcessor pageProcessor=null;
	
   

	private static final String ERRORPAGEURL="";
	private static final String PARMGETPAGE = "page";
	// Ԥ������Ϣ�����·��
	private static final String MANAGE_HOME = "/ToucsMonitor/posBCCard";
	// Ԥ������Ϣ����ҳ���·��
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
        //Ȩ��У�飬�û���½У��
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
             
            	 //��lyp���ӷ��ذ�ť ----Fancy
               PromptBean prompt = new PromptBean("���俨����");

            	//ת�ɹ���ʾҳ��
            	if (hand_flag.trim().equalsIgnoreCase("1") ) {
            		writeLog(logInfo, "�ѽ����ֿ���Ϊ[ �ɽ������� ]״̬��");
            		prompt.setPrompt("�ѽ����ֿ���Ϊ[ �ɽ������� ]״̬��");
            	} else if (hand_flag.trim().equalsIgnoreCase("0") ) {
            		writeLog(logInfo, "�ѽ����ֿ���Ϊ[ ���ɽ������� ]״̬��");
            		prompt.setPrompt("�ѽ����ֿ���Ϊ[ ���ɽ������� ]״̬��");
            	}

            	//�޸�ʱ���ڵ�ҳ��
            	String showPage = request.getParameter("curPage");
            	String url="/ToucsMonitor/posBCCard?reqCode=16005&page="+showPage+"&isFirstTime=0"+"&reQueryFlag=1";
            	Debug.print("*************Debug:promp URL = ");
            	Debug.println( url );
            	prompt.setButtonUrl(0, "����", url);
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
        //Ȩ��У�飬�û���½У��
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
        		
        		//ȡ�ò�ѯ����
				POSBCCardInfo cardInfo = getCardDataQ(request);
				
				//��ѯ
			   List queryList=posManage.QueryCardType(cardInfo);
				session.setAttribute("QueryCardRstList",queryList);
        		//��Ӳ�ѯ������ӵ�session��
        		request.getSession().setAttribute("Query_Card_Condion",cardInfo);
        		
        		//��ѯԤ������˾���
        		 POSBCCompanyId companyId=new POSBCCompanyId();
                 companyId.query(0);
                 request.setAttribute("company_id",companyId.getCompany_id());
        		paginate(queryList,1,request);

        		lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
        	}else if(reqCode.equals(REGCARD)){
                //���յ�����µĿ�����Ϣ����
        		POSBCCardInfo cardInfo=getCardDataA(request);
        		posManage.addCardType(cardInfo);
                //֪ͨATMP
                //����֪ͨATMP���ص���Ϣ
        		String target="/ToucsMonitor/posBCCard?reqCode=16004";
        		String btnTitle="��ѯ����";
                request.setAttribute("targetURL",target);
                request.setAttribute("btnTitle",btnTitle);
                String Message="";
                String retMessage="";
              
                    //������־��Ϣ
                    String userOperRstDesc="������һ��������Ϣ,������Ϣ:";
                    userOperRstDesc=userOperRstDesc+"�����="+cardInfo.getCard_id()+"�ŵ���="+cardInfo.getTrack_no();
                    userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getCard_match()+"����״̬="+cardInfo.getRec_useflag();
                    userOperRstDesc=userOperRstDesc+"��������ʱ���="+cardInfo.getDatetime()+"��˾���="+cardInfo.getCompany_id();
                    userOperRstDesc=userOperRstDesc+"��ע1="+cardInfo.getMemo1()+"��ע2="+cardInfo.getMemo2();
                    userOperRstDesc=userOperRstDesc+"�����صĴ�������"+retMessage;
                    //д����־
                    this.writeLog(loginfo,userOperRstDesc);
                
                Message="���ӿ�����Ϣ�ɹ���";
                Message=Message+retMessage;
                request.setAttribute("Message",Message);

                PromptBean prompt=new PromptBean("������Ϣ����");
                prompt.setPrompt(Message);
                prompt.setButtonUrl(0,"�������","/ToucsMonitor/posBCCard?reqCode="+REGCARD);
                prompt.setButtonUrl(1,"������ѯ","/ToucsMonitor/posBCCard?reqCode="+QUERYCARD);
                request.setAttribute("prompt",prompt);
                lTarget="/ToucsMonitor/Success.jsp";
        	}else if(reqCode.equals(MODCARD)){
				//���յ��޸Ŀ�����Ϣ����ȡ���޸ĵ�ֵ���ύ�޸�����
				Debug.println("���յ��޸�Ԥ����pos��������Ϣ����ȡ���޸ĵ�ֵ���ύ�޸�����");
				POSBCCardInfo cardInfo=getCardDataQ(request);
				Debug.println("POSBCCardBean.updateCardType");
				
				String oldCard_id=request.getParameter("oldCard_id");
				
				String oldCard_match=request.getParameter("oldCard_match");
				System.out.println("ceshi+++++++++++"+oldCard_match);
				posManage.updateCardType(cardInfo,oldCard_id,oldCard_match);
				Debug.println("POSBCCardBean.updateCardType  end");
				
				//֪ͨATMP
				String retMessage="";
				String Message="";
				Util util = new Util();
				int time = Integer.parseInt(util.getCurrentTime());
				System.out.println("��ǰInteger��ʱ��"+time);
				
						String userOperRstDesc="�޸�ǰԤ����pos��������Ϣ:";
						

						userOperRstDesc=userOperRstDesc+"�����="+cardInfo.getCard_id()+"�ŵ���="+cardInfo.getTrack_no();
	                    userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getCard_match()+"����״̬="+cardInfo.getRec_useflag();
	                    userOperRstDesc=userOperRstDesc+"��������ʱ���="+cardInfo.getDatetime()+"��˾���="+cardInfo.getCompany_id();
	                    userOperRstDesc=userOperRstDesc+"���Ѿ�֪ͨATMP,���صĴ�������"+retMessage;
						
					
						//д����־
						this.writeLog(loginfo,userOperRstDesc);
					
				//����֪ͨATMP���ص���Ϣ

				PromptBean prompt=new PromptBean("Ԥ����pos��������Ϣ����");
				prompt.setPrompt(Message);
				prompt.setPrompt("Ԥ���������޸ĳɹ���");
				prompt.setButtonUrl(0,"���","/ToucsMonitor/posBCCard?reqCode="+REGCARD);
				prompt.setButtonUrl(1,"��ѯ","/ToucsMonitor/posBCCard?reqCode="+QUERYCARD);
				request.setAttribute("prompt",prompt);
				lTarget="/ToucsMonitor/Success.jsp";
        	}else if(reqCode.equals(MODCARDREQ1)){
				//���յ�Ҫ�޸Ŀ�����Ϣ�����û�������Ҫ�޸ĵĿ������������û������������ѯ�������ѯ���Ϊ1����ת���޸�Ҷ�棬
				//�����ѯ�Ľ����Ϊ1����ת����ѯ���Ҷ�档�����ѯ���Ϊ0���򷵻ص��޸�����Ҷ��
				/*��ѯ********************************************************/
				POSBCCardInfo cardInfo = getCardDataQ(request);
				List queryList=posManage.QueryCardType(cardInfo);
				//�Ѳ�ѯ������ӵ�session�С�
				request.getSession().setAttribute("Query_Card_Condion",cardInfo);
				/*��ѯ  End******************************************************/
				if(queryList==null || queryList.size()==0){
				    POSBCCompanyId companyId=new POSBCCompanyId();
		            companyId.query(0);
		            request.setAttribute("company_id",companyId.getCompany_id());
					request.setAttribute("Message","û�����������������ļ�¼�����������룡");
					request.setAttribute("reqCode",MODCARD);
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQuery.jsp";
				}else if(queryList.size()==1){  //��ѯ��������Ϊ1����ת���޸�Ҷ��
					POSBCCardInfo cardTypeStruct=(POSBCCardInfo)queryList.get(0);
					request.setAttribute("CardTypeStruct",cardTypeStruct);
					 POSBCCompanyId companyId=new POSBCCompanyId();
		             companyId.query(0);
		             request.setAttribute("company_id",companyId.getCompany_id());
	
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardModify.jsp";
				}else if(queryList.size()>1){//��ѯ�������ݡ�1����ת����ѯ���Ҷ��
					session.setAttribute("QueryCardRstList",queryList);
					paginate(queryList,1,request);
					//��ѯԤ������˾���
					 POSBCCompanyId companyId=new POSBCCompanyId();
		             companyId.query(0);
		             request.setAttribute("company_id",companyId.getCompany_id());
					lTarget="/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
				}
			}else if(reqCode.equals(MODCARDREQ2)){
				/**
				 *�û�������ѡ��ĳһҪ�޸ĵÿ�����Ϣ�����Ȳ�ѯ�û�ѡ��Ŀ�����Ϣ������ÿ�����Ϣ
				 *�Ѿ��������û�ɾ��������ʾ�ÿ�����Ϣ�Ѿ���ɾ�����������޸�ҳ��
				 **/
				//ȡ���û�ѡ��Ŀ�����Ϣ
				Debug.println("deal MODIFYCARDTYPEREQUEST2  reuest: begin");
				String strIndex="";
				int index=0;
				List queryList=(List)session.getAttribute("QueryCardRstList");
				
				//���session���ڣ��׳��쳣����ʾ�û��뿪�û��뿪ϵͳ̫��
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
				//�����û�����Ŀ�����Ϣ��ѯ
				List queryNewList=posManage.QueryCardType(cardInfo);
				/*��ѯ  End******************************************************/
				
				//�����ѯ���Ϊ0�����򵽴���ҳ��
				if(queryNewList==null || queryNewList.size()==0){
					throw new MonitorException(ErrorDefine.OTHERDELETETHISCARDID,ErrorDefine.OTHERDELETETHISCARDIDDESC);
				}else{
					//�����ѯ�����Ϊ0�������޸�ҳ��.��������û�����Ĳ�ѯ��		
					//��ѯ����������أ���ʱ��������������������ǵ������������ʵ���ϲ����ڣ�
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
				//ȡ��Ҫɾ���Ŀ�����Ϣ�ṹ�壬��ɾ��֮��
				//���´����ݿ��в�ѯ��ȡ���û�����һ��ҳ�����õĲ�ѯ������
				String strIndex="";
				int index=0;
										
				//ȡ��session�еĲ�ѯ�����
				List queryList=(List)session.getAttribute("QueryCardRstList");
				//���session���ڣ����׳��쳣����ʾ�û����µ�½
				if(queryList==null){
					throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
				}
				if(strArray != null){
					//ҳ�洫�ݸ�ѡ���е�ֵ��Ϊ�����������ɾ��
					posManage.deleteBCCards(strArray);
				//	String retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					//jsp��ת
					PromptBean prompt = new PromptBean("Ԥ����POS������Ϣ����");
					prompt.setPrompt("Ԥ����POS������Ϣɾ���ɹ���");
					prompt.setButtonUrl(0, "��������", MANAGE_HOME);
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
					//ɾ��
					posManage.deleteCardType(cardInfo);
					String Message="";
	                String retMessage="";
					//֪ͨATMP

						
							//������־��Ϣ
							String userOperRstDesc="ɾ��Ԥ����pos��������Ϣ,������Ϣ:";
							userOperRstDesc=userOperRstDesc+"�����="+cardInfo.getCard_id()+"�ŵ���="+cardInfo.getTrack_no();
		                    userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getCard_match()+"����״̬="+cardInfo.getRec_useflag();
		                    userOperRstDesc=userOperRstDesc+"��������ʱ���="+cardInfo.getDatetime()+"��˾���="+cardInfo.getCompany_id();
		                    userOperRstDesc=userOperRstDesc+"���Ѿ�֪ͨATMP,���صĴ�������"+retMessage;
							//д����־
							this.writeLog(loginfo,userOperRstDesc);
					
						//retMessage="ɾ��Ԥ����pos��������Ϣ�ɹ�.";
						  
		                Message="ɾ��Ԥ����pos��������Ϣ�ɹ�!";
		                Message=Message+retMessage;
		                request.setAttribute("Message",Message);
                        
		                PromptBean prompt=new PromptBean("������Ϣ����");
		                prompt.setPrompt(Message);
		              
		                request.setAttribute("prompt",prompt);
		                lTarget="/ToucsMonitor/Success.jsp";
					}	
					
					
					//���²�ѯ
					Object obj2=request.getSession().getAttribute("Query_Card_Condion");
					
					if(obj2==null){
						throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
					}else{
						POSBCCardInfo cardInfo2 = (POSBCCardInfo)obj2;
						Debug.println("begin QueryCardType(qCardTypeStruct)");
						List queryListN=posManage.QueryCardType(cardInfo2);
						//Debug.println("intCurrPageNum.intValue()==========================="+intCurrPageNum.intValue());
						Debug.println("End QueryCardType(qCardTypeStruct)");
						//�������ò�ѯ�����
						session.setAttribute("QueryCardRstList",queryListN);
						//paginate(queryListN,intCurrPageNum.intValue(),request);
					}
					
					//��ѯԤ������˾
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
   * ȡ������ҳ���ֵ
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

            //���ѡ���ѯ����
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
   * ȡ��ʱ���
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
   * @param inNum  Ҫ��ʽ��������
   * @param dig    ��ʽ���������λ��
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
   * ͨ��Ȩ��У��
   * @param request
   * @param response
   * @throws MonitorException
   */
    private LoginInfo commProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,MonitorException,MonitorException{
		//��½У��
		LoginInfo loginfo=checkLogin(request,response);
		//ȡ���û�Ȩ����
		String maskCode=loginfo.getPermission();
		//ȡ���û���ԴȨ��
		Hashtable opvHashtable=POSBCCardBean.getOperRes(maskCode);
		//set�û�Ȩ�ޣ���jspҶ��ȡ���û�Ȩ��
		request.setAttribute("OpervRes",opvHashtable);
		//Ȩ��У��
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
    //�û�ѡ��ȡ������
    private String cancel(HttpServletRequest request) throws MonitorException{
        //���²�ѯ
        Object obj2=request.getSession().getAttribute("Query_Card_Condion");
        request.getSession().removeAttribute("QueryCardRstList");
        if(obj2!=null){
        	POSBCCardInfo qcardInfo=(POSBCCardInfo)obj2;
            List queryListN=posManage.QueryCardType(qcardInfo);
            //�������ò�ѯ�����
            request.getSession().setAttribute("QueryCardRstList",queryListN);
            //��ѯԤ������˾
            POSBCCompanyId companyId=new POSBCCompanyId();
            companyId.query(0);
            request.setAttribute("company_id",companyId.getCompany_id());
            paginate(queryListN,1,request);
        }
        return "/ToucsMonitor/POSDeviceManage/POSBCCardQueryRst.jsp";
    }

}
