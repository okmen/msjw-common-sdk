package cn.sdk.cache;

import java.io.Serializable;
import java.util.Date;

//用户基础属性
public class UserBaseAttr implements Serializable{
	private static final long serialVersionUID = 564030970612965052L;
	private long id;
	private String username;
	private String password;
	private String nickname;
	private int enabled;
	private Date createOn;
	private long bigHeadImgId;
	private String clientType;
	private String version;
	private String platform;
	private String token;
	private String setupMark;
	private int photoNum;
	private String osVersion;
	private Date birthday;
	private String career;
	private int showSex;
	private String jpushId;
    private long groupId; //if groupId > 0 ：体验师，否则是普通用户
    private String sign;
    private int sexOrientation;
    private String miPushId;//小米push唯一标识
    private String deviceTokens;//umeng唯一标识
    private String _bigHeadUrl; //用户大头像，私有变量，不属于数据库字段
    private String _smallHeadUrl; //用户小头像，私有变量，不属于数据库字段

    public UserBaseAttr(){
    	
    }
    
	public UserBaseAttr( long id ){
		this.id = id;
	}
	
	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getSetupMark() {
		return setupMark;
	}

	public void setSetupMark(String setupMark) {
		this.setupMark = setupMark;
	}

	public long getBigHeadImgId() {
		return bigHeadImgId;
	}

	public void setBigHeadImgId(long bigHeadImgId) {
		this.bigHeadImgId = bigHeadImgId;
	}

	public int getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getJpushId() {
		return jpushId;
	}

	public void setJpushId(String jpushId) {
		this.jpushId = jpushId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public int getShowSex() {
		return showSex;
	}

	public void setShowSex(int showSex) {
		this.showSex = showSex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public int getSexOrientation() {
		return sexOrientation;
	}

	public void setSexOrientation(int sexOrientation) {
		this.sexOrientation = sexOrientation;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMiPushId() {
		return miPushId;
	}

	public void setMiPushId(String miPushId) {
		this.miPushId = miPushId;
	}

	public String getDeviceTokens() {
		return deviceTokens;
	}

	public void setDeviceTokens(String deviceTokens) {
		this.deviceTokens = deviceTokens;
	}

	public String getBigHeadUrl() {
		return _bigHeadUrl;
	}

	public void setBigHeadUrl(String bigHeadUrl) {
		this._bigHeadUrl = bigHeadUrl;
	}

	public String getSmallHeadUrl() {
		return _smallHeadUrl;
	}

	public void setSmallHeadUrl(String smallHeadUrl) {
		this._smallHeadUrl = smallHeadUrl;
	}
	
	@Override
	public String toString(){
	    String s = "id=" + id + ",username=" + username + ",password=" + password + ",nickname=" + nickname 
	    		 + ",enabled=" + enabled + ",createOn=" + createOn + ",bigHeadImgId=" + bigHeadImgId
	    		 + ",clientType=" + clientType + ",version=" + version + ",platform=" + platform
	    		 + ",token=" + token + ",setupmark=" + setupMark + ",photoNum=" + photoNum+",osVersion=" + osVersion
	    		 + ",birthday=" + birthday + ",career="+ career + ",showSex=" + showSex + ",jpushId=" + jpushId
	    		 + ",groupId=" + groupId + ",sign=" + sign + ",sexOrientation=" + sexOrientation
	    		 + ",mipushId=" + miPushId + ",deviceTokens=" + deviceTokens; 
		return s;
	}

	
}
