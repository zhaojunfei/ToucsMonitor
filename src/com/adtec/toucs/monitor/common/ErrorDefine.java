package com.adtec.toucs.monitor.common;

/**
 * <p>Title: 错误码定义</p>
 * <p>Description: 定义了系统错误代码</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

/**
 *系统错误码定义规则：-MMXX（MM:模块代码、XX:错误序号）
 * -10XX:登录与权限控制
 * -20XX:卡表管理
 * -30XX:设备参数管理
 * -40XX：用户管理
 * -50XX:地图管理
 * -90XX:公共模块和未归类的错误
 */

public class ErrorDefine {
    /**************************************************************************************/
    //公共模块或未归类错误
    //未知错误
    public static final int UNKNOWERROR=-9001;
    //输入数据错误
    public static final int INPUTDATAERROR=-9002;
    //请求参数错误
    public static final int REQ_PARA_ERROR=-9003;
    //报文解析出错
    public static final int XMLPARSEERROR=-9004;
    //XML流为空
    public static final int XMLSTREAMISEMPTY=-9005;
    //连接ATMP失败
    public static final int CONNECTERROR=-9006;
    public static final int COMMATMPERROR=-9006;
    //ATMP返回的报文有误
    public static  final int RETMESSAGEISNULL=-9007;
    //读数据超时
    public static final int READDATATIMEOUT=-9008;
    //请求报文为空
    public static final int SENDDATAISNULL=-9009;
    //发送数据出错
    public static final int SENDDATAERROR=-9010;
    //接收数据出错
    public static final int RECEVICEDATAERROR=-9011;
    //Socket连接被关闭
    public static final int SOCKETISCLOSE=-9012;
    //输入流已关闭
    public static final int INPUTSTREAMISCLOSE=-9013;
    //输出流已关闭
    public static final int OUTPUTSTREAMISCLOSE=-9014;
    //取数据库连接失败
    public static final int FAIL_DB_CONN=-9015;
    //操作数据库失败，未知原因代码
    public static final int UNKNOWDBERR=-9016;
    //记录不存在，可能已经被其他用户删除
    public static final int RECNOTFOUND = -9017;
    //修改的密码不能与前3次的修改记录相同
    public static final int RECORDSAME = -9018;

    public static final String INPUTDATAERRORDESC="输入数据有误，请检查你的输入数据";
    public static final String UNKNOWERRORDESC="未知的系统错误，请重试";
    public static final String XMLPARSEERRORDESC="XML解析出错";
    public static final String XMLSTREAMISEMPTYDESC="XML流为空";
    public static final String RETMESSAGEISNULLDESC="ATMP返回的报文有误";
    public static final String SENDDATAISNULLDESC="请求报文为空";
    public static final String CONNECTERRORDESC="连接ATMP失败";
    public static final String SENDDATAERRORDESC="发送数据时出错";
    public static final String RECEVICEDATAERRORDESC="接受数据时出错";
    public static final String SOCKETISCLOSEDESC="Socket连接被关闭";
    public static final String OUTPUTSTREAMISCLOSEDESC="输出流已关闭";
    public static final String INPUTSTREAMISCLOSEDESC="输入流已关闭";
    public static final String COMMATMPERRORDESC="与ATMP通讯失败,请检查网络是否正常";
    public static final String FAIL_DB_CONN_DESC="取数据库连接失败";
    public static final String RECNOTFOUNDDESC = "记录不存在，可能已经被其他用户删除";
    public static final String UNKNOWDBERRDESC="操作数据库失败，未知原因";
    public static final String RECORDSAMEDESC="修改的密码不能与前3次的修改记录相同";
    /**************************************************************************************/
    //登录与权限控制
    //错误码：用户不存在
    public static final int INVALID=-1001;
    //错误码：密码错误
    public static final int ERRORPW=-1002;
    //错误码：重复登录（用户在不同的工作站登录）
    public static final int MULTI_LOGIN=-1003;
    //错误码：用户未登录
    public static final int NOT_LOGIN=-1004;
    //错误码：用户会话过期（离线）
    public static final int TIMEOUT=-1005;
    //错误码：权限不足
    public static final int NOT_AUTHORIZED=-1006;
    //错误码：访问数据库失败
    public static final int DB_ERROR=-1007;
    //错误码：资源标识错误
    public static final int ERR_RESOURCE=-1008;
    //错误码：用户处于无效状态
    public static final int NOT_USE=-1009;
    //错误码：取用户信息失败
    public static final int GETUSERINFOERROR=1010;
    public static final String GETUSERINFOERRORDESC="取用户信息失败，请重新登录";
    //长时间未登录,系统自动冻结此用户
    public static final int CONGEALUSER=-1011;
    public static final String CONGEALUSERDESC="长时间未登录,系统自动冻结此用户";

    /**************************************************************************************/
    public static final int DELETEEFFECTDBISZERO=-2001;
    //用户要修改的卡表信息已经被其他用户删除
    public static final int OTHERDELETETHISCARDID=-2002;
    //要修改的卡表信息未找到
    public static final int PLEASESELECTMODCARDINFOISNULL=-2003;
    //要删除的卡表信息未找到
    public static final int PLEASESELECTDELCARDINFOISNULL=-2003;
    //int值没有赋值
    public static final int intISNULL=-2004;
    //会话中的卡表信息已经过期，需要重新查询
    public static final int SESSONTIMEOUT=-2005;

    public static final String DELETEEFFECTDBISZERODESC="删除卡表不成功,该卡表信息可能已经被其他用户删除，请重新查询后再试";
    public static final String OTHERDELETETHISCARDIDDESC="该卡表信息已经被其他用户删除，请重新查询后再试";
    public static final String PLEASESELECTMODCARDINFOISNULLDESC="没有找到你要修改的卡表信息,请重新查询后再试";
    public static final String PLEASESELECTDELCARDINFOISNULLDESC="没有找到你要删除的卡表信息,请重新查询后再试";
    public static final String SESSONTIMEOUTDESC="会话中的卡表信息已经过期，需要重新查询";
    /**************************************************************************************/
    //设备管理
    //设备不存在
    public static final int ATM_NOT_EXIST=-3001;
    //未找到指定的信息
    public static final int NOT_FOUND=-3002;
    //添加设备失败
    public static final int REG_FAILED=-3003;
    //修改设备信息失败
    public static final int UPDATE_FAILED=-3004;
    //删除设备失败
    public static final int DELETE_FAILED=-3005;
    //钞箱未配置或信息不完整
    public static final int BOX_ERROR=-3006;
    //密钥未配置或信息不完整
    public static final int KEY_ERROR=-3007;
    //上送的数据报文错误
    public static final int DATA_ERROR=-3008;
    //给定的设备号不存在或不在管理范围
    public static final int NOTHISDEVICE=-3009;
    public static final String NOTHISDEVICEDESC="给定的设备号不存在或不在管理范围";
    /**************************************************************************************/
    //用户管理
    //Fancy Added BEGIN
    //两次输入的密码不相符
    public static final int PWDCONFIRMERR = -4001;
    public static final String PWDCONFIRMERRDESC = "两次输入的密码不相符";

    //机构或下级机构定义有ATM信息
    public static final int ATMEXISTERR = -4002;
    public static final String ATMEXISTDESC  = "机构或下级机构定义有ATM信息，请先删除ATM信息";

    //机构或下级机构定义有用户信息
    public static final int USEREXISTERR = -4008;
    public static final String USEREXISTDESC  = "机构或下级机构定义有用户信息，请先删除用户信息";

    //取角色编码失败！
    public static final int GETROLEIDERR = -4003;
    public static final String GETROLEIDERRDESC = "取角色编码失败";

    //取用户ID失败
    public static final int GETUSERIDERR = -4004;
    public static final String GETUSERIDERRDESC = "取用户ID失败";

    //原密码错误
    public static final int CHECKPWDERR = -4005;
    public static final String CHECKPWDERRDESC = "校验密码失败，请重新输入用户密码";

    //取系统交易代码失败
    public static final int GETTXCODEERR = -4006;
    public static final String GETTXCODEERRDESC = "取系统交易代码失败";

    //没有查询到任何交易流水信息
    public static final int NOTRANDETAILFOUND = -4007;
    public static final String NOTRANDETAILFOUNDDESC = "没有任何交易信息或者你没有权限查看这些信息";
    //Fancy Added END
    /**************************************************************************************/
    //地图管理
    //修改不成功
    public static final int EFFECTDBISZERO=-5001;
    //删除不成功
    public static final int DElETEDBISZERO=-5002;
    //地图信息不存在
    public static final int INPUTMAPINFORERROR=-5003;

    public static final String EFFECTDBISZERODESC="修改地图失败,输入条件不正确或该记录已经被其他用户删除";
    public static final String DElETEDBISZERODESC="删除地图失败，该记录可能已经被其他用户删除";
    public static final String INPUTMAPINFORERRORDESC="系统错误:地图文件上传失败，输入的地图信息不正确或地图文件路径不正确";
    //lihl add begin
    //添加商户失败
    public static final int ADDMCT_FAILED = -4008;
    //修改商户失败
    public static final int UPDATEMCT_FAILED = -4009;
    //商户下定义有POS信息，请先删除POS信息
    public static final int DELMCT_FAILED = -4010;
    //商户下清理标志为正常，请先清理商户
    public static final int DELMCT_FAILED1 = -4011;
    //机构或下级机构定义有商户信息，请先删除商户信息
    public static final int DELORG_FAILED2 = -4012;
    //机构或下级机构定义CDM设备信息，请先删除CDM设备信息
    public static final int DELORG_FAILED3 = -4013;
    //lihl add end

    //Fancy Added Begin
    //设备编号为空
    public static final int DEVICECODEISREQUIRED = 6001;
    public static final String DEVICECODEISREQUIREDDESC = "设备编号为空！";
    public static final int NULLPOINTEX = 10000;
    public static final String NULLPOINTEXDESC = "系统出现空指针异常，请联系系统管理员！";

}