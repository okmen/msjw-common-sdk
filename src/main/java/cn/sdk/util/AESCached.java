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

public class AESCached {
	private static final String IV_PARAM_AES = "00000000000000";
	private static transient final Logger log = Logger.getLogger(AESCached.class);
	private static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";
	private static final int HASH_ITERATIONS = 10000;
	private static final int KEY_LENGTH = 128;
	private static final byte[] salt = { 0, 7, 2, 3, 4, 5, 6, 7, 8, 1, 0xA, 0xB, 0xE, 0xD, 0xE, 0xF };
	private static final String CIPHERMODEPADDING = "AES/CBC/NoPadding";
	private static SecretKeyFactory keyfactory = null;
	private SecretKeySpec skforAES;
	private static final byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 };
	private static final IvParameterSpec IV = new IvParameterSpec(iv);

	static {
		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
		} catch (NoSuchAlgorithmException e) {
			log.error("factory init fail!", e);
		}
	}

	public AESCached(SecretKeySpec skforAES) {
		this.skforAES = skforAES;
	}

	public static SecretKeySpec getSkforAES(String token) {
		SecretKeySpec skforAESTmp = null;
		try {
			PBEKeySpec myKeyspec = new PBEKeySpec(token.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
			SecretKey sk = keyfactory.generateSecret(myKeyspec);
			byte[] skAsByteArray = sk.getEncoded();
			skforAESTmp = new SecretKeySpec(skAsByteArray, "AES");
			return skforAESTmp;
		} catch (InvalidKeySpecException ikse) {
			log.debug("AES~~~~~~~" + ikse);
		}
		return skforAESTmp;
	}

	public String encrypt(String data) {
		byte[] plaintext = data.getBytes();
		byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
		String base64_ciphertext = Base64Encoder.encode(ciphertext);
		return base64_ciphertext;
	}

	public String decrypt(String ciphertext_base64) {
		byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV, s));
		try {
			int num = Integer.parseInt(decrypted.substring(decrypted.length() - 2));
			decrypted = decrypted.substring(0, decrypted.length() - num);
		} catch (Exception e) {
			log.debug("AES,decrypt(String ciphertext_base64,String token)~~~~~" + decrypted);
		}

		return decrypted;
	}

	private static byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) {
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
			needEncryptStr = arraycat(msg, newStr.getBytes());

			return c.doFinal(needEncryptStr);
		} catch (NoSuchAlgorithmException nsae) {
			log.debug("AES~~~" + nsae);
		} catch (NoSuchPaddingException nspe) {
			log.debug("AES~~~" + nspe);
		} catch (InvalidKeyException e) {
			log.debug("AES~~~" + e);
		} catch (InvalidAlgorithmParameterException e) {
			log.debug("AES~~~" + e);
		} catch (IllegalBlockSizeException e) {
			log.debug("AES~~~" + e);
		} catch (BadPaddingException e) {
			log.debug("AES~~~" + e);
		}
		return null;
	}

	private static byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			byte[] b = c.doFinal(ciphertext);
			return b;
		} catch (NoSuchAlgorithmException nsae) {
			log.debug("AES~~~" + nsae);
		} catch (NoSuchPaddingException nspe) {
			log.debug("AES~~~" + nspe);
		} catch (InvalidKeyException e) {
			log.debug("AES~~~" + e);
		} catch (InvalidAlgorithmParameterException e) {
			log.debug("AES~~~" + e);
		} catch (IllegalBlockSizeException e) {
			log.debug("AES~~~" + e);
		} catch (BadPaddingException e) {
			log.debug("AES~~~" + e);
		}
		return null;
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
}