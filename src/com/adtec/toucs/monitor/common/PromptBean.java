package com.adtec.toucs.monitor.common;

/**
 * <p>Title:成功提示类 </p>
 * <p>Description: 用于提示的javaBean</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PromptBean {
	//常量：返回前页面
	public static final String RETURN="javascript:history.back()";
	//常量：返回主页
	public static final String CLOSE="javascript:location.href='/ToucsMonitor/'";
	//页面按纽个数
	private static final int BUTTONSZ=5;
	//提示标题
	private String title="ATM设备监控系统";
	//提示信息
	private String prompt="";
	//按纽标签
	private String[] btnLabel;
	//按纽执行的目标
	private String[] btnTarget;
	//为了使用POST方法
	private  String[]  btnURL;

	private  String  buttonMethod="GET";
  /**
   * 构造方法
   */
	public PromptBean(){
		this("ATM设备监控");
	}

  /**
   * 构造方法
   * @param tt 提示标题
   */
	public PromptBean(String tt){
		title=tt;
		prompt="";
		btnLabel=new String[BUTTONSZ];
		btnTarget=new String[BUTTONSZ];
		btnURL = new String[BUTTONSZ];
	}

  /**
   * 取提示标题
   * @return 提示标题
   */
	public String getTitle(){
		return title;
	}

  /**
   * 设置提示信息
   * @param pmt 提示信息
   */
	public void setPrompt(String pmt){
		prompt=pmt;
	}

  /**
   * 取提示信息
   * @return 提示信息
   */
	public String getPrompt(){
		return prompt;
	}

  /**
   * 设置按纽属性
   * @param idx 按纽索引
   * @param label 按纽标签
   * @param target 按纽目标URL
   */
	public void setButtonUrl(int idx,String label,String target){
		if(idx>=0&&idx<BUTTONSZ){
			btnLabel[idx]=label;
			btnTarget[idx]="javascript:location.href='"+target+"'";
			//绝对地址
			btnURL[idx]= target;
		}
	}

  /**
   * 设置按纽动作
   * @param idx 按纽索引
   * @param action 按纽动作
   */
	public void setButtonAction(int idx,String action){
		if(idx>=0&&idx<BUTTONSZ){
			if(action.equals(RETURN)){
				btnLabel[idx]="返回";
				btnTarget[idx]=RETURN;
			}else if(action.equals(CLOSE)){
				btnLabel[idx]="关闭";
				btnTarget[idx]=CLOSE;
			}
		}
	}

  /**
   * 取按纽个数
   * @return 按纽个数
   */
	public int getBtnSize(){
		return BUTTONSZ;
	}

  /**
   * 取按纽标签
   * @param idx 按纽索引
   * @return 按纽标签
   */
	public String getBtnLabel(int idx){
		return btnLabel[idx];
	}

  /**
   * 取按纽执行的目标URL
   * @param idx 按纽索引
   * @return 按纽执行的目标URL
   */
	public String getBtnTarget(int idx){
		return btnTarget[idx];
	}

  /**
   * 取得POST方法的URL
   * @param  idx 按纽索引
   * @return 按纽执行的目标URL
   */
	public String getBtnURL( int idx ){
		if( idx<0 || idx> BUTTONSZ )
			idx =0;
		return btnURL[idx];
	}

	public static void main(String[] args) {
	}
  /**
   * 设置按钮相应动作的请求方式
   * @param method
   * @return void
   */
	public  void setMethod(  String method ) {
		if( method.equalsIgnoreCase("POST") ){
			buttonMethod = "POST";
		}else
			buttonMethod = "GET";
		return;
	}
  /**
   * 设置按钮相应动作的请求方式
   * @param void
   * @return method
   */
	public   String getMethod( ){
		return  buttonMethod ;
	}
}