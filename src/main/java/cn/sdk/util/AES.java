package cn.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密
 *
 * 2016年9月7日
 * @author zhengpin
 */
public class AES {
    public static String CHARARRAY = "0123456789ABCDEF";
    public static String CHARSET = "UTF-8";
    public static final String QRCODE_AES_KEY = "PINGANTRAFFICORCODE8618121311331";
    
    /**
     * 加密
     *
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String password) throws Exception{
        byte[] bytes = aesEncrypt(content.getBytes(CHARSET), password, true);
        return bytes2Hex(bytes);
    }
    
    /**
     * 解密
     *
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String password) throws Exception{
        byte[] bytes = aesDecrypt(Hex2bytes(content), password, true);
        return new String(bytes, CHARSET);
    }

    /**
     * 方法说明：AES加密
     * 
     * @param content
     *            加密原文
     * @param password
     *            加密密钥
     * @return
     * @throws Exception
     */
    private static byte[] aesEncrypt(byte[] byteContent, String password, boolean isEncryptKey)
            throws Exception {
        SecretKeySpec key = null;
        if (isEncryptKey) {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(CHARSET));
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, "AES");
        } else {
            // 对密钥做MD5并取前16位值作为密钥
            password = bytes2Hex(hashEncrypt(password, "MD5")).substring(0, 16);
            key = new SecretKeySpec(password.getBytes(), "AES");
        }
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密
    }

    /**
     * 方法说明：AES解密
     * 
     * @param content
     *            字节数组格式的密文
     * @param password
     *            解密密钥
     * @return
     * @throws Exception
     */
    private static byte[] aesDecrypt(byte[] content, String password, boolean isEncryptKey)
            throws Exception {
        SecretKeySpec key = null;
        if (isEncryptKey) {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(CHARSET));
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, "AES");
        } else {
            // 对密钥做MD5并取前32位值作为密钥
            password = bytes2Hex(hashEncrypt(password, "MD5")).substring(0, 16);
            key = new SecretKeySpec(password.getBytes(), "AES");
        }
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密
    }

    /**
     * 方法说明：用指定的哈希算法计算摘要值
     * 
     * @param strSrc
     *            原信息
     * @param encName
     *            哈希算法
     * @return
     */
    private static byte[] hashEncrypt(String strSrc, String encName) {
        MessageDigest md = null;
        try {
            byte[] bt = strSrc.getBytes(CHARSET);
            if (encName == null || encName.equals("")) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
        } catch (UnsupportedEncodingException e1) {
            System.out.println("不支持的编码.");
            e1.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            System.out.println("非法的消息摘要算法.");
            e2.printStackTrace();
            return null;
        }
        return md.digest();
    }

    /**
     * 方法说明：字节数组转十六进制
     * 
     * @param src
     * @return
     */
    private static String bytes2Hex(byte[] src) {
        StringBuffer dst = new StringBuffer();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String temp = Integer.toHexString(v);
            if (temp.length() == 2) {
                dst.append(temp);
            } else {
                dst.append("0" + temp);
            }
        }
        return dst.toString().toUpperCase();
    }
    
    /**
     * 方法说明：十六进制转字节数组
     * 
     * @param hexString
     * @return
     * @throws Exception
     */
    private static byte[] Hex2bytes(String hexString) throws Exception {
        if (hexString.length() % 2 != 0) {
            Exception c = new ArrayIndexOutOfBoundsException();
            throw c;
        }
        String src = hexString.toUpperCase();
        int length = src.length();
        byte[] dst = new byte[length / 2];
        char[] hexChars = src.toCharArray();
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                dst[i / 2] = (byte) (CHARARRAY.indexOf(hexChars[i]));
            } else {
                dst[i / 2] = (byte) ((dst[i / 2]) << 4 | (CHARARRAY.indexOf(hexChars[i])));
            }
        }
        return dst;
    }

    public static void main(String[] args) throws Exception {
        String key = "1234567800000000";
        String data = "admin";
        System.out.println(Arrays.toString(key.getBytes()));
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
        byte[] result = cipher.doFinal(data.getBytes("utf-8"));
        String encodeBase64String = Base64.encodeBase64String(result);
        System.out.println(encodeBase64String);
    }
}
