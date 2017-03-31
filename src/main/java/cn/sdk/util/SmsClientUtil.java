package cn.sdk.util;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsClientUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private HttpClient client;

    public HttpClient getClient() {
        if(client == null) {
            client = new DefaultHttpClient();
        }
        return client;
    }

    private void releaseConnection(HttpRequestBase request) {
        if (request != null) {
            request.releaseConnection();
        }
    }

    public boolean chuanglanPost(String uri, HttpEntity entity) {
        HttpPost postMethod = null;
        try {
            postMethod = new HttpPost(uri);
            postMethod.setEntity(entity); // 设置请求实体，例如表单数据
            HttpResponse response = this.getClient().execute(postMethod); // 执行请求，获取HttpResponse对象
            HttpEntity entity1 = response.getEntity(); // 响应实体/内容
            String ret = EntityUtils.toString(entity1, "UTF-8");
            logger.debug("chuanglanPost ret：" + ret);
            if (ret != null && ret.indexOf("<error>0</error>") != -1 || ret != null && ret.indexOf("\"status\":\"0\"") != -1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("chuanglanPost exception:", e);
            return false;
        } finally {
            releaseConnection(postMethod);
        }
        return false;
    }

    public boolean yimeiPost(String uri, HttpEntity entity) {// post方法提交
        HttpPost postMethod = null;
        try {
            postMethod = new HttpPost(uri);
            postMethod.setEntity(entity);// 设置请求实体，例如表单数据
            HttpResponse response = this.getClient().execute(postMethod);// 执行请求，获取HttpResponse对象
            HttpEntity entity1 = response.getEntity();// 响应实体/内容
            String ret = EntityUtils.toString(entity1, "gbk");
            logger.debug("yimeiPost ret：" + ret);

            if (ret != null && ret.indexOf("<error>0</error>") != -1 || ret != null && ret.indexOf("\"status\":\"0\"") != -1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("yimeiPost exception:", e);
            return false;
        } finally {
            releaseConnection(postMethod);// 释放连接
        }
        return false;
    }

    public boolean yimeiHttpsPost(String uri, HttpEntity entity) {// https
                                                                  // post方法提交
        HttpPost postMethod = null;
        HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
        X509ExtendedTrustManager xtm = new X509ExtendedTrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
            }
        };
        try {
            // TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext ctx = SSLContext.getInstance("TLS");
            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);
            // 创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            // 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
            postMethod = new HttpPost(uri);// 创建HttpPost
            postMethod.setEntity(entity);
            HttpResponse response = httpClient.execute(postMethod); // 执行POST请求
            HttpEntity entity1 = response.getEntity(); // 获取响应实体
            String ret = EntityUtils.toString(entity1, "gbk");
            if (ret != null && ret.indexOf("<error>0</error>") != -1 || ret != null && ret.indexOf("\"status\":\"0\"") != -1) {
                return true;
            }
        } catch (Exception e) {
            logger.error("yimeiHttpsPost exception:", e);
            return false;
        } finally {
            releaseConnection(postMethod);// 释放连接
        }
        return false;
    }


}
