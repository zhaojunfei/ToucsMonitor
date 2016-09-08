package com.adtec.toucs.monitor.systemmanage;

import java.io.Serializable;
import java.sql.*;
import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;

/**
 * <p>Title: 交易代码类</p>
 * <p>Description:封装交易代码信息 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ADTec </p>
 * @author liyp
 * @version 1.0
 */

public class TxnCodeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//设备类型代码：(都是小写)
	private String sys_id="";
	//交易码编号
	private String txn_code="";
	//交易名称
	private String txn_name="";
	//交易掩码位置
	private int location=0;
	//是否手输交易属性，0表示没有此属性
	private String hand_flag="";
	//冲正交易属性， 0：非冲正交易；1：冲正交易
	private String reverse_flag="";
	//是否插入流水交易属性， 1：是 0：否
	private String insert_flag="";
	//交易状态1：有效 0：无效
	//private String validflag;
	//借贷方标志，1为借方，0为贷方
	private String dc_flag="";
	//应用属性1， 对于pos，表示是否进行票据检查，0：否 1：是
	private String app_flag1="0";
	//应用属性2：对于pos有效，0:普通交易 1:预授权交易
	private String app_flag2="0";

	//设备类型属性读写
	public String getAppFlag2(){
		return app_flag2;
	}
	public void setAppFlag2(String flag){
		app_flag2=flag;
	}


	  /**
	   * 构造方法
	   */
	public TxnCodeBean() {
	}

	//设备类型属性读写
	public String getDeviceType(){
		return sys_id;
	}
	public void setDeviceType(String type){
		sys_id=type;
	}

	//交易代码属性读写
	public String getTxnCode(){
		return txn_code;
	}
	public void setTxnCode(String txnCode){
		txn_code=txnCode;
	}

	//交易名称属性读写
	public String getTxnName(){
		return txn_name;
	}
	public void setTxnName(String txnName){
		txn_name=txnName;
	}

	//是否手输交易属性读写
	public String getHandFlag(){
		return hand_flag;
	}	
	public void setHandFlag(String handFlag){
		hand_flag=handFlag;
	}

	//交易掩码位置属性读写
	public int getMaskSerial(){
		return location;
	}
	public void setMaskSerial(int serial){
		location=serial;
	}

	//冲正交易属性读写
	public String getReverseFlag(){
		return reverse_flag;
	}
	public void setReverseFlag(String reverseFlag){
		reverse_flag=reverseFlag;
	}

	//冲正交易属性读写
	public String getInsertFlag(){
		return insert_flag;
	}
	public void setInsertFlag(String insertFlag){
		insert_flag=insertFlag;
	}

	//应用属性1读写
	public String getAppFlag1(){
		return app_flag1;
	}
	public void setAppFlag1(String appFlag){
		app_flag1=appFlag;
	}

	//借贷方标志读写
	public String getDCFlag(){
		return dc_flag;
	}
	public void setDCFlag(String dcFlag){
		dc_flag=dcFlag;
	}	


  /**
   * 从查询结果中取代码信息
   * @param rst 查询结果集
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException{
          setDeviceType(rst.getString("sys_id").trim());
          setTxnCode(rst.getString("txn_code").trim());
          setTxnName(toucsString.unConvert(rst.getString("txn_name").trim()));
          setHandFlag(rst.getString("hand_flag").trim());
          setInsertFlag(rst.getString("insert_flag").trim());
          setMaskSerial(rst.getInt("location"));
          setReverseFlag(rst.getString("reverse_flag").trim());
          setAppFlag1(rst.getString("app_flag1").trim());
          setAppFlag2(rst.getString("app_flag2").trim());
          setDCFlag(rst.getString("dc_flag").trim());
  }
   /**
    * 从Http请求中取CDM基本信息
    * @param request Http请求
    */
    public void fetchData(HttpServletRequest request){
    	setDeviceType(request.getParameter("deviceType"));
    	setTxnCode(request.getParameter("txn_code"));
    	setTxnName(request.getParameter("txn_name"));
    	setHandFlag(request.getParameter("hand_flag"));
    	setInsertFlag(request.getParameter("insert_flag"));
    	setReverseFlag(request.getParameter("reverse_flag"));
    	setDCFlag(request.getParameter("dc_flag"));
    	if( sys_id.equals("pos") ){
    		setAppFlag1(request.getParameter("app_flag1"));
    		setAppFlag2(request.getParameter("app_flag2"));
    	}

    }
	  /**
	   * 添加代码
	   * @param sq 数据库访问对象
	   * @return 影响的记录数
	   * @throws SQLException
	   */
    public int insert(SqlAccess sq) throws SQLException{
         //计算掩码位置
        String sql="SELECT MAX(location) FROM kernel_txn_code WHERE sys_id='"+sys_id+"'";
        ResultSet rs=sq.queryselect(sql);
        while(rs.next()){
                location = rs.getInt(1);
        }
        rs.close();
        location++;
        sql="INSERT INTO kernel_txn_code(sys_id,txn_code,txn_name,location,hand_flag,dc_flag,"+
           "reverse_flag,insert_flag,app_flag1,app_flag2,app_flag3,flag1,flag2,flag3,valid_flag) "+
           "VALUES('"+sys_id+"','"+txn_code+"','"+txn_name+"',"+location+",'"+hand_flag+"','"+dc_flag+
           "','"+reverse_flag+"','"+insert_flag+"','"+app_flag1+"','"+app_flag2+"','0','0','0','0','1')";
        return sq.queryupdate(sql);
  }

	  /**
	   * 修改代码
	   * @param sq 数据库访问对象
	   * @param id 代码
	   * @return 影响的记录数
	   * @throws SQLException
	   */
    public int update(SqlAccess sq) throws SQLException{
    	String sql="UPDATE kernel_txn_code SET txn_name=?,hand_flag=?,dc_flag=?,reverse_flag=?,"+
               "insert_flag=?,app_flag1=?,app_flag2=?"+
               "WHERE sys_id=? AND txn_code=?";
    	PreparedStatement stm=sq.conn.prepareStatement(sql);
    	stm.setString(1,txn_name);
    	stm.setString(2,hand_flag);
    	stm.setString(3,dc_flag);
    	stm.setString(4,reverse_flag);
    	stm.setString(5,insert_flag);
    	stm.setString(6,app_flag1);
    	stm.setString(7,app_flag2);
    	stm.setString(8,sys_id);
    	stm.setString(9,txn_code);

        Debug.println(sql);
        return stm.executeUpdate();
  }

	  /**
	   * 转换为XML格式报文，填充到字符串缓冲对象中
	   * @param buf 字符串缓冲对象
	   */
    public void toXML(StringBuffer buf){
    	buf.append("<TxnCodeInfo  type=\""+sys_id+"\" code=\""+txn_code+"\">\n");
    	buf.append("<Name>"+txn_name+"</Name>\n");
    	buf.append("</TxnCodeInfo >\n");
    }

  /**
   * 转换为XML格式的报文
   * @return 转换后的报文
   */
    public String toXML(){
    	StringBuffer buf=new StringBuffer();
    	toXML(buf);
    	return buf.toString();
    }

    public static void main(String[] args) {
    }
}
