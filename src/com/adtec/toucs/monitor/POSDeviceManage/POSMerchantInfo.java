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

public class POSMerchantInfo {

	//�̻���
	private String merchant_id = "";
	//�̻�����
	private String mct_name = "";
	//�̻�Ӣ����
	private String mct_enname = "";
	//POS����
	private String curr_type = "";
	//��־
	//1����2ͣ��
	private String flag = "";
	//�̻�����
	//1���ݡ�2�ٻ���3�������֡�4��˾��5���ز���6������7����
	private String mct_type = "";
	//����֧�к�
	private String manage_bankno = "";
	//����֧������
	private String manage_bankname = "";
	//����Ա
	private String operator = "";
	//������
	private String org_id = "";
	//ѹ������
	private String pcard_machine_no = "";
	//POS����
	private String pos_machine_no = "";
	//�绰
	private String telephone = "";
	//����
	private String fax = "";
	//�ʱ�
	private String zipcode = "";
	//��ַ
	private String address = "";
	//���˴���
	private String artificial_person = "";
	//������
	private String handle_man = "";
	//ǩԼ����
	private String signature_date = "";
	//��������
	private String cancel_date = "";
	//�����־
	//1.����2.����
	private String clear_flag = "";
	
	public POSMerchantInfo() {
	}

	//�̻��Ŷ�д
	public void setMerchantid(String Merchantid) {
		merchant_id = Merchantid;
	}

	public String getMerchantid() {
		return merchant_id;
	}

	//�̻����ƶ�д
	public void setMctname(String Mctname) {
		mct_name = Mctname;
	}

	public String getMctname() {
		return mct_name;
	}

	//�̻�Ӣ������д
	public void setMctenname(String Mctenname) {
		mct_enname = Mctenname;
	}

	public String getMctenname() {
		return mct_enname;
	}

	//POS���ֶ�д
	public void setCurrtype(String Currtype) {
		curr_type = Currtype;
	}

	public String getCurrtype() {
		return curr_type;
	}

	//��־��д
	public void setFlag(String Flag) {
		flag = Flag;
	}

	public String getFlag() {
		return flag;
	}

	//�̻����Ͷ�д
	public void setMcttype(String Mcttype) {
		mct_type = Mcttype;
	}

	public String getMcttype() {
		return mct_type;
	}

	//����֧�кŶ�д
	public void setManagebankno(String Managebankno) {
		manage_bankno = Managebankno;
	}

	public String getManagebankno() {
		return manage_bankno;
	}

	//����֧�����ƶ�д
	public void setManagebankname(String Managebankname) {
		manage_bankname = Managebankname;
	}

	public String getManagebankname() {
		return manage_bankname;
	}

	//����Ա��д
	public void setOperator(String Operator) {
		operator = Operator;
	}

	public String getOperator() {
		return operator;
	}

	//�����Ŷ�д
	public void setOrgid(String Orgid) {
		org_id = Orgid;
	}

	public String getOrgid() {
		return org_id;
	}

	//ѹ��������д
	public void setPcardmachine_no(String Pcardmachine_no) {
		pcard_machine_no = Pcardmachine_no;
	}

	public String getPcardmachine_no() {
		return pcard_machine_no;
	}

	//POS������д
	public void setPosmachine_no(String Posmachine_no) {
		pos_machine_no = Posmachine_no;
	}

	public String getPosmachine_no() {
		return pos_machine_no;
	}

	//�绰��д
	public void setTelephone(String Telephone) {
		telephone = Telephone;
	}

	public String getTelephone() {
		return telephone;
	}

	//�����д
	public void setFax(String Fax) {
		fax = Fax;
	}

	public String getFax() {
		return fax;
	}

	//�ʱ��д
	public void setZipcode(String Zipcode) {
		zipcode = Zipcode;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	//��ַ��д
	public void setAddress(String Address) {
		address = Address;
	}

	public String getAddress() {
		return address;
	}

	//���˴����д
	public void setArtificialperson(String Artificialperson) {
		artificial_person = Artificialperson;
	}

	public String getArtificialperson() {
		return artificial_person;
	}

	//�����˶�д
	public void setHandleman(String Handleman) {
		handle_man = Handleman;
	}

	public String getHandleman() {
		return handle_man;
	}

	//ǩԼ���ڶ�д
	public void setSignaturedate(String Signaturedate) {
		signature_date = Signaturedate;
	}	

	public String getSignaturedate() {
		return signature_date;
	}

	//�������ڶ�д
	public void setCanceldate(String Canceldate) {
		cancel_date = Canceldate;
	}
	
	public String getCanceldate() {
		return cancel_date;
	}

	//�����־��д
	public void setClearflag(String Clearflag) {
		clear_flag = Clearflag;
	}

	public String getClearflag() {
		return clear_flag;
	}

  /**
   * ��Http������ȡATM������Ϣ
   * @param request Http����
   */
	public void fetchData(HttpServletRequest request) {
		//�̻���
		setMerchantid(request.getParameter("merchant_id"));
		//�̻�����
		setMctname(request.getParameter("mct_name"));
		//�̻�Ӣ����
		setMctenname(request.getParameter("mct_enname"));
		//POSƱ�ݴ�ӡ����
		setCurrtype(request.getParameter("curr_type"));
		//��־
		setFlag(request.getParameter("flag"));
		//�̻�����
		setMcttype(request.getParameter("mct_type"));
		//����֧�к�
		setManagebankno(request.getParameter("manage_bankno"));
		//����֧������
		setManagebankname(request.getParameter("managebankname_temp"));
		//����Ա
		setOperator(request.getParameter("operator"));
		//������
		setOrgid(request.getParameter("org_id"));
		//ѹ������
		setPcardmachine_no(request.getParameter("pcard_machine_no"));		
		//POS����
		setPosmachine_no(request.getParameter("pos_machine_no"));
		//�绰
		setTelephone(request.getParameter("telephone"));
		//����
		setFax(request.getParameter("fax"));
		//�ʱ�
		setZipcode(request.getParameter("zipcode"));
		//��ַ
		setAddress(request.getParameter("address"));
		//������
		setHandleman(request.getParameter("handle_man"));
		//���˴���
		setArtificialperson(request.getParameter("artificial_person"));
		//ǩԼ����
		setSignaturedate(request.getParameter("signature_date"));
		//��������
		setCanceldate(request.getParameter("cancel_date"));
		//�����־
		setClearflag(request.getParameter("clear_flag"));
	}

  /**
   * �Ӳ�ѯ�����ȡATM������Ϣ
   * @param rst ��ѯ�����
   * @throws SQLException
   */
	public void fetchData(ResultSet rst) throws SQLException {
		//�̻���
		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
		//�̻�����
		setMctname(toucsString.unConvert(rst.getString("mct_name")));
		//�̻�Ӣ����	
		setMctenname(toucsString.unConvert(rst.getString("mct_enname")));
		//POS��	��
		setCurrtype(toucsString.unConvert(rst.getString("curr_type")));
		//��־
		setFlag(toucsString.unConvert(rst.getString("flag")));
		//�̻�����
		setMcttype(toucsString.unConvert(rst.getString("mct_type")));
		//����֧�к�	
		setManagebankno(toucsString.unConvert(rst.getString("manage_bankno")));
		//����֧������
		setManagebankname(toucsString.unConvert(rst.getString("manage_bankname")));
		//����Ա
		setOperator(toucsString.unConvert(rst.getString("operator")));	
		//������
		setOrgid(toucsString.unConvert(rst.getString("org_id")));
		//ѹ������
		setPcardmachine_no(toucsString.unConvert(rst.getString("pcard_machine_no")));
		//POS����
		setPosmachine_no(toucsString.unConvert(rst.getString("pos_machine_no")));
		//�绰
		setTelephone(toucsString.unConvert(rst.getString("telephone")));
		//����
		setFax(toucsString.unConvert(rst.getString("fax")));
		//��	��
		setZipcode(toucsString.unConvert(rst.getString("zipcode")));
		//��ַ
		setAddress(toucsString.unConvert(rst.getString("address")));
		//������
		setHandleman(toucsString.unConvert(rst.getString("handle_man")));	
		//���˴���
		setArtificialperson(toucsString.unConvert(rst.getString("artificial_person")));
		//ǩԼ����	
		setSignaturedate(toucsString.unConvert(rst.getString("signature_date")));
		//��������
		setCanceldate(toucsString.unConvert(rst.getString("cancel_date")));
		//�����־
		setClearflag(toucsString.unConvert(rst.getString("clear_flag")));
	}

  /**
   * ����������ӵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException {
		String sql = "INSERT INTO pos_merchant("
				   + "merchant_id,mct_name,mct_enname,curr_type,flag,"
				   + "mct_type,manage_bankno,manage_bankname,operator,org_id,"
				   + "pcard_machine_no,pos_machine_no,telephone,fax,zipcode,"
				   + "address,artificial_person,handle_man,signature_date,cancel_date,"
				   + "clear_flag)" + " VALUES(?,?,?,?,?," + "?,?,?,?,?,"
				   + "?,?,?,?,?," + "?,?,?,?,?," + "'1')";
		System.out.println(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println(stm.toString());
		
		//�̻���
		stm.setString(1, merchant_id);
		//�̻�����
		stm.setString(2, mct_name);	
		//�̻�Ӣ����
		stm.setString(3, mct_enname);
		//POSƱ�ݴ�ӡ����
		stm.setString(4, curr_type);
		//��־
		stm.setString(5, flag);
		//�̻�����
		stm.setString(6, mct_type);
		//����֧�к�
		stm.setString(7, manage_bankno);
		//����֧������
		stm.setString(8, manage_bankname);
		//����Ա
		stm.setString(9, operator);
		//������
		stm.setString(10, org_id);
		//ѹ������
		stm.setString(11, pcard_machine_no);
		//POS����
		stm.setString(12, pos_machine_no);
		//�绰
		stm.setString(13, telephone);
		//����
		stm.setString(14, fax);
		//�ʱ�
		stm.setString(15, zipcode);
		//��ַ
		stm.setString(16, address);
		//������
		stm.setString(17, handle_man);
		//���˴���
		stm.setString(18, artificial_person);
		//ǩԼ����
		stm.setString(19, signature_date);
		//��������
		stm.setString(20, cancel_date);
		return stm;
	}

  /**
   * ���������޸ĵĶ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeUpdateStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET("
				   + "mct_name,mct_enname,curr_type,flag,"
				   + "mct_type,manage_bankno,manage_bankname,operator,org_id,"
				   + "pcard_machine_no,pos_machine_no,telephone,fax,zipcode,"
				   + "address,artificial_person,handle_man,signature_date,cancel_date"
				   + ")" + " =(?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?,"
				   + "?,?,?,?,?" + ")" + "WHERE merchant_id = ?";
		System.out.println("UPDATE SQL:" + sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//�̻�����	
		stm.setString(1, mct_name);
		//�̻�Ӣ����
		stm.setString(2, mct_enname);
		//POSƱ�ݴ�ӡ����
		stm.setString(3, curr_type);
		//��־
		stm.setString(4, flag);
		//�̻�����
		stm.setString(5, mct_type);
		//����֧�к�
		stm.setString(6, manage_bankno);
    	//����֧������
		stm.setString(7, manage_bankname);
		//����Ա
		stm.setString(8, operator);
		//������
		stm.setString(9, org_id);
		//ѹ������
		stm.setString(10, pcard_machine_no);
		//POS����
		stm.setString(11, pos_machine_no);
		//�绰
		stm.setString(12, telephone);
		//����
		stm.setString(13, fax);
		//�ʱ�
		stm.setString(14, zipcode);
		//��ַ
		stm.setString(15, address);
		//���˴���
		stm.setString(16, artificial_person);
		//������
		stm.setString(17, handle_man);
		//ǩԼ����
		stm.setString(18, signature_date);
		//��������
		stm.setString(19, cancel_date);

		//�޸ĵ�����
		stm.setString(20, key);
		System.out.println("UPDATE KEY:" + key);
		return stm;
	}

  /**
   * ��������ɾ���Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeDeleteStm(Connection conn, String key) throws SQLException {
		String sql = "DELETE FROM pos_merchant WHERE (clear_flag = '2')" + "AND(merchant_id = ?)";
		Debug.println("it is a new World!!!!!");
		PreparedStatement stm = conn.prepareStatement(sql);
		//�޸ĵ�����
		stm.setString(1, key);
		Debug.println("the key is :" + key);	
		System.out.println("DELETE SQL:" + sql);
		return stm;
	}

  /**
   * ��������ɾ���Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeClearStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET clear_flag = '2' " + "WHERE (merchant_id = ?)";
		System.out.println("CLEAR SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//�޸ĵ�����
		stm.setString(1, key);
		System.out.println("KEY:" + key);
		return stm;
	}

  /**
   * ��������ɾ���Ķ�̬SQL���
   * @param conn ���ݿ����Ӷ���
   * @param key �豸��ţ��ؼ��֣�
   * @return ��̬SQL������
   * @throws SQLException
   */
	public PreparedStatement makeNormStm(Connection conn, String key) throws SQLException {
		String sql = "UPDATE pos_merchant SET clear_flag = '1' " + "WHERE (merchant_id = ?)";
		System.out.println("CLEAR SQL:" + sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//�޸ĵ�����
		stm.setString(1, key);
		System.out.println("KEY:" + key);
		return stm;
	}
	
	public String getOrg_Name() throws MonitorException {
		String OrgName = "";
		SqlAccess sq = SqlAccess.createConn();
		if (sq == null) {
			throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
		}
		try {
			Statement stm = sq.conn.createStatement();
			String sql = "SELECT org_name FROM monit_orginfo WHERE org_id = '" + org_id + "'";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				OrgName = rs.getString(1);
			}
			rs.close();
		} catch (SQLException e) {
			throw new MonitorException(e);
		} finally {
			sq.close();
		}
		return OrgName;
	}
}
