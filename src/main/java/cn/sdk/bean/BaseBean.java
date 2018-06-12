package cn.sdk.bean;

import java.io.IOException;
import java.io.Serializable;

import cn.sdk.util.Gson4DateFormatUtil;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.GsonDateFormatUtil;
import cn.sdk.util.GsonUtil;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * bean基类
 * @author Mbenben
 */
public class BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String code;
	public String msg;
	public Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 普通转换
	 * 带有&符号 请使用toJsonBullider
	 * @return
	 */
	public String toJson(){
		return GsonUtil.toJson(this);
	}
	
	public String toGsonBuilder(){
		GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(
				String.class, new TypeAdapter<String>() {

					@Override
					public void write(JsonWriter out, String value)
							throws IOException {
						if (value == null) {
							// out.nullValue();
							out.value(""); // 序列化时将 null 转为 ""
						} else {
							out.value(value);
						}
					}

					@Override
					public String read(JsonReader in) throws IOException {
						if (in.peek() == JsonToken.NULL) {
							in.nextNull();
							return null;
						}
						// return in.nextString();
						String str = in.nextString();
						if (str.equals("")) { // 反序列化时将 "" 转为 null
							return null;
						} else {
							return str;
						}
					}

				});
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return gsonBuilder.create().toJson(this);
	}
	
	/**
	 * 对象中带有&符号的 需使用此方法
	 * @return
	 */
	public String toJsonBullider(){
		return GsonBuilderUtil.toJson(this);
	}
	
	/**
	 * 格式化日期类型  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public String toJson4DateFormat(){
		return Gson4DateFormatUtil.toJson(this);
	}
	
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public String toJsonDateFormat(){
		return GsonDateFormatUtil.toJson(this);
	}
}
