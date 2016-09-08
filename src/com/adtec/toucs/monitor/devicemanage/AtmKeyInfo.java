package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: ��Կ������Ϣ��</p>
 * <p>Description: ��װATM�豸����Կ������Ϣ</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmKeyInfo{
    //�豸���
    private String atmCode="";
    //DES���ܷ�ʽ
    private char[] desType={' ',' ',' ',' '};
    //PINBLOCK��ʽ
    private String pinblkMet="";
    //MAC�㷨
    private String macMet="";
    //��������Կ����
    private String cdkLen="";
    //����PIN��Կ����
    private String pikLen="";
    //MAC��Կ����
    private String makLen="";

    //�豸������Զ�д
    public String getAtmCode(){return atmCode;}
    public void setAtmCode(String code){atmCode=code;}

    //ȡDES���ܷ�ʽ
    public String getDesTypeStr(){
        return String.valueOf(desType);
    }
    //ȡָ����Կ���͵�DES���ܷ�ʽ
    public String getDesTypeStr(int keyType){
        return String.valueOf(getDesType(keyType));
    }

    //DES���ܷ�ʽ���Զ�д
    public char getDesType(int keyType){
        if(keyType<0||keyType>3)
            return '\0';
        else
            return desType[keyType];
    }
    public void setDesType(int keyType,char type){
        if(keyType<0||keyType>3)
            return;
        else
            desType[keyType]=type;
    }

    //PINBLOCK��ʽ���Զ�д
    public String getPinblkMet(){return pinblkMet;}
    public void setPinblkMet(String met){pinblkMet=met;}

    //MAC�㷨���Զ�д
    public String getMacMet(){return macMet;}
    public void setMacMet(String met){macMet=met;}

    //��������Կ�������Զ�д
    public String getCdkLen(){return cdkLen;}
    public void setCdkLen(String len){cdkLen=len;}

    //����PIN��Կ�������Զ�д
    public String getPikLen(){return pikLen;}
    public void setPikLen(String len){pikLen=len;}

    public String getMakLen(){return makLen;}
    public void setMakLen(String len){makLen=len;}

    /**
     * ��Http������ȡ��Կ������Ϣ
     * @param request Http�������
     */
    public void fetchData(HttpServletRequest request){
        atmCode=request.getParameter("atmCode");
        String cdkDes=request.getParameter("CDKDes");
        String pikDes=request.getParameter("PIKDes");
        String makDes=request.getParameter("MAKDes");
        desType[0]=cdkDes.charAt(0);
        desType[1]=pikDes.charAt(0);
        desType[2]=makDes.charAt(0);
        pinblkMet=request.getParameter("pinblkMet");
        macMet=request.getParameter("macMet");
        cdkLen=request.getParameter("CDKLen");
        pikLen=request.getParameter("PIKLen");
        makLen=request.getParameter("MAKLen");
    }

    /**
     * �Ӳ�ѯ�����ȡ��Կ������Ϣ
     * @param rst ��ѯ�����
     * @throws SQLException
     */
    public void fetchData(ResultSet rst) throws SQLException{
        atmCode=Util.getString(rst,"sender_agency","");
        String desTypeStr=Util.getString(rst,"des_type","");
        for(int i=0;i<4;i++){
            if(i<desTypeStr.length())
                desType[i]=desTypeStr.charAt(i);
            else
                desType[i]='\0';
        }
        pinblkMet=Util.getString(rst,"pinblk_met","");
        macMet=Util.getString(rst,"mac_met","");
        cdkLen=Util.getString(rst,"cdk_len","");
        pikLen=Util.getString(rst,"pik_len","");
        makLen=Util.getString(rst,"mak_len","");
    }

    /**
     * ���ATM��Կ������Ϣ
     * @param sq ���ݿ���ʶ���
     * @return �ɹ�����1
     * @throws SQLException
     */
    public int insert(SqlAccess sq) throws SQLException{
        String sql="INSERT INTO kernel_key_info(sender_agency,des_type,pinblk_met,"
                  +"mac_met,cdk_len,pik_len,mak_len,pin_key,mac_key,"
                  +"old_pin_key,old_mac_key,trans_key,Old_trans_key,master_key,Work_key)"
                  +" VALUES(?,?,?,?,?,?,?,'','','','','','','','')";
        PreparedStatement stm=sq.conn.prepareStatement(sql);
        stm.setString(1,atmCode);
        stm.setString(2,getDesTypeStr());
        stm.setString(3,pinblkMet);
        stm.setString(4,macMet);
        stm.setString(5,cdkLen);
        stm.setString(6,pikLen);
        stm.setString(7,makLen);
        return stm.executeUpdate();
    }

    /**
     * ������Կ������Ϣ
     * @param sq ���ݿ���ʶ���
     * @return �ɹ�����1
     * @throws SQLException
     */
    public int update(SqlAccess sq) throws SQLException{
        String sql="UPDATE kernel_key_info SET des_type=?,pinblk_met=?,"
           +"mac_met=?,cdk_len=?,pik_len=?,mak_len=?"
              +" WHERE sender_agency=?";
        PreparedStatement stm=sq.conn.prepareStatement(sql);
        stm.setString(1,getDesTypeStr());
        stm.setString(2,pinblkMet);
        stm.setString(3,macMet);
        stm.setString(4,cdkLen);
        stm.setString(5,pikLen);
        stm.setString(6,makLen);
        stm.setString(7,atmCode);
        return stm.executeUpdate();
    }

    /**
     * У����Կ������Ϣ���ж��Ƿ��������
     * @param sq
     * @return ��Կ����������������򷵻�true
     * @throws SQLException
     */
    public boolean loadEnabled(SqlAccess sq) throws SQLException{
        boolean ret=false;
        ResultSet rst=sq.queryselect("SELECT * FROM kernel_key_info WHERE sender_agency='"+atmCode+"'");
        if(rst.next()){
            fetchData(rst);
            ret=true;
        }
        else
            ret=false;
        rst.close();
        return ret;
    }

    /**
     * ���ǵ�toString()����
     * @return
     */
    public String toString(){
        return "["+atmCode+"]";
    }

    /**
     * ���췽��
     */
    public AtmKeyInfo() {

    }

    /**
     * ���췽��
     * @param code ATM���
     */
    public AtmKeyInfo(String code){
        atmCode=code;
    }

    public static void main(String[] args) {
      
    }
}