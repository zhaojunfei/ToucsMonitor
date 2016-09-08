package com.adtec.toucs.monitor.devicemanage;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title:��ѯѡ���� </p>
 * <p>Description:�ṩ���豸�йصĲ�ѯ���ã�������ѯҪ�ص����á���ѯ�������γɵȹ��ܡ� </p>
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
	//�豸���
	private String atmCode;
	//��������
	private String orgId;
	//�豸����
	private String atmType;
	//�豸��װ�ص�����
	private String atmAddrType;
	//�豸״̬
	private String atmStat;
	//��ѯĿ�����
	private String qryTarget;
	//�����б�
	private List orgList;

	//���췽��
	public QueryOption() {
	}

	//�豸������Զ�д
	public void setAtmCode(String code){
		atmCode=code;
	}
	public String getAtmCode(){
		return atmCode;
	}

	//�����������Զ�д
	public void setOrgId(String id){
		orgId=id;
	}
	public String getOrgId(){
		return orgId;
	}

	//�豸�������Զ�д
	public void setAtmType(String type){
		atmType=type;
	}
	public String getAtmType(){
		return atmType;
	}

	//�豸��װ�ص��������Զ�д
	public void setAtmAddrType(String type){
		atmAddrType=type;
	}
	public String getAtmAddrType(){
		return atmAddrType;
	}

	//�豸״̬���Զ�д
	public void setAtmStat(String stat){
		atmStat=stat;
	}
	public String getAtmStat(){
		return atmStat;
	}

	//��ѯĿ��������Զ�д
	public void setQryTarget(String target){
		qryTarget=target;
	}
	public String getQryTarget(){
		return qryTarget;
	}

	//�����б����Զ�д
	public void setOrgList(List list){
		orgList=list;
	}
	public List getOrgList(){
		return orgList;
	}

	  /**
	   * ��֯��ѯ����
	   * @return ��ѯ����SQL���
	   */
	public String genCondition(){
		//��Ҫ���ݲ�ѯҪ�ص�������֯��ѯ����
		//��ѯ�豸��š��豸���͡�������š����ر�־���豸״̬
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

		//ȷ�����ܲ�ѯ�Ļ�����Χ(��Ҫ��ѯ�ӻ���ָ����)
		//�������Ϊ�����������������л���
		s+=" AND (org_id='"+orgId+"' OR org_id IN(SELECT org_id FROM monit_orgref WHERE p_org_id='"+orgId+"'))";
		sql+=" WHERE "+s;
		return sql;
	}

	  /**
	   * ���ǵ�toString()����
	   * @return
	   */
	public String toString(){
		return atmCode+"|"+atmType+"|"+atmAddrType+"|"+orgId+"|"+atmStat;
	}

	  /**
	   * ��Http������ȡ��ѯ����
	   * @param request Http����
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

