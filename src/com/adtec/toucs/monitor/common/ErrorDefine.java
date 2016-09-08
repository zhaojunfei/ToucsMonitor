package com.adtec.toucs.monitor.common;

/**
 * <p>Title: �����붨��</p>
 * <p>Description: ������ϵͳ�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

/**
 *ϵͳ�����붨�����-MMXX��MM:ģ����롢XX:������ţ�
 * -10XX:��¼��Ȩ�޿���
 * -20XX:�������
 * -30XX:�豸��������
 * -40XX���û�����
 * -50XX:��ͼ����
 * -90XX:����ģ���δ����Ĵ���
 */

public class ErrorDefine {
    /**************************************************************************************/
    //����ģ���δ�������
    //δ֪����
    public static final int UNKNOWERROR=-9001;
    //�������ݴ���
    public static final int INPUTDATAERROR=-9002;
    //�����������
    public static final int REQ_PARA_ERROR=-9003;
    //���Ľ�������
    public static final int XMLPARSEERROR=-9004;
    //XML��Ϊ��
    public static final int XMLSTREAMISEMPTY=-9005;
    //����ATMPʧ��
    public static final int CONNECTERROR=-9006;
    public static final int COMMATMPERROR=-9006;
    //ATMP���صı�������
    public static  final int RETMESSAGEISNULL=-9007;
    //�����ݳ�ʱ
    public static final int READDATATIMEOUT=-9008;
    //������Ϊ��
    public static final int SENDDATAISNULL=-9009;
    //�������ݳ���
    public static final int SENDDATAERROR=-9010;
    //�������ݳ���
    public static final int RECEVICEDATAERROR=-9011;
    //Socket���ӱ��ر�
    public static final int SOCKETISCLOSE=-9012;
    //�������ѹر�
    public static final int INPUTSTREAMISCLOSE=-9013;
    //������ѹر�
    public static final int OUTPUTSTREAMISCLOSE=-9014;
    //ȡ���ݿ�����ʧ��
    public static final int FAIL_DB_CONN=-9015;
    //�������ݿ�ʧ�ܣ�δ֪ԭ�����
    public static final int UNKNOWDBERR=-9016;
    //��¼�����ڣ������Ѿ��������û�ɾ��
    public static final int RECNOTFOUND = -9017;
    //�޸ĵ����벻����ǰ3�ε��޸ļ�¼��ͬ
    public static final int RECORDSAME = -9018;

    public static final String INPUTDATAERRORDESC="���������������������������";
    public static final String UNKNOWERRORDESC="δ֪��ϵͳ����������";
    public static final String XMLPARSEERRORDESC="XML��������";
    public static final String XMLSTREAMISEMPTYDESC="XML��Ϊ��";
    public static final String RETMESSAGEISNULLDESC="ATMP���صı�������";
    public static final String SENDDATAISNULLDESC="������Ϊ��";
    public static final String CONNECTERRORDESC="����ATMPʧ��";
    public static final String SENDDATAERRORDESC="��������ʱ����";
    public static final String RECEVICEDATAERRORDESC="��������ʱ����";
    public static final String SOCKETISCLOSEDESC="Socket���ӱ��ر�";
    public static final String OUTPUTSTREAMISCLOSEDESC="������ѹر�";
    public static final String INPUTSTREAMISCLOSEDESC="�������ѹر�";
    public static final String COMMATMPERRORDESC="��ATMPͨѶʧ��,���������Ƿ�����";
    public static final String FAIL_DB_CONN_DESC="ȡ���ݿ�����ʧ��";
    public static final String RECNOTFOUNDDESC = "��¼�����ڣ������Ѿ��������û�ɾ��";
    public static final String UNKNOWDBERRDESC="�������ݿ�ʧ�ܣ�δ֪ԭ��";
    public static final String RECORDSAMEDESC="�޸ĵ����벻����ǰ3�ε��޸ļ�¼��ͬ";
    /**************************************************************************************/
    //��¼��Ȩ�޿���
    //�����룺�û�������
    public static final int INVALID=-1001;
    //�����룺�������
    public static final int ERRORPW=-1002;
    //�����룺�ظ���¼���û��ڲ�ͬ�Ĺ���վ��¼��
    public static final int MULTI_LOGIN=-1003;
    //�����룺�û�δ��¼
    public static final int NOT_LOGIN=-1004;
    //�����룺�û��Ự���ڣ����ߣ�
    public static final int TIMEOUT=-1005;
    //�����룺Ȩ�޲���
    public static final int NOT_AUTHORIZED=-1006;
    //�����룺�������ݿ�ʧ��
    public static final int DB_ERROR=-1007;
    //�����룺��Դ��ʶ����
    public static final int ERR_RESOURCE=-1008;
    //�����룺�û�������Ч״̬
    public static final int NOT_USE=-1009;
    //�����룺ȡ�û���Ϣʧ��
    public static final int GETUSERINFOERROR=1010;
    public static final String GETUSERINFOERRORDESC="ȡ�û���Ϣʧ�ܣ������µ�¼";
    //��ʱ��δ��¼,ϵͳ�Զ�������û�
    public static final int CONGEALUSER=-1011;
    public static final String CONGEALUSERDESC="��ʱ��δ��¼,ϵͳ�Զ�������û�";

    /**************************************************************************************/
    public static final int DELETEEFFECTDBISZERO=-2001;
    //�û�Ҫ�޸ĵĿ�����Ϣ�Ѿ��������û�ɾ��
    public static final int OTHERDELETETHISCARDID=-2002;
    //Ҫ�޸ĵĿ�����Ϣδ�ҵ�
    public static final int PLEASESELECTMODCARDINFOISNULL=-2003;
    //Ҫɾ���Ŀ�����Ϣδ�ҵ�
    public static final int PLEASESELECTDELCARDINFOISNULL=-2003;
    //intֵû�и�ֵ
    public static final int intISNULL=-2004;
    //�Ự�еĿ�����Ϣ�Ѿ����ڣ���Ҫ���²�ѯ
    public static final int SESSONTIMEOUT=-2005;

    public static final String DELETEEFFECTDBISZERODESC="ɾ�������ɹ�,�ÿ�����Ϣ�����Ѿ��������û�ɾ���������²�ѯ������";
    public static final String OTHERDELETETHISCARDIDDESC="�ÿ�����Ϣ�Ѿ��������û�ɾ���������²�ѯ������";
    public static final String PLEASESELECTMODCARDINFOISNULLDESC="û���ҵ���Ҫ�޸ĵĿ�����Ϣ,�����²�ѯ������";
    public static final String PLEASESELECTDELCARDINFOISNULLDESC="û���ҵ���Ҫɾ���Ŀ�����Ϣ,�����²�ѯ������";
    public static final String SESSONTIMEOUTDESC="�Ự�еĿ�����Ϣ�Ѿ����ڣ���Ҫ���²�ѯ";
    /**************************************************************************************/
    //�豸����
    //�豸������
    public static final int ATM_NOT_EXIST=-3001;
    //δ�ҵ�ָ������Ϣ
    public static final int NOT_FOUND=-3002;
    //����豸ʧ��
    public static final int REG_FAILED=-3003;
    //�޸��豸��Ϣʧ��
    public static final int UPDATE_FAILED=-3004;
    //ɾ���豸ʧ��
    public static final int DELETE_FAILED=-3005;
    //����δ���û���Ϣ������
    public static final int BOX_ERROR=-3006;
    //��Կδ���û���Ϣ������
    public static final int KEY_ERROR=-3007;
    //���͵����ݱ��Ĵ���
    public static final int DATA_ERROR=-3008;
    //�������豸�Ų����ڻ��ڹ���Χ
    public static final int NOTHISDEVICE=-3009;
    public static final String NOTHISDEVICEDESC="�������豸�Ų����ڻ��ڹ���Χ";
    /**************************************************************************************/
    //�û�����
    //Fancy Added BEGIN
    //������������벻���
    public static final int PWDCONFIRMERR = -4001;
    public static final String PWDCONFIRMERRDESC = "������������벻���";

    //�������¼�����������ATM��Ϣ
    public static final int ATMEXISTERR = -4002;
    public static final String ATMEXISTDESC  = "�������¼�����������ATM��Ϣ������ɾ��ATM��Ϣ";

    //�������¼������������û���Ϣ
    public static final int USEREXISTERR = -4008;
    public static final String USEREXISTDESC  = "�������¼������������û���Ϣ������ɾ���û���Ϣ";

    //ȡ��ɫ����ʧ�ܣ�
    public static final int GETROLEIDERR = -4003;
    public static final String GETROLEIDERRDESC = "ȡ��ɫ����ʧ��";

    //ȡ�û�IDʧ��
    public static final int GETUSERIDERR = -4004;
    public static final String GETUSERIDERRDESC = "ȡ�û�IDʧ��";

    //ԭ�������
    public static final int CHECKPWDERR = -4005;
    public static final String CHECKPWDERRDESC = "У������ʧ�ܣ������������û�����";

    //ȡϵͳ���״���ʧ��
    public static final int GETTXCODEERR = -4006;
    public static final String GETTXCODEERRDESC = "ȡϵͳ���״���ʧ��";

    //û�в�ѯ���κν�����ˮ��Ϣ
    public static final int NOTRANDETAILFOUND = -4007;
    public static final String NOTRANDETAILFOUNDDESC = "û���κν�����Ϣ������û��Ȩ�޲鿴��Щ��Ϣ";
    //Fancy Added END
    /**************************************************************************************/
    //��ͼ����
    //�޸Ĳ��ɹ�
    public static final int EFFECTDBISZERO=-5001;
    //ɾ�����ɹ�
    public static final int DElETEDBISZERO=-5002;
    //��ͼ��Ϣ������
    public static final int INPUTMAPINFORERROR=-5003;

    public static final String EFFECTDBISZERODESC="�޸ĵ�ͼʧ��,������������ȷ��ü�¼�Ѿ��������û�ɾ��";
    public static final String DElETEDBISZERODESC="ɾ����ͼʧ�ܣ��ü�¼�����Ѿ��������û�ɾ��";
    public static final String INPUTMAPINFORERRORDESC="ϵͳ����:��ͼ�ļ��ϴ�ʧ�ܣ�����ĵ�ͼ��Ϣ����ȷ���ͼ�ļ�·������ȷ";
    //lihl add begin
    //����̻�ʧ��
    public static final int ADDMCT_FAILED = -4008;
    //�޸��̻�ʧ��
    public static final int UPDATEMCT_FAILED = -4009;
    //�̻��¶�����POS��Ϣ������ɾ��POS��Ϣ
    public static final int DELMCT_FAILED = -4010;
    //�̻��������־Ϊ���������������̻�
    public static final int DELMCT_FAILED1 = -4011;
    //�������¼������������̻���Ϣ������ɾ���̻���Ϣ
    public static final int DELORG_FAILED2 = -4012;
    //�������¼���������CDM�豸��Ϣ������ɾ��CDM�豸��Ϣ
    public static final int DELORG_FAILED3 = -4013;
    //lihl add end

    //Fancy Added Begin
    //�豸���Ϊ��
    public static final int DEVICECODEISREQUIRED = 6001;
    public static final String DEVICECODEISREQUIREDDESC = "�豸���Ϊ�գ�";
    public static final int NULLPOINTEX = 10000;
    public static final String NULLPOINTEXDESC = "ϵͳ���ֿ�ָ���쳣������ϵϵͳ����Ա��";

}