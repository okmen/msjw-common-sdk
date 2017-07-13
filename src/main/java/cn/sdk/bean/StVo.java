package cn.sdk.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class StVo implements Serializable{
	private String base64Img;
	private String imgDateTime;
	public String getBase64Img() {
		return base64Img;
	}
	public void setBase64Img(String base64Img) {
		this.base64Img = base64Img;
	}
	public String getImgDateTime() {
		return imgDateTime;
	}
	public void setImgDateTime(String imgDateTime) {
		this.imgDateTime = imgDateTime;
	}
	public StVo() {
	
	}
	public StVo(String base64Img, String imgDateTime) {
		this.base64Img = base64Img;
		this.imgDateTime = imgDateTime;
	}
	@Override 
    public String toString() { 
       return ReflectionToStringBuilder.toString(this); 
    }
}
