package cn.sdk.bean.aj.open;

public class PayToWechat extends BaseOpenRequest{
	private String openId;
	private String reUserName;
	private Integer amount;
	private String orderId;
	private String desc;
	private String ip;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getReUserName() {
		return reUserName;
	}
	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "PayToWechat [openId=" + openId + ", reUserName=" + reUserName
				+ ", amount=" + amount + ", orderId=" + orderId + ", desc="
				+ desc + ", ip=" + ip + "]";
	}
}
