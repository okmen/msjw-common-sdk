package cn.sdk.util;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
public class HMACSHA1 {   
  
    private static final String HMAC_SHA1 = "HmacSHA1";   
   
     /**  
      * 生成签名数据  
      *   
      * @param data 待加密的数据  
      * @param key  加密使用的key  
      * @throws InvalidKeyException  
      * @throws NoSuchAlgorithmException  
      */  
     public static byte[] getSignature(String data,String key){
    	byte[] rawHmac = null;
    	try
    	{
	       	 byte[] keyBytes=key.getBytes();
	         SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);   
	         Mac mac = Mac.getInstance(HMAC_SHA1);   
	         mac.init(signingKey);   
	         rawHmac = mac.doFinal(data.getBytes());    		
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException(e);
    	}
         return rawHmac;   
     }
     
     public static String byteToHexString(byte ib){
      char[] Digit={
        '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
      };
      char[] ob=new char[2];
      ob[0]=Digit[(ib>>>4)& 0X0f];
      ob[1]=Digit[ib & 0X0F];
      String s=new String(ob);
      return s;         
     }     
 } 


