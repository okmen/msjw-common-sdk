package cn.sdk.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


/***
 * 代替原来的AES，用静态类直接调用的封装AES加解密算法
 * @author phoenix
 *
 */
public class AESNewUtils {
	private static final String IV_PARAM_AES = "00000000000000";
	// private final String KEY_GENERATION_ALG = "PBEWITHSHAANDTWOFISH-CBC";
	private static transient final Logger log = Logger.getLogger(AESNewUtils.class);
	private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	private static final int HASH_ITERATIONS = 10000;
	private static final int KEY_LENGTH = 128;

//	private char[] humanPassphrase = { 'P', 'e', 'r', ' ', 'v', 'a', 'l', 'l',
//			'u', 'm', ' ', 'd', 'u', 'c', 'e', 's', ' ', 'L', 'a', 'b', 'a',
//			'n'};

	private static byte[] salt = { 0, 7, 2, 3, 4, 5, 6, 7, 8, 1, 0xA, 0xB, 0xE, 0xD,0xE, 0xF }; // must save this for next time we want the key

	private static PBEKeySpec myKeyspec; 
	
	private static final String CIPHERMODEPADDING = "AES/CBC/NoPadding";//迫不得已使用无填充  算法/模式/填充 详见：http://blog.sina.com.cn/s/blog_679daa6b0100zmpp.html 或者 http://stackoverflow.com/questions/10900894/ios-encryption-aes128-cbc-nopadding-why-is-not-working

	private static SecretKeyFactory keyfactory = null;
	private static SecretKey sk = null;
	private static SecretKeySpec skforAES = null;
	private static byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC,
			0xD, 91 };

	private static IvParameterSpec IV;

//	static {  支持pkCs7 的添加
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  
//    }  
	static {
		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			log.error("factory init fail!", e);
		}
	}
	
	public AESNewUtils() {

	}

	public static String encrypt(String data,String token) {
		initTokenKey(token);
		byte[] plaintext = data.getBytes();
		
		byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
		String base64_ciphertext = Base64Encoder.encode(ciphertext);
		return base64_ciphertext;
	}

	public static String decrypt(String ciphertext_base64,String token) {
		initTokenKey(token);
		byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV,
				s));
		try{
			int num = Integer.parseInt(decrypted.substring(decrypted.length()-2));
			decrypted = decrypted.substring(0, decrypted.length()-num);
		}catch(Exception e){
			log.debug("AES,decrypt(String ciphertext_base64,String token)~~~~~"+decrypted);
		}
		
		return decrypted;
	}

	// Use this method if you want to add the padding manually
	// AES deals with messages in blocks of 16 bytes.
	// This method looks at the length of the message, and adds bytes at the end
	// so that the entire message is a multiple of 16 bytes.
	// the padding is a series of bytes, each set to the total bytes added (a
	// number in range 1..16).
	public  byte[] addPadding(byte[] plain) {
		byte plainpad[] = null;
		int shortage = 16 - (plain.length % 16);
		// if already an exact multiple of 16, need to add another block of 16
		// bytes
		if (shortage == 0)
			shortage = 16;

		// reallocate array bigger to be exact multiple, adding shortage bits.
		plainpad = new byte[plain.length + shortage];
		for (int i = 0; i < plain.length; i++) {
			plainpad[i] = plain[i];
		}
		for (int i = plain.length; i < plain.length + shortage; i++) {
			plainpad[i] = (byte) shortage;
		}
		return plainpad;
	}

	// Use this method if you want to remove the padding manually
	// This method removes the padding bytes
	public byte[] dropPadding(byte[] plainpad) {
		byte plain[] = null;
		int drop = plainpad[plainpad.length - 1]; // last byte gives number of
													// bytes to drop

		// reallocate array smaller, dropping the pad bytes.
		plain = new byte[plainpad.length - drop];
		for (int i = 0; i < plain.length; i++) {
			plain[i] = plainpad[i];
			plainpad[i] = 0; // don't keep a copy of the decrypt
		}
		return plain;
	}

	private static byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			byte[] msg) {
		byte[] needEncryptStr = msg;
		try {
			int start = msg.length;
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			int blockSize = c.getBlockSize();
		    int diffSize = msg.length % blockSize;
		    
		    diffSize = blockSize - diffSize;
	        byte[] oldDecrypted = msg;
	        msg = new byte[msg.length + diffSize];
	        System.arraycopy(oldDecrypted, 0, msg, 0, oldDecrypted.length);
	        for (int i = 0; i < diffSize; i++) {
	    	    msg[i + oldDecrypted.length] = "0".getBytes()[0];
	        }
	        int end = msg.length;
	        int newSize = end - start + 16;
	        String newStr = IV_PARAM_AES + String.valueOf(newSize);
	        needEncryptStr = arraycat(msg,newStr.getBytes());  
		    
			return c.doFinal(needEncryptStr);
		} catch (NoSuchAlgorithmException nsae) {
			log.debug("AES~~~"+nsae);
		} catch (NoSuchPaddingException nspe) {
			log.debug("AES~~~"+nspe);
		} catch (InvalidKeyException e) {
			log.debug("AES~~~"+e);
		} catch (InvalidAlgorithmParameterException e) {
			log.debug("AES~~~"+e);
		} catch (IllegalBlockSizeException e) {
			log.debug("AES~~~"+e);
		} catch (BadPaddingException e) {
			log.debug("AES~~~"+e);
		}
		return null;
	}

	private static byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			byte[] b = c.doFinal(ciphertext);
			return b;
		} catch (NoSuchAlgorithmException nsae) {
			log.debug("AES~~~"+nsae);
		} catch (NoSuchPaddingException nspe) {
			log.debug("AES~~~"+nspe);
		} catch (InvalidKeyException e) {
			log.debug("AES~~~"+e);
		} catch (InvalidAlgorithmParameterException e) {
			log.debug("AES~~~"+e);
		} catch (IllegalBlockSizeException e) {
			log.debug("AES~~~"+e);
		} catch (BadPaddingException e) {
			log.debug("AES~~~"+e);
		}
		return null;
	}
	
	public static void initTokenKey(String token){
		try {
			myKeyspec = new PBEKeySpec(token.toCharArray(), salt,HASH_ITERATIONS, KEY_LENGTH);
			
			//keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);
			
//		} catch (NoSuchAlgorithmException nsae) {
//			log.debug("AES~~~~~~~"+nsae);
		} catch (InvalidKeySpecException ikse) {
			log.debug("AES~~~~~~~"+ikse);
		}

		byte[] skAsByteArray = sk.getEncoded();
		skforAES = new SecretKeySpec(skAsByteArray, "AES");

		IV = new IvParameterSpec(iv);
	}
	
	static byte[] arraycat(byte[] buf1, byte[] buf2) {
		byte[] bufret = null;
		int len1 = 0;
		int len2 = 0;
		if (buf1 != null)
			len1 = buf1.length;
		if (buf2 != null)
			len2 = buf2.length;
		if (len1 + len2 > 0)
			bufret = new byte[len1 + len2];
		if (len1 > 0)
			System.arraycopy(buf1, 0, bufret, 0, len1);
		if (len2 > 0)
			System.arraycopy(buf2, 0, bufret, len1, len2);
		return bufret;
	}
	
	public static void main(String[] s){
		
		 String tokenKey = "81ed6de52ae8f418b745e44dc0748ec4";//token
		 String str = "1234567890123456";//原始密文
	     System.out.println("原始串:"+str+"长度:"+str.length());
         
	     String dec = AESNewUtils.encrypt(str, tokenKey);
	     //String ask = "62ya8iQ/16dcjAAnzIslVQTLHAXBeEIPX3wRRR1qlbQ=";
	     System.out.println("加密后的串： "+dec);
	     System.out.println("解密后的串： "+AESNewUtils.decrypt(dec,tokenKey));	
 	}

}