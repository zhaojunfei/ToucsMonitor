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
	//用户ID
	private String user_id = "";
	//用户名称
	private String user_name = "";
	//机构编码
	private String org_id = "";
	//描述信息
	private String user_desc = "";
	//雇佣日期
	private String employ_date = "";
	//解雇日期
	private String fire_date = "";
	//用户状态
	private String user_state = "";

	/**
	 * 构造方法
	 */
	public userInfo() {
	}

  /**
   * 设置用户ID
   * @param userId 用户ID
   */
	public void setUserId(String userId){
		this.user_id = userId;
	}

  /**
   * 取得用户ID
   * @return 用户ID
   */
	public String getUserId(){
		return this.user_id;
	}

  /**
   * 设置用户名称
   * @param userName 用户名称
   */
	public void setUserName(String userName){
		this.user_name = userName;
	}

  /**
   * 取得用户名称
   * @return 用户名称
   */
	public String getUserName(){
		return this.user_name;
	}

  /**
   * 设置机构编码
   * @param orgId 机构编码
   */
	public void setOrgId(String orgId){
		this.org_id = orgId;
	}

  /**
   * 取得机构编码
   * @return 机构编码
   */
	public String getOrgId(){
		return this.org_id;
	}

  /**
   * 设置用户描述信息
   * @param userDesc 描述信息
   */
	public void setUserDesc(String userDesc){
		this.user_desc = userDesc;
	}

  /**
   * 取得用户描述信息
   * @return 描述信息
   */
	public String getUserDesc(){
		return this.user_desc;
	}

  /**
   * 设置雇佣时间
   * @param EmployDate 雇佣时间
   */
	public void setEmployDate(String EmployDate){
		this.employ_date = EmployDate;
	}

  /**
   * 取得雇佣时间
   * @return 雇佣时间
   */
	public String getEmployDate(){
		return this.employ_date;
	}

  /**
   * 设置解雇时间
   * @param FireDate 解雇时间
   */
	public void setFireDate(String FireDate){
		this.fire_date = FireDate;
	}

  /**
   * 取得解雇时间
   * @return 解雇时间
   */
	public String getFireDate(){
		return this.fire_date;
	}

  /**
   * 设置用户状态
   * @param userState 用户状态
   */
	public void setUserState(String userState){
		this.user_state = userState;
	}

  /**
   * 取得用户状态
   * @return 用户状态
   */
	public String getUserState(){
		return this.user_state;
	}
}