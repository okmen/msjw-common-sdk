package cn.sdk.util;

public class BeanUtils {
	/**
	 * @param dest 新对象/待赋值对象
	 * @param orig 源对象
	 */
	public static void copyProperties(Object dest,Object orig) throws Exception{
		org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
	}
}
