package cn.sdk.exception.szga;

import cn.sdk.util.SzgaMsgCode;

public class ServiceException extends Exception{
	private String code;

	public ServiceException(String code,String msg){
		super(msg);
		this.code = code;
	}
	
	public ServiceException(String msg){
		super(msg);
		this.code = SzgaMsgCode.SERVICE_ERROR;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
