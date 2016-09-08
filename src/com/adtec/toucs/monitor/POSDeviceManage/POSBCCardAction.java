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
	 * <p>Title: ���pos��������Ϣ�ļ��ϴ�</p>
	 * <p>Description: �������ܿͻ����󣬲�������Ӧ����������</p>
	 * <p>Copyright: Copyright (c) 2013</p>
	 * <p>Company: ADTEC</p>
	 * @author syl*/	
	 
	public ActionForward uploadFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		POSBCCardForm posBCCardForm = (POSBCCardForm)form;
		POSBCCardBean posBCCardBean = new POSBCCardBean();
		ActionForward forward = new ActionForward();
		FormFile file = posBCCardForm.getFile();
		
		//���Session�Ƿ���Ч
		HttpSession session = request.getSession();
		if(session != null){	
			session.invalidate();
		}		
		//����һ���µ�Session
		session = request.getSession(true);
		
		//������ ����ļ���ַ
		String path= "D:/load/";
		
		FileOutputStream os = new FileOutputStream(new File (path+file.getFileName()));
		os.write(file.getFileData());
		os.flush();
		os.close();
		
		//����excel�ļ�
		List list = posBCCardBean.recJXL(path+file.getFileName());
		PromptBean prompt = new PromptBean("���POS�������");
		List list2 = new ArrayList();
	    int num = 0;
    		//��������	 
		/*or(int i=0;i<list.size();i++){
				POSBCCardInfo posBCCard = (POSBCCardInfo)list.get(i);
				//��ѯ���ݿ�,�жϼ����е������Ƿ��Ѿ�����
				List list3 = posBCCardBean.QueryCardType(posBCCard);
				if(list3.size()==0){
					//��������ӵ����ݿ���
					posBCCardBean.addCardType(posBCCard);
				}else{
					//����������ݷŵ�������
					//System.out.println("�ļ��г����ظ��Ŀ�bin"+posBCCard.getcardMatch());
					list2.add(posBCCard);
					num++;
				}
			} 	*/
			
		if(list2.size()==0){	
			 prompt.setPrompt("excel�ļ������ɹ���");
			 request.setAttribute("prompt", prompt);
			 forward = mapping.findForward("success");
		}else{
			 prompt.setPrompt("�ϴ��ļ�����"+num+"����bin��Ϣ�Ѿ����ڣ�����ϸ�˶��ϴ��ļ���");
			 request.setAttribute("prompt", prompt);
			 forward = mapping.findForward("failure");
		}
		return forward;
	}
		
		
	/**
	 * <p>Title: ���pos��bin��Ϣ�ļ�ģ������</p>
	 * <p>Description: �������ܿͻ����󣬲�������Ӧ����������</p>
	 * <p>Copyright: Copyright (c) 2013</p>
	 * <p>Company: ADTEC</p>
	 * @author syl
	 */
	public ActionForward loadFile(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//�����ļ���Ϣģ������·��
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
