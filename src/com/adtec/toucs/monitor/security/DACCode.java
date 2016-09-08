/**
 * Title:        SysManage
 * Description:  The System Manage Project.
 * Copyright:    Copyright (c) 2001
 * Company:      Adtec
 * @author:      �ƾ�
 * @Date         2001-10-23
 * @version 1.0
 */

package com.adtec.toucs.monitor.security;

import java.util.*;

/*
  ��DES�����㷨����DACУ����
*/
public class DACCode {

	//DES������
	private DESCryptography DES;

	public DACCode() {
		//ʵ����DES������
		DES = new DESCryptography();
	}


  /* �����÷��� */
/*  public static void main(String[] args){
    CommonManager newx = new CommonManager();
    DACCode dac = new DACCode();

    com.adtec.gd.common.db.dbtest test = new com.adtec.gd.common.db.dbtest();

    String tablename = "D_seqinfo";
    Vector tableinfo = test.getrecord(tablename);
    //Debug.println(tableinfo.toString());
    int len = tableinfo.size();

    for (int i = 0; i < len ; i++) {
      Debug.println("�����"+tablename+"���DAC��  "+dac.getDAC((Vector)tableinfo.get(i),tablename));
      //Debug.println("ԭ��¼"+((Vector)tableinfo.get(i)).toString());
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

	  /** ��� ����Ա�����(M_Oper) DACУ����
	   *  �����ֶ�: C_OrgNo,C_OperNo,C_OperName,C_FunCode
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
	

	  /** ��� ����Աְ������Ȩ�ޱ�M_OperFunExam�� DACУ����
	   *  �����ֶ�: C_OperNo,C_FunCode,C_LoanCode,I_ExamAmt
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


	  /** ��� ְ�����������M_FunLoanExam�� DACУ����
	   *  �����ֶ�: C_FunCode,C_LoanCode,I_ExamAmt
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


	  /** ��� ְ��ϵͳ����Ȩ�޷����M_Authority�� DACУ����
	    * �����ֶ�: C_FunCode,C_SuperFun,C_ModRight
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


   /** ��� �����������Ϣ��(I_Quantum) DACУ����
    *  �����ֶ�: C_CompCode,F_QuanAmt,F_UsedAmt,F_MaxLoanScale
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


	/** ��� ����������Ϣ��D_SEQINFO�� DACУ����
	 *  �����ֶ�:
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

  /** ��� ���������D_COMMENT�� DACУ����
   *  �����ֶ�:
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


  /** ��� ���������P_Rate�� DACУ����
   *  �����ֶ�: S_Serial,F_Rate
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


  /** ��� ��¼��ַ��P_IP�� DACУ����
   *  �����ֶ�: C_IP,C_MAC
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


  /** ��� ϵͳ������־��M_SysLog�� DACУ����
   *  �����ֶ�: C_OrgNo,C_OperNo,C_OperType
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


  /** ��� ��Կ��P_Key�� DACУ����
   *  �����ֶ�: S_Serial,C_Key,C_Flag
  **/
	public String getPKeyDAC(Vector aRecord) {
		if (aRecord == null)
			return null;
		String str = ((String)aRecord.get(1)).trim()+((String)aRecord.get(3)).trim();
		String enmess = DES.digest(str);
		return enmess;
	}

}