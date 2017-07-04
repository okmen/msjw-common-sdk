package cn.sdk.webservice;

import java.util.LinkedHashMap;

public class TEst {
	public static void main(String[] args) throws Exception {
		//String url = "http://app.stc.gov.cn:8092/book/services/wsBookService";
		String url = "http://cheguansuo.chudaokeji.com/book/services/wsBookService";
		String method = "getCarTypes";
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("arg0", 0);
		params.put("arg1", 1);
		
		WebServiceClient.vehicleAdministrationWebService(url, method, params);
		
		 /*Service service = new Service();
         Call call = (Call) service.createCall() ;
         call.setTargetEndpointAddress(url) ;  
         call.setOperationName(method) ;//ws方法名  
         //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面
         call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
         call.addParameter("arg1",org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
         call.setReturnType(XMLType.XSD_STRING);
         call.setUseSOAPAction(true);
         String arg0 = "0";
         String arg1 = "1";
         String respXml = (String) call.invoke(new Object[]{arg0,arg1});
         System.out.println(respXml);*/
	}
}
