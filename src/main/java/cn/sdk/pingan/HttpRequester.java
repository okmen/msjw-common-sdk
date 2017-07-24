package cn.sdk.pingan;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http工具类
 *
 * 2017年5月8日
 * @author zhengpin
 */
public class HttpRequester {
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequester.class);
    
    /**
     * 发送Form请求提交
     *
     * @param postUrl
     * @param params
     * @return
     */
    public static String post(String postUrl, Map<String, String> params) {
        String requestBody = "";
        if (params != null) {
            requestBody = mapParams2Str(params);
        }
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(postUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 如果是https则设置SSL信息
            if (httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) httpURLConnection;
                httpsConnection.setSSLSocketFactory(createSSLSocketFactory());
                httpsConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            }

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpURLConnection.setRequestMethod("POST");
            // 设置内容类型
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置报文体长度
            httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(requestBody.length()));
            httpURLConnection.connect();

            // 将请求报文写入输出流
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(requestBody);
            outputStreamWriter.flush();// 刷新

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpURLConnection.disconnect();
        } catch (ConnectException ce) {
            LOG.error("连接异常", ce);
        } catch (Exception e) {
            LOG.error("", e);
        }
        return buffer.toString();
    }
    
    /**
     * 用post方式发起API请求
     *
     * @param postUrl
     * @param requestBody
     * @return
     */
    public static String postApi(String postUrl, String requestBody){
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(postUrl);  
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            // 如果是https则设置SSL信息
            if(httpURLConnection instanceof HttpsURLConnection){
                HttpsURLConnection httpsConnection = (HttpsURLConnection) httpURLConnection;  
                httpsConnection.setSSLSocketFactory(createSSLSocketFactory());
                httpsConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            }
            
            httpURLConnection.setDoOutput(true);  
            httpURLConnection.setDoInput(true);  
            httpURLConnection.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpURLConnection.setRequestMethod("POST");  
            // 设置内容类型
            httpURLConnection.setRequestProperty("Content-Type", "text/xml");
            // 设置报文体长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(requestBody.length()));
            httpURLConnection.connect();
            
            // 将请求报文写入输出流
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(requestBody);
            outputStreamWriter.flush();// 刷新
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpURLConnection.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpURLConnection.disconnect();  
        } catch (ConnectException ce) {
            LOG.error("连接异常", ce);
        } catch (Exception e) {
            LOG.error("", e);
        }  
        return buffer.toString();
    }
    
    
	/**
	 * https get请求
	 *
	 * @param httpsUrl
	 * @return
	 */
    public static String httpsGet(String httpsUrl){
        StringBuffer buffer = new StringBuffer();  
        try {  
  
            URL url = new URL(httpsUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(createSSLSocketFactory());  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
        } catch (ConnectException ce) {  
            LOG.error("连接异常", ce);   
        } catch (Exception e) {  
            LOG.error("", e);
        }  
        return buffer.toString(); 
    }
    
    private static String mapParams2Str(Map<String, String> params) {
        StringBuilder paramsStr = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramsStr.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
            }
            paramsStr.deleteCharAt(paramsStr.length() - 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return paramsStr.toString();
    }
    
    private static SSLSocketFactory createSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
        // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
        TrustManager[] tm = { new MyX509TrustManager() };  
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
        sslContext.init(null, tm, new java.security.SecureRandom());  
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
        SSLSocketFactory ssf = sslContext.getSocketFactory(); 
        return ssf;
    }
    
    static class MyX509TrustManager implements X509TrustManager {

       public void checkClientTrusted(X509Certificate[] chain, String authType)
               throws CertificateException {
       }

       public void checkServerTrusted(X509Certificate[] chain, String authType)
               throws CertificateException {
       }

       public X509Certificate[] getAcceptedIssuers() {
           return null;
       }
    }
    
    static class TrustAnyHostnameVerifier implements HostnameVerifier {  
        public boolean verify(String hostname, SSLSession session)  
        {  
            return true;  
        }  
    }
}
