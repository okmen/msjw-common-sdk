package cn.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1加密 (40位)
 */

public class SHA1 {

	public SHA1() {
	}

	/**
	 * SHA1加密 (40位)
	 * @param data
	 * @return String
	 */
	public static String sha1(String data) {
		String resultString = null;
		try {
			resultString = new String(data);
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(data.getBytes());
			StringBuffer buf = new StringBuffer();
			byte[] bits = md.digest();
			for (int i = 0; i < bits.length; i++) {
				int a = bits[i];
				if (a < 0)
					a += 256;
				if (a < 16)
					buf.append("0");
				buf.append(Integer.toHexString(a));
			}
			resultString = buf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return resultString;
	}
}