package cn.sdk.util;

import java.lang.reflect.InvocationTargetException;


public class PropertyUtils {
	/**
	 * @param dest 新对象/待赋值对象
	 * @param orig 源对象
	 */
	public static void copyProperties(Object dest,Object orig){
		try {
			org.apache.commons.beanutils.PropertyUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
