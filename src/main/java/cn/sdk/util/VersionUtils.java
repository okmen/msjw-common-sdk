package cn.sdk.util;

import cn.sdk.util.RealVersion;

public class VersionUtils {
	public static final String V2_0_0 = "2.0.0";
	public static final String V2_1_5 = "2.1.5";
	public static final String V2_4_0 = "2.4.0";
	public static final String V2_4_7 = "2.4.7";
	public static final String V3_0_0 = "3.0.0";
	public static final String V3_0_3 = "3.0.3";
	public static final String V3_0_5 = "3.0.5";
	public static final String V3_0_6 = "3.0.6";
	public static final String V3_0_7 = "3.0.7";
	public static final String V3_1_0 = "3.1.0";
	public static final String V3_1_2 = "3.1.2";
	public static final String V3_1_3 = "3.1.3";
	public static final String V3_1_5 = "3.1.5";
	public static final String V3_1_6 = "3.1.6";
	public static final String V3_2_0 = "3.2.0";
	public static final String V3_2_5 = "3.2.5";
	public static final String V3_2_6 = "3.2.6";
	public static final String V_AES_GZIP_VERSION = "3.2.6";// "3.2.6";测试需要改成330
	public static final String V_TALK_GROUP_VERSION = "3.3.0.0";
	public static final String V3_7_0 = "3.7.0.0";
	public static final String V4_0_0 = "4.0.0.0";
	public static final String V3_7_3 = "3.7.3.0";// 小心情版本
	public static final String V_3_7_2_0 = "3.7.2.0";
	public static final String V_RECOMMEND_POSITION = "3.7.1.0";// 推荐位版本
	public static final String V3_7_2_2 = "3.7.2.2";// 设备激活版本
	public static final String V3_7_2_3 = "3.7.2.3";
	public static final String V3_7_2_6 = "3.7.2.6";
	public static final String V3_7_5 = "3.7.5.0";

	private static int compareVersion(String versionLeft, String versionRight) {
		return new RealVersion(versionLeft).compareTo(new RealVersion(versionRight));
	}

	/**
	 * 判断是否为ios企业版
	 * 
	 * @param version
	 * @return
	 */
	public static boolean isIosEnterpriseVersion(String version) {
		return new RealVersion(version).isIosEnterpriseVersion();
	}

	/**
	 * 大于等于
	 * 
	 * @param versionLeft
	 * @param versionRight
	 * @return
	 */
	public static boolean isGreaterOrEqual(String versionLeft, String versionRight) {
		return compareVersion(versionLeft, versionRight) >= 0;
	}

	/**
	 * 大于
	 * 
	 * @param versionLeft
	 * @param versionRight
	 * @return
	 */
	public static boolean isGreater(String versionLeft, String versionRight) {
		return compareVersion(versionLeft, versionRight) > 0;
	}

	/**
	 * 小于等于
	 * 
	 * @param versionLeft
	 * @param versionRight
	 * @return
	 */
	public static boolean isLessOrEqual(String versionLeft, String versionRight) {
		return compareVersion(versionLeft, versionRight) <= 0;
	}

	/**
	 * 小于
	 * 
	 * @param versionLeft
	 * @param versionRight
	 * @return
	 */
	public static boolean isLess(String versionLeft, String versionRight) {
		return compareVersion(versionLeft, versionRight) < 0;
	}

	/**
	 * 等于
	 * 
	 * @param versionLeft
	 * @param versionRight
	 * @return
	 */
	public static boolean isEqual(String versionLeft, String versionRight) {
		return compareVersion(versionLeft, versionRight) == 0;
	}
}
