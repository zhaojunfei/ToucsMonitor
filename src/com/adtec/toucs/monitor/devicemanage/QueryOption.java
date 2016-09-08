package com.adtec.toucs.monitor.devicemanage;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:查询选项类 </p>
 * <p>Description:提供与设备有关的查询设置，包括查询要素的设置、查询条件的形成等功能。 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class QueryOption implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//设备编号
	private String atmCode;
	//机构编码
	private String orgId;
	//设备类型
	private String atmType;
	//设备安装地点类型
	private String atmAddrType;
	//设备状态
	private String atmStat;
	//查询目标代码
	private String qryTarget;
	//机构列表
	private List orgList;

	//构造方法
	public QueryOption() {
	}

	//设备编号属性读写
	public void setAtmCode(String code){
		atmCode=code;
	}
	public String getAtmCode(){
		return atmCode;
	}

	//机构编码属性读写
	public void setOrgId(String id){
		orgId=id;
	}
	public String getOrgId(){
		return orgId;
	}

	//设备类型属性读写
	public void setAtmType(String type){
		atmType=type;
	}
	public String getAtmType(){
		return atmType;
	}

	//设备安装地点类型属性读写
	public void setAtmAddrType(String type){
		atmAddrType=type;
	}
	public String getAtmAddrType(){
		return atmAddrType;
	}

	//设备状态属性读写
	public void setAtmStat(String stat){
		atmStat=stat;
	}
	public String getAtmStat(){
		return atmStat;
	}

	//查询目标代码属性读写
	public void setQryTarget(String target){
		qryTarget=target;
	}
	public String getQryTarget(){
		return qryTarget;
	}

	//机构列表属性读写
	public void setOrgList(List list){
		orgList=list;
	}
	public List getOrgList(){
		return orgList;
	}

	  /**
	   * 组织查询条件
	   * @return 查询条件SQL语句
	   */
	public String genCondition(){
		//需要根据查询要素的设置组织查询条件
		//查询设备编号、设备类型、机构编号、加载标志、设备状态
		String sql="SELECT atm_code,atm_type,atm_code,org_id,use_flag,atm_stat FROM atm_info";
		String s="";

		if(atmCode!=null&&atmCode.trim().length()>0)
			s+="atm_code='"+atmCode+"'";
		else
			s+="atm_code LIKE 'A%'";

		if(atmType!=null&&!atmType.equals("")){
			s+=" AND atm_type='"+atmType+"'";
		}

		if(atmAddrType!=null&&!atmAddrType.equals("")){
			s+=" AND atm_addr_type='"+atmAddrType+"'";
		}

		//确定所能查询的机构范围(需要查询子机构指引表)
		//机构编号为本级机构或下属所有机构
		s+=" AND (org_id='"+orgId+"' OR org_id IN(SELECT org_id FROM monit_orgref WHERE p_org_id='"+orgId+"'))";
		sql+=" WHERE "+s;
		return sql;
	}

	  /**
	   * 覆盖的toString()方法
	   * @return
	   */
	public String toString(){
		return atmCode+"|"+atmType+"|"+atmAddrType+"|"+orgId+"|"+atmStat;
	}

	  /**
	   * 从Http请求中取查询条件
	   * @param request Http请求
	   */
	public void fetchData(HttpServletRequest request){
		atmCode=Util.getStrPara(request,"atmCode","");
		orgId=Util.getStrPara(request,"orgId","");
		atmType=Util.getStrPara(request,"atmType","");
		atmAddrType=Util.getStrPara(request,"atmAddrType","");
	}

	public static void main(String[] args) {
	}
}

