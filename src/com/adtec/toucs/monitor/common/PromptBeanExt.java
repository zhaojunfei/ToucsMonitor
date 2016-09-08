package com.adtec.toucs.monitor.common;

/**
 * <p>Title:�ɹ���ʾ�� </p>
 * <p>Description: ������ʾ��javaBean</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PromptBeanExt {
	//����������ǰҳ��
	public static final String RETURN="javascript:history.back()";
	//������������ҳ
	public static final String CLOSE="javascript:location.href='/ToucsMonitor/'";
	//ҳ�水Ŧ����
	private static final int BUTTONSZ=5;
	//��ʾ����
	private String title="ATM�豸���ϵͳ";
	//��ʾ��Ϣ
	private String prompt="";
	//��Ŧ��ǩ
	private String[] btnLabel;
	//��Ŧִ�е�Ŀ��
	private String[] btnTarget;

  /**
   * ���췽��
   */
	public PromptBeanExt(){
		this("ATM�豸���");
	}

  /**
   * ���췽��
   * @param tt ��ʾ����
   */
	public PromptBeanExt(String tt){
		title=tt;
		prompt="";
		btnLabel=new String[BUTTONSZ];
		btnTarget=new String[BUTTONSZ];
	}

  /**
   * ȡ��ʾ����
   * @return ��ʾ����
   */
	public String getTitle(){
		return title;
	}

  /**
   * ������ʾ��Ϣ
   * @param pmt ��ʾ��Ϣ
   */
	public void setPrompt(String pmt){
		prompt=pmt;
	}

  /**
   * ȡ��ʾ��Ϣ
   * @return ��ʾ��Ϣ
   */
	public String getPrompt(){
		return prompt;
	}

  /**
   * ���ð�Ŧ����
   * @param idx ��Ŧ����
   * @param label ��Ŧ��ǩ
   * @param target ��ŦĿ��URL
   */
	public void setButtonUrl(int idx,String label,String target){
		if(idx>=0&&idx<BUTTONSZ){
			btnLabel[idx]=label;
			btnTarget[idx]=target;
		}
	}

  /**
   * ���ð�Ŧ����
   * @param idx ��Ŧ����
   * @param action ��Ŧ����
   */
	public void setButtonAction(int idx,String action){
		if(idx>=0&&idx<BUTTONSZ){
			if(action.equals(RETURN)){
				btnLabel[idx]="����";
				btnTarget[idx]=RETURN;
			}else if(action.equals(CLOSE)){
				btnLabel[idx]="�ر�";
				btnTarget[idx]=CLOSE;
			}
		}
	}

  /**
   * ȡ��Ŧ����
   * @return ��Ŧ����
   */
	public int getBtnSize(){
		return BUTTONSZ;
	}

  /**
   * ȡ��Ŧ��ǩ
   * @param idx ��Ŧ����
   * @return ��Ŧ��ǩ
   */
	public String getBtnLabel(int idx){
		return btnLabel[idx];
	}

  /**
   * ȡ��Ŧִ�е�Ŀ��URL
   * @param idx ��Ŧ����
   * @return ��Ŧִ�е�Ŀ��URL
   */
	public String getBtnTarget(int idx){
		Debug.println("INDEX:"+idx+" "+btnTarget[idx]);
		return btnTarget[idx];
	}

	public static void main(String[] args) {
		System.out.println("���Կ�ʼ.....");
	}
}