package cn.sdk.msg;

import org.apache.commons.lang3.StringUtils;

/**
 * 短信模板
 * @author Mbenben
 *
 */
public class MsgTemplate {
	public static String szjj = "szjj"; //深圳交警公众号
	public static String easternReservation = "easternReservation"; //东部预约短信验证码
	public static String app = "app";//深圳交警app
	
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
			if (app.equals(businessType)) {
				msgContent = "短信验证码："+valideteCode+"，您正在使用深圳交警APP业务，有效时间为5分钟。";
			}
		}
		return msgContent;
	}

	/**
	 * 成功短信
	 * @param apptDate
	 * @param apptInterval
	 * @param apptDistrict
	 * @return
	 */
	public static String getEastApptSuccessMsgTemplate(String apptDate, String apptInterval, String apptDistrict){
		String msgContent = "";
		if("1".equals(apptInterval)){	//1-上午
			apptInterval = "上午(00:00-12:00)";
		}
		if("2".equals(apptInterval)){	//2-下午
			apptInterval = "下午(12:00-18:00)";
		}
		if("3".equals(apptInterval)){	//2-下午
			apptInterval = "下午(18:00-24:00)";
		}
		if("MSPQ".equals(apptDistrict)){	//1-梅沙片区
			apptDistrict = "梅沙片区";
		}
		if("DPPQ".equals(apptDistrict)){	//2-大鹏片区
			apptDistrict = "大鹏片区";
		}
		
		msgContent = "尊敬的车主，您好！您已成功预约于"+apptDate+"，"+apptInterval+"前往"+apptDistrict+"。如因交通拥堵未在预约时间内抵达的，通过83333333电话提前报备，否则视为违约，将影响下次预约。";
		return msgContent;
	}
	
	/**
	 * 失败短信
	 * @param apptDate
	 * @param apptInterval
	 * @param apptDistrict
	 * @return
	 */
	public static String getEastApptFailMsgTemplate(String failReason, String apptDate, String apptInterval, String apptDistrict){
		String msgContent = "";
		if("1".equals(apptInterval)){	//1-上午
			apptInterval = "上午(00:00-12:00)";
		}
		if("2".equals(apptInterval)){	//2-下午
			apptInterval = "下午(12:00-18:00)";
		}
		if("3".equals(apptInterval)){	//2-下午
			apptInterval = "下午(18:00-24:00)";
		}
		if("MSPQ".equals(apptDistrict)){	//1-梅沙片区
			apptDistrict = "梅沙片区";
		}
		if("DPPQ".equals(apptDistrict)){	//2-大鹏片区
			apptDistrict = "大鹏片区";
		}
		
		msgContent = "尊敬的车主，您好! 很抱歉，由于"+failReason+"，您预约的"+apptDate+"，"+apptInterval+"前往"+apptDistrict+"无法成功预约。如需预约其他时间，请前往深圳交警公众号再次尝试预约。给您带来不便敬请谅解。";
		return msgContent;
	}
	
	/**
	 * 取消预约成功短信
	 * @return
	 */
	public static String getEastApptCancelMsgNotice(){
		String msgContent = "尊敬的车主，您好！您已成功取消预约。温馨提示：1年内累计有3次预约成功后未履约前往情形的，取消当年预约资格（预约手机号和预约车牌均不得预约）。";
		return msgContent;
	}
	
	/**
	 * 酒店预约成功短信
	 * @param apptDate
	 * @param apptInterval
	 * @param apptDistrict
	 * @return
	 */
	public static String getEastHotelApptSuccessMsgTemplate(String hotelName, String apptDate, String apptInterval, String apptDistrict){
		String msgContent = "";
		if("1".equals(apptInterval)){			//1-上午
			apptInterval = "上午(00:00-12:00)";
		}else if("2".equals(apptInterval)){		//2-下午
			apptInterval = "下午(12:00-24:00)";
		}
		
		if("1".equals(apptDistrict)){			//1-梅沙片区
			apptDistrict = "梅沙片区";
		}else if("2".equals(apptDistrict)){		//2-大鹏片区
			apptDistrict = "大鹏片区";
		}
		
		msgContent = "尊敬的车主，您好！"+hotelName+"已成功帮您预约于"+apptDate+"，"+apptInterval+"前往"+apptDistrict+"。如因交通拥堵未在预约时间内抵达的，通过83333333电话提前报备，否则视为违约，将影响下次预约。";
		return msgContent;
	}
}
