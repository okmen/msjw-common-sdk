package cn.sdk.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONObject;

import cn.sdk.exception.WebServiceException;
import cn.sdk.util.MsgCode;
/**
 * webservice客户端工具类
 * @author Mbenben
 *
 */
@SuppressWarnings(value="all")
public class WebServiceClient {
	//需要过滤的接口编号
	private static List<String> jkids = new ArrayList<String>();
	//需要过滤的xml图片节点
	private static List<String> filterImgNodes = new ArrayList<>();
	
	private static String imgMsg = "";
	
	public static final Logger logger= Logger.getLogger(WebServiceClient.class);
	
    private WebServiceClient() {
    } 
    
    //使用volatile关键字保其可见性  
    volatile private static WebServiceClient instance = null;
    
    /**
     * 图片Base64过滤接口log
     */
    private static void filterInterfaceLog(){
    	jkids.clear();
    	jkids.add("DZJSZ");
    	jkids.add("DZXSZ");
    	jkids.add("EZ1007");
    	
    	filterImgNodes.clear();
    	filterImgNodes.add("CZSFZMHMTPA");
		filterImgNodes.add("CZSFZMHMTP");
		filterImgNodes.add("qt_tp");
		filterImgNodes.add("zp");
		filterImgNodes.add("PHOTO6");
		filterImgNodes.add("PHOTO9");
		filterImgNodes.add("jbtp1");
		filterImgNodes.add("jbtp2");
		filterImgNodes.add("jbtp3");
    }
    
    public static WebServiceClient getInstance(){
        try {    
            if(instance != null){//懒汉式   
                  
            }else{  
                //创建实例之前可能会有一些准备性的耗时工作   
                Thread.sleep(100);  
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
    	filterInterfaceLog();
    	//url = "http://123.56.180.216:19002/xxfbpt/services/xxfbptservices";
    	String base64Str = "";
    	String logXml = xml;
    	
    	//logger.info("调试日志：filterImgNodes" + filterImgNodes.toString());
    	/*if(null != filterImgNodes){
    		for(String filterImgNode : filterImgNodes){
    			if(xml.contains(filterImgNode)){
    				base64Str = StringUtils.substringBetween(xml, "<"+ filterImgNode + ">", "</"+ filterImgNode + ">");
    				logXml = logXml.replace(base64Str, imgMsg);
    			}
    		}
    	}*/
    	/*if(null != filterImgNodes){
    		for(String filterImgNode : filterImgNodes){
    			if(!xml.contains(filterImgNode)){
    				logger.info("requestWebService请求的xml：" + logXml);
    			}
    		}
    	}*/
    	
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
            
            long startTime = System.currentTimeMillis();
            respXml = (String) call.invoke(new Object[]{userid,userpwd,jkid,srcs});
            long endTime = System.currentTimeMillis();
            long result = (endTime - startTime) / 1000;
            if(result >= 20){
            	logger.info(jkid + "接口编号执行耗时:" + result + " 秒");
            	throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
            }
            if(!jkids.contains(jkid)){
            	logger.info("响应的xml：" + respXml);
            }
            //解密
            Document doc= DocumentHelper.parseText(respXml);
            Xml2Json.dom4j2Json(doc.getRootElement(),json);
            
            //返回的数据
            String msg = (String) json.get("msg");
            //返回的状态码
            //解密
        	respJson = DESCorder.decryptMode(msg,key, "utf-8");
        	
            Document doc1 = DocumentHelper.parseText(respJson);
        	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
        	
        	if(!jkids.contains(jkid)){
            	logger.info("xml转换成json：" + json2);
            }
        } catch (Exception e) {
        	logger.error("webservice调用错误,url=" + url + ",method=" + method + ",jkid=" + jkid + ",xml=" + xml + ",userid=" + userid + ",userpwd=" + userpwd + ",key=" + key,e);
            throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
        }  
		return json2;
	}
	
    
    /**
     * webservice请求
     * @param url 请求url
     * @param method 方法名称
     * @param xml 组装的xml参数
     * @param userid 用户名
     * @param userpwd 密码
     * @param key 秘钥
     * @return
     * @throws Exception
     */
    public static JSONObject easyWebService(String url,String method,String xml) throws Exception{
    	
    	logger.info("easyWebService请求的xml：" + xml);
    	
		String respXml = "";
		String respJson = "";
		JSONObject json=new JSONObject();	
		try {  
            Service service = new Service();
            Call call = (Call) service.createCall() ;
            call.setTargetEndpointAddress(url) ;  
            call.setOperationName(method) ;//ws方法名  
            //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面  
            call.addParameter("in0",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN); 
            call.setReturnType(XMLType.XSD_STRING);  
            call.setUseSOAPAction(true);
            long startTime = System.currentTimeMillis();
            respXml = (String) call.invoke(new Object[]{xml}) ;
            long endTime = System.currentTimeMillis();
            long result = (endTime - startTime) / 1000;
            if(result >= 20){
            	logger.error(method + "接口方法执行耗时:" + result + " 秒");
            	throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
            }
            logger.info("响应的xml：" + respXml);
            //解密
            Document doc= DocumentHelper.parseText(respXml);
            
            Xml2Json.dom4j2Json(doc.getRootElement(),json);
            logger.info("xml转换成json：" + json);
    		          
        } catch (Exception e) {
        	logger.error("webservice调用错误，url=" + url + ",=method" + method + ",xml=" + xml,e);
        	throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
        }  
		return json;
	}
    
    
	
	public static void main(String[] args) throws Exception {
			
		String xml = "<request><userid>WX02</userid><userpwd>WX02@168</userpwd ><lrip>123.56.180.216</lrip><lrmac>00:16:3e:10:16:4d</lrmac></request>";
	
		String url = "https://szjjapi.chudaokeji.com/yywfcl/services/yywfcl";
		String method = "getCldbmAll";
		
		String inxml = "<request>"+
				"<userid>WX02</userid>"+
				"<userpwd>WX02@168</userpwd>"+
				"<lrip>123.56.180.216</lrip>"+
				"<lrmac>00:16:3e:10:16:4d</lrmac>"+
				"</request>"; 
	//	String respStr = getInstance().easyWebService(url, method, inxml);
		
	}
    
    
}
