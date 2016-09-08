package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ATM钞箱配置类</p>
 * <p>Description:封装钞箱配置信息，实现数据库的增加、删除操作</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmBoxInfo {
	//设备编号
	private String atmCode;
	//钞箱币值
	private float[] boxPara;
	//钞箱币种
	private String[] boxCode;
	//钞箱张数
	private int[] boxCnt;
	//硬币钞箱币值
	private float[] coinPara;
	//硬币钞箱张数
	private int[] coinCnt;
	//废钞箱张数
	private int feeBox;

	//取款交易总笔数
	private int totalCwdNum;
	//取款交易总金额
	private float totalCwdAmt;
	//圈存交易总笔数
	private int totalGdtNum;
	//圈存交易总金额
	private float totalGdtAmt;

	//设备编号属性读写
	public String getAtmCode(){
		return atmCode;
	}
	public void setAtmCode(String code){
		atmCode=code;
	}

	//钞箱币值属性读写
	public float getBoxPara(int idx){
		return boxPara[idx];
	}
	public void setBoxPara(int idx,float para){
		boxPara[idx]=para;
	}

	//钞箱币种属性读写
	public String getBoxCode(int idx){
		return boxCode[idx];
	}
	public void setBoxCode(int idx,String code){
		boxCode[idx]=code;
	}

	//钞箱张数属性读写
	public int getBoxCnt(int idx){
		return boxCnt[idx];
	}
	public void setBoxCnt(int idx,int cnt){
		boxCnt[idx]=cnt;
	}

	//硬币钞箱币值属性读写
	public float getCoinPara(int idx){
		return coinPara[idx];
	}
	public void setCoinPara(int idx,float para){
		coinPara[idx]=para;
	}

	//硬币钞箱张数属性读写
	public int getCoinCnt(int idx){
		return coinCnt[idx];
	}
	public void setCoinCnt(int idx,int cnt){
		coinCnt[idx]=cnt;
	}

	//取款交易总笔数属性读写
	public int getTotalCwdNum(){
		return totalCwdNum;
	}
	public void setTotalCwdNum(int num){
		totalCwdNum=num;
	}

	//取款交易总金额属性读写
	public float getTotalCwdAmt(){
		return totalCwdAmt;
	}
	public void setTotalCwdAtm(float amt){
		totalCwdAmt=amt;
	}

	//圈存交易总笔数属性读写
	public int getTotalGdtNum(){
		return totalGdtNum;
	}
	public void setTotalGdtNum(int num){
		totalGdtNum=num;
	}

	//圈存交易总金额属性读写
	public float totalGdtAmt(){
		return totalGdtAmt;
	}
	public void setGdtAmt(float amt){
		totalGdtAmt=amt;
	}

	//取钞箱5~8配置标志
	public boolean getFlagBox5_8(){
		boolean ret=false;
		for(int i=4;i<8;i++){
			if(!boxCode[i].equals("")&&boxPara[i]>0){
				ret=true;
				break;
			}
		}
		return ret;
	}

  /**
   * 取钞箱余额
   * @param idx 钞箱编号（从0开始）
   * @return 钞箱余额
   */
	public float getBoxAmt(int idx){
		return boxPara[idx]*boxCnt[idx];
	}

  /**
   * 构造方法
   */
	public AtmBoxInfo(){
		this("");
	}

  /**
   * 构造方法
   * @param code 设备编号
   */
	public AtmBoxInfo(String code) {
		atmCode=code;
		boxCode=new String[8];
		boxPara=new float[8];
		boxCnt=new int[8];
		coinPara=new float[4];
		coinCnt=new int[4];
		for(int i=0;i<8;i++){
			boxCode[i]="";
			boxPara[i]=0;
			boxCnt[i]=0;
		}
		for(int i=0;i<4;i++){
			coinPara[i]=0;
			coinCnt[i]=0;
		}
		totalCwdNum=0;
		totalCwdAmt=0;
		totalGdtNum=0;
		totalGdtAmt=0;
  }	
	
  /**
   * 生成添加钞箱配置信息的动态SQL语句对象
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
		String sql="INSERT INTO atm_box_config(atmCode) VALUES(?)";
		PreparedStatement stm=conn.prepareStatement(sql);

		stm.setString(1,atmCode);

		return stm;
	}

  /**
   * 生成修改钞箱配置信息的动态SQL语句对象
   * @param conn 数据库连接对象
   * @return 动态SQL语句对象
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn) throws SQLException{
		String sql="UPDATE atm_box_config SET box1_code=?,box2_code=?,box3_code=?,"
              	+"box4_code=?,box5_code=?,box6_code=?,box7_code=?,box8_code=?,"
              	+"box1_para=?,box2_para=?,box3_para=?,box4_para=?,"
              	+"box5_para=?,box6_para=?,box7_para=?,box8_para=?"
              	+" WHERE atm_code=?";
		PreparedStatement stm=conn.prepareStatement(sql);

		for(int i=0;i<8;i++){
			stm.setString(i+1,boxCode[i]);
			stm.setFloat(i+9,boxPara[i]);
		}	
		stm.setString(17,atmCode);
		return stm;
	}

  /**
   * 从查询结果中取钞箱配置信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchConfigData(ResultSet rst) throws SQLException{
		for(int i=1;i<=8;i++){
			boxPara[i-1]=rst.getFloat("box"+i+"_para");
			boxCode[i-1]=Util.getString(rst,"box"+i+"_code","");
		}
	}

  /**
   * 从查询结果集中取钞箱状态信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchStateData(ResultSet rst) throws SQLException{
		for(int i=1;i<8;i++){
			boxPara[i-1]=rst.getFloat("box"+i+"_para");
			boxCode[i-1]=Util.getString(rst,"box"+i+"_code","");
			boxCnt[i-1]=rst.getInt("box"+i+"_cnt");
		}
		feeBox=rst.getInt("fee_box");
		totalCwdNum=rst.getInt("total_cwd_num");
		totalCwdAmt=rst.getFloat("total_cwd_amt");
		totalGdtNum=rst.getInt("total_gdt_num");
		totalGdtAmt=rst.getFloat("total_gdt_amt");
	}

  /**
   * 从Http请求中取钞箱配置信息
   * @param request Http请求对象
   */
	public void fetchConfigData(HttpServletRequest request){
		atmCode=request.getParameter("atmCode");
		//取1~4号钞箱配置
		for(int i=1;i<=4;i++){
			boxCode[i-1]=request.getParameter("box"+i+"Code");
			boxPara[i-1]=Util.getFloatPara(request,"box"+i+"Para",0);
		}
		//取5~8号钞箱配置
		String para=request.getParameter("box5_8");
		if(para!=null&&para.equals("used")){
			for(int i=5;i<=8;i++){
				boxCode[i-1]=request.getParameter("box"+i+"Code");
				boxPara[i-1]=Util.getFloatPara(request,"box"+i+"Para",0);
			}
		}
	}

  /**
   * 取设备允许加载标志
   * @return 允许加载返回0，否则返回2
   */
	public int getUseFlag(){
		for(int i=0;i<8;i++){
			if(boxCode[i].equals("")&&boxPara[i]>0)
				return 2;
		}
		return 0;
	}

  /**
   * 校验钞箱配置信息，判断是否允许加载
   * @param sq 数据库访问对象
   * @return 钞箱配置满足加载条件则返回true
   */
	public boolean loadEnabled(SqlAccess sq) throws SQLException{
		boolean ret=true;
		ResultSet rst=sq.queryselect("SELECT * FROM atm_box_config WHERE atm_code='"+atmCode+"'");
		if(rst.next()){
			fetchConfigData(rst);
			ret=(getUseFlag()==0);
		}else
			ret=false;
		rst.close();
		return ret;
  }
	
  /**
   * 转换为XML格式报文
   * @return
   */
	public String toXML(){
		return "<AtmBoxInfo atmCode="+atmCode+"></AtmBoxInfo>";
	}

  /**
   * 覆盖toString()方法
   * @return ret
   */
	public String toString(){
		String ret="[AtmBoxInfo]"+atmCode;
		for(int i=0;i<8;i++){
			ret+="|"+boxCode[i];
			ret+=","+boxPara[i];
			ret+=","+boxCnt[i];
		}
		return ret;
	}

	public static void main(String[] args) {
	}

	//面值列表
	public static float[] valueList={0,10,20,50,100,500,1000};	
}