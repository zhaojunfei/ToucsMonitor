package com.adtec.toucs.monitor.POSDeviceManage;



import java.sql.*;
import com.adtec.toucs.monitor.common.*;


/**
 * <p>扩展pos信息 </p>
 * <p>主要用于pos设备查询中的多列表的情况 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class POSExInfo {

    public POSExInfo() {
    }

    //POS编号
    private String  pos_code;
    //POS-DCC编号
    private String  posdcc_code;
    //商户号
    private String  merchant_id;
    //状态
    private String  pos_stat;
    //属于根据需求应用范围
    private String mct_name = "";
    //机构号
    private String org_name = "";

    //根据需求附加应用部分
    //商户名称读写
    public void setMctname(String Mctname){
        mct_name=Mctname;
    }
    public String getMctname(){
        return mct_name;
    }

    //机构号读写
    public void setOrgname(String Orgname){
        org_name=Orgname;
    }
    public String getOrgname(){
        return org_name;
    }

    //POS编号属性读写
    public void setPoscode(String poscode){
      pos_code=poscode;
    }
    public String getPoscode(){
      return pos_code;
    }

    //dcc 编号
    public void setdcccode(String posdcccode){
      posdcc_code=posdcccode;
    }
    public String getdcccode(){
      return posdcc_code;
    }


    //POS所属商户读写
    public void setMerchantid(String Merchartid){
      merchant_id=Merchartid;
    }
    public String getMerchantid(){
      return merchant_id;
    }

    //商户状态读写
    public void setPosstat(String Posstat){
      pos_stat=Posstat;
    }
    public String getPosstat(){
      return pos_stat;
    }


    /**
     * 从查询结果中取ATM基本信息
     * @param rst 查询结果集
     * @throws SQLException
     */
    public void fetchData(ResultSet rst) throws SQLException{
        setPoscode(toucsString.unConvert(rst.getString("pos_code")));
        setdcccode(toucsString.unConvert(rst.getString("pos_dcc_code")));
        setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
        setPosstat(toucsString.unConvert(rst.getString("pos_stat")));
        setMctname(toucsString.unConvert(rst.getString("mct_name")));
        setOrgname(toucsString.unConvert(rst.getString("org_name")));
    }

    /**
     * 创建用于根据商户查询的动态SQL语句
     * @param conn 数据库连接对象
     * @param key 设备编号（关键字）
     * @return 动态SQL语句对象
     * @throws SQLException
     */
    public PreparedStatement makeQueryByMctStm(Connection conn,String key) throws SQLException{
        String sql="SELECT a.pos_code ,c.org_name , b.mct_name , a.pos_stat "
                  +"FROM pos_info a,pos_merchant b , monit_orginfo c"
                  +" WHERE (a.merchant_id=?)AND(a.merchant_id = b.merchant_id)"
                  +"AND(b.org_id = c.org_id)";
        System.out.println("POSINFO:"+sql);
        PreparedStatement stm=conn.prepareStatement(sql);

        stm.setString(1,key);
        System.out.println("POSINFO QUERY:"+key);
        return stm;
    }
}