package cn.sdk.util;

import javax.crypto.spec.SecretKeySpec;

public class AESCachedUtils {
	public AESCachedUtils() {

	}

	public static String encrypt(String data, SecretKeySpec secretKeySpec) {
		return new AESCached(secretKeySpec).encrypt(data);
	}

	public static String decrypt(String ciphertext_base64, SecretKeySpec secretKeySpec) {
		return new AESCached(secretKeySpec).decrypt(ciphertext_base64);
	}

	public static SecretKeySpec getSkforAES(String token) {
		return AESCached.getSkforAES(token);
	}

	public static void main(String args[]) {
		String tokenKey = "81ed6de52ae8f418b745e44dc0748ec4";// token
		String str = "123";// 原始密文
		SecretKeySpec secretKeySpec = AESCachedUtils.getSkforAES(tokenKey);
		String dec = AESCachedUtils.encrypt(str, secretKeySpec);
		System.out.println("加密后的串： " + dec);
		System.out.println("解密后的串： " + AESCachedUtils.decrypt(dec, secretKeySpec));
	}
}
