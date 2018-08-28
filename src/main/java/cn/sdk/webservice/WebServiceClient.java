package cn.sdk.webservice;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
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
	//警司通需要过滤的接口编号
	private static List<String> jkids = new ArrayList<String>();
	
	//车管所需要过滤的接口编号
	private static List<String> cheguansuoMethod = new ArrayList<String>();
	
	//不限制调用时间的接口
	private static List<String> notHaveTime = new ArrayList<String>();
	//需要过滤的xml图片节点
	private static List<String> filterImgNodes = new ArrayList<>();
	
	private static List<String> replaceNodes = new ArrayList<>();
	
	private static String imgMsg = "";
	
	public static final Logger logger= Logger.getLogger(WebServiceClient.class);
	
	//private static final String imageNodes = "TCZP,TCGZDZP,FPTP,CSTP,zjtp,wftp,zp,jbtp1,jbtp2,jbtp3,jbtp4,jbtp5,PHOTO6,PHOTO9,PHOTO18,PHOTO16,photo9,photo32,photo33,CZSFZMHMTPA,CZSFZMHMTP,SFZHMA,YQZM,SFZHMB,JZZA,JZZB,PHOTO31,STTJSBB,JSZ,";
    
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
    	jkids.add("WFTPCX");
    	jkids.add("EZ1002");
    	jkids.add("EZ1001");
    	
    	cheguansuoMethod.clear();
    	notHaveTime.clear();
    	notHaveTime.add("EZ1002");
    	notHaveTime.add("EZ1001");
    	
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
		filterImgNodes.add("wftp");
		
		/*replaceNodes.clear();
		String[] tempImgNodes = imageNodes.split(",");
		replaceNodes = Arrays.asList(tempImgNodes);*/
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
	 * 过滤xml中的base64图片字符串
	 * @param base64Str base64字符串
	 * @param replaceNodes 图片节点 List集合
	 * @return
	 */
	public static String filterBase64(String base64Str,List<String> replaceNodes){
		String returnStr = "";
		try {
			for(int i=0;i<replaceNodes.size();i++){
				String replaceNode = replaceNodes.get(i);
				if(base64Str.contains(replaceNode)){
					int startIndex = base64Str.indexOf(replaceNode);
					startIndex = startIndex + 1 + replaceNode.length();
					int endIndex = base64Str.lastIndexOf(replaceNode);
					endIndex = endIndex - 2;
					String replaceStr = base64Str.substring(startIndex, endIndex);
					base64Str = base64Str.replace(replaceStr, "照片base64不打印");
				}
				if(i == replaceNodes.size() - 1){
					returnStr = base64Str;
				}
			}
		} catch (Exception e) {
			logger.error("过滤xml中的base64图片字符串  错误", e);
		}
		return returnStr;
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
    public static JSONObject changedWebService(String url,String method,String jkid,String xml,String userid,String userpwd,String key) throws Exception{
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
            if(result >= 30 && !notHaveTime.contains(jkid)){
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
        	if (respJson.startsWith("<?xml version=\"1.0\" encoding=\"utf-8\"?>")) {
        		Document doc1 = DocumentHelper.parseText(respJson);
            	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
			}else{
				int startIndex = respJson.indexOf("<response>");
	        	int endIndex = respJson.lastIndexOf("</msg></return>");
	        	String substring = respJson.substring(startIndex, endIndex);
	        	substring = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+substring;
	            Document doc1 = DocumentHelper.parseText(substring);
	        	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
			}
        	
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
     * @param jkid 接口编号
     * @param xml 组装的xml参数
     * @param userid 用户名
     * @param userpwd 密码
     * @param key 秘钥
     * @return json字符串
     * @throws Exception
     */
    public static JSONObject changed2WebService(String url,String method,String jkid,String xml,String userid,String userpwd,String key) throws Exception{
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
            if(result >= 30 && !notHaveTime.contains(jkid)){
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
        	if (respJson.contains("<response>")) {
        		int startIndex = respJson.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            	int endIndex = respJson.lastIndexOf("</msg></return>");
            	String substring = respJson.substring(startIndex, endIndex);
                Document doc1 = DocumentHelper.parseText(substring);
            	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
			}else{
				int startIndex = respJson.indexOf("<code>");
	        	int endIndex = respJson.lastIndexOf("</return>");
	        	String substring = respJson.substring(startIndex, endIndex);
	        	substring = "<?xml version=\"1.0\" encoding=\"utf-8\"?><response>"+substring+"</response>";
	            Document doc1 = DocumentHelper.parseText(substring);
	        	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
			}
        	
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
     * @param jkid 接口编号
     * @param xml 组装的xml参数
     * @param userid 用户名
     * @param userpwd 密码
     * @param key 秘钥
     * @return json字符串
     * @throws Exception
     */
    public static JSONObject complexWebService(String url,String method,String jkid,String xml,String userid,String userpwd,String key) throws Exception{
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
            if(result >= 30 && !notHaveTime.contains(jkid)){
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
        	int startIndex = respJson.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        	int endIndex = respJson.lastIndexOf("</msg></return>");
        	String substring = respJson.substring(startIndex, endIndex);
        	//System.out.println(substring);
        	
            Document doc1 = DocumentHelper.parseText(substring);
//            Element rootEle1 = doc1.getRootElement();
//            String msg2 = rootEle1.elementTextTrim("msg");
//            Document doc2= DocumentHelper.parseText(msg2);
//            Element rootEle2 = doc2.getRootElement();
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
    	
    	logger.info("请求报文："+xml);
    	//请求响应的结果xml
		String respXml = "";
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
            //请求相应结果xml String
			respXml = (String) call.invoke(new Object[]{userid,userpwd,jkid,srcs});
            long endTime = System.currentTimeMillis();
            long result = (endTime - startTime) / 1000;
            //如果执行时间过长 打印出日志
            if(result >= 30 && !notHaveTime.contains(jkid)){
            	logger.info(jkid + "接口编号执行耗时:" + result + " 秒");
            	throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
            }
          
            //解密
            Document doc= DocumentHelper.parseText(respXml);
            Xml2Json.dom4j2Json(doc.getRootElement(),json);
            String code=json.getString("code");
            if("0000".equals(code)){
            	String msg=json.getString("msg");
            	try {
            		String respJson = DESCorder.decryptMode(msg,key, "utf-8");
            		Document doc1 = DocumentHelper.parseText(respJson);
                	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
				} catch (Exception e) {
					logger.error("msg解密报错,url=" + url + ",method=" + method + ",jkid=" + jkid + ",xml="+respXml,e);
				}
            }else {
				return json;
			}
         
//            	String msg = (String) json.get("msg");
//            	//返回的状态码
//            	//解密
//            	respJson = DESCorder.decryptMode(msg,key, "utf-8");
//            	//System.out.println(respJson);
//            	if(!jkids.contains(jkid)){
//            		logger.info("响应的xml：" + respJson);
//            	}
//            	
//            	Document doc1 = DocumentHelper.parseText(respJson);
//            	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
//            	
//            	if(!jkids.contains(jkid)){
//            		logger.info("xml转换成json：" + json2);
//            	}

        } catch (Exception e) {
        	logger.error("webservice调用错误,url=" + url + ",method=" + method + ",jkid=" + jkid + ",xml=" +respXml,e);
            throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
        }  
		return json2;
	}
    
    /**
     * webservice请求,携带超时时间(毫秒)
     * @param url 请求url
     * @param method 方法名称
     * @param jkid 接口编号
     * @param xml 组装的xml参数
     * @param userid 用户名
     * @param userpwd 密码
     * @param key 秘钥
     * @param timeout 超时时间(毫秒)
     * @return json字符串
     * @throws Exception
     */
    public static JSONObject requestWebServiceWithTimeout(String url,String method,String jkid,String xml,String userid,String userpwd,String key,int timeout) throws Exception{
    	filterInterfaceLog();
    	//url = "http://123.56.180.216:19002/xxfbpt/services/xxfbptservices";
    	String base64Str = "";
    	String logXml = xml;
    	/*String reqXml = filterBase64(xml, replaceNodes);
    	if(StringUtils.isNotBlank(reqXml)){
    		logger.info("请求xml为：" + reqXml);
    	}else{
    		logger.info("请求xml为：" + xml);
    	}*/
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
            Call call = (Call) service.createCall();
            //设置超时时间
            if(timeout < 5000){
            	timeout = 5000;
            	call.setTimeout(timeout);
            }else{
            	call.setTimeout(timeout);
            }
            logger.info("接口编号(" + jkid + ")设置webservice超时时间：" + timeout + "ms");
            
            call.setTargetEndpointAddress(url);
            call.setOperationName(method);//ws方法名  
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
//            if(result >= 30 && !notHaveTime.contains(jkid)){
//            	logger.info(jkid + "接口编号执行耗时:" + result + " 秒");
//            	throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
//            }
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
        	//System.out.println(respJson);
        	
            Document doc1 = DocumentHelper.parseText(respJson);
        	Xml2Json.dom4j2Json(doc1.getRootElement(),json2);
        	
        	if(!jkids.contains(jkid)){
            	logger.info("xml转换成json：" + json2);
            }
        } catch (Exception e) {
        	logger.error("webservice调用错误,url=" + url + ",method=" + method + ",jkid=" + jkid + ",xml=" + "xml不打印" + ",userid=" + userid + ",userpwd=" + userpwd + ",key=" + key,e);
            throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
        }  
		return json2;
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
    public static String requestWebServiceReturnXml(String url,String method,String jkid,String xml,String userid,String userpwd,String key) throws Exception{
    	filterInterfaceLog();
    	String base64Str = "";
    	String logXml = xml;
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
            call.setTimeout(30000);
            //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面
            call.addParameter("userid",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
            call.addParameter("userpwd",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
            call.addParameter("jkid",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
            call.addParameter("srcs",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
            
            respXml = (String) call.invoke(new Object[]{userid,userpwd,jkid,srcs});
            
        /*    MessageContext messageContext = call.getMessageContext();
            Message reqMsg = messageContext.getRequestMessage();
            logger.info("请求报文："+reqMsg.getSOAPPartAsString());
            
            logger.info("返回报文:"+respXml);*/
            
            //解密
            Document doc= DocumentHelper.parseText(respXml);
            Xml2Json.dom4j2Json(doc.getRootElement(),json);
            
            //返回的数据
            String msg = (String) json.get("msg");
            //返回的状态码
            //解密
        	respJson = DESCorder.decryptMode(msg,key, "utf-8");
        	return respJson;
        } catch (Exception e) {
        	logger.error("webservice调用错误,url=" + url + ",method=" + method + ",jkid=" + jkid + ",xml=" + xml + ",userid=" + userid + ",userpwd=" + userpwd + ",key=" + key,e);
            throw new WebServiceException(Integer.valueOf(MsgCode.webServiceCallError), MsgCode.webServiceCallMsg);
        }
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
            if(result >= 30 ){
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
    
    /**
     * 車管所webService調用 旧版
     * @param url  正式环境地址 http://app.stc.gov.cn:8092/book/services/wsBookService
     * @param method webservice方法名称
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static JSONObject vehicleAdministrationWebService(String url,String method,LinkedHashMap<String, Object> params) throws Exception{
    	JSONObject json=new JSONObject();
    	try {
    		 Service service = new Service();
             Call call = (Call) service.createCall() ;
             call.setTargetEndpointAddress(url) ;  
             call.setOperationName(method) ;//ws方法名  
             //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面
             List<String> objects = new LinkedList<String>();
             for (Entry<String, Object> entry : params.entrySet()) {
            	  call.addParameter(entry.getKey(),org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
            	  objects.add(entry.getValue().toString());
             }
             call.setReturnType(XMLType.XSD_STRING);
             call.setUseSOAPAction(true);
             long startTime = System.currentTimeMillis();
             String respXml = (String) call.invoke(objects.toArray());
             long endTime = System.currentTimeMillis();
             long result = (endTime - startTime) / 1000;
             if(result >= 30){
             	logger.error(method + "车管所接口方法执行耗时:" + result + " 秒");
             	throw new WebServiceException(Integer.valueOf(MsgCode.vehicleAdministrationwebServiceCallError), MsgCode.vehicleAdministrationwebServiceCallMsg);
             }
             /*logger.info("车管所响应的xml：" + respXml);
             Document doc= DocumentHelper.parseText(respXml);
             Xml2Json.dom4j2Json(doc.getRootElement(),json);
             logger.info("xml转换成json：" + json);*/
             logger.info("车管所响应的xml：" + respXml);
             //将xml转为json  
             cn.sdk.webservice.jsonxml.JSONObject xmlJSONObj = cn.sdk.webservice.jsonxml.XML.toJSONObject(respXml);  
             //设置缩进  
             String jsonPrettyPrintString = xmlJSONObj.toString(4);  
             //输出格式化后的json
             logger.info("xml转json之后的结果" + jsonPrettyPrintString);
             json = JSONObject.parseObject(jsonPrettyPrintString);
             json= json.getJSONObject("ResponseMessage");
		} catch (Exception e) {
			logger.error("车管所webservice调用错误，url=" + url + ",=method" + method + ",params=" + params,e);
        	throw new WebServiceException(Integer.valueOf(MsgCode.vehicleAdministrationwebServiceCallError), MsgCode.vehicleAdministrationwebServiceCallMsg);
		}
		return json;
	}
    
    /**
     * 车管所webservice新版
     * @param url 正式环境地址 http://app.stc.gov.cn:8092/book/services/wsBookUniformService?wsdl
     * @param jkId 接口ID号
     * @param data Xml报文
     * @param account 账号
     * @param password 序列号
     * @return
     * @throws Exception
     */
    public static JSONObject vehicleAdministrationWebServiceNew(String url,String jkId,String data,String account,String password) throws Exception{
    	filterInterfaceLog();
    	String base64Str = "";
    	String logXml = data;
		String respXml = "";
		String respJson = "";
		JSONObject json = new JSONObject();
		try {  
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(url));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("http://appointment.service.admin.rich.com/uniformAccess");
			call.setOperationName(new QName("http://appointment.service.admin.rich.com/","uniformAccess"));
			call.addParameter("in0", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in1", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in2", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in3", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			call.setTimeout(40000);
            long startTime = System.currentTimeMillis();
            respXml = (String) call.invoke(new Object[] {jkId,account,password,DESCorder.encryptAES(data)});// 调用方法
            long endTime = System.currentTimeMillis();
            long result = (endTime - startTime) / 1000;
            if(result >= 30 && !notHaveTime.contains(jkId)){
            	logger.info(jkId + "接口编号执行耗时:" + result + " 秒");
            	throw new WebServiceException(Integer.valueOf(MsgCode.vehicleAdministrationwebServiceCallError), MsgCode.vehicleAdministrationwebServiceCallMsg);
            }
            if(!jkids.contains(jkId)){
            	logger.info("响应的xml：" + respXml);
            }
            cn.sdk.webservice.jsonxml.JSONObject xmlJSONObj = cn.sdk.webservice.jsonxml.XML.toJSONObject(respXml);  
            //设置缩进  
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            //解密
            json = JSONObject.parseObject(jsonPrettyPrintString);
            json= json.getJSONObject("ResponseMessage");
        	if(!jkids.contains(jkId)){
            	logger.info("xml转换成json：" + json);
            }
        	String code = json.getString("code");
        	/*if(!"00".equals(code)){
        		logger.error(json.get("msg") + "\n" + json.getString("result"));
                throw new WebServiceException(Integer.valueOf(MsgCode.vehicleAdministrationwebServiceCallError), MsgCode.vehicleAdministrationwebServiceCallMsg);
        	}*/
        } catch (Exception e) {
        	logger.error("webservice调用错误,url=" + url  + ",jkid=" + jkId + ",xml=" + data + ",userid=" + account + ",password=" + password,e);
            throw new WebServiceException(Integer.valueOf(MsgCode.vehicleAdministrationwebServiceCallError), MsgCode.vehicleAdministrationwebServiceCallMsg);
        }  
		return json;
	}
    
    
    /**
	 * JK01
	 * 
	 * @Author        succ
	 * @date 2017-7-18 上午10:52:01
	 */
	public static void createDriveInfo() {
		Call call = null;
		try {
			Service service = new Service();
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL("http://app.stc.gov.cn:8092/book/services/wsBookUniformService"));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("http://appointment.service.admin.rich.com/uniformAccess");
			call.setOperationName(new QName("http://appointment.service.admin.rich.com/","uniformAccess"));
			call.addParameter("in0", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in1", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in2", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("in3", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型
			
			StringBuilder sb = new StringBuilder();
			sb.append("<root>").append("<orgId>4028818c473569ae0147356e1cec0002</orgId><businessTypeId>402881854e6214d7014e625a57b70003</businessTypeId>")
			.append("<name>test1</name>").append("<idTypeId>e4e48584399473d20139947f125e2b2c</idTypeId>")
			.append("<idNumber>431227199701211123</idNumber>").append("<mobile>13407665642</mobile>")
			.append("<appointmentDate>2017-07-20</appointmentDate>").append("<appointmentTime>09:00-11:00</appointmentTime>")
			.append("<bookerName>test1</bookerName>").append("<bookerIdNumber>431227199701211123</bookerIdNumber>")
			.append("<bookType>0</bookType>").append("<bookerMobile>13412341234</bookerMobile>")
			.append("<msgNumber>148835</msgNumber>").append("</root>");
			String re = (String) call.invoke(new Object[] {"JK01","cdkjcs","4028823f5cec9613015d07046034729c",DESCorder.encryptAES(sb.toString())});// 调用方法
			System.out.println(re);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
			
		/*String xml = "<request><userid>WX02</userid><userpwd>WX02@168</userpwd ><lrip>123.56.180.216</lrip><lrmac>00:16:3e:10:16:4d</lrmac></request>";
	
		String url = "https://szjjapi.chudaokeji.com/yywfcl/services/yywfcl";
		String method = "getCldbmAll";
		
		String inxml = "<request>"+
				"<userid>WX02</userid>"+
				"<userpwd>WX02@168</userpwd>"+
				"<lrip>123.56.180.216</lrip>"+
				"<lrmac>00:16:3e:10:16:4d</lrmac>"+
				"</request>"; */
	//	String respStr = getInstance().easyWebService(url, method, inxml);
		
		createDriveInfo();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<root>").append("<orgId>4028818c473569ae0147356e1cec0002</orgId><businessTypeId>402881854e6214d7014e625a57b70003</businessTypeId>")
		.append("<name>test1</name>").append("<idTypeId>e4e48584399473d20139947f125e2b2c</idTypeId>")
		.append("<idNumber>431227199701211123</idNumber>").append("<mobile>13407665642</mobile>")
		.append("<appointmentDate>2017-07-20</appointmentDate>").append("<appointmentTime>09:00-11:00</appointmentTime>")
		.append("<bookerName>test1</bookerName>").append("<bookerIdNumber>431227199701211123</bookerIdNumber>")
		.append("<bookType>0</bookType>").append("<bookerMobile>13412341234</bookerMobile>")
		.append("<msgNumber>148835</msgNumber>").append("</root>");
		vehicleAdministrationWebServiceNew("http://app.stc.gov.cn:8092/book/services/wsBookUniformService", "JK01", sb.toString(), "cdkj", "4028823f5d4fed1d015d4ff8056f0000");
		
	
	}
    
    
}
