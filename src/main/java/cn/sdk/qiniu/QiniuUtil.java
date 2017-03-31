package cn.sdk.qiniu;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.EncodeUtils;

public class QiniuUtil {
	public static String ACCESS_KEY = "8dTF_hipQKpZa9ttp_BuLio2TqlQk3JCRF0XoYKv";
	public static String SECRET_KEY = "QKmTW3d2Im__bbgVnbijXQCdsa41n_LBYcuZAcS3";
	public static String bucketName = "img-bilin";
	public static String qiniuImgDomain = "http://img.onbilin.com";
	private String callbackUrl;

	public static String signData(byte[] data) throws AuthException,
			IllegalStateException, UnsupportedEncodingException {
		String charset = "utf-8";
		Mac mack = new Mac(ACCESS_KEY, SECRET_KEY);
		byte[] secretKey = mack.secretKey.getBytes(charset);
		javax.crypto.Mac mac = null;
		try {
			mac = javax.crypto.Mac.getInstance("HmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new AuthException("No algorithm called HmacSHA1!", e);
		}
		SecretKeySpec keySpec = new SecretKeySpec(secretKey, "HmacSHA1");
		try {
			mac.init(keySpec);
			mac.update(data);
		} catch (InvalidKeyException e) {
			throw new AuthException("You've passed an invalid secret key!", e);
		} catch (IllegalStateException e) {
			throw new AuthException(e);
		}

		byte[] digest = mac.doFinal();
		byte[] digestBase64 = EncodeUtils.urlsafeEncodeBytes(digest);
		return new String(digestBase64, charset);
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

}
