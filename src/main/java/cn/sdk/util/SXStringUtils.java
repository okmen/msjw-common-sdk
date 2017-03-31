/**
 * @filename			StringUtil.java
 * @function			客户端提交信息通用处理类
 * @author				skyz <skyzhw@gmail.com>
 * @datetime			Mar	1, 2008
 * @lastmodify			Mar	1, 2008
 */
package cn.sdk.util;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串通用处理类
 * 
 * @author skyz <skyzhw@gmail.com>
 * @version 1.0
 */
public class SXStringUtils {
	private final static String EMPTYSTRING = "";

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 * @return String
	 */
	public static String randomStr(int length) {
		// 数字 大小写字母
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer str = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			str.append(buffer.charAt(r.nextInt(range)));
		}
		return str.toString();
	}
	
	/**
	 * 字符串转字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(Object obj) {
		if(null==obj){
			return "";
		}
		String str=obj.toString();
		if (StringUtils.isNotEmpty(str)) {
			return str;
		}
		return "";
	}

	/**
	 * 字符串转字节
	 * 
	 * @param str
	 * @return
	 */
	public static byte toByte(String str) {
		if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
			try{
				return Byte.parseByte(str);
			}catch(Exception e){
			}
		}
		return 0;
	}
	
	public static Short toShort(String str){
		if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
			try{
				return Short.parseShort(str);
			}catch(Exception e){
			}
		}
		return 0;
	}

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
			try{
				return Integer.parseInt(str);
			}catch(Exception e){
			}
		}
		return 0;
	}

	/**
	 * 字符串转长整型
	 * 
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		if (StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)) {
			try{
				return Long.parseLong(str);
			}catch(Exception e){
			}
		}
		return 0;
	}

	/**
	 * 字符串转浮点型
	 * 
	 * @param str
	 * @return
	 */
	public static float toFloat(String str) {
		if (StringUtils.isNotEmpty(str)) {
			try{
				return Float.parseFloat(str);
			}catch(Exception e){
			}
		}
		return 0;
	}

	/**
	 * 字符串转双精度浮点型
	 * 
	 * @param str
	 * @return
	 */
	public static double toDouble(String str) {
		if (StringUtils.isNotEmpty(str)) {
			try{
				return Double.parseDouble(str);
			}catch(Exception e){
			}
		}
		return 0;
	}

	/**
	 * 插入数据时转义字符串
	 * 
	 * @param obj
	 * @param mark
	 * @return
	 */
	public static String escapeStr(String str) {
		if (StringUtils.isEmpty(str)) {
			str = EMPTYSTRING;
		}
		return str.replace("\\", "\\\\").replace("'", "\\'")
				.replace("\"", "\\\"");
	}

	/**
	 * 截取字符串
	 * 
	 * @param src
	 * @param len
	 * @return
	 */
	public static String getSubWord(String src, int len) {
		return getSubWord(src, len, null);
	}

	/**
	 * 截取字符串
	 * 
	 * @param src
	 * @param len
	 * @param replace
	 * @return
	 */
	public static String getSubWord(String src, int len, String replace) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isEmpty(src)) {
			return EMPTYSTRING;
		}
		if (StringUtils.isEmpty(replace)) {
			replace = EMPTYSTRING;
		}
		char[] c = src.toCharArray();
		for (int x = 0; x < c.length; x++) {
			sb.append(c[x]);
			if (sb.toString().getBytes().length > len) {
				return sb.substring(0, x) + replace;
			}
		}
		return src;
	}

	/**
	 * 把list转化成字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<Long> list, String split) {
		String result = "";
		split=split==null?",":split;
		for (Object obj : list) {
			result = split + obj.toString();
		}
		if (result.startsWith(split)) {
			result.substring(1);
		}
		return result;
	}

	/**
	 * 半角转全角
	 * 
	 * @param QJstr
	 * @return
	 */
	public static final String half2full(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (b[3] != -1) {
				b[2] = (byte) (b[2] - 32);
				b[3] = -1;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}

		return outStr;
	}

	/**
	 * 全角转半角
	 * 
	 * @param QJstr
	 * @return
	 */
	public static final String full2half(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}

		return outStr;
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 * @return
	 */
	public static String full2half2(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
