package cn.sdk.exception;
/**
 * 接口API的返回码，关于错误码的定义，请参考文档【<a href="http://wiki.choumei.me/pages/viewpage.action?pageId=1277956">API错误代码及系统异常处理</a>】
 * @author wubinhong
 */
public class ResultCode {

	/** 成功*/
	public static final Integer SUCCESS = 0;
	
	/** 未知错误，【异常错误】*/
	public static final Integer SYS_UNKNOWN = 10000;
	public static final String SYS_UNKNOWN_MSG = "未知错误";
	/** 非法请求 - BUNDLE验证失败，【异常错误】*/
	public static final Integer SYS_INVALID_REQUEST = 10011;
	public static final String SYS_INVALID_REQUEST_MSG = "BUNDLE验证失败";
	/** access token过期*/
	public static final Integer SYS_ACCESS_TOKEN_EXPIRED = 10609;
	public static final String SYS_ACCESS_TOKEN_EXPIRED_MSG = "access token过期";
	/** refreshToken失效，或者不正确*/
	public static final Integer SYS_INVALID_REFRESH_TOKEN = 10614;
	public static final String SYS_INVALID_REFRESH_TOKEN_MSG = "refreshToken失效";
	/** 解密token失败，【异常错误】*/
	public static final Integer SYS_INVALID_TOKEN = 10689;
	public static final String SYS_INVALID_TOKEN_MSG = "解密token失败";
		/** 参数校验错误，【异常错误】*/
	public static final Integer SYS_INVALID_REQUEST_PARAMS = 10022;
	public static final String SYS_INVALID_REQUEST_PARAMS_MSG = "参数校验失败";
	/** 数据库异常，【异常错误】*/
	public static final Integer SYS_DB_EXCEPTION = 10100;
	public static final String SYS_DB_EXCEPTION_MSG = "数据库异常";
	/** SQL异常，【异常错误】*/
	public static final Integer SYS_DB_SQL_EXCEPTION = 10101;
	public static final String SYS_DB_SQL_EXCEPTION_MSG = "SQL异常";
	/** 无结果集，【异常错误】*/
	public static final Integer SYS_DB_NO_RESULT_EXCEPTION = 10102;
	public static final String SYS_DB_NO_RESULT_EXCEPTION_MSG = "无结果集";
	
	public static final Integer SYS_SERVICE_EXCEPTION = 10103;
    public static final String SYS_SERVICE_EXCEPTION_MSG = "服务器异常";
    
    public static final Integer SYS_OVER_FREQUENCY = 10104;
    public static final String SYS_OVER_FREQUENCY_MSG = "请求频率太高";
    
    public static final Integer SYS_REQUEST_NOT_AUTHORIZED = 10105;
    public static final String SYS_REQUEST_NOT_AUTHORIZED_MSG = "操作未授权";
	
	/** 用户名或密码错误*/
	public static final Integer USER_USERNAME_PASSWORD_DISMATCH = 11010;
	public static final String USER_USERNAME_PASSWORD_DISMATCH_MSG = "用户名或密码错误";
	/** 用户名已存在*/
	public static final Integer USER_USERNAME_EXISTS = 11015;
	public static final String USER_USERNAME_EXISTS_MSG = "用户名已存在";
	
	public static final Integer USER_DISABLED = 11016;
    public static final String USER_DISABLED_MSG = "账号被停用，请联系客服400-9933-800";
	
	/****************************商家 result code*******************************/
	/** 项目非在售*/
    public static final Integer ITEM_NOT_ONSALE = 12001;
    public static final String ITEM_NOT_ONSALE_MSG = "项目已下架";
    
    /** 项目非在售*/
    public static final Integer SALON_CLOSED = 12002;
    public static final String SALON_CLOSED_MSG = "店铺已终止合作";
    
    /** 项目非在售*/
    public static final Integer PRICE_RANGE_NULL = 12003;
    public static final String PRICE_RANGE_NULL_MSG = "店铺已终止合作";
    
    /****************************商家 result code*******************************/
	
	public static final Integer USER_NOT_EXISTS = 11016;
    public static final String USER_NOT_EXISTS_MSG = "用户不存在";
    
    public static final Integer USER_IN_BLACK_LIST = 11017;
    public static final String USER_IN_BLACK_LIST_MSG = "您目前处于黑名单状态，不允许下单，若有疑问请致电臭美客服 400-9933-800";
    
    public static final Integer VOUCHER_INVALID_STATUS = 14001;
    public static final String VOUCHER_INVALID_STATUS_MSG = "代金券状态不正确";
    
    public static final Integer VOUCHER_EARLIER_THAN_GAINABLE_TIME = 14002;
    public static final String VOUCHER_EARLIER_THAN_GAINABLE_TIME_MSG = "早于可获取时间";
    
    public static final Integer VOUCHER_LATER_THAN_GAINABLE_TIME = 14003;
    public static final String VOUCHER_LATER_THAN_GAINABLE_TIME_MSG = "晚于可获取时间";
    
    public static final Integer SMS_OUTOF_SEND_NUM = 15001;
    public static final String SMS_OUTOF_SEND_NUM_MSG = "短信次数已用完，请24小时后再试";
    
    
    /****************************微信 result code*******************************/
    public static final Integer ERR_WECHAT_OPENID_NOT_BIND = 11017;
    public static final String ERR_WECHAT_OPENID_NOT_BIND_MSG = "未绑定openId"; 
    
    
    /****************************评论 result code*******************************/
    public static final Integer COMMENT_ORDER_ISCOMMENTED = 19001;
    public static final String COMMENT_ORDER_ISCOMMENTED_MSG = "订单已经被评论";
    
    
    /****************************定妆 result code*******************************/
    public static final Integer ARTIFICER_NOT_EXISTS = 17001;
    public static final String ARTIFICER_NOT_EXISTS_MSG = "该定妆师不存在或已结束合作";
    
    public static final Integer BEAUTY_ITEM_NOT_EXISTS = 17002;
    public static final String BEAUTY_ITEM_NOT_EXISTS_MSG = "该项目不存在或者已下架";
    
    public static final Integer BOOKER_CANT_RECOMMENDED = 17003;
    public static final String BOOKER_CANT_RECOMMENDED_MSG = "该用户不能被推荐";
    
    /****************************退款 result code*******************************/
    public final static int NORMAL_REFUND_BOOKING_ORDER = 24000;

    public final static int ERR_APPLY_BOOKING_ORDER_REFUND = 24001;
    public final static String ERR_APPLY_BOOKING_ORDER_REFUND_MSG = "发生异常，申请退款失败";
    
    public final static int ERR_REFUND_ORDER_SN = 24002;
    public final static String ERR_REFUND_ORDER_SN_MSG = "申请退款失败：订单号不正确";
    
    public final static int ERR_REFUND_USER_ID = 24003;
    public final static String ERR_REFUND_USER_ID_MSG = "申请退款失败：用户无法与该订单匹配";
    
    public final static int ERR_REFUND_STATUS = 24004;
    public final static String ERR_REFUND_STATUS_MSG = "申请退款失败：订单状态异常";
    
    public final static int ERR_REFUND_FAIL_ADD_REFUND_RECORD = 24005;
    public final static String ERR_REFUND_FAIL_ADD_REFUND_RECORD_MSG = "申请退款失败：添加退款记录失败";
    
    public final static int ERR_REFUND_FAIL_UPDATE_ORDER_STATUS = 24006;
    public final static String ERR_REFUND_FAIL_UPDATE_ORDER_STATUS_MSG = "申请退款失败: 无法更新预约单状态";

    public final static int ERR_GET_REFUND_LIST = 24007;
    public final static String ERR_GET_REFUND_LIST_MSG = "发生异常，获取退款列表失败";

    public final static int ERR_GET_REFUND_DETAIL = 24008;
    public final static String ERR_GET_REFUND_DETAIL_MSG = "发生异常，获取退款详情失败";
    
    public final static int ERR_REFUND_BOOKING_ORDER = 24009;
    public final static String ERR_REFUND_BOOKING_ORDER_MSG = "无法根据订单编号获取对应的订单记录和退款记录";
    
    public final static int ERR_REFUND_MANAGER_UID = 24010;
    public final static String ERR_REFUND_MANAGER_UID_MSG = "申请退款失败：此订单为后台代下的预约单，无法通过线上退款";
    
    public final static int ERR_REFUND_VERIFICATION_CODE = 24011;
    public final static String ERR_REFUND_VERIFICATION_CODE_MSG = "申请退款失败：验证码错误";
    
    public final static int ERR_REFUND_SUBSTITUTOR_ID = 24012;
    public final static String ERR_REFUND_SUBSTITUTOR_ID_MSG = "申请退款失败：代预约人无法与该订单匹配";

    public static final int ERR_EMPTY_REFUND_VERIFICATION_SMS = 24013;

    public static final String ERR_EMPTY_REFUND_VERIFICATION_SMS_MSG = "发送退款确认短信失败：无法生成短信内容";

    public static final int ERR_SEND_REFUND_VERIFICATION_SMS = 24014;

    public static final String ERR_SEND_REFUND_VERIFICATION_SMS_MSG = "发送退款确认短信失败: 无法发送短信";
    
}
