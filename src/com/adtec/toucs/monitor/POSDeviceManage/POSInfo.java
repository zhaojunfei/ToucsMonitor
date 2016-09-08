package com.adtec.toucs.monitor.POSDeviceManage;

import java.util.*;
import java.sql.*;

import javax.servlet.http.*;

import com.adtec.toucs.monitor.common.*;
import com.adtec.toucs.monitor.systemmanage.*;

/**
 * <p>Title:POS�豸����������Ϣ�� </p>
 * <p>Description: ��װPOS�豸������Ϣ</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Adtec</p>
 * @author lihaile
 * @version 1.0
 */

public class POSInfo {

	private int MAXLENGTH = 50;

	public POSInfo() {
	}

	//POS���
	private String pos_code = "";

	//POS-C���
	private String pos_c_code = "";

	//POS-DCC���
	private String pos_dcc_code = "";

	//�̻���
	private String merchant_id = "";
	
	//POS״̬
	private String pos_stat = "";
	
	//�ؿ���
	private String rebate = "";
	
	//��һؿ���
	private String frn_rebate = "";

	//��������
	private String open_bankno = "";
	
	//������
	private String exg_no = "";	
	
	//�˺�
	private String acct_no = "";
	
	//�����˺�
	private String vacct_no = "";

	//��װ����
	private String model = "";
	
	//��װ��̨
	private String counter = "";
	
	//��װ��ַ
	private String fix_address = "";

	//��װ��ϵ��
	private String fix_person = "";

	//��װ����
	private String fix_date = "";

	//��������
	private String record_date = "";

	//��ע1
	private String memo1 = "";

	//��ע2
	private String memo2 = "";

	//��ע3
	private String memo3 = "";
	
	//ͨѶ����
	private String net_type = "";
	
	//�̻�����
	private String mct_name = "";

	//<<<<<begin add by liyp 20030703
	//������� Ĭ��Ϊ�����("1")
	private String equip_type = "1";
	
	//Ԥ��Ȩ���� 0:��Ԥ��Ȩ�ཻ�� 1:��Ԥ��Ȩ�ཻ�� 2:���� Ĭ��Ϊ��Ԥ��Ȩ
	private String pre_authorized = "0";
	
	//����Ȩ������
	private String tran_right = "";
	
	//���俨����
	private String hand_flag = "";
	
	public void setEquiptType(String type) {
		equip_type = type;
	}
	
	public String getEquiptType() {
		return equip_type;
	}
	
	public void setPreAutorized(String type) {
		pre_authorized = type;
	}
	
	public String getPreAutorized() {
		return pre_authorized;
	}
	
	public void setTxnRight(String right) {
		tran_right = right;
	}
	
	public String getTxnRight() {
		return tran_right;
	}
	
	public void setHandFlag(String flag) {
		hand_flag = flag;
	}
	
	public String getHandFlag() {
		return hand_flag;
	}
	
	//>>>>>>>>>end add by liyp 20030703
	
	//POS������Զ�д
	public void setPoscode(String poscode) {
		pos_code = poscode;
	}
	
	public String getPoscode() {
		return pos_code;
	}
	
	//POS������Զ�д
	public void setPosCCode(String posCCode) {
		pos_c_code = posCCode;
	}
	
	public String getPosCCode() {
		return pos_c_code;
	}
	
	//POS������Զ�д
	public void setPosDccCode(String posDccCode) {
		pos_dcc_code = posDccCode;
	}
	
	public String getPosDccCode() {
		return pos_dcc_code;
	}
	
	//POS�����̻���д
	public void setMerchantid(String Merchartid) {
		merchant_id = Merchartid;
	}

	public String getMerchantid() {
		return merchant_id;
	}
	
	//�̻�״̬��д
	public void setPosstat(String Posstat) {
		pos_stat = Posstat;
	}
	
	public String getPosstat() {
		return pos_stat;
	}
	
	//�ؿ��ʶ�д
	public void setRebate(String Rebate) {
		rebate = Rebate;
	}
	
	public String getRebate() {
		return rebate;
	}
	
	//��һؿ��ʶ�д
	public void setFrnRebate(String Rebate) {
		frn_rebate = Rebate;
	}
	
	public String getFrnRebate() {
		return frn_rebate;
	}
	
	//��������
	public void setOpenbankno(String Openbankno) {
		open_bankno = Openbankno;
	}

	public String getOpenbankno() {
		return open_bankno;
	}
	
	//������
	public void setExgno(String Exgno) {
		exg_no = Exgno;
	}

	public String getExgno() {
		return exg_no;
	}

	//�˺�
	public void setAcctno(String Acctno) {
		acct_no = Acctno;
	}
	
	public String getAcctno() {
		return acct_no;
	}
	
	//�����˺�
	public void setVacctno(String Vacctno) {
		vacct_no = Vacctno;
	}
	
	public String getVacctno() {
		return vacct_no;
	}
	
	//��װ����
	public void setModel(String Model) {
		model = Model;
	}

	public String getModel() {
		return model;
	}

	//��װ��̨
	public void setCounter(String Counter) {
		counter = Counter;
	}

	public String getCounter() {
		return counter;
	}

	//��װ��ַ
	public void setFixaddress(String Fixaddress) {
		fix_address = Fixaddress;
	}

	public String getFixaddress() {
		return fix_address;
	}	

	//��װ��ϵ��
	public void setFixperson(String Fixperson) {
		fix_person = Fixperson;
	}

	public String getFixperson() {
		return fix_person;
	}

	//��װ����
	public void setFixdate(String Fixdate) {
		fix_date = Fixdate;
	}

	public String getFixdate() {
		return fix_date;
	}

	//��������
	public void setRecorddate(String Recorddate) {
		record_date = Recorddate;
	}

	public String getRecorddate() {
		return record_date;
	}

	//��ע1
	public void setMemo1(String Memo1) {
		memo1 = Memo1;
	}

	public String getMemo1() {
		return memo1;
	}

	public String getMemo1a() {
		if (memo1 == null) {
			return "0";
		}
		if (memo1.length() < 1) {
			return "0";
		}
		return memo1.substring(0, 1);
	}

	public String getMemo1b() {
		if (memo1 == null) {
			return "0";
		}
		if (memo1.length() < 2) {
			return "0";
		}
		return memo1.substring(1, 2);
	}
	//��ע2
	public String getMemo1c() {
		if (memo1 == null) {
			return "0";
		}
		if (memo1.length() < 3) {
			return "0";
		}
		return memo1.substring(2, 3);
	} 
	//��ע2

	public void setMemo2(String Memo2) {
		memo2 = Memo2;
	}

	public String getMemo2() {
		return memo2;
	}

	//��ע3
	public void setMemo3(String Memo3) {
		memo3 = Memo3;
	}

	public void setMct_name() throws MonitorException {
		if (merchant_id != "") {
			String MctName = "";
			SqlAccess sq = SqlAccess.createConn();
			if (sq == null) {
				throw new MonitorException(ErrorDefine.FAIL_DB_CONN,ErrorDefine.FAIL_DB_CONN_DESC);
			}
			try {
				String sql = "select mct_name from pos_merchant where merchant_id = '" +
				merchant_id + "'";
				ResultSet rs = sq.queryselect(sql);
				while (rs.next()) {
					MctName = rs.getString("mct_name");
				}
				rs.close();
			}catch (SQLException e) {
				throw new MonitorException(e);
			}finally {
				sq.close();
			}
			mct_name = MctName;
		}
	}

	public String getMemo3() {
		return memo3;
	}
	
	public String getMct_name() {
		return mct_name;
	}

	//ͨѶ����
	public void setNettype(String Nettype) {
		net_type = Nettype;
	}
	
	public String getNettype() {
		return net_type;
	}
	
	  /**
	   * ��Http������ȡPOS������Ϣ
	   * @param request Http����
	   */
	public void fetchData(HttpServletRequest request) {
		setPoscode(request.getParameter("pos_code"));
		setMerchantid(request.getParameter("merchant_id"));
		setRebate(request.getParameter("rebate"));
		setFrnRebate(request.getParameter("frn_rebate"));
		setOpenbankno(request.getParameter("open_bankno"));
		setExgno(request.getParameter("exg_no"));
		setAcctno(request.getParameter("acct_no"));
		setVacctno(request.getParameter("vacct_no"));
		setModel(request.getParameter("model"));
		setCounter(request.getParameter("counter"));
		setFixaddress(request.getParameter("fix_address"));
		setFixperson(request.getParameter("fix_person"));
		setFixdate(request.getParameter("fix_date"));
		setRecorddate(request.getParameter("record_date"));
		setMemo1(request.getParameter("memo1a") + request.getParameter("memo1b") + request.getParameter("memo1c"));
		Debug.println("Roxx:" + getMemo1());
		setMemo2(request.getParameter("memo2"));
		setMemo3(request.getParameter("memo3"));
		setNettype(request.getParameter("net_type"));
		setPosDccCode(request.getParameter("pos_dcc_code"));
		setPosCCode(request.getParameter("pos_c_code"));
		//add by liyp 20030703
		this.setPreAutorized(request.getParameter("pre_authorized"));
		this.setEquiptType(request.getParameter("equip_type"));
	}
	
	  /**
	   * �Ӳ�ѯ�����ȡPOS������Ϣ
	   * @param rst ��ѯ�����
	   * @throws SQLException
	   */
	public void fetchData(ResultSet rst) throws SQLException {
		setPoscode(toucsString.unConvert(rst.getString("pos_code")));
		setMerchantid(toucsString.unConvert(rst.getString("merchant_id")));
		setRebate(toucsString.unConvert(rst.getString("rebate")));
		setFrnRebate(toucsString.unConvert(rst.getString("rebate_fc")));
		setOpenbankno(toucsString.unConvert(rst.getString("open_bankno")));
		setExgno(toucsString.unConvert(rst.getString("exg_no")));
		setAcctno(toucsString.unConvert(rst.getString("acct_no")));
		setVacctno(toucsString.unConvert(rst.getString("vacct_no")));
		setModel(toucsString.unConvert(rst.getString("model")));
		setCounter(toucsString.unConvert(rst.getString("counter")));
		setFixaddress(toucsString.unConvert(rst.getString("fix_address")));
		setFixperson(toucsString.unConvert(rst.getString("fix_person")));
		setFixdate(toucsString.unConvert(rst.getString("fix_date")));
		setRecorddate(toucsString.unConvert(rst.getString("record_date")));
		setMemo1(toucsString.unConvert(rst.getString("memo1")));
		setMemo2(toucsString.unConvert(rst.getString("memo2")));
		setMemo3(toucsString.unConvert(rst.getString("memo3")));
		setNettype(toucsString.unConvert(rst.getString("net_type")));
		//add by liyp 20030704
		this.setEquiptType(rst.getString("equip_type"));
		this.setPreAutorized(rst.getString("pre_authorized"));
		this.setPosCCode(toucsString.unConvert(rst.getString("pos_c_code")));
		this.setPosDccCode(toucsString.unConvert(rst.getString("pos_dcc_code")));
		setTxnRight(toucsString.unConvert(rst.getString("tran_right")));
		setHandFlag(toucsString.unConvert(rst.getString("hand_flag")));
	}

	  /**
	   * ��Http������ȡPOS���׼�����������Ϣ
	   * @param request Http����
	   */
	public void fetchTxnData(HttpServletRequest request) throws MonitorException {
		this.setPoscode(request.getParameter("pos_code"));
		this.setEquiptType(request.getParameter("equip_type"));
		this.setPreAutorized(request.getParameter("pre_authorized"));
		setMemo1(request.getParameter("memo1a") + getMemo1b() + getMemo1c());
		
		//����Ҽ������POS
		char handMask[] = new char[MAXLENGTH];
		char txnMask[] = new char[MAXLENGTH];
		int sn = 0;
		for (int i = 0; i < MAXLENGTH; i++) {
			handMask[i] = '0';
			txnMask[i] = '0';
		}
		//��Ԥ��Ȩ�����Ԥ��Ȩ��ʹ��Ĭ������
		if (pre_authorized.equals("0") || pre_authorized.equals("1")) {
			try {
				Vector v = TxnCodeManageBean.queryPosTxnMask(pre_authorized,equip_type.charAt(0), getMemo1a().trim());
				Debug.println("txnCode:" + (String) v.get(0) + ":handMask:" + (String) v.get(1));
				this.setTxnRight( (String) v.get(0));
				this.setHandFlag( (String) v.get(1));
			} catch (SQLException e) {
				throw new MonitorException(e);
			}
			return; //����
		}
		String txnPermR[] = (String[]) request.getParameterValues("txnPermR");
		String handFlagR[] = (String[]) request.getParameterValues("handFlagR");
		String txnPermF[] = (String[]) request.getParameterValues("txnPermF");
		String handFlagF[] = (String[]) request.getParameterValues("handFlagF");

		if (equip_type.equals("1") || equip_type.equals("3")) {
			if (txnPermR != null) {
				for (int i = 0; i < txnPermR.length; i++) {
					try {
						sn = Integer.parseInt(txnPermR[i]);
						txnMask[sn] = '1';
					} catch (NumberFormatException e) {
						throw new MonitorException(ErrorDefine.INPUTDATAERROR,"POS����Ȩ������ʧ��!");
					}
				}
			}
			if (handFlagR != null) {
				for (int i = 0; i < handFlagR.length; i++) {
					try {
						sn = Integer.parseInt(handFlagR[i]);
						handMask[sn] = '1';
					} catch (NumberFormatException e) {
						throw new MonitorException(ErrorDefine.INPUTDATAERROR,"POS�������俨������ʧ��!");
					}
				}
			}
		}
		if (equip_type.equals("2") || equip_type.equals("3")) {
			if (txnPermF != null) {
				for (int i = 0; i < txnPermF.length; i++) {
					try {
						sn = Integer.parseInt(txnPermF[i]);
						if (txnMask[sn] == '1') {
							txnMask[sn] = '3'; //�����������Ҳ�������
						} else {
							txnMask[sn] = '2'; //�������
						}
					} catch (NumberFormatException e) {
						throw new MonitorException(ErrorDefine.INPUTDATAERROR,"POS����Ȩ������ʧ��!");
					}
				}
			}
			if (handFlagF != null) {
				for (int i = 0; i < handFlagF.length; i++) {
					try {
						sn = Integer.parseInt(handFlagF[i]);
						if (handMask[sn] == '1') {
							handMask[sn] = '3';
						} else {
							handMask[sn] = '2';
						}
					} catch (NumberFormatException e) {
						throw new MonitorException(ErrorDefine.INPUTDATAERROR,"POS�������俨������ʧ��!");
					}
				}
			}
		}
		Debug.println("************txnMask=" + this.getTxnRight());
		Debug.println("************handMask=" + this.getHandFlag());
		this.setTxnRight(String.valueOf(txnMask));
		this.setHandFlag(String.valueOf(handMask));
	}

	  /**
	   * �Ӳ�ѯ�����ȡPOS���׼�����������Ϣ
	   * @param rst ��ѯ�����
	   * @throws SQLException
	   */
	public void fetchTxnData(ResultSet rst) throws SQLException {
		setPoscode(toucsString.unConvert(rst.getString("pos_code")));
		this.setPosCCode(toucsString.unConvert(rst.getString("pos_c_code")));
		this.setPosDccCode(toucsString.unConvert(rst.getString("pos_dcc_code")));
		this.setPreAutorized(rst.getString("pre_authorized"));
		this.setEquiptType(rst.getString("equip_type"));
		this.setTxnRight(rst.getString("tran_right"));
		this.setHandFlag(rst.getString("hand_flag"));
		this.setMemo1(rst.getString("memo1"));
	}

	
	/**
	 * ��ѯpos��������
	 * @return int  ����
	 * @throws MonitorException
	 */
	public int posTxnNum() throws MonitorException{
		SqlAccess sq = SqlAccess.createConn();
		String num = "";
		String sql = "select count(*) from kernel_txn_code where sys_id = 'pos'";
		try {
			ResultSet rst = sq.queryselect(sql);
			if(rst != null && rst.next()){
				num = rst.getString(1);
				System.out.println("pos�ܽ�������:"+num);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(num);
	}
	
	
	
	  /**
	   * ����������ӵĶ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeInsertStm(Connection conn) throws SQLException, MonitorException {
		//modify by liyp 20031011 add last_date , last_serial
		String sql = "UPDATE  pos_info SET"
				   + "(pos_code,merchant_id,pos_stat,rebate,open_bankno,"
                   + "exg_no,acct_no,vacct_no,model,counter,"
                   + "fix_address,fix_person,fix_date,record_date,memo1,"
                   + "memo2,memo3,net_type,bill_no,pos_batch,"
                   + "pos_c_code,pos_dcc_code,equip_type,pre_authorized,tran_right,"
                   + "hand_flag,rebate_fc,last_date,last_serial)"
                   + "=(?,?,?,?,?,?,?,?,?,?,"
                   + "?,?,?,?,?,?,?,?,?,?,"
                   + "?,?,?,?,?,?,?,?,?)"
                   + "WHERE pos_code= ?";
		PreparedStatement stm = conn.prepareStatement(sql);
		System.out.println("INSERT POSINFO:" + sql);
		//ȡ��POSP���
		pos_code = getAFreePOSCode();
		pos_c_code = pos_code.substring(1, 9);
		stm.setString(1, pos_code);
		stm.setString(2, merchant_id);

		//begin add by liyp 20030808
		//Ĭ��״̬Ϊ����
		stm.setString(3, "1");
		//end add by liyp 20030808
		
		stm.setString(4, rebate);
		stm.setString(5, open_bankno);
		stm.setString(6, exg_no);
		stm.setString(7, acct_no);
		stm.setString(8, vacct_no);
		stm.setString(9, model);
		stm.setString(10, counter);
		stm.setString(11, fix_address);
		stm.setString(12, fix_person);
		stm.setString(13, fix_date);
		//��ȡ��ǰʱ�䣬����
		String curDate = Util.getCurrentDate();
		stm.setString(14, curDate);
		stm.setString(15, memo1);
		stm.setString(16, memo2);
		stm.setString(17, memo3);
		stm.setString(18, net_type);
		stm.setString(19, "000001");
		stm.setString(20, "000001");
		stm.setString(21, pos_c_code);
		stm.setString(22, pos_dcc_code);
		stm.setString(23, equip_type);
		stm.setString(24, pre_authorized);
		try {
			Vector v;
			Debug.println("~~~~~~~~~Memo1:[" + memo1 + "]");
			v = TxnCodeManageBean.queryPosTxnMask(pre_authorized,equip_type.charAt(0), memo1.substring(0,1));
			Debug.println("txnCode:" + (String) v.get(0) + ":handMask:" + (String) v.get(1));
			posTxnNum();//��ѯpos�ܽ�����
			stm.setString(25, ((String) v.get(0)).substring(0, 50));
			stm.setString(26, ((String) v.get(1)).substring(0, 50));
		} catch (SQLException e) {
			throw e;
		}
		stm.setString(27, frn_rebate);
		stm.setString(28, "");
		stm.setInt(29, 0);
		stm.setString(30, pos_code);
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
		boolean isCustom = false; //Ԥ��Ȩ�����Ƿ��Զ���
		Debug.println("**************pre_authorized:" + pre_authorized + "*****************");
		if (pre_authorized == null || pre_authorized == "") {
			isCustom = true;
		}
		String sql = "UPDATE pos_info SET " + "(merchant_id,rebate,open_bankno," + "exg_no,acct_no,vacct_no,model,counter,"
				   + "fix_address,fix_person,fix_date,memo1,"
                   + "memo2,memo3,net_type,pos_c_code,pos_dcc_code,rebate_fc";	
		//�����Ԥ��Ȩ����Ϊ�Զ��彻��Ȩ�����ͣ��������豸���͡�Ԥ��Ȩ���͡�����Ȩ�ޡ�����Ȩ��
		if (isCustom) {
			sql += ")" + " =(?,?,?,?,?," + "?,?,?,?,?," + "?,?,?,?,?," + "?,?,?)" + " WHERE pos_code=?";
		} else {
			sql += ",equip_type,pre_authorized,tran_right,hand_flag )" + " =(?,?,?,?,?," + "?,?,?,?,?,"
				 + "?,?,?,?,?," + "?,?,?,?,?," + "?,?)" + " WHERE pos_code=?";
		}
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, merchant_id);
		stm.setString(2, rebate);
		stm.setString(3, open_bankno);
		stm.setString(4, exg_no);
		stm.setString(5, acct_no);
		stm.setString(6, vacct_no);
		stm.setString(7, model);
		stm.setString(8, counter);
		stm.setString(9, fix_address);
		stm.setString(10, fix_person);
		stm.setString(11, fix_date);
		stm.setString(12, memo1);
		stm.setString(13, memo2);
		stm.setString(14, memo3);
		stm.setString(15, net_type);
		stm.setString(16, pos_c_code);
		stm.setString(17, pos_dcc_code);
		stm.setString(18, frn_rebate); //add by liyp 20030807
		if (isCustom) {
			stm.setString(19, key);
		} else {
			stm.setString(19, equip_type); //
			stm.setString(20, pre_authorized); //
			try {
				Vector v = TxnCodeManageBean.queryPosTxnMask(pre_authorized,equip_type.charAt(0), memo1.substring(0,1));
				Debug.println("txnCode:" + (String) v.get(0) + ":handMask:" + (String) v.get(1));
				stm.setString(21, ((String) v.get(0)).substring(0, 50)); //
				stm.setString(22, ((String) v.get(1)).substring(0, 50)); //
			} catch (SQLException e) {
				throw e;
			}
			stm.setString(23, key);
		}
		return stm;
	}

	  /**
	   * ���������޸Ľ��׼�����������Ϣ�Ķ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeTxnUpdateStm(Connection conn) throws SQLException {
		//50���ֶΣ�keyָ��Ҫ���µ�POS�ı��
		PreparedStatement stm;
		String sql = "UPDATE pos_info SET "
				   + "(pre_authorized,equip_type,tran_right,hand_flag,memo1)"
				   + " =(?,?,?,?,?)" + " WHERE pos_code=?";
		Debug.println(sql);
		
		stm = conn.prepareStatement(sql);
		stm.setString(1, pre_authorized); //�޸Ĺ����׼����俨����,Ԥ��Ȩ����Ϊ����
		stm.setString(2, equip_type);
		stm.setString(3, tran_right.substring(0, 50));
		System.out.println("tran_right"+tran_right.substring(0, 50));
		stm.setString(4, hand_flag.substring(0, 50));
		System.out.println("hand_flag"+hand_flag.substring(0, 50));
		stm.setString(5, memo1);
		stm.setString(6, pos_code);
		
		return stm;
	}

	  /**
	   * ����������������ͣ��״̬�ĵĶ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeStatStm(Connection conn, String key,boolean bStat) throws SQLException {
		String sql = "";
		if (bStat) {
			sql = "UPDATE pos_info SET " + "pos_stat=1" + " WHERE pos_code=?";
		} else {
			sql = "UPDATE pos_info SET " + "pos_stat=0" + " WHERE pos_code=?";
		}
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, key);
		return stm;
	}

	  /**
	   * �������ڸ����̻���ѯ�Ķ�̬SQL���
	   * @param conn ���ݿ����Ӷ���
	   * @param key �豸��ţ��ؼ��֣�
	   * @return ��̬SQL������
	   * @throws SQLException
	   */
	public PreparedStatement makeQueryByMctStm(Connection conn, String key) throws SQLException {
		String sql = "SELECT * FROM pos_info WHERE (merchant_id=?)";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, key);
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
		//17���ֶΣ�keyָ��Ҫɾ����POS�ı��
		String sql = "DELETE FROM pos_info WHERE " + "(pos_code = ?)";
		System.out.println("DELETE SQL:" + sql);	
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		//ɾ��������
		stm.setString(1, key);
		return stm;
	}

	  /**
	   * �ӿ���ȡ�õ�ǰpos��ſ��õ���Сֵ
	   * ����pos_info�������pos_code ֵ��1
	   * @param conn ���ݿ����Ӷ���
	   * @return pos ���
	   * @throws SQLException
	   */
	synchronized private String getAFreePOSCode() throws SQLException, MonitorException {
		Debug.println("*********wuye debug in GetAFreePosCode() ******************");
		SqlAccess sq =  SqlAccess.createConn();
		String rCode = "";
		String sql = " SELECT max(pos_code) FROM pos_info;";
		ResultSet rst = sq.queryselect(sql);
		while (rst.next()) {
			rCode = rst.getString(1);
		}
		if (rCode == null || rCode == "") {
			Debug.println("wuye debug ******* rCode=null ***");
			rCode = "P00000000";
		}
		int posNum;
		rCode = rCode.trim().substring(1, 9);
		try {
			posNum = Integer.parseInt(rCode);
			posNum += 1;
			posNum += 1e+8;
			rCode = String.valueOf(posNum);
			rCode = rCode.substring(1, 9);
			rCode = "P" + rCode;

			sql = "INSERT INTO pos_info VALUES('" + rCode + "','" +
			rCode.substring(1, 9) + "','" + rCode.substring(1, 9) + "',"
			+ "'','','','',0,0,0,0,0,0,'','','','','','','','','','','','','','','','',0,'','','')";
			Debug.println("****in getAFreePoscode() wuye debug  SQL:" + sql + "************");
			
			int affRows = sq.queryupdate(sql);
		} catch (NumberFormatException ne) {
			throw new MonitorException(MonitorException.SYSERR, ne.getMessage());
		} finally {
			sq.close();
		}
		
		return rCode;
	}

	  /**
	   * �ӿ���ȡ�õ�ǰpos��ŵ���Сֵ
	   * ����pos_info�������pos_code ֵ
	   * @param conn ���ݿ����Ӷ���
	   * @return pos ���
	   * @throws SQLException
	   */
	public void getLastPOSCode() throws SQLException, MonitorException {
		SqlAccess sq =  SqlAccess.createConn();
		String rCode = "";
		String sql = " SELECT max(pos_code) FROM pos_info;";
		ResultSet rst = sq.queryselect(sql);
		while (rst.next()) {
			rCode = rst.getString(1);
		}
		if (rCode == null || rCode == "") {
			Debug.println("panxu debug ******* rCode=null ***");
			rCode = "P00000000";
		}
		sq.close();
		System.out.println("---------------------rCode:" + rCode);
		
		SqlAccess sqtmp =  SqlAccess.createConn();
		sql = "SELECT "
			+ "pos_dcc_code,merchant_id,equip_type,pre_authorized,rebate,"
			+ "rebate_fc,open_bankno,exg_no,acct_no,vacct_no,"
			+ "model,counter,fix_address,fix_person,fix_date,"
			+ "net_type,memo1,memo2,memo3 "
			+ "FROM pos_info WHERE pos_code='" + rCode + "'";
		System.out.println("lastpos:" + sql);
		ResultSet rsttmp = sqtmp.queryselect(sql);
		if (rsttmp.next()) {
			pos_dcc_code = toucsString.unConvert(rsttmp.getString(1));
			System.out.println("pos_dcc_code:" + pos_dcc_code);
			merchant_id = toucsString.unConvert(rsttmp.getString(2));
			equip_type = toucsString.unConvert(rsttmp.getString(3));
			pre_authorized = toucsString.unConvert(rsttmp.getString(4));
			rebate = toucsString.unConvert(rsttmp.getString(5));

			frn_rebate = toucsString.unConvert(rsttmp.getString(6));
			open_bankno = toucsString.unConvert(rsttmp.getString(7));
			exg_no = toucsString.unConvert(rsttmp.getString(8));
			acct_no = toucsString.unConvert(rsttmp.getString(9));
			vacct_no = toucsString.unConvert(rsttmp.getString(10));
			
			model = toucsString.unConvert(rsttmp.getString(11));
			counter = toucsString.unConvert(rsttmp.getString(12));
			fix_address = toucsString.unConvert(rsttmp.getString(13));
			fix_person = toucsString.unConvert(rsttmp.getString(14));
			fix_date = toucsString.unConvert(rsttmp.getString(15));
			
			net_type = toucsString.unConvert(rsttmp.getString(16));
			memo1 = toucsString.unConvert(rsttmp.getString(17));
			memo2 = toucsString.unConvert(rsttmp.getString(18));
			memo3 = toucsString.unConvert(rsttmp.getString(19));
			System.out.println("-------------LAST POSINFO GET------------");
		}
		
		sqtmp.close();
	}

	public void ThrLastPOSCode() throws MonitorException {
		try {
			getLastPOSCode();
		} catch (SQLException e) {
			throw new MonitorException(e);
		}
	}
}
