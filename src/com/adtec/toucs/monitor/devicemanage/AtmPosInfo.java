package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM安装位置信息类 </p>
 * <p>Description:封装ATM的安装位置信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmPosInfo {
	//ATM编号
	private String atmId;
	//X坐标
	private int atmPointX;
	//Y坐标
	private int atmPointY;
	//是否安装标志
	private String installFlag;

	//ATM编号属性读写
	public void setAtmId(String id){
		atmId=id;
	}
	public String getAtmId(){
		return atmId;
	}

	//X坐标属性读写
	public void setAtmPointX(int x){
		atmPointX=x;
	}
	public int getAtmPointX(){
		return atmPointX;
	}

	//Y坐标属性读写
	public void setAtmPointY(int y){
		atmPointY=y;
	}
	public int getAtmPointY(){
		return atmPointY;
	}

	//安装标志属性读写
	public void setInstallFlag(String flag){
		installFlag=flag;
	}
	public String getInstallFlag(){
		return installFlag;
	}

	  /**
	   * 覆盖的toString()方法
	   * @return
	   */
	public String toString(){
		String ret="[AtmPosInfo]";
		ret+=atmId+"|"+atmPointX+"|"+atmPointY+"|"+installFlag;
		return ret;
	}

	  /**
	   * 转换为XML格式报文
	   * @return XML格式报文
	   */
	public String toXML(){
		StringBuffer buf=new StringBuffer();
		toXML(buf);
		return buf.toString();
	}

	  /**
	   * 转换为XML格式报文，填充到字符串缓冲对象中
	   * @param buf 字符串缓冲对象
	   * @param withRoot 根节点标志
	   */
	public void toXML(StringBuffer buf,boolean withRoot){
		if(withRoot)
			buf.append("<AtmInfo id=\""+atmId+"\">\n");
		toXML(buf);
		if(withRoot)
			buf.append("</AtmInfo>\n");
	}

	  /**
	   * 转换为XML格式报文，填充到字符串缓冲对象中
	   * @param buf 字符串缓冲对象
	   */
	public void toXML(StringBuffer buf){
		buf.append("<Pos x=\""+atmPointX+"\" y=\""+atmPointY+"\"/>\n");
		buf.append("<InstallFlag>"+installFlag+"</InstallFlag>\n");
	}

	  /**
	   * 从查询结果中取数据
	   * @param rst 查询结果集
	   * @throws SQLException SQL异常
	   */
	public void fetchData(ResultSet rst) throws SQLException{
		atmId=Util.getString(rst,"atm_id","");
		atmPointX=rst.getInt("atm_point_x");
		atmPointY=rst.getInt("atm_point_y");
		installFlag=Util.getString(rst,"fix_flg","");
	}

	  /**
	   * 构造方法
	   */
	public AtmPosInfo() {
	}

	public static void main(String[] args) {
   
	}
}