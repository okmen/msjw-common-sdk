package cn.sdk.util;

import java.util.EmptyStackException;

/**
 * 身份证验证处理类
 * 
 * @author zhanghuawei
 * 
 */
public class IdCard {
	static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] ai = new int[18];

	/**
	 * 验证身份证是否合法
	 * 
	 * @param idcard
	 *            身份证号码
	 * @return boolean
	 */
	public static boolean Verify(String idcard) {
		try {
			if (idcard.length() == 15) {
				idcard = uptoeighteen(idcard);
			}
			if (idcard.length() != 18) {
				return false;
			}
			String verify = idcard.substring(17, 18);
			if (verify.equals(getVerify(idcard))) {
				return true;
			}
			return false;
		} catch (EmptyStackException e) {
			return false;
		}
	}

	/**
	 * 返回用户生日
	 * 
	 * @param idcard
	 *            身份证号码
	 * @return String 用户生日
	 */
	public static String getBirthday(String idcard) {
		try {
			if (idcard.length() == 15) {
				idcard = uptoeighteen(idcard);
			}
			if (idcard.length() != 18) {
				return null;
			}
			String verify = idcard.substring(17, 18);
			String birth = idcard.substring(6, 10) + "-"
					+ idcard.substring(10, 12) + "-" + idcard.substring(12, 14);
			if (verify.equals(getVerify(idcard))) {
				return birth;
			}
			return null;
		} catch (EmptyStackException e) {
			return null;
		}
	}

	/**
	 * 返回用户性别
	 * 
	 * @param idcard
	 *            身份证号码
	 * @return int 用户性别 0未知 1男 2女
	 */
	public static int getSex(String idcard) {
		try {
			if (idcard.length() == 15) {
				idcard = uptoeighteen(idcard);
			}
			if (idcard.length() != 18) {
				return 0;
			}
			String verify = idcard.substring(17, 18);
			if (verify.equals(getVerify(idcard))) {
				if (Integer.parseInt(idcard.substring(16, 17)) % 2 == 1) {
					return 1;
				} else {
					return 2;
				}
			}
			return 0;
		} catch (EmptyStackException e) {
			return 0;
		}
	}

	private static String getVerify(String eightcardid) {
		int remaining = 0;
		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}
		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	/**
	 * 把15位身份证升级为18位的
	 * 
	 * @param fifteencardid
	 *            15位身份证号码
	 * @return 18位身份证号码
	 */
	private static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}
}
