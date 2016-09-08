package com.adtec.toucs.monitor.POSDeviceManage;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;
import com.adtec.toucs.monitor.common.Debug;
import com.adtec.toucs.monitor.common.MonitorException;
import com.adtec.toucs.monitor.common.toucsString;

/**
 * <p>Title: ��ҵIC����˾�����</p>
 * <p>Description: ��ҵIC����˾�����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ADTEC</p>
 * @author liuxy
 * @version 1.0
 */

public class PosCompanyInfo {
	
	public PosCompanyInfo() {
		
	}
	
	private String company_id = "";     //��˾����
	private String company_name = "";   //��˾����
	private String company_sname = "";  //��˾���
	private String company_type = "";   //��˾����
	private String branch_id = "";      //��������
	private String ip_addr = "";        //IP��ַ
	private String port = "";           //�˿ں�
	private String secu_kind = "";      //��ȫ��������: 0-�����; 1-Ӳ����; 2-����
	private String master_key = "";     //����Կ
	private String pin_key = "";        //PIN KEY
	private String mac_key = "";        //MAC KEY
	private String company_stat = "";   //״̬: 0-ͣ��; 1-����
	private String health_stat = "";    //����״̬: 0-������; 1-����
	private int error_num = 0;          //�������
	private String last_date = "";      //���������
	private String last_time = "";      //�����ʱ��
	private String memo1 = "";          //��ע 1
	private String memo2 = "";          //��ע 2
	private String memo3 = "";          //��ע 3
	
	/**
	 * @return ���� branch_id��
	 */
	public String getBranch_id() {
		return branch_id;
	}
	
	/**
	 * @param branch_id Ҫ���õ� branch_id��
	 */
	public void setBranch_id(String branch_id) {
		this.branch_id = branch_id;
	}
	
	/**
	 * @return ���� company_id��
	 */
	public String getCompany_id() {
		return company_id;
	}
	
	/**
	 * @param company_id Ҫ���õ� company_id��
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	/**
	 * @return ���� company_name��
	 */
	public String getCompany_name() {
		return company_name;
	}
	
	/**
	 * @param company_name Ҫ���õ� company_name��
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	/**
	 * @return ���� company_stat��
	 */
	public String getCompany_stat() {
		return company_stat;
	}
	
	/**
	 * @param company_stat Ҫ���õ� company_stat��
	 */
	public void setCompany_stat(String company_stat) {
		this.company_stat = company_stat;
	}
	
	/**
	 * @return ���� company_type��
	 */
	public String getCompany_type() {
		return company_type;
	}
	
	/**
	 * @param company_type Ҫ���õ� company_type��
	 */
	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}
	
	/**
	 * @return ���� error_num��
	 */
	public int getError_num() {
		return error_num;
	}
	
	/**
	 * @param error_num Ҫ���õ� error_num��
	 */
	public void setError_num(int error_num) {
		this.error_num = error_num;
	}
	
	/**
	 * @return ���� health_stat��
	 */
	public String getHealth_stat() {
		return health_stat;
	}
	
	/**
	 * @param health_stat Ҫ���õ� health_stat��
	 */
	public void setHealth_stat(String health_stat) {
		this.health_stat = health_stat;
	}
	
	/**
	 * @return ���� ip_addr��
	 */
	public String getIp_addr() {
		return ip_addr;
	}
	
	/**
	 * @param ip_addr Ҫ���õ� ip_addr��
	 */
	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}
	
	/**
	 * @return ���� last_date��
	 */
	public String getLast_date() {
		return last_date;
	}
	
	/**
	 * @param last_date Ҫ���õ� last_date��
	 */
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	
	/**
	 * @return ���� last_time��
	 */
	public String getLast_time() {
		return last_time;
	}
	
	/**
	 * @param last_time Ҫ���õ� last_time��
	 */
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	
	/**
	 * @return ���� mac_key��
	 */
	public String getMac_key() {
		return mac_key;
	}
	
	/**
	 * @param mac_key Ҫ���õ� mac_key��
	 */
	public void setMac_key(String mac_key) {
		this.mac_key = mac_key;
	}
	
	/**
	 * @return ���� master_key��
	 */
	public String getMaster_key() {
		return master_key;
	}
	
	/**
	 * @param master_key Ҫ���õ� master_key��
	 */
	public void setMaster_key(String master_key) {
		this.master_key = master_key;
	}
	
	/**
	 * @return ���� memo1��
	 */
	public String getMemo1() {
		return memo1;
	}
	
	/**
	 * @param memo1 Ҫ���õ� memo1��
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	
	/**
	 * @return ���� memo2��
	 */
	public String getMemo2() {
		return memo2;
	}
	
	/**
	 * @param memo2 Ҫ���õ� memo2��
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	
	/**
	 * @return ���� memo3��
	 */
	public String getMemo3() {
		return memo3;
	}
	
	/**
	 * @param memo3 Ҫ���õ� memo3��
	 */
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	
	/**
	 * @return ���� pin_key��
	 */
	public String getPin_key() {
		return pin_key;
	}
	
	/**
	 * @param pin_key Ҫ���õ� pin_key��
	 */
	public void setPin_key(String pin_key) {
		this.pin_key = pin_key;
	}
	
	/**
	 * @return ���� port��
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * @param port Ҫ���õ� port��
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * @return ���� secu_kind��
	 */
	public String getSecu_kind() {
		return secu_kind;
	}
	
	/**
	 * @param secu_kind Ҫ���õ� secu_kind��
	 */
	public void setSecu_kind(String secu_kind) {
		this.secu_kind = secu_kind;
	}
	
	/**
	 * @return ���� company_sname��
	 */
	public String getCompany_sname() {
		return company_sname;
	}

	/**
	 * @param company_sname Ҫ���õ� company_sname��
	 */
	public void setCompany_sname(String company_sname) {
		this.company_sname = company_sname;
	}

	/**
	 * ��Http������ȡ��ҵIC����˾����Ϣ
	 * @param request Http����
	 */
	public void fetchData(HttpServletRequest request) {
		setCompany_id(request.getParameter("company_id"));
		setCompany_name(request.getParameter("company_name"));
		setCompany_sname(request.getParameter("company_sname"));
		setCompany_type(request.getParameter("company_type"));
		setBranch_id(request.getParameter("branch_id"));
		setIp_addr(request.getParameter("ip_addr"));
		setPort(request.getParameter("port"));
		setSecu_kind(request.getParameter("secu_kind"));
		setCompany_stat(request.getParameter("company_stat"));
		setHealth_stat(request.getParameter("health_stat"));
		setLast_date(request.getParameter("last_date"));
		setLast_time(request.getParameter("last_time"));
		setMemo1(request.getParameter("memo1"));
		setMemo2(request.getParameter("memo2"));
		setMemo3(request.getParameter("memo3"));
	}
	
	/**
	 * �Ӳ�ѯ�����ȡ��ҵIC����˾����Ϣ
	 * @param rst ��ѯ�����
	 * @throws SQLException
	 */
	public void fetchData(ResultSet rst) throws SQLException {
		setCompany_id(toucsString.unConvert(rst.getString("company_id")));
		setCompany_name(toucsString.unConvert(rst.getString("company_name")));
		setCompany_sname(toucsString.unConvert(rst.getString("company_sname")));
		setCompany_type(toucsString.unConvert(rst.getString("company_type")));
		setBranch_id(toucsString.unConvert(rst.getString("branch_id")));
		setIp_addr(toucsString.unConvert(rst.getString("ip_addr")));
		setPort(toucsString.unConvert(rst.getString("port")));
		setSecu_kind(toucsString.unConvert(rst.getString("secu_kind")));
		setCompany_stat(toucsString.unConvert(rst.getString("company_stat")));
		setHealth_stat(toucsString.unConvert(rst.getString("health_stat")));
		setLast_date(toucsString.unConvert(rst.getString("last_date")));
		setLast_time(toucsString.unConvert(rst.getString("last_time")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
	}
	
	/**
	 * ����������ӵĶ�̬SQL���
	 * @param conn ���ݿ����Ӷ���
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {		
		String sql = "INSERT INTO pos_company VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT POS_COMPANY:" + sql);
		
		stm.setString(1, company_id);
		stm.setString(2, company_name);
		stm.setString(3, company_sname);
		stm.setString(4, company_type);
		stm.setString(5, branch_id);
		stm.setString(6, ip_addr);
		stm.setString(7, port);
		stm.setString(8, secu_kind);
		stm.setString(9, "0000000000000000");
		stm.setString(10, "0000000000000000");
		stm.setString(11, "0000000000000000");
		stm.setString(12, "1");
		stm.setString(13, health_stat);
		stm.setInt(14, error_num);
		stm.setString(15, last_date);
		stm.setString(16, last_time);
		stm.setString(17, memo1);
		stm.setString(18, memo2);
		stm.setString(19, memo3);	
		return stm;
	}
	
	/**
	 * ��������ɾ���Ķ�̬SQL���
	 * @param conn ���ݿ����Ӷ���
	 * @param key �豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeDeleteStm(Connection conn, String strKey) throws SQLException {
		String sql = "DELETE FROM pos_company WHERE company_id = ?";
		System.out.println("DELETE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		//ɾ��������
		stm.setString(1, strKey);
		return stm;
	}
	
	/**
	 * ���������޸ĵĶ�̬SQL���
	 * @param conn ���ݿ����Ӷ���
	 * @param key �豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException, MonitorException {
		String sql = "UPDATE pos_company SET "
				   + "(company_name,company_sname,company_type,branch_id,ip_addr,port,secu_kind,health_stat,memo1,memo2,memo3)"
				   + " =(?,?,?,?,?,?,?,?,?,?,?)"
				   + " WHERE company_id=?";
		Debug.println(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, company_name);
		stm.setString(2, company_sname);
		stm.setString(3, company_type);
		stm.setString(4, branch_id);
		stm.setString(5, ip_addr);
		stm.setString(6, port);
		stm.setString(7, secu_kind);
		stm.setString(8, health_stat);
		stm.setString(9, memo1);
		stm.setString(10, memo2);
		stm.setString(11, memo3);
		stm.setString(12, company_id);
		
		return stm;
	}
	
	/**
	 * ����������������ͣ��״̬�ĵĶ�̬SQL���
	 * @param conn ���ݿ����Ӷ���
	 * @param key �豸��ţ��ؼ��֣�
	 * @return ��̬SQL������
	 * @throws SQLException
	 */
	public PreparedStatement makeStatStm(Connection conn, String key, boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_company SET " + "company_stat='0'" + " WHERE company_id=?";
		} else {
			sql = "UPDATE pos_company SET " + "company_stat='1'" + " WHERE company_id=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);	
		stm.setString(1, company_id);	
		return stm;
	}	
}
