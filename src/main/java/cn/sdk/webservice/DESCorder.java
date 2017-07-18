package cn.sdk.webservice;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.sdk.util.Base64;


/**
 * 警司通
 * @文件名:DESCorder.java
 * @作者:wy E-mail:wyong@szjst.com.cn
 * @创建日期:2014-7-15
 * @描述:
 * @版本:V 1.0
 */
public class DESCorder {
	// 定义加密算法，有DES、DESede(即3DES)、Blowfish
	private static final String Algorithm = "DESede";
	private static final String PASSWORD_CRYPT_KEY = "94D863D9BE7FB032E6A19430CC892610";

	/**
	 * 加密方法
	 * 
	 * @param src
	 *            源数据的字节数组
	 * @return
	 */
	public static byte[] encryptMode(String src, String key) throws Exception{
		if(src != null){
			try {
				byte[] des = src.getBytes();
				SecretKey deskey = new SecretKeySpec(
						getKeybyte(initKeyString(key)), Algorithm); // 生成密钥
				Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
				c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
				return c1.doFinal(des);
			} catch (java.security.NoSuchAlgorithmException e1) {
				throw e1;
			} catch (javax.crypto.NoSuchPaddingException e2) {
				throw e2;
			} catch (java.lang.Exception e3) {
				throw e3;
			}
		}
		return null;
	}
	
	/**
	 * 加密方法
	 * @param src 源数据的字节数组
	 * @return 返回base64字符串
	 */
	public static String encryptModeToString(String src, String key) throws Exception{
		if(src != null){
			try {
				byte[] des = src.getBytes();
				SecretKey deskey = new SecretKeySpec(
						getKeybyte(initKeyString(key)), Algorithm); // 生成密钥
				Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
				c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
				//return Coder.encryptBASE64(c1.doFinal(des));
				return Base64.encode(c1.doFinal(des));
				
			} catch (java.security.NoSuchAlgorithmException e1) {
				throw e1;
			} catch (javax.crypto.NoSuchPaddingException e2) {
				throw e2;
			} catch (java.lang.Exception e3) {
				throw e3;
			}
		}
		return null;
	}
	/**
	 * 车管所xml参数解密算法
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES(String data) throws Exception {
		String iv = "com.cscx.www*+_-";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	  try {
          	  String key = "Cscx"+ sdf.format(new Date()) +"+*";
              Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");  
              int blockSize = cipher.getBlockSize();  
              byte[] dataBytes = data.getBytes();  
              int plaintextLength = dataBytes.length;  
              if (plaintextLength % blockSize != 0) {  
                  plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));  
              }  
              byte[] plaintext = new byte[plaintextLength];  
              System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);  
              SecretKey keyspec = new SecretKeySpec(key.getBytes(), "AES");  
              IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  
              cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);  
              byte[] encrypted = cipher.doFinal(plaintext);  
              return new String(new Base64().encode(encrypted)).trim();  
          } catch (Exception e) {  
              e.printStackTrace();  
              return null;  
          }
      }

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            Base64密文的字节数组
	 * @return
	 */
	public static String decryptMode(String src, String key, String zfgs) throws Exception {
		if(src != null){
			try {
				//byte[] by = Coder.decryptBASE64(src);
				byte[] by = Base64.decode(src);
				
				SecretKey deskey = new SecretKeySpec(
						getKeybyte(initKeyString(key)), Algorithm);
				Cipher c1 = Cipher.getInstance(Algorithm);
				c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
				return new String(c1.doFinal(by), zfgs);
			} catch (java.security.NoSuchAlgorithmException e1) {
				throw e1;
			} catch (javax.crypto.NoSuchPaddingException e2) {
				throw e2;
			} catch (java.lang.Exception e3) {
				throw e3;
			}
		}
		return null;
	}
	
	/**
     * MD5加密
     * @param s
     * @return
     */
    public static String encodeMD5(String s) throws Exception{

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("utf-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			throw e;
		}
	
	}

	/**
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr)
			throws Exception {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	/**
	 * 初始化密钥
	 * @return
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public static String initKeyString(String key) throws  Exception{
		return Base64.encode(build3DesKey(key));
		//return Coder.encryptBASE64(build3DesKey(key));
	}
	
	/**
	 * 获取密钥
	 * @param key base64码
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getKeybyte(String key) throws Exception{
		return Base64.decode(key);
		//return Coder.decryptBASE64(key);
	}
	
	
	public static void main(String[] args) throws Exception {
//		String msg = "<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>测试"+
//		"<?xml version='1.0' encoding='UTF-8'?><beans xmlns='http://www.springframework.org/schema/beans' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'xmlns:p='http://www.springframework.org/schema/p' xsi:schemaLocation='http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd'></beans><!-- 将SessionFactory注入DAO --><bean name='defaultDao' class='com.wffzglpt.ssh.dao.impl.DefaultDaoImpl'><property name='sessionFactory' ref='sessionFactory' /></bean>测试";
//		msg = "<return><head><yhdh>00002</yhdh><ip></ip><lsh></lsh><fhz>0005</fhz><fhz-msg>该用户登录已失效，请重新登录</fhz-msg><bz></bz></head><body><yzm></yzm></body></return>";
//		System.out.println("【加密前】：" + msg);
//		System.out.println("【密钥】："+DESCorder.initKeyString(PASSWORD_CRYPT_KEY));
//
//		// 加密
//		String secretArr = DESCorder.encryptModeToString(msg, PASSWORD_CRYPT_KEY);
//		System.out.println("【加密后】：" + secretArr);
//
//		// 解密
//		secretArr = "O3qXCO/dla8fG33QbC2sDVw/J3Edbq0NrWQAhFBdFmSVFxeQlCGlCeCs4eYV8D4J8nxlJy1TseZJ5twDk+h8uIe6lMljfQrbhaygHIJQzkl3nsnnQK8r9W0KL3s6Pi3waRmmhdItBKNcLh/448mdF34fv66kd8bt1u6+i8xioUuiaW97jWiKOyqQqg83PapJ0GUm1zQe9y0144YD5AEmDMPktxBv+noL";
//		String myMsgArr = DESCorder.decryptMode(secretArr, "6900106", "gbk");
//		System.out.println("【解密后】：" + myMsgArr);
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><REQUEST><USERNAME>15920071829</USERNAME><PWD>168321</PWD><YHLY>WX_XCX</YHLY><SFZMHM></SFZMHM><XM></XM></REQUEST>";
		String secretArr = DESCorder.encryptModeToString(xml, PASSWORD_CRYPT_KEY);
		System.out.println(secretArr);
		
		String random = (int)(Math.random()*100000000)+"";
		System.out.println(random);
		
		String md5 = DESCorder.encodeMD5("070469070469");
		System.out.println(md5);
	}

}
