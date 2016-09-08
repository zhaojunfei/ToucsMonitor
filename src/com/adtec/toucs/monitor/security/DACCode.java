/**
 * Title:        SysManage
 * Description:  The System Manage Project.
 * Copyright:    Copyright (c) 2001
 * Company:      Adtec
 * @author:      唐君
 * @Date         2001-10-23
 * @version 1.0
 */

package com.adtec.toucs.monitor.security;

import java.util.*;

/*
  用DES加密算法生成DAC校验码
*/
public class DACCode {

	//DES加密类
	private DESCryptography DES;

	public DACCode() {
		//实例化DES加密类
		DES = new DESCryptography();
	}


  /* 测试用方法 */
/*  public static void main(String[] args){
    CommonManager newx = new CommonManager();
    DACCode dac = new DACCode();

    com.adtec.gd.common.db.dbtest test = new com.adtec.gd.common.db.dbtest();

    String tablename = "D_seqinfo";
    Vector tableinfo = test.getrecord(tablename);
    //Debug.println(tableinfo.toString());
    int len = tableinfo.size();

    for (int i = 0; i < len ; i++) {
      Debug.println("计算出"+tablename+"表的DAC码  "+dac.getDAC((Vector)tableinfo.get(i),tablename));
      //Debug.println("原记录"+((Vector)tableinfo.get(i)).toString());
    }

  }

*/
	public String getDAC(Vector aRecord,String tablename) {
		if (tablename.equalsIgnoreCase("M_oper"))
			return getMoperDAC(aRecord);

		if (tablename.equalsIgnoreCase("M_OperFunExam"))
			return getMOperFunExamDAC(aRecord);

		if (tablename.equalsIgnoreCase("M_FunLoanExam"))
			return getMFunLoanExamDAC(aRecord);

		if (tablename.equalsIgnoreCase("M_Authority"))
			return getMAuthorityDAC(aRecord);

		if (tablename.equalsIgnoreCase("I_Quantum"))
			return getIQuantumDAC(aRecord);

    //if (tablename.equalsIgnoreCase("D_Examine"))
      //return getDExamineDAC(aRecord);

    //if (tablename.equalsIgnoreCase("D_Examined"))
      //return getDExaminedDAC(aRecord);

		if (tablename.equalsIgnoreCase("D_SeqInfo"))
			return getDSeqinfoDAC(aRecord);
		
		if (tablename.equalsIgnoreCase("D_Comment"))
			return this.getDCommentDAC(aRecord);

		if (tablename.equalsIgnoreCase("P_Rate"))
			return getPRateDAC(aRecord);

		if (tablename.equalsIgnoreCase("P_IP"))
			return getPIPDAC(aRecord);

		if (tablename.equalsIgnoreCase("M_SysLog"))
			return getMSysLogDAC(aRecord);

		if (tablename.equalsIgnoreCase("P_Key"))
			return getPKeyDAC(aRecord);

		return "";
	}

	  /** 获得 操作员管理表(M_Oper) DAC校验码
	   *  计算字段: C_OrgNo,C_OperNo,C_OperName,C_FunCode
	   **/
	public String getMoperDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim()
					+((String)aRecord.get(2)).trim()+((String)aRecord.get(3)).trim()
					+((String)aRecord.get(6)).trim()+((String)aRecord.get(8)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}
	

	  /** 获得 操作员职务审批权限表（M_OperFunExam） DAC校验码
	   *  计算字段: C_OperNo,C_FunCode,C_LoanCode,I_ExamAmt
	   **/
	public String getMOperFunExamDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim()
					+((String)aRecord.get(2)).trim()+((String)aRecord.get(3)).trim()
					+((String)aRecord.get(4)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


	  /** 获得 职务贷种审批表（M_FunLoanExam） DAC校验码
	   *  计算字段: C_FunCode,C_LoanCode,I_ExamAmt
	   **/
	public String getMFunLoanExamDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim()
                 	+((String)aRecord.get(2)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


	  /** 获得 职务系统操作权限分配表（M_Authority） DAC校验码
	    * 计算字段: C_FunCode,C_SuperFun,C_ModRight
	    **/
	public String getMAuthorityDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(2)).trim()
                 	+((String)aRecord.get(3)).trim()+((String)aRecord.get(4)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


   /** 获得 合作方额度信息表(I_Quantum) DAC校验码
    *  计算字段: C_CompCode,F_QuanAmt,F_UsedAmt,F_MaxLoanScale
    **/
	public String getIQuantumDAC(Vector aRecord) {
		String tmp1 = "";
		String tmp2 = "";
		String tmp3 = "";
		if (aRecord == null)
			return null;
		tmp1 = (String)aRecord.get(2);
		tmp2 = (String)aRecord.get(3);
		tmp3 = (String)aRecord.get(15);

		if ((tmp1 == null)||(tmp1.length() == 0))
			tmp1 = "";
		if ((tmp2 == null)||(tmp2.length() == 0))
			tmp2 = "";
		if ((tmp3 == null)||(tmp3.length() == 0))
			tmp3 = "";

		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim() +tmp1+tmp2
                  +((String)aRecord.get(4)).trim()+((String)aRecord.get(5)).trim()
                  +((String)aRecord.get(12)).trim().substring(0,2)+((String)aRecord.get(13)).trim().substring(0,2)
                  +((String)aRecord.get(14)).trim().substring(0,2)+tmp3;

		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


	/** 获得 贷款申请信息表（D_SEQINFO） DAC校验码
	 *  计算字段:
    **/
	public String getDSeqinfoDAC(Vector aRecord) {
		String tmp1 = "";
		String tmp2 = "";
		String tmp3 = "";
		String tmp4 = "";
		if (aRecord == null)
			return null;
		tmp1 = (String)aRecord.get(22);
		tmp2 = (String)aRecord.get(23);
		tmp3 = (String)aRecord.get(24);
		tmp4 = (String)aRecord.get(19);

		if ((tmp1 == null)||(tmp1.length() == 0))
			tmp1 = "";
		if ((tmp2 == null)||(tmp2.length() == 0))
			tmp2 = "";
		if ((tmp3 == null)||(tmp3.length() == 0))
			tmp3 = "";
		if ((tmp4 == null)||(tmp4.length() == 0))
			tmp4 = "";

		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim()
                  	+((String)aRecord.get(4)).trim()+((String)aRecord.get(5)).trim()
                  	+((String)aRecord.get(6)).trim()+((String)aRecord.get(7)).trim()
                  	+((String)aRecord.get(8)).trim().substring(0,2)+((String)aRecord.get(9)).trim().substring(0,2)
                  	+((String)aRecord.get(17)).trim()+((String)aRecord.get(18)).trim()
                  	+tmp4+((String)aRecord.get(21)).trim()
                  	+tmp1+tmp2
                  	+tmp3+((String)aRecord.get(25)).trim()+((String)aRecord.get(26)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}

  /** 获得 审批意见表（D_COMMENT） DAC校验码
   *  计算字段:
  **/
	public String getDCommentDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(1)).trim()+((String)aRecord.get(2)).trim()
					+((String)aRecord.get(3)).trim()+((String)aRecord.get(4)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


  /** 获得 利率情况表（P_Rate） DAC校验码
   *  计算字段: S_Serial,F_Rate
  **/
	public String getPRateDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(1)).trim()+((String)aRecord.get(2)).trim()
					+((String)aRecord.get(3)).trim()+((String)aRecord.get(5)).trim().substring(0,2);
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


  /** 获得 登录地址表（P_IP） DAC校验码
   *  计算字段: C_IP,C_MAC
  **/
	public String getPIPDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(0)).trim()+((String)aRecord.get(1)).trim()
					+((String)aRecord.get(2)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


  /** 获得 系统操作日志表（M_SysLog） DAC校验码
   *  计算字段: C_OrgNo,C_OperNo,C_OperType
  **/
	public String getMSysLogDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(1)).trim()
					+((String)aRecord.get(2)).trim()+((String)aRecord.get(3)).trim();
		String enmess = DES.encrypt(str);
		enmess = DES.digest(enmess);
		return enmess;
	}


  /** 获得 密钥表（P_Key） DAC校验码
   *  计算字段: S_Serial,C_Key,C_Flag
  **/
	public String getPKeyDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(1)).trim()+((String)aRecord.get(3)).trim();
		String enmess = DES.digest(str);
		return enmess;
	}

}