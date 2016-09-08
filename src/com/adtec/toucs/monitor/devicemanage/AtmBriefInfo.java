package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:ATM基本信息</p>
 * <p>Description:封装ATM设备基本信息（用于客户端ATM信息下载）</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmBriefInfo {
	//编号
	private String atmCode="";	
	//名称
	private String atmName="";
	//设备类型
	private String atmType="";
	//设备型号
	private String atmMode="";
	//机构编号
	private String orgId="";
	//管理员电话
	private String mngPhone="";
	//安装地点类型
	private String atmAddrType="";
	//上月平均提款额
	private float lastMmddAmount=0;
	//重要度
	private String atmLevel="";
	//设备状态
	private String atmStat="";

  /**
   * 取设备编号
   * @return 设备编号
   */
	public String getAtmCode(){
		return atmCode;
	}

  /**
   * 构造方法
   */
	public AtmBriefInfo() {
	}

  /**
   * 从查询结果中取设备基本信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
		atmCode=Util.getString(rst,"atm_code","");
		atmName=Util.getString(rst,"atm_name","");
		atmType=Util.getString(rst,"atm_type","");
		atmMode=Util.getString(rst,"atm_mode","");
		orgId=Util.getString(rst,"org_id","");
		mngPhone=Util.getString(rst,"mng_phone","");
		atmAddrType=Util.getString(rst,"atm_addr_type","");
		lastMmddAmount=rst.getFloat("last_mmdd_amount");
		atmLevel=Util.getString(rst,"atm_level","");
		atmStat=Util.getString(rst,"atm_stat","");
	}

  /**
   * 转换为XML格式报文，填充到字符串缓冲对象中
   * @param buf 字符串缓冲对象
   */
	public void toXML(StringBuffer buf){
		buf.append("<Name>"+atmName+"</Name>\n");
		buf.append("<OrgId>"+orgId+"</OrgId>\n");
		buf.append("<Type>"+atmType+"</Type>\n");
		buf.append("<Mode>"+atmMode+"</Mode>\n");
		buf.append("<Phone>"+mngPhone+"</Phone>\n");
		buf.append("<InstallType>"+atmAddrType+"</InstallType>\n");
    	buf.append("<LMPerDayDrawing>"+String.valueOf(lastMmddAmount)+"</LMPerDayDrawing>\n");
    	buf.append("<Stress>"+atmLevel+"</Stress>\n");
    	buf.append("<DeviceStat>"+atmStat+"</DeviceStat>\n");
	}
}