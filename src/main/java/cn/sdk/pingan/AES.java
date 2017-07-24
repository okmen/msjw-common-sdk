package cn.sdk.pingan;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final String CHARSET = "utf-8";
    

    /**
     * 加密
     *
     * @return
     * @throws CryptException 
     */
    public static String encrypt(String text, String aesKey) throws CryptException {
        try{
            // 对密钥进行MD5处理
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] aesKeyBytes = md5.digest(aesKey.getBytes(CHARSET));
            
            // AES
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(aesKeyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 加密
            byte[] encrypted = cipher.doFinal(text.getBytes(CHARSET));

            // 将密文转换成hexString
            String HexString = BytesHexStringUtil.bytesToHexString(encrypted);
            
            return HexString;
        }catch(Exception e){
            throw new CryptException(CryptException.EncryptAESError);
        }
    }
    
    
    /**
     * 解密
     *
     * @return
     * @throws CryptException 
     */
    public static String decrypt(String text, String aesKey) throws CryptException{
        try{
            // 对密钥进行MD5处理
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] aesKeyBytes = md5.digest(aesKey.getBytes(CHARSET));
            
            // AES
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(aesKeyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // 将hexString密文转换成byte数组
            byte[] hexEncrypted = BytesHexStringUtil.hexStringToBytes(text); 
            
            // 解密
            byte[] decrypted = cipher.doFinal(hexEncrypted);

            return new String(decrypted, CHARSET);
        }catch(Exception e){
            throw new CryptException(CryptException.DecryptAESError);
        }
    }
    
    public static void main(String[] args) throws CryptException {
        String text = "{\"billNo\":\"000000000001\"}";
        String aesKey = "123456";
        String encrypted = encrypt(text, aesKey);
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted, aesKey);
        System.out.println(decrypted);
    }
}
