package com.adtec.toucs.monitor.POSDeviceManage;




/**
 * <p>Title:POS���������� </p>
 * <p>Description: �洢ĳ̨POS�Ķ���ĳ�����׵�����</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSTxnInfo {

    public POSTxnInfo() {
    }
    //����Ȩ��λ
    private int location=0;
    //������
    private String txn_name="";
    //�������� 0�������� 1������������ҽ��� 2����������ҽ��� 3�������������Ҳ������ҽ���
    private char txn_perm;
    //�������� 0�� ���������� 1���������������2��������������� 3�������������Ҳ�����������
    private char hand_perm;

    public void setLocation(int sn){ location=sn; }
    public int getLocation(){ return location; }

    public void setTxnName(String name){ txn_name=name; }
    public String getTxnName(){ return txn_name; }

    public void setTxnPerm(char txnPerm){ txn_perm=txnPerm; }
    public char getTxnPerm(){  return txn_perm; }

    public void setHandPerm(char handPerm){ hand_perm=handPerm; }
    public char getHandPerm(){ return hand_perm; }

}
