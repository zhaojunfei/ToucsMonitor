package com.adtec.toucs.monitor.systemmanage;

/**
 * <p>Title:ϵͳ���볣������ </p>
 * <p>Description:��������ϵͳ������صĳ��� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeDefine {
    //ϵͳ��������
    public static final String SYS_CLASS="0000";
    //ϵͳ�������
    public static final String SYS_ERROR="0010";
    //���ݿ�������
    public static final String DB_ERROR="0020";
    //�豸����
    public static final String ATM_TYPE="0030";
    //����״̬
    public static final String BOX_STAT="0040";
    //�泮��״̬
    public static final String SAVE_BOX_STAT="0050";
    //��ˮ��ӡ��״̬
    public static final String FLOW_PRN_STAT="0060";
    //ƾ����ӡ��״̬
    public static final String RECPT_PRN_STAT="0070";
    //���ģ��״̬
    public static final String EDP_STAT="0080";
    //�ſ���д��״̬
    public static final String READW_STAT="0090";
    //����״̬
    public static final String KEYBOARD_STAT="0100";
    //Ӳ������״̬
    public static final String DEM_STAT="0110";
    //IC����дģ��״̬
    public static final String ICREAD_STAT="0120";
    //PSAMģ��״̬
    public static final String PSAM_STAT="0130";
    //Ӳ��״̬
    public static final String HD_STAT="0140";
    //�����豸����е��״̬
    public static final String MACHINE_STAT="0150";
    //����״̬
    public static final String NET_STAT="0160";
    //���׿����
    public static final String CARD_TYPE="0170";
    //��������
    public static final String MSG_TYPE="0180";
    //ͨѶЭ������
    public static final String NET_TYPE="0190";
    //ATMʶ����
    public static final String CARD_REF_TYPE="0200";
    //���ر�־
    public static final String LOAD_FLAG="0210";
    //���ͱ�־
    public static final String SEND_FLAG="0220";
    //����������
    public static final String READER_MODE="0230";
    //ATM�豸״̬
    public static final String ATM_STAT="0240";
    //���ϼ���
    public static final String FAULT_LEVEL="0250";
    //�Ƿ�ͣ����־
    public static final String HAULT_FLAG="0260";
    //֪ͨ��־
    public static final String NOTIFY_FLAG="0270";
    //��������
    public static final String TB_CARD_CLASS="0280";
    //��������
    public static final String TB_CARD_TYPE="0290";
    //���������ñ�־
    public static final String CARD_USE_FLAG="0300";
    //�û�״̬
    public static final String USER_STAT="0310";
    //�������Ĵ���
    public static final String CENTER_TYPE="0320";
    //�豸��Ҫ��
    public static final String ATM_LEVEL="0330";
    //����
    public static final String CURRENCY_TYPE="0340";
    //ƽ̨�ڲ�������
    public static final String INNER_TRAN_CODE="0350";
    //����Ӧ����
    public static final String TRAN_ECHO_CODE="0360";
    //�豸��װ�ص����
    public static final String ADDR_TYPE="0370";
    //ATM�豸����״̬
    public static final String RUN_STAT="0500";
    //DES���ܷ�ʽ
    public static final String DES_TYPE="0380";
    //PINBLOCK��ʽ
    public static final String PINBLOCK="0390";
    //MAC�㷨
    public static final String MAC_METHOD="0400";
    //�豸�������
    public static final String DEV_PART_CLASS="0500";
    //�̿�ԭ�����
    public static final String CARD_SWALLOW_RSN="0510";
    //���ư�״̬
    public static final String CTRL_FACE_STAT="0520";
    //������״̬
    public static final String SAFE_BOX_STAT="0530";
    //����ģ��״̬
    public static final String CDM_STAT="0540";
    //CDM����״̬
    public static final String CDM_BOX_STAT="0559";
    //MIT��ӡ��״̬
    public static final String MIT_PRN_STAT ="0561";

    //MIT������״̬
    public static final String MIT_READW_STAT ="0562";
    //MIT����״̬
    public static final String MIT_KEYBOARD_STAT ="0563";
    //��ӡ��״̬
    public static final String PEM_PRN_STAT ="0564";
    //����״̬
    public static final String PEM_SELFDEVICE_STAT ="0565";
    //*****ATMP������
    //ATM����
    public static final String ATMPREQ_ATMLOAD="MG7510";
    //ATMɾ��
    public static final String ATMPREQ_ATMDEL="MG7590";

    //���ܻ�����
    public static final String ATMPREQ_ENCRYLOAD="MG7502";

    //POS�豸������Ŀ��Fancy���ӣ�BEGIN)
    //POSǩ��
    public static final String POS_REGIST = "MG7810";
    //��Կ����
    public static final String POS_KEYDOWN = "MG7820";
    //�Թ��ʻ���Ȩ������
    public static final String POS_AUTHDOWN = "MG7830";
    //����Կ����
    public static final String MASTER_KEYDOWN = "MG7001";
    //����Կ����(new)
    public static final String NEW_MASTER_KEYDOWN = "MG7002";

    //CDM�豸���룬Fancy���ӣ�BEGIN��
    public static final String CDM_REGIST = "MG7610";
    //CDM�豸���룬Fancy���ӣ�END��

    //MITǩ��
    public static final String MIT_REGIST ="MG7910";
    //PEMǩ��
    public static final String PEM_REGIST ="MG7710";

    //*****ATMP��������
    //ATMP����ɹ�
    public static final String ATMPCODE_SUCCESS="00";

    //*****��ҵ�Ա��
    //**�������
    public static final String CHANNEL_ID ="0567";
    //**ҵ������
    public static final String TRADE_TYPE ="0568";
}