package com.adtec.toucs.monitor.POSDeviceManage;




/**
 * <p>Title:POS交易设置类 </p>
 * <p>Description: 存储某台POS的对于某个交易的设置</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSTxnInfo {

    public POSTxnInfo() {
    }
    //交易权限位
    private int location=0;
    //交易名
    private String txn_name="";
    //交易设置 0－不允许 1－允许做人民币交易 2－允许做外币交易 3－既允许人民币也允许外币交易
    private char txn_perm;
    //手输设置 0－ 不允许手输 1－允许人民币手输2－允许做外币手输 3－既允许人民币也允许外币手输
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
