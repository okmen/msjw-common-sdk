package cn.sdk.webservice;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONObject;

import cn.sdk.util.Base64;
import cn.sdk.util.DESUtils;
/**
 * webservice客户端工具类
 * @author Mbenben
 *
 */
@SuppressWarnings(value="all")
public class WebServiceClient {
	public static final Logger logger= Logger.getLogger(WebServiceClient.class);
	
    private WebServiceClient() {
    } 
    
    //使用volatile关键字保其可见性  
    volatile private static WebServiceClient instance = null;
    
    public static WebServiceClient getInstance() {  
        try {    
            if(instance != null){//懒汉式   
                  
            }else{  
                //创建实例之前可能会有一些准备性的耗时工作   
                Thread.sleep(300);  
                synchronized (WebServiceClient.class) {  
                    if(instance == null){
                    	//二次检查  
                        instance = new WebServiceClient();  
                    }  
                }  
            }   
        } catch (InterruptedException e) {   
            e.printStackTrace();  
        }  
        return instance;  
    }
    
    /**
     * webservice请求
     * @param url 请求url
     * @param method 方法名称
     * @param jkid 接口编号
     * @param xml 组装的xml参数
     * @param userid 用户名
     * @param userpwd 密码
     * @param key 秘钥
     * @return json字符串
     * @throws Exception
     */
    public static JSONObject requestWebService(String url,String method,String jkid,String xml,String userid,String userpwd,String key) throws Exception{
		String respXml = "";
		String respJson = "";
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		String srcs = DESCorder.encryptModeToString(xml,key);
		try {  
            Service service = new Service();
            Call call = (Call) service.createCall() ;
            call.setTargetEndpointAddress(url) ;  
            call.setOperationName(method) ;//ws方法名  
            //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面  
            call.addParameter("userid",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);  
            call.addParameter("userpwd",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);  
            call.addParameter("jkid",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN); 
            call.addParameter("srcs",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN); 
            call.setReturnType(XMLType.XSD_STRING);  
            call.setUseSOAPAction(true);
            respXml = (String) call.invoke(new Object[]{userid,userpwd,jkid,srcs}) ;
            logger.info("响应的xml：" + respXml);
            //解密
            Document doc= DocumentHelper.parseText(respXml);
           
            Xml2Json.dom4j2Json(doc.getRootElement(),json);
            logger.info("xml转换成json：" + json);
    		
            //返回的数据
            String msg = (String) json.get("msg");
            //返回的状态码
            String code = (String) json.get("code");
            if("0000".equals(code)){
            	//解密
            	respJson = DESCorder.decryptMode(msg,key, "utf-8");
            	
                Document doc1 = DocumentHelper.parseText(respJson);
            	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
            }else{
            	logger.error("webservice返回非0000状态，xml内容为" + respXml);
            }
        } catch (Exception e) {
        	logger.error("webservice调用错误" + e);
            e.printStackTrace();
        }  
		return json2;
	}
	
	
	public static void main(String[] args) throws Exception {
		String srcs = "G22ymwlgEJ82k5XwgzgTZ4IyUVLlrH+z3/8X6R8jm+wNOVrzXwQP8bZ7eOcFCxRUHj87zoP5frTK2k3jkDQ2pzum04w4bQe2GQboWgAAR/WflsfJ/vYu+XslEmNfJ85+e2REwV7y7qBOjQ03+Gbi+MviBBiRcM/dhU05gzCUZXB78sY0txQSSa8QaPyTh2dZdKcnpM4xr90=";
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><REQUEST><USERNAME>15920071829</USERNAME><PWD>168321</PWD><YHLY>WX_XCX</YHLY><SFZMHM></SFZMHM><XM></XM></REQUEST>";
		String dd = DESUtils.encrypt(xml, "94D863D9BE7FB032E6A19430CC892610");
		String xxxxxd = Base64.encode(dd.getBytes());
		System.out.println(xxxxxd);
		
		
		String url = "http://123.56.180.216:19002/xxfbpt/services/xxfbptservice";
		String method = "xxptSchuding";
		String jkid = "xxcj03";
		
		JSONObject jsonObject = getInstance().requestWebService(url, method, jkid, xml,"WX02","WX02@168","94D863D9BE7FB032E6A19430CC892610");
		
		
		System.out.println(jsonObject);
	}
    
    
}
