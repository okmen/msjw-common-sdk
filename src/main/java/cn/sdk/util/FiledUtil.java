package cn.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class FiledUtil {
	static Logger logger = Logger.getLogger(FiledUtil.class.getSimpleName());
	/**
	 * 取属性值 目前找两级   当前类 -> 一级父类
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(Object obj, String propertyName){
		try {
			Class clazz = obj.getClass();
			Class superClazz = clazz.getSuperclass();
			Field[] clazz_fds = clazz.getDeclaredFields();
			Field[] superClazz_fds = superClazz.getDeclaredFields();

			Field[] allField = new Field[clazz_fds.length + superClazz_fds.length];
			for (int i = 0; i < clazz_fds.length; i++) {
				allField[i] = clazz_fds[i];
			}

			for (int i = 0; i < superClazz_fds.length; i++) {
				allField[clazz_fds.length + i] = superClazz_fds[i];
			}

			for (int i = 0; i < allField.length; i++) {
				if (propertyName.equals(allField[i].getName())) {
					Object val = invokeMethod(obj, allField[i].getName());
					return val;
				}
			}
		} catch (Exception e) {
			logger.error("反射取值出错",e);
		}
		return null;
	}

	/**
	 * 执行get方法 获取属性值
	 * @param owner
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object owner, String methodName)
			throws Exception {
		Class ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return " can't find 'get" + methodName + "' method";
		}
		return method.invoke(owner);
	}
}
