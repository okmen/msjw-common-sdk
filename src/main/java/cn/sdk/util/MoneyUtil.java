package cn.sdk.util;
/** 
 * @author lifangyong
 * @date 2017年11月24日 下午3:55:29 
 */
public class MoneyUtil {

	public static String getMoneyStr(Integer money){
		String str = money + "";
	/*	if (str.length()<3) {
			str = "0." + str.substring(str.length()-2, str.length());
		}*/
		if (str.length()>=3) {
			str = str.substring(0,str.length()-2) + "." + str.substring(str.length()-2, str.length());
		}
		return str;
	}
}
