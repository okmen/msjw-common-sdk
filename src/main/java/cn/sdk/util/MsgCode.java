package cn.sdk.util;
/**
 * 消息码
 * @author Mbenben
 *
 */
public class MsgCode {
	public static String success = "0000"; //成功
	public static String exception = "500"; //方法异常
	public static String paramsError = "0001"; //参数错误
	public static String businessError = "0002"; //业务失败
	public static String webServiceCallError = "1000"; //警司通webservice无法调用
	public static String webServiceCallMsg = "错误1000";
	public static String httpPingAnCallError = "2000"; //平安接口无法调用
	public static String httpPingAnCallMsg = "错误2000";
	public static String systemMsg = "系统异常";
}
