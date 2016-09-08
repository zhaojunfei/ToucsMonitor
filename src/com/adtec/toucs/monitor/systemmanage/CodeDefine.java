package com.adtec.toucs.monitor.systemmanage;

/**
 * <p>Title:系统代码常量定义 </p>
 * <p>Description:定义了与系统代码相关的常量 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CodeDefine {
    //系统代码种类
    public static final String SYS_CLASS="0000";
    //系统错误代码
    public static final String SYS_ERROR="0010";
    //数据库错误代码
    public static final String DB_ERROR="0020";
    //设备类型
    public static final String ATM_TYPE="0030";
    //钞箱状态
    public static final String BOX_STAT="0040";
    //存钞箱状态
    public static final String SAVE_BOX_STAT="0050";
    //流水打印机状态
    public static final String FLOW_PRN_STAT="0060";
    //凭条打印机状态
    public static final String RECPT_PRN_STAT="0070";
    //存款模块状态
    public static final String EDP_STAT="0080";
    //磁卡读写器状态
    public static final String READW_STAT="0090";
    //键盘状态
    public static final String KEYBOARD_STAT="0100";
    //硬加密器状态
    public static final String DEM_STAT="0110";
    //IC卡读写模块状态
    public static final String ICREAD_STAT="0120";
    //PSAM模块状态
    public static final String PSAM_STAT="0130";
    //硬盘状态
    public static final String HD_STAT="0140";
    //自助设备（机械）状态
    public static final String MACHINE_STAT="0150";
    //网络状态
    public static final String NET_STAT="0160";
    //交易卡类别
    public static final String CARD_TYPE="0170";
    //报文类型
    public static final String MSG_TYPE="0180";
    //通讯协议类型
    public static final String NET_TYPE="0190";
    //ATM识别卡种
    public static final String CARD_REF_TYPE="0200";
    //加载标志
    public static final String LOAD_FLAG="0210";
    //发送标志
    public static final String SEND_FLAG="0220";
    //读卡器类型
    public static final String READER_MODE="0230";
    //ATM设备状态
    public static final String ATM_STAT="0240";
    //故障级别
    public static final String FAULT_LEVEL="0250";
    //是否停机标志
    public static final String HAULT_FLAG="0260";
    //通知标志
    public static final String NOTIFY_FLAG="0270";
    //卡表卡种类
    public static final String TB_CARD_CLASS="0280";
    //卡表卡类型
    public static final String TB_CARD_TYPE="0290";
    //卡表项启用标志
    public static final String CARD_USE_FLAG="0300";
    //用户状态
    public static final String USER_STAT="0310";
    //银行中心代码
    public static final String CENTER_TYPE="0320";
    //设备重要度
    public static final String ATM_LEVEL="0330";
    //币种
    public static final String CURRENCY_TYPE="0340";
    //平台内部交易码
    public static final String INNER_TRAN_CODE="0350";
    //交易应答码
    public static final String TRAN_ECHO_CODE="0360";
    //设备安装地点类别
    public static final String ADDR_TYPE="0370";
    //ATM设备运行状态
    public static final String RUN_STAT="0500";
    //DES加密方式
    public static final String DES_TYPE="0380";
    //PINBLOCK格式
    public static final String PINBLOCK="0390";
    //MAC算法
    public static final String MAC_METHOD="0400";
    //设备部件类别
    public static final String DEV_PART_CLASS="0500";
    //吞卡原因代码
    public static final String CARD_SWALLOW_RSN="0510";
    //控制板状态
    public static final String CTRL_FACE_STAT="0520";
    //保险箱状态
    public static final String SAFE_BOX_STAT="0530";
    //出钞模块状态
    public static final String CDM_STAT="0540";
    //CDM钞箱状态
    public static final String CDM_BOX_STAT="0559";
    //MIT打印机状态
    public static final String MIT_PRN_STAT ="0561";

    //MIT读卡器状态
    public static final String MIT_READW_STAT ="0562";
    //MIT键盘状态
    public static final String MIT_KEYBOARD_STAT ="0563";
    //打印机状态
    public static final String PEM_PRN_STAT ="0564";
    //服务状态
    public static final String PEM_SELFDEVICE_STAT ="0565";
    //*****ATMP交易码
    //ATM加载
    public static final String ATMPREQ_ATMLOAD="MG7510";
    //ATM删除
    public static final String ATMPREQ_ATMDEL="MG7590";

    //加密机启用
    public static final String ATMPREQ_ENCRYLOAD="MG7502";

    //POS设备接入项目，Fancy增加（BEGIN)
    //POS签到
    public static final String POS_REGIST = "MG7810";
    //密钥下载
    public static final String POS_KEYDOWN = "MG7820";
    //对公帐户授权码下载
    public static final String POS_AUTHDOWN = "MG7830";
    //主密钥下载
    public static final String MASTER_KEYDOWN = "MG7001";
    //主密钥下载(new)
    public static final String NEW_MASTER_KEYDOWN = "MG7002";

    //CDM设备接入，Fancy增加（BEGIN）
    public static final String CDM_REGIST = "MG7610";
    //CDM设备接入，Fancy增加（END）

    //MIT签到
    public static final String MIT_REGIST ="MG7910";
    //PEM签到
    public static final String PEM_REGIST ="MG7710";

    //*****ATMP处理结果码
    //ATMP处理成功
    public static final String ATMPCODE_SUCCESS="00";

    //*****代业对表管
    //**渠道编号
    public static final String CHANNEL_ID ="0567";
    //**业务种类
    public static final String TRADE_TYPE ="0568";
}