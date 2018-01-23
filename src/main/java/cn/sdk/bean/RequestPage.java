package cn.sdk.bean;

import java.io.Serializable;
public class RequestPage implements Serializable{
	//当前页
	protected int page;
	//页大小
	protected int pageSize;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public RequestPage(int page,int pageSize)throws IllegalArgumentException{
		if(page <= 0 || pageSize < 1){
			throw new IllegalArgumentException("页数或页码为负，不合法");
		}
		
		this.page = (page-1) * pageSize;
		this.pageSize = pageSize;
	}
	public RequestPage(){}
	@Override
	public String toString() {
		return "RequestPage [page=" + page + ", pageSize=" + pageSize + "]";
	}
}
