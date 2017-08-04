package cn.sdk.bean;

import java.io.Serializable;

public class ResponsePage implements Serializable{
	protected int pageCount;
	protected int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public ResponsePage(int pageCount){
		this.pageCount = pageCount;
	}
}
