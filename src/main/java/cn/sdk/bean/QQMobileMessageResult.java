package cn.sdk.bean;

import java.io.Serializable;

public class QQMobileMessageResult implements Serializable{
	private int result;
	private String errmsg;
	private String sid;
	private int fee;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	@Override
	public String toString() {
		return "QQMobileMessageResult [result=" + result + ", errmsg=" + errmsg
				+ ", sid=" + sid + ", fee=" + fee + "]";
	}
}
