package cn.sdk.util;

/**
 * @ClassName: RespCode
 * @Description: TODO(接口Code码)
 * @author yangzan
 * @date 2017年5月12日
 */
public class RespCode {
	public static String SUCCESS = "0000";  //返回成功
	public static String SUCCESS_MSG = "操作成功";  //返回成功消息
	
	public static String PARAMES_ERROR = "1001";  //参数异常
	public static String PARAMES_ERROR_MSG = "请求参数错误";  //参数异常
	
	public static String BUSINESS_ERROR = "1002";  //业务异常
	public static String BUSINESS_ERROR_MSG = "业务异常";
	
	public static String SYS_ERROR = "5000";//系统异常
	public static String SYS_ERROR_MSG = "服务器繁忙,请稍后重试!";
	
	
	
}
