package cn.sdk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 日期gson转换类
 * @author gaoxigang
 *
 */
public class Gson4DateFormatUtil {
	static public Gson gson = null;
	static{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gson = gsonBuilder.create();  
	}
	static public <T> T fromJson(String src,Class<T> tClass){
		return gson.fromJson(src, tClass);
	}
	
	static public String toJson(Object obj){
		return gson.toJson(obj);
	}
	
}
