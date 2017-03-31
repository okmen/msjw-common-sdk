package cn.sdk.util;

import org.apache.commons.lang3.StringUtils;

/**
 * compare two version like 'x.x.x' or 'x.x.x.xx'
 * 
 * @author zhengxiaoguo
 * @mail zhengxiaoguo@inbilin.com
 * 
 */
public class RealVersion implements Comparable<RealVersion> {
	private String version;
	private boolean isIosEnterpriseVersion;
	private static final String DEFAULT_VERSION = "3.0.0";
	public RealVersion(String version) {
		version = StringUtils.isBlank(version) ? DEFAULT_VERSION : version.trim();
		int firstPrefixIndex = version.indexOf(".");
		if(firstPrefixIndex == -1){
			throw new RuntimeException("construnt exception:" + version + ", prefix is not found, verion should like x.x.x.x");
		}
		isIosEnterpriseVersion = firstPrefixIndex > 2 ? true : false;
		this.version = isIosEnterpriseVersion ? version.substring(4) : version;
	}

	public int compareTo(RealVersion o) {
		int result = 0;
		String realVersionLeftArray[] = this.version.split("\\.");
		String realVersionRightArray[] = o.version.split("\\.");
		if (realVersionLeftArray == null || realVersionRightArray == null) {
			throw new RuntimeException("format exception:" + this.version + o.version);
		}
		if (realVersionLeftArray.length > realVersionRightArray.length) {
			result = 1;
		} else if (realVersionLeftArray.length < realVersionRightArray.length) {
			result = -1;
		} else {
			for (int i = 0; i < realVersionLeftArray.length; i++) {
				int left = Integer.parseInt(realVersionLeftArray[i]);
				int right = Integer.parseInt(realVersionRightArray[i]);
				if (left != right) {
					result = left > right ? 1 : -1;
					break;
				}
			}
		}
		return result;
	}

	public boolean isIosEnterpriseVersion() {
		return this.isIosEnterpriseVersion;
	}
}
