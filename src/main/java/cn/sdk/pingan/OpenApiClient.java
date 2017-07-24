package cn.sdk.pingan;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


/**
 * OpenAPI客户端
 *
 * 2017年4月21日
 * @author zhengpin
 */
public class OpenApiClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(OpenApiClient.class);
    
    /**
     * 时间戳格式化
     */
    private static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private String gateway;// api网关地址
    private String appId;// 合作方标识ID
    private String appKey;// 合作方交互key
    private boolean isDataEncrypt;// 是否对业务数据进行加密
    private boolean isSign;// 是否使用签名
    
    /**
     * 实例化OpenAPI客户端,客户端实例化一次可多次使用
     * 
     * @param gateway openapi的请求地址
     * @param appId 
     * @param appKey 
     * @param isDataEncrypt 是否需要对报文data部分进行加密
     */
    public OpenApiClient(String gateway, String appId, String appKey, boolean isDataEncrypt, boolean isSign){
        this.gateway = gateway;
        this.appId = appId;
        this.appKey = appKey;
        this.isDataEncrypt = isDataEncrypt;
        this.isSign = isSign;
    }
    
    /**
     * 生成请求的Json字符串
     *
     * @param dataJson
     * @return
     */
    public OpenApiReq genReqJson(String method, String dataJson){
        OpenApiReq req = new OpenApiReq();
        req.setAppId(appId);// appId
        req.setSerialNo(RandomStringGenerator.getRandomStringByLength(32));// 生成32位随机数作为通讯流水号
        req.setTimeStamp(TIME_STAMP_FORMAT.format(new Date()));// 时间戳
        req.setMethod(method);// 调用接口
        
        // 如需对业务数据加密，则进行加密操作
        if(isDataEncrypt){
            try{
                LOG.debug("请求报文data加密，加密前: " + dataJson);
                dataJson = AES.encrypt(dataJson, appKey);
                LOG.debug("请求报文data加密，加密后: " + dataJson);
            }catch(CryptException e){
                LOG.error("AES加密发生异常", e);
            }
        }
        // 业务数据或加密后的业务数据
        req.setData(dataJson);
        
        // 如需进行签名，则生成签名
        if(isSign){
            try{
                // 加入appKey一起做数据签名
                LOG.debug("请求报文生成签名--开始");
                String sign = genReqSign(req);
                LOG.debug("请求报文生成签名--结束，sign: " + sign);
                req.setSign(sign);// 签名
            }catch(CryptException e){
                LOG.error("AES加密发生异常", e);
            }
        }
        return req;
    }
    
    /**
     * 发起请求,使用完整的请求报文
     *
     * @param reqJson 请求的json报文
     * @return
     */
    public OpenApiRsp execute(OpenApiReq req) throws ApiException{
        // 将请求对象转换成json字符串
        String reqJson = JSON.toJSONString(req);
        LOG.debug("请求：" + reqJson);
        String respStr = HttpRequester.postApi(gateway, reqJson);
        LOG.debug("响应：" + respStr);
        // 将响应消息从json转换成对象
        OpenApiRsp rsp = JSON.parseObject(respStr, OpenApiRsp.class);
        
        // 返回时成功的则验证签名
        if(OpenApiRsp.SUCCESS.equals(rsp.getReturnCode())){
            // 如果开启了签名，则需要校验签名
            if(isSign){
                if(!verifyRspSign(rsp)){
                    throw new ApiException("响应数据签名校验失败");
                }
            }
            
            // 如果data部分为加密传输则进行解密
            if(isDataEncrypt && rsp != null && rsp.getData() != null){
                try {
                  String decData = AES.decrypt(rsp.getData(), appKey);
                  rsp.setData(decData);
                } catch (CryptException e) {
                    LOG.error("业务数据解密失败", e);
                }
            }
        }
        return rsp;
    }
    
    /**
     * 发起请求，仅使用报文的data部分
     *
     * @param method
     * @param dataJson
     * @return
     */
    public OpenApiRsp execute(String method, String dataJson) throws ApiException{
        OpenApiReq req = genReqJson(method, dataJson);
        return execute(req);
    }
    
    /**
     * 生成请求签名
     *
     * @param req
     * @return
     */
    private String genReqSign(OpenApiReq req) throws CryptException{
        // 加入appKey一起做数据签名
        String[] signDataArray = new String[6];
        signDataArray[0] = req.getAppId();
        signDataArray[1] = req.getSerialNo();
        signDataArray[2] = req.getTimeStamp();
        signDataArray[3] = req.getMethod();
        signDataArray[4] = req.getData();
        signDataArray[5] = appKey;
        return SHA1.genSign(signDataArray);
    }
    
    /**
     * 校验响应签名
     *
     * @param rsp
     * @return
     */
    private boolean verifyRspSign(OpenApiRsp rsp){
        String[] signDatas = new String[]{appKey, rsp.getAppId(), rsp.getSerialNo(), 
                rsp.getTimeStamp(), rsp.getMethod(), rsp.getReturnCode(), 
                rsp.getReturnMsg(), rsp.getData()};
        return SHA1.verifySign(signDatas, rsp.getSign());
    }
    
}
