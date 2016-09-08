package com.adtec.toucs.monitor.POSDeviceManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.adtec.toucs.monitor.common.PromptBean;

public class POSBCCardAction extends DispatchAction{
		
	/**
	 * <p>Title: 理财pos卡卡表信息文件上传</p>
	 * <p>Description: 用来接受客户请求，并调用相应处理方法处理</p>
	 * <p>Copyright: Copyright (c) 2013</p>
	 * <p>Company: ADTEC</p>
	 * @author syl*/	
	 
	public ActionForward uploadFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		POSBCCardForm posBCCardForm = (POSBCCardForm)form;
		POSBCCardBean posBCCardBean = new POSBCCardBean();
		ActionForward forward = new ActionForward();
		FormFile file = posBCCardForm.getFile();
		
		//检查Session是否有效
		HttpSession session = request.getSession();
		if(session != null){	
			session.invalidate();
		}		
		//创建一个新的Session
		session = request.getSession(true);
		
		//服务器 存放文件地址
		String path= "D:/load/";
		
		FileOutputStream os = new FileOutputStream(new File (path+file.getFileName()));
		os.write(file.getFileData());
		os.flush();
		os.close();
		
		//解析excel文件
		List list = posBCCardBean.recJXL(path+file.getFileName());
		PromptBean prompt = new PromptBean("理财POS卡表管理");
		List list2 = new ArrayList();
	    int num = 0;
    		//遍历集合	 
		/*or(int i=0;i<list.size();i++){
				POSBCCardInfo posBCCard = (POSBCCardInfo)list.get(i);
				//查询数据库,判断集合中的数据是否已经存在
				List list3 = posBCCardBean.QueryCardType(posBCCard);
				if(list3.size()==0){
					//将数据添加到数据库中
					posBCCardBean.addCardType(posBCCard);
				}else{
					//将错误的数据放到集合中
					//System.out.println("文件中出现重复的卡bin"+posBCCard.getcardMatch());
					list2.add(posBCCard);
					num++;
				}
			} 	*/
			
		if(list2.size()==0){	
			 prompt.setPrompt("excel文件操作成功！");
			 request.setAttribute("prompt", prompt);
			 forward = mapping.findForward("success");
		}else{
			 prompt.setPrompt("上传文件中有"+num+"条卡bin信息已经存在，请仔细核对上传文件！");
			 request.setAttribute("prompt", prompt);
			 forward = mapping.findForward("failure");
		}
		return forward;
	}
		
		
	/**
	 * <p>Title: 理财pos卡bin信息文件模板下载</p>
	 * <p>Description: 用来接受客户请求，并调用相应处理方法处理</p>
	 * <p>Copyright: Copyright (c) 2013</p>
	 * <p>Company: ADTEC</p>
	 * @author syl
	 */
	public ActionForward loadFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//下载文件信息模板所在路径
		String path = "D:/CCB/pos_lc_card.xls";
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		
		File uploadFile = new File(path);
		fis = new FileInputStream(uploadFile);
		
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		
		response.setHeader("Content-disposition" , "attachment;filename="+URLEncoder.encode("pos_lc_card.xls" , "GBK"));
		
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while((bytesRead = bis.read(buffer, 0, 8192))!=-1){
			bos.write(buffer, 0, bytesRead);
		}
		
		fos.flush();
		bos.flush();
		fis.close();
		bis.close();
		fos.close();
		bos.close();
		
		return null;
	}
		
}
