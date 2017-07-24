package cn.sdk.pingan;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 class
 *
 * 计算消息签名接口.
 */
public class SHA1 {
	
    /**
     * 用SHA1算法生成安全签名
     *
     * @param data 待签名数据
     * @return 安全签名
     * @throws CryptException
     */
    public static String genSign(String[] data) throws CryptException {
        for(int i = 0; i < data.length; i++){
            if(data[i] == null){
                data[i] = "";
            }
        }
        StringBuffer sb = new StringBuffer();
        // 字符串排序
        Arrays.sort(data);
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
        }
        String str = sb.toString();
        return genSign(str);
    }
    
    /**
     * 用SHA1算法生成安全签名
     * 
     * @param data 待签名数据
     * @return 安全签名
     * @throws CryptException 
     */
    public static String genSign(String data) throws CryptException {
        try {
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(data.getBytes());
            byte[] digest = md.digest();

            return BytesHexStringUtil.bytesToHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptException(CryptException.ComputeSignatureError);
        }
    }
    
    /**
     * 校验签名
     *
     * @param data 待验证数据
     * @param sign 签名值
     * @return
     */
    public static boolean verifySign(String[] data, String sign) {
        boolean isVerify = false;
        try{
         // 生成原数据生成签名
            String verifySign = genSign(data);
            // 对比签名值
            if(verifySign.equals(sign)){
                isVerify = true; 
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isVerify;
    }
    
    /**
     * 校验签名
     *
     * @param data 待验证数据
     * @param sign 签名值
     * @return
     */
    public static boolean verifySign(String data, String sign) {
        boolean isVerify = false;
        try{
         // 生成原数据生成签名
            String verifySign = genSign(data);
            // 对比签名值
            if(verifySign.equals(sign)){
                isVerify = true; 
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isVerify;
    }
    
    public static void main(String[] args) throws CryptException {
        String appId = "APPID88888";
        String serialNo = "sn12345678900987654321";
        String timeStamp = "20161201141000";
        String data = "EpQ2wjVMPiM4BcmmJnXaydzQIP2F/ZU69SEveShrcaA=";
        String appKey = "123456";
        String[] dataArr = new String[]{appId, appKey, serialNo, timeStamp, data}; 
        String sign = genSign(dataArr);
        System.out.println(sign);
        boolean isVerify = verifySign(dataArr, sign);
        System.out.println(isVerify);
    }

}
