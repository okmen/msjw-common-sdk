package cn.sdk.util;
import org.apache.commons.lang3.StringUtils;
public class ReplaceQiniuImg {
	private static final String QINIU_IMG_URL = "http://img-bilin.qiniudn.com/";
	private static final String BILIN_IMG_URL = "http://img.onbilin.com/";
	private static final String QINIU_IMG_URL_TEMP = "http://7d9kvt.com1.z0.glb.clouddn.com/";
	public static String replaceQiniuImg(String url){
		if(StringUtils.isBlank(url)){
			return "";
		}
		return url.replaceAll(QINIU_IMG_URL, BILIN_IMG_URL).replaceAll(QINIU_IMG_URL_TEMP, BILIN_IMG_URL);
	}
}
