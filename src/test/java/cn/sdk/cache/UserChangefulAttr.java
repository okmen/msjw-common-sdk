package cn.sdk.cache;

import java.io.Serializable;
import java.util.Date;

//用户信息中频繁变化的属性
public class UserChangefulAttr implements Serializable{
	private static final long serialVersionUID = 7281352791847983083L;
	private long id;
	private Date lastLogin;
	private long showTotalCallTime;
	private int showGlamourValue;
	private int showFlowerNum;
	private int numOfPhotoGlamourLess;  //剩余照片魅力值，新注册用户120个魅力值，每上传一张扣6个魅力值，直到扣完为止
	private Date showLastLogin;

	public UserChangefulAttr(){
		
	}
	
	public UserChangefulAttr(long id) {
		this.id = id;
	}

	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public long getShowTotalCallTime() {
		return showTotalCallTime;
	}
	public void setShowTotalCallTime(long showTotalCallTime) {
		this.showTotalCallTime = showTotalCallTime;
	}
	public int getShowGlamourValue() {
		return showGlamourValue;
	}
	public void setShowGlamourValue(int showGlamourValue) {
		this.showGlamourValue = showGlamourValue;
	}
	public int getShowFlowerNum() {
		return showFlowerNum;
	}
	public void setShowFlowerNum(int showFlowerNum) {
		this.showFlowerNum = showFlowerNum;
	}
	public int getNumOfPhotoGlamourLess() {
		return numOfPhotoGlamourLess;
	}
	public void setNumOfPhotoGlamourLess(int numOfPhotoGlamourLess) {
		this.numOfPhotoGlamourLess = numOfPhotoGlamourLess;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@Override 
	public String toString(){
		String s = "id=" + id + ",lastLogin=" + lastLogin + ",showTotalCallTime=" + showTotalCallTime
				 + ",showGlamourValue=" + showGlamourValue + ",showFlowerNum=" + showFlowerNum 
				 + ",numOfPhotoGlamourLess=" + numOfPhotoGlamourLess;
		return s;
	}

	public Date getShowLastLogin() {
		return showLastLogin;
	}

	public void setShowLastLogin(Date showLastLogin) {
		this.showLastLogin = showLastLogin;
	}

}
