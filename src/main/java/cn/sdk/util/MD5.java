/**
 * @filename			MD5.java
 * @function			Md5加密
 * @author				skyz <skyzhw@gmail.com>
 * @datetime			Jul 2, 2007
 * @lastmodify			Jul 2, 2007
 */
package cn.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密
 * 
 * @author skyz <skyzhw@gmail.com>
 * @version 1.0
 */

public class MD5 {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	public MD5() {
	}

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return (new StringBuilder()).append(hexDigits[d1])
				.append(hexDigits[d2]).toString();
	}

	/**
	 * md5加密
	 * 
	 * @param origin要加密的字符串
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (NoSuchAlgorithmException e) {
		}
		return resultString;
	}

}