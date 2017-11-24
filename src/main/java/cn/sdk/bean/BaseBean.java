package cn.sdk.bean;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.sdk.util.Gson4DateFormatUtil;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.GsonUtil;

/**
 * bean基类
 * @author Mbenben
 */
public class BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String code;
	public String msg;
	public Object data;
	public Integer totle;
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
	
	/**
	 * 对象中带有&符号的 需使用此方法
	 * @return
	 */
	public String toJsonBullider(){
		return GsonBuilderUtil.toJson(this);
	}
	
	/**
	 * 格式化日期类型
	 * @return
	 */
	public String toJson4DateFormat(){
		return Gson4DateFormatUtil.toJson(this);
	}
	public Integer getTotle() {
		return totle;
	}
	public void setTotle(Integer totle) {
		this.totle = totle;
	}
}
