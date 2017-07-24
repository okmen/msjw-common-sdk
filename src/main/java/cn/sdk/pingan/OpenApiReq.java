package cn.sdk.pingan;

import java.io.Serializable;


/**
 * OpenAPI请求公共报文
 *
 * 2017年4月20日
 * @author zhengpin
 */
public class OpenApiReq implements Serializable{
    private static final long serialVersionUID = 134450442124599102L;
    
    private String appId = "";// 合作方唯一标识
    private String serialNo = "";// 通讯流水号
    private String timeStamp = "";// 时间戳，格式为yyyyMMddHHmmss
    private String method = "";// 接口名称
    private String data = "";// 业务数据加密字符串
    private String sign = "";// 对上述所有字段序列化后进行签名的值
    
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getSerialNo() {
        return serialNo;
    }
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    @Override
    public String toString() {
        return "OpenApiReq [appId=" + appId + ", serialNo=" + serialNo + ", timeStamp=" + timeStamp
                + ", method=" + method + ", data=" + data + ", sign=" + sign + "]";
    }
}
