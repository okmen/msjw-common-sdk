package cn.sdk.bean.aj.open;

public class PayToBank extends BaseOpenRequest{
	private Integer amount;
	private String bankCode;
	private String accountType;
	private String bankNote;
	private String desc;
	private String encBankNo;
	private String encTrueName;
	private String orderId;
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankNote() {
		return bankNote;
	}
	public void setBankNote(String bankNote) {
		this.bankNote = bankNote;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEncBankNo() {
		return encBankNo;
	}
	public void setEncBankNo(String encBankNo) {
		this.encBankNo = encBankNo;
	}
	public String getEncTrueName() {
		return encTrueName;
	}
	public void setEncTrueName(String encTrueName) {
		this.encTrueName = encTrueName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}	
