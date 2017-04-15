package cn.sdk.bean;

import java.io.Serializable;

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
	
	
}
