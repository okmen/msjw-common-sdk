package cn.sdk.msg;

import org.apache.commons.lang3.StringUtils;

/**
 * 短信模板
 * @author Mbenben
 *
 */
public class MsgTemplate {
	public static String szjj = "szjj"; //深圳交警公众号
	public static String easternReservation = "easternReservation"; //东部预约
	/**
	 * 
	 * 深圳交警公众号短信模板
	 * @param valideteCode
	 * @return
	 */
	public static String getSzjjSendMsgTemplate(String valideteCode,String businessType){
		String msgContent = "";
		if(StringUtils.isNotBlank(businessType)){
			if(szjj.equals(businessType)){
				msgContent= "短信验证码："+valideteCode+"，您正在使用深圳交警微信业务，有效时间为5分钟。";
			}
			if(easternReservation.equals(businessType)){
				msgContent = "短信验证码："+valideteCode+"，您正在使用深圳交警东部预约业务，有效时间为5分钟。";
			}
		}
		return msgContent;
	}
}
