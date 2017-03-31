package cn.sdk.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class AesCipherUtil {
	
	private static final Logger LOG = Logger.getLogger(SecurityUtil.class);
	
	// AESkey   
	private static final String KEY_AES = "AES";
	
	// AES算法 
	private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
	
	// 16 位字符串
	private static final String IV_PARAM_AES = "EIJ78&*^IK7839#!";
	
	/** 
	 * 生成AESkey 
	 * @param keySize key的位数 
	 * @param seed 随机种子 
	 * @return 返回base64编码后的key信息 
	 */
	public static String generateAESKey(int keySize, String seed) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
			kgen.init(keySize, new SecureRandom(seed.getBytes()));
			SecretKey key = kgen.generateKey();
			return TranscodeUtil.byteArrayToBase64Str(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("error: " + e.getMessage());
		}
		return null;
	}
	
	/** 
	 * AES加密 
	 * @param data 要加密的数据 
	 * @param key 密钥 
	 * @param algorithmParameter 算法参数 
	 * @return 返回加密数据 
	 */
	public static String aesEncrypt(String data, String key) {
		return aesCipher(data, key, Cipher.ENCRYPT_MODE);
	}
	
	/** 
	 * AES解密 
	 * @param data 要解密的数据 
	 * @param key 密钥 
	 * @param algorithmParameter 算法参数 
	 * @return 返回解密数据 
	 */
	public static String aesDecrypt(String data, String key) {
		return aesCipher(data, key, Cipher.DECRYPT_MODE);
	}
	
	/** 
	 * 实现AES加密解密 
	 * @param data 要加密或解密的数据 
	 * @param key 密钥 
	 * @param mode 加密或解密 
	 * @return 返回加密或解密的数据 
	 */
	private static String aesCipher(String data, String key, int mode) {
		try {
			Key k = toKey(key, KEY_AES);
			// 算法参数使用固定的一个字符串，方便手动生成加密后字符串与解密
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV_PARAM_AES.getBytes());
			Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
			ecipher.init(mode, k, paramSpec);
			return mode == Cipher.DECRYPT_MODE ? new String(ecipher.doFinal(TranscodeUtil.base64StrToByteArray(data))) : TranscodeUtil.byteArrayToBase64Str(ecipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			LOG.error("error: " + e.getMessage());
		}
		return "";
	}
	
	/** 
	 * 将base64编码后的密钥字符串转换成密钥对象 
	 * @param key 密钥字符串 
	 * @param algorithm 加密算法 
	 * @return 返回密钥对象 
	 */
	private static Key toKey(String key, String algorithm) {
		SecretKey secretKey = new SecretKeySpec(TranscodeUtil.base64StrToByteArray(key), algorithm);
		return secretKey;
	}
	
	public static void main(String[] args) {
		String key = "";
		String data = "";
		String password = "";
		if (args.length == 0) {
			key = generateAESKey(128, String.valueOf(Math.random()));
			System.out.println("生成的随机密钥为: " + key);
			System.out.println("你可以用此密钥为字符串加密或解密，只需提供以下三个参数: ");
			System.out.println("1. 第一个参数为1或者2(1代表加密，2代表解密)");
			System.out.println("2. 第二个参数为密钥");
			System.out.println("3. 第三个参数为需要加密或解密的字符换");
		} else if (args.length == 1) {
			System.out.println("正在生成随机密钥并进行加密操作...");
			data = args[0];
			System.out.println("需要加密的字符串为：" + data);
			key = generateAESKey(128, String.valueOf(Math.random()));
			System.out.println("生成的随机密钥为：" + key);
			password = aesEncrypt(data, key);
			System.out.println("加密后的字符串为：" + password);
		} else if (args.length == 3) {
			if ("1".equals(args[0])) {
				System.out.println("正在进行加密操作...");
				key = args[1];
				data = args[2];
				System.out.println("给定的密钥为：" + key);
				System.out.println("需要加密的字符串为：" + data);
				password = aesEncrypt(data, key);
				System.out.println("加密后的字符串为：" + password);
			} else if ("2".equals(args[0])) {
				System.out.println("正在进行解密操作...");
				key = args[1];
				data = args[2];
				System.out.println("给定的密钥为：" + key);
				System.out.println("需要解密的字符串为：" + data);
				password = aesDecrypt(data, key);
				System.out.println("解密后的字符串为：" + password);
			} else {
				printIntro();
			}
		} else {
			printIntro();
		}
	}
	
	private static void printIntro() {
		System.out.println("提供的参数错误，参数说明如下：");
		System.out.println("1. 可以不提供任何参数，则随机生成一个密钥");
		System.out.println("2. 可以只提供一个参数，则随机生成一个密钥并基于此密钥对提供的字符串进行加密");
		System.out.println("3. 可以提供三个参数，三个参数的说明如下：");
		System.out.println("	1. 第一个参数为1或者2(1代表加密，2代表解密)");
		System.out.println("	2. 第二个参数为密钥");
		System.out.println("	3. 第三个参数为需要加密或解密的字符换");
	}
}
