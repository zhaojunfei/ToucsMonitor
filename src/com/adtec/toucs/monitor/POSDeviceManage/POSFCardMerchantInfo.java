package com.adtec.toucs.monitor.POSDeviceManage;


import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class POSFCardMerchantInfo {

	//建行商户号
	private String mer_id = "";	
	//中文名称
	private String mer_name = "";	
	//英文名称
	private String mer_eg = "";
	//所在城市（拼音）
	private String mer_city = "";
	//地址
	private String mer_addr = "";
	//邮编
	private String mer_zip = "";
	//电话
	private String mer_tel = "";
	//公司网站地址
	private String mer_url = "";
	//商户类型码（MCC）
	private String mcc = "";
	//终端类型码（TCC）
	private String tcc = "";
	//法人代表
	private String manager_a = "";
	//法人代表电话
	private String tel_a = "";
	//经办人
	private String manager_b = "";
	//经办人电话
	private String tel_b = "";
	//发展行号
	private String dev_bankno = "";
	//管辖行号
	private String man_bankno = "";
	//默认JCB商户号
	private String mer_id_jcb = "";
	//默认Dinner商户号
	private String mer_id_dinner = "";
	//默认AE商户号
	private String mer_id_ae = "";
	//回扣率Visa
	private String rate_visa = "";
	//回扣率Master
	private String rate_master = "";
	//回扣率Jcb
	private String rate_jcb = "";
	//回扣率Dinner
	private String rate_dinner = "";
	//回扣率ae
	private String rate_ae = "";
	//返回率Visa
	private String rate_visa2 = "";
	//返回率Master
	private String rate_master2 = "";
	//返回率Jcb
	private String rate_jcb2 = "";
	//返回率Dinner
	private String rate_dinner2 = "";	
	//返回率ae
	private String rate_ae2 = "";
	//开通标志
	private String open_flag = "";
	//默认划款账号
	private String acct_no = "";
	//默认划款账号开户行
	private String acct_bankno = "";
	//默认划款帐户名称
	private String acct_name = "";
	//对帐单地址	
	private String bill_ck_addr = "";
	//对帐单邮编
	private String bill_ck_zip = "";
	//对帐单收件人
	private String bill_ck_rcv = "";
	//对帐单email
	private String bill_ck_email = "";
	//对帐单传真
	private String bill_ck_fax = "";
	//商户级别
	private String mer_level = "";
	//商户状态
	private String mer_state = "";
	//资金结算日期
	private String settle_date = "";
	//最后修改人
	private String last_mod_op = "";
	//最后修改时间
	private String last_mod_time = "";
	//录入员
	private String op_id = "";	
	//录入时间
	private String input_time = "";
	//标志位
	private String flag = "";
	public String getMer_id() {
		return mer_id;
	}

	public String getOpen_flag() {
		return open_flag;
	}

	public String getAcct_bankno() {
		return acct_bankno;
	}

	public String getInput_time() {
		return input_time;
	}

	public String getOp_id() {
		return op_id;
	}

	public String getRate_master() {
		return rate_master;
	}

	public String getMer_id_dinner() {
		return mer_id_dinner;
	}

	public String getMer_state() {
		return mer_state;
	}

	public String getLast_mod_time() {
		return last_mod_time;
	}

	public String getMer_id_ae() {
		return mer_id_ae;
	}

	public String getMer_eg() {
		return mer_eg;
	}

	public String getMer_city() {
		return mer_city;
	}

	public String getRate_visa() {
		return rate_visa;
	}

	public String getFlag() {
		return flag;
	}

	public String getRate_ae2() {
		return rate_ae2;
	}

	public String getRate_jcb2() {
		return rate_jcb2;
	}

	public String getRate_master2() {
		return rate_master2;
	}

	public String getDev_bankno() {
		return dev_bankno;
	}

	public String getRate_dinner() {
		return rate_dinner;
	}

	public String getMer_tel() {
		return mer_tel;
	}

	public String getBill_ck_rcv() {
		return bill_ck_rcv;
	}

	public String getRate_visa2() {
		return rate_visa2;
	}

	public String getAcct_no() {
		return acct_no;
	}

	public String getMer_level() {
		return mer_level;
	}

	public String getManager_a() {
		return manager_a;
	}

	public String getMer_zip() {
		return mer_zip;
	}

	public String getMan_bankno() {
		return man_bankno;
	}

	public String getMer_addr() {
		return mer_addr;
	}

	public String getBill_ck_zip() {
		return bill_ck_zip;
	}

	public String getTel_a() {
		return tel_a;
	}

	public String getBill_ck_addr() {
		return bill_ck_addr;
	}

	public String getSettle_date() {
		return settle_date;
	}

	public String getLast_mod_op() {
		return last_mod_op;
	}

	public String getBill_ck_email() {
		return bill_ck_email;
	}

	public String getBill_ck_fax() {
		return bill_ck_fax;
	}

	public String getMer_name() {
		return mer_name;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public String getMcc() {
		return mcc;
	}

	public String getTcc() {
		return tcc;
	}

	public String getTel_b() {
		return tel_b;
	}

	public String getRate_ae() {
		return rate_ae;
	}

	public String getMer_id_jcb() {
		return mer_id_jcb;
	}

	public String getManager_b() {
		return manager_b;
	}

	public String getRate_dinner2() {
		return rate_dinner2;
	}

	public String getRate_jcb() {
		return rate_jcb;
	}

	public void setMer_url(String mer_url) {
		this.mer_url = mer_url;
	}

	public void setMer_id(String mer_id) {
		this.mer_id = mer_id;
	}

	public void setOpen_flag(String open_flag) {
		this.open_flag = open_flag;
	}

	public void setAcct_bankno(String acct_bankno) {
		this.acct_bankno = acct_bankno;
	}

	public void setInput_time(String input_time) {
		this.input_time = input_time;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public void setRate_master(String rate_master) {
		this.rate_master = rate_master;
	}

	public void setMer_id_dinner(String mer_id_dinner) {
		this.mer_id_dinner = mer_id_dinner;
	}

	public void setMer_state(String mer_state) {
		this.mer_state = mer_state;
	}

	public void setLast_mod_time(String last_mod_time) {
		this.last_mod_time = last_mod_time;
	}

	public void setMer_id_ae(String mer_id_ae) {
		this.mer_id_ae = mer_id_ae;
	}

	public void setMer_eg(String mer_eg) {
		this.mer_eg = mer_eg;
	}

	public void setMer_city(String mer_city) {
		this.mer_city = mer_city;
	}

	public void setRate_visa(String rate_visa) {
		this.rate_visa = rate_visa;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setRate_ae2(String rate_ae2) {
		this.rate_ae2 = rate_ae2;
	}

	public void setRate_jcb2(String rate_jcb2) {
		this.rate_jcb2 = rate_jcb2;
	}

	public void setRate_master2(String rate_master2) {
		this.rate_master2 = rate_master2;
	}

	public void setDev_bankno(String dev_bankno) {
		this.dev_bankno = dev_bankno;
	}

	public void setRate_dinner(String rate_dinner) {
		this.rate_dinner = rate_dinner;
	}

	public void setMer_tel(String mer_tel) {
		this.mer_tel = mer_tel;
	}

	public void setBill_ck_rcv(String bill_ck_rcv) {
		this.bill_ck_rcv = bill_ck_rcv;
	}

	public void setRate_visa2(String rate_visa2) {
		this.rate_visa2 = rate_visa2;
	}

	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}

	public void setMer_level(String mer_level) {
		this.mer_level = mer_level;
	}

	public void setManager_a(String manager_a) {
		this.manager_a = manager_a;
	}

	public void setMer_zip(String mer_zip) {
		this.mer_zip = mer_zip;
	}

	public void setMan_bankno(String man_bankno) {
		this.man_bankno = man_bankno;
	}

	public void setMer_addr(String mer_addr) {
		this.mer_addr = mer_addr;
	}

	public void setBill_ck_zip(String bill_ck_zip) {
		this.bill_ck_zip = bill_ck_zip;
	}

	public void setTel_a(String tel_a) {
		this.tel_a = tel_a;
	}

	public void setBill_ck_addr(String bill_ck_addr) {
		this.bill_ck_addr = bill_ck_addr;
	}

	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}

	public void setLast_mod_op(String last_mod_op) {
		this.last_mod_op = last_mod_op;
	}

	public void setBill_ck_email(String bill_ck_email) {
		this.bill_ck_email = bill_ck_email;
	}
	
	public void setBill_ck_fax(String bill_ck_fax) {
		this.bill_ck_fax = bill_ck_fax;
	}

	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public void setTcc(String tcc) {
		this.tcc = tcc;
	}

	public void setTel_b(String tel_b) {
		this.tel_b = tel_b;
	}

	public void setRate_ae(String rate_ae) {
		this.rate_ae = rate_ae;
	}

	public void setMer_id_jcb(String mer_id_jcb) {
		this.mer_id_jcb = mer_id_jcb;
	}

	public void setManager_b(String manager_b) {
		this.manager_b = manager_b;
	}

	public void setRate_dinner2(String rate_dinner2) {
		this.rate_dinner2 = rate_dinner2;
	}

	public void setRate_jcb(String rate_jcb) {
		this.rate_jcb = rate_jcb;
	}

	public String getMer_url() {
		return mer_url;
	}

	public POSFCardMerchantInfo() {
	}


    /**
     * 从Http请求中取POSFCard基本信息
     * @param request Http请求
     */
    public void fetchData(HttpServletRequest request){
    	setMer_id(request.getParameter("mer_id"));
    	setMer_name(request.getParameter("mer_name"));
    	setMer_eg(request.getParameter("mer_eg"));
    	setMer_city(request.getParameter("mer_city"));
    	setMer_addr(request.getParameter("mer_addr"));
    	setMer_zip(request.getParameter("mer_zip"));
    	setMer_tel(request.getParameter("mer_tel"));
    	setMer_url(request.getParameter("mer_url"));
    	setMcc(request.getParameter("mcc"));
    	setTcc(request.getParameter("tcc"));
    	setManager_a(request.getParameter("manager_a"));
    	setTel_a(request.getParameter("tel_a"));
    	setManager_b(request.getParameter("manager_b"));
    	setTel_b(request.getParameter("tel_b"));
    	setDev_bankno(request.getParameter("dev_bankno"));
    	setMan_bankno(request.getParameter("man_bankno"));
    	setMer_id_jcb(request.getParameter("mer_id_jcb"));
    	setMer_id_dinner(request.getParameter("mer_id_dinner"));
    	setMer_id_ae(request.getParameter("mer_id_ae"));
    	setRate_visa(request.getParameter("rate_visa"));
    	setRate_master(request.getParameter("rate_master"));
    	setRate_jcb(request.getParameter("rate_jcb"));
    	setRate_dinner(request.getParameter("rate_dinner"));
    	setRate_ae(request.getParameter("rate_ae"));
    	setRate_visa2(request.getParameter("rate_visa2"));
    	setRate_master2(request.getParameter("rate_master2"));
    	setRate_jcb2(request.getParameter("rate_jcb2"));
    	setRate_dinner2(request.getParameter("rate_dinner2"));
    	setRate_ae2(request.getParameter("rate_ae2"));
    	setOpen_flag(request.getParameter("open_flag"));
    	setAcct_no(request.getParameter("acct_no"));
    	setAcct_bankno(request.getParameter("acct_bankno"));
    	setAcct_name(request.getParameter("acct_name"));
    	setBill_ck_addr(request.getParameter("bill_ck_addr"));
    	setBill_ck_zip(request.getParameter("bill_ck_zip"));
    	setBill_ck_rcv(request.getParameter("bill_ck_rcv"));
    	setBill_ck_email(request.getParameter("bill_ck_email"));
    	setBill_ck_fax(request.getParameter("bill_ck_fax"));
    	setMer_level(request.getParameter("mer_level"));
    	setMer_state(request.getParameter("mer_state"));
    	setSettle_date(request.getParameter("settle_date"));
    	setLast_mod_op(request.getParameter("last_mod_op"));
    	setLast_mod_time(request.getParameter("last_mod_time"));
    	setOp_id(request.getParameter("op_id"));
    	setInput_time(request.getParameter("input_time"));
    	setFlag(request.getParameter("flag"));
    }


    /**
     * 从查询结果中取POSFCard基本信息
     * @param rst 查询结果集
     * @throws SQLException
     */
    public void fetchData(ResultSet rst) throws SQLException{
    	setMer_id(toucsString.unConvert(rst.getString("mer_id")));
    	setMer_name(toucsString.unConvert(rst.getString("mer_name")));
      	setMer_eg(toucsString.unConvert(rst.getString("mer_eg")));
      	setMer_city(toucsString.unConvert(rst.getString("mer_city")));
      	setMer_addr(toucsString.unConvert(rst.getString("mer_addr")));
      	setMer_zip(toucsString.unConvert(rst.getString("mer_zip")));
      	setMer_tel(toucsString.unConvert(rst.getString("mer_tel")));
      	setMer_url(toucsString.unConvert(rst.getString("mer_url")));
      	setMcc(toucsString.unConvert(rst.getString("mcc")));
      	setTcc(toucsString.unConvert(rst.getString("tcc")));
      	setManager_a(toucsString.unConvert(rst.getString("manager_a")));
      	setTel_a(toucsString.unConvert(rst.getString("tel_a")));
      	setManager_b(toucsString.unConvert(rst.getString("manager_b")));
      	setTel_b(toucsString.unConvert(rst.getString("tel_b")));
      	String dev = "", man = "";
      	int idx;
      	dev = toucsString.unConvert(rst.getString("dev_bankno"));
      	man = toucsString.unConvert(rst.getString("man_bankno"));
      	idx = dev.indexOf("000000000-100000001-");
      	Debug.println("Rox!!!!!!!!!!!" + dev + "~~~" + idx);
      	if (idx > -1) {
      		setDev_bankno(dev.substring(idx + 20));
      	} else {
      		setDev_bankno(dev);
      	}
      	idx = man.indexOf("000000000-100000001-");
      	Debug.println("Rox!!!!!!!!!!!" + man + "~~~" + idx);
      	if (idx > -1) {
      		setMan_bankno(man.substring(idx + 20));
      	} else {
      		setMan_bankno(man);
      	}
      	setMer_id_jcb(toucsString.unConvert(rst.getString("mer_id_jcb")));
      	setMer_id_dinner(toucsString.unConvert(rst.getString("mer_id_dinner")));
      	setMer_id_ae(toucsString.unConvert(rst.getString("mer_id_ae")));
      	setRate_visa(toucsString.unConvert(rst.getString("rate_visa")));
      	setRate_master(toucsString.unConvert(rst.getString("rate_master")));
      	setRate_jcb(toucsString.unConvert(rst.getString("rate_jcb")));
      	setRate_dinner(toucsString.unConvert(rst.getString("rate_dinner")));
      	setRate_ae(toucsString.unConvert(rst.getString("rate_ae")));
      	setRate_visa2(toucsString.unConvert(rst.getString("rate_visa2")));
      	setRate_master2(toucsString.unConvert(rst.getString("rate_master2")));
      	setRate_jcb2(toucsString.unConvert(rst.getString("rate_jcb2")));
      	setRate_dinner2(toucsString.unConvert(rst.getString("rate_dinner2")));
      	setRate_ae2(toucsString.unConvert(rst.getString("rate_ae2")));
      	setOpen_flag(toucsString.unConvert(rst.getString("open_flag")));
      	setAcct_no(toucsString.unConvert(rst.getString("acct_no")));
      	setAcct_bankno(toucsString.unConvert(rst.getString("acct_bankno")));
      	setAcct_name(toucsString.unConvert(rst.getString("acct_name")));
      	setBill_ck_addr(toucsString.unConvert(rst.getString("bill_ck_addr")));
      	setBill_ck_zip(toucsString.unConvert(rst.getString("bill_ck_zip")));
      	setBill_ck_rcv(toucsString.unConvert(rst.getString("bill_ck_rcv")));
      	setBill_ck_email(toucsString.unConvert(rst.getString("bill_ck_email")));
      	setBill_ck_fax(toucsString.unConvert(rst.getString("bill_ck_fax")));
      	setMer_level(toucsString.unConvert(rst.getString("mer_level")));
      	setMer_state(toucsString.unConvert(rst.getString("mer_state")));
      	setSettle_date(toucsString.unConvert(rst.getString("settle_date")));
      	setLast_mod_op(toucsString.unConvert(rst.getString("last_mod_op")));
      	setLast_mod_time(toucsString.unConvert(rst.getString("last_mod_time")));
      	setOp_id(toucsString.unConvert(rst.getString("op_id")));
      	setInput_time(toucsString.unConvert(rst.getString("input_time")));
      	setFlag(toucsString.unConvert(rst.getString("flag")));
    }

    /**
     * 创建用于添加的动态SQL语句
     * @param conn 数据库连接对象
     * @return 动态SQL语句对象
     * @throws SQLException
     */
    public PreparedStatement makeInsertStm(Connection conn) throws SQLException{
    	String sql = "INSERT INTO merchant_list("
    			   + "mer_id, mer_name, mer_eg, mer_city, mer_addr, mer_zip, mer_tel, mer_url, mcc, tcc, manager_a, tel_a, manager_b, tel_b, dev_bankno, man_bankno, mer_id_jcb, mer_id_dinner, mer_id_ae, rate_visa, rate_master, rate_jcb, rate_dinner, rate_ae, rate_visa2, rate_master2, rate_jcb2, rate_dinner2, rate_ae2, open_flag, acct_no, acct_bankno, acct_name, bill_ck_addr, bill_ck_zip, bill_ck_rcv, bill_ck_email, bill_ck_fax, mer_level, mer_state, settle_date, last_mod_op, last_mod_time, op_id, input_time, flag)"
                   + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	Debug.println("UPDATE SQL:"+sql);
    	PreparedStatement stm=conn.prepareStatement(sql);
    	stm.setString(1, mer_id);
    	stm.setString(2, mer_name);
    	stm.setString(3, mer_eg);
    	stm.setString(4, mer_city);
    	stm.setString(5, mer_addr);
    	stm.setString(6, mer_zip);
    	stm.setString(7, mer_tel);
    	stm.setString(8, mer_url);
    	stm.setString(9, mcc);
    	stm.setString(10, tcc);
    	stm.setString(11, manager_a);
    	stm.setString(12, tel_a);
    	stm.setString(13, manager_b);
    	stm.setString(14, tel_b);
    	stm.setString(15, "000000000-100000001-" + dev_bankno);
    	stm.setString(16, "000000000-100000001-" + man_bankno);
    	stm.setString(17, mer_id_jcb);
    	stm.setString(18, mer_id_dinner);
    	stm.setString(19, mer_id_ae);
    	stm.setString(20, rate_visa);
    	stm.setString(21, rate_master);
    	stm.setString(22, rate_jcb);
    	stm.setString(23, rate_dinner);
    	stm.setString(24, rate_ae);
    	stm.setString(25, rate_visa2);
    	stm.setString(26, rate_master2);
    	stm.setString(27, rate_jcb2);
    	stm.setString(28, rate_dinner2);
    	stm.setString(29, rate_ae2);
    	stm.setString(30, open_flag);
    	stm.setString(31, acct_no);
    	stm.setString(32, acct_bankno);
    	stm.setString(33, acct_name);
    	stm.setString(34, bill_ck_addr);
    	stm.setString(35, bill_ck_zip);
    	stm.setString(36, bill_ck_rcv);
    	stm.setString(37, bill_ck_email);
    	stm.setString(38, bill_ck_fax);
    	stm.setString(39, mer_level);
    	stm.setString(40, mer_state);
    	stm.setString(41, settle_date);
    	stm.setString(42, last_mod_op);
    	stm.setString(43, last_mod_time);
    	stm.setString(44, op_id);
    	stm.setString(45, input_time);
    	stm.setString(46, flag);
    	
    	return stm;
    }

    /**
     * 创建用于修改的动态SQL语句
     * @param conn 数据库连接对象
     * @param key 设备编号（关键字）
     * @return 动态SQL语句对象
     * @throws SQLException
     */
    public PreparedStatement makeUpdateStm(Connection conn,String key) throws SQLException{
    	String sql="UPDATE merchant_list SET("
                   +"mer_name, mer_eg, mer_city, mer_addr, mer_zip, mer_tel, mer_url, mcc, tcc, manager_a, tel_a, manager_b, tel_b, dev_bankno, man_bankno, mer_id_jcb, mer_id_dinner, mer_id_ae, rate_visa, rate_master, rate_jcb, rate_dinner, rate_ae, rate_visa2, rate_master2, rate_jcb2, rate_dinner2, rate_ae2, open_flag, acct_no, acct_bankno, acct_name, bill_ck_addr, bill_ck_zip, bill_ck_rcv, bill_ck_email, bill_ck_fax, mer_level, mer_state, settle_date, last_mod_op, last_mod_time, op_id, input_time, flag)"
                   +" =(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + "WHERE mer_id = ?";
        Debug.println("UPDATE SQL:"+sql);
        PreparedStatement stm=conn.prepareStatement(sql);
        stm.setString(1, mer_name);
        stm.setString(2, mer_eg);
        stm.setString(3, mer_city);
        stm.setString(4, mer_addr);
        stm.setString(5, mer_zip);
        stm.setString(6, mer_tel);
        stm.setString(7, mer_url);
        stm.setString(8, mcc);
        stm.setString(9, tcc);
        stm.setString(10,  manager_a);
        stm.setString(11, tel_a);
        stm.setString(12, manager_b);
        stm.setString(13, tel_b);
        stm.setString(14, "000000000-100000001-" + dev_bankno);
        stm.setString(15, "000000000-100000001-" + man_bankno);
        stm.setString(16, mer_id_jcb);
        stm.setString(17, mer_id_dinner);
        stm.setString(18, mer_id_ae);
        stm.setString(19, rate_visa);
        stm.setString(20, rate_master);
        stm.setString(21, rate_jcb);
        stm.setString(22, rate_dinner);
        stm.setString(23, rate_ae);
        stm.setString(24, rate_visa2);
        stm.setString(25, rate_master2);
        stm.setString(26, rate_jcb2);
        stm.setString(27, rate_dinner2);
        stm.setString(28, rate_ae2);
        stm.setString(29, open_flag);
        stm.setString(30, acct_no);
        stm.setString(31, acct_bankno);
        stm.setString(32, acct_name);
        stm.setString(33, bill_ck_addr);
        stm.setString(34, bill_ck_zip);
        stm.setString(35, bill_ck_rcv);
        stm.setString(36, bill_ck_email);
        stm.setString(37, bill_ck_fax);
        stm.setString(38, mer_level);
        stm.setString(39, mer_state);
        stm.setString(40, settle_date);
        stm.setString(41, last_mod_op);
        stm.setString(42, last_mod_time);
        stm.setString(43, op_id);
        stm.setString(44, input_time);
        stm.setString(45, flag);
        stm.setString(46, key);

        return stm;
    }
    /**
     * 创建用于删除的动态SQL语句
     * @param conn 数据库连接对象
     * @param key 设备编号（关键字）
     * @return 动态SQL语句对象
     * @throws SQLException
     */
    public PreparedStatement makeDeleteStm(Connection conn,String key) throws SQLException{
        String sql="DELETE FROM merchant_list WHERE mer_id = ?";
        Debug.println("DELETE SQL:"+sql);
        PreparedStatement stm=conn.prepareStatement(sql);
        stm.setString(1, key);
        return stm;
    }
}
