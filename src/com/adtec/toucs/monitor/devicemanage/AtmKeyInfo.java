package com.adtec.toucs.monitor.devicemanage;

import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 密钥配置信息类</p>
 * <p>Description: 封装ATM设备的密钥配置信息</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AtmKeyInfo{
    //设备编号
    private String atmCode="";
    //DES加密方式
    private char[] desType={' ',' ',' ',' '};
    //PINBLOCK格式
    private String pinblkMet="";
    //MAC算法
    private String macMet="";
    //传输主密钥长度
    private String cdkLen="";
    //个人PIN密钥长度
    private String pikLen="";
    //MAC密钥长度
    private String makLen="";

    //设备编号属性读写
    public String getAtmCode(){return atmCode;}
    public void setAtmCode(String code){atmCode=code;}

    //取DES加密方式
    public String getDesTypeStr(){
        return String.valueOf(desType);
    }
    //取指定密钥类型的DES加密方式
    public String getDesTypeStr(int keyType){
        return String.valueOf(getDesType(keyType));
    }

    //DES加密方式属性读写
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

    //PINBLOCK格式属性读写
    public String getPinblkMet(){return pinblkMet;}
    public void setPinblkMet(String met){pinblkMet=met;}

    //MAC算法属性读写
    public String getMacMet(){return macMet;}
    public void setMacMet(String met){macMet=met;}

    //传输主密钥长度属性读写
    public String getCdkLen(){return cdkLen;}
    public void setCdkLen(String len){cdkLen=len;}

    //个人PIN密钥长度属性读写
    public String getPikLen(){return pikLen;}
    public void setPikLen(String len){pikLen=len;}

    public String getMakLen(){return makLen;}
    public void setMakLen(String len){makLen=len;}

    /**
     * 从Http请求中取密钥配置信息
     * @param request Http请求对象
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
     * 从查询结果中取密钥配置信息
     * @param rst 查询结果集
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
     * 添加ATM密钥配置信息
     * @param sq 数据库访问对象
     * @return 成功返回1
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
     * 更新密钥配置信息
     * @param sq 数据库访问对象
     * @return 成功返回1
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
     * 校验密钥配置信息，判断是否允许加载
     * @param sq
     * @return 密钥配置满足加载条件则返回true
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
     * 覆盖的toString()方法
     * @return
     */
    public String toString(){
        return "["+atmCode+"]";
    }

    /**
     * 构造方法
     */
    public AtmKeyInfo() {

    }

    /**
     * 构造方法
     * @param code ATM编号
     */
    public AtmKeyInfo(String code){
        atmCode=code;
    }

    public static void main(String[] args) {
      
    }
}