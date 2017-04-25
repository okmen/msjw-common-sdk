package cn.sdk.bean;

public class ErrorBean extends BaseBean{
	public ErrorBean(String code,String msg){
		super.code = code;
		super.msg = msg;
	}
	
	public ErrorBean(){}
}
