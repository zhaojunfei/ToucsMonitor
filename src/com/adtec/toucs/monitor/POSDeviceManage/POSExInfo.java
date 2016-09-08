package com.adtec.toucs.monitor.POSDeviceManage;



import java.sql.*;
import com.adtec.toucs.monitor.common.*;


/**
 * <p>��չpos��Ϣ </p>
 * <p>��Ҫ����pos�豸��ѯ�еĶ��б����� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class POSExInfo {

    public POSExInfo() {
    }

    //POS���
    private String  pos_code;
    //POS-DCC���
    private String  posdcc_code;
    //�̻���
    private String  merchant_id;
    //״̬
    private String  pos_stat;
    //���ڸ�������Ӧ�÷�Χ
    private String mct_name = "";
    //������
    private String org_name = "";

    //�������󸽼�Ӧ�ò���
    //�̻����ƶ�д
    public void setMctname(String Mctname){
        mct_name=Mctname;
    }
    public String getMctname(){
        return mct_name;
    }

    //�����Ŷ�д
    public void setOrgname(String Orgname){
        org_name=Orgname;
    }
    public String getOrgname(){
        return org_name;
    }

    //POS������Զ�д
    public void setPoscode(String poscode){
      pos_code=poscode;
    }
    public String getPoscode(){
      return pos_code;
    }

    //dcc ���
    public void setdcccode(String posdcccode){
      posdcc_code=posdcccode;
    }
    public String getdcccode(){
      return posdcc_code;
    }


    //POS�����̻���д
    public void setMerchantid(String Merchartid){
      merchant_id=Merchartid;
    }
    public String getMerchantid(){
      return merchant_id;
    }

    //�̻�״̬��д
    public void setPosstat(String Posstat){
      pos_stat=Posstat;
    }
    public String getPosstat(){
      return pos_stat;
    }


    /**
     * �Ӳ�ѯ�����ȡATM������Ϣ
     * @param rst ��ѯ�����
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
     * �������ڸ����̻���ѯ�Ķ�̬SQL���
     * @param conn ���ݿ����Ӷ���
     * @param key �豸��ţ��ؼ��֣�
     * @return ��̬SQL������
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