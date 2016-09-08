package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title:POS�̻�����Servlet�� </p>
 * <p>Description: ����POS�̻�������ص�Http����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Adtec</p>
 * @author lihl
 * @version 1.0
 */

public class POSFCardMerchantServlet extends CommonServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final private String CONTENT_TYPE = "text/html; charset=GBK";
    /**
     *���������
     **/
    //�����룺��ȡ�̻�����
    private static final String POSFCARDMERCHANTADD = "10501";
    //�����룺�̻��޸�
    private static final String POSFCARDMERCHANTUPDATE = "10502";
    //�����룺�̻���ѯ
    private static final String POSFCARDMERCHANTQUERY = "10504";
    //�����룺�̻�ɾ��
    private static final String POSFCARDMERCHANTDELETE = "10503";

    //������������
    private static final String PARMGETPAGE = "page";
    private static final String PARMGETQUERYPAGE = "queryPage";

    /**
     *��������
     **/
    //POS�̻������·��
    private static final String MANAGE_HOME="/ToucsMonitor/fcardmerchantconfig";
    //POS����ҳ���·��
    private static final String PAGE_HOME="/POSDeviceManage/";
    //POS�̻�������ʵ��
    private POSFCardMerchantBean  posFCardMerchant = new POSFCardMerchantBean();

    public void init() throws ServletException {
    }
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ȡ�������
        String reqCode=request.getParameter("reqCode");
        response.setHeader("Cache-Control","no-store");
        String target="";
        target = request.getParameter("target");
        //�û����У��
        LoginInfo login=null;
        //Ȩ�޿���
        try{
            //У���û��Ƿ��¼
            login=checkLogin(request,response);
            //У���û�����Ȩ��
            LoginManageBean.operValidate(login, POSFCARDMERCHANTADD);
        }catch(MonitorException exp){
            errProc(request,response,exp);
            return;
        }
        //Ŀ��JSPҳ��
        String tarJsp=null;
        //�̻���������
        if(reqCode==null){
            try{
                List l = posFCardMerchant.getMerchantList();
                request.setAttribute("posFCardMerchantList",l);
            }catch(MonitorException exp){
            	exp.printStackTrace();
            	System.out.println(exp.getMessage());
            }
            //Ҫ���ݵ�ǰ�û���ӵ���̻�����Ȩ�ޣ���ȷ��������ִ�еĲ���
            setPagePerm(request,login.getPermission());
            tarJsp=PAGE_HOME+"FCardPosMerchantManage.jsp?uid=" +login.getUserID();
            toPage(request,response,tarJsp);
        }else{
            //��ȡ����̻�ҳ��
            Debug.println("Rox not here");
            if(reqCode.equals(POSFCARDMERCHANTADD)){
                if ((target == null)||target.trim().equals("")){
                    posFCardMerchantAdd(request,response,false,login);
                }else if (target != null && target.equals(PARMGETPAGE)){
                    posFCardMerchantAdd(request,response,true,login);
                }
            }
            //��ȡ�޸��̻�ҳ��
            if(reqCode.equals(POSFCARDMERCHANTUPDATE)){
                if ((target == null)||target.trim().equals("")){
                    posFCardMerchantUpdate(request,response,login);
                }else{
                    if (target.equals(PARMGETPAGE)){
                        posFCardMerchantUpdateQuery(request,response,true,login);
                    }else if (target.equals(PARMGETQUERYPAGE)){
                        posFCardMerchantUpdateQuery(request,response,false,login);
                    }
                }
            }else if(reqCode.equals(POSFCARDMERCHANTQUERY)){//��ѯ�̻�
            	Debug.println("Target:"+target);
                if ((target == null)||target.trim().equals("")){
                    posFCardMerchantQuery(request,response,login);
                }else{
                    if (target.equals(PARMGETPAGE)){
                        posFCardMerchantQueryPage(request,response,login);
                    }
                }
            }else if(reqCode.equals(POSFCARDMERCHANTDELETE)){//ɾ���̻�
                Debug.println("ɾ���̻���Ϣ...POST����");
                posFCardMerchantDelete(request,response,login);
            }
        }
    }
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        //ȡ�������
        String reqCode=request.getParameter("reqCode");
        //ȡ�������
        String target="";
        target = request.getParameter("target");
        //Ȩ�޿���
        LoginInfo login=null;
        try{
            //У���û��Ƿ��¼
            login=checkLogin(request,response);
            //У���û�����Ȩ��
            LoginManageBean.operValidate(login,POSFCARDMERCHANTADD);
        }catch(MonitorException exp){
            errProc(request,response,exp);
            return;
        }

        //ȡsession��Ϣ
        HttpSession session=request.getSession(true);
        POSFCardMerchantInfo Info=(POSFCardMerchantInfo)session.getAttribute("posFCardMerchant");
        if (Info != null){
            try{
                List l = posFCardMerchant.getMerchantList();
                request.setAttribute("posFCardMerchantList",l);
            }catch(MonitorException exp){
            	exp.printStackTrace();
            	System.out.println(exp.getMessage());
            }
        }else{
            Vector posFCardMerchantList=new Vector();
            session.setAttribute("posFCardMerchantList",posFCardMerchantList);
            Debug.println("**********posFCardMerchantList post Null***************");
        }
        //����̻�
        if(reqCode.equals(POSFCARDMERCHANTADD)){
        	Debug.print("Here You are");
            if ((target == null)||target.trim().equals("")){
            	Debug.println("?");
                posFCardMerchantAdd(request,response,false,login);
            }else{
            	Debug.println("!");
                if (target.equals(PARMGETPAGE)){
                	Debug.println("!");
                    posFCardMerchantAdd(request,response,true,login);
                }
            }
        }else if(reqCode.equals(POSFCARDMERCHANTUPDATE)){//�޸��̻�
            if ((target == null)||target.trim().equals("")){
                posFCardMerchantUpdate(request,response,login);
            }else{
                if (target.equals(PARMGETPAGE)){
                    posFCardMerchantUpdateQuery(request,response,true,login);
                }else if (target.equals(PARMGETQUERYPAGE)){
                    posFCardMerchantUpdateQuery(request,response,false,login);
                }
            }
        }else if(reqCode.equals(POSFCARDMERCHANTQUERY)){//��ѯ�̻�
            if ((target == null)||target.trim().equals("")){
                posFCardMerchantQuery(request,response,login);
            }else{
                if (target.equals(PARMGETPAGE)){
                    posFCardMerchantQueryPage(request,response,login);
                }
            }
        }else if(reqCode.equals(POSFCARDMERCHANTDELETE)){ //ɾ���̻�
            posFCardMerchantDelete(request,response,login);
        }
    }
    //Clean up resources
    public void destroy() {
    }

    /**
     * �����û���ҳ��Ĳ���Ȩ��
     * @param req Http����
     * @param maskCode �û�Ȩ����
     */
    private void setPagePerm(HttpServletRequest req,String maskCode){
        if(LoginManageBean.checkPermMask(maskCode,POSFCARDMERCHANTADD)==0)
            req.setAttribute("POSFCARDMERCHANTADD","1");
        if(LoginManageBean.checkPermMask(maskCode,POSFCARDMERCHANTUPDATE)==0)
            req.setAttribute("POSFCARDMERCHANTUPDATE","1");
        if(LoginManageBean.checkPermMask(maskCode,POSFCARDMERCHANTDELETE)==0)
            req.setAttribute("POSFCARDMERCHANTDELETE","1");
        if(LoginManageBean.checkPermMask(maskCode,POSFCARDMERCHANTQUERY)==0)
            req.setAttribute("POSFCARDMERCHANTQUERY","1");
    }


    /**
     * ��ʼ���̻����ҳ��������б���Ϣ
     * @param req Http����
     * @param orgId ��������
     */
    private void initMerchantAddList(HttpServletRequest req,String orgId){
        Vector v;
        SqlAccess sq=null;
        try{
            sq=SqlAccess.createConn();
            //�̻�����
            v=CodeManageBean.queryCodes("0553",sq);
            req.setAttribute("McttypeList",v);
            //ʹ�ñ�־
            v=CodeManageBean.queryCodes("0554",sq);
            req.setAttribute("FlagList",v);
            //�����־
            v=CodeManageBean.queryCodes("0555",sq);
            req.setAttribute("ClearFlagList",v);

            //�����б�
            List list=CodeManageBean.queryOrgList(orgId);
            req.setAttribute("orgList",list);
            //�������б�
            List cardOrgList = null;
            if( list!=null ) {
            	cardOrgList = new ArrayList();
                for( int i=0 ; i<list.size() ; i++ ){
                	CodeBean code = (CodeBean)list.get(i);
                	//����DCC�������������˳����п�����
                	//363700�����ģ�3600���ֲ�
                	if( code.getCodeId().substring(5,9).equals("3600") || code.getCodeId().substring(3,9).equals("363700") ) {
                		cardOrgList.add(code);
                	}
                }
            }
            req.setAttribute("cardOrgList",cardOrgList);
        }catch(Exception exp){
        	exp.printStackTrace();
        	System.out.println(exp.getMessage());
        }finally{
            if(sq!=null){
                  sq.close();
            }      
        }
    }


    /**
     * ��ʼ���̻����ҳ��������б���Ϣ
     * @param req Http����
     * @param orgId ��������
     */
    private void initMerchantQueryList(HttpServletRequest req,String orgId){
        SqlAccess sq=null;
        try{
            sq=SqlAccess.createConn();
            System.out.println("1");
            List list=CodeManageBean.queryOrgList(orgId);
            req.setAttribute("orgList",list);
            //֧�У������������б�
            if(list!=null){
                String[] branchs=new String[list.size()];
                //�Ӳ�ѯ�õ�������������ȡ�����ϼ�����������֧�У����
                for(int i=0;i<branchs.length;i++){
                    CodeBean code=(CodeBean)list.get(i);
                    branchs[i]=code.getCodeType();
                }
                req.setAttribute("branchs",branchs);
            }
        }catch(Exception exp){
        	exp.printStackTrace();
        	System.out.println(exp.getMessage());
        }finally{
            if(sq!=null){
                  sq.close();
            }      
        }
    }


    /**
     * �̻����������
     * @param request Http����
     * @param response Http��Ӧ
     * @param isGet Get�����־
     * @param login �û���Ϣ
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantAdd(HttpServletRequest request,
                                HttpServletResponse response,
                                boolean isGetPage,LoginInfo login) throws ServletException, IOException{
    	Debug.println("Where is Rox" + (isGetPage ? "?" : "!"));
        String tarJsp=null;
        String mer_id = request.getParameter("mer_id");
        //�̻����ҳ������
        if(isGetPage){
        	tarJsp=PAGE_HOME+"FCardPosMerChantAdd.jsp?post="+POSFCARDMERCHANTADD + "&uid="+login.getUserID();
        	if (mer_id != null && mer_id.length() > 0) {
        		tarJsp=PAGE_HOME+"FCardPosMerChantAdd.jsp?post="+POSFCARDMERCHANTADD+"&uid="+login.getUserID();
        		POSMerchantBean p = new POSMerchantBean();
        		try {
        			POSMerchantInfo p1 = p.qryPosMerchant(mer_id);
        			if (p1 == null){
        				//δ�ҵ�������̻���ʾҳ��
        				PromptBean prompt=new PromptBean("�⿨�̻�����");
        				prompt.setPrompt("δ�ҵ�������̻�������¼��������̻����ϡ�");
        				prompt.setButtonUrl(0, "�����⿨�̻�����ҳ��", MANAGE_HOME + "?reqCode=10501&target=page");
        				prompt.setButtonUrl(1, "ת��������̻�����ҳ��", "/ToucsMonitor/posmerchantconfig?reqCode=10401&target=page&merchant_id="+mer_id);
        				request.setAttribute("prompt",prompt);
        				tarJsp="/Success.jsp";
        			} else {
        				POSFCardMerchantInfo p2 = new POSFCardMerchantInfo();
        				p2.setMer_id(p1.getMerchantid());
        				p2.setMer_name(p1.getMctname());
        				p2.setMer_eg(p1.getMctenname());
        				p2.setDev_bankno(p1.getOrgid());
        				p2.setMan_bankno(p1.getManagebankno());
        				p2.setMer_addr(p1.getAddress());
        				p2.setMer_zip(p1.getZipcode());
        				p2.setMer_tel(p1.getTelephone());
        				p2.setManager_a(p1.getArtificialperson());
        				p2.setManager_b(p1.getHandleman());
        				p2.setOp_id(p1.getOperator());
        				request.setAttribute("PosMercant", p2);
        			}
        		} catch (MonitorException ex) {
        			ex.printStackTrace();
        			System.out.println(ex.getMessage());
        		}
        	}
       } else{
            POSFCardMerchantInfo posFCardMerchantInfo=new POSFCardMerchantInfo();
            //��Http������ȡ�̻�������Ϣ
            posFCardMerchantInfo.fetchData(request);
            try{
                posFCardMerchant.addPosMerchant(posFCardMerchantInfo);
                //��¼��־
                writeLog(login,"�̻�["+posFCardMerchantInfo.getMer_id()+"]��ӳɹ�");
                //ת�ɹ���ʾҳ��
                PromptBean prompt=new PromptBean("�̻�����");
                prompt.setPrompt("�̻���ӳɹ�!");
                prompt.setButtonUrl(0,"��������",MANAGE_HOME);
                request.setAttribute("prompt",prompt);
                tarJsp="/Success.jsp";
            }catch(MonitorException exp){
                //��¼��־
                writeLog(login,"�̻����ʧ��");
                errProc(request,response,exp);
            }
        }
        //תĿ��ҳ��
        Debug.println(tarJsp);
        toPage(request,response,tarJsp);
    }

   /**
    * �̻���ѯ������
    * @param request Http����
    * @param response Http��Ӧ
    * @param login �û���Ϣ
    * @throws ServletException
    * @throws IOException
    */
    private void posFCardMerchantQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  LoginInfo login) throws  ServletException, IOException{
        //ȡ�̻����
        String merchantId=request.getParameter("merchant_id");
        System.out.println("merchant_id:"+merchantId);
        if(merchantId!=null&&!merchantId.equals("")){
            String tarJsp=null;
            try{
                //��ѯ�̻���ϸ��Ϣ
                POSFCardMerchantInfo posFCardMerchantInfo=posFCardMerchant.qryPosMerchant(merchantId);
                if(posFCardMerchantInfo==null)
                    throw new MonitorException(ErrorDefine.RECNOTFOUND,"�̻������ڣ�");
                initMerchantAddList(request,login.getOrgID());
                tarJsp=PAGE_HOME+"FCardPosMerchantInfo.jsp?";
                request.setAttribute("PosMercant",posFCardMerchantInfo);
                toPage(request,response,tarJsp);
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }else{
            try{
                List l = posFCardMerchant.getMerchantList();
                request.setAttribute("posFCardMerchantList",l);
                setPagePerm(request,login.getPermission());
                toPage(request,response,PAGE_HOME+"PosMerchantManage.jsp?uid=" + login.getUserID());
            }catch(MonitorException exp){
                errProc(request,response,exp);
            }
        }
    }

    /**
     * �̻���ѯ������
     * @param request Http����
     * @param response Http��Ӧ
     * @param login �û���Ϣ
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantQueryPage(HttpServletRequest request,
                                      HttpServletResponse response,
                                      LoginInfo login) throws  ServletException, IOException{
    	Debug.println("Target:where");
        initMerchantQueryList(request , "110000000");
        toPage(request,response,PAGE_HOME+"FCardPosMerchantInfo.jsp?post="+POSFCARDMERCHANTQUERY+"&uid="+login.getUserID());
    }

   /**
    * �̻��޸Ĳ�ѯ������
    * @param request Http����
    * @param response Http��Ӧ
    * @param login �û���Ϣ
    * @throws ServletException
    * @throws IOException
    */
    private void posFCardMerchantUpdateQuery(HttpServletRequest request,
                                        HttpServletResponse response,
                                        boolean isGetPage,
                                        LoginInfo login) throws  ServletException, IOException{
        //ȡ�̻����
        String mer_id=request.getParameter("merchant_id");
        System.out.println("mer_id:"+mer_id);
        //ȡ��ѯ���ݣ�������Ϣ��
        if (!isGetPage){
            if(mer_id!=null){
                String tarJsp=null;
                //��ѯ�̻�������Ϣ
                try{
                    //��ѯ�̻�������Ϣ
                    POSFCardMerchantInfo posFCardMerchantInfo=posFCardMerchant.qryPosMerchant(mer_id);
                    if(posFCardMerchantInfo==null)
                        throw new MonitorException(ErrorDefine.RECNOTFOUND,"�̻������ڣ�");
                    initMerchantAddList(request,login.getOrgID());
                    tarJsp=PAGE_HOME+"FCardPosMerChantUpdate.jsp?post="+POSFCARDMERCHANTUPDATE;
                    System.out.println("��ѯ���޸ĵ����ݣ�ָ��"+tarJsp);
                    request.setAttribute("PosMercant",posFCardMerchantInfo);
                    toPage(request,response,tarJsp);
                }catch(MonitorException exp){
                    errProc(request,response,exp);
                }
            }
        } else{
            initMerchantQueryList(request , "110000000");
            toPage(request,response,PAGE_HOME+"PosMerchantUpdateQuery.jsp?"+"&uid="+login.getUserID());
        }
    }

    /**
     * �޸��̻�������Ϣ
     * @param request Http����
     * @param response Http��Ӧ
     * @param isGet Get�����־
     * @param login �û���Ϣ
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantUpdate(HttpServletRequest request,
                                   HttpServletResponse response,LoginInfo login) throws ServletException, IOException{
        POSFCardMerchantInfo info=new POSFCardMerchantInfo();
        //������������ȡ��Ҫ�޸ĵ��̻�������Ϣ
        info.fetchData(request);
        //��ȡ����
        String mer_id=request.getParameter("mer_id");
        try{
            //�޸��̻�������Ϣ
            posFCardMerchant.updatePosMerchant(info,mer_id);
            //��¼��־
            writeLog(login,"�̻�["+mer_id+"]��Ϣ�޸ĳɹ�");
            //ת�ɹ���ʾҳ��
            PromptBean prompt=new PromptBean("�̻�����");
            prompt.setPrompt("�̻���Ϣ�޸ĳɹ���");
            prompt.setButtonUrl(0,"��������",MANAGE_HOME);
            request.setAttribute("prompt",prompt);
            toPage(request,response,"/Success.jsp?uid="+login.getUserID());
        }catch(MonitorException exp){
            //��¼��־
            writeLog(login,"�̻�["+mer_id+"]��Ϣ�޸ĳɹ�");
            errProc(request,response,exp);
        }
    }


    /**
     * �޸��̻�������Ϣ
     * @param request Http����
     * @param response Http��Ӧ
     * @param isGet Get�����־
     * @param login �û���Ϣ
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantDelete(HttpServletRequest request,
                                   HttpServletResponse response,LoginInfo login) throws ServletException, IOException{
        POSFCardMerchantInfo info=new POSFCardMerchantInfo();
        //������������ȡ��Ҫ�޸ĵ��̻�������Ϣ
        info.fetchData(request);
        String key=request.getParameter("merchant_id");
        try{
            //�޸��̻�������Ϣ
            int nRst = posFCardMerchant.deletePosMerchant(key);
            PromptBean prompt=new PromptBean("�̻�����");
            if (nRst == -1){
                prompt.setPrompt("�̻���Ϣ��POS�豸��Ϣ��ɾ�����ɹ���");
            }
            if (nRst == 0){
                prompt.setPrompt("�̻������־Ϊ��������״̬��ɾ�����ɹ���");
                System.out.println("�̻������־Ϊ��������״̬��ɾ�����ɹ���");
            }
            if (nRst>0){
                //��¼��־
                writeLog(login,"�⿨�̻�["+key+"]ɾ���ɹ�");
                //ת�ɹ���ʾҳ��
                prompt.setPrompt("�⿨�̻���Ϣɾ���ɹ���");
            }
            //��ҪУ���û��Ƿ��м���Ȩ��
            prompt.setButtonUrl(0,"��������",MANAGE_HOME);
            request.setAttribute("prompt",prompt);
            toPage(request,response,"/Success.jsp?uid="+login.getUserID());
        }catch(MonitorException exp){
            //��¼��־
            writeLog(login,"�⿨�̻�["+key+"]ɾ��ʧ��");
            errProc(request,response,exp);
        }
    }
}
