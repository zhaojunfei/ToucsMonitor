package com.adtec.toucs.monitor.POSDeviceManage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.loginmanage.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title:POS商户管理Servlet类 </p>
 * <p>Description: 处理POS商户管理相关的Http请求</p>
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
     *交易码参数
     **/
    //交易码：获取商户新增
    private static final String POSFCARDMERCHANTADD = "10501";
    //交易码：商户修改
    private static final String POSFCARDMERCHANTUPDATE = "10502";
    //交易码：商户查询
    private static final String POSFCARDMERCHANTQUERY = "10504";
    //交易码：商户删除
    private static final String POSFCARDMERCHANTDELETE = "10503";

    //其他参数代码
    private static final String PARMGETPAGE = "page";
    private static final String PARMGETQUERYPAGE = "queryPage";

    /**
     *其他参数
     **/
    //POS商户管理根路径
    private static final String MANAGE_HOME="/ToucsMonitor/fcardmerchantconfig";
    //POS管理页面根路径
    private static final String PAGE_HOME="/POSDeviceManage/";
    //POS商户管理类实例
    private POSFCardMerchantBean  posFCardMerchant = new POSFCardMerchantBean();

    public void init() throws ServletException {
    }
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //取请求参数
        String reqCode=request.getParameter("reqCode");
        response.setHeader("Cache-Control","no-store");
        String target="";
        target = request.getParameter("target");
        //用户身份校验
        LoginInfo login=null;
        //权限控制
        try{
            //校验用户是否登录
            login=checkLogin(request,response);
            //校验用户操作权限
            LoginManageBean.operValidate(login, POSFCARDMERCHANTADD);
        }catch(MonitorException exp){
            errProc(request,response,exp);
            return;
        }
        //目标JSP页面
        String tarJsp=null;
        //商户管理请求
        if(reqCode==null){
            try{
                List l = posFCardMerchant.getMerchantList();
                request.setAttribute("posFCardMerchantList",l);
            }catch(MonitorException exp){
            	exp.printStackTrace();
            	System.out.println(exp.getMessage());
            }
            //要根据当前用户所拥有商户管理权限，以确定其所能执行的操作
            setPagePerm(request,login.getPermission());
            tarJsp=PAGE_HOME+"FCardPosMerchantManage.jsp?uid=" +login.getUserID();
            toPage(request,response,tarJsp);
        }else{
            //获取添加商户页面
            Debug.println("Rox not here");
            if(reqCode.equals(POSFCARDMERCHANTADD)){
                if ((target == null)||target.trim().equals("")){
                    posFCardMerchantAdd(request,response,false,login);
                }else if (target != null && target.equals(PARMGETPAGE)){
                    posFCardMerchantAdd(request,response,true,login);
                }
            }
            //获取修改商户页面
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
            }else if(reqCode.equals(POSFCARDMERCHANTQUERY)){//查询商户
            	Debug.println("Target:"+target);
                if ((target == null)||target.trim().equals("")){
                    posFCardMerchantQuery(request,response,login);
                }else{
                    if (target.equals(PARMGETPAGE)){
                        posFCardMerchantQueryPage(request,response,login);
                    }
                }
            }else if(reqCode.equals(POSFCARDMERCHANTDELETE)){//删除商户
                Debug.println("删除商户信息...POST请求");
                posFCardMerchantDelete(request,response,login);
            }
        }
    }
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        //取请求参数
        String reqCode=request.getParameter("reqCode");
        //取请求参数
        String target="";
        target = request.getParameter("target");
        //权限控制
        LoginInfo login=null;
        try{
            //校验用户是否登录
            login=checkLogin(request,response);
            //校验用户操作权限
            LoginManageBean.operValidate(login,POSFCARDMERCHANTADD);
        }catch(MonitorException exp){
            errProc(request,response,exp);
            return;
        }

        //取session信息
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
        //添加商户
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
        }else if(reqCode.equals(POSFCARDMERCHANTUPDATE)){//修改商户
            if ((target == null)||target.trim().equals("")){
                posFCardMerchantUpdate(request,response,login);
            }else{
                if (target.equals(PARMGETPAGE)){
                    posFCardMerchantUpdateQuery(request,response,true,login);
                }else if (target.equals(PARMGETQUERYPAGE)){
                    posFCardMerchantUpdateQuery(request,response,false,login);
                }
            }
        }else if(reqCode.equals(POSFCARDMERCHANTQUERY)){//查询商户
            if ((target == null)||target.trim().equals("")){
                posFCardMerchantQuery(request,response,login);
            }else{
                if (target.equals(PARMGETPAGE)){
                    posFCardMerchantQueryPage(request,response,login);
                }
            }
        }else if(reqCode.equals(POSFCARDMERCHANTDELETE)){ //删除商户
            posFCardMerchantDelete(request,response,login);
        }
    }
    //Clean up resources
    public void destroy() {
    }

    /**
     * 设置用户对页面的操作权限
     * @param req Http请求
     * @param maskCode 用户权限码
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
     * 初始化商户添加页面所需的列表信息
     * @param req Http请求
     * @param orgId 机构编码
     */
    private void initMerchantAddList(HttpServletRequest req,String orgId){
        Vector v;
        SqlAccess sq=null;
        try{
            sq=SqlAccess.createConn();
            //商户类型
            v=CodeManageBean.queryCodes("0553",sq);
            req.setAttribute("McttypeList",v);
            //使用标志
            v=CodeManageBean.queryCodes("0554",sq);
            req.setAttribute("FlagList",v);
            //清理标志
            v=CodeManageBean.queryCodes("0555",sq);
            req.setAttribute("ClearFlagList",v);

            //机构列表
            List list=CodeManageBean.queryOrgList(orgId);
            req.setAttribute("orgList",list);
            //卡中心列表
            List cardOrgList = null;
            if( list!=null ) {
            	cardOrgList = new ArrayList();
                for( int i=0 ; i<list.size() ; i++ ){
                	CodeBean code = (CodeBean)list.get(i);
                	//根据DCC机构编码规则过滤出银行卡中心
                	//363700卡中心；3600卡分部
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
     * 初始化商户添加页面所需的列表信息
     * @param req Http请求
     * @param orgId 机构编码
     */
    private void initMerchantQueryList(HttpServletRequest req,String orgId){
        SqlAccess sq=null;
        try{
            sq=SqlAccess.createConn();
            System.out.println("1");
            List list=CodeManageBean.queryOrgList(orgId);
            req.setAttribute("orgList",list);
            //支行（二级机构）列表
            if(list!=null){
                String[] branchs=new String[list.size()];
                //从查询得到的三级机构中取得其上级机构（二级支行）编号
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
     * 商户添加请求处理
     * @param request Http请求
     * @param response Http响应
     * @param isGet Get请求标志
     * @param login 用户信息
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantAdd(HttpServletRequest request,
                                HttpServletResponse response,
                                boolean isGetPage,LoginInfo login) throws ServletException, IOException{
    	Debug.println("Where is Rox" + (isGetPage ? "?" : "!"));
        String tarJsp=null;
        String mer_id = request.getParameter("mer_id");
        //商户添加页面请求
        if(isGetPage){
        	tarJsp=PAGE_HOME+"FCardPosMerChantAdd.jsp?post="+POSFCARDMERCHANTADD + "&uid="+login.getUserID();
        	if (mer_id != null && mer_id.length() > 0) {
        		tarJsp=PAGE_HOME+"FCardPosMerChantAdd.jsp?post="+POSFCARDMERCHANTADD+"&uid="+login.getUserID();
        		POSMerchantBean p = new POSMerchantBean();
        		try {
        			POSMerchantInfo p1 = p.qryPosMerchant(mer_id);
        			if (p1 == null){
        				//未找到人民币商户提示页面
        				PromptBean prompt=new PromptBean("外卡商户新增");
        				prompt.setPrompt("未找到人民币商户，请先录入人民币商户资料。");
        				prompt.setButtonUrl(0, "返回外卡商户新增页面", MANAGE_HOME + "?reqCode=10501&target=page");
        				prompt.setButtonUrl(1, "转到人民币商户新增页面", "/ToucsMonitor/posmerchantconfig?reqCode=10401&target=page&merchant_id="+mer_id);
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
            //从Http请求中取商户基本信息
            posFCardMerchantInfo.fetchData(request);
            try{
                posFCardMerchant.addPosMerchant(posFCardMerchantInfo);
                //记录日志
                writeLog(login,"商户["+posFCardMerchantInfo.getMer_id()+"]添加成功");
                //转成功提示页面
                PromptBean prompt=new PromptBean("商户管理");
                prompt.setPrompt("商户添加成功!");
                prompt.setButtonUrl(0,"结束配置",MANAGE_HOME);
                request.setAttribute("prompt",prompt);
                tarJsp="/Success.jsp";
            }catch(MonitorException exp){
                //记录日志
                writeLog(login,"商户添加失败");
                errProc(request,response,exp);
            }
        }
        //转目标页面
        Debug.println(tarJsp);
        toPage(request,response,tarJsp);
    }

   /**
    * 商户查询请求处理
    * @param request Http请求
    * @param response Http响应
    * @param login 用户信息
    * @throws ServletException
    * @throws IOException
    */
    private void posFCardMerchantQuery(HttpServletRequest request,
                                  HttpServletResponse response,
                                  LoginInfo login) throws  ServletException, IOException{
        //取商户编号
        String merchantId=request.getParameter("merchant_id");
        System.out.println("merchant_id:"+merchantId);
        if(merchantId!=null&&!merchantId.equals("")){
            String tarJsp=null;
            try{
                //查询商户详细信息
                POSFCardMerchantInfo posFCardMerchantInfo=posFCardMerchant.qryPosMerchant(merchantId);
                if(posFCardMerchantInfo==null)
                    throw new MonitorException(ErrorDefine.RECNOTFOUND,"商户不存在！");
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
     * 商户查询请求处理
     * @param request Http请求
     * @param response Http响应
     * @param login 用户信息
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
    * 商户修改查询请求处理
    * @param request Http请求
    * @param response Http响应
    * @param login 用户信息
    * @throws ServletException
    * @throws IOException
    */
    private void posFCardMerchantUpdateQuery(HttpServletRequest request,
                                        HttpServletResponse response,
                                        boolean isGetPage,
                                        LoginInfo login) throws  ServletException, IOException{
        //取商户编号
        String mer_id=request.getParameter("merchant_id");
        System.out.println("mer_id:"+mer_id);
        //取查询内容（基本信息）
        if (!isGetPage){
            if(mer_id!=null){
                String tarJsp=null;
                //查询商户基本信息
                try{
                    //查询商户基本信息
                    POSFCardMerchantInfo posFCardMerchantInfo=posFCardMerchant.qryPosMerchant(mer_id);
                    if(posFCardMerchantInfo==null)
                        throw new MonitorException(ErrorDefine.RECNOTFOUND,"商户不存在！");
                    initMerchantAddList(request,login.getOrgID());
                    tarJsp=PAGE_HOME+"FCardPosMerChantUpdate.jsp?post="+POSFCARDMERCHANTUPDATE;
                    System.out.println("查询待修改的数据，指向："+tarJsp);
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
     * 修改商户基本信息
     * @param request Http请求
     * @param response Http响应
     * @param isGet Get请求标志
     * @param login 用户信息
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantUpdate(HttpServletRequest request,
                                   HttpServletResponse response,LoginInfo login) throws ServletException, IOException{
        POSFCardMerchantInfo info=new POSFCardMerchantInfo();
        //从请求数据中取得要修改的商户基本信息
        info.fetchData(request);
        //获取主键
        String mer_id=request.getParameter("mer_id");
        try{
            //修改商户基本信息
            posFCardMerchant.updatePosMerchant(info,mer_id);
            //记录日志
            writeLog(login,"商户["+mer_id+"]信息修改成功");
            //转成功提示页面
            PromptBean prompt=new PromptBean("商户管理");
            prompt.setPrompt("商户信息修改成功！");
            prompt.setButtonUrl(0,"结束配置",MANAGE_HOME);
            request.setAttribute("prompt",prompt);
            toPage(request,response,"/Success.jsp?uid="+login.getUserID());
        }catch(MonitorException exp){
            //记录日志
            writeLog(login,"商户["+mer_id+"]信息修改成功");
            errProc(request,response,exp);
        }
    }


    /**
     * 修改商户基本信息
     * @param request Http请求
     * @param response Http响应
     * @param isGet Get请求标志
     * @param login 用户信息
     * @throws ServletException
     * @throws IOException
     */
    private void posFCardMerchantDelete(HttpServletRequest request,
                                   HttpServletResponse response,LoginInfo login) throws ServletException, IOException{
        POSFCardMerchantInfo info=new POSFCardMerchantInfo();
        //从请求数据中取得要修改的商户基本信息
        info.fetchData(request);
        String key=request.getParameter("merchant_id");
        try{
            //修改商户基本信息
            int nRst = posFCardMerchant.deletePosMerchant(key);
            PromptBean prompt=new PromptBean("商户管理");
            if (nRst == -1){
                prompt.setPrompt("商户信息下POS设备信息，删除不成功！");
            }
            if (nRst == 0){
                prompt.setPrompt("商户清理标志为‘正常’状态，删除不成功！");
                System.out.println("商户清理标志为‘正常’状态，删除不成功！");
            }
            if (nRst>0){
                //记录日志
                writeLog(login,"外卡商户["+key+"]删除成功");
                //转成功提示页面
                prompt.setPrompt("外卡商户信息删除成功！");
            }
            //需要校验用户是否有加载权限
            prompt.setButtonUrl(0,"结束配置",MANAGE_HOME);
            request.setAttribute("prompt",prompt);
            toPage(request,response,"/Success.jsp?uid="+login.getUserID());
        }catch(MonitorException exp){
            //记录日志
            writeLog(login,"外卡商户["+key+"]删除失败");
            errProc(request,response,exp);
        }
    }
}
