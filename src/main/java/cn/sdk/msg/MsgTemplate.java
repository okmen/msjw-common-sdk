package cn.sdk.msg;
/**
 * 短信模板
 * @author Mbenben
 *
 */
public class MsgTemplate {
	/**
	 * 
	 * 深圳交警公众号短信模板
	 * @param valideteCode
	 * @return
	 */
	public static String getSzjjSendMsg(String valideteCode){
		String msgContent = "短信验证码："+valideteCode+"，您正在使用深圳交警微信业务，有效时间为5分钟。";
		return msgContent;
	}
	/**
	 * 东部预约短信模板
	 * @param valideteCode
	 * @return
	 */
	public static String getEastReservationMsg(String valideteCode){
		String msgContent = "短信验证码："+valideteCode+"，您正在使用深圳交警东部预约业务，有效时间为5分钟。";
		return msgContent;
	}
}
