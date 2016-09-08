package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;


import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;

/**
 * <p>Title: ���pos������ҵ����Servlet</p>
 * <p>Description: �������ܿͻ����󣬲�������Ӧ����������</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ADTEC</p>
 * @author syl
 */
public class POSLcCardServlet extends CommonServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String REGCARD="14701";  //�������pos��������Ϣ������
	private static final String MODCARD="14702";  //�޸����pos��������Ϣ������
	private static final String MODCARDREQ1="1470201";  //�Ӳ�ѯҳ�淢����޸����pos��������Ϣ������
	private static final String MODCARDREQ2="1470202";  //�ӹ���ҳ�淢����޸����pos��������Ϣ������
	private static final String DELCARD="14704";  //ɾ�����pos��������Ϣ������
	private static final String QUERYCARD="14703";  //��ѯ���pos��������Ϣ������
	private static final String QUERYHANDCARD="14705";	//���pos�����俨�Ų�ѯ
	private static final String CANCEL="CANCEL";  //ȡ��������
	private static final String SETHANDFLAG="14706";//�������pos�������־����
	private static final String UPLOAD="14707";//���pos������Ϣ�ļ��ϴ�����
	
	private static final String PAGEPROCESSOR="PAGEPROCESSOR";
	private static final String ERRORPAGEURL="";
	private static final String MG7550="12015";
	private static PageProcessor pageProcessor=null;
	
	//ҵ��Bean��ʵ��
	private static POSLcCardBean posLcCardBean=new POSLcCardBean();
	
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
			lTarger = "/ToucsMonitor/POSDeviceManage/PosLcCardUpload.jsp";			
		}
		if(reqCode!=null) reqCode=reqCode.trim();
		try{
			if(reqCode.equals(REGCARD)){
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(0);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("cardClass",queryCode.getCardClass());
				request.setAttribute("cardType",queryCode.getCardType());
				lTarger="/ToucsMonitor/POSDeviceManage/POSLcCardReg.jsp";
			} else if(reqCode.equals(MODCARD)){
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(1);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("reqCode",MODCARDREQ1);
				lTarger="/ToucsMonitor/POSDeviceManage/POSLcCardQuery.jsp";
			} else if(reqCode.equals(QUERYCARD) ){
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(1);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("reqCode",QUERYCARD);
				lTarger="/ToucsMonitor/POSDeviceManage/POSLcCardQuery.jsp";
			}else if(reqCode.equals(DELCARD)){
 	    
			}else if(reqCode.equals(CANCEL)){
				return cancel(request);
			}else if(reqCode.equals(UPLOAD)){
				lTarger = "/ToucsMonitor/POSDeviceManage/PosLcCardUpload.jsp";
			}else if(reqCode.equals(QUERYHANDCARD)){
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(1);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("reqCode",QUERYHANDCARD);
				lTarger="/ToucsMonitor/POSDeviceManage/POSLcHandCardQuery.jsp";
			}else if (reqCode.equals(SETHANDFLAG)) {
				String hand_flag = request.getParameter("flag");
				String Track_no = request.getParameter("track_no");
				String cardMatch = request.getParameter("cardMatch");
				String MatchStart = request.getParameter("matchStart");
				if (hand_flag == null || Track_no == null ||
						cardMatch == null || MatchStart == null) {
					throw new MonitorException(ErrorDefine.GETTXCODEERR,ErrorDefine.GETTXCODEERRDESC);
				}
				POSLcCardInfo cardInfo = new POSLcCardInfo();
				cardInfo.set_hand_flag(hand_flag);
				cardInfo.setTrack_no(Track_no);
				cardInfo.setcardMatch(cardMatch);
				cardInfo.setMatchStart(MatchStart);
				if (posLcCardBean.setCardManuFlag(cardInfo) <= 0) {
					throw new MonitorException(ErrorDefine.UNKNOWERROR,ErrorDefine.UNKNOWERRORDESC);
				}
				/***
				 ��lyp���ӷ��ذ�ť ----Fancy***/
				PromptBean prompt = new PromptBean("��ҵIC�����俨����");
				//ת�ɹ���ʾҳ��
				if (hand_flag.trim().equalsIgnoreCase("1") ) {
					writeLog(logInfo, "�ѽ����ֿ���Ϊ[ �ɽ������� ]״̬��");
					prompt.setPrompt("�ѽ����ֿ���Ϊ[ �ɽ������� ]״̬��");
				}else if (hand_flag.trim().equalsIgnoreCase("0") ) {
					writeLog(logInfo, "�ѽ����ֿ���Ϊ[ ���ɽ������� ]״̬��");
					prompt.setPrompt("�ѽ����ֿ���Ϊ[ ���ɽ������� ]״̬��");
				}
				
				//�޸�ʱ���ڵ�ҳ��
				String showPage = request.getParameter("curPage");
				String url="/ToucsMonitor/posLcCard?reqCode=14705&page="+showPage+"&isFirstTime=0"+"&reQueryFlag=1";
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
			if(reqCode.equals(REGCARD)){
				//���յ�����µĿ�����Ϣ����
				POSLcCardInfo cardInfo = getCardDataA(request);
				posLcCardBean.addCardType(cardInfo);
				
				//֪ͨATMP
				//����֪ͨATMP���ص���Ϣ
				String Message="";
				String retMessage="";
				Util util = new Util();
				int time = Integer.parseInt(util.getCurrentTime());
				System.out.println("��ǰInteger��ʱ��"+time);
				if(time<90000||time>180000){
					try{
						retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					}catch(MonitorException mexp){
						String target="/ToucsMonitor/posLcCard?reqCode=14703";
						String btnTitle="��ѯ���pos������";
						request.setAttribute("targetURL",target);
						request.setAttribute("btnTitle",btnTitle);
						Message="�ɹ��������pos��������Ϣ�������У���";
						throw new MonitorException(mexp.getErrCode(),Message+mexp.getMessage());
					}finally{
						//������־��Ϣ
						String userOperRstDesc="������һ�����pos��������Ϣ,������Ϣ:";
						userOperRstDesc=userOperRstDesc+"�ŵ���="+cardInfo.getTrack_no()+"����ƫ��="+cardInfo.getMatchStart();
						userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getcardMatch()+"����״̬="+cardInfo.getrec_useflag();
						userOperRstDesc=userOperRstDesc+"��������ʱ���="+cardInfo.getrec_datetime()+"��������="+cardInfo.getbankCode();
						userOperRstDesc=userOperRstDesc+"�����صĴ�������"+retMessage;
						//д����־
						this.writeLog(loginfo,userOperRstDesc);
					}
					Message="�������pos��������Ϣ�ɹ�.";
				}else{
					Message="�������pos��������Ϣ���ݿ�����ɹ�,��ˢ�¿���ʧ��,���ڹ涨��ʱ����(18:00-23:59��00:00-8:00)���п���ˢ�²���.";
				}	
				request.setAttribute("Message",Message);
				PromptBean prompt=new PromptBean("���pos��������Ϣ����");
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,"���","/ToucsMonitor/posLcCard?reqCode="+REGCARD);
				prompt.setButtonUrl(1,"��ѯ","/ToucsMonitor/posLcCard?reqCode="+QUERYCARD);
				request.setAttribute("prompt",prompt);
				lTarget="/ToucsMonitor/Success.jsp";
			}else if(reqCode.equals(MODCARD)){
				//���յ��޸Ŀ�����Ϣ����ȡ���޸ĵ�ֵ���ύ�޸�����
				Debug.println("���յ��޸����pos��������Ϣ����ȡ���޸ĵ�ֵ���ύ�޸�����");
				POSLcCardInfo cardInfo=getCardDataA(request);
				Debug.println("parameterSetBean.updateCardType");
				
				Integer tmpold=null;
				tmpold=new Integer(request.getParameter("oldTrack_no"));
				int oldTrack_no=tmpold.intValue();
				
				tmpold=new Integer(request.getParameter("oldMatchStart"));
				int oldMatchStart=tmpold.intValue();
				
				String oldCardMatch=request.getParameter("oldCardMatch");
				String oldRec_datetime=request.getParameter("oldRec_datetime");
				String oldRec_useflag=request.getParameter("oldRec_useflag");
				
				posLcCardBean.updateCardType(cardInfo,oldTrack_no,oldMatchStart,oldCardMatch,oldRec_datetime,oldRec_useflag);
				Debug.println("parameterSetBean.updateCardType  end");
				
				//֪ͨATMP
				String retMessage="";
				String Message="";
				Util util = new Util();
				int time = Integer.parseInt(util.getCurrentTime());
				System.out.println("��ǰInteger��ʱ��"+time);
				if(time<90000||time>180000){
					try{
						retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					}catch(MonitorException mexp){
						String target="/ToucsMonitor/posLcCard?reqCode=14703";
						String btnTitle="��ѯ���pos������";
						request.setAttribute("targetURL",target);
						request.setAttribute("btnTitle",btnTitle);
						Message="�ɹ��޸����pos��������Ϣ����";
						retMessage=Message+mexp.getMessage();
					}finally{
						//������־��Ϣ
						String userOperRstDesc="�޸�ǰ���pos��������Ϣ:";
						userOperRstDesc=userOperRstDesc+"�ŵ���="+String.valueOf(oldTrack_no) +"����ƫ��="+String.valueOf(oldMatchStart);
						userOperRstDesc=userOperRstDesc+"�����ʶ="+oldCardMatch+"����״̬="+oldRec_useflag;
						userOperRstDesc=userOperRstDesc+"ʱ���="+oldRec_datetime;
						userOperRstDesc=userOperRstDesc+"�޸ĺ�:";
						userOperRstDesc=userOperRstDesc+"�ŵ���="+cardInfo.getTrack_no()+"����ƫ��="+cardInfo.getMatchStart();
						userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getcardMatch()+"����״̬="+cardInfo.getrec_useflag();
						userOperRstDesc=userOperRstDesc+"ʱ���="+cardInfo.getrec_datetime()+"���д���="+cardInfo.getbankCode();
						userOperRstDesc=userOperRstDesc+";"+retMessage;
						//д����־
						this.writeLog(loginfo,userOperRstDesc);
					}
					Message="�޸����pos��������Ϣ�ɹ�.";
				}else{
					Message="�޸����pos��������Ϣ���ݿ�����ɹ�,��ˢ�¿���ʧ��,���ڹ涨��ʱ����(18:00-23:59��00:00-8:00)���п���ˢ�²���.";
				}	
				//����֪ͨATMP���ص���Ϣ

				PromptBean prompt=new PromptBean("���pos��������Ϣ����");
				prompt.setPrompt(Message);
				prompt.setButtonUrl(0,"���","/ToucsMonitor/posLcCard?reqCode="+REGCARD);
				prompt.setButtonUrl(1,"��ѯ","/ToucsMonitor/posLcCard?reqCode="+QUERYCARD);
				request.setAttribute("prompt",prompt);
				lTarget="/ToucsMonitor/Success.jsp";
			}else if(reqCode.equals(MODCARDREQ1)){
				//���յ�Ҫ�޸Ŀ�����Ϣ�����û�������Ҫ�޸ĵĿ������������û������������ѯ�������ѯ���Ϊ1����ת���޸�Ҷ�棬
				//�����ѯ�Ľ����Ϊ1����ת����ѯ���Ҷ�档�����ѯ���Ϊ0���򷵻ص��޸�����Ҷ��
				/*��ѯ********************************************************/
				POSLcCardInfo cardInfo = getCardDataQ(request);
				List queryList=posLcCardBean.QueryCardType(cardInfo);
				//�Ѳ�ѯ������ӵ�session�С�
				request.getSession().setAttribute("Query_Card_Condion",cardInfo);
				/*��ѯ  End******************************************************/
				if(queryList==null || queryList.size()==0){
					QueryCode2 queryCode=new QueryCode2();
					queryCode.query(1);
					request.setAttribute("bankCode",queryCode.getBankCode()) ;
					request.setAttribute("Message","û�����������������ļ�¼�����������룡");
					request.setAttribute("reqCode",MODCARD);
					lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardQuery.jsp";
				}else if(queryList.size()==1){  //��ѯ��������Ϊ1����ת���޸�Ҷ��
					POSLcCardInfo cardTypeStruct=(POSLcCardInfo)queryList.get(0);
					request.setAttribute("CardTypeStruct",cardTypeStruct);
					QueryCode2 queryCode=new QueryCode2();
					queryCode.query(0);
					request.setAttribute("bankCode",queryCode.getBankCode()) ;
					request.setAttribute("cardClass",queryCode.getCardClass());
					request.setAttribute("cardType",queryCode.getCardType());
					lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardModify.jsp";
				}else if(queryList.size()>1){//��ѯ�������ݡ�1����ת����ѯ���Ҷ��
					session.setAttribute("QueryCardRstList",queryList);
					paginate(queryList,1,request);
					//��ѯ���д��룬�����࣬����������
					QueryCode2 queryCode=new QueryCode2();
					queryCode.query(0);
					request.setAttribute("bankCode",queryCode.getBankCode()) ;
					request.setAttribute("cardClass",queryCode.getCardClass());
					request.setAttribute("cardType",queryCode.getCardType());
					lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardQueryRst.jsp";
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
			
				POSLcCardInfo cardInfo = (POSLcCardInfo)queryList.get(index);
				//�����û�����Ŀ�����Ϣ��ѯ
				List queryNewList=posLcCardBean.QueryCardType(cardInfo);
				/*��ѯ  End******************************************************/
				
				//�����ѯ���Ϊ0�����򵽴���ҳ��
				if(queryNewList==null || queryNewList.size()==0){
					throw new MonitorException(ErrorDefine.OTHERDELETETHISCARDID,ErrorDefine.OTHERDELETETHISCARDIDDESC);
				}else{
					//�����ѯ�����Ϊ0�������޸�ҳ��.��������û�����Ĳ�ѯ��		
					//��ѯ����������أ���ʱ��������������������ǵ������������ʵ���ϲ����ڣ�
					POSLcCardInfo cardTypeStruct1=(POSLcCardInfo)queryNewList.get(0);
					request.setAttribute("CardTypeStruct",cardTypeStruct1);
					QueryCode2 queryCode=new QueryCode2();
					queryCode.query(0);
					request.setAttribute("bankCode",queryCode.getBankCode()) ;
					request.setAttribute("cardClass",queryCode.getCardClass());
					request.setAttribute("cardType",queryCode.getCardType());
					lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardModify.jsp";
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
					posLcCardBean.deleteLcCards(strArray);
					String retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					//jsp��ת
					PromptBean prompt = new PromptBean("���POS������Ϣ����");
					prompt.setPrompt("���POS������Ϣɾ���ɹ���");
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
					
					POSLcCardInfo cardInfo = (POSLcCardInfo)queryList.get(index);
					//ɾ��
					posLcCardBean.deleteCardType(cardInfo);
					
					//֪ͨATMP
					String retMessage="";
					Util util = new Util();
					int time = Integer.parseInt(util.getCurrentTime());
					System.out.println("��ǰInteger��ʱ��"+time);
					if(time<90000||time>180000){
						try{
							retMessage=notifyATMP("MG7550","000000000",loginfo.getOrgID());
						}catch(MonitorException mexp){
							String target="/ToucsMonitor/posLcCard?reqCode=14703";
							String btnTitle="��ѯ���pos������";
							request.setAttribute("targetURL",target);
							request.setAttribute("btnTitle",btnTitle);
							String Message="�ɹ������ݿ���ɾ�����pos��������Ϣ,��";
							retMessage=Message+mexp.getMessage();
						}finally{
							//������־��Ϣ
							String userOperRstDesc="ɾ�����pos��������Ϣ,������Ϣ:";
							userOperRstDesc=userOperRstDesc+"�ŵ���="+cardInfo.getTrack_no()+"����ƫ��="+cardInfo.getMatchStart();
							userOperRstDesc=userOperRstDesc+"�����ʶ="+cardInfo.getcardMatch()+"����״̬="+cardInfo.getrec_useflag();
							userOperRstDesc=userOperRstDesc+"��������ʱ���="+cardInfo.getrec_datetime()+"��������="+cardInfo.getbankCode();
							userOperRstDesc=userOperRstDesc+";���صĴ�������"+retMessage;
							//д����־
							this.writeLog(loginfo,userOperRstDesc);
						}
						retMessage="ɾ�����pos��������Ϣ�ɹ�.";
					}else{
						retMessage="ɾ�����pos��������Ϣ���ݿ�����ɹ�,��ˢ�¿���ʧ��,���ڹ涨��ʱ����(18:00-23:59��00:00-8:00)���п���ˢ�²���.";
					}	
					
					//����֪ͨATMP���ص���Ϣ
					request.setAttribute("Message",retMessage);
					//���²�ѯ
					Object obj2=request.getSession().getAttribute("Query_Card_Condion");
					
					if(obj2==null){
						throw new MonitorException(ErrorDefine.SESSONTIMEOUT,ErrorDefine.SESSONTIMEOUTDESC);
					}else{
						POSLcCardInfo cardInfo2 = (POSLcCardInfo)obj2;
						Debug.println("begin QueryCardType(qCardTypeStruct)");
						List queryListN=posLcCardBean.QueryCardType(cardInfo2);
						Debug.println("intCurrPageNum.intValue()==========================="+intCurrPageNum.intValue());
						Debug.println("End QueryCardType(qCardTypeStruct)");
						//�������ò�ѯ�����
						session.setAttribute("QueryCardRstList",queryListN);
						paginate(queryListN,intCurrPageNum.intValue(),request);
					}
					
					//��ѯ���д��룬�����࣬����������
					QueryCode2 queryCode=new QueryCode2();
					queryCode.query(0);
					request.setAttribute("bankCode",queryCode.getBankCode()) ;
					request.setAttribute("cardClass",queryCode.getCardClass());
					request.setAttribute("cardType",queryCode.getCardType());
					lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardQueryRst.jsp";
				}
			}else if(reqCode.equals(QUERYCARD)){
				//ȡ�ò�ѯ����
				POSLcCardInfo cardInfo = getCardDataQ(request);
				//��ѯ
				List queryList=posLcCardBean.QueryCardType(cardInfo);
				session.setAttribute("QueryCardRstList",queryList);
				//��Ӳ�ѯ������ӵ�session��
				request.getSession().setAttribute("Query_Card_Condion",cardInfo);
				
				//��ѯ���д��룬�����࣬����������
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(0);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("cardClass",queryCode.getCardClass());
				request.setAttribute("cardType",queryCode.getCardType());
				paginate(queryList,1,request);
				
				lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardQueryRst.jsp";
			}else if(reqCode.equals(QUERYHANDCARD)){
				POSLcCardInfo cardInfo;
				String isFirstTime = request.getParameter("isFirstTime");
				String reQueryFlag = request.getParameter("reQueryFlag");
				if( reQueryFlag == null ){  
					reQueryFlag ="";
				}	
				if( isFirstTime.trim().equalsIgnoreCase("1") ){ //ȡ�ò�ѯ����
					cardInfo=getCardDataQ(request);
					session.setAttribute("QueryCardType", cardInfo );
				}else{
					cardInfo = (POSLcCardInfo)session.getAttribute("QueryCardType");
					if( cardInfo == null ){
						throw new MonitorException(ErrorDefine.NULLPOINTEX,ErrorDefine.NULLPOINTEXDESC);
					}	
				}
				
				//��ѯ
				List queryList = null;
				if( isFirstTime != null && isFirstTime.trim().equalsIgnoreCase("0")&&!reQueryFlag.trim().equalsIgnoreCase("1")){
					Debug.println("**********debug: handquery FirstTime = false **********");
					queryList = (List)session.getAttribute("HandQueryRstList");
				}else{
					Debug.println("**********debug: handquery FirstTime = true **********");
					queryList=posLcCardBean.QueryCardType(cardInfo);
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
				//��ѯ���д��룬�����࣬����������
				QueryCode2 queryCode=new QueryCode2();
				queryCode.query(0);
				request.setAttribute("bankCode",queryCode.getBankCode()) ;
				request.setAttribute("cardClass",queryCode.getCardClass());
				request.setAttribute("cardType",queryCode.getCardType());
				lTarget="/ToucsMonitor/POSDeviceManage/POSLcHandCardQueryRst.jsp";
			}else if(reqCode.equals(PAGEPROCESSOR)){
				List queryList=(List)session.getAttribute("QueryCardRstList");
				String strCurrPageNum=request.getParameter("CurrPageNum");
				request.setAttribute("CurrPageNum",strCurrPageNum);
				Integer intCurrPageNum=new Integer(strCurrPageNum);
				paginate(queryList,intCurrPageNum.intValue(),request);
				lTarget="/ToucsMonitor/POSDeviceManage/POSLcCardQueryRst.jsp";
			}else if(reqCode.equals(MG7550)){
				if(loginfo!=null){
					String Message=notifyATMP("MG7550","000000000",loginfo.getOrgID());
					//д����־
					this.writeLog(loginfo,Message);
					PromptBean prompt=new PromptBean("ATM�豸����");
					prompt.setPrompt(Message);
					//����ǿ���ˢ�����������ύ�ɹ���ת������ˢ��ҳ��
					//ȡ�������������²�ѯ��ص������б�ҳ��
					String nurl="/ToucsMonitor/posLcCard?reqCode="+CANCEL;
					prompt.setButtonUrl(0,"ȷ��",nurl);
					request.setAttribute("prompt",prompt);
					lTarget="/ToucsMonitor/Success.jsp";
				}else{
					throw new MonitorException(ErrorDefine.GETUSERINFOERROR,ErrorDefine.GETUSERINFOERRORDESC);
				}
			}else if(reqCode.equals(UPLOAD)){
				String path =request.getParameter("excel");//excel�ϴ��ļ����ڵ�·��
				System.out.println("�ļ����ڵ�·��Ϊ"+path);				  
//				System.out.println("ready........");
//				posLcCardBean.downloadFile();
//				System.out.println("end..........");
				PromptBean prompt = new PromptBean("���POS�������");
				File file = new File("D:\\pos_lc_card.xls");
				System.out.println("·����ӡ����:"+file);			
				
				FileInputStream in=new FileInputStream(file.getPath());   
			
				
				boolean flag = posLcCardBean.uploadFile("12.0.98.50", 21, "weblogic", "weblogic", "D:/load/", "pos_lc_card.xls", in);
				
				if(flag==true){
					System.out.println("�ļ��ϴ��ɹ�!!!");
				}else{
					 prompt.setPrompt("�ļ��ϴ�ʧ�ܣ�");
					 request.setAttribute("prompt", prompt);				 
				     lTarget="/ToucsMonitor/Success.jsp";
				}

			}else if(reqCode.equals(CANCEL)){
				return cancel(request);
			}
		}catch(MonitorException exp1){
			exp1.printStackTrace();
			throw exp1;
		}
		return lTarget;
	}
	
	/**
	 * ȡ������ҳ���ֵ
	 * @param request
	 * @return
	 */
	private POSLcCardInfo getCardDataA(HttpServletRequest request) throws MonitorException{
		POSLcCardInfo cardInfo=new POSLcCardInfo();
		String track_no="";
		String matchStart="1";
		String cardMatch="";
		String cardStart="1";
		String cardLen="";
		String bankStart="0";
		String bankLen="";
		String bankMatch="";
		String bankCode="";
		String cardClass="";
		String cardType="";
		String pinoffset="";
		String cardArea="";
		String rec_datetime="";
		String rec_useflag="";
		
		try{
			Integer tmp=null;
			try{
				track_no=request.getParameter("track_no").trim();
				tmp=new Integer(track_no);
			}catch(Exception exp1){
				throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
			}
			cardMatch=request.getParameter("cardMatch");
			try{
				cardLen=request.getParameter("cardlen");
				cardLen=cardLen.trim();
			}catch(Exception exp1){
				cardLen=null;
			}

			bankCode=request.getParameter("bankCode");
			cardClass=request.getParameter("cardClass");
			cardType=request.getParameter("cardType");
			rec_datetime=getDataTime();
			rec_useflag=request.getParameter("rec_useflag");
			cardInfo.setTrack_no(track_no);
			cardInfo.setMatchStart(matchStart);
			cardInfo.setcardMatch(cardMatch);
			cardInfo.setcardStart(cardStart) ;
			cardInfo.setcardLen(cardLen);
			cardInfo.setbankStart(bankStart) ;
			cardInfo.setbankLen(bankLen) ;
			cardInfo.setbankMatch(bankMatch) ;
			cardInfo.setbankCode(bankCode);
			cardInfo.setcardClass(cardClass) ;
			cardInfo.setcardType(cardType);
			cardInfo.setpinoffset(pinoffset) ;
			cardInfo.setcardArea(cardArea);
			cardInfo.setrec_datetime(rec_datetime) ;
			cardInfo.setrec_useflag(rec_useflag) ;
		}catch(Exception exp){
			exp.printStackTrace();
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		}
		return cardInfo;
	}
	
	private POSLcCardInfo getCardDataQ(HttpServletRequest request) throws MonitorException{
		POSLcCardInfo  cardInfo=new POSLcCardInfo();
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
			
			//���ѡ���ѯ����
			if(rec_useflag == null || rec_useflag.equals("3")){
				rec_useflag=null;
			}	
			
			cardInfo.setTrack_no(track_no);
			cardInfo.setMatchStart(matchStart);
			cardInfo.setcardMatch(cardMatch);
			cardInfo.setbankCode(bankCode);
			cardInfo.setrec_datetime(rec_datetime) ;
			cardInfo.setrec_useflag(rec_useflag) ;
		}catch(Exception exp){
			throw new MonitorException(ErrorDefine.INPUTDATAERROR,ErrorDefine.INPUTDATAERRORDESC);
		}
		return cardInfo;
	}
	
	/**
	 * ȡ��ʱ���
	 * @return
	 */
	public String getDataTime(){
		Calendar lcaSysNow = Calendar.getInstance();
		int year=lcaSysNow.get(Calendar.YEAR);
		int month=lcaSysNow.get(Calendar.MONTH)+1;
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
	//========================================================================================================================================================//
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
		Hashtable opvHashtable=POSLcCardBean.getOperRes(maskCode);
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
		QueryCode2 queryCode=new QueryCode2();
		queryCode.query(0);
		request.setAttribute("bankCode",queryCode.getBankCode()) ;
		request.setAttribute("cardClass",queryCode.getCardClass());
		request.setAttribute("cardType",queryCode.getCardType());
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
			POSLcCardInfo cardInfo = (POSLcCardInfo)obj2;
			List queryListN=posLcCardBean.QueryCardType(cardInfo);
			//�������ò�ѯ�����
			request.getSession().setAttribute("QueryCardRstList",queryListN);
			//��ѯ���д��룬�����࣬����������
			QueryCode2 queryCode=new QueryCode2();
			queryCode.query(0);
			request.setAttribute("bankCode",queryCode.getBankCode()) ;
			request.setAttribute("cardClass",queryCode.getCardClass());
			request.setAttribute("cardType",queryCode.getCardType());
			paginate(queryListN,1,request);
		}
		return "/ToucsMonitor/POSDeviceManage/POSLcCardQueryRst.jsp";
	}
}