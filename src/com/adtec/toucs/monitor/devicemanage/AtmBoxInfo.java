package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ATM����������</p>
 * <p>Description:��װ����������Ϣ��ʵ�����ݿ�����ӡ�ɾ������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmBoxInfo {
	//�豸���
	private String atmCode;
	//�����ֵ
	private float[] boxPara;
	//�������
	private String[] boxCode;
	//��������
	private int[] boxCnt;
	//Ӳ�ҳ����ֵ
	private float[] coinPara;
	//Ӳ�ҳ�������
	private int[] coinCnt;
	//�ϳ�������
	private int feeBox;

	//ȡ����ܱ���
	private int totalCwdNum;
	//ȡ����ܽ��
	private float totalCwdAmt;
	//Ȧ�潻���ܱ���
	private int totalGdtNum;
	//Ȧ�潻���ܽ��
	private float totalGdtAmt;

	//�豸������Զ�д
	public String getAtmCode(){
		return atmCode;
	}
	public void setAtmCode(String code){
		atmCode=code;
	}

	//�����ֵ���Զ�д
	public float getBoxPara(int idx){
		return boxPara[idx];
	}
	public void setBoxPara(int idx,float para){
		boxPara[idx]=para;
	}

	//����������Զ�д
	public String getBoxCode(int idx){
		return boxCode[idx];
	}
	public void setBoxCode(int idx,String code){
		boxCode[idx]=code;
	}

	//�����������Զ�д
	public int getBoxCnt(int idx){
		return boxCnt[idx];
	}
	public void setBoxCnt(int idx,int cnt){
		boxCnt[idx]=cnt;
	}

	//Ӳ�ҳ����ֵ���Զ�д
	public float getCoinPara(int idx){
		return coinPara[idx];
	}
	public void setCoinPara(int idx,float para){
		coinPara[idx]=para;
	}

	//Ӳ�ҳ����������Զ�д
	public int getCoinCnt(int idx){
		return coinCnt[idx];
	}
	public void setCoinCnt(int idx,int cnt){
		coinCnt[idx]=cnt;
	}

	//ȡ����ܱ������Զ�д
	public int getTotalCwdNum(){
		return totalCwdNum;
	}
	public void setTotalCwdNum(int num){
		totalCwdNum=num;
	}

	//ȡ����ܽ�����Զ�д
	public float getTotalCwdAmt(){
		return totalCwdAmt;
	}
	public void setTotalCwdAtm(float amt){
		totalCwdAmt=amt;
	}

	//Ȧ�潻���ܱ������Զ�д
	public int getTotalGdtNum(){
		return totalGdtNum;
	}
	public void setTotalGdtNum(int num){
		totalGdtNum=num;
	}

	//Ȧ�潻���ܽ�����Զ�д
	public float totalGdtAmt(){
		return totalGdtAmt;
	}
	public void setGdtAmt(float amt){
		totalGdtAmt=amt;
	}

	//ȡ����5~8���ñ�־
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
   * ȡ�������
   * @param idx �����ţ���0��ʼ��
   * @return �������
   */
	public float getBoxAmt(int idx){
		return boxPara[idx]*boxCnt[idx];
	}

  /**
   * ���췽��
   */
	public AtmBoxInfo(){
		this("");
	}

  /**
   * ���췽��
   * @param code �豸���
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
   * ������ӳ���������Ϣ�Ķ�̬SQL������
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
		String sql="INSERT INTO atm_box_config(atmCode) VALUES(?)";
		PreparedStatement stm=conn.prepareStatement(sql);

		stm.setString(1,atmCode);

		return stm;
	}

  /**
   * �����޸ĳ���������Ϣ�Ķ�̬SQL������
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
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
   * �Ӳ�ѯ�����ȡ����������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchConfigData(ResultSet rst) throws SQLException{
		for(int i=1;i<=8;i++){
			boxPara[i-1]=rst.getFloat("box"+i+"_para");
			boxCode[i-1]=Util.getString(rst,"box"+i+"_code","");
		}
	}

  /**
   * �Ӳ�ѯ�������ȡ����״̬��Ϣ
   * @param rst ��ѯ�����
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
   * ��Http������ȡ����������Ϣ
   * @param request Http�������
   */
	public void fetchConfigData(HttpServletRequest request){
		atmCode=request.getParameter("atmCode");
		//ȡ1~4�ų�������
		for(int i=1;i<=4;i++){
			boxCode[i-1]=request.getParameter("box"+i+"Code");
			boxPara[i-1]=Util.getFloatPara(request,"box"+i+"Para",0);
		}
		//ȡ5~8�ų�������
		String para=request.getParameter("box5_8");
		if(para!=null&&para.equals("used")){
			for(int i=5;i<=8;i++){
				boxCode[i-1]=request.getParameter("box"+i+"Code");
				boxPara[i-1]=Util.getFloatPara(request,"box"+i+"Para",0);
			}
		}
	}

  /**
   * ȡ�豸������ر�־
   * @return ������ط���0�����򷵻�2
   */
	public int getUseFlag(){
		for(int i=0;i<8;i++){
			if(boxCode[i].equals("")&&boxPara[i]>0)
				return 2;
		}
		return 0;
	}

  /**
   * У�鳮��������Ϣ���ж��Ƿ��������
   * @param sq ���ݿ���ʶ���
   * @return ��������������������򷵻�true
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
   * ת��ΪXML��ʽ����
   * @return
   */
	public String toXML(){
		return "<AtmBoxInfo atmCode="+atmCode+"></AtmBoxInfo>";
	}

  /**
   * ����toString()����
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

	//��ֵ�б�
	public static float[] valueList={0,10,20,50,100,500,1000};	
}