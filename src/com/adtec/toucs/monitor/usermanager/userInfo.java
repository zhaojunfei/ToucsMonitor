package com.adtec.toucs.monitor.usermanager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class userInfo {
	//�û�ID
	private String user_id = "";
	//�û�����
	private String user_name = "";
	//��������
	private String org_id = "";
	//������Ϣ
	private String user_desc = "";
	//��Ӷ����
	private String employ_date = "";
	//�������
	private String fire_date = "";
	//�û�״̬
	private String user_state = "";

	/**
	 * ���췽��
	 */
	public userInfo() {
	}

  /**
   * �����û�ID
   * @param userId �û�ID
   */
	public void setUserId(String userId){
		this.user_id = userId;
	}

  /**
   * ȡ���û�ID
   * @return �û�ID
   */
	public String getUserId(){
		return this.user_id;
	}

  /**
   * �����û�����
   * @param userName �û�����
   */
	public void setUserName(String userName){
		this.user_name = userName;
	}

  /**
   * ȡ���û�����
   * @return �û�����
   */
	public String getUserName(){
		return this.user_name;
	}

  /**
   * ���û�������
   * @param orgId ��������
   */
	public void setOrgId(String orgId){
		this.org_id = orgId;
	}

  /**
   * ȡ�û�������
   * @return ��������
   */
	public String getOrgId(){
		return this.org_id;
	}

  /**
   * �����û�������Ϣ
   * @param userDesc ������Ϣ
   */
	public void setUserDesc(String userDesc){
		this.user_desc = userDesc;
	}

  /**
   * ȡ���û�������Ϣ
   * @return ������Ϣ
   */
	public String getUserDesc(){
		return this.user_desc;
	}

  /**
   * ���ù�Ӷʱ��
   * @param EmployDate ��Ӷʱ��
   */
	public void setEmployDate(String EmployDate){
		this.employ_date = EmployDate;
	}

  /**
   * ȡ�ù�Ӷʱ��
   * @return ��Ӷʱ��
   */
	public String getEmployDate(){
		return this.employ_date;
	}

  /**
   * ���ý��ʱ��
   * @param FireDate ���ʱ��
   */
	public void setFireDate(String FireDate){
		this.fire_date = FireDate;
	}

  /**
   * ȡ�ý��ʱ��
   * @return ���ʱ��
   */
	public String getFireDate(){
		return this.fire_date;
	}

  /**
   * �����û�״̬
   * @param userState �û�״̬
   */
	public void setUserState(String userState){
		this.user_state = userState;
	}

  /**
   * ȡ���û�״̬
   * @return �û�״̬
   */
	public String getUserState(){
		return this.user_state;
	}
}