package cn.sdk.util;

import java.util.UUID;

/**
 * @ClassName: UUIDTool 
 * @Description: TODO(UUID生成工具类) 
 * @author yangzan
 * @date 2017年11月22日 下午5:01:53
 */
public class UUIDTool {
	
	/**
	 * @Title: getUUID 
	 * @Description: TODO(自动生成32位的UUid) 
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
    public static String getUUID() {  
        /*UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13)  
                + str.substring(14, 18) + str.substring(19, 23)  
                + str.substring(24);  
        return temp;*/  
          
        return UUID.randomUUID().toString().replace("-", "");  
    }  
    
    
    public static void main(String[] args) {  
//      String[] ss = getUUID(10);  
        for (int i = 0; i < 10; i++) {  
            System.out.println("ss[" + i + "]=====" + getUUID());  
        }  
    }  
    

}
