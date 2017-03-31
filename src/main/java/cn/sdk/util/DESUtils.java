package cn.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public final class DESUtils {

    public static String decrypt(final String hexMessage, final String key) throws Exception {

        SecretKey secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

        byte[] messageBytes = Hex.decodeHex(hexMessage.toCharArray());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] resultBytes = cipher.doFinal(messageBytes);
        return new String(resultBytes, "UTF-8");

    }
    
    public static String encrypt(final String plaintext, final String key) throws Exception {

        SecretKey secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

        byte[] messageBytes = plaintext.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] resultBytes = cipher.doFinal(messageBytes);
        
        return Hex.encodeHexString(resultBytes);
    }

    private static SecretKey generateKey(final String key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

        byte[] keyBytes = new byte[24];
        byte[] tempKeyBytes = key.getBytes("UTF-8");

        if (keyBytes.length > tempKeyBytes.length) {
            System.arraycopy(tempKeyBytes, 0, keyBytes, 0, tempKeyBytes.length);
        } else {
            System.arraycopy(tempKeyBytes, 0, keyBytes, 0, keyBytes.length);
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        return secretKey;

    }
}
